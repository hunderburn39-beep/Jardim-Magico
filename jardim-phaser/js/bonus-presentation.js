/* Jardim Mágico Phaser V1.5 — apresentação pura dos bónus.
 * Não manipula a banca, apostas, símbolos nem gerador de resultados.
 * As moedas mostradas vêm SEMPRE do Hold 4×4 já calculado. */
export function holdLandingIndices(previous,next){
 if(!Array.isArray(next)||next.length!==16)
  throw Error('Grelha Hold inválida');
 return next.flatMap((value,index)=>
  Number(value)>0&&(!Array.isArray(previous)||Number(previous[index]||0)===0)?
   [index]:[]);
}
export function holdDisplay(hold){
 if(!hold||!Array.isArray(hold.grid)||hold.grid.length!==16)
  throw Error('Hold 4×4 inválido');
 const coins=hold.grid.filter(x=>Number(x)>0).length;
 const attempts=Math.max(0,Math.min(3,Math.trunc(Number(hold.tries)||0)));
 const total=Math.max(0,Number(hold.total)||0);
 return {coins,attempts,total,filled:coins===16,rounds:Math.max(0,Number(hold.rounds)||0)};
}
export function freeDisplay(snapshot){
 const remaining=Math.max(0,Math.trunc(Number(snapshot.freeSpins)||0));
 const total=Math.max(0,Number(snapshot.freeTotal)||0);
 const multiplier=Math.max(1,Math.min(5,Number(snapshot.freeMultiplier)||1));
 return {remaining,total,multiplier};
}
