/* Auditoria V72 — node scripts/auditar-rtp-v72.cjs --spins 20000
 * Usa o gerador, avaliador e cascatas do motor real, sem executar as animacoes.
 * Apenas creditos ficticios; RTP nao certificado.
 */
'use strict';
function between(s,a,b){const i=s.indexOf(a),j=s.indexOf(b,i+a.length);if(i<0||j<0)throw Error('Marcador nao encontrado: '+a);return s.slice(i,j);}
function calibration(s){
 const x=s.match(/const RTP_CALIBRATION=\{base:([\d.]+),free:([\d.]+),hold:([\d.]+),holdPurchase:([\d.]+)\}/);
 const y=s.match(/const FREQUENT_RTP_CALIBRATION=\{base:([\d.]+),free:([\d.]+)\}/);
 if(!x||!y)throw Error('Calibracao nao encontrada');
 return {base:+x[1],free:+x[2],hold:+x[3],holdPurchase:+x[4],frequentBase:+y[1],frequentFree:+y[2]};
}
function engine(source,mode,seed,c){
 const core=[between(source,'const BETS=','const CASH_PIN='),
 between(source,'function chooseSpinMul()','/* V59'),
 between(source,'const FREQUENT_SYMBOL_WEIGHTS=','function flyCoinToVault'),
 between(source,'function wholeGrid()','function mulIndices'),
 between(source,'function buildStickyColumn','async function animateCascade')].join('\n');
 const setup=[
 'let seed='+JSON.stringify(seed)+'>>>0;const rng=()=>{seed^=seed<<13;seed^=seed>>>17;seed^=seed<<5;return (seed>>>0)/4294967296};',
 'const R=n=>Math.floor(rng()*n),pick=a=>a[R(a.length)];',
 'let symbolSeq=0,freeSpins=0,awardContext="base",volatilityMode='+JSON.stringify(mode)+',bonus=null,betIdx=2;',
 'const RTP_CALIBRATION='+JSON.stringify({base:c.base,free:c.free,hold:c.hold,holdPurchase:c.holdPurchase})+';',
 'const FREQUENT_RTP_CALIBRATION='+JSON.stringify({base:c.frequentBase,free:c.frequentFree})+';',
 'const rtpScaleFor=k=>{const type=k==="hold"&&bonus?.origin==="compra"?"holdPurchase":k;const t=volatilityMode==="frequentes"&&(type==="free"||type==="base")?FREQUENT_RTP_CALIBRATION:RTP_CALIBRATION;return t[type]??RTP_CALIBRATION[type]??1;};',
 'const roundedPayout=v=>v>0?Math.floor(v)+(rng()<v-Math.floor(v)?1:0):0;',
 'const W=900,H=535,CH=H/3;',core,
 'return {rng,pick,round:roundedPayout,factor:chooseHoldFactor,',
 'setMode:(free,origin)=>{freeSpins=free?1:0;awardContext=free?"free":"base";bonus=origin?{origin}:null;},grid:wholeGrid,evaluate,',
 'fall:(g,removed,last)=>{const next=g.map(c=>c.slice());for(let x=0;x<5;x++){const rows=[];for(const k of removed){const [col,row]=k.split(":");if(+col===x)rows.push(+row)}',
 'if(rows.length)next[x]=buildStickyColumn(g[x],new Set(rows),()=>{if(!last)return sym();for(let i=0;i<12;i++){const v=sym();if(v.id!=="mul"&&v.id!=="coin")return v;}return {id:pick(regular)};}).next;}return next;}};'
 ].join('\n').replaceAll('Math.random()','rng()');
 return new Function(setup)();
}
function simulate(source,opt={}){
 const {mode='equilibrado',seed=23812,spins=15000,stake=20,freeBuyRate=0,holdBuyRate=0,initialFundX=1000,overrides={}}=opt;
 const c={...calibration(source),...overrides},e=engine(source,mode,seed,c);
 const kinds=['ULTRA','MAX','MED','MIN'],multipliers={ULTRA:5000,MAX:1000,MED:500,MIN:250},
  rates={ULTRA:1.35,MAX:.75,MED:.50,MIN:.40},jp={ULTRA:0,MAX:0,MED:0,MIN:0},jpCarry={ULTRA:0,MAX:0,MED:0,MIN:0};
 let pool=Math.round(stake*initialFundX),ownerCarry=0,poolCarry=0,vaultCoins=0,net=0,minNet=0;
 const s={wager:0,base:0,free:0,hold:0,jackpot:0,baseHits:0,freeBuys:0,holdBuys:0,
 freeTriggers:0,holdTriggers:0,freeRounds:0,retriggers:0,limits:0,underpaid:0,minPool:pool,maxCash:0};
 const pick=a=>e.pick(a);
 function fund(w){s.wager+=w;const o=w*.04+ownerCarry,own=Math.floor(o+1e-10);ownerCarry=o-own;
  const a=w*.96+poolCarry,main=Math.min(w-own,Math.floor(a+1e-10));poolCarry=a-main;
  let toJp=0;for(const k of kinds){const q=w*rates[k]/100+jpCarry[k],n=Math.min(main-toJp,Math.floor(q+1e-10));jpCarry[k]=q-n;jp[k]+=n;toJp+=n;}
  pool+=main-toJp;net+=main-toJp;
 }
 function pay(amount,type){const wanted=Math.max(0,Math.round(amount)),paid=Math.min(pool,wanted);pool-=paid;s[type]+=paid;
  net-=wanted;minNet=Math.min(minNet,net);s.maxCash=Math.max(s.maxCash,paid);s.minPool=Math.min(s.minPool,pool);
  if(paid<wanted){s.limits++;s.underpaid+=wanted-paid;}return paid;
 }
 function jackpot(kind,limit){if(!kind)return 0;const paid=Math.min(jp[kind],stake*multipliers[kind],limit);
  jp[kind]-=paid;s.jackpot+=paid;return paid;
 }
 const wheel=['MIN','MAX','MIN','MED','MIN','RESPIN','MIN','MED','MIN','MAX','MIN','MED','MIN','MAX','MIN','MED',
 'MIN','ULTRA','MIN','MED','MIN','MAX','MIN','MED','MIN','RESPIN','MIN','MED','MIN','MAX','MIN','MED'];
 function spin(free){
  e.setMode(free);let grid=e.grid(),scat=0,lines=0,balls=[],coins=0,six=false;const seen=new Set(),pure=new Set();
  const testSix=()=>{if(grid.reduce((a,col)=>a+col.filter(p=>p.id==='coin').length,0)>=6)six=true;};testSix();
  for(let cascade=1;cascade<=12;cascade++){
   scat=Math.max(scat,grid.flat().filter(p=>p.id==='scatter').length);
   const ev=e.evaluate(grid,stake,pure),removed=new Set();
   lines+=free?ev.amount:Math.max(0,Math.min(ev.amount,Math.min(pool,stake*5000)-lines));
   for(const w of ev.won)for(const [x,y] of w.points)if(grid[x][y].id!=='wild')removed.add(x+':'+y);
   for(let x=0;x<5;x++)for(let y=0;y<3;y++){const p=grid[x][y],key=x+':'+y;
    if(p.id==='mul'){removed.add(key);balls.push(p.value)}
    if(p.id==='coin'&&!seen.has(p.uid)){seen.add(p.uid);removed.add(key);coins++}
   }
   if(!removed.size)break;
   grid=e.fall(grid,removed,cascade===12);scat=Math.max(scat,grid.flat().filter(p=>p.id==='scatter').length);testSix();
  }
  vaultCoins+=coins;return {scat,lines,balls,coins,six};
 }
 function hold(bought){
  s.holdTriggers++;e.setMode(false,bought?'compra':'cofre');
  const b={cells:Array(16).fill(null),coins:0,factors:[],gems:{ULTRA:0,MAX:0,MED:0,MIN:0},focus:pick(kinds),jp:null,end:false,reason:'',left:3};
  const empty=()=>{const a=[];for(let i=0;i<16;i++)if(!b.cells[i])a.push(i);for(let i=a.length-1;i>0;i--){const j=Math.floor(e.rng()*(i+1));[a[i],a[j]]=[a[j],a[i]];}return a;};
  function add(i,kind,v){if(b.cells[i])return false;
   if(kind==='coin'){const value=e.round(v*(bought?c.holdPurchase:c.hold));b.cells[i]={kind,value};b.coins+=value}
   else if(kind==='factor'){b.cells[i]={kind,value:v};b.factors.push(v)}
   else{if(b.gems[v]>=4)return false;b.cells[i]={kind,type:v};b.gems[v]++;if(b.gems[v]===4){b.jp=v;b.end=true;b.reason='jackpot'}}
   return true;
  }
  const spots=empty();for(let i=0;i<7;i++)add(spots[i],'coin',stake*pick([1,2,2,3,5,8,10]));
  add(spots[7],'gem',b.focus);if(e.rng()<.012)add(spots[8],'factor',e.factor());
  for(let loop=0;!b.end&&loop<300;loop++){
   const a=empty();let added=0,n=0;
   if(e.rng()>=.495)n=1+(e.rng()<.39?1:0)+(e.rng()<.17?1:0);
   for(let i=0;i<n&&a.length&&!b.end;i++){const idx=a.pop(),p=e.rng();
    const ok=p<.82?add(idx,'coin',stake*pick([1,1,1,2,2,3,5,5,8,10])):p<.87?add(idx,'factor',e.factor()):add(idx,'gem',e.rng()<.58?b.focus:pick(kinds));
    if(ok)added++;
   }
   b.left=added?3:Math.max(0,b.left-1);
   if(b.cells.every(Boolean)){b.end=true;b.reason='completo'}
   else if(!b.end&&b.left===0){b.end=true;b.reason='tentativas'}
  }
  const factor=b.factors.length?b.factors.reduce((a,v)=>a+v,0):1;
  const cash=pay(Math.min(b.coins*factor,stake*5000),'hold');let left=stake*5000-cash;
  if(b.jp)left-=jackpot(b.jp,left);
  if(b.reason==='completo'){let chosen='RESPIN',loops=0;while(chosen==='RESPIN'&&loops++<64)chosen=pick(wheel);
   jackpot(chosen==='RESPIN'?'MIN':chosen,left);}
 }
 function freeSequence(){
  let left=15,ext=0,base=0,balls=[],guard=0;
  while(left>0&&guard++<65){left--;s.freeRounds++;const r=spin(true);base+=r.lines;balls.push(...r.balls);
   if(r.six)hold(false);
   const award=r.scat>=3?15:r.scat===2?8:0;
   if(award&&ext<2){left+=award;ext++;s.retriggers++;}
  }
  const m=balls.length?balls.reduce((a,v)=>a+v,0):1;pay(Math.min(base*m,stake*5000),'free');
 }
 for(let i=0;i<spins;i++){
  fund(stake);const r=spin(false);if(r.lines>0)s.baseHits++;
  const m=r.balls.length?r.balls.reduce((a,v)=>a+v,0):1;pay(Math.min(r.lines*m,stake*5000),'base');
  if(r.six)hold(false);
  else if(r.scat>=3){s.freeTriggers++;freeSequence();}
  else if(r.coins&&vaultCoins>=8&&pool>=Math.max(20,stake*5)&&e.rng()<.018){vaultCoins=0;hold(false)}
  if(e.rng()<freeBuyRate){fund(stake*20);s.freeBuys++;freeSequence()}
  if(e.rng()<holdBuyRate){fund(stake*100);s.holdBuys++;hold(true)}
 }
 const total=s.base+s.free+s.hold+s.jackpot,pct=n=>+(100*n/s.wager).toFixed(2);
 return {mode,stakeEur:stake/100,spins,seed,initialFundX,freeBuyRate,holdBuyRate,
 rtp:pct(total),base:pct(s.base),free:pct(s.free),hold:pct(s.hold),jackpot:pct(s.jackpot),
 wagerEur:s.wager/100,paidEur:total/100,freeBuys:s.freeBuys,holdBuys:s.holdBuys,
 freeTriggers:s.freeTriggers,holdTriggers:s.holdTriggers,
 baseHitPct:+(100*s.baseHits/spins).toFixed(2),limits:s.limits,
 unpaidEur:s.underpaid/100,poolEndEur:pool/100,poolMinEur:s.minPool/100,
 requiredInitialFundObservedEur:Math.max(0,-minNet)/100,
 jackpotBalanceEur:Object.values(jp).reduce((a,b)=>a+b,0)/100};
}
function suite(source,options={}){
 const spins=options.spins||15000,seed=options.seed||23812,stake=options.stake||20,overrides=options.overrides||{};
 const sets=[
 ['base_sem_compras',0,0,1000],['compras_moderadas',.01,.002,1000],['compras_intensas',.10,.01,1000],
 ['fundo_vazio',.01,.002,0],['fundo_5000x',.01,.002,5000]
 ];
 return {seed,spins,stakeEur:stake/100,calibration:{...calibration(source),...overrides},
 results:['equilibrado','frequentes'].flatMap(mode=>sets.map(([name,freeBuyRate,holdBuyRate,initialFundX])=>({
 scenario:name,...simulate(source,{mode,spins,seed,stake,freeBuyRate,holdBuyRate,initialFundX,overrides})
 })))};
}
module.exports={simulate,suite,calibration};
if(typeof require!=='undefined'&&require.main===module){
 const fs=require('node:fs'),path=require('node:path'),args=process.argv.slice(2);
 const get=(flag,def)=>{const idx=args.indexOf(flag);return idx<0?def:Number(args[idx+1]);};
 const source=fs.readFileSync(path.resolve(__dirname,'../js/jogo-2.js'),'utf8');
 process.stdout.write(JSON.stringify(suite(source,{spins:get('--spins',15000),seed:get('--seed',23812),stake:get('--stake',20)}),null,2)+'\n');
}
