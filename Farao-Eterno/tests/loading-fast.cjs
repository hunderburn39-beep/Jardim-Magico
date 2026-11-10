'use strict';
// Regressão do loading: o jogo tem de abrir mesmo com uma imagem indisponível.
const assert=require('node:assert/strict');
const {chromium,webkit}=require('playwright');
const base='http://127.0.0.1:8123/index.html?qa=loading-fast';
(async()=>{
  for(const browserType of [chromium,webkit]){
    const browser=await browserType.launch({headless:true});
    try{
      for(const blocked of [false,true]){
        const page=await browser.newPage({viewport:{width:390,height:844}});
        const errors=[];
        page.on('pageerror',error=>errors.push(error.message));
        if(blocked){
          await page.route('**/assets/horus.webp*',route=>route.abort());
          await page.route('**/assets/horus.svg*',route=>route.abort());
        }
        const started=Date.now();
        await page.goto(base,{waitUntil:'domcontentloaded',timeout:15000});
        try{
          await page.waitForFunction(
            ()=>window.__faraoGame?.ready===true,null,{timeout:5500}
          );
        }catch(error){
          const state=await page.evaluate(()=>({
            gameExists:!!window.__faraoGame,
            ready:window.__faraoGame?.ready,
            artLoaded:window.__faraoGame?.artLoaded,
            artFailed:window.__faraoGame?.artFailed,
            html:document.readyState,
            scripts:[...document.scripts].map(s=>({src:s.src,loaded:!!s.src})),
          })).catch(e=>({evaluationError:String(e)}));
          console.error('DIAGNÓSTICO DE LOADING:',browserType.name(),blocked?'falha simulada':'normal',
            JSON.stringify({state,errors}));
          throw error;
        }
        const elapsed=Date.now()-started;
        const state=await page.evaluate(()=>({
          ready:window.__faraoGame.ready,
          assets:window.__faraoGame.artLoaded,
          total:window.__faraoGame.artTotal,
          failed:window.__faraoGame.artFailed,
          version:document.documentElement.dataset.faraoVersion
        }));
        assert.ok(state.ready,'Jogo preso no loading');
        assert.ok(elapsed<5500,'Loading demasiado longo: '+elapsed+'ms');
        assert.ok(!errors.length,'Erros JS: '+errors.join('; '));
        if(blocked)assert.ok(state.failed.includes('HORUS'),
          'Falha de imagem não foi contabilizada');
        console.log(browserType.name(),blocked?'falha simulada':'normal',
          elapsed+'ms','imagens '+state.assets+'/'+state.total,
          'falhas '+state.failed.join(','));
        await page.close();
      }
    }finally{await browser.close();}
  }
})().catch(error=>{console.error(error);process.exitCode=1;});
