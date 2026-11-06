/* Portões do Trovão V13 — regras matemáticas puras de SCATTER/WILD.
 * O RNG é fornecido pelo motor do jogo. Nunca adapta probabilidades
 * ao saldo, às perdas, aos prémios recentes ou ao jogador.
 * RTP: estimar com tools/simular-rtp-v13.js; demo, não certificado.
 */
(function(){
 "use strict";
 const KINDS=["crown","ring","goblet","hourglass","ruby","sapphire","emerald","amethyst","amber"];
 const WEIGHTS=[5,6,6,7,8,9,9,9,9];
 const MULTIPLIERS=[2,3,4,5,6,8,10,12,15,20,25,50,100,250,500];
 const MULT_WEIGHTS=[44,30,17,8,4,2,1,.6,.35,.22,.12,.03,.012,.003,.001];
 const WILD_VALUES=[1,2,3,4,5];
 const WILD_WEIGHTS=[80,15,4,.8,.2];
 const CONFIG={
   base:{scatter:.015,mult:.004,wild:.023},
   bonus:{scatter:.021,mult:.006,wild:.058}
 };
 function pick(values,weights,rng){
   let total=0;for(let i=0;i<weights.length;i++)total+=weights[i];
   let roll=rng()*total;
   for(let i=0;i<weights.length;i++){roll-=weights[i];if(roll<0)return values[i]}
   return values[values.length-1];
 }
 function draw(bonus,id,rng){
   const cfg=bonus?CONFIG.bonus:CONFIG.base;
   const roll=rng();
   if(roll<cfg.scatter)return{id,kind:"scatter"};
   if(roll<cfg.scatter+cfg.mult)return{id,kind:"mult",value:pick(MULTIPLIERS,MULT_WEIGHTS,rng)};
   if(roll<cfg.scatter+cfg.mult+cfg.wild)return{id,kind:"wild",value:pick(WILD_VALUES,WILD_WEIGHTS,rng)};
   return{id,kind:pick(KINDS,WEIGHTS,rng)};
 }
 function score(grid,payTable,bet=1){
   const counts=Object.fromEntries(KINDS.map(k=>[k,0]));
   const wild=[];
   for(const cell of grid){
     if(cell.kind==="wild")wild.push(cell);
     else if(Object.prototype.hasOwnProperty.call(counts,cell.kind))counts[cell.kind]++;
   }
   const result=[],matched=new Set();
   let pay=0,best=null,bestExtra=0;
   // Cada Wild pode substituir um símbolo, mas só participa numa única
   // combinação por queda. Escolhe-se a combinação mais vantajosa.
   // Se houver vários Wilds, o maior multiplicador (máx. 5×) vale
   // para esta combinação: evita multiplicações sobrepostas.
   let wildX=1;
   for(const w of wild)wildX=Math.max(wildX,Math.min(5,Math.max(1,w.value|0)));
   for(const kind of KINDS){
     const n=counts[kind];
     const native=n>=8?bet*payTable[kind][n>=12?2:n>=10?1:0]:0;
     if(native>0){
       result.push({symbol:kind,count:n,amount:native,wildCount:0,wildMultiplier:1});
       matched.add(kind);pay+=native;
     }
     const enhancedN=n+wild.length;
     if(wild.length && enhancedN>=8){
       const withWild=bet*payTable[kind][enhancedN>=12?2:enhancedN>=10?1:0]*wildX;
       const increment=withWild-native;
       if(increment>bestExtra){
         bestExtra=increment;
         best={kind,count:enhancedN,amount:withWild,wildCount:wild.length,wildMultiplier:wildX};
       }
     }
   }
   if(best){
     const ix=result.findIndex(item=>item.symbol===best.kind);
     const item={symbol:best.kind,count:best.count,amount:best.amount,wildCount:best.wildCount,wildMultiplier:best.wildMultiplier};
     if(ix>=0)result[ix]=item;else result.push(item);
     matched.add(best.kind);matched.add("wild");
     pay+=bestExtra;
   }
   return {result,pay,matched};
 }
 function scatterMultiplier(n){return n>=6?100:n===5?5:n>=4?3:0}
 window.TrovaoRules={CONFIG,KINDS,WEIGHTS,MULTIPLIERS,MULT_WEIGHTS,WILD_VALUES,WILD_WEIGHTS,draw,score,scatterMultiplier};
})();
