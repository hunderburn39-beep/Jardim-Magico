/* Jardim Mágico 3D V3 — sons originais via Web Audio.
   No áudio externo, sem amostras com direitos de autor.
   Só toca após interação do utilizador; Safari pode suspender em background. */
class GardenAudio{
 constructor(){this.ctx=null;this.effects=true;this.music=false;this.musicTimer=null}
 ensure(){
  const C=window.AudioContext||window.webkitAudioContext;
  if(!C)return null;
  try{
   if(!this.ctx)this.ctx=new C();
   if(this.ctx.state==='suspended')this.ctx.resume().catch(()=>{});
  }catch(err){return null}
  return this.ctx;
 }
 note(frequency,duration=.13,volume=.045,kind='sine',delay=0){
  const ctx=this.ensure();
  if(!ctx)return;
  const when=ctx.currentTime+delay;
  const oscillator=ctx.createOscillator();
  const gain=ctx.createGain();
  oscillator.type=kind;oscillator.frequency.setValueAtTime(Math.max(50,frequency),when);
  gain.gain.setValueAtTime(.0001,when);
  gain.gain.exponentialRampToValueAtTime(Math.max(.0002,volume),when+.008);
  gain.gain.exponentialRampToValueAtTime(.0001,when+duration);
  oscillator.connect(gain);gain.connect(ctx.destination);
  oscillator.start(when);oscillator.stop(when+duration+.03);
 }
 play(name){
  if(!this.effects)return;
  if(name==='click'){this.note(460,.07,.04,'sine')}
  if(name==='spin'){
   this.note(420,.10,.035,'triangle');
   this.note(355,.12,.028,'triangle',.10);
  }
  if(name==='cascade'){
   for(let i=0;i<3;i++)this.note(650-i*180,.11,.024,'sine',i*.065);
  }
  if(name==='win'){
   for(let i=0;i<4;i++)this.note([523,659,784,1047][i],.16,.045,'sine',i*.11);
  }
  if(name==='bonus'){
   for(let i=0;i<5;i++)this.note([392,523,659,784,1047][i],.19,.045,'triangle',i*.10);
  }
 }
 toggleEffects(){
  this.ensure();this.effects=!this.effects;return this.effects;
 }
 toggleMusic(){
  this.ensure();this.music=!this.music;
  if(this.music){
   let index=0;
   const pattern=[392,493.88,587.33,493.88,440,523.25,659.25,523.25];
   this.musicTimer=setInterval(()=>{
    if(document.hidden)return;
    this.note(pattern[index++%pattern.length],.36,.010,'sine');
   },520);
  }else{
   clearInterval(this.musicTimer);this.musicTimer=null;
  }
  return this.music;
 }
}
export const audio=new GardenAudio();
