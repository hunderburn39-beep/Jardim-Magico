import { chromium } from 'playwright';
import assert from 'node:assert/strict';
const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true});
const errors=[];page.on('pageerror',e=>errors.push(e.message));
const expected=[
 {name:'P-38 Lightning',primary:2,missiles:2,weapon:'DUPLO'},
 {name:'Spitfire',primary:2,missiles:2,weapon:'RAJADA'},
 {name:'Zero',primary:3,missiles:2,weapon:'LEQUE'},
 {name:'Mustang',primary:1,missiles:2,weapon:'PESADO'}
];
for(let craft=0;craft<4;craft++){
 const spec=expected[craft];
 await page.goto('http://127.0.0.1:8765/Strikers-1945/remake.html?test=1',{waitUntil:'load'});
 await page.locator('.planeCard').nth(craft).click();
 await page.locator('#startBtn').click();
 const hasHook=await page.evaluate(()=>Boolean(window.__strikersTest));
 assert(hasHook,'Debug test interface em localhost');
 assert((await page.locator('#weaponHint').innerText()).includes(spec.weapon),spec.name+' rótulo de arma');

 // Each aircraft must have a genuinely different base weapon pattern.
 const basic=await page.evaluate(()=>{
  const t=window.__strikersTest;t.resetShots();t.stopRockets();t.prime();t.stopRockets();t.advance(.016);
  return t.snapshot()
 });
 const primaries=basic.bullets.filter(b=>b.owner==='player'&&b.kind==='shot');
 assert.equal(primaries.length,spec.primary,spec.name+' tiros base');
 if(craft===2)assert(primaries.some(b=>b.vx<0)&&primaries.some(b=>b.vx>0),'O Zero deve disparar em leque');
 if(craft===3)assert(primaries[0].vy< -600,'O Mustang tem canhão pesado');

 // Side-target rockets should launch from tail/wing and turn toward each enemy.
 const salvo=await page.evaluate(()=>{
  const t=window.__strikersTest;t.setLevel(3);t.resetShots();
  t.spawnTarget(68,235);t.spawnTarget(295,230);t.prime();t.advance(.018);
  return t.snapshot()
 });
 const rockets=salvo.bullets.filter(b=>b.kind==='missile');
 assert.equal(rockets.length,spec.missiles,spec.name+' salva secundária');
 assert(rockets.every(b=>b.variant===craft),spec.name+' identidade visual própria dos mísseis');
 if(craft===0||craft===3){
  assert(rockets.some(b=>b.fromWing===false),spec.name+' deve lançar mísseis pela cauda');
 }
 if(craft===1||craft===2||craft===3){
  assert(rockets.some(b=>b.fromWing===true),spec.name+' deve ter foguetes laterais');
 }
 if(craft===3){
  const before=await page.evaluate(()=>{
   const t=window.__strikersTest;t.resetShots();
   t.launch(-1,false);t.launch(1,true);return t.snapshot().bullets.filter(b=>b.kind==='missile')
  });
  assert(before[0].targetX<180&&before[1].targetX>180,'Cada míssil escolhe um inimigo do seu lado');
  const after=await page.evaluate(()=>{
   const t=window.__strikersTest;t.advance(.28);return t.snapshot().bullets.filter(b=>b.kind==='missile')
  });
  assert(after.length===2,'Mísseis permanecem ativos durante a curva');
  assert(Math.abs(after[0].angle-before[0].angle)>.05,'Míssil de cauda curva para o alvo');
  assert(Math.abs(after[1].angle-before[1].angle)>.05,'Míssil lateral curva para o alvo');
  assert(after[0].targetX<180&&after[1].targetX>180,'Mantêm alvos em lados diferentes');
 }
 const fx=await page.evaluate(variant=>{
  const t=window.__strikersTest;t.detonate(variant);return t.snapshot();
 },craft);
 assert(fx.waves.some(w=>w.style===['electric','fire','plasma','heavy'][craft]),spec.name+' explosão temática');
 assert(fx.waves.some(w=>w.color===['#83eaff','#ffc363','#9eff89','#ff7959'][craft]),spec.name+' cor de explosão');
 assert(fx.audioState==='running'||fx.audioState==='suspended',spec.name+' contexto de áudio criado');
 await page.waitForTimeout(95);
 await page.screenshot({path:'strikers-explosion-'+craft+'.png'});
 await page.waitForTimeout(250);
 await page.screenshot({path:'strikers-weapon-'+craft+'.png'});
 console.log(spec.name+': '+primaries.length+' tiros base, '+rockets.length+' foguetes na arma evoluída');
}
assert.deepEqual(errors,[],'Sem erros JavaScript nos quatro aviões');
await browser.close();
console.log('STRIKERS V3.2: quatro sistemas, modelos de mísseis, explosões específicas e som iniciado VALIDADOS.');
