import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script);
new vm.Script(script);
const source=script.split('$("introPlay").addEventListener')[0];
const head=source.match(/^const WEIGHTS=.*$/m)?.[0];
const count=source.slice(source.indexOf('function count(b,id)'),source.indexOf('function collapse(b,removed,free)'));
assert.ok(head && count,'Buy trigger and evaluation functions are present');
const gen=source.slice(source.indexOf('function generate(mode)'),source.indexOf('function format(x)'));
const context=vm.createContext({
  Math,console,
  freeFeature:()=>({amount:13579,events:[{kind:'freeStart',remaining:15},{kind:'freeEnd',amount:13579}]}),
  holdFeature:()=>({amount:24680,events:[{kind:'hold',turn:0},{kind:'holdEnd',amount:24680}]}),
});
vm.runInContext(head+'\n'+count+'\n'+gen,context,{timeout:10000});

function js(code){return vm.runInContext(code,context)}
const base=js('purchasedBonusTriggerBoard("free")');
const coins=js('purchasedBonusTriggerBoard("hold")');
for(const [mode,b,type,n] of [['free',base,9,3],['hold',coins,11,6]]){
  assert.equal(b.length,5,mode+' must show five reels');
  assert.ok(b.every(col=>col.length===3),mode+' must show three symbols per reel');
  assert.equal(b.flat().filter(id=>id===type).length,n,mode+' guaranteed activation');
  assert.equal(b.flat().filter(id=>id===(mode==='free'?11:9)).length,0,'Only the selected feature triggers');
  context.fixture=b;
  assert.equal(js('evaluate(fixture).lines.length'),0,
    mode+' activation board cannot pay imaginary base paylines');
  assert.equal(js('evaluate(fixture).removed.length'),0,
    mode+' activation never cascades nor changes prize accounting');
}
assert.equal(base.flat().filter(id=>id===9).length,3);
assert.equal(coins.flat().filter(id=>id===11).length,6);
assert.equal(js('purchasedBonusTriggerBoard("free").flat().join(",")'),base.flat().join(','),
  'The presentation is reproducible without changing RNG outcomes');

const free=js('generate("free")');
const hold=js('generate("hold")');
assert.equal(free.cost,20);
assert.equal(hold.cost,30);
assert.equal(free.payout,13579,'Purchased Free Spins keep their generated result exactly');
assert.equal(hold.payout,24680,'Purchased Hold keeps its generated result exactly');
assert.equal(free.events[0].kind,'purchaseTrigger');
assert.equal(hold.events[0].kind,'purchaseTrigger');
assert.equal(free.events[1].kind,'freeStart','3 SCATTER spin happens before free bonus');
assert.equal(hold.events[1].kind,'hold','6 COINS spin happens before 4x4 bonus');
assert.equal(free.events[0].mode,'free');
assert.equal(hold.events[0].mode,'hold');
assert.deepEqual(JSON.parse(JSON.stringify(free.events[0].board)),JSON.parse(JSON.stringify(base)));
assert.deepEqual(JSON.parse(JSON.stringify(hold.events[0].board)),JSON.parse(JSON.stringify(coins)));
assert.ok(!('win' in free.events[0])&&!('win' in hold.events[0]));
assert.ok(!('payout' in free.events[0])&&!('payout' in hold.events[0]),
  'Activation event must have no independent payout or second debit');

assert.match(source,/async function showPurchasedBonusTrigger\(evt\)/);
assert.match(source,/await animateReelStop\(evt\.board\)/,'Trigger uses actual rolling reels before bonus starts');
assert.match(source,/if\(evt\.kind==="purchaseTrigger"\)\{\s*await showPurchasedBonusTrigger\(evt\);\s*\}else if\(evt\.kind==="freeStart"\)/,
 'The trigger event must be displayed before the feature modal');
assert.match(source,/if\(count\(evt\.board,9\)!==3\)/);
assert.match(source,/if\(count\(evt\.board,11\)!==6\)/);
assert.match(source,/frame\.classList\.add\("hold-purchase-trigger"\)/);
assert.match(source,/banner\.textContent="✦ 6 MOEDAS · HOLD & WIN ATIVADO ✦"/);
assert.match(source,/if\(skipAnimations\)return/,'PASSAR can bypass the triggering ceremony');
assert.match(source,/function detailFrames\(record\)\{return record\.book\.events\.filter\(evt=>evt\.kind==="purchaseTrigger"/);
assert.match(source,/function clearScatterScene\(\)/);
assert.match(source,/frame\?\.classList\?\.remove\("scatter-tease","scatter-bonus","hold-purchase-trigger"\)/);

const css=html.match(/<style>([\s\S]*?)<\/style>/)?.[1];
assert.match(css,/#reelFrame\.hold-purchase-trigger #reels \.cell img\.jmSymbolArt\[data-symbol-id="11"\]/);
assert.ok(html.includes('assets/figuras-3d-aprovadas-20261114/scatter-3d.png'));
assert.ok(html.includes('assets/jardim-magico-fundo-aprovado-original.png'));
console.log('Purchased Free Spins: guaranteed three SCATTER on a rolling spin; purchased Hold: guaranteed six COINS; playback, history and precomputed amounts untouched.');
