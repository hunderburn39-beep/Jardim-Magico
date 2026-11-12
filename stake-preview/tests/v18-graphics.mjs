import assert from 'node:assert/strict';
import {readFileSync,existsSync,statSync} from 'node:fs';
import {dirname,resolve,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const dir=resolve(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(dir,'index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.1/);
assert.match(html,/V1\.8\.1 • NOVO VISUAL/);
const assets=['assets/jardim-v181-cenario-limpo.svg','assets/jardim-v181-logo.svg','assets/jardim-v181-moldura.svg'];
for(const filename of assets){
  const path=resolve(dir,filename);
  assert.ok(existsSync(path),'New HD asset missing: '+filename);
  assert.ok(statSync(path).size>3500,'New vector asset empty: '+filename);
  assert.ok(html.includes(filename),'Live game does not use '+filename);
}
const background=readFileSync(join(dir,assets[0]),'utf8');
const logo=readFileSync(join(dir,assets[1]),'utf8');
const frame=readFileSync(join(dir,assets[2]),'utf8');
assert.match(background,/viewBox="0 0 941 1672"/,'True portrait image, no low-resolution stretch');
assert.match(background,/jardim|Jardim/i);
assert.doesNotMatch(background,/<text\b/i,'Background may not contain printed lettering');
assert.doesNotMatch(background,/<image\b/i,'Background may not contain screenshot, low-res raster or fake controls');
assert.doesNotMatch(background,/base64/i,'Background must remain crisp 100% vector');
assert.match(background,/id="water"/,'Original waterfall and pond atmosphere');
assert.match(background,/id="leaf"/,'Detailed independent foliage');
assert.match(logo,/viewBox="0 0 940 410"/);
assert.match(logo,/>Jardim<\/text>/,'Lettering renders as vector text');
assert.match(logo,/>MÁGICO<\/text>/,'Full accentuated title');
assert.doesNotMatch(logo,/<clipPath\b/i,'Logo should not have a cutout/crop');
assert.match(frame,/viewBox="0 0 920 580"/);
assert.match(frame,/interior is transparent/i,'No fake reel graphic in frame');
assert.match(frame,/url\(#gold\)/,'Gold trim remains');
assert.match(frame,/url\(#jewel\)/,'Purple gemstone remains');
assert.doesNotMatch(frame,/<image\b/i,'Frame is vector');

assert.doesNotMatch(html,/jardim-v18-reference\.png/i,'No old blurry screenshot as background');
assert.doesNotMatch(html,/url\('\.\.\/assets\/imagens\/jm80-cenario-imersivo\.svg'\)/,'No old background fallback');
assert.match(html,/\.app\s*\{[^}]*jardim-v181-cenario-limpo\.svg/s);
assert.match(html,/\.frame:after\s*\{[^}]*jardim-v181-moldura\.svg/s);
assert.match(html,/\.logo-wrap\s*\{[^}]*env\(safe-area-inset-top\)/s,'Logo clears iPhone safe area');
assert.match(html,/\.logo-wrap\s*\{[^}]*height:13\.4%/s);
assert.match(html,/\.reels\s*\{[^}]*height:100%/s,'Five dynamic reel columns fill their frame');
assert.match(html,/\.frame\s*\{[^}]*top:29%/s);
assert.match(html,/\.bonus-row\s*\{[^}]*top:64\.2%/s);
assert.match(html,/\.money-row\s*\{[^}]*top:74\.9%/s);
assert.match(html,/\.actions\s*\{[^}]*top:85\.6%/s);
assert.equal((html.match(/src="assets\/jardim-v181-logo\.svg"/g)||[]).length,2,'Both intro and game logo use the same crisp asset');

for(const id of ['reels','balance','bet','minus','plus','spin','auto','turbo','buyFree','buyHold','paytableBtn','historyBtn','soundBtn','musicBtn','skipBtn','sessionStatus','freePanel','holdGrid','linePrize']){
 assert.ok(html.includes('id="'+id+'"'),'Real functional HTML control missing: '+id);
}
for(const fn of ['commitRound','settlePendingRound','freeFeature','holdFeature','stopAuto','showPaytable']){
 assert.ok(html.includes('function '+fn),'Core engine retained: '+fn);
}
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script,'Full working engine present');
new vm.Script(script,{filename:'stake-preview/index.html'});
console.log('Stake Preview V1.8.1: true SVG background with no screenshot, crisp full logo, transparent 5x3 frame, real buttons and valid JS passed.');
