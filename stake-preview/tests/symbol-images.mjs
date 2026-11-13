import assert from 'node:assert/strict';
import {readFileSync,existsSync,statSync} from 'node:fs';
import {dirname,join,resolve} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const dir=resolve(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(dir,'index.html'),'utf8');
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script);
assert.match(html,/Stake Preview V1\.9\.8/);
assert.match(html,/V1\.9\.8 — nenhum símbolo pode ultrapassar/);

const typeSource=script.match(/const TYPES=\[[\s\S]*?\n\];/)?.[0];
const symbolSource=script.match(/const SYMBOL_FALLBACKS=\[[\s\S]*?\n(?=const WEIGHTS=)/)?.[0];
assert.ok(typeSource,'All original symbol descriptions present');
assert.ok(symbolSource,'Symbol preload and image fallback present');
const names=Array.from(typeSource.matchAll(/image:"([^"]+)"/g),m=>m[1]);
assert.equal(names.length,12);
assert.equal(new Set(names).size,12,'All symbols have unique image files');
for(const asset of names){
  const path=resolve(dir,asset);
  assert.ok(existsSync(path),'Missing symbol file: '+asset);
  assert.ok(statSync(path).size>1000,'Corrupt or empty symbol file: '+asset);
  const item='<link rel="preload" as="image" href="'+asset+'">';
  assert.ok(html.includes(item),'Browser image preloading missing: '+asset);
}
assert.equal((html.match(/<link rel="preload" as="image"/g)||[]).length,12);
assert.match(html,/\.cell > \.symbol-fallback\[hidden\]/);
assert.match(html,/\.reel-scroll-symbol > \.symbol-fallback\[hidden\]/);
assert.match(html,/\.cell > img\[hidden\]/);
assert.match(html,/\.reel-scroll-symbol > img\[hidden\]/);
assert.match(script,/const art=symbolArtworkMarkup\(b\[c\]\[r\]\)/,'Base game grid uses fallback');
assert.match(script,/symbolArtworkMarkup\(id\)/,'Animated reels use same fallback');

let started=0,decoded=0;
class FakeImage{
  set src(value){this.file=value;started++}
  set decoding(value){this.decodingMode=value}
  decode(){decoded++;return Promise.resolve()}
}
const ctx=vm.createContext({Image:FakeImage,Map,console,Promise});
vm.runInContext(typeSource+'\n'+symbolSource,ctx);
assert.equal(vm.runInContext('TYPES.length',ctx),12);
assert.equal(vm.runInContext('SYMBOL_FALLBACKS.length',ctx),12);
assert.equal(vm.runInContext('warmSymbolImages()',ctx),12);
assert.equal(started,12);
assert.equal(decoded,12);
assert.equal(vm.runInContext('warmSymbolImages()',ctx),12);
assert.equal(started,12,'Multiple spins do not reload the twelve original images');
for(let i=0;i<12;i++){
  ctx.id=i;
  const markup=vm.runInContext('symbolArtworkMarkup(id)',ctx);
  assert.ok(markup.includes('class="symbol-fallback"'),'A visible placeholder always exists');
  assert.ok(markup.includes('src="'+names[i]+'"'),'Artwork uses the original correct source');
  assert.ok(markup.includes('onload="this.previousElementSibling.hidden=true"'),
    'Loaded image hides its placeholder');
  assert.ok(markup.includes('onerror="this.hidden=true"'),
    'A broken image is hidden, with the placeholder still visible');
  assert.ok(markup.includes('decoding="sync" loading="eager"'),
    'Avoid lazy loading of onscreen reel tiles');
}
vm.runInContext('Image=undefined',ctx);
assert.equal(vm.runInContext('warmSymbolImages()',ctx),0,
  'Game can still show results when the Image constructor is unavailable');
// V1.9.8: nenhuma imagem sai dos quadrados, nem nos símbolos animados.
const fitBlock=html.split('/* V1.9.8 — nenhum símbolo pode ultrapassar')[1]?.split('</style>')[0];
assert.ok(fitBlock,'Reel artwork fitting CSS must be present');
assert.match(fitBlock,/#reelFrame \.cell img\.jmSymbolArt,/);
assert.match(fitBlock,/#reelFrame \.reel-scroll-symbol img\.jmSymbolArt/);
assert.match(fitBlock,/max-width:96%!important/);
assert.match(fitBlock,/max-height:96%!important/);
const zooms=Array.from(fitBlock.matchAll(/--jm-fit:\s*(\d+)%/g),m=>Number(m[1]));
assert.ok(zooms.length>=6);
assert.ok(zooms.every(p=>p<=96 && p>=80),'All reel images must stay inside their cells');
assert.doesNotMatch(fitBlock,/--jm-fit:1(?:[1-9]\d|\d{2})%/);

console.log('Stake Preview V1.9.8: all 12 genuine figures exist, are preloaded once, and never show empty tiles during Safari decoding or errors.');
