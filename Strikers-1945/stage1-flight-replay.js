/* Strikers 1945 — animation and flight replay, stage 1.
   Recorded sprite registers at 116 moments of a local MAME run.
   Only position / orientation codes, color register samples and timing are stored.
   Sprite pixels are always decoded from the player's own s1945.zip.
   Interpolation and collision/fire mechanics are adaptations, not the arcade AI. */
(()=>{'use strict';
const DATA='./stage1-plane-frames.b64';
let frames=null,palettes=new Map(),busy=null,tracks=new Map(),cache=new Map(),timeLast=-1;
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
function reset(){tracks.clear();timeLast=-1}
function snapshot(time){
 if(!frames)return null;
 const frame=480+Math.max(0,time)*60;
 if(frame>frames[frames.length-1].frame+16)return {a:frames.at(-1),b:frames.at(-1),t:1,expired:true};
 let low=0,high=frames.length-1;
 while(low<high){const m=(low+high+1)>>1;if(frames[m].frame<=frame)low=m;else high=m-1}
 const a=frames[low],b=frames[Math.min(low+1,frames.length-1)];
 return {a,b,t:b===a?0:Math.min(1,Math.max(0,(frame-a.frame)/(b.frame-a.frame))),expired:false};
}
function sync(time){
 if(!ready())return [];
 if(time<timeLast-.1)reset();
 timeLast=time;
 const f=snapshot(time);if(!f||f.expired)return [];
 const later=new Map(f.b.items.map(x=>[x.id,x])),result=[],seen=new Set();
 for(const item of f.a.items){
  let prior=tracks.get(item.id),future=later.get(item.id),x=item.x,y=item.y;
  const canLerp=future&&Math.abs(item.w-future.w)<20&&Math.abs(item.h-future.h)<20&&
   Math.hypot(future.x-item.x,future.y-item.y)<155;
  if(canLerp){x+=(future.x-item.x)*f.t;y+=(future.y-item.y)*f.t}
  // Frame source slots are reused: respawn only on a substantial jump/type change.
  const reused=prior&&(
   Math.hypot(prior.x-x,prior.y-y)>150||
   (Math.abs(prior.lastCode-item.code)>1000&&prior.palette!==item.palette)||
   (Math.abs(prior.w-item.w)>90));
  if(!prior||reused){
   const heavy=item.nx>=5||item.ny>=5;
   prior={kind:'romPlane',romFlight:true,active:true,score:heavy?1900:400,
    hp:heavy?21:4,max:heavy?21:4,
    fire:1.1+((item.id*7)%11)*.11,age:0,t:0,
    drop:heavy?'gold':null,x,y};
   tracks.set(item.id,prior);
  }
  prior.x=x;prior.y=y;prior.w=item.w;prior.h=item.h;prior.sprite=item;
  prior.lastCode=item.code;prior.palette=item.palette;prior.id=item.id;
  prior.heavy=item.nx>=5||item.ny>=5;seen.add(item.id);
  if(prior.active&&x>-150&&x<510&&y>-190&&y<810)result.push(prior);
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
 if(cache.size>260)cache.delete(cache.keys().next().value);
 cache.set(key,canvas);
 return canvas;
}
function draw(ctx,e){
 if(!e.romFlight||!ready()||!e.sprite)return false;
 const asset=sprite(e.sprite);
 if(!asset)return false;
 ctx.save();ctx.imageSmoothingEnabled=false;
 ctx.drawImage(asset,e.x-e.w/2,e.y-e.h/2,e.w,e.h);
 ctx.restore();return true;
}
const api=Object.freeze({ensure,ready,sync,draw,reset,
 stats:()=>({frames:frames?.length||0,earliestFrame:frames?.[0]?.frame||0,latestFrame:frames?.at(-1)?.frame||0,trackCount:tracks.size}),
 test:(location.hostname==='localhost'||location.hostname==='127.0.0.1')?Object.freeze({
 frame:(time)=>snapshot(time)?.a.items.map(i=>({id:i.id,code:i.code,attr:i.attr,x:i.x,y:i.y}))||[],
 parsed:()=>!!frames
 }):undefined});
window.StrikersFlightReplay=api;
window.addEventListener('strikers-rom-ready',()=>api.ensure());
if(window.StrikersROM?.isLoaded())api.ensure();
})();