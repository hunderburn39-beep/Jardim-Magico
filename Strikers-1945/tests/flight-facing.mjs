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
   let duplicates=0,steps=0,maxJump=0,backwardFighters=0,b35=0,b35Flipped=0,big=0,bigFlipped=0,shadows=0,shadowsFlipped=0;
   let prev=new Map();
   for(let frame=0;frame<=3680;frame++){
    const t=frame/60,es=api.test.smooth(t),ids=new Set(),current=new Map();
    for(const e of es){
     if(ids.has(e.uid))duplicates++;ids.add(e.uid);
     const p=prev.get(e.uid);
     if(p){maxJump=Math.max(maxJump,Math.hypot(e.x-p.x,e.y-p.y));steps++}
     current.set(e.uid,e);
     if(e.role==='fighter'&&e.vy>40&&e.turn>2.65)backwardFighters++;
     if([21691,21715].includes(e.code)){
       b35++;
       if(e.role!=='bomber')throw Error('B-35 erroneously treated as ground: '+e.code);
       if(e.turn>2.65)b35Flipped++;
     }
     if([21316,21424].includes(e.code)){
       big++;
       if(e.turn>2.65)bigFlipped++;
     }
     if(e.code===21460){
       shadows++;
       if(e.turn>2.65)shadowsFlipped++;
     }
    }
    prev=current;
   }
   return {stats:api.stats(),steps,maxJump,duplicates,backwardFighters,b35,b35Flipped,big,bigFlipped,shadows,shadowsFlipped};
 });
 assert.equal(data.stats.frames,116);
 assert.equal(data.duplicates,0,'No aircraft duplicated in same visible frame');
 assert(data.steps>500,'Enough neighboring visible samples');
 assert(data.maxJump<16,'No visible aircraft teleports at 60 Hz');
 assert(data.b35>50&&data.b35Flipped>5,'B-35 wing flies in correct forward direction');
 assert(data.big>10&&data.bigFlipped>0,'Diagonal bombers correct reverse motion');
 assert(data.shadows>10&&data.shadowsFlipped>0,'Original shadow follows airplane heading');
 assert(data.backwardFighters>0,'Reverse-moving small fighters correct their heading');
 assert.deepEqual(errors,[],'No browser JavaScript errors');
 console.log('STRIKERS V8.2 REAL-CAPTURE FLIGHT FACING:',JSON.stringify(data));
}finally{await browser.close()}
