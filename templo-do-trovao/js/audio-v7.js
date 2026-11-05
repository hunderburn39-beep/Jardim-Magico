/* PORTÕES DO TROVÃO V9 — som da moeda no SCATTER e clique no GIRAR.
 * Mantém intacto o MP3 fornecido: apenas muda o evento que o reproduz.
 * Som de clique gerado em PCM float32, curto, alto e sem som de rotação.
 * Os impactos individuais de couro e restantes SFX continuam independentes.
 */
(()=>{
  "use strict";
  const previous=window.TrovaoAudio;
  const SOURCE="assets/sons/spin-original-256k-v7.mp3";
  // O ficheiro original contém silêncio no início.
  const AUDIBLE_START=.495, POOL_SIZE=3, SCATTER_COOLDOWN=1900;
  const pool=[];
  let enabled=true, roundRobin=0, lastScatter=-Infinity;
  let clickCtx=null, clickGain=null, clickBuffer=null;
  function buildClick(){
    if(clickCtx)return true;
    const Ctx=window.AudioContext||window.webkitAudioContext;
    if(!Ctx)return false;
    try{
      clickCtx=new Ctx({latencyHint:"interactive"});
      clickGain=clickCtx.createGain();
      clickGain.gain.value=.86;
      const limiter=clickCtx.createDynamicsCompressor();
      limiter.threshold.value=-11;
      limiter.knee.value=8;
      limiter.ratio.value=8;
      limiter.attack.value=.001;
      limiter.release.value=.08;
      clickGain.connect(limiter);
      limiter.connect(clickCtx.destination);
      const rate=clickCtx.sampleRate;
      const n=Math.round(rate*.100);
      clickBuffer=clickCtx.createBuffer(1,n,rate);
      const arr=clickBuffer.getChannelData(0);
      let filtered=0, previousNoise=0;
      for(let i=0;i<n;i++){
        const t=i/rate;
        const white=Math.random()*2-1;
        filtered+=.38*(white-filtered);
        const transient=(filtered-previousNoise);
        previousNoise=filtered;
        const snap=transient*Math.exp(-t*175)*1.20;
        const thunk=Math.sin(2*Math.PI*245*t)*Math.exp(-t*82)*.55;
        const body=Math.sin(2*Math.PI*110*t)*Math.exp(-t*57)*.22;
        arr[i]=Math.max(-.94,Math.min(.94,(snap+thunk+body)*1.12));
      }
      return true;
    }catch(e){console.warn("Clique Web Audio indisponível",e);return false;}
  }
  function unlock(){
    previous?.unlock?.();
    if(!enabled)return false;
    if(buildClick()&&clickCtx?.state!=="running")clickCtx.resume().catch(()=>{});
    return true;
  }
  function click(){
    unlock();
    if(!clickCtx||!clickBuffer){
      previous?.play?.("ui",{level:1.6});
      return;
    }
    try{
      const src=clickCtx.createBufferSource();
      src.buffer=clickBuffer;
      src.connect(clickGain);
      src.onended=()=>{try{src.disconnect()}catch(_){}};
      src.start();
    }catch(_){previous?.play?.("ui",{level:1.6});}
  }
  function makeChannel(){
    if(typeof Audio!=="function")return null;
    const player=new Audio(SOURCE+"#t=0.495");
    player.preload="auto";
    player.volume=.91;
    player.playsInline=true;
    player.setAttribute("playsinline","");
    player.addEventListener("loadedmetadata",()=>{
      try{if(player.paused&&player.currentTime<AUDIBLE_START-.02)player.currentTime=AUDIBLE_START}catch(_){}
    });
    player.addEventListener("error",()=>console.warn("SCATTER: áudio original não carregou; a usar som de reserva."),{once:true});
    return player;
  }
  for(let i=0;i<POOL_SIZE;i++){
    const channel=makeChannel();
    if(channel)pool.push(channel);
  }
  function scatter(options){
    const now=typeof performance!=="undefined"?performance.now():Date.now();
    if(now-lastScatter<SCATTER_COOLDOWN)return;
    lastScatter=now;
    if(!pool.length){previous?.play?.("scatter",options);return;}
    const player=pool.find(p=>p.paused||p.ended)||pool[(roundRobin++)%pool.length];
    try{
      if(!player.paused)player.pause();
      player.currentTime=AUDIBLE_START;
      player.volume=Math.min(1,.91*(options.level??1));
      const promise=player.play();
      if(promise?.catch)promise.catch(()=>previous?.play?.("scatter",options));
    }catch(_){previous?.play?.("scatter",options);}
  }
  function play(name,options={}){
    if(!enabled)return;
    if(name==="spin"){click();return;}
    if(name==="scatter"){scatter(options);return;}
    previous?.play?.(name,options);
  }
  function setEnabled(value){
    enabled=!!value;
    previous?.setEnabled?.(enabled);
    if(!enabled){
      for(const p of pool){p.pause();try{p.currentTime=AUDIBLE_START}catch(_){}}
      if(clickCtx?.state==="running")clickCtx.suspend().catch(()=>{});
    }else unlock();
    return enabled;
  }
  window.TrovaoAudio={
    play,unlock,setEnabled,isEnabled:()=>enabled,
    getInfo:()=>({
      spinSound:"Clique curto e forte (PCM float32)",
      scatterSound:"MP3 original enviado pelo utilizador — 256 kbps",
      scatterSource:SOURCE,
      scatterCooldownMs:SCATTER_COOLDOWN,
      remainingEffects:previous?.getInfo?.()??null,
      channels:POOL_SIZE
    })
  };
})();