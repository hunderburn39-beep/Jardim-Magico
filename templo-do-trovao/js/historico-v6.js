/* PORTÕES DO TROVÃO V6 — histórico visual das pedras e prémios.
 * Regista apenas dados locais de demonstração; não interfere nos sorteios.
 * Registos ficam na sessão do navegador (máximo 24 rodadas).
 */
(()=>{
"use strict";
const SYMBOL_NAMES={
 sapphire:"Pedra azul",emerald:"Pedra verde",amber:"Pedra amarela",
 amethyst:"Pedra roxa",ruby:"Pedra vermelha",goblet:"Cálice",
 ring:"Anel",hourglass:"Ampulheta",crown:"Coroa",
 scatter:"SCATTER",mult:"Multiplicador",wild:"WILD"
};
const ORDER=["crown","hourglass","ring","goblet","ruby","amethyst","amber","emerald","sapphire","wild","scatter","mult"];
const POSITION={
 sapphire:[0,0],emerald:[1,0],amber:[2,0],amethyst:[3,0],ruby:[4,0],
 goblet:[0,1],ring:[1,1],hourglass:[2,1],crown:[3,1],scatter:[4,1],mult:[0,2]
};
const money=n=>new Intl.NumberFormat("pt-PT",{style:"currency",currency:"EUR"}).format(Math.max(0,n||0));
const $=id=>document.getElementById(id);
const panel=$("historyPanel"),shade=$("historyShade"),list=$("historyList"),count=$("historyCount");
let records=[],active=null,serial=0;
try{
 const loaded=JSON.parse(sessionStorage.getItem("trovaoHistoryV6")||"[]");
 if(Array.isArray(loaded)){
   records=loaded.filter(v=>v&&Array.isArray(v.stages)).slice(0,24);
   serial=records.reduce((n,v)=>Math.max(n,Number(v.id)||0),0);
 }
}catch(_){}
function store(){try{sessionStorage.setItem("trovaoHistoryV6",JSON.stringify(records.slice(0,24)))}catch(_){}}
function tally(symbols){
 const c={};
 for(const s of symbols||[]){const type=s?.kind;if(type&&SYMBOL_NAMES[type])c[type]=(c[type]||0)+1;}
 return c;
}
function stage(placed,newTypes,tag){
 return {tag,placed:tally(placed),newTypes:tally(newTypes),prizes:[],payout:0};
}
function startSpin({bet,bonus,grid}){
 const entry={
  id:++serial,at:new Date().toLocaleTimeString("pt-PT",{hour:"2-digit",minute:"2-digit",second:"2-digit"}),
  bet:Number(bet)||0,bonus:!!bonus,total:0,baseWin:0,mult:1,scatters:0,
  completed:false,stages:[stage(grid,grid,"Giro inicial")]
 };
 records.unshift(entry);records=records.slice(0,24);active=entry;store();refresh();
}
function recordWin(wins){
 if(!active||!wins||!wins.result)return;
 const current=active.stages[active.stages.length-1];if(!current)return;
 for(const w of wins.result){
   current.prizes.push({symbol:w.symbol,count:w.count,amount:Number(w.amount)||0,wildCount:w.wildCount||0,wildMultiplier:w.wildMultiplier||1});
   current.payout+=Number(w.amount)||0;
   active.baseWin+=Number(w.amount)||0;
 }
 store();refresh();
}
function recordFall(grid,newborn){
 if(!active)return;
 const born=newborn instanceof Set?grid.filter(s=>newborn.has(s.id)):(newborn||[]);
 active.stages.push(stage(grid,born,"Queda "+active.stages.length));
 store();refresh();
}
function endSpin({total=0,mult=1,scatterAward=0}={}){
 if(!active)return;
 active.total=Number(total)||0;active.mult=Number(mult)||1;
 active.scatters=Number(scatterAward)||0;
 active.completed=true;active=null;store();refresh();
}
function node(tag,cls,txt){
 const e=document.createElement(tag);
 if(cls)e.className=cls;
 if(txt!==undefined)e.textContent=String(txt);
 return e;
}
function makeGlyph(kind){
 const outer=node("span","history-glyph");
 outer.dataset.kind=kind;
 const cell=POSITION[kind]||POSITION.amber;
 outer.style.setProperty("--ax",(cell[0]*25)+"%");
 outer.style.setProperty("--ay",(cell[1]*50)+"%");
 return outer;
}
function makeChip(kind,count,amount){
 const chip=node("span","history-chip"+(amount>0?" paid":""));
 chip.append(makeGlyph(kind));
 chip.append(node("span","history-chip-name",SYMBOL_NAMES[kind]||kind));
 chip.append(node("b","history-chip-count","×"+count));
 if(amount>0)chip.append(node("strong","history-chip-pay","+"+money(amount)));
 return chip;
}
function open(){
 if(!panel)return;
 panel.classList.add("is-open");panel.setAttribute("aria-hidden","false");
 shade?.classList.remove("hidden");
 $("btnHistory")?.setAttribute("aria-expanded","true");
 refresh();
}
function close(){
 panel?.classList.remove("is-open");panel?.setAttribute("aria-hidden","true");
 shade?.classList.add("hidden");$("btnHistory")?.setAttribute("aria-expanded","false");
}
function refresh(){
 if(!list)return;
 count.textContent=String(records.length);
 const fragment=document.createDocumentFragment();
 if(!records.length){fragment.append(node("p","history-empty","Ainda não há rodadas. Gira a máquina para veres aqui cada queda e o valor de cada pedra."))}
 records.forEach((item,index)=>{
  const details=node("details","history-spin");
  if(index===0)details.open=true;
  const summary=node("summary","history-spin-summary");
  const left=node("span","history-spin-left");
  left.append(node("strong","",("#"+item.id+" · "+(item.bonus?"GRÁTIS":"NORMAL"))));
  left.append(node("small","",item.at+" · Aposta "+money(item.bet)));
  const right=node("span","history-spin-right");
  right.append(node("strong","",item.completed?money(item.total):"A decorrer"));
  right.append(node("small",""," "+item.stages.length+" grelhas"));
  summary.append(left,right);details.append(summary);
  item.stages.forEach((step,i)=>{
   const section=node("section","history-stage");
   const header=node("div","history-stage-head");
   header.append(node("b","",step.tag));
   header.append(node("span","",step.payout>0?"Base "+money(step.payout):"Sem prémio"));
   section.append(header);
   const sub=node("small","history-stage-sub",i===0?"30 peças iniciais":"Peças novas desta queda");
   section.append(sub);
   const block=node("div","history-chips");
   const values=step.newTypes||{};
   const prizeMap={};
   for(const p of step.prizes||[])prizeMap[p.symbol]=(prizeMap[p.symbol]||0)+p.amount;
   for(const kind of ORDER){
    if(values[kind])block.append(makeChip(kind,values[kind],prizeMap[kind]||0));
   }
   // Um símbolo premiado pode ter surgido antes desta queda: indicar o prémio à parte.
   const paidKind=new Set(Object.keys(values));
   for(const p of step.prizes||[]){
    if(!paidKind.has(p.symbol))block.append(makeChip(p.symbol,p.count,p.amount));
   }
   if(!block.children.length)block.append(node("span","history-empty-mini","Sem novas peças"));
   section.append(block);
   if(step.prizes?.length){
    const awarded=node("div","history-awarded");
    for(const p of step.prizes){const prize=node("div","");prize.append(makeGlyph(p.symbol));prize.append(node("span","",p.count+" iguais"+(p.wildCount?" • "+p.wildCount+" WILD ×"+p.wildMultiplier:"")));prize.append(node("b","",money(p.amount)));awarded.append(prize)}
    section.append(awarded);
   }
   details.append(section);
  });
  const foot=node("div","history-spin-footer");
  foot.append(node("span","","Multiplicador: "+item.mult+"×"));
  if(item.scatters>0)foot.append(node("span","","SCATTER: "+money(item.scatters)));
  foot.append(node("strong","","Total: "+(item.completed?money(item.total):"a calcular")));
  details.append(foot);
  fragment.append(details);
 });
 list.replaceChildren(fragment);
}
$("btnHistory")?.addEventListener("click",()=>panel?.classList.contains("is-open")?close():open());
$("btnHistoryClose")?.addEventListener("click",close);
shade?.addEventListener("click",close);
document.addEventListener("keydown",e=>{if(e.key==="Escape"&&panel?.classList.contains("is-open")){e.stopPropagation();close()}});
$("btnClearHistory")?.addEventListener("click",()=>{records=[];active=null;store();refresh()});
refresh();
window.TrovaoHistory={startSpin,recordWin,recordFall,endSpin,open,close,refresh,
  read:()=>JSON.parse(JSON.stringify(records))};
})();