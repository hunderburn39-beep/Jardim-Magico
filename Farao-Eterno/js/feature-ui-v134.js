/* Faraó Eterno — interface da cerimónia, cofre e Free Spins.
 * Camada visual/UX; o motor de pagamentos continua em session.js.
 */
(() => {
 'use strict';
 const g=window.__faraoGame, S=window.FaraoSession;
 const $=id=>document.getElementById(id);
 const modal=$('modal'),body=$('modal-content');
 if(!g||!S||!modal)return;
 const fmt=value=>Number(value).toLocaleString('pt-PT',
   {minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 const now=()=>performance.now();
 const starting=!!(g.feature&&g.feature.played===0);
 g._awaitBonusStart=starting;
 g._freeStarted=!!(g.feature&&g.feature.played>0);
 g._welcome=!!(starting&&!g.ledger.feature?.introSeen&&!g.ledger.feature?.purchaseRevealPending);
 g._freeTurbo=false;
 g._savedTurbo=!!g.ledger.settings.turbo;
 g._autoDispatch=false;
 g._summaryRequested=false;
 if(!g.feature&&g.ledger.last?.bonusDone&&
    g.ledger.last.id!==g.ledger.lastSummarySeenId){
   g.pendingSummary=g.ledger.last.bonusDone;
 }
 const oldOpen=g.openModal.bind(g),oldClose=g.closeModal.bind(g);
 g.openModal=function(title,html,themed=false){
   oldOpen(title,html);
   if(!modal.classList.contains('hidden'))
    modal.classList.toggle('farao-theme',themed);
 };
 g.closeModal=function(){
   const reveal=this._welcomeShown;
   this._welcomeShown=false;
   oldClose();
   modal.classList.remove('farao-theme');
   if(reveal&&this.feature){
     this._welcome=false;this._awaitBonusStart=true;
     this.ledger.feature.introSeen=true;
     this.featureIntro=null;
     this.queueIntro(this.feature.symbols[0],
       Math.max(now()+240,this.reveal?.end||0));
     this.save();
   }
 };
 g.showFeatureWelcome=function(){
   if(!this.feature||this.spinning||this._welcomeShown)return;
   this._welcomeShown=true;this._welcome=true;this._awaitBonusStart=true;
   this.openModal('PARABÉNS!',`<div class="farao-ceremony">
    <div class="farao-seal" aria-hidden="true">✦</div>
    <p class="farao-welcome">Você ganhou acesso ao cofre do Faraó Eterno.<br>Boa sorte!</p>
    <p class="farao-spins-label">FREE SPINS</p>
    <strong class="farao-spins-number">${this.feature.left}</strong>
    <button type="button" id="reveal-feature" class="btn farao-gold-btn">REVELAR CARTA</button>
   </div>`,true);
   $('reveal-feature').onclick=()=>this.closeModal();
 };
 g.showSummary=function(){
   if(!this._summaryRequested||!this.pendingSummary||
      this.ledger.payout||this.spinning)return;
   this._summaryRequested=false;
   const b=this.pendingSummary;this.pendingSummary=null;
   this._freeTurbo=false;this.turbo=this._savedTurbo;
   this._freeStarted=false;this._awaitBonusStart=false;
   this.openModal('TESOURO DO FARAÓ',`<div class="farao-ceremony">
    <div class="farao-seal" aria-hidden="true">✦</div>
    <p class="farao-spins-label">TOTAL DAS FREE SPINS</p>
    <strong class="farao-spins-number">${fmt(S.credits(b.totalU))}</strong>
    <p class="farao-bonus-rounds">${b.played} RODADAS</p>
    <button type="button" class="btn farao-gold-btn" id="collect-bonus">CONTINUAR</button>
   </div>`,true);
   this.audio?.fanfare?.(3);
   const acknowledged=()=>{
     this.ledger.lastSummarySeenId=this.ledger.last?.id;
     this.save();this.closeModal();
   };
   $('collect-bonus').onclick=acknowledged;
   // Se o utilizador tocar no X, também marcar este resumo como visto.
   this._summaryAck=acknowledged;
 };
 const previousClose=g.closeModal.bind(g);
 g.closeModal=function(){
   const closingSummary=!!this._summaryAck;
   this._summaryAck=null;
   if(closingSummary){this.ledger.lastSummarySeenId=this.ledger.last?.id;this.save();}
   previousClose();
 };
 g.showCreditMenu=function(){
   if(this.superseded||this.spinning)return;
   const locked=!!(this.feature||this.ledger.pending||this.ledger.payout||this.pendingSummary);
   this.openModal('COFRE DO FARAÓ',`<div class="farao-ceremony">
    <p class="farao-spins-label">SALDO DO JOGO</p>
    <strong class="farao-spins-number">${fmt(this.balance)}</strong>
    <div class="farao-popup-buttons">
     <button class="btn farao-gold-btn" id="credit-deposit" ${locked?'disabled':''}>DEPÓSITO</button>
     <button class="btn farao-gold-btn" id="credit-withdraw" ${locked?'disabled':''}>LEVANTAMENTO</button>
    </div>
    ${locked?'<p class="farao-note">Termina o bónus para movimentar o saldo.</p>':''}
   </div>`,true);
   $('credit-deposit').onclick=()=>this.showCreditAction('deposit');
   $('credit-withdraw').onclick=()=>this.showCreditAction('withdraw');
 };
 g.showCreditAction=function(operation){
   if(this.feature||this.spinning||this.ledger.pending||this.ledger.payout||this.pendingSummary)return;
   const deposit=operation==='deposit';
   this.openModal(deposit?'DEPÓSITO':'LEVANTAMENTO',`<div class="farao-ceremony">
    <p class="farao-spins-label">VALORES DO JOGO</p>
    <p>Saldo: <b>${fmt(this.balance)}</b></p>
    <label class="farao-credit-label" for="credit-amount">Montante em euros (€)</label>
    <input id="credit-amount" class="farao-credit-input" type="number"
       inputmode="decimal" min="0.01" step="0.01"
       max="${deposit?10000:Math.min(10000,this.balance)}" placeholder="10,00">
    <p class="farao-note" id="credit-error" role="status"></p>
    <div class="farao-popup-buttons">
     <button class="btn farao-gold-btn" id="credit-confirm">${deposit?'DEPOSITAR':'LEVANTAR'}</button>
     <button class="btn" id="credit-back">VOLTAR</button>
    </div>
   </div>`,true);
   $('credit-back').onclick=()=>this.showCreditMenu();
   $('credit-confirm').onclick=()=>{
     try{
       const v=Number(String($('credit-amount').value).replace(',','.'));
       S.adjustCredits(this.ledger,operation,v);
       this.syncLedger();this.save();this.showCreditMenu();
     }catch(err){$('credit-error').textContent=err.message||'Valor inválido';}
   };
 };
 g.showBetMultiplier=function(){
   if(this.superseded||this.spinning)return;
   const locked=!!(this.feature||this.ledger.pending||this.ledger.payout||this.pendingSummary);
   const current=this.ledger.betMultiplier||1,base=this.bet/current;
   const options=[1,2,3,4,5].map(m=>`<button type="button"
      class="btn farao-multi-btn ${current===m?'selected':''}"
      data-multi="${m}" ${locked||base*m>10.000001?'disabled':''}>
      ${m}× <small>${fmt(base*m)}</small>
    </button>`).join('');
   this.openModal('MULTIPLICADOR DE APOSTA',`<div class="farao-ceremony">
    <p class="farao-spins-label">APOSTA ATUAL</p>
    <strong class="farao-spins-number">${fmt(this.bet)}</strong>
    <div class="farao-multipliers">${options}</div>
    <p class="farao-note">Aposta máxima: 10 € por jogada.
      ${locked?'Alterações indisponíveis durante as Free Spins.':''}</p>
   </div>`,true);
   body.querySelectorAll('[data-multi]').forEach(b=>b.onclick=()=>{
     try{
       S.setBetMultiplier(this.ledger,Number(b.dataset.multi));
       this.syncLedger();this.save();this.closeModal();
     }catch(err){this.notice(err.message||'Multiplicador indisponível',1100,'red');}
   });
 };
 // As caixas SALDO e APOSTA mantêm os desenhos e passam a ser clicáveis.
 const oldStatus=g.statusBar.bind(g);
 g.statusBar=function(t){
   oldStatus(t);
   const r=this.visualV11?.layout;
   if(!r||!this.ready||this.superseded)return;
   const y=r.top+r.gh*3+14;
   if(!this.spinning){
     this.action(28,y,164,72,()=>this.showCreditMenu(),'Abrir cofre e saldo');
     this.action(198,y,164,72,()=>this.showBetMultiplier(),'Multiplicador de aposta');
   }
 };
 const oldFinish=g.finishSpin.bind(g);
 g.finishSpin=function(){
   if(!this.spinObject)return;
   const wasFree=!!this.spinObject.free;
   const triggered=!!this.spinObject.result?.trigger;
   const purchasedSpin=!!this.spinObject.purchaseReveal;
   oldFinish();
   if(wasFree){
     this.v04Celebration=null;
     if(this.pendingSummary)this._awaitBonusStart=false;
   }else if(triggered&&this.feature){
     this.featureIntro=null;this.v04Celebration=null;
     this._awaitBonusStart=true;this._freeStarted=false;this._freeTurbo=false;
     this._welcome=true;
     this.nextActionAt=Math.max(now()+(purchasedSpin?1350:400),this.reveal?.end||0);
     if(purchasedSpin){
       // Mostrar a combinação de ativação completa antes da cerimónia.
       window.setTimeout(()=>{
         if(this.feature&&!this.spinning&&!this.superseded&&
            document.getElementById('modal')?.classList.contains('hidden')){
              this.showFeatureWelcome();
         }
       },1250);
     }else this.showFeatureWelcome();
   }
 };
 const oldBuy=g.confirmBuyBonus.bind(g);
 g.confirmBuyBonus=function(mode='regular'){
   const before=!!this.feature;
   oldBuy(mode);
   if(!before&&this.feature){
     this.featureIntro=null;this.v04Celebration=null;
     this._awaitBonusStart=true;this._freeStarted=false;this._freeTurbo=false;
     if(this.spinning&&this.spinObject?.purchaseReveal){
       this._welcome=false;
       return;
     }
     // Apenas recuperação de uma compra sem rotação visual pendente.
     this._welcome=true;
     this.nextActionAt=now()+450;
     this.showFeatureWelcome();
   }
 };
 const oldSync=g.syncLedger.bind(g);
 g.syncLedger=function(){
   oldSync();
   // Free Spins a velocidade estável, mesmo que o Turbo esteja guardado.
   this._savedTurbo=!!this.ledger.settings.turbo;
   if(this.feature)this.turbo=false;
 };
 const oldSave=g.save.bind(g);
 g.save=function(){
   const active=this.turbo;
   // Preservar a preferência do jogo base sem gravar o modo lento como Turbo.
   if(this.feature)this.turbo=this._savedTurbo;
   oldSave();
   if(!this.feature)this._savedTurbo=!!this.ledger.settings.turbo;
   this.turbo=active;
 };
 const oldStart=g.startSpin.bind(g);
 g.startSpin=function(){
   const manual=!this._autoDispatch;
   if(this.pendingSummary){
     if(manual&&now()>=this.nextActionAt&&!this.ledger.payout){
       this._summaryRequested=true;this.showSummary();
     }
     return;
   }
   if(this.feature&&this._awaitBonusStart){
     if(this._welcome){
       if(manual)this.showFeatureWelcome();
       return;
     }
     if(this.featureIntro&&now()<this.featureIntro.end)return;
     // O livro inicia as Free Spins automaticamente no fim da revelação.
     // Não exigir um segundo toque no botão GIRAR.
     if(now()<this.nextActionAt)return;
     this._awaitBonusStart=false;this._freeStarted=true;
     return oldStart();
   }
   if(manual&&this.paused){oldStart();return;}
   // GIRAR durante as Free Spins nunca altera a velocidade acidentalmente.
   // O ritmo é controlado pela animação normal do bónus.
   if(this.feature&&manual&&(this._freeStarted||this.feature.played>0))return;
   oldStart();
 };
 const originalAuto=g.autoSpin.bind(g);
 g.autoSpin=function(){
   if(this.pendingSummary)return;
   if(this._awaitBonusStart){
     // Enquanto a carta roda, aguardar; depois iniciar sem toque manual.
     if(this._welcome||this.paused||this.spinning||
        (this.featureIntro&&now()<this.featureIntro.end)||
        now()<this.nextActionAt)return;
     this._autoDispatch=true;
     try{this.startSpin();}finally{this._autoDispatch=false;}
     return;
   }
   this._autoDispatch=true;
   try{originalAuto();}finally{this._autoDispatch=false;}
 };
 // Em modo retrigger, continuar a mostrar a carta automaticamente.
 // Após o último giro, só o botão central abre o resumo do bónus.
 document.documentElement.dataset.faraoFeatureUi='1.3.55';
})();
