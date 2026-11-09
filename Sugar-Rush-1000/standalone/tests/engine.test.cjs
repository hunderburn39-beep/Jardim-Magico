"use strict";
const assert = require("node:assert/strict");
const E = require("../engine.js");
assert.equal(E.SIZE,7);
assert.equal(E.PAY.length,7);
assert.ok(E.PAY.every(row=>row.length===11));
function seeded(seed) {
  let s=seed>>>0;
  return ()=>{s=(Math.imul(s,1664525)+1013904223)>>>0;return s/4294967296;};
}
function checker() {
  return Array.from({length:7},(_,r)=>
    Array.from({length:7},(_,c)=>(r*3+c)%7));
}
const empty=checker();
assert.equal(E.findClusters(empty).length,0,"Grelha sem grupos nao pode pagar.");
const scatter=checker();
scatter[0][0]=7;scatter[2][2]=7;scatter[4][5]=7;
assert.equal(E.countScatters(scatter),3);
const awarded=E.calculateRound({betCents:20,initialBoard:scatter,rng:seeded(1)});
assert.equal(awarded.winCents,0);
assert.equal(awarded.freeAward,10);
assert.equal(E.awardFor(7),30);
assert.equal(E.awardFor(2),0);

const grouped=checker();
for(let c=0;c<5;c++)grouped[0][c]=0;
const groups=E.findClusters(grouped);
assert.ok(groups.some(g=>g.id===0&&g.cells.length>=5),"5 simbolos ligados devem premiar.");

const full=Array.from({length:7},()=>Array(7).fill(0));
const capped=E.calculateRound({betCents:20,initialBoard:full,capCents:150,rng:seeded(99)});
assert.equal(capped.winCents,150,"O limite de premio tem de ser respeitado.");
assert.equal(capped.capReached,true);
assert.ok(capped.frames.some(f=>f.kind==="win"));

const accumulated=E.blank();
accumulated[0][0]=4;
const r=E.calculateRound({betCents:20,initialBoard:grouped,spots:accumulated,
  capCents:500000,rng:seeded(27)});
assert.ok(r.winCents>=32,"Multiplicador persistente deve aumentar o premio.");
assert.ok(r.spots[0][0]>=8,"Uma casa ja multiplicadora sobe de nivel.");
assert.equal(accumulated[0][0],4,"O motor nao deve alterar o estado de entrada.");

const simRng=seeded(20261109);
let total=0,bonuses=0,max=0,withWins=0,rounds=2000;
for(let i=0;i<rounds;i++){
  const o=E.calculateRound({betCents:20,rng:simRng});
  assert.ok(Number.isSafeInteger(o.winCents) && o.winCents>=0);
  assert.ok(o.winCents<=20*E.MAX_WIN_MULT);
  assert.equal(o.board.length,7);
  assert.equal(o.board[0].length,7);
  assert.equal(o.freeAward,E.awardFor(o.scatters));
  assert.ok(o.cascades<=E.MAX_CASCADES);
  total+=o.winCents;
  if(o.winCents>0)withWins++;
  if(o.freeAward>0)bonuses++;
  max=Math.max(max,o.winCents);
}
console.log(JSON.stringify({
  resultado:"OK",girosBase:rounds,apostaCentimos:20,
  premiados:withWins,ativacoesBonus:bonuses,
  premiosTotaisCentimos:total,premioMaximoCentimos:max,
  aviso:"Distribuicao de amostra: NAO e RTP certificado; exclui a resolucao posterior de Free Spins."
},null,2));
