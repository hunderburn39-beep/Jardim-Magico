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
 function coinSound(final=false){
  if(!active())return;
  const ac=audio.ctx,bus=audio.out||ac.destination;
  const hits=final?21:9, duration=final?1.9:.78;
  // Clatter metálico irregular com ataques pequenos e decaimento natural.
  // Separado do bus de música; nunca reduz o volume do Pharaoh's Ritual.
  try{
   for(let i=0;i<hits;i++){
    const offset=duration*Math.pow(i/Math.max(1,hits-1),.8);
    const when=ac.currentTime+offset+(Math.random()-.5)*.025;
    const loudness=(final?.014:.010)*(1-.38*i/hits);
    const base=(i%5===0?820:1130)+Math.random()*650;
    const gain=ac.createGain();
    gain.gain.setValueAtTime(.0001,when);
    gain.gain.exponentialRampToValueAtTime(loudness,when+.004);
    gain.gain.exponentialRampToValueAtTime(.0001,when+.095+(i%3)*.013);
    for(const ratio of [1,1.49]){
     const osc=ac.createOscillator();
     osc.type='sine';
     osc.frequency.setValueAtTime(base*ratio,when);
     osc.frequency.exponentialRampToValueAtTime(base*ratio*.84,when+.11);
     osc.connect(gain);osc.start(when);osc.stop(when+.15);
     osc.onended=()=>{
      try{osc.disconnect();}catch(_){}
      if(ratio===1.49)try{gain.disconnect();}catch(_){}
     };
    }
    gain.connect(bus);
   }
   // Um tilintar mais cheio no valor final sem acorde eletrónico básico.
   if(final){
    for(let j=0;j<5;j++){
     const when=ac.currentTime+.27+j*.17;
     const osc=ac.createOscillator(),env=ac.createGain();
     osc.type='triangle';
     osc.frequency.setValueAtTime(340+j*54,when);
     osc.frequency.exponentialRampToValueAtTime(310+j*45,when+.19);
     env.gain.setValueAtTime(.0001,when);
     env.gain.exponentialRampToValueAtTime(.016,when+.012);
     env.gain.exponentialRampToValueAtTime(.0001,when+.37);
     osc.connect(env);env.connect(bus);osc.start(when);osc.stop(when+.4);
     osc.onended=()=>{try{osc.disconnect();env.disconnect();}catch(_){}};
    }
   }
  }catch(_){/* Sem contexto áudio funcional: nunca bloquear o jogo. */}
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
 function shower(points,t,final=false){
  if(reduced){return;}
  const base=final?7:5, n=Math.min(5,points.length);
  for(let p=0;p<n;p++){
   const pos=points[p];
   for(let i=0;i<base;i++){
    if(coins.length>=MAX_COINS)coins.shift();
    const angle=(Math.random()-.5)*2.3;
    coins.push({
     x:pos.x+(Math.random()-.5)*23,y:pos.y+(Math.random()-.5)*13,
     vx:Math.sin(angle)*(final?155:120),vy:-(final?205:145)-Math.random()*95,
     g:240+Math.random()*90,born:t+p*(final?48:75)+Math.random()*115,
     life:final?2250:1800,r:3+Math.random()*3.2,spin:Math.random()*TAU,
     spinV:2.4+Math.random()*6
    });
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
   if(base&&base.amount>0)award(base,winners(base.win),t);
   const focused=this.sequenceFocusAt?.(t);
   if(focused&&t>=focused.start+(focused.preDelay||0)&&focused.amount>0)
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
   const s=age/1000,x=c.x+c.vx*s,y=c.y+c.vy*s+.5*c.g*s*s;
   if(x<18||x>542||y<235||y>750)continue;
   ctx.save();
   ctx.globalAlpha=Math.min(1,age/100,1-(age/c.life)**2);
   ctx.translate(x,y);ctx.rotate(c.spin+c.spinV*s);
   ctx.shadowColor='#eeb646';ctx.shadowBlur=6;
   ctx.fillStyle='#8d4d13';ctx.strokeStyle='#ffe8a0';ctx.lineWidth=1.3;
   ctx.beginPath();ctx.ellipse(0,0,c.r,c.r*.76,0,0,TAU);ctx.fill();ctx.stroke();
   ctx.beginPath();ctx.ellipse(-c.r*.13,-c.r*.17,c.r*.57,c.r*.42,0,0,TAU);
   ctx.fillStyle='#ffd978';ctx.fill();
   ctx.lineWidth=.7;ctx.strokeStyle='#c58b34';ctx.stroke();
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
   el.style.cssText='position:absolute;left:'+x+'%;top:-8%;width:'+
     (5+Math.random()*6)+'px;height:'+(5+Math.random()*6)+
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
  version:'1.0-coins-from-winning-lines',
  events:{sequences:0,finales:0},
  get particles(){return coins.length;},
  audioOnlyFx:true,
  mathUnchanged:true
 };
})();
