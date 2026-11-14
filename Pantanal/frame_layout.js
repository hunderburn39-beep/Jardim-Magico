/* Pantanal v2.21 — moldura e tambores no mesmo sistema de coordenadas.
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
    /* Shift the main original backing and the numeric labels as a unit vertically. */
    '#screen.portrait #game>.bg{top:calc(50% - 240px)}',
    '#screen.portrait #credit,#screen.portrait #win,#screen.portrait #bet{top:calc(50% - 227px)}',
    '#screen.portrait #status{top:calc(50% + 222px)}',
    '#screen.portrait #pantanalJackpots{top:calc(50% + 315px)!important}',
    '@media (prefers-reduced-motion:reduce){#screen.portrait #reelArea #reelFrame{box-shadow:inset 0 0 0 3px #5b3516,0 8px 22px #000d}}'
   ].join('\n');
   doc.head.appendChild(style);
 }
 function mount(doc=typeof document!=='undefined'?document:null){
   if(!doc)return false;
   injectCss(doc);
   const reels=doc.getElementById('reels');
   const game=doc.getElementById('game');
   if(!reels||!game)return false;
   if(doc.getElementById('reelArea'))return true;
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
   return true;
 }
 if(typeof document!=='undefined'){
   if(document.readyState==='loading'){
     document.addEventListener('DOMContentLoaded',()=>mount(document),{once:true});
   }else mount(document);
 }
 return Object.freeze({mount,injectCss,metrics:{frame:FRAME,reels:REELS,
   horizontalOffset:(FRAME.width-REELS.width)/2,
   verticalOffset:(FRAME.height-REELS.height)/2}});
});
