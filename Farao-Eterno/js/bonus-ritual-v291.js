/* Faraó Eterno — ritual do bónus inspirado na cadência da referência.
 * Arte e som originais; nenhuma mudança de RNG, créditos ou premiação.
 * V2.10.0 | 14-11-2026
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
   sweep(2100,390,.57,.148,0,'sawtooth');
   hush(.58,.132,5000);
   sweep(90,52,.71,.172,.16);
   sweep(523,784,.43,.071,.27);
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
 function makeWelcome(symbolOverride){
  const symbol=symbolOverride||g._ritualTriggerSymbol||g.feature?.symbols?.[0];
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
   // Super Scatter: o pergaminho passa ao dado sem fechar o modal.
   // O bloqueio de fecho antes do sorteio permanece ativo.
   if(g.feature?.superMultiplier && !g.ledger.feature?.superRollSeen){
    g._ritualSwitchToDice=true;
    g._welcomeShown=false;
    try{g.showFeatureWelcome();}finally{g._ritualSwitchToDice=false;}
    return;
   }
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

 // Retirar definitivamente o livro antigo do sorteio. A escolha da figura
 // já pertence à sessão: o pergaminho mostra-a uma única vez.
 const oldQueueIntro=g.queueIntro.bind(g);
 g.queueIntro=function(symbol,from){
  if(!this.feature)return oldQueueIntro(symbol,from);
  this.featureIntro=null;
  // A confirmação do pergaminho/dado passa pelo closeModal original, cujo
  // código antigo tenta reabrir o livro: não voltar a apresentar a figura.
  if(this._ritualClosing||this.feature.played===0)return;
  // Retrigger: em vez do livro anterior, mostrar outro pergaminho com a
  // nova figura que o motor já acrescentou ao bónus.
  if(!this.feature.symbols?.includes(symbol))return;
  this._ritualPendingSymbol=symbol;
  this._ritualPendingAt=Math.max(performance.now(),Number(from)||0);
  this._welcome=true;this._awaitBonusStart=true;this._freeStarted=true;
  const serial=(this._ritualRetriggerSerial||0)+1;
  this._ritualRetriggerSerial=serial;
  const showRetrigger=()=>{
   if(serial!==this._ritualRetriggerSerial||this.superseded||!this.feature)return;
   if(this.spinning||this.ledger.payout||document.hidden||
      performance.now()<this._ritualPendingAt||
      !modal.classList.contains('hidden')){
    setTimeout(showRetrigger,240);
    return;
   }
   this._ritualTriggerSymbol=symbol;
   this._welcomeShown=true;
   this.openModal('NOVO SÍMBOLO EXPANSIVO',
     '<div class="farao-ceremony">A revelar a nova figura...</div>',true);
   if(modal.classList.contains('hidden')){
    this._welcomeShown=false;
    setTimeout(showRetrigger,240);
    return;
   }
   makeWelcome(symbol);
  };
  setTimeout(showRetrigger,Math.max(120,this._ritualPendingAt-performance.now()+30));
 };

 const priorWelcome=g.showFeatureWelcome.bind(g);
 g.showFeatureWelcome=function(...args){
  // O retrigger aguarda o fim das animações para abrir o seu pergaminho.
  if(this._ritualPendingSymbol)return;
  if(this._ritualSwitchToDice){
   priorWelcome(...args);
   return;
  }
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
  const wasAlreadyVisible=!!this._welcomeShown;
  priorWelcome(...args);
  if(wasAlreadyVisible)return;
  // Num reload a meio da animação, a face já persistida não é sorteada de novo.
  if(this.feature&&this._welcomeShown&&!modal.classList.contains('hidden')&&
     !(this.feature.superMultiplier&&this.ledger.feature?.superRollSeen))
   makeWelcome();
 };
 const priorClose=g.closeModal.bind(g);
 g.closeModal=function(...args){
  // O símbolo só pode ser confirmado pelo botão no próprio pergaminho.
  // Uma tentativa de fechar pelo X não salta a revelação.
  if(modal.classList.contains('farao-ritual-modal')&&this._welcomeShown)return;
  const closingBonus=!!(this._welcomeShown&&this.feature&&
    (this.feature.played===0||this._ritualTriggerSymbol||this._ritualPendingSymbol)&&
    (!this.feature.superMultiplier||this.ledger.feature?.superRollSeen));
  shuffleToken++;
  modal.classList.remove('farao-ritual-modal');
  const wasClosing=this._ritualClosing;
  this._ritualClosing=true;
  let result;
  try{result=priorClose(...args);}
  finally{this._ritualClosing=wasClosing;}
  if(closingBonus&&modal.classList.contains('hidden')){
   // A carta já foi revelada. Não há um segundo livro ou segundo sorteio.
   // Libertar a continuação automática do motor após o fecho da janela.
   this.featureIntro=null;
   this._welcome=false;this._awaitBonusStart=false;this._freeStarted=true;
   this._ritualPendingSymbol=null;this._ritualTriggerSymbol=null;
   this._ritualRetriggerSerial=(this._ritualRetriggerSerial||0)+1;
   this.nextActionAt=Math.max(this.nextActionAt,performance.now()+350);
  }
  return result;
 };

 // Luz própria nas pirâmides: segue a silhueta do PNG original.
 // Sem bolas, círculos, órbitas, arcos ou qualquer elemento em rotação.
 let thirdFlashAt=-1e9,thirdFlashCell=null,thirdFlashSpin=null;
 function halo(x,y,size,t,power=1){
  const img=g.images?.TOMB;
  if(!img?.complete||!img.naturalWidth||!img.naturalHeight)return;
  const breath=.88+.12*Math.sin(t*.0045+x*.014);
  const strength=clamp(power*breath,0,3.2);
  // A luz é formada pelo alfa da própria imagem, não por um disco desenhado.
  const scale=Math.min((size*1.34)/img.naturalWidth,
                       (size*1.70)/img.naturalHeight);
  const w=img.naturalWidth*scale,h=img.naturalHeight*scale;
  ctx.save();
  ctx.globalCompositeOperation='screen';
  ctx.globalAlpha=clamp(.08+.075*strength,0,.46);
  ctx.shadowColor='#ffe29a';
  ctx.shadowBlur=13+strength*11;
  ctx.drawImage(img,x-w/2,y-h/2,w,h);
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
   // Terceira pirâmide: uma única explosão de luz exatamente na paragem.
   if(points.length>=3&&spin.result?.trigger&&thirdFlashSpin!==spin){
    thirdFlashSpin=spin;
    thirdFlashCell=points[2].join(':');
    thirdFlashAt=t;
   }
  }else if(g.lastResult?.trigger && t-flashAt<3700){
   for(let c=0;c<5;c++)
    for(let row=0;row<3;row++)if(g.grid?.[c]?.[row]==='TOMB')
     points.push([c,row]);
  }
  if(!points.length)return;
  const elapsed=t-thirdFlashAt;
  const flashPower=elapsed>=0&&elapsed<1100
   ?Math.pow(1-elapsed/1100,1.45):0;
  for(const [col,row] of points){
   const id=col+':'+row;
   // 1ª e 2ª com brilho suave; 3ª faz o clarão branco-dourado.
   const boost=thirdFlashCell===id?flashPower*2.0:flashPower*.27;
   const x=R.left+col*R.step+R.cw*.5;
   const y=R.top+row*R.gh+R.gh*.5;
   halo(x,y,Math.min(R.cw,R.gh)*.8,t,.85+boost);
  }
  // Clarão breve do conjunto, sem aro e sem objeto a mover-se.
  if(points.length>=3&&flashPower>.01){
   ctx.save();ctx.globalCompositeOperation='screen';
   ctx.fillStyle='rgba(255,244,186,'+(.13*flashPower)+')';
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
 g.bonusRitual={version:'2.10.0',events:stats,mathUnchanged:true};
 document.documentElement.dataset.faraoBonusRitual='2.10.0';
})();