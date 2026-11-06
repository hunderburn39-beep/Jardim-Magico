/* Jardim Mágico 3D — simulação independente de créditos virtuais.
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
export function count(board,id){return board.flat().filter(x=>x===id).length}
export function evaluate(board,bet){
 // Uma combinação paga por linha; quaisquer várias linhas somam sempre.
 // Cada combinação inclui as posições exatas que participam na vitória.
 // Um WILD partilhado por duas linhas pode contar nas DUAS linhas,
 // mas será animado uma única vez pelo renderizador 3D.
 const lineWins=[];
 let wilds=0;
 for(let index=0;index<PAYLINES.length;index++){
   const path=PAYLINES[index];
   const chain=path.map((row,c)=>board[c][row]);
   const pairWild=chain[0]===WILD&&chain[1]===WILD;
   const first=chain.find(s=>s!==WILD)??WILD;
   let amount=pairWild?bet*.55:0;
   let matched=pairWild?2:0;
   let wm=0;
   if(![SCATTER,STONE,COIN].includes(first)){
     let n=0;
     for(const symbol of chain){
       if(symbol!==first&&symbol!==WILD)break;
       n++;if(symbol===WILD)wm++;
     }
     if(n>=3){
       const pay=[0,0,0,.07,.25,.8][n]*([3,4,8,5,10,7,4,4,7,0,0,0][first]||1);
       amount+=bet*pay*Math.min(5,1+wm);
       matched=n;
       wilds+=wm;
     }
   }
   if(amount>0){
     lineWins.push({
       line:index+1,
       symbol:first,
       count:matched,
       amount:round(amount),
       cells:path.slice(0,matched).map((r,c)=>({c,r}))
     });
   }
 }
 // A soma visível de cada combinação tem de ser igual ao prémio das linhas.
 const raw=round(lineWins.reduce((sum,win)=>sum+win.amount,0));
 return {
   raw,
   lines:lineWins.length,
   lineWins,
   wilds,
   scatter:count(board,SCATTER),
   coins:count(board,COIN)
 };
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
 return board;
}
function safeCollapse(previous,removed,rng,free){
 // Repetir APENAS as novas peças do topo: manter sempre as sobreviventes.
 for(let attempt=0;attempt<32;attempt++){
   const next=collapseBoard(previous,removed,rng,free);
   if(evaluate(next,1).lines===0)return next;
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
 if(evaluate(next,1).lines!==0){
   throw new Error('Não foi possível gerar uma queda sem prémios');
 }
 return next;
}
export function cascadeStages(board,bet,rng=Math.random,free=false,policy={}){
 const reserve=policy.reserve===undefined?Infinity:Math.max(0,policy.reserve);
 const freeCarry=policy.freeTotal||0;
 const freeMultiplier=policy.freeMultiplier||1;
 const wildMultiplier=policy.wildMultiplier||1;
 const stages=[];
 let currentBoard=board,removed=[],raw=0,potential=0,wildCount=0,maxCoins=0;
 // O fundo NÃO decide a duração, apenas que novos símbolos são possíveis.
 const afford=(ev)=>{
   const nextRaw=round(raw+ev.raw);
   const nextPotential=round(potential+ev.raw*(ev.wilds?wildMultiplier:1));
   const nextWilds=wildCount+ev.wilds;
   const projectedMultiplier=clamp(freeMultiplier+
     (nextWilds>0?wildMultiplier-1:0),1,5);
   const eventualFreePrize=round((freeCarry+nextRaw)*projectedMultiplier);
   const fullPrize=free?eventualFreePrize:nextPotential;
   // Se cair um Hold, as seis moedas iniciais têm de ser integralmente
   // pagáveis depois de qualquer prémio da mesma jogada.
   const triggeringHold=!free&&Math.max(maxCoins,ev.coins)>=6;
   const minHold=triggeringHold?round(bet*6):0;
   return round(fullPrize+minHold)<=round(reserve);
 };
 for(;;){
   let ev=evaluate(currentBoard,bet);
   if(!afford(ev)){
     let accepted=false;
     for(let attempt=0;attempt<48;attempt++){
       const candidate=stages.length?
         collapseBoard(stages.at(-1).board,removed,rng,free):
         newBoard(rng,free);
       const candidateEv=evaluate(candidate,bet);
       if(afford(candidateEv)){
         currentBoard=candidate;ev=candidateEv;accepted=true;break;
       }
     }
     if(!accepted){
       currentBoard=stages.length?
         safeCollapse(stages.at(-1).board,removed,rng,free):
         safeOpening(rng,free);
       ev=evaluate(currentBoard,bet);
       if(!afford(ev))throw new Error('Grelha segura excedeu o cofre virtual');
     }
   }
   stages.push({board:currentBoard,ev,removed});
   raw=round(raw+ev.raw);
   potential=round(potential+ev.raw*(ev.wilds?wildMultiplier:1));
   wildCount+=ev.wilds;
   maxCoins=Math.max(maxCoins,ev.coins);
   const winners=winningCells(ev);
   if(!winners.length)break;
   currentBoard=collapseBoard(currentBoard,winners,rng,free);
   removed=winners;
 }
 return stages;
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
   this.hold=null;
   this.status='Pronto para rodar';
 }
 get snapshot(){return {balance:this.balance,reserve:this.reserve,stake:this.stake,spins:this.spins,lastWin:this.lastWin,freeSpins:this.freeSpins,freeTotal:this.freeTotal,freeMultiplier:this.freeMultiplier,mode:this.mode,hold:this.hold?{...this.hold,grid:[...this.hold.grid]}:null,status:this.status}}
 get bonusPrice(){return round(this.stake*20)}
 get holdPrice(){return round(this.stake*30)}
 setStake(value){if(this.mode!=='base')return false;this.stake=clamp(round(value),.05,10);return true}
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
   if(this.mode!=='base'||this.balance<this.holdPrice)return {ok:false,message:'Saldo virtual insuficiente'};
   this.balance=round(this.balance-this.holdPrice);
   this.reserve=round(this.reserve+this.holdPrice);
   this.startHold();
   return {ok:true,message:'Hold & Win de demonstração iniciado'};
 }
 startHold(){
   this.mode='hold';
   this.hold={grid:Array(16).fill(0),tries:3,rounds:0,total:0};
   // Seis moedas iniciais, sempre sem custo real.
   const positions=new Set();
   while(positions.size<6)positions.add(Math.floor(Math.random()*16));
   for(const p of positions)this.hold.grid[p]=[1,2,3,5,10][Math.floor(Math.random()*5)];
   this.hold.total=round(this.hold.grid.reduce((a,b)=>a+b,0)*this.stake);
   this.status='Hold & Win · 3 tentativas';
   return this.snapshot;
 }
 holdTurn(rng=Math.random){
   if(this.mode!=='hold')return {ok:false,message:'Hold & Win não ativo'};
   const h=this.hold;
   const empty=h.grid.flatMap((n,i)=>n?[]:[i]);
   if(empty.length&&rng()<.72){
     const countNew=Math.min(empty.length,rng()<.18?2:1);
     for(let j=0;j<countNew;j++){
       const pos=Math.floor(rng()*empty.length),i=empty.splice(pos,1)[0];
       h.grid[i]=[1,2,3,5,10,20][Math.floor(rng()*6)];
     }
     h.tries=3;
   }else h.tries--;
   h.rounds++;
   h.total=round(h.grid.reduce((a,b)=>a+b,0)*this.stake);
   const complete=h.tries<=0||h.grid.every(Boolean)||h.rounds>=28;
   if(!complete){
     this.status='Hold & Win · '+h.tries+' tentativas';
     return {ok:true,done:false,hold:{...h,grid:[...h.grid]}};
   }
   const potential=h.total+(h.grid.every(Boolean)?this.stake*250:0);
   const prize=Math.min(this.reserve,round(potential));
   this.reserve=round(this.reserve-prize);
   this.balance=round(this.balance+prize);
   this.lastWin=prize;
   this.mode='base';
   this.status='Hold & Win: '+prize.toFixed(2)+' créditos';
   const result={ok:true,done:true,prize,hold:{...h,grid:[...h.grid]}};
   this.hold=null;return result;
 }
 spin(rng=Math.random){
   if(!this.canSpin())return {ok:false,message:'Saldo virtual insuficiente ou bónus ativo'};
   const free=this.mode==='free';
   if(!free){
     this.balance=round(this.balance-this.stake);
     this.reserve=round(this.reserve+this.stake*.48);
   }
   this.spins++;
   const wildMultiplier=clamp(1+Math.floor(rng()*5),1,5);
   // Selecionar apenas grelhas/cascatas inteiramente pagáveis pelo fundo.
   // Não há "prémio cortado": todas as linhas que saírem são pagas.
   const stages=cascadeStages(newBoard(rng,free),this.stake,rng,free,{
     reserve:this.reserve,freeTotal:this.freeTotal,
     freeMultiplier:this.freeMultiplier,wildMultiplier
   });
   const board=stages[0].board;
   const ev=stages[0].ev;
   const rawTotal=round(stages.reduce((sum,stage)=>sum+stage.ev.raw,0));
   const totalWilds=stages.reduce((sum,stage)=>sum+stage.ev.wilds,0);
   const scatterCount=Math.max(...stages.map(stage=>stage.ev.scatter));
   const coinCount=Math.max(...stages.map(stage=>stage.ev.coins));
   const potential=round(stages.reduce((sum,stage)=>
     sum+stage.ev.raw*(stage.ev.wilds?wildMultiplier:1),0));
   let credited=0,triggered='';
   if(free){
     this.freeTotal=round(this.freeTotal+rawTotal);
     this.freeMultiplier=clamp(this.freeMultiplier+(totalWilds?wildMultiplier-1:0),1,5);
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
     wildMultiplier,triggered,state:this.snapshot};
 }
}
export const money=v=>Number(v).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2});
