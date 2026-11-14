import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {createHash} from 'node:crypto';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';

const root=join(dirname(fileURLToPath(import.meta.url)),'..');
const path='assets/figuras-3d-aprovadas-20261114/scatter-3d.png';
const asset=readFileSync(join(root,path));
assert.equal(asset.length,2136099,'PNG must be the exact generated image');
assert.equal(createHash('sha256').update(asset).digest('hex'),
 '0e1964ceb0a43ff739527842a8b7feded56a04e444c39be6ffdadb5ff46effdc',
 'Never redraw or recompress the approved SCATTER');
assert.equal(asset.subarray(0,8).toString('hex'),'89504e470d0a1a0a');
assert.equal(asset.readUInt32BE(16),1254);
assert.equal(asset.readUInt32BE(20),1254);
assert.equal(asset[25],6,'Original transparent PNG must remain RGBA');

const html=readFileSync(join(root,'index.html'),'utf8');
assert.equal(html.split(path).length-1,2,'SCATTER path must occur in symbol table and one preload');
assert.match(html,/<link rel="preload" as="image" href="assets\/figuras-3d-aprovadas-20261114\/scatter-3d\.png">/);
assert.match(html, /\{label:"SCATTER",image:"assets\/figuras-3d-aprovadas-20261114\/scatter-3d\.png"\}/);
assert.doesNotMatch(html,/\.\.\/assets\/imagens\/d95ddf1f53c88d01c4\.png/,'No old SCATTER');

const styles=html.match(/<style>([\s\S]*?)<\/style>/)?.[1]||'';
const patch=styles.split('/* V3.6 — SCATTER florido aprovado:')[1]?.split('</style>')[0];
assert.ok(patch,'SCATTER styling must override earlier 98% sizing and circular clip');
assert.match(patch,/width:122%!important/);
assert.match(patch,/height:122%!important/);
assert.match(patch,/width:128%!important/);
assert.match(patch,/height:128%!important/);
assert.match(patch,/z-index:32!important/);
assert.match(patch,/z-index:35!important/);
assert.match(patch,/overflow:visible!important/);
assert.match(patch,/clip-path:none!important/);
assert.match(patch,/translate\(-50%,-50%\)/);
assert.match(patch,/scatterOversizeIdle/);
assert.match(patch,/scatterOversizeTrigger/);
assert.match(patch,/prefers-reduced-motion:reduce/);
assert.ok(html.includes('sprite.className="rain-symbol"+(entry.symbol===9?" scatter-fall":"")'),
 'Falling SCATTER must remain above neighbouring figures');
const original=readFileSync(join(root,'assets/jardim-magico-fundo-aprovado-original.png'));
assert.equal(createHash('sha256').update(original).digest('hex'),
 'fac09acce095ad40f9d4930877e8d329941411581efb7fa758c061d1de28dcb2',
 'Background approved by the user stays byte-identical');
assert.match(styles,/--jm-fit:98%!important/,'The other eleven figures keep their 98% size');
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
new Function(script);
console.log('Jardim Mágico: original SCATTER PNG 1254 RGBA and SHA256 verified, 122% normal / 128% bonus, layer and motion unchanged.');
