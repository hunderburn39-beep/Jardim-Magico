/* Strikers 1945 / Psikyo exact 4bpp graphic tile decoder.
   Matches MAME's gfx_16x16x4_packed_msb and ROM_LOAD16_WORD_SWAP.
   This is NOT an emulator: stage tilemaps and xRGB555 palette live in running hardware RAM.
   Never guess an "original background" by shuffling tile graphics.
   All ROM bytes are provided by the player via local File API; never uploaded. */
(()=>{'use strict';
const W=16,H=16,TILE_BYTES=128,NAME_ORDER=['u20.bin','u22.bin','u21.bin','u23.bin'];
const SIZES={'u34.bin':2097152,'u20.bin':2097152,'u22.bin':2097152,'u21.bin':2097152,'u23.bin':2097152,'u1.bin':262144};
const expectedCRC={'u34.bin':0xaaf83e23,'u20.bin':0x28a27fee,'u22.bin':0xca152a32,'u21.bin':0xc5d60ea9,'u23.bin':0x48710332,'u1.bin':0xdee22654};
const DB='strikers1945-graphics-decoder-v2',STORE='rom',KEY='original-graphics';
let gfx=null,sprites=null,lut=null,verified=false,assetSheets=new Map(),restoring=null;
function storage(){
 return new Promise((res,rej)=>{
  if(!indexedDB)return rej(Error('Armazenamento indisponível'));
  const req=indexedDB.open(DB,1);
  req.onupgradeneeded=()=>{if(!req.result.objectStoreNames.contains(STORE))req.result.createObjectStore(STORE)};
  req.onerror=()=>rej(req.error);
  req.onsuccess=()=>res(req.result);
 });
}
async function saveLocal(d){
 const db=await storage();
 try{await new Promise((res,rej)=>{
  const t=db.transaction(STORE,'readwrite');t.objectStore(STORE).put(d,KEY);
  t.oncomplete=res;t.onerror=()=>rej(t.error);t.onabort=()=>rej(t.error);
 })}finally{db.close()}
}
async function readLocal(){
 const db=await storage();
 try{return await new Promise((res,rej)=>{
  const t=db.transaction(STORE,'readonly'),req=t.objectStore(STORE).get(KEY);
  req.onsuccess=()=>res(req.result);req.onerror=()=>rej(req.error);
 })}finally{db.close()}
}
async function zipEntries(file){
 const a=new Uint8Array(await file.arrayBuffer()),v=new DataView(a.buffer),names={},decoder=new TextDecoder();
 let end=-1;
 for(let i=a.length-22;i>=Math.max(0,a.length-65557);i--)if(v.getUint32(i,true)===0x06054b50){end=i;break}
 if(end<0)throw Error('Não encontrei a estrutura ZIP.');
 const total=v.getUint16(end+10,true);let pos=v.getUint32(end+16,true);
 for(let k=0;k<total;k++){
  if(pos+46>a.length||v.getUint32(pos,true)!==0x02014b50)throw Error('Índice ZIP inválido.');
  const method=v.getUint16(pos+10,true),crc=v.getUint32(pos+16,true),
   comp=v.getUint32(pos+20,true),size=v.getUint32(pos+24,true),
   nl=v.getUint16(pos+28,true),extra=v.getUint16(pos+30,true),comment=v.getUint16(pos+32,true),head=v.getUint32(pos+42,true);
  const name=decoder.decode(a.subarray(pos+46,pos+46+nl)).split('/').pop().toLowerCase();
  pos+=46+nl+extra+comment;
  if(!SIZES[name])continue;
  if(size!==SIZES[name])throw Error(name+' tem tamanho inválido.');
  if(head+30>a.length||v.getUint32(head,true)!==0x04034b50)throw Error(name+' tem cabeçalho inválido.');
  const start=head+30+v.getUint16(head+26,true)+v.getUint16(head+28,true);
  if(start+comp>a.length)throw Error(name+' está incompleto.');
  const compressed=a.subarray(start,start+comp);
  let decoded;
  if(method===0)decoded=compressed.slice();
  else if(method===8){
   if(!globalThis.DecompressionStream)throw Error('Safari sem suporte ao formato ZIP.');
   decoded=new Uint8Array(await new Response(new Blob([compressed]).stream().pipeThrough(new DecompressionStream('deflate-raw'))).arrayBuffer());
  }else throw Error('Método ZIP não suportado em '+name);
  if(decoded.length!==size)throw Error(name+' não descomprimiu corretamente.');
  names[name]={bytes:decoded,crcMatch:crc===expectedCRC[name]};
 }
 for(const name of Object.keys(SIZES))if(!names[name])throw Error('Falta '+name+' na ROM. Escolhe s1945.zip completo.');
 return names;
}
function activate(parts,isRestore){
 const t=parts['u34.bin'];
 if(!t||t.length!==SIZES['u34.bin'])throw Error('Gráficos de cenário incompletos.');
 const spriteParts=NAME_ORDER.map(name=>parts[name]);
 for(let i=0;i<NAME_ORDER.length;i++)if(!spriteParts[i]||spriteParts[i].length!==SIZES[NAME_ORDER[i]])throw Error('Sprites incompletos: '+NAME_ORDER[i]);
 if(!parts['u1.bin']||parts['u1.bin'].length!==SIZES['u1.bin'])throw Error('Falta tabela u1.bin.');
 gfx=t;
 sprites=new Uint8Array(8388608);
 for(let i=0;i<4;i++)sprites.set(spriteParts[i],i*2097152);
 // u1.bin is ROM_REGION16_LE; little-endian lookup of 131072 sprite tile indexes.
 const d=parts['u1.bin'];
 lut=new Uint16Array(131072);
 for(let i=0;i<lut.length;i++)lut[i]=d[i*2]|(d[i*2+1]<<8);
 verified=!!parts.__verified;
 assetSheets.clear();
 dispatchEvent(new CustomEvent('strikers-rom-ready',{detail:{restored:isRestore,verified}}));
}
function rawPixel(raw,tileId,x,y){
 if(!raw||!Number.isInteger(tileId)||tileId<0||tileId>=raw.length/TILE_BYTES||x<0||x>15||y<0||y>15)return 0;
 // MAME: 16*16x4 packed MSB (high nibble first), ROM_LOAD16_WORD_SWAP (bytes reversed each 16-bit word).
 const byte=raw[((tileId*TILE_BYTES)+(y<<3)+(x>>1))^1];
 return (x&1)?(byte&15):(byte>>4);
}
function pixel(kind,id,x,y,lookup=false){
 if(kind==='tile')return rawPixel(gfx,id,x,y);
 return rawPixel(sprites,lookup?lut[id&0x1ffff]:id,x,y);
}
function colors(palette='gray'){
 const rgb=new Uint8Array(16*4);
 const shades=['#13202a','#26333c','#354951','#435c67','#52717a','#62878b','#74999d','#86aaab','#96b8b7','#acc7c4','#bad3cb','#cdded6','#dbe7dc','#e8efe8','#f5f8f2','#ffffff'];
 const opts=palette==='gray'?Array.from({length:16},(_,i)=>[i*17,i*17,i*17]):shades.map(x=>[parseInt(x.slice(1,3),16),parseInt(x.slice(3,5),16),parseInt(x.slice(5,7),16)]);
 for(let i=0;i<16;i++)for(let j=0;j<3;j++)rgb[i*4+j]=opts[i][j];
 for(let i=0;i<16;i++)rgb[i*4+3]=255;
 return rgb;
}
function atlas(kind,page=0,{columns=16,rows=8,scale=2,palette='gray',transparent=15,lookup=true}={}){
 if(!gfx)return null;
 const sprite=kind==='sprite';
 const w=Math.round(columns*16*scale),h=Math.round(rows*16*scale);
 const canvas=document.createElement('canvas');canvas.width=w;canvas.height=h;
 const c=canvas.getContext('2d',{alpha:false});
 const img=c.createImageData(w,h),o=img.data,cols=colors(palette);
 const bank=sprite?sprites:gfx;
 const total=sprite?lut.length:gfx.length/TILE_BYTES;
 const base=page*columns*rows;
 for(let k=0;k<columns*rows;k++){
  const code=base+k;if(code>=total)break;
  const idx=sprite&&lookup?lut[code&0x1ffff]:code;
  for(let py=0;py<16;py++)for(let px=0;px<16;px++){
   const n=rawPixel(bank,idx,px,py);
   const t0=k%columns*16*scale+px*scale,u0=Math.floor(k/columns)*16*scale+py*scale;
   // Grayscale diagnostic (not an arcade hardware palette).
   const cc=n===transparent?0:n*4;
   for(let dy=0;dy<scale;dy++)for(let dx=0;dx<scale;dx++){
    const q=((u0+dy)*w+t0+dx)*4;
    if(n===transparent){o[q]=16;o[q+1]=34;o[q+2]=44;}
    else{o[q]=cols[cc];o[q+1]=cols[cc+1];o[q+2]=cols[cc+2];}
    o[q+3]=255;
   }
  }
 }
 c.putImageData(img,0,0);return canvas;
}
function spriteGroup(start=0,nx=4,ny=4,{scale=3,palette='gray',transparent=15}={}){
 if(!sprites)return null;
 nx=Math.max(1,Math.min(8,nx));ny=Math.max(1,Math.min(8,ny));
 const c=document.createElement('canvas');c.width=nx*16*scale;c.height=ny*16*scale;
 const ctx=c.getContext('2d',{alpha:false}),data=ctx.createImageData(c.width,c.height),out=data.data,cols=colors(palette);
 for(let y=0;y<ny*16;y++)for(let x=0;x<nx*16;x++){
  const code=start+Math.floor(x/16)+Math.floor(y/16)*nx,
   i=lut[code&0x1ffff],p=rawPixel(sprites,i,x%16,y%16);
  for(let dy=0;dy<scale;dy++)for(let dx=0;dx<scale;dx++){
   const offset=(((y*scale)+dy)*c.width+x*scale+dx)*4;
   out[offset]=p===transparent?16:cols[p*4];
   out[offset+1]=p===transparent?34:cols[p*4+1];
   out[offset+2]=p===transparent?44:cols[p*4+2];
   out[offset+3]=255;
  }
 }
 ctx.putImageData(data,0,0);return c;
}
async function load(file){
 if(!file)throw Error('Escolhe o ficheiro s1945.zip.');
 const all=await zipEntries(file);
 const p={__verified:Object.values(all).every(x=>x.crcMatch)};
 for(const name of Object.keys(SIZES))p[name]=all[name].bytes;
 activate(p,false);
 let saved=false;
 try{await saveLocal(p);saved=true}catch(e){}
 return 'ROM interpretada: 16 384 tiles de fundo, 65 536 tiles de sprite, 131 072 entradas LUT. '+
  (p.__verified?'CRC correspondente à versão World. ':'CRC da versão não confirmado. ')+
  (saved?'Guardada neste iPhone.':'Importada apenas nesta sessão.');
}
async function restore(){
 try{
  const p=await readLocal();if(!p)return false;
  activate(p,true);return true;
 }catch(e){return false}
}
// No "original" backgrounds without actual tilemap VRAM, palette RAM and scroll register snapshots.
function scene(){return null}
window.StrikersROM=Object.freeze({
 load,restore,scene,rawPixel:pixel,atlas,spriteGroup,isLoaded:()=>!!gfx,
 isVerified:()=>verified,stats:()=>({tiles:16384,spriteTiles:65536,lutEntries:131072,verified}),
 // Test-only introspection to validate nibble and endianness against actual ROM fixtures.
 test:(location.hostname==='127.0.0.1'||location.hostname==='localhost')?Object.freeze({lookup:i=>lut?.[i],pixel:(k,id,x,y,byLut)=>pixel(k,id,x,y,byLut)}):undefined
});
restoring=restore();restoring.catch(()=>{});
})();