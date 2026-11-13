'use strict';
const assert=require('node:assert/strict');
const p=require('../original_rules.js');
assert.equal(p.LINES20.length,20);
assert.deepEqual(p.LEVELS,[1,5,9,15,25]);
assert.deepEqual(p.LINES20[19],[2,1,1,1,0]);
assert.equal(p.stake(2,25),50);
const empty=()=>Array.from({length:3},()=>Array(5).fill(3));
for(let s=0;s<10;s++){
 const b=empty();for(let r=0;r<3;r++)b[r][1]=s;
 const v=p.verticalResults(b,25,2);
 const w=v.wins.find(x=>x.line===22);
 assert(w,'missed line 22 for symbol '+s);
 assert.equal(w.amount,p.VERTICAL_PAYS[s]*2);
 assert.equal(w.count,3);
 assert.equal(w.vertical,true);
}
for(let c=0;c<5;c++){
 const b=Array.from({length:3},()=>Array(5).fill(4));
 for(let r=0;r<3;r++)b[r][c]=2;
 const v=p.verticalResults(b,25,1);
 assert(v.wins.some(w=>w.line===21+c && w.symbol===2));
 assert.equal(v.bonus.dice,1);
 assert.equal(p.verticalResults(b,15,1).bonus.dice,0);
}
const board=Array.from({length:3},()=>Array(5).fill(0));
assert.equal(p.verticalResults(board,25,3).total,1500);
assert.equal(p.verticalResults(board,25,3).wins.length,5);
assert.equal(p.diceRoll(()=>0,false).multiplier,1);
assert.equal(p.diceRoll(()=>0.25,false).multiplier,2);
assert.equal(p.diceRoll(()=>0.749,false).multiplier,3);
assert.equal(p.diceRoll(()=>0.99,false).multiplier,4);
assert.equal(p.diceRoll(()=>0.84,true).multiplier,5);
assert.equal(p.diceRoll(()=>0.96,true).multiplier,6);
assert.equal(p.dicePrize(150, {multiplier:4}),600);
assert.throws(()=>p.verticalResults([[1]],25,1),/tabuleiro/);
console.log('PASS: 20 percursos, 5 verticais, 10 pagamentos, triggers, RNG dados e aposta por linha');
