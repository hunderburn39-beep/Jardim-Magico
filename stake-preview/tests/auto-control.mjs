import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.2/);
assert.match(html,/id="autoHeading"/);
assert.match(html,/id="autoLabel"/);
assert.match(html,/action-small\.auto-running/);
const code=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(code,'Game JS has an isolated game engine');
const elements=new Map();
function el(id){
  if(!elements.has(id)){
    const states=new Set();
    elements.set(id,{
      innerHTML:'',textContent:'',hidden:false,disabled:false,dataset:{},style:{},children:[],
      classList:{add:k=>states.add(k),remove:k=>states.delete(k),contains:k=>states.has(k),toggle(k,v){if(v)states.add(k);else states.delete(k)}},
      setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []}
    });
  }
  return elements.get(id);
}
const buttons=[.2,1,7.5,10].map(v=>{const e=el('quick'+v);e.dataset.bet=String(v);return e});
let nextTimer=0;
const sleepers=new Map();
const delayed190=[];
const pendingLong=[];
function fakeTimeout(fn,ms){
  const id=++nextTimer;
  sleepers.set(id,{fn,ms,cancelled:false});
  if(ms===190)delayed190.push(id);
  else if(ms>=5000)pendingLong.push(id);
  else Promise.resolve().then(()=>{const t=sleepers.get(id);if(t&&!t.cancelled)t.fn()});
  return id;
}
function fakeClear(id){const t=sleepers.get(id);if(t)t.cancelled=true}
function trigger(id){
  const t=sleepers.get(id);
  if(t&&!t.cancelled){t.cancelled=true;t.fn();return true}
  return false;
}
const store=new Map();
const ctx=vm.createContext({
  document:{hidden:false,getElementById:el,querySelectorAll(k){return k==='[data-bet]'?buttons:[]}},
  localStorage:{getItem:k=>store.get(k)??null,setItem:(k,v)=>store.set(k,v)},
  Date,Math,Intl,Uint32Array,console,
  setTimeout:fakeTimeout,clearTimeout:fakeClear
});
vm.runInContext(code,ctx,{timeout:10000});
const run=x=>vm.runInContext(x,ctx);
const turn=async(n=120)=>{for(let i=0;i<n;i++)await Promise.resolve()};
run('betIndex=BETS.indexOf(7.5);generate = mode => ({cost:1,payout:100,events:[]});');
const initial=run('balance');
const length=()=>run('historyRecords.length');
const remaining=()=>run('autoLeft');
const busy=()=>run('busy');

// PASSAR terminates a delay that has already started, not just future pauses.
run('busy=true;skipAnimations=false');
const longDelay=run('pause(10000)');
assert.equal(run('activePauses.size'),1);
assert.equal(run('skipCurrentAnimations()'),true);
await longDelay;
assert.equal(run('activePauses.size'),0);
assert.equal(run('skipAnimations'),true);
assert.equal(pendingLong.length,1);
assert.equal(sleepers.get(pendingLong[0]).cancelled,true,'10-second timer cleared immediately');
assert.equal(run('skipCurrentAnimations()'),true,'PASSAR is idempotent while busy');
run('busy=false;skipAnimations=false');
assert.equal(run('skipCurrentAnimations()'),false,'PASSAR does nothing when idle');
assert.equal(length(),0,'No results modified by PASSAR');

// Cancelled while spin 1 is in progress: that spin must finish and count once.
assert.equal(run('toggleAuto()'),true);
assert.equal(remaining(),5);
assert.equal(busy(),true);
assert.equal(el('auto').disabled,false,'PARAR works during current spin');
assert.equal(el('autoHeading').textContent,'■ PARAR');
assert.equal(el('auto').classList.contains('auto-running'),true);
assert.equal(el('auto')['aria-pressed'],'true');
assert.equal(el('spin').disabled,true);
assert.equal(run('stopAuto()'),true);
await turn();
assert.equal(length(),1);
assert.equal(busy(),false);
assert.equal(remaining(),0);
assert.equal(el('autoHeading').textContent,'↻ AUTO');
assert.equal(el('auto')['aria-pressed'],'false');
assert.equal(run('stopAuto()'),false,'Cannot cancel twice');
assert.equal(delayed190.length,0,'Stopped spin never schedules another spin');

// Stop during the gap and restart a new sequence before the old timeout fires.
assert.equal(run('toggleAuto()'),true);
await turn();
assert.equal(length(),2);
assert.equal(busy(),false);
assert.equal(remaining(),4,'Four future rounds after the first completed');
assert.equal(el('autoLabel').textContent,'4 RESTAM');
assert.equal(delayed190.length,1);
const oldTimer=delayed190.shift();
const oldSession=run('autoSession');
assert.equal(run('stopAuto()'),true);
assert.equal(run('autoSession'),oldSession+1);
assert.equal(run('toggleAuto()'),true);
await turn();
assert.equal(length(),3,'First spin of the newly started sequence completed');
assert.equal(remaining(),4);
assert.equal(trigger(oldTimer),true,'Old delay resolves after a new AUTO begins');
await turn();
assert.equal(length(),3,'Old session must never start an extra spin');
assert.equal(remaining(),4);

// Finish precisely five spins of the current session, with stable balance.
for(let i=0;i<4;i++){
  const fresh=delayed190.shift();
  assert.ok(fresh,'Next auto spin has a scheduled interval');
  assert.equal(trigger(fresh),true);
  await turn();
  assert.equal(length(),4+i,'Exactly one new spin per interval');
  assert.equal(remaining(),3-i);
}
assert.equal(remaining(),0);
assert.equal(busy(),false);
assert.equal(el('autoHeading').textContent,'↻ AUTO');
assert.equal(length(),7,'One stopped spin + one stopped interval spin + five new spins');
assert.equal(run('balance'),initial,'Seven 7.50 spins that each pay 7.50 never change the balance');
assert.equal(run('historyRecords.length'),7);
assert.equal(run('pendingRound'),null,'No unfinished transaction');
assert.equal(run('serial'),7,'No duplicated records');

// Low balance: never enter AUTO and never debit.
run('balance=0');
assert.equal(run('toggleAuto()'),false);
assert.equal(remaining(),0);
assert.equal(busy(),false);
assert.equal(length(),7);
assert.match(el('notice').textContent,/Saldo insuficiente/);
run('balance=1000;busy=true');
assert.equal(run('toggleAuto()'),false,'AUTO cannot start while a manual spin or replay is running');
run('busy=false');
console.log('Stake Preview V1.8.2: immediate PASSAR, stop during spin/gap, restart safety, five spins, balance and records passed.');
