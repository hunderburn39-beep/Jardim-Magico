/* Jardim Mágico 3D — simulação independente de créditos virtuais.
   Não usa euros reais e não representa RTP auditado ou jackpot garantido.
   Matriz [coluna][linha], 5×3. 10 linhas da demo. */
export const SYMBOLS=['potion','green','heart','teapot','wild','gem','mushroom','hat','rose','scatter','stone','coin'];
export const LABELS=['Poção','Esmeralda','Coração','Bule','WILD','Cristal','Cogumelo','Chapéu','Rosa','SCATTER','Pedra','Moeda'];
export const WEIGHTS=[10,13,11,10,4,11,10,9,10,5,12,7];
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
 let raw=0,lines=0,wilds=0;
 for(const path of PAYLINES){
   const chain=path.map((row,c)=>board[c][row]);
   if(chain[0]===WILD&&chain[1]===WILD){raw+=bet*.55;lines++}
   let first=chain.find(s=>s!==WILD);
   if(first===undefined)first=WILD;
   if([SCATTER,STONE,COIN].includes(first))continue;
   let n=0,wm=0;
   for(const s of chain){
     if(s!==first && s!==WILD)break;
     n++;if(s===WILD)wm++;
   }
   if(n>=3){
     const pay=[0,0,0,.65,2.1,6.6][n]*([3,4,8,5,10,7,4,4,7,0,0,0][first]||1);
     raw+=bet*pay*Math.min(5,1+wm);
     lines++;wilds+=wm;
   }
 }
 return {raw:round(raw),lines,wilds,scatter:count(board,SCATTER),coins:count(board,COIN)};
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
   if(!free){this.balance=round(this.balance-this.stake);this.reserve=round(this.reserve+this.stake*.48)}
   this.spins++;
   const board=newBoard(rng,free);
   const ev=evaluate(board,this.stake);
   const wildMultiplier=clamp(1+Math.floor(rng()*5),1,5);
   const potential=round(ev.raw*(ev.wilds?wildMultiplier:1));
   let credited=0;
   let triggered='';
   if(free){
     this.freeTotal=round(this.freeTotal+potential);
     this.freeMultiplier=clamp(this.freeMultiplier+(ev.wilds?wildMultiplier-1:0),1,80);
     this.freeSpins--;
     this.freePlayed++;
     const space=Math.max(0,60-this.freePlayed-this.freeSpins);
     if(ev.scatter>=3&&space>0){
       const n=Math.min(15,space);this.freeSpins+=n;triggered='Reativação: +'+n+' Free Spins';
     }else if(ev.scatter>=2&&space>0){
       const n=Math.min(8,space);this.freeSpins+=n;triggered='Reativação: +'+n+' Free Spins';
     }
     if(this.freeSpins===0){
       const total=round(this.freeTotal*this.freeMultiplier);
       credited=Math.min(this.reserve,total);
       this.balance=round(this.balance+credited);
       this.reserve=round(this.reserve-credited);
       this.lastWin=credited;
       this.mode='base';
       triggered=triggered||'Fim das Free Spins';
       this.freeAwarded=credited;
     }
   }else{
     credited=Math.min(this.reserve,potential);
     this.reserve=round(this.reserve-credited);
     this.balance=round(this.balance+credited);
     this.lastWin=credited;
     if(ev.scatter>=3){this.addFree(15);triggered='15 Free Spins!'}
     else if(ev.coins>=6){this.startHold();triggered='Hold & Win!'}
   }
   this.status=triggered||(credited?'Prémio: '+credited.toFixed(2):'Tenta novamente');
   return {ok:true,board,ev,free,credited,potential,wildMultiplier,triggered,state:this.snapshot};
 }
}
export const money=v=>Number(v).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2});
