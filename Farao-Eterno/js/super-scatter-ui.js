/* Faraó Eterno — cerimónia Super Scatter: 6 faces, Faraó e prémio final. */
(function(){
 'use strict';
 const game=window.__faraoGame,api=window.FaraoSuperScatter,session=window.FaraoSession;
 if(!game||!api||!session)return;
 const $=id=>document.getElementById(id);
 const fmt=u=>session.credits(u).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 const rotations=['rotateX(0deg) rotateY(0deg)',
  'rotateY(-90deg)','rotateY(180deg)','rotateY(90deg)',
  'rotateX(-90deg)','rotateX(90deg)'];
 const oldClose=game.closeModal.bind(game);
 game.closeModal=function(){
  if(this._welcomeShown&&this.feature?.superMultiplier&&
    (!this.ledger.feature?.superRollSeen||this._superRolling))return;
  $('close-modal').style.visibility='';
  oldClose();
 };
 const oldWelcome=game.showFeatureWelcome.bind(game);
 game.showFeatureWelcome=function(){
  const f=this.feature;
  if(!f||!api.faces.includes(f.superMultiplier))return oldWelcome();
  if(this.spinning||this._welcomeShown)return;
  this._welcomeShown=true;this._welcome=true;this._awaitBonusStart=true;
  $('close-modal').style.visibility='hidden';
  const face=Number.isInteger(f.superFace)?f.superFace:api.faces.indexOf(f.superMultiplier);
  const already=!!this.ledger.feature.superRollSeen;
  const sides=api.faces.map((m,i)=>
   '<span class="super-die-side super-face-'+i+'">×'+m+'</span>').join('');
  this.openModal('SUPER SCATTER!', '<div class="farao-ceremony super-ceremony">'+
   '<div class="super-royal-portrait"><img src="assets/super-reference.webp?v=original-reference-1" alt="O Faraó dourado com coroa azul e serpente sagrada"></div>'+
   '<p class="super-intro">As três pirâmides despertaram o Faraó!</p>'+
   '<p class="farao-spins-label">8 FREE SPINS · MULTIPLICADOR DO BÓNUS</p>'+
   '<div class="super-dice-area"><div class="super-die" id="super-die" role="img" aria-label="Dado de seis faces">'+sides+'</div>'+'<div class="super-die-docked" id="super-die-docked" aria-hidden="true">'+(already?'×'+f.superMultiplier:'?')+'</div></div>'+
   '<div id="super-dice-status" class="super-dice-status" role="status" aria-live="polite">'+
    (already?'MULTIPLICADOR SORTEADO':'LANÇA O DADO SAGRADO')+'</div>'+
   '<strong id="super-dice-result" class="super-multiplier">'+(already?'×'+f.superMultiplier:'?')+'</strong>'+
   '<button type="button" class="btn farao-gold-btn" id="super-roll">'+
    (already?'COMEÇAR FREE SPINS':'LANÇAR DADO')+'</button>'+
   '</div>',true);
  const die=$('super-die'),docked=$('super-die-docked'),area=die.parentElement,
    btn=$('super-roll'),status=$('super-dice-status'),value=$('super-dice-result');
  if(already)die.style.transform=rotations[face];
  btn.onclick=()=>{
   if(this.ledger.feature.superRollSeen){this.closeModal();return;}
   // O valor já foi sorteado e persistido na criação do bónus:
   // a animação NUNCA sorteia outro número.
   // Guardar 'dado visto' apenas QUANDO a face aparece. Se o Safari
   // fechar durante a rotação, o jogador pode repetir a animação, mas
   // a face matemática já escolhida continua rigorosamente a mesma.
   btn.disabled=true;btn.textContent='A SORTEAR...';
   status.textContent='O FARAÓ LANÇA O DADO';
   value.textContent='✦';
   this.audio?.extra?.();
   this._superRolling=true;
   area.classList.add('is-rolling');
   die.classList.add('rolling');
   window.setTimeout(()=>{
    this._superRolling=false;
    if(!die.isConnected||!this.ledger.feature||
       this.ledger.feature.superMultiplier!==f.superMultiplier)return;
    this.ledger.feature.superRollSeen=true;
    this.syncLedger();this.save();
    die.classList.remove('rolling');
    area.classList.remove('is-rolling');
    die.style.transform=rotations[face];
    docked.textContent='×'+f.superMultiplier;
    value.textContent='×'+f.superMultiplier;
    status.textContent='TODOS OS PRÉMIOS DAS FREE SPINS ×'+f.superMultiplier;
    btn.disabled=false;btn.textContent='COMEÇAR FREE SPINS';
    btn.onclick=()=>this.closeModal();
    this.audio?.fanfare?.(2);
   },1900);
  };
 };
 const oldSummary=game.showSummary.bind(game);
 game.showSummary=function(){
  const b=this.pendingSummary;
  if(!b||!api.faces.includes(b.superMultiplier))return oldSummary();
  if(!this._summaryRequested||this.ledger.payout||this.spinning)return;
  this._summaryRequested=false;
  this.pendingSummary=null;
  this._freeTurbo=false;this.turbo=this._savedTurbo;
  this._freeStarted=false;this._awaitBonusStart=false;
  this.openModal('TESOURO SUPER SCATTER', '<div class="farao-ceremony super-ceremony super-final">'+
   '<div class="super-royal-portrait"><img src="assets/super-reference.webp?v=original-reference-1" alt="Faraó de ouro e lápis-lazúli"></div>'+
   '<p class="farao-spins-label">AS TUAS '+b.played+' FREE SPINS</p>'+
   '<div class="super-total-equation">'+
    '<div><small>TOTAL ACUMULADO</small><strong>'+fmt(b.superBaseTotalU)+'</strong></div>'+
    '<span class="super-times">×</span>'+
    '<div><small>DADO SAGRADO</small><strong>'+b.superMultiplier+'</strong></div>'+
   '</div>'+
   '<p class="farao-spins-label">PRÉMIO FINAL</p>'+
   '<strong class="super-total-prize" id="super-final-total" aria-label="Prémio final: '+fmt(b.totalU)+'">'+fmt(b.totalU)+'</strong>'+
   '<p class="farao-bonus-rounds">'+b.played+' RODADAS · '+b.retriggers+' RETRIGGERS</p>'+
   '<button type="button" class="btn farao-gold-btn" id="collect-bonus">RECEBER TESOURO</button>'+
   '</div>',true);
  this.audio?.fanfare?.(3);
  // A contagem é visual. O bónus foi pago pela sessão antes desta janela.
  // Nunca chamar settle(), creditNext() ou alterar o cofre neste código.
  const counter=$('super-final-total');
  const reduce=!!window.matchMedia?.('(prefers-reduced-motion: reduce)').matches;
  const totalU=Number.isSafeInteger(b.totalU)&&b.totalU>=0?b.totalU:0;
  let finished=false;
  let raf=0;
  const finishCount=()=>{
   if(finished)return;
   finished=true;
   if(raf)window.cancelAnimationFrame(raf);
   counter.textContent=fmt(totalU);
   counter.classList.add('reached');
   counter.removeAttribute('aria-busy');
  };
  if(!reduce&&totalU>0){
   const started=performance.now(),duration=2050;
   counter.textContent=fmt(0);
   counter.setAttribute('aria-busy','true');
   const step=now=>{
    if(!counter.isConnected){finished=true;return;}
    const progress=Math.min(1,Math.max(0,(now-started)/duration));
    // Início lento, aceleração progressiva, final com desaceleração.
    const eased=progress<.5?2*progress*progress:1-Math.pow(-2*progress+2,2)/2;
    counter.textContent=fmt(Math.min(totalU,Math.floor(totalU*eased)));
    if(progress>=1){finishCount();return;}
    raf=window.requestAnimationFrame(step);
   };
   raf=window.requestAnimationFrame(step);
   // Um toque no valor permite apresentar logo o prémio total.
   counter.addEventListener('pointerdown',finishCount,{once:true});
  }else counter.classList.add('reached');
  const ack=()=>{finishCount();
   this.ledger.lastSummarySeenId=this.ledger.last?.id;
   this.save();this.closeModal();
  };
  $('collect-bonus').onclick=ack;
  this._summaryAck=ack;
 };
 // No Super Scatter o resumo abre sozinho após as animações da última Free Spin.
 // Free Spins clássicas conservam o botão manual de consulta do resultado.
 const oldFinish=game.finishSpin.bind(game);
 game.finishSpin=function(){
  oldFinish();
  if(this.pendingSummary?.superMultiplier)this._summaryRequested=true;
 };
 // Mostrar o multiplicador em curso de forma discreta e acessível.
 const oldStatus=game.statusBar.bind(game);
 game.statusBar=function(t){
  oldStatus(t);
  const m=api.superFactor(this.ledger);
  if(m===1||!this.visualV11?.layout)return;
  const ctx=$('canvas').getContext('2d');
  ctx.save();
  ctx.shadowColor='#eec46b';ctx.shadowBlur=9;
  ctx.fillStyle='#f3d58b';ctx.font='900 14px Georgia,serif';
  ctx.textAlign='center';
  ctx.fillText('SUPER SCATTER ×'+m,280,231);
  ctx.restore();
 };
 // Explicar as seis faces do novo símbolo na tabela de pagamentos.
 const previousInfo=game.openInfo.bind(game);
 game.openInfo=function(tab){
  previousInfo(tab);
  if(tab!=='pays')return;
  const anchor=document.querySelector('.farao-scatter-pay');
  if(!anchor)return;
  const note=document.createElement('section');
  note.className='super-pay-info';
  note.innerHTML='<img src="assets/super-reference.webp?v=original-reference-1" alt="Super Scatter">'+
   '<div><strong>SUPER SCATTER · BÓNUS DO FARAÓ</strong>'+
   '<p>Um SUPER SCATTER com 3 ou mais pirâmides no jogo base dá 8 Free Spins especiais. O dado sorteia ×2, ×3, ×4, ×5, ×8 ou ×10. No fim, multiplica o total acumulado. Retriggers mantêm o dado. O prémio respeita o limite de 5.000× e o fundo disponível.</p></div>';
  anchor.insertAdjacentElement('afterend',note);
 };
 document.documentElement.dataset.superScatter='1.2-original-figure';
})();
