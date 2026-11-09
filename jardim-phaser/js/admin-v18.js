/* Jardim Mágico Phaser V1.8: relatórios e contabilidade fictícia local. */
const round=v=>Math.round((v+Number.EPSILON)*100)/100;
const num=v=>{const n=Number(v);return Number.isFinite(n)?n:0};
export function readCredits(raw){
 const input=String(raw??'').trim();
 if(!/^\d+(?:[.,]\d{1,2})?$/.test(input))return null;
 const n=Number(input.replace(',','.'));
 return Number.isFinite(n)&&n>0&&n<=1e12?round(n):null;
}
export function readPercent(value){
 const input=String(value??'').trim().replace(',','.');
 const n=Number(input);
 return input&&Number.isFinite(n)&&n>=0&&n<=100?round(n):null;
}
export function normalizeAdmin(owner=50){
 const ownerPct=readPercent(owner)??50;
 return {ownerPct,merchantPct:round(100-ownerPct)};
}
export function validateFundChange(kind,raw,reserve,mode='base',running=false){
 if(running||mode!=='base')return {ok:false,message:'Termina primeiro o giro ou bónus.'};
 const amount=readCredits(raw);
 if(amount===null||
    !Number.isSafeInteger(Math.round(amount*100)))
  return {ok:false,message:'Indica um valor válido.'};
 if(!['deposit','withdraw'].includes(kind))return {ok:false,message:'Operação inválida.'};
 if(kind==='withdraw'&&amount>round(reserve))
  return {ok:false,message:'O cofre não tem esse valor disponível.'};
 const target=round(reserve+(kind==='deposit'?amount:-amount));
 if(target>1e12||!Number.isSafeInteger(Math.round(target*100)))
  return {ok:false,message:'Precisão numérica excedida.'};
 return {ok:true,amount,target};
}
export function summarizeLedger(events=[]){
 let net=0,owner=0,merchant=0,wagers=0,costs=0,paid=0,spins=0;
 for(const e of events){
  if(!['spin','buy-free','buy-hold','hold-paid'].includes(e.type))continue;
  const w=round(Math.max(0,num(e.wager)));
  const c=round(Math.max(0,num(e.cost)));
  const p=round(Math.max(0,num(e.paid)));
  const delta=round(w+c-p);
  const pct=readPercent(e.ownerPct)??50;
  const o=round(delta*pct/100);
  net=round(net+delta);owner=round(owner+o);merchant=round(merchant+round(delta-o));
  wagers=round(wagers+w);costs=round(costs+c);paid=round(paid+p);
  if(e.type==='spin')spins++;
 }
 return {net,owner,merchant,wagers,costs,paid,spins};
}
function escapeHtml(x){return String(x??'').replace(/[&<>"']/g,ch=>({
 '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'
})[ch])}
const money=v=>num(v).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2});
export function makePrintableReport({summary,history,settings,reserve,dbMode}){
 const rows=history.map(x=>
  '<tr><td>'+escapeHtml(String(x.timestamp||'').replace('T',' ').slice(0,19))+
  '</td><td>'+escapeHtml(x.type==='credit-demo'?'Recarga de saldo':x.type)+'</td><td>'+money(x.amount)+'</td><td>'+
  money(num(x.wager)+num(x.cost))+'</td><td>'+money(x.paid)+'</td><td>'+
  escapeHtml(x.ownerPct??'50')+'%</td></tr>').join('');
 return '<!doctype html><html lang="pt"><head><meta charset="utf-8">'+
 '<title>Jardim Mágico — Relatório de movimentos</title><style>'+
 'body{font:12px Arial,sans-serif;max-width:950px;margin:20px auto;color:#222}'+
 'h1{font-size:24px}h2{font-size:16px;margin-top:20px}'+
 'table{border-collapse:collapse;width:100%}th,td{border:1px solid #bbb;padding:6px;text-align:left}'+
 'th{background:#eee}tr{break-inside:avoid}.notice{padding:10px;background:#eee}'+
 '@page{size:A4;margin:12mm}@media print{body{margin:0}.noprint{display:none}}</style></head><body>'+
 '<h1>Jardim Mágico — Relatório de movimentos</h1><p>Gerado: '+
 escapeHtml(new Date().toLocaleString('pt-PT'))+' · Base: '+escapeHtml(dbMode)+'</p>'+
 '<p class="notice">Movimentos internos deste dispositivo. Este relatório não substitui documentação contabilística.</p>'+
 '<h2>Movimentos de jogo observados</h2>'+
 '<table><tr><th>Apostas</th><th>Compras de bónus</th><th>Prémios</th><th>Resultado líquido</th></tr>'+
 '<tr><td>'+money(summary.wagers)+'</td><td>'+money(summary.costs)+'</td><td>'+
 money(summary.paid)+'</td><td>'+money(summary.net)+'</td></tr></table>'+
 '<h2>Repartição por percentagens</h2><table><tr><th>Dono</th><th>Comerciante</th><th>Fundo atual</th><th>Percentagens atuais</th></tr>'+
 '<tr><td>'+money(summary.owner)+'</td><td>'+money(summary.merchant)+'</td><td>'+
 money(reserve)+'</td><td>'+settings.ownerPct+'% / '+settings.merchantPct+'%</td></tr></table>'+
 '<p>Percentagens históricas guardadas por evento; nos eventos anteriores à V1.8 assume-se 50/50.</p>'+
 '<h2>'+history.length+' movimentos guardados neste dispositivo</h2>'+
 '<table><thead><tr><th>Data</th><th>Tipo</th><th>Valor do fundo</th><th>Entradas</th><th>Prémios</th><th>Quota dono</th></tr></thead>'+
 '<tbody>'+rows+'</tbody></table>'+
 '<p class="noprint">Imprimir → Guardar como PDF. O histórico conserva os últimos 2500 movimentos. Exporta também o JSON para uma cópia da sessão.</p></body></html>';
}
