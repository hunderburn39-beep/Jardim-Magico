'use strict';
const test=require('node:test'),assert=require('node:assert/strict');
const vm=require('node:vm'),fs=require('node:fs'),path=require('node:path');
const source=fs.readFileSync(path.join(__dirname,'../js/coin-celebrations.js'),'utf8');

function boot(){
 const nodes={oscillators:0,gains:0,coords:[]};
 const noop=()=>{};
 const sound={
  destination:{},currentTime:1,
  createOscillator(){
   nodes.oscillators++;
   return {type:'sine',frequency:{setValueAtTime:noop,exponentialRampToValueAtTime:noop},
    connect:noop,disconnect:noop,start:noop,stop:noop};
  },
  createGain(){
   nodes.gains++;
   return {gain:{setValueAtTime:noop,exponentialRampToValueAtTime:noop},
    connect:noop,disconnect:noop};
  }
 };
 const ctx={
  save:noop,restore:noop,beginPath:noop,ellipse:noop,fill:noop,stroke:noop,
  translate(x,y){nodes.coords.push({x,y});},rotate:noop
 };
 const result={lineWins:[],scatterWin:0,expanders:[]};
 const game={
  audio:{on:true,ctx:sound,out:sound.destination},
  paused:false,superseded:false,spinning:false,grid:[
   ['10','J','Q'],['K','A','10'],['10','J','K'],['A','10','J'],['Q','K','A']
  ],lastResult:result,baseStage:null,freeStage:null,
  activeBasePaylineAt(){return this.baseStage;},
  sequenceFocusAt(){return this.freeStage;},
  frame(t){this.drawFx(t);},drawFx(){},
  showSummary(){this.shown=(this.shown||0)+1;}
 };
 const document={
  hidden:false,getElementById(id){return id==='canvas'?{getContext:()=>ctx}:null;},
  querySelector:()=>null
 };
 vm.runInNewContext(source,{window:{__faraoGame:game,matchMedia:()=>({matches:false})},
  document,Math,console,WeakSet},{filename:'coin-celebrations.js'});
 return {game,nodes,document};
}
test('moedas saem uma vez por linha premiada, sem pagamentos adicionais',()=>{
 const {game,nodes}=boot();
 const line={count:3,rows:[1,1,1,1,1],expanded:false,line:0,amount:1};
 game.lastResult.lineWins=[line];
 game.baseStage={win:line,amount:1,start:100,end:1200};
 game.frame(210);assert.equal(game.coinCelebrations.events.sequences,0,'Não iniciar antes de a linha brilhar');
 game.frame(790);assert.equal(game.coinCelebrations.events.sequences,1);
 assert(game.coinCelebrations.particles>=15);
 const before=nodes.oscillators;
 game.frame(850);assert.equal(game.coinCelebrations.events.sequences,1);
 assert.equal(nodes.oscillators,before);
 assert(nodes.coords.every(p=>Number.isFinite(p.x)&&Number.isFinite(p.y)));
});
test('cada linha Free Spins tem as suas moedas e som na hora certa',()=>{
 const {game,nodes}=boot();
 const win={count:4,rows:[0,1,2,1,0],expanded:false,line:1,amount:3};
 game.freeStage={kind:'line',start:2000,preDelay:200,perReel:200,reels:[0,1,2,3],
  end:4600,win,amount:3};
 game.frame(2100);assert.equal(game.coinCelebrations.events.sequences,0);
 game.frame(2240);assert.equal(game.coinCelebrations.events.sequences,0,'Aguarda pela linha visível');
 game.frame(2820);assert.equal(game.coinCelebrations.events.sequences,1);
 assert(nodes.oscillators>=18,'Oito ou mais impactos metálicos por linha');
});
test('a celebração final é mais intensa e não se repete a cada frame',()=>{
 const {game,nodes}=boot();
 game.audio.fanfare(3);
 assert(nodes.oscillators>=45,'Queda longa de moedas no total final');
 const start=nodes.oscillators;
 game.v04Celebration={start:100,end:5000};
 game.frame(500);assert.equal(game.coinCelebrations.events.finales,1);
 game.frame(600);assert.equal(game.coinCelebrations.events.finales,1);
 assert.equal(nodes.oscillators,start);
});
test('som desligado e jogo em pausa nunca emitem novos impactos',()=>{
 const {game,nodes}=boot();
 game.audio.on=false;game.audio.fanfare(3);
 assert.equal(nodes.oscillators,0);
 game.audio.on=true;game.paused=true;game.audio.fanfare(3);
 assert.equal(nodes.oscillators,0);
});
