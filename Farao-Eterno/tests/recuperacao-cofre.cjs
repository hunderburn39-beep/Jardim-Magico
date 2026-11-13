'use strict';
/* Auditoria determinística de pagamentos progressivos e recuperação
 * Super Scatter — créditos fictícios, sem alterar o motor original.
 * Executar: node --test Farao-Eterno/tests/recuperacao-cofre.cjs
 */
const test=require('node:test'),assert=require('node:assert/strict');
const E=require('../js/engine.js'),S=require('../js/session.js');
globalThis.window=globalThis;
globalThis.TombEngine=E;globalThis.FaraoSession=S;
require('../js/super-scatter-core.js');
require('../js/economy.js');
const FACES=[2,3,4,5,8,10],U=S.units;
const clone=s=>JSON.parse(JSON.stringify(s));
const grid=Array.from({length:5},()=>['A','J','Q']);
function newBonus(m,fundCredits){
 const s=S.fresh();
 S.adjustCredits(s,'deposit',5000);
 S.economy.adjustFund(s,'add',fundCredits);
 // Simula a derradeira Free Spin, após sete já contabilizadas.
 s.feature={left:1,played:7,totalU:U(20),symbols:['KING'],retriggers:0,
  superMultiplier:m,superFace:FACES.indexOf(m),superRollSeen:true};
 S.validate(s);
 return s;
}
function beginWithGrid(s){
 const original=E.makeGrid;
 E.makeGrid=()=>grid.map(x=>x.slice());
 try{return S.begin(s,()=>.43147);}finally{E.makeGrid=original;}
}
function reservedAllowance(s,m){
 const reserve=(m-1)*s.feature.totalU;
 const prize=Math.floor(Math.max(0,s.economy.prizeU-reserve)/m);
 const max=Math.max(0,Math.floor(U(E.upperLimit(S.stake(s)))/m)-s.feature.totalU);
 return Math.min(prize,max);
}
for(const m of FACES){
 test('Super ×'+m+': nunca escolher combinação acima do cofre, mesmo com fundo apertado',()=>{
   const s=newBonus(m,400),allowed=reservedAllowance(s,m);
   const {result}=beginWithGrid(s);
   assert(U(result.total)<=allowed,
     'A seleção de símbolos prometeu '+U(result.total)+' unidades; cota Super '+allowed);
   const out=S.settle(s,undefined,{defer:true});
   assert(out,'A ronda deve poder ser liquidada');
   S.finishPayout(s);
   S.validate(s);
   assert(s.economy.prizeU>=0,'Fundo insuficiente após bónus');
 });
 test('Super ×'+m+': recuperar a última Free Spin em qualquer pagamento parcial',()=>{
   const s=newBonus(m,25000);
   const initialBalance=s.balanceU,initialWon=s.stats.winU,initialFund=s.economy.prizeU;
   const {result}=beginWithGrid(s);
   assert(result.total>0,'Grelha de teste deve premiar');
   const out=S.settle(s,undefined,{defer:true});
   assert(out.bonusDone,'A última Free Spin deve fechar o bónus');
   assert.equal(out.bonusDone.superMultiplier,m);
   const bonusU=out.bonusDone.superExtraU;
   assert.equal(bonusU,(m-1)*(U(20)+out.prizeU));
   const p=s.payout;
   assert(p&&p.groups.length>=2,'Esperadas várias linhas de prémio diferido');
   const expectedBalance=initialBalance+out.prizeU+bonusU;
   const expectedWon=initialWon+out.prizeU+bonusU;
   const expectedFund=initialFund-out.prizeU-bonusU;
   const committed=clone(s);
   for(let cut=0;cut<=p.groups.length;cut++){
     let rec=S.restore(clone(committed));
     for(let i=0;i<cut;i++){
       const group=S.creditNext(rec,p.id,i);
       assert(group,'Não foi possível pagar a parcela '+i);
     }
     // Simular o Safari a fechar DEPOIS de algumas parcelas já creditadas.
     rec=S.restore(clone(rec));
     S.finishPayout(rec);
     S.validate(rec);
     assert.equal(rec.balanceU,expectedBalance,'Saldo incorreto após reiniciar na parcela '+cut);
     assert.equal(rec.stats.winU,expectedWon,'Prémio duplicado/perdido após reiniciar na parcela '+cut);
     assert.equal(rec.economy.prizeU,expectedFund,'Cofre mudou com a recuperação');
     assert.equal(rec.payout,null,'Pagamento por linhas ficou a meio');
     assert.equal(rec.superFinishPending,undefined,'Extra do dado ficou pendente');
     assert.equal(rec.last.bonusDone.superExtraCredited,true);
     // Repetir a restauração não pode voltar a pagar o dado.
     const again=S.restore(clone(rec));
     S.finishPayout(again);
     assert.equal(again.balanceU,expectedBalance);
     assert.equal(again.stats.winU,expectedWon);
     assert.equal(again.economy.prizeU,expectedFund);
   }
 });
 test('Super ×'+m+': Safari fecha logo após o sorteio, antes de terminar o giro',()=>{
   const s=newBonus(m,25000),balance=s.balanceU,won=s.stats.winU;
   beginWithGrid(s);
   assert(s.pending&&!s.payout);
   const rec=S.restore(clone(s));
   assert(rec.pending,'Resultado sorteado não foi recuperado');
   S.settle(rec);
   S.finishPayout(rec);
   S.validate(rec);
   assert(rec.last.bonusDone,'Bónus deveria terminar');
   const added=rec.last.prizeU+rec.last.bonusDone.superExtraU;
   assert.equal(rec.balanceU,balance+added,'Saldo mudou incorretamente após recuperar giro');
   assert.equal(rec.stats.winU,won+added,'Pagamento duplicado após recuperar giro');
   const restored=S.restore(clone(rec));
   assert.equal(restored.balanceU,rec.balanceU,'Segundo reload pagou novamente');
 });
}
