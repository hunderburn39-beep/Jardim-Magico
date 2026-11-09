/* Faraó Eterno — biblioteca de efeitos originais V1.2.8.
   Referência de estilo: ruído de rotação, paragens amortecidas, suspenso
   de duas torres e pequenas celebrações. Sem extrair áudio de outro jogo.
   Apenas apresentação; não altera motor, RNG, pagamentos nem resultados.
*/
(() => {
  'use strict';
  const game=window.__faraoGame, audio=game?.audio;
  if(!game || !audio) return;
  const ready=()=>audio.on && audio.ctx && !document.hidden && !game.paused;
  const out=()=>audio.out||audio.ctx.destination;
  const now=(delay=0)=>audio.ctx.currentTime+delay;

  // Oscilador com caída suave — evita tons agudos / 'laser' nos impactos.
  function note(from,to,dur,volume,delay=0,shape='sine'){
    if(!ready())return;
    try{
      const ac=audio.ctx,t=now(delay),osc=ac.createOscillator(),env=ac.createGain();
      osc.type=shape;
      osc.frequency.setValueAtTime(from,t);
      osc.frequency.exponentialRampToValueAtTime(Math.max(25,to),t+dur);
      env.gain.setValueAtTime(.0001,t);
      env.gain.exponentialRampToValueAtTime(Math.max(.0001,volume),t+.009);
      env.gain.exponentialRampToValueAtTime(.0001,t+dur);
      osc.connect(env);env.connect(out());osc.start(t);osc.stop(t+dur+.02);
      osc.onended=()=>{try{osc.disconnect();env.disconnect();}catch(_){}};
    }catch(_){}
  }

  // Textura mecânica gerada localmente: não reproduz amostras proprietárias.
  function scratch(dur,volume,high=110,low=1100,delay=0){
    if(!ready())return;
    try{
      const ac=audio.ctx,t=now(delay);
      if(!audio._egyptSoftNoise || audio._egyptSoftNoise.sampleRate!==ac.sampleRate){
        const buffer=ac.createBuffer(1,Math.round(ac.sampleRate*.09),ac.sampleRate),
              samples=buffer.getChannelData(0);
        let seed=0x1f2e3d4c;
        for(let i=0;i<samples.length;i++){
          seed^=seed<<13;seed^=seed>>>17;seed^=seed<<5;
          samples[i]=((seed>>>0)/2147483648-1)*.78;
        }
        audio._egyptSoftNoise=buffer;
      }
      const src=ac.createBufferSource(),hp=ac.createBiquadFilter(),
            lp=ac.createBiquadFilter(),env=ac.createGain();
      src.buffer=audio._egyptSoftNoise;src.loop=true;
      src.playbackRate.value=.92+(.065*(Math.sin(t*19)+1));
      hp.type='highpass';hp.frequency.value=high;
      lp.type='lowpass';lp.frequency.value=low;
      env.gain.setValueAtTime(.0001,t);
      env.gain.exponentialRampToValueAtTime(Math.max(volume,.0001),t+.006);
      env.gain.exponentialRampToValueAtTime(.0001,t+dur);
      src.connect(hp);hp.connect(lp);lp.connect(env);env.connect(out());
      src.start(t);src.stop(t+dur+.01);
      src.onended=()=>{try{src.disconnect();hp.disconnect();lp.disconnect();env.disconnect();}catch(_){}};
    }catch(_){}
  }

  // Breve arranque da fita sem bip de sintetizador.
  audio.spin=function(){
    scratch(.115,.008,180,950);
    note(108,76,.16,.008);
  };

  // Paragem: 1 único 'tuc' de madeira / mecanismo, grave e curto.
  audio.tick=function(reel){
    note(130-reel*3,71-reel*2,.115,.023);
    scratch(.033,.013,175,980);
  };

  // As torres têm um aviso próprio, sem duplicar a pancada da paragem.
  audio.sanctuaryLanding=function(reel,count){
    if(count===1){
      note(345,265,.23,.011);
    }else if(count===2 && game.spinning){
      // Suspense quando a segunda torre fica visível.
      note(160,118,.55,.013);
      note(275,239,.43,.007,.055);
      scratch(.40,.005,280,750);
    }else if(count>=3){
      note(260,195,.24,.013);
      note(389,315,.35,.012,.08);
    }
  };

  // Som de linha premiada com brilho discreto, sem estampidos.
  audio.paylineChime=function(index){
    const pitches=[392,440,493.88,523.25,587.33];
    const f=pitches[index%pitches.length];
    note(f,f*.87,.26,.014);
    note(f*1.5,f*1.31,.21,.005,.06);
  };

  // Pequena recompensa melódica, com volume controlado.
  audio.win=function(bonus=false){
    const pitches=bonus?[293.66,440,587.33,698.46]:[329.63,440,523.25];
    pitches.forEach((f,i)=>note(f,f*.91,.30,bonus?.021:.015,i*.105));
  };
  audio.extra=function(){
    [261.63,329.63,440,587.33].forEach((f,i)=>
      note(f,f*.94,.34,.022,i*.115,'sine'));
  };

  game.slotSounds={version:'1.2.8',original:true,procedural:true};
})();
