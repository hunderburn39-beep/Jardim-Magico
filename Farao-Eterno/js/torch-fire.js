/* Faraó Eterno — chama das tochas: amostra enviada pelo utilizador.
 * Loop leve em duas posições, independente da música e dos efeitos dos tambores.
 * Não altera a matemática nem os resultados das jogadas.
 */
(()=>{
 'use strict';
 const game=window.__faraoGame,fx=game?.audio;
 if(!game||!fx)return;
 const url='assets/torches-fire.mp3?v=2';
 let bytesPromise=null,bufferPromise=null,buffer=null,active=false;
 let next=[0,0],timer=null,voices=new Set();
 // A gravação tem RMS ~-40 dBFS; compensar no WebAudio com limitação no compressor existente.
 // 20% do nível compensado original das tochas (mantém-se audível em telemóveis).
 const torchVolume=0.20;
 const paths=[-.62,.62],volume=4.2*torchVolume;
 const allowed=()=>!!(fx.ctx&&game.soundMode==='all'&&game.ledger?.settings?.music&&!game.paused&&!game.superseded&&!document.hidden&&fx.ctx.state==='running');
 function getBytes(){
  if(!bytesPromise)bytesPromise=fetch(new URL(url,document.baseURI),{cache:'force-cache'})
   .then(r=>{if(!r.ok)throw Error('Ficheiro das tochas em falta: '+r.status);return r.arrayBuffer();})
   .catch(e=>{bytesPromise=null;throw e;});
  return bytesPromise;
 }
 getBytes().catch(()=>{});
 function prepare(){
  if(buffer)return Promise.resolve(buffer);
  if(!fx.ctx)return Promise.resolve(null);
  if(!bufferPromise){
   bufferPromise=getBytes().then(b=>fx.ctx.decodeAudioData(b.slice(0)))
    .then(b=>{buffer=b;return b;})
    .catch(()=>{bufferPromise=null;return null;});
  }
  return bufferPromise;
 }
 function stop(){
  if(!active)return;
  active=false;
  if(timer){clearInterval(timer);timer=null;}
  const t=fx.ctx?.currentTime||0;
  for(const v of voices){
   try{v.gain.gain.cancelScheduledValues(t);v.gain.gain.setTargetAtTime(.0001,t,.07);v.source.stop(t+.28);}catch(_){}
  }
  voices.clear();
 }
 function schedule(){
  if(!active||!allowed()||!buffer){stop();return;}
  const ctx=fx.ctx,D=buffer.duration,fade=Math.min(1.15,D*.13);
  if(D<3){stop();return;}
  for(let side=0;side<2;side++){
   while(next[side]<ctx.currentTime+2.2){
    const t=Math.max(next[side],ctx.currentTime+.035);
    const src=ctx.createBufferSource(),gain=ctx.createGain();
    src.buffer=buffer;
    const pan=ctx.createStereoPanner?.();
    gain.gain.setValueAtTime(.0001,t);
    gain.gain.linearRampToValueAtTime(volume*.5,t+fade);
    gain.gain.setValueAtTime(volume*.5,t+D-fade);
    gain.gain.linearRampToValueAtTime(.0001,t+D);
    src.connect(gain);
    if(pan){pan.pan.value=paths[side];gain.connect(pan);pan.connect(fx.out||ctx.destination);}
    else gain.connect(fx.out||ctx.destination);
    const v={source:src,gain,pan};voices.add(v);
    src.onended=()=>{voices.delete(v);try{src.disconnect();gain.disconnect();pan?.disconnect();}catch(_){}};
    src.start(t);src.stop(t+D+.025);
    next[side]=t+D-fade;
   }
  }
 }
 function start(){
  if(active||!allowed()||!buffer)return;
  active=true;
  const t=fx.ctx.currentTime+.04;
  next=[t,t+Math.max(.1,buffer.duration*.37)];
  schedule();
  timer=setInterval(schedule,700);
 }
 function sync(){
  if(!allowed()){stop();return;}
  if(buffer){start();return;}
  prepare().then(b=>{if(b&&allowed())start();});
 }
 const priorInit=fx.init.bind(fx);
 fx.init=function(...args){const result=priorInit(...args);sync();return result;};
 const priorStopMusic=fx.stopMusic.bind(fx);
 fx.stopMusic=function(...args){if(game.paused||game.superseded||document.hidden)stop();return priorStopMusic(...args);};
 document.addEventListener('visibilitychange',sync);
 window.addEventListener('pagehide',stop);
 const watcher=setInterval(sync,1100);
 game.torchFire={version:'1.3-torch20',sample:'Warm Camp Fire - (High Quality)',artist:'Prem_Adhikary',source:'Pixabay 176816',get ready(){return !!buffer;},stop};
})();
