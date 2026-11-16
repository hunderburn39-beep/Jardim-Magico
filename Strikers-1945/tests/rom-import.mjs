import {chromium} from 'playwright';
import assert from 'node:assert/strict';
import {deflateRawSync} from 'node:zlib';

function makeTestZip(){
 const raw=Buffer.alloc(2097152);
 for(let i=0;i<raw.length;i++)raw[i]=((i*23+(i>>8)*11)>>>0)&255;
 const packed=deflateRawSync(raw),name=Buffer.from('u34.bin');
 const local=Buffer.alloc(30);local.writeUInt32LE(0x04034b50,0);
 local.writeUInt16LE(20,4);local.writeUInt16LE(8,8);
 local.writeUInt32LE(packed.length,18);local.writeUInt32LE(raw.length,22);
 local.writeUInt16LE(name.length,26);
 const central=Buffer.alloc(46);central.writeUInt32LE(0x02014b50,0);
 central.writeUInt16LE(20,4);central.writeUInt16LE(20,6);
 central.writeUInt16LE(8,10);
 central.writeUInt32LE(packed.length,20);central.writeUInt32LE(raw.length,24);
 central.writeUInt16LE(name.length,28);
 const end=Buffer.alloc(22);end.writeUInt32LE(0x06054b50,0);
 end.writeUInt16LE(1,8);end.writeUInt16LE(1,10);
 end.writeUInt32LE(central.length+name.length,12);
 end.writeUInt32LE(local.length+name.length+packed.length,16);
 return Buffer.concat([local,name,packed,central,name,end]);
}
const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844},deviceScaleFactor:2,isMobile:true,hasTouch:true});
const errors=[];page.on('pageerror',e=>errors.push(e.message));
await page.goto('http://127.0.0.1:8765/Strikers-1945/?test=1',{waitUntil:'load'});
assert(await page.locator('#romLoadBtn').isVisible(),'A opção original deve existir no menu');
assert(await page.evaluate(()=>!!window.StrikersROM),'O descodificador deve carregar no navegador');
const input=page.locator('#romFile');
await input.setInputFiles({name:'s1945.zip',mimeType:'application/zip',buffer:makeTestZip()});
await page.waitForFunction(()=>document.querySelector('#romInfo').textContent.includes('Blocos da ROM carregados'),{timeout:30000});
assert(await page.evaluate(()=>window.StrikersROM.isLoaded()),'O ZIP local foi descodificado');
assert(await page.locator('#romPreview').isVisible(),'Deve mostrar uma miniatura dos blocos do cenário');
assert((await page.locator('#romLoadBtn').innerText()).includes('TROCAR ROM'),'A interface deve reconhecer o carregamento');
// When reloading, browser-local storage restores graphic bytes without repicking the ZIP.
await page.reload({waitUntil:'load'});
await page.waitForFunction(()=>window.StrikersROM?.isLoaded(),{timeout:20000});
assert((await page.locator('#romInfo').innerText()).includes('recuperados'),'Restauro automático da ROM deve ser visível');

const stats=await page.evaluate(()=>{
 return Array.from({length:6},(_,i)=>{
  const c=window.StrikersROM.scene(i+1);
  const p=c.getContext('2d').getImageData(100,300,1,1).data;
  return {w:c.width,h:c.height,r:p[0],g:p[1],b:p[2],alpha:p[3]};
 });
});
assert(stats.every(s=>s.w===360&&s.h===960&&s.alpha===255),'Seis cenas válidas com pixels opacos');
const colors=new Set(stats.map(s=>[s.r,s.g,s.b].join('/')));
assert(colors.size>=4,'As seis fases têm perfis de cor próprios');
await page.locator('#startBtn').click();
await page.waitForTimeout(400);
assert((await page.locator('#romBadge').innerText()).includes('ROM LOCAL: ATIVA'),'O jogador deve ver se os blocos estão realmente ativos');
assert(await page.locator('#romBadge').isVisible(),'O estado da ROM fica visível no jogo');
assert(!(await page.locator('#romQuickBtn').isVisible()),'Se a ROM está ativa não é preciso o atalho de carregamento');

assert(await page.locator('#pauseBtn').isVisible(),'Gameplay deve continuar funcional');
await page.screenshot({path:'strikers-rom-mode.png'});
assert.deepEqual(errors,[],'Sem erros JavaScript');
console.log('STRIKERS V4.1: seleção local de ROM, restauro automático, pré-visualização, indicadores visíveis e jogabilidade OK');
await browser.close();
