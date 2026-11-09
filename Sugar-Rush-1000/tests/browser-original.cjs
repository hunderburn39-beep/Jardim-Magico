"use strict";
const { chromium, webkit } = require("playwright");
const assert = require("node:assert/strict");
const fs = require("node:fs");

(async () => {
  fs.mkdirSync("visual-previews", { recursive: true });
  const base = "http://127.0.0.1:8139";
  for (const [name, engine] of [["chromium", chromium], ["webkit", webkit]]) {
    const browser = await engine.launch({ headless: true });
    try {
      const page = await browser.newPage({
        viewport: { width: 390, height: 844 },
        deviceScaleFactor: 2, isMobile: true, hasTouch: true,
      });
      const errors = [];
      page.on("pageerror", err => errors.push(err.message));
      const paths = [
        "images/candies/candy_0.png",
        "images/candies/candy_6.png",
        "images/candies/scatter.png",
        "images/candies/wild.png",
        "images/bg_environment_base.jpeg",
        "images/bg_environment_panel.jpeg",
        "audio/music_default.mp3"
      ];
      for(const path of paths) {
        const response = await page.request.get(base + "/Sugar-Rush-1000/play/" + path);
        assert.equal(response.status(), 200, name + " asset missing " + path);
        assert.ok(Number(response.headers()["content-length"] || 1) > 0, "Empty asset " + path);
      }
      await page.goto(base + "/index2.html", { waitUntil: "domcontentloaded" });
      await page.waitForURL(/Sugar-Rush-1000\/play\/index\.html/, {timeout: 20000});
      await page.locator("canvas").first().waitFor({state:"visible", timeout:25000});
      await page.waitForFunction(() => {
        const loader = document.getElementById("loadingOverlay");
        return !loader || loader.classList.contains("hidden");
      }, null, {timeout:30000});
      await page.screenshot({path:"visual-previews/sugar-original-"+name+"-entrada.png",fullPage:true});
      await page.keyboard.press("Space");
      await page.waitForTimeout(3400);
      await page.screenshot({path:"visual-previews/sugar-original-"+name+"-jogo.png",fullPage:true});
      assert.equal(errors.length,0,"Erros Javascript em "+name+": "+errors.join("\n"));
      console.log(JSON.stringify({browser:name,status:"OK",assets:paths.length,title:await page.title()}));
    } finally {
      await browser.close();
    }
  }
})().catch(err=>{console.error(err);process.exitCode=1;});
