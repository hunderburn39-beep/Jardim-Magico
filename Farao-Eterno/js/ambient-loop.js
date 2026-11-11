/* Faraó Eterno — música do utilizador (Pharaoh's Ritual) com reserva CC0.
 * Substitui apenas a música ambiente, preservando o gong do Wild e os tambores.
 * Crossfade entre repetições, compatível com Safari/iPhone.
 */
(()=>{
 'use strict';
 const game=window.__faraoGame, audio=game?.audio;
 if(!game||!audio)return;
 const originalStart=audio.startMusic.bind(audio),originalStop=audio.stopMusic.bind(audio);
 const assets=[
   {url:'assets/pharaohs-ritual.mp3?v=1436a1f8',name:"Pharaoh's Ritual",artist:'Áudio fornecido pelo utilizador',license:'Não especificada'},
   {url:'assets/dark-pyramid-ambience.mp3?v=1.0',name:'Dark Pyramid Night Ambience Loop',artist:'szegvari',license:'CC0'}
 ];
 let selected=assets[0];
 let rawPromise=null,buffer=null,decodePromise=null,playing=false,timer=null,nextStart=0;
 let sources=new Set();
 const allowed=()=>audio.ctx&&game.soundMode==='all'&&game.ledger?.settings?.music&&!game.paused&&!game.superseded&&!document.hidden;
 const fetchAudio=()=>{
   if(!rawPromise){
     rawPromise=(async()=>{
       for(const candidate of assets){
         try{
           const response=await fetch(new URL(candidate.url,document.baseURI),{cache:'no-store'});
           if(!response.ok)continue;
           const bytes=await response.arrayBuffer();
           if(!bytes.byteLength)continue;
           selected=candidate;
           return bytes;
         }catch(_){/* Segue para a faixa de reserva sem bloquear o jogo. */}
       }
       throw Error('Nenhuma faixa ambiente disponível');
     })().catch(e=>{rawPromise=null;throw e;});
   }
   return rawPromise;
 };
 const decode=()=>{
   if(buffer)return Promise.resolve(buffer);
   if(!audio.ctx)return Promise.resolve(null);
   if(!decodePromise){
     decodePromise=fetchAudio()
       .then(bytes=>audio.ctx.decodeAudioData(bytes.slice(0)))
       .then(decoded=>{buffer=decoded;return decoded;})
       .catch(()=>{decodePromise=null;return null;});
   }
   return decodePromise;
 };
 // Pré-carregamento da rede sem abrir o contexto áudio do telemóvel.
 fetchAudio().catch(()=>{});
 function stopOriginal(){originalStop();}
 function stopLoop(){
   playing=false;
   if(timer){clearInterval(timer);timer=null;}
   const t=audio.ctx?.currentTime??0;
   for(const item of sources){
     try{item.gain.gain.cancelScheduledValues(t);item.gain.gain.setTargetAtTime(.0001,t,.035);item.source.stop(t+.15);}catch(_){}
   }
   sources.clear();
 }
 function schedule(){
   if(!playing||!allowed()||!buffer){stopLoop();return;}
   const ctx=audio.ctx,D=buffer.duration,blend=Math.min(1.05,D*.12);
   if(D<=2){stopLoop();return;}
   while(nextStart<ctx.currentTime+2.5){
     const t=Math.max(nextStart,ctx.currentTime+.025);
     const source=ctx.createBufferSource(),gain=ctx.createGain();
     source.buffer=buffer;source.loop=false;
     gain.gain.setValueAtTime(.0001,t);
     gain.gain.linearRampToValueAtTime(.105,t+blend);
     gain.gain.setValueAtTime(.105,t+D-blend);
     gain.gain.linearRampToValueAtTime(.0001,t+D);
     source.connect(gain);gain.connect(audio.musicBus||audio.out||ctx.destination);
     const item={source,gain};sources.add(item);
     source.onended=()=>{
       sources.delete(item);
       try{source.disconnect();gain.disconnect();}catch(_){}
     };
     source.start(t);source.stop(t+D+.025);
     nextStart=t+D-blend;
   }
 }
 function startLoop(){
   if(playing||!allowed()||!buffer)return;
   stopOriginal(); // não deixar a antiga música de sintetizador por baixo
   playing=true;
   nextStart=audio.ctx.currentTime+.045;
   schedule();
   timer=setInterval(schedule,650);
 }
 audio.startMusic=function(){
   if(!allowed())return;
   if(buffer){startLoop();return;}
   originalStart(); // fallback enquanto carrega ou se o ficheiro não estiver disponível
   decode().then(decoded=>{
     if(decoded&&allowed())startLoop();
   });
 };
 audio.stopMusic=function(){
   stopLoop();
   stopOriginal();
 };
 const prevInit=audio.init.bind(audio);
 audio.init=function(...args){
   const result=prevInit(...args);
   if(audio.ctx)decode().then(decoded=>{if(decoded&&allowed())startLoop();});
   return result;
 };
 document.addEventListener('visibilitychange',()=>{
   if(document.hidden)audio.stopMusic();
   else if(audio.ctx&&allowed())audio.startMusic();
 });
 game.ambientLoop={version:'1.3',get source(){return selected.name;},get artist(){return selected.artist;},get license(){return selected.license;},crossfade:true,get ready(){return !!buffer;}};
})();
