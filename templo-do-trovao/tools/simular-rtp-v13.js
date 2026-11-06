/* Portões do Trovão V13 — simulador independente, reprodutível.
 * Executar na pasta templo-do-trovao:
 *   node tools/simular-rtp-v13.js 300000 5000 681925
 * Usa o MESMO módulo js/regras-wild-v13.js carregado pelo jogo.
 * RTP em créditos virtuais, por 1 unidade apostada. Não certificado.
 */
(function(root){
 "use strict";
 const TABLE={
  crown:[10,25,50],hourglass:[2.5,10,25],ring:[2,5,15],
  goblet:[1.5,2,12],ruby:[1,1.5,10],amethyst:[.8,1.2,8],
  amber:[.5,1,5],emerald:[.4,.9,4],sapphire:[.25,.75,2]
 };
 const MAX=5000;
 function simulate(rules,config={}){
  const N=Number(config.spins)||300000,B=Number(config.features)||5000;
  let seed=(Number(config.seed)||681925)>>>0,id=0;
  function rand(){seed^=seed<<13;seed^=seed>>>17;seed^=seed<<5;return(seed>>>0)/4294967296}
  function spawn(b){return rules.draw(b,++id,rand)}
  function spin(b){
   let g=Array.from({length:30},()=>spawn(b));
   let base=0,mult=0,scatterPay=0,maxScatter=0,trigger=false,cascades=0;
   const seen=new Set();
   for(;;){
    let scat=0;
    for(const cell of g){
     if(cell.kind==="scatter")scat++;
     else if(cell.kind==="mult"&&!seen.has(cell.id)){seen.add(cell.id);mult+=cell.value}
    }
    if(scat>=(b?3:4))trigger=true;
    if(scat>=4&&scat>maxScatter){
      scatterPay+=rules.scatterMultiplier(scat)-rules.scatterMultiplier(maxScatter);
      maxScatter=scat;
    }
    const win=rules.score(g,TABLE,1);
    if(!win.result.length||cascades>=36)break;
    cascades++;base+=win.pay;
    const next=Array(30);
    for(let col=0;col<6;col++){
     const survivors=[];
     for(let row=4;row>=0;row--){
      const x=g[6*row+col];if(!win.matched.has(x.kind))survivors.push(x)
     }
     let row=4;
     for(const x of survivors)next[6*row--+col]=x;
     while(row>=0)next[6*row--+col]=spawn(b);
    }
    g=next;
   }
   return{base,mult,scatterPay,trigger,cascades};
  }
  let baseSum=0,baseSquared=0,hit=0,trigger=0,cascades=0;
  const bins=[0,0,0,0];
  for(let i=0;i<N;i++){
   const x=spin(false),paid=Math.min(MAX,x.base*Math.max(1,x.mult)+x.scatterPay);
   baseSum+=paid;baseSquared+=paid*paid;
   if(paid>0)hit++;
   if(x.trigger)trigger++;
   cascades+=x.cascades;
   bins[paid===0?0:paid<1?1:paid<5?2:3]++;
  }
  let featureSum=0,featureSquared=0,featureSpinCount=0,maxFeature=0;
  for(let j=0;j<B;j++){
   let free=15,won=0,cumulative=0,used=0;
   while(free>0&&won<MAX&&used<200){
    used++;free--;
    const x=spin(true);
    if(x.base>0&&x.mult>0)cumulative+=x.mult;
    won+=x.base*(x.base>0&&x.mult>0?Math.max(1,cumulative):1)+x.scatterPay;
    if(x.trigger)free+=5;
    won=Math.min(MAX,won);
   }
   featureSum+=won;featureSquared+=won*won;
   featureSpinCount+=used;maxFeature=Math.max(maxFeature,won);
  }
  const meanBase=baseSum/N,meanFeature=featureSum/B,trig=trigger/N;
  const varianceBase=Math.max(0,baseSquared/N-meanBase**2);
  const varianceFeature=Math.max(0,featureSquared/B-meanFeature**2);
  const standardErrorBase=Math.sqrt(varianceBase/N);
  const standardErrorFeature=Math.sqrt(varianceFeature/B);
  // Aproximação do erro combinado, ignorando covariâncias residuais.
  const se=Math.sqrt(standardErrorBase**2+(trig*standardErrorFeature)**2+
    (meanFeature**2*trig*(1-trig)/N));
  return{
   spins:N,bonusFeatures:B,seed:Number(config.seed)||681925,
   RTP_base_pct:100*meanBase,
   RTP_total_pct:100*(meanBase+trig*meanFeature),
   RTP_95CI_approx_pct:[100*(meanBase+trig*meanFeature-1.96*se),100*(meanBase+trig*meanFeature+1.96*se)],
   hit_pct:100*hit/N,scatter_trigger_pct:100*trig,
   mean_cascades:cascades/N,avg_bonus_payout_x:meanFeature,
   avg_bonus_spins:featureSpinCount/B,
   bonus_buy_100x_expected_RTP_pct:100*meanFeature/100,
   max_bonus_observed_x:maxFeature,win_distribution:bins,
   max_win_cap_x:MAX
  };
 }
 if(typeof module!=="undefined"&&module.exports){
  module.exports={simulate};
  if(require.main===module){
   global.window={};
   require("../js/regras-wild-v13.js");
   const result=simulate(global.window.TrovaoRules,{
    spins:Number(process.argv[2])||300000,
    features:Number(process.argv[3])||5000,
    seed:Number(process.argv[4])||681925
   });
   process.stdout.write(JSON.stringify(result,null,2)+"\n");
  }
 }else root.TrovaoRtpSimulator={simulate};
})(typeof window!=="undefined"?window:{});
