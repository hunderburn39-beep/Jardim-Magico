import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.1/);
for(const id of ['holdOverlay','holdGrid','holdLives','holdWin','holdSummary','holdSummaryValue','holdSummaryLabel','closeHold']){
  assert.ok(html.includes('id="'+id+'"'),'missing HOLD element '+id);
}
assert.match(html,/\$\("closeHold"\)\.addEventListener\("click",acknowledgeHoldContinue\)/);
const code=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(code);
const elements=new Map();
function el(id){
  if(!elements.has(id)){
    const classes=new Set();
    elements.set(id,{
      id,innerHTML:'',textContent:'',style:{},hidden:true,disabled:false,children:[],
      classList:{add(k){classes.add(k)},remove(k){classes.delete(k)},contains(k){return classes.has(k)},toggle(k,v){v?classes.add(k):classes.delete(k)}},
      setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []}
    });
  }
  return elements.get(id);
}
const betButtons=[.2,1,7.5,10].map(b=>{const e=el('bet'+b);e.dataset={bet:String(b)};return e});
let randSeed=991113;
const store=new Map();
const context=vm.createContext({
  document:{hidden:false,getElementById:el,querySelectorAll(s){if(s==='[data-bet]')return betButtons;return []}},
  localStorage:{getItem:k=>store.get(k)??null,setItem:(k,v)=>store.set(k,v)},
  window:{},Math,Intl,Date,console,Uint32Array,
  crypto:{getRandomValues(arr){randSeed=(Math.imul(randSeed,1664525)+1013904223)>>>0;arr[0]=randSeed;return arr}},
  setTimeout(f){Promise.resolve().then(f);return 1},
  clearTimeout(){}
});
vm.runInContext(code,context,{timeout:10000});
vm.runInContext('betIndex=BETS.indexOf(7.5);turbo=true',context);
const six=[1,2,0,0,0,0,1,3,5,0,0,0,0,0,0,0];
context.six=six;
vm.runInContext('renderHold(six,3,Array(16).fill(0))',context);
const grid=el('holdGrid').innerHTML;
assert.equal((grid.match(/class="coin-slot /g)||[]).length,16,'4×4 grid with 16 slots');
assert.match(grid,/×5/,'coin face carries its multiplier');
assert.match(grid,/37,50/,'coin displays monetary credit value at bet 7.50');
assert.equal((el('holdLives').innerHTML.match(/active/g)||[]).length,3,'three visual tries');
assert.equal(el('holdWin').textContent,'ACUMULADO 90,00','accumulated virtual credits');
vm.runInContext('renderHold(six,2,six)',context);
assert.equal((el('holdLives').innerHTML.match(/active/g)||[]).length,2,'tries decrease to two');
vm.runInContext('renderHold(six,0,six)',context);
assert.equal((el('holdLives').innerHTML.match(/active/g)||[]).length,0,'no tries at end');

vm.runInContext('skipAnimations=false',context);
const unfinished=vm.runInContext('waitHoldContinue()',context);
let resolved=false;
unfinished.then(()=>{resolved=true});
await Promise.resolve();
assert.equal(resolved,false,'the final result does not close automatically');
assert.equal(vm.runInContext('typeof pendingHoldContinue',context),'function','still awaiting a real click');
el('holdOverlay').classList.add('show');
el('closeHold').hidden=false;
assert.equal(vm.runInContext('acknowledgeHoldContinue()',context),true,'CONTINUAR acknowledges exactly once');
await unfinished;
assert.equal(resolved,true);
assert.equal(el('holdOverlay').classList.contains('show'),false);
assert.equal(el('closeHold').hidden,true);
assert.equal(vm.runInContext('acknowledgeHoldContinue()',context),false,'repeated click cannot settle twice');

vm.runInContext('skipAnimations=true',context);
await vm.runInContext('waitHoldContinue()',context);
assert.equal(vm.runInContext('pendingHoldContinue',context),null,'PASSAR bypasses visual hold');
vm.runInContext('skipAnimations=false',context);

const initial=vm.runInContext('balance',context);
const operation=vm.runInContext('play("hold")',context);
assert.equal(vm.runInContext('busy',context),true);
for(let i=0;i<80;i++)await Promise.resolve();
assert.equal(vm.runInContext('typeof pendingHoldContinue',context),'function','actual game awaits confirmation');
assert.equal(el('holdOverlay').classList.contains('show'),true);
assert.equal(el('holdSummary').hidden,false);
assert.equal(el('closeHold').hidden,false);
assert.match(el('holdSummaryValue').textContent,/^\d[\d\s]*,\d{2} créditos$/);
const onHoldBalance=vm.runInContext('balance',context);
assert.equal(onHoldBalance,initial-225,'buy debit is committed before visual result');
assert.equal(vm.runInContext('historyRecords.length',context),0,'history not settled until confirmation');
const credits=vm.runInContext('pendingRound.payout',context);
assert.ok(credits>=0,'actual pending result resolved before CONTINUAR');
assert.equal(vm.runInContext('acknowledgeHoldContinue()',context),true);
await operation;
const final=vm.runInContext('balance',context);
assert.ok(Math.abs(final-(initial-225+credits))<1e-6,'pressing CONTINUAR settles exactly the original award');
assert.equal(vm.runInContext('pendingRound',context),null,'pending state cleared');
assert.equal(vm.runInContext('historyRecords.length',context),1,'result stored as one round');
assert.equal(vm.runInContext('lastWin',context),credits);
assert.equal(vm.runInContext('acknowledgeHoldContinue()',context),false);
assert.equal(vm.runInContext('balance',context),final,'extra clicks do not pay twice');

// A second actual round must still require CONTINUAR and settle once.
const another=vm.runInContext('play("hold")',context);
for(let n=0;n<160;n++){
  if(vm.runInContext('typeof pendingHoldContinue',context)==='function')break;
  await Promise.resolve();
}
assert.equal(vm.runInContext('typeof pendingHoldContinue',context),'function');
assert.equal(vm.runInContext('acknowledgeHoldContinue()',context),true);
await another;
assert.equal(vm.runInContext('pendingHoldContinue',context),null);
assert.equal(el('holdOverlay').classList.contains('show'),false);
assert.equal(vm.runInContext('historyRecords.length',context),2,'a second manual confirmation records exactly one new round');

console.log('Stake Preview V1.8.1: Hold 4×4 values at 7,50, three lives, manual CONTINUAR and single settlement verified.');
