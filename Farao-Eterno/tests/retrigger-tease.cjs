'use strict';
const test=require('node:test'),assert=require('node:assert/strict');
const fs=require('node:fs'),path=require('node:path');
const E=require('../js/engine.js'),S=require('../js/session.js');
const rng=(seed=1)=>()=>((seed=(Math.imul(seed,1664525)+1013904223)>>>0)/4294967296);
const code=fs.readFileSync(path.join(__dirname,'../js/cinematic-v12.js'),'utf8');
const sounds=fs.readFileSync(path.join(__dirname,'../js/slot-sounds.js'),'utf8');
test('o jogo base conserva os pesos e as Free Spins aumentam os Scatters sem forçar o terceiro',()=>{
 assert.equal(E.WEIGHTS.TOMB,3.85);
 assert.equal(E.FREE_WEIGHTS.TOMB,6.5);
 for(const sym of E.NORMAL)assert.equal(E.WEIGHTS[sym],E.FREE_WEIGHTS[sym]);
 const results=[];
 for(const weights of [E.WEIGHTS,E.FREE_WEIGHTS]){
  const rnd=rng(291);let retrigger=0,twos=0;
  for(let i=0;i<12000;i++){
   const grid=E.makeGrid(rnd,weights);
   assert(grid.every(col=>new Set(col).size===3),'sem duplicados num tambor');
   const count=E.countScatters(grid);
   if(count>=3)retrigger++;
   if(count===2)twos++;
  }
  results.push({retrigger,twos});
 }
 assert(results[0].retrigger>0);
 assert(results[1].retrigger>results[0].retrigger*2);
 assert(results[1].twos>results[0].twos*1.3);
 console.log('12.000 jogadas: base '+results[0].retrigger+
  ' ativações; Free Spins '+results[1].retrigger+' retriggers; '+results[1].twos+' teases de duas torres');
});
test('as Free Spins usam mesmo pesos reforçados; o jogo base não',()=>{
 const free=S.fresh();
 free.feature={left:8,played:0,totalU:0,symbols:['A'],retriggers:0};
 const referenceFree=E.makeGrid(rng(1059),E.FREE_WEIGHTS);
 const b=S.begin(free,rng(1059));
 assert.deepEqual(b.pending.grid,referenceFree);
 const base=S.fresh();base.balanceU=S.units(300);
 const referenceBase=E.makeGrid(rng(1059),E.WEIGHTS);
 const ordinary=S.begin(base,rng(1059));
 assert.deepEqual(ordinary.pending.grid,referenceBase);
});
test('três Scatters reais dentro das Free Spins dão +8 e um novo expansivo',()=>{
 const s=S.fresh(),grid=[
  ['TOMB','10','J'],['KING','TOMB','Q'],['K','A','TOMB'],
  ['J','Q','A'],['HORUS','10','K']
 ];
 s.feature={left:1,played:3,totalU:0,symbols:['10'],retriggers:0};
 s.feature.left=0;s.feature.played++;
 s.pending={id:++s.sequence,grid,free:true,bet:S.stake(s),lines:s.lines,
  nextSymbol:'J',paylinesVersion:2,expansionPayVersion:2};
 const payout=S.settle(s);
 assert.equal(payout.result.scatterCount,3);
 assert.equal(s.feature.left,8);
 assert.equal(s.feature.retriggers,1);
 assert.deepEqual(s.feature.symbols,['10','J']);
 assert.equal(s.history[0].rounds[0].scatters,3);
 assert.equal(s.history[0].id,1);
 S.validate(s);
 const restored=S.restore(JSON.parse(JSON.stringify(s)));
 assert.equal(restored.feature.left,8,'O retrigger recupera após fechar');
 assert.equal(restored.feature.retriggers,1);
});
test('o tease aparece apenas após dois Scatters parados e durante Free Spins',()=>{
 assert.match(code,/spin\.free && !spin\.teasePlayed/);
 assert.match(code,/count === 2 && spin\.wildFadeAt === null/);
 assert.match(code,/spin\.stopped\.some\(stopped => !stopped\)/);
 assert.match(code,/active\?\.free && this\.spinning/);
 assert.match(code,/FALTA 1 SCATTER!/);
 assert.match(code,/spin\.teaseEndAt=t/);
 assert.match(code,/this\.audio\.tease\?\.\(\)/);
 assert.match(sounds,/audio\.tease=function\(/);
 assert.match(sounds,/audio\.retrigger=function\(/);
});
