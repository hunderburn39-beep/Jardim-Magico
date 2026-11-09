'use strict';
/* Testa a regra EXACTA do som de Wild carregada em cinematic-v12.js.
   Os pagamentos, a contagem de Wilds e as animações não são alterados. */
const assert=require('node:assert/strict');
const fs=require('node:fs');
const path=require('node:path');
const vm=require('node:vm');
const code=fs.readFileSync(path.resolve(__dirname,'../js/cinematic-v12.js'),'utf8');
const match=code.match(/game\.shouldSoundWildLanding = function \(reel, spin = this\.spinObject\) \{[\s\S]*?\n  \};/);
assert.ok(match,'Regra de Wild não encontrada no código carregado');
const game={spinObject:null};
vm.runInNewContext(match[0],{game},{timeout:1000});
const grid=(wildReels)=>Array.from({length:5},(_,i)=>wildReels.includes(i)?['TOMB','J','Q']:['10','J','Q']);
const play=(reel,wildReels)=>game.shouldSoundWildLanding(reel,{grid:grid(wildReels)});
for(const reel of [3,4]){
 assert.equal(play(reel,[3,4]),false,'Não tocar gong com Wilds apenas em 4/5');
}
assert.equal(play(3,[3]),false,'Não tocar gong com Wild apenas no 4.º');
assert.equal(play(4,[4]),false,'Não tocar gong com Wild apenas no 5.º');
assert.equal(play(0,[0]),true,'Tocar gong no primeiro tambor');
assert.equal(play(1,[1,4]),true,'Tocar gong no segundo');
assert.equal(play(2,[2,4]),true,'Tocar gong no terceiro');
assert.equal(play(4,[0,4]),true,'Manter gong no quinto quando já há Wild no primeiro');
assert.equal(play(3,[2,3]),true,'Manter gong no quarto quando já há Wild no terceiro');
assert.equal(play(2,[3,4]),false,'Sem símbolo Wild no terceiro, sem som de Wild');
const audio=fs.readFileSync(path.resolve(__dirname,'../js/slot-sounds.js'),'utf8');
assert.match(audio,/game\.shouldSoundWildLanding\?\.\(reel,spin\)/,
 'Repor o som mecânico normal nos Wilds tardios sem gong');
console.log('Wild áudio: 10 cenários aprovados (tambores 1–5 e combinações 4/5).');
