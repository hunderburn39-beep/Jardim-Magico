#!/usr/bin/env python3
"""Integrar primeira fase de regras verificadas do APK Pantanal na app web.

Não altera pagamentos das linhas 1-20 (pendentes de auditoria).
Regista aposta por linha, 25 trajetos, 5 pagamentos verticais e dados.
"""
from pathlib import Path
import re

html=Path("Pantanal/index.html")
rules=Path("Pantanal/original_rules.js").read_text(encoding="utf8")
s=html.read_text(encoding="utf8")
if "original_rules.js?v=2.13" in s:
    print("Já integrado: Pantanal v2.13")
    raise SystemExit(0)

def once(a,b,reason):
    global s
    n=s.count(a)
    assert n==1, f"{reason}: esperado 1, recebido {n}"
    s=s.replace(a,b,1)

m=re.search(r"const LINES20=Object\.freeze\(\[\s*(.*?)\s*\]\);",rules,re.S)
assert m,"Não foram encontrados os 20 percursos originais"
native=m.group(1)
paths="const LINES=[\n"+native+",\n"+",".join(["[0,1,2]"]*5)+"\n];"
s,n=re.subn(r"const LINES=\[\s*.*?\s*\];",lambda _:paths,s,count=1,flags=re.S)
assert n==1,"Não foi possível substituir a tabela antiga de linhas"
once("for(let l=0;l<state.lines;l++)","for(let l=0;l<Math.min(state.lines,20);l++)","limitar cálculos antigos às linhas horizontais")
old=" return {wins,total}}"
new=""" const vertical=window.PantanalOriginalRules.verticalResults(b,state.lines,BET_LEVELS[state.betIndex]);
 wins.push(...vertical.wins);
 return {wins,total:total+vertical.total,bonus:vertical.bonus}}"""
once(old,new,"adicionar pagamentos verticais do APK")
once("const stake=BET_LEVELS[state.betIndex];if(state.credits<stake)",
     "const stake=window.PantanalOriginalRules.stake(BET_LEVELS[state.betIndex],state.lines);if(state.credits<stake)",
     "aposta por linha multiplicada pelo número de linhas")
once("el('bet').textContent=money(BET_LEVELS[state.betIndex]);",
     "el('bet').textContent=money(BET_LEVELS[state.betIndex]*state.lines);",
     "mostrar aposta total")
once("let {wins,total}=calcWins(board);",
     "let {wins,total,bonus}=calcWins(board);",
     "receber metadados dos bónus")
once("const bonusTrigger=board.flat().filter(v=>v===9).length>=3;",
     "const bonusTrigger=bonus.wheel>0;const diceTrigger=bonus.dice>0;pendingDice=bonus.dice;",
     "gatilho de roleta/dados vertical, sem scatter inventado")
once("if(auto){await wait(450);if(auto)spin()}\n }",
     "if(diceTrigger){auto=false;await wait(500);showDiceBonus();return}\n if(auto){await wait(450);if(auto)spin()}\n }",
     "mostrar dados após os tambores")
once("function flashWin(wins){",
     "function flashWin(wins){",
     "validar função de apresentação")
once("window.PantanalClearPaylines?.();spinBusy=true;",
     "window.PantanalClearPaylines?.();pendingDice=0;spinBusy=true;",
     "limpar fila de bónus antes de rodar")
once("action.onclick=doWheel;\n  };",
     "action.onclick=doWheel;\n   if(pendingDice>0)showDiceBonus();\n  };",
     "chamar os dados após terminar a roleta")
old_line=[l for l in s.splitlines() if "el('linesBtn').onclick=" in l]
assert len(old_line)==1,"Um único handler de linhas é esperado"
old=old_line[0]
new="""el('linesBtn').onclick=()=>{if(spinBusy||bonusBusy)return;
 const levels=window.PantanalOriginalRules.LEVELS;
 const current=levels.indexOf(state.lines);
 state.lines=levels[(current+1)%levels.length];
 save();update();showStatus(state.lines+' LINHAS ATIVAS');
 window.PantanalPreviewLines?.(LINES.slice(0,state.lines));
};"""
once(old,new,"seleção nativa 1/5/9/15/25")
old_bet=[l for l in s.splitlines() if "el('betBtn').onclick=" in l]
assert len(old_bet)==1
orig=old_bet[0]
new="""el('betBtn').onclick=()=>{if(spinBusy||bonusBusy)return;
 state.betIndex=(state.betIndex+1)%BET_LEVELS.length;
 save();update();
 showStatus('POR LINHA: '+money(BET_LEVELS[state.betIndex])+' • TOTAL: '+money(BET_LEVELS[state.betIndex]*state.lines)+' CRÉDITOS');
};"""
once(orig,new,"clareza sobre aposta por linha")
once("paylines.js?v=2.12","paylines.js?v=2.13","invalidar cache de linhas")
once("</body>","<script src=\"original_rules.js?v=2.13\"></script>\n</body>","incluir módulo de matemática antes de o utilizador jogar")

inject=r'''
let pendingDice=0;
function showDiceBonus(){
 if(pendingDice<=0)return;
 bonusBusy=true;
 auto=false;
 const old=document.getElementById('pantanalDiceBonus');
 if(old)old.remove();
 const layer=document.createElement('div');
 layer.id='pantanalDiceBonus';
 layer.style.cssText='position:absolute;inset:0;z-index:35;background:rgba(10,5,3,.86);display:flex;align-items:center;justify-content:center;box-sizing:border-box;';
 const panel=document.createElement('div');
 panel.style.cssText='width:760px;max-width:96%;min-height:345px;padding:20px;border:6px ridge #e8b85d;border-radius:23px;text-align:center;box-sizing:border-box;background:radial-gradient(ellipse,#895221,#361b0b);box-shadow:0 0 35px #000;';
 const h=document.createElement('h2');
 h.textContent='BÓNUS DOS DADOS';
 h.style.cssText='margin:8px 0 8px;font-size:39px;color:#ffe9a4;text-shadow:2px 3px 4px #000;';
 const sub=document.createElement('div');
 sub.textContent='ESCOLHE UM DOS 5 DADOS';
 sub.style.cssText='font-size:24px;font-weight:bold;color:#fff3d9;margin:7px;';
 const row=document.createElement('div');
 row.style.cssText='display:flex;justify-content:center;gap:9px;margin:30px 5px;flex-wrap:nowrap;';
 const info=document.createElement('div');
 info.textContent='PRÉMIO ATUAL: '+money(state.lastWin)+' CRÉDITOS';
 info.style.cssText='font-size:25px;color:#ffe9a4;font-weight:bold;min-height:39px;';
 const dice=[];
 for(let i=0;i<5;i++){
  const b=document.createElement('button');
  b.type='button';b.title='Escolher dado '+(i+1);
  b.style.cssText='width:125px;height:115px;border:4px solid #efb976;border-radius:15px;background:radial-gradient(circle at 30% 20%,#df4933,#861c16 85%);box-shadow:0 5px 9px #120500;font-size:61px;color:#fff5c6;font-weight:bold;transition:transform .3s;';
  // dado.png: atlas original de 8 colunas por 9 linhas.
  const icon=asset('dado.png');
  if(icon){b.style.backgroundImage='url("'+icon+'")';b.style.backgroundSize='800% 900%';b.style.backgroundPosition='0% 0%';b.textContent='';}
  else b.textContent='⚄';
  b.addEventListener('click',()=>{
   if(!bonusBusy||layer.dataset.picked)return;
   layer.dataset.picked='true';
   dice.forEach(x=>x.disabled=true);
   b.style.transform='translateY(70px) rotate(720deg) scale(1.06)';
   // Gerador do APK: 80 ou 100 posições, consoante a condição de pagamento.
   // Esta é uma aproximação inicial dessa condição; o motor completo fica pendente.
   const paymentMode=(state.credits<42500&&state.totalSpins<500);
   const roll=window.PantanalOriginalRules.diceRoll(rnd,paymentMode);
   const final=window.PantanalOriginalRules.dicePrize(state.lastWin,roll);
   const extra=final-state.lastWin;
   state.lastWin=final;state.credits+=extra;state.totalPaid+=extra;
   state.maxWin=Math.max(state.maxWin,final);
   if(state.history[0]){
     state.history[0].total=final;
     state.history[0].diceMultiplier=roll.multiplier;
     state.history[0].bonus=true;
   }
   save();update();play('big');
   b.style.backgroundImage='none';
   b.textContent='×'+roll.multiplier;
   b.style.fontSize='50px';
   info.textContent='DADO ×'+roll.multiplier+' — PRÉMIO FINAL: '+money(final)+' CRÉDITOS';
   showStatus('BÓNUS DOS DADOS: ×'+roll.multiplier+' — '+money(final)+' CRÉDITOS');
   setTimeout(()=>{
     layer.remove();bonusBusy=false;pendingDice=Math.max(0,pendingDice-1);
     if(pendingDice>0)showDiceBonus();
   },2500);
  });
  dice.push(b);row.appendChild(b);
 }
 panel.append(h,sub,row,info);layer.appendChild(panel);el('game').appendChild(layer);
 play('bonus');
}
'''
anchor="async function spin(){"
assert s.count(anchor)==1,"Não foi possível adicionar o bónus"
s=s.replace(anchor,inject+"\n"+anchor,1)
assert s.count("window.PantanalOriginalRules.verticalResults(")==1
assert s.count("showDiceBonus(){")==1
assert s.count("const diceTrigger")==1
assert "const LINES=[" in s and "function calcWins(b)" in s
html.write_text(s,encoding="utf8")
print('Pantanal v2.13 integrado — 20 linhas APK, 5 verticais, seleção original e dados (fase 1).')
