/* Native sprite descriptor table from the verified World 68EC020 program.
 * 0x01f9c4..0x0242cb: 4674 four-byte descriptors. No ROM bytes embedded.
 * size bits 8..10 and 12..14 are hardware tile extents minus one;
 * bit 0 extends tile ID to 17 bits. Portrait rotation is counterclockwise.
 */
(()=>{'use strict';
const START=0x1f9c4,END=0x242cc,COUNT=(END-START)/4;
let cached=null;
function read(){
 const rom=window.StrikersROM;
 if(!rom?.isVerified?.()||!rom.codeInfo?.())return null;
 if(cached)return cached;
 const list=[],byId=new Map();
 let expected=0;
 for(let address=START;address<END;address+=4){
  const word=rom.programWord(address),lo=rom.programWord(address+2);
  if(!Number.isInteger(word)||!Number.isInteger(lo)||(word&~0x7701)!==0)throw Error('Invalid original sprite descriptor at '+address.toString(16));
  const code=lo|((word&1)<<16),nx=((word>>8)&7)+1,ny=((word>>12)&7)+1;
  if(code!==expected||code+nx*ny>131072)throw Error('Noncontiguous ROM sprite table at '+address.toString(16));
  const entry=Object.freeze({address,code,nx,ny,width:ny*16,height:nx*16});
  list.push(entry);byId.set(code,entry);expected+=nx*ny;
 }
 if(list.length!==4674||expected!==94689)throw Error('Unexpected sprite table bounds');
 cached={list:Object.freeze(list),byId,covered:expected};return cached;
}
function get(code){return read()?.byId.get(code)??null}
function all(){return read()?.list??[]}
// One native compositor for all sprite families and captured display records.
 // Hardware flips are applied before portrait rotation. A caller may pass the
 // actual captured extents even when they differ from the static descriptor.
function drawRaw(canvas,code,nx,ny,palette,flipX=false,flipY=false,projection=false){
 const rom=window.StrikersROM;
 if(!rom?.isVerified?.()||!Number.isInteger(code)||code<0||code>=131072||
 !Number.isInteger(nx)||!Number.isInteger(ny)||nx<1||nx>8||ny<1||ny>8)return false;
 if(palette&&palette.length!==16)throw Error('Expected 16 palette words');
 const width=ny*16,height=nx*16;
 canvas.width=width;canvas.height=height;
 const c=canvas.getContext('2d'),im=c.createImageData(width,height),out=im.data;
 for(let y=0;y<ny;y++)for(let x=0;x<nx;x++)for(let py=0;py<16;py++)for(let px=0;px<16;px++){
  const id=(code+y*nx+x)&131071;
  const pen=rom.rawPixel('sprite',id,px,py,true);
  if(pen===15)continue;
  const xx=x*16+px,yy=y*16+py;
  const u=flipY?width-1-yy:yy,v=flipX?xx:height-1-xx,i=(v*width+u)*4;
  const rgb=projection?0:palette?.[pen],g=Math.min(255,50+pen*14);
  out[i]=projection||palette?((rgb>>10)&31)*255/31|0:g;
  out[i+1]=projection||palette?((rgb>>5)&31)*255/31|0:g;
  out[i+2]=projection||palette?(rgb&31)*255/31|0:g;
  out[i+3]=255;
 }
 c.putImageData(im,0,0);return true;
}
function draw(canvas,code,palette){
 const e=get(code);
 return !!e&&drawRaw(canvas,code,e.nx,e.ny,palette);
}
// Palette block immediately preceding sprite descriptor table. 516 sequential
// 16-word records; the first fifteen words carry RGB555 colours. Word 15
// is not drawn (transparent sprite pen). Palette roles still need verification.
const PALETTE_START=0x1b944,PALETTE_END=0x1f9c4,PALETTE_COUNT=(PALETTE_END-PALETTE_START)/32;
function palette(index){
 if(!Number.isInteger(index)||index<0||index>=PALETTE_COUNT||!window.StrikersROM?.isVerified?.())return null;
 const words=Array.from({length:16},(_,i)=>window.StrikersROM.programWord(PALETTE_START+index*32+2*i));
 return words.every(Number.isInteger)?Object.freeze(words):null;
}
// 16-entry palette pointer list, referenced four times in the ROM.
 // The addresses are proven; their on-screen roles are not yet identified.
const PALETTE_POINTER_TABLE=0x59654;
function palettePointerList(){
 const rom=window.StrikersROM;
 if(!rom?.isVerified?.())return null;
 return Object.freeze(Array.from({length:16},(_,i)=>{
  const a=PALETTE_POINTER_TABLE+i*4,p=rom.programWord(a)*65536+rom.programWord(a+2);
  const index=(p-PALETTE_START)/32;
  if(!Number.isInteger(index)||index<0||index>=PALETTE_COUNT)throw Error('Invalid original palette pointer '+i);
  return Object.freeze({slot:i,address:p,index});
 }));
}
// Sixteen 32-slot tables at 0x58ad8..0x592d7. Table 4 matches the
// captured first-stage banks 11,12,23,25,27,29,31 at every visible pen.
// Slots 1..7 point to identical palette records in all sixteen tables.
// Retain unresolved pointers and sentinel slots; never turn them into colors.
const STAGE_PALETTE_TABLE=0x58ad8,STAGE_PALETTE_COUNT=16;
let stageTables=null;
function stagePaletteTables(){
 const rom=window.StrikersROM;
 if(!rom?.isVerified?.())return null;
 if(stageTables)return stageTables;
 const result=[];
 for(let table=0;table<STAGE_PALETTE_COUNT;table++){
  const slots=[];
  for(let bank=0;bank<32;bank++){
   const a=STAGE_PALETTE_TABLE+table*128+bank*4;
   const ptr=rom.programWord(a)*65536+rom.programWord(a+2);
   const n=(ptr-PALETTE_START)/32;
   const index=Number.isInteger(n)&&n>=0&&n<PALETTE_COUNT?n:null;
   if(bank>=1&&bank<=7&&index!==15+bank)throw Error('Common sprite palette table mismatch');
   slots.push(Object.freeze({bank,address:ptr,index,status:index!==null?'record':ptr===0xffffffff?'unused':'unresolved'}));
  }
  result.push(Object.freeze(slots));
 }
 const reference={11:30,12:31,23:75,25:73,27:71,28:70,29:69,31:67};
 for(const [bank,index] of Object.entries(reference))if(result[4][bank].index!==index)throw Error('Captured palette table mismatch');
 stageTables=Object.freeze(result);return stageTables;
}
function commonSpritePalette(bank){
 if(!Number.isInteger(bank)||bank<1||bank>7)return null;
 const tables=stagePaletteTables();if(!tables)return null;
 return palette(tables[0][bank].index);
}
function stagePalette(table,bank){
 const record=stagePaletteTables()?.[table]?.[bank];
 return record?.index!=null?palette(record.index):null;
}
window.addEventListener('strikers-rom-ready',()=>{stageTables=null});

function stats(){const a=read();return {count:a?.list.length??0,covered:a?.covered??0,expected:COUNT,start:START,endExclusive:END,paletteCount:PALETTE_COUNT,paletteStart:PALETTE_START}}
window.addEventListener('strikers-rom-ready',()=>{cached=null});
window.StrikersSpriteDefinitions=Object.freeze({all,get,draw,drawRaw,palette,palettePointerList,stagePaletteTables,commonSpritePalette,stagePalette,stats});
})();