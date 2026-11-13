import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.9\.7/);
assert.match(html,/id="sessionStatus"/);
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(script);

let limit=160000,blocked=false;
const store=new Map();
function newQuota(){
  const error=new Error('Storage quota exceeded'); error.name='QuotaExceededError';
  return error;
}
const storage={
  getItem:k=>store.get(k)??null,
  setItem(k,v){
    if(blocked){const e=new Error('Storage disabled');e.name='SecurityError';throw e}
    if(v.length>limit)throw newQuota();
    store.set(k,v);
  },
  removeItem:k=>store.delete(k)
};
let seed=932184;
function makeContext(){
  const els=new Map();
  function get(id){
    if(!els.has(id)){
      const classes=new Set();
      els.set(id,{id,style:{},dataset:{},hidden:true,disabled:false,innerHTML:'',textContent:'',value:'',
        children:[],classList:{add:k=>classes.add(k),remove:k=>classes.delete(k),contains:k=>classes.has(k),toggle(k,v){if(v)classes.add(k);else classes.delete(k)}},
        setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []}
      });
    }
    return els.get(id);
  }
  const ctx=vm.createContext({
    document:{hidden:false,getElementById:get,querySelectorAll:()=>[]},
    localStorage:storage,
    crypto:{getRandomValues(a){seed=(Math.imul(seed,1664525)+1013904223)>>>0;a[0]=seed;return a}},
    window:{},Date,Math,Intl,Uint32Array,
    console:{log:console.log,error:console.error,warn:()=>{}},
    setTimeout(cb){Promise.resolve().then(cb);return 1},clearTimeout(){}
  });
  vm.runInContext(script,ctx,{timeout:10000});
  return {ctx,get,run:expression=>vm.runInContext(expression,ctx)};
}
let {ctx,get,run}=makeContext();
const chunky=Array.from({length:25},(_,i)=>({kind:'reels',drop:i+1,debug:'q'.repeat(2000)}));
const rounds=Array.from({length:20},(_,i)=>({
  id:20-i,mode:'base',bet:7.5,cost:7.5,payout:7.5,
  book:{payout:100,events:chunky},time:'2026-11-12T16:00:00.000Z'
}));
ctx.seedRounds=rounds;
run('historyRecords=seedRounds;serial=20;balance=1000;betIndex=BETS.indexOf(7.5)');
assert.equal(run('saveSession()'),true,'History should shrink to fit a realistic storage quota');
const reduced=run('historyRecords.length');
assert.ok(reduced>=1&&reduced<20,'Oldest history records were pruned, newest remain');
assert.equal(run('historyRecords[0].id'),20,'Most recent history preserved in array order');
const snap=JSON.parse(store.get('jardim_stake_preview_v07'));
assert.equal(snap.historyRecords.length,reduced);
assert.ok(snap.historyRecords.every(x=>x.id>=21-reduced));
assert.equal(snap.balance,1000);
assert.equal(snap.serial,20,'Last spin number unchanged');
assert.equal(snap.betIndex,run('betIndex'));
assert.match(get('sessionStatus').textContent,/Histórico antigo reduzido/);
assert.equal(get('sessionStatus').hidden,false);
assert.ok(store.get('jardim_stake_preview_v07').length<=limit);
assert.equal(run('saveSession()'),true);
assert.equal(run('historyRecords.length'),reduced);
assert.equal(get('sessionStatus').hidden,true,'Warning clears after successful save without further pruning');

// A generated outcome is fixed and saved before animating. Closing Safari between
// purchase and payout must settle the same prize exactly once on reload.
const book=run('generate("base")');
ctx.generatedBook=book;
ctx.expectedPayout=+(book.payout/100*7.5).toFixed(2);
const before=run('balance');
const record=run('commitRound("base",7.5,7.5,expectedPayout,generatedBook)');
assert.equal(record.id,21);
assert.equal(run('balance'),before-7.5);
const pending=JSON.parse(store.get('jardim_stake_preview_v07'));
assert.equal(pending.pendingRound.id,21,'Pending spin persisted with debit and outcome');
assert.equal(pending.balance,before-7.5);
assert.equal(pending.pendingRound.payout,ctx.expectedPayout);
assert.ok(pending.pendingRound.book.events.length,'Complete pending book retained');
({ctx,get,run}=makeContext());
run('loadSession()');
assert.equal(run('recoveredRoundId'),21);
assert.equal(run('pendingRound'),null);
assert.equal(run('balance'),Math.round((before-7.5+record.payout)*100)/100);
assert.equal(run('historyRecords.filter(x=>x.id===21).length'),1);
const finalBalance=run('balance');
const afterRecovery=JSON.parse(store.get('jardim_stake_preview_v07'));
assert.equal(afterRecovery.pendingRound,null);
assert.equal(afterRecovery.balance,finalBalance);
run('loadSession()');
assert.equal(run('balance'),finalBalance,'Repeated loading never credits again');
assert.equal(run('historyRecords.filter(x=>x.id===21).length'),1);
({ctx,get,run}=makeContext());
run('loadSession()');
assert.equal(run('balance'),finalBalance,'Second browser tab reload retains exactly the settled balance');
assert.equal(run('historyRecords.filter(x=>x.id===21).length'),1);

// If even a tiny pending book cannot fit, rollback all in-memory debit and ID.
limit=1;
const beforeBlocked=run('balance'),lastSerial=run('serial'),historyLength=run('historyRecords.length');
ctx.generatedBook=run('generate("base")');
let didThrow=false;
try{run('commitRound("base",7.5,7.5,0,generatedBook)')}catch(e){didThrow=/Guardar sessão indisponível/.test(String(e))}
assert.equal(didThrow,true,'Quota failure must block the spin');
assert.equal(run('balance'),beforeBlocked,'No unpersisted debit');
assert.equal(run('serial'),lastSerial,'No ghost play number');
assert.equal(run('pendingRound'),null,'No unpersisted pending round');
assert.equal(run('historyRecords.length'),historyLength);
assert.equal(JSON.parse(store.get('jardim_stake_preview_v07')).balance,finalBalance,'Previously saved balance intact');
assert.match(get('sessionStatus').textContent,/Não é possível guardar/);
assert.equal(get('sessionStatus').hidden,false);
await run('play("base")');
assert.equal(run('balance'),beforeBlocked);
assert.equal(run('pendingRound'),null);
assert.equal(run('historyRecords.length'),historyLength,'Failed play does not add history');
assert.equal(run('busy'),false);
assert.match(get('notice').textContent,/Nenhum crédito foi descontado/);

// Blocked localStorage must fail quickly without repeated quota retries.
limit=160000;blocked=true;
assert.equal(run('saveSession()'),false);
assert.equal(run('pendingRound'),null);
blocked=false;
assert.equal(run('saveSession()'),true);
assert.equal(get('sessionStatus').hidden,true);
console.log('Stake Preview V1.9.7: quota adaptation, newest history, pending settlement after reload, atomic debit rollback and storage-blocker recovery passed.');
