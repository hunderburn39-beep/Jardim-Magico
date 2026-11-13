/* Faraó Eterno — acabamento gráfico/sonoro V2.
 * Nunca altera RNG, grelhas sorteadas, multiplicadores, saldo, cofre ou payouts.
 * Os sinais são observados APÓS a liquidação e não geram novos resultados.
 */
(()=>{
 'use strict';
 const g=window.__faraoGame,stage=document.getElementById('stage');
 if(!g||!stage||g.qualityV2)return;
 const reduce=!!window.matchMedia?.('(prefers-reduced-motion: reduce)').matches;
 const layer=document.createElement('div');
 layer.className='farao-quality-layer';
 layer.setAttribute('aria-hidden','true');
 const flash=document.createElement('div');flash.className='farao-win-flash';layer.appendChild(flash);
 const sparkCount=reduce?0:18;
 for(let i=0;i<sparkCount;i++){
   const n=document.createElement('i');
   n.className='farao-spark';
   const x=(8+((i*37)%84)),y=(31+((i*19)%32));
   const angle=(i*2.399963229728653),dist=44+(i%5)*22;
   const dx=Math.cos(angle)*dist,dy=Math.sin(angle)*dist*.75;
   n.style.setProperty('--sx',x+'%');n.style.setProperty('--sy',y+'%');
   n.style.setProperty('--dx',dx.toFixed(1)+'px');
   n.style.setProperty('--dy',dy.toFixed(1)+'px');
   n.style.setProperty('--dx2',(dx*.58).toFixed(1)+'px');
   n.style.setProperty('--dy2',(dy*.58).toFixed(1)+'px');
   n.style.setProperty('--delay',((i%5)*43)+'ms');
   layer.appendChild(n);
 }
 stage.appendChild(layer);
 let seqSeen=g.ledger?.last?.id||0;
 let effectUntil=0,lastTick=0;
 function light(t){
   if(reduce)return;
   layer.classList.remove('burst');
   // A reativação faz começar a sequência em todos os motores WebKit.
   void layer.offsetWidth;
   layer.classList.add('burst');
   effectUntil=t+1900;
 }
 function chord(type){
   const a=g.audio;
   if(!a?.ctx||!a.on||g.soundMode==='mute'||g.paused||document.hidden)return;
   try{
     const ctx=a.ctx,out=a.out||ctx.destination,t=ctx.currentTime;
     const notes=type==='super'?[164.81,246.94,329.63,493.88]:
       type==='epic'?[146.83,220,293.66,440]:[220,277.18,329.63];
     for(let i=0;i<notes.length;i++){
       const o=ctx.createOscillator(),v=ctx.createGain(),hi=ctx.createBiquadFilter();
       o.type=i===0?'triangle':'sine';o.frequency.value=notes[i];
       hi.type='lowpass';hi.frequency.value=1400;
       const start=t+i*.095;
       v.gain.setValueAtTime(.0001,start);
       v.gain.exponentialRampToValueAtTime(type==='super'?.013:.009,start+.065);
       v.gain.exponentialRampToValueAtTime(.0001,start+1.22);
       o.connect(hi);hi.connect(v);v.connect(out);
       o.start(start);o.stop(start+1.24);
       o.onended=()=>{try{o.disconnect();hi.disconnect();v.disconnect()}catch(_){}};
     }
   }catch(_){/* AudioContext bloqueado nunca interrompe o jogo. */}
 }
 // O clique do dado mantém a face já guardada pela sessão. Som adicional apenas.
 document.addEventListener('click',ev=>{
   const b=ev.target?.closest?.('#super-roll');
   if(!b||b.disabled||!g.ledger?.feature?.superMultiplier)return;
   if(/LANÇAR DADO/i.test(b.textContent||''))chord('super');
 },true);
 const api={
   version:'2.0',presentationOnly:true,mathUnchanged:true,
   get lastProcessedSpin(){return seqSeen;},
   get active(){return layer.classList.contains('burst');}
 };
 g.qualityV2=api;
 function monitor(t){
   requestAnimationFrame(monitor);
   if(t-lastTick<90)return;
   lastTick=t;
   if(effectUntil&&t>effectUntil){layer.classList.remove('burst');effectUntil=0;}
   if(g.superseded||document.hidden)return;
   const last=g.ledger?.last;
   // Evitar tocar no resultado antes de a sessão o ter liquidado.
   if(!last||!Number.isInteger(last.id)||last.id===seqSeen||g.spinning||
      g.ledger?.pending)return;
   seqSeen=last.id;
   const bet=Number(last.bet)||1;
   const won=Number(last.prizeU)||0;
   const unit=window.FaraoSession?.units?.(bet)||252000;
   const factor=won/unit;
   if(last.result?.superTrigger){light(t);chord('super');return;}
   if(factor>=30){light(t);chord(factor>=100?'epic':'win');}
 }
 requestAnimationFrame(monitor);
})();