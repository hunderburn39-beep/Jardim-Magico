/* Halloween III: tambores mecânicos, WebAudio sem ficheiros de áudio externos. */
(function(root){"use strict";
class HalloweenReelAudio{
 constructor(scene){this.scene=scene;this.lastTick=-1;this.noise=null;this.noiseRate=0;}
 context(){
   if(this.scene.silence)return null;
   try{
     const AC=window.AudioContext||window.webkitAudioContext;
     if(!AC)return null;
     const ctx=this.scene.audio||(this.scene.audio=new AC());
     if(ctx.state==="suspended")ctx.resume().catch(()=>{});
     if(ctx.state==="closed")return null;
     if(!this.noise||this.noiseRate!==ctx.sampleRate){
       this.noise=ctx.createBuffer(1,Math.ceil(ctx.sampleRate*.11),ctx.sampleRate);
       const d=this.noise.getChannelData(0);let v=0;
       for(let i=0;i<d.length;i++){v=v*.62+(Math.random()*2-1)*.38;d[i]=v;}
       this.noiseRate=ctx.sampleRate;
     }
     return ctx;
   }catch{return null;}
 }
 play(kind,col=2,p=0){
   if(this.scene.silence)return;
   try{
     const ctx=this.context();if(!ctx)return;
     const at=ctx.currentTime;
     if(kind==="tick"){if(at-this.lastTick<.032)return;this.lastTick=at;}
     const stop=kind==="stop",start=kind==="start";
     const len=stop?.14:start?.18:.035,peak=stop?.084:start?.043:.017+p*.005;
     const filter=ctx.createBiquadFilter();filter.type="lowpass";
     filter.frequency.setValueAtTime(stop?1300:start?860:1450,at);filter.Q.value=.55;
     const g=ctx.createGain();g.gain.setValueAtTime(.0001,at);
     g.gain.linearRampToValueAtTime(peak,at+(stop?.008:.003));
     g.gain.exponentialRampToValueAtTime(.0001,at+len);
     filter.connect(g);
     if(ctx.createStereoPanner){
       const pan=ctx.createStereoPanner();pan.pan.setValueAtTime((col-2)*.18,at);
       g.connect(pan);pan.connect(ctx.destination);
     }else g.connect(ctx.destination);
     const o=ctx.createOscillator();o.type=stop?"triangle":start?"sawtooth":"triangle";
     const hz=stop?105+col*11:start?125:300+col*12;
     o.frequency.setValueAtTime(hz,at);
     o.frequency.exponentialRampToValueAtTime(Math.max(65,hz*(stop?.46:start?.64:.72)),at+len);
     o.connect(filter);o.start(at);o.stop(at+len+.012);
     const src=ctx.createBufferSource();src.buffer=this.noise;
     src.playbackRate.value=stop?.82:start?.62:1.38;
     const cut=ctx.createBiquadFilter();cut.type="highpass";cut.frequency.value=stop?190:start?130:680;
     const grain=ctx.createGain();grain.gain.value=stop?.72:start?.47:.34;
     src.connect(cut);cut.connect(grain);grain.connect(filter);
     src.start(at);src.stop(at+Math.min(len,this.noise.duration*.76));
     if(stop){
       const latch=ctx.createOscillator(),lg=ctx.createGain();latch.type="sine";
       latch.frequency.setValueAtTime(1020+col*65,at+.016);
       latch.frequency.exponentialRampToValueAtTime(530+col*24,at+.072);
       lg.gain.setValueAtTime(.0001,at+.016);
       lg.gain.linearRampToValueAtTime(.009,at+.021);
       lg.gain.exponentialRampToValueAtTime(.0001,at+.075);
       latch.connect(lg);lg.connect(g);latch.start(at+.016);latch.stop(at+.079);
     }
   }catch{/* Os sons nunca bloqueiam a jogada. */}
 }
 start(){this.play("start",2,0);}
 tick(col,progress){this.play("tick",col,progress);}
 stop(col){this.play("stop",col,1);}
}
root.HalloweenReelAudio=HalloweenReelAudio;
})(typeof globalThis!=="undefined"?globalThis:this);
