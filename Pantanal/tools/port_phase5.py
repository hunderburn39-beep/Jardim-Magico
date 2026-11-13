#!/usr/bin/env python3
"""Pantanal v2.17 — desbloqueio local MED/MAX para créditos virtuais.
No APK, PainelScreen.EnviarLeitura recebe OK do servidor e grava os flags
SharedPreferences 'med' e 'max' e o número 'sorteio'. O navegador não tem
esse servidor. Esta fase permite autorizar manualmente os estados para
testes, preservando o consumo único quando Generate produz o evento.
"""
from pathlib import Path
p=Path('Pantanal/index.html')
s=p.read_text('utf8')
if 'bonus_ui.js?v=2.17' in s:
    print('v2.17 já integrada')
    raise SystemExit(0)
def once(old,new,reason):
    global s
    n=s.count(old)
    assert n==1,(reason,n)
    s=s.replace(old,new,1)
once("bonusCount:0,sound:true,history:[],blocked:50,apkAccumPercent:5,jackpots:null};",
     "bonusCount:0,sound:true,history:[],blocked:50,apkAccumPercent:5,jackpots:null,generatorCalls:0,medEnabled:false,maxEnabled:false,apjNumSorteio:200,apjSorteio:0};",
     "defaults compatíveis com flags do APK")
anchor='<label>Acumulação (%) <input id="apkAccumPercent" type="number" min="0" max="10" step=".5"></label>'
extra=('''<label>Autorizar MED (modo local) <input id="medEnabled" type="checkbox" ${state.medEnabled?'checked':''}></label>'''
       '''<label>Autorizar MAX (modo local) <input id="maxEnabled" type="checkbox" ${state.maxEnabled?'checked':''}></label>'''
       '''<label>Intervalo especial do APK <input id="apjNumSorteio" type="number" min="1" max="5000" step="1" value="${state.apjNumSorteio||200}"></label>''')
once(anchor,anchor+extra,'admin autorizacoes e sorteio')
once("el('blocked').value=state.blocked;el('apkAccumPercent').value=state.apkAccumPercent;",
     "el('blocked').value=state.blocked;el('apkAccumPercent').value=state.apkAccumPercent;el('apjNumSorteio').value=state.apjNumSorteio||200;",
     'estado admin')
once("state.apkAccumPercent=Math.max(0,Math.min(10,Number(el('apkAccumPercent').value)||0));for(let k=1;k<=3;k++)",
     """state.apkAccumPercent=Math.max(0,Math.min(10,Number(el('apkAccumPercent').value)||0));
 state.medEnabled=el('medEnabled').checked;state.maxEnabled=el('maxEnabled').checked;
 state.apjNumSorteio=Math.max(1,Math.min(5000,Math.floor(Number(el('apjNumSorteio').value)||200)));
 for(let k=1;k<=3;k++""",
     'persistir desbloqueio')
once("state.apjSorteio=generation.meta.sorteio;",
     """state.apjSorteio=generation.meta.sorteio;
 if(generation.meta.medTriggered)state.medEnabled=false;
 if(generation.meta.maxTriggered)state.maxEnabled=false;""",
     'desbloqueio consumido quando sorteado')
once("jackpotNames.push(type.toUpperCase()+' '+money(award.amount));",
     """jackpotNames.push(type.toUpperCase()+' '+money(award.amount));
     if(type==='med')state.medEnabled=false;
     if(type==='max')state.maxEnabled=false;""",
     'desbloqueio consumido no pagamento')
once("Parâmetros aplicados na simulação local. O APK original usa estados adicionais e ainda não está certificado.",
     "Os desbloqueios MED/MAX originais vinham de um servidor externo. Estes botões simulam a autorização local com créditos virtuais; não há servidor nem certificação matemática.",
     'transparencia do admin')
for name in ['apk_engine.js','bonus_ui.js','paylines.js']:
    once(name+'?v=2.16',name+'?v=2.17','cache '+name)
assert 'state.medEnabled=el(' in s and 'state.maxEnabled=el(' in s
assert 'if(generation.meta.medTriggered)' in s
assert 'bonus_ui.js?v=2.17' in s
p.write_text(s,encoding='utf8')
print('PASS Pantanal 2.17: MED MAX / sorteio controláveis localmente; consumo único sem servidor')
