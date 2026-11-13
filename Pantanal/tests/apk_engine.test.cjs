'use strict';
const assert=require('node:assert/strict'),e=require('../apk_engine.js');
const same=(sym)=>Array.from({length:3},()=>Array.from({length:5},()=>sym));
const empty=()=>Array.from({length:3},()=>Array.from({length:5},()=>-1));
assert.equal(e.LINES.length,20);
assert.deepEqual(e.LEVELS,[1,5,9,15,25]);
assert.equal(e.betTotal(2,25),50);
const p0=e.resultBoard(same(0),25,2);
assert.equal(p0.wins.filter(w=>w.vertical).length,5);
assert.equal(p0.wins.filter(w=>!w.vertical).length,20);
assert.equal(p0.total,20*200*2+5*100*2);
for(let s=0;s<10;s++){
 const board=empty();for(let r=0;r<3;r++)board[r][2]=s;
 const o=e.resultBoard(board,25,3);
 const w=o.wins.find(w=>w.line===23);
 assert(w, 'linha 23 em símbolo '+s);
 assert.equal(w.amount,e.VERTICAL_PAYS[s]*3);
}
for(let c=0;c<5;c++){
 let b=empty();for(let r=0;r<3;r++)b[r][c]=2;
 assert.equal(e.resultBoard(b,25,1).bonus.dice,1);
 assert.equal(e.resultBoard(b,15,1).bonus.dice,0);
}
for(let symbol=0;symbol<10;symbol++){
 const b=empty();for(let c=0;c<5;c++)b[e.LINES[0][c]][c]=symbol;
 const r=e.resultBoard(b,1,1);
 assert(r.wins.some(w=>w.line===1&&w.symbol===symbol));
}
assert.equal(e.resultBoard(same(4),1,1).total,2000);
assert.equal(e.resultBoard(same(9),1,1).bonus.cauldron,3);
assert.equal(e.resultBoard(same(6),1,1).jackpots[0].type,'max');
assert.equal(e.resultBoard(same(7),1,1).jackpots[0].type,'med');
// Distribuições do sorteio dos dados e da roleta (80/100 e 60/100).
assert.equal(e.diceRoll(()=>0,false).multiplier,1);
assert.equal(e.diceRoll(()=>0.99,false).multiplier,4);
assert.equal(e.diceRoll(()=>0.96,true).multiplier,6);
assert.equal(e.wheelRoll(()=>0,false,300).multiplier,1);
assert.equal(e.wheelRoll(()=>0.99,false,300).multiplier,7);
assert.equal(e.wheelRoll(()=>0.999,true,300).multiplier,24);
const d=e.difficulty(5000,0,50,()=>0);
assert.equal(d.active,true);
assert.equal(d.limit,42500);
assert.equal(e.difficulty(80000,600,50,()=>0).active,false);
assert.equal(e.difficulty(60000,5,5,()=>0).limit,50000);
assert.equal(e.strawberryDeck(()=>0).length,12);
assert.equal(e.strawberryPay(5,2,3),600);
assert.equal(e.cauldronPay(3,2,1),300);
assert.equal(e.pumpkinPay(7,2,1),2000);
const current=e.defaultJackpots();
assert.deepEqual(Object.keys(current).sort(),['max','med','min']);
assert.equal(e.awardJackpot(current,'min').amount,50000);
assert(e.advanceJackpots(current,1000,0,5).min>current.min);
for(let i=0;i<5000;i++){
 const b=e.generateBoard(null,false,Math.random);
 for(const row of b)for(const s of row)assert(s>=0&&s<10);
 const w=e.resultBoard(b,25,1);
 assert(w.total>=0&&Number.isFinite(w.total));
}
console.log('PASS: horizontal masks, 25 paylines, 4 bonuses, jackpots, difficulty, RNG and 5000 rounds');
