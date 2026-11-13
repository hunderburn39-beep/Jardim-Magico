'use strict';
const assert=require('node:assert/strict');
const e=require('../apk_engine.js');
const pos=(board,tableIndex)=>{
 const col=Math.floor((tableIndex-1)/3),row=(tableIndex-1)%3;
 return board[row][col];
};
const count=(board,symbol)=>board.flat().filter(x=>x===symbol).length;
const uniformRand=x=>()=>x;
// Níveis 1,5,9,15,25 e pagamentos constantes anteriores.
assert.equal(e.generateRound(null,true,uniformRand(.2)).board.length,3);
const standard=e.generateRound(null,false,uniformRand(.99),{lines:25,betPerLine:1,spinCount:0});
assert(standard.meta.guards.includes('min'));
assert(standard.meta.guards.includes('med'));
assert(standard.meta.guards.includes('max'));

// Guard do acumulado MAX (símbolo 7 na tabela Android, índice 6 na web).
// Faixa escolhe 5 ícones do símbolo 7 no conjunto dos 15; travaAcum(7)
// transforma a segunda e a quinta ocorrência em figuras 1..5.
{
 let i=0;const r=()=>i++<5?0:0.99;
 const x=e.generateRound(null,false,r,{lines:25,betPerLine:1,spinCount:100});
 assert.equal(count(x.board,6),3,'max guard 5->3');
 assert(x.meta.guards.includes('max'));
}
// Guard do acumulado MED (símbolo 8 na tabela Android, índice 7 na web).
{
 let i=0;const r=()=>i++<5?0.93:0.99;
 const x=e.generateRound(null,false,r,{lines:25,betPerLine:1,spinCount:100});
 assert.equal(count(x.board,7),3,'med guard 5->3');
 assert(x.meta.guards.includes('med'));
}
// Force de baixa jogada/alta dificuldade: sorteio 30 => símbolos 10 nas
// casas tabela[2], [5], [8] (linha central dos 3 primeiros tambores).
{
 let i=0;
 const r=()=>i++===15?29.1/400:.99;
 const x=e.generateRound(null,true,r,{
   lines:25,betPerLine:2,generatorCount:100,numSorteio:200
 });
 assert(x.meta.forced.includes('low-spin-30'),'sorteio 30');
 assert.equal(pos(x.board,2),9);
 assert.equal(pos(x.board,5),9);
 assert.equal(pos(x.board,8),9);
}
// Evento MED autorizado força exatamente uma das 5 linhas originais.
// Fazer sorteio 1 repetidamente para visitar 1o desenho [1,5,9,11,13].
{
 const x=e.generateRound(null,true,uniformRand(0),{
   lines:25,betPerLine:2,generatorCount:250,canPay:true,
   medEnabled:true,maxEnabled:false,numSorteio:200
 });
 assert(x.meta.medTriggered);
 assert(x.meta.forced.includes('med-1'));
 for(const p of [1,5,9,11,13])assert.equal(pos(x.board,p),7);
}
// Evento MAX autorizado depois de 239 jogadas força o padrão, mas
// o símbolo MAX só pode surgir quando canPay e maxEnabled forem verdadeiros.
{
 const x=e.generateRound(null,true,uniformRand(0),{
   lines:25,betPerLine:2,generatorCount:250,canPay:true,
   medEnabled:false,maxEnabled:true,numSorteio:200
 });
 assert(x.meta.maxTriggered);
 assert(x.meta.forced.includes('max-1'));
 for(const p of [1,5,9,11,13])assert.equal(pos(x.board,p),6);
}
// Distribuição de morangos original base e restrita sob transição.
{
 const base=e.strawberryDeck(uniformRand(0));
 assert.equal(base.length,12);
 assert(base.includes(5));
 const baseAgain=e.strawberryDeck(uniformRand(0),{
   triggerCount:3,paymentMode:false,existingBonus:0
 });
 assert(baseAgain.includes(5));
 const restricted=e.strawberryDeck(uniformRand(0),{
   triggerCount:3,paymentMode:false,existingBonus:100
 });
 assert.equal(restricted.filter(x=>x===6).length,4);
 assert(!restricted.includes(5));
}
// O novo avaliador não deve deixar entrar indices de símbolos inválidos.
for(let j=0;j<5000;j++){
 const active=j%2===0;
 const x=e.generateRound(null,active,Math.random,{
   lines:25,betPerLine:2,generatorCount:j,numSorteio:200,
   canPay:j>=239,medEnabled:j%17===0,maxEnabled:j%23===0
 });
 assert.equal(x.board.length,3);
 for(const row of x.board)for(const sym of row)
   assert(sym>=0&&sym<=9&&Number.isInteger(sym));
 const prize=e.resultBoard(x.board,25,2);
 assert(prize.total>=0&&Number.isFinite(prize.total));
}
console.log('PASS: jackpot guards, forced line patterns, early bonuses, straw decks, 5000 generated rounds');
