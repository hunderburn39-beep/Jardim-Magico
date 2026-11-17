/* Strikers 1945 — animation and flight replay, stage 1.
   Recorded sprite registers at 116 moments of a local MAME run.
   Only position / orientation codes, color register samples and timing are stored.
   Sprite pixels are always decoded from the player's own s1945.zip.
   Interpolation and collision/fire mechanics are adaptations, not the arcade AI. */
(()=>{'use strict';
const DATA='./stage1-plane-frames.b64';
let frames=null,palettes=new Map(),busy=null,tracks=new Map(),cache=new Map(),shadowCache=new Map(),timeLast=-1;
const validKinds=new Set([10,11,12,23,25,27,28]);
function dimensions(rawX,rawY){
 const nx=((rawX>>9)&7)+1,ny=((rawY>>9)&7)+1;
 let rx=rawX&511,ry=(rawY&255)-(rawY&256);
 if(rx>=384)rx-=512;
 const zx=(rawX>>12)&15,zy=(rawY>>12)&15;
 rx+=(nx*zx+2)>>2;ry+=(ny*zy+2)>>2;
 const nativeW=nx*(32-zx)/2,nativeH=ny*(32-zy)/2;
 // Psikyo native 224×320: rotate to portrait and crop 22 px per side.
 const cx=(ry-22+nativeH*.5)*2,cy=(320-rx-nativeW*.5)*2;
 // The EP1 capture's non-square graphics have transposed extents.
 // All six non-square LUT codes were verified against the original ROM.
 // Retain recorded centre/trajectory; swap the image extents and display size.
 if(nx!==ny)return {x:cx,y:cy,w:nativeW*2,h:nativeH*2,nx:ny,ny:nx};
 return {x:cx,y:cy,w:nativeH*2,h:nativeW*2,nx,ny};
}
async function prepare(){
 if(!globalThis.DecompressionStream)throw Error('Safari sem descompressão zlib');
 const r=await fetch(DATA,{cache:'force-cache'});
 if(!r.ok)throw Error('Capturas de animação não disponíveis');
 const b64=(await r.text()).trim(),binary=atob(b64);
 const packed=Uint8Array.from(binary,c=>c.charCodeAt(0));
 const buf=await new Response(new Blob([packed]).stream().pipeThrough(new DecompressionStream('deflate'))).arrayBuffer();
 const view=new DataView(buf),bytes=new Uint8Array(buf);
 if(bytes.length<30||String.fromCharCode(...bytes.subarray(0,3))!=='EP1'||bytes[3]!==1)
  throw Error('Formato de captura de caças inválido');
 const count=view.getUint16(4),palCount=bytes[6],bankList=[...bytes.subarray(7,7+palCount)];
 if(count!==116||palCount!==7)throw Error('Registo da primeira fase incompleto');
 let offset=7+palCount;
 const banks=new Map();
 for(const bank of bankList){
  const groups=[];
  for(let phase=0;phase<3;phase++){
   const vals=new Uint16Array(16);
   for(let i=0;i<16;i++){vals[i]=view.getUint16(offset);offset+=2}
   groups.push(vals);
  }
  banks.set(bank,groups);
 }
 const result=[];
 for(let i=0;i<count;i++){
  if(offset+4>bytes.length)throw Error('Fotograma incompleto');
  const frame=view.getUint16(offset),phase=bytes[offset+2],countItems=bytes[offset+3];offset+=4;
  const items=[];
  for(let j=0;j<countItems;j++){
   if(offset+9>bytes.length)throw Error('Sprite incompleto');
   const id=bytes[offset],xraw=view.getUint16(offset+1),yraw=view.getUint16(offset+3);
   const attr=view.getUint16(offset+5),code=view.getUint16(offset+7)|((attr&1)<<16);offset+=9;
   const palette=(attr>>8)&63;
   if(!validKinds.has(palette))continue;
   const dims=dimensions(xraw,yraw);
   items.push({id,code,attr,phase,palette,...dims});
  }
  result.push({frame,items});
 }
 if(offset!==bytes.length)throw Error('Captura com dados adicionais não reconhecidos');
 frames=result;palettes=banks;tracks.clear();cache.clear();shadowCache.clear();timeLast=-1;linked=false;
 dispatchEvent(new Event('strikers-flight-ready'));return true;
}
function ready(){return !!frames&&!!window.StrikersROM?.isLoaded()}
function ensure(){
 if(!window.StrikersROM?.isLoaded())return Promise.resolve(false);
 if(frames)return Promise.resolve(true);
 if(busy)return busy;
 busy=prepare().catch(e=>{console.warn('Strikers plane replay:',e);busy=null;return false});
 return busy;
}
function reset(){tracks.clear();timeLast=-1}
function snapshot(time){
 if(!frames)return null;
 // Match the same 47-second timeline used by the reconstructed stage-1 background.
 // Background and enemies now advance through their captured emulated frames together.
 const firstFrame=frames[0].frame,lastFrame=frames[frames.length-1].frame;
 // Captures are 60 Hz MAME frames: 480..4160 is 61.33 s, not 47 s.
 // Never compress the recorded flight path; the background uses the same clock.
 const frame=firstFrame+Math.max(0,time)*60;
 if(frame>lastFrame+15)return {a:frames[frames.length-1],b:frames[frames.length-1],t:1,expired:true};
 let low=0,high=frames.length-1;
 while(low<high){const m=(low+high+1)>>1;if(frames[m].frame<=frame)low=m;else high=m-1}
 const a=frames[low],b=frames[Math.min(low+1,frames.length-1)];
 return {a,b,t:b===a?0:Math.min(1,Math.max(0,(frame-a.frame)/(b.frame-a.frame))),expired:false};
}
// A sprite-RAM slot is not a permanent aircraft ID. Compare screen position,
 // size and graphic family before associating two captured frames.
// The dark aircraft-shaped ROM silhouettes are separate terrain shadows, not
// hostile aircraft. Determine this from their original palette/pixels, rather
// than manufacturing a blurred copy of the aircraft sprite.
function isTerrainShadow(e){
 if(e.nx<4||e.ny<4)return false;
 const key=[e.code,e.attr,e.nx,e.ny,e.palette,e.phase].join(':');
 if(shadowCache.has(key))return shadowCache.get(key);
 // Confirmed visually against the local stage-1 catalog (LUT 21460).
 if(e.code===21460){shadowCache.set(key,true);return true;}
 const c=sprite(e);
 if(!c)return false;
 const data=c.getContext('2d').getImageData(0,0,c.width,c.height).data;
 let opaque=0,dark=0,light=0;
 for(let i=0;i<data.length;i+=4){
  if(data[i+3]<200)continue;
  opaque++;
  const r=data[i],g=data[i+1],b=data[i+2];
  if(Math.max(r,g,b)<57&&Math.max(r,g,b)-Math.min(r,g,b)<26)dark++;
  if(Math.max(r,g,b)>96)light++;
 }
 const coverage=opaque/(c.width*c.height);
 const result=coverage>.075&&coverage<.62&&dark/Math.max(1,opaque)>.92&&light/Math.max(1,opaque)<.02;
 if(shadowCache.size>400)shadowCache.clear();
 shadowCache.set(key,result);
 return result;
}
function role(e){
 if(isTerrainShadow(e))return 'shadow';
 // B-35 flying wing silhouettes are AIRBORNE enemies, never terrain scenery.
 // Stage scroll can mimic an apparently reversed direction; correct heading
 // instead of suppressing collision, fire and enemy gameplay.
 if([21691,21715].includes(e.code))return 'bomber';
 if([21312,21769,21781,21787,21811].includes(e.code))return 'fighter';
 if(e.palette===28||e.nx>=5||e.ny>=5)return 'bomber';
 if(e.nx>=3||e.ny>=3)return 'ground';
 return 'fighter';
}
// Directions inspected on 106 actual ROM sprite/flip combinations, not
// inferred from sprite size or palette. Degrees clockwise from screen top.
// Reference flags are the hardware flip bits for the inspected tile assembly.
// Pitch/roll frames with ambiguous projected nose retain their original flags.
const NOSE_GROUPS=[
 [1,180,[9583,9587,9591,9595,9599,9603]],
 [1,165,[9655,9659]],
 [1,150,[9663,9667,9671,9675]],
 [1,135,[9679,9683]],
 [1,120,[9687,9691]],
 [1,105,[9695,9699]],
 [1,90,[9703,9707,9711,9715,9787]],
 [0,90,[9775,9779]],
 [0,180,[9919,9923,9935,9939]],
 [0,0,[9967,9971,9999,10003,10031,10035,10047,10051]],
 [0,45,[21316]],
 [0,-45,[21424,21460]],
 [0,0,[21691,21715,21769,21781,21787,21811]],
 [1,180,[21817,21825,21833,21837,21845,21853,21909,21913,21917,21921,21925]],
 [1,0,[21857,21861,21865,21869,21929,21933,21937,21941,21949,21953,21957,21981,21985,21989]]
];
const NOSE_DATA=new Map();
for(const [flags,deg,codes] of NOSE_GROUPS)for(const code of codes)NOSE_DATA.set(code,{flags,deg});
function noseVector(item,attr=item.attr){
 const spec=NOSE_DATA.get(item.code);if(!spec)return null;
 const angle=spec.deg*Math.PI/180,flip=(attr>>14)^spec.flags;
 return {x:Math.sin(angle)*(flip&2?-1:1),y:-Math.cos(angle)*(flip&1?-1:1)};
}
function isPitchFamily(code){return (code>=9827&&code<=10051)||(code>=21857&&code<=21989)}
// A pitch loop can face the camera or briefly show the underside while its
// projected screen motion continues sideways. Never fake a turn in this family.
function hasPitchChange(item){
 if(!isPitchFamily(item.code))return false;
 let p=item,n=item;
 for(let i=0;i<3;i++){
  p=p?.prev;n=n?.next;
  if((p&&isPitchFamily(p.code)&&p.code!==item.code)||(n&&isPitchFamily(n.code)&&n.code!==item.code))return true;
 }
 return false;
}
function orientFrame(item,vx,vy){
 const nose=noseVector(item),speed=Math.hypot(vx,vy);
 if(!nose||speed<34||item.role==='ground'||hasPitchChange(item))return item;
 const dot=(nose.x*vx+nose.y*vy)/speed;
 if(dot>-.28)return item;
 // A 180-degree inversion uses the exact original tiles with both hardware
 // flips. No continuous canvas rotation / paper-like spinning or guessed art.
 // Opposite heading must be a material improvement over the captured frame.
 const attr=item.attr^0xc000;
 return {...item,attr,orientationMask:0xc000};
}
// Match full consecutive sprite lists, not their transient display-list slots.
let linked=false,linkStats={pairs:0,changedSlots:0,tracks:0,maxStep:0};
function clamp01(t){return Math.max(0,Math.min(1,t))}
function family(e){return e.code<20000?0:1}
function nearField(e){return e.x+e.w/2>=-100&&e.x-e.w/2<=460&&e.y+e.h/2>=-100&&e.y-e.h/2<=740}
function matchCost(a,b,dt){
 if(a.palette!==b.palette||a.role!==b.role||family(a)!==family(b))return 1e6;
 const cd=Math.abs(a.code-b.code),ds=Math.abs(a.w-b.w)+Math.abs(a.h-b.h);
 if(cd>700||ds>125)return 1e6;
 const dx=b.x-a.x,dy=b.y-a.y,dist=Math.hypot(dx,dy);
 if(dist>Math.min(255,500*dt+24))return 1e6;
 if(!nearField(a)&&!nearField(b)&&dist>95)return 1e6;
 const p=a.prev,dt0=p?Math.max(1/60,(a.frame-p.frame)/60):dt;
 const vx=p?(a.x-p.x)/dt0:0,vy=p?(a.y-p.y)/dt0:0;
 // Velocity prediction is a tie-breaker, not an obligation to fly straight:
 // the captured positions can support reversals and banked turns.
 const pred=p?Math.hypot(dx-vx*dt,dy-vy*dt):dist;
 let cost=p ? .52*dist+.48*Math.min(pred,dist+115) : dist;
 cost+=Math.min(46,cd*.09)+ds*.22;
 if(a.id===b.id)cost-=3;
 return Math.max(0,cost);
}
// Rectangular minimum-cost assignment; dummy columns allow each old sprite
// to leave rather than falsely linking to a distant new aircraft.
function assign(cost){
 const n=cost.length,m=cost[0]?.length||0;if(!n)return [];
 const u=Array(n+1).fill(0),v=Array(m+1).fill(0),p=Array(m+1).fill(0),way=Array(m+1).fill(0);
 for(let i=1;i<=n;i++){
  p[0]=i;let j0=0;const minv=Array(m+1).fill(Infinity),used=Array(m+1).fill(false);
  do{used[j0]=true;const i0=p[j0];let delta=Infinity,j1=0;
   for(let j=1;j<=m;j++)if(!used[j]){
    const c=cost[i0-1][j-1]-u[i0]-v[j];
    if(c<minv[j]){minv[j]=c;way[j]=j0}
    if(minv[j]<delta){delta=minv[j];j1=j}
   }
   for(let j=0;j<=m;j++)if(used[j]){u[p[j]]+=delta;v[j]-=delta}else minv[j]-=delta;
   j0=j1;
  }while(p[j0]!==0);
  do{const j1=way[j0];p[j0]=p[j1];j0=j1}while(j0);
 }
 const result=Array(n).fill(-1);for(let j=1;j<=m;j++)if(p[j])result[p[j]-1]=j-1;return result;
}
function linkAll(){
 if(linked)return;
 let serial=0,pairs=0,changedSlots=0,maxStep=0;
 for(let i=0;i<frames.length;i++){
  const curr=frames[i];for(const a of curr.items){a.frame=curr.frame;a.role=role(a);a.prev=null;a.next=null;a.trackId=0}
  if(i){const old=frames[i-1].items,now=curr.items,dt=(curr.frame-frames[i-1].frame)/60;
   const unmatched=184, costs=old.map(a=>[...now.map(b=>matchCost(a,b,dt)),...old.map(()=>unmatched)]);
   const matches=assign(costs);
   for(let k=0;k<old.length;k++){const j=matches[k];if(j<0||j>=now.length||costs[k][j]>=unmatched)continue;
    const a=old[k],b=now[j];a.next=b;b.prev=a;b.trackId=a.trackId;pairs++;
    if(a.id!==b.id)changedSlots++;
    maxStep=Math.max(maxStep,Math.hypot(a.x-b.x,a.y-b.y));
   }
  }
  for(const a of curr.items)if(!a.trackId)a.trackId=++serial;
 }
 linkStats={pairs,changedSlots,tracks:serial,maxStep};linked=true;
}
function curve(a,b,p,n,t){
 const d=b-a;let m0=p===null?d:(b-p)*.5,m1=n===null?d:(n-a)*.5;
 const limit=Math.max(24,Math.abs(d)*1.5);
 m0=Math.max(-limit,Math.min(limit,m0));m1=Math.max(-limit,Math.min(limit,m1));
 const t2=t*t,t3=t2*t;
 return (2*t3-3*t2+1)*a+(t3-2*t2+t)*m0+(-2*t3+3*t2)*b+(t3-t2)*m1;
}
function position(a,b,t){
 const p=a.prev,n=b.next,allowance=Math.min(18,Math.max(4,Math.hypot(b.x-a.x,b.y-a.y)*.12));
 return {x:Math.max(Math.min(a.x,b.x)-allowance,Math.min(Math.max(a.x,b.x)+allowance,curve(a.x,b.x,p?.x??null,n?.x??null,t))),
 y:Math.max(Math.min(a.y,b.y)-allowance,Math.min(Math.max(a.y,b.y)+allowance,curve(a.y,b.y,p?.y??null,n?.y??null,t)))};
}
function edgePosition(a,elapsed,incoming){
 const b=incoming?a.next:a.prev;
 if(!b)return {x:a.x,y:a.y};
 const dt=Math.max(1/60,Math.abs(b.frame-a.frame)/60),d=Math.min(.14,Math.max(0,elapsed))/dt;
 // Keep entering/exiting objects moving, but never extrapolate a whole pass.
 return {x:a.x+(a.x-b.x)*d,y:a.y+(a.y-b.y)*d};
}
function sync(time){
 if(!ready())return [];
 if(!linked)linkAll();
 if(time<timeLast-.1)reset();
 timeLast=time;
 const f=snapshot(time);if(!f||f.expired){tracks.clear();return []}
 const result=[],seen=new Set(),t=clamp01(f.t),dt=(f.b.frame-f.a.frame)/60;
 function record(item,other,fade,pos){
  const id=item.trackId,kind=item.role,heavy=kind==='bomber',ground=kind==='ground',shadow=kind==='shadow';
  seen.add(id);
  let e=tracks.get(id);
  if(!e){e={id,uid:id,kind:shadow?'romShadow':ground?'romGround':'romPlane',romFlight:true,active:true,
   score:shadow?0:heavy?1900:ground?650:400,hp:shadow?Infinity:heavy?21:ground?11:4,max:shadow?Infinity:heavy?21:ground?11:4,
   fire:shadow?Infinity:ground?2.4:1.1+((id*7)%11)*.11,age:0,t:0,drop:heavy?'gold':null};tracks.set(id,e)}
  // Preserve object identity, hit points and destruction across re-ordered slots.
  e.rawId=item.id;e.x=pos.x;e.y=pos.y;e.w=other?item.w+(other.w-item.w)*t:item.w;e.h=other?item.h+(other.h-item.h)*t:item.h;
  // Keep captured hardware zoom and dimensions; no artificial bomber shrink.
  e.visualScale=1;
  e.renderW=e.w*e.visualScale;e.renderH=e.h*e.visualScale;e.source=item;e.sprite=item;e.lastCode=item.code;e.palette=item.palette;
  e.heavy=heavy;e.role=kind;e.alpha=fade;e.spriteMix=0;e.spriteFrom=null;e.spriteTo=null;
  if(other&&dt>0){
   // Use the local tangent of the curve being drawn, not its entire chord.
   const h=.0125,lo=Math.max(0,t-h),hi=Math.min(1,t+h);
   const p0=position(item,other,lo),p1=position(item,other,hi);
   const span=Math.max(1e-5,(hi-lo)*dt);
   e.vx=(p1.x-p0.x)/span;e.vy=(p1.y-p0.y)/span;
   if(item.code!==other.code||item.attr!==other.attr||item.phase!==other.phase){
    e.spriteMix=clamp01((t-.45)/.10);
    e.sprite=e.spriteMix>.5?other:item;
   }
  }else{
   const last=item.prev,next=item.next,neighbor=next||last;
   if(neighbor){const span=(neighbor.frame-item.frame)/60;e.vx=(neighbor.x-item.x)/span;e.vy=(neighbor.y-item.y)/span}
   else{e.vx=0;e.vy=0}
  }
  // Preserve genuine pitch-loop frames and apply only confirmed inversions.
  // Never cross-dissolve two different silhouettes into a double aircraft.
  e.originalSprite=e.sprite;e.sprite=orientFrame(e.sprite,e.vx,e.vy);
  e.facingMask=e.sprite.attr^e.originalSprite.attr;e.visualTurn=0;
  const nose=noseVector(e.sprite),speed=Math.hypot(e.vx,e.vy);
  e.alignment=nose&&speed>=34?(nose.x*e.vx+nose.y*e.vy)/speed:null;
  e.pitch=hasPitchChange(e.originalSprite);
  const halfW=e.renderW/2,halfH=e.renderH/2;
  if(e.active&&fade>.025&&e.x>-halfW-24&&e.x<360+halfW+24&&e.y>-halfH-24&&e.y<640+halfH+24)result.push(e);
 }
 for(const a of f.a.items){
  if(f.a===f.b){record(a,null,1,{x:a.x,y:a.y});continue}
  const b=a.next;
  if(b&&b.frame===f.b.frame)record(a,b,1,position(a,b,t));
  else record(a,null,clamp01(1-t*2),edgePosition(a,t*dt,false));
 }
 if(f.a!==f.b)for(const b of f.b.items)if(!b.prev||b.prev.frame!==f.a.frame){
  if(t>=.5)record(b,null,clamp01((t-.5)*2),edgePosition(b,(1-t)*dt,true));
 }
 // The original matching terrain shadow follows its owning bomber's exact
 // flip mask, even if the two captured positions have minor rounding error.
 for(const shadow of result)if(shadow.role==='shadow'&&shadow.sprite.code===21460){
  let owner=null,dist=36;
  for(const e of tracks.values())if(e.role==='bomber'&&e.source?.code===21424){
   const d=Math.hypot(e.x-shadow.x,e.y-shadow.y);
   if(d<dist){dist=d;owner=e}
  }
  if(owner){
   shadow.sprite={...shadow.originalSprite,attr:shadow.originalSprite.attr^owner.facingMask};
   shadow.facingMask=owner.facingMask;
   if(!owner.active)shadow.alpha=0;
  }
 }
 // Keep destroyed objects so they cannot return when their RAM slot changes.
 for(const id of tracks.keys())if(!seen.has(id))tracks.delete(id);
 return result.filter(e=>e.alpha>.025);
}
function sprite(item){
 const key=[item.code,item.attr,item.nx,item.ny,item.phase].join(':');
 if(cache.has(key))return cache.get(key);
 const cx=item.ny*16,cy=item.nx*16;
 const canvas=document.createElement('canvas');canvas.width=cx;canvas.height=cy;
 const c=canvas.getContext('2d'),im=c.createImageData(cx,cy),o=im.data;
 const paletteSets=palettes.get(item.palette);
 if(!paletteSets)return null;
 // The recorded colors change across the base's three palette-RAM snapshots.
 let colors=paletteSets[item.phase]||paletteSets[0];
 if(colors.every(v=>v===0x8000||v===0))colors=paletteSets.find(a=>a.some(v=>v!==0x8000&&v!==0))||colors;
 const flipX=!!(item.attr&0x4000),flipY=!!(item.attr&0x8000);
 let code=item.code;
 for(let dy=flipY?item.ny-1:0;flipY?dy>=0:dy<item.ny;dy+=flipY?-1:1){
  for(let dx=flipX?item.nx-1:0;flipX?dx>=0:dx<item.nx;dx+=flipX?-1:1){
   for(let py=0;py<16;py++)for(let px=0;px<16;px++){
    const u=flipX?15-px:px,v=flipY?15-py:py;
    const pix=window.StrikersROM.rawPixel('sprite',code,u,v,true);
    if(pix===15)continue;
    const rgb=colors[pix],ix=dy*16+py,iy=item.nx*16-1-(dx*16+px);
    const p=(iy*cx+ix)*4;
    o[p]=((rgb>>10)&31)*255/31|0;o[p+1]=((rgb>>5)&31)*255/31|0;
    o[p+2]=(rgb&31)*255/31|0;o[p+3]=255;
   }
   code=(code+1)&0x1ffff;
  }
 }
 c.putImageData(im,0,0);
 if(cache.size>160)cache.delete(cache.keys().next().value);
 cache.set(key,canvas);
 return canvas;
}
function draw(ctx,e){
 if(!e.romFlight||!ready()||!e.sprite)return false;
 const sheet=sprite(e.sprite);if(!sheet)return false;
 const alpha=(e.alpha??1)*(e.visualAlpha??1);if(alpha<=.02)return true;
 ctx.save();ctx.imageSmoothingEnabled=false;ctx.globalAlpha*=alpha;
 const rw=e.renderW??e.w,rh=e.renderH??e.h;
 ctx.drawImage(sheet,e.x-rw/2,e.y-rh/2,rw,rh);
 ctx.restore();return true;
}
const api=Object.freeze({ensure,ready,sync,draw,reset,
 stats:()=>({frames:frames?.length||0,earliestFrame:frames?.[0]?.frame||0,latestFrame:frames?.at(-1)?.frame||0,trackCount:tracks.size,matching:linkStats,terrainShadowDetection:'ROM silhouettes + palette',flightFacing:'per-LUT native heading, hardware flips, local motion tangent and pitch-frame preservation',flightTime:frames?(frames.at(-1).frame-frames[0].frame)/60:61.333,interpolation:'global-assignment-bounded-hermite',spriteBlend:false}),
 test:(location.hostname==='localhost'||location.hostname==='127.0.0.1')?Object.freeze({
 smooth:(t)=>sync(t).filter(e=>e.romFlight).map(e=>({uid:e.uid,id:e.id,rawId:e.rawId,role:e.role,x:e.x,y:e.y,alpha:e.alpha,mix:e.spriteMix,code:e.sprite.code,vx:e.vx,vy:e.vy,turn:e.visualTurn,attr:e.sprite.attr,originalAttr:e.originalSprite.attr,facingMask:e.facingMask,alignment:e.alignment,pitch:e.pitch})),
 links:()=>{if(!linked&&frames)linkAll();return {...linkStats}},
 frame:(time)=>snapshot(time)?.a.items.map(i=>({id:i.id,code:i.code,attr:i.attr,x:i.x,y:i.y}))||[],
 parsed:()=>!!frames
 }):undefined});
window.StrikersFlightReplay=api;
window.addEventListener('strikers-rom-ready',()=>{cache.clear();shadowCache.clear();linked=false;reset();api.ensure()});
if(window.StrikersROM?.isLoaded())api.ensure();
})();