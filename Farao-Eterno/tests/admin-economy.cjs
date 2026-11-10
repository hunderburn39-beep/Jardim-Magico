'use strict';
const test=require('node:test'),assert=require('node:assert/strict');
const E=require('../js/engine.js'),S=require('../js/session.js');
const A=require('../js/economy.js');
const U=S.units,CR=S.credits;
function rng(seed=20261110){return()=>{seed=(Math.imul(seed,1664525)+1013904223)>>>0;return seed/4294967296;};}
test('cofres com 5000 créditos iniciais, PINs 12345 e 100% de comissões',()=>{
 const s=S.fresh(),e=s.economy;
 assert.equal(e.prizeU,U(5000));
 assert.deepEqual([e.level,e.technicianPercent,e.merchantPercent],[90,50,50]);
 for(const type of ['admin','deposit','withdraw'])assert.equal(A.checkPin(s,type,'12345'),true);
 assert.equal(A.checkPin(s,'admin','1234'),false);
 assert.equal(e.techDueU+e.merchantDueU,0);
});
test('depósitos e levantamentos guardam totais sem criar lucros artificiais',()=>{
 const s=S.fresh(),bank=s.economy.prizeU;
 S.adjustCredits(s,'deposit',123.45);
 S.adjustCredits(s,'withdraw',23.45);
 assert.equal(s.economy.totals.depositsU,U(123.45));
 assert.equal(s.economy.totals.withdrawalsU,U(23.45));
 assert.equal(s.economy.prizeU,bank);
 assert.equal(s.balanceU,U(400));
 assert.deepEqual(s.history.slice(0,2).map(x=>x.kind),['withdraw','deposit']);
 const restored=S.restore(JSON.parse(JSON.stringify(s)));
 assert.equal(restored.economy.totals.depositsU,U(123.45));
 assert.equal(restored.economy.totals.withdrawalsU,U(23.45));
});
test('a comissão do técnico e do comerciante completa exatamente 100%',()=>{
 const s=S.fresh();
 for(const pct of [0,1,35,50,68,99,100]){
  A.setSplit(s,pct);
  assert.equal(s.economy.technicianPercent+s.economy.merchantPercent,100);
 }
 assert.throws(()=>A.setSplit(s,101),/Comissão/);
 assert.throws(()=>A.setSplit(s,-1),/Comissão/);
});
test('PINs de administrador, depósito e levantamento mudam individualmente',()=>{
 const s=S.fresh();
 assert.throws(()=>A.changePin(s,'deposit','00000','65432'),/incorreto/);
 A.changePin(s,'deposit','12345','65432');
 A.changePin(s,'withdraw','12345','76432');
 A.changePin(s,'admin','12345','93421');
 assert.equal(A.checkPin(s,'deposit','65432'),true);
 assert.equal(A.checkPin(s,'withdraw','76432'),true);
 assert.equal(A.checkPin(s,'admin','93421'),true);
 assert.equal(A.checkPin(s,'admin','12345'),false);
 assert.throws(()=>A.changePin(s,'deposit','93421','12'),/4 a 12/);
 const restored=S.restore(JSON.parse(JSON.stringify(s)));
 assert.equal(A.checkPin(restored,'admin','93421'),true);
});
test('aposta partilha receita e o fundo nunca paga mais que disponível',()=>{
 const s=S.fresh();A.setLevel(s,85);A.setSplit(s,40);
 const before=s.economy.prizeU,betU=U(S.stake(s));
 const next=S.begin(s,rng());
 assert.equal(next.pending.economyVersion,1);
 assert.equal(s.economy.prizeU,before+Math.floor(betU*.85));
 const profit=betU-Math.floor(betU*.85);
 assert.equal(s.economy.techDueU,Math.floor(profit*.4));
 assert.equal(s.economy.merchantDueU,profit-Math.floor(profit*.4));
 assert.ok(U(next.result.total)<=s.economy.prizeU);
 const settled=S.settle(s);S.finishPayout(s);
 assert.ok(settled.prizeU<=before+Math.floor(betU*.85));
 assert.equal(s.economy.prizeU,before+Math.floor(betU*.85)-settled.prizeU);
 assert.equal(s.economy.totals.winsU,settled.prizeU);
});
test('fundo vazio: rejeita sequências acima do prémio disponível',()=>{
 const s=S.fresh();
 A.adjustFund(s,'remove',5000);
 assert.equal(s.economy.prizeU,0);
 const next=S.begin(s,rng(27));
 assert.ok(U(next.result.total)<=Math.floor(U(S.stake(s))*.9));
 const settled=S.settle(s);assert.ok(s.economy.prizeU>=0);
 assert.ok(settled.prizeU>=0);
 assert.throws(()=>A.adjustFund(s,'remove',5000),/não possui/);
});
test('fundos insuficientes durante bónus não permitem jogada impossível',()=>{
 const s=S.fresh();
 A.adjustFund(s,'remove',5000);
 s.feature={left:8,played:0,totalU:0,symbols:[...E.NORMAL],retriggers:0};
 const pendingBefore=s.pending,sequenceBefore=s.sequence,leftBefore=s.feature.left;
 // Com todos os símbolos como expansivos e 0 créditos, nenhuma grelha pode
 // ser autorizada se tiver prémio a pagar.
 try{
  const next=S.begin(s,rng(101));
  assert.equal(U(next.result.total),0);
  const out=S.settle(s);
  assert.equal(out.prizeU,0);
 }catch(err){
  assert.match(err.message,/Fundo de prémios insuficiente/);
  assert.equal(s.pending,pendingBefore);
  assert.equal(s.sequence,sequenceBefore);
  assert.equal(s.feature.left,leftBefore);
 }
});
test('técnico e comerciante podem levantar apenas a sua parte de lucros',()=>{
 const s=S.fresh();S.begin(s,rng(40));S.settle(s);
 const e=s.economy,tech=e.techDueU,merchant=e.merchantDueU;
 assert.ok(tech>0&&merchant>0);
 A.collect(s,'technician',CR(tech));
 A.collect(s,'merchant',CR(merchant));
 assert.equal(e.techDueU,0);assert.equal(e.merchantDueU,0);
 assert.equal(e.totals.techCollectedU,tech);
 assert.equal(e.totals.merchantCollectedU,merchant);
 assert.throws(()=>A.collect(s,'merchant',1),/insuficiente/);
});
test('85 a 95 alteram o peso de símbolos premium e a verba das apostas',()=>{
 const low=A.weighted(85,false),high=A.weighted(95,false);
 assert.ok(low.KING<high.KING);
 assert.ok(low.TOMB<high.TOMB);
 for(let value=85;value<=95;value++)assert.doesNotThrow(()=>A.setLevel(S.fresh(),value));
 assert.throws(()=>A.setLevel(S.fresh(),96),/85% e 95%/);
});
test('cofres não mudam durante jogo ou Free Spins',()=>{
 const s=S.fresh();S.begin(s,rng(32));
 assert.throws(()=>A.setLevel(s,95),/Termina/);
 assert.throws(()=>A.adjustFund(s,'remove',1),/Termina/);
 assert.throws(()=>A.setSplit(s,30),/Termina/);
 S.settle(s);S.finishPayout(s);
});
