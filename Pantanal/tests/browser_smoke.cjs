'use strict';
const {chromium}=require('playwright');
const assert=require('node:assert/strict');
const fs=require('node:fs');
(async()=>{
 const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
 const page=await browser.newPage({
   viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true
 });
 const errors=[];
 page.on('pageerror',e=>errors.push(String(e)));
 await page.goto('http://127.0.0.1:8765/Pantanal/?v=2.21',{waitUntil:'domcontentloaded',timeout:50000});
 await page.waitForSelector('#screen');
 let baseline=await page.evaluate(()=>({
  scripts:!!window.PantanalApkEngine&&!!window.PantanalBonusUI,
  dims:{w:innerWidth,h:innerHeight},
  portrait:document.getElementById('screen').classList.contains('portrait')
 }));
 assert(baseline.scripts,'Os modulos APK nao foram carregados');
 assert(baseline.portrait,'O modo 9:16 nao foi activado no iPhone');
 await page.locator('#start').click({force:true});
 await page.waitForSelector('#reels .symbol',{timeout:10000});
 assert.equal(await page.locator('#reels .symbol').count(),15);
 await page.locator('#linesBtn').click({force:true});
 await page.waitForSelector('#pantanalPaylines');
 for(let i=0;i<4;i++)await page.locator('#linesBtn').click({force:true});
 const p=await page.evaluate(()=>{
  const r=document.getElementById('reels').getBoundingClientRect();
  const p=document.getElementById('pantanalPaylines').getBoundingClientRect();
  return {reels:{x:r.x,y:r.y,w:r.width,h:r.height},
    overlay:{x:p.x,y:p.y,w:p.width,h:p.height},
    paylines:document.querySelectorAll('#pantanalPaylines polyline').length,
    current:state.lines};
 });
 assert.equal(p.current,25,'25 linhas seleccionadas');
 assert(Math.abs(p.overlay.x-p.reels.x)<2,'Linhas deslocadas horizontalmente');
 assert(Math.abs(p.overlay.y-p.reels.y)<2,'Linhas deslocadas verticalmente');
 assert(Math.abs(p.overlay.w-p.reels.w)<2);
 assert(Math.abs(p.overlay.h-p.reels.h)<2);
 assert(p.paylines>=25,'Pre-visualizacao completa com 25 linhas');
 const centerPortrait=await page.evaluate(()=>{
   const r=document.getElementById('reels').getBoundingClientRect();
   const screen=document.getElementById('screen').getBoundingClientRect();
   return {delta:Math.abs(r.left+r.width/2-screen.left-screen.width/2),
     width:parseFloat(getComputedStyle(document.getElementById('reels')).width),
     height:parseFloat(getComputedStyle(document.getElementById('reels')).height)};
 });
 assert(centerPortrait.delta<1.5,'Tambores descentrados no iPhone');
 assert.equal(centerPortrait.width,611);
 assert.equal(centerPortrait.height,314);
 const framePortrait=await page.evaluate(()=>{
  const screen=document.getElementById('screen').getBoundingClientRect();
  const frame=document.getElementById('reelFrame').getBoundingClientRect();
  const area=document.getElementById('reelArea').getBoundingClientRect();
  const reels=document.getElementById('reels').getBoundingClientRect();
  const ctrls=document.getElementById('ctrls').getBoundingClientRect();
  return {
   ratio:area.width/screen.width,
   frameVisible:getComputedStyle(document.getElementById('reelFrame')).display!=='none',
   areaX:Math.abs((area.x+area.width/2)-(screen.x+screen.width/2)),
   areaY:Math.abs((area.y+area.height/2)-(screen.y+screen.height/2)),
   reelsX:Math.abs((reels.x+reels.width/2)-(area.x+area.width/2)),
   reelsY:Math.abs((reels.y+reels.height/2)-(area.y+area.height/2)),
   frameMargin:frame.width-reels.width,
   spaceToControls:ctrls.top-area.bottom,
   reelsParent:document.getElementById('reels').parentElement.id
  };
 });
 assert(framePortrait.frameVisible,'Moldura 9:16 não aparece');
 assert.equal(framePortrait.reelsParent,'reelArea');
 assert(Math.abs(framePortrait.ratio-.875)<.005,'Proporção da moldura incorreta');
 for(const key of ['areaX','areaY','reelsX','reelsY']){
  assert(framePortrait[key]<1.5,'Centragem falhada: '+key+' = '+framePortrait[key]);
 }
 assert(framePortrait.frameMargin>30,'Moldura não envolve os tambores');
 assert(framePortrait.spaceToControls>30,'Moldura sobrepõe os comandos');


 fs.mkdirSync('Pantanal/tests/screenshots',{recursive:true});
 await page.screenshot({path:'Pantanal/tests/screenshots/pantanal-v221-iphone.png',fullPage:false});
 await page.evaluate(()=>{
  window.__modeCalls=0;window.__visualTestState={lastWin:100,credits:1000,totalPaid:0,maxWin:0,history:[{total:100}]};
  window.__visualTestPromise=PantanalBonusUI.launch({
   bonus:{dice:1},state:window.__visualTestState,betPerLine:1,
   asset:()=>'',rng:()=>0.75,difficulty:{active:false},
   nextDifficulty:()=>{window.__modeCalls++;return {active:false}}, 
   hooks:{save(){},update(){},showStatus(){},play(){},money:n=>String(n),setBusy(flag){window.__visualTestBusy=flag;}}
  });
 });
 await page.waitForSelector('#pantanalBonusModal button',{timeout:5000});
 await page.locator('#pantanalBonusModal button').first().click({force:true});
 await page.waitForFunction(()=>window.__visualTestState.lastWin===400);
 const bonus=await page.evaluate(()=>({
  win:window.__visualTestState.lastWin,
  credits:window.__visualTestState.credits,
  paid:window.__visualTestState.totalPaid
 }));
 assert.deepEqual(bonus,{win:400,credits:1300,paid:300});
 assert.equal(await page.evaluate(()=>window.__modeCalls),1,'Dados não consultaram a dificuldade separadamente');
 await page.evaluate(()=>window.__visualTestPromise);
 assert.equal(await page.locator('#pantanalBonusModal').count(),0,'Bónus fechado');
 // Administração: parâmetros novos têm de persistir realmente no jogo.
 await page.evaluate(()=>window.adminPanel());
 await page.waitForSelector('#blocked');
 await page.locator('#blocked').fill('30');
 await page.locator('#apkAccumPercent').fill('5');
 await page.locator('#medEnabled').check({force:true});
 await page.locator('#maxEnabled').check({force:true});
 await page.locator('#apjNumSorteio').fill('200');
 await page.locator('#admSave').click({force:true});
 const blocked=await page.evaluate(()=>state.blocked);
 assert.equal(blocked,30,'Bloqueio do admin nao foi gravado');
 const jackpotPerms=await page.evaluate(()=>({med:state.medEnabled,max:state.maxEnabled,sorteio:state.apjNumSorteio}));
 assert.deepEqual(jackpotPerms,{med:true,max:true,sorteio:200},'Autorização local MED/MAX não foi guardada');
 await page.locator('#close').click({force:true});

 // Uma jogada completa sem erros de JS no ambiente Safari-like do iPhone.
 const spinsBefore=await page.evaluate(()=>state.totalSpins);
 const genBefore=await page.evaluate(()=>Number(state.generatorCalls)||0);
 await page.locator('#spinButton').click({force:true});
 await page.waitForSelector('#pantanalReelSpinStage .reelSpinLane',{timeout:5000});
 const activeColumns=await page.locator('#pantanalReelSpinStage .reelSpinLane').count();
 assert.equal(activeColumns,5,'Rolagem tem de ter cinco faixas independentes');
 const firstStrip=page.locator('#pantanalReelSpinStage .reelSpinStrip').first();
 const initialMatrix=await firstStrip.evaluate(el=>getComputedStyle(el).transform);
 await page.waitForTimeout(140);
 const laterMatrix=await firstStrip.evaluate(el=>getComputedStyle(el).transform);
 assert.notEqual(initialMatrix,laterMatrix,'O movimento não é contínuo entre frames');
 await page.waitForFunction(n=>state.totalSpins>n,spinsBefore,{timeout:12000});
 await page.waitForFunction(()=>!spinBusy,{timeout:12000});
 assert.equal(await page.locator('#pantanalReelSpinStage').count(),0,
   'Faixas temporárias não desapareceram após a paragem');
 const settled=await page.evaluate(()=>({spins:state.totalSpins,lastWin:state.lastWin,credits:state.credits}));
 assert(Number.isFinite(settled.credits)&&settled.spins>spinsBefore,'Jogada nao concluida');
 assert((await page.evaluate(()=>state.generatorCalls))>genBefore,'Gerador não incrementou o contador persistente');

 // Bónus morangos e caldeirão, incluindo possibilidade das 5 abóboras.
 for(const type of ['strawberry','cauldron']){
   await page.evaluate(type=>{
     window.__extraState={lastWin:100,credits:1000,totalPaid:0,maxWin:0,history:[{total:100}]};
     window.__extraPromise=PantanalBonusUI.launch({
       bonus:{[type]:1},state:window.__extraState,betPerLine:1,
       asset:()=>'',rng:()=>0.4,difficulty:{active:true},
       hooks:{save(){},update(){},showStatus(){},play(){},money:n=>String(n),setBusy(flag){window.__extraBusy=flag;}}
     });
   },type);
   await page.waitForSelector('#pantanalBonusModal button');
   for(let j=0;j<13;j++){
     const open=await page.locator('#pantanalBonusModal').count();
     if(!open)break;
     const active=page.locator('#pantanalBonusModal button:not([disabled])');
     if(await active.count())await active.first().click({force:true});
     await page.waitForTimeout(370);
     if(j===12)await page.waitForTimeout(1400);
   }
   await page.evaluate(()=>window.__extraPromise);
   assert.equal(await page.locator('#pantanalBonusModal').count(),0,'Fecho bonus '+type);
 }

 // Recuperação real de uma fila de bónus persistida após fechar o navegador.
 await page.evaluate(()=>{
   state.lastWin=80;
   state.history.unshift({at:'recovery-check',stake:50,total:80,
     board:[[1,2,3,4,5],[5,4,3,2,1],[0,1,2,3,4]]});
   state.pendingBonus=PantanalBonusUI.createPending({dice:1},2);
   save();
 });
 const beforeRecovery=await page.evaluate(()=>({credits:state.credits,totalPaid:state.totalPaid}));
 await page.reload({waitUntil:'domcontentloaded'});
 await page.locator('#start').click({force:true});
 await page.waitForSelector('#pantanalBonusModal button',{timeout:5000});
 const boardRestored=await page.evaluate(()=>board.map(row=>row.slice()));
 assert.deepEqual(boardRestored,[[1,2,3,4,5],[5,4,3,2,1],[0,1,2,3,4]],
   'Os tambores não restauraram os símbolos da jogada guardada');
 const recoveryLabel=await page.locator('#pantanalBonusModal').innerText();
 assert(recoveryLabel.includes('DADOS'),'Bonus de dados guardado não retomou');
 await page.locator('#pantanalBonusModal button').first().click({force:true});
 await page.waitForFunction(()=>state.pendingBonus?.dice===0,{timeout:6000});
 await page.waitForFunction(()=>state.pendingBonus===null,{timeout:8000});
 const recovered=await page.evaluate(()=>({
   lastWin:state.lastWin,credits:state.credits,totalPaid:state.totalPaid
 }));
 assert(recovered.lastWin>=80&&recovered.lastWin<=480,'Valor de recuperação inválido');
 assert.equal(recovered.credits-beforeRecovery.credits,recovered.lastWin-80,
   'O saldo foi alterado mais do que uma vez durante a recuperação');
 assert.equal(recovered.totalPaid-beforeRecovery.totalPaid,recovered.lastWin-80,
   'Valor pago duplicado na recuperação');
 await page.reload({waitUntil:'domcontentloaded'});
 await page.locator('#start').click({force:true});
 await page.waitForTimeout(300);
 assert.equal(await page.locator('#pantanalBonusModal').count(),0,
   'O bónus já terminado voltou a aparecer após o reload');
 const persisted=await page.evaluate(()=>({
   credits:state.credits,lastWin:state.lastWin,pending:state.pendingBonus
 }));
 assert.equal(persisted.credits,recovered.credits);
 assert.equal(persisted.lastWin,recovered.lastWin);
 assert.equal(persisted.pending,null);
 // Desktop horizontal: viewport reajustado sem perder estado e com posições estáveis.
 await page.setViewportSize({width:1000,height:600});
 await page.waitForTimeout(200);
 await page.locator('#linesBtn').click({force:true});
 const desktop=await page.evaluate(()=>{
  const r=document.getElementById('reels').getBoundingClientRect();
  const p=document.getElementById('pantanalPaylines').getBoundingClientRect();
  return {portrait:document.getElementById('screen').classList.contains('portrait'),
   dx:Math.abs(r.x-p.x),dy:Math.abs(r.y-p.y)};
 });
 assert.equal(desktop.portrait,false);
 assert(desktop.dx<2&&desktop.dy<2,'Linhas perdidas no modo horizontal');
 const centerDesktop=await page.evaluate(()=>{
   const r=document.getElementById('reels').getBoundingClientRect();
   const screen=document.getElementById('screen').getBoundingClientRect();
   return Math.abs(r.left+r.width/2-screen.left-screen.width/2);
 });
 assert(centerDesktop<1.5,'Tambores descentrados no modo horizontal');
 assert.equal(await page.locator('#reelArea').count(),1,'Área da moldura perdida ao rodar');
 assert.equal(await page.locator('#reelFrame').evaluate(el=>getComputedStyle(el).display),'none',
   'Moldura adicional não deve duplicar a original em horizontal');


 await page.screenshot({path:'Pantanal/tests/screenshots/pantanal-v221-desktop.png'});
 const readableErrors=errors.filter(x=>!x.includes('Audio'));
 assert.equal(readableErrors.length,0,'Erros de JS no browser: '+readableErrors.join(' | '));
 console.log(JSON.stringify({success:true,portrait:baseline,lines:p,bonus,desktop,jsErrors:readableErrors},null,2));
 await browser.close();
})().catch(err=>{console.error('BROWSER SMOKE FAIL',err.stack||String(err));process.exitCode=1;});
