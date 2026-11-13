#!/usr/bin/env python3
"""Pantanal v2.16 — contador de dificuldade persistido, incluindo bónus.
Acompanhamento da classe GeneratorNew.downDificult do APK 2.06.
"""
from pathlib import Path
p=Path('Pantanal/index.html')
s=p.read_text()
if 'bonus_ui.js?v=2.16' in s:
    print('v2.16 já integrada')
    raise SystemExit(0)

def once(old,new,why):
    global s
    n=s.count(old)
    assert n==1,(why,n)
    s=s.replace(old,new,1)

once("const difficulty=window.PantanalApkEngine.difficulty(state.credits,state.totalSpins-1,state.blocked,rnd);",
     """const generatorCalls=Math.max(0,Math.floor(Number(state.generatorCalls??(state.totalSpins-1))||0));
 const difficulty=window.PantanalApkEngine.difficulty(state.credits,generatorCalls,state.blocked,rnd);
 state.generatorCalls=difficulty.nextSpins;""",
     'persistir contador gerador')
once("generatorCount:Math.max(0,state.totalSpins-1),",
     "generatorCount:state.generatorCalls,",
     'contador passado à geração')
once("""     asset,difficulty,
     hooks:{save,update,showStatus,play,money,setBusy(flag){bonusBusy=!!flag;}}""",
     """     asset,difficulty,
     nextDifficulty(){
       const d=apk.difficulty(state.credits,state.generatorCalls||0,state.blocked,rnd);
       state.generatorCalls=d.nextSpins;save();return d;
     },
     hooks:{save,update,showStatus,play,money,setBusy(flag){bonusBusy=!!flag;}}""",
     'contador durante bónus')
once("state[k]=0;state.history=[];state.totalAdded=state.credits;",
     "state[k]=0;state.generatorCalls=0;state.apjSorteio=0;state.history=[];state.totalAdded=state.credits;",
     'zerar também contadores da máquina')
for file in ['apk_engine.js','bonus_ui.js','paylines.js']:
    once(file+'?v=2.15',file+'?v=2.16','cache '+file)
assert s.count('nextDifficulty(){')==1
assert s.count('state.generatorCalls=difficulty.nextSpins')==1
assert 'generatorCount:state.generatorCalls' in s
p.write_text(s)
print('PASS Pantanal 2.16: jogadas, roleta, dados, morangos e caldeirão contam para downDificult')
