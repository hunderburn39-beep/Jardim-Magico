/* Pantanal v2.25 — identidade sonora independente dos rolos e dos prémios.
 * Reutiliza os efeitos originais incorporados no index.html e acrescenta
 * sinais curtos com Web Audio. Não altera o motor de pagamento.
 */
(function(root,factory){
 'use strict';
 const audio=factory();
 if(typeof module==='object'&&module.exports)module.exports=audio;
 if(root)root.PantanalSoundFX=audio;
})(typeof globalThis!=='undefined'?globalThis:null,function(){
 'use strict';
 let context=null;
 function ctx(){
   if(typeof window==='undefined')return null;
   const Constructor=window.AudioContext||window.webkitAudioContext;
   if(!Constructor)return null;
   try{
     if(!context)context=new Constructor();
     if(context.state==='suspended')context.resume().catch(()=>{});
     return context;
   }catch(_){return null;}
 }
 function tone(frequency,delay=0,length=.12,volume=.11,wave='sine'){
   const a=ctx();if(!a||a.state==='closed')return;
   const t=a.currentTime+Math.max(0,delay);
   const oscill=a.createOscillator(),gain=a.createGain();
   oscill.type=wave;oscill.frequency.setValueAtTime(Math.max(40,frequency),t);
   gain.gain.setValueAtTime(.0001,t);
   gain.gain.exponentialRampToValueAtTime(Math.max(.0002,volume),t+.009);
   gain.gain.exponentialRampToValueAtTime(.0001,t+Math.max(.03,length));
   oscill.connect(gain);gain.connect(a.destination);
   oscill.start(t);oscill.stop(t+Math.max(.035,length)+.02);
 }
 function sample(nodes,key,scale=1){
   const base=nodes&&nodes[key];
   if(!base)return false;
   try{
     const a=base.cloneNode();
     a.volume=Math.min(1,Math.max(0,base.volume*scale));
     const promise=a.play();
     if(promise&&typeof promise.catch==='function')promise.catch(()=>{});
     return true;
   }catch(_){return false;}
 }
 function arpeggio(notes,spacing=.11,vol=.10,wave='sine'){
   notes.forEach((n,i)=>tone(n,i*spacing,.25,vol,wave));
 }
 function classifyWin(total,stake){
   const prize=Number(total),bet=Math.max(1,Number(stake)||1);
   if(!Number.isFinite(prize)||prize<=0)return null;
   return prize>=20*bet?'win-high':prize>=5*bet?'win-mid':'win-low';
 }
 function play(kind,nodes={}){
   if(typeof kind!=='string')return false;
   // Desbloquear AudioContext com a interação inicial de GIRAR.
   if(kind==='reel'){ctx();return false;}
   const match=/^stop:(\d)$/.exec(kind);
   if(match){
     const column=Math.min(4,Number(match[1]));
     tone(140-column*8,0,.09,.145,'triangle');
     tone(450-column*22,.012,.052,.045,'square');
     return true;
   }
   switch(kind){
     case 'win-low':
       sample(nodes,'win',.42);
       arpeggio([660,880],.09,.08,'sine');
       return true;
     case 'win-mid':
       sample(nodes,'win',.78);
       arpeggio([523.25,659.25,783.99,1046.5],.105,.105,'triangle');
       return true;
     case 'win-high':
       sample(nodes,'big',.98);
       arpeggio([392,523.25,659.25,783.99,1046.5,1318.5],.095,.12,'triangle');
       tone(196,0,.55,.075,'sine');
       return true;
     case 'jackpot':
       sample(nodes,'big',1.1);
       arpeggio([392,493.88,587.33,783.99,987.77,1174.66,1567.98],.14,.13,'triangle');
       tone(196,0,.9,.10,'sine');
       tone(392,.55,.6,.085,'sine');
       return true;
     case 'strawberry-pick':
       arpeggio([784,1174.66],.07,.065,'sine');
       return true;
     case 'bonus-strawberry':
       sample(nodes,'end',.65);
       arpeggio([659.25,783.99,1046.5],.10,.09,'sine');
       return true;
     case 'bonus-cauldron':
       sample(nodes,'bonus',.58);
       tone(196,0,.38,.13,'triangle');
       tone(294,.16,.28,.10,'sine');
       return true;
     case 'bonus-dice':
       sample(nodes,'big',.82);
       arpeggio([587.33,739.99,880,1174.66],.11,.10,'triangle');
       return true;
     case 'bonus-wheel':
       sample(nodes,'end',.78);
       arpeggio([523.25,659.25,783.99,1046.5],.14,.10,'sine');
       return true;
     default:return false;
   }
 }
 return Object.freeze({play,classifyWin});
});
