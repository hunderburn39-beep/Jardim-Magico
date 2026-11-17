/* Original Strikers 1945 World ROM UI graphics.
 * Verified by visual inspection of u1 LUT entries 1430-1504 using the
 * user's verified ten-chip ROM. Pixel indices are original; the grayscale
 * preview palette is provisional until the title-screen palette is captured.
 */
(()=>{'use strict';
const blocks=Object.freeze({
 insertCoin:[1458,1459,1460,1461],
 gameOver:[1447,1448,1449,1450,1451],
 continuePrompt:[1471,1472,1473,1474,1475],
 credit:[1478,1479,1480],
 start:[1452,1453,1454],
 onePlayer:[1476],twoPlayer:[1477],
 copyright:[1485,1486,1487],
 digit0:[1495],digit1:[1496],digit2:[1497],digit3:[1498],digit4:[1499],
 digit5:[1500],digit6:[1501],digit7:[1502],digit8:[1503],digit9:[1504]
});
const palette=Array.from({length:16},(_,i)=>Math.min(255,50+i*14));
function draw(canvas,ids,scale=2){
 const rom=window.StrikersROM;
 if(!rom?.isLoaded()||!rom.isVerified?.()||!ids?.length)return false;
 canvas.width=ids.length*16;canvas.height=16;
 const c=canvas.getContext('2d');const im=c.createImageData(canvas.width,16);
 for(let i=0;i<ids.length;i++)for(let y=0;y<16;y++)for(let x=0;x<16;x++){
  // Hardware sprite tiles rotated 90 degrees into portrait coordinates.
  const pen=rom.rawPixel('sprite',ids[i],15-y,x,true);
  if(pen===15)continue;
  const p=(y*canvas.width+i*16+x)*4,v=palette[pen];
  im.data[p]=v;im.data[p+1]=v;im.data[p+2]=v;im.data[p+3]=255;
 }
 c.putImageData(im,0,0);canvas.style.width=(canvas.width*scale)+'px';
 canvas.style.height=(16*scale)+'px';canvas.style.imageRendering='pixelated';
 return true;
}
function digits(n){return String(Math.max(0,Math.min(99,Math.floor(Number(n)||0)))).padStart(2,'0').split('').map(c=>1495+Number(c));}
function update(credits){
 const prompt=document.getElementById('coinPrompt'),label=document.getElementById('titleCredits');
 if(!prompt||!label)return;
 for(const [el,ids,use] of [[prompt,blocks.insertCoin,credits===0],[label,[...blocks.credit,0,...digits(credits)],true]]){
  let c=el.querySelector('canvas');
  if(!c){c=document.createElement('canvas');c.setAttribute('aria-hidden','true');}
  if(use&&draw(c,ids)){el.setAttribute('aria-label',el.textContent|| (el===prompt?'INSERT COIN':'CREDIT '+credits));el.replaceChildren(c);}
 }
}
window.StrikersOriginalUI=Object.freeze({blocks,draw,digits,update,paletteStatus:'provisional grayscale'});
})();