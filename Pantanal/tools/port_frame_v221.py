#!/usr/bin/env python3
"""Pantanal v2.21 — instalar contentor compartilhado de moldura e tambores.

Frame/reels/paylines center as one for 9:16, with legacy horizontal
layout preserved. No game economy, RNG, spin or bonus code is modified.
"""
from pathlib import Path
p=Path('Pantanal/index.html')
s=p.read_text(encoding='utf8')
if 'frame_layout.js?v=2.21' in s:
    print('Pantanal v2.21 já integrado')
    raise SystemExit(0)

old='<script src="reels_motion.js?v=2.20"></script></body></html>'
new='<script src="reels_motion.js?v=2.20"></script><script src="frame_layout.js?v=2.21"></script></body></html>'
assert s.count(old)==1,'Scripts originais não correspondem à versão 2.20'
s=s.replace(old,new,1)
assert s.count('frame_layout.js?v=2.21')==1
assert 'PantanalReelMotion.spin(board,target' in s
assert 'window.PantanalApkEngine.resultBoard(' in s
p.write_text(s,encoding='utf8')
print('PASS v2.21: centered frame + reels + winning lines, 9:16')
