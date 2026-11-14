import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
const css=html.split('</style>')[0];
const premium=css.split('/* PREMIUM HYBRID V2')[1];
assert.ok(premium,'the floating-symbol skin is present');
assert.match(premium,/#reelFrame\.frame\s*\{[^}]*border:0!important/);
assert.match(premium,/#reelFrame #reels\s*\{[^}]*grid-template-columns:repeat\(5,minmax\(0,1fr\)\)/);
assert.match(premium,/#reelFrame #reels \.column\s*\{[^}]*grid-template-rows:repeat\(3,minmax\(0,1fr\)\)/);
assert.match(premium,/#reelFrame #reels \.cell\s*\{[^}]*background:transparent!important/);
assert.match(premium,/#reelFrame \.payline-visual\{display:none!important\}/);
assert.match(html,/await animateSymbolRain\(evt\.board\)/,'gameplay calls gravity fall instead of legacy reel spinning');
const raw=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
const code=raw?.split('$("introPlay").addEventListener')[0];
assert.ok(code,'inline engine exists');
new vm.Script(code);
const elements=new Map(),animations=[],appended=[];
function elem(id){
 if(!elements.has(id)){
  const classes=new Set();
  elements.set(id,{id,textContent:'',innerHTML:'',hidden:false,style:{},
   classList:{add(k){classes.add(k)},remove(k){classes.delete(k)},contains(k){return classes.has(k)},toggle(k,v){v?classes.add(k):classes.delete(k)}},
   setAttribute(){},querySelectorAll(){return []},appendChild(child){this.lastChild=child;appended.push(child);child.parent=this}});
 }
 return elements.get(id);
}
elem('reelFrame').getBoundingClientRect=()=>({left:0,top:200,width:400,height:325});
elem('reels').getBoundingClientRect=()=>({left:10,top:212,width:380,height:300});
const columns=Array.from({length:5},(_,c)=>{
 const cells=Array.from({length:3},(_,r)=>({
   getBoundingClientRect(){return {left:10+c*76,top:212+r*100,width:76,height:100}},
   classList:{add(){},remove(){}}
 }));
 return {querySelectorAll(q){return q==='.cell'?cells:[]}};
});
let constructed=0,removed=0,timer=0;
function createElement(){
 constructed++;
 return {className:'',innerHTML:'',style:{},children:[],setAttribute(){},
   appendChild(x){this.children.push(x)},remove(){removed++;this.removed=true},
   animate(frames,timing){animations.push({frames,timing});return {cancel(){}}}};
}
const context=vm.createContext({
 document:{hidden:false,getElementById:elem,createElement,querySelectorAll(q){return q==='.column'?columns:[]}},
 window:{matchMedia(){return {matches:false}}},
 Math,Intl,Date,console,Uint32Array,
 setTimeout(fn){Promise.resolve().then(fn);return ++timer},
 clearTimeout(){}
});
vm.runInContext(code,context,{timeout:10000});
const board=[[9,11,0],[9,1,11],[9,3,4],[11,11,0],[11,2,0]];
context.boardForRain=board;
const plan=vm.runInContext('premiumRainPlan(boardForRain)',context);
assert.equal(plan.length,15,'exactly 15 falling images');
assert.equal(new Set(plan.map(x=>x.c+':'+x.r)).size,15,'every invisible grid cell is occupied exactly once');
assert.equal(plan.filter(x=>x.symbol===9).length,3,'Scatter positions stay untouched');
assert.throws(()=>vm.runInContext('premiumRainPlan([[1,2,3]])',context),/5×3/,'invalid grids are rejected');
await vm.runInContext('animateSymbolRain(boardForRain)',context);
assert.equal(animations.length,15,'each symbol gets its own gravity animation');
assert.ok(animations.every(a=>a.frames[0].transform.includes('translate3d(0,-')),'all images enter from above');
assert.ok(animations.every(a=>a.frames.at(-1).transform.includes('translate3d(0,0px,0)')),'all symbols land at exact logical coordinates');
assert.ok(animations.every(a=>a.timing.easing.includes('cubic-bezier')),'natural easing');
assert.ok(animations[0].timing.delay!==animations[3].timing.delay,'staggered columns');
assert.equal(elem('reelFrame').classList.contains('rain-loading'),false,'no invisibility leaked after the spin');
assert.equal(removed,1,'flying image layer removed');
assert.equal((elem('reels').innerHTML.match(/class="cell /g)||[]).length,15,'settled grid still shows all original symbols');
const before=vm.runInContext('balance',context);
vm.runInContext('skipAnimations=true',context);
await vm.runInContext('animateSymbolRain(boardForRain)',context);
assert.equal(animations.length,15,'PASSAR does not launch decorative animations');
assert.equal(vm.runInContext('balance',context),before,'visual redesign cannot change credits');
console.log('Premium Hybrid: no visible reels or boxes, 15 independently falling symbols, untouched 5x3 board and payout verified.');
