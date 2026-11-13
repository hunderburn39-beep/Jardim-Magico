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
 const oldWelcome=game.showFeatureWelcome.bind(game);
 game.showFeatureWelcome=function(){
  const f=this.feature;
  if(!f||!api.faces.includes(f.superMultiplier))return oldWelcome();
  if(this.spinning||this._welcomeShown)return;
  this._welcomeShown=true;this._welcome=true;this._awaitBonusStart=true;
  const face=Number.isInteger(f.superFace)?f.superFace:api.faces.indexOf(f.superMultiplier);
  const already=!!this.ledger.feature.superRollSeen;
  const sides=api.faces.map((m,i)=>
   '<span class="super-die-side super-face-'+i+'">×'+m+'</span>').join('');
  this.openModal('SUPER SCATTER!', '<div class="farao-ceremony super-ceremony">'+
   '<div class="super-royal-portrait"><img src="assets/super-pharaoh.svg" alt="O Faraó dourado com coroa azul e serpente sagrada"></div>'+
   '<p class="super-intro">As três pirâmides despertaram o Faraó!</p>'+
   '<p class="farao-spins-label">8 FREE SPINS · MULTIPLICADOR REAL</p>'+
   '<div class="super-dice-area"><div class="super-die" id="super-die" role="img" aria-label="Dado de seis faces">'+sides+'</div></div>'+
   '<div id="super-dice-status" class="super-dice-status" role="status" aria-live="polite">'+
    (already?'MULTIPLICADOR SORTEADO':'LANÇA O DADO SAGRADO')+'</div>'+
   '<strong id="super-dice-result" class="super-multiplier">'+(already?'×'+f.superMultiplier:'?')+'</strong>'+
   '<button type="button" class="btn farao-gold-btn" id="super-roll">'+
    (already?'COMEÇAR FREE SPINS':'LANÇAR DADO')+'</button>'+
   '</div>',true);
  const die=$('super-die'),btn=$('super-roll'),status=$('super-dice-status'),value=$('super-dice-result');
  if(already)die.style.transform=rotations[face];
  btn.onclick=()=>{
   if(this.ledger.feature.superRollSeen){this.closeModal();return;}
   // O valor já foi sorteado e persistido na criação do bónus:
   // a animação NUNCA sorteia outro número.
   this.ledger.feature.superRollSeen=true;
   this.syncLedger();this.save();
   btn.disabled=true;btn.textContent='A SORTEAR...';
   status.textContent='O FARAÓ LANÇA O DADO';
   value.textContent='✦';
   this.audio?.extra?.();
   die.classList.add('rolling');
   window.setTimeout(()=>{
    if(!die.isConnected)return;
    die.classList.remove('rolling');
    die.style.transform=rotations[face];
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
   '<div class="super-royal-portrait"><img src="assets/super-pharaoh.svg" alt="Faraó de ouro e lápis-lazúli"></div>'+
   '<p class="farao-spins-label">AS TUAS '+b.played+' FREE SPINS</p>'+
   '<div class="super-total-equation">'+
    '<div><small>TOTAL ACUMULADO</small><strong>'+fmt(b.superBaseTotalU)+'</strong></div>'+
    '<span class="super-times">×</span>'+
    '<div><small>DADO SAGRADO</small><strong>×'+b.superMultiplier+'</strong></div>'+
   '</div>'+
   '<p class="farao-spins-label">PRÉMIO FINAL</p>'+
   '<strong class="super-total-prize">'+fmt(b.totalU)+'</strong>'+
   '<p class="farao-bonus-rounds">'+b.played+' RODADAS · '+b.retriggers+' RETRIGGERS</p>'+
   '<button type="button" class="btn farao-gold-btn" id="collect-bonus">RECEBER TESOURO</button>'+
   '</div>',true);
  this.audio?.fanfare?.(3);
  const ack=()=>{
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
 document.documentElement.dataset.superScatter='1.0';
})();
