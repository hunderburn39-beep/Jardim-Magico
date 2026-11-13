/* Faraó Eterno — Super Scatter (créditos virtuais).
 * Integração reversível sobre o motor aprovado; não muda as linhas nem os pagamentos base.
 * A face é sorteada UMA vez no disparo e guardada antes da animação do dado.
 * O extra das Free Spins fica reservado durante a sessão e é creditado UMA só vez
 * após o último pagamento pendente. O teto 5000× e o cofre são respeitados.
 */
(function(root){
 'use strict';
 const E=root.TombEngine,S=root.FaraoSession;
 if(!E||!S||root.FaraoSuperScatter)return;
 const FACES=Object.freeze([2,3,4,5,8,10]);
 const U=S.units,CR=S.credits;
 const validAmount=n=>Number.isSafeInteger(n)&&n>=0;
 // SUPER é uma figura própria. Só 3+ pirâmides TOMB ativam Free Spins;
 // SUPER + 3+ pirâmides inicia a variante multiplicada.
 E.ALL.push('SUPER');
 E.WEIGHTS.SUPER=1.25;
 E.PAYS.SUPER={};
 const originalEval=E.evaluate;
 E.evaluate=function(grid,...args){
  const result=originalEval(grid,...args);
  const count=grid.flat().filter(s=>s==='SUPER').length;
  result.superCount=count;
  result.superTrigger=result.trigger&&count>0;
  return result;
 };
 const previousBegin=S.begin;
 const previousSettle=S.settle;
 const previousCredit=S.creditNext;
 const previousFinish=S.finishPayout;
 const previousRestore=S.restore;
 const originalUpper=E.upperLimit;
 function superFactor(s){
  const n=s.feature&&s.feature.superMultiplier;
  return FACES.includes(n)?n:1;
 }
 function reserve(s,m){
  return (m-1)*(s.feature?.totalU||0);
 }
 function finishExtra(s){
  const pay=s.superFinishPending;
  if(!pay||s.payout)return false;
  if(!validAmount(pay.extraU)||!FACES.includes(pay.multiplier)){
   throw Error('Reserva Super Scatter inválida');
  }
  // O cofre foi reservado no começo de cada rodada. Não repetir o pagamento
  // após recuperação do Safari nem quando o resumo é novamente aberto.
  if(pay.extraU>s.economy.prizeU)throw Error('Fundo Super Scatter inconsistente');
  s.economy.prizeU-=pay.extraU;
  s.economy.totals.winsU+=pay.extraU;
  s.stats.winU+=pay.extraU;
  s.balanceU+=pay.extraU;
  if(s.last&&s.last.bonusDone)
   s.last.bonusDone.superExtraCredited=true;
  delete s.superFinishPending;
  return true;
 }
 S.begin=function(s,rng){
  const m=superFactor(s);
  if(m===1)return previousBegin(s,rng);
  if(!s.economy||!validAmount(s.economy.prizeU))
   throw Error('Cofre Super Scatter inválido');
  const originalPrize=s.economy.prizeU;
  // O prêmio bruto tem de ser pagável INCLUINDO o extra já reservado
  // das rodadas anteriores. Reduzir temporariamente apenas a quota
  // admitida no sorteio, restaurando o valor real no finally.
  const available=Math.max(0,originalPrize-reserve(s,m));
  s.economy.prizeU=Math.floor(available/m);
  E.upperLimit=bet=>originalUpper(bet)/m;
  try{
   return previousBegin(s,rng);
  }finally{
   E.upperLimit=originalUpper;
   s.economy.prizeU=originalPrize;
  }
 };
 S.settle=function(s,...args){
  const pending=s.pending;
  if(!pending)return previousSettle(s,...args);
  const isFree=pending.free;
  const m=isFree?superFactor(s):1;
  const isSuper=!isFree&&E.countScatters(pending.grid)>=3&&
    pending.grid.some(col=>col.includes('SUPER'));
  const out=previousSettle(s,...args);
  if(!out)return out;
  if(isSuper&&s.feature){
   const face=Math.min(5,Math.floor(E.randomFloat()*6));
   s.feature.superMultiplier=FACES[face];
   s.feature.superFace=face;
   s.feature.superRollSeen=false;
  }
  if(isFree&&m>1&&out.bonusDone){
   const b=out.bonusDone,baseU=b.totalU,extraU=baseU*(m-1);
   if(!validAmount(extraU))throw Error('Total Super Scatter inválido');
   b.superBaseTotalU=baseU;
   b.superExtraU=extraU;
   b.superMultiplier=m;
   b.totalU=baseU+extraU;
   b.superExtraCredited=false;
   if(s.last?.bonusDone)s.last.bonusDone=JSON.parse(JSON.stringify(b));
   const history=s.history.find(h=>h.kind==='free-bonus'&&h.id===(b.historyId||pending.id));
   if(history){
    history.superBaseTotalU=baseU;
    history.superMultiplier=m;
    history.win=CR(b.totalU);
   }
   s.superFinishPending={extraU,multiplier:m};
   finishExtra(s); // Só paga já se não houver animações de pagamento pendentes.
  }
  return out;
 };
 S.creditNext=function(s,...args){
  const out=previousCredit(s,...args);
  if(out&&!s.payout)finishExtra(s);
  return out;
 };
 S.finishPayout=function(s,...args){
  const out=previousFinish(s,...args);
  if(!s.payout)finishExtra(s);
  return out;
 };
 S.restore=function(raw){
  const s=previousRestore(raw);
  if(s.superFinishPending&&!s.payout)finishExtra(s);
  return s;
 };
 root.FaraoSuperScatter={faces:FACES,superFactor};
})(typeof window!=='undefined'?window:globalThis);
