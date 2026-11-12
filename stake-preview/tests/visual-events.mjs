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
assert.match(html, /V1\.0/);
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
assert.equal(vm.runInContext('money(1000)',context),'75,00','10x aposta de 7,50 são 75 créditos');
assert.equal(vm.runInContext('money(100)',context),'7,50','1x aposta de 7,50 são 7,50 créditos');
assert.equal(vm.runInContext('format(BETS[betIndex]*30)',context),'225,00','Compra Hold 30x na aposta de 7,50');
const linesShown=[];
Object.defineProperty(el('paylineSvg'),'innerHTML',{
  get(){return this._lineHtml||''},
  set(value){this._lineHtml=value;if(value)linesShown.push(value)}
});
context.lineEvent={lines:[{line:1,amount:150,cells:[[0,1],[1,1],[2,1]]}],multi:2,free:false};
await vm.runInContext('highlightLines(lineEvent)',context);
assert.ok(linesShown.some(x=>x.includes('polyline')),'Traçado da linha premiada desenhado');
assert.ok(linesShown.some(x=>x.includes('250,150')),'Linha de três peças termina no terceiro tambor');
assert.ok(linesShown.every(x=>!x.includes('450,150')),'Linha de três peças não marca o quinto tambor');
assert.equal(el('linePrize').hidden,true,'Linha desvanece depois de apresentada');
assert.equal(el('linePrize').textContent,'LINHA 1 · +22,50','Valor da linha multiplicado pela aposta 7,50');
context.lineEvent.free=true;
await vm.runInContext('highlightLines(lineEvent)',context);
assert.equal(el('linePrize').textContent,'LINHA 1 · BRUTO 11,25','Linha de Free Spins mostra bruto, não pagamento antecipado');
const initial=vm.runInContext('board()',context);
context.initial=initial;
await vm.runInContext('animateReelStop(initial)',context);
assert.ok(el('reels').innerHTML.includes('class="column"'));
await vm.runInContext('animateCascade(initial,[[0,0],[3,1]])',context);
const bought=vm.runInContext('generate("hold")',context);
assert.equal(bought.cost,30);
assert.ok(bought.events.length>3);
context.bought=bought;
vm.runInContext('skipAnimations=true',context);
await vm.runInContext('playback(bought)',context);
vm.runInContext('skipAnimations=false',context);
assert.equal(el('holdOverlay').classList.contains('show'),false,'Hold tem de fechar no fim');
assert.match(el('holdText').textContent,/HOLD & WIN TERMINADO|GRELHA COMPLETA/);
assert.match(el('holdWin').textContent,/créditos/);
const base=vm.runInContext('generate("base")',context);
context.base=base;
await vm.runInContext('playback(base)',context);
assert.ok(el('reels').innerHTML.includes('class="column"'));
const free=vm.runInContext('generate("free")',context);
context.free=free;
vm.runInContext('skipAnimations=true',context);
await vm.runInContext('playback(free)',context);
assert.equal(el('freePanel').hidden,true,'HUD Free Spins fecha depois de pagar o bónus');
assert.equal(String(el('freeRemaining').textContent),'0','Contagem de Free Spins concluída');
assert.match(el('freeRaw').textContent,/\d+,\d{2}/,'Total bruto de Free Spins mostrado');
assert.match(el('freeMult').textContent,/^×\d+/,'Multiplicador de Free Spins mostrado');
vm.runInContext('skipAnimations=true',context);
const preBalance=vm.runInContext('balance',context);
await vm.runInContext('play("hold")',context);
const postBalance=vm.runInContext('balance',context);
const paid=vm.runInContext('lastWin',context);
assert.ok(Math.abs(postBalance-(preBalance-225+paid))<1e-6,'Saltar animações não altera custo nem pagamento do Hold');
assert.equal(el('skipBtn').hidden,true,'Botão PASSAR esconde-se após terminar');
assert.equal(el('paylineSvg').innerHTML,'','Linha SVG desaparece após vitória');
assert.equal(vm.runInContext('finalWinTier(750,7.5)',context),'SENSACIONAL');
assert.equal(vm.runInContext('finalWinTier(75,7.5)',context),'BIG WIN');
console.log('Jardim Stake Preview V1.0: tambores, linhas até prémio real, Free Spins, Hold, 7,50 e passar animações validados.');
