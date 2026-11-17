/* Strikers 1945 / Psikyo exact 4bpp graphic tile decoder.
   Matches MAME's gfx_16x16x4_packed_msb and ROM_LOAD16_WORD_SWAP.
   This is NOT an emulator: stage tilemaps and xRGB555 palette live in running hardware RAM.
   Never guess an "original background" by shuffling tile graphics.
   All ROM bytes are provided by the player via local File API; never uploaded. */
(()=>{'use strict';
const W=16,H=16,TILE_BYTES=128,NAME_ORDER=['u20.bin','u22.bin','u21.bin','u23.bin'];
const SIZES={'2s.u40':262144,'3s.u41':262144,'3-u63.bin':131072,'u34.bin':2097152,'u20.bin':2097152,'u22.bin':2097152,'u21.bin':2097152,'u23.bin':2097152,'u1.bin':262144,'u61.bin':2097152};
const expectedCRC={'2s.u40':0x9b10062a,'3s.u41':0xf87e871a,'3-u63.bin':0x42d40ae1,'u34.bin':0xaaf83e23,'u20.bin':0x28a27fee,'u22.bin':0xca152a32,'u21.bin':0xc5d60ea9,'u23.bin':0x48710332,'u1.bin':0xdee22654,'u61.bin':0xa839cf47};
const DB='strikers1945-native-all10-v1',STORE='rom',KEY='original-all10';
let gfx=null,sprites=null,lut=null,verified=false,assetSheets=new Map(),restoring=null;
let program=null,soundProgram=null,opl4Samples=null,resetPC=null,resetSP=null;
let waveTable=[];
const CRC_TABLE=Uint32Array.from({length:256},(_,n)=>{let c=n;for(let j=0;j<8;j++)c=(c&1)?(0xedb88320^(c>>>1)):(c>>>1);return c>>>0});
function crc32(bytes){let c=0xffffffff;for(let i=0;i<bytes.length;i++)c=CRC_TABLE[(c^bytes[i])&255]^(c>>>8);return (c^0xffffffff)>>>0}
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
  const computed=crc32(decoded);
  names[name]={bytes:decoded,crcMatch:computed===expectedCRC[name],archiveCrcMatch:crc===computed};
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
 const low=parts['2s.u40'],high=parts['3s.u41'];
 if(!low||!high||!parts['3-u63.bin']||!parts['u61.bin'])throw Error('Falta o programa principal, programa de som ou amostras originais.');
 // MAME ROM_LOAD32_WORD_SWAP: 2s.u40 occupies byte lanes 0/1, 3s.u41 lanes 2/3.
 // The original native 68EC020 program begins with reset SP=0xffff8000 and PC=0x00000400.
 program=new Uint8Array(0x100000);program.fill(0xff);
 for(let i=0;i<low.length;i+=2){const at=(i>>1)*4;program[at]=low[i+1];program[at+1]=low[i];program[at+2]=high[i+1];program[at+3]=high[i]}
 soundProgram=parts['3-u63.bin'];opl4Samples=parts['u61.bin'];
 waveTable=parseOpl4Waves(opl4Samples);
 const dv=new DataView(program.buffer);resetSP=dv.getUint32(0);resetPC=dv.getUint32(4);
 verified=!!parts.__verified;
 assetSheets.clear();
 dispatchEvent(new CustomEvent('strikers-rom-ready',{detail:{restored:isRestore,verified}}));
}
// Yamaha YMF278B sample ROM: wave headers are 12 bytes, 256 slots occupy 0x0C00 bytes.
// This is a native PCM decoder for auditing original recorded waveforms, not a chip emulator.
// The original Z80/LZ8420M program still has to be analysed for note timing/pitch/envelopes.
function parseOpl4Waves(rom){
 const waves=[];
 if(!rom||rom.byteLength<0x0c00)return waves;
 for(let id=0;id<256;id++){
  const o=id*12,format=rom[o]>>>6,offset=((rom[o]&63)<<16)|(rom[o+1]<<8)|rom[o+2],
   loop=(rom[o+3]<<8)|rom[o+4],
   length=0x10000-((rom[o+5]<<8)|rom[o+6]);
  // The supplied 1995 Strikers bank uses 8-bit signed PCM; reject blank headers.
  if(format!==0||offset<0x0c00||length<1||offset+length>rom.length||loop>=length)continue;
  waves.push(Object.freeze({id,offset,length,loop,bits:8}));
 }
 return waves;
}
function pcmWave(id){
 if(!Number.isInteger(id)||!opl4Samples)return null;
 const info=waveTable.find(w=>w.id===id);
 if(!info)return null;
 const samples=new Float32Array(info.length);
 for(let i=0;i<info.length;i++){
  const n=opl4Samples[info.offset+i];
  samples[i]=(n<128?n:n-256)/128;
 }
 return samples;
}
function waveInfos(){return waveTable.map(w=>({...w}))}
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
 const p={__verified:Object.values(all).every(x=>x.crcMatch&&x.archiveCrcMatch)};
 for(const name of Object.keys(SIZES))p[name]=all[name].bytes;
 activate(p,false);
 let saved=false;
 try{await saveLocal(p);saved=true}catch(e){}
 return '10/10 ROMs importadas: programa original 68EC020, programa de som, 2 MiB de amostras OPL4, 16 384 tiles de cenário, 65 536 tiles de sprite e 131 072 entradas LUT. '+
  (p.__verified?'Todos os CRC32 originais confirmados. ':'Atenção: CRC de pelo menos uma ROM não corresponde à versão World. ')+
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
 isVerified:()=>verified,stats:()=>({chips:program?10:0,tiles:16384,spriteTiles:65536,lutEntries:131072,programBytes:program?.length||0,soundProgramBytes:soundProgram?.length||0,opl4SampleBytes:opl4Samples?.length||0,opl4WaveCount:waveTable.length,resetPC,resetSP,undumpedMCU:'4-u59.bin',verified}),
 // Full-code audit via local ROM, not CPU emulation. The PIC16C57 security chip (4-u59.bin) was never dumped.
 codeInfo:()=>program?Object.freeze({architecture:'68EC020',resetPC,resetSP,programBytes:program.length,soundCpu:'Z80 compatible',soundProgramBytes:soundProgram.length,opl4Bytes:opl4Samples.length,protectionMcuDumped:false}):null,
 programWord:addr=>program&&Number.isInteger(addr)&&addr>=0&&addr+1<program.length?(program[addr]<<8)|program[addr+1]:null,
 opl4WaveInfo:waveInfos,
 opl4PCM:pcmWave,
 soundBytes:(start=0,length=16)=>soundProgram?Array.from(soundProgram.slice(Math.max(0,start|0),Math.max(0,start|0)+Math.max(0,Math.min(64,length|0)))):[],
 // Test-only introspection to validate nibble and endianness against actual ROM fixtures.
 test:(location.hostname==='127.0.0.1'||location.hostname==='localhost')?Object.freeze({lookup:i=>lut?.[i],pixel:(k,id,x,y,byLut)=>pixel(k,id,x,y,byLut)}):undefined
});
restoring=restore();restoring.catch(()=>{});
})();