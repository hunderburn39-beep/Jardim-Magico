/* Faraó Eterno — moedas nas linhas premiadas e tesouro final (V1.0).
 * Apenas apresentação e som. Não chama motor, RNG, settle, creditNext nem save.
 * Respeita música ambiente (volume independente), mute, pausa e reduced motion.
 */
(()=>{
 'use strict';
 const g=window.__faraoGame, canvas=document.getElementById('canvas');
 if(!g||!canvas||!g.audio)return;
 const ctx=canvas.getContext('2d');
 const audio=g.audio,TAU=Math.PI*2;
 const reduced=!!window.matchMedia?.('(prefers-reduced-motion: reduce)').matches;
 const seen=new WeakSet(),coins=[];
 const REEL_LAYOUT=g.visualV11?.layout||{left:18,top:284,cw:105,gh:129,step:109};
 const REEL_X=REEL_LAYOUT.left, STEP_X=REEL_LAYOUT.step,
       CELL_W=REEL_LAYOUT.cw, TOP=REEL_LAYOUT.top,
       CELL_H=REEL_LAYOUT.gh, MAX_COINS=105;
 const clamp=(v,lo,hi)=>Math.min(hi,Math.max(lo,v));
 const active=()=>!!(audio.on&&audio.ctx&&!g.paused&&!g.superseded&&!document.hidden);
 // Som de moedas real enviado pelo criador, cortado no primeiro impacto.
 // MP3 mono local embutido para reprodução instantânea em dispositivos móveis.
 const SAMPLE_B64='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';
 const sampleBytes=Uint8Array.from(atob(SAMPLE_B64),c=>c.charCodeAt(0)).buffer;
 let sampleCtx=null,sampleBuffer=null,lastClatter=-Infinity;
 function prepareSample(){
  const ac=audio.ctx;if(!ac||sampleCtx===ac)return;
  sampleCtx=ac;sampleBuffer=null;
  try{ac.decodeAudioData(sampleBytes.slice(0)).then(b=>{if(sampleCtx===ac)sampleBuffer=b;}).catch(()=>{});}
  catch(_){}
 }
 const originalInit=audio.init.bind(audio);
 audio.init=function(...args){const result=originalInit(...args);prepareSample();return result;};
 function coinSound(final=false){
  if(!active())return;
  prepareSample();
  const now=performance.now();
  if(!final&&now-lastClatter<85)return;
  lastClatter=now;
  if(!sampleBuffer)return;
  const ac=audio.ctx,bus=audio.out||ac.destination;
  const offsets=final?[0,.35,.76,1.17,1.58,1.98]:[0];
  for(let i=0;i<offsets.length;i++){
   try{
    const source=ac.createBufferSource(),gain=ac.createGain();
    source.buffer=sampleBuffer;
    source.playbackRate.value=final?.96+(i%3)*.025:1;
    gain.gain.value=final?1.05:1.80; // moedas +~6 dB; música independente
    source.connect(gain);gain.connect(bus);
    source.start(ac.currentTime+.006+offsets[i]);
    source.onended=()=>{try{source.disconnect();gain.disconnect();}catch(_){}};
   }catch(_){/* Erros sonoros não interrompem o jogo. */}
  }
 }
 audio.coinCascade=coinSound;
 // A fanfarra anterior era uma escala simples; substitui-a por moedas a cair.
 audio.fanfare=function(){coinSound(true);};
 function point(reel,row){
  return {x:REEL_X+CELL_W*.5+STEP_X*reel,y:TOP+CELL_H*(row+.5)};
 }
 function winners(w){
  if(!w||!Number.isInteger(w.count))return [];
  if(w.expanded){
   const exp=g.lastResult?.expanders?.find(e=>e.symbol===w.symbol);
   return (exp?.reels||[]).map(reel=>{
    const row=g.grid?.[reel]?.indexOf(w.symbol);
    return point(reel,row>=0?row:1);
   });
  }
  if(!Array.isArray(w.rows))return [];
  return Array.from({length:clamp(w.count,0,5)},(_,reel)=>point(reel,clamp(w.rows[reel]??1,0,2)));
 }
 function stagePoints(stage){
  if(stage.win)return winners(stage.win);
  if(stage.kind==='expansion'&&Array.isArray(stage.reels)){
   return stage.reels.map(reel=>{
    const row=g.grid?.[reel]?.indexOf(stage.symbol);
    return point(reel,row>=0?row:1);
   });
  }
  if(stage.kind==='scatter'){
   const result=[];
   for(let c=0;c<5;c++)for(let r=0;r<3;r++)if(g.grid?.[c]?.[r]==='TOMB')result.push(point(c,r));
   return result;
  }
  return [];
 }
 // Sprites feitos uma única vez para evitar gradientes em cada moeda/frame.
 function makeCoinSprite(back=false){
  const bitmap=document.createElement('canvas');bitmap.width=96;bitmap.height=96;
  const c=bitmap.getContext('2d');
  if(!c)return bitmap;
  const shade=c.createRadialGradient(34,24,4,48,48,39);
  shade.addColorStop(0,'#ffffe4');
  shade.addColorStop(.23,'#fff3ae');
  shade.addColorStop(.52,back?'#daa74a':'#f1b938');
  shade.addColorStop(.78,'#a56416');shade.addColorStop(1,'#452305');
  c.fillStyle=shade;c.strokeStyle='#fff2bb';c.lineWidth=4;
  c.beginPath();c.arc(48,48,35,0,TAU);c.fill();c.stroke();
  c.strokeStyle='#75400b';c.lineWidth=2.2;c.beginPath();c.arc(48,48,28,0,TAU);c.stroke();
  c.strokeStyle='#ffed9e';c.lineWidth=1.7;c.beginPath();c.arc(48,48,23,0,TAU);c.stroke();
  // Cunho com flor egípcia de seis pétalas, não um disco amarelo liso.
  c.save();c.translate(48,48);
  c.fillStyle=back?'#ffcf65':'#ffec99';c.strokeStyle='#8f5717';c.lineWidth=1.2;
  for(let i=0;i<6;i++){
   c.save();c.rotate(i*Math.PI/3);
   c.beginPath();c.ellipse(0,-11,5.4,10.5,0,0,TAU);c.fill();c.stroke();
   c.restore();
  }
  c.fillStyle='#a76b1d';c.beginPath();c.arc(0,0,5,0,TAU);c.fill();
  c.fillStyle='#fff3ba';c.beginPath();c.arc(-1.3,-1.6,2,0,TAU);c.fill();
  c.restore();
  const light=c.createLinearGradient(16,12,70,76);
  light.addColorStop(0,'#fffdd9b8');light.addColorStop(.37,'#ffeeb200');light.addColorStop(1,'#3b230233');
  c.fillStyle=light;c.beginPath();c.arc(48,48,34,0,TAU);c.fill();
  return bitmap;
 }
 const coinFront=makeCoinSprite(),coinBack=makeCoinSprite(true);
 function goldenCoin(x,y,r,phase,opacity=1,glint=false){
  ctx.save();
  ctx.globalAlpha*=clamp(opacity,0,1);
  ctx.translate(x,y);
  ctx.rotate(Math.sin(phase*.41)*.17);
  const face=Math.cos(phase),flatten=Math.max(.15,Math.abs(face));
  ctx.scale(flatten,1);
  ctx.shadowColor='#ffd45a';ctx.shadowBlur=6;
  ctx.drawImage(face>=0?coinFront:coinBack,-r,-r,2*r,2*r);
  // Brilho pontual desloca-se sobre o metal ao rodar.
  if(glint&&flatten>.38){
   ctx.shadowColor='#fffbd7';ctx.shadowBlur=11;
   ctx.fillStyle='#fffbd0';
   ctx.beginPath();ctx.arc(-r*.38,-r*.43,Math.max(.65,r*.12),0,TAU);ctx.fill();
  }
  ctx.restore();
 }
 const sparkles=[];
 const MAX_SPARKLES=85;
 function addSparkle(x,y,t,life=480){
  if(sparkles.length>=MAX_SPARKLES)sparkles.shift();
  sparkles.push({x,y,t,life,size:1.4+Math.random()*2.5,phase:Math.random()*TAU});
 }
 function shower(points,t,final=false){
  if(reduced)return;
  const base=final?9:7,n=Math.min(5,points.length);
  for(let p=0;p<n;p++){
   const pos=points[p];
   for(let i=0;i<base;i++){
    if(coins.length>=MAX_COINS)coins.shift();
    const angle=(Math.random()-.5)*2.3;
    coins.push({
     x:pos.x+(Math.random()-.5)*23,y:pos.y+(Math.random()-.5)*13,
     vx:Math.sin(angle)*(final?160:123),vy:-(final?215:155)-Math.random()*100,
     g:245+Math.random()*90,born:t+p*(final?48:75)+Math.random()*115,
     life:final?2450:2050,r:10.1+Math.random()*8.2,
     spin:Math.random()*TAU,spinV:4.1+Math.random()*7.5
    });
    if(i%3===0)addSparkle(pos.x+(Math.random()-.5)*28,pos.y+(Math.random()-.5)*24,t+p*72,520);
   }
  }
 }

 function award(stage,points,t){
  if(!points.length||seen.has(stage))return;
  seen.add(stage);
  shower(points,t);
  coinSound(false);
  g.coinCelebrations.events.sequences++;
 }
 const previousFrame=g.frame.bind(g);
 g.frame=function(t){
  if(!this.spinning&&!this.paused&&!this.superseded&&!document.hidden){
   const base=this.activeBasePaylineAt?.(t);
   // A chuva entra quando a caneta já revelou os símbolos da combinação,
   // em vez de tocar antes de a linha ficar visível.
   if(base&&base.amount>0&&t>=base.start+(this.turbo?470:650))
    award(base,winners(base.win),t);
   const focused=this.sequenceFocusAt?.(t);
   if(focused&&focused.amount>0&&
      t>=focused.start+(focused.preDelay||0)+
        (focused.kind==='line'?Math.min(3,focused.reels?.length||3)*(focused.perReel||0):0))
    award(focused,stagePoints(focused),t);
   const r=this.lastResult;
   if(r?.scatterWin>0&&!r.lineWins?.length&&r!==this._coinScatterSeen){
    this._coinScatterSeen=r;
    const scatter={kind:'scatter'};
    award(scatter,stagePoints(scatter),t);
   }
   const final=this.v04Celebration;
   if(final&&t>=final.start&&t<=final.end&&!seen.has(final)){
    seen.add(final);
    const wins=this.lastResult?.lineWins||[];
    const points=wins.flatMap(w=>winners(w)).slice(0,14);
    shower(points.length?points:[point(2,1),point(1,1),point(3,1)],t,true);
    this.coinCelebrations.events.finales++;
    // A fanfarra no effects.js chama coinCascade; não tocar duas vezes.
   }
  }
  return previousFrame(t);
 };
 const previousDrawFx=g.drawFx.bind(g);
 g.drawFx=function(t){
  previousDrawFx(t);
  if(document.hidden||this.paused)return;
  for(let i=coins.length-1;i>=0;i--){
   const c=coins[i],age=t-c.born;
   if(age<0)continue;
   if(age>c.life){coins.splice(i,1);continue;}
   const sec=age/1000,x=c.x+c.vx*sec,y=c.y+c.vy*sec+.5*c.g*sec*sec;
   if(x<18||x>542||y<235||y>750)continue;
   const opacity=Math.min(1,age/85,Math.pow(1-age/c.life,.65));
   goldenCoin(x,y,c.r,c.spin+c.spinV*sec,opacity,i%3===0);
  }
  // Centelhas minúsculas e rápidas acompanham a abertura dos prémios.
  for(let i=sparkles.length-1;i>=0;i--){
   const p=sparkles[i],age=t-p.t;
   if(age<0)continue;
   if(age>p.life){sparkles.splice(i,1);continue;}
   const op=Math.sin(Math.PI*age/p.life);
   ctx.save();
   ctx.globalAlpha=op;
   ctx.translate(p.x+Math.sin(p.phase+age*.003)*5,p.y-age*.02);
   ctx.rotate(Math.PI/4);
   ctx.shadowColor='#fff3a1';ctx.shadowBlur=11;
   ctx.strokeStyle='#fff6d2';ctx.lineWidth=1.3;
   ctx.beginPath();ctx.moveTo(-p.size,0);ctx.lineTo(p.size,0);
   ctx.moveTo(0,-p.size);ctx.lineTo(0,p.size);ctx.stroke();
   ctx.restore();
  }

 };
 function modalCoins(){
  const panel=document.querySelector('#modal .modal-inner');
  if(!panel||!document.getElementById('collect-bonus')||panel.querySelector('.farao-final-coins'))return;
  const wrap=document.createElement('div');
  wrap.className='farao-final-coins';wrap.setAttribute('aria-hidden','true');
  wrap.style.cssText='position:absolute;inset:0;z-index:1;overflow:hidden;pointer-events:none;border-radius:17px';
  const count=reduced?8:32;
  for(let i=0;i<count;i++){
   const el=document.createElement('i');
   const x=6+Math.random()*88,travel=80+Math.random()*180;
   const size=12+Math.random()*10.5;
   el.style.cssText='position:absolute;left:'+x+'%;top:-8%;width:'+
     size+'px;height:'+size+
     'px;border:1px solid #fff0b8;border-radius:50%;box-shadow:0 0 5px #e6a33a;'+
     'background:radial-gradient(circle at 30% 30%,#fff2ba,#e6ad39 65%,#915619);'+
     'animation:farao-coin-fall '+(1.05+Math.random()*.7)+'s ease-in '+
     (Math.random()*.9)+'s both;--fall:'+travel+'px';
   wrap.appendChild(el);
  }
  panel.appendChild(wrap);
  coinSound(true); // A cascata sonora acompanha o resumo final.
  if(!document.getElementById('farao-coin-style')){
   const st=document.createElement('style');st.id='farao-coin-style';
   st.textContent='@keyframes farao-coin-fall{0%{opacity:0;transform:translateY(-15px) rotate(0)}'+
     '15%{opacity:1}85%{opacity:1}100%{opacity:0;transform:translateY(var(--fall)) rotate(540deg)}}';
   document.head.appendChild(st);
  }
 }
 const previousShowSummary=g.showSummary.bind(g);
 g.showSummary=function(...args){
  const result=previousShowSummary(...args);
  modalCoins();
  return result;
 };
 g.coinCelebrations={
  version:'1.5-real-coin-sample',
  drawGoldenCoin:goldenCoin,
  get sparkles(){return sparkles.length;},
  events:{sequences:0,finales:0},
  get particles(){return coins.length;},
  audioOnlyFx:true,
  mathUnchanged:true
 };
})();
