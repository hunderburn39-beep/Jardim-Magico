/* PORTÕES DO TROVÃO — motor áudio V5 (estéreo 48 kHz, MP3 320 kbps).
   O master de design foi produzido em 32-bit float; WebAudio descodifica
   para PCM float32, com até 32 vozes simultâneas e limiter no bus master.
   Sem reprodução automática: o contexto só é desbloqueado por interação. */
(()=>{
  "use strict";
  const PREVIOUS=window.TrovaoAudio;
  const SRC="assets/sons/efeitos-320k-v5.mp3";
  const CUES={
    spin:{offset:0,duration:.72},drop1:{offset:.81,duration:.28},
    drop2:{offset:1.18,duration:.28},drop3:{offset:1.55,duration:.28},
    tumble:{offset:1.92,duration:.62},burst:{offset:2.63,duration:.83},
    reward:{offset:3.55,duration:1.12},mult:{offset:4.76,duration:.8},
    scatter:{offset:5.65,duration:1.6},bigwin:{offset:7.34,duration:2.44},
    ui:{offset:9.87,duration:.18}
  };
  const PRIORITY={spin:1,drop:1,tumble:2,ui:1,burst:3,reward:2,mult:2,scatter:4,bigwin:5};
  const VOLUME={spin:.42,drop:.76,tumble:.58,burst:.74,reward:.64,mult:.7,scatter:.76,bigwin:.81,ui:.38};
  const MAX_VOICES=32;
  let ctx=null,buffer=null,master=null,compressor=null,enabled=true,loading=null;
  let bytes=null,voices=[],lastDrop=0,dropIndex=0,ready=false;
  const clamp=(v,l,h)=>Math.min(h,Math.max(l,v));
  const Ctx=window.AudioContext||window.webkitAudioContext;
  // Pré-carregar bytes sem criar áudio audível nem pedir permissão de reprodução.
  if(typeof fetch==="function"){
    loading=fetch(SRC,{cache:"force-cache"}).then(r=>{if(!r.ok)throw Error("HTTP "+r.status);return r.arrayBuffer()})
      .then(arr=>{bytes=arr;return arr}).catch(err=>{console.warn("SFX MP3 indisponível, a usar áudio de reserva",err);return null});
  }
  function build(){
    if(ctx||!Ctx)return;
    ctx=new Ctx({latencyHint:"interactive"});
    master=ctx.createGain();master.gain.value=.78;
    compressor=ctx.createDynamicsCompressor();
    compressor.threshold.value=-16;compressor.knee.value=13;compressor.ratio.value=6;
    compressor.attack.value=.003;compressor.release.value=.19;
    master.connect(compressor);compressor.connect(ctx.destination);
  }
  function decode(){
    if(!ctx||buffer||!bytes||ready)return;
    ready=true;
    // decodeAudioData pode consumir o ArrayBuffer, por isso fazemos uma cópia.
    ctx.decodeAudioData(bytes.slice(0)).then(b=>{buffer=b;bytes=null}).catch(e=>{
      ready=false;console.warn("Descodificação SFX falhou",e);
    });
  }
  function unlock(){
    if(!enabled)return false;
    // Desbloquear também o sintetizador de impactos no mesmo gesto do
    // utilizador. Essencial no Safari/iPhone para ouvir pedras a pousar.
    PREVIOUS?.unlock?.();
    try{
      build();
      if(!ctx)return false;
      if(ctx.state!=="running")ctx.resume().catch(()=>{});
      if(bytes)decode();
      else if(loading&&!ready){loading.then(()=>decode());}
      return true;
    }catch(e){return false}
  }
  function stopVoice(v){
    try{v.source.stop()}catch(_){}
    try{v.source.disconnect();v.gain.disconnect();v.pan?.disconnect()}catch(_){}
    voices=voices.filter(item=>item!==v);
  }
  function addVoice(priority){
    if(voices.length<MAX_VOICES)return true;
    const candidate=[...voices].sort((a,b)=>a.priority-b.priority||a.start-b.start)[0];
    if(!candidate||candidate.priority>priority)return false;
    stopVoice(candidate);return true;
  }
  function fallback(key,opts){
    if(PREVIOUS?.play)PREVIOUS.play(key,opts);
  }
  function play(name,opts={}){
    if(!enabled)return;
    // A aterragem tem uma segunda camada física sintetizada, mesmo quando
    // o pacote MP3 está carregado, para se ouvir o impacto sobre a música.
    if(name==="land"){fallback("land",opts);return;}
    const key=name==="drop"?["drop1","drop2","drop3"][(dropIndex++)%3]:name;
    const cue=CUES[key]||CUES.ui;
    const base=name==="drop"?"drop":name;
    if(base==="drop"){
      const t=performance.now();
      if(t-lastDrop<21)return;
      lastDrop=t;
    }
    unlock();
    if(!buffer||!ctx||ctx.state!=="running"){fallback(name,opts);return}
    const priority=PRIORITY[base]||1;
    if(!addVoice(priority))return;
    try{
      const source=ctx.createBufferSource();
      const gain=ctx.createGain();
      const pan=typeof ctx.createStereoPanner==="function"?ctx.createStereoPanner():null;
      const pos=clamp(((opts.column??2.5)-2.5)/4,-.78,.78);
      const variation=base==="drop"?.958+Math.random()*.086:base==="burst"?.984+Math.random()*.034:1;
      source.buffer=buffer;source.playbackRate.value=variation;
      gain.gain.value=clamp((VOLUME[base]??.55)*(opts.level??1),.04,.95);
      source.connect(gain);
      if(pan){pan.pan.value=pos;gain.connect(pan);pan.connect(master)}
      else gain.connect(master);
      const voice={source,gain,pan,priority,start:ctx.currentTime};
      voices.push(voice);
      source.onended=()=>stopVoice(voice);
      source.start(0,cue.offset,cue.duration);
    }catch(e){fallback(name,opts)}
  }
  function setEnabled(yes){
    enabled=Boolean(yes);
    PREVIOUS?.setEnabled?.(enabled);
    if(enabled)unlock();
    else{
      voices.slice().forEach(stopVoice);
      if(ctx?.state==="running")ctx.suspend().catch(()=>{});
    }
    return enabled;
  }
  window.TrovaoAudio={play,setEnabled,unlock,
    isEnabled:()=>enabled,getInfo:()=>({loaded:Boolean(buffer),voices:voices.length,maxVoices:MAX_VOICES,
      format:"48 kHz / MP3 320 kbps; master 32-bit float"})};
})();
