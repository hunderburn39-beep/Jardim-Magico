/* Strikers 1945: frame-register-based catalog, NOT a CPU emulator.
 * 116 captured MAME moments; 17-bit sprite code includes attr bit 0.
 * Graphics come from the user's privately loaded ROM.
 * Classes are size-only visual groups, not proven game roles. */
(()=>{'use strict';
const DATA='./stage1-plane-frames.b64',SUPPORTED=new Set([10,11,12,23,25,27,28]);
let loaded=false,job=null,groups=null,items=null,moments=0,rawItems=0,frameFirst=0,frameLast=0;
function dimensions(rx,ry){
 // EP1's six non-square graphics store their tile extents transposed.
 // Verified on the supplied original ROM: 21691/21715 need 3×8,
 // and 21769/21781/21787/21811 need 2×3 in the row-major renderer.
 const nx=((ry>>9)&7)+1,ny=((rx>>9)&7)+1;
 return {nx,ny};
}
function classOf(e){
 const m=Math.max(e.nx,e.ny);
 if(m>=5)return 'grandes';
 if(m>=3)return 'medios';
 return 'pequenos';
}
async function prepare(){
 if(!globalThis.DecompressionStream)throw Error('O Safari não suporta descompressão desta captura.');
 const resp=await fetch(DATA,{cache:'force-cache'});if(!resp.ok)throw Error('Não encontrei os registos da primeira fase.');
 const binary=atob((await resp.text()).trim()),compressed=Uint8Array.from(binary,c=>c.charCodeAt(0));
 const raw=new Uint8Array(await new Response(new Blob([compressed]).stream().pipeThrough(new DecompressionStream('deflate'))).arrayBuffer());
 const dv=new DataView(raw.buffer,raw.byteOffset,raw.byteLength);
 if(raw.length<30||String.fromCharCode(...raw.subarray(0,3))!=='EP1'||raw[3]!==1)throw Error('Captura inválida.');
 const count=dv.getUint16(4),palCount=raw[6],banks=[...raw.subarray(7,7+palCount)];
 if(count!==116||palCount!==7)throw Error('Captura de referência incompleta.');
 let off=7+palCount;groups=new Map();
 for(const bank of banks){
   const phases=[];
   for(let p=0;p<3;p++){const colors=new Uint16Array(16);for(let i=0;i<16;i++){colors[i]=dv.getUint16(off);off+=2}phases.push(colors)}
   groups.set(bank,phases);
 }
 const unique=new Map();moments=count;rawItems=0;
 for(let f=0;f<count;f++){
   if(off+4>raw.length)throw Error('Fotograma incompleto.');
   const frame=dv.getUint16(off),phase=raw[off+2],n=raw[off+3];off+=4;
   if(f===0)frameFirst=frame;
   frameLast=frame;
   for(let k=0;k<n;k++){
    if(off+9>raw.length)throw Error('Registo de sprite incompleto.');
    const id=raw[off],rx=dv.getUint16(off+1),ry=dv.getUint16(off+3),attr=dv.getUint16(off+5),code=dv.getUint16(off+7)|((attr&1)<<16);off+=9;
    const palette=(attr>>8)&63;
    if(!SUPPORTED.has(palette))continue;
    const dims=dimensions(rx,ry);
    const key=[code,attr,dims.nx,dims.ny,palette,phase].join(':');
    rawItems++;
    if(unique.has(key)){const old=unique.get(key);old.occurrences++;old.lastFrame=frame;continue}
    const e={id,code,attr,palette,phase,nx:dims.nx,ny:dims.ny,firstFrame:frame,lastFrame:frame,occurrences:1};
    e.group=classOf(e);unique.set(key,e);
   }
 }
 if(off!==raw.length)throw Error('Dados adicionais não reconhecidos na captura.');
 items=[...unique.values()].sort((a,b)=>a.firstFrame-b.firstFrame||a.code-b.code);
 loaded=true;return true;
}
function ensure(){if(loaded)return Promise.resolve(true);if(job)return job;job=prepare().catch(e=>{job=null;throw e});return job}
function records(group='todos'){return (items||[]).filter(e=>group==='todos'||e.group===group).map(e=>({...e}))}
function stats(){return {ready:loaded,moments,rawItems,uniqueEntries:items?.length||0,frameFirst,frameLast,seconds:(frameLast-frameFirst)/60,groups:{pequenos:items?.filter(e=>e.group==='pequenos').length||0,medios:items?.filter(e=>e.group==='medios').length||0,grandes:items?.filter(e=>e.group==='grandes').length||0}}}
function draw(canvas,e){
 if(!window.StrikersROM?.isLoaded())return false;
 const phases=groups?.get(e.palette);if(!phases)return false;
 const colors=phases[e.phase]||phases[0];
 const w=e.ny*16,h=e.nx*16;
 canvas.width=w;canvas.height=h;
 const cx=canvas.getContext('2d'),im=cx.createImageData(w,h),out=im.data;
 const flipX=!!(e.attr&0x4000),flipY=!!(e.attr&0x8000);
 let code=e.code;
 for(let dy=flipY?e.ny-1:0;flipY?dy>=0:dy<e.ny;dy+=flipY?-1:1){
  for(let dx=flipX?e.nx-1:0;flipX?dx>=0:dx<e.nx;dx+=flipX?-1:1){
   for(let py=0;py<16;py++)for(let px=0;px<16;px++){
    const u=flipX?15-px:px,v=flipY?15-py:py;
    const pix=window.StrikersROM.rawPixel('sprite',code,u,v,true);
    if(pix===15)continue;
    const rgb=colors[pix],x=dy*16+py,y=e.nx*16-1-(dx*16+px),off=(y*w+x)*4;
    out[off]=((rgb>>10)&31)*255/31|0;
    out[off+1]=((rgb>>5)&31)*255/31|0;
    out[off+2]=(rgb&31)*255/31|0;out[off+3]=255;
   }
   code=(code+1)&0x1ffff;
  }
 }
 cx.putImageData(im,0,0);return true;
}
function csv(group='todos'){
 const header=['code','attr','palette','phase','nx','ny','group_size','firstFrame','lastFrame','occurrences','id_first'];
 const lines=[header.join(',')];
 for(const e of records(group))lines.push([e.code,e.attr,e.palette,e.phase,e.nx,e.ny,e.group,e.firstFrame,e.lastFrame,e.occurrences,e.id].join(','));
 return lines.join('\n')+'\n';
}
window.StrikersStage1Catalog=Object.freeze({ensure,ready:()=>loaded,records,draw,stats,csv});
})();