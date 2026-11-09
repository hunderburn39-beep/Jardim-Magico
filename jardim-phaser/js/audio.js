/* Recorded MP3 effects (Kenney, CC0) and free licensed meditation piano.
   No oscillator tones. Music starts only after a user gesture. */
export const AUDIO_FILES=Object.freeze({
 click:'./assets/audio/click.mp3',spin:'./assets/audio/spin.mp3',
 stop:'./assets/audio/stop.mp3',cascade:'./assets/audio/cascade.mp3',
 win:'./assets/audio/win.mp3',bonus:'./assets/audio/bonus.mp3',
 wild:'./assets/audio/wild.mp3',scatter:'./assets/audio/scatter.mp3',
 coin:'./assets/audio/coin.mp3'
});
export const ZEN_TRACK='./assets/audio/zen-meditation.mp3';
const SETTINGS_KEY='jm-phaser-recorded-audio-v51';
export class GardenAudio{
 constructor({view=globalThis,storage,fetchFile,AudioType,ContextType}={}){
  this.view=view;this.ctx=null;this.effects=true;this.music=true;
  this.unlocked=false;this.suspended=false;this.background=null;
  this.buffers=new Map();this.active=new Set();this.lastPlayed=new Map();
  this.failed=new Set();this.onChange=null;
  this.epoch=0;
  this.fetchFile=fetchFile||view.fetch?.bind(view);
  this.AudioType=AudioType||view.Audio;
  this.ContextType=ContextType||view.AudioContext||view.webkitAudioContext;
  try{
   this.storage=storage||view.localStorage;
   const prefs=JSON.parse(this.storage?.getItem(SETTINGS_KEY)||'null');
   if(typeof prefs?.effects==='boolean')this.effects=prefs.effects;
   if(typeof prefs?.music==='boolean')this.music=prefs.music;
  }catch{this.storage=null}
 }
 get musicPlaying(){return !!this.background&&!this.background.paused}
 notify(){try{this.onChange?.()}catch{}}
 persist(){
  try{this.storage?.setItem(SETTINGS_KEY,JSON.stringify({effects:this.effects,music:this.music}))}catch{}
  this.notify();
 }
 allowed(){
  return !this.suspended&&!this.view.document?.hidden&&
   this.view.matchMedia?.('(orientation: landscape)')?.matches!==true;
 }
 ensure(){
  if(!this.ContextType)return null;
  try{
   if(!this.ctx||this.ctx.state==='closed'){
    this.ctx=new this.ContextType();
    this.master=this.ctx.createDynamicsCompressor?.()||null;
    if(this.master){
     this.master.threshold.value=-9;this.master.knee.value=12;this.master.ratio.value=5;
     this.master.attack.value=.003;this.master.release.value=.15;
     this.master.connect(this.ctx.destination);
    }
   }
   if(['suspended','interrupted'].includes(this.ctx.state))this.ctx.resume().catch(()=>{});
   return this.ctx;
  }catch{return null}
 }
 loadEffect(name){
  if(this.buffers.has(name))return this.buffers.get(name);
  const ctx=this.ensure(),url=AUDIO_FILES[name];
  if(!ctx||!url||!this.fetchFile)return Promise.resolve(null);
  const loading=this.fetchFile(url,{cache:'force-cache'}).then(response=>{
   if(!response.ok)throw Error('Audio unavailable');return response.arrayBuffer();
  }).then(bytes=>ctx.decodeAudioData(bytes)).catch(()=>{this.failed.add(name);return null});
  this.buffers.set(name,loading);return loading;
 }
 makeBackground(){
  if(this.background||!this.AudioType)return this.background;
  const player=new this.AudioType(ZEN_TRACK);
  player.preload='none';player.loop=true;player.volume=.6;
  player.setAttribute?.('playsinline','');
  for(const event of ['playing','pause','ended'])player.addEventListener?.(event,()=>this.notify());
  player.addEventListener?.('error',()=>{this.failed.add('music');this.notify()});
  this.background=player;return player;
 }
 startMusic(){
  if(!this.music||!this.unlocked||!this.allowed())return false;
  const player=this.makeBackground();if(!player)return false;
  if(player.paused)try{player.play()?.then(()=>this.notify()).catch(()=>this.notify())}catch{}
  return true;
 }
 unlock({startMusic=true}={}){
  if(!this.allowed())return false;
  this.unlocked=true;this.ensure();
  if(this.effects)for(const name of Object.keys(AUDIO_FILES))void this.loadEffect(name);
  if(startMusic)this.startMusic();return true;
 }
 async play(name){
  if(!this.effects||!AUDIO_FILES[name]||!this.unlocked||!this.allowed())return false;
  const now=Date.now(),last=this.lastPlayed.get(name)||0;
  if(now-last<100)return false;this.lastPlayed.set(name,now);
  const epoch=this.epoch;
  const buffer=await this.loadEffect(name);
  if(!this.effects||!this.allowed()||epoch!==this.epoch)return false;
  while(this.active.size>=8)this.stopOne(this.active.values().next().value);
  if(buffer&&this.ctx){
   try{
    const source=this.ctx.createBufferSource(),gain=this.ctx.createGain();
    source.buffer=buffer;gain.gain.value=name==='spin' ? .48 : .66;
    source.connect(gain);gain.connect(this.master||this.ctx.destination);
    const entry={source,gain,name};this.active.add(entry);
    source.onended=()=>this.stopOne(entry,false);
    source.start();return true;
   }catch{return false}
  }
  // Older browsers can still play the same recordings through HTML audio.
  if(this.AudioType)try{
   const player=new this.AudioType(AUDIO_FILES[name]);player.volume=.55;
   const entry={player,name};this.active.add(entry);
   player.addEventListener?.('ended',()=>this.stopOne(entry,false),{once:true});
   await player.play();return true;
  }catch{for(const entry of this.active)if(entry.player?.paused)this.stopOne(entry);return false}
  return false;
 }
 stopOne(entry,stop=true){
  if(!entry)return;this.active.delete(entry);
  try{if(stop)entry.source?.stop();entry.source?.disconnect();entry.gain?.disconnect();entry.player?.pause()}catch{}
 }
 stop(name){
  for(const entry of [...this.active])if(entry.name===name){
   if(entry.source&&entry.gain?.gain?.setTargetAtTime)try{
    entry.gain.gain.setTargetAtTime(0,this.ctx.currentTime,.008);
    entry.source.stop(this.ctx.currentTime+.04);
   }catch{this.stopOne(entry)}
   else this.stopOne(entry);
  }
 }
 pause(){
  this.epoch++;
  this.suspended=true;this.background?.pause();
  for(const entry of [...this.active])this.stopOne(entry);
  this.notify();
 }
 resume(){this.suspended=false;if(this.unlocked&&this.allowed()){this.ensure();this.startMusic()}this.notify()}
 toggleEffects(){
  this.effects=!this.effects;
  if(!this.effects){this.epoch++;for(const entry of [...this.active])this.stopOne(entry)}
  else this.unlock({startMusic:false});
  this.persist();return this.effects;
 }
 toggleMusic(){
  this.music=!this.music;
  if(this.music)this.unlock();else this.background?.pause();
  this.persist();return this.music;
 }
}
export const audio=new GardenAudio();
