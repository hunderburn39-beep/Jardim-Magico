'use strict';
/* Faraó Eterno — auditoria de matemática e Super Scatter.
 * Reproduz o motor/sessão/cofre reais, sem mexer nos pesos.
 * Execute: node Farao-Eterno/tests/auditoria-completa.cjs
 * SAMPLES=20000 node Farao-Eterno/tests/auditoria-completa.cjs
 */
const assert=require('node:assert/strict');
const E=require('../js/engine.js'),S=require('../js/session.js');
globalThis.window=globalThis;
globalThis.TombEngine=E;
globalThis.FaraoSession=S;
require('../js/super-scatter-core.js');
require('../js/economy.js');
const seed0=0x6a13f00d;
function seeded(s){
 return ()=>{s+=0x6d2b79f5;let x=s;x=Math.imul(x^(x>>>15),x|1);
 x^=x+Math.imul(x^(x>>>7),x|61);
 return ((x^(x>>>14))>>>0)/4294967296;};
}
const N=Number(process.env.SAMPLES||5000),LEVEL=Number(process.env.LEVEL||90);
assert([85,90,95].includes(LEVEL),'LEVEL: 85, 90 ou 95');
assert(Number.isSafeInteger(N)&&N>=100&&N<=200000,'SAMPLES fora do intervalo seguro');
assert.deepEqual(globalThis.FaraoSuperScatter.faces,[2,3,4,5,8,10]);
assert.equal(E.LINES.length,10);
assert.equal(E.ALL.includes('SUPER'),true);
assert.equal(S.economy.freshEco().level,90);
// O dado nunca altera um resultado já sorteado, e SUPER isolado não dá FS.
const yes=[['TOMB','J','Q'],['TOMB','Q','K'],['TOMB','A','10'],
           ['SUPER','K','J'],['KING','A','Q']];
const no=[['TOMB','J','Q'],['TOMB','Q','K'],['A','10','J'],
          ['SUPER','K','J'],['KING','A','Q']];
assert(E.evaluate(yes,1,10).superTrigger);
assert(!E.evaluate(no,1,10).superTrigger);
assert(E.evaluate(no,1,10).superCount===1);
// Super Scatter: o fundo tem de suportar base + multiplicador prometido.
{
 const rng=seeded(seed0),s=S.fresh();
 S.adjustCredits(s,'deposit',100);
 S.economy.adjustFund(s,'add',500);
 s.feature={left:3,played:5,totalU:S.units(20),symbols:['Q'],retriggers:0,
   superMultiplier:10,superFace:5,superRollSeen:true};
 const cap0=s.economy.prizeU,liability0=9*s.feature.totalU;
 const allowance=Math.floor((cap0-liability0)/10);
 assert(allowance>0);
 const {result}=S.begin(s,rng);
 assert(S.units(result.total)<=allowance,
   'O resultado base do Super Scatter ultrapassou a verba de 10×');
 S.settle(s);
 S.finishPayout(s);
 const liability=9*(s.feature?.totalU||s.last?.bonusDone?.superBaseTotalU||0);
 // Pode concluir o bónus na última ronda; nesse caso o extra já foi pago.
 if(s.feature)assert(s.economy.prizeU>=liability,'Reserva final do dado insuficiente');
 S.validate(s);
}
// Auditoria end-to-end com aposta real do motor, 10 linhas, cofre bem financiado.
// "Nível 90" é repartição de cada aposta; NÃO É o RTP.
const s=S.fresh(),rng=seeded(seed0+47);
S.adjustCredits(s,'deposit',10000);
S.economy.adjustFund(s,'add',1000000);
S.economy.setLevel(s,LEVEL);
const initialBalance=s.balanceU;
const originalRandom=E.randomFloat;
E.randomFloat=rng;
let paid=0,free=0,superBonuses=0,maxWon=0,guard=0;
let baseReturnU=0,freeReturnU=0,superExtraU=0;
try{
 while(paid<N || s.feature){
   if(++guard>N*30+3000)throw Error('Limite preventivo de retriggers excedido');
   if(!s.feature&&paid>=N)break;
   const wasFree=!!s.feature,previousWinU=s.stats.winU;
   const {pending,result}=S.begin(s,rng);
   if(!wasFree)paid++; else free++;
   if(!wasFree&&result.superTrigger)superBonuses++;
   const out=S.settle(s);
   S.finishPayout(s);
   const creditedU=s.stats.winU-previousWinU;
   if(wasFree){freeReturnU+=out.prizeU;superExtraU+=creditedU-out.prizeU;}
   else baseReturnU+=creditedU;
   assert(creditedU>=0,'O prémio nunca pode ser negativo');
   assert(s.economy.prizeU>=0,'Saldo do cofre negativo');
   assert(s.balanceU>=0,'Saldo de créditos negativo');
   assert(s.stats.winU>=0);
   maxWon=Math.max(maxWon,S.credits(out.prizeU));
 }
}finally{E.randomFloat=originalRandom;}
S.validate(s);
const stake=S.credits(s.stats.stakeU),wins=S.credits(s.stats.winU);
assert.equal(baseReturnU+freeReturnU+superExtraU,s.stats.winU,'As fontes dos prémios devem somar o total creditado');
assert(Math.abs((S.credits(s.balanceU)-S.credits(initialBalance))-(wins-stake))<1e-6,
 'Diferença no livro razão: possível perda ou crédito duplicado');
assert(s.feature===null&&s.pending===null&&s.payout===null,'Bónus/pagamento incompleto');
const report={
 rounds:paid,freeSpins:free,features:s.stats.features,superBonuses,
 stakeCredits:Math.round(stake*100)/100,returnedCredits:Math.round(wins*100)/100,
 measuredRtpPct:Math.round(10000*wins/stake)/100,
 baseReturnPct:Math.round(10000*S.credits(baseReturnU)/stake)/100,
 freeSpinsReturnPct:Math.round(10000*S.credits(freeReturnU)/stake)/100,
 superExtraReturnPct:Math.round(10000*S.credits(superExtraU)/stake)/100,
 maxBaseOrFreeWinCredits:Math.round(maxWon*100)/100,
 level:s.economy.level,prizeFundRemaining:S.credits(s.economy.prizeU),
 meaning:'RTP observado na amostra, sem garantia de retorno futuro; saldo de créditos fictícios.',
 assumptions:'Amostra independente com nível '+LEVEL+', aposta base de 1 crédito, 10 linhas, fundo pré-financiado, sem compra de bónus.'
};
console.log('AUDITORIA_FARAO_V2 '+JSON.stringify(report));
