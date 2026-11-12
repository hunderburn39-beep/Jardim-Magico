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
 const REEL_X=33, STEP_X=101, CELL_W=99, TOP=284, CELL_H=129, MAX_COINS=105;
 const clamp=(v,lo,hi)=>Math.min(hi,Math.max(lo,v));
 const active=()=>!!(audio.on&&audio.ctx&&!g.paused&&!g.superseded&&!document.hidden);
 // Pequenas pancadas de metal com harmónicos e ruído percussivo filtrado.
 // Tudo no barramento de efeitos: a música continua separada e não é reduzida.
 let lastClatter=-Infinity,noiseContext=null,noiseBuffer=null;
 function coinSound(final=false){
  if(!active())return;
  const now=performance.now();
  if(!final&&now-lastClatter<160)return;
  lastClatter=now;
  const ac=audio.ctx,bus=audio.out||ac.destination;
  const hits=final?38:13,duration=final?2.65:.78;
  try{
   if(noiseContext!==ac){
    noiseContext=ac;noiseBuffer=null;
    if(typeof ac.createBuffer==='function'){
     noiseBuffer=ac.createBuffer(1,Math.round(ac.sampleRate*.075),ac.sampleRate);
     const data=noiseBuffer.getChannelData(0);
     for(let i=0;i<data.length;i++){
      const decay=Math.pow(1-i/data.length,1.6);
      data[i]=(Math.random()*2-1)*decay;
     }
    }
   }
   const start=ac.currentTime+.008;
   for(let i=0;i<hits;i++){
    const when=start+duration*Math.pow(i/Math.max(1,hits-1),.88)+(Math.random()-.5)*.015;
    const base=510+Math.random()*1050+(i%5===0?190:0)-i*(final?4:0);
    const level=(final?.021:.018)*(1-.46*i/hits);
    const env=ac.createGain();
    env.gain.setValueAtTime(.0001,when);
    env.gain.exponentialRampToValueAtTime(level,when+.004);
    env.gain.exponentialRampToValueAtTime(.0001,when+.18);
    const output=typeof ac.createStereoPanner==='function'?ac.createStereoPanner():null;
    if(output){
     output.pan.value=Math.max(-.6,Math.min(.6,(i%5-2)*.23));
     env.connect(output);output.connect(bus);
    }else env.connect(bus);
    for(const [idx,ratio] of [1,1.47,2.35].entries()){
     const osc=ac.createOscillator();
     osc.type=idx===2?'triangle':'sine';
     osc.frequency.setValueAtTime(base*ratio,when);
     osc.frequency.exponentialRampToValueAtTime(base*ratio*(idx===0?.82:.94),when+.16);
     const attenuation=ac.createGain();
     attenuation.gain.value=idx===0?1:idx===1?.38:.12;
     osc.connect(attenuation);attenuation.connect(env);
     osc.start(when);osc.stop(when+.2);
     osc.onended=()=>{try{osc.disconnect();attenuation.disconnect();}catch(_){}
      if(idx===2){try{env.disconnect();output?.disconnect();}catch(_){}}};
    }
    // O ruído curto imita o choque e as arestas das moedas a cair.
    if(noiseBuffer&&i%2===0&&typeof ac.createBiquadFilter==='function'){
     const noise=ac.createBufferSource(),filter=ac.createBiquadFilter(),ng=ac.createGain();
     noise.buffer=noiseBuffer;filter.type='highpass';filter.frequency.value=2600+i%4*380;
     ng.gain.setValueAtTime(.0001,when);
     ng.gain.exponentialRampToValueAtTime(final?.007:.005,when+.003);
     ng.gain.exponentialRampToValueAtTime(.0001,when+.058);
     noise.connect(filter);filter.connect(ng);ng.connect(bus);
     noise.start(when);noise.stop(when+.074);
     noise.onended=()=>{try{noise.disconnect();filter.disconnect();ng.disconnect();}catch(_){}};
    }
   }
  }catch(_){/* A apresentação sonora não pode impedir uma jogada. */}
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
     life:final?2450:2050,r:6.7+Math.random()*5.5,
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
   const size=8+Math.random()*7;
   el.style.cssText='position:absolute;left:'+x+'%;top:-8%;width:'+
     size+'px;height:'+size+
     'px;border:1px solid #fff0b8;border-radius:50%;box-shadow:0 0 5px #e6a33a;'+
     'background:radial-gradient(circle at 30% 30%,#fff2ba,#e6ad39 65%,#915619);'+
     'animation:farao-coin-fall '+(1.05+Math.random()*.7)+'s ease-in '+
     (Math.random()*.9)+'s both;--fall:'+travel+'px';
   wrap.appendChild(el);
  }
  panel.appendChild(wrap);
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
  version:'1.3-larger-gold-coins',
  drawGoldenCoin:goldenCoin,
  get sparkles(){return sparkles.length;},
  events:{sequences:0,finales:0},
  get particles(){return coins.length;},
  audioOnlyFx:true,
  mathUnchanged:true
 };
})();
