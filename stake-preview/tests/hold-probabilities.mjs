import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { dirname, join } from 'node:path';
import vm from 'node:vm';

const html = readFileSync(join(dirname(fileURLToPath(import.meta.url)), '..', 'index.html'), 'utf8');
const match = html.match(/<script>([\s\S]*?)<\/script>/);
assert.ok(match, 'Inline game JavaScript exists');
const code = match[1].split('$("spin").addEventListener')[0];
assert.ok(code.includes('function holdFeature()'), 'Hold logic present');
let seed=0x1234abcd;
const fakeCrypto={getRandomValues(values) {
  seed = (Math.imul(seed,1664525) + 1013904223) >>> 0;
  values[0]=seed; return values;
}};
const ctx=vm.createContext({crypto:fakeCrypto,Uint32Array,console,Intl,Math,setTimeout,clearTimeout});
vm.runInContext(code,ctx,{timeout:10000});
const playHold=vm.runInContext('holdFeature',ctx);
const sample=10000;
let completed=0, amountSum=0, turnsSum=0, incomplete=0;
for(let i=0;i<sample;i++){
  const result=playHold();
  const entries=result.events, last=entries.at(-1);
  assert.equal(entries[0].kind,'hold');
  assert.equal(entries[0].grid.filter(Boolean).length,6);
  assert.equal(last.kind,'holdEnd');
  assert.equal(last.grid.length,16);
  assert.equal(last.filled,last.grid.every(Boolean));
  assert.equal(last.amount,result.amount);
  assert.equal(last.amount,100*last.grid.reduce((a,b)=>a+b,0)+(last.filled?25000:0));
  const turns=entries.length-2;
  assert.ok(turns >= 1 && turns<=35, 'Hold finishes, no infinite respins');
  assert.ok(last.grid.filter(Boolean).length>=6);
  if(last.filled) completed++; else incomplete++;
  amountSum+=result.amount/100;
  turnsSum+=turns;
}
const frequency=completed/sample;
const mean=amountSum/sample;
assert.ok(frequency>0 && frequency<.06,'Not every Hold is full; full board remains rare, observed '+frequency);
assert.ok(incomplete>sample*.94,'Most Hold rounds do not fill all 16 slots');
assert.ok(mean>15 && mean<45,'Average Hold value plausible for 30x cost; observed '+mean);
assert.ok(BETS_DO_NOT_AFFECT_RESULTS(),'Hold RNG is independent of bet, including 7.50');
function BETS_DO_NOT_AFFECT_RESULTS() {
  // The function does not receive or reference stake; 7.50 only scales cost/payout.
  const source=html.slice(html.indexOf('function holdFeature()'),html.indexOf('function generate(mode)'));
  return !/\bbet\b|\bbalance\b|\bbetIndex\b/.test(source)
    && html.includes('7.5') && html.includes('mode==="hold"?30:1');
}
console.log('Hold 4x4 preview test passed:',JSON.stringify({
  spins:sample, fullRatePercent:+(100*frequency).toFixed(2),
  meanPayoutXBaseBet:+mean.toFixed(2), averageTurns:+(turnsSum/sample).toFixed(2),
  incomplete, costAt7_50:225
}));
