'use strict';
const assert=require('node:assert/strict');
const motion=require('../reels_motion.js');
const target=[
 [1,2,3,4,5],
 [2,3,4,5,6],
 [3,4,5,6,7]
];
motion.validateBoard(target);
assert.deepEqual(motion.metrics,{
 reelWidth:115,cellHeight:102,pitch:106,stageWidth:611,stageHeight:314
});
assert.equal((800-motion.metrics.stageWidth)/2,94.5);
for(let col=0;col<5;col++){
 const strip=motion.buildStrip(target,col,()=>9);
 assert.deepEqual(strip.values.slice(0,3),target.map(row=>row[col]));
 assert(strip.numberOfExtraCells>=13);
 assert(strip.values.length>=strip.numberOfExtraCells+3);
 assert.equal(strip.startTranslate,-strip.numberOfExtraCells*106);
 assert.equal(strip.endTranslate,0);
 assert.equal(strip.durationMs,1900+col*265);
 assert(strip.values.slice(3).every(x=>x===9));
}
assert.throws(()=>motion.validateBoard([[1,2]]),/3 filas e 5 colunas/);
console.log('PASS: 5 flowing columns, exact final symbols, easing schedule and central 611px reel grid');
