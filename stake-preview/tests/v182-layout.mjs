import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {join,dirname} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';
const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
const css=html.split('</style>')[0];
const visual=css.split('/* V1.8.2 • Mobile polish')[1];
const refinement=css.split('/* V1.8.3 — subir o logótipo')[1];
assert.ok(refinement,'V1.8.3 top and betting spacing corrections are present');
assert.ok(visual,'V1.8.2 garden graphics and responsive support retained');
assert.match(html,/Stake Preview V1\.8\.4/);
assert.match(html,/V1\.8\.4 • TAMBORES MAIS LENTOS/);
assert.match(visual,/\.top\s*\{[^}]*safe-area-inset-top/s);
assert.match(visual,/\.logo-wrap\s*\{[^}]*safe-area-inset-top/s,'Safe top on notched iPhones');
assert.match(refinement,/\.logo-wrap\s*\{[^}]*height:10\.6%/s);
assert.match(visual,/\.statline\s*\{[^}]*top:23\.4%/s);
assert.match(visual,/\.frame\s*\{[^}]*top:29\.4%;height:30\.2%/s);
assert.match(visual,/\.bonus-row\s*\{[^}]*top:63\.4%;height:7\.0%/s);
assert.match(visual,/\.money-row\s*\{[^}]*top:74\.5%;height:7\.8%/s);
assert.match(visual,/\.actions\s*\{[^}]*top:86\.2%;height:10\.5%/s);
assert.match(visual,/@media\(max-height:700px\)/,'Short iPhone screens get their own CSS');
assert.match(visual,/@media\(max-width:360px\)/,'Narrow screen controls are smaller');
assert.match(visual,/prefers-reduced-motion:reduce/,'Accessible motion reduction');
assert.match(visual,/\.scene-fx\s*\{[^}]*pointer-events:none/s,'Decorations do not block game clicks');
assert.match(visual,/\.scene-fx\s*\{[^}]*z-index:1/s,'Decorations render behind interactive controls');
assert.equal((html.match(/class="firefly"/g)||[]).length,10);
assert.equal((html.match(/class="butterfly(?: butterfly-purple)?"/g)||[]).length,2);
assert.match(visual,/@keyframes floatFirefly/);
assert.match(visual,/@keyframes wingFlap/);
assert.doesNotMatch(visual,/url\(['"]?assets\/jardim-v18-reference\.png/);
assert.match(html,/url\('assets\/jardim-v181-cenario-limpo\.svg'\)/);
assert.match(html,/src="assets\/jardim-v181-logo\.svg"/);
assert.match(html,/url\('assets\/jardim-v181-moldura\.svg'\)/);
for(const id of ['spin','auto','turbo','minus','plus','buyFree','buyHold','bet','balance','reels','paytableBtn','skipBtn','historyBtn']){
  assert.ok(html.includes('id="'+id+'"'),'Must keep clickable or live control '+id);
}
const main=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];assert.ok(main);
new vm.Script(main,{filename:'stake-preview/index.html'});
// Pure geometry sanity checks for both main layout variants; base percentage
// bounds stay separated even with Safari safe-area.
const phones=[
 {name:'iPhone Pro Max',width:428,height:926,safeTop:59,compact:false},
 {name:'iPhone Pro',width:393,height:852,safeTop:47,compact:false},
 {name:'iPhone 12 Mini',width:375,height:812,safeTop:47,compact:false},
 {name:'iPhone SE',width:375,height:667,safeTop:0,compact:true},
 {name:'Narrow Android',width:320,height:568,safeTop:0,compact:true}
];
for(const {name,width,height,safeTop,compact} of phones){
 const logoTop=Math.max((compact?.062:.068)*height,safeTop+(compact?18:20));
 const logoBottom=logoTop+(compact?.11:.106)*height;
 const statTop=(compact?.23:.234)*height;
 const statEnd=statTop+(compact?.042:.041)*height;
 const frameTop=(compact?.29:.294)*height;
 const frameEnd=frameTop+(compact?.297:.302)*height;
 const bonusTop=(compact?.63:.634)*height;
 const bonusEnd=bonusTop+(compact?.069:.07)*height;
 const moneyTop=(compact?.721:.738)*height;
 const moneyEnd=moneyTop+(compact?.078:.067)*height;
 const shortcutTop=(compact?.802:.81)*height;
 const actionsTop=(compact?.86:.867)*height;
 assert.ok(logoTop>=safeTop+18,name+': logo respects the iPhone safe area');
 assert.ok(logoBottom<=statTop+0.5,name+': full logo must end before scorecards');
 assert.ok(statEnd<frameTop,name+': scorecards must not obscure the reels');
 assert.ok(frameEnd<bonusTop,name+': animated reels must not cover bonus purchase buttons');
 assert.ok(bonusEnd<moneyTop,name+': bonus buttons must not cover credits');
 assert.ok(moneyEnd+1<=shortcutTop,name+': quick bets must not overlap the balance');
 const shortcutEnd=shortcutTop+.031*height;
 assert.ok(actionsTop-shortcutEnd>=.02*height,name+': at least 2% height between quick bets and spin controls');
 assert.ok(shortcutTop<actionsTop,name+': betting and spin controls are in logical order');
 assert.ok(width>=300,name+': phone width supported');
}
console.log('Stake Preview V1.8.4: responsive iPhone/Android layouts, full logo, animated background, real controls and reduced motion passed.');
