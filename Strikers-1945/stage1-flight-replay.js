/* Strikers 1945 — animation and flight replay, stage 1.
   Recorded sprite registers at 116 moments of a local MAME run.
   Only position / orientation codes, color register samples and timing are stored.
   Sprite pixels are always decoded from the player's own s1945.zip.
   Interpolation and collision/fire mechanics are adaptations, not the arcade AI. */
(()=>{'use strict';
const DATA='./stage1-plane-frames.b64';
let frames=null,palettes=new Map(),busy=null,tracks=new Map(),cache=new Map(),timeLast=-1,nextSerial=0;
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
   const attr=view.getUint16(offset+5),code=view.getUint16(offset+7);offset+=9;
   const palette=(attr>>8)&63;
   if(!validKinds.has(palette))continue;
   const dims=dimensions(xraw,yraw);
   items.push({id,code,attr,phase,palette,...dims});
  }
  result.push({frame,items});
 }
 if(offset!==bytes.length)throw Error('Captura com dados adicionais não reconhecidos');
 frames=result;palettes=banks;tracks.clear();cache.clear();timeLast=-1;
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
function reset(){tracks.clear();timeLast=-1;nextSerial=0}
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
function role(e){
 if(e.nx>=5&&e.ny<=3)return 'ground';
 if(e.nx>=5||e.ny>=5)return 'bomber';
 if(e.nx>=3||e.ny>=3)return 'ground';
 return 'fighter';
}
function related(a,b){
 if(!a||!b||role(a)!==role(b))return false;
 if(Math.abs(a.w-b.w)>85||Math.abs(a.h-b.h)>85)return false;
 if(Math.abs(a.code-b.code)>700)return false;
 // One RAM slot can be recycled for another aircraft with similar graphics.
 // A palette-bank change means a new tracked object, not a banked turn.
 if(a.palette!==b.palette)return false;
 if(Math.hypot(a.x-b.x,a.y-b.y)>220)return false;
 return true;
}
const MAX_KEYFRAME_JUMP=245;
function clamp01(t){return Math.max(0,Math.min(1,t))}
function hermite(a,b,prev,next,t){
 const d=b-a;
 // Estimate tangent from adjacent real captures. Linear fallback when missing.
 let m0=prev===null?d:(b-prev)*.5;
 let m1=next===null?d:(next-a)*.5;
 const limit=Math.max(30,Math.abs(d)*1.6);
 m0=Math.max(-limit,Math.min(limit,m0));m1=Math.max(-limit,Math.min(limit,m1));
 const t2=t*t,t3=t2*t;
 return (2*t3-3*t2+1)*a+(t3-2*t2+t)*m0+(-2*t3+3*t2)*b+(t3-t2)*m1;
}
function frameAt(i){return frames[Math.max(0,Math.min(frames.length-1,i))]}
function sync(time){
 if(!ready())return [];
 if(time<timeLast-.1)reset();
 timeLast=time;
 const f=snapshot(time);if(!f||f.expired)return [];
 const ai=frames.indexOf(f.a),prev=frameAt(ai-1),next=frameAt(ai+2);
 const before=new Map(prev.items.map(it=>[it.id,it]));
 const future=new Map(f.b.items.map(it=>[it.id,it]));
 const after=new Map(next.items.map(it=>[it.id,it]));
 const current=new Map(f.a.items.map(it=>[it.id,it]));
 const result=[],seen=new Set(),t=clamp01(f.t);
 function record(item,other,fade=1,incoming=false){
  if(fade<=.025)return;
  const id=item.id,entryKey=incoming?'incoming:'+id:id;
  if(!incoming&&tracks.has('incoming:'+id)){
   const pending=tracks.get('incoming:'+id);
   if(related(pending.source,item)){tracks.set(id,pending);tracks.delete('incoming:'+id)}
  }
  let e=tracks.get(entryKey);
  const continuous=related(item,other);
  let x=item.x,y=item.y;
  if(continuous){
   const beforeItem=before.get(id),afterItem=after.get(id);
   const p=related(beforeItem,item)?beforeItem:null;
   const n=related(other,afterItem)?afterItem:null;
   x=hermite(item.x,other.x,p?.x??null,n?.x??null,t);
   y=hermite(item.y,other.y,p?.y??null,n?.y??null,t);
   // Limit cubic overshoot; banked aircraft never teleport off the flight corridor.
   const minX=Math.min(item.x,other.x),maxX=Math.max(item.x,other.x);
   const minY=Math.min(item.y,other.y),maxY=Math.max(item.y,other.y);
   x=Math.max(minX,Math.min(maxX,x));y=Math.max(minY,Math.min(maxY,y));
  }
  const prevItem=e?.source;
  const reused=!!e&&(
   !related(prevItem,item)&&Math.hypot((prevItem?.x??x)-item.x,(prevItem?.y??y)-item.y)>90||
   e.role!==role(item)||
   Math.hypot(e.x-x,e.y-y)>MAX_KEYFRAME_JUMP||
   (Math.abs(e.lastCode-item.code)>2200));
  if(!e||reused){
   const heavy=role(item)==='bomber',ground=role(item)==='ground';
   e={kind:ground?'romGround':'romPlane',romFlight:true,uid:++nextSerial,active:true,
    score:heavy?1900:ground?650:400,hp:heavy?21:ground?11:4,
    max:heavy?21:ground?11:4,fire:ground?2.4:1.1+((id*7)%11)*.11,age:0,t:0,
    drop:heavy?'gold':null,x,y};
   tracks.set(entryKey,e);
  }
  e.id=id;e.w=item.w;e.h=item.h;e.x=x;e.y=y;
   // Keep the genuine ROM frames but normalize oversized bomber silhouettes for a narrow phone.
   e.visualScale=role(item)==='bomber'?Math.min(1,166/Math.max(item.w,item.h)):1;
   e.renderW=e.w*e.visualScale;e.renderH=e.h*e.visualScale;
  e.source=item;e.sprite=item;e.lastCode=item.code;e.palette=item.palette;
  e.heavy=role(item)==='bomber';e.role=role(item);e.alpha=fade;
  if(continuous){
   e.vx=(other.x-item.x)/Math.max(.016,(f.b.frame-f.a.frame)/60);
   e.vy=(other.y-item.y)/Math.max(.016,(f.b.frame-f.a.frame)/60);
   // The two real sprite codes represent the changing silhouette in a turn.
   // Dissolve briefly between them instead of spinning a flat image.
   const mix=clamp01((t-.43)/.14);
   if(item.code!==other.code||item.attr!==other.attr){
    e.sprite= mix>.5?other:item;
    e.spriteFrom=item;e.spriteTo=other;e.spriteMix=mix;
   }else{e.spriteMix=0;e.spriteFrom=null;e.spriteTo=null}
  }else{e.vx=0;e.vy=0;e.spriteMix=0;e.spriteFrom=null;e.spriteTo=null}
  const halfW=(e.renderW??e.w)*.5,halfH=(e.renderH??e.h)*.5;
  if(e.active&&x>-halfW-24&&x<360+halfW+24&&y>-halfH-24&&y<640+halfH+24)result.push(e);
  seen.add(entryKey);
 }
 for(const item of f.a.items){
  const other=future.get(item.id);
  const same=related(item,other);
  // Fade out at a recorded exit/replacement; prevents snapping to another plane.
  // Fade out the departing slot in the middle of a capture interval.
  // Do not show a replacement sprite as if it were the same airplane.
  const alpha=same?1:clamp01((.54-t)/.28);
  record(item,same?other:null,alpha);
 }
 // Introduce aircraft newly present in the next capture without a harsh pop.
 for(const item of f.b.items){
  const previous=current.get(item.id);
  if(previous&&related(previous,item))continue;
  if(t<.55)continue;
  // Re-used slot: a distinct incoming aircraft must have a distinct identity.
  // At the next captured frame its track is promoted to the regular slot ID.
  record(item,null,clamp01((t-.55)/.45),!!previous);
 }
 for(const id of tracks.keys())if(!seen.has(id))tracks.delete(id);
 return result;
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
 ctx.save();ctx.imageSmoothingEnabled=false;const baseAlpha=ctx.globalAlpha;ctx.globalAlpha=baseAlpha*alpha;
 const rw=e.renderW??e.w,rh=e.renderH??e.h;
 const dx=e.x-rw/2,dy=e.y-rh/2;
 // Cross-dissolve real animation frames for only ~75ms at each change.
 // The original ROM supplies both silhouettes; no fake 2D rotation.
 if(e.spriteFrom&&e.spriteTo&&e.spriteFrom!==e.spriteTo){
  const t=e.spriteMix;
  const old=sprite(e.spriteFrom),up=sprite(e.spriteTo);
  if(old&&up){ctx.globalAlpha=baseAlpha*alpha*(1-t);ctx.drawImage(old,dx,dy,rw,rh);
   ctx.globalAlpha=baseAlpha*alpha*t;ctx.drawImage(up,dx,dy,rw,rh);}
  else ctx.drawImage(sheet,dx,dy,rw,rh);
 }else ctx.drawImage(sheet,dx,dy,rw,rh);
 ctx.restore();return true;
}
const api=Object.freeze({ensure,ready,sync,draw,reset,
 stats:()=>({frames:frames?.length||0,earliestFrame:frames?.[0]?.frame||0,latestFrame:frames?.at(-1)?.frame||0,trackCount:tracks.size,flightTime:frames?(frames.at(-1).frame-frames[0].frame)/60:61.333,interpolation:'monotone-hermite',spriteBlend:true}),
 test:(location.hostname==='localhost'||location.hostname==='127.0.0.1')?Object.freeze({
 smooth:(t)=>sync(t).filter(e=>e.romFlight).map(e=>({uid:e.uid,id:e.id,role:e.role,x:e.x,y:e.y,alpha:e.alpha,mix:e.spriteMix,code:e.sprite.code})),
 frame:(time)=>snapshot(time)?.a.items.map(i=>({id:i.id,code:i.code,attr:i.attr,x:i.x,y:i.y}))||[],
 parsed:()=>!!frames
 }):undefined});
window.StrikersFlightReplay=api;
window.addEventListener('strikers-rom-ready',()=>{cache.clear();reset();api.ensure()});
if(window.StrikersROM?.isLoaded())api.ensure();
})();