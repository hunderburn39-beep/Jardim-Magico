'use strict';
/* Pesquisa de calibração em ambiente de testes. NÃO modifica o jogo.
 * Ajusta apenas os pesos sorteados pela economia via makeGrid.
 * Todos os montantes continuam sujeitos ao cofre e ao teto 5000x.
 */
const E=require('../js/engine.js'),S=require('../js/session.js');
globalThis.window=globalThis;globalThis.TombEngine=E;globalThis.FaraoSession=S;
require('../js/super-scatter-core.js');require('../js/economy.js');
const N=Number(process.env.CALIBRATION_SAMPLES||5000),level=90;
const mulberry32=seed=>()=>{seed+=0x6d2b79f5;let x=seed;
 x=Math.imul(x^(x>>>15),x|1);x^=x+Math.imul(x^(x>>>7),x|61);
 return ((x^(x>>>14))>>>0)/4294967296;};
const ordinaryMake=E.makeGrid,ordinaryRandom=E.randomFloat;
function trial(premium,baseScatter,freeScatter){
 const s=S.fresh(),rng=mulberry32(0x3011ce);
 S.adjustCredits(s,'deposit',10000);
 S.economy.adjustFund(s,'add',1000000);
 S.economy.setLevel(s,level);
 let onFree=false,paid=0,fs=0,guard=0;
 E.makeGrid=(nextRng,weights)=>{
   if(!weights)return ordinaryMake(nextRng,weights);
   const base=onFree?E.FREE_WEIGHTS:E.WEIGHTS,custom={...weights};
   for(const symbol of ['HORUS','MASK','ANUBIS','KING']){
     const tweak=onFree?1:symbol==='KING'?1.12:symbol==='ANUBIS'?1.05:.94;
     custom[symbol]=base[symbol]*premium*tweak;
   }
   custom.TOMB=base.TOMB*(onFree?freeScatter:baseScatter);
   return ordinaryMake(nextRng,custom);
 };
 E.randomFloat=rng;
 try{
   while(paid<N||s.feature){
     if(++guard>N*30)throw Error('truncation');
     if(!s.feature&&paid>=N)break;
     onFree=!!s.feature;
     const {result}=S.begin(s,rng);
     if(onFree)fs++;else paid++;
     S.settle(s);
     S.finishPayout(s);
   }
 }finally{E.makeGrid=ordinaryMake;E.randomFloat=ordinaryRandom;}
 const stake=S.credits(s.stats.stakeU),win=S.credits(s.stats.winU);
 return {premium,baseScatter,freeScatter,rtp:+(100*win/stake).toFixed(2),
   features:s.stats.features,fs,win:+win.toFixed(2)};
}
const out=[];
for(const premium of [.55,.70,.85,1.0])
 for(const base of [.30,.40,.50,.60])
  for(const free of [.20,.30,.40]){
   try{out.push(trial(premium,base,free));}
   catch(e){out.push({premium,base,free,error:String(e)});}
  }
out.sort((a,b)=>Math.abs((a.rtp??9999)-90)-Math.abs((b.rtp??9999)-90));
for(const row of out.slice(0,14))console.log('CALIBRACAO '+JSON.stringify(row));
