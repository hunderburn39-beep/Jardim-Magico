"use strict";
/* Testes de regressao do motor Zeus Clusters 7×7. Sem dinheiro real. */
const assert=require("node:assert/strict");
const fs=require("node:fs");
const vm=require("node:vm");
const path=require("node:path");
const base=path.resolve(__dirname,"..");
global.window={};
require(path.join(base,"js/regras-wild-v13.js"));
const R=global.window.TrovaoRules;
const P={crown:[2,6,16],hourglass:[1.5,4,12],ring:[1.25,3,10],
  goblet:[.9,2.2,8],ruby:[.7,1.7,6],amethyst:[.6,1.3,4.5],
  amber:[.5,1,3],emerald:[.4,.8,2.5],sapphire:[.35,.7,2]};
assert.equal(R.CLUSTER_COLS,7);
assert.equal(R.CLUSTER_ROWS,7);
assert.equal(R.CLUSTER_SIZE,49);
const symbols=R.KINDS;
const checker=()=>Array.from({length:49},(_,i)=>({
  id:i,kind:symbols[(Math.floor(i/7)+i%7)%9]}));
const noWin=checker();
assert.equal(R.clusterScore(noWin,P,1).result.length,0);
const win=checker();
for(let i=0;i<5;i++)win[i]={id:i,kind:"crown"};
const scored=R.clusterScore(win,P,1);
assert.equal(scored.result[0].count,5);
assert.equal(scored.pay,2);
assert.deepEqual(Array.from(scored.matchedPositions).sort((a,b)=>a-b),[0,1,2,3,4]);
const s0=R.emptySpots();
const s1=R.upgradeSpots(s0,scored.matchedPositions);
const s2=R.upgradeSpots(s1,scored.matchedPositions);
const s3=R.upgradeSpots(s2,scored.matchedPositions);
assert.equal(s0[0],0);assert.equal(s1[0],1);assert.equal(s2[0],2);assert.equal(s3[0],4);
const second=R.clusterScore(win,P,1,s2);
assert.equal(second.pay,20,"Cinco casas x2 somam multiplicador 10");
assert.equal(R.clusterScore(win,P,1,s2,3.50).pay,3.5,"Teto com centimos");
const temp=R.upgradeSpots(Array(49).fill(1024),[0,1,2]);
assert.equal(temp[0],1024);
for(const [n,award] of [[0,0],[2,0],[3,10],[4,12],[5,15],[6,20],[7,30],[9,30]])
 assert.equal(R.freeSpinAward(n),award);
let freshId=900;
const after=R.tumbleClusters(win,new Set([0,1,2,3,4]),()=>({id:freshId++,kind:"ruby"}));
assert.equal(after.length,49);
for(let c=0;c<5;c++)assert.ok(after[c].id>=900,"A coluna recebe uma peca nova na primeira casa");
console.log("Portoes Clusters: teste matematico de grupos, spots, limites e free spins aprovado.");
// A versao Phaser embutida deve ser executavel e nao apenas ficheiros js antigos.
const html=fs.readFileSync(path.join(base,"clusters-v19.html"),"utf8");
assert.ok(html.includes("Phaser 3.90.0"));
assert.ok(html.includes("Zeus Clusters 7×7"));
const scripts=Array.from(html.matchAll(/<script(?:\s[^>]*)?>([\s\S]*?)<\/script>/g));
const inlineLogic=scripts.map(x=>x[1]).find(x=>x.includes("const COLS = 7;"));
const phaserCode=scripts.map(x=>x[1]).find(x=>x.includes("class ThunderScene extends Phaser.Scene"));
assert.ok(phaserCode,"Falta o motor visual Phaser V19");
new vm.Script(phaserCode,{filename:"clusters-v19-phaser.js"});

assert.ok(inlineLogic,"Motor 7x7 nao foi incluido no HTML Phaser.");
const context={console,Math,Number,Array,Object,Set,WeakSet,Date};
context.globalThis=context;
vm.runInNewContext(inlineLogic,context,{timeout:4000});
const L=context.PTLogic;
assert.equal(L.COLS,7);
assert.equal(L.ROWS,7);
assert.equal(L.MIN_MATCH,5);
assert.equal(L.MAX_MULTIPLE,5000);
const grid=Array.from({length:7},(_,c)=>Array.from({length:7},(_,r)=>({
 type:(c+r)%9,multiplier:1
})));
for(let c=0;c<5;c++)grid[c][0]={type:8,multiplier:1};
let result=L.evaluate(grid,1,L.emptySpots());
assert.ok(result.wins.some(w=>w.type===8&&w.count>=5),"Phaser: 5 coroas ligadas premiam");
const marks=L.upgradeSpots(L.emptySpots(),result.winning);
const active=L.upgradeSpots(marks,result.winning);
const better=L.evaluate(grid,1,active);
assert.ok(better.total>result.total,"Phaser: spots x2 aumentam premio");
assert.ok(L.evaluate(grid,1,active,5).total<=5,"Phaser: limite de premio");
assert.equal(L.freeSpinAward(3),10);
console.log("Portoes Phaser embutido: engine 7x7, spots e teto validados.");
