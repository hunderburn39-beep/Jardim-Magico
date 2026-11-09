/* Smoke tests reais dos navegadores: Chromium e Safari/WebKit. */
"use strict";
const assert=require("node:assert/strict");
const fs=require("node:fs");
const { chromium,webkit }=require("playwright");
const ORIGIN="http://127.0.0.1:8124/Halloween/?smoke=1";
const W=720,H=1280;
async function tap(page,x,y){
  const box=await page.locator("canvas").boundingBox();
  assert.ok(box&&box.width>100&&box.height>100,"canvas sem área visível");
  await page.mouse.click(box.x+(x/W)*box.width,box.y+(y/H)*box.height);
}
async function run(name,launcher){
  const browser=await launcher.launch({headless:true});
  try{
    const context=await browser.newContext({viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true});
    const page=await context.newPage();
    const errors=[];
    page.on("pageerror",e=>{errors.push(e.message);console.error(name+" pageerror:",e.message);});
    page.on("console",msg=>{if(msg.type()==="error")errors.push(msg.text());});
    const response=await page.goto(ORIGIN,{waitUntil:"domcontentloaded",timeout:25000});
    assert.equal(response.status(),200);
    await page.locator("canvas").waitFor({state:"visible",timeout:25000});
    try{
      await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.symbols?.length===5,null,{timeout:12000});
    }catch(error){
      const details=await page.evaluate(()=>({
        statusText:document.getElementById("status")?.textContent,
        phaserLoaded:typeof window.Phaser!=="undefined",
        engineLoaded:typeof window.HalloweenEngine!=="undefined",
        artLoaded:typeof window.HalloweenArt!=="undefined",
        sceneExists:Boolean(window.__HALLOWEEN_SCENE__),
        sceneSymbols:window.__HALLOWEEN_SCENE__?.symbols?.length,
        images:Array.from(document.images).map(e=>({src:e.currentSrc,complete:e.complete})),
        scripts:Array.from(document.scripts).map(e=>e.src),
        resources:performance.getEntriesByType("resource").filter(e=>/Halloween|phaser/i.test(e.name)).map(e=>({name:e.name,duration:e.duration}))
      }));
      console.error(name+" diagnóstico:",JSON.stringify(details),"pageerrors:",JSON.stringify(errors));
      fs.mkdirSync("visual-previews",{recursive:true});
      await page.screenshot({path:"visual-previews/halloween-"+name+"-erro-arranque.png"});
      throw error;
    }
    await page.waitForTimeout(300);
    const visible=await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      return {isCanvas:document.querySelector("canvas")?.width>0,
        grid:s.symbols.reduce((a,c)=>a+c.length,0),
        balance:s.state.balance,spins:s.state.spins,
        symbolTypes:window.HalloweenEngine.SYMBOLS.length,
        wheelSectors:window.HalloweenEngine.WHEEL_SECTORS.length,
        accumulators:s.jackpotsText.length,
        stakeCents:s.state.bet,activeLines:s.state.lines};
    });
    assert.equal(visible.isCanvas,true);
    assert.equal(visible.grid,15);
    assert.equal(visible.spins,0);
    assert.equal(visible.symbolTypes,10);
    assert.equal(visible.wheelSectors,30);
    assert.equal(visible.accumulators,4);
    assert.equal(visible.stakeCents,1);
    assert.equal(visible.activeLines,1);
    // Clicar em + aumenta ambas as dimensões do escalão,
    // clicar em − volta ao escalão anterior.
    await tap(page,208,1192);
    await page.waitForFunction(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      return s?.state?.bet===5 && s?.state?.lines===5;
    });
    await tap(page,90,1192);
    await page.waitForFunction(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      return s?.state?.bet===1 && s?.state?.lines===1;
    });
    fs.mkdirSync("visual-previews",{recursive:true});
    await page.screenshot({path:`visual-previews/halloween-${name}-inicio.png`});
    await tap(page,671,272);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.tableOpen===true);
    await page.screenshot({path:`visual-previews/halloween-${name}-tabela-original.png`});
    await tap(page,360,998);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.tableOpen===false);
    // Interromper um giro a meio: o resultado e o saldo já devem
    // estar guardados e os símbolos restaurados no novo carregamento.
    await tap(page,367,1192);
    await page.waitForFunction(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      return s?.state?.spins===1 && s?.spinning===true && !!s?.state?.lastGrid;
    },{},{timeout:15000});
    const saved=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state);
    await page.reload({waitUntil:"domcontentloaded"});
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.symbols?.length===5,{},{timeout:25000});
    const restored=await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      return {state:s.state,visible:s.symbols.map(col=>col.map(icon=>icon.texture.key.slice(2)))};
    });
    assert.equal(restored.state.spins,1);
    assert.equal(restored.state.balance,saved.balance);
    assert.deepEqual(restored.state.lastGrid,saved.lastGrid);
    assert.deepEqual(restored.visible,saved.lastGrid);
    await page.screenshot({path:`visual-previews/halloween-${name}-giro.png`});
    // Simular um bónus persistente após fechar a aplicação.
    await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      const next={...s.state,pendingPot:{choices:[4,10,16]},freeSpins:0};
      localStorage.setItem("halloween-classico-v02",JSON.stringify(next));
    });
    await page.reload({waitUntil:"domcontentloaded"});
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.potOpen===true,{},{timeout:25000});
    const before=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    await tap(page,155,620);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.state?.pendingPot===null,{},{timeout:5000});
    const after=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    assert.equal(after,Math.round((before+4)*100)/100);
    await page.screenshot({path:`visual-previews/halloween-${name}-bonus.png`});
    // A roleta deve recuperar depois de fechar a página e pagar
    // exatamente o prémio guardado, sem repetir a atribuição.
    await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      const next={...s.state,pendingPot:null,pendingWheel:{sectorIndex:6,payout:351}};
      localStorage.setItem("halloween-classico-v02",JSON.stringify(next));
    });
    await page.reload({waitUntil:"domcontentloaded"});
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.wheelOpen===true,{},{timeout:25000});
    const wheelBefore=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    await tap(page,360,852);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.state?.pendingWheel===null,{},{timeout:12000});
    const wheelAfter=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    assert.equal(wheelAfter,wheelBefore+351);
    await page.screenshot({path:`visual-previews/halloween-${name}-roleta.png`});
    await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      const next={...s.state,pendingPot:null,pendingWheel:null,
        pendingStrawberry:{choices:Array.from({length:12},(_,i)=>i+1)}};
      localStorage.setItem("halloween-classico-v02",JSON.stringify(next));
    });
    await page.reload({waitUntil:"domcontentloaded"});
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.strawberryOpen===true,{},{timeout:25000});
    const berries=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.strawberryHits.length);
    assert.equal(berries,12);
    const berryBefore=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    await tap(page,144,506);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.state?.pendingStrawberry===null,{},{timeout:5000});
    const berryAfter=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    assert.equal(berryAfter,Math.round((berryBefore+1)*100)/100);
    await page.screenshot({path:`visual-previews/halloween-${name}-morangos.png`});
    await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      const next={...s.state,pendingPot:null,pendingStrawberry:null,pendingWheel:null,
        pendingDice:{faces:[1,2,3,4,6]}};
      localStorage.setItem("halloween-classico-v02",JSON.stringify(next));
    });
    await page.reload({waitUntil:"domcontentloaded"});
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.diceOpen===true,{},{timeout:25000});
    assert.equal(await page.evaluate(()=>window.__HALLOWEEN_SCENE__.diceHits.length),5);
    const diceBefore=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    await tap(page,620,638);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.state?.pendingDice===null,{},{timeout:5000});
    const diceAfter=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.balance);
    const diceBet=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state.bet);
    assert.equal(diceAfter,Math.round((diceBefore+6*diceBet)*100)/100);
    await page.screenshot({path:`visual-previews/halloween-${name}-dados.png`});
    assert.deepEqual(errors,[],`${name}: erros no browser: ${errors.join("; ")}`);
    console.log(`${name}: carregamento, 15 símbolos, giro, potes, morangos, dados, roleta, recuperação e screenshots OK`);
    await context.close();
  }finally{await browser.close();}
}
(async()=>{
  await run("chromium",chromium);
  await run("webkit",webkit);
})().catch(error=>{console.error(error);process.exitCode=1;});
