/* Safari/WebKit e Chromium: o utilizador escolhe um HTML local sem publicar sprites. */
"use strict";
const assert=require("node:assert/strict");
const {chromium,webkit}=require("playwright");

async function verify(label, engine){
  const browser=await engine.launch({headless:true});
  try{
    const context=await browser.newContext({
      viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true
    });
    const page=await context.newPage();
    const errors=[];
    page.on("pageerror",e=>errors.push(e.message));
    const response=await page.goto("http://127.0.0.1:8124/Halloween/abrir-originais.html",
      {waitUntil:"domcontentloaded",timeout:25000});
    assert.equal(response.status(),200);
    assert.match(await page.title(),/Halloween III/);
    const dummy=[
      "<!doctype html><html><body>",
      '<main id="game"></main><div id="status"></div>',
      "<script>",
      'window.HALLOWEEN_ASSET_DATA={"sprites/pumpkin.webp":"data:image/webp;base64,AAA"};',
      'const Phaser={Game:function(){const c=document.createElement("canvas");',
      'c.width=720;c.height=1280;document.getElementById("game").appendChild(c);}};',
      "new Phaser.Game();",
      "<\/script></body></html>"
    ].join("\n");
    await page.locator("#file").setInputFiles({
      name:"Halloween_III_ORIGINAIS_Rebordo_3D_iPhone.html",
      mimeType:"text/html",
      buffer:Buffer.from(dummy,"utf8")
    });
    await page.frameLocator("#gameframe").locator("canvas").waitFor({
      state:"attached",timeout:12000
    });
    assert.equal(await page.locator("#gameframe").isVisible(),true);
    assert.deepEqual(errors,[],label+" teve erros JS");
    await page.locator("#toolbar").click();
    assert.equal(await page.locator("#file").isVisible(),true);
    console.log(label+": abertura de HTML local, jogo e regresso funcionam.");
    await context.close();
  }finally{await browser.close();}
}
(async()=>{
  await verify("Chromium",chromium);
  await verify("WebKit/Safari",webkit);
})().catch(e=>{console.error(e);process.exitCode=1;});
