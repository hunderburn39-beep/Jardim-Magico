import assert from "node:assert/strict";
import {readFileSync} from "node:fs";
import {dirname,join} from "node:path";
import {fileURLToPath} from "node:url";
import vm from "node:vm";

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),"..","index.html"),"utf8");
assert.match(html,/Stake Preview V1\.8\.8/);
assert.match(html,/V1\.8\.8 • SÍMBOLOS SEM FALHAS/);
assert.match(html,/\.reel-scroll-symbol\{[\s\S]*?flex:0 0 var\(--reel-cell-height,80px\)/);
assert.match(html,/\.reel-scroll-viewport\{[\s\S]*?overflow:hidden/);
assert.doesNotMatch(html,/reel-scroll-strip\.braking/,"Changing strip dimensions on brake is forbidden");
assert.match(html,/player\.loop=name==="spin"/);
const m=html.match(/const REEL_SPIN_PROFILE=Object\.freeze\([\s\S]*?\nasync function animateCascade/);
assert.ok(m,"Engine found");
const code=m[0].slice(0,-"async function animateCascade".length);
const helpers=html.match(/const SYMBOL_FALLBACKS=\[[\s\S]*?\n(?=const WEIGHTS=)/)?.[0];
assert.ok(helpers,"Shared symbol-artwork renderer must be available to the reel tests");
const bundle=helpers+"\n"+code;
new vm.Script(bundle);

const TYPES=Array.from({length:12},(_,id)=>({label:"SYMBOL"+id,image:"/symbols/"+id+".svg"}));
const final=Array.from({length:5},(_,col)=>[col,(col+1)%12,(col+2)%12]);
const json=JSON.stringify(final);
const testScenario=async(turbo,{skipAt=0,reduced=false}={})=>{
  const events=[],pauses=[],drawn=[],sfx=[],columns=[];
  let stopFx=0,created=0;
  for(let i=0;i<5;i++)columns.push({
    mounted:[],getBoundingClientRect(){return {height:300}},
    appendChild(v){this.mounted.push(v)},
    querySelectorAll(s){assert.equal(s,".cell");return Array.from({length:3},()=>({classList:{add(k){events.push("land:"+i+":"+k)}}}))}
  });
  function createElement(tag){
    assert.equal(tag,"div");created++;
    const el={
      className:"",innerHTML:"",children:[],removed:false,anim:[],
      style:{height:"",transform:"",props:{},setProperty(k,v){this.props[k]=v}},
      classList:{add(k){events.push("class:"+k)}},
      setAttribute(k,v){events.push("attr:"+k+":"+v)},
      appendChild(v){this.children.push(v)},
      getBoundingClientRect(){return {height:0}},
      remove(){this.removed=true},
      animate(frames,opts){
        const roll=opts.iterations===Infinity;
        const n=(el.innerHTML.match(/reel-scroll-symbol/g)||[]).length;
        if(roll){
          events.push("roll");
          assert.equal(n,18,"The physical strip always has 18 symbols");
          assert.equal(opts.easing,"linear");
          assert.ok(frames[0].transform.includes("-1200px"));
          assert.ok(frames[1].transform.includes("-600px"));
          assert.equal(el.style.height,"1800px");
        } else {
          events.push("brake");
          assert.equal(n,18,"The same 18 images remain during the entire brake");
          assert.equal(el.style.height,"1800px");
          assert.equal(opts.fill,"forwards");
          assert.ok(frames[0].transform.includes("px,0)"));
          assert.equal(frames[1].transform,"translate3d(0,0px,0)");
        }
        const a={currentTime:roll?opts.duration*.45:0,cancelled:false,cancel(){this.cancelled=true}};
        el.anim.push(a);return a;
      }
    };return el;
  }
  const ctx=vm.createContext({
    document:{createElement,querySelectorAll(q){assert.equal(q,".column");return columns}},
    window:{matchMedia(){return {matches:reduced}}},
    TYPES,console,Math,Object,Number,Boolean,Array,
    turbo,skipAnimations:false,
    draw(b){drawn.push(JSON.stringify(b))},
    playRecorded(name){sfx.push(name)},
    stopSpinEffect(){stopFx++},
    pause(ms){pauses.push(ms);if(skipAt&&pauses.length===skipAt)ctx.skipAnimations=true;return Promise.resolve()}
  });
  vm.runInContext(bundle,ctx);
  ctx.result=final;
  await vm.runInContext("animateReelStop(result)",ctx);
  assert.equal(drawn.at(-1),json,"The predetermined result is preserved");
  if(reduced){
    assert.equal(created,1,"Reduced motion does not produce scrolling DOM");
    assert.equal(stopFx,0);
    assert.equal(sfx.length,0);
  }else{
    assert.equal(created,11,"Only 5 viewports, 5 strips and the feature probe");
    assert.equal(columns.every(col=>col.mounted.length===1),true);
    assert.equal(columns.every(col=>col.mounted[0].removed),true,"The overlays must never remain on top");
    assert.equal(columns.every(col=>col.mounted[0].children[0].anim[0].cancelled),true);
    assert.equal(stopFx,1);
  }
  return {events,pauses,sfx,columns,created};
};
const normal=await testScenario(false);
assert.equal(normal.events.filter(x=>x==="roll").length,5);
assert.equal(normal.events.filter(x=>x==="brake").length,5);
assert.equal(normal.pauses[0],1050);
assert.equal(normal.pauses.filter(x=>x===245).length,4);
assert.equal(normal.sfx.filter(x=>x==="stop").length,5);
assert.equal(normal.columns.every(col=>col.mounted[0].children[0].innerHTML.includes("reel-scroll-symbol")),true);
const turbo=await testScenario(true);
assert.equal(turbo.pauses[0],40);
assert.equal(turbo.pauses.filter(x=>x===22).length,4);
const skip=await testScenario(false,{skipAt:1});
assert.equal(skip.events.filter(x=>x==="brake").length,0);
assert.equal(skip.sfx.filter(x=>x==="stop").length,0);
const accessible=await testScenario(false,{reduced:true});
assert.equal(accessible.pauses.length,0);
const ctx=vm.createContext({TYPES,Math,Object,Array,Number});
vm.runInContext(bundle,ctx);
for(const phase of [0,.05,.25,.5,.75,.95,.999]){
 ctx.phase=phase;
 const arr=Array.from({length:18},(_,i)=>i);
 const shifted=-12+6*phase;
 const startIndex=Math.floor(-shifted);
 const required=Math.ceil(-shifted+3);
 assert.ok(startIndex>=6 && required<=18,"Reel strip fills all three rows at any phase, even just before stop");
 ctx.id=0;ctx.boardColumn=final[0];
 const strip=Array.from(vm.runInContext("buildContinuousReelSymbols(boardColumn,id)",ctx));
 assert.equal(strip.length,18);
 assert.deepEqual(strip.slice(0,3),final[0],"Results placed at end of scroll");
 assert.deepEqual(strip.slice(6,12),strip.slice(12,18),"Repeated cycle is truly seamless");
 assert.equal(strip.slice(startIndex,startIndex+3).length,3);
}
console.log("Stake Preview V1.8.8: all three rows filled through the complete roll/brake, no changing the DOM strip, Safari pixel heights, 5 reels, TURBO and PASSAR validated.");
