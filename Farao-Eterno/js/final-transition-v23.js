/* Faraó Eterno V2.3 — cinema de linhas e transição para o tesouro.
 * APRESENTAÇÃO apenas: não mexe em resultados, RTP, saldo, cofre ou pagamentos.
 * Carrega depois dos módulos de Free Spins/Super Scatter e moeda.
 */
(()=>{
 'use strict';
 const g=window.__faraoGame,stage=document.getElementById('stage');
 const canvas=document.getElementById('canvas');
 if(!g||!stage||!canvas||g.finalV23)return;
 const ctx=canvas.getContext('2d'),reduced=!!window.matchMedia?.('(prefers-reduced-motion: reduce)').matches;
 const pathOverlay=document.createElement('div');
 pathOverlay.className='farao-treasure-transition';
 pathOverlay.setAttribute('aria-hidden','true');
 pathOverlay.innerHTML='<div class="farao-treasure-sun"></div>'+
   '<div class="farao-treasure-kicker">O TEMPLO DESPERTA</div>'+
   '<div class="farao-treasure-title">O TESOURO REVELADO</div>'+
   '<div class="farao-treasure-stars">✦ &nbsp; ✦ &nbsp; ✦</div>';
 stage.appendChild(pathOverlay);
 const DURATION=1450;
 let revealStart=0,revealId=null,finishedKey=null;
 let traceCount=0,summaryCount=0;
 const now=()=>performance.now();
 const previousSummary=g.showSummary.bind(g);
 function clearTransition(){
   revealStart=0;revealId=null;
   pathOverlay.classList.remove('is-visible');
 }
 function checkSummary(){
   return !g.spinning&&!g.superseded&&!g.ledger?.payout&&
    !g.paused&&!document.hidden&&g._summaryRequested&&!!g.pendingSummary;
 }
 g.showSummary=function(...args){
   if(!checkSummary()){
     // Não bloquear a lógica original quando não há resumo pronto.
     return previousSummary(...args);
   }
   const b=this.pendingSummary;
   const key=this.ledger?.last?.id+'|'+b?.played+'|'+b?.totalU+'|'+(b?.superMultiplier||1);
   if(finishedKey===key){
     clearTransition();
     return previousSummary(...args);
   }
   if(reduced){
     finishedKey=key;
     return previousSummary(...args);
   }
   if(revealId!==key){
     revealId=key;
     revealStart=now();
     pathOverlay.classList.remove('is-visible');
     void pathOverlay.offsetWidth;
     pathOverlay.classList.add('is-visible');
     summaryCount++;
     this.nextActionAt=Math.max(this.nextActionAt,revealStart+DURATION);
   }
   if(now()-revealStart<DURATION)return;
   finishedKey=key;
   clearTransition();
   return previousSummary(...args);
 };
 // Glint leve que percorre a LINHA EFETIVAMENTE PREMIADA.
 // Não cria uma segunda linha, não emite som e não toca no resultado.
 const oldDrawFx=g.drawFx.bind(g);
 g.drawFx=function(t){
   oldDrawFx(t);
   if(reduced||this.spinning||this.paused||this.superseded||document.hidden)return;
   const stageLine=this.sequenceFocusAt?.(t);
   const base=this.activeBasePaylineAt?.(t);
   const line=stageLine?.kind==='line'?stageLine.win:base?.win;
   if(!line||line.expanded||!Array.isArray(line.rows)||line.count<3)return;
   const R=this.visualV11?.layout;
   if(!R)return;
   const n=Math.min(5,line.count,line.rows.length),nodes=[];
   for(let c=0;c<n;c++){
     const row=line.rows[c];
     if(!Number.isInteger(row)||row<0||row>2)return;
     nodes.push({x:R.left+c*R.step+R.cw*.5,y:R.top+row*R.gh+R.gh*.5});
   }
   if(nodes.length<3)return;
   const first=stageLine||base;
   const delay=stageLine?(first.preDelay||0):this.turbo?470:650;
   const duration=stageLine?Math.max(200,(first.perReel||350)*n):970;
   const progress=Math.max(0,Math.min(1,(t-first.start-delay)/duration));
   if(progress<=0)return;
   const k=Math.min(n-2,Math.floor(progress*(n-1)));
   const sub=progress*(n-1)-k;
   const x=nodes[k].x+(nodes[k+1].x-nodes[k].x)*sub;
   const y=nodes[k].y+(nodes[k+1].y-nodes[k].y)*sub;
   ctx.save();
   ctx.beginPath();ctx.rect(R.left-4,R.top-8,4*R.step+R.cw+8,3*R.gh+16);ctx.clip();
   const radius=11+3*Math.sin(t*.013)**2;
   const glow=ctx.createRadialGradient(x,y,0,x,y,radius*2.3);
   glow.addColorStop(0,'rgba(255,253,222,.95)');
   glow.addColorStop(.18,'rgba(242,226,135,.83)');
   glow.addColorStop(.5,'rgba(113,217,251,.23)');
   glow.addColorStop(1,'rgba(113,217,251,0)');
   ctx.globalCompositeOperation='screen';
   ctx.fillStyle=glow;
   ctx.beginPath();ctx.arc(x,y,radius*2.3,0,Math.PI*2);ctx.fill();
   ctx.restore();
   traceCount++;
 };
 const previousFrame=g.frame.bind(g);
 g.frame=function(t){
   if(revealStart&&(this.superseded||document.hidden||!this.pendingSummary)){
     clearTransition();
   }
   return previousFrame(t);
 };
 g.finalV23={
   version:'2.3-rc',visualOnly:true,
   get traces(){return traceCount;},
   get transitions(){return summaryCount;},
   get active(){return pathOverlay.classList.contains('is-visible');},
   get durationMs(){return DURATION;}
 };
})();