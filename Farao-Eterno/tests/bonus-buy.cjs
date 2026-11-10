'use strict';
const test=require('node:test'),a=require('node:assert/strict');
const S=require('../js/session.js');
const E=require('../js/engine.js');
const make=(betIndex=4,lines=10)=>{const s=S.fresh();s.betIndex=betIndex;s.lines=lines;return s;};
test('compra de bónus custa exatamente 100x a aposta TOTAL ativa',()=>{
 for(const [betIndex,lines] of [[0,1],[0,10],[4,5],[4,10],[7,1],[7,10]]){
  const s=make(betIndex,lines);s.balanceU=S.units(10000);
  const before=s.balanceU,stakeU=S.units(S.stake(s)),stats=s.stats.stakeU;
  const bought=S.buyBonus(s,()=>.34,'2026-11-10T17:00:00.000Z');
  a.equal(bought.costU,100*stakeU);
  a.equal(s.balanceU,before-100*stakeU);
  a.equal(s.stats.stakeU,stats+100*stakeU);
  a.equal(s.feature.left,8);
  a.equal(s.feature.symbols.length,1);
  a.ok(E.NORMAL.includes(s.feature.symbols[0]));
  a.equal(s.history[0].kind,'bonus-buy');
  a.equal(s.history[0].cost,S.credits(100*stakeU));
  a.equal(s.sequence,0,'Comprar não inventa um ID de jogada');
  S.validate(s);
 }
});
test('créditos insuficientes não alteram o saldo nem o histórico',()=>{
 const s=make(7,10);const before=JSON.stringify(s);
 a.throws(()=>S.buyBonus(s,()=>0),/insuficientes/i);
 a.equal(JSON.stringify(s),before);
});
test('não permite comprar outra vez durante um bónus',()=>{
 const s=make();S.buyBonus(s,()=>0);
 const after=JSON.stringify(s);
 a.throws(()=>S.buyBonus(s,()=>0),/bónus ativo/i);
 a.equal(JSON.stringify(s),after);
});
test('não permite comprar com uma jogada pendente',()=>{
 const s=make();S.begin(s,()=>0);
 const after=JSON.stringify(s);
 a.throws(()=>S.buyBonus(s,()=>0),/jogada por terminar/i);
 a.equal(JSON.stringify(s),after);
});
test('bónus comprado é recuperável sem perder créditos nem duplicar compra',()=>{
 let s=make();const before=s.balanceU;
 S.buyBonus(s,()=>0);
 s=S.restore(JSON.parse(JSON.stringify(s)));
 a.equal(s.balanceU,before-S.units(100));
 a.equal(s.feature.left,8);
 a.equal(s.history.filter(h=>h.kind==='bonus-buy').length,1);
 a.equal(s.sequence,0);
});
test('8 Free Spins depois da compra não debitam outra aposta',()=>{
 const s=make();S.buyBonus(s,()=>0);
 const paid=s.balanceU;
 for(let n=0;n<8;n++){
  const before=s.balanceU;
  const next=S.begin(s,()=>0);
  a.equal(next.pending.free,true);
  a.equal(s.balanceU,before,'Iniciar Free Spin não debita');
  const settle=S.settle(s);
  a.equal(settle.free,true);
  a.equal(s.sequence,n+1,'A jogada continua a usar os IDs normais');
 }
 a.equal(s.feature,null,'8 spins terminam com resultado sem retriggers');
 a.equal(s.stats.free,8);
 a.ok(s.balanceU>=paid,'Nenhum débito além do preço');
 a.equal(s.history.filter(h=>h.kind==='bonus-buy').length,1);
});
