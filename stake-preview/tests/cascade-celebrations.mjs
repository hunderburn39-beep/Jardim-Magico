import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.5/);
for(const id of ['cascadeMeter','cascadeWinLabel','cascadeWinValue','cascadeTotalLabel','cascadeTotalValue','winSparks','reelFrame']) {
 assert.ok(html.includes('id="'+id+'"'),'Missing visualization '+id);
}
assert.match(html,/@media\(prefers-reduced-motion:reduce\)/,'Reduced motion accessibility respected');
assert.match(html,/<output id="linePrize"/);
const raw=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(raw,'Inline JS found');
const code=raw.split('$("introPlay").addEventListener')[0];
const elements=new Map();
function el(id){
  if(!elements.has(id)){
    const classes=new Set();
    elements.set(id,{
      id,innerHTML:'',textContent:'',hidden:false,disabled:false,style:{},dataset:{},children:[],
      classList:{add(k){classes.add(k)},remove(k){classes.delete(k)},contains(k){return classes.has(k)},toggle(k,v){v?classes.add(k):classes.delete(k)}},
      setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []}
    });
  }
  return elements.get(id);
}
let seed=1234567;
const ctx=vm.createContext({
  document:{hidden:false,getElementById:el,querySelectorAll:()=>[]},window:{},
  crypto:{getRandomValues(a){seed=(Math.imul(seed,1664525)+1013904223)>>>0;a[0]=seed;return a}},
  Math,Intl,Date,console,Uint32Array,
  setTimeout(fn){Promise.resolve().then(fn);return 1},clearTimeout(){}
});
vm.runInContext(code,ctx,{timeout:12000});
vm.runInContext('betIndex=BETS.indexOf(7.5)',ctx);

ctx.baseEvent={
  kind:'reels',drop:1,free:false,
  board:[[0,1,2],[0,2,1],[0,2,1],[3,1,2],[7,1,2]],
  removed:[[0,0],[1,0],[2,0]],wildAwards:[],
  lines:[{line:1,amount:250,cells:[[0,0],[1,0],[2,0]]}],multi:3,win:750
};
const getAmount=vm.runInContext('cascadeAmount',ctx);
const showMeter=vm.runInContext('showCascadeMeter',ctx);
const sparks=vm.runInContext('showWinSparks',ctx);
assert.equal(getAmount(ctx.baseEvent),750,'Base event takes already-multiplied engine award');
assert.equal(showMeter(ctx.baseEvent,1000),750);
assert.equal(el('cascadeMeter').hidden,false);
assert.equal(el('cascadeWinValue').textContent,'56,25','7.50 times 7.5x win');
assert.equal(el('cascadeTotalValue').textContent,'75,00');
assert.equal(el('cascadeWinLabel').textContent,'PRÉMIO DA QUEDA');
assert.equal(el('cascadeTotalLabel').textContent,'TOTAL EM CASCATAS');
assert.equal(sparks(ctx.baseEvent),true);
assert.equal(el('reelFrame').classList.contains('win-flash'),true);
assert.equal((el('winSparks').innerHTML.match(/win-spark"/g)||[]).length,8);
vm.runInContext('clearWinSparks()',ctx);
assert.equal(el('winSparks').innerHTML,'');
assert.equal(el('reelFrame').classList.contains('win-flash'),false);

ctx.freeEvent={
  kind:'reels',drop:2,free:true,
  board:ctx.baseEvent.board,removed:ctx.baseEvent.removed,wildAwards:[],
  lines:[{line:2,amount:200,cells:[[0,1],[1,1],[2,1]]}],multi:20,win:4000
};
assert.equal(getAmount(ctx.freeEvent),200,'Free Spins must use raw line award');
assert.equal(showMeter(ctx.freeEvent,450),200);
assert.equal(el('cascadeWinLabel').textContent,'BRUTO DA QUEDA');
assert.equal(el('cascadeTotalLabel').textContent,'BRUTO DO GIRO');
assert.equal(el('cascadeWinValue').textContent,'15,00');
assert.equal(el('cascadeTotalValue').textContent,'33,75');
ctx.emptyEvent={kind:'reels',free:false,win:0,lines:[]};
assert.equal(showMeter(ctx.emptyEvent,0),0);
assert.equal(el('cascadeMeter').hidden,true);
assert.equal(sparks(ctx.emptyEvent),false);

vm.runInContext('skipAnimations=true',ctx);
assert.equal(sparks(ctx.baseEvent),false,'PASSAR skips decorative particles');
assert.equal(el('reelFrame').classList.contains('win-flash'),false);
vm.runInContext('skipAnimations=false',ctx);

// Playback has no accounting side effects; cascade total resets at each fresh spin.
ctx.book={events:[
 {...ctx.baseEvent,drop:1},
 {...ctx.baseEvent,drop:2,win:500},
 {...ctx.baseEvent,drop:1,win:125}
]};
await vm.runInContext('playback(book)',ctx);
assert.equal(el('cascadeTotalValue').textContent,'9,38','Reset running total to new spin, with 1.25x at 7.50');
assert.equal(el('cascadeWinValue').textContent,'9,38');
assert.equal(el('winSparks').innerHTML,'');
assert.equal(el('reelFrame').classList.contains('win-flash'),false);

let winningDrops=0;
let accounted=0;
const stages=vm.runInContext('stages',ctx);
for(let i=0;i<2500;i++){
  const f=i%3===0,round=stages(f);
  let raw=0,paid=0;
  for(const e of round.events){
    assert.equal(cascadeAmountFrom(e),f?e.lines.reduce((a,b)=>a+b.amount,0):e.win);
    raw+=e.lines.reduce((a,b)=>a+b.amount,0);
    paid+=e.win;
    if(e.lines.length)winningDrops++;
  }
  assert.equal(raw,round.raw,'Line raw stays identical');
  assert.equal(paid,round.paid,'Paid amount stays identical');
  accounted+=round.paid;
}
assert.ok(winningDrops>100);
assert.ok(accounted>0);
console.log('Stake Preview V1.8.5: 2500 cascade outcomes, base/free award clarity, running totals and presentation-only sparks verified.');

function cascadeAmountFrom(evt){return getAmount(evt)}
