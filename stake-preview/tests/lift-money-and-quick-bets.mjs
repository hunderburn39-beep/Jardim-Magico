import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
const css=html.match(/<style>([\s\S]*?)<\/style>/)?.[1]||'';
const patch=css.split('/* V3.4 — SALDO, APOSTA e APOSTAS RÁPIDAS')[1]?.split('</style>')[0];
assert.ok(patch,'Latest layout adjustment exists');
assert.match(patch,/\.money-row\{top:69\.3%!important\}/);
assert.match(patch,/\.bet-shortcuts\{top:76\.4%!important\}/);
assert.match(patch,/\.notice\{top:65\.2%!important\}/);
assert.match(patch,/max-height:700px/);
assert.match(patch,/\.money-row\{top:68\.7%!important\}/);
assert.match(patch,/\.bet-shortcuts\{top:76\.0%!important\}/);
assert.match(patch,/max-height:560px/);
assert.match(patch,/\.money-row\{top:65\.8%!important\}/);
assert.match(patch,/\.bet-shortcuts\{top:73\.8%!important\}/);
assert.ok(html.includes('id="openBonusBuy"') && html.includes('id="paytableBtn"'));
assert.ok(html.includes('jardim-magico-fundo-aprovado-original.png'));
assert.match(html, /--jm-fit:98%!important/);
const mobile=[
  {label:'iPhone normal',oldMoney:72.8,oldQuick:79.9,newMoney:69.3,newQuick:76.4,moneyHeight:6.3},
  {label:'iPhone compacto',oldMoney:72.2,oldQuick:79.5,newMoney:68.7,newQuick:76.0,moneyHeight:6.5},
  {label:'ecrã muito curto',oldMoney:69.3,oldQuick:77.3,newMoney:65.8,newQuick:73.8,moneyHeight:7.2}
];
for(const v of mobile){
  assert.ok(Math.abs(v.oldMoney-v.newMoney-3.5)<.0001,v.label+' money moved 3.5 points up');
  assert.ok(Math.abs(v.oldQuick-v.newQuick-3.5)<.0001,v.label+' quick bets moved 3.5 points up');
  assert.ok(v.newQuick>=v.newMoney+v.moneyHeight+.5,v.label+' balance does not overlap quick bets');
}
const js=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
new Function(js);
console.log('Jardim Mágico: balance + stake and quick bets raised together by 3.5% viewport (~30% of free vertical gap), preserves mini bonus shop and original art.');
