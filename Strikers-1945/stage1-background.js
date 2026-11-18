/* Stage 1 background from original Strikers 1945 hardware RAM.
   122 captured tilemaps and xRGB555 palettes, no copyrighted ROM pixels bundled.
   User-supplied ROM supplies individual u34.bin tile pixels in browser.
   Sampled every 16 emulated frames, interpolating scroll between captures. */
(()=>{'use strict';
 const PACKED=(window.StrikersCaptureChunks||[]).join('');
 let frames=null,busy=null,cache=new Map(),tileCache=new Map(),terminalCanvas=null,mapAudit=null;
 function dataUrlToBytes(text){
  const src=atob(text),a=new Uint8Array(src.length);
  for(let i=0;i<src.length;i++)a[i]=src.charCodeAt(i);
  return a;
 }
 async function expand(){
  if(PACKED.length!==7020)throw Error('Dados da captura da fase 1 incompletos');
  if(!globalThis.DecompressionStream)throw Error('Safari necessita de suporte à descompressão deflate');
  const data=dataUrlToBytes(PACKED);
  const ab=await new Response(new Blob([data]).stream().pipeThrough(new DecompressionStream('deflate'))).arrayBuffer();
  const dv=new DataView(ab),count=dv.getUint16(0,true),cols=dv.getUint16(2,true),rows=dv.getUint16(4,true);
  if(cols!==21||rows!==14||count!==122)throw Error('Captura do cenário inválida');
  let offset=6;frames=[];
  for(let j=0;j<count;j++){
   const frame=dv.getUint16(offset,true),scroll=dv.getUint16(offset+2,true),scroll1=dv.getUint16(offset+4,true),firstCol=dv.getUint16(offset+6,true),firstCol1=dv.getUint16(offset+8,true);
   offset+=10;
   const palettes=new Uint16Array(256);
   for(let k=0;k<256;k++,offset+=2)palettes[k]=dv.getUint16(offset,true);
   const codes=new Uint16Array(cols*rows*2);
   for(let i=0;i<codes.length;i++,offset+=2)codes[i]=dv.getUint16(offset,true);
   frames.push({frame,scroll,scroll1,firstCol,firstCol1,palettes,codes});
  }
  if(offset!==ab.byteLength)throw Error('Comprimento inválido da captura');
 }
 // Replace captured tile words ONLY when the original program contains
 // the exact same strip at the recorded scroll column. Unmatched strips
 // retain the capture. Both layers' original map IDs are independently known.
 function prepareMaps(){
  if(mapAudit)return mapAudit;
  if(!frames||!window.StrikersOriginalMaps?.all())return null;
  const totals={matchedLayers:0,retainedLayers:0,matchedWords:0};
  for(const f of frames){
   f.nativeCodes=[null,null];
   for(let layer=0;layer<2;layer++){
    const start=layer*294,col=layer===0?f.firstCol:f.firstCol1;
    const strip=window.StrikersOriginalMaps.matchCapture(layer,col,f.codes.subarray(start,start+294));
    if(strip){f.nativeCodes[layer]=strip;totals.matchedLayers++;totals.matchedWords+=strip.length;}
    else totals.retainedLayers++;
   }
  }
  cache.clear();terminalCanvas=null;
  mapAudit=Object.freeze(totals);return mapAudit;
 }
 function tile(id){
  if(tileCache.has(id))return tileCache.get(id);
  const api=window.StrikersROM,arr=new Uint8Array(256);
  for(let y=0;y<16;y++)for(let x=0;x<16;x++)arr[y*16+x]=api.rawPixel('tile',id,x,y);
  tileCache.set(id,arr);return arr;
 }
 // Cache each hardware tile layer independently so different scroll
 // registers never cause the upper layer to jump ahead at capture boundaries.
 function layerCanvas(index,layer){
  const key=index*2+layer;
  if(cache.has(key))return cache.get(key);
  const fr=frames[index],c=document.createElement('canvas');c.width=224;c.height=320;
  const ctx=c.getContext('2d',{alpha:layer!==0}),im=ctx.createImageData(224,320),d=im.data;
  if(layer===0)for(let z=3;z<d.length;z+=4)d[z]=255;
  const RGB5=v=>(v<<3)|(v>>2);
  const scroll=layer===0?fr.scroll:fr.scroll1,firstCol=layer===0?fr.firstCol:fr.firstCol1;
  for(let col=0;col<21;col++)for(let row=0;row<14;row++){
   const word=fr.nativeCodes?.[layer]?.[col*14+row]??fr.codes[layer*21*14+col*14+row],colors=(word>>13)&7,pxs=tile(word&8191);
   for(let dx=0;dx<16;dx++){
    const rawx=(firstCol*16+col*16+dx)-scroll;
    if(rawx<0||rawx>=320)continue;
    const dy=319-rawx;
    for(let py=0;py<16;py++){
     const pix=pxs[py*16+dx];if(pix===15)continue;
     const v=fr.palettes[layer*128+colors*16+pix],z=(dy*224+row*16+py)*4;
     d[z]=RGB5((v>>10)&31);d[z+1]=RGB5((v>>5)&31);d[z+2]=RGB5(v&31);d[z+3]=255;
    }
   }
  }
  ctx.putImageData(im,0,0);cache.set(key,c);
  if(cache.size>12)cache.delete(cache.keys().next().value);
  return c;
 }
 function frameCanvas(index){
  const c=document.createElement('canvas');c.width=224;c.height=320;
  const ctx=c.getContext('2d',{alpha:false});
  ctx.drawImage(layerCanvas(index,0),0,0);
  ctx.drawImage(layerCanvas(index,1),0,0);
  return c;
 }
 // Source strips do not overlap: transparent areas of the upper layer must
 // reveal the lower layer, not a second copy of the upper layer.
 function scrollStrips(step,f){
  const delta=Math.max(0,Math.min(320,step)),offset=delta*Math.max(0,Math.min(1,f));
  return {offset,currentHeight:320-offset,nextY:delta-offset,nextHeight:offset};
 }
 function drawLayer(ctx,i,layer,f,w,h){
  const a=frames[i],b=frames[Math.min(i+1,frames.length-1)];
  const field=layer===0?'scroll':'scroll1',step=b[field]-a[field];
  const p=scrollStrips(step,f),scale=h/320;
  const current=layerCanvas(i,layer);
  if(p.currentHeight>0)ctx.drawImage(current,22,0,180,p.currentHeight,0,p.offset*scale,w,p.currentHeight*scale);
  if(p.nextHeight>0)ctx.drawImage(layerCanvas(i+1,layer),22,p.nextY,180,p.nextHeight,0,0,w,p.nextHeight*scale);
 }
 async function ensure(){
  if(!window.StrikersROM?.isLoaded())return false;
  if(frames)return true;
  if(busy)return busy;
  busy=expand().then(()=>{cache.clear();tileCache.clear();terminalCanvas=null;dispatchEvent(new Event('strikers-stage1-ready'));return true})
   .catch(e=>{busy=null;frames=null;console.warn('Stage 1 original:',e);return false});
  return busy;
 }
 function draw(ctx,stageTime,w=360,h=640){
  if(!frames||!window.StrikersROM?.isLoaded())return false;
  const first=frames[0],last=frames.at(-1);
  // Aircraft capture starts at MAME frame 480 and the tilemap at 496.
  // Align the absolute emulated frame clocks, not merely elapsed seconds.
  const replayOrigin=window.StrikersFlightReplay?.ready()?window.StrikersFlightReplay.stats().earliestFrame:first.frame;
  const originalSeconds=(last.frame-replayOrigin)/60;
  // This tilemap capture ends at 2432 (32.27 s), whereas the airplane RAM
  // runs until frame 4160 (61.33 s). Avoid a frozen scene after the last map.
  if(stageTime>=originalSeconds){
   const prior=frames.at(-2),deltaFrames=Math.max(1,last.frame-prior.frame);
   const rawSpeed=(last.scroll-prior.scroll)*120/deltaFrames;
   const scrollSpeed=Number.isFinite(rawSpeed)&&rawSpeed>35&&rawSpeed<225?rawSpeed:110;
   const offset=((stageTime-originalSeconds)*scrollSpeed)%h;
   const still=terminalCanvas||(terminalCanvas=frameCanvas(frames.length-1));
   ctx.save();ctx.imageSmoothingEnabled=false;
   // Extrapolation ONLY: recycle the final recorded map tile to sustain
   // camera motion. The missing original map data cannot be reconstructed.
   ctx.drawImage(still,22,0,180,320,0,offset-h,w,h);
   ctx.drawImage(still,22,0,180,320,0,offset,w,h);
   ctx.restore();return true;
  }
  // Use native 60 Hz timing while actual map frames exist.
  const target=Math.max(first.frame,Math.min(last.frame,replayOrigin+stageTime*60));
  let i=Math.min(frames.length-1,Math.max(0,Math.floor((target-frames[0].frame)/16)));
  while(i<frames.length-1&&frames[i+1].frame<=target)i++;
  while(i>0&&frames[i].frame>target)i--;
  const a=frames[i],b=frames[Math.min(i+1,frames.length-1)];
  const f=a===b?0:Math.max(0,Math.min(1,(target-a.frame)/(b.frame-a.frame)));
  ctx.save();ctx.imageSmoothingEnabled=false;
  // Original portrait, same central crop; interpolate both scroll registers.
  drawLayer(ctx,i,0,f,w,h);
  drawLayer(ctx,i,1,f,w,h);
  ctx.restore();return true;
 }
 function ready(){return !!frames&&!!window.StrikersROM?.isLoaded()}
 window.StrikersOriginalStage1=Object.freeze({ensure,ready,prepareMaps,draw,preview:()=>frames?frameCanvas(5):null,
  info:()=>({frames:frames?.length??0,mapAudit,originalWidth:224,originalHeight:320,firstFrame:frames?.[0]?.frame??null,lastFrame:frames?.at(-1)?.frame??null,recordedSeconds:frames?(frames.at(-1).frame-frames[0].frame)/60:null,layerInterpolation:'independent-scroll-registers',extrapolation:'repeated-last-capture',firstFlightFrame:window.StrikersFlightReplay?.stats()?.earliestFrame??null,stage:1})});
 window.addEventListener('strikers-rom-ready',()=>{mapAudit=null;cache.clear();tileCache.clear();terminalCanvas=null;if(frames)for(const f of frames)f.nativeCodes=null;ensure()});
 if(window.StrikersROM?.isLoaded())ensure();
})();