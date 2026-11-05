/* PORTÕES DO TROVÃO — Áudio personalizado V8.
 * A gravação do utilizador é reproduzida SEM recompressão a partir do MP3 original.
 * Os restantes eventos (quedas, impactos, bónus, etc.) continuam no motor Web Audio V5.
 * Audio HTML nativo foi escolhido para iniciar de imediato ao toque também no Safari iOS.
 */
(()=>{
  "use strict";
  const previous=window.TrovaoAudio;
  const SOURCE="assets/sons/spin-original-256k-v7.mp3";
  // O original tem 0,51 s de silêncio antes do clique audível.
  // Ignorar essa parte evita que as peças pareçam cair antes do botão.
  const AUDIBLE_START=.495;
  const pool=[];
  const POOL_SIZE=4;
  let enabled=true,roundRobin=0;
  function makeChannel(){
    if(typeof Audio!=="function")return null;
    const player=new Audio(SOURCE+"#t=0.495");
    player.preload="auto";
    player.volume=.83;
    player.playsInline=true;
    player.setAttribute("playsinline","");
    player.addEventListener("loadedmetadata",()=>{
      try{if(player.paused&&player.currentTime<AUDIBLE_START-.02)player.currentTime=AUDIBLE_START}catch(_){}
    });
    player.addEventListener("error",()=>console.warn("Falha no carregamento do áudio de GIRAR; a usar o som de reserva."),{once:true});
    return player;
  }
  for(let i=0;i<POOL_SIZE;i++){
    const p=makeChannel();
    if(p)pool.push(p);
  }
  function unlock(){
    // Desbloqueia os efeitos e impactos no mesmo gesto que inicia o spin.
    previous?.unlock?.();
    return true;
  }
  function play(name,options={}){
    if(!enabled)return;
    if(name!=="spin"){
      previous?.play?.(name,options);
      return;
    }
    if(!pool.length){
      previous?.play?.("spin",options);
      return;
    }
    // Faz-se sempre o som que o utilizador forneceu em cada spin.
    // Evita cortar uma gravação anterior durante auto-spins mais rápidos.
    const player=pool.find(p=>p.paused||p.ended)||pool[(roundRobin++)%pool.length];
    try{
      if(!player.paused)player.pause();
      player.currentTime=AUDIBLE_START;
      player.volume=Math.min(1,.83*(options.level??1));
      const promise=player.play();
      if(promise?.catch)promise.catch(()=>previous?.play?.("spin",options));
    }catch(_){previous?.play?.("spin",options)}
  }
  function setEnabled(value){
    enabled=Boolean(value);
    previous?.setEnabled?.(enabled);
    if(!enabled)for(const p of pool){p.pause();try{p.currentTime=AUDIBLE_START}catch(_){}}
    return enabled;
  }
  window.TrovaoAudio={
    play,setEnabled,unlock,
    isEnabled:()=>enabled,
    getInfo:()=>({
      spinSound:"Ficheiro original do utilizador — MP3 estéreo 256 kbps",
      spinSource:SOURCE,
      remainingEffects:previous?.getInfo?.()??null,
      channels:POOL_SIZE
    })
  };
})();
