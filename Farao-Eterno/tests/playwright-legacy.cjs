'use strict';
const assert=require('node:assert/strict');
const fs=require('node:fs');
const path=require('node:path');
const {chromium,webkit}=require('playwright');
const base='http://127.0.0.1:8123';
const output=path.resolve('visual-previews');
fs.mkdirSync(output,{recursive:true});

(async()=>{
 let failed=false;
 for(const [name,engine] of [['chromium',chromium],['webkit',webkit]]){
  let browser;
  try{
   browser=await engine.launch({headless:true});
   const context=await browser.newContext({viewport:{width:390,height:844},deviceScaleFactor:2});
   const page=await context.newPage();
   const errors=[],resources=[];
   page.on('pageerror',error=>errors.push(String(error)));
   page.on('response',res=>{if(res.status()>=400)resources.push(res.status()+' '+res.url());});
   await page.goto(base+'/tests/browser.html',{waitUntil:'domcontentloaded',timeout:30000});
   await page.getByRole('button',{name:'Executar testes'}).click();
   await page.waitForFunction(()=>Array.isArray(window.FARAO_QA_RESULTS),null,{timeout:120000});
   const rows=await page.evaluate(()=>window.FARAO_QA_RESULTS);
   await page.screenshot({path:path.join(output,'farao-'+name+'-qa.png'),fullPage:true});
   const bad=rows.filter(r=>!r.pass);
   const game=page.frameLocator('#game-frame');
   const version=await game.locator('html').getAttribute('data-farao-version');
   assert.equal(version,'1.0','Versão inesperada');
   assert(rows.length>=19,'Cobertura de testes insuficiente');
   assert.deepEqual(resources,[],'Recursos em falta: '+resources.join(', '));
   assert.deepEqual(errors,[],'Erros JavaScript: '+errors.join('; '));
   assert.deepEqual(bad,[],'Falhas: '+JSON.stringify(bad));
   console.log(name.toUpperCase()+' PASS: '+rows.length+'/'+rows.length+' cenários, 0 erros JS e 0 recursos ausentes');
   await context.close();
  }catch(error){
   failed=true;
   console.error(name.toUpperCase()+' FAIL:',error.stack||String(error));
  }finally{if(browser)await browser.close();}
 }
 if(failed)process.exitCode=1;
})().catch(error=>{console.error(error);process.exitCode=1;});
