import { chromium } from 'playwright';
import assert from 'node:assert/strict';
import { deflateRawSync } from 'node:zlib';

const sizes={'2s.u40':262144,'3s.u41':262144,'3-u63.bin':131072,'u61.bin':2097152,'u34.bin':2097152,'u20.bin':2097152,'u22.bin':2097152,
 'u21.bin':2097152,'u23.bin':2097152,'u1.bin':262144};
function zip(){
 const parts=[],directory=[];
 for(const [name,size] of Object.entries(sizes)){
  const raw=Buffer.alloc(size);
  const patterns={'u34.bin':[0x12,0x34],'u20.bin':[0xab,0xcd],
   'u22.bin':[0x56,0x78],'u21.bin':[0x9a,0xbc],'u23.bin':[0xde,0xf0]};
  if(patterns[name]){raw[0]=patterns[name][0];raw[1]=patterns[name][1]}
  if(name==='2s.u40')raw.set([0xff,0xff,0x00,0x00,0x00,0x00,0x00,0x00],0);
  if(name==='u61.bin'){raw.set([0,12,0,0,8,0xff,0xf7,0,0xf0,0,0xff,0],0);raw[0x0c00]=64;raw[0x0c01]=192;raw[0x0c02]=32;}
  if(name==='3s.u41')raw.set([0x00,0x80,0x00,0x04,0xe8,0x98,0xe8,0x98],0);
  if(name==='u1.bin'){ // ROM_REGION16_LE: LUT[0..4] map into all four graphics banks
   const indices=[0,1,16384,32768,49152];
   indices.forEach((id,i)=>raw.writeUInt16LE(id,i*2));
  }
  const packed=deflateRawSync(raw),n=Buffer.from(name);
  const offset=parts.reduce((sum,p)=>sum+p.length,0);
  const h=Buffer.alloc(30);h.writeUInt32LE(0x04034b50,0);h.writeUInt16LE(20,4);
  h.writeUInt16LE(8,8);h.writeUInt32LE(packed.length,18);h.writeUInt32LE(size,22);
  h.writeUInt16LE(n.length,26);
  parts.push(h,n,packed);
  const ch=Buffer.alloc(46);ch.writeUInt32LE(0x02014b50,0);
  ch.writeUInt16LE(20,4);ch.writeUInt16LE(20,6);ch.writeUInt16LE(8,10);
  ch.writeUInt32LE(packed.length,20);ch.writeUInt32LE(size,24);
  ch.writeUInt16LE(n.length,28);ch.writeUInt32LE(offset,42);
  directory.push(ch,n);
 }
 const cd=Buffer.concat(directory),local=Buffer.concat(parts),e=Buffer.alloc(22);
 e.writeUInt32LE(0x06054b50,0);e.writeUInt16LE(6,8);e.writeUInt16LE(6,10);
 e.writeUInt32LE(cd.length,12);e.writeUInt32LE(local.length,16);
 return Buffer.concat([local,cd,e]);
}
const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true});
const errors=[];page.on('pageerror',e=>errors.push(e.message));
const url='http://127.0.0.1:8765/Strikers-1945/remake.html?test=1';
await page.goto(url,{waitUntil:'load'});
assert(await page.locator('#romLoadBtn').isVisible(),'Importação visível no menu');
await page.locator('#romFile').setInputFiles({name:'s1945.zip',mimeType:'application/zip',buffer:zip()});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),null,{timeout:30000});
const fullRom=await page.evaluate(()=>window.StrikersROM.codeInfo());
assert.equal(fullRom.programBytes,1048576,'Programa original 68EC020: 1 MiB interleaved');
assert.equal(fullRom.resetSP,0xffff8000,'Original reset stack recovered from two interleaved chips');
assert.equal(fullRom.resetPC,0x400,'68EC020 reset vector comes from real ROM lane order');
assert.equal(await page.evaluate(()=>window.StrikersROM.programWord(0)),0xffff,'Program words are assembled big-endian');
assert.equal(fullRom.soundProgramBytes,131072,'Programa Z80/LZ8420M recuperado');
assert.equal(fullRom.opl4Bytes,2097152,'Banco OPL4 recuperado');
const opl4Info=await page.evaluate(()=>window.StrikersROM.opl4WaveInfo());
assert.equal(opl4Info.length,1,'Wave table of test fixture has exactly one valid sample');
assert.equal(opl4Info[0].offset,0xc00,'Sample audio data starts after the Yamaha header table');
assert.deepEqual(await page.evaluate(()=>Array.from(window.StrikersROM.opl4PCM(0).slice(0,3))),[0.5,-0.5,0.25],'8-bit signed PCM decoded without synthesis');
assert.equal(fullRom.protectionMcuDumped,false,'MCU de proteção não está presente no dump');
await page.waitForFunction(()=>window.StrikersFlightReplay?.ready(),null,{timeout:30000});
assert.equal(await page.evaluate(()=>window.StrikersFlightReplay.stats().frames),116,'116 instantes originais dos caças descodificados');
const recordedSeconds=await page.evaluate(()=>window.StrikersFlightReplay.stats().flightTime);
assert(Math.abs(recordedSeconds-61.33333)<.05,'A captura MAME deve ocupar os 61,33 segundos reais, sem compressão de tempo');

const flightMotion=await page.evaluate(()=>{
 const api=window.StrikersFlightReplay,seen=new Map();
 let samples=0,moving=0,blend=0,fade=0,duplicate=0,maxJump=0,changedCodes=0;
 api.reset();
 for(let i=0;i<=Math.ceil(61.333*30);i++){
  const current=api.test.smooth(i/30);
  const slots=new Set();
  for(const e of current){
   samples++;
   if(slots.has(e.id))duplicate++;
   slots.add(e.id);
   if(e.mix>0&&e.mix<1)blend++;
   if(e.alpha>.02&&e.alpha<1)fade++;
   const previous=seen.get(e.uid);
   if(previous){
    const delta=Math.hypot(e.x-previous.x,e.y-previous.y);
    maxJump=Math.max(maxJump,delta);
    if(delta>.4)moving++;
    if(e.code!==previous.code)changedCodes++;
   }
   seen.set(e.uid,e);
  }
 }
 api.reset();
 return {samples,moving,blend,fade,duplicate,maxJump,changedCodes};
});
assert(flightMotion.samples>1200,'Há caças nos fotogramas da primeira fase');
assert(flightMotion.moving>300,'Os inimigos percorrem trajetórias animadas');
assert(flightMotion.blend>20,'As viragens usam transições dos sprites originais');
assert(flightMotion.fade>10,'Entradas e saídas de inimigos são suavizadas');
assert.equal(flightMotion.duplicate,0,'O mesmo slot da ROM não é duplicado no ecrã');
assert(flightMotion.maxJump<70,'Um avião persistente não pode teletransportar-se entre fotogramas');
console.log('Teste de fluidez da fase 1:',flightMotion);


await page.waitForFunction(()=>window.StrikersOriginalStage1?.ready(),null,{timeout:30000});
assert(await page.locator('#romPreview').isVisible(),'Pré-visualização visível');
assert(await page.locator('#romInspectBtn').isVisible(),'Ligação ao inspetor dos sprites visível');
const state=await page.evaluate(()=>{
 const p=window.StrikersROM;
 return {stats:p.stats(),scene:p.scene(1),
  p0:p.test.pixel('tile',0,0,0),p1:p.test.pixel('tile',0,1,0),
  p2:p.test.pixel('tile',0,2,0),p3:p.test.pixel('tile',0,3,0),
  sprite0:p.test.pixel('sprite',0,0,0),
  mapped2:p.test.lookup(2),mapped3:p.test.lookup(3),mapped4:p.test.lookup(4),
  bank2:p.test.pixel('sprite',2,0,0,true),
  bank3:p.test.pixel('sprite',3,0,0,true),
  bank4:p.test.pixel('sprite',4,0,0,true)};
});
assert.equal(state.p0,3,'MSB nibble after word swap');
assert.equal(state.p1,4,'LSB nibble after word swap');
assert.equal(state.p2,1,'MSB nibble on source byte zero');
assert.equal(state.p3,2,'LSB nibble on source byte zero');
assert.equal(state.sprite0,12,'Sprite 4bpp word swap');
assert.deepEqual([state.mapped2,state.mapped3,state.mapped4],[16384,32768,49152],'u1.bin little-endian sprite LUT');
assert.deepEqual([state.bank2,state.bank3,state.bank4],[7,11,15],'Correct sprite ROM bank order');
assert.equal(state.stats.tiles,16384);
assert.equal(state.stats.spriteTiles,65536);
assert.equal(state.stats.lutEntries,131072);
assert.equal(state.scene,null,'Não inventar um mapa arcade a partir dos sprites');
await page.locator('#startBtn').click();await page.waitForTimeout(150);
await page.waitForFunction(()=>window.StrikersOriginalStage1?.ready(),null,{timeout:15000});
assert.equal(await page.evaluate(()=>window.StrikersOriginalStage1.info().frames),122,'122 capturas reais do tilemap presentes');
 const mapContinuation=await page.evaluate(()=>{
  const api=window.StrikersOriginalStage1,info=api.info(),canvas=document.createElement('canvas');
  canvas.width=48;canvas.height=96;
  const context=canvas.getContext('2d');
  const hash=t=>{
   context.clearRect(0,0,48,96);api.draw(context,t,48,96);
   const data=context.getImageData(0,0,48,96).data;let h=2166136261;
   for(let i=0;i<data.length;i+=53)h=Math.imul(h^data[i],16777619);
   return h>>>0;
  };
  return {seconds:info.recordedSeconds,after:hash(info.recordedSeconds+1),later:hash(info.recordedSeconds+2)};
 });
 assert(Math.abs(mapContinuation.seconds-32.2667)<.05,'A captura dos mapas originais termina aos 32,27 s');
 assert.notEqual(mapContinuation.after,mapContinuation.later,'O cenário deve continuar a deslocar-se após terminar a captura de mapas');
assert((await page.locator('#romBadge').innerText()).includes('FUNDO ORIGINAL'),'Fundo da fase 1 deve estar integrado na jogabilidade');
const arts=await page.evaluate(()=>{
  const t=window.__strikersTest;t.invulnerable();return t.snapshot().romSkinTest;
});
assert(arts.length>=7&&arts.every(a=>a.canvas),'Todos os tipos de inimigos devem descodificar sprites originais');
await page.evaluate(()=>{const t=window.__strikersTest;t.invulnerable();t.advance(10)});
const battle=await page.evaluate(()=>window.__strikersTest.snapshot());
assert(battle.airbaseCue>=3,'Entradas da primeira fase de acordo com sequência planeada');
assert(battle.flightEnemies.length>0,'Os inimigos da fase 1 devem usar fotogramas capturados da ROM');
assert(battle.flightEnemies.some(e=>e.code>=9000),'Cada avião tem um código de sprite real');
const animation=await page.evaluate(()=>{
 const t=window.__strikersTest;
 return {a:t.flightSource(12).map(i=>i.code),b:t.flightSource(15).map(i=>i.code)};
});
assert(animation.a.length>0&&animation.b.length>0,'Fotogramas originais com aviões disponíveis');
assert(animation.a.join(',')!==animation.b.join(','),'Códigos dos sprites mudam durante as curvas e viragens');

// Enemy aircraft now follow the recorded ROM sprite stream, not the old synthetic swooper.
await page.evaluate(()=>{const t=window.__strikersTest;t.disableFire();t.resetShots();t.advance(8.9)});
const before=await page.evaluate(()=>window.__strikersTest.snapshot());
assert(before.flightEnemies.length>0,'A fase original deve conter aeronaves capturadas pela ROM');
await page.evaluate(()=>window.__strikersTest.advance(.7));
const after=await page.evaluate(()=>window.__strikersTest.snapshot());
assert(after.flightEnemies.length>0,'A fase original deve continuar a apresentar aviões');
const ids=new Map(before.flightEnemies.map(e=>[e.id,e]));
const moving=after.flightEnemies.filter(e=>ids.has(e.id)).map(e=>({new:e,old:ids.get(e.id)}))
 .filter(v=>Math.hypot(v.new.x-v.old.x,v.new.y-v.old.y)>8);
assert(moving.length>0,'Os aviões capturados devem executar trajetórias dinâmicas, não ficar parados');
console.log('Caças reais em movimento:',moving.slice(0,4).map(v=>({id:v.new.id,
 anterior:[Math.round(v.old.x),Math.round(v.old.y)],
 atual:[Math.round(v.new.x),Math.round(v.new.y)]})));
await page.screenshot({path:'strikers-v54-flight-turns.png'});
await page.screenshot({path:'strikers-v52-enemies.png'});
await page.screenshot({path:'strikers-v42-background.png'});
await page.goto(url.split('?')[0]+'inspector.html',{waitUntil:'load'});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),null,{timeout:30000});
await page.locator('#viewer').waitFor({state:'visible',timeout:15000});
assert(await page.locator('#viewer').isVisible(),'O inspetor restaurou os dados via IndexedDB');
const canvas=page.locator('#assetGrid');assert((await canvas.evaluate(x=>x.width))===512);
await page.locator('#kind').selectOption('sprite');
await page.locator('#next').click();
assert((await page.locator('#range').innerText()).includes('128'));
await page.locator('#start').fill('2');
await page.locator('#dims').selectOption('4');
await page.locator('#groupRefresh').click();
assert((await page.locator('#groupImg').evaluate(x=>x.width))===192,'Sprite 4x4 descodificado por LUT');
await page.screenshot({path:'strikers-v42-sprites.png'});
await page.goto('http://127.0.0.1:8765/Strikers-1945/sprites-rom.html',{waitUntil:'load'});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),null,{timeout:30000});
assert.equal(await page.locator('#gallery .item').count(),12,'O catálogo deve mostrar doze referências originais');
assert.equal(await page.locator('#sprite').evaluate(x=>x.width),64,'O visualizador deve montar quatro tiles na dimensão vertical');
await page.locator('#code').fill('6584');
await page.locator('#code').dispatchEvent('change');
assert((await page.locator('#meta').innerText()).includes('6584'),'Navegação pelo índice ROM válida');
await page.locator('#palette').selectOption('11');
assert((await page.locator('#meta').innerText()).includes('Banco de cor temporário: 11'),'Banco de cor documentado');
await page.locator('#aircraft').selectOption('p38');
await page.locator('#apply').click();
const candidate=await page.evaluate(()=>JSON.parse(localStorage.getItem('strikers1945_plane_asset_candidates_v1')).p38);
assert.equal(candidate.code,6584,'O sprite guardado usa código diretamente da LUT da ROM');
assert.equal(candidate.bank,'11','O teste visual preserva o banco de cor selecionado');
await page.screenshot({path:'strikers-original-rom-sprite-viewer.png',fullPage:true});
await page.goto('http://127.0.0.1:8765/Strikers-1945/?test=1',{waitUntil:'load'});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded()&&window.StrikersFlightReplay?.ready()&&window.StrikersOriginalStage1?.ready(),null,{timeout:30000});
await page.locator('#startBtn').click();
assert(await page.evaluate(()=>window.__strikersTest.snapshot().nativePlayerAsset),'O motor principal usa pixels originais do sprite selecionado em vez do avião vetorial');
await page.goto('http://127.0.0.1:8765/Strikers-1945/stage1-captured.html',{waitUntil:'load'});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),null,{timeout:30000});
await page.locator('#analyze').click();
await page.waitForFunction(()=>window.StrikersStage1Catalog?.ready(),null,{timeout:30000});
const captureStats=await page.evaluate(()=>window.StrikersStage1Catalog.stats());
assert.equal(captureStats.moments,116,'Catalog must process all 116 captured original RAM snapshots');
assert.equal(captureStats.frameFirst,480,'Original first captured MAME frame');
assert.equal(captureStats.frameLast,4160,'Original last captured MAME frame');
assert(captureStats.rawItems>400,'Catalog must include genuine sprite register entries');
assert(captureStats.uniqueEntries>20,'Deduplicated sprite register catalog must contain variants');
assert.equal(await page.locator('#results .entry').count(),24,'Mobile gallery paginates at 24');
await page.locator('#group').selectOption('grandes');
assert(!(await page.locator('#stats').innerText()).includes('Ainda sem resultados'),'Group filtering shows ground truth count');
assert(await page.locator('#csv').isEnabled(),'Captured register CSV export available');
await page.screenshot({path:'strikers-v75-captured-rom-catalog.png',fullPage:true});
assert.deepEqual(errors,[],'Sem erros JavaScript');
await browser.close();
console.log('STRIKERS V5.5: sprites originais em jogo, caças e bombardeiros, 4bpp packed MSB, word-swap, LUT, 122 frames da fase 1 integrados, vista e cache VALIDADOS.');
