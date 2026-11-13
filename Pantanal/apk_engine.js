/* Pantanal APK 2.06 — regras reconstruidas e testáveis.
 * Fontes: MainActivity.Verifica/NumRoleta/NumDado, GeneratorNew.faixa/downDificult.
 * NÃO é uma versão certificada do gerador Android ou apta a dinheiro real.
 */
(function(root,factory){'use strict';
 const out=factory();
 if(typeof module==='object'&&module.exports)module.exports=out;
 if(root)root.PantanalApkEngine=out;
})(typeof globalThis==='undefined'?null:globalThis,function(){'use strict';
 const LINES=[
 [1,1,1,1,1],[0,0,0,0,0],[2,2,2,2,2],[0,1,2,1,0],[2,1,0,1,2],
 [0,0,1,2,2],[2,2,1,0,0],[1,2,2,2,1],[1,0,0,0,1],[1,0,1,2,1],
 [1,2,1,0,1],[2,1,0,0,0],[0,1,2,2,2],[0,0,0,1,2],[2,2,2,1,0],
 [0,0,0,0,1],[2,2,2,2,1],[1,0,0,0,0],[1,2,2,2,2],[2,1,1,1,0]
 ];
 const LEVELS=[1,5,9,15,25];
 const VERTICAL_PAYS=[100,70,50,20,0,20,300,250,200,0];
 const TRIPLE_ALL=[7,11,19,14,22,26,28,25,21,13];
 const TRIPLE_EDGE=[7,14,28];
 const FOUR_ANY=[15,29,30,23,27];
 const FOUR_LEFT_RIGHT=[15,29,30,23,27];
 const maskHas=(n,list)=>list.includes(n);
 const pop=(n)=>n.toString(2).replace(/0/g,'').length;
 const jackpotDefaults=()=>({min:500,med:700,max:1200});
 const jackpotCaps=()=>({min:1000,med:1200,max:2000});
 const names={0:'Caçador',1:'Jipe',2:'Rinoceronte',3:'Caçadores',4:'Chimpanzé',5:'Gorila',6:'Elefante',7:'Leão',8:'Cobra',9:'Girafa'};
 function valid(board){
   if(!Array.isArray(board)||board.length!==3||!board.every(r=>Array.isArray(r)&&r.length===5))
     throw new TypeError('Tabuleiro inválido (3×5)');
 }
 function resultBoard(board,lines,betPerLine){
   valid(board);
   if(!LEVELS.includes(lines))throw new RangeError('Linhas inválidas');
   if(!Number.isInteger(betPerLine)||betPerLine<1)throw new RangeError('Aposta por linha inválida');
   const wins=[],jackpots=[],bonus={strawberry:0,cauldron:0,wheel:0,dice:0},diagnostics=[];
   let total=0;
   for(let index=0;index<Math.min(20,lines);index++){
     const path=LINES[index],values=path.map((row,col)=>board[row][col]),line=index+1;
     for(let symbol=0;symbol<=9;symbol++){
       let mask=0;
       for(let c=0;c<5;c++)if(values[c]===symbol)mask|=1<<c;
       const n=pop(mask);
       if(n<3)continue;
       let multiple=0,feature='',jackpot='';
       switch(symbol){
         case 0:
           if(n===5)multiple=200;
           else if(n===4)multiple=50;
           else if(maskHas(mask,TRIPLE_ALL))multiple=10;
           break;
         case 1:
           if(n===5)multiple=150;
           else if(n===4)multiple=100;
           else if(maskHas(mask,TRIPLE_EDGE))multiple=7;
           break;
         case 2:
           if(n===5)multiple=75;
           else if(n===4)multiple=30;
           else if(mask===14)multiple=2;
           else if(mask===7||mask===28)multiple=5;
           break;
         case 3:
           if(n===5)multiple=30;
           else if(n===4)multiple=2;
           break;
         case 4:
           if(n===5)multiple=2000;
           else if(n===4)feature='strawberry:2';
           else if(maskHas(mask,TRIPLE_EDGE))feature='strawberry:1';
           break;
         case 5:
           if(n===5)multiple=30;
           else if(n===4)multiple=7;
           else if(mask===14)multiple=2;
           else if(mask===7||mask===28)multiple=5;
           break;
         case 6:
           if(n===5)jackpot='max';
           else if(n===4)jackpot='min';
           else if(maskHas(mask,TRIPLE_ALL))multiple=200;
           break;
         case 7:
           if(n===5)jackpot='med';
           else if(n===4)multiple=1000;
           else if(maskHas(mask,TRIPLE_EDGE))multiple=500;
           else if(maskHas(mask,[11,19,22,26,25,21,13]))multiple=200;
           break;
         case 8:
           if(n===5)multiple=2000;
           else if(n===4)multiple=500;
           else if(mask===7||mask===28)multiple=250;
           break;
         case 9:
           if(n===5)feature='cauldron:3';
           else if(mask===15||mask===30)feature='cauldron:2';
           else if(mask===23||mask===29||mask===7||mask===28)feature='cauldron:1';
           break;
       }
       if(!multiple&&!feature&&!jackpot)continue;
       const cells=values.flatMap((v,c)=>v===symbol?[[c,path[c]]]:[]);
       const amount=multiple*betPerLine;
       const win={line,symbol,path:path.slice(),mask,count:n,cells,amount,vertical:false};
       if(feature){const [key,units]=feature.split(':');bonus[key]+=Number(units);win.bonus=key;}
       if(jackpot){jackpots.push({type:jackpot,line,symbol,mask});win.jackpot=jackpot;}
       wins.push(win);total+=amount;
     }
   }
   if(lines===25){
     for(let col=0;col<5;col++){
       const symbol=board[0][col];
       if(!Number.isInteger(symbol)||symbol<0||symbol>9)continue;
       if(board[1][col]!==symbol||board[2][col]!==symbol)continue;
       const multiple=VERTICAL_PAYS[symbol],amount=multiple*betPerLine;
       const line=21+col;
       const win={line,symbol,count:3,mask:7,vertical:true,column:col,path:[0,1,2],cells:[[col,0],[col,1],[col,2]],amount};
       if(symbol===1){bonus.wheel++;win.bonus='wheel';}
       if(symbol===2){bonus.dice++;win.bonus='dice';}
       if(symbol===4){bonus.strawberry++;win.bonus='strawberry';}
       if(symbol===9){bonus.cauldron++;win.bonus='cauldron';}
       wins.push(win);total+=amount;
     }
   }
   return {wins,total,bonus,jackpots,diagnostics};
 }
 const betTotal=(perLine,lines)=>{if(!LEVELS.includes(lines))throw new RangeError('Linhas');return perLine*lines;};
 // Reconstrução literal de GeneratorNew.downDificult():
 // amostra aleatória de 1 a 10, idade das jogadas, saldo e bloqueio.
 const LIMITS={5:50000,10:100000,15:150000,20:200000,30:300000};
 function difficulty(credit,spins,blocked=50,rng=Math.random){
   const limit=LIMITS[blocked]||42500;
   const lucky=1+Math.floor(rng()*10);
   const active=(credit<limit&&spins<500)||(lucky===4&&spins<239&&blocked===50);
   return {active,limit,lucky,nextSpins:spins+1,blocked};
 }
 // "faixa": cinco amostras de três símbolos, usando as 10 fronteiras por coluna;
 // reproduz o ramo de Generate para gerar o tabuleiro base e mantém a peça anterior
 // quando o sorteio alcança um limiar que não tem atribuição.
 function faixa(draw,bounds,previous){
   const [a,b,c,d,e,f,g,h,i,j]=bounds;
   if(draw<a)return 6;
   if(draw<b)return 9;
   if(draw<c)return 0;
   if(draw<d)return 1;
   if(draw<e)return 2;
   if(draw<f)return 5;
   if(draw<g)return 3;
   if(draw<h)return 8;
   if(draw<i)return 7;
   if(draw<j)return 4;
   return previous;
 }
 const THRESHOLDS=[
   {range:84, normal:[2,10,18,29,42,57,74,77,80,85],paid:[2,10,18,29,42,57,74,77,80,85]},
   {range:85, normal:[3,3,8,12,20,32,51,76,78,84],paid:[3,3,11,19,30,43,58,75,78,81,86]},
   {range:86, normal:[4,9,13,21,33,52,77,79,81,85],paid:[4,12,20,31,44,59,76,79,82,87]},
   {range:85, normal:[3,8,12,20,32,51,74,78,80,84],paid:[3,11,19,30,43,58,75,78,81,86]},
   {range:84, normal:[2,7,11,19,31,50,75,77,79,83],paid:[2,10,18,29,42,57,74,77,80,85]}
 ];
 // Segunda coluna: os 11 números originais do Java incluem offset; para faixa
 // removemos o primeiro parâmetro de posição quando aplicável.
 const REAL_THRESHOLDS=[
   {range:84,normal:[2,10,18,29,42,57,74,77,80,85],paid:[2,10,18,29,42,57,74,77,80,85]},
   {range:85,normal:[3,8,12,20,32,51,76,78,80,84],paid:[3,11,19,30,43,58,75,78,81,86]},
   {range:86,normal:[4,9,13,21,33,52,77,79,81,85],paid:[4,12,20,31,44,59,76,79,82,87]},
   {range:85,normal:[3,8,12,20,32,51,74,78,80,84],paid:[3,11,19,30,43,58,75,78,81,86]},
   {range:84,normal:[2,7,11,19,31,50,75,77,79,83],paid:[2,10,18,29,42,57,74,77,80,85]}
 ];
 function generateBoard(prev,mode=false,rng=Math.random){
   const start=[[0,3,6,9,2],[1,4,7,0,3],[2,5,8,1,4]];
   const out=Array.from({length:3},(_,r)=>Array.from({length:5},(_,c)=>prev?.[r]?.[c]??start[r][c]));
   for(let col=0;col<5;col++){
     const rowset=REAL_THRESHOLDS[col],bounds=mode?rowset.paid:rowset.normal;
     for(let row=0;row<3;row++){
       const draw=Math.floor(rng()*rowset.range);
       out[row][col]=faixa(draw,bounds,out[row][col]);
     }
   }
   return out;
 }
 function diceRoll(rng=Math.random,mode=false){
   const n=mode?100:80,k=Math.floor(rng()*n);
   if(k<0||k>=n)throw new RangeError('sorteio dados inválido');
   return {draw:k,multiplier:k<20?1:k<40?2:k<60?3:k<80?4:k<90?5:6};
 }
 function wheelRoll(rng=Math.random,mode=false,currentPrize=0){
   const n=mode&&currentPrize<=10000?100:60,p=Math.floor(rng()*n);
   if(p<0||p>=n)throw new RangeError('sorteio roleta inválido');
   const b=p<60?[0,6]:p<70?[7,12]:p<80?[13,17]:p<90?[18,21]:[22,23];
   const sector=b[0]+Math.floor(rng()*(b[1]-b[0]+1));
   return {draw:p,sector,multiplier:sector+1,displaySector:sector+1,offsetDeg:-15*sector};
 }
 function shuffled(values,rng=Math.random){
   const a=values.slice();
   for(let i=a.length-1;i>0;i--){const j=Math.floor(rng()*(i+1));[a[i],a[j]]=[a[j],a[i]];}
   return a;
 }
 const strawberryDeck=(rng=Math.random)=>shuffled([0,0,1,1,2,2,3,3,4,4,5,6],rng);
 const strawberryPay=(category,betPerLine,triggerCount)=>[2,5,10,20,50,100,0][category]*betPerLine*triggerCount;
 const cauldronDeck=(rng=Math.random)=>shuffled([0,1,2,3,8],rng);
 const cauldronPay=(category,betPerLine,triggers)=>[50,75,100,150][category]*betPerLine*triggers;
 const pumpkinDeck=(rng=Math.random)=>shuffled([3,4,5,6,7],rng);
 const pumpkinPay=(category,betPerLine,triggers)=>[0,0,0,150,200,250,500,1000][category]*betPerLine*triggers;
 function defaultJackpots(){return {min:500,med:700,max:1200};}
 function advanceJackpots(current,stakePaid,paid,percentage=5,caps={min:1000,med:1200,max:2000}){
   const out={...defaultJackpots(),...(current||{})};
   const margin=Math.max(0,stakePaid-paid);
   const increment=margin/1000*(percentage/100);
   for(const k of ['min','med','max'])out[k]=Math.min(caps[k],out[k]+increment);
   return out;
 }
 function awardJackpot(current,type){
   if(!['min','med','max'].includes(type))throw new RangeError('tipo de jackpot');
   const amount=Math.floor((current||defaultJackpots())[type]*100);
   return {amount,reset:defaultJackpots()[type]};
 }
 return Object.freeze({LINES,LEVELS,VERTICAL_PAYS,TRIPLE_ALL,TRIPLE_EDGE,FOUR_ANY,
  betTotal,resultBoard,difficulty,faixa,generateBoard,diceRoll,wheelRoll,shuffled,
  strawberryDeck,strawberryPay,cauldronDeck,cauldronPay,pumpkinDeck,pumpkinPay,
  jackpotDefaults,jackpotCaps,defaultJackpots,advanceJackpots,awardJackpot,names});
});
