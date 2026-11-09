/* Original procedural ambient score. No external samples or music. */
(()=>{
 'use strict';
 const g=window.__faraoGame,a=g.audio,init=a.init.bind(a);
 a.init=function(){
  try{const enabled=this.on;this.on=true;init();this.on=enabled;
   if(this.ctx&&!this.compressor){this.compressor=this.ctx.createDynamicsCompressor();this.compressor.threshold.value=-18;this.compressor.ratio.value=5;this.compressor.connect(this.ctx.destination);this.out=this.compressor;}
   if(!document.hidden&&!g.paused&&g.ledger.settings.music)this.startMusic();
  }catch(_){/* Blocked audio never blocks play. */}
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
     env.gain.setValueAtTime(.0001,when);env.gain.exponentialRampToValueAtTime(.012,when+.035);env.gain.exponentialRampToValueAtTime(.0001,when+1.3);
     o.connect(env);env.connect(this.out||ctx.destination);o.start(when);o.stop(when+1.35);this.musicNodes.add(o);o.onended=()=>{this.musicNodes.delete(o);env.disconnect();};
    }
    if(n%8===0){
     const o=ctx.createOscillator(),env=ctx.createGain();o.type='sine';o.frequency.value=n%16?73.42:55;env.gain.setValueAtTime(.0001,when);env.gain.linearRampToValueAtTime(.021,when+.7);env.gain.exponentialRampToValueAtTime(.0001,when+5.8);
     o.connect(env);env.connect(this.out||ctx.destination);o.start(when);o.stop(when+6);this.musicNodes.add(o);o.onended=()=>{this.musicNodes.delete(o);env.disconnect();};
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
