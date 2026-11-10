const test=require('node:test'),a=require('node:assert/strict');
const E=require('../js/engine.js'),V=require('./engine-v06.cjs'),S=require('../js/session.js');
function rng(seed=42){return()=>{seed|=0;seed=seed+0x6D2B79F5|0;let t=Math.imul(seed^seed>>>15,1|seed);t=t+Math.imul(t^t>>>7,61|t)^t;return((t^t>>>14)>>>0)/4294967296;};}
const quiet=()=>[['10','J','Q'],['K','A','10'],['J','Q','K'],['A','10','J'],['Q','K','A']];
function fixture(s,grid,{free=false,nextSymbol=null,paylinesVersion=E.PAYLINES_VERSION}={}){s.pending={id:++s.sequence,grid,free,bet:S.BETS[s.betIndex],lines:s.lines,nextSymbol,paylinesVersion};return s;}
test('prémios aprovados, pesos e símbolos preservados; dez linhas iguais à foto',()=>{
 for(const k of ['PAYS','WEIGHTS','NORMAL','ALL'])a.deepEqual(E[k],V[k]);
 a.deepEqual(E.LEGACY_LINES,V.LINES);
 a.equal(E.PAYLINES_VERSION,2);
 a.deepEqual(E.LINES,[
  [1,1,1,1,1],[0,0,0,0,0],[2,2,2,2,2],[0,1,2,1,0],[2,1,0,1,2],
  [1,0,0,0,1],[1,2,2,2,1],[0,0,1,2,2],[2,2,1,0,0],[1,0,1,2,1]
 ]);
 a.deepEqual(E.startFeature(()=>0),V.startFeature(()=>0));
});
test('10000 unique reel grids preserve the V0.6 payout evaluator',()=>{const r=rng(0xE6);for(let n=0;n<10000;n++){const seed=(r()*0xffffffff)>>>0,g=E.makeGrid(rng(seed));a.ok(g.every(col=>new Set(col).size===3),'Repetição no tambor da ronda '+n);const special=E.NORMAL.filter(()=>r()<.35),bet=S.BETS[n%8],lines=n%10+1;a.deepEqual(E.evaluate(g,bet,lines,special,E.LEGACY_LINES),V.evaluate(g,bet,lines,special));}});
test('jogadas novas guardam versão 2; antigas são liquidadas pela geometria anterior',()=>{
 const s=S.fresh(),r=rng(217);
 S.begin(s,r);
 a.equal(s.pending.paylinesVersion,2);
 const newId=s.pending.id;
 S.settle(s);
 a.equal(s.history[0].id,newId);
 a.equal(s.history[0].paylinesVersion,2);
 // Simular uma jogada pendente antes da atualização: sem campo de versão.
 const old=S.fresh();
 const grid=quiet();grid[0][1]='KING';grid[1][2]='KING';
 const bet=S.stake(old),before=old.balanceU;
 old.balanceU-=S.units(bet); // Simular o débito já feito ao iniciar a jogada antiga.
 old.pending={id:++old.sequence,grid,free:false,bet,lines:10,nextSymbol:null};
 S.validate(old);
 const expected=E.evaluate(grid,bet,10,[],E.LEGACY_LINES);
 const out=S.settle(old);
 a.equal(out.result.total,expected.total);
 a.equal(old.balanceU,before-S.units(bet)+out.prizeU);
 a.equal(old.history[0].paylinesVersion,1);
});
test('all ten individual line geometries award their approved symbol pay',()=>{E.LINES.forEach((line,i)=>{const g=quiet();line.forEach((r,c)=>g[c][r]='KING');const win=E.evaluate(g,1,10).lineWins.find(w=>w.line===i);a.equal(win.count,5);a.equal(win.amount,500);});});
test('wild substitution chooses only the largest win on each line',()=>{const g=quiet();['TOMB','TOMB','KING','KING','10'].forEach((v,i)=>g[i][1]=v);const win=E.evaluate(g,1,1).lineWins[0];a.equal(win.symbol,'KING');a.equal(win.count,4);a.equal(win.amount,1000);});
test('3/4/5 scattered tombs award ×2/×20/×200 and activate',()=>{for(let n=3;n<=5;n++){const g=quiet();for(let c=0;c<n;c++)g[c][c%3]='TOMB';const r=E.evaluate(g,2,10);a.equal(r.scatterWin,2*({3:2,4:20,5:200}[n]));a.equal(r.trigger,true);}});
test('premium expansion pays on separated reels; cards need three distinct reels',()=>{let g=quiet();g[1][2]='ANUBIS';g[4][0]='ANUBIS';let r=E.evaluate(g,1,10,['ANUBIS']);a.equal(r.expanders[0].count,2);a.ok(r.lineWins.every(w=>w.amount>=.5));g=quiet();g[0]=['KING','KING','KING'];a.equal(E.evaluate(g,1,10,['KING']).expanders.length,0);});
test('simultaneous expanders never add together on the same line',()=>{const g=quiet();g[1][0]='KING';g[4][0]='KING';g[0][2]='ANUBIS';g[3][2]='ANUBIS';const r=E.evaluate(g,1,10,['KING','ANUBIS']);a.equal(r.expanders.length,2);a.ok(r.lineWins.every(w=>w.amount===1));a.equal(r.total,10);});
test('tomb is never an expanding normal symbol',()=>{a.equal(E.evaluate(quiet(),1,10,['TOMB']).expanders.length,0);});
test('10000 more spins never repeat a symbol in one reel',()=>{const r=rng(11);for(let i=0;i<10000;i++)a.ok(E.makeGrid(r).every(c=>new Set(c).size===3));});
test('constant RNG still yields three distinct symbols per reel',()=>{const g=E.makeGrid(()=>0);a.ok(g.every(c=>new Set(c).size===3));a.deepEqual(g[0],['10','J','Q']);});
test('pending paid spin survives serialization; settlement is idempotent',()=>{let s=S.fresh();S.begin(s,rng(2));const pending=JSON.stringify(s.pending),expected=E.evaluate(s.pending.grid,1,10).total;s=S.restore(JSON.parse(JSON.stringify(s)));a.equal(JSON.stringify(s.pending),pending);S.settle(s);a.equal(s.balanceU,S.units(299+expected));const b=s.balanceU;a.equal(S.settle(s),null);a.equal(s.balanceU,b);});
test('last free spin survives reload at zero remaining',()=>{let s=S.fresh();s.feature={left:1,played:0,totalU:0,symbols:['KING'],retriggers:0};S.begin(s,rng(6));a.equal(s.feature.left,0);s=S.restore(JSON.parse(JSON.stringify(s)));const r=S.settle(s);a.equal(r.free,true);a.ok(r.bonusDone||s.feature);});
test('insufficient credits cannot start or mutate a paid spin',()=>{const s=S.fresh();s.balanceU=0;const copy=JSON.stringify(s);a.throws(()=>S.begin(s),/insuficientes/);a.equal(JSON.stringify(s),copy);});
test('free spins do not debit credits',()=>{const s=S.fresh();s.balanceU=0;s.feature={left:8,played:0,totalU:0,symbols:['KING'],retriggers:0};S.begin(s,rng(16));a.equal(s.balanceU,0);a.equal(s.feature.left,7);});
test('eight initial spins, retrigger adds eight and a new nonduplicate symbol',()=>{const s=S.fresh(),g=quiet();for(let c=0;c<3;c++)g[c][0]='TOMB';fixture(s,g,{nextSymbol:'KING'});S.settle(s);a.equal(s.feature.left,8);fixture(s,g,{free:true,nextSymbol:'ANUBIS'});S.settle(s);a.equal(s.feature.left,16);a.deepEqual(s.feature.symbols,['KING','ANUBIS']);});
test('all nine symbols retain +8 retriggers without a tenth or duplicate',()=>{const s=S.fresh(),g=quiet();s.feature={left:1,played:20,totalU:0,symbols:[...E.NORMAL],retriggers:8};for(let c=0;c<3;c++)g[c][0]='TOMB';fixture(s,g,{free:true});S.settle(s);a.equal(s.feature.left,9);a.equal(s.feature.symbols.length,9);});
test('base and feature ceilings preserve the distinct V0.6 5000× limits',()=>{const s=S.fresh();fixture(s,Array.from({length:5},()=>['KING','KING','KING']));a.equal(S.settle(s).prizeU,S.units(5000));s.feature={left:1,played:0,totalU:S.units(4999),symbols:['KING'],retriggers:0};fixture(s,Array.from({length:5},()=>['KING','KING','KING']),{free:true});const r=S.settle(s);a.equal(r.prizeU,S.units(1));a.equal(r.bonusDone.totalU,S.units(5000));a.equal(s.feature,null);});
test('integer credit unit exactly represents every approved bet divided by 1–10 lines',()=>{for(const bet of S.BETS)for(let lines=1;lines<=10;lines++)a.equal(S.units(bet)%lines,0);});
test('gamble uses original winning stake cap after bet is changed',()=>{const s=S.fresh();s.lastBet=1;s.lastBaseWinU=S.units(100);s.balanceU=S.units(1000);s.betIndex=0;const r=S.gamble(s,'hearts',()=>0);a.equal(r.prizeU,S.units(400));a.equal(s.balanceU,S.units(1300));});
test('losing gamble debits only the last win and cannot be repeated',()=>{const s=S.fresh();s.lastBaseWinU=S.units(5);a.equal(S.gamble(s,'black',()=>0).won,false);a.equal(s.balanceU,S.units(295));a.throws(()=>S.gamble(s,'black',()=>0));});
test('five gamble attempts and invalid guesses are enforced',()=>{const s=S.fresh();s.lastBaseWinU=S.units(1);for(let i=0;i<5;i++)S.gamble(s,'red',()=>0);a.throws(()=>S.gamble(s,'red',()=>0));a.throws(()=>S.gamble(s,'bogus',()=>0));});
test('reset clears gamble eligibility and last prize',()=>{const s=S.fresh();s.lastBaseWinU=S.units(100);s.lastWinU=S.units(100);S.reset(s);a.equal(s.balanceU,S.units(300));a.equal(s.lastBaseWinU,0);a.equal(s.lastWinU,0);});
test('valid V0.6 credit session and bonus migrate',()=>{const s=S.migrate({balance:321.5,betIndex:2,lines:3,feature:{left:7,played:1,total:1.2,symbols:['ANUBIS'],retriggers:0},sound:false});a.equal(s.balanceU,S.units(321.5));a.equal(s.lines,3);a.equal(s.feature.left,7);a.equal(s.settings.sound,false);});
test('corrupt saves, invalid grids and feature symbols are rejected',()=>{for(const mutate of [s=>s.balanceU=NaN,s=>s.feature={left:2,played:0,totalU:0,retriggers:0,symbols:['BOGUS']},s=>s.grid[0][0]='BOGUS',s=>s.lines=11,s=>s.betIndex=8]){const s=S.fresh();mutate(s);a.throws(()=>S.restore(s));}});
test('2000 paid rounds and their complete bonuses maintain balance invariants',()=>{const s=S.fresh(),r=rng(1121);s.balanceU=S.units(100000);for(let i=0;i<2000;i++){s.betIndex=i%8;s.lines=i%10+1;do{const before=s.balanceU,free=!!s.feature,bet=S.stake(s);S.begin(s,r);const out=S.settle(s);a.equal(s.balanceU,before-(free?0:S.units(bet))+out.prizeU);a.ok(Number.isSafeInteger(s.balanceU));S.validate(s);}while(s.feature);}});
