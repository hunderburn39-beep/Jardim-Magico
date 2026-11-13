#!/usr/bin/env python3
"""Pantanal v2.15: integrar lógica de geração do APK já validada.
Não mexe nos pagamentos, só na criação dos símbolos.
"""
from pathlib import Path

page=Path("Pantanal/index.html")
s=page.read_text(encoding="utf8")
if "generateRound(board,difficulty.active" in s:
    print("v2.15 já aplicado")
    raise SystemExit(0)

old="const target=window.PantanalApkEngine.generateBoard(board,difficulty.active,rnd);"
new="""const generation=window.PantanalApkEngine.generateRound(board,difficulty.active,rnd,{
   lines:state.lines,
   betPerLine:BET_LEVELS[state.betIndex],
   generatorCount:Math.max(0,state.totalSpins-1),
   canPay:state.totalSpins>=239,
   medEnabled:Boolean(state.medEnabled),
   maxEnabled:Boolean(state.maxEnabled),
   lastSorteio:state.apjSorteio||0,
   numSorteio:state.apjNumSorteio||200
 });
 const target=generation.board;
 state.apjSorteio=generation.meta.sorteio;"""
assert s.count(old)==1,"Chamada antiga do gerador alterada"
s=s.replace(old,new,1)
checks=[
 ("apk_engine.js?v=2.14","apk_engine.js?v=2.15"),
 ("bonus_ui.js?v=2.14","bonus_ui.js?v=2.15"),
 ("paylines.js?v=2.14","paylines.js?v=2.15")
]
for old,new in checks:
    assert s.count(old)==1,(old,s.count(old))
    s=s.replace(old,new,1)

assert "const generation=window.PantanalApkEngine.generateRound(" in s
assert "PantanalApkEngine.resultBoard" in s
assert "PantanalBonusUI.launch" in s
page.write_text(s,encoding='utf8')
print("PASS: v2.15 linked generator, special spins, jackpot guards and historical conditions")
