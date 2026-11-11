/* Original procedural ambient score. No external samples or music. */
(()=>{
 'use strict';
 const g=window.__faraoGame,a=g.audio,init=a.init.bind(a);
 a.init=function(){
  try{const enabled=this.on;this.on=true;init();this.on=enabled;
   // Ganho mestre: cala imediatamente todos os sons, incluindo áudio já agendado.
   if(this.ctx&&!this.masterGain){
    this.masterGain=this.ctx.createGain();
    this.masterGain.gain.value=g.soundMode==='mute'?0:1;
    this.masterGain.connect(this.ctx.destination);
   }
   if(this.ctx&&!this.compressor){this.compressor=this.ctx.createDynamicsCompressor();this.compressor.threshold.value=-18;this.compressor.ratio.value=5;this.compressor.connect(this.masterGain||this.ctx.destination);this.out=this.compressor;}
   if(this.ctx&&!this.musicBus){this.musicBus=this.ctx.createGain();this.musicBus.gain.value=2.5;this.musicBus.connect(this.out||this.ctx.destination);}
   if(this.ctx&&this.masterGain)this.masterGain.gain.setTargetAtTime(g.soundMode==='mute'?0:1,this.ctx.currentTime,.012);
   if(!document.hidden&&!g.paused&&g.ledger.settings.music)this.startMusic();
  }catch(_){/* Blocked audio never blocks play. */}
 };
 a.setMasterMuted=function(muted){
  if(!this.ctx)return;
  const gain=this.masterGain?.gain;
  if(!gain)return;
  const t=this.ctx.currentTime;
  gain.cancelScheduledValues(t);
  gain.setTargetAtTime(muted?0:1,t,.012);
 };
 a.musicNodes=new Set();
 a.stopMusic=function(){clearInterval(this.musicTimer);this.musicTimer=null;for(const o of this.musicNodes){try{o.stop();}catch(_){}}this.musicNodes.clear();this.musicStep=0;};
 a.startMusic=function(){
  if(this.musicTimer||!this.ctx||!g.ledger.settings.music)return;
  const ctx=this.ctx,scale=[146.83,164.81,174.61,220,233.08,293.66,349.23,440];
  this.musicStep=0;this.musicNext=ctx.currentTime+.06;
  const schedule=()=>{
   if(document.hidden||g.paused||g.superseded||!g.ledger.settings.music){this.stopMusic();return;}
   while(this.musicNext<ctx.currentTime+.45){
    const n=this.musicStep++,when=this.musicNext;this.musicNext+=.78;
    if(n%4!==3){
     const notes=[0,4,3,1,0,6,4,2,0,3,7,4,2,1,4,0],freq=scale[notes[n%notes.length]],o=ctx.createOscillator(),env=ctx.createGain();o.type='sine';o.frequency.value=freq;
     env.gain.setValueAtTime(.0001,when);env.gain.exponentialRampToValueAtTime(.022,when+.035);env.gain.exponentialRampToValueAtTime(.0001,when+1.3);
     o.connect(env);env.connect(this.musicBus||this.out||ctx.destination);o.start(when);o.stop(when+1.35);this.musicNodes.add(o);o.onended=()=>{this.musicNodes.delete(o);env.disconnect();};
    }
    if(n%8===0){
     const o=ctx.createOscillator(),env=ctx.createGain();o.type='sine';o.frequency.value=n%16?73.42:55;env.gain.setValueAtTime(.0001,when);env.gain.linearRampToValueAtTime(.037,when+.7);env.gain.exponentialRampToValueAtTime(.0001,when+5.8);
     o.connect(env);env.connect(this.musicBus||this.out||ctx.destination);o.start(when);o.stop(when+6);this.musicNodes.add(o);o.onended=()=>{this.musicNodes.delete(o);env.disconnect();};
    }
   }
  };
  schedule();this.musicTimer=setInterval(schedule,250);
 };
 document.addEventListener('keydown',ev=>{
  const modal=document.getElementById('modal');if(modal.classList.contains('hidden')||ev.key!=='Tab')return;
  const items=[...modal.querySelectorAll('button:not(:disabled),a[href],input,select')],first=items[0],last=items.at(-1);
  if(ev.shiftKey&&document.activeElement===first){ev.preventDefault();last.focus();}else if(!ev.shiftKey&&document.activeElement===last){ev.preventDefault();first.focus();}
 });
 document.addEventListener('visibilitychange',()=>{if(document.hidden)a.stopMusic();});
})();
