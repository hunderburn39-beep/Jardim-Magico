/* Faraó Eterno: faixa normal desde a capa, música de bónus nas Free Spins.
 * Elementos HTMLAudio são pré-carregados ao abrir o site e iniciados na primeira
 * interação permitida pelo navegador (ou por autoplay, quando autorizado).
 * A primeira jogada nunca cria outra instância nem reinicia a faixa.
 * A mudança de tema pausa a faixa anterior, preservando currentTime.
 */
(()=>{
'use strict';
const game=window.__faraoGame, fx=game?.audio;
if(!game||!fx)return;
const normalURL='assets/pharaohs-ritual.mp3?v=1436a1f8';
const bonusURL='assets/bonus-ritual.mp3?v=1.0';
function makeTrack(url,volume){
  const element=document.createElement('audio');
  element.preload='auto';
  element.loop=true;
  element.controls=false;
  element.setAttribute('playsinline','');
  element.src=url;
  element.volume=volume;
  element.load();
  return element;
}
const tracks={
 normal:makeTrack(normalURL,.75),
 bonus:makeTrack(bonusURL,.75)
};
let activeTrack=null;
let loadingTarget=null;
let requestId=0;
let lastRequested=null;
const inBonus=()=>!!(!game._introCoverVisible&&(game.feature||game.pendingSummary||game._summaryAck));
const desired=()=>inBonus()?'bonus':'normal';
const allowed=()=>!!(game.soundMode==='all'&&game.ledger?.settings?.music&&
 !game.superseded&&!document.hidden&&(!game.paused||game._introCoverVisible));
function silence(){
  requestId++;
  loadingTarget=null;
  for(const a of Object.values(tracks))a.pause();
  activeTrack=null;
}
function sync(forceGesture=false){
  if(!allowed())return;
  const target=desired(),next=tracks[target];
  if(!next)return;
  if(activeTrack===target&&!next.paused)return;
  if(loadingTarget===target&&!forceGesture)return;
  // play() é chamado imediatamente, dentro do pointerdown quando existir.
  // Não aguardar por fetch/decodeAudioData antes de tentar iniciar o som.
  const currentRequest=++requestId;
  loadingTarget=target;
  lastRequested=target;
  let attempt;
  try{attempt=next.play();}
  catch(_){loadingTarget=null;return;}
  const success=()=>{
    if(currentRequest!==requestId||!allowed()||desired()!==target){
      next.pause();
      return;
    }
    loadingTarget=null;
    for(const [name,el] of Object.entries(tracks)){
      if(name!==target)el.pause();
    }
    activeTrack=target;
  };
  const failure=()=>{
    if(currentRequest===requestId)loadingTarget=null;
  };
  if(attempt&&typeof attempt.then==='function')attempt.then(success).catch(failure);
  else success();
}
fx.startMusic=function(){sync();};
fx.unlockMusic=function(){sync(true);};
fx.stopMusic=function(){silence();};
// O jogo inicializa o contexto WebAudio para os sons dos tambores;
// a música gravada, pelo contrário, não espera pelo decodeAudioData.
const oldInit=fx.init.bind(fx);
fx.init=function(...args){
  const result=oldInit(...args);
  sync();
  return result;
};
const watcher=setInterval(()=>{
  if(allowed()){
    if(loadingTarget!==desired()&&(activeTrack!==desired()||tracks[desired()].paused))sync();
  }else if(activeTrack||loadingTarget){
    silence();
  }
},320);
document.addEventListener('visibilitychange',()=>{
  if(document.hidden)silence();
  else if(allowed())sync();
});
game.ambientLoop={
  version:'1.7-instant-html-audio',
  get source(){return desired()==='bonus'?'Faraó Eterno — Bonus Ritual':"Pharaoh's Ritual";},
  get normalReady(){return tracks.normal.readyState>=2;},
  get bonusReady(){return tracks.bonus.readyState>=2;},
  get activeTrack(){return activeTrack;},
  get ready(){return tracks.normal.readyState>=2;},
  get normalPosition(){return tracks.normal.currentTime;},
  get bonusPosition(){return tracks.bonus.currentTime;},
  get loadingTarget(){return loadingTarget;},
  get lastRequested(){return lastRequested;},
  crossfade:false,
  stopWatcher(){clearInterval(watcher);}
};
// O carregamento da apresentação deve tentar começar a música logo.
// O Safari iOS só liberta a reprodução após um toque do utilizador.
sync();
})();