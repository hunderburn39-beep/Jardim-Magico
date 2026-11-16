import { chromium } from 'playwright';
import assert from 'node:assert/strict';
import { deflateRawSync } from 'node:zlib';

const sizes={'u34.bin':2097152,'u20.bin':2097152,'u22.bin':2097152,
 'u21.bin':2097152,'u23.bin':2097152,'u1.bin':262144};
function zip(){
 const parts=[],directory=[];
 for(const [name,size] of Object.entries(sizes)){
  const raw=Buffer.alloc(size);
  const patterns={'u34.bin':[0x12,0x34],'u20.bin':[0xab,0xcd],
   'u22.bin':[0x56,0x78],'u21.bin':[0x9a,0xbc],'u23.bin':[0xde,0xf0]};
  if(patterns[name]){raw[0]=patterns[name][0];raw[1]=patterns[name][1]}
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
const url='http://127.0.0.1:8765/Strikers-1945/';
await page.goto(url,{waitUntil:'load'});
assert(await page.locator('#romLoadBtn').isVisible(),'Importação visível no menu');
await page.locator('#romFile').setInputFiles({name:'s1945.zip',mimeType:'application/zip',buffer:zip()});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),null,{timeout:30000});
assert((await page.locator('#romInfo').innerText()).includes('paletas')||
 (await page.locator('#romInfo').innerText()).includes('cenário'), 'O jogo não deve fingir que os fundos são do arcade');
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
assert((await page.locator('#romBadge').innerText()).includes('FUNDO HTML5'),'Indicação honesta no jogo');
await page.screenshot({path:'strikers-v42-background.png'});
await page.goto(url+'inspector.html',{waitUntil:'load'});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),null,{timeout:30000});
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
assert.deepEqual(errors,[],'Sem erros JavaScript');
await browser.close();
console.log('STRIKERS V4.2: 4bpp packed MSB, word-swap, bancos u20/u22/u21/u23, LUT u1, fundos sem falsificações, vista e cache VALIDADOS.');
