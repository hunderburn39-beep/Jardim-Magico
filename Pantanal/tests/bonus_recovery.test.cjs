'use strict';
const assert=require('node:assert/strict');
const engine=require('../apk_engine.js');
const ui=require('../bonus_ui.js');
const job=ui.createPending({strawberry:2,cauldron:1,wheel:3,dice:2},10);
assert.deepEqual(job,{
 version:1,strawberry:2,cauldron:1,wheel:3,dice:2,betPerLine:10
});
assert(ui.hasPending(job));
assert(!ui.hasPending(ui.createPending({},10)));
assert(!ui.hasPending(null));
assert.deepEqual(ui.createPending({wheel:-500,dice:Infinity},1).wheel,0);
assert.equal(ui.createPending({dice:101},1).dice,100);
assert.equal(ui.createPending({dice:3,betPerLine:40},1).betPerLine,40);
// APK Generate aplica sempre a última faixa à primeira coluna,
// inclusive quando o modo de pagamento está desligado.
const ctx={lines:25,betPerLine:2,generatorCount:600,canPay:true};
const a=engine.generateRound(null,false,()=>.12,ctx).board;
const b=engine.generateRound(null,true,()=>.12,ctx).board;
for(let row=0;row<3;row++)assert.equal(a[row][0],b[row][0],
 'Primeira coluna deve usar a mesma distribuição final de GeneratorNew.Generate');
console.log('PASS: bonus checkpoint schema and first-reel final APK thresholds');
