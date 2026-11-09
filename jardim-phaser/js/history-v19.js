/* Jardim Mágico V1.9 — histórico organizado e pesquisa local.
 * Esta interface consulta apenas a base local de créditos de demonstração.
 */
export const HISTORY_PAGE_SIZE=20;
const EVENT_LABELS=Object.freeze({
 spin:'Giro',stake:'Alteração de aposta','credit-demo':'Recarga de saldo',
 'buy-free':'Compra de Free Spins','buy-hold':'Compra de Hold & Win',
 'hold-paid':'Prémio Hold & Win','hold-turn':'Tentativa Hold & Win',
 'test-free':'Free Spins gratuitas','test-hold':'Hold & Win gratuito',
 'fund-deposit':'Entrada de fundo','fund-withdraw':'Retirada de fundo',
 'fund-plus':'Entrada de fundo','player-credit':'Adicionar créditos ao jogador',
 'player-debit':'Retirar créditos do jogador','admin-share':'Alteração de percentagens',
 'admin-access':'Acesso à administração','admin-profile':'Alteração de modo',
 'admin-pin-change':'Alteração do PIN local'
});
export function eventLabel(type){return EVENT_LABELS[type]||'Movimento local'}
export function filterHistory(events,options={}){
 const query=String(options.query||'').toLocaleLowerCase('pt-PT').trim();
 const category=options.category||'all';
 const dateFrom=options.dateFrom||'';
 const dateTo=options.dateTo||'';
 return events.filter(event=>{
  const type=String(event.type||'');
  if(category==='spins'&&type!=='spin')return false;
  if(category==='bonus'&&!['buy-free','buy-hold','hold-paid','hold-turn','test-free','test-hold'].includes(type))return false;
  if(category==='fund'&&!['fund-deposit','fund-withdraw','fund-plus','credit-demo','player-credit','player-debit'].includes(type))return false;
  if(category==='admin'&&!['admin-share','admin-access','admin-profile','admin-pin-change'].includes(type))return false;
  const day=String(event.timestamp||'').slice(0,10);
  if(dateFrom&&day<dateFrom)return false;
  if(dateTo&&day>dateTo)return false;
  if(query){
   const content=[type,eventLabel(type),event.note,event.timestamp,event.eventId,
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
