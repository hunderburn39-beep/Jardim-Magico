/* Faraó Eterno — cofre e administração locais, exclusivamente créditos simulados.
 * PINs não são autenticação segura: não existe servidor nem dinheiro real.
 * O motor decide a grelha ANTES da animação e nunca promete prémio > fundo.
 */
(function(root,factory){
  if(typeof module==='object'&&module.exports)
    module.exports=factory(require('./engine.js'),require('./session.js'));
  else root.FaraoEconomy=factory(root.TombEngine,root.FaraoSession);
})(typeof globalThis!=='undefined'?globalThis:this,function(E,S){
 'use strict';
 if(!E||!S)throw Error('Motor e sessão necessários para o cofre');
 const U=S.units,CR=S.credits,DEFAULT_PIN='12345';
 const validPin=value=>typeof value==='string'&&/^[0-9]{4,12}$/.test(value);
 const int=value=>Number.isSafeInteger(value)&&value>=0;
 const date=()=>new Date().toISOString();
 const freshEco=()=>({
   v:1,level:90,technicianPercent:50,merchantPercent:50,
   pin:{admin:DEFAULT_PIN,deposit:DEFAULT_PIN,withdraw:DEFAULT_PIN},
   prizeU:0,techDueU:0,merchantDueU:0,
   totals:{depositsU:0,withdrawalsU:0,stakesU:0,bonusesU:0,
     winsU:0,techCollectedU:0,merchantCollectedU:0,
     prizeAddedU:0,prizeRemovedU:0},
   log:[{at:date(),kind:'opening',amountU:0}]
 });
 function ensure(s){
   if(!s.economy){
     const initial=freshEco();
     // Preservar os totais conhecidos ao atualizar uma sessão antiga.
     // Movimentos anteriores a 100 registos podem já não estar disponíveis.
     if(s.stats){
       if(int(s.stats.stakeU))initial.totals.stakesU=s.stats.stakeU;
       if(int(s.stats.winU))initial.totals.winsU=s.stats.winU;
     }
     for(const item of (Array.isArray(s.history)?s.history:[])){
       if(item.kind==='deposit'||item.kind==='withdraw'){
         const amount=Number(item.amount);
         if(Number.isFinite(amount)&&amount>0){
           const field=item.kind==='deposit'?'depositsU':'withdrawalsU';
           initial.totals[field]+=U(amount);
         }
       }
     }
     initial.log.unshift({at:date(),kind:'history-migration',amountU:0,
       detail:'Movimentos recuperáveis das últimas 100 ações'});
     s.economy=initial;
   }
   // Uma jogada anterior à implementação do cofre tem de poder terminar
   // com a matemática e a aposta que já estavam guardadas.
   if(s.pending&&s.pending.economyVersion!==1){
     const p=s.pending,e=s.economy;
     const outcome=E.evaluate(p.grid,p.bet,p.lines,p.free?s.feature.symbols:[],
       p.paylinesVersion===E.PAYLINES_VERSION?E.LINES:E.LEGACY_LINES,
       p.expansionPayVersion===2?'separate':'legacy',
       p.free&&p.towerPayVersion===1?'free-anywhere':'base');
     const remaining=p.free?E.upperLimit(p.bet)-CR(s.feature.totalU):E.upperLimit(p.bet);
     e.prizeU=Math.max(e.prizeU,U(Math.min(outcome.total,Math.max(0,remaining))));
   }
   return s.economy;
 }
 function log(s,kind,amountU=0,detail=''){
   const e=ensure(s);
   e.log.unshift({at:date(),kind,amountU,detail});
   e.log=e.log.slice(0,150);
 }
 function validateEconomy(s){
   const e=ensure(s),t=e.totals;
   if(e.v!==1||!Number.isInteger(e.level)||e.level<85||e.level>95||
     !Number.isInteger(e.technicianPercent)||e.technicianPercent<0||
     e.technicianPercent>100||e.merchantPercent!==100-e.technicianPercent||
     !['admin','deposit','withdraw'].every(key=>validPin(e.pin?.[key]))||
     !['prizeU','techDueU','merchantDueU'].every(key=>int(e[key]))||
     !['depositsU','withdrawalsU','stakesU','bonusesU','winsU',
       'techCollectedU','merchantCollectedU','prizeAddedU','prizeRemovedU']
       .every(key=>int(t?.[key]))||!Array.isArray(e.log))
     throw Error('Cofres da sessão inválidos');
   return e;
 }
 function requireIdle(s){
   if(s.pending||s.payout||s.feature)throw Error('Termina a jogada ou o bónus antes de alterar os cofres');
 }
 function incoming(s,amountU,bonus=false){
   const e=validateEconomy(s),prizeU=Math.floor(amountU*e.level/100);
   const profitU=amountU-prizeU;
   const technician=Math.floor(profitU*e.technicianPercent/100);
   e.prizeU+=prizeU;e.techDueU+=technician;e.merchantDueU+=profitU-technician;
   e.totals.stakesU+=amountU;
   if(bonus)e.totals.bonusesU+=amountU;
   return {prizeU,profitU};
 }
 function weighted(level,free){
   const original=free?E.FREE_WEIGHTS:E.WEIGHTS;
   // Calibração V2: reduzir o excesso de prémios nas Free Spins e
   // manter uma progressão suave entre os níveis 85–95.
   // Os valores são pesos relativos, NÃO percentagens de RTP garantidas.
   // A tabela aprovada e o motor E.evaluate permanecem inalterados.
   const boost=.70+(level-90)*.006;
   const w={...original};
   for(const k of ['HORUS','MASK','ANUBIS','KING'])w[k]=original[k]*boost;
   const tombScale=free ? (0.40+(level-90)*0.004) : (0.58+(level-90)*0.006);
   w.TOMB=original.TOMB*tombScale;
   // Equilíbrio V1.4 (exclusivamente jogo base):
   // ligeiramente mais Faraó e Anúbis para criar combinações grandes
   // sem alterar a tabela de pagamentos, os Wilds ou as Free Spins.
   // Hórus e Máscara cedem algum peso para não concentrar toda
   // a alteração nos prémios medianos. Continua a ser sorteio genuíno,
   // sujeito ao fundo de prémios disponível.
   if(!free){
     w.KING*=1.12;
     w.ANUBIS*=1.05;
     w.HORUS*=.94;
     w.MASK*=.94;
   }
   return w;
 }
 const old={
   fresh:S.fresh,restore:S.restore,migrate:S.migrate,validate:S.validate,
   begin:S.begin,settle:S.settle,buyBonus:S.buyBonus,adjustCredits:S.adjustCredits,
   gamble:S.gamble,reset:S.reset
 };
 S.fresh=function(){const s=old.fresh();ensure(s);return s;};
 S.restore=function(raw){const s=old.restore(raw);ensure(s);validateEconomy(s);return s;};
 S.migrate=function(raw){const s=old.migrate(raw);ensure(s);return s;};
 S.validate=function(s){old.validate(s);validateEconomy(s);return s;};
 S.begin=function(s,rng=E.randomFloat){
   S.validate(s);
   if(s.pending||s.payout)throw Error('Existe uma jogada por terminar');
   const free=!!s.feature,bet=S.stake(s),stakeU=U(bet),e=s.economy;
   if(!free&&s.balanceU<stakeU)throw Error('Saldo insuficiente');
   const incomingPrize=free?0:Math.floor(stakeU*e.level/100);
   // A camada super-scatter-core.js já reduz temporariamente o cofre
   // e o teto de 5000× pelo multiplicador em S.begin. Não voltar a
   // dividir nesta camada: isso reservaria o mesmo prémio duas vezes.
   const available=e.prizeU+incomingPrize;
   const maxBonus=U(E.upperLimit(bet))-(free?s.feature.totalU:0);
   const limit=Math.min(available,Math.max(0,maxBonus));
   const weights=weighted(e.level,free);
   let chosen=null;
   for(let attempt=0;attempt<180;attempt++){
     const grid=E.makeGrid(rng,weights);
     const result=E.evaluate(grid,bet,s.lines,free?s.feature.symbols:[],E.LINES,
       'separate',free?'free-anywhere':'base');
     // Nunca escolher uma grelha com uma promessa bruta acima do fundo.
     // O teto de 5000× continua a aplicar-se à liquidação depois do sorteio.
     const rawAwardU=U(result.total);
     if(rawAwardU<=limit){chosen=grid;break;}
   }
   // Apenas uma grelha matematicamente válida e sem prémio pode ser usada
   // quando a amostra aleatória não encontrou outra suportada pelo cofre.
   if(!chosen){
     const zero=[['10','J','Q'],['K','A','10'],['J','Q','K'],
       ['A','10','J'],['Q','K','A']];
     const evaluated=E.evaluate(zero,bet,s.lines,free?s.feature.symbols:[],E.LINES,
       'separate',free?'free-anywhere':'base');
     if(U(evaluated.total)<=limit)chosen=zero;
   }
   if(!chosen)throw Error('Fundo de prémios insuficiente para continuar. Abastece o fundo de prémios no painel de administração.');
   // Reutilizar a transação original, preservando sorteio de novo expansivo,
   // contador de giros, saldo e recuperação de sessões no Safari.
   const normalMake=E.makeGrid;
   E.makeGrid=()=>chosen.map(column=>column.slice());
   try{
     const begun=old.begin(s,rng);
     // Marcar a transação NOVA antes de mexer no cofre: incoming()
     // valida a sessão e ensure() só pode financiar legados históricos.
     // Sem esta ordem, uma grelha atual poderia criar fundo fictício.
     begun.pending.economyVersion=1;
     if(!free)incoming(s,stakeU);
     return begun;
   }finally{E.makeGrid=normalMake;}
 };
 S.settle=function(s,...args){
   if(s.pending) {
     const e=validateEconomy(s),p=s.pending;
     const result=E.evaluate(p.grid,p.bet,p.lines,p.free?s.feature.symbols:[],
       p.paylinesVersion===E.PAYLINES_VERSION?E.LINES:E.LEGACY_LINES,
       p.expansionPayVersion===2?'separate':'legacy',
       p.free&&p.towerPayVersion===1?'free-anywhere':'base');
     const maxPrize=U(E.upperLimit(p.bet))-(p.free?s.feature.totalU:0);
     const owed=Math.max(0,Math.min(U(result.total),Math.max(0,maxPrize)));
     if(owed>e.prizeU)throw Error('Fundo de prémios insuficiente para pagar esta jogada');
   }
   const out=old.settle(s,...args);
   if(out){
     const e=ensure(s);
     e.prizeU-=out.prizeU;
     e.totals.winsU+=out.prizeU;
   }
   return out;
 };
 S.buyBonus=function(s,...args){
   S.validate(s);
   const out=old.buyBonus(s,...args);
   incoming(s,out.costU,true);
   log(s,'buy-bonus',out.costU);
   return out;
 };
 S.adjustCredits=function(s,operation,amount,...args){
   S.validate(s);
   const out=old.adjustCredits(s,operation,amount,...args);
   const e=s.economy,field=operation==='deposit'?'depositsU':'withdrawalsU';
   e.totals[field]+=U(out.amount);
   log(s,operation,U(out.amount));
   return out;
 };
 S.gamble=function(s,guess,rng=E.randomFloat,...args){
   S.validate(s);
   const possible=s.lastBaseWinU*3;
   if(possible>s.economy.prizeU)
     throw Error('O cofre de prémios não suporta o risco máximo desta aposta');
   const before=s.lastBaseWinU,out=old.gamble(s,guess,rng,...args);
   if(out.won){
     const delta=Math.max(0,out.prizeU-before);
     s.economy.prizeU-=delta;s.economy.totals.winsU+=delta;
   }else incoming(s,before);
   return out;
 };
 S.reset=function(s,...args){S.validate(s);return old.reset(s,...args);};
 function setLevel(s,level){
   S.validate(s);requireIdle(s);
   if(!Number.isInteger(level)||level<85||level>95)throw Error('Nível entre 85% e 95%, em passos de 1%');
   s.economy.level=level;log(s,'level',0,String(level));
 }
 function setSplit(s,tech){
   S.validate(s);requireIdle(s);
   if(!Number.isInteger(tech)||tech<0||tech>100)throw Error('Comissão entre 0 e 100%');
   const e=s.economy,pool=e.techDueU+e.merchantDueU;
   e.technicianPercent=tech;e.merchantPercent=100-tech;
   e.techDueU=Math.floor(pool*tech/100);e.merchantDueU=pool-e.techDueU;
   log(s,'split',0,String(tech)+'/'+String(100-tech));
 }
 function changePin(s,role,current,next){
   S.validate(s);requireIdle(s);
   if(!['admin','deposit','withdraw'].includes(role))throw Error('Código desconhecido');
   if(String(current)!==s.economy.pin.admin)throw Error('Código de administrador incorreto');
   if(!validPin(String(next)))throw Error('Novo código: 4 a 12 algarismos');
   s.economy.pin[role]=String(next);log(s,'pin-change',0,role);
 }
 function checkPin(s,role,entered){
   S.validate(s);
   return ['admin','deposit','withdraw'].includes(role)&&
     s.economy.pin[role]===String(entered);
 }
 function amountUnits(value){
   const valueNum=Number(String(value).replace(',','.'));
   if(!Number.isFinite(valueNum)||valueNum<=0||valueNum>1000000||
     Math.round(valueNum*100)!==valueNum*100&&Math.abs(Math.round(valueNum*100)-valueNum*100)>1e-7)
     throw Error('Montante entre 0,01 e 1 000 000, até 2 casas decimais');
   return U(Math.round(valueNum*100)/100);
 }
 function adjustFund(s,action,value){
   S.validate(s);requireIdle(s);
   const amount=amountUnits(value),e=s.economy;
   if(!['add','remove'].includes(action))throw Error('Operação inválida');
   if(action==='remove'&&amount>e.prizeU)throw Error('O fundo não possui este montante');
   e.prizeU+=action==='add'?amount:-amount;
   e.totals[action==='add'?'prizeAddedU':'prizeRemovedU']+=amount;
   log(s,action==='add'?'fund-add':'fund-remove',amount);
 }
 function collect(s,role,value){
   S.validate(s);requireIdle(s);
   if(!['technician','merchant'].includes(role))throw Error('Beneficiário inválido');
   const amount=amountUnits(value),e=s.economy;
   const key=role==='technician'?'techDueU':'merchantDueU';
   if(amount>e[key])throw Error('Lucro disponível insuficiente');
   e[key]-=amount;
   e.totals[role==='technician'?'techCollectedU':'merchantCollectedU']+=amount;
   log(s,'collect-'+role,amount);
 }
 const api={ensure,validateEconomy,freshEco,checkPin,setLevel,setSplit,changePin,
   adjustFund,collect,amountUnits,weighted};
 S.economy=api;
 return api;
});
