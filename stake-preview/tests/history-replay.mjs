import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {fileURLToPath} from 'node:url';
import {dirname,join} from 'node:path';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V0\.7/);
assert.match(html,/id="historyBtn"/);
assert.match(html,/id="historyOverlay"/);
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script,'Game script exists');
const source=script.split('$("spin").addEventListener')[0];
const elements=new Map(),buttons=[];
function elem(id){
  if(!elements.has(id)){
    const cl=new Set();
    elements.set(id,{
      id,innerHTML:'',textContent:'',hidden:false,style:{},dataset:{},disabled:false,
      classList:{add(n){cl.add(n)},remove(n){cl.delete(n)},contains(n){return cl.has(n)},toggle(n,on){on?cl.add(n):cl.delete(n)}},
      setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []},children:[]
    });
  }
  return elements.get(id);
}
const cols=Array.from({length:5},(_,i)=>({
  children:[0,1,2].map(j=>elem('cell'+i+','+j)),
  querySelectorAll(){return this.children}
}));
const doc={
  getElementById:elem,
  querySelectorAll(selector){
    if(selector==='[data-bet]')return buttons;
    if(selector==='.column')return cols;
    if(selector==='.cell')return cols.flatMap(x=>x.children);
    return [];
  }
};
for(const value of [.2,1,7.5,10]){
  const b=elem('button'+value);
  b.dataset.bet=String(value);
  buttons.push(b);
}
let randSeed=123458345;
const fakeCrypto={
  getRandomValues(arr){randSeed=(Math.imul(randSeed,1664525)+1013904223)>>>0;arr[0]=randSeed;return arr}
};
const saved=new Map();
const localStorage={
  setItem(k,v){saved.set(k,v)},
  getItem(k){return saved.has(k)?saved.get(k):null},
  removeItem(k){saved.delete(k)}
};
const context=vm.createContext({
  document:doc,window:{},console,crypto:fakeCrypto,Uint32Array,Intl,Math,Date,
  localStorage,setTimeout(callback){Promise.resolve().then(callback);return 1},clearTimeout(){}
});
vm.runInContext(source,context,{timeout:10000});
vm.runInContext('betIndex=BETS.indexOf(7.5);skipAnimations=true;turbo=true;',context);
const startBalance=vm.runInContext('balance',context);
await vm.runInContext('play("hold")',context);
const records=vm.runInContext('historyRecords',context);
assert.equal(records.length,1,'One actual completed Hold purchase stored');
assert.equal(records[0].mode,'hold');
assert.equal(records[0].bet,7.5);
assert.equal(records[0].cost,225);
assert.equal(records[0].payout,vm.runInContext('lastWin',context));
const afterBalance=vm.runInContext('balance',context);
assert.ok(Math.abs(afterBalance-(startBalance-225+records[0].payout))<1e-5);
const originalBook=JSON.stringify(records[0].book);
const originalId=records[0].id;
assert.ok(saved.has('jardim_stake_preview_v07'),'Session written to browser storage');
vm.runInContext('betIndex=BETS.indexOf(0.2)',context);
vm.runInContext('showHistory()',context);
assert.equal(elem('historyOverlay').classList.contains('show'),true);
assert.match(elem('historyList').innerHTML,/HOLD & WIN/);
assert.match(elem('historyList').innerHTML,/7,50/);
assert.match(elem('historyList').innerHTML,/225,00/);
context.originalId=originalId;
const replayed=await vm.runInContext('replayRound(originalId)',context);
assert.equal(replayed,true);
assert.equal(elem('historyOverlay').classList.contains('show'),false);
assert.equal(vm.runInContext('balance',context),afterBalance,'Replay must not alter player balance');
assert.equal(vm.runInContext('historyRecords.length',context),1,'Replay is not another spin');
assert.equal(JSON.stringify(vm.runInContext('historyRecords[0].book',context)),originalBook,'Replay preserves every winning event');
assert.equal(vm.runInContext('replayBet',context),null,'Replay wager override cleared');
assert.equal(vm.runInContext('lastWin',context),records[0].payout);

vm.runInContext('balance=0;lastWin=0;betIndex=0;historyRecords=[];serial=0',context);
vm.runInContext('loadSession()',context);
assert.equal(vm.runInContext('balance',context),afterBalance,'Restore saved credits');
assert.equal(vm.runInContext('historyRecords.length',context),1,'Restore saved round');
assert.equal(vm.runInContext('serial',context),originalId,'Restore round numbering');
vm.runInContext('historyRecords=[];saveSession();renderHistory()',context);
assert.match(elem('historyList').innerHTML,/Ainda não há jogadas/);
assert.equal(vm.runInContext('historyRecords.length',context),0);

console.log('Stake Preview V0.7: Hold @7,50, replay with same book and no credit changes, session recovery, history clear passed.');
