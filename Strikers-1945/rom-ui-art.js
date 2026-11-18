/* V9.1: UI artwork decoded from the World ROM's native sprite descriptors.
 * The code and dimensions are verified; screen palette assignment is pending.
 * No ROM pixels are stored in this file.
 */
(()=>{'use strict';
const codes=Object.freeze({
 insertCoin:1458,gameOver:1447,largeGameOver:1439,continuePrompt:1471,
 credit:1478,start:1452,onePlayer:1476,twoPlayer:1477,copyright:1485,publisherTail:1493,
 addCoin:1463,freePlay:1467,ending:1505,warning:1569,select:1575,
 pleaseWait:1581,yes:1586,no:1588,time:1592,powerUp:1665,powerDown:1669
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
function result(won){if(!won)notice(document.getElementById('resultTitle'),'largeGameOver','GAME OVER');}
window.StrikersOriginalUI=Object.freeze({codes,blocks,draw,digits,update,result,notice,replaceArt,paletteStatus:'provisional grayscale'});
})();