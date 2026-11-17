import { chromium } from 'playwright';
import assert from 'node:assert/strict';

const browser=await chromium.launch({headless:true,args:['--no-sandbox']});
const page=await browser.newPage({viewport:{width:390,height:844},isMobile:true,hasTouch:true,deviceScaleFactor:2,serviceWorkers:'allow'});
const errors=[];page.on('pageerror',e=>errors.push(e.message));
const base='http://127.0.0.1:8765/Strikers-1945/';
await page.goto(base,{waitUntil:'load'});
assert(await page.locator('#choose').isVisible(),'Página principal inicia em modo arcade original');
assert.equal(await page.locator('#startBtn').count(),0,'O remake já não ocupa a página principal');
await page.goto(base+'arcade.html',{waitUntil:'load'});
assert(await page.locator('#choose').isVisible(),'Botão para importar a ROM');
assert(await page.locator('#launch').isDisabled(),'Original não arranca sem a ROM');

const content=Buffer.alloc(5*1024*1024);
content.write('PK\x03\x04',0,'latin1');
content.write('s1945.zip local-asset test',60,'ascii');
await page.locator('#rom').setInputFiles({name:'s1945.zip',mimeType:'application/zip',buffer:content});
await page.waitForFunction(()=>!document.querySelector('#launch').disabled);
await page.evaluate(async()=>{
 await navigator.serviceWorker.register('./sw.js',{scope:'./'});
 await navigator.serviceWorker.ready;
});
if(!(await page.evaluate(()=>!!navigator.serviceWorker.controller))){
 await page.reload({waitUntil:'load'});
}
await page.waitForFunction(()=>!!navigator.serviceWorker.controller,{timeout:20000});
const info=await page.evaluate(async()=>{
 const r=await fetch('./roms/s1945.zip',{cache:'no-store'}),buf=await r.arrayBuffer();
 const first=new Uint8Array(buf.slice(0,4));
 return {status:r.status,len:buf.byteLength,type:r.headers.get('content-type'),signature:[...first]};
});
assert.equal(info.status,200,'ROM privada servida pelo Service Worker');
assert.equal(info.len,content.length,'Todos os bytes da ROM original devem chegar ao emulador');
assert.equal(info.type,'application/zip','Content-Type ZIP');
assert.deepEqual(info.signature,[80,75,3,4],'Ficheiro original ZIP preservado');

await page.reload({waitUntil:'load'});
assert(await page.locator('#launch').isEnabled(),'Rom recuperada do armazenamento local após recarregar');
const route=await page.evaluate(()=>new URL('./roms/s1945.zip',location.href).pathname);
assert(route.endsWith('/Strikers-1945/roms/s1945.zip'),'Nome arcade s1945.zip preservado');
await page.screenshot({path:'strikers-arcade-mode.png',fullPage:true});
await page.locator('#reset').click();
await page.waitForTimeout(500);
const after=await page.evaluate(async()=>{const r=await fetch('./roms/s1945.zip',{cache:'no-store'});return r.status});
assert.equal(after,404,'Remover a ROM local deve desativar o URL privado');
assert.deepEqual(errors,[]);
await browser.close();
console.log('MODO ORIGINAL: upload local, SW com s1945.zip, bytes intactos, persistência e limpeza OK. Execução FBNeo não verificada neste teste.');
