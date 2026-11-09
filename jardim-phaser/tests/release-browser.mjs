/* Runs in CI against disposable browser contexts; never touches a player's data. */
import {chromium,webkit} from 'playwright';
import assert from 'node:assert/strict';
import {mkdir,readFile} from 'node:fs/promises';
const origin=process.env.GAME_TEST_ORIGIN||'http://127.0.0.1:8123';
await mkdir('visual-previews',{recursive:true});
let checks=0;
const check=(value,label)=>{assert.ok(value,label);checks++};
async function stored(page){
 return page.evaluate(()=>new Promise((resolve,reject)=>{
  const request=indexedDB.open('jardim-magico-phaser-demo',1);
  request.onerror=()=>reject(request.error);
  request.onsuccess=()=>{
   const db=request.result,tx=db.transaction(['state','events'],'readonly');
   const state=tx.objectStore('state').get('machine');
   const events=tx.objectStore('events').getAll();
   tx.oncomplete=()=>{db.close();resolve({state:state.result?.payload||null,events:events.result})};
   tx.onerror=()=>{db.close();reject(tx.error)};
  };
 }));
}
const ready=page=>page.waitForFunction(()=>window.jardimPhaserScene?.ready&&
 !document.querySelector('#spinBtn').disabled,null,{timeout:45000});
const saved=async(page,test)=>{
 for(let n=0;n<120;n++){
  const data=await stored(page);if(test(data))return data;
  await page.waitForTimeout(100);
 }
 throw Error('A sessão não atingiu o estado esperado');
};
for(const [name,type] of [['chromium',chromium],['webkit',webkit]]){
 const browser=await type.launch({headless:true,...(name==='chromium'?{args:['--no-sandbox','--disable-gpu-sandbox']}:{} )});
 try{
  for(const viewport of [{width:320,height:568},{width:390,height:844},{width:430,height:932}]){
   console.log('START',name,viewport.width+'x'+viewport.height);
   const context=await browser.newContext({viewport,locale:'pt-PT',timezoneId:'Europe/Lisbon',acceptDownloads:true});
   context.setDefaultTimeout(20000);context.setDefaultNavigationTimeout(20000);
   // A fixed non-winning fixture makes UI costs, Free Spins and Hold finishes reproducible.
   // Random awards and full grids are exercised separately by final.mjs and smoke.mjs.
   await context.addInitScript(()=>{
    Math.random=()=>.9;
    // Inspect the printable HTML and invocation without an operating-system print dialog.
    window.print=()=>{window.testPrintInvoked=true};
   });
   const page=await context.newPage(),errors=[],requests=[];
   page.on('pageerror',error=>errors.push(error.message));
   page.on('request',request=>requests.push(request.url()));
   await page.goto(origin+'/jardim-phaser/',{waitUntil:'load'});await ready(page);
   const geometry=await page.evaluate(()=>{
    const rect=s=>{const r=document.querySelector(s).getBoundingClientRect();return {x:r.x,y:r.y,w:r.width,h:r.height,bottom:r.bottom,right:r.right}};
    return {spin:rect('#spinBtn'),reel:rect('.reel-window'),frame:rect('.branch-frame'),
     top:[...document.querySelectorAll('.top-actions button,.top-actions a')].map(el=>{const r=el.getBoundingClientRect();return {right:r.right,w:r.width}}),
     overflow:document.documentElement.scrollWidth-innerWidth,
     ready:document.querySelector('#phaser-reels').getAttribute('aria-busy')};
   });
   check(geometry.overflow===0,name+' sem overflow horizontal');
   check(geometry.spin.bottom<=viewport.height&&geometry.spin.w>=(viewport.height<650?90:128),name+' RODAR maior e visível');
   check(Math.abs(geometry.reel.w/geometry.reel.h-5/3)<.04,name+' tabuleiro proporcional');
   check(geometry.frame.right<=viewport.width+2&&geometry.frame.x>=-2,name+' moldura dentro do ecrã');
   check(geometry.top.every(r=>r.w>=35&&r.right<=viewport.width),name+' botões superiores acessíveis');
   check(geometry.ready==='false',name+' carregamento concluído');
   check(requests.every(url=>url.startsWith(origin)||url.startsWith('data:')||url.startsWith('blob:')),name+' sem CDN externo');
   check(await page.locator('#dropHud').isHidden(),name+' painel sem multiplicador permanece oculto');
   await page.locator('#turboBtn').click();
   await page.locator('#betUp').click();await saved(page,d=>d.state?.snapshot.stake===.4);
   await page.locator('#betDown').click();await saved(page,d=>d.state?.snapshot.stake===.2);
   await page.locator('#creditBtn').click();await saved(page,d=>d.state?.snapshot.balance===125);
   await page.locator('#spinBtn').click();await ready(page);
   let data=await stored(page);check(data.state.snapshot.balance===124.8&&data.state.snapshot.spins===1,name+' giro cobrado uma única vez');
   await page.reload();await ready(page);await page.locator('#turboBtn').click();data=await stored(page);
   check(data.state.snapshot.spins===1&&data.state.snapshot.balance===124.8,name+' reload sem repetição');
   await page.locator('#infoBtn').click();
   await page.waitForFunction(()=>document.querySelector('#overlay').contains(document.activeElement));
   check(await page.locator('.controls').evaluate(el=>el.inert),name+' jogo inerte atrás do painel');
   await page.keyboard.press('Escape');
   check(await page.locator('#infoBtn').evaluate(el=>document.activeElement===el),name+' foco devolvido ao botão');
   // Simulated BFCache and context signals must preserve recorded credits.
   await page.evaluate(()=>{dispatchEvent(new Event('pagehide'));dispatchEvent(new Event('pageshow'))});
   await ready(page);check((await stored(page)).state.snapshot.balance===124.8,name+' suspensão sem pagamento duplicado');
   await page.evaluate(()=>{const c=document.querySelector('canvas');c.dispatchEvent(new Event('webglcontextlost',{cancelable:true}))});
   check(await page.locator('#spinBtn').isDisabled(),name+' contexto perdido bloqueia giro');
   await page.evaluate(()=>document.querySelector('canvas').dispatchEvent(new Event('webglcontextrestored')));
   await ready(page);check((await stored(page)).state.snapshot.balance===124.8,name+' restauro sem mudar saldo');
   if(viewport.width===390){
    const audioState=await page.evaluate(async()=>{
     const {audio,AUDIO_FILES}=await import('./js/audio.js?v=5.1.0');
     const clips=await Promise.all(Object.keys(AUDIO_FILES).map(name=>audio.loadEffect(name)));
     return {decoded:clips.every(buffer=>buffer&&buffer.duration>0),music:audio.musicPlaying,src:audio.background?.currentSrc,loop:audio.background?.loop};
    });
    check(audioState.decoded&&audioState.music&&audioState.loop&&audioState.src.includes('zen-meditation.mp3'),name+' gravações descodificadas e fundo zen em loop');
    await page.locator('#musicBtn').click();
    check(await page.locator('#musicBtn').getAttribute('aria-pressed')==='false',name+' ambiente zen pode ser desligado');
    await page.locator('#musicBtn').click();
    await page.locator('#adminBtn').click();
    await page.locator('#adminPinNew').fill('246810');await page.locator('#adminPinConfirm').fill('246810');
    await page.locator('.admin-pin-form button[type="submit"]').click();
    await page.locator('#adminFundAmount').waitFor();
    await page.locator('#ownerPct').fill('33.3');await page.locator('#saveOwner').click();
    await saved(page,d=>d.state.snapshot.admin.ownerPct===33.3);
    await page.locator('#adminFundAmount').fill('1.001');await page.locator('#fundDeposit').click();
    check((await page.locator('#adminMessage').innerText()).includes('válido'),name+' recusa frações de cêntimo');
    const oldReserve=(await stored(page)).state.snapshot.reserve;
    await page.locator('#adminFundAmount').fill('10,25');await page.locator('#fundDeposit').click();
    await saved(page,d=>d.state.snapshot.reserve===Math.round((oldReserve+10.25)*100)/100);
    await page.locator('#adminFundAmount').fill('10,25');await page.locator('#fundWithdraw').click();
    await saved(page,d=>d.state.snapshot.reserve===oldReserve);
    await page.locator('#adminCreditAmount').fill('12,50');await page.locator('#playerCredit').click();
    await saved(page,d=>d.state.snapshot.balance===137.3);
    await page.locator('#adminCreditAmount').fill('2,50');await page.locator('#playerDebit').click();
    await saved(page,d=>d.state.snapshot.balance===134.8);
    // A failed IndexedDB write must leave both stored and displayed credits unchanged.
    await page.evaluate(()=>{
     window.testOriginalTransaction=IDBDatabase.prototype.transaction;
     IDBDatabase.prototype.transaction=function(names,mode,...args){
      if(mode==='readwrite'&&Array.isArray(names)&&names.includes('events'))throw Error('Injected storage failure');
      return window.testOriginalTransaction.call(this,names,mode,...args);
     };
    });
    await page.locator('#adminCreditAmount').fill('5');await page.locator('#playerCredit').click();
    await page.waitForFunction(()=>document.querySelector('#adminMessage').textContent.includes('Falha'));
    check((await stored(page)).state.snapshot.balance===134.8&&
     (await page.locator('#adminBalance').innerText()).includes('134,80'),name+' rollback após falha real de escrita');
    await page.evaluate(()=>{IDBDatabase.prototype.transaction=window.testOriginalTransaction;delete window.testOriginalTransaction});
    const popupPromise=page.waitForEvent('popup');await page.locator('#adminReport').click();
    const popup=await popupPromise;await popup.waitForLoadState();
    check((await popup.locator('body').innerText()).includes('movimentos guardados'),name+' relatório completo imprimível');
    check(await popup.evaluate(()=>window.testPrintInvoked===true),name+' impressão invocada');await popup.close();
    await page.locator('#adminHistory').click();await page.locator('#historyQuery').fill('adicionar créditos');
    check(await page.locator('.history-row').count()===1,name+' histórico pesquisável em português');
    const downloadPromise=page.waitForEvent('download');await page.getByRole('button',{name:/Exportar cópia JSON/}).click();
    const file=await downloadPromise,backup=JSON.parse(await readFile(await file.path(),'utf8'));
    check(backup.state.snapshot.balance===134.8&&!JSON.stringify(backup).includes('246810'),name+' exporta saldo sem PIN');
    await page.locator('#historyBtn').click();
    const chooserPromise=page.waitForEvent('filechooser');
    await page.getByRole('button',{name:/Restaurar cópia JSON/}).click();
    const chooser=await chooserPromise;
    page.once('dialog',dialog=>dialog.accept());
    const navigation=page.waitForNavigation({waitUntil:'load'});
    await chooser.setFiles(await file.path());await navigation;await ready(page);
    check((await stored(page)).state.snapshot.balance===134.8,name+' restauro JSON transacional');
    await page.locator('#turboBtn').click();
    await page.locator('#infoBtn').click();
    await page.getByRole('button',{name:/Experimentar Hold & Win/}).click();
    await page.locator('#holdOverlay').waitFor({state:'visible'});
    const holdBalance=(await stored(page)).state.snapshot.balance;
    for(let n=0;n<3;n++){await page.locator('#holdBtn').click();if(n<2)await page.locator('#holdBtn:not([disabled])').waitFor()}
    await page.locator('#holdOverlay').waitFor({state:'hidden',timeout:15000});await ready(page);
    data=await stored(page);check(data.state.snapshot.balance===Math.round((holdBalance+12)*100)/100,name+' Hold termina e paga seis moedas de 10×');
    await page.locator('#bonusBtn').click();await page.getByRole('button',{name:/15 Free Spins/}).click();
    await saved(page,d=>d.events.some(e=>e.type==='buy-free'));
    await ready(page);data=await stored(page);
    check(data.state.snapshot.mode==='base'&&data.state.snapshot.freePlayed===15,name+' compra e conclusão das 15 Free Spins');
    await page.evaluate(()=>{Math.random=()=>.4});
    await page.locator('#spinBtn').click();
    await page.locator('#dropHud').waitFor({state:'visible',timeout:10000});
    const floating=await page.locator('#dropHud').evaluate(el=>{
     const frame=el.closest('.frame-region'),r=el.getBoundingClientRect(),f=frame.getBoundingClientRect();
     const frameArt=document.querySelector('.branch-frame');
     const frameBackground=getComputedStyle(frameArt).backgroundImage;
     const played=getComputedStyle(document.querySelector('#hudFreePlayed')).display;
     const winBand=getComputedStyle(document.querySelector('#winNotice')).display;
     return {above:!!frame&&r.top>=0&&r.bottom<=f.top+10,
      frameBackground,played,winBand,value:el.querySelector('#multiTotal').textContent};
    });
    check(floating.above&&Number(floating.value.replace('×',''))>1,name+' valor da queda e MULTI acima da moldura');
    check(floating.frameBackground.includes('jm51-moldura-limpa.svg')&&floating.played==='none'&&floating.winBand==='none',
     name+' cantos livres e informações redundantes ocultas');
    await ready(page);await page.locator('#dropHud').waitFor({state:'hidden',timeout:6000});
    await page.evaluate(()=>{Math.random=()=>.9});
    const spinCount=data.state.snapshot.spins;
    await page.locator('#autoBtn').click();await page.getByRole('button',{name:/10 giros/}).click();
    await saved(page,d=>d.state.snapshot.spins>=spinCount+1);
    await page.locator('#autoBtn').click();await ready(page);
    check((await page.locator('#autoRemaining').innerText())==='∞',name+' automático pode ser parado');
    await page.setViewportSize({width:844,height:390});
    check(await page.locator('#portraitLock').isVisible(),name+' paisagem bloqueada');
    await page.setViewportSize(viewport);await ready(page);
    const second=await context.newPage();await second.goto(origin+'/jardim-phaser/');
    await second.waitForFunction(()=>window.jardimPhaserScene?.ready);
    check((await second.locator('#spinBtn').isDisabled())||(await page.locator('#spinBtn').isDisabled()),name+' abas sem escrita simultânea');await second.close();await page.bringToFront();await ready(page);
   }
   check(errors.length===0,name+' erros JavaScript: '+errors.join(' | '));
   await page.screenshot({path:'visual-previews/final-'+name+'-'+viewport.width+'x'+viewport.height+'.png'});
   console.log('PASS',name,viewport.width+'x'+viewport.height);
   await context.close();
  }
 }finally{await browser.close()}
}
console.log(JSON.stringify({suite:'release-browser',checks,engines:['Chromium','WebKit'],viewports:['320x568','390x844','430x932']}));
