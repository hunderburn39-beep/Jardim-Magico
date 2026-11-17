import {chromium} from 'playwright';
import assert from 'node:assert/strict';

const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844}});
const errors=[];page.on('pageerror',e=>errors.push(e.message));
try{
 await page.goto('http://127.0.0.1:8765/Strikers-1945/stage1-captured.html',{waitUntil:'load'});
 // RAM trajectory capture is genuine; ROM pixel bytes are intentionally stubbed
 // to avoid uploading private copyrighted game data to continuous integration.
 await page.evaluate(()=>{window.StrikersROM={isLoaded:()=>true,rawPixel:()=>15};});
 await page.addScriptTag({url:'http://127.0.0.1:8765/Strikers-1945/stage1-flight-replay.js'});
 const ready=await page.evaluate(()=>window.StrikersFlightReplay.ensure());
 assert(ready,'Original captured sprite RAM stream must load');
 const shadowChecks=await page.evaluate(()=>{
  const shadowPlan=window.StrikersFlightReplay.test.shadowPlan;
  const assert=(p,m)=>{if(!p)throw Error(m)};
const owner={uid:7,romFlight:true,active:true,role:'bomber',alpha:1,x:200,y:300,w:192,h:192};
const sh={uid:8,kind:'romShadow',active:true,ownerId:7,ownerRef:owner,x:200,y:300,w:192,h:192,alpha:1};
let p=shadowPlan([owner]);assert(p.length===1&&p[0].kind==='silhouette'&&p[0].x===152&&p[0].y===252&&p[0].w===102,'Missing shadow fallback');
p=shadowPlan([owner,sh]);assert(p.length===1&&p[0].kind==='native'&&p[0].x===152&&p[0].y===252,'Overlapping native shadow offset');
p=shadowPlan([owner,{...sh,x:152,y:252,w:102,h:102}]);assert(p.length===1&&p[0].x===152&&p[0].w===102,'Keep separated native projection');
p=shadowPlan([owner,sh,{...sh,uid:9,alpha:.5}]);assert(p.length===1,'No duplicate projection');
p=shadowPlan([{...owner,active:false},{...sh,ownerRef:{...owner,active:false}}]);assert(p.length===0,'Destroyed owner loses shadow');
p=shadowPlan([{...owner,role:'ground'}]);assert(p.length===0,'No fake ground target shadow');
p=shadowPlan([{...owner,role:'fighter'}]);assert(p.length===0,'No change to fighters');
p=shadowPlan([{...owner,x:210,y:325}]);assert(p[0].x===162&&p[0].y===277,'Shadow follows displacement');
p=shadowPlan([owner,{...sh,ownerId:null,ownerRef:null,uid:9,x:0}]);assert(p.length===2,'Unpaired original remains visible');
return 9;
 });
 assert.equal(shadowChecks,9,'Bomber ground shadow regression');
 const data=await page.evaluate(()=>{
   const api=window.StrikersFlightReplay,seen=new Map();
   let duplicates=0,steps=0,maxJump=0,b35=0,big=0,shadows=0,corrected=0,pitch=0,wrong=0,spinning=0,pitchFlips=0,invalidFlips=0,horizontalBig=0,wrongHorizontalBig=0;
   let prev=new Map(),projectionCount=0,projectionCombat=0,pairedVisible=0,deathProbe=null;
   for(let frame=0;frame<=3680;frame++){
    const t=frame/60,es=api.test.smooth(t),ids=new Set(),current=new Map();
    const visibleIds=new Set(es.filter(e=>e.role!=='shadow').map(e=>e.uid));
    for(const e of es){
     if(ids.has(e.uid))duplicates++;ids.add(e.uid);
     const p=prev.get(e.uid);
     if(p){maxJump=Math.max(maxJump,Math.hypot(e.x-p.x,e.y-p.y));steps++}
     current.set(e.uid,e);
     if(e.projection){projectionCount++;if(e.role!=='shadow')projectionCombat++;if(visibleIds.has(e.ownerId)){pairedVisible++;if(!deathProbe&&frame>0)deathProbe={t,owner:e.ownerId,shadow:e.uid};}}
     if(e.turn!==0)spinning++;
     if(e.facingMask!==0&&e.facingMask!==0xc000&& !((e.code===21316||e.role==='shadow')&&[0x4000,0x8000].includes(e.facingMask)))invalidFlips++;
     if(e.code===21316&&e.role==='bomber'&&Math.abs(e.vx)>=8&&Math.abs(e.vx)>=2*Math.abs(e.vy)){
      horizontalBig++;
      if((e.attr&0x4000)!==0||((e.attr&0x8000)!==0)!==(e.vx<0))wrongHorizontalBig++;
     }
     if(e.facingMask)corrected++;
     if(e.pitch&&e.role!=='shadow'){pitch++;if(e.facingMask)pitchFlips++}
     if(!e.pitch&&e.role!=='ground'&&e.role!=='shadow'&&e.alignment!==null&&e.alignment<-.281)wrong++;
     if([21691,21715].includes(e.code)&&!e.projection){
       b35++;if(e.role!=='bomber')throw Error('B-35 treated as ground');
     }
     if([21316,21424].includes(e.code))big++;
     if(e.code===21460)shadows++;
    }
    prev=current;
   }
   const stats=api.stats();let deathPersistence=false;
   if(deathProbe){api.reset();api.test.smooth(deathProbe.t);api.test.destroy(deathProbe.owner);const after=api.test.smooth(deathProbe.t+1/60);deathPersistence=!after.some(e=>e.uid===deathProbe.owner||e.uid===deathProbe.shadow);}
   return {stats,steps,maxJump,duplicates,b35,big,shadows,corrected,pitch,wrong,spinning,pitchFlips,invalidFlips,horizontalBig,wrongHorizontalBig,projectionCount,projectionCombat,pairedVisible,deathPersistence};
 });
 assert.equal(data.stats.frames,116);
 assert.equal(data.stats.matching.projectionRecords,287,'All reduced palette-10 records accounted for');
 assert(data.stats.matching.pairedProjections>100,'Same-code offset matches support the projection classification');
 assert.equal(data.projectionCombat,0,'Terrain projections are never enemies');
 assert(data.pairedVisible>100&&data.deathPersistence,'Paired projections vanish when their owner is destroyed');
 assert.equal(data.duplicates,0,'No aircraft duplicated in same visible frame');
 assert(data.steps>500,'Enough neighboring visible samples');
 assert(data.maxJump<16,'No visible aircraft teleports at 60 Hz');
 assert(data.b35>50&&data.big>10&&data.shadows>10,'Original enemy families still visible');
 assert(data.corrected>0,'Confirmed backwards sprites receive hardware flips');
 assert(data.pitch>0,'Original pitch/loop sequence occurs');
 assert.equal(data.wrong,0,'No confirmed non-loop heading remains strongly opposite to trajectory');
 assert.equal(data.spinning,0,'No artificial canvas rotation');
 assert.equal(data.pitchFlips,0,'Actual pitch/roll animation keeps captured hardware orientation');
 assert.equal(data.invalidFlips,0,'Only original hardware flip flags alter tiles');
 assert(data.horizontalBig>10,'Lateral four-engine formation is visible');
 assert.equal(data.wrongHorizontalBig,0,'Lateral bombers face along travel with an upward nose');
 assert.deepEqual(errors,[],'No browser JavaScript errors');
 console.log('STRIKERS V8.7 BOMBER ORIENTATION AND SHADOW AUDIT:',JSON.stringify(data));
}finally{await browser.close()}
