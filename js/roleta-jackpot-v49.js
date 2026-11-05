/* Jardim Mágico V49 — roleta visual, com 32 setores e RESPIN automático.
 * O resultado é selecionado e persistido pelo motor antes de iniciar a animação.
 * Este módulo só apresenta o resultado: não altera saldos, jackpots nem probabilidades.
 */
(function(){
'use strict';
const NS='http://www.w3.org/2000/svg', FULL_CIRCLE=360, STEP=360/32;
const COLORS={MIN:['#29834f','#173c2a'],MED:['#9553db','#48266f'],MAX:['#328fd6','#17395f'],ULTRA:['#ffd05c','#9a4526'],RESPIN:['#ef75bb','#7b2e92']};
const NAMES={MIN:'MIN',MED:'MED',MAX:'MAX',ULTRA:'ULTRA',RESPIN:'↻'};
const wait=ms=>new Promise(done=>setTimeout(done,ms));
const reduced=()=>window.matchMedia&&window.matchMedia('(prefers-reduced-motion:reduce)').matches;
function svg(tag,props){const el=document.createElementNS(NS,tag);Object.entries(props||{}).forEach(([key,val])=>el.setAttribute(key,String(val)));return el;}
function angle(deg,r){const rad=deg*Math.PI/180;return [Math.cos(rad)*r,Math.sin(rad)*r];}
function createFace(slots){
 const wheel=svg('svg',{viewBox:'-204 -204 408 408',role:'img','aria-label':'Roleta de 32 casas, 4 jackpots e duas casas RESPIN'});
 const defs=svg('defs');
 const grad=svg('radialGradient',{id:'jm49wheelHub'});[['0%','#ffe7ab'],['55%','#bb743d'],['100%','#36143c']].forEach(([offset,color])=>grad.append(svg('stop',{offset,'stop-color':color})));defs.append(grad);wheel.append(defs);
 const outer=svg('circle',{r:195,fill:'#270f39',stroke:'#ffe9a5','stroke-width':8});wheel.append(outer);
 slots.forEach((kind,i)=>{
  const center=-90+i*STEP,start=center-STEP/2,end=center+STEP/2;
  const a=angle(start,182),b=angle(end,182);
  const path=svg('path',{d:'M 0 0 L '+a[0].toFixed(3)+' '+a[1].toFixed(3)+' A 182 182 0 0 1 '+b[0].toFixed(3)+' '+b[1].toFixed(3)+' Z',fill:COLORS[kind][i%2],'stroke':'#fbe0a7','stroke-width':.9});
  wheel.append(path);
  const labelPos=angle(center,145),label=svg('text',{x:labelPos[0].toFixed(2),y:labelPos[1].toFixed(2),'text-anchor':'middle','dominant-baseline':'middle','font-family':'Georgia,serif','font-size':kind==='ULTRA'?8:kind==='RESPIN'?19:10,'font-weight':900,fill:'#fff3cc',stroke:'#190b27','stroke-width':.65,'paint-order':'stroke'});
  label.textContent=NAMES[kind];
  wheel.append(label);
 });
 wheel.append(svg('circle',{r:57,fill:'#220d33',stroke:'#ffd98b','stroke-width':5}));
 wheel.append(svg('circle',{r:48,fill:'url(#jm49wheelHub)',stroke:'#fff3b5','stroke-width':3}));
 const t=svg('text',{x:0,y:-3,'text-anchor':'middle','dominant-baseline':'middle','font-family':'Georgia,serif','font-size':23,'font-weight':900,fill:'#fff3c2',stroke:'#683b30','stroke-width':1.4,'paint-order':'stroke'});
 t.textContent='JARDIM';wheel.append(t);
 const t2=svg('text',{x:0,y:23,'text-anchor':'middle','dominant-baseline':'middle','font-family':'Georgia,serif','font-size':18,'font-weight':900,fill:'#fff2c2'});
 t2.textContent='MÁGICO';wheel.append(t2);
 return wheel;
}
function build(slots){
 const modal=document.createElement('section');
 modal.id='jm49WheelModal';modal.setAttribute('role','dialog');modal.setAttribute('aria-modal','true');modal.setAttribute('aria-labelledby','jm49WheelHeading');
 modal.innerHTML='<div class="jm49WheelDialog"><div class="jm49WheelStars" aria-hidden="true">✦ ✧ ✦</div><h2 id="jm49WheelHeading">ROULETA DE JACKPOTS</h2><p class="jm49WheelExplanation">16 QUADRADOS COMPLETOS · 32 CASAS</p><div class="jm49WheelBoard"><div class="jm49WheelPointer" aria-hidden="true">▼</div><div class="jm49WheelDisc"></div></div><p id="jm49WheelStatus" class="jm49WheelStatus" role="status" aria-live="polite">A roda vai começar…</p><p class="jm49WheelPrize" id="jm49WheelPrize"></p><button type="button" class="jm52WheelSpin" id="jm52WheelSpin" aria-label="Parar a roleta">⏹ PARAR GIRO</button><div class="jm49WheelFooter">MIN · MED · MAX · ULTRA · <strong>↻ RESPIN</strong><small>O RESPIN faz a roleta rodar novamente, automaticamente.</small></div></div>';
 const target=modal.querySelector('.jm49WheelDisc');
 target.append(createFace(slots));
 document.body.append(modal);
 return {modal,disc:target,status:modal.querySelector('#jm49WheelStatus'),prize:modal.querySelector('#jm49WheelPrize')};
}
/* V52 — o jogador controla a paragem e confirma o jackpot com o botão GIRO.
   O resultado continua pré-sorteado pelo motor e não depende do instante do toque. */
let view=null,playing=false,requestSpinAction=null;
function handleSpin(){
 if(typeof requestSpinAction!=='function')return false;
 requestSpinAction();
 return true;
}
async function play(options){
 if(playing)return;
 playing=true;
 const {slots,stops,boardCells=16,turbo=false,amount='0,00 €',onSpin,onStop}=options;
 if(!Array.isArray(slots)||slots.length!==32||!Array.isArray(stops)||!stops.length){playing=false;throw Error('Invalid wheel configuration')}
 if(!view)view=build(slots);
 const {modal,disc,status,prize}=view,btn=modal.querySelector('#jm52WheelSpin');
 const description=modal.querySelector('.jm49WheelExplanation');
 if(description)description.textContent=boardCells+' QUADRADOS COMPLETOS · 32 CASAS';
 btn.onclick=()=>handleSpin();
 modal.classList.add('open');disc.style.transition='none';disc.style.transform='rotate(0deg)';
 let angleNow=0,spinEpoch=0,spinning=false,token=0;
 function startMovement(){
  const ticket=++token;spinning=true;let last=performance.now();
  const frame=(now)=>{
   if(!spinning||ticket!==token)return;
   angleNow+=(now-last)*(turbo?1.12:.78);last=now;
   disc.style.transition='none';
   disc.style.transform='rotate('+angleNow+'deg)';
   requestAnimationFrame(frame);
  };
  requestAnimationFrame(frame);
 }
 function waitForTap(){
  return new Promise(resolve=>{
   requestSpinAction=()=>{requestSpinAction=null;resolve();};
  });
 }
 try{
  prize.textContent='';status.textContent='TABULEIRO COMPLETO! ✨';
  await wait(reduced()?120:570);
  for(let n=0;n<stops.length;n++){
   const idx=stops[n],kind=slots[idx];
   if(!Number.isInteger(idx)||idx<0||idx>=32)throw Error('Invalid stop index');
   status.textContent=n?'↻ RESPIN! A RODA JÁ ESTÁ A GIRAR':'A RODA ESTÁ A GIRAR!';
   prize.textContent='';btn.disabled=false;btn.textContent='⏹ PARAR GIRO';btn.setAttribute('aria-label','Parar a roleta');
   spinEpoch=performance.now();
   startMovement();if(onSpin)onSpin();
   status.textContent='CARREGA EM GIRO PARA PARAR';
   await waitForTap();
   // A confirmação do jogador controla a paragem; o resultado foi guardado antes do bónus.
   const elapsed=performance.now()-spinEpoch;
   if(elapsed<420)await wait(420-elapsed);
   spinning=false;token++;
   const turns=turbo?1:2;
   const normalize=n=>((n%360)+360)%360;
   const delta=normalize(-idx*STEP-normalize(angleNow));
   const endAngle=angleNow+turns*360+delta;
   const ms=reduced()?120:turbo?850:1600;
   disc.style.transition='transform '+ms+'ms cubic-bezier(.12,.67,.16,1)';
   disc.style.transform='rotate('+endAngle+'deg)';
   angleNow=endAngle;
   btn.disabled=true;btn.textContent='A PARAR…';
   await wait(ms+90);
   if(onStop)onStop(kind,idx);
   if(kind==='RESPIN'){
    status.textContent='↻ RESPIN!';
    prize.textContent='NOVA VOLTA GRÁTIS!';
    modal.classList.add('jm49WheelRespin');
    await wait(reduced()?140:turbo?560:1000);
    modal.classList.remove('jm49WheelRespin');
    // Volta automaticamente; o utilizador pára a nova rotação no botão GIRO.
   }else{
    status.textContent='🏆 JACKPOT '+kind+'!';
    prize.textContent=amount;prize.classList.add('jm52WheelSuspense');
    modal.classList.add('jm49WheelWinner');
    btn.disabled=false;btn.textContent='⟳ GIRO · CONTINUAR';btn.setAttribute('aria-label','Confirmar jackpot e continuar');
    await waitForTap();
    prize.classList.remove('jm52WheelSuspense');
    modal.classList.remove('jm49WheelWinner');
   }
  }
 }finally{
  spinning=false;token++;requestSpinAction=null;btn.onclick=null;
  modal.classList.remove('open','jm49WheelWinner','jm49WheelRespin');
  prize.classList.remove('jm52WheelSuspense');playing=false;
 }
}
window.JM_WHEEL_V49={play,handleSpin,isPlaying:()=>playing};
})();