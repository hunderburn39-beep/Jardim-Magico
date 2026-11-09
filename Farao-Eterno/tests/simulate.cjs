/* Reproducible full-round Monte Carlo, using the actual approved evaluator. */
const E=require('../js/engine.js'),fs=require('node:fs');
const count=Number(process.argv[2]||1000000),seed=Number(process.argv[3]||20261109);
let state=seed;const rng=()=>{state|=0;state=state+0x6D2B79F5|0;let t=Math.imul(state^state>>>15,1|state);t=t+Math.imul(t^t>>>7,61|t)^t;return((t^t>>>14)>>>0)/4294967296;};
let sum=0,squares=0,baseSum=0,bonusSum=0,bonuses=0,free=0,retriggers=0,hits=0,max=0,maxFree=0,caps=0;
const started=Date.now();
for(let n=0;n<count;n++){
 const result=E.evaluate(E.makeGrid(rng),1,10),base=Math.min(5000,result.total);let bonus=0,played=0;
 if(result.trigger){
  bonuses++;const feature=E.startFeature(rng);
  while(feature.left>0){
   feature.left--;played++;free++;
   const r=E.evaluate(E.makeGrid(rng),1,10,feature.symbols),award=Math.max(0,Math.min(r.total,5000-bonus));bonus+=award;
   if(r.trigger){E.retrigger(feature,rng);retriggers++;}
   if(bonus>=5000){feature.left=0;caps++;}
  }
 }
 const total=base+bonus;sum+=total;squares+=total*total;baseSum+=base;bonusSum+=bonus;if(total>0)hits++;max=Math.max(max,total);maxFree=Math.max(maxFree,played);
}
const mean=sum/count,variance=(squares-count*mean*mean)/(count-1),se=Math.sqrt(variance/count),p=E.WEIGHTS.TOMB/Object.values(E.WEIGHTS).reduce((a,b)=>a+b,0),q=1-(1-p)**3;
const trigger=10*q**3*(1-q)**2+5*q**4*(1-q)+q**5;
const report={model:'Faraó Eterno V1.0 — pagamentos V0.6',seed,paidRounds:count,lines:10,bet:1,rtpPercent:mean*100,approx95CI:[(mean-1.96*se)*100,(mean+1.96*se)*100],baseRtpPercent:baseSum/count*100,bonusRtpPercent:bonusSum/count*100,paidRoundHitPercent:hits/count*100,features:bonuses,featureFrequency:count/bonuses,exactTriggerProbability:trigger,expectedFreeSpinsWithoutCap:8/(1-8*trigger),freeSpins:free,retriggers,featureCapHits:caps,largestObservedRound:max,longestObservedFeature:maxFree,elapsedSeconds:(Date.now()-started)/1000,notes:'Estimativa de amostra de rondas completas, sem arriscar. IC normal aproximado, distribuição com caudas pesadas; não é certificação ou garantia de retorno.'};
const json=JSON.stringify(report,null,2);console.log(json);if(process.argv[4])fs.writeFileSync(process.argv[4],json+'\n');
