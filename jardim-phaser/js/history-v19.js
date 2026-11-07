/* Jardim Mágico V1.9 — histórico organizado e pesquisa local.
 * Esta interface consulta apenas a base local de créditos de demonstração.
 */
export const HISTORY_PAGE_SIZE=20;
export function filterHistory(events,options={}){
 const query=String(options.query||'').toLocaleLowerCase('pt-PT').trim();
 const category=options.category||'all';
 const dateFrom=options.dateFrom||'';
 const dateTo=options.dateTo||'';
 return events.filter(event=>{
  const type=String(event.type||'');
  if(category==='spins'&&type!=='spin')return false;
  if(category==='bonus'&&!['buy-free','buy-hold','hold-paid','hold-turn','test-free','test-hold'].includes(type))return false;
  if(category==='fund'&&!['fund-deposit','fund-withdraw','fund-plus','credit-demo'].includes(type))return false;
  if(category==='admin'&&!['admin-share','admin-access'].includes(type))return false;
  const day=String(event.timestamp||'').slice(0,10);
  if(dateFrom&&day<dateFrom)return false;
  if(dateTo&&day>dateTo)return false;
  if(query){
   const content=[type,event.note,event.timestamp,event.eventId,
    event.wager,event.cost,event.paid,event.amount].join(' ').toLocaleLowerCase('pt-PT');
   if(!content.includes(query))return false;
  }
  return true;
 });
}
export function historyPage(events,page=0,perPage=HISTORY_PAGE_SIZE){
 const pages=Math.max(1,Math.ceil(events.length/perPage));
 const index=Math.min(Math.max(0,Math.floor(page)),pages-1);
 return {items:events.slice(index*perPage,(index+1)*perPage),page:index,pages,total:events.length};
}
