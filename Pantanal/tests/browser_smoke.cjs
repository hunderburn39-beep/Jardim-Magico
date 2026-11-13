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
 await page.goto('http://127.0.0.1:8765/Pantanal/?v=2.14',{waitUntil:'domcontentloaded',timeout:50000});
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
 fs.mkdirSync('Pantanal/tests/screenshots',{recursive:true});
 await page.screenshot({path:'Pantanal/tests/screenshots/pantanal-v214-iphone.png',fullPage:false});
 await page.evaluate(()=>{
  window.__visualTestState={lastWin:100,credits:1000,totalPaid:0,maxWin:0,history:[{total:100}]};
  window.__visualTestPromise=PantanalBonusUI.launch({
   bonus:{dice:1},state:window.__visualTestState,betPerLine:1,
   asset:()=>'',rng:()=>0.75,difficulty:{active:false},
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
 await page.evaluate(()=>window.__visualTestPromise);
 assert.equal(await page.locator('#pantanalBonusModal').count(),0,'Bónus fechado');
 // Administração: parâmetros novos têm de persistir realmente no jogo.
 await page.evaluate(()=>window.adminPanel());
 await page.waitForSelector('#blocked');
 await page.locator('#blocked').fill('30');
 await page.locator('#apkAccumPercent').fill('5');
 await page.locator('#admSave').click({force:true});
 const blocked=await page.evaluate(()=>state.blocked);
 assert.equal(blocked,30,'Bloqueio do admin nao foi gravado');
 await page.locator('#close').click({force:true});

 // Uma jogada completa sem erros de JS no ambiente Safari-like do iPhone.
 const spinsBefore=await page.evaluate(()=>state.totalSpins);
 await page.locator('#spinButton').click({force:true});
 await page.waitForFunction(n=>state.totalSpins>n,spinsBefore,{timeout:12000});
 await page.waitForFunction(()=>!spinBusy,{timeout:12000});
 const settled=await page.evaluate(()=>({spins:state.totalSpins,lastWin:state.lastWin,credits:state.credits}));
 assert(Number.isFinite(settled.credits)&&settled.spins>spinsBefore,'Jogada nao concluida');

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
 await page.screenshot({path:'Pantanal/tests/screenshots/pantanal-v214-desktop.png'});
 const readableErrors=errors.filter(x=>!x.includes('Audio'));
 assert.equal(readableErrors.length,0,'Erros de JS no browser: '+readableErrors.join(' | '));
 console.log(JSON.stringify({success:true,portrait:baseline,lines:p,bonus,desktop,jsErrors:readableErrors},null,2));
 await browser.close();
})().catch(err=>{console.error('BROWSER SMOKE FAIL',err.stack||String(err));process.exitCode=1;});
