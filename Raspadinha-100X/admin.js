
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
  if(unlocked)draw();else{$("scratch-admin-error").textContent="";$("scratch-admin-pin").value="";$("scratch-admin-pin").focus({preventScroll:true})}
}
function close(){modal.hidden=true;modal.setAttribute("aria-hidden","true");$("scratch-admin-open").focus({preventScroll:true})}
function draw(){
  if(!unlocked)return;
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
  draw();
});
$("scratch-admin-logout").addEventListener("click",()=>{unlocked=false;$("scratch-admin-view").hidden=true;$("scratch-admin-login").hidden=false;$("scratch-admin-pin").value=""});
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
