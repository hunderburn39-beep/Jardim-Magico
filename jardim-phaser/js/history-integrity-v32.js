/* Phaser V3.2 — auditoria informativa, nunca altera prémios ou dados. */
const amount=n=>typeof n==='number'&&Number.isFinite(n)?n:null;
export function auditSessionHistory(state,events){
 const s=state?.snapshot;
 if(!s)return {status:'empty',label:'Sem sessão para conferir',checked:0,linked:false};
 if(!Array.isArray(events)||!events.length)return {
  status:'unknown',label:'Histórico vazio — sem movimentos para comparar',checked:0,linked:false
 };
 const records=events.filter(e=>e&&typeof e==='object').slice()
  .sort((a,b)=>String(b.timestamp||'').localeCompare(String(a.timestamp||''))||
   String(b.eventId||'').localeCompare(String(a.eventId||'')));
 const ids=new Set();let duplicates=0;
 for(const e of records){
  if(typeof e.eventId!=='string'||ids.has(e.eventId))duplicates++;
  else ids.add(e.eventId);
 }
 const latest=records.find(e=>amount(e.balance)!==null&&amount(e.reserve)!==null);
 const balanceMatches=latest&&amount(s.balance)!==null&&amount(s.reserve)!==null&&
  Math.abs(s.balance-latest.balance)<.005&&
  Math.abs(s.reserve-latest.reserve)<.005;
 const marker=s.pendingVisual;
 const linked=!!(marker?.id&&records.some(e=>e.recoveryId===marker.id));
 const legacy=!!(marker?.id&&!linked&&
  !records.some(e=>typeof e.recoveryId==='string')&&
  records.some(e=>['spin','hold-turn','hold-paid'].includes(e.type)));
 if(duplicates||!balanceMatches||marker?.id&&!linked&&!legacy)return {
  status:'review',label:'Conferir sessão e histórico local',checked:records.length,linked
 };
 return {
  status:legacy?'legacy':'ok',
  label:legacy?'Cópia antiga: recibo sem ligação no histórico':'Saldo e histórico coerentes',
  checked:records.length,linked
 };
}
