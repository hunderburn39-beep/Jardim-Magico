/* Faraó Eterno: equilíbrio do jogo base sem alterar Free Spins nem cofre. */
'use strict';
const test=require('node:test'),assert=require('node:assert/strict');
const E=require('../js/engine.js');
const S=require('../js/session.js');
const A=require('../js/economy.js');
function seeded(seed=20261113){
 return()=>{seed|=0;seed=seed+0x6D2B79F5|0;
  let t=Math.imul(seed^seed>>>15,1|seed);
  t=t+Math.imul(t^t>>>7,61|t)^t;
  return((t^t>>>14)>>>0)/4294967296;};
}
function oldWeights(level,free){
 const original=free?E.FREE_WEIGHTS:E.WEIGHTS;
 const boost=.58+(level-85)*.145,w={...original};
 for(const k of ['HORUS','MASK','ANUBIS','KING'])w[k]=original[k]*boost;
 w.TOMB=original.TOMB*(.78+(level-85)*.065);
 return w;
}
test('prémios e 10 linhas mantêm-se na tabela aprovada',()=>{
 assert.equal(E.PAYS.KING[5],5000);
 assert.equal(E.PAYS.ANUBIS[5],1600);
 assert.equal(E.LINES.length,10);
 assert.equal(E.upperLimit(1),5000);
});
test('Free Spins conservam integralmente pesos anteriores a esta alteração',()=>{
 for(let level=85;level<=95;level++){
  assert.deepEqual(A.weighted(level,true),oldWeights(level,true));
 }
});
test('jogo base privilegia Faraó e Anúbis sem mexer no Scatter',()=>{
 for(let level=85;level<=95;level++){
  const base=oldWeights(level,false),tuned=A.weighted(level,false);
  assert.equal(tuned.KING,base.KING*1.12);
  assert.equal(tuned.ANUBIS,base.ANUBIS*1.05);
  assert.equal(tuned.HORUS,base.HORUS*.94);
  assert.equal(tuned.MASK,base.MASK*.94);
  assert.equal(tuned.TOMB,base.TOMB);
  for(const k of ['10','J','Q','K','A'])assert.equal(tuned[k],base[k]);
 }
});
test('não há resultados forçados: cada tambor sorteado continua sem símbolos repetidos',()=>{
 const rng=seeded(),w=A.weighted(90,false);
 for(let i=0;i<5000;i++){
  const grid=E.makeGrid(rng,w);
  assert.ok(grid.every(c=>new Set(c).size===3));
  const r=E.evaluate(grid,1,10);
  assert.ok(r.total>=0&&Number.isFinite(r.total));
  assert.ok(r.lineWins.every(x=>x.amount>=0));
 }
});
test('cofre continua a pagar apenas valores autorizados, incluindo prémios grandes em base',()=>{
 const s=S.fresh(),rng=seeded(983);
 S.adjustCredits(s,'deposit',5000);
 A.adjustFund(s,'add',2000);
 let paid=0,free=0,bonus=0;
 while(paid<1200){
  const prior=s.economy.prizeU;
  const betU=S.units(S.stake(s));
  const isFree=!!s.feature;
  const started=S.begin(s,rng);
  const budget=prior+(isFree?0:Math.floor(betU*s.economy.level/100));
  assert.ok(S.units(started.result.total)<=budget);
  const out=S.settle(s);
  S.finishPayout(s);
  assert.ok(s.economy.prizeU>=0);
  assert.equal(s.economy.prizeU,budget-out.prizeU);
  if(out.free)free++;else{paid++;if(out.result.trigger)bonus++;}
 }
 assert.equal(paid,1200);
 assert.ok(free>=0&&bonus>=0);
});
