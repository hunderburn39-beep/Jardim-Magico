'use strict';
// Regression: o som de alerta só toca quando as colunas que JÁ PARARAM
// confirmam uma linha premiada. Não antecipa prémios nem mexe no motor.
const assert=require('node:assert/strict');
const fs=require('node:fs');
const vm=require('node:vm');
const path=require('node:path');
const E=require('../js/engine.js');
const cinema=fs.readFileSync(path.resolve(__dirname,'../js/cinematic-v12.js'),'utf8');
const audio=fs.readFileSync(path.resolve(__dirname,'../js/slot-sounds.js'),'utf8');
const match=cinema.match(/  function hasConfirmedSequence\(spin,activeLines\) \{([\s\S]*?)\n  \}\n  game\.cinematicV12\.hasConfirmedSequence/);
assert.ok(match,'Detector de sequências não encontrado');
const detector='function hasConfirmedSequence(spin,activeLines) {'+match[1]+'\n}';
const check=vm.runInNewContext('('+detector+')',{E},{timeout:1000});
const make=(columns,n)=>({
  grid:columns,
  stopped:[0,1,2,3,4].map(i=>i<n)
});
const standard=[
 ['J','10','A'],['Q','10','K'],['J','10','A'],
 ['Q','A','J'],['K','Q','10']
];
assert.equal(check(make(standard,2),1),false,'Duas cartas 10 não são sequência paga');
assert.equal(check(make(standard,3),1),true,'Três cartas 10 formam prémio');
const premium=[
 ['J','KING','10'],['Q','KING','A'],['A','J','10'],
 ['K','10','Q'],['K','10','J']
];
assert.equal(check(make(premium,1),1),false,'Primeiro tambor sozinho não paga');
assert.equal(check(make(premium,2),1),true,'Dois KING já pagam');
const topOnly=[
 ['10','J','K'],['10','Q','J'],['10','A','K'],
 ['A','K','J'],['J','10','K']
];
assert.equal(check(make(topOnly,3),1),false,'Linha superior desativada não avisa');
assert.equal(check(make(topOnly,3),2),true,'Linha superior ativa avisa');
const broken=[
 ['A','10','Q'],['K','J','10'],['10','Q','A'],
 ['J','A','10'],['K','10','Q']
];
assert.equal(check(make(broken,5),1),false,'Figuras não contíguas não são linha paga');
assert.equal(check(make(standard,1),1),false,'Não antecipar símbolos ainda a rodar');
const pending=make(premium,2);pending.stopped=[false,true,true,false,false];
assert.equal(check(pending,1),false,'Só analisar tambores parados desde a esquerda');
assert.match(cinema,/!spin\._sequenceAlerted/,'Alerta não deve repetir durante o mesmo giro');
assert.match(cinema,/spin\._sequenceAlerted=true/,'Marcar alerta como reproduzido');
assert.match(audio,/audio\.sequenceAlert=function\(/,'Efeito de alerta em falta');
assert.match(audio,/vol\.gain\.value=gongHere\?\.075:\.145/,'Não alterar pop suave dos tambores');
console.log('OK: 9 cenários de sequência e integração com efeitos de áudio.');
