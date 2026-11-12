import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {fileURLToPath} from 'node:url';
import {dirname,join} from 'node:path';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.1/);
for(const name of ['paytableBtn','paytableOverlay','paytableStake','paytableGrid','paytableRules','paylineSheet','closePaytable','closePaytableBottom']){
  assert.ok(html.includes('id="'+name+'"'),'Paytable element exists: '+name);
}
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script);
const code=script.split('$("introPlay").addEventListener')[0];
const els=new Map();
function el(id){
  if(!els.has(id)){
    const set=new Set();
    els.set(id,{innerHTML:'',textContent:'',hidden:false,disabled:false,style:{},dataset:{},
      classList:{add(k){set.add(k)},remove(k){set.delete(k)},contains(k){return set.has(k)},toggle(k,v){v?set.add(k):set.delete(k)}},
      setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []}});
  }
  return els.get(id);
}
const local=new Map();
let seed=127991;
const ctx=vm.createContext({
 document:{hidden:false,getElementById:el,querySelectorAll(){return []}},
 localStorage:{getItem:k=>local.get(k)??null,setItem:(k,v)=>local.set(k,v)},
 console,Math,Intl,Date,Uint32Array,window:{},
 crypto:{getRandomValues(a){seed=(Math.imul(seed,1664525)+1013904223)>>>0;a[0]=seed;return a}},
 setTimeout,clearTimeout,
});
vm.runInContext(code,ctx,{timeout:10000});
const p=vm.runInContext('prizeForSymbol',ctx);
const evaluate=vm.runInContext('evaluate',ctx);
const rows=vm.runInContext('LINES',ctx);
const coeffs=vm.runInContext('COEFS',ctx);
const pay=vm.runInContext('PAY',ctx);
assert.equal(rows.length,10,'10 paylines present');
assert.equal(new Set(rows.map(r=>r.join(','))).size,10,'10 distinct paylines');
assert.ok(rows.every(line=>line.length===5&&line.every(i=>i>=0&&i<=2)));
for(const bet of [.05,.2,1,7.5,10]){
  for(let type=0;type<9;type++){
    for(const count of [3,4,5]){
      const board=Array.from({length:5},()=>[10,10,10]);
      for(let i=0;i<count;i++)board[i][1]=type;
      const result=evaluate(board);
      const line=result.lines.find(l=>l.line===1);
      assert.ok(line,'expected payline 1 for symbol '+type+' count '+count);
      const expected=+(line.amount*bet/100).toFixed(2);
      const shown=p(type,count,bet);
      assert.ok(Math.abs(shown-expected)<1e-8,'paytable must use actual engine outcome '+[bet,type,count,shown,expected]);
      if(type!==4)assert.equal(line.amount,coeffs[type]*pay[count],'Regular symbol payline coefficient must match motor');
      assert.equal(line.cells.length,count,'winning line stops on last match');
    }
  }
}
assert.equal(p(9,3,7.5),0,'Scatter no line win');
assert.equal(p(11,5,7.5),0,'Coins no line win');
assert.equal(p(4,3,7.5),4.13,'Three WILDs followed by a stone pay only the WILD pair');
assert.equal(p(4,4,7.5),4.13,'Four WILDs followed by a stone pay only the WILD pair');
assert.equal(p(4,5,7.5),64.13,'Five WILDs pay 8.55x including the extra WILD pair');
assert.equal(p(0,3,7.5),1.58,'Caracol 3 at bet 7.50');
assert.equal(p(8,5,7.5),42,'Flor vermelha 5 at bet 7.50');

const moneyBefore=vm.runInContext('balance',ctx);
vm.runInContext('betIndex=BETS.indexOf(7.5)',ctx);
const result=vm.runInContext('showPaytable()',ctx);
assert.equal(result,true);
assert.equal(el('paytableOverlay').classList.contains('show'),true);
assert.equal(el('paytableStake').textContent.includes('7,50'),true);
assert.equal(el('paytableGrid').innerHTML.split('class="paytable-row"').length-1,9);
assert.match(el('paytableGrid').innerHTML,/4,13/);
assert.match(el('paytableRules').innerHTML,/150,00/,'Free Spins cost 20x at 7.5');
assert.match(el('paytableRules').innerHTML,/225,00/,'Hold cost 30x at 7.5');
assert.match(el('paytableRules').innerHTML,/1(?:\s|.)?875,00/,'Full 250x prize at 7.5');
assert.equal(el('paylineSheet').innerHTML.split('class="payline-card"').length-1,10);
assert.equal(el('paylineSheet').innerHTML.split('class="active"').length-1,50,'50 active line markers total');
vm.runInContext('hidePaytable()',ctx);
assert.equal(el('paytableOverlay').classList.contains('show'),false);
assert.equal(vm.runInContext('balance',ctx),moneyBefore,'Paytable must not alter credits');
assert.equal(vm.runInContext('historyRecords.length',ctx),0,'Paytable must not create a round');
vm.runInContext('busy=true',ctx);
assert.equal(vm.runInContext('showPaytable()',ctx),false,'No paytable while recording a spin');
vm.runInContext('busy=false',ctx);
console.log('Stake Preview V1.1: 135 payline payouts checked against motor, ten line diagrams, free/hold prices at bet 7,50.');
