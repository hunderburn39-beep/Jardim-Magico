import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';
const file=join(dirname(fileURLToPath(import.meta.url)),'..','index.html');
const html=readFileSync(file,'utf8');
const css=html.match(/<style>([\s\S]*?)<\/style>/)?.[1];
const code=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(code&&css,'The game contains CSS and JavaScript');
const shop=html.match(/<div class="overlay" id="bonusBuyOverlay"[\s\S]*?<div class="overlay" id="freeResultOverlay"/)?.[0];
assert.ok(shop,'The bonus shop modal exists');
assert.equal((html.match(/id="openBonusBuy"/g)||[]).length,1,'Exactly one small shop button');
assert.equal((html.match(/id="buyFree"/g)||[]).length,1);
assert.equal((html.match(/id="buyHold"/g)||[]).length,1);
assert.match(shop,/id="buyFree"/);
assert.match(shop,/id="buyHold"/);
assert.match(shop,/id="closeBonusBuy"/);
const lower=html.match(/<div class="bet-shortcuts"[\s\S]*?<\/div>/)?.[0];
assert.match(lower,/id="openBonusBuy"/,'Compact purchase button stays in the lower quick-action bar');
assert.doesNotMatch(lower,/id="buyFree"|id="buyHold"/,'Large purchase buttons must not remain in the main screen');
assert.match(css,/\.bet-shortcuts #openBonusBuy\{/);
assert.match(css,/\.bonus-buy-modal \.bonus-row\{/);
assert.match(css,/\.bonus-buy-modal \.bonus-btn\{/);
assert.match(code,/\$\("openBonusBuy"\)\.addEventListener\("click",openBonusShop\)/);
assert.match(code,/\$\("buyFree"\)\.addEventListener\("click",\(\)=>\{closeBonusShop\(\);void play\("free"\)\}\)/);
assert.match(code,/\$\("buyHold"\)\.addEventListener\("click",\(\)=>\{closeBonusShop\(\);void play\("hold"\)\}\)/);
assert.match(code,/\$\("bonusBuyOverlay"\)\.addEventListener\("click"/);
assert.match(code,/\["spin","minus","plus","openBonusBuy","buyFree","buyHold"/,'Shop disables during spins and AUTO');
assert.match(html,/jardim-magico-fundo-aprovado-original\.png/,'Approved exact image remains');
new vm.Script(code);

const elements=new Map();
function el(id){
 if(!elements.has(id)){
  const classes=new Set(),span={textContent:''};
  elements.set(id,{
   id,hidden:false,disabled:false,textContent:'',innerHTML:'',dataset:{},style:{},
   classList:{add(k){classes.add(k)},remove(k){classes.delete(k)},contains(k){return classes.has(k)},toggle(k,v){if(v)classes.add(k);else classes.delete(k)}},
   setAttribute(k,v){this[k]=v},querySelector(q){return q==='span'?span:null},
   querySelectorAll(){return []},span
  });
 }
 return elements.get(id);
}
const ctx=vm.createContext({
 document:{hidden:false,getElementById:el,querySelectorAll(){return []}},
 localStorage:{getItem(){return null},setItem(){}},
 Date,Math,Intl,Uint32Array,console,setTimeout(){return 1},clearTimeout(){}
});
vm.runInContext(code.split('$("introPlay").addEventListener')[0],ctx,{timeout:10000});
const run=expression=>vm.runInContext(expression,ctx);
const balance=run('balance');
assert.equal(run('openBonusShop()'),true,'Opens while idle');
assert.equal(el('bonusBuyOverlay').classList.contains('show'),true);
assert.equal(el('openBonusBuy')['aria-expanded'],'true');
assert.match(el('buyFree').span.textContent,/20×/);
assert.match(el('buyFree').span.textContent,/4,00/,'20x costs 4.00 at 0.20 bet');
assert.match(el('buyHold').span.textContent,/30×/);
assert.match(el('buyHold').span.textContent,/6,00/,'30x costs 6.00 at 0.20 bet');
assert.equal(run('balance'),balance,'Opening the shop never debits');
run('closeBonusShop()');
assert.equal(el('bonusBuyOverlay').classList.contains('show'),false);
assert.equal(el('openBonusBuy')['aria-expanded'],'false');
assert.equal(run('balance'),balance,'Cancelling never debits');
run('busy=true');
assert.equal(run('openBonusShop()'),false,'Cannot open while reels are active');
run('busy=false;autoLeft=4');
assert.equal(run('openBonusShop()'),false,'Cannot open during auto spins');
run('autoLeft=0;betIndex=BETS.indexOf(7.5)');
assert.equal(run('openBonusShop()'),true);
assert.match(el('buyFree').span.textContent,/150,00/,'Prices update for selected 7.50 bet');
assert.match(el('buyHold').span.textContent,/225,00/);
assert.equal(run('balance'),balance);
console.log('Jardim Mágico: tiny lower BÓNUS button opens/cancels two priced purchases; no debit before choice, AUTO/busy guarded.');
