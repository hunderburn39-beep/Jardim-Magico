#!/usr/bin/env python3
from pathlib import Path
import re
p=Path('Pantanal/index.html')
s=p.read_text()
if 'id="apkAccumPercent"' in s:
 print('Admin v2.14 ja atualizado')
 raise SystemExit(0)
def once(old,new,why):
 global s
 assert s.count(old)==1,(why,s.count(old))
 s=s.replace(old,new,1)
s,n=re.subn(r'<label>Dificuldade <input id="difficulty"[^>]*></label>',
 '<label>Bloqueio do APK <input id="blocked" type="number" min="5" max="50" step="5"></label>',s,count=1)
assert n==1,'campo dificuldade'
s,n=re.subn(r'<label>Fator acumulação <input id="accumulation"[^>]*></label>',
 '<label>Acumulação (%) <input id="apkAccumPercent" type="number" min="0" max="10" step=".5"></label>',s,count=1)
assert n==1,'campo acumulacao'
once("Estes parâmetros são guardados no browser. Não são aplicados à probabilidade como no motor original, ainda não recuperado integralmente.",
     "Parâmetros aplicados na simulação local. O APK original usa estados adicionais e ainda não está certificado.",
     'descricao admin')
once(" el('admCredit').onclick=",
     " el('blocked').value=state.blocked;el('apkAccumPercent').value=state.apkAccumPercent;\n el('admCredit').onclick=",
     'mostrar valores')
once("state.difficulty=Math.max(1,Math.min(100,Number(el('difficulty').value)||60));state.accumulation=Math.max(0,Math.min(1,Number(el('accumulation').value)||0));",
     "state.blocked=Number(el('blocked').value)||50;state.apkAccumPercent=Math.max(0,Math.min(10,Number(el('apkAccumPercent').value)||0));",
     'persistir bloqueio e acum')
once("save();adminPanel()};\n el('admPass')",
     "state.jackpots=state.jackpots||window.PantanalApkEngine.defaultJackpots();"
     "for(const [idx,name] of ['min','med','max'].entries())"
     "state.jackpots[name]=Math.max(state['accum'+(idx+1)+'min'],Math.min(state['accum'+(idx+1)+'max'],state.jackpots[name]));"
     "save();update();adminPanel()};\n el('admPass')",
     'gravar acumulados')
p.write_text(s)
print('Admin v2.14: bloqueio real do generator, percentagem de acumulacao e limites ativos')
