import {chromium} from 'playwright';
import assert from 'node:assert/strict';
import {mkdir} from 'node:fs/promises';

await mkdir('visual-previews',{recursive:true});
const browser=await chromium.launch({headless:true,args:['--no-sandbox','--disable-gpu-sandbox']});
const scenarios=[
 {name:'iphone-390x844',width:390,height:844},
 {name:'referencia-864x1536',width:864,height:1536},
 {name:'vertical-360x640',width:360,height:640}
];
try{
 for(const scenario of scenarios){
  const page=await browser.newPage({viewport:{width:scenario.width,height:scenario.height},deviceScaleFactor:1});
  const errors=[];
  page.on('pageerror',err=>errors.push(err.message));
  await page.goto('http://127.0.0.1:8123/jardim-phaser/?qa=4.1',{waitUntil:'domcontentloaded'});
  await page.waitForFunction(()=>window.jardimPhaserScene?.ready===true,{timeout:30000});
  const metrics=await page.evaluate(()=>{
   const box=sel=>{
    const r=document.querySelector(sel)?.getBoundingClientRect();
    return r?{x:Math.round(r.x),y:Math.round(r.y),w:Math.round(r.width),h:Math.round(r.height),
      bottom:Math.round(r.bottom),right:Math.round(r.right)}:null;
   };
   const scene=window.jardimPhaserScene;
   const art=['potion','green','heart','rose','wild','stone',
    'teapot','gem','mushroom','hat','scatter','coin']
    .map(s=>scene.textures.exists('art-'+s));
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
    frame:box('.frame-region .branch-frame'),
    botanicalScale:scene.getVisible(1,0)?.displayWidth,
    assets:art,
    artFailed:scene.failedAssets.filter(key=>key.startsWith('botanical-')),
    premium:scene.premiumSymbols,
    materialBaked:scene.premiumMaterialCount,
    photoWild:scene.photoWild,
    remainingHD:scene.remainingHD,
    coinHD:scene.coinHD,
    coinMissing:scene.failedAssets.includes('premium-coin'),
    visual:window.getComputedStyle(machine).backgroundImage,
    spinLabel:window.getComputedStyle(document.querySelector('#spinBtn>b')).visibility,
    spinArrow:window.getComputedStyle(document.querySelector('#spinBtn .spin-arrow')).visibility,
    touchAction:window.getComputedStyle(machine).touchAction,
    viewportMeta:document.querySelector('meta[name="viewport"]')?.content,
    portraitLockVisible:window.getComputedStyle(document.querySelector('#portraitLock')).display,
    cssGrid:window.getComputedStyle(machine).display,
    overflow:document.documentElement.scrollWidth-innerWidth
   };
  });
  const {viewport,machine,rows,reel,spin,assets}=metrics;
  assert(assets.every(Boolean),scenario.name+' missing botanical texture');
  assert(metrics.artFailed.length===0&&metrics.premium>=12&&
   metrics.materialBaked>=12&&metrics.photoWild&&
   metrics.remainingHD===5&&metrics.coinHD&&!metrics.coinMissing,
   scenario.name+' texturas HD ausentes ou WILD fotográfico não aplicado: '+
   JSON.stringify({failed:metrics.artFailed,baked:metrics.materialBaked,photoWild:metrics.photoWild}));
  assert(Math.abs(reel.w/reel.h-5/3)<.04,scenario.name+' reel aspect ratio wrong: '+JSON.stringify(reel));
  assert(metrics.visual.includes('jm80-cenario-imersivo.svg'),scenario.name+' scenic background missing');
  assert(metrics.overflow<=3,scenario.name+' horizontal overflow '+metrics.overflow);
  assert(metrics.frame&&Math.abs(metrics.frame.w/metrics.frame.h-1200/760)<.055,
   scenario.name+' moldura não deve ficar esticada '+JSON.stringify(metrics.frame));
  assert(metrics.frame.w>=reel.w+10&&metrics.frame.w<=machine.w+2,
   scenario.name+' moldura deve encaixar no tambor '+JSON.stringify(metrics.frame));
  assert(metrics.botanicalScale>180,scenario.name+' símbolos devem ser maiores');
  assert(spin.w>=40&&spin.h>=40,scenario.name+' spin inaccessible');
  assert(metrics.spinLabel==='visible'&&metrics.spinArrow==='visible',
   scenario.name+' label RODAR deve aparecer');
  assert(metrics.touchAction==='manipulation'&&
   metrics.viewportMeta.includes('maximum-scale=1')&&
   metrics.portraitLockVisible==='none',
   scenario.name+' vertical e sem zoom duplo');
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
  if(scenario.name==='iphone-390x844'){
   const timing=await page.evaluate(async()=>{
    const scene=window.jardimPhaserScene;
    const target=[
     [0,1,8],[2,10,4],[8,0,1],[4,2,10],[1,8,4]
    ];
    const started=performance.now();
    await scene.spin(target,{turbo:false});
    const elapsed=performance.now()-started;
    const exact=target.every((col,c)=>col.every((symbol,r)=>
     scene.columns[c].visible[r].symbol===scene.columns[c].visible[r].symbol &&
     scene.columns[c].visible[r].symbol===
       ['potion','green','heart','teapot','wild','gem','mushroom','hat','rose','scatter','stone','coin'][symbol]));
    return {elapsed,exact,busy:scene.busy};
   });
   assert(timing.elapsed>=2200&&timing.elapsed<6500&&
    timing.exact&&!timing.busy,
    'giro normal dura mais e pára na combinação original: '+JSON.stringify(timing));
   console.log('PASS giro normal V3.9.1: '+JSON.stringify(timing));
  }
  if(scenario.name==='iphone-390x844'){
   const checkFall=await page.evaluate(async()=>{
    const scene=window.jardimPhaserScene;
    const initial=[[0,1,8],[2,10,4],[8,0,1],[4,2,10],[1,8,4]];
    const after=initial.map(col=>col.slice());
    after[1]=[7,2,4];
    scene.recover(initial);
    await scene.burst([{c:1,r:1},{c:1,r:1}],{destroy:true,duration:420});
    await scene.cascade(after,[{c:1,r:1}],{turbo:false});
    const correct=after.every((col,c)=>col.every((id,r)=>
      scene.columns[c].visible[r].symbol===
      ['potion','green','heart','teapot','wild','gem','mushroom','hat','rose','scatter','stone','coin'][id]));
    const result={correct,busy:scene.busy,
      aligned:scene.columns.every(col=>col.visible.every((piece,r)=>
       Math.abs(piece.sprite.y-(r+.5)*180)<.1))};
    scene.recover(initial);
    return result;
   });
   assert(checkFall.correct&&!checkFall.busy&&checkFall.aligned,
    'cascata preserva combinações e a grelha: '+JSON.stringify(checkFall));
   console.log('PASS brilho e cascata V4.1: '+JSON.stringify(checkFall));
  }
  const zoomPrevented=await page.evaluate(()=>{
   const target=document.querySelector('#multiTotal');
   const event=new MouseEvent('dblclick',{bubbles:true,cancelable:true});
   const dispatched=target.dispatchEvent(event);
   return !dispatched&&event.defaultPrevented;
  });
  assert(zoomPrevented,scenario.name+' duplo toque deve impedir a ação de zoom');
  await page.screenshot({path:'visual-previews/'+scenario.name+'.png',fullPage:false});
  console.log('PASS '+scenario.name+' '+JSON.stringify({...metrics,visual:'jm80-cenario-imersivo.svg'}));
  await page.close();
 }
 const landscape=await browser.newPage({viewport:{width:844,height:390}});
 try{
  await landscape.goto('http://127.0.0.1:8123/jardim-phaser/?qa=4.1',{waitUntil:'domcontentloaded'});
  const visibility=await landscape.evaluate(()=>{
   const e=document.querySelector('#portraitLock');
   const s=getComputedStyle(e);
   return {display:s.display,position:s.position,z:s.zIndex,
    message:e.textContent,rect:e.getBoundingClientRect().height};
  });
  assert(visibility.display==='flex'&&visibility.position==='fixed'&&
   Number(visibility.z)>100&&visibility.rect>=380&&
   visibility.message.includes('vertical'),
   'horizontal não pode deixar jogar: mostrar aviso para rodar');
  console.log('PASS bloqueio horizontal: '+JSON.stringify(visibility));
 }finally{await landscape.close()}
}finally{await browser.close()}
