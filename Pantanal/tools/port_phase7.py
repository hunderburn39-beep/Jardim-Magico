#!/usr/bin/env python3
"""Pantanal v2.19 — manter os mesmos símbolos após atualizar o browser.

Ao recuperar bónus, a v2.18 reabria o prémio corretamente, mas o painel
de tambores era preenchido com símbolos aleatórios. Agora restaura o
tabuleiro da última jogada persistida no histórico (quando válido).
"""
from pathlib import Path
page=Path('Pantanal/index.html')
s=page.read_text(encoding='utf8')
if 'bonus_ui.js?v=2.19' in s:
    print('v2.19 já integrada')
    raise SystemExit(0)
old="for(let r=0;r<3;r++)for(let c=0;c<5;c++)board[r][c]=pick();renderBoard();update();"
new="""const savedBoard=state.history?.find(entry=>
 Array.isArray(entry.board)&&entry.board.length===3&&
 entry.board.every(row=>Array.isArray(row)&&row.length===5&&
   row.every(v=>Number.isInteger(v)&&v>=0&&v<10))
)?.board;
if(savedBoard){
 board=savedBoard.map(row=>row.slice());
}else{
 for(let r=0;r<3;r++)for(let c=0;c<5;c++)board[r][c]=pick();
}
renderBoard();update();"""
assert s.count(old)==1,("board init",s.count(old))
s=s.replace(old,new,1)
for file in ('apk_engine.js','bonus_ui.js','paylines.js'):
    current=file+'?v=2.18'
    assert s.count(current)==1,(file,s.count(current))
    s=s.replace(current,file+'?v=2.19',1)
assert "const savedBoard=state.history?.find(" in s
page.write_text(s,encoding='utf8')
print('PASS Pantanal v2.19: recover reel visuals from last validated history board')
