#!/usr/bin/env python3
"""Pantanal v2.22 — 25 números visíveis na moldura 9:16.

A matemática, RNG, créditos e animação não são alterados.
A seleção de 1/5/9/15/25 linhas passa a iluminar os números ativos.
"""
from pathlib import Path
page=Path("Pantanal/index.html")
s=page.read_text(encoding="utf-8")
if 'frame_layout.js?v=2.22' in s:
    print("Pantanal v2.22 já integrado")
    raise SystemExit(0)

def once(old,new,label):
    global s
    count=s.count(old)
    assert count==1,(label,count)
    s=s.replace(old,new,1)

once('frame_layout.js?v=2.21','frame_layout.js?v=2.22','moldura/numeração')
once('paylines.js?v=2.20','paylines.js?v=2.22','realce de números')

once("el('spinLabel').textContent=spinBusy?'RODANDO':'JOGAR';updateJackpotHUD();}",
     "el('spinLabel').textContent=spinBusy?'RODANDO':'JOGAR';updateJackpotHUD();window.PantanalFrameLayout?.setActiveLines(state.lines);}",
     "mostrar 25 números e iluminar as linhas ativas")

assert s.count('frame_layout.js?v=2.22')==1
assert "PantanalFrameLayout?.setActiveLines(state.lines)" in s
assert "PantanalApkEngine.resultBoard" in s
assert "PantanalReelMotion.spin" in s
page.write_text(s,encoding="utf-8")
print("PASS: numeração 1–10 esquerda, 11–20 direita, 21–25 em baixo; realce ativo")
