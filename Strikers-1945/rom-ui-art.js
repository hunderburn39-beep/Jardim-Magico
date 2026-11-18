/* V9.1: UI artwork decoded from the World ROM's native sprite descriptors.
 * The code and dimensions are verified; screen palette assignment is pending.
 * No ROM pixels are stored in this file.
 */
(()=>{'use strict';
const codes=Object.freeze({
 insertCoin:1458,gameOver:1447,largeGameOver:1439,continuePrompt:1471,
 credit:1478,start:1452,onePlayer:1476,twoPlayer:1477,copyright:1485,publisherTail:1493,
 addCoin:1463,freePlay:1467,ending:1505,warning:1569,select:1575,
 cursor1:1594,cursor2:1602,pleaseWait:1581,yes:1586,no:1588,time:1592,powerUp:1665,powerDown:1669
});
const blocks=Object.freeze(Object.fromEntries(Object.entries(codes).map(([name,code])=>[name,Object.freeze([code])])));
function render(code){
 const def=window.StrikersSpriteDefinitions;
 if(!window.StrikersROM?.isVerified?.()||!def?.get(code))return null;
 const c=document.createElement('canvas');
 return def.draw(c,code)?c:null;
}
function draw(canvas,ids,scale=2){
 if(!ids?.length)return false;
 // Each entry is the start of a complete original sprite, not a tile strip.
 const sprites=ids.map(id=>id===null?null:render(id));
 if(sprites.some((c,i)=>ids[i]!==null&&!c))return false;
 const width=sprites.reduce((n,c)=>n+(c?.width??8),0);
 const height=Math.max(16,...sprites.map(c=>c?.height??0));
 canvas.width=width;canvas.height=height;
 const ctx=canvas.getContext('2d');ctx.imageSmoothingEnabled=false;
 let x=0;
 for(const c of sprites){if(c)ctx.drawImage(c,x,Math.floor((height-c.height)/2));x+=c?.width??8;}
 canvas.style.width=(width*scale)+'px';canvas.style.maxWidth='100%';
 canvas.style.height='auto';canvas.style.imageRendering='pixelated';
 return true;
}
function digits(n){return String(Math.max(0,Math.min(99,Math.floor(Number(n)||0)))).padStart(2,'0').split('').map(c=>1495+Number(c));}
function replaceArt(el,ids,label,scale=2){
 if(!el)return false;
 const canvas=document.createElement('canvas');canvas.setAttribute('aria-hidden','true');
 try{if(!draw(canvas,ids,scale))return false;}catch(e){console.warn('ROM UI:',e);return false;}
 el.setAttribute('aria-label',label);el.replaceChildren(canvas);return true;
}
function update(credits){
 replaceArt(document.getElementById('coinPrompt'),credits===0?blocks.insertCoin:blocks.start,credits===0?'INSERT COINS!':'START');
 replaceArt(document.getElementById('titleCredits'),[codes.credit,null,...digits(credits)],'CREDITS '+credits);
 const publisher=document.getElementById('titlePublisher');
 // The real logo spans two descriptors: 1485 holds the opening, 1493
 // completes the final Y/O. Confirmed by decoding both from the private ZIP.
 if(replaceArt(document.getElementById('titleCopyright'),[codes.copyright,codes.publisherTail],'©1995 Psikyo',1.6)&&publisher)publisher.hidden=true;
}
function notice(el,name,label){const code=codes[name];return Number.isInteger(code)&&replaceArt(el,[code],label);}
// Aircraft labels live inside the 8x8 font atlas at LUT 320. They
// are tile subsets, rather than complete descriptor starts.
const aircraftLabels=Object.freeze({
 p38:[348],p51:[349],spitfire:[350],bf109:[352,353],zero:[354],shinden:[355,356]
});
function labelAircraft(el,id){
 const ids=aircraftLabels[id],d=window.StrikersSpriteDefinitions;
 if(!el||!ids||!window.StrikersROM?.isVerified?.())return false;
 const c=document.createElement('canvas');c.width=ids.length*16;c.height=16;
 const g=c.getContext('2d');g.imageSmoothingEnabled=false;
 for(let i=0;i<ids.length;i++){
  const tile=document.createElement('canvas');
  if(!d?.drawRaw(tile,ids[i],1,1))return false;
  g.drawImage(tile,i*16,0);
 }
 c.style.width=c.width*2+'px';c.style.height='32px';c.style.maxWidth='100%';c.style.imageRendering='pixelated';
 c.setAttribute('aria-hidden','true');el.setAttribute('aria-label',el.textContent||id);el.replaceChildren(c);return true;
}
function cursor(el,player=1){return replaceArt(el,[player===2?codes.cursor2:codes.cursor1],player+'P',2);}
function result(won){
 const el=document.getElementById('resultTitle');
 notice(el,won?'ending':'largeGameOver',won?'THE END':'GAME OVER');
}
// Native tile atlas, visually verified against the private World ROM.
// The screen positions, palette and icon-to-life mapping are provisional.
// Tile 128..227 contains all two-digit combinations 00..99.
// Tiles 238..243 are individual aircraft reserve-life icons, in ROM selection order.
const hudAtlas=Object.freeze({digits:228,pairDigits:128,letters:321,onePlayer:30,twoPlayer:31,life:238,bomb:248,blank:320});
const lifeTiles=Object.freeze({p38:238,p51:239,spitfire:240,bf109:241,zero:242,shinden:243});
function scoreTiles(value){
 const text=String(value);
 if(!/^\d+$/.test(text))return hudText(text);
 const result=[];let i=0;
 if(text.length%2){result.push(hudAtlas.digits+Number(text[0]));i=1}
 for(;i<text.length;i+=2)result.push(hudAtlas.pairDigits+Number(text.slice(i,i+2)));
 return result;
}
let hudCache=new Map();
function hudTile(code){
 if(!window.StrikersROM?.isVerified?.())return null;
 if(hudCache.has(code))return hudCache.get(code);
 const c=document.createElement('canvas');
 if(!window.StrikersSpriteDefinitions?.drawRaw(c,code,1,1))return null;
 hudCache.set(code,c);return c;
}
function hudText(text){
 return Array.from(String(text).toUpperCase(),ch=>ch>='0'&&ch<='9'?hudAtlas.digits+Number(ch):ch>='A'&&ch<='Z'?hudAtlas.letters+ch.charCodeAt(0)-65:hudAtlas.blank);
}
function hudTiles(el,ids,label,advance=12){
 if(!el||!ids.length||!window.StrikersROM?.isVerified?.())return false;
 const key=ids.join(',')+':'+advance;
 if(el.dataset.hudKey===key&&el.querySelector('canvas'))return true;
 const sprites=ids.map(hudTile);
 if(sprites.some(c=>!c))return false;
 const c=document.createElement('canvas');c.width=(ids.length-1)*advance+16;c.height=16;
 const g=c.getContext('2d');g.imageSmoothingEnabled=false;
 sprites.forEach((tile,i)=>g.drawImage(tile,i*advance,0));
 c.style.width=c.width*1.5+'px';c.style.maxWidth='100%';c.style.height='auto';c.style.imageRendering='pixelated';
 c.setAttribute('aria-hidden','true');el.setAttribute('aria-label',label);el.dataset.hudKey=key;el.replaceChildren(c);return true;
}
function hudNumber(el,n){const text=String(n);return hudTiles(el,scoreTiles(text),text,16);}
function hudMarks(el,code,n,label){if(!el||!window.StrikersROM?.isVerified?.())return false;n=Math.max(0,Math.min(8,n|0));if(!n){el.replaceChildren();el.setAttribute('aria-label',label);delete el.dataset.hudKey;return true;}return hudTiles(el,Array(n).fill(code),label,12);}
window.addEventListener('strikers-rom-ready',()=>hudCache.clear());

window.StrikersOriginalUI=Object.freeze({codes,blocks,draw,digits,update,result,notice,replaceArt,labelAircraft,aircraftLabels,cursor,hudAtlas,lifeTiles,scoreTiles,hudText,hudTiles,hudNumber,hudMarks,paletteStatus:'provisional grayscale'});
})();