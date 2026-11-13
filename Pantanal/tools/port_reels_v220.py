#!/usr/bin/env python3
"""Pantanal v2.20: 5 rolos suaves, desaceleração e alinhamento central.

Somente animação e geometria; não altera tabela de prémios ou RNG da ronda.
"""
from pathlib import Path

page=Path('Pantanal/index.html')
s=page.read_text(encoding='utf8')
if 'reels_motion.js?v=2.20' in s:
    print('v2.20 já integrada')
    raise SystemExit(0)

old="""// Asynchronous reel-stop animation staggered from left to right.
 for(let tick=0;tick<31;tick++){for(let col=0;col<5;col++)if(tick<10+col*4){for(let row=0;row<3;row++)board[row][col]=pick()}else if(tick===10+col*4){for(let row=0;row<3;row++)board[row][col]=target[row][col];play('line')};renderBoard();await wait(49)}
 board=target;renderBoard();"""

new="""// Cada faixa desloca-se continuamente para baixo e abranda antes de parar.
 // A animação não participa no RNG nem altera os símbolos escolhidos.
 await window.PantanalReelMotion.spin(board,target,{
   pick,asset,
   onStop(){play('line')},
   onComplete(){board=target;renderBoard();}
 });"""

n=s.count(old)
assert n==1, f'Não foi encontrado um único ciclo de animação antigo ({n})'
s=s.replace(old,new,1)

for name in ['paylines.js','apk_engine.js','bonus_ui.js']:
    old=name+'?v=2.19'
    assert s.count(old)==1, (old,s.count(old))
    s=s.replace(old,name+'?v=2.20',1)

old='</body></html>'
new='<script src="reels_motion.js?v=2.20"></script></body></html>'
assert s.count(old)==1
s=s.replace(old,new,1)
assert s.count('PantanalReelMotion.spin(board,target')==1
assert 'for(let tick=0;tick<31;tick++)' not in s
assert 'window.PantanalApkEngine.resultBoard(b,state.lines' in s
page.write_text(s,encoding='utf8')
print('PASS v2.20: reels smooth 5 columns with easing, centered; game math unchanged')
