import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {createHash} from 'node:crypto';
import {fileURLToPath} from 'node:url';
const root=join(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(root,'index.html'),'utf8');
const images=[
 {label:'CARACOL',file:'caracol.png',bytes:1775830,sha:'4e13017587f0e7ba90f2d0d2e27a16c560db044dd2b9394910c386951fd403f8'},
 {label:'BULE',file:'bule.png',bytes:1699781,sha:'2b4ad5075cb18e8bb8f50effc27eb1f9e94c54dca66c14382f4f52e2f5d2340b'},
 {label:'COGUMELO',file:'cogumelo.png',bytes:1675215,sha:'bbd3318a0cb049d25ef757f7337141852503c1b208bde3674fe460fa2a2134bd'},
 {label:'PEDRA',file:'pedra.png',bytes:2001329,sha:'2ee7cb9e6328c62011a83fff29b28f2780a2bff30abd873827c3cddb1748d252'},
];
for(const {label,file,bytes,sha} of images){
 const path='assets/figuras-3d-aprovadas-20261114/'+file;
 const buf=readFileSync(join(root,path));
 assert.equal(buf.length,bytes,label+' must be the identical file');
 assert.equal(createHash('sha256').update(buf).digest('hex'),sha,label+' was altered');
 assert.equal(buf.subarray(0,8).toString('hex'),'89504e470d0a1a0a',label+' is PNG');
 assert.equal(buf.readUInt32BE(16),1254);
 assert.equal(buf.readUInt32BE(20),1254);
 assert.equal(buf[25],6,label+' must have a transparent RGBA channel');
 assert.equal((html.match(new RegExp(path.replace(/[.*+?^${ }()|[\]\\]/g,'\\$&'),'g'))||[]).length,2,
  label+' must appear in TYPES and preload exactly once each');
 assert.ok(html.includes('{label:"'+label+'",image:"'+path+'"}'),label+' symbol id mapped');
}
const css=html.match(/<style>([\s\S]*?)<\/style>/)?.[1]||'';
assert.match(css,/V3\.5 — quatro figuras 3D aprovadas/);
assert.match(css,/:not\(\.winning\) img\.jmSymbolArt:is\(/);
assert.match(css,/filter:none!important/);
assert.match(css,/--jm-fit:98%!important/);
const approved=readFileSync(join(root,'assets','jardim-magico-fundo-aprovado-original.png'));
assert.equal(createHash('sha256').update(approved).digest('hex'),
 'fac09acce095ad40f9d4930877e8d329941411581efb7fa758c061d1de28dcb2','Approved full background unchanged');
const script=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
new Function(script);
assert.ok(html.includes('id="openBonusBuy"'),'Compact bonus button untouched');
console.log('4 approved 3D PNGs are byte-identical transparent originals in game and preloads, background preserved.');
