/* PORTÕES DO TROVÃO V8 — aterragem individual de cada peça.
 * Som de couro/almofada: ataque macio, ressonância curta e abafada.
 * O timbre é sintetizado localmente em PCM float32 à taxa de amostragem
 * nativa (normalmente 48 kHz). Sem rede, sem estalos metálicos.
 */
(()=>{
  "use strict";
  const variants=7, maxVoices=48;
  let ctx=null,bus=null,comp=null,buffers=[],enabled=true;
  const voices=new Set();
  const clamp=(v,a,b)=>Math.max(a,Math.min(b,v));
  function createBuffer(seed){
    const rate=ctx.sampleRate,duration=.19;
    const length=Math.floor(rate*duration);
    const buffer=ctx.createBuffer(1,length,rate);
    const samples=buffer.getChannelData(0);
    let random=(seed+1)*19937>>>0,lp=0,lo=0,lo2=0;
    const r=()=>{random=(1664525*random+1013904223)>>>0;return random/4294967296*2-1};
    const base=72+seed*8,softness=130+seed*27;
    for(let i=0;i<length;i++){
      const t=i/rate;
      const envelope=(1-Math.exp(-t*160))*Math.exp(-t*(27+seed*.8));
      // Ruído filtrado: contacto da pedra com couro, não metal.
      const white=r();
      lp+=clamp(softness/rate,.001,.12)*(white-lp);
      lo+=.085*(lp-lo);lo2+=.16*(lo-lo2);
      const thump=Math.sin(2*Math.PI*(base*t+38*t*Math.exp(-32*t)))*Math.exp(-t*32);
      const skin=lo2*.83+lp*.21;
      const creak=Math.sin(2*Math.PI*(215+seed*13)*t)*Math.exp(-t*57)*.08;
      samples[i]=clamp((thump*.67+skin*.72+creak)*envelope*1.24,-.95,.95);
    }
    return buffer;
  }
  function init(){
    if(!enabled)return false;
    try{
      if(!ctx){
        const Ctx=window.AudioContext||window.webkitAudioContext;
        if(!Ctx)return false;
        ctx=new Ctx({latencyHint:"interactive"});
        bus=ctx.createGain();bus.gain.value=.56;
        comp=ctx.createDynamicsCompressor();
        comp.threshold.value=-22;comp.knee.value=16;comp.ratio.value=7;
        comp.attack.value=.001;comp.release.value=.10;
        bus.connect(comp);comp.connect(ctx.destination);
        buffers=Array.from({length:variants},(_,i)=>createBuffer(i));
      }
      if(ctx.state!=="running")ctx.resume().catch(()=>{});
      return true;
    }catch(e){return false}
  }
  function stop(voice){
    try{voice.source.disconnect();voice.gain.disconnect();voice.pan?.disconnect()}catch(_){}
    voices.delete(voice);
  }
  function play({column=2.5,row=0,kind="amber",strength=.75}={}){
    if(!enabled||!init()||ctx.state!=="running"||!buffers.length)return;
    if(voices.size>=maxVoices)return;
    const index=(Math.abs(Math.floor(column)*3+Math.floor(row)+String(kind).length))%variants;
    const source=ctx.createBufferSource(),gain=ctx.createGain();
    const pan=typeof ctx.createStereoPanner==="function"?ctx.createStereoPanner():null;
    const volume=clamp(strength,.22,1)*(.52+Math.random()*.14);
    source.buffer=buffers[index];
    source.playbackRate.value=.94+Math.random()*.105;
    gain.gain.setValueAtTime(.0001,ctx.currentTime);
    gain.gain.exponentialRampToValueAtTime(Math.max(.0005,volume),ctx.currentTime+.009);
    gain.gain.exponentialRampToValueAtTime(.0001,ctx.currentTime+.145);
    source.connect(gain);
    if(pan){
      pan.pan.value=clamp((column-2.5)/4,-.68,.68);
      gain.connect(pan);pan.connect(bus);
    }else gain.connect(bus);
    const voice={source,gain,pan};
    voices.add(voice);
    source.onended=()=>stop(voice);
    source.start();
    source.stop(ctx.currentTime+.17);
  }
  function setEnabled(value){
    enabled=Boolean(value);
    if(!enabled&&ctx?.state==="running")ctx.suspend().catch(()=>{});
    if(enabled)init();
  }
  window.TrovaoLeather={play,unlock:init,setEnabled,getInfo:()=>({
    material:"Couro acolchoado",variants,voices:voices.size,
    sampleRate:ctx?.sampleRate??null,format:"WebAudio PCM float32"
  })};
})();