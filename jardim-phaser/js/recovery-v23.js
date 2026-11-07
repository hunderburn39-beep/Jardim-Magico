/* Jardim Mágico Phaser V2.3: recibos exclusivamente visuais, sem novos pagamentos.
 * O estado financeiro fica na transação já concluída pela DemoDatabase.
 */
const round=v=>Math.round((Number(v)||0)*100)/100;
const safeAmount=v=>Math.max(0,Number.isFinite(Number(v))?round(v):0);
const validMode=m=>['base','free','hold'].includes(m)?m:'base';
const token=kind=>kind+'-'+Date.now()+'-'+Math.random().toString(36).slice(2);
export function spinRecoveryReceipt(result,state,stages=1){
 return {
  id:token('spin'),date:new Date().toISOString(),kind:'spin',
  paid:safeAmount(result?.credited),wasFree:!!result?.free,
  triggered:String(result?.triggered||'').slice(0,96),
  stages:Math.max(1,Math.min(999,Math.trunc(Number(stages)||1))),
  modeAfter:validMode(state?.mode),
  remaining:Math.max(0,Math.min(60,Math.trunc(Number(state?.freeSpins)||0))),
  accrued:safeAmount(state?.freeTotal),
  multiplier:Math.max(1,Math.min(5,Math.trunc(Number(state?.freeMultiplier)||1)))
 };
}
export function holdRecoveryReceipt(result,state){
 const grid=Array.isArray(result?.hold?.grid)&&result.hold.grid.length===16?
  result.hold.grid.map(x=>Math.max(0,Number(x)||0)):Array(16).fill(0);
 return {
  id:token('hold'),date:new Date().toISOString(),kind:'hold',
  paid:safeAmount(result?.prize),done:!!result?.done,
  modeAfter:validMode(state?.mode),grid,
  attempts:Math.max(0,Math.min(3,Math.trunc(Number(result?.hold?.tries)||0))),
  rounds:Math.max(0,Math.trunc(Number(result?.hold?.rounds)||0)),
  total:safeAmount(result?.hold?.total)
 };
}
export function recoveryMessage(marker,state){
 const mode=validMode(state?.mode);
 if(mode==='hold'&&Array.isArray(state?.hold?.grid)){
  const coins=state.hold.grid.filter(v=>Number(v)>0).length;
  return {kind:'hold-active',title:'HOLD & WIN RECUPERADO',
   detail:coins+' moedas na grelha · '+Math.max(0,state.hold.tries||0)+
    ' tentativas restantes. As moedas já guardadas não são sorteadas novamente.'};
 }
 if(mode==='free'){
  const remaining=Math.max(0,Math.trunc(Number(state?.freeSpins)||0));
  const total=safeAmount(state?.freeTotal);
  const multi=Math.max(1,Math.min(5,Number(state?.freeMultiplier)||1));
  return {kind:'free-active',title:'FREE SPINS RECUPERADAS',
   detail:remaining+' giros por jogar · acumulado '+total.toFixed(2)+
    ' ×'+multi+'. Continua do saldo já guardado, sem repetir o giro.'};
 }
 if(marker?.kind==='hold'&&marker.done){
  return {kind:'hold-complete',title:'HOLD & WIN CONCLUÍDO',
   detail:'Prémio já creditado: '+safeAmount(marker.paid).toFixed(2)+
    ' créditos fictícios. A grelha e o valor foram recuperados, sem novo pagamento.'};
 }
 if(marker?.kind==='spin'&&marker.wasFree){
  return {kind:'free-complete',title:'FREE SPINS CONCLUÍDAS',
   detail:'O resultado final já foi contabilizado: '+safeAmount(marker.paid).toFixed(2)+
    ' créditos fictícios. Não é feito novo pagamento.'};
 }
 return {kind:'spin-complete',title:'GIRO RECUPERADO',
  detail:'O resultado e o saldo já estavam guardados; não foi cobrado nem pago novamente.'};
}
export function createDiagnostics(maxEvents=70){
 const events=[],counts=Object.create(null);
 const cap=Math.max(10,Math.min(200,Math.trunc(maxEvents)||70));
 const allowed=new Set(['boot','writer-granted','writer-denied','save-failed',
  'save-ok','checkpoint-failed','checkpoint-ok','spin-visual-error',
  'hold-visual-error','recovered-free','recovered-hold','recovered-spin',
  'storage-error','visual-timeout','page-background','writer-release']);
 return Object.freeze({
  record(type){
   if(!allowed.has(type))return false;
   counts[type]=(counts[type]||0)+1;
   events.push({type,at:new Date().toISOString()});
   if(events.length>cap)events.shift();
   return true;
  },
  snapshot(){
   return {format:'jardim-phaser-diagnostics-v23',exportedAt:new Date().toISOString(),
    counts:{...counts},events:events.map(x=>({...x}))};
  }
 });
}
