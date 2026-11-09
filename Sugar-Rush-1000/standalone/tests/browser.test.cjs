"use strict";
const assert=require("node:assert/strict");
const fs=require("node:fs");
const {chromium,webkit}=require("playwright");

(async function main(){
  fs.mkdirSync("visual-previews",{recursive:true});
  for(const [name,browserType] of [["chromium",chromium],["webkit",webkit]]){
    const browser=await browserType.launch({headless:true});
    const page=await browser.newPage({
      viewport:{width:390,height:844},deviceScaleFactor:2,
      isMobile:true,hasTouch:true
    });
    const errors=[];
    page.on("pageerror",e=>errors.push(e.message));
    page.on("console",m=>{if(m.type()==="error")errors.push(m.text());});
    try{
      await page.goto("http://127.0.0.1:8137/index2.html",{waitUntil:"load",timeout:30000});
      await page.waitForURL(/Sugar-Rush-1000\/standalone\/index\.html/,{timeout:15000});
      await page.locator("#board canvas").waitFor({state:"visible",timeout:25000});
      await page.waitForFunction(()=>!document.querySelector("#spin").disabled,
        {timeout:25000});
      assert.equal(await page.locator("#board canvas").count(),1);
      const title=await page.title();
      assert.ok(title.includes("Sugar Rush 1000"));
      await page.locator("#info").click();
      assert.equal(await page.locator("#overlay").isVisible(),true);
      await page.locator("#close-info").click();
      assert.equal(await page.locator("#overlay").isVisible(),false);
      const before=await page.locator("#balance").innerText();
      await page.locator("#spin").click();
      await page.waitForFunction(()=>{
        const el=document.getElementById("spin");
        const msg=document.getElementById("message").textContent;
        return !el.disabled&&(/Sem prémio|PRÉMIO TOTAL|PRÉMIO MÁXIMO|Limite de/.test(msg));
      },{timeout:60000});
      const after=await page.locator("#balance").innerText();
      const last=await page.locator("#lastwin").innerText();
      assert.ok(after.length>0&&last.length>0);
      assert.equal(errors.length,0,errors.join("\n"));
      await page.screenshot({path:"visual-previews/sugar-"+name+".png",fullPage:true});
      console.log(JSON.stringify({browser:name,result:"OK",before,after,last}));
    } finally{
      await browser.close();
    }
  }
})().catch(err=>{console.error(err);process.exitCode=1;});
