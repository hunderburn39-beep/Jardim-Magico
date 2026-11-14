import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const dir=join(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(dir,'index.html'),'utf8');
const scene=readFileSync(join(dir,'assets','jardim-v3-palco-cascata.svg'),'utf8');
const logo=readFileSync(join(dir,'assets','jardim-v181-logo.svg'),'utf8');
assert.match(scene,/viewBox="0 0 941 1672"/);
assert.match(scene,/id="v3-reel-stage"/);
assert.match(scene,/v3StageMoss/);
assert.match(scene,/v3WaterCurtain/);
assert.doesNotMatch(scene,/<(?:text|image)\b/i,'background has no fake symbols or logos');
assert.match(logo,/viewBox="0 0 940 410"/);
assert.match(logo,/>Jardim<\/text>/);
assert.match(logo,/>MÁGICO<\/text>/);
assert.match(logo,/id="sapphire"/);
assert.match(html,/assets\/jardim-v3-palco-cascata\.svg/);
assert.match(html,/async function revealBaseCoins\(evt\)/);
assert.match(html,/await revealBaseCoins\(evt\)/);
assert.match(html,/@keyframes baseCoinUnveil/);
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script);
const code=script.split('$("introPlay").addEventListener')[0];
new vm.Script(code);
const els=new Map(),values=[],timings=[];
function el(id){
 if(!els.has(id)){
  const classes=new Set();
  els.set(id,{
   id,hidden:false,innerHTML:'',textContent:'',style:{},
   classList:{add(k){classes.add(k)},remove(k){classes.delete(k)},contains(k){return classes.has(k)},toggle(k,v){v?classes.add(k):classes.delete(k)}},
   setAttribute(){},querySelectorAll(){return []}
  });
 }
 return els.get(id);
}
const cells=Array.from({length:5},()=>Array.from({length:3},()=>({
 innerHTML:'',classes:new Set(),classList:{add(k){this._owner?.classes.add(k)}}
})));
for(const col of cells)for(const cell of col)cell.classList._owner=cell;
const columns=cells.map(col=>({querySelectorAll(sel){assert.equal(sel,'.cell');return col}}));
let seed=7653,timer=0;
const ctx=vm.createContext({
 document:{hidden:false,getElementById:el,querySelectorAll(sel){return sel==='.column'?columns:[]}},
 window:{matchMedia(){return {matches:false}}},
 crypto:{getRandomValues(a){seed=(Math.imul(seed,1664525)+1013904223)>>>0;a[0]=seed;return a}},
 Math,Intl,Uint32Array,Date,console,
 setTimeout(fn,ms){timings.push(ms);Promise.resolve().then(fn);return ++timer},
 clearTimeout(){}
});
vm.runInContext(code,ctx,{timeout:10000});
const board=[[4,10,10],[4,10,10],[4,10,10],[10,10,10],[10,10,10]];
const awards=[{c:0,r:0,value:3},{c:1,r:0,value:1},{c:2,r:0,value:5}];
ctx.evt={kind:'reels',board,removed:[[0,0],[1,0],[2,0]],wildAwards:awards,free:false,multi:9};
vm.runInContext('betIndex=BETS.indexOf(.05)',ctx);
const balance=vm.runInContext('balance',ctx);
const payout=vm.runInContext('evt.wildAwards.reduce((a,x)=>a+x.value,0)',ctx);
const count=await vm.runInContext('revealBaseCoins(evt)',ctx);
assert.equal(count,3);
assert.deepEqual(timings,[390,660,390,660,390,660,680],'base reveal is one at a time with a final hold');
assert.equal((el('reels').innerHTML.match(/base-valued-coin/g)||[]).length,3);
for(const value of ['0,15','0,05','0,25'])assert.ok(el('reels').innerHTML.includes(value));
assert.match(cells[0][0].innerHTML,/0,15/);
assert.match(cells[1][0].innerHTML,/0,05/);
assert.match(cells[2][0].innerHTML,/0,25/);
assert.equal(el('wildTallyText').textContent,'MOEDAS DOURADAS · 0,15 € · 0,05 € · 0,25 €');
assert.equal(vm.runInContext('balance',ctx),balance);
assert.equal(vm.runInContext('evt.wildAwards.reduce((a,x)=>a+x.value,0)',ctx),payout);
timings.length=0;
vm.runInContext('turbo=true',ctx);
assert.equal(await vm.runInContext('revealBaseCoins(evt)',ctx),3);
assert.deepEqual(timings,[120,110,120,110,120,110,85],'turbo stays quick');
timings.length=0;
vm.runInContext('skipAnimations=true',ctx);
assert.equal(await vm.runInContext('revealBaseCoins(evt)',ctx),0);
assert.deepEqual(timings,[],'PASSAR does not wait');
vm.runInContext('skipAnimations=false;turbo=false;evt.free=true',ctx);
assert.equal(await vm.runInContext('revealBaseCoins(evt)',ctx),0,'free spins unaffected');
assert.equal(vm.runInContext('balance',ctx),balance);
console.log('Jardim Mágico: new natural 5x3 stage, revised logo, slower base coin awards, untouched outcomes and quick turbo/skip passed.');
