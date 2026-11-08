/* Jardim Mágico Phaser — simulação independente de créditos virtuais.
   Não usa euros reais e não representa RTP auditado ou jackpot garantido.
   Matriz [coluna][linha], 5×3. 10 linhas da demo. */
export const SYMBOLS=['potion','green','heart','teapot','wild','gem','mushroom','hat','rose','scatter','stone','coin'];
export const LABELS=['Poção','Esmeralda','Coração','Bule','WILD','Cristal','Cogumelo','Chapéu','Rosa','SCATTER','Pedra','Moeda'];
export const WEIGHTS=[10,13,11,10,4,11,10,9,10,4,12,7];
export const PAYLINES=[
 [1,1,1,1,1],[0,0,0,0,0],[2,2,2,2,2],
 [0,1,2,1,0],[2,1,0,1,2],
 [0,0,1,2,2],[2,2,1,0,0],[1,0,0,1,2],
 [1,2,2,1,0],[0,1,1,1,2]
];
const WILD=4,SCATTER=9,STONE=10,COIN=11;
const round=v=>Math.round((v+Number.EPSILON)*100)/100;
const clamp=(n,a,b)=>Math.min(b,Math.max(a,n));
const weighted=(rng=Math.random,free=false)=>{
 const weights=free?WEIGHTS.map((x,i)=>i===9?2:x):WEIGHTS;
 const total=weights.reduce((a,b)=>a+b,0);
 let x=rng()*total;
 for(let i=0;i<weights.length;i++){x-=weights[i];if(x<0)return i}
 return weights.length-1;
};
export function newBoard(rng=Math.random,free=false){
 return Array.from({length:5},()=>Array.from({length:3},()=>weighted(rng,free)));
}

/* Perfil da demonstração: tendência de combinações iniciais, NÃO é RTP.
   Nunca manipula símbolos depois da animação nem promete uma quota financeira.
   No modo NORMAL a distribuição original permanece idêntica.
*/
export const DEMO_PROFILES=Object.freeze({
 premios:'Prémios',normal:'Normal',entretenimento:'Entretenimento'
});
export function openingByProfile(rng=Math.random,free=false,profile='normal',bet=.2){
 const original=newBoard(rng,free);
 // Em Free Spins conservamos as regras e frequência originais.
 if(free||profile==='normal'||!(profile in DEMO_PROFILES))return original;
 if(rng()>=.42)return original;
 const wantsHit=profile==='premios';
 const matches=board=>{
  const evalResult=evaluate(board,bet);
  const hasEvent=evalResult.lines>0||evalResult.scatter>=3||evalResult.coins>=6;
  return wantsHit?hasEvent:!hasEvent;
 };
 if(matches(original))return original;
 for(let attempt=0;attempt<6;attempt++){
  const candidate=newBoard(rng,free);
  if(matches(candidate))return candidate;
 }
 return original;
}

export function count(board,id){return board.flat().filter(x=>x===id).length}
export function evaluate(board,bet){
 // Uma combinação paga por linha; quaisquer várias linhas somam sempre.
 // Cada combinação inclui as posições exatas que participam na vitória.
 // Um WILD partilhado por duas linhas pode contar nas DUAS linhas,
 // mas será animado uma única vez pelo renderizador 3D.
 const lineWins=[];
 const usedWilds=new Set();
 for(let index=0;index<PAYLINES.length;index++){
   const path=PAYLINES[index];
   const chain=path.map((row,c)=>board[c][row]);
   const pairWild=chain[0]===WILD&&chain[1]===WILD;
   const first=chain.find(s=>s!==WILD)??WILD;
   let amount=pairWild?bet*.55:0;
   let matched=pairWild?2:0;
   if(![SCATTER,STONE,COIN].includes(first)){
     let n=0;
     for(const symbol of chain){
       if(symbol!==first&&symbol!==WILD)break;
       n++;
     }
     if(n>=3){
       const pay=[0,0,0,.07,.25,.8][n]*([3,4,8,5,10,7,4,4,7,0,0,0][first]||1);
       amount+=bet*pay;
       matched=n;
     }
   }
   if(amount>0){
     const cells=path.slice(0,matched).map((r,c)=>({c,r}));
     for(const {c,r} of cells)if(board[c][r]===WILD)usedWilds.add(c+','+r);
     lineWins.push({line:index+1,symbol:first,count:matched,
       amount:round(amount),cells});
   }
 }
 // A soma visível de cada combinação tem de ser igual ao prémio das linhas.
 const raw=round(lineWins.reduce((sum,win)=>sum+win.amount,0));
 const wildCells=[...usedWilds].map(key=>{
  const [c,r]=key.split(',').map(Number);return {c,r};
 });
 return {
   raw,
   lines:lineWins.length,
   lineWins,
   wilds:wildCells.length,
   wildCells,
   scatter:count(board,SCATTER),
   coins:count(board,COIN)
 };
}
/* Conta os WILDs premiados sem os repetir nem os multiplicar entre si.
   Primeira queda ×2 -> ×2; outra ×3 -> ×5; outra ×1 -> ×6. */
export function addWildValues(previous,values=[]){
 const initial=Number.isFinite(Number(previous))?Math.max(0,Number(previous)):0;
 const gained=Array.isArray(values)?values.reduce((sum,v)=>
  sum+(Number.isInteger(v)&&v>=1&&v<=5?v:0),0):0;
 const total=initial+gained;
 return {gained,total,multiplier:Math.max(1,total)};
}
/* Cascatas sem limite artificial: enquanto uma etapa tiver
   combinações premiadas, as peças vencedoras desaparecem, as restantes
   descem na coluna e entram peças novas de cima.
   Termina apenas quando uma etapa já não tem combinação premiada.
   O pagamento é calculado antes da animação e limitado ao fundo virtual,
   nunca o número de quedas. */
export function winningCells(evaluation){
 const ids=new Map();
 for(const line of evaluation.lineWins||[])for(const cell of line.cells){
   ids.set(cell.c+','+cell.r,{c:cell.c,r:cell.r});
 }
 return [...ids.values()];
}
export function collapseBoard(board,removed,rng=Math.random,free=false){
 const winners=new Set(removed.map(p=>p.c+','+p.r));
 const next=Array.from({length:5},()=>Array(3).fill(null));
 for(let c=0;c<5;c++){
   const survivors=[];
   for(let r=2;r>=0;r--){
     if(!winners.has(c+','+r))survivors.push(board[c][r]);
   }
   let dest=2;
   for(const symbol of survivors)next[c][dest--]=symbol;
   while(dest>=0)next[c][dest--]=weighted(rng,free);
 }
 return next;
}
/* Gerador solvente para CRÉDITOS FICTÍCIOS.
   Nunca corta o pagamento depois de mostrar as peças. Escolhe a grelha
   e TODAS as cascatas verificando antecipadamente o fundo disponível.
   O número de cascatas não tem limite: acabam numa grelha sem prémios.
   As tentativas internas apenas procuram uma combinação financeiramente
   viável, não interrompem uma cadeia visível ao jogador. */
function safeOpening(rng=Math.random,free=false){
 // Primeiro tentar uma grelha natural sem linhas pagas.
 for(let n=0;n<48;n++){
   const board=newBoard(rng,free);
   if(evaluate(board,1).lines===0&&count(board,SCATTER)<3&&count(board,COIN)<6)return board;
 }
 // Resguardo determinístico para fontes pseudoaleatórias defeituosas.
 // A pedra bloqueia as linhas e não é apresentada como símbolo premiado.
 const board=newBoard(rng,free);
 for(let c=0;c<3;c++)for(let row=0;row<3;row++)board[c][row]=STONE;
 // Evitar também três Scatter ou seis moedas numa grelha sem fundos.
 for(let c=3;c<5;c++)for(let row=0;row<3;row++){
   if(board[c][row]===SCATTER||board[c][row]===COIN)board[c][row]=STONE;
 }
 return board;
}
function safeCollapse(previous,removed,rng,free,afford,bet){
 // Repetir APENAS as novas peças do topo: manter sempre as sobreviventes.
 for(let attempt=0;attempt<32;attempt++){
   const next=collapseBoard(previous,removed,rng,free);
   const ev=evaluate(next,bet);
   if(ev.lines===0&&afford(ev))return next;
 }
 // Se o fundo não suporta novas linhas, preencher os espaços que caíram
 // com pedras; a coluna permanece coerente e acaba naturalmente.
 const stoneRng=()=>0;
 const next=collapseBoard(previous,removed,stoneRng,free);
 const touched=new Set(removed.map(p=>p.c));
 for(let col=0;col<5;col++){
   if(!touched.has(col))continue;
   const number=removed.filter(p=>p.c===col).length;
   for(let row=0;row<number;row++)next[col][row]=STONE;
 }
 const ev=evaluate(next,bet);
 // Uma combinação inevitável formada pelas peças sobreviventes exige
 // outra sequência inteira: nenhuma linha visível pode ficar por pagar.
 if(ev.lines!==0||!afford(ev))return null;
 return next;
}
function tryCascadeStages(board,bet,rng=Math.random,free=false,policy={}){
 const reserve=policy.reserve===undefined?Infinity:Math.max(0,policy.reserve);
 const freeCarry=policy.freeTotal||0;
 const freeMultiplier=policy.freeMultiplier||1;
 const stages=[];
 let currentBoard=board,removed=[],raw=0,potential=0,wildPoints=0,maxCoins=0;
 const score=board=>{
  const ev=evaluate(board,bet);
  ev.wildAwards=(ev.wildCells||[]).map(cell=>({
   ...cell,value:clamp(1+Math.floor(rng()*5),1,5)
  }));
  ev.wildGain=addWildValues(0,ev.wildAwards.map(w=>w.value)).gained;
  return ev;
 };
 // Todas as cascatas são calculadas antes da animação e conferidas
 // contra o cofre com o acumulado de WILDs efetivo.
 const afford=(ev)=>{
   const nextRaw=round(raw+ev.raw);
   const nextSpin=addWildValues(wildPoints,(ev.wildAwards||[]).map(w=>w.value));
   const nextPotential=round(potential+round(ev.raw*nextSpin.multiplier));
   const projectedMultiplier=clamp(freeMultiplier+
    Math.max(0,nextSpin.multiplier-1),1,1000);
   const eventualFreePrize=round((freeCarry+nextRaw)*projectedMultiplier);
   const fullPrize=free?eventualFreePrize:nextPotential;
   // Se cair um Hold, as seis moedas iniciais têm de ser integralmente
   // pagáveis depois de qualquer prémio da mesma jogada.
   const triggeringHold=!free&&Math.max(maxCoins,ev.coins)>=6;
   const minHold=triggeringHold?round(bet*6):0;
   return round(fullPrize+minHold)<=round(reserve);
 };
 for(;;){
   let ev=score(currentBoard);
   if(!afford(ev)){
     let accepted=false;
     for(let attempt=0;attempt<48;attempt++){
       const candidate=stages.length?
         collapseBoard(stages.at(-1).board,removed,rng,free):
         newBoard(rng,free);
       const candidateEv=score(candidate);
       if(afford(candidateEv)){
         currentBoard=candidate;ev=candidateEv;accepted=true;break;
       }
     }
     if(!accepted){
       currentBoard=stages.length?
         safeCollapse(stages.at(-1).board,removed,rng,free,afford,bet):
         safeOpening(rng,free);
       if(!currentBoard)return null;
       ev=score(currentBoard);
       if(!afford(ev))return null;
     }
   }
   const multi=addWildValues(wildPoints,ev.wildAwards.map(w=>w.value));
   ev.multiplier=multi.multiplier;
   ev.paid=round(ev.raw*ev.multiplier);
   stages.push({board:currentBoard,ev,removed});
   raw=round(raw+ev.raw);
   potential=round(potential+ev.paid);
   wildPoints=multi.total;
   maxCoins=Math.max(maxCoins,ev.coins);
   const winners=winningCells(ev);
   if(!winners.length)break;
   currentBoard=collapseBoard(currentBoard,winners,rng,free);
   removed=winners;
 }
 return stages;
}
export function cascadeStages(board,bet,rng=Math.random,free=false,policy={}){
 // Os cenários são gerados e verificados ANTES de iniciar a animação.
 // Se uma queda obrigasse a pagar mais do que o fundo permite, descartar
 // o giro inteiro e procurar outra trajetória solvente.
 for(let attempt=0;attempt<48;attempt++){
   const candidate=attempt===0?board:newBoard(rng,free);
   const outcome=tryCascadeStages(candidate,bet,rng,free,policy);
   if(outcome)return outcome;
 }
 const safe=safeOpening(rng,free);
 const result=tryCascadeStages(safe,bet,rng,free,policy);
 if(!result)throw new Error('Não existe grelha financiável para o cofre');
 return result;
}

export class DemoBank{
 constructor(){
   this.balance=100;
   this.reserve=2300;
   this.stake=.2;
   this.spins=0;
   this.lastWin=0;
   this.freeSpins=0;
   this.freeTotal=0;
   this.freeMultiplier=1;
   this.freeAwarded=0;
   this.freePlayed=0;
   this.mode='base';
   this.profile='normal';
   this.hold=null;
   this.status='Pronto para rodar';
 }
 get snapshot(){return {balance:this.balance,reserve:this.reserve,stake:this.stake,spins:this.spins,lastWin:this.lastWin,freeSpins:this.freeSpins,freeTotal:this.freeTotal,freeMultiplier:this.freeMultiplier,freePlayed:this.freePlayed,freeAwarded:this.freeAwarded,mode:this.mode,profile:this.profile,hold:this.hold?{...this.hold,grid:[...this.hold.grid]}:null,status:this.status}}
 get bonusPrice(){return round(this.stake*20)}
 get holdPrice(){return round(this.stake*30)}
 setStake(value){if(this.mode!=='base')return false;this.stake=clamp(round(value),.05,10);return true}
 setProfile(value){if(this.mode!=='base'||!(value in DEMO_PROFILES))return false;this.profile=value;return true}
 creditDemo(amount=25){this.balance=round(this.balance+clamp(amount,1,100));this.status='Créditos de demonstração adicionados';return this.snapshot}
 canSpin(){return this.mode==='free'||(this.mode==='base'&&this.balance>=this.stake)}
 addFree(count=15){
   if(this.mode==='hold')return false;
   if(this.mode==='base'){this.mode='free';this.freeTotal=0;this.freeMultiplier=1;this.freeAwarded=0;this.freeSpins=0;this.freePlayed=0;}
   this.freeSpins=clamp(this.freeSpins+count,0,60);
   this.status=count+' Free Spins de demonstração';
   return true;
 }
 buyFree(){
   if(this.mode!=='base'||this.balance<this.bonusPrice)return {ok:false,message:'Saldo virtual insuficiente'};
   this.balance=round(this.balance-this.bonusPrice);
   this.reserve=round(this.reserve+this.bonusPrice);
   this.addFree(15);
   return {ok:true,message:'Compraste 15 Free Spins fictícias'};
 }
 buyHold(){
   if(this.mode!=='base'||this.balance<this.holdPrice){
     return {ok:false,message:'Saldo virtual insuficiente'};
   }
   // Compra voluntária: acrescenta o preço ao fundo antes de gerar moedas.
   const price=this.holdPrice;
   if(round(this.reserve+price)<round(this.stake*6)){
     return {ok:false,message:'Cofre virtual sem capacidade para iniciar o Hold'};
   }
   this.balance=round(this.balance-price);
   this.reserve=round(this.reserve+price);
   const result=this.startHold();
   if(!result)throw new Error('Compra do Hold sem grelha financiável');
   return {ok:true,message:'Hold & Win de demonstração iniciado'};
 }
 startHold(rng=Math.random){
   if(this.mode!=='base'||round(this.stake*6)>this.reserve){
     this.status='Cofre sem capacidade para as seis moedas iniciais';
     return false;
   }
   // Reserva suficiente para pagar TODAS as moedas já desenhadas,
   // sem reduzir o seu valor depois.
   const positions=[];
   const available=Array.from({length:16},(_,i)=>i);
   for(let i=0;i<6;i++){
     const n=Math.floor(rng()*available.length);
     positions.push(available.splice(n,1)[0]);
   }
   const grid=Array(16).fill(0);
   const multipliers=[1,2,3,5,10];
   let sum=0;
   for(let n=0;n<positions.length;n++){
     const p=positions[n];
     // Guardar pelo menos 1× para CADA uma das moedas que ainda falta
     // colocar. Só permitir moedas maiores com o excedente do fundo.
     const remainingMinimum=positions.length-n-1;
     const eligible=multipliers.filter(x=>
       round((sum+x+remainingMinimum)*this.stake)<=this.reserve);
     if(!eligible.length)throw new Error('Moedas iniciais não financiadas');
     const pick=eligible[Math.floor(rng()*eligible.length)];
     grid[p]=pick;sum+=pick;
   }
   this.mode='hold';
   this.hold={grid,tries:3,rounds:0,total:round(sum*this.stake)};
   this.status='Hold & Win · 3 tentativas';
   return this.snapshot;
 }
 holdTurn(rng=Math.random){
   if(this.mode!=='hold')return {ok:false,message:'Hold & Win não ativo'};
   const h=this.hold;
   const empty=h.grid.flatMap((n,i)=>n?[]:[i]);
   let newCoins=0;
   if(empty.length&&rng()<.72){
     const target=Math.min(empty.length,rng()<.18?2:1);
     for(let j=0;j<target;j++){
       const nextLast=empty.length===1;
       const base=h.grid.reduce((sum,n)=>sum+n,0);
       const valid=[1,2,3,5,10,20].filter(x=>{
         const coinsValue=round((base+x)*this.stake);
         // Fechar o 4×4 pode ativar um prémio extra de 250×.
         const completePrize=nextLast?round(this.stake*250):0;
         return round(coinsValue+completePrize)<=this.reserve;
       });
       if(!valid.length)break; // Não deixar cair moeda que não possa pagar.
       const index=Math.floor(rng()*empty.length);
       const pos=empty.splice(index,1)[0];
       h.grid[pos]=valid[Math.floor(rng()*valid.length)];
       newCoins++;
     }
   }
   h.tries=newCoins?3:h.tries-1;
   h.rounds++;
   h.total=round(h.grid.reduce((sum,n)=>sum+n,0)*this.stake);
   const filled=h.grid.every(Boolean);
   const complete=h.tries<=0||filled;
   if(!complete){
     this.status='Hold & Win · '+h.tries+' tentativas';
     return {ok:true,done:false,hold:{...h,grid:[...h.grid]}};
   }
   const prize=round(h.total+(filled?round(this.stake*250):0));
   if(prize>this.reserve)throw new Error('Grelha Hold não financiada');
   this.reserve=round(this.reserve-prize);
   this.balance=round(this.balance+prize);
   this.lastWin=prize;
   this.mode='base';
   this.status='Hold & Win: '+prize.toFixed(2)+' créditos';
   const result={ok:true,done:true,prize,hold:{...h,grid:[...h.grid]}};
   this.hold=null;
   return result;
 }
 spin(rng=Math.random){
   if(!this.canSpin())return {ok:false,message:'Saldo virtual insuficiente ou bónus ativo'};
   const free=this.mode==='free';
   if(!free){
     this.balance=round(this.balance-this.stake);
     this.reserve=round(this.reserve+this.stake*.48);
   }
   this.spins++;
   // Cada WILD participante recebe um valor próprio de ×1 até ×5.
   // Selecionar apenas grelhas/cascatas inteiramente pagáveis pelo fundo.
   // Não há "prémio cortado": todas as linhas que saírem são pagas.
   const stages=cascadeStages(openingByProfile(rng,free,this.profile,this.stake),this.stake,rng,free,{
     reserve:this.reserve,freeTotal:this.freeTotal,
     freeMultiplier:this.freeMultiplier
   });
   const board=stages[0].board;
   const ev=stages[0].ev;
   const rawTotal=round(stages.reduce((sum,stage)=>sum+stage.ev.raw,0));
   const totalWilds=stages.reduce((sum,stage)=>sum+stage.ev.wilds,0);
   const scatterCount=Math.max(...stages.map(stage=>stage.ev.scatter));
   const coinCount=Math.max(...stages.map(stage=>stage.ev.coins));
   const potential=round(stages.reduce((sum,stage)=>sum+stage.ev.paid,0));
   const spinMultiplier=stages.at(-1).ev.multiplier;
   let credited=0,triggered='';
   if(free){
     this.freeTotal=round(this.freeTotal+rawTotal);
     this.freeMultiplier=clamp(this.freeMultiplier+
      (totalWilds?Math.max(0,spinMultiplier-1):0),1,1000);
     this.freeSpins--;
     this.freePlayed++;
     const space=Math.max(0,60-this.freePlayed-this.freeSpins);
     if(scatterCount>=3&&space>0){
       const n=Math.min(15,space);this.freeSpins+=n;triggered='Reativação: +'+n+' Free Spins';
     }else if(scatterCount>=2&&space>0){
       const n=Math.min(8,space);this.freeSpins+=n;triggered='Reativação: +'+n+' Free Spins';
     }
     if(this.freeSpins===0){
       const total=round(this.freeTotal*this.freeMultiplier);
       if(total>this.reserve)throw new Error('Prémio Free Spins não financiado');
       credited=total;
       this.balance=round(this.balance+credited);
       this.reserve=round(this.reserve-credited);
       this.lastWin=credited;
       this.mode='base';
       triggered=triggered||'Fim das Free Spins';
       this.freeAwarded=credited;
     }
   }else{
     if(potential>this.reserve)throw new Error('Prémio base não financiado');
     credited=potential;
     this.reserve=round(this.reserve-credited);
     this.balance=round(this.balance+credited);
     this.lastWin=credited;
     if(scatterCount>=3){this.addFree(15);triggered='15 Free Spins!'}
     else if(coinCount>=6){this.startHold();triggered='Hold & Win!'}
   }
   this.status=triggered||(credited?'Prémio: '+credited.toFixed(2):'Tenta novamente');
   return {ok:true,board,ev,stages,rawTotal,free,credited,potential,
     wildMultiplier:spinMultiplier,spinMultiplier,triggered,state:this.snapshot};
 }
}
export const money=v=>Number(v).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2});
