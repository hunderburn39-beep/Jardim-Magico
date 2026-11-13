/* Faraó Eterno — Super Scatter: Faraó original à frente dos outros símbolos,
 * pulsação 3D e "tchammm" metálico ao assentar em cada tambor.
 * Apenas apresentação: sem alterações de RNG, pagamentos, cofre ou Free Spins.
 */
(()=>{
'use strict';
const game=window.__faraoGame,canvas=document.getElementById('canvas');
if(!game||!game.audio||!canvas||!game.reelBoard)return;
const ctx=canvas.getContext('2d');
if(!ctx)return;
const audio=game.audio,REDUCED=!!window.matchMedia?.('(prefers-reduced-motion: reduce)').matches;
const TAU=Math.PI*2;
const clamp=(n,lo,hi)=>Math.max(lo,Math.min(hi,n));
const stats={landings:0,bonusReveals:0,overlays:0};
const reducedSize=1.12;
function highlight(x,y,w,h,t,strong,img){
 const wave=REDUCED?.35:(.5+.5*Math.sin(t*.0068+x*.015));
 const pulse=reducedSize*(1+(strong?.25:.18)*wave);
 const center=x+w/2,cy=y+h*.46;
 const iw=Math.min(w*.92,h*.74*img.naturalWidth/img.naturalHeight)*pulse;
 const ih=iw*img.naturalHeight/img.naturalWidth;
 const rad=Math.max(iw,ih)*.80;
 ctx.save();
 // Halo independente da casa, mas preso à moldura exterior dos 5 tambores.
 ctx.globalCompositeOperation='screen';
 const aura=ctx.createRadialGradient(center,cy,3,center,cy,rad);
 aura.addColorStop(0,'rgba(255,252,216,'+(.32+.19*wave)+')');
 aura.addColorStop(.43,'rgba(245,189,58,'+(.31+.16*wave)+')');
 aura.addColorStop(.77,'rgba(54,209,225,'+(.10+.08*wave)+')');
 aura.addColorStop(1,'rgba(255,200,75,0)');
 ctx.fillStyle=aura;
 ctx.beginPath();ctx.arc(center,cy,rad,0,TAU);ctx.fill();
 ctx.restore();
 // A mesma fotografia WebP que surge no ecrã do bónus. Sem redesenhar rostos.
 ctx.save();
 ctx.shadowColor='#ffdd70';
 ctx.shadowBlur=REDUCED?8:16+23*wave;
 ctx.drawImage(img,center-iw/2,cy-ih/2,iw,ih);
 ctx.restore();
 if(!REDUCED){
  const starX=center+Math.cos(t*.0037+x)*iw*.32;
  const starY=cy-ih*.33;
  const starSize=3.4+2.6*wave;
  ctx.save();
  ctx.globalAlpha=.35+.65*wave;
  ctx.globalCompositeOperation='screen';
  ctx.shadowColor='#fff9c8';ctx.shadowBlur=14;
  ctx.strokeStyle='#fff8d0';ctx.lineWidth=1.7;
  ctx.beginPath();ctx.moveTo(starX-starSize,starY);ctx.lineTo(starX+starSize,starY);
  ctx.moveTo(starX,starY-starSize*1.55);ctx.lineTo(starX,starY+starSize*1.55);ctx.stroke();
  ctx.restore();
 }
 // Pequena placa dourada no rodapé, sempre legível mesmo com o rosto pulsante.
 const bx=x+w*.075,by=y+h-30,bw=w*.85,bh=26;
 ctx.save();
 const plate=ctx.createLinearGradient(bx,by,bx,by+bh);
 plate.addColorStop(0,'#99703d');plate.addColorStop(.24,'#13263b');plate.addColorStop(1,'#07182b');
 ctx.fillStyle=plate;ctx.strokeStyle='#f8d77c';ctx.lineWidth=1.8;
 ctx.fillRect(bx,by,bw,bh);ctx.strokeRect(bx,by,bw,bh);
 ctx.textAlign='center';ctx.textBaseline='middle';
 ctx.shadowColor='#f6d78e';ctx.shadowBlur=REDUCED?0:5+6*wave;
 ctx.fillStyle='#fff1bf';
 ctx.font='900 11px Georgia,serif';ctx.fillText('SUPER',center,by+8);
 ctx.font='900 9px Georgia,serif';ctx.fillText('SCATTER',center,by+19);
 ctx.restore();
}
const baseBoard=game.reelBoard.bind(game);
game.reelBoard=function(t){
 baseBoard(t);
 if(!this.ready||this.superseded)return;
 const img=this.images?.SUPER;
 if(!img?.complete||!img.naturalWidth||!img.naturalHeight)return;
 const R=this.visualV11?.layout||{left:35,top:284,cw:95,gh:129,step:101};
 const spin=this.spinObject;
 const boosted=!!(spin?.result?.superTrigger||this.lastResult?.superTrigger);
 ctx.save();ctx.beginPath();
 ctx.rect(R.left-3,R.top-7,4*R.step+R.cw+6,R.gh*3+14);ctx.clip();
 for(let reel=0;reel<5;reel++){
  if(this.spinning&&spin&&!spin.stopped[reel])continue;
  const symbols=spin&&spin.stopped[reel]?spin.grid?.[reel]:this.grid?.[reel];
  if(!Array.isArray(symbols))continue;
  for(let row=0;row<3;row++){
   if(symbols[row]!=='SUPER')continue;
   const landed=this.landings?.[reel]||0,age=t-landed;
   const bounce=landed&&age>=0&&age<280?-5*Math.pow(1-age/280,2)*Math.cos(age*.038):0;
   highlight(R.left+reel*R.step,R.top+row*R.gh+bounce,R.cw,R.gh,t,boosted,img);
   stats.overlays++;
  }
 }
 ctx.restore();
};
function readySound(){
 return !!(audio.on&&audio.ctx&&!game.paused&&!game.superseded&&!document.hidden);
}
// "TCHAMMM": ataque de metal + impacto grave + harmónicos ressonantes.
// O bus da música não é tocado, sem reduzir o Pharaoh's Ritual.
audio.superScatterHit=function({bonus=false,delay=0}={}){
 if(!readySound())return false;
 try{
  const ac=audio.ctx,when=ac.currentTime+delay,bus=audio.out||ac.destination;
  const bell=(type,from,to,volume,attack,length)=>{
   const oscillator=ac.createOscillator(),env=ac.createGain();
   oscillator.type=type;
   oscillator.frequency.setValueAtTime(from,when);
   oscillator.frequency.exponentialRampToValueAtTime(to,when+length*.80);
   env.gain.setValueAtTime(.0001,when);
   env.gain.exponentialRampToValueAtTime(volume*(bonus?1.20:1),when+attack);
   env.gain.exponentialRampToValueAtTime(.0001,when+length);
   oscillator.connect(env);env.connect(bus);
   oscillator.start(when);oscillator.stop(when+length+.03);
   oscillator.onended=()=>{try{oscillator.disconnect();env.disconnect()}catch(_){}};
  };
  bell('triangle',430,272,.082,.014,bonus?1.40:1.08);
  bell('sine',820,585,.047,.008,bonus?1.18:.87);
  bell('triangle',1170,805,.022,.006,bonus?1.00:.67);
  bell('sine',152,71,.088,.012,.46);
  // Uma fricção curta faz o "tch" inicial, com a cauda "ammm" dos sinos.
  if(typeof ac.createBuffer==='function'&&typeof ac.createBiquadFilter==='function'){
   const len=Math.max(4,Math.floor(ac.sampleRate*.095));
   const buffer=ac.createBuffer(1,len,ac.sampleRate),data=buffer.getChannelData(0);
   for(let i=0;i<len;i++)data[i]=(Math.random()*2-1)*Math.pow(1-i/len,1.7);
   const noise=ac.createBufferSource(),hp=ac.createBiquadFilter(),ng=ac.createGain();
   noise.buffer=buffer;hp.type='highpass';hp.frequency.value=1450;
   ng.gain.setValueAtTime(.0001,when);
   ng.gain.exponentialRampToValueAtTime(bonus?.045:.036,when+.004);
   ng.gain.exponentialRampToValueAtTime(.0001,when+.092);
   noise.connect(hp);hp.connect(ng);ng.connect(bus);
   noise.start(when);noise.stop(when+.10);
   noise.onended=()=>{try{noise.disconnect();hp.disconnect();ng.disconnect()}catch(_){}};
  }
  return true;
 }catch(_){return false;}
};
const baseTick=audio.tick.bind(audio);
audio.tick=function(reel){
 const answer=baseTick(reel);
 const spin=game.spinObject;
 if(!spin?.stopped?.[reel]||!spin.grid?.[reel]?.includes('SUPER'))return answer;
 if(!spin._superFxLanded)spin._superFxLanded=new Set();
 if(spin._superFxLanded.has(reel))return answer;
 spin._superFxLanded.add(reel);
 if(this.superScatterHit?.())stats.landings++;
 return answer;
};
const baseFinish=game.finishSpin.bind(game);
game.finishSpin=function(...args){
 const spin=this.spinObject;
 const result=baseFinish(...args);
 if(spin?.result?.superTrigger&&!spin._superFxBonusFired){
  spin._superFxBonusFired=true;
  if(this.audio.superScatterHit?.({bonus:true,delay:.25}))stats.bonusReveals++;
 }
 return result;
};
game.superScatterFx={version:'1.0-pulse-tcham',stats,visualOnly:true,soundsOnLanding:true};
})();
