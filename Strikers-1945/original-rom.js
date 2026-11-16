/* Optional, browser-local reconstruction from a user-supplied Psikyo s1945.zip.
 * The GitHub repository contains no original ROM bytes, sprites or backgrounds.
 * Tile arrangement and RGB palettes are experimental, NOT accurate arcade stages.
 */
(()=>{'use strict';
 const W=360,H=960,COLS=24,ROWS=60,TILE=16;
 const groups=[
  {name:'FLORESTA',start:6144,end:8192,stops:['#203535','#3b5548','#6b8671','#d7e1c3']},
  {name:'PORTO',start:8192,end:10240,stops:['#112d37','#466575','#87a4a3','#e1d8b5']},
  {name:'DESERTO',start:12288,end:14336,stops:['#493d2d','#897451','#c2a673','#fae0b5']},
  {name:'COSTA',start:10240,end:12288,stops:['#133644','#456478','#7daaa3','#d6dab2']},
  {name:'MONTANHA',start:0,end:2048,stops:['#243444','#587063','#92aa91','#dde8d6']},
  {name:'NOITE',start:14336,end:16384,stops:['#101d35','#344869','#668394','#c8cdbd']}
 ];
 let gfx=null,cache=new Map();
 // The user's selected graphic bytes remain on their device in IndexedDB.
 const DB='strikers1945-user-rom-art-v1', STORE='assets', KEY='u34';
 function openDb(){
  return new Promise((resolve,reject)=>{
   if(!globalThis.indexedDB)return reject(Error('IndexedDB não disponível'));
   const req=indexedDB.open(DB,1);
   req.onupgradeneeded=()=>{if(!req.result.objectStoreNames.contains(STORE))req.result.createObjectStore(STORE)};
   req.onsuccess=()=>resolve(req.result);req.onerror=()=>reject(req.error);
  });
 }
 async function saveLocal(a){
  const db=await openDb();
  try{await new Promise((resolve,reject)=>{
   const tx=db.transaction(STORE,'readwrite');
   tx.objectStore(STORE).put(a,KEY);
   tx.oncomplete=resolve;tx.onerror=()=>reject(tx.error);tx.onabort=()=>reject(tx.error);
  })}finally{db.close()}
 }
 async function readLocal(){
  const db=await openDb();
  try{return await new Promise((resolve,reject)=>{
   const tx=db.transaction(STORE,'readonly'),req=tx.objectStore(STORE).get(KEY);
   req.onsuccess=()=>resolve(req.result);req.onerror=()=>reject(req.error);
  })}finally{db.close()}
 }
 function activate(raw,restored=false){
  const bytes=raw instanceof Uint8Array?raw:new Uint8Array(raw);
  if(bytes.length!==2097152)throw Error('Os gráficos guardados têm tamanho inválido.');
  gfx=bytes;cache.clear();
  dispatchEvent(new CustomEvent('strikers-rom-ready',{detail:{restored}}));
 }
 async function restore(){
  try{
   const raw=await readLocal();
   if(!raw)return false;
   activate(raw,true);return true;
  }catch(e){return false}
 }

 const rgb=s=>[1,3,5].map(k=>parseInt(s.slice(k,k+2),16));
 function palette(stops){
  const v=stops.map(rgb),out=[];
  for(let i=0;i<16;i++){const f=i/5,k=Math.min(2,Math.floor(f)),t=f-k;out.push(v[k].map((n,j)=>Math.round(n*(1-t)+v[k+1][j]*t)))}
  return out;
 }
 function pixel(a,id,y,x){const p=(id<<7)+(y<<3)+(x>>1),v=a[p^1];return x&1?v&15:v>>4}
 function edgeMean(a,offset){let sum=0;for(let i=0;i<16;i++)sum+=a[offset+i];return sum/16}
 function scene(n){
  if(!gfx)return null;
  const stage=Math.max(1,Math.min(6,n)),g=groups[stage-1];
  if(cache.has(stage))return cache.get(stage);
  const count=g.end-g.start,left=new Uint8Array(count*16),right=new Uint8Array(count*16),top=new Uint8Array(count*16),bottom=new Uint8Array(count*16);
  const bucketsL=Array.from({length:17},()=>[]),bucketsT=Array.from({length:17},()=>[]);
  for(let i=0;i<count;i++){
   let ls=0,ts=0,b=i*16;
   for(let k=0;k<16;k++){
    const id=i+g.start,l=pixel(gfx,id,k,0),r=pixel(gfx,id,k,15),t=pixel(gfx,id,0,k),d=pixel(gfx,id,15,k);
    left[b+k]=l;right[b+k]=r;top[b+k]=t;bottom[b+k]=d;ls+=l;ts+=t;
   }
   bucketsL[Math.round(ls/16)].push(i);bucketsT[Math.round(ts/16)].push(i);
  }
  const map=new Uint16Array(COLS*ROWS),usage=new Uint8Array(count);
  let seed=(20261116+stage*451)>>>0;
  function rand(){seed=(Math.imul(seed,1664525)+1013904223)>>>0;return seed/4294967296}
  map[0]=Math.floor(rand()*count);usage[map[0]]=1;
  for(let y=0;y<ROWS;y++)for(let x=0;x<COLS;x++){
   if(!x&&!y)continue;
   const ind=y*COLS+x,prev=x?map[ind-1]:0,above=y?map[ind-COLS]:0;
   const b=Math.round(x?edgeMean(right,prev*16):edgeMean(bottom,above*16));
   const buckets=x?bucketsL:bucketsT,choices=[];
   for(let k=Math.max(0,b-2);k<=Math.min(16,b+2);k++)choices.push(...buckets[k]);
   const sample=Math.min(choices.length,270),offset=Math.floor(rand()*Math.max(1,choices.length)),step=Math.max(1,Math.floor(choices.length/sample));
   let best=-1,bc=Infinity,second=-1,sc=Infinity;
   for(let c=0;c<sample;c++){
    const t=choices[(offset+c*step)%choices.length],q=t*16;
    let cost=usage[t]*2.5;
    if(x){const z=prev*16;for(let i=0;i<16;i++){const d=left[q+i]-right[z+i];cost+=d*d/16}}
    if(y){const z=above*16;for(let i=0;i<16;i++){const d=top[q+i]-bottom[z+i];cost+=d*d/16}}
    cost+=rand()*.65;
    if(cost<bc){second=best;sc=bc;best=t;bc=cost}
    else if(cost<sc){second=t;sc=cost}
   }
   map[ind]=second>=0&&rand()>.86?second:best>=0?best:Math.floor(rand()*count);
   usage[map[ind]]=Math.min(255,usage[map[ind]]+1);
  }
  const color=palette(g.stops),off=document.createElement('canvas');off.width=COLS*TILE;off.height=H;
  const oc=off.getContext('2d',{alpha:false}),im=oc.createImageData(off.width,H),out=im.data;
  for(let row=0;row<ROWS;row++)for(let col=0;col<COLS;col++){
   const id=g.start+map[row*COLS+col];
   for(let yy=0;yy<TILE;yy++)for(let xx=0;xx<TILE;xx++){
    const p=((row*16+yy)*off.width+col*16+xx)*4,c=color[pixel(gfx,id,yy,xx)];
    out[p]=c[0];out[p+1]=c[1];out[p+2]=c[2];out[p+3]=255;
   }
  }
  oc.putImageData(im,0,0);
  const c=document.createElement('canvas');c.width=W;c.height=H;
  const ctx=c.getContext('2d',{alpha:false});ctx.imageSmoothingEnabled=false;ctx.drawImage(off,-12,0);
  cache.set(stage,c);return c;
 }
 async function zipMember(file,target){
  const raw=new Uint8Array(await file.arrayBuffer()),v=new DataView(raw.buffer),d=new TextDecoder();
  let e=-1;for(let i=raw.length-22;i>=Math.max(0,raw.length-66000);i--)if(v.getUint32(i,true)===0x06054b50){e=i;break}
  if(e<0)throw Error('Arquivo ZIP inválido.');
  const n=v.getUint16(e+10,true);let pos=v.getUint32(e+16,true);
  for(let i=0;i<n;i++){
   if(v.getUint32(pos,true)!==0x02014b50)throw Error('Índice ZIP inválido.');
   const method=v.getUint16(pos+10,true),compressed=v.getUint32(pos+20,true),length=v.getUint32(pos+24,true);
   const nl=v.getUint16(pos+28,true),extra=v.getUint16(pos+30,true),comment=v.getUint16(pos+32,true),lh=v.getUint32(pos+42,true);
   const name=d.decode(raw.subarray(pos+46,pos+46+nl));
   pos+=46+nl+extra+comment;
   if(name.split('/').pop().toLowerCase()!==target.toLowerCase())continue;
   if(length>16*1024*1024||v.getUint32(lh,true)!==0x04034b50)throw Error('Entrada ROM inválida.');
   const start=lh+30+v.getUint16(lh+26,true)+v.getUint16(lh+28,true),blob=raw.subarray(start,start+compressed);
   if(method===0)return blob.slice();
   if(method===8){
    if(!globalThis.DecompressionStream)throw Error('Este navegador não suporta ZIP.');
    const result=new Uint8Array(await new Response(new Blob([blob]).stream().pipeThrough(new DecompressionStream('deflate-raw'))).arrayBuffer());
    if(result.length!==length)throw Error('Erro ao descomprimir os gráficos.');
    return result;
   }
   throw Error('Compressão ZIP incompatível.');
  }
  throw Error('Não encontrei u34.bin. Escolhe o s1945.zip original.');
 }
 async function load(file){
  if(!file)throw Error('Seleciona s1945.zip.');
  const raw=await zipMember(file,'u34.bin');
  if(raw.length!==2097152)throw Error('A ROM tem gráficos de tamanho inesperado.');
  activate(raw,false);
  await new Promise(r=>setTimeout(r,10));scene(1);
  let saved=false;
  try{await saveLocal(raw);saved=true}catch(e){}
  return saved?'Blocos da ROM carregados e guardados neste iPhone.':'Blocos da ROM carregados. O Safari não permitiu guardar para a próxima visita.';
 }
 window.StrikersROM=Object.freeze({load,scene,isLoaded:()=>!!gfx,restore});
 // Restore the asset automatically on subsequent visits, without asking for the ZIP again.
 restore().catch(()=>{});
})();