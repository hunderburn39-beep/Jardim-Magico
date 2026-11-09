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
    page.on("pageerror",e=>errors.push(e.message));
    page.on("console",msg=>{if(msg.type()==="error")errors.push(msg.text());});
    const response=await page.goto(ORIGIN,{waitUntil:"domcontentloaded",timeout:25000});
    assert.equal(response.status(),200);
    await page.locator("canvas").waitFor({state:"visible",timeout:25000});
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.symbols?.length===5,{},{timeout:25000});
    await page.waitForTimeout(300);
    const visible=await page.evaluate(()=>{
      const s=window.__HALLOWEEN_SCENE__;
      return {isCanvas:document.querySelector("canvas")?.width>0,grid:s.symbols.reduce((a,c)=>a+c.length,0),balance:s.state.balance,spins:s.state.spins};
    });
    assert.equal(visible.isCanvas,true);
    assert.equal(visible.grid,15);
    assert.equal(visible.spins,0);
    fs.mkdirSync("visual-previews",{recursive:true});
    await page.screenshot({path:`visual-previews/halloween-${name}-inicio.png`});
    await tap(page,367,1192);
    await page.waitForFunction(()=>window.__HALLOWEEN_SCENE__?.state?.spins>=1,{},{timeout:20000});
    await page.screenshot({path:`visual-previews/halloween-${name}-giro.png`});
    const next=await page.evaluate(()=>window.__HALLOWEEN_SCENE__.state);
    assert.equal(next.spins,1);
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
    assert.deepEqual(errors,[],`${name}: erros no browser: ${errors.join("; ")}`);
    console.log(`${name}: carregamento, 15 símbolos, giro, recuperação, escolha e screenshots OK`);
    await context.close();
  }finally{await browser.close();}
}
(async()=>{
  await run("chromium",chromium);
  await run("webkit",webkit);
})().catch(error=>{console.error(error);process.exitCode=1;});
