import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {createHash} from 'node:crypto';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';

const root=join(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(root,'index.html'),'utf8');
const css=html.match(/<style>([\s\S]*?)<\/style>/)?.[1];
assert.ok(css,'The style sheet is present');
const depth=css.split('/* V3.2 — profundidade 3D')[1]?.split('</style>')[0];
assert.ok(depth,'3D relief must be defined after earlier symbol styles');
assert.match(depth,/#reelFrame #reels \.cell img\.jmSymbolArt,/);
assert.match(depth,/#reelFrame \.rain-symbol img\.jmSymbolArt,/);
assert.match(depth,/#reelFrame \.reel-scroll-symbol img\.jmSymbolArt/);
assert.match(depth,/#reelFrame #reels \.cell\.winning img\.jmSymbolArt/);
assert.match(depth,/#reelFrame #reels \.cell\.scatter-cell img\.jmSymbolArt/);
assert.match(depth,/img\.jmSymbolArt\[data-symbol-id="4"\]/,'WILD gets a gilded relief');
assert.match(depth,/img\.jmSymbolArt\[data-symbol-id="11"\]/,'Gold coin gets a gilded relief');
assert.match(depth,/#holdGrid \.coin-slot\.filled/,'Hold coins are rounded and dimensional');
assert.ok((depth.match(/drop-shadow\(/g)||[]).length >= 20,'Layered highlights, bevel and depth shadows');
assert.ok(!/\btransform\s*:|\bscale\(/.test(depth),'Relief must not resize any symbol when it lands');
assert.ok(!/url\(/.test(depth),'3D effects must reuse, not replace, every existing symbol asset');
const fitted=css.split('/* V3.1 — símbolos 98%')[1]?.split('/* V3.2 — profundidade 3D')[0];
assert.match(fitted,/--jm-fit:98%!important/);
assert.match(fitted,/#reelFrame #reels\{\s*gap:1px!important/);
assert.match(fitted,/#reelFrame #reels \.column\{\s*gap:1px!important/);
const photo=readFileSync(join(root,'assets','jardim-magico-fundo-aprovado-original.png'));
assert.equal(createHash('sha256').update(photo).digest('hex'),
  'fac09acce095ad40f9d4930877e8d329941411581efb7fa758c061d1de28dcb2',
  'The previously approved scenery must remain byte-for-byte original');
const types=html.match(/const TYPES=\[[\s\S]*?\n\];/)?.[0];
assert.equal((types?.match(/image:"/g)||[]).length,12,'All twelve original images remain');
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
new Function(script);
assert.match(html,/await animateSymbolRain\(evt\.board\)/,'The original 15-piece falling behaviour remains');
console.log('Jardim Mágico: 3D beveled silhouettes, 12 original assets, original approved 9:16 backdrop, 98% size, 1px gaps and gameplay intact.');
