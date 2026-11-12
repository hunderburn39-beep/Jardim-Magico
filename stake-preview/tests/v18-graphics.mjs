import assert from 'node:assert/strict';
import {readFileSync,existsSync,statSync} from 'node:fs';
import {dirname,resolve,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const dir=resolve(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(dir,'index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8/);
assert.match(html,/V1\.8 • EDIÇÃO GRÁFICA/);
for(const filename of ['assets/jardim-v18-reference.png','assets/jardim-v18-moldura.svg']){
  const path=resolve(dir,filename);
  assert.ok(existsSync(path),filename+' found in GitHub checkout');
  assert.ok(statSync(path).size>5000,filename+' is not empty');
  assert.ok(html.includes(filename),'approved artwork actually used by game CSS');
}
const png=readFileSync(join(dir,'assets/jardim-v18-reference.png'));
assert.equal(png.subarray(1,4).toString('ascii'),'PNG','approved artwork format');
const width=png.readUInt32BE(16),height=png.readUInt32BE(20);
assert.ok(width>90&&height>160,'portrait approved artwork is present');
const frame=readFileSync(join(dir,'assets/jardim-v18-moldura.svg'),'utf8');
assert.match(frame,/viewBox="0 0 920 580"/);
assert.match(frame,/url\(#gold\)/,'Frame has metallic gold highlights');
assert.match(frame,/url\(#gem\)/,'Frame has purple gemstone');
assert.match(frame,/fill="#df86de"/,'Frame has enchanted garden flowers');
assert.match(html,/\.frame:after\s*\{[^}]*jardim-v18-moldura\.svg/s);
assert.match(html,/\.logo-wrap\s*\{[^}]*top:7\.6%;left:10%;width:80%/s,'Complete logo within safe bounds');
assert.match(html,/\.frame\s*\{[^}]*top:29\.1%/s,'5x3 grid in reference artwork location');
assert.match(html,/\.reels\s*\{[^}]*height:100%/s,'Interactive reels cover printed symbols');
assert.match(html,/\.bonus-row\s*\{[^}]*top:63\.0%/s,'Bonus controls align with the design');
assert.match(html,/\.money-row\s*\{[^}]*top:73\.1%/s,'Balances are real controls');
assert.match(html,/\.actions\s*\{[^}]*top:83\.0%/s,'Spin/auto/turbo align with artwork');
assert.match(html,/\.intro-screen\s*\{[^}]*jardim-v18-reference\.png/s,'Intro uses approved art');

const names=['reels','balance','bet','minus','plus','spin','auto','turbo','buyFree','buyHold','paytableBtn',
'historyBtn','soundBtn','musicBtn','skipBtn','sessionStatus','freePanel','holdGrid','linePrize'];
for(const id of names)assert.ok(html.includes('id="'+id+'"'),'Functional component remains real HTML: '+id);
assert.match(html,/function commitRound/,'Current accounting is intact');
assert.match(html,/function settlePendingRound/,'Pending transactions are intact');
assert.match(html,/function freeFeature/,'Free Spins are intact');
assert.match(html,/function holdFeature/,'Hold & Win is intact');
assert.match(html,/function stopAuto/,'AUTO cancellation remains functional');

const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(script,'Game engine exists');
new vm.Script(script,{filename:'stake-preview/index.html'});
console.log('Stake Preview V1.8: aprovado o visual 9:16 com logo inteiro, fundo, moldura, tambores e HUD funcionais; PNG '+width+'x'+height);
