/* Faraó Eterno — ritual do bónus inspirado na cadência da referência.
 * Arte e som originais; nenhuma mudança de RNG, créditos ou premiação.
 * V2.9.2 | 14-11-2026
 */
(() => {
 'use strict';
 const g=window.__faraoGame;
 const modal=document.getElementById('modal');
 const body=document.getElementById('modal-content');
 const canvas=document.getElementById('canvas');
 if(!g||!modal||!body||!canvas)return;
 const ctx=canvas.getContext('2d');
 const clamp=(v,a=0,b=1)=>Math.max(a,Math.min(b,v));
 const sound=()=>g.audio?.on && g.audio.ctx && !document.hidden;
 const output=()=>g.audio.out||g.audio.ctx.destination;
 const stats={triggers:0,scrolls:0,cardReveals:0,expansionCues:0};
 let flashAt=-1e9,scheduled=false,shuffleToken=0,seenIntro=null,seenPage=-1;

 // Eventos sonoros próprios, ligados ao bus de efeitos existente.
 function sweep(a,b,d,vol=.045,delay=0,type='sine'){
  if(!sound())return;
  try{
   const ac=g.audio.ctx,t=ac.currentTime+delay,o=ac.createOscillator(),v=ac.createGain();
   o.type=type;o.frequency.setValueAtTime(a,t);
   o.frequency.exponentialRampToValueAtTime(Math.max(32,b),t+d);
   v.gain.setValueAtTime(.0001,t);
   v.gain.exponentialRampToValueAtTime(Math.max(.0002,vol),t+.018);
   v.gain.exponentialRampToValueAtTime(.0001,t+d);
   o.connect(v);v.connect(output());
   o.start(t);o.stop(t+d+.03);
   o.onended=()=>{o.disconnect();v.disconnect();};
  }catch(_){}
 }
 function hush(d=.24,vol=.035,cut=1800,delay=0){
  if(!sound())return;
  try{
   const ac=g.audio.ctx,t=ac.currentTime+delay;
   const count=Math.ceil(ac.sampleRate*Math.min(.55,d+.04));
   const buf=ac.createBuffer(1,count,ac.sampleRate),samples=buf.getChannelData(0);
   for(let k=0;k<count;k++)samples[k]=Math.random()*2-1;
   const src=ac.createBufferSource(),filter=ac.createBiquadFilter(),gain=ac.createGain();
   src.buffer=buf;filter.type='lowpass';filter.frequency.setValueAtTime(cut,t);
   filter.frequency.exponentialRampToValueAtTime(Math.max(220,cut*.34),t+d);
   gain.gain.setValueAtTime(.0001,t);
   gain.gain.linearRampToValueAtTime(vol,t+.045);
   gain.gain.exponentialRampToValueAtTime(.0001,t+d);
   src.connect(filter);filter.connect(gain);gain.connect(output());
   src.start(t);src.stop(t+d+.01);
   src.onended=()=>{src.disconnect();filter.disconnect();gain.disconnect();};
  }catch(_){}
 }
 const cue={
  third(){
   // Dobra grave mais três notas crescentes + brilho do terceiro SCATTER.
   sweep(98,47,.62,.085,0,'sine');
   [330,440,660].forEach((f,i)=>sweep(f,f*1.28,.37,.045,i*.16,'triangle'));
   hush(.8,.052,3800,.07);
   sweep(880,1550,.83,.018,.4);
  },
  scroll(){
   hush(.72,.038,2600,0);
   sweep(120,360,.48,.027,.11,'triangle');
   sweep(480,840,.52,.02,.30);
  },
  flip(){
   hush(.105,.022,4200,0);
   sweep(450,840,.085,.009);
  },
  chosen(){
   [392,523.25,659.25,784].forEach((f,i)=>sweep(f,f*.96,.65,.033,i*.12));
   sweep(85,57,1,.063,0,'sine');
   sweep(1176,1568,.48,.018,.20);
  },
  expand(){
   sweep(2100,390,.57,.078,0,'sawtooth');
   hush(.58,.070,5000);
   sweep(90,52,.71,.092,.16);
   sweep(523,784,.43,.038,.27);
  }
 };

 const oldFinish=g.finishSpin.bind(g);
 g.finishSpin=function(...args){
  const item=this.spinObject,activated=!!item?.result?.trigger;
  // A luz arranca antes de a janela aparecer: o 3.º símbolo fica visível.
  if(activated){flashAt=performance.now();stats.triggers++;}
  if(activated&&!item?.purchaseReveal){
   this._ritualDelayUntil=performance.now()+1050;
  }
  const result=oldFinish(...args);
  if(activated&&this.feature)cue.third();
  return result;
 };

 function imageFor(key){
  const loaded=g.images?.[key];
  if(loaded?.src)return loaded.src;
  return 'assets/'+String(key).toLowerCase()+'.svg';
 }
 function makeWelcome(){
  const symbol=g.feature?.symbols?.[0];
  if(!symbol)return;
  shuffleToken++;
  const token=shuffleToken;
  const left=Math.max(1,Number(g.feature.left)||8);
  const alternatives=['10','J','Q','K','A','HORUS','MASK','ANUBIS','KING']
   .filter(x=>x!==symbol);
  // Carta final já decidida pela sessão — animação não faz novo sorteio.
  body.innerHTML=
   '<div class="ritual-ceremony" aria-label="Abertura do pergaminho do bónus">'+
    '<div class="ritual-scroll">'+
     '<div class="ritual-parchment">'+
      '<p class="ritual-eyebrow">OS SEGREDOS DA ETERNIDADE</p>'+
      '<p class="ritual-congrats">O TEMPLO DESPERTOU</p>'+
      '<strong class="ritual-spins">'+left+' GIROS GRÁTIS</strong>'+
      '<p class="ritual-prompt">SÍMBOLO EXPANSIVO</p>'+
      '<div class="ritual-card" id="ritual-card"><img id="ritual-symbol" alt="A escolher símbolo expansivo" src="'+imageFor(alternatives[0]||symbol)+'"></div>'+
      '<p id="ritual-status" class="ritual-status" aria-live="polite">AS CARTAS DO FARAÓ ESTÃO A VIRAR...</p>'+
      '<button type="button" class="btn farao-gold-btn ritual-enter" id="reveal-feature" disabled>AGUARDA A REVELAÇÃO</button>'+
     '</div>'+
    '</div>'+
   '</div>';
  modal.classList.add('farao-ritual-modal');
  const img=document.getElementById('ritual-symbol');
  const card=document.getElementById('ritual-card');
  const msg=document.getElementById('ritual-status');
  const btn=document.getElementById('reveal-feature');
  if(!img||!card||!btn)return;
  stats.scrolls++;cue.scroll();
  btn.onclick=()=>{
   if(btn.disabled)return;
   shuffleToken++;
   modal.classList.remove('farao-ritual-modal');
   g.closeModal();
  };
  let tick=0;
  [750,1020,1290,1560,1830].forEach(ms=>{
   setTimeout(()=>{
    if(token!==shuffleToken||modal.classList.contains('hidden')||!g.feature)return;
    card.classList.remove('ritual-picked');
    img.src=imageFor(alternatives[(tick*3+2)%alternatives.length]||symbol);
    card.classList.remove('ritual-turn');void card.offsetWidth;card.classList.add('ritual-turn');
    tick++;cue.flip();
   },ms);
  });
  setTimeout(()=>{
   if(token!==shuffleToken||modal.classList.contains('hidden')||!g.feature)return;
   img.src=imageFor(symbol);img.alt='Símbolo expansivo escolhido pelo motor do jogo';
   card.classList.remove('ritual-turn');card.classList.add('ritual-picked');
   msg.textContent='A FIGURA ESCOLHIDA FOI REVELADA';
   btn.disabled=false;btn.textContent='COMEÇAR FREE SPINS';
   stats.cardReveals++;cue.chosen();
  },2230);
 }
 const priorWelcome=g.showFeatureWelcome.bind(g);
 g.showFeatureWelcome=function(...args){
  if(this._ritualDelayUntil && performance.now()<this._ritualDelayUntil){
   if(!scheduled){
    scheduled=true;
    setTimeout(()=>{
     scheduled=false;
     if(!this.superseded&&this.feature&&!this.spinning&&!this._welcomeShown)
      this.showFeatureWelcome();
    },Math.max(100,this._ritualDelayUntil-performance.now()+20));
   }
   return;
  }
  priorWelcome(...args);
  if(this.feature&&this._welcomeShown&&!modal.classList.contains('hidden'))
   makeWelcome();
 };
 const priorClose=g.closeModal.bind(g);
 g.closeModal=function(...args){
  shuffleToken++;
  modal.classList.remove('farao-ritual-modal');
  return priorClose(...args);
 };

 // Brilho por cima das pirâmides sem tapar a arte nem os resultados.
 function halo(x,y,size,t,power=1){
  const wave=.86+.14*Math.sin(t/115);
  ctx.save();ctx.globalCompositeOperation='screen';
  const radius=size*(.78+.12*wave);
  const grad=ctx.createRadialGradient(x,y,3,x,y,radius);
  grad.addColorStop(0,'rgba(255,255,226,'+(power*.32)+')');
  grad.addColorStop(.3,'rgba(255,220,106,'+(power*.23)+')');
  grad.addColorStop(.7,'rgba(255,156,34,'+(power*.12)+')');
  grad.addColorStop(1,'rgba(255,132,27,0)');
  ctx.fillStyle=grad;ctx.fillRect(x-radius,y-radius,radius*2,radius*2);
  ctx.strokeStyle='rgba(255,236,143,'+(power*.67)+')';
  ctx.lineWidth=2.1;ctx.shadowColor='#ffe0a4';ctx.shadowBlur=17*power;
  const a=t/420;ctx.beginPath();ctx.arc(x,y,size*.45,a,a+Math.PI*1.25);ctx.stroke();
  const spark=.5+.5*Math.sin(t/78+x*.33);
  const sy=y-size*.36;
  ctx.fillStyle='rgba(255,248,199,'+(power*spark*.92)+')';
  ctx.fillRect(x-2,sy-17,4,34);ctx.fillRect(x-17,sy-2,34,4);
  ctx.restore();
 }
 function drawPyramids(t){
  const R=g.visualV11?.layout;if(!R)return;
  const points=[];
  const spin=g.spinObject;
  if(spin&&g.spinning){
   for(let c=0;c<5;c++){
    if(!spin.stopped?.[c])continue;
    for(let row=0;row<3;row++)if(spin.grid?.[c]?.[row]==='TOMB')
     points.push([c,row]);
   }
  }else if(g.lastResult?.trigger && t-flashAt<3700){
   for(let c=0;c<5;c++)
    for(let row=0;row<3;row++)if(g.grid?.[c]?.[row]==='TOMB')
     points.push([c,row]);
  }
  if(points.length<2)return;
  const recently=t-flashAt;
  const burst=recently>=0&&recently<1700 ? 1+1.5*(1-clamp(recently/1700)):1;
  const intensity=(points.length>=3?1.0:.65)*burst;
  for(const [col,row] of points){
   halo(R.left+col*R.step+R.cw*.5,
        R.top+row*R.gh+R.gh*.5,
        Math.min(R.cw,R.gh)*.8,t,intensity);
  }
  if(points.length>=3&&recently>=0&&recently<1150){
   const p=1-clamp(recently/1150);
   ctx.save();ctx.globalCompositeOperation='screen';
   ctx.fillStyle='rgba(255,241,173,'+(.21*p)+')';
   ctx.fillRect(R.left,R.top,5*R.step,R.gh*3);
   ctx.restore();
  }
 }
 function drawExpansions(t){
  const reveal=g.reveal,stages=reveal?.stages||[];
  const R=g.visualV11?.layout;
  if(!R||g.spinning||!g.lastWasFree)return;
  for(const stage of stages){
   if(stage.kind!=='expansion')continue;
   const when=stage.start+(stage.preDelay||0);
   if(t>=when && !stage._ritualSoundPlayed){
    stage._ritualSoundPlayed=true;
    if(t-when<1400){cue.expand();stats.expansionCues++;}
   }
   if(t<when||t>stage.end)continue;
   const alpha=clamp((t-when)/350)*clamp((stage.end-t)/400);
   ctx.save();ctx.globalCompositeOperation='screen';
   stage.reels?.forEach(reel=>{
    const x=R.left+reel*R.step;
    const pulse=.7+.3*Math.sin(t/90+reel);
    const left=ctx.createLinearGradient(x,0,x+R.cw,0);
    left.addColorStop(0,'rgba(255,222,117,'+(alpha*pulse*.70)+')');
    left.addColorStop(.12,'rgba(255,235,161,'+(alpha*.08)+')');
    left.addColorStop(.88,'rgba(255,235,161,'+(alpha*.08)+')');
    left.addColorStop(1,'rgba(255,222,117,'+(alpha*pulse*.70)+')');
    ctx.fillStyle=left;ctx.fillRect(x,R.top,R.cw,R.gh*3);
   });
   ctx.restore();
  }
 }
 function introPages(t){
  const intro=g.featureIntro;
  if(!intro||t<intro.start||t>intro.end)return;
  if(seenIntro!==intro){seenIntro=intro;seenPage=-1;}
  const lead=600,hold=1250,turns=intro.turns||4;
  const len=Math.max(1,(intro.end-intro.start-lead-hold)/turns);
  const page=Math.min(turns,Math.floor(Math.max(0,t-intro.start-lead)/len));
  if(page>seenPage){if(seenPage>=0)cue.flip();seenPage=page;}
 }
 const oldDraw=g.drawFx.bind(g);
 g.drawFx=function(t){
  oldDraw(t);
  if(!document.hidden && !this.superseded){
   drawPyramids(t);drawExpansions(t);introPages(t);
  }
 };
 g.bonusRitual={version:'2.9.2',events:stats,mathUnchanged:true};
 document.documentElement.dataset.faraoBonusRitual='2.9.2';
})();