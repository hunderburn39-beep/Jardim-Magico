/* Jardim Mágico Phaser V2.9 — medir somente fluidez de apresentação.
   Não altera RTP, símbolos, apostas, RNG, saldos ou resultados. */
export function slowFrameRatio(gaps){
 const valid=gaps.filter(ms=>Number.isFinite(ms)&&ms>=5&&ms<150);
 if(valid.length===0)return 0;
 return valid.filter(ms=>ms>28).length/valid.length;
}
/* Limitar apenas partículas cosméticas, nunca remoção de peças ou ganhos. */
export function burstParticleBudget(winners,low=false){
 const count=Math.max(1,Math.min(15,Math.trunc(Number(winners)||1)));
 if(low)return Math.max(1,Math.min(2,Math.floor(16/count)));
 return Math.max(2,Math.min(6,Math.floor(48/count)));
}
export function createPerformanceGuard(root,view=window,onQualityChange=null){
 let active=false,paused=false,frameId=null,last=0,samples=[],goodWindows=0;
 let lite=!!root.classList.contains?.('lite-effects');
 const request=view.requestAnimationFrame?.bind(view);
 const cancel=view.cancelAnimationFrame?.bind(view);
 const reduced=!!view.matchMedia?.('(prefers-reduced-motion: reduce)')?.matches;
 function changeQuality(low){
  const next=!!low;
  if(lite===next)return;
  lite=next;
  if(lite)root.classList.add('lite-effects');
  else root.classList.remove('lite-effects');
  if(typeof onQualityChange==='function')onQualityChange(lite);
 }
 function schedule(){frameId=request?request(sample):null}
 function sample(now){
  if(!active||paused)return;
  if(last){
   const gap=now-last;
   if(gap>=5&&gap<150)samples.push(gap);
  }
  last=now;
  if(samples.length>=70){
   const slow=slowFrameRatio(samples);
   if(slow>=.27){changeQuality(true);goodWindows=0}
   else if(slow<=.08&&!reduced){
    goodWindows++;
    if(goodWindows>=3)changeQuality(false);
   }else goodWindows=0;
   samples=[];
  }
  schedule();
 }
 return {
  start(){
   if(active)return;
   active=true;paused=false;
   root.classList.add('is-spinning');
   last=0;samples=[];goodWindows=0;
   if(reduced)changeQuality(true);
   if(typeof onQualityChange==='function')onQualityChange(lite);
   schedule();
  },
  pause(){
   if(!active||paused)return;
   paused=true;
   if(frameId!==null)cancel?.(frameId);
   frameId=null;last=0;samples=[];goodWindows=0;
  },
  resume(){
   if(!active||!paused)return;
   paused=false;last=0;samples=[];goodWindows=0;
   schedule();
  },
  stop(){
   if(!active)return;
   active=false;paused=false;
   if(frameId!==null)cancel?.(frameId);
   frameId=null;last=0;samples=[];goodWindows=0;
   root.classList.remove('is-spinning');
  },
  isLite(){return lite}
 };
}
