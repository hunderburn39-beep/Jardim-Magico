import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.5/);
assert.match(html,/V1\.8\.5 • ROLAR VERTICAL/);
assert.match(html,/player\.loop=name==="spin"/,'Spinning motor continues while strips move');
assert.match(html,/\.column\{position:relative;overflow:hidden\}/,'Each reel has a clipping window');
assert.match(html,/\.reel-scroll-viewport\{[\s\S]*?overflow:hidden/);
assert.match(html,/\.reel-scroll-strip\{[\s\S]*?height:800%/);
assert.match(html,/animation:reelRollDown/,'Physical movement instead of changing symbols in place');
assert.match(html,/@keyframes reelRollDown\{\s*from\{transform:translate3d\(0,-50%,0\)\}\s*to\{transform:translate3d\(0,0,0\)\}/,
  'A repeated symbol strip physically moves from top to bottom and loops');
assert.match(html,/@keyframes reelBrakeDown\{\s*from\{transform:translate3d\(0,-66\.666667%,0\)\}\s*to\{transform:translate3d\(0,0,0\)\}/,
  'Braking strip visibly travels downward until predetermined symbols land');
assert.match(html,/\.reel-scroll-viewport:after\{/,'Curved edge lighting without image blur');

const raw=html.match(/const REEL_SPIN_PROFILE=Object\.freeze\([\s\S]*?\nasync function animateCascade/);
assert.ok(raw,'Reel strip engine and main animation must be present');
const source=raw[0].slice(0,-'async function animateCascade'.length);
new vm.Script(source);
const TYPES=Array.from({length:12},(_,id)=>({label:'IMAGE_'+id,image:'/assets/'+id+'.png'}));
const final=Array.from({length:5},(_,c)=>[c,(c+1)%12,(c+2)%12]);

async function scenario(isTurbo,{skipAt=0,reducedMotion=false}={}){
  const calls=[],pauses=[],played=[],drawn=[],columns=[];
  let removeCount=0,stopCount=0,clipCount=0;
  const fakeClassList=()=>({add(...names){for(const name of names)calls.push('class:'+name)}});
  for(let col=0;col<5;col++){
    const column={
      classList:fakeClassList(),viewport:null,
      appendChild(viewport){
        this.viewport=viewport;
        clipCount++;
        calls.push('append:'+col+':'+(viewport.innerHTML.match(/class="reel-scroll-symbol"/g)||[]).length);
      },
      querySelectorAll(selector){
        assert.equal(selector,'.cell');
        return Array.from({length:3},()=>({classList:fakeClassList()}));
      }
    };
    columns.push(column);
  }
  let created=0,removed=0;
  const ctx=vm.createContext({
    document:{
      querySelectorAll(selector){assert.equal(selector,'.column');return columns},
      createElement(tag){
        assert.equal(tag,'div');
        created++;
        return {
          className:'',style:{props:{},setProperty(k,v){this.props[k]=v}},
          setAttribute(k,v){calls.push('attr:'+k+':'+v)},
          innerHTML:'',classList:{
            add(name){
              calls.push('viewport:'+name);
              if(name==='braking'){
                const content=columns.find(c=>c.viewport===viewport)?.viewport.innerHTML||'';
                calls.push('brake-cells:'+(content.match(/class="reel-scroll-symbol"/g)||[]).length);
              }
            }
          },
          remove(){removed++;calls.push('removed')}
        };
        function viewport(){}
      }
    },
    window:{matchMedia(){return {matches:reducedMotion}}},
    TYPES,Math,Object,console,Array,Boolean,
    turbo:isTurbo,skipAnimations:false,
    draw(b){drawn.push(JSON.stringify(b))},
    playRecorded(name){played.push(name)},
    stopSpinEffect(){stopCount++},
    pause(ms){
      pauses.push(ms);
      if(skipAt&&pauses.length===skipAt)ctx.skipAnimations=true;
      return Promise.resolve();
    }
  });
  // Replace createElement stub with a proper closure around the created node.
  ctx.document.createElement=(tag)=>{
    assert.equal(tag,'div');created++;
    const el={
      className:'',style:{props:{},setProperty(k,v){this.props[k]=v}},
      setAttribute(k,v){calls.push('attr:'+k+':'+v)},
      innerHTML:'',removed:false,
      classList:{add(name){
        calls.push('viewport:'+name);
        if(name==='braking'){
          calls.push('brake-cells:'+(el.innerHTML.match(/class="reel-scroll-symbol"/g)||[]).length);
          calls.push('brake-final:' + el.innerHTML.slice(0,270));
        }
      }},
      remove(){removed++;this.removed=true;calls.push('removed')}
    };
    return el;
  };
  vm.runInContext(source,ctx);
  ctx.final=final;
  await vm.runInContext('animateReelStop(final)',ctx);
  assert.equal(drawn.at(-1),JSON.stringify(final),'Final board is predetermined and shown exactly');
  if(!reducedMotion){
    assert.equal(created,5,'Five physical reel viewports must exist');
    assert.equal(clipCount,5,'All five reel windows receive rolling strips');
    assert.equal(removed,5,'All rolling overlays cleaned up, including PASSAR');
    assert.equal(stopCount,1,'Spinning audio ends exactly once');
    assert.deepEqual(calls.filter(c=>c.startsWith('append:')),Array.from({length:5},(_,i)=>'append:'+i+':24'),
      'Each drum uses 12 unique images repeated twice for seamless downward scrolling');
    for(const c of columns){
      assert.ok(c.viewport.removed,'No strip remains over the symbols once stopped');
      assert.ok(c.viewport.style.props['--reel-roll-duration'],'Scrolling is paced by the actual mode');
    }
  }else{
    assert.equal(created,0,'Reduced motion uses the predetermined static grid');
    assert.equal(stopCount,0,'No spinning sound when there is no animation');
  }
  return {calls,pauses,played,drawn,created,removed,totalMs:pauses.reduce((a,b)=>a+b,0)};
}

const normal=await scenario(false);
assert.deepEqual(normal.pauses,[980,320,355,390,425,460]);
assert.equal(normal.totalMs,2930,'Normal vertical scroll lasts ~2.93 seconds');
assert.deepEqual(normal.played,['spin','stop','stop','stop','stop','stop']);
assert.equal(normal.calls.filter(c=>c==='viewport:braking').length,5,'Five independent mechanical stops');
assert.equal(normal.calls.filter(c=>c==='brake-cells:9').length,5,'Each stop crosses six intermediate symbols before exact three result symbols');
const turbo=await scenario(true);
assert.deepEqual(turbo.pauses,[35,20,20,20,20,20]);
assert.equal(turbo.totalMs,135,'TURBO remains fast');
const skipped=await scenario(false,{skipAt:1});
assert.equal(skipped.pauses.length,1,'PASSAR interrupts the ongoing vertical movement');
assert.equal(skipped.calls.filter(c=>c==='viewport:braking').length,0,'No further reel animations after PASSAR');
assert.deepEqual(skipped.played,['spin']);
const accessible=await scenario(false,{reducedMotion:true});
assert.equal(accessible.totalMs,0,'Reduced motion does not force a spin animation');
assert.deepEqual(accessible.played,[]);
console.log('Stake Preview V1.8.5: looping top-to-bottom reels, five descending stops, 2930 ms normal, 135 ms turbo, PASSAR and reduced motion passed.');
