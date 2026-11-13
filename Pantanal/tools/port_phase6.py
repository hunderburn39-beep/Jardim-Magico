#!/usr/bin/env python3
"""Pantanal v2.18 — retoma de bónus interrompidos sem crédito duplicado.

Cada pagamento final de etapa é armazenado no mesmo save que avança
a fila pendente. Numa recarga, apenas a etapa ainda não paga se reabre.
"""
from pathlib import Path

p=Path("Pantanal/index.html")
s=p.read_text(encoding="utf8")
if "bonus_ui.js?v=2.18" in s:
    print("Pantanal 2.18 já integrado")
    raise SystemExit(0)

def once(a,b,msg):
    global s
    n=s.count(a)
    assert n==1, (msg,n)
    s=s.replace(a,b,1)

once("bonusCount:0,sound:true,history:[],blocked:50,apkAccumPercent:5,jackpots:null,generatorCalls:0,medEnabled:false,maxEnabled:false,apjNumSorteio:200,apjSorteio:0};",
     "bonusCount:0,sound:true,history:[],blocked:50,apkAccumPercent:5,jackpots:null,generatorCalls:0,medEnabled:false,maxEnabled:false,apjNumSorteio:200,apjSorteio:0,pendingBonus:null};",
     "estado persistente do bónus")

once("function showGame(){el('home').style.display='none';el('game').style.display='block';el('bonusLayer').style.display='none';renderBoard();update();showStatus('PANTANAL — 25 LINHAS');}",
     """function showGame(){
 el('home').style.display='none';el('game').style.display='block';
 el('bonusLayer').style.display='none';renderBoard();update();
 showStatus('PANTANAL — '+state.lines+' LINHAS');
 if(window.PantanalBonusUI?.hasPending(state.pendingBonus)){
   showStatus('BÓNUS POR TERMINAR — A RETOMAR');
   setTimeout(()=>resumePendingBonuses(),100);
 }
}""",
     "retoma ao entrar no ecrã")

anchor="async function spin(){"
if s.count(anchor)!=1: raise RuntimeError("não existe um único spin()")
s=s.replace(anchor,"""async function resumePendingBonuses(){
 if(spinBusy||bonusBusy||celebrationBusy||
    !window.PantanalBonusUI?.hasPending(state.pendingBonus))return false;
 auto=false;
 const apk=window.PantanalApkEngine;
 showStatus('BÓNUS GUARDADO — CONTINUAR');
 await window.PantanalBonusUI.launch({
   state,betPerLine:state.pendingBonus.betPerLine,
   rng:rnd,asset,
   nextDifficulty(){
     const d=apk.difficulty(state.credits,state.generatorCalls||0,state.blocked,rnd);
     state.generatorCalls=d.nextSpins;save();return d;
   },
   hooks:{save,update,showStatus,play,money,setBusy(flag){bonusBusy=!!flag;}}
 });
 return true;
}
""" + anchor,1)

once("async function spin(){if(spinBusy||bonusBusy||celebrationBusy)return;const stake=",
     """async function spin(){
 if(spinBusy||bonusBusy||celebrationBusy)return;
 if(window.PantanalBonusUI?.hasPending(state.pendingBonus)){
   await resumePendingBonuses();
   return;
 }
 const stake=""",
     "impedir novo débito com bónus por concluir")

once("const bonusTrigger=Object.values(bonus).some(v=>v>0);\n state.history.unshift(",
     """const bonusTrigger=Object.values(bonus).some(v=>v>0);
 state.pendingBonus=bonusTrigger
   ?window.PantanalBonusUI.createPending(bonus,BET_LEVELS[state.betIndex])
   :null;
 state.history.unshift(""",
     "guardar prémio e a fila na mesma transação")

old="""if(bonusTrigger){
   auto=false;await wait(450);
   await window.PantanalBonusUI.launch({
     bonus,state,betPerLine:BET_LEVELS[state.betIndex],rng:rnd,
     asset,difficulty,
     nextDifficulty(){
       const d=apk.difficulty(state.credits,state.generatorCalls||0,state.blocked,rnd);
       state.generatorCalls=d.nextSpins;save();return d;
     },
     hooks:{save,update,showStatus,play,money,setBusy(flag){bonusBusy=!!flag;}}
   });
   return;
 }"""
new="""if(bonusTrigger){
   auto=false;await wait(450);
   await resumePendingBonuses();
   return;
 }"""
once(old,new,"entrada de bónus usa a mesma fila da recuperação")

for filename in ("paylines.js","apk_engine.js","bonus_ui.js"):
    once(filename+"?v=2.17",filename+"?v=2.18","cache "+filename)

assert "state.pendingBonus=bonusTrigger" in s
assert "async function resumePendingBonuses()" in s
assert "bonus_ui.js?v=2.18" in s
p.write_text(s,encoding="utf8")
print("PASS: v2.18 resumption, atomic bonus checkpoint and cache invalidation")
