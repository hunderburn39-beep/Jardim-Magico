/* Faraó Eterno — efeitos originais V1.3.0.
   Mecanismo de rotação com velocidade variável, travagem e paragem amortecida.
   Síntese WebAudio local, sem gravações proprietárias e sem mexer no RNG.
*/
(() => {
  'use strict';
  const game=window.__faraoGame, audio=game?.audio;
  if(!game || !audio) return;
  const ready=()=>audio.on && audio.ctx && !document.hidden && !game.paused;
  const output=()=>audio.out||audio.ctx.destination;
  const clamp=(v,lo,hi)=>Math.min(hi,Math.max(lo,v));

  function note(from,to,dur,volume,delay=0,shape='sine'){
    if(!ready())return;
    try{
      const ac=audio.ctx,t=ac.currentTime+delay,osc=ac.createOscillator(),env=ac.createGain();
      osc.type=shape;
      osc.frequency.setValueAtTime(from,t);
      osc.frequency.exponentialRampToValueAtTime(Math.max(25,to),t+dur);
      env.gain.setValueAtTime(.0001,t);
      env.gain.exponentialRampToValueAtTime(Math.max(.0001,volume),t+.006);
      env.gain.exponentialRampToValueAtTime(.0001,t+dur);
      osc.connect(env);env.connect(output());osc.start(t);osc.stop(t+dur+.02);
      osc.onended=()=>{try{osc.disconnect();env.disconnect();}catch(_){}};
    }catch(_){}
  }

  // Ruído controlado em memória: uma pequena amostra original de fricção.
  function noiseBuffer(){
    const ac=audio.ctx;
    if(audio._reelNoiseV13 && audio._reelNoiseV13.sampleRate===ac.sampleRate)
      return audio._reelNoiseV13;
    const length=Math.round(ac.sampleRate*.48),buf=ac.createBuffer(1,length,ac.sampleRate),
          a=buf.getChannelData(0);
    let seed=0x1853ac27,smoothed=0;
    for(let i=0;i<length;i++){
      seed^=seed<<13;seed^=seed>>>17;seed^=seed<<5;
      const white=(seed>>>0)/2147483648-1;
      smoothed=.78*smoothed+.22*white;
      // Textura irregular, sem ciclo de notas agudas.
      a[i]=clamp(smoothed*.91+white*.09,-1,1);
    }
    audio._reelNoiseV13=buf;
    return buf;
  }

  function friction(duration,vol,cutoff=1250,delay=0){
    if(!ready())return;
    try{
      const ac=audio.ctx,t=ac.currentTime+delay,src=ac.createBufferSource(),
        hp=ac.createBiquadFilter(),lp=ac.createBiquadFilter(),env=ac.createGain();
      src.buffer=noiseBuffer();
      hp.type='highpass';hp.frequency.value=180;
      lp.type='lowpass';lp.frequency.value=cutoff;
      env.gain.setValueAtTime(.0001,t);
      env.gain.exponentialRampToValueAtTime(Math.max(.0001,vol),t+.004);
      env.gain.exponentialRampToValueAtTime(.0001,t+duration);
      src.connect(hp);hp.connect(lp);lp.connect(env);env.connect(output());
      src.start(t);src.stop(t+duration+.01);
      src.onended=()=>{try{src.disconnect();hp.disconnect();lp.disconnect();env.disconnect();}catch(_){}};
    }catch(_){}
  }

  audio.startReelWhirr=function(){
    this.stopReelWhirr();
    if(!ready())return;
    try{
      const ac=this.ctx,t=ac.currentTime,src=ac.createBufferSource(),
        hp=ac.createBiquadFilter(),lp=ac.createBiquadFilter(),env=ac.createGain(),
        motor=ac.createOscillator(),motorGain=ac.createGain();
      src.buffer=noiseBuffer();src.loop=true;src.playbackRate.value=.82;
      hp.type='highpass';hp.frequency.value=145;
      lp.type='lowpass';lp.frequency.value=850;lp.Q.value=.35;
      env.gain.setValueAtTime(.0001,t);
      env.gain.exponentialRampToValueAtTime(.012,t+.14);
      src.connect(hp);hp.connect(lp);lp.connect(env);env.connect(output());
      motor.type='sine';motor.frequency.value=55;
      motorGain.gain.setValueAtTime(.0001,t);
      motorGain.gain.exponentialRampToValueAtTime(.003,t+.12);
      motor.connect(motorGain);motorGain.connect(output());
      src.start(t);motor.start(t);
      this._reelAudio={source:src,filter:lp,highpass:hp,gain:env,motor,motorGain,
        lastUpdate:0,lastGear:0};
      src.onended=()=>{try{src.disconnect();hp.disconnect();lp.disconnect();env.disconnect();}catch(_){}};
      motor.onended=()=>{try{motor.disconnect();motorGain.disconnect();}catch(_){}};
    }catch(_){this._reelAudio=null;}
  };

  audio.stopReelWhirr=function(){
    const s=this._reelAudio;this._reelAudio=null;
    if(!s||!this.ctx)return;
    try{
      const t=this.ctx.currentTime;
      for(const gain of [s.gain.gain,s.motorGain.gain]){
        gain.cancelScheduledValues(t);
        gain.setTargetAtTime(.0001,t,.035);
      }
      s.source.stop(t+.20);s.motor.stop(t+.20);
    }catch(_){}
  };

  // Segue as 5 paragens calculadas pela animação, incluindo a travagem
  // especial depois de duas torres, sem alterar a duração real da jogada.
  audio.updateReelWhirr=function(t,spin){
    const s=this._reelAudio;
    if(!s)return;
    if(!ready()||!game.spinning||!spin){this.stopReelWhirr();return;}
    if(t-s.lastUpdate<55)return; // poucas automações de áudio por segundo
    s.lastUpdate=t;
    let active=0,motion=0;
    for(let c=0;c<5;c++){
      if(spin.stopped[c])continue;
      active++;
      const stop=spin.stopAt?.[c]??(spin.start+spin.duration+c*spin.reelGap);
      const p=clamp((t-spin.start)/Math.max(1,stop-spin.start),0,1);
      motion+=p<.13?p/.13:p>.78?(1-p)/.22:1;
    }
    if(!active){this.stopReelWhirr();return;}
    const speed=clamp(motion/active,0,1),weight=active/5;
    const ac=this.ctx,at=ac.currentTime;
    // Motor acelera, mantém velocidade, abranda e desaparece por etapas.
    s.source.playbackRate.setTargetAtTime(.66+.72*speed,at,.085);
    s.filter.frequency.setTargetAtTime(680+1250*speed,at,.075);
    // Quando um Wild toca, as engrenagens recuam para deixar o PAAAMMM em primeiro plano.
    const elapsed=t-(this._wildSoundAtMs??-10000);
    const duck=elapsed<0?1:elapsed<380?.28:elapsed<1120
      ?.28+.72*((elapsed-380)/740):1;
    s.gain.gain.setTargetAtTime((.002+.015*weight*(.34+.66*speed))*duck,at,.08);
    s.motor.frequency.setTargetAtTime(53+21*speed,at,.09);
    s.motorGain.gain.setTargetAtTime(.0005+.003*weight*speed,at,.09);
    // Catraca muito discreta, ligeiramente mais espaçada durante a travagem.
    const gearPeriod=95+120*(1-speed);
    if(t-s.lastGear>=gearPeriod && speed>.16){
      s.lastGear=t;
      friction(.022,.0029*weight,720+540*speed);
    }
  };

  audio.spin=function(){
    friction(.10,.008,980);
    note(108,79,.16,.006);
  };

  // Os tambores normais mantêm o "clac" curto. Ao revelar um Wild,
  // ignorar o clac para deixar ouvir um só PAAAMMM, sem duas pancadas.
  audio.tick=function(reel){
    if(game.spinObject?.grid?.[reel]?.includes('TOMB'))return;
    friction(.042,.016,1360);
    note(152-reel*4,79-reel*2,.13,.016);
    friction(.020,.004,640,.015);
  };

  // "PAAAAAMMMMM": efeito principal do Wild, muito acima do som dos
  // tambores. Médios e harmónicos para se ouvir bem em altifalantes móveis.
  // A compressão master existente limita os picos para evitar saturação.
  function wildPaam(count){
    if(!ready())return;
    const ac=audio.ctx,t=ac.currentTime;
    const force=count<=1 ? 1 : count===2 ? 1.12 : 1.22;
    const duration=count>=3 ? 1.75 : 1.55;
    audio._wildSoundAtMs=performance.now();

    // Dar prioridade real ao Wild: ambiente quase em silêncio durante
    // o impacto e a ressonância. Depois a música regressa gradualmente.
    const music=audio.musicBus?.gain;
    if(music){
      try{
        music.cancelScheduledValues(t);
        music.setValueAtTime(Math.max(.001,music.value),t);
        music.setTargetAtTime(.10,t,.022);
        music.setTargetAtTime(2.5,t+duration-.13,.28);
      }catch(_){}
    }
    try{
      function layer(type,from,to,peak,length,cutoff,attack=.010){
        const osc=ac.createOscillator(),lp=ac.createBiquadFilter(),
              env=ac.createGain();
        osc.type=type;
        osc.frequency.setValueAtTime(from,t);
        osc.frequency.exponentialRampToValueAtTime(Math.max(25,to),t+length*.77);
        lp.type='lowpass';
        lp.frequency.setValueAtTime(cutoff,t);
        lp.frequency.exponentialRampToValueAtTime(Math.max(330,cutoff*.62),
          t+Math.min(.65,length*.69));
        env.gain.setValueAtTime(.0001,t);
        env.gain.exponentialRampToValueAtTime(peak*force,t+attack);
        env.gain.exponentialRampToValueAtTime(.0001,t+length);
        osc.connect(lp);lp.connect(env);env.connect(output());
        osc.start(t);osc.stop(t+length+.03);
        osc.onended=()=>{try{osc.disconnect();lp.disconnect();env.disconnect();}catch(_){}};
      }
      // Todas as camadas começam juntas: um PAAAAM único, não vários bipes.
      layer('triangle',245,164,.25,duration,1850);
      layer('sawtooth',395,263,.11,duration*.88,1620);
      layer('sine',117,81,.105,duration*.94,690);
      layer('triangle',590,385,.080,duration*.75,2100);
      layer('sine',820,555,.056,duration*.53,2300);
      // Pancada inicial, seguida de um "mmmm" longo e cheio.
      friction(.105,.073*force,2200);
    }catch(_){
      note(265,152,1.22,.13*force);
    }
  }
  audio.sanctuaryLanding=function(reel,count){
    wildPaam(count);
  };

  audio.paylineChime=function(index){
    const tones=[392,440,493.88,523.25,587.33],f=tones[index%tones.length];
    note(f,f*.90,.25,.013);
    note(f*1.5,f*1.34,.19,.004,.065);
  };
  audio.win=function(bonus=false){
    const tones=bonus?[293.66,440,587.33,698.46]:[329.63,440,523.25];
    tones.forEach((f,i)=>note(f,f*.91,.30,bonus?.020:.015,i*.11));
  };
  audio.extra=function(){
    [261.63,329.63,440,587.33].forEach((f,i)=>note(f,f*.94,.34,.022,i*.115));
  };

  // Este wrapper apenas atualiza a síntese áudio depois do frame existente.
  const baseFrame=game.frame.bind(game);
  game.frame=function(t){
    baseFrame(t);
    this.audio.updateReelWhirr?.(t,this.spinObject);
  };
  document.addEventListener('visibilitychange',()=>{
    if(document.hidden)audio.stopReelWhirr();
  });
  game.slotSounds={version:'1.3.4',original:true,procedural:true,dynamicReelSpeed:true};
})();
