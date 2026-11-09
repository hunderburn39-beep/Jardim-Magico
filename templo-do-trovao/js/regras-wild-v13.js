/* Portões do Trovão V14 — regras definitivas de substituição do WILD.
 * O RNG é fornecido pelo motor do jogo. Nunca adapta probabilidades
 * ao saldo, às perdas, aos prémios recentes ou ao jogador.
 * RTP: estimar com tools/simular-rtp-v13.js; demo, não certificado.
 */
(function(){
 "use strict";
 const KINDS=["crown","ring","goblet","hourglass","ruby","sapphire","emerald","amethyst","amber"];
 const EXCLUDED_WILD_KINDS=Object.freeze(["scatter","mult","wild"]);
 const canWildReplace=kind=>KINDS.includes(kind)&&!EXCLUDED_WILD_KINDS.includes(kind);
 const WEIGHTS=[5,6,6,7,8,9,9,9,9];
 const MULTIPLIERS=[2,3,4,5,6,8,10,12,15,20,25,50,100,250,500];
 const MULT_WEIGHTS=[44,30,17,8,4,2,1,.6,.35,.22,.12,.03,.012,.003,.001];
 const WILD_VALUES=[1,2,3,4,5];
 const WILD_WEIGHTS=[80,15,4,.8,.2];
 const CONFIG={
   base:{scatter:.015,mult:.004,wild:.0225},
   bonus:{scatter:.021,mult:.006,wild:.057}
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
     // O Wild substitui qualquer uma das nove peças pagantes; NUNCA
     // SCATTER, esfera multiplicadora ou outro WILD.
     if(!canWildReplace(kind))continue;
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

 // V19: mecânica 7x7 de grupos ortogonais (estilo sugar cluster).
 const CLUSTER_COLS=7,CLUSTER_ROWS=7,CLUSTER_SIZE=49,MAX_CLUSTER_MULT=1024;
 function neighbours(index){
   const x=index%CLUSTER_COLS,y=Math.floor(index/CLUSTER_COLS),out=[];
   if(x>0)out.push(index-1);
   if(x<CLUSTER_COLS-1)out.push(index+1);
   if(y>0)out.push(index-CLUSTER_COLS);
   if(y<CLUSTER_ROWS-1)out.push(index+CLUSTER_COLS);
   return out;
 }
 function emptySpots(){return Array(CLUSTER_SIZE).fill(0)}
 function upgradeSpots(spots,positions){
   if(!Array.isArray(spots)||spots.length!==CLUSTER_SIZE)throw Error("Spots inválidos");
   const next=spots.slice();
   for(const i of positions){
     if(!Number.isInteger(i)||i<0||i>=CLUSTER_SIZE)throw Error("Índice inválido");
     const old=next[i]||0;
     next[i]=old===0?1:old===1?2:Math.min(MAX_CLUSTER_MULT,old*2);
   }
   return next;
 }
 function clusterScore(grid,payTable,bet=1,spots=emptySpots(),maxPay=Infinity){
   if(!Array.isArray(grid)||grid.length!==CLUSTER_SIZE)throw Error("Grelha não é 7x7");
   if(!Array.isArray(spots)||spots.length!==CLUSTER_SIZE)throw Error("Spots inválidos");
   if(!Number.isFinite(bet)||bet<=0)throw Error("Aposta inválida");
   const allowed=Number(maxPay);
   if(Number.isNaN(allowed)||allowed<0)throw Error("Teto inválido");
   const candidates=[];
   for(const kind of KINDS){
     const visited=new Set();
     for(let start=0;start<CLUSTER_SIZE;start++){
       if(visited.has(start)||grid[start]?.kind!==kind)continue;
       const queue=[start],group=new Set([start]);visited.add(start);
       for(let head=0;head<queue.length;head++){
         for(const n of neighbours(queue[head])){
           if(group.has(n))continue;
           const t=grid[n]?.kind;
           if(t!==kind&&t!=="wild")continue;
           group.add(n);queue.push(n);
           if(t===kind)visited.add(n);
         }
       }
       const positions=Array.from(group);
       if(positions.length<5)continue;
       const wildMultiplier=positions.reduce((v,i)=>grid[i].kind==="wild"?
         Math.max(v,Math.min(5,Math.max(1,Number(grid[i].value)||1))):v,1);
       const rate=payTable[kind][positions.length>=12?2:positions.length>=8?1:0];
       const spotX=Math.max(1,positions.reduce((n,i)=>n+(spots[i]>=2?spots[i]:0),0));
       candidates.push({kind,positions,
         potential:bet*rate*spotX*wildMultiplier});
     }
   }
   candidates.sort((a,b)=>b.potential-a.potential);
   const taken=new Set(),matchedPositions=new Set(),result=[];
   let totalCents=0;
   const capCents=Number.isFinite(allowed)?Math.floor(allowed*100+0.0000001):Number.MAX_SAFE_INTEGER;
   for(const candidate of candidates){
     // Resolver novamente cada componente sem WILD/peças já utilizados.
     const eligible=new Set(candidate.positions.filter(i=>!taken.has(i)));
     const visited=new Set();
     for(const start of eligible){
       if(visited.has(start)||grid[start].kind!==candidate.kind)continue;
       const queue=[start],component=[];visited.add(start);
       for(let j=0;j<queue.length;j++){
         const ix=queue[j];component.push(ix);
         for(const n of neighbours(ix)){
           if(eligible.has(n)&&!visited.has(n)){visited.add(n);queue.push(n)}
         }
       }
       if(component.length<5)continue;
       const wildCount=component.filter(i=>grid[i].kind==="wild").length;
       const wildMultiplier=component.reduce((v,i)=>grid[i].kind==="wild"?
         Math.max(v,Math.min(5,Math.max(1,Number(grid[i].value)||1))):v,1);
       const rate=payTable[candidate.kind][component.length>=12?2:component.length>=8?1:0];
       const spotMultiplier=Math.max(1,component.reduce((n,i)=>n+(spots[i]>=2?spots[i]:0),0));
       const nominal=Math.round(bet*100*rate*spotMultiplier*wildMultiplier);
       const cents=Math.max(0,Math.min(nominal,capCents-totalCents));
       if(!cents)break;
       totalCents+=cents;
       component.forEach(i=>{taken.add(i);matchedPositions.add(i)});
       result.push({symbol:candidate.kind,count:component.length,
         amount:cents/100,wildCount,wildMultiplier,spotMultiplier,positions:component});
       if(totalCents>=capCents)break;
     }
     if(totalCents>=capCents)break;
   }
   return{result,pay:totalCents/100,matchedPositions,
     matched:new Set(result.map(x=>x.symbol)),capReached:totalCents>=capCents};
 }
 function freeSpinAward(n){
   return n>=7?30:n===6?20:n===5?15:n===4?12:n===3?10:0;
 }
 function maxActiveSpot(spots){
   return Math.max(1,...(spots||[]).filter(n=>n>=2));
 }

 window.TrovaoRules={CONFIG,KINDS,EXCLUDED_WILD_KINDS,canWildReplace,WEIGHTS,MULTIPLIERS,MULT_WEIGHTS,WILD_VALUES,WILD_WEIGHTS,draw,score,scatterMultiplier,CLUSTER_COLS,CLUSTER_ROWS,CLUSTER_SIZE,MAX_CLUSTER_MULT,clusterScore,emptySpots,upgradeSpots,freeSpinAward,maxActiveSpot};
})();
