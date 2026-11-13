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
   '<div class="super-royal-portrait"><img src="data:image/webp;base64,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" alt="O Faraó dourado com coroa azul e serpente sagrada"></div>'+
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
   this._superRolling=true;
   die.classList.add('rolling');
   window.setTimeout(()=>{
    this._superRolling=false;
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
   '<div class="super-royal-portrait"><img src="data:image/webp;base64,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" alt="Faraó de ouro e lápis-lazúli"></div>'+
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
