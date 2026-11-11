import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {fileURLToPath} from 'node:url';
import {dirname,join} from 'node:path';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(script,'JS da pré-visualização existe');
const source=script.split('$("spin").addEventListener')[0];
assert.match(source,/async function animateReelStop/);
assert.match(source,/async function animateCascade/);
assert.match(source,/async function playback/);
assert.match(html, /data-bet="7\.5"/);
assert.match(html, /V0\.4/);
const elements=new Map();
function el(id) {
  if(!elements.has(id)) {
    const classes=new Set();
    elements.set(id,{
      id,innerHTML:'',textContent:'',style:{},dataset:{},
      classList:{add(x){classes.add(x)},remove(x){classes.delete(x)},contains(x){return classes.has(x)},toggle(x,v){if(v)classes.add(x);else classes.delete(x)}},
      setAttribute(k,v){this[k]=v},querySelectorAll(){return []},children:[],
    });
  }
  return elements.get(id);
}
const columns=Array.from({length:5},(_,col)=>({
  children:Array.from({length:3},(_,n)=>el('cell'+col+''+n)),
  querySelectorAll(){return this.children},
}));
const betButtons=[.2,1,7.5,10].map(x=>{const a=el('bet'+x);a.dataset.bet=String(x);return a});
const doc={
  getElementById:el,
  querySelectorAll(sel) {
    if(sel==='[data-bet]')return betButtons;
    if(sel==='.column')return columns;
    if(sel==='.cell')return columns.flatMap(col=>col.children);
    return [];
  },
};
let seed=742123;
const crypto={getRandomValues(values){seed=(Math.imul(seed,1664525)+1013904223)>>>0;values[0]=seed;return values;}};
let timer=0;
const context=vm.createContext({
  document:doc,window:{},crypto,Uint32Array,Math,console,Intl,
  setTimeout(fn){Promise.resolve().then(fn);return ++timer;},
  clearTimeout(){},
});
vm.runInContext(source,context,{timeout:10000});
vm.runInContext('betIndex=BETS.indexOf(7.5);display()',context);
assert.equal(el('bet').textContent,'7,50');
assert.equal(el('bet7.5').classList.contains('active'),true);
const initial=vm.runInContext('board()',context);
context.initial=initial;
await vm.runInContext('animateReelStop(initial)',context);
assert.ok(el('reels').innerHTML.includes('class="column"'));
await vm.runInContext('animateCascade(initial,[[0,0],[3,1]])',context);
const bought=vm.runInContext('generate("hold")',context);
assert.equal(bought.cost,30);
assert.ok(bought.events.length>3);
context.bought=bought;
await vm.runInContext('playback(bought)',context);
assert.equal(el('holdOverlay').classList.contains('show'),false,'Hold tem de fechar no fim');
assert.match(el('holdText').textContent,/HOLD & WIN TERMINADO|GRELHA COMPLETA/);
assert.match(el('holdWin').textContent,/créditos/);
const base=vm.runInContext('generate("base")',context);
context.base=base;
await vm.runInContext('playback(base)',context);
assert.ok(el('reels').innerHTML.includes('class="column"'));
console.log('Jardim Stake Preview V0.4: tambores, cascatas, aposta 7,50 e Hold visual verificados.');
