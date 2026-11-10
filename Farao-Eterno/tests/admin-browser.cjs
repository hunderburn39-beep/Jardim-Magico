'use strict';
const assert=require('node:assert/strict');
const {chromium,webkit}=require('playwright');
const url='http://127.0.0.1:8123/index.html?qa=admin-economy';
(async()=>{
 for(const browserType of [chromium,webkit]){
  const browser=await browserType.launch({headless:true});
  try{
   const context=await browser.newContext({viewport:{width:390,height:844}});
   const page=await context.newPage(),errors=[];
   page.on('pageerror',e=>errors.push(e.message));
   await page.goto(url,{waitUntil:'domcontentloaded',timeout:20000});
   await page.waitForFunction(()=>window.__faraoGame?.ready===true,null,{timeout:12000});
   await page.evaluate(()=>window.__faraoGame.openInfo('admin'));
   assert.equal(await page.locator('#admin-enter').count(),1,'Aba mostra um só botão de acesso');
   await page.locator('#admin-enter').click();
   await page.locator('#admin-login-code').fill('00000');
   await page.locator('#admin-login-submit').click();
   assert.match(await page.locator('#farao-admin-error').innerText(),/incorreto/);
   await page.locator('#admin-login-code').fill('12345');
   await page.locator('#admin-login-submit').click();
   await page.locator('#admin-tech').fill('65');
   assert.match(await page.locator('#admin-merchant-label').innerText(),/35%/);
   await page.locator('#admin-save-split').click();
   await page.locator('#admin-level').fill('95');
   await page.locator('#admin-save-level').click();
   await page.locator('#admin-fund').fill('20,50');
   await page.locator('#admin-fund-add').click();
   assert.equal(await page.evaluate(()=>window.__faraoGame.ledger.economy.prizeU/252000),20.5);
   await page.locator('#admin-change-deposit').click();
   await page.locator('#admin-current-pin').fill('12345');
   await page.locator('#admin-new-pin').fill('24680');
   await page.locator('#admin-change-save').click();
   await page.locator('#admin-logout').click();
   await page.evaluate(()=>window.__faraoGame.showCreditMenu());
   await page.locator('#credit-deposit').click();
   await page.locator('#credit-pin').fill('12345');
   await page.locator('#credit-pin-submit').click();
   assert.match(await page.locator('#farao-admin-error').innerText(),/incorreto/);
   await page.locator('#credit-pin').fill('24680');
   await page.locator('#credit-pin-submit').click();
   assert.equal(await page.locator('#credit-amount').count(),1);
   await page.locator('#credit-amount').fill('25');
   await page.locator('#credit-confirm').click();
   await page.locator('#credit-withdraw').click();
   await page.locator('#credit-pin').fill('12345');
   await page.locator('#credit-pin-submit').click();
   await page.locator('#credit-amount').fill('10');
   await page.locator('#credit-confirm').click();
   const state=await page.evaluate(()=>{
     const e=window.__faraoGame.ledger.economy;
     return {deposit:e.totals.depositsU/252000,
       withdrawal:e.totals.withdrawalsU/252000,
       level:e.level,tech:e.technicianPercent,
       merchant:e.merchantPercent,prize:e.prizeU/252000,
       changedDeposit:e.pin.deposit};
   });
   assert.deepEqual(state,{deposit:25,withdrawal:10,level:95,tech:65,
     merchant:35,prize:20.5,changedDeposit:'24680'});
   await page.reload({waitUntil:'domcontentloaded'});
   await page.waitForFunction(()=>window.__faraoGame?.ready===true,null,{timeout:12000});
   assert.equal(await page.evaluate(()=>window.__faraoGame.ledger.economy.pin.deposit),'24680');
   assert.deepEqual(errors,[],'Sem erros de execução');
   console.log(browserType.name()+': aba, PINs, comissões, fundo, euros e recuperação OK');
   await context.close();
  }finally{await browser.close();}
 }
})().catch(e=>{console.error(e);process.exitCode=1;});
