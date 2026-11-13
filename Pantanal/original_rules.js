/* Pantanal — primeira fase de porting de regras verificadas no APK 2.06.
 * Fontes: GeneratorNew.Generate/Calcpre1 e MainActivity.Verifica/NumDado.
 * O algoritmo horizontal completo, os acumulados e os restantes bónus
 * ainda NÃO estão portados nem certificados.
 */
(function(root,factory){
  const rules=factory();
  if(typeof module==='object'&&module.exports)module.exports=rules;
  if(root)root.PantanalOriginalRules=rules;
})(typeof globalThis!=='undefined'?globalThis:null,function(){
  'use strict';
  const LINES20=Object.freeze([
    [1,1,1,1,1],[0,0,0,0,0],[2,2,2,2,2],[0,1,2,1,0],[2,1,0,1,2],
    [0,0,1,2,2],[2,2,1,0,0],[1,2,2,2,1],[1,0,0,0,1],[1,0,1,2,1],
    [1,2,1,0,1],[2,1,0,0,0],[0,1,2,2,2],[0,0,0,1,2],[2,2,2,1,0],
    [0,0,0,0,1],[2,2,2,2,1],[1,0,0,0,0],[1,2,2,2,2],[2,1,1,1,0]
  ]);
  // A tabela original usa símbolos numerados de 0 a 9 na slotTileValues.
  // Índices 1 e 2: três na vertical chamam a roleta e os dados.
  // Índices 4 e 9: acionam outros bónus, não recompensas diretas.
  const VERTICAL_PAYS=Object.freeze([100,70,50,20,0,20,300,250,200,0]);
  const LEVELS=Object.freeze([1,5,9,15,25]);
  function assertBoard(board){
    if(!Array.isArray(board)||board.length!==3||!board.every(row=>Array.isArray(row)&&row.length===5))
      throw new TypeError('O tabuleiro tem de ter 3 filas por 5 tambores');
  }
  function stake(betPerLine,lines){
    if(!Number.isInteger(betPerLine)||betPerLine<1||!Number.isInteger(lines)||lines<1||lines>25)throw new RangeError('aposta/linhas inválidas');
    return betPerLine*lines;
  }
  function verticalResults(board,lines,betPerLine){
    assertBoard(board);
    const wins=[],bonus={wheel:0,dice:0,strawberry:0,cauldron:0},totalLines=Number(lines);
    if(totalLines!==25)return {wins,total:0,bonus};
    let total=0;
    for(let col=0;col<5;col++){
      const symbol=board[0][col];
      if(!Number.isInteger(symbol)||symbol<0||symbol>9)continue;
      if(board[1][col]!==symbol||board[2][col]!==symbol)continue;
      const line=col+21,amount=VERTICAL_PAYS[symbol]*betPerLine;
      if(symbol===1)bonus.wheel++;
      if(symbol===2)bonus.dice++;
      if(symbol===4)bonus.strawberry++;
      if(symbol===9)bonus.cauldron++;
      wins.push({line,amount,symbol,count:3,vertical:true,column:col,path:[0,1,2]});
      total+=amount;
    }
    return {wins,total,bonus};
  }
  // MainActivity.NumDado: nextInt(80) sem modo de pagamento,
  // nextInt(100) quando downDificult() é verdadeiro. Multiplica o
  // prémio ATUAL (não a aposta), e substitui-o pelo valor multiplicado.
  function diceRoll(rng=Math.random,paymentMode=false){
    const n=paymentMode?100:80,draw=Math.floor(rng()*n);
    if(!Number.isInteger(draw)||draw<0||draw>=n)throw new RangeError('RNG fora do intervalo');
    const multi=draw<20?1:draw<40?2:draw<60?3:draw<80?4:draw<90?5:6;
    return {draw,multiplier:multi};
  }
  function dicePrize(currentPrize,roll){
    if(!Number.isFinite(currentPrize)||currentPrize<0)throw new RangeError('prémio inválido');
    const m=typeof roll==='number'?roll:roll.multiplier;
    if(!Number.isInteger(m)||m<1||m>6)throw new RangeError('multiplicador inválido');
    return currentPrize*m;
  }
  return Object.freeze({LINES20,VERTICAL_PAYS,LEVELS,stake,verticalResults,diceRoll,dicePrize});
});
