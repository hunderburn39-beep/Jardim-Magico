/* Jardim Magico - original audio signatures, Web Audio (2026).
 * Sound is only unlocked by the player's gesture. No autoplay, no payout RNG.
 * Dedicated Scatter arrival/tease/trigger and layered prize/coin motifs.
 */
(function(root){
"use strict";
let ctx=null,bus=null;
function unlock(){
  try{
    const Constructor=root.AudioContext||root.webkitAudioContext;
    if(typeof Constructor!=="function")return false;
    if(!ctx){
      ctx=new Constructor();
      const limiter=ctx.createDynamicsCompressor(),master=ctx.createGain();
      limiter.threshold.value=-20;limiter.knee.value=12;limiter.ratio.value=5;
      limiter.attack.value=.005;limiter.release.value=.16;
      master.gain.value=.27;limiter.connect(master);master.connect(ctx.destination);bus=limiter;
    }
    if(ctx.state==="suspended"){
      const op=ctx.resume();if(op&&typeof op.catch==="function")op.catch(()=>{});
    }
    return true;
  }catch{return false}
}
function tone(freq,time,duration,volume,type="sine",slide=0){
  const osc=ctx.createOscillator(),gain=ctx.createGain();
  osc.type=type;
  osc.frequency.setValueAtTime(freq,time);
  if(slide>0)osc.frequency.exponentialRampToValueAtTime(slide,time+duration*.78);
  gain.gain.setValueAtTime(.0001,time);
  gain.gain.exponentialRampToValueAtTime(Math.max(.001,volume),time+.014);
  gain.gain.exponentialRampToValueAtTime(.0001,time+duration);
  osc.connect(gain);gain.connect(bus);
  osc.onended=()=>{try{osc.disconnect();gain.disconnect()}catch{}};
  osc.start(time);osc.stop(time+duration+.035);
}
function shimmer(time,duration=.22,level=.05){
  if(!ctx.createBuffer||!ctx.createBufferSource||!ctx.createBiquadFilter)return;
  const rate=ctx.sampleRate||44100,len=Math.round(rate*duration);
  const buffer=ctx.createBuffer(1,len,rate),samples=buffer.getChannelData(0);
  for(let i=0;i<len;i++)samples[i]=(Math.random()*2-1)*Math.pow(1-i/len,.55);
  const source=ctx.createBufferSource(),filter=ctx.createBiquadFilter(),gain=ctx.createGain();
  source.buffer=buffer;filter.type="highpass";filter.frequency.value=1250;
  gain.gain.setValueAtTime(Math.max(.001,level),time);
  gain.gain.exponentialRampToValueAtTime(.0001,time+duration);
  source.connect(filter);filter.connect(gain);gain.connect(bus);
  source.onended=()=>{try{source.disconnect();filter.disconnect();gain.disconnect()}catch{}};
  source.start(time);source.stop(time+duration);
}
function play(kind){
  if(!ctx||!bus||ctx.state==="closed"||ctx.state==="suspended")return false;
  try{
    const t=ctx.currentTime+.008;
    const n=(f,delay,duration,vol=.1,type="sine",slide=0)=>tone(f,t+delay,duration,vol,type,slide);
    switch(kind){
      case "scatterLand":
        n(245,0,.32,.17,"triangle",195);n(784,.07,.42,.10,"sine",1046);
        n(1568,.17,.32,.048);shimmer(t+.08,.20,.04);break;
      case "scatterTease":
        n(138,0,.75,.22,"sawtooth",175);n(294,.08,.66,.11,"triangle",440);
        n(587,.27,.5,.11,"sine",880);n(1174,.48,.31,.048);shimmer(t+.33,.25,.055);break;
      case "scatterTrigger":
        n(122,0,.85,.24,"triangle",88);
        [392,493.88,587.33,783.99,1046.5].forEach((f,i)=>n(f,i*.092,.76,.105+i*.007,i%2?"triangle":"sine"));
        n(1568,.44,.75,.072);shimmer(t+.025,.26,.085);shimmer(t+.44,.30,.075);break;
      case "prizeCoin":
        n(740,0,.20,.11,"triangle",1090);n(1480,.045,.26,.055);shimmer(t,.10,.024);break;
      case "prizeSmall":
        n(196,0,.28,.13,"sine",155);
        [659.25,830.61,987.77].forEach((f,i)=>n(f,.06+i*.09,.44,.12-i*.012,i===1?"triangle":"sine"));
        shimmer(t+.11,.18,.04);break;
      case "prizeLarge":
      case "prizeFinal":
        n(130.81,0,.65,.21,"triangle",98);
        [392,523.25,659.25,783.99,1046.5].forEach((f,i)=>n(f,.035+i*.095,kind==="prizeFinal"?.9:.65,.105+i*.006,i%2?"triangle":"sine"));
        n(1568,.34,.6,.062);shimmer(t+.06,.24,.065);
        if(kind==="prizeFinal")shimmer(t+.48,.32,.07);
        break;
      default:return false;
    }
    return true;
  }catch{return false}
}
function suspend(){
  if(ctx&&ctx.state==="running"){
    try{const op=ctx.suspend();if(op&&typeof op.catch==="function")op.catch(()=>{})}catch{}
  }
}
root.GardenModernAudio=Object.freeze({unlock,play,suspend});
})(globalThis);
