import {chromium} from 'playwright';
import assert from 'node:assert/strict';

// Run after starting a local server on port 8765 at the repository root.
const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844},isMobile:true,hasTouch:true});
const errors=[];
page.on('pageerror',e=>errors.push(e.message));
try{
 await page.goto('http://127.0.0.1:8765/Strikers-1945/?test=1',{waitUntil:'load'});
 await page.locator('#startBtn').click();
 const result=await page.evaluate(()=>{
  const t=window.__strikersTest;
  if(!t)throw Error('Test instrumentation missing');
  t.setPosition(180,520);
  t.setInv(0);
  t.disableFire();
  t.resetShots();
  const firstHP=t.snapshot().playerHP;
  const groundCollision=t.testContact({kind:'romGround',romFlight:true,role:'ground',x:180,y:520,w:220,h:180,alpha:1});
  const fadedCollision=t.testContact({kind:'romPlane',romFlight:true,role:'bomber',x:180,y:520,w:220,h:180,alpha:.3});
  const farFuselageCollision=t.testContact({kind:'romPlane',romFlight:true,role:'bomber',x:235,y:520,w:220,h:180,alpha:1});
  const closeFuselageCollision=t.testContact({kind:'romPlane',romFlight:true,role:'bomber',x:195,y:520,w:220,h:180,alpha:1});
  t.addGround('tank',180,520);
  t.advance(1/60);
  const afterGround=t.snapshot().playerHP;
  // One 160px/frame bullet movement traverses the player cockpit.
  t.addEnemyShot(100,520,10000,0);
  t.advance(.016);
  const afterBullet=t.snapshot();
  // Invulnerability must block subsequent hits while preserving the game state.
  t.addEnemyShot(100,520,10000,0);
  t.advance(.016);
  return {firstHP,groundCollision,fadedCollision,farFuselageCollision,
    closeFuselageCollision,afterGround,afterBulletHP:afterBullet.playerHP,
    afterBulletInv:afterBullet.playerInv,afterSecond:t.snapshot().playerHP};
 });
 assert.equal(result.firstHP,3,'Initial lives');
 assert.equal(result.groundCollision,false,'Ground replay art must not collide');
 assert.equal(result.fadedCollision,false,'Fading sprite must not hit player');
 assert.equal(result.farFuselageCollision,false,'Far wing cannot count as fuselage hit');
 assert.equal(result.closeFuselageCollision,true,'Visible central fuselage must collide');
 assert.equal(result.afterGround,3,'Standing on tank does not remove life');
 assert.equal(result.afterBulletHP,2,'Fast bullet crossing cockpit removes one life');
 assert(result.afterBulletInv>2.5,'Hit must activate protection');
 assert.equal(result.afterSecond,2,'Invulnerable player cannot lose a second life');
 // Visual clarity: the projectile cap stops an entire wall of enemy bullets.
 const clutter=await page.evaluate(()=>{
  const t=window.__strikersTest;
  t.resetShots();t.setInv(100);t.disableFire();
  for(let i=0;i<65;i++)t.addEnemyShot(200,180,0,140);
  return {
   enemyShots:t.snapshot().bullets.filter(b=>b.owner==='enemy').length,
   romBadge:document.querySelector('#romBadge').style.display,
   build:document.querySelector('#menu .meta')?.textContent
  };
 });
 assert(clutter.enemyShots<=16,'Stage 1 must cap enemy bullets to 16');
 assert.equal(clutter.romBadge,'none','No ROM badge covering the playing field');
 assert.match(clutter.build,/VERSÃO 5\.7/,'Correct visible build');
 assert.deepEqual(errors,[],'No browser JS errors');
 console.log('STRIKERS V5.7: ground, fuselage, fade, swept bullet, invulnerability OK');
}finally{await browser.close()}
