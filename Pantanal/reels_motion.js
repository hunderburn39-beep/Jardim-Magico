/* Pantanal v2.20 — animação visual independente do gerador e dos pagamentos.
 * Rolagem vertical real com 5 faixas contínuas, desaceleração e paragem
 * por tambor. Zero alterações na matemática ou nos símbolos de destino.
 */
(function(root,factory){
 'use strict';
 const motion=factory();
 if(typeof module==='object'&&module.exports)module.exports=motion;
 if(root)root.PantanalReelMotion=motion;
})(typeof globalThis==='undefined'?null:globalThis,function(){
 'use strict';
 const REEL_WIDTH=115,CELL_HEIGHT=102,ROW_GAP=4,REEL_GAP=9;
 const REEL_COUNT=5,ROWS=3,PITCH=CELL_HEIGHT+ROW_GAP;
 const STAGE_WIDTH=REEL_WIDTH*REEL_COUNT+REEL_GAP*(REEL_COUNT-1);
 const STAGE_HEIGHT=CELL_HEIGHT*ROWS+ROW_GAP*(ROWS-1);
 let stylesInjected=false;
 function installStyles(){
   if(stylesInjected||typeof document==='undefined')return;
   stylesInjected=true;
   const css=document.createElement('style');
   css.id='pantanalReelMotionCSS';
   css.textContent=
     '#reels{left:50%;top:76px;width:'+STAGE_WIDTH+'px;height:'+STAGE_HEIGHT+'px;transform:translateX(-50%);'+
     'grid-template-columns:repeat(5,115px);grid-template-rows:repeat(3,102px);column-gap:9px;row-gap:4px;overflow:hidden}'+
     '#reels.reelsRolling>.symbol{visibility:hidden}'+
     '#reels .reelSpinStage{position:absolute;inset:0;z-index:5;display:flex;gap:9px;width:100%;height:100%;overflow:hidden;pointer-events:none}'+
     '#reels .reelSpinLane{position:relative;flex:0 0 115px;width:115px;height:314px;overflow:hidden;contain:paint}'+
     '#reels .reelSpinStrip{position:absolute;left:0;top:0;width:115px;display:flex;flex-direction:column;gap:4px;will-change:transform}'+
     '#reels .reelSpinTile{flex:0 0 102px;width:115px;height:102px;background-repeat:no-repeat;'+
     'backface-visibility:hidden;transform:translateZ(0)}'+
     '@media(prefers-reduced-motion:reduce){#reels .reelSpinStrip{will-change:auto}}';
   document.head.appendChild(css);
 }
 function validateBoard(board){
   if(!Array.isArray(board)||board.length!==ROWS||
     !board.every(row=>Array.isArray(row)&&row.length===REEL_COUNT&&
       row.every(x=>Number.isInteger(x)&&x>=0&&x<=9))){
     throw new TypeError('Tambores devem conter 3 filas e 5 colunas válidas');
   }
 }
 function buildStrip(target,col,pickSymbol){
   const numberOfExtraCells=13+col*3;
   const values=[];
   for(let row=0;row<ROWS;row++)values.push(target[row][col]);
   for(let i=0;i<numberOfExtraCells+2;i++){
     const s=pickSymbol();
     values.push(Number.isInteger(s)&&s>=0&&s<=9?s:(i%10));
   }
   return {values,numberOfExtraCells,
     startTranslate:-numberOfExtraCells*PITCH,
     endTranslate:0,
     durationMs:1900+col*265};
 }
 function createStage(root,target,opts={}){
   const pick=typeof opts.pick==='function'?opts.pick:()=>Math.floor(Math.random()*10);
   const image=typeof opts.asset==='function'?opts.asset('05_slots.png'):'';
   const stage=document.createElement('div');stage.className='reelSpinStage';stage.id='pantanalReelSpinStage';
   const reels=[];
   for(let col=0;col<REEL_COUNT;col++){
     const desc=buildStrip(target,col,pick);
     const lane=document.createElement('div');lane.className='reelSpinLane';
     lane.dataset.column=String(col);
     const strip=document.createElement('div');strip.className='reelSpinStrip';
     strip.style.transform='translate3d(0,'+desc.startTranslate+'px,0)';
     for(const symbol of desc.values){
       const tile=document.createElement('div');
       tile.className='symbol reelSpinTile';
       if(image)tile.style.backgroundImage='url("'+image+'")';
       tile.style.backgroundPosition='-115px -'+(symbol*CELL_HEIGHT)+'px';
       strip.appendChild(tile);
     }
     lane.appendChild(strip);stage.appendChild(lane);
     reels.push({col,lane,strip,...desc});
   }
   root.classList.add('reelsRolling');
   root.appendChild(stage);
   return {stage,reels};
 }
 async function spin(from,target,opts={}){
   validateBoard(from);validateBoard(target);
   installStyles();
   const root=document.getElementById('reels');
   if(!root)throw new Error('Área dos tambores não encontrada');
   const previous=root.querySelector('#pantanalReelSpinStage');
   if(previous)previous.remove();
   const {stage,reels}=createStage(root,target,opts);
   const reduced=opts.reduceMotion===true||
      (opts.reduceMotion!==false&&typeof matchMedia==='function'&&matchMedia('(prefers-reduced-motion: reduce)').matches);
   const timing=[];
   try{
     await Promise.all(reels.map(async reel=>{
       const duration=reduced?1:reel.durationMs;
       timing.push(duration);
       if(typeof reel.strip.animate==='function'){
         const anim=reel.strip.animate(
           [{transform:'translate3d(0,'+reel.startTranslate+'px,0)'},
            {transform:'translate3d(0,0px,0)'}],
           {duration,easing:'cubic-bezier(0.12,0.72,0.24,1)',fill:'forwards'});
         try{await anim.finished;}catch(e){if(e?.name!=='AbortError')throw e;}
       }else{
         reel.strip.style.transition='transform '+duration+'ms cubic-bezier(0.12,0.72,0.24,1)';
         void reel.strip.offsetHeight;
         reel.strip.style.transform='translate3d(0,0px,0)';
         await new Promise(resolve=>setTimeout(resolve,duration+45));
       }
       if(typeof opts.onStop==='function')opts.onStop(reel.col);
     }));
   }finally{
     // stage only removed after the application updates the static symbols.
     // Keeping the final tiles visible avoids blank frames.
     if(typeof opts.onComplete==='function')opts.onComplete();
     root.classList.remove('reelsRolling');
     stage.remove();
   }
   return {durationMs:Math.max(...timing),reelDurationsMs:timing};
 }
 if(typeof document!=='undefined')installStyles();
 return Object.freeze({spin,buildStrip,validateBoard,installStyles,
   metrics:{reelWidth:REEL_WIDTH,cellHeight:CELL_HEIGHT,pitch:PITCH,
     stageWidth:STAGE_WIDTH,stageHeight:STAGE_HEIGHT}});
});
