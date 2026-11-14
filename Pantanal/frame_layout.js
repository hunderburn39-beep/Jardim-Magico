/* Pantanal v2.23 — moldura e tambores no mesmo sistema de coordenadas.
 * Regras, prémios e rolagem permanecem inalterados.
 */
(function(root,make){
 'use strict';
 const frame=make();
 if(typeof module==='object'&&module.exports)module.exports=frame;
 if(root)root.PantanalFrameLayout=frame;
})(typeof globalThis!=='undefined'?globalThis:null,function(){
 'use strict';
 const FRAME={width:700,height:398},REELS={width:611,height:314};
 let cssInstalled=false;
 function injectCss(doc){
   if(cssInstalled||!doc||doc.getElementById('pantanalFrameLayoutCSS'))return;
   cssInstalled=true;
   const style=doc.createElement('style');
   style.id='pantanalFrameLayoutCSS';
   style.textContent=[
    /* Horizontal: retain the established v2.20 geometry and styling. */
    '#reelArea{position:absolute;top:76px;left:50%;width:611px;height:314px;transform:translateX(-50%);pointer-events:none;z-index:2}',
    '#reelArea #reels{position:absolute;top:0;left:0;width:611px;height:314px;transform:none}',
    '#reelArea #lineOverlay{position:absolute;top:0;left:0;width:611px;height:314px;transform:none;pointer-events:none}',
    '#reelArea #reelFrame{display:none}',
    /* Portrait: 700/800=87.5% viewport width; inner reels centered 44.5px / 42px. */
    '#screen.portrait #reelArea{top:50%;left:50%;width:700px;height:398px;transform:translate(-50%,-50%);z-index:2}',
    '#screen.portrait #reelArea::before{content:"";position:absolute;inset:0;border-radius:30px;'+
      'background:radial-gradient(ellipse at 50% 46%,#392313 0%,#211305 67%,#0d0905 100%);'+
      'box-shadow:0 10px 35px #070401db;pointer-events:none}',
    '#screen.portrait #reelArea #reels{top:42px;left:44.5px;width:611px;height:314px;transform:none;z-index:2}',
    '#screen.portrait #reelArea #lineOverlay{top:42px;left:44.5px;width:611px;height:314px;transform:none;z-index:3}',
    /* The frame is its own layer instead of a baked offset in the background. */
    '#screen.portrait #reelArea #reelFrame{display:block;position:absolute;inset:0;z-index:4;'+
      'border:12px ridge #d5a156;border-radius:30px;pointer-events:none;'+
      'box-shadow:inset 0 0 0 3px #5b3516,inset 0 0 19px #f9d777b5,'+
      '0 0 0 2px #5d3515,0 12px 26px #0a0505d9,0 0 23px #d5963e60}',
    '#screen.portrait #reelArea #reelFrame::before{content:"";position:absolute;inset:-17px;'+
      'border:2px solid #f3ce79;border-radius:34px;box-shadow:0 0 0 2px #45250c;pointer-events:none}',
    '#screen.portrait #reelArea #reelFrame::after{content:"";position:absolute;inset:5px;'+
      'border:2px solid #f7df9e87;border-radius:17px;pointer-events:none}',
    /* Numbers belong to the frame, not to the clipped reel viewport. */
    '#reelArea #lineNumberLabels{display:none}',
    '#screen.portrait #reelArea #lineNumberLabels{display:block;position:absolute;inset:0;z-index:7;pointer-events:none;overflow:visible}',
    '#screen.portrait #lineNumberLabels .lineNumber{position:absolute;display:flex;align-items:center;justify-content:center;'+
      'width:33px;height:27px;box-sizing:border-box;border:2px solid #bd873b;border-radius:8px;'+
      'font:900 20px/1 Arial,sans-serif;letter-spacing:-.5px;text-align:center;white-space:nowrap;'+
      'color:#ffeac0;background:linear-gradient(#40250f,#281607);'+
      'text-shadow:0 1px 1px #000;box-shadow:0 1px 3px #0d0506cf;opacity:.78}',
    '#screen.portrait #lineNumberLabels .lineNumber.isActive{opacity:1;color:#fff3a7;'+
      'border-color:#f7c86e;background:linear-gradient(#8a4c16,#44240b);box-shadow:0 0 7px #f1b84772}',
    '#screen.portrait #lineNumberLabels .lineNumber.isCurrent{opacity:1;color:#1e1203;'+
      'border-color:#fffac9;background:#ffe67b;box-shadow:0 0 14px #ffe76b;transform:scale(1.14)}',
    '#screen.portrait #lineNumberLabels .lineNumber[data-side="left"]{left:6px}',
    '#screen.portrait #lineNumberLabels .lineNumber[data-side="right"]{right:6px}',
    /* Five original vertical lines are identified directly below their reel. */
    '#screen.portrait #lineNumberLabels .lineNumber[data-side="bottom"]{height:27px;top:365px}',

    /* v2.23 – estrutura sem sobreposição para o ecrã vertical. */
    '#screen.portrait #game>.bg{display:none!important}',
    '#screen.portrait #reelArea{top:calc(50% - 30px)}',
    '#screen.portrait #credit,#screen.portrait #win,#screen.portrait #bet{'+
      'top:calc(50% - 320px);width:200px;height:67px;min-height:67px;z-index:6;box-sizing:border-box;'+
      'padding:29px 7px 5px;text-align:center;color:#fff0b5;background:linear-gradient(#5b2e0b,#261509);'+
      'border:2px solid #c99545;border-radius:13px;box-shadow:inset 0 0 12px #d9a04d35,0 4px 12px #0009;'+
      'font:bold 27px/30px Arial,sans-serif;white-space:nowrap;overflow:hidden}',
    '#screen.portrait #credit{left:62px}',
    '#screen.portrait #win{left:300px}',
    '#screen.portrait #bet{left:538px}',
    '#screen.portrait #credit::before,#screen.portrait #win::before,#screen.portrait #bet::before{'+
      'position:absolute;top:5px;left:0;right:0;text-align:center;font:bold 17px Arial,sans-serif;'+
      'letter-spacing:1.7px;color:#e9bc74}',
    '#screen.portrait #credit::before{content:"CRÉDITOS"}',
    '#screen.portrait #win::before{content:"PRÉMIO"}',
    '#screen.portrait #bet::before{content:"APOSTA"}',
    '#screen.portrait #status{top:calc(50% + 184px)!important;left:60px;right:60px;'+
      'height:45px;min-height:0;padding:7px 8px;box-sizing:border-box;overflow:hidden;'+
      'font:bold 20px/28px Arial,sans-serif;text-align:center;white-space:nowrap;text-overflow:ellipsis}',
    '#screen.portrait #pantanalJackpots{top:calc(50% + 242px)!important;'+
      'left:65px!important;width:670px!important;gap:14px!important;z-index:5}',
    '#screen.portrait #pantanalJackpots>div{box-sizing:border-box;min-height:65px;'+
      'padding:6px 8px!important;box-shadow:0 4px 12px #0006}',
    '#screen.portrait #ctrls{bottom:3%!important;height:288px;'+
      'grid-template-rows:repeat(2,138px);gap:10px}',
    '#screen.portrait #spinButton{height:135px!important}',
    '#screen.portrait #spinButton img{height:130px!important;width:137px!important}',
    '#screen.portrait #ctrls button span{bottom:-1px}',
    '#screen.portrait #lineNumberLabels .lineNumber{opacity:.97}',
    /* Shift the main original backing and the numeric labels as a unit vertically. */
    '#screen.portrait #game>.bg{top:calc(50% - 240px)}',
    '#screen.portrait #credit,#screen.portrait #win,#screen.portrait #bet{top:calc(50% - 227px)}',
    '#screen.portrait #status{top:calc(50% + 222px)}',
    '#screen.portrait #pantanalJackpots{top:calc(50% + 315px)!important}',
    '@media (prefers-reduced-motion:reduce){#screen.portrait #reelArea #reelFrame{box-shadow:inset 0 0 0 3px #5b3516,0 8px 22px #000d}}'
   ].join('\n');
   doc.head.appendChild(style);
 }
 function addLineNumbers(area,doc){
   let wrapper=doc.getElementById('lineNumberLabels');
   if(wrapper)return wrapper;
   wrapper=doc.createElement('div');
   wrapper.id='lineNumberLabels';
   wrapper.setAttribute('aria-label','Números das 25 linhas de prémio');
   // 10 numbers in each side rail; numbers 21–25 below their own columns.
   for(let line=1;line<=25;line++){
     const badge=doc.createElement('span');
     badge.className='lineNumber';
     badge.dataset.lineNumber=String(line);
     badge.textContent=String(line);
     badge.setAttribute('aria-label','Linha '+line);
     if(line<=20){
       const index=(line-1)%10;
       badge.dataset.side=line<=10?'left':'right';
       badge.style.top=(48+30*index)+'px';
     }else{
       const col=line-21;
       badge.dataset.side='bottom';
       badge.style.left=(44.5+col*124+57.5-16.5)+'px';
     }
     wrapper.appendChild(badge);
   }
   area.appendChild(wrapper);
   return wrapper;
 }
 function setActiveLines(n){
   if(typeof document==='undefined')return;
   const active=[1,5,9,15,25].includes(Number(n))?Number(n):25;
   const labels=document.querySelectorAll('#lineNumberLabels [data-line-number]');
   labels.forEach(label=>label.classList.toggle('isActive',Number(label.dataset.lineNumber)<=active));
 }
 function highlightLine(n){
   if(typeof document==='undefined')return;
   document.querySelectorAll('#lineNumberLabels [data-line-number]').forEach(label=>
     label.classList.toggle('isCurrent',Number(label.dataset.lineNumber)===Number(n)));
 }
 function mount(doc=typeof document!=='undefined'?document:null){
   if(!doc)return false;
   injectCss(doc);
   const reels=doc.getElementById('reels');
   const game=doc.getElementById('game');
   if(!reels||!game)return false;
   if(doc.getElementById('reelArea')){
     addLineNumbers(doc.getElementById('reelArea'),doc);
     setActiveLines(25);
     return true;
   }
   const area=doc.createElement('div');
   area.id='reelArea';
   reels.parentNode.insertBefore(area,reels);
   area.appendChild(reels);
   const originalOverlay=doc.getElementById('lineOverlay');
   if(originalOverlay)area.appendChild(originalOverlay);
   const frame=doc.createElement('div');
   frame.id='reelFrame';
   frame.setAttribute('aria-hidden','true');
   area.appendChild(frame);
   addLineNumbers(area,doc);
   setActiveLines(25);
   return true;
 }
 if(typeof document!=='undefined'){
   if(document.readyState==='loading'){
     document.addEventListener('DOMContentLoaded',()=>mount(document),{once:true});
   }else mount(document);
 }
 return Object.freeze({mount,injectCss,setActiveLines,highlightLine,metrics:{frame:FRAME,reels:REELS,
   horizontalOffset:(FRAME.width-REELS.width)/2,
   verticalOffset:(FRAME.height-REELS.height)/2}});
});
