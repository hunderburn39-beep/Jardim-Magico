/* V2.6: presentation only; no RNG, payouts, or ledger changes. */
const clampInt=(n,min,max)=>Math.max(min,Math.min(max,Math.trunc(Number(n)||0)));
export function freeProgress(state={}){
 return {
  remaining:clampInt(state.freeSpins,0,60),
  played:clampInt(state.freePlayed,0,60),
  total:Math.max(0,Number(state.freeTotal)||0),
  active:state.mode==='free'
 };
}
export function holdProgress(state={}){
 const grid=state.grid;
 if(!Array.isArray(grid)||grid.length!==16)throw Error('Grelha Hold 4×4 inválida');
 const filled=grid.filter(v=>Number(v)>0).length;
 const left=16-filled,tries=clampInt(state.tries,0,3);
 return {
  filled,left,percentage:filled/16*100,
  missingLabel:left?'FALTAM '+left+' CASAS':'GRELHA COMPLETA',
  attemptsText:tries+' tentativa'+(tries===1?'':'s')+' restante'+(tries===1?'':'s'),
  progressText:filled+' de 16 casas preenchidas'
 };
}
