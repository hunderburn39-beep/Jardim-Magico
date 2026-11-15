/* Faraó Eterno — painel de administração de créditos locais.
 * Os PINs só bloqueiam os controlos de interface e não protegem dinheiro real.
 */
(() => {
 'use strict';
 const g=window.__faraoGame,S=window.FaraoSession,E=window.FaraoEconomy;
 if(!g||!S||!E)return;
 const $=id=>document.getElementById(id);
 const fmt=amount=>Number(amount).toLocaleString('pt-PT',
   {minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 const val=key=>fmt(S.credits(key||0));
 const number=raw=>Number(String(raw).trim().replace(',','.'));
 const errors=message=>{const el=$('farao-admin-error');if(el)el.textContent=message||'';};
 const active=()=>!g.superseded&&!g.spinning;
 const field=(id,label,value,type='number',extra='')=>
   '<label class="farao-admin-field" for="'+id+'"><span>'+label+'</span>'+
   '<input id="'+id+'" type="'+type+'" value="'+value+'" '+extra+'></label>';
 const button=(id,text)=>'<button type="button" class="btn farao-gold-btn" id="'+id+'">'+text+'</button>';
 const note='<p class="farao-admin-disclaimer">Simulação local sem movimentar dinheiro real. Os códigos não protegem valores financeiros: podem ser consultados ou alterados no navegador.</p>';
 const status=()=>g.ledger?.economy||E.ensure(g.ledger);
 const save=()=>{g.syncLedger();g.save();};
 const guard=action=>{try{if(!g._adminAuthenticated)throw Error('Entra primeiro com o código de administrador');if(!active())throw Error('A jogada tem de terminar');action();save();return true;}catch(err){errors(err.message||'Operação não permitida');return false;}};
 function isBusy(){return !!(g.spinning||g.ledger.pending||g.ledger.payout||g.feature);}
 function adminPage(){
   if(!g._adminAuthenticated){g.openAdminLogin();return;}
   const e=status(),t=e.totals,profit=e.techDueU+e.merchantDueU,locked=isBusy();
   const heading=(text)=>'<h3 class="farao-admin-heading">'+text+'</h3>';
   const metric=(label,amount)=>'<div class="farao-admin-metric"><span>'+label+'</span><strong>'+val(amount)+'</strong></div>';
   const period=e.periodNumber||1,net=window.FaraoMachineNetwork;
   const dashboard='<div class="farao-admin-panel">'+
     '<p class="farao-admin-top">Cofres e movimentos desta sessão neste navegador.</p>'+
     '<div class="farao-admin-metrics">'+
      metric('Total depósitos',t.depositsU)+
      metric('Total levantamentos',t.withdrawalsU)+
      metric('Fundo disponível de prémios',e.prizeU)+
      metric('Cofre de lucros disponíveis',profit)+
      metric('Lucro do técnico por levantar',e.techDueU)+
      metric('Lucro do comerciante por levantar',e.merchantDueU)+
      metric('Lucro do técnico já levantado',t.techCollectedU)+
      metric('Lucro do comerciante já levantado',t.merchantCollectedU)+
      metric('Apostas/compra de bónus',t.stakesU)+
      metric('Prémios atribuídos',t.winsU)+'</div>'+
     '<p class="farao-admin-note">Período de contadores: <b>'+period+'</b> · Giros pagos: <b>'+g.ledger.stats.paid+'</b> · Free Spins: <b>'+g.ledger.stats.free+'</b> · Bónus: <b>'+g.ledger.stats.features+'</b></p>'+
     heading('Comissões: total obrigatório 100%')+
     field('admin-tech','Comissão do técnico (%)',e.technicianPercent,'number',
       'min="0" max="100" step="1" inputmode="numeric"')+
     '<p class="farao-admin-derived" id="admin-merchant-label">Comerciante: '+e.merchantPercent+'%</p>'+
     button('admin-save-split','GUARDAR COMISSÕES')+
     heading('Taxa da máquina · 85% a 95%')+
     field('admin-level','Perfil de prémios (%)',e.level,'number',
       'min="85" max="95" step="1" inputmode="numeric"')+
     '<p class="farao-admin-note">85%: prémios altos menos frequentes. 95%: mais frequentes. O valor também determina a parcela das apostas enviada ao fundo de prémios. Não é RTP garantido.</p>'+
     button('admin-save-level','GUARDAR TAXA')+
     heading('Fundo de prémios')+
     field('admin-fund','Valor a colocar ou retirar (€)','', 'text',
       'inputmode="decimal" placeholder="Ex.: 100,00"')+
     '<div class="farao-admin-actions">'+button('admin-fund-add','ADICIONAR FUNDO')+
       button('admin-fund-remove','RETIRAR FUNDO')+'</div>'+
     heading('Levantamento dos lucros')+
     field('admin-profit-amount','Valor a levantar (€)','', 'text',
       'inputmode="decimal" placeholder="Ex.: 10,50"')+
     '<div class="farao-admin-actions">'+button('admin-tech-collect','LEVANTAR TÉCNICO')+
       button('admin-merchant-collect','LEVANTAR COMERCIANTE')+'</div>'+
     heading('Alterar códigos')+
     '<p class="farao-admin-note">O código inicial de administração, depósito e levantamento é 12345. Para alterar qualquer um, confirma o código atual do administrador.</p>'+
     '<div class="farao-admin-actions">'+button('admin-change-admin','ADMINISTRADOR')+
       button('admin-change-deposit','DEPÓSITOS')+
       button('admin-change-withdraw','LEVANTAMENTOS')+'</div>'+
     heading('Contadores e relatórios')+
     '<p class="farao-admin-note">Limpar contadores inicia um novo período a zeros. O saldo, os cofres, os créditos de lucro e o histórico de jogadas NÃO são apagados. Os períodos anteriores ficam arquivados.</p>'+
     '<p class="farao-admin-note">Períodos arquivados: '+(e.periods?.length||0)+'</p>'+
     '<div class="farao-admin-actions">'+button('admin-reset-counters','LIMPAR CONTADORES')+
       button('admin-export-report','EXPORTAR RELATÓRIO')+'</div>'+
     (net?net.adminHTML():'')+
     '<p class="farao-admin-note">Alterações de cofres, taxas e códigos são bloqueadas durante uma jogada ou bónus ativo.</p>'+
     (locked?'<p class="farao-admin-warning">Termina as Free Spins para alterar estas definições.</p>':'')+
     '<p id="farao-admin-error" class="farao-admin-error" role="alert"></p>'+
     button('admin-logout','SAIR DA ADMINISTRAÇÃO')+note+'</div>';
   g.openModal('ADMINISTRAÇÃO',dashboard,true);
   const tech=$('admin-tech'),merchant=$('admin-merchant-label');
   tech.oninput=()=>{const n=number(tech.value);merchant.textContent='Comerciante: '+(Number.isInteger(n)&&n>=0&&n<=100?100-n:'—')+'%';};
   $('admin-save-split').onclick=()=>guard(()=>{
     E.setSplit(g.ledger,number(tech.value));adminPage();
   });
   $('admin-save-level').onclick=()=>guard(()=>{
     E.setLevel(g.ledger,number($('admin-level').value));adminPage();
   });
   for(const op of ['add','remove'])$('admin-fund-'+op).onclick=()=>guard(()=>{
     E.adjustFund(g.ledger,op,$('admin-fund').value);adminPage();
   });
   for(const role of ['technician','merchant'])$('admin-'+(role==='technician'?'tech':'merchant')+'-collect').onclick=()=>guard(()=>{
     E.collect(g.ledger,role,$('admin-profit-amount').value);adminPage();
   });
   for(const role of ['admin','deposit','withdraw'])
     $('admin-change-'+role).onclick=()=>g.openAdminPinChange(role);
   $('admin-reset-counters').onclick=()=>g.openAdminCounterReset();
   $('admin-export-report').onclick=()=>g.exportAdminReport();
   net?.bindAdmin(errors);
   $('admin-logout').onclick=()=>{
     g._adminAuthenticated=false;g.openInfo('admin');
   };
 }
 g.openAdminLogin=function(){
   if(this.superseded)return;
   this.openModal('ACESSO ADMINISTRATIVO',
    '<div class="farao-admin-panel"><p>Introduz o código de administrador.</p>'+
    field('admin-login-code','Código','', 'password',
      'inputmode="numeric" autocomplete="off" maxlength="12" placeholder="•••••"')+
    '<p id="farao-admin-error" class="farao-admin-error" role="alert"></p>'+
    button('admin-login-submit','ENTRAR')+
    '<button type="button" class="btn" id="admin-login-back">VOLTAR</button>'+
    note+'</div>',true);
   const check=()=>{
     if(!E.checkPin(this.ledger,'admin',$('admin-login-code').value)){
       errors('Código de administrador incorreto');$('admin-login-code').value='';return;
     }
     this._adminAuthenticated=true;adminPage();
   };
   $('admin-login-submit').onclick=check;
   $('admin-login-code').onkeydown=e=>{if(e.key==='Enter'){e.preventDefault();check();}};
   $('admin-login-back').onclick=()=>this.openInfo('admin');
   $('admin-login-code').focus();
 };
 g.openAdminDashboard=adminPage;
 g.exportAdminReport=function(){
  if(!this._adminAuthenticated)return;
  const econ=status(),s=this.ledger;
  const report={
   generatedAt:new Date().toISOString(),machineId:window.FaraoMachineNetwork?.machineId||null,
   currentPeriod:econ.periodNumber||1,stats:{...s.stats},totals:{...econ.totals},
   balanceU:s.balanceU,prizeFundU:econ.prizeU,technicianDueU:econ.techDueU,
   merchantDueU:econ.merchantDueU,technicianPercent:econ.technicianPercent,
   merchantPercent:econ.merchantPercent,prizeProfile:econ.level,
   closedPeriods:econ.periods||[],audit:econ.log||[],
   history:s.history.map(item=>({id:item.id||null,kind:item.kind,date:item.t,
     bet:item.bet||0,win:item.win||0,amount:item.amount||0}))
  };
  const blob=new Blob([JSON.stringify(report,null,2)],{type:'application/json'});
  const url=URL.createObjectURL(blob),link=document.createElement('a');
  link.href=url;link.download='farao-relatorio-'+new Date().toISOString().slice(0,10)+'.json';
  document.body.appendChild(link);link.click();link.remove();
  setTimeout(()=>URL.revokeObjectURL(url),3000);
 };
 g.openAdminCounterReset=function(){
  if(!this._adminAuthenticated){this.openAdminLogin();return;}
  if(isBusy()){errors('Termina primeiro a jogada e os bónus');return;}
  const oldPeriod=status().periodNumber||1;
  this.openModal('NOVO PERÍODO DE CONTADORES',
   '<div class="farao-admin-panel"><p><b>ATENÇÃO:</b> Os totais e os contadores de giros passam a zero. Os saldos e os cofres mantêm-se. O período '+oldPeriod+' será arquivado.</p>'+
   field('admin-reset-pin','Confirma o código de administrador','', 'password',
     'inputmode="numeric" autocomplete="off" maxlength="12"')+
   field('admin-reset-confirm','Escreve LIMPAR para confirmar','', 'text',
     'autocomplete="off" placeholder="LIMPAR"')+
   '<p id="farao-admin-error" class="farao-admin-error" role="alert"></p>'+
   button('admin-reset-confirm-button','CONFIRMAR LIMPEZA')+
   '<button class="btn" type="button" id="admin-reset-cancel">CANCELAR</button>'+note+'</div>',true);
  $('admin-reset-confirm-button').onclick=()=>guard(()=>{
   if(!E.checkPin(this.ledger,'admin',$('admin-reset-pin').value))
    throw Error('Código de administrador incorreto');
   if($('admin-reset-confirm').value.trim().toUpperCase()!=='LIMPAR')
    throw Error('Escreve LIMPAR para confirmar');
   E.resetCounters(this.ledger);
   this.openAdminDashboard();
  });
  $('admin-reset-cancel').onclick=()=>this.openAdminDashboard();
 };
 g.openAdminPinChange=function(role){
   if(!this._adminAuthenticated){this.openAdminLogin();return;}
   if(isBusy()){this.openAdminDashboard();return;}
   const names={admin:'ADMINISTRADOR',deposit:'DEPÓSITOS',withdraw:'LEVANTAMENTOS'};
   this.openModal('ALTERAR CÓDIGO · '+names[role],
    '<div class="farao-admin-panel">'+
    field('admin-current-pin','Código de administrador atual','', 'password',
       'inputmode="numeric" autocomplete="off" maxlength="12"')+
    field('admin-new-pin','Novo código (4 a 12 algarismos)','', 'password',
       'inputmode="numeric" autocomplete="new-password" maxlength="12"')+
    '<p id="farao-admin-error" class="farao-admin-error" role="alert"></p>'+
    button('admin-change-save','GUARDAR CÓDIGO')+
    '<button class="btn" type="button" id="admin-change-back">VOLTAR</button>'+note+
    '</div>',true);
   $('admin-change-save').onclick=()=>guard(()=>{
     E.changePin(this.ledger,role,$('admin-current-pin').value,$('admin-new-pin').value);
     this.openAdminDashboard();
   });
   $('admin-change-back').onclick=()=>this.openAdminDashboard();
 };
 // Um código diferente para depósito e levantamento, antes de apresentar
 // o campo de montante livre. O valor continua a ser validado pela sessão.
 const oldCreditAction=g.showCreditAction.bind(g);
 g.showCreditAction=function(operation){
   if(!['deposit','withdraw'].includes(operation)||!active()||isBusy())return;
   const name=operation==='deposit'?'DEPÓSITO':'LEVANTAMENTO';
   this.openModal('CÓDIGO DE '+name,
     '<div class="farao-admin-panel">'+
     '<p>Confirma o código de '+name.toLowerCase()+' para introduzir o montante.</p>'+
     field('credit-pin','Código','', 'password',
       'inputmode="numeric" autocomplete="off" maxlength="12" placeholder="•••••"')+
     '<p id="farao-admin-error" class="farao-admin-error" role="alert"></p>'+
     button('credit-pin-submit','CONFIRMAR')+
     '<button type="button" class="btn" id="credit-pin-back">VOLTAR</button>'+
     '</div>',true);
   const verify=()=>{
     if(!E.checkPin(this.ledger,operation,$('credit-pin').value)){
       errors('Código de '+name.toLowerCase()+' incorreto');$('credit-pin').value='';return;
     }
     oldCreditAction(operation);
   };
   $('credit-pin-submit').onclick=verify;
   $('credit-pin').onkeydown=e=>{if(e.key==='Enter'){e.preventDefault();verify();}};
   $('credit-pin-back').onclick=()=>this.showCreditMenu();
   $('credit-pin').focus();
 };
 document.documentElement.dataset.faraoAdmin='1.0';
})();
