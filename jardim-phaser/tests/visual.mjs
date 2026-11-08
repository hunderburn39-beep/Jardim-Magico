import {chromium} from 'playwright';
import assert from 'node:assert/strict';
import {mkdir} from 'node:fs/promises';

await mkdir('visual-previews',{recursive:true});
const browser=await chromium.launch({headless:true,args:['--no-sandbox','--disable-gpu-sandbox']});
const scenarios=[
 {name:'iphone-390x844',width:390,height:844},
 {name:'referencia-864x1536',width:864,height:1536},
 {name:'paisagem-844x390',width:844,height:390}
];
try{
 for(const scenario of scenarios){
  const page=await browser.newPage({viewport:{width:scenario.width,height:scenario.height},deviceScaleFactor:1});
  const errors=[];
  page.on('pageerror',err=>errors.push(err.message));
  await page.goto('http://127.0.0.1:8123/jardim-phaser/?qa=3.5',{waitUntil:'domcontentloaded'});
  await page.waitForFunction(()=>window.jardimPhaserScene?.ready===true,{timeout:30000});
  const metrics=await page.evaluate(()=>{
   const box=sel=>{
    const r=document.querySelector(sel)?.getBoundingClientRect();
    return r?{x:Math.round(r.x),y:Math.round(r.y),w:Math.round(r.width),h:Math.round(r.height),
      bottom:Math.round(r.bottom),right:Math.round(r.right)}:null;
   };
   const scene=window.jardimPhaserScene;
   const art=['potion','green','heart','rose','wild','stone'].map(s=>scene.textures.exists('art-'+s));
   const initial=box('.reel-window');
   scene.setBoard([
    [0,1,8],
    [2,10,4],
    [8,0,1],
    [4,2,10],
    [1,8,4]
   ]);
   scene.showWildValues?.([{c:1,r:2,value:2},{c:3,r:0,value:3}]);
   const machine=document.querySelector('.machine');
   return {
    viewport:{width:innerWidth,height:innerHeight},
    machine:box('.machine'),
    rows:Object.fromEntries(['.game-header','.jackpots','.multiplier','.frame-region','.result-text','.dashboard','.controls','footer'].map(x=>[x,box(x)])),
    reel:initial,
    spin:box('#spinBtn'),
    assets:art,
    premium:scene.premiumSymbols,
    visual:window.getComputedStyle(machine).backgroundImage,
    cssGrid:window.getComputedStyle(machine).display,
    overflow:document.documentElement.scrollWidth-innerWidth
   };
  });
  const {viewport,machine,rows,reel,spin,assets}=metrics;
  assert(assets.every(Boolean),scenario.name+' missing botanical texture');
  assert(Math.abs(reel.w/reel.h-5/3)<.04,scenario.name+' reel aspect ratio wrong: '+JSON.stringify(reel));
  assert(metrics.visual.includes('jm80-cenario-imersivo.svg'),scenario.name+' scenic background missing');
  assert(metrics.overflow<=3,scenario.name+' horizontal overflow '+metrics.overflow);
  assert(spin.w>=40&&spin.h>=40,scenario.name+' spin inaccessible');
  assert(spin.bottom<=scenario.height+4,scenario.name+' spin clipped');
  if(scenario.width<scenario.height){
   assert(metrics.cssGrid==='grid',scenario.name+' expected portrait grid');
   const order=['.game-header','.jackpots','.multiplier','.frame-region','.result-text','.dashboard','.controls','footer'];
   for(let i=0;i<order.length-1;i++){
    assert(rows[order[i]].bottom<=rows[order[i+1]].y+4,
      scenario.name+' overlapping '+order[i]+' and '+order[i+1]);
   }
  }
  assert(!errors.length,scenario.name+' JS: '+errors.join(' | '));
  await page.screenshot({path:'visual-previews/'+scenario.name+'.png',fullPage:false});
  console.log('PASS '+scenario.name+' '+JSON.stringify({...metrics,visual:'jm80-cenario-imersivo.svg'}));
  await page.close();
 }
}finally{await browser.close()}
