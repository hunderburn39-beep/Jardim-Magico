import {chromium} from 'playwright';
import assert from 'node:assert/strict';

// Start a server at the repository root: python3 -m http.server 8765
const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true});
const errors=[];
page.on('pageerror',e=>errors.push(e.message));
try{
 await page.goto('http://127.0.0.1:8765/Strikers-1945/?test=1',{waitUntil:'load'});
 assert.match(await page.locator('#menu .meta').innerText(),/VERSÃO 6\.0/);
 await page.locator('#startBtn').click();
 await page.evaluate(()=>{window.__strikersTest?.setInv(100);window.__strikersTest?.disableFire()});
 const initial=await page.evaluate(()=>window.__strikersTest.snapshot());
 const box=await page.locator('#game').boundingBox();
 assert(box && box.width>200 && box.height>300,'Canvas visible on portrait mobile');
 const sx=box.x+box.width*.17,sy=box.y+box.height*.44;
 await page.mouse.move(sx,sy);await page.mouse.down();
 await page.waitForTimeout(170);
 const afterTouch=await page.evaluate(()=>window.__strikersTest.snapshot());
 assert(Math.abs(afterTouch.playerX-initial.playerX)<5,'Touching away from plane must not teleport it horizontally');
 assert(Math.abs(afterTouch.playerY-initial.playerY)<5,'Touching away from plane must not teleport it vertically');
 await page.mouse.move(sx+48,sy-36,{steps:12});
 await page.waitForTimeout(180);
 const afterDrag=await page.evaluate(()=>window.__strikersTest.snapshot());
 assert(afterDrag.playerX-initial.playerX>25,'Dragging right should move the plane right');
 assert(afterDrag.playerX-initial.playerX<55,'Dragging right should not overshoot');
 assert(initial.playerY-afterDrag.playerY>18,'Dragging up should move the plane up');
 assert(initial.playerY-afterDrag.playerY<48,'Dragging up should not overshoot');
 await page.mouse.up();
 await page.screenshot({path:'strikers-v6-touch.png',fullPage:true});
 await page.locator('#pauseBtn').click();
 assert.equal(await page.locator('#pauseScreen').isVisible(),true,'Pause button opens pause menu');
 await page.locator('#resumeBtn').click();
 assert.equal(await page.locator('#pauseScreen').isVisible(),false,'Resume returns to gameplay');
 assert.deepEqual(errors,[],'No browser runtime errors');
 console.log('STRIKERS V6: no touch teleport, relative drag, HUD and pause/resume OK');
}finally{await browser.close()}
