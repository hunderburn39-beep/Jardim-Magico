'use strict';
// Confirma que o histórico NÃO apresenta miniaturas e que a procura é feita
// pelo ID exato, abrindo as imagens apenas no detalhe da jogada.
const assert=require('node:assert/strict');
const {chromium,webkit}=require('playwright');
const url='http://127.0.0.1:8123/index.html?qa=history-search';
(async()=>{
 for(const type of [chromium,webkit]){
  const browser=await type.launch({headless:true});
  try{
   const page=await browser.newPage({viewport:{width:390,height:844}});
   const errors=[];page.on('pageerror',e=>errors.push(e.message));
   await page.goto(url,{waitUntil:'domcontentloaded',timeout:20000});
   await page.waitForFunction(()=>window.__faraoGame?.ready===true,null,{timeout:8000});
   await page.evaluate(()=>{
    const g=window.__faraoGame;
    const make=(id,win)=>({
     id,kind:'spin',t:'2026-11-10T12:00:00.000Z',
     bet:1,win,lines:10,paylinesVersion:2,
     grid:g.grid.map(col=>col.slice()),lineWins:[],scatterWin:0,bonus:false
    });
    g.ledger.history=[make(125,1.2),make(126,0),make(1125,2.4)];
    g.syncLedger();
    g.openInfo('history');
   });
   const list=page.locator('.farao-history-list');
   assert.equal(await list.locator('img').count(),0,'A lista não deve ter imagens');
   assert.equal(await list.locator('[data-history-open]').count(),3,'Três jogadas clicáveis');
   const search=page.locator('#history-search-number');
   await search.fill('125');
   assert.equal(await list.locator('.farao-history-row:visible').count(),1,'Pesquisa por ID exato');
   assert.equal(await list.locator('[data-history-open="125"]').isVisible(),true);
   assert.equal(await list.locator('[data-history-open="1125"]').isVisible(),false);
   await page.locator('[data-history-open="125"]').click();
   assert.equal(await page.locator('#modal-title').innerText(),'JOGADA #125');
   assert.equal(await page.locator('#modal-content img').count(),15,'Detalhe com grelha 5×3');
   await page.locator('#back-history').click();
   assert.equal(await page.locator('#history-search-number').inputValue(),'125','Manter pesquisa ao voltar');
   await page.locator('#history-search-number').fill('99999');
   assert.match(await page.locator('#history-search-count').innerText(),/não encontrada/i);
   await page.locator('#history-search-clear').click();
   assert.equal(await list.locator('.farao-history-row:visible').count(),3,'Limpar mostra tudo');
   assert.equal(await list.locator('img').count(),0,'Não mostrar imagens ao regressar');
   assert.deepEqual(errors,[],'Sem erros JS: '+errors.join('; '));
   console.log(type.name()+': histórico compacto, procura exata, detalhe e regresso OK');
   await page.close();
  }finally{await browser.close();}
 }
})().catch(e=>{console.error(e);process.exitCode=1;});
