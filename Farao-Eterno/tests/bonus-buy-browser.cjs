'use strict';
const assert=require('node:assert/strict');
const {chromium,webkit}=require('playwright');
const url='http://127.0.0.1:8123/index.html?qa=bonus-buy';
(async()=>{
 for(const type of [chromium,webkit]){
  const browser=await type.launch({headless:true});
  try{
   const context=await browser.newContext({viewport:{width:390,height:844},deviceScaleFactor:2});
   const page=await context.newPage(),errors=[];
   page.on('pageerror',e=>errors.push(e.message));
   await page.goto(url,{waitUntil:'domcontentloaded',timeout:20000});
   await page.waitForFunction(()=>window.__faraoGame?.ready===true,null,{timeout:8500});
   const button=page.locator('#touch-controls button[aria-label="COMPRAR BÓNUS"]');
   await button.waitFor({state:'visible',timeout:5000});
   const position=await page.evaluate(()=>{
     const g=window.__faraoGame,t=g.targets.find(t=>t.label==='COMPRAR BÓNUS');
     const statusTop=g.visualV11.layout.top+g.visualV11.layout.gh*3+14;
     return {x:t?.x,y:t?.y,w:t?.w,h:t?.h,statusEnd:statusTop+72};
   });
   assert.equal(position.x,28);
   assert(position.y>=position.statusEnd,'Botão deve estar por baixo do saldo');
   const balanceBefore=await page.evaluate(()=>window.__faraoGame.ledger.balanceU);
   await button.click();
   assert.equal(await page.locator('#modal-title').innerText(),'COMPRAR BÓNUS');
   assert.match(await page.locator('#modal-content').innerText(),/100[,.]00 cr/);
   await page.locator('#buy-bonus-cancel').click();
   assert.equal(await page.evaluate(()=>window.__faraoGame.ledger.balanceU),balanceBefore,'Cancelar não debita');
   await button.click();
   await page.locator('#buy-bonus-confirm').click();
   let state=await page.evaluate(()=>{
     const g=window.__faraoGame;
     return {balance:g.ledger.balanceU,feature:g.ledger.feature,
       history:g.ledger.history,seq:g.ledger.sequence,modalClosed:document.getElementById('modal').classList.contains('hidden')};
   });
   assert.equal(state.balance,balanceBefore-100*252000);
   assert.equal(state.feature.left,8,'Inicia exatamente com 8 Free Spins');
   assert.equal(state.history[0].kind,'bonus-buy');
   assert.equal(state.history[0].cost,100);
   assert.equal(state.seq,0,'A compra não cria jogada sem girar');
   assert.equal(state.modalClosed,true);
   await page.reload({waitUntil:'domcontentloaded'});
   await page.waitForFunction(()=>window.__faraoGame?.ready===true,null,{timeout:8500});
   state=await page.evaluate(()=>({balance:window.__faraoGame.ledger.balanceU,
       feature:window.__faraoGame.ledger.feature,history:window.__faraoGame.ledger.history}));
   assert.equal(state.balance,balanceBefore-100*252000,'Saldo é recuperado');
   assert.equal(state.feature.left,8,'Free Spins são recuperadas');
   assert.equal(state.history.filter(h=>h.kind==='bonus-buy').length,1);
   await page.evaluate(()=>window.__faraoGame.openInfo('history'));
   assert.match(await page.locator('#history-results').innerText(),/Compra de bónus/);
   assert.match(await page.locator('#history-results').innerText(),/Custo 100[,.]00 cr/);
   assert.deepEqual(errors,[],'Erros JS: '+errors.join('; '));
   console.log(type.name()+': botão sob SALDO, confirmação, débito 100×, recuperação e histórico OK');
   await context.close();
  }finally{await browser.close();}
 }
})().catch(e=>{console.error(e);process.exitCode=1;});
