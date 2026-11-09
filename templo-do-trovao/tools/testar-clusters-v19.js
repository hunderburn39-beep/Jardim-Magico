"use strict";
/* Portões do Trovão V19 — matemática do modo Clusters.
   Testes determinísticos, créditos virtuais. Não certificam RTP. */
const assert=require("node:assert/strict");
global.window={};
require("../js/regras-wild-v13.js");
const R=global.window.TrovaoRules;
const TABLE={
  crown:[2,6,16],hourglass:[1.5,4,12],ring:[1.25,3,10],
  goblet:[.9,2.2,8],ruby:[.7,1.7,6],amethyst:[.6,1.3,4.5],
  amber:[.5,1,3],emerald:[.4,.8,2.5],sapphire:[.35,.7,2]
};
assert.equal(R.CLUSTER_SIZE,49);
function checker(){
  return Array.from({length:49},(_,i)=>({id:i+1,kind:R.KINDS[(Math.floor(i/7)*3+i%7)%9]}));
}
const empty=R.emptySpots();
assert.equal(empty.length,49);
assert.ok(empty.every(s=>s===0));
assert.equal(R.clusterScore(checker(),TABLE,1,empty).pay,0,
  "Padrão sem cinco adjacentes não pode dar prémio");

const line=checker();
for(let c=0;c<5;c++)line[c]={id:c+1,kind:"crown"};
const first=R.clusterScore(line,TABLE,1,empty);
assert.ok(first.pay>=2,"Cinco coroas ligadas devem pagar");
assert.ok(first.result.some(x=>x.symbol==="crown"&&x.count>=5));
for(const i of first.matchedPositions)assert.ok(i>=0&&i<49);

const marks=R.upgradeSpots(empty,first.matchedPositions);
assert.ok([...first.matchedPositions].every(i=>marks[i]===1),
  "Primeiro prémio apenas marca a posição");
const active=R.upgradeSpots(marks,first.matchedPositions);
assert.ok([...first.matchedPositions].every(i=>active[i]===2),
  "Segundo prémio ativa ×2");
const doubled=R.clusterScore(line,TABLE,1,active);
assert.ok(doubled.pay>first.pay,"Spots ativos têm de aumentar o prémio");
const four=R.upgradeSpots(active,first.matchedPositions);
assert.ok([...first.matchedPositions].every(i=>four[i]===4));
assert.ok(empty.every(x=>x===0),"Nunca alterar os spots da entrada");
let spot=empty;for(let i=0;i<15;i++)spot=R.upgradeSpots(spot,[0]);
assert.equal(spot[0],1024,"Multiplicador nunca ultrapassa ×1024");

const withWild=checker();
for(let c=0;c<4;c++)withWild[c]={id:100+c,kind:"crown"};
withWild[4]={id:104,kind:"wild",value:5};
const wildWin=R.clusterScore(withWild,TABLE,1,empty);
assert.ok(wildWin.result.some(x=>x.symbol==="crown"&&x.wildCount>0));
const wildUsed=wildWin.result.reduce((n,g)=>n+g.positions.filter(i=>withWild[i].kind==="wild").length,0);
assert.equal(wildUsed,1,"WILD não pode ser contabilizado em grupos diferentes");
assert.ok(!wildWin.matchedPositions.has(48)||withWild[48].kind==="crown");

const disconnected=checker();
for(const i of [0,3,9,20,43])disconnected[i]={id:400+i,kind:"crown"};
assert.equal(R.clusterScore(disconnected,TABLE,1,empty).result.some(x=>x.symbol==="crown"&&x.count>=5),false,
  "Cinco símbolos afastados não devem premiar");

const huge=Array.from({length:49},(_,i)=>({id:i+1,kind:"crown"}));
const limit=R.clusterScore(huge,TABLE,10,Array(49).fill(1024),123);
assert.equal(limit.pay,123);
assert.equal(limit.capReached,true);

const fallen=R.tumbleClusters(line,new Set([0,7,14]),(()=>{let n=100;return ()=>({id:++n,kind:"ruby"})})());
assert.equal(fallen.length,49);
for(let col=1;col<7;col++){
  const prev=Array.from({length:7},(_,r)=>line[r*7+col].id);
  const after=Array.from({length:7},(_,r)=>fallen[r*7+col].id);
  assert.deepEqual(after,prev,"Coluna sem remoções não se deve alterar");
}
assert.equal(R.freeSpinAward(2),0);
assert.equal(R.freeSpinAward(3),10);
assert.equal(R.freeSpinAward(4),12);
assert.equal(R.freeSpinAward(5),15);
assert.equal(R.freeSpinAward(6),20);
assert.equal(R.freeSpinAward(7),30);
assert.equal(R.maxActiveSpot([0,1,2,4]),4);

let seed=20261109;
const rng=()=>{seed^=seed<<13;seed^=seed>>>17;seed^=seed<<5;return (seed>>>0)/4294967296};
let paid=0,hits=0,features=0,winCap=0;
const SPINS=2500;
for(let i=0;i<SPINS;i++){
  let grid=Array.from({length:49},(_,j)=>R.drawCluster(false,100000+i*49+j,rng));
  let spots=R.emptySpots(),total=0,award=0;
  for(let step=0;step<45;step++){
    const scatters=grid.filter(x=>x.kind==="scatter").length;
    award=Math.max(award,R.freeSpinAward(scatters));
    const w=R.clusterScore(grid,TABLE,1,spots,5000-total);
    assert.ok(w.pay>=0 && Number.isFinite(w.pay));
    if(w.result.length===0)break;
    total+=w.pay;assert.ok(total<=5000.00001);
    spots=R.upgradeSpots(spots,w.matchedPositions);
    if(total>=5000)break;
    grid=R.tumbleClusters(grid,w.matchedPositions,()=>R.drawCluster(false,10000000+Math.floor(rng()*9999999),rng));
  }
  if(total>0)hits++;
  if(award)features++;
  paid+=total;winCap=Math.max(winCap,total);
}
console.log(JSON.stringify({
  status:"OK",spins:SPINS,
  baseOnlyAvgX:paid/SPINS,baseOnlyHitPct:100*hits/SPINS,
  baseScatterTriggerPct:100*features/SPINS,maxWinObservedX:winCap,
  note:"RTP NÃO certificado; amostra só de giros base, sem resolver bónus."
},null,2));
