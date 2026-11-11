
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
  let total=0;
  const target=$("scratch-admin-cards");target.replaceChildren();
  for(const mode of CARD_MODES){
    const p=state.pools[String(mode.mult)];
    total+=p.available;
    const card=document.createElement("article");
    card.className="scratch-admin-card";
    const title=document.createElement("h3");title.textContent=mode.mult+"X · "+money(mode.bet);
    const balance=document.createElement("strong");balance.textContent=eur(p.available);
    const detail=document.createElement("dl");
    const rows=[
      ["Bilhetes",String(p.sold)],["Vendas",eur(p.stakes)],
      ["Entradas 60%",eur(p.funded)],["Prémios atribuídos",eur(p.awarded)],
      ["Prémios pagos",eur(p.paid)],["Por pagar",eur(Math.max(0,p.awarded-p.paid))],
      ["Fundo colocado",eur(p.added)],["Fundo retirado",eur(p.removed)]
    ];
    for(const [name,value] of rows){
      const dt=document.createElement("dt"),dd=document.createElement("dd");
      dt.textContent=name;dd.textContent=value;detail.append(dt,dd);
    }
    card.append(title,balance,detail);target.append(card);
  }
  $("scratch-admin-total").textContent=eur(total);
  $("scratch-admin-legacy").textContent=state.prizeBank>0
    ?"Fundo antigo partilhado (histórico, fora dos 4 cofres): "+eur(state.prizeBank)+"."
    :"Os quatro cofres estão separados.";
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
  notice.textContent="Divisão guardada: "+merchant+"% comerciante e "+technician+"% técnico. Aplica-se a novos bilhetes.";
}
function commissionAvailable(role){
  return Math.max(0,state[role]-state.commissionWithdrawn[role]);
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
  const roleName=role==="merchant"?"comerciante":"técnico";
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
  $("scratch-commission-merchant-rate").textContent=merchant+"%";
  $("scratch-commission-technician-rate").textContent=technician+"%";
  for(const role of ["merchant","technician"]){
    $("scratch-commission-"+role+"-earned").textContent=eur(state[role]);
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
    item.textContent=date+" · "+(entry.role==="merchant"?"Comerciante":"Técnico")+" · "+eur(entry.amount);
    history.append(item);
  }
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
