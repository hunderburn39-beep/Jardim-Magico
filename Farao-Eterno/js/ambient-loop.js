/* Faraó Eterno: Pharaoh's Ritual na capa/jogo, Bonus Ritual só nas Free Spins.
 * Faixas em HTMLAudio para arranque imediato após o toque; GainNodes do WebAudio
 * permitem transições suaves de 0,9 s também no Safari, sem reiniciar a música.
 */
(()=>{
'use strict';
const g=window.__faraoGame,fx=g?.audio;
if(!g||!fx)return;
if(g._introCoverVisible===undefined)g._introCoverVisible=true;
const V=.75,FADE=.9;
function create(url){
 const el=document.createElement('audio');
 el.preload='auto';el.loop=true;el.controls=false;el.setAttribute('playsinline','');
 el.src=url;el.volume=V;el.load();
 return {el,gain:null,level:0,pending:false,retryAfter:0};
}
const tracks={
 normal:create('assets/pharaohs-ritual.mp3?v=1436a1f8'),
 bonus:create('assets/bonus-ritual.mp3?v=1.0')
};
let active=null,lastRequested=null,lastTick=performance.now(),mixed=false;
const now=()=>performance.now();
const wanted=()=>!g._introCoverVisible&&!!g.feature?'bonus':'normal';
const allowed=()=>g.soundMode==='all'&&!!g.ledger?.settings?.music&&
 !g.superseded&&!document.hidden&&(!g.paused||g._introCoverVisible);
function mixer(){
 if(mixed||!fx.ctx||!fx.ctx.createMediaElementSource)return;
 try{
  const ctx=fx.ctx;
  for(const s of Object.values(tracks)){
   const src=ctx.createMediaElementSource(s.el),gain=ctx.createGain();
   gain.gain.value=s.level;
   src.connect(gain);gain.connect(ctx.destination);
   s.gain=gain;s.el.volume=1;
  }
  mixed=true;
 }catch(_){/* Se não houver WebAudio, a música continua em HTMLAudio. */}
}
function setLevel(s,value){
 const v=Math.max(0,Math.min(V,value));
 s.level=v;
 if(s.gain&&fx.ctx){
  try{s.gain.gain.setTargetAtTime(v,fx.ctx.currentTime,.014);}
  catch(_){s.gain.gain.value=v;}
 }else{
  try{s.el.volume=v;}catch(_){}
 }
}
function silence(){
 for(const s of Object.values(tracks)){
  setLevel(s,0);s.el.pause();s.pending=false;
 }
 active=null;lastTick=now();
}
function start(name,gesture){
 if(!allowed())return;
 const s=tracks[name];
 if(s.pending||(!gesture&&now()<s.retryAfter))return;
 if(!s.el.paused){active=name;return;}
 s.pending=true;lastRequested=name;
 let p;
 try{p=s.el.play();}
 catch(_){s.pending=false;s.retryAfter=now()+1600;return;}
 Promise.resolve(p).then(()=>{
  s.pending=false;s.retryAfter=0;
  if(!allowed()||wanted()!==name){
   if(s.level<.001)s.el.pause();
   return;
  }
  active=name;
 }).catch(()=>{s.pending=false;s.retryAfter=now()+1600;});
}
function sync(gesture=false){
 if(!allowed()){
  if(active||Object.values(tracks).some(s=>!s.el.paused))silence();
  return;
 }
 mixer();
 const name=wanted();
 if(active!==name||tracks[name].el.paused)start(name,gesture);
 const t=now(),dt=Math.min(.2,Math.max(0,(t-lastTick)/1000));
 lastTick=t;
 for(const [key,s] of Object.entries(tracks)){
  const goal=active===key&&!s.el.paused?V:0;
  const step=mixed?V*dt/FADE:V;
  if(step>0)setLevel(s,s.level<goal?Math.min(goal,s.level+step):Math.max(goal,s.level-step));
  if(key!==active&&!s.pending&&s.level<.001&&!s.el.paused)s.el.pause();
 }
}
fx.startMusic=function(){sync();};
fx.unlockMusic=function(){sync(true);};
fx.stopMusic=function(){silence();};
const baseInit=fx.init.bind(fx);
fx.init=function(...args){const result=baseInit(...args);mixer();sync();return result;};
const timer=setInterval(()=>sync(),65);
document.addEventListener('visibilitychange',()=>{if(document.hidden)silence();else sync();});
g.ambientLoop={
 version:'1.8-cinema-crossfade',
 get source(){return active==='bonus'?'Faraó Eterno — Bonus Ritual':"Pharaoh's Ritual";},
 get activeTrack(){return active;},
 get normalReady(){return tracks.normal.el.readyState>=2;},
 get bonusReady(){return tracks.bonus.el.readyState>=2;},
 get ready(){return tracks.normal.el.readyState>=2;},
 get normalPosition(){return tracks.normal.el.currentTime;},
 get bonusPosition(){return tracks.bonus.el.currentTime;},
 get loadingTarget(){return tracks[wanted()].pending?wanted():null;},
 get lastRequested(){return lastRequested;},
 get normalVolume(){return tracks.normal.level;},
 get bonusVolume(){return tracks.bonus.level;},
 crossfade:true,
 stopWatcher(){clearInterval(timer);}
};
sync();
})();
