import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.9\.8/);
for(const id of ['freePanel','freeRemaining','freeRaw','freeMult','freeRetrigger','freeResultOverlay','freeResultTotal','freeResultPlayed','freeResultRaw','freeResultMulti','closeFree']){
  assert.ok(html.includes('id="'+id+'"'),'missing Free Spins element: '+id);
}
assert.match(html,/\$\("closeFree"\)\.addEventListener\("click",acknowledgeFreeContinue\)/);
const code=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(code,'game logic found');
const elements=new Map();
function el(id) {
  if(!elements.has(id)){
    const cl=new Set();
    elements.set(id,{
      id,hidden:false,disabled:false,innerHTML:'',textContent:'',style:{},dataset:{},children:[],
      classList:{add(k){cl.add(k)},remove(k){cl.delete(k)},contains(k){return cl.has(k)},toggle(k,v){v?cl.add(k):cl.delete(k)}},
      setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []},
    });
  }
  return elements.get(id);
}
const quick=[.2,1,7.5,10].map(b=>{const e=el('quick-'+b);e.dataset.bet=String(b);return e});
let seed=0x20041113;
const saved=new Map();
const ctx=vm.createContext({
  document:{hidden:false,getElementById:el,querySelectorAll(s){return s==='[data-bet]'?quick:[]}},
  localStorage:{getItem:k=>saved.get(k)??null,setItem:(k,v)=>saved.set(k,v)},
  window:{},console,Math,Date,Intl,Uint32Array,
  crypto:{getRandomValues(a){seed=(Math.imul(seed,1664525)+1013904223)>>>0;a[0]=seed;return a}},
  setTimeout(callback){Promise.resolve().then(callback);return 1},
  clearTimeout(){},
});
vm.runInContext(code,ctx,{timeout:15000});
vm.runInContext('betIndex=BETS.indexOf(7.5);turbo=true;',ctx);
vm.runInContext('showFreeRetrigger(8)',ctx);
assert.equal(el('freeRetrigger').hidden,false);
assert.match(el('freeRetrigger').textContent,/RETRIGGER \+8/);
vm.runInContext('showFreeRetrigger(15)',ctx);
assert.match(el('freeRetrigger').textContent,/RETRIGGER \+15/);
vm.runInContext('showFreeRetrigger(0)',ctx);
assert.equal(el('freeRetrigger').hidden,true);

const generated=vm.runInContext('generate("free")',ctx);
assert.equal(generated.cost,20);
const finalEvent=generated.events.find(e=>e.kind==='freeEnd');
assert.ok(finalEvent);
assert.equal(finalEvent.amount,generated.payout);
assert.ok(finalEvent.played>=15&&finalEvent.played<=60);
const progresses=generated.events.filter(e=>e.kind==='progress');
assert.equal(progresses.length,finalEvent.played);
assert.ok(progresses.every(e=>Number.isInteger(e.left)&&e.left>=0&&Number.isInteger(e.add)));
assert.ok(progresses.every(e=>e.add===0||e.add===8||e.add===15||e.add<15),'retrigger count valid');

async function waitForModal(promise){
  let showing=false;
  for(let i=0;i<25000;i++){
    if(vm.runInContext('typeof pendingFreeContinue',ctx)==='function'){showing=true;break;}
    await Promise.resolve();
  }
  assert.equal(showing,true,'Free Spins must await a click at the final result');
  assert.equal(el('freeResultOverlay').classList.contains('show'),true);
  // Do not adopt the still-pending play promise here: the test must click CONTINUAR first.
  return true;
}
const initial=vm.runInContext('balance',ctx);
const operation=vm.runInContext('play("free")',ctx);
await waitForModal(operation);
assert.equal(vm.runInContext('busy',ctx),true);
assert.ok(el('freePanel').hidden===false,'HUD remains during the last result');
assert.equal(el('freeRemaining').textContent,0);
assert.equal(vm.runInContext('balance',ctx),initial-150,'buy cost debited once');
const pending=vm.runInContext('pendingRound',ctx);
assert.equal(pending.mode,'free');
assert.equal(pending.bet,7.5);
assert.equal(pending.cost,150);
assert.equal(vm.runInContext('historyRecords.length',ctx),0,'not settled until CONTINUAR');
const expected=+pending.payout;
const savedEnd=pending.book.events.find(e=>e.kind==='freeEnd');
assert.equal(el('freeResultTotal').textContent,vm.runInContext('money('+savedEnd.amount+')',ctx)+' créditos');
assert.equal(String(el('freeResultPlayed').textContent),String(savedEnd.played));
assert.equal(el('freeResultRaw').textContent,vm.runInContext('money('+savedEnd.raw+')',ctx));
assert.equal(el('freeResultMulti').textContent,'×'+savedEnd.multi);
assert.ok(saved.has('jardim_stake_preview_v07'),'pending outcome persisted');
assert.equal(vm.runInContext('acknowledgeFreeContinue()',ctx),true);
await operation;
assert.ok(Math.abs(vm.runInContext('balance',ctx)-(initial-150+expected))<1e-7);
assert.equal(vm.runInContext('lastWin',ctx),expected);
assert.equal(vm.runInContext('pendingRound',ctx),null);
assert.equal(vm.runInContext('historyRecords.length',ctx),1);
assert.equal(el('freeResultOverlay').classList.contains('show'),false);
assert.equal(el('freePanel').hidden,true);
assert.equal(vm.runInContext('acknowledgeFreeContinue()',ctx),false,'double click is inert');
const after=vm.runInContext('balance',ctx);
const recordedId=vm.runInContext('historyRecords[0].id',ctx);
ctx.recordedId=recordedId;
vm.runInContext('betIndex=0;',ctx);
const replay=vm.runInContext('replayRound(recordedId)',ctx);
await waitForModal(replay);
assert.equal(vm.runInContext('acknowledgeFreeContinue()',ctx),true);
assert.equal(await replay,true);
assert.equal(vm.runInContext('balance',ctx),after,'replay never credits twice');
assert.equal(vm.runInContext('historyRecords.length',ctx),1);
assert.equal(vm.runInContext('replayBet',ctx),null);

// PASSAR before the final result may skip the manual hold, without changing the result.
vm.runInContext('skipAnimations=true',ctx);
const another=vm.runInContext('generate("free")',ctx);
ctx.another=another;
await vm.runInContext('playback(another)',ctx);
assert.equal(vm.runInContext('pendingFreeContinue',ctx),null);
assert.equal(el('freeResultOverlay').classList.contains('show'),false);
assert.equal(vm.runInContext('balance',ctx),after);
assert.equal(vm.runInContext('acknowledgeFreeContinue()',ctx),false);
console.log('Stake Preview V1.9.8: Free Spins final CONTINUAR, retriggers, 7,50, accounting once, replay and PASSAR passed.');
