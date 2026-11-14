import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {createHash} from 'node:crypto';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const dir=join(dirname(fileURLToPath(import.meta.url)),'..');
const asset=join(dir,'assets','jardim-magico-fundo-original-aprovado.png');
const buffer=readFileSync(asset);
assert.equal(buffer.length,3068672,'the exact approved PNG has been copied unmodified');
assert.equal(createHash('sha256').update(buffer).digest('hex'),
  'fac09acce095ad40f9d4930877e8d329941411581efb7fa758c061d1de28dcb2',
  'GitHub has the identical original pixels/bytes');
assert.equal(buffer.subarray(1,4).toString(),'PNG');
assert.equal(buffer.readUInt32BE(16),941);
assert.equal(buffer.readUInt32BE(20),1672);
const html=readFileSync(join(dir,'index.html'),'utf8');
assert.match(html,/link rel="preload" as="image" href="assets\/jardim-magico-fundo-original-aprovado\.png"/);
const finalCSS=html.split('/* EXACT APPROVED IMAGE')[1]?.split('</style>')[0];
assert.ok(finalCSS,'approved asset must be the final CSS override');
assert.match(finalCSS,/background-image:url\('assets\/jardim-magico-fundo-original-aprovado\.png'\)!important/);
assert.match(finalCSS,/background-size:100% 100%!important/,'entire approved illustration visible, no crop');
assert.match(finalCSS,/\.app > \.logo-wrap\{display:none!important\}/,'no separate logo over original');
assert.match(finalCSS,/#introScreen \.intro-logo,#introScreen \.intro-subtitle,#introScreen \.intro-lead\{display:none!important\}/,'splash does not duplicate original logo');
assert.match(finalCSS,/#reelFrame\.frame::before,#reelFrame\.frame::after\{display:none!important/,'old veil and frames hidden');
assert.match(finalCSS,/#introScreen\.intro-screen\{\s*background:transparent!important/);
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script);
new vm.Script(script);
for(const name of ['animateSymbolRain','revealBaseCoins','holdFeature','freeFeature']){
  assert.ok(script.includes('function '+name),'keep the unchanged gameplay: '+name);
}
console.log('Exact approved image: 941x1672, SHA-256 verified byte for byte; no duplicate logo or redraw; gameplay intact.');
