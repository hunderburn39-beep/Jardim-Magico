
"use strict";
// Apenas para o protótipo local. Num jogo online real, a autenticação e os saldos
// devem estar num servidor: este PIN e o localStorage não fornecem segurança.
(()=>{
const $=id=>document.getElementById(id);
const adminPin="12345";
let unlocked=false,previousY=null;
const modal=$("scratch-admin-modal"),panel=$("scratch-admin-panel");
function show(){
  endScratch();
  modal.hidden=false;modal.setAttribute("aria-hidden","false");
  $("scratch-admin-login").hidden=unlocked;
  $("scratch-admin-view").hidden=!unlocked;
  if(unlocked){draw();refreshShares()}else{$("scratch-admin-error").textContent="";$("scratch-admin-pin").value="";$("scratch-admin-pin").focus({preventScroll:true})}
}
function close(){modal.hidden=true;modal.setAttribute("aria-hidden","true");$("scratch-admin-open").focus({preventScroll:true})}
function draw(){
  if(!unlocked)return;
  drawCommissions();
  drawOfficialPlans();
  const report=profitReport();
  const target=$("scratch-admin-cards");target.replaceChildren();
  for(const mode of CARD_MODES){
    const p=state.pools[String(mode.mult)];
    const card=document.createElement("article");
    card.className="scratch-admin-card";
    const title=document.createElement("h3");title.textContent=mode.mult+"X · "+money(mode.bet);
    const balance=document.createElement("strong");
    balance.textContent="Lucro: "+formatSignedEuro(p.stakes-p.paid);
    const detail=document.createElement("dl");
    const stock=state.officialStock?.[String(mode.mult)];
    const remaining=stock?.remaining?Object.values(stock.remaining)
      .reduce((total,count)=>total+(Number.isSafeInteger(count)&&count>0?count:0),0):0;
    const formatted=n=>n.toLocaleString("pt-PT");
    const rows=[
      ["Tiragem vendida",formatted(stock?.issued||0)],
      ["Bilhetes restantes",formatted(remaining)],
      ["Vendas",eur(p.stakes)],
      ["Prémios pagos",eur(p.paid)],
      ["Prémios pendentes",eur(Math.max(0,p.awarded-p.paid))],
      ["Lucro da tiragem",formatSignedEuro(p.stakes-p.paid)],
      ["Fundo auxiliar (não usado)",eur(p.available)]
    ];
    for(const [name,value] of rows){
      const dt=document.createElement("dt"),dd=document.createElement("dd");
      dt.textContent=name;dd.textContent=value;detail.append(dt,dd);
    }
    const resetButton=document.createElement("button");
    resetButton.type="button";
    resetButton.className="scratch-admin-reset-mode";
    resetButton.textContent="♻ NOVA TIRAGEM "+mode.mult+"X · REPOR CONTADORES";
    resetButton.setAttribute("aria-label","Reiniciar a tiragem "+mode.mult+"X e os respetivos contadores");
    resetButton.addEventListener("click",()=>confirmModeReset(mode.mult));
    card.append(title,balance,detail,resetButton);target.append(card);
  }
  $("scratch-admin-total").textContent=formatSignedEuro(report.profit);
  $("scratch-admin-legacy").textContent=
    "O lucro total inclui as vendas e os prémios pagos de todas as séries anteriores (mesmo após reiniciar uma raspadinha). "+
    "Os fundos auxiliares não alteram os prémios nem a divisão do lucro."+
    (state.prizeBank>0?" Fundo antigo guardado como histórico: "+eur(state.prizeBank)+".":"");
}

function formatSignedEuro(cents){return cents<0?"−"+eur(-cents):eur(cents)}
function confirmModeReset(mult){
  if(!unlocked)return;
  const mode=CARD_MODES.find(m=>m.mult===Number(mult));
  if(!mode)return;
  const ok=window.confirm(
    "Iniciar nova tiragem "+mult+"X?\n\n"+
    "São apagados o bilhete em curso, a tiragem atual e os contadores "+
    "desta raspadinha. A nova série começa com zero bilhetes vendidos.\n\n"+
    "O saldo e o histórico global de lucro/levantamentos continuam intactos.");
  if(!ok)return;
  const success=window.resetScratchMode(mult);
  if(!success){$("scratch-reset-note").textContent="Não foi possível reiniciar durante a revelação das casas.";return}
  $("scratch-reset-note").textContent="Nova tiragem "+mult+
    "X iniciada, com os contadores desta raspadinha a zero.";
  draw();
}
function confirmAllReset(){
  if(!unlocked)return;
  const ok=window.confirm(
    "REPOSIÇÃO TOTAL: apagar todos os contadores e iniciar quatro tiragens novas?\n\n"+
    "Isto apaga o saldo, os bilhetes, as tiragens, as vendas, os prémios, "+
    "o lucro, os levantamentos e os históricos deste navegador. Esta operação não pode ser anulada.");
  if(!ok)return;
  const success=window.resetScratchAll();
  if(!success){$("scratch-reset-note").textContent="Conclui primeiro a revelação das casas.";return}
  $("scratch-reset-note").textContent=
    "Reposição total realizada. Tudo a zero; quatro tiragens novas. Adiciona créditos pela carteira.";
  draw();refreshShares();
}
function refreshShares(){
  $("scratch-commission-merchant").value=String(state.commissionSplit.merchant);
  $("scratch-commission-technician").value=String(state.commissionSplit.technician);
  $("scratch-commission-save").textContent="GRAVAR "+state.commissionSplit.merchant+"% / "+state.commissionSplit.technician+"%";
}
function validPercent(raw){
  if(!/^\d{1,3}$/.test(raw))return null;
  const percent=Number(raw);
  return Number.isInteger(percent)&&percent>=0&&percent<=100?percent:null;
}
function updateOtherPercent(role){
  const thisId="scratch-commission-"+role;
  const otherId="scratch-commission-"+(role==="merchant"?"technician":"merchant");
  const share=validPercent($(thisId).value.trim());
  if(share!==null){
    $(otherId).value=String(100-share);
    $("scratch-commission-save").textContent="GRAVAR "+
      $("scratch-commission-merchant").value+"% / "+$("scratch-commission-technician").value+"%";
  }
}
function saveShares(event){
  event.preventDefault();
  if(!unlocked)return;
  const merchant=validPercent($("scratch-commission-merchant").value.trim());
  const technician=validPercent($("scratch-commission-technician").value.trim());
  const notice=$("scratch-commission-note");
  if(merchant===null||technician===null||merchant+technician!==100){
    notice.textContent="Indica percentagens inteiras de 0 a 100, cuja soma seja 100%.";
    return;
  }
  state.commissionSplit={merchant,technician};
  save();draw();refreshShares();
  notice.textContent="Divisão do lucro guardada: "+merchant+"% comerciante e "+technician+
    "% dono do jogo. Recalcula a quota do lucro disponível, descontando levantamentos anteriores.";
}
function commissionAvailable(role){
  const result=profitReport();
  return role==="merchant"?result.merchantAvailable:result.ownerAvailable;
}
function parseCommissionAmount(role){
  const raw=$("scratch-commission-"+role+"-amount").value.trim().replace(/\s/g,"").replace(",",".");
  if(!/^\d{1,8}(?:\.\d{1,2})?$/.test(raw))return null;
  const parts=raw.split(".");
  const amount=Number(parts[0])*100+Number((parts[1]||"").padEnd(2,"0"));
  return Number.isSafeInteger(amount)&&amount>=1?amount:null;
}
function withdrawCommission(role){
  if(!unlocked)return;
  const roleName=role==="merchant"?"comerciante":"dono do jogo";
  const amount=parseCommissionAmount(role);
  const available=commissionAvailable(role);
  const note=$("scratch-commission-withdraw-note");
  if(amount===null){note.textContent="Indica um valor válido, pelo menos 0,01 €.";return}
  if(amount>available){
    note.textContent="Saldo insuficiente para o "+roleName+". Disponível: "+eur(available)+".";
    return;
  }
  const prior=state.commissionWithdrawn[role];
  if(!Number.isSafeInteger(prior+amount)){note.textContent="O montante excede o limite suportado.";return}
  state.commissionWithdrawn[role]=prior+amount;
  state.commissionHistory.push({role,amount,at:new Date().toISOString()});
  state.commissionHistory=state.commissionHistory.slice(-100);
  save();draw();
  $("scratch-commission-"+role+"-amount").value="";
  note.textContent="Levantamento simulado de "+eur(amount)+" registado para o "+roleName+". Restam "+eur(commissionAvailable(role))+".";
}
function drawCommissions(){
  const merchant=state.commissionSplit.merchant,technician=state.commissionSplit.technician;
  const report=profitReport();
  $("scratch-commission-merchant-rate").textContent=merchant+"%";
  $("scratch-commission-technician-rate").textContent=technician+"%";
  $("scratch-profit-sales").textContent=eur(report.sales);
  $("scratch-profit-paid").textContent=eur(report.paid);
  $("scratch-profit-pending").textContent=eur(report.pending);
  $("scratch-profit-net").textContent=formatSignedEuro(report.profit);
  $("scratch-profit-distributable").textContent=eur(report.distributable);
  for(const role of ["merchant","technician"]){
    const quota=role==="merchant"?report.merchantShare:report.ownerShare;
    $("scratch-commission-"+role+"-earned").textContent=eur(quota);
    $("scratch-commission-"+role+"-withdrawn").textContent=eur(state.commissionWithdrawn[role]);
    $("scratch-commission-"+role+"-available").textContent=eur(commissionAvailable(role));
  }
  const history=$("scratch-commission-history");
  history.replaceChildren();
  const last=state.commissionHistory.slice(-8).reverse();
  if(!last.length){history.textContent="Ainda não há levantamentos.";return}
  for(const entry of last){
    const item=document.createElement("div");
    const timestamp=new Date(entry.at);
    const date=Number.isNaN(timestamp.getTime())?"Data desconhecida":timestamp.toLocaleString("pt-PT",{dateStyle:"short",timeStyle:"short"});
    item.className="scratch-commission-history-item";
    item.textContent=date+" · "+(entry.role==="merchant"?"Comerciante":"Dono do Jogo")+" · "+eur(entry.amount);
    history.append(item);
  }
}


function drawOfficialPlans(){
  const mode=$("scratch-plans-mode").value;
  const plan=window.SANTA_PLANS?.[mode];
  const summary=$("scratch-plans-summary"),tbody=$("scratch-plans-tbody");
  tbody.replaceChildren();
  if(!plan){summary.textContent="Tabela indisponível.";return}
  const euros=value=>(value).toLocaleString("pt-PT",{style:"currency",currency:"EUR",maximumFractionDigits:0});
  const integer=value=>Number(value).toLocaleString("pt-PT");
  const odds=(plan.emission/plan.winningTickets).toLocaleString("pt-PT",{maximumFractionDigits:2});
  const stock=state.officialStock?.[mode];
  const left=stock?.remaining?Object.values(stock.remaining)
    .reduce((total,count)=>total+(Number.isSafeInteger(count)&&count>0?count:0),0):0;
  summary.textContent="Jogo "+plan.game+" · Preço "+euros(plan.bet/100)+
    " · Prémio máximo "+euros(plan.maxPrize)+
    " · "+plan.luckyCount+" estrela(s) / "+plan.playCount+" casas"+
    " · Tiragem original "+integer(plan.emission)+
    " · Vendidos aqui "+integer(stock?.issued||0)+
    " · Restantes aqui "+integer(left)+
    " · 1 prémio em "+odds+
    " · Retorno anunciado "+(plan.rtp*100).toLocaleString("pt-PT",{maximumFractionDigits:2})+"%"+
    " · símbolos "+plan.multipliers.map(n=>n+"X").join(", ");
  const rows=[[0,plan.emission-plan.winningTickets],...plan.prizes];
  for(const [eurosPrize,count] of rows){
    const tr=document.createElement("tr");
    const label=document.createElement("td");
    const quantity=document.createElement("td");
    const probability=document.createElement("td");
    label.textContent=eurosPrize===0?"Sem prémio":euros(eurosPrize);
    quantity.textContent=integer(count);
    probability.textContent=(plan.emission/count).toLocaleString("pt-PT",{maximumFractionDigits:1});
    tr.append(label,quantity,probability);
    tbody.append(tr);
  }
  const link=$("scratch-plans-source");
  link.href=plan.url;
  link.textContent="Ver verso oficial do jogo "+plan.game;
}

function amount(){
  const raw=$("scratch-admin-amount").value.trim().replace(/\s/g,"").replace(",",".");
  if(!/^\d{1,8}(?:\.\d{1,2})?$/.test(raw))return null;
  const [whole,decimal=""]=raw.split(".");
  const cents=Number(whole)*100+Number(decimal.padEnd(2,"0"));
  return Number.isSafeInteger(cents)&&cents>=100?cents:null;
}
function modify(kind){
  if(!unlocked)return;
  const mode=$("scratch-admin-mode").value,p=state.pools[mode];
  const sum=amount(),notice=$("scratch-admin-note");
  if(!p||sum===null){notice.textContent="Indica um valor válido de pelo menos 1 €.";return}
  if(kind==="remove"){
    if(sum>p.available){notice.textContent="Esse cofre não tem saldo disponível suficiente.";return}
    p.available-=sum;p.removed+=sum;
    notice.textContent=eur(sum)+" retirados do fundo "+mode+"X.";
  }else{
    if(!Number.isSafeInteger(p.available+sum)||!Number.isSafeInteger(p.added+sum)){notice.textContent="Montante acima do limite.";return}
    p.available+=sum;p.added+=sum;
    notice.textContent=eur(sum)+" colocados no fundo "+mode+"X.";
  }
  save();draw();
}
$("scratch-admin-open").addEventListener("click",show);
$("scratch-admin-close").addEventListener("click",close);
$("scratch-admin-login-form").addEventListener("submit",e=>{
  e.preventDefault();
  if($("scratch-admin-pin").value!==adminPin){$("scratch-admin-error").textContent="Código incorreto.";return}
  unlocked=true;$("scratch-admin-login").hidden=true;$("scratch-admin-view").hidden=false;
  draw();refreshShares();
});
$("scratch-admin-logout").addEventListener("click",()=>{unlocked=false;$("scratch-admin-view").hidden=true;$("scratch-admin-login").hidden=false;$("scratch-admin-pin").value=""});
$("scratch-commission-merchant").addEventListener("input",()=>updateOtherPercent("merchant"));
$("scratch-commission-technician").addEventListener("input",()=>updateOtherPercent("technician"));
$("scratch-commission-form").addEventListener("submit",saveShares);
$("scratch-commission-merchant-withdraw").addEventListener("click",()=>withdrawCommission("merchant"));
$("scratch-commission-technician-withdraw").addEventListener("click",()=>withdrawCommission("technician"));
$("scratch-plans-mode").addEventListener("change",drawOfficialPlans);
$("scratch-reset-all").addEventListener("click",confirmAllReset);
$("scratch-admin-add").addEventListener("click",()=>modify("add"));
$("scratch-admin-remove").addEventListener("click",()=>modify("remove"));
panel.addEventListener("touchstart",e=>{if(e.touches.length===1)previousY=e.touches[0].clientY},{passive:true});
panel.addEventListener("touchmove",e=>{
  if(previousY===null||e.touches.length!==1)return;
  const y=e.touches[0].clientY;
  panel.scrollTop+=previousY-y;previousY=y;
  if(e.cancelable)e.preventDefault();
},{passive:false});
panel.addEventListener("touchend",()=>previousY=null,{passive:true});
window.refreshScratchAdmin=()=>{if(unlocked&&!modal.hidden)draw()};
})();
