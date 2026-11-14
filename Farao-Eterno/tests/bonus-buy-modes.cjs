'use strict';
const test=require('node:test'),assert=require('node:assert/strict');
const E=require('../js/engine.js'),S=require('../js/session.js');
require('../js/economy.js');
globalThis.TombEngine=E;
globalThis.FaraoSession=S;
require('../js/super-scatter-core.js');

function session(){
 const s=S.fresh();
 S.adjustCredits(s,'deposit',1000);
 return s;
}
test('comprar 3 pirâmides custa 100× e aguarda a animação de ativação',()=>{
 const s=session(),before=s.balanceU;
 const bought=S.buyBonus(s,()=>.2,undefined,'regular');
 assert.equal(S.credits(bought.costU),100);
 assert.equal(S.credits(before-s.balanceU),100);
 assert.equal(s.feature.purchasedMode,'regular');
 assert.equal(s.feature.purchaseRevealPending,true);
 assert.equal(s.feature.superMultiplier,undefined);
 assert.equal(s.feature.left,8);
 assert.equal(s.sequence,0);
 assert.equal(s.history.filter(x=>x.kind==='bonus-buy').length,1);
 assert.equal(S.restore(JSON.parse(JSON.stringify(s))).feature.purchaseRevealPending,true);
});
test('comprar Super Scatter custa 200× e prepara o dado guardado',()=>{
 const s=session(),before=s.balanceU;
 const bought=S.buyBonus(s,()=>.2,undefined,'super');
 assert.equal(S.credits(bought.costU),200);
 assert.equal(S.credits(before-s.balanceU),200);
 assert.equal(s.feature.purchasedMode,'super');
 assert.equal(s.feature.purchaseRevealPending,true);
 assert.equal(s.feature.superMultiplier,3);
 assert.equal(s.feature.superFace,1);
 assert.equal(s.feature.superRollSeen,false);
 assert.equal(s.feature.left,8);
 assert.equal(s.sequence,0);
 assert.equal(s.history[0].mode,'super');
 const restored=S.restore(JSON.parse(JSON.stringify(s)));
 assert.equal(restored.feature.superMultiplier,3);
 assert.equal(restored.feature.purchaseRevealPending,true);
});
test('saldo insuficiente ou tipo inválido nunca debitam nem abrem bónus',()=>{
 const s=S.fresh();s.balanceU=S.units(150);
 const before=JSON.stringify(s);
 assert.throws(()=>S.buyBonus(s,()=>.4,undefined,'super'),/insuficiente/i);
 assert.equal(JSON.stringify(s),before);
 assert.throws(()=>S.buyBonus(s,()=>.4,undefined,'outro'),/inválido/i);
 assert.equal(JSON.stringify(s),before);
});
