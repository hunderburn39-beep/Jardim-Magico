/* Portões do Trovão V3 — efeitos sintetizados Web Audio
 * Sem ficheiros externos: sons de caída, impacto, estilhaços, bónus e vitória.
 * O som é sempre opcional e só é ativado após uma interação do utilizador. */
(function(){
  "use strict";
  let context=null, master=null, noiseBuffer=null, enabled=true, lastDrop=0;
  const cap=(n,min,max)=>Math.min(max,Math.max(min,n));
  const now=()=>context?context.currentTime:0;
  function unlock(){
    if(!enabled)return false;
    try{
      const Ctx=window.AudioContext||window.webkitAudioContext;
      if(!Ctx)return false;
      if(!context){
        context=new Ctx();
        master=context.createGain();
        master.gain.value=.67;
        const limiter=context.createDynamicsCompressor();
        limiter.threshold.value=-13;limiter.knee.value=14;
        limiter.ratio.value=7;limiter.attack.value=.003;limiter.release.value=.16;
        master.connect(limiter);limiter.connect(context.destination);
        const len=Math.floor(context.sampleRate*.7);
        noiseBuffer=context.createBuffer(1,len,context.sampleRate);
        const d=noiseBuffer.getChannelData(0);
        for(let i=0;i<len;i++)d[i]=Math.random()*2-1;
      }
      if(context.state==="suspended")context.resume().catch(()=>{});
      return true;
    }catch(_){return false}
  }
  function pan(output,p){
    if(!context || !context.createStereoPanner)return output;
    const stereo=context.createStereoPanner();
    stereo.pan.setValueAtTime(cap(p,-.9,.9),now());
    stereo.connect(output);
    return stereo;
  }
  function tone(start,duration,from,to,type="sine",volume=.09,panValue=0){
    if(!context || !enabled)return;
    const osc=context.createOscillator(),g=context.createGain();
    const t=Math.max(now(),start),end=t+Math.max(.012,duration);
    osc.type=type;
    osc.frequency.setValueAtTime(Math.max(25,from),t);
    osc.frequency.exponentialRampToValueAtTime(Math.max(25,to),end);
    g.gain.setValueAtTime(.0001,t);
    g.gain.exponentialRampToValueAtTime(Math.max(.0003,volume),t+.008);
    g.gain.exponentialRampToValueAtTime(.0001,end);
    const bus=pan(master,panValue);
    osc.connect(g);g.connect(bus);
    osc.onended=()=>{try{osc.disconnect();g.disconnect();if(bus!==master)bus.disconnect()}catch(_){}};
    osc.start(t);osc.stop(end+.025);
  }
  function noise(start,duration,volume=.08,low=900,high=5600,panValue=0){
    if(!context||!noiseBuffer||!enabled)return;
    const t=Math.max(now(),start),end=t+Math.max(.018,duration);
    const source=context.createBufferSource(),filter=context.createBiquadFilter(),g=context.createGain();
    source.buffer=noiseBuffer;
    filter.type="bandpass";filter.Q.value=.7;
    filter.frequency.setValueAtTime(Math.max(80,high),t);
    filter.frequency.exponentialRampToValueAtTime(Math.max(80,low),end);
    g.gain.setValueAtTime(.0001,t);
    g.gain.exponentialRampToValueAtTime(volume,t+.008);
    g.gain.exponentialRampToValueAtTime(.0001,end);
    const bus=pan(master,panValue);
    source.connect(filter);filter.connect(g);g.connect(bus);
    source.onended=()=>{try{source.disconnect();filter.disconnect();g.disconnect();if(bus!==master)bus.disconnect()}catch(_){}};
    source.start(t);source.stop(Math.min(end+.02,t+.68));
  }
  function play(event,opts={}){
    if(!enabled||!unlock())return;
    const t=now()+.003;
    const level=cap(opts.level||1,.3,2),column=opts.column||0,at=opts.at||0;
    const p=cap((column-2.5)/4,-.75,.75);
    switch(event){
      case "spin":
        noise(t,.28,.105,240,5700,-.16);
        tone(t,.27,410,110,"sawtooth",.043);
        tone(t+.06,.19,650,185,"triangle",.038);
        break;
      case "drop":
        // Pequenos estalidos de peças a encaixar; alternam notas e posição estéreo.
        if(t-lastDrop<.024)return;
        lastDrop=t;
        tone(t,.092,280+Math.max(0,at)*19+column*18,100+column*9,"triangle",.04*level,p);
        tone(t+.011,.105,890+column*35,460+column*20,"sine",.025*level,p);
        noise(t,.058,.026*level,550,3200,p);
        break;
      case "land":
        // Impacto percetível de pedra sobre pedra, em estéreo por coluna.
        // Grave curto + estalo de superfície + pequena ressonância cristalina.
        tone(t,.18,175+column*12,56+column*5,"triangle",.135*level,p);
        tone(t+.008,.085,570+column*35,225+column*13,"sine",.059*level,p);
        noise(t,.12,.078*level,200,4600,p);
        tone(t+.06,.13,780+column*51,490+column*28,"sine",.026*level,p);
        break;
      case "tumble":
        noise(t,.22,.053,210,3600);
        tone(t,.15,490,180,"triangle",.052);
        for(let k=0;k<5;k++)tone(t+k*.045,.092,830-k*72,570-k*31,"sine",.022,((k-2)/3));
        break;
      case "burst":
        // Impacto grave + estilhaços de cristal em alturas diferentes.
        noise(t,.28,.10*level,400,6600);
        tone(t,.18,145,51,"triangle",.12*level);
        tone(t,.2,690,1350,"sine",.054*level,-.2);
        for(let k=0;k<5;k++)tone(t+.025*k,.19,800+170*k,1180+220*k,"sine",.022*level,(k-2)/3);
        break;
      case "reward":
        [523.25,659.25,783.99,1046.5].forEach((freq,k)=>{
          tone(t+.085*k,.38,freq,freq*.995,"sine",.067,k%2?.3:-.3);
          tone(t+.085*k,.18,freq*2,freq*1.93,"triangle",.014);
        });
        break;
      case "mult":
        tone(t,.25,220,96,"sawtooth",.058);
        tone(t+.05,.33,720,1200,"sine",.05);
        noise(t+.07,.18,.035,700,4100);
        break;
      case "scatter":
        noise(t,.42,.085,700,7900);
        [392,493.88,587.33,783.99,1174.66].forEach((freq,k)=>tone(t+k*.08,.46,freq,freq*1.012,"sine",.061));
        break;
      case "bigwin":
        noise(t,.48,.12,300,6500);
        tone(t,.50,110,42,"triangle",.16);
        [392,523.25,659.25,783.99,1046.5,1318.5].forEach((freq,k)=>tone(t+k*.12,.58,freq,freq*.997,"sine",.07));
        break;
      default:
        tone(t,.09,600,440,"sine",.03);
    }
  }
  function setEnabled(value){
    enabled=!!value;
    if(!enabled && context && context.state==="running")context.suspend().catch(()=>{});
    if(enabled)unlock();
    return enabled;
  }
  window.TrovaoAudio={play,setEnabled,unlock,isEnabled:()=>enabled};
})();