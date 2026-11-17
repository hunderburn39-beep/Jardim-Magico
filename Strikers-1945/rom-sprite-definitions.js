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
function draw(canvas,code,palette){
 const e=get(code),rom=window.StrikersROM;
 if(!e)return false;
 // Palette words are optional; grayscale preview is not original RGB data.
 if(palette&&palette.length!==16)throw Error('Expected 16 palette words');
 canvas.width=e.width;canvas.height=e.height;
 const c=canvas.getContext('2d'),im=c.createImageData(e.width,e.height);
 for(let y=0;y<e.ny;y++)for(let x=0;x<e.nx;x++)for(let py=0;py<16;py++)for(let px=0;px<16;px++){
  const pen=rom.rawPixel('sprite',code+y*e.nx+x,px,py,true);
  if(pen===15)continue;
  const u=y*16+py,v=e.height-1-(x*16+px),i=(v*e.width+u)*4;
  const rgb=palette?.[pen],g=Math.min(255,50+pen*14);
  im.data[i]=palette?((rgb>>10)&31)*255/31:g;
  im.data[i+1]=palette?((rgb>>5)&31)*255/31:g;
  im.data[i+2]=palette?(rgb&31)*255/31:g;
  im.data[i+3]=255;
 }
 c.putImageData(im,0,0);return true;
}
function stats(){const a=read();return {count:a?.list.length??0,covered:a?.covered??0,expected:COUNT,start:START,endExclusive:END}}
window.addEventListener('strikers-rom-ready',()=>{cached=null});
window.StrikersSpriteDefinitions=Object.freeze({all,get,draw,stats});
})();