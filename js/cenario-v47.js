/* Jardim Mágico V47 — animação do logótipo sem interferir nos eventos do jogo.
   Pássaros atravessam primeiro por trás, depois pela frente; borboleta pousa nas letras. */
(()=>{
 'use strict';
 const logo=document.querySelector('.game .logoLoop');
 if(!logo || logo.querySelector('.jmButterflyPerch'))return;
 function birdSvg(color1,color2){
  return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 110 90" aria-hidden="true">
   <defs><linearGradient id="wing" x2="1" y2="1"><stop stop-color="${color1}"/><stop offset="1" stop-color="${color2}"/></linearGradient></defs>
   <path d="M48 48 Q15 0 2 14 Q19 58 50 57Z" fill="url(#wing)" stroke="#fff8c5" stroke-width="1.4"/>
   <path d="M54 47 Q78 1 105 24 Q86 65 55 59Z" fill="url(#wing)" stroke="#ffe9af" stroke-width="1.4"/>
   <path d="M44 50Q53 42 64 50L84 71Q60 72 47 61Z" fill="${color2}"/>
   <path d="M40 48Q53 37 67 46Q70 55 55 62Q43 62 40 48" fill="${color1}" stroke="#ffdc98" stroke-width="1.3"/>
   <circle cx="45" cy="47" r="2.6" fill="#101624"/><path d="M36 50L22 54L37 57Z" fill="#ffca55"/>
   <path d="M60 64Q70 76 84 79Q70 80 55 68" fill="none" stroke="${color2}" stroke-width="5" stroke-linecap="round"/>
   </svg>`;
 }
 const butterfly=`<svg viewBox="0 0 100 85" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
 <defs><radialGradient id="bloomWing"><stop stop-color="#fff6bc"/><stop offset=".36" stop-color="#ff9bdd"/><stop offset=".78" stop-color="#d143e9"/><stop offset="1" stop-color="#6b13ae"/></radialGradient></defs>
 <g class="jmButterflyLeft"><path d="M48 40Q11 -18 2 17Q0 42 43 51Q5 62 19 79Q41 80 49 52Z" fill="url(#bloomWing)" stroke="#ffedb8" stroke-width="3"/><path d="M37 29Q22 8 17 24M36 56Q22 64 25 68" stroke="#fff6df" stroke-width="3" fill="none"/></g>
 <g class="jmButterflyRight"><path d="M52 40Q89 -18 98 17Q100 42 57 51Q95 62 81 79Q59 80 51 52Z" fill="url(#bloomWing)" stroke="#ffedb8" stroke-width="3"/><path d="M63 29Q78 8 83 24M64 56Q78 64 75 68" stroke="#fff6df" stroke-width="3" fill="none"/></g>
 <path d="M49 32Q48 54 51 69" fill="none" stroke="#39224d" stroke-width="5" stroke-linecap="round"/>
 <path d="M49 36Q38 19 35 17M51 36Q62 19 65 17" stroke="#fff0b6" stroke-width="2.5" fill="none" stroke-linecap="round"/>
 </svg>`;
 const back=document.createElement('div');back.className='jmTitleAnim jmBirdBack';
 back.innerHTML=birdSvg('#41cefb','#2878d0');
 const front=document.createElement('div');front.className='jmTitleAnim jmBirdFront';
 front.innerHTML=birdSvg('#fbd690','#df7544');
 const high=document.createElement('div');high.className='jmTitleAnim jmBirdHigh';
 high.innerHTML=birdSvg('#bbeafb','#4677b0');
 const perch=document.createElement('div');perch.className='jmTitleAnim jmButterflyPerch';
 perch.innerHTML=butterfly;
 logo.prepend(back);
 logo.append(high,front,perch);
})();
