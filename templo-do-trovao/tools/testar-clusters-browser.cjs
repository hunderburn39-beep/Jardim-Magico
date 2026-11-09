"use strict";
const assert=require("node:assert/strict");
const fs=require("node:fs");
const {chromium,webkit}=require("playwright");
const output="visual-previews";
fs.mkdirSync(output,{recursive:true});
(async()=>{
  for(const [name,engine] of [["chromium",chromium],["webkit",webkit]]){
    const browser=await engine.launch({headless:true});
    try{
      const page=await browser.newPage({
        viewport:{width:390,height:844},
        deviceScaleFactor:2,isMobile:true,hasTouch:true,
        // Perfil de Safari iPhone real: o Phaser usa Canvas como no dispositivo.
        userAgent:"Mozilla/5.0 (iPhone; CPU iPhone OS 17_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.6 Mobile/15E148 Safari/604.1"
      });
      const errors=[];
      page.on("pageerror",e=>errors.push(e.message));
      const response=await page.goto(
        "http://127.0.0.1:8144/templo-do-trovao/clusters-v19.html",
        {waitUntil:"domcontentloaded",timeout:30000}
      );
      assert.equal(response.status(),200,name+" HTML falhou");
      await page.waitForFunction(
        ()=>document.body.classList.contains("ready"),null,{timeout:35000}
      );
      await page.locator("canvas").first().waitFor({state:"visible",timeout:15000});
      const mode=await page.evaluate(()=>({
        scene:window.PTMode,
        cols:window.PTLogic?.COLS,
        rows:window.PTLogic?.ROWS,
        match:window.PTLogic?.MIN_MATCH,
        maxWin:window.PTLogic?.MAX_MULTIPLE,
      }));
      assert.deepEqual({...mode},{scene:"phaser",cols:7,rows:7,match:5,maxWin:5000});
      await page.screenshot({path:output+"/portoes-v19-"+name+"-entrada.png",fullPage:true});
      const before=await page.evaluate(()=>{
        try{return JSON.parse(localStorage.getItem("portoes-phaser-clusters-v19")||"{}")}catch{return {}}
      });
      // No iPhone 390x844, RODAR está centrado em x=195,y=H-132.
      await page.mouse.click(195,712);
      await page.waitForFunction(()=>{
        try{
          const s=JSON.parse(localStorage.getItem("portoes-phaser-clusters-v19")||"{}");
          return Number(s.spins)>0;
        }catch{return false}
      },null,{timeout:20000});
      await page.waitForTimeout(2200);
      await page.screenshot({path:output+"/portoes-v19-"+name+"-giro.png",fullPage:true});
      const after=await page.evaluate(()=>JSON.parse(localStorage.getItem("portoes-phaser-clusters-v19")||"{}"));
      assert.ok(after.spins>Number(before.spins||0),name+" nao rodou");
      assert.equal(errors.length,0,errors.join("\n"));
      console.log(JSON.stringify({name,result:"OK",before:before.spins||0,
        after:after.spins,mode,balance:after.balance}));
    }finally{await browser.close();}
  }
})().catch(e=>{console.error(e);process.exitCode=1;});
