'use strict';
const assert=require('node:assert/strict');
const {chromium,webkit}=require('playwright');
const url='http://127.0.0.1:8123/index.html?qa=expansion-order';
(async()=>{
 for(const browserType of [chromium,webkit]){
  const browser=await browserType.launch({headless:true});
  try{
   const page=await browser.newPage({viewport:{width:390,height:844}});
   const errors=[];page.on('pageerror',err=>errors.push(err.message));
   await page.goto(url,{waitUntil:'domcontentloaded',timeout:20000});
   await page.waitForFunction(()=>window.__faraoGame?.ready===true,null,{timeout:10000});
   const state=await page.evaluate(()=>{
    const g=window.__faraoGame,E=window.TombEngine,S=window.FaraoSession;
    const grid=[['10','J','ANUBIS'],['KING','A','10'],['J','ANUBIS','K'],
      ['A','10','J'],['KING','K','A']];
    const symbols=['KING','ANUBIS','J','10'];
    const expected=E.evaluate(grid,g.bet,g.lines,symbols);
    const old=E.evaluate(grid,g.bet,g.lines,symbols,E.LINES,'legacy');
    g.ledger.feature={left:0,played:1,totalU:0,symbols,retriggers:0};
    g.ledger.pending={id:++g.ledger.sequence,grid,free:true,bet:S.stake(g.ledger),
      lines:g.ledger.lines,nextSymbol:null,paylinesVersion:2,expansionPayVersion:2};
    g.syncLedger();g.spinning=true;
    g.spinObject={grid,result:expected,free:true};
    g.finishSpin();
    const stages=g.reveal?.stages||[],counted=g.winCount?.stages||[];
    const start=g.winCount.start-1,end=g.reveal.end+10;
    const values=[g.displayWinAt(start),...stages.map(stage=>
      g.displayWinAt(stage.end+5)),g.displayWinAt(end)];
    const grouped=g.ledger.history[0];
    const round=grouped.kind==='free-bonus'?grouped.rounds[0]:grouped;
    const total=grouped.win;
    const number=round.id;
    return {order:stages.map(s=>s.symbol),startTimes:stages.map(s=>s.start),
      endTimes:stages.map(s=>s.end),values,total,oldTotal:old.total,
      engineTotal:expected.total,featureEnded:!g.ledger.feature,
      historyId:number,version:round.expansionPayVersion,
      savedSymbols:round.lineWins.filter(w=>w.expanded).map(w=>w.symbol)};
   });
   assert.deepEqual(state.order,['10','J','ANUBIS','KING'],'Figuras devem ser mostradas por valor crescente');
   assert(state.engineTotal>state.oldTotal,'As quatro figuras têm de contribuir para o prémio');
   assert(state.startTimes.every((t,i)=>i===0||t>state.endTimes[i-1]),'Etapas não se sobrepõem');
   assert(state.values.every((v,i)=>i===0||v>=state.values[i-1]-1e-9),'Contador não pode retroceder');
   assert(Math.abs(state.values.at(-1)-state.total)<1e-8,'Contador termina no prémio realmente creditado');
   assert(state.savedSymbols.includes('10')&&state.savedSymbols.includes('KING'),'Histórico guarda os vários expansivos');
   assert.equal(state.version,2);
   await page.waitForTimeout(120);
   assert.deepEqual(errors,[],'Sem erros de execução durante a sequência');
   console.log(browserType.name()+': quatro expansões sequenciais, créditos e histórico verificados');
   await page.close();
  }finally{await browser.close();}
 }
})().catch(e=>{console.error(e);process.exitCode=1;});
