import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.7/);
assert.match(html,/id="wildTally"/);
assert.match(html,/id="wildTallyText"/);
const code=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(code,'Logic section found');
const elements=new Map();
function el(id){
  if(!elements.has(id)){
    const classes=new Set();
    elements.set(id,{id,innerHTML:'',textContent:'',hidden:false,children:[],style:{},
      classList:{add:k=>classes.add(k),remove:k=>classes.delete(k),contains:k=>classes.has(k),toggle(k,v){v?classes.add(k):classes.delete(k)}},
      setAttribute(){},querySelectorAll(){return []}});
  }
  return elements.get(id);
}
const doc={hidden:false,getElementById:el,querySelectorAll:()=>[]};
let seed=1188391;
const ctx=vm.createContext({document:doc,crypto:{getRandomValues(a){seed=(Math.imul(seed,1664525)+1013904223)>>>0;a[0]=seed;return a}},Uint32Array,Math,Intl,Date,console,setTimeout,clearTimeout});
vm.runInContext(code,ctx,{timeout:10000});
const stages=vm.runInContext('stages',ctx);
const freeFeature=vm.runInContext('freeFeature',ctx);
const gen=vm.runInContext('generate',ctx);
let awardedRounds=0,awardedCells=0;
for(let spin=0;spin<2500;spin++){
  const free=(spin%4===0);
  const result=stages(free);
  let previousWild=0,raw=0,paid=0;
  for(const evt of result.events){
    assert.equal(evt.kind,'reels');
    assert.ok(Array.isArray(evt.wildAwards));
    const expected=evt.removed.filter(([c,r])=>evt.board[c][r]===4).map(x=>x.join(':')).sort();
    assert.deepEqual(evt.wildAwards.map(a=>a.c+':'+a.r).sort(),expected,'one value per removed WILD, never duplicate');
    for(const award of evt.wildAwards){
      assert.ok(Number.isInteger(award.value)&&award.value>=1&&award.value<=5);
      assert.equal(evt.board[award.c][award.r],4,'awarded symbol must be WILD');
    }
    const sum=evt.wildAwards.reduce((a,v)=>a+v.value,0);
    assert.equal(evt.wildAdded,sum);
    assert.equal(evt.multiBefore,Math.max(1,previousWild));
    previousWild+=sum;
    assert.equal(evt.multi,Math.max(1,previousWild));
    const lineRaw=evt.lines.reduce((a,line)=>a+line.amount,0);
    assert.equal(evt.win,lineRaw*evt.multi,'no change to prize math');
    raw+=lineRaw;paid+=evt.win;
    if(sum){awardedRounds++;awardedCells+=evt.wildAwards.length}
  }
  assert.equal(result.raw,raw);
  assert.equal(result.paid,paid);
  assert.equal(result.wild,previousWild);
}
assert.ok(awardedRounds>20,'enough payable WILD cells to exercise presentation');

const board=[[4,10,10],[4,10,10],[4,10,10],[10,10,10],[10,10,10]];
ctx.boardForTest=board;
ctx.awardsForTest=[{c:0,r:0,value:3},{c:1,r:0,value:1},{c:2,r:0,value:5}];
vm.runInContext('draw(boardForTest,[[0,0],[1,0],[2,0]],awardsForTest)',ctx);
const reelHtml=el('reels').innerHTML;
assert.equal((reelHtml.match(/class="wild-badge"/g)||[]).length,3,'3 glowing badges');
for(const value of ['+3','+1','+5'])assert.ok(reelHtml.includes(value));
ctx.eventForTest={kind:'reels',wildAwards:ctx.awardsForTest,multi:9};
vm.runInContext('showWildTally(eventForTest)',ctx);
assert.equal(el('wildTally').hidden,false);
assert.match(el('wildTallyText').textContent,/WILD \+3 · \+1 · \+5  →  MULTI ×9/);
vm.runInContext('hideWildTally()',ctx);
assert.equal(el('wildTally').hidden,true);
const f=freeFeature();
for(const e of f.events.filter(e=>e.kind==='progress')){
  assert.ok(Number.isInteger(e.wildTotal)&&e.wildTotal>=0);
  assert.equal(e.wildGain,e.wildTotal?Math.max(0,e.wildTotal-1):0,'free spin contribution equals old math');
}
for(let n=0;n<10;n++){
  const round=gen('base');
  const wins=round.events.filter(e=>e.kind==='reels'&&e.wildAwards.length);
  assert.ok(wins.every(evt=>evt.wildAwards.every(item=>item.value>=1&&item.value<=5)));
}
console.log('Stake Preview V1.7: '+(2500)+' stages, '+awardedRounds+' WILD-winning cascades / '+awardedCells+' valued symbols, badges, free tally and payouts verified.');
