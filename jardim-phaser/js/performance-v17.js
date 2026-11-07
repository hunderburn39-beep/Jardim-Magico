/* Jardim Mágico Phaser V1.7 — controlo de animação decorativa.
   Observa apenas o ritmo de desenho; não toca em símbolos, RNG, prémios ou Phaser. */
export function slowFrameRatio(gaps){
 const valid=gaps.filter(ms=>Number.isFinite(ms)&&ms>=5&&ms<150);
 if(valid.length===0)return 0;
 return valid.filter(ms=>ms>28).length/valid.length;
}
export function createPerformanceGuard(root,view=window){
 let active=false,frameId=null,last=0,samples=[],goodWindows=0;
 const request=view.requestAnimationFrame?.bind(view);
 const cancel=view.cancelAnimationFrame?.bind(view);
 function sample(now){
  if(!active)return;
  if(last){
   const gap=now-last;
   if(gap>=5&&gap<150)samples.push(gap);
  }
  last=now;
  if(samples.length>=70){
   const slow=slowFrameRatio(samples);
   if(slow>=.27){
    root.classList.add('lite-effects');
    goodWindows=0;
   }else if(slow<=.08){
    goodWindows++;
    if(goodWindows>=3)root.classList.remove('lite-effects');
   }else goodWindows=0;
   samples=[];
  }
  frameId=request(sample);
 }
 return {
  start(){
   if(active)return;
   active=true;
   root.classList.add('is-spinning');
   last=0;samples=[];frameId=request?request(sample):null;
  },
  stop(){
   if(!active)return;
   active=false;
   if(frameId!==null)cancel?.(frameId);
   frameId=null;last=0;samples=[];
   root.classList.remove('is-spinning');
  }
 };
}
