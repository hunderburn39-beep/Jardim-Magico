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
 const data=await page.evaluate(()=>{
   const api=window.StrikersFlightReplay,seen=new Map();
   let duplicates=0,steps=0,maxJump=0,b35=0,big=0,shadows=0,corrected=0,pitch=0,wrong=0,spinning=0,pitchFlips=0,invalidFlips=0;
   let prev=new Map();
   for(let frame=0;frame<=3680;frame++){
    const t=frame/60,es=api.test.smooth(t),ids=new Set(),current=new Map();
    for(const e of es){
     if(ids.has(e.uid))duplicates++;ids.add(e.uid);
     const p=prev.get(e.uid);
     if(p){maxJump=Math.max(maxJump,Math.hypot(e.x-p.x,e.y-p.y));steps++}
     current.set(e.uid,e);
     if(e.turn!==0)spinning++;
     if(e.facingMask!==0&&e.facingMask!==0xc000)invalidFlips++;
     if(e.facingMask)corrected++;
     if(e.pitch){pitch++;if(e.facingMask)pitchFlips++}
     if(!e.pitch&&e.role!=='ground'&&e.role!=='shadow'&&e.alignment!==null&&e.alignment<-.281)wrong++;
     if([21691,21715].includes(e.code)){
       b35++;if(e.role!=='bomber')throw Error('B-35 treated as ground');
     }
     if([21316,21424].includes(e.code))big++;
     if(e.code===21460)shadows++;
    }
    prev=current;
   }
   return {stats:api.stats(),steps,maxJump,duplicates,b35,big,shadows,corrected,pitch,wrong,spinning,pitchFlips,invalidFlips};
 });
 assert.equal(data.stats.frames,116);
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
 assert.deepEqual(errors,[],'No browser JavaScript errors');
 console.log('STRIKERS V8.3 LUT DIRECTION AUDIT:',JSON.stringify(data));
}finally{await browser.close()}
