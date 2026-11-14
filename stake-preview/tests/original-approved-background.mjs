import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {createHash} from 'node:crypto';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';

const root=join(dirname(fileURLToPath(import.meta.url)),'..');
const path='assets/jardim-magico-fundo-aprovado-original.png';
const bytes=readFileSync(join(root,path));
assert.equal(bytes.length,3068672,'original PNG must be identical, not recompressed');
assert.equal(createHash('sha256').update(bytes).digest('hex'),
  'fac09acce095ad40f9d4930877e8d329941411581efb7fa758c061d1de28dcb2',
  'approved image bytes changed');
assert.equal(bytes.subarray(0,8).toString('hex'),'89504e470d0a1a0a');
assert.equal(bytes.readUInt32BE(16),941);
assert.equal(bytes.readUInt32BE(20),1672);
const html=readFileSync(join(root,'index.html'),'utf8');
const style=html.match(/<style>([\s\S]*?)<\/style>/)?.[1];
assert.ok(style);
const finalStage=style.slice(style.lastIndexOf('/* THE APPROVED FULL PNG:'));
assert.ok(finalStage.includes("background-image:url('"+path+"')!important"));
assert.match(finalStage,/background-size:contain!important/);
assert.match(finalStage,/\.intro-screen\s*\{[^}]*jardim-magico-fundo-aprovado-original\.png/);
assert.match(finalStage,/\.logo-wrap,\.intro-screen \.intro-logo/);
assert.match(finalStage,/#reelFrame\.frame::before\{content:none!important;display:none!important\}/);
assert.match(finalStage,/#reelFrame\.frame\{background:transparent!important/);
assert.ok(html.includes('await animateSymbolRain(evt.board)'),'15 symbols must still fall in invisible columns');
const js=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
new Function(js);
console.log('Approved original PNG is exact SHA256, 941x1672, shown unchanged without a duplicate logo; gameplay preserved.');
