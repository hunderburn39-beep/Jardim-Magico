import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.6/);
assert.match(html,/V1\.8\.6 • ROLAGEM REAL/);
assert.match(html,/player\.loop=name==="spin"/,'Spinning sound stays alive until the reel is fully stopped');
assert.match(html,/\.reel-scroll-viewport\{[\s\S]*?overflow:hidden/);
assert.match(html,/\.reel-scroll-strip\{[\s\S]*?height:800%/);
assert.match(html,/\.reel-scroll-strip\.braking\{height:433\.333333%\}/);
assert.match(html,/\.reel-scroll-viewport:after\{/,'The reel surface has curved shadowing');
assert.doesNotMatch(html,/@keyframes reelBrakeDown/,'Abrupt old reset animation was removed');
const match=html.match(/const REEL_SPIN_PROFILE=Object\.freeze\([\s\S]*?\nasync function animateCascade/);
assert.ok(match,'Rolling reel engine exists');
const js=match[0].slice(0,-'async function animateCascade'.length);
new vm.Script(js,{filename:'reel-engine.js'});
const TYPES=Array.from({length:12},(_,n)=>({label:'SYMBOL'+n,image:'/assets/symbol-'+n+'.svg'}));
const board=Array.from({length:5},(_,c)=>[c,(c+1)%12,(c+2)%12]);

function testScenario(turbo,{skipAt=0,reducedMotion=false}={}){
 const calls=[],pauses=[],played=[],drawn=[];
 let factoryCount=0,stopCount=0;
 const columns=Array.from({length:5},(_,i)=>({
   appended:[],cells:Array.from({length:3},()=>({classList:{add(v){calls.push('landing:'+i+':'+v)}}})),
   appendChild(x){this.appended.push(x);calls.push('append:'+i)},
   querySelectorAll(selector){assert.equal(selector,'.cell');return this.cells}
 }));
 function element(){
   factoryCount++;
   const obj={
     className:'',attrs:{},innerHTML:'',children:[],removed:false,
     style:{transform:'',setProperty(k,v){this[k]=v}},
     classList:{add(k){calls.push('class:'+k)}},
     setAttribute(k,v){this.attrs[k]=v},
     appendChild(v){this.children.push(v)},
     remove(){this.removed=true;calls.push('removed')},
     getBoundingClientRect(){return {height:300}},
     animate(frames,opts){
       const initial=opts.iterations===Infinity;
       calls.push(initial?'roll':'brake');
       if(initial){
         assert.equal(opts.easing,'linear');
         assert.equal(opts.iterations,Infinity);
         assert.equal((this.innerHTML.match(/reel-scroll-symbol/g)||[]).length,24);
       }else{
         assert.equal((this.innerHTML.match(/reel-scroll-symbol/g)||[]).length,13);
         assert.match(this.className+' '+calls.join(' '),/braking/);
         assert.equal(opts.fill,'forwards');
         assert.match(opts.easing,/cubic-bezier/);
         const start=Number(frames[0].transform.match(/translate3d\(0,(-?[\d.]+)px/)[1]);
         assert.ok(start<=-900&&start>=-1000,'Physical brake begins at old scrolling phase, not at an arbitrary start');
         assert.equal(frames[1].transform,'translate3d(0,0px,0)');
       }
       const animation={currentTime:initial?opts.duration*.45:0,cancelled:false,cancel(){this.cancelled=true}};
       (this.animations??=[]).push(animation);
       return animation;
     }
   };
   return obj;
 }
 const ctx=vm.createContext({
   document:{querySelectorAll(s){assert.equal(s,'.column');return columns},createElement:element},
   window:{matchMedia(){return {matches:reducedMotion}}},
   TYPES,Math,Object,Array,Number,Boolean,console,
   turbo,skipAnimations:false,
   draw(grid){drawn.push(JSON.stringify(grid))},
   playRecorded(name){played.push(name)},
   stopSpinEffect(){stopCount++},
   pause(ms){pauses.push(ms);if(skipAt&&pauses.length===skipAt)ctx.skipAnimations=true;return Promise.resolve()}
 });
 vm.runInContext(js,ctx);
 ctx.finalBoard=board;
 return vm.runInContext('animateReelStop(finalBoard)',ctx).then(()=>{
   assert.equal(drawn.at(-1),JSON.stringify(board),'Final result unchanged after animation');
   if(!reducedMotion){
     assert.equal(factoryCount,11,'Five reel windows, five strips plus one feature probe');
     assert.equal(stopCount,1,'Motor sound stops once');
     assert.equal(columns.filter(c=>c.appended.length===1).length,5);
     for(const col of columns){
       const window=col.appended[0];
       assert.ok(window.removed,'Scrolling window removed at the end');
       const strip=window.children[0];
       assert.equal(strip.animations[0].cancelled,true,'Continuous movement cleaned up');
       if(strip.animations[1])assert.equal(strip.animations[1].cancelled,true,'Brake animation cleaned up');
     }
   }else{
     assert.equal(factoryCount,1,'Reduced motion never creates moving reel strips');
     assert.equal(stopCount,0);
     assert.deepEqual(played,[]);
   }
   return {calls,pauses,played,drawn,columns};
 });
}
const normal=await testScenario(false);
assert.equal(normal.played[0],'spin');
assert.equal(normal.played.filter(v=>v==='stop').length,5,'Five naturally staggered stop sounds');
assert.equal(normal.calls.filter(x=>x==='roll').length,5);
assert.equal(normal.calls.filter(x=>x==='brake').length,5);
assert.equal(normal.pauses.filter(ms=>ms===240).length,4);
assert.equal(normal.pauses.filter(ms=>ms===760).length,5);
assert.equal(normal.pauses[0],1050);
const turbo=await testScenario(true);
assert.equal(turbo.pauses[0],35);
assert.equal(turbo.pauses.filter(ms=>ms===20).length,4);
assert.equal(turbo.pauses.filter(ms=>ms===70).length,5);
const skipped=await testScenario(false,{skipAt:1});
assert.equal(skipped.calls.filter(x=>x==='roll').length,5);
assert.equal(skipped.calls.filter(x=>x==='brake').length,0);
assert.equal(skipped.played.filter(x=>x==='stop').length,0,'No extra stop noises after PASSAR');
const reduced=await testScenario(false,{reducedMotion:true});
assert.deepEqual(reduced.pauses,[]);

const ctx=vm.createContext({TYPES,Math,Object,Array,Number,console});
vm.runInContext(js,ctx);
for(const phase of [0,.08,.25,.45,.75,.99]){
  ctx.phase=phase;ctx.demo=board[0];ctx.seq=Array.from({length:12},(_,x)=>x);
  const r=vm.runInContext('brakeSymbolsForPhase(seq,demo,phase)',ctx);
  assert.equal(r.ids.length,13,'Brake has 13 physical symbols');
  assert.deepEqual(Array.from(r.ids.slice(0,3)),board[0],'Bottom end of the scroll gives the exact committed result');
  assert.ok(r.remainder>=-1e-9&&r.remainder<1+1e-9);
  const shifted=-12+12*phase;
  const k=Math.max(1,Math.min(12,Math.ceil(-shifted)));
  const existing=Array.from({length:12},(_,x)=>x).concat(Array.from({length:12},(_,x)=>x));
  assert.deepEqual(Array.from(r.ids.slice(-4)),existing.slice(k-1,k+3),
    'Four visible symbols remain identical when the brake begins: no jump');
}
console.log('Stake Preview V1.8.6: five seamless reel strips, phase-preserving 13-symbol brakes, overlapping stops, TURBO, PASSAR and reduced motion passed.');
