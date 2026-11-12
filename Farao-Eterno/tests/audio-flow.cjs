'use strict';
// Testes de regressão da música. Sem acesso à rede ou a áudio real.
const assert=require('node:assert/strict');
const fs=require('node:fs');
const path=require('node:path');
const vm=require('node:vm');
const script=fs.readFileSync(path.join(__dirname,'..','js','ambient-loop.js'),'utf8');
let time=0;
const callbacks=[],audios=[],events={};
const ctx={
 currentTime:0,destination:{},
 createMediaElementSource(){return {connect(){}};},
 createGain(){return {gain:{value:0,setTargetAtTime(v){this.value=v;}},connect(){}};}
};
const doc={
 hidden:false,
 createElement(tag){
  assert.equal(tag,'audio');
  const item={
   paused:true,volume:1,readyState:4,currentTime:0,playCount:0,
   setAttribute(){},load(){},
   play(){this.paused=false;this.playCount++;return Promise.resolve();},
   pause(){this.paused=true;}
  };
  audios.push(item);return item;
 },
 addEventListener(name,callback){events[name]=callback;}
};
const g={
 feature:{left:5,played:3},_introCoverVisible:true,paused:true,
 soundMode:'all',ledger:{settings:{music:true}},superseded:false,
 audio:{ctx,init(){},startMusic(){},stopMusic(){}}
};
vm.runInNewContext(script,{
 window:{__faraoGame:g},
 document:doc,
 performance:{now:()=>time},
 setInterval(fn){callbacks.push(fn);return callbacks.length;},
 clearInterval(){},
 Promise
},{filename:'ambient-loop.js'});
async function advance(steps){
 for(let i=0;i<steps;i++){
  time+=65;ctx.currentTime=time/1000;
  for(const fn of callbacks)fn();
  for(let j=0;j<5;j++)await Promise.resolve();
 }
}
(async()=>{
 await advance(3);
 assert.equal(g.ambientLoop.activeTrack,'normal','Capa deve tocar música normal mesmo ao retomar bónus');
 assert.equal(audios[1].playCount,0,'A capa não pode arrancar o Bonus Ritual');
 const normalPlays=audios[0].playCount;
 g.audio.init();await advance(4);
 assert.equal(audios[0].playCount,normalPlays,'Nova inicialização não reinicia a faixa');
 audios[0].currentTime=28.75;
 g._introCoverVisible=false;g.paused=false;g.audio.unlockMusic();
 await advance(20);
 assert.equal(g.ambientLoop.activeTrack,'bonus','Entrada no bónus troca a faixa');
 assert.equal(audios[0].paused,true,'A música base fica em pausa após fade');
 g.feature=null;g.pendingSummary={totalU:1000};
 await advance(20);
 assert.equal(g.ambientLoop.activeTrack,'normal','Última Free Spin devolve a música base');
 assert.equal(audios[1].paused,true,'A música do bónus pára após fade');
 assert.equal(audios[0].currentTime,28.75,'Retomar música normal sem voltar ao início');
 g.soundMode='mute';await advance(2);
 assert.equal(audios[0].paused,true,'Mute pára a música');
 g.soundMode='all';g.audio.unlockMusic();await advance(3);
 assert.equal(g.ambientLoop.activeTrack,'normal','Restaurar áudio retoma tema normal');
 doc.hidden=true;events.visibilitychange();
 assert.equal(audios[0].paused,true,'Ocultar aplicação pausa a música');
 console.log('Audio PASS: intro normal, retomada, Bonus Ritual, crossfade, mute e visibilidade');
})().catch(e=>{console.error(e.stack||String(e));process.exitCode=1;});
