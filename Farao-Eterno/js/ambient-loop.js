/* Faraó Eterno — banda sonora normal e música exclusiva do bónus.
 * Áudio fornecido pelo utilizador; música normal 75%; transição suave entre faixas.
 * A faixa normal retoma do ponto onde ficou após as Free Spins.
 */
(()=>{
 'use strict';
 const game=window.__faraoGame, audio=game?.audio;
 if(!game||!audio)return;
 const originalStart=audio.startMusic.bind(audio);
 const originalStop=audio.stopMusic.bind(audio);
 const descriptors={
   normal:[
     {url:'assets/pharaohs-ritual.mp3?v=1436a1f8',name:"Pharaoh's Ritual",artist:'Áudio fornecido pelo utilizador'},
     {url:'assets/dark-pyramid-ambience.mp3?v=1.0',name:'Dark Pyramid Night Ambience Loop',artist:'szegvari'}
   ],
   bonus:[
     {url:'assets/bonus-ritual.mp3?v=1.0',name:'Faraó Eterno — Bonus Ritual',artist:'Áudio fornecido pelo utilizador'}
   ]
 };
 const states={
   normal:{raw:null,loading:null,decoding:null,buffer:null,offset:0,source:null,gain:null,startedAt:0,selected:descriptors.normal[0],failedAt:0},
   bonus:{raw:null,loading:null,decoding:null,buffer:null,offset:0,source:null,gain:null,startedAt:0,selected:descriptors.bonus[0],failedAt:0}
 };
 const volume=.75, normalBusGain=2.5, sampleGain=volume/normalBusGain;
 let activeTrack=null;
 const canPlay=()=>!!(audio.ctx&&game.soundMode==='all'&&game.ledger?.settings?.music&&(!game.paused||game._introCoverVisible)&&!game.superseded&&!document.hidden);
 const inBonus=()=>!!(!game._introCoverVisible&&(game.feature||game.pendingSummary||game._summaryAck));
 const desired=()=>inBonus()?'bonus':'normal';
 function fetchTrack(name){
   const s=states[name];
   if(s.raw)return Promise.resolve(s.raw);
   if(s.loading)return s.loading;
   if(s.failedAt&&Date.now()-s.failedAt<60000)return Promise.resolve(null);
   s.loading=(async()=>{
     for(const option of descriptors[name]){
       try{
         const res=await fetch(new URL(option.url,document.baseURI),{cache:'no-store'});
         if(!res.ok)continue;
         const raw=await res.arrayBuffer();
         if(!raw.byteLength)continue;
         s.selected=option;s.raw=raw;return raw;
       }catch(_){}
     }
     throw Error('Faixa '+name+' indisponível');
   })().catch(()=>{s.failedAt=Date.now();return null;}).finally(()=>{s.loading=null;});
   return s.loading;
 }
 function loadTrack(name){
   const s=states[name];
   if(s.buffer)return Promise.resolve(s.buffer);
   if(!audio.ctx)return Promise.resolve(null);
   if(s.decoding)return s.decoding;
   s.decoding=fetchTrack(name).then(raw=>raw?audio.ctx.decodeAudioData(raw.slice(0)):null)
     .then(buffer=>{if(buffer)s.buffer=buffer;return buffer;})
     .catch(()=>null).finally(()=>{s.decoding=null;});
   return s.decoding;
 }
 function fadeOut(name,seconds){
   const s=states[name],src=s.source;
   if(!src||!audio.ctx)return;
   const now=audio.ctx.currentTime;
   if(s.buffer?.duration){
     s.offset=(s.offset+Math.max(0,now-s.startedAt))%s.buffer.duration;
   }
   if(s.gain){
     const p=s.gain.gain;
     p.cancelScheduledValues(now);p.setValueAtTime(Math.max(.0001,p.value),now);
     p.linearRampToValueAtTime(.0001,now+seconds);
   }
   try{src.stop(now+seconds+.03);}catch(_){}
   s.source=null;s.gain=null;
 }
 function fadeIn(name,seconds){
   const s=states[name],ctx=audio.ctx;
   if(!s.buffer||!ctx||s.source)return;
   const src=ctx.createBufferSource(),gn=ctx.createGain();
   src.buffer=s.buffer;src.loop=true;
   const now=ctx.currentTime,offset=s.offset%s.buffer.duration;
   gn.gain.setValueAtTime(.0001,now);
   gn.gain.linearRampToValueAtTime(sampleGain,now+seconds);
   src.connect(gn);gn.connect(audio.musicBus||audio.out||ctx.destination);
   s.source=src;s.gain=gn;s.startedAt=now;
   src.onended=()=>{if(s.source===src){s.source=null;s.gain=null;}try{src.disconnect();gn.disconnect();}catch(_){}};
   src.start(now,offset);
 }
 function silence(){
   fadeOut('normal',.10);fadeOut('bonus',.10);
   activeTrack=null;originalStop();
 }
 function sync(){
   if(!canPlay())return;
   const target=desired(),s=states[target];
   if(!s.buffer){
     // Se a gravação do bónus não existir no servidor, manter a música normal.
     loadTrack(target).then(()=>{if(canPlay()&&states[target].buffer)sync();});
     if(target==='bonus'&&!states.bonus.buffer&&states.normal.buffer){
       if(!states.normal.source)fadeIn('normal',.35);
       activeTrack='normal';originalStop();
     }else if(!states.normal.buffer&&!states.bonus.buffer){
       originalStart();
     }
     return;
   }
   if(s.source&&activeTrack===target)return;
   originalStop();
   const fade=target==='bonus'?.80:1.05;
   fadeIn(target,fade);
   fadeOut(target==='normal'?'bonus':'normal',fade);
   activeTrack=target;
 }
 // Pré-carrega as duas gravações sem desbloquear automaticamente o áudio do Safari.
 fetchTrack('normal');
 fetchTrack('bonus');
 audio.startMusic=function(){
   if(!canPlay())return;
   sync();
 };
 audio.stopMusic=function(){silence();};
 const previousInit=audio.init.bind(audio);
 audio.init=function(...args){
   const response=previousInit(...args);
   if(audio.ctx){
     loadTrack('normal').then(()=>{if(canPlay())sync();});
     loadTrack('bonus').then(()=>{if(canPlay())sync();});
   }
   return response;
 };
 // A sessão e a transição de Free Spins são geridas pelo motor; sem alterar pagamentos.
 const watcher=setInterval(()=>{
   if(!audio.ctx)return;
   if(!canPlay()){if(states.normal.source||states.bonus.source)silence();return;}
   if(activeTrack!==desired()||!states[desired()].source)sync();
 },400);
 document.addEventListener('visibilitychange',()=>{
   if(document.hidden)audio.stopMusic();
   else if(audio.ctx&&canPlay())audio.startMusic();
 });
 game.ambientLoop={
   version:'1.5-bonus-switch',
   get source(){return states[activeTrack||'normal'].selected.name;},
   get normalReady(){return !!states.normal.buffer;},
   get bonusReady(){return !!states.bonus.buffer;},
   get activeTrack(){return activeTrack;},
   get ready(){return !!states.normal.buffer;},
   crossfade:true,
   stopWatcher(){clearInterval(watcher);}
 };
})();
