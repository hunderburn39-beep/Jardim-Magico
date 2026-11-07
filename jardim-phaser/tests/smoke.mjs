// Jardim Mágico Phaser V1 — testes Node sem navegador (créditos fictícios).
// node jardim-phaser/tests/smoke.mjs
import {readFile} from 'node:fs/promises';
import assert from 'node:assert/strict';
const read=p=>readFile(new URL(p,import.meta.url),'utf8');
const source=await read('../js/engine.js');
const main=await read('../js/app.js');
const reels=await read('../js/reels.js');
const dbsource=await read('../js/database.js');
const audio=await read('../js/audio.js');
const html=await read('../index.html');
const css=await read('../css/estilo.css');
let checked=0,spins=0,free=0,hold=0,stages=0;
const ok=(v,label)=>{assert.ok(v,label);checked++};
function parseModule(s){
 const c=s.replace(/^import\s+.*?;\s*$/gm,'').replace(/\bexport\s+(?=(?:const|function|class)\b)/g,'');
 new Function(c);
 return c;
}
for(const [n,s] of [['engine',source],['app',main],['reels',reels],['database',dbsource],['audio',audio]]){
 parseModule(s);ok(true,'sintaxe '+n);
}
const E=new Function(parseModule(source)+'\nreturn {DemoBank,winningCells,collapseBoard};')();
const {DemoBank,winningCells,collapseBoard}=E;
for(const stake of [.05,.2,1,5,10])for(const reserve of [0,.01,.2,2,10,100,2300]){
 const bank=new DemoBank();bank.balance=10000;bank.reserve=reserve;bank.stake=stake;
 for(let i=0;i<260;i++){
  const v=bank.spin();
  ok(v.ok,'giro existente');spins++;
  ok(v.stages.at(-1).ev.lines===0,'cadeia termina sem prémios');
  ok(bank.reserve>=-.00001,'reserva sem negativos');
  if(!v.free)ok(Math.abs(v.potential-v.credited)<.001,'pagamento integral');
  for(const step of v.stages)stages++;
  if(bank.mode==='free')free++;
  if(bank.mode==='hold'){
   hold++;let turns=0;
   const original=bank.reserve;
   while(bank.mode==='hold'&&turns++<50){
    const h=bank.holdTurn();
    if(h.done)ok(h.prize<=original+.001,'Hold solvente');
   }
   ok(bank.mode==='base','Hold conclui');
  }
 }
}
const exact=Array.from({length:5},(_,c)=>Array.from({length:3},(_,r)=>(c+r*2)%12));
const removed=[{c:0,r:1},{c:0,r:2},{c:2,r:0},{c:3,r:2}];
const after=collapseBoard(exact,removed,()=>.5);
ok(after.length===5&&after.every(c=>c.length===3),'cascata 5×3');
ok(winningCells({lineWins:[{cells:[{c:1,r:2},{c:2,r:1}]},{cells:[{c:1,r:2}]}]}).length===2,
 'peça partilhada contabilizada uma só vez no estouro');
class FakeSprite{
 constructor(x,y){this.x=x;this.y=y;this.alpha=1;this.scaleX=1;this.scaleY=1;}
 setTexture(x){this.texture=x;return this}
 setDisplaySize(w,h){this.width=w;this.height=h;this.scaleX=1;this.scaleY=1;return this}
 setRotation(v){this.rotation=v;return this}
 setFlip(x,y){this.flipX=x;this.flipY=y;return this}
 setAlpha(v){this.alpha=v;return this}
 setScale(x,y=x){this.scaleX=x;this.scaleY=y;return this}
 setY(y){this.y=y;return this}
 setAngle(a){this.angle=a;return this}
}
globalThis.Phaser={Scene:class{},Math:{Clamp:(x,a,b)=>Math.max(a,Math.min(b,x))}};
const factory=new Function('Phaser','SYMBOLS','newBoard',parseModule(reels)+'\nreturn ReelScene;');
const Scene=factory(globalThis.Phaser,source.match(/SYMBOLS=\[.*?\]/)?.[0],()=>exact);
// More predictable: parse exports and obtain original symbol labels.
const SYMBOLS=['potion','green','heart','teapot','wild','gem','mushroom','hat','rose','scatter','stone','coin'];
const Reel=factory(globalThis.Phaser,SYMBOLS,()=>exact);
const scene=new Reel();
scene.ready=true;
scene.busy=false;
scene.textures={exists:()=>true};
// Simular vários frames até à paragem para verificar que o resultado
// aparece DURANTE a última descida, nunca trocado pelo onComplete.
let preStopColumns=0;
let swappedOnStop=0;
let burstSpriteTweens=0;
scene.tweens={
 add(cfg){
  if(cfg.targets instanceof FakeSprite&&typeof cfg.alpha==='number')
   burstSpriteTweens++;
  if('distance'in cfg){
   const frames=cfg.distance*8;
   for(let frame=0;frame<=frames;frame++){
    cfg.targets.distance=cfg.distance*frame/frames;
    cfg.onUpdate?.();
   }
   const currentColumn=preStopColumns++;
   const beforeStop=scene.columns[currentColumn].items
    .filter(item=>item.slot>=0&&item.slot<3)
    .sort((a,b)=>a.slot-b.slot)
    .map(item=>item.symbol);
   const expected=after[currentColumn].map(id=>SYMBOLS[id]);
   assert.deepEqual(beforeStop,expected,
    'O resultado deve entrar pelo topo ANTES da paragem');
   cfg.onComplete?.();
   const afterStop=scene.columns[currentColumn].items
    .filter(item=>item.slot>=0&&item.slot<3)
    .sort((a,b)=>a.slot-b.slot)
    .map(item=>item.symbol);
   if(JSON.stringify(beforeStop)!==JSON.stringify(afterStop))swappedOnStop++;
  }else{
   if('y'in cfg)cfg.targets.y=cfg.y;
   cfg.onUpdate?.();cfg.onComplete?.();
  }
 },
 killAll(){}
};
scene.add={circle:(x,y,r)=>({
 x,y,r,scale:1,alpha:1,setDepth(){return this},destroy(){this.dead=true}
})};
scene.columns=Array.from({length:5},(_,c)=>{
 const items=[-1,0,1,2,3].map(slot=>({
  slot,wrapped:0,sprite:new FakeSprite((c+.5)*180,(slot+.5)*180),symbol:'potion'
 }));
 return {items,visible:items.filter(item=>item.slot>=0&&item.slot<=2)};
});
scene.board=exact;
scene.setBoard(exact);
await scene.spin(after,{turbo:true});
ok(preStopColumns===5,'As cinco colunas terminaram com imagens previamente posicionadas');
ok(swappedOnStop===0,'Nenhuma coluna altera imagens no instante da paragem');
for(let c=0;c<5;c++)for(let r=0;r<3;r++){
 ok(scene.columns[c].visible[r].symbol===SYMBOLS[after[c][r]],'resultado do giro '+c+','+r);
 ok(scene.columns[c].visible[r].sprite.y===(r+.5)*180,'tambores alinhados '+c+','+r);
 ok(!scene.columns[c].visible[r].sprite.flipY,'símbolo não invertido '+c+','+r);
}
// Duplicados de linhas vencedoras: apenas um efeito por posição.
const beforeBurst=burstSpriteTweens;
await scene.burst([
 {c:1,r:1},{c:1,r:1},{c:3,r:2}
],{destroy:false,duration:430});
ok(burstSpriteTweens-beforeBurst===2,
 'apenas duas peças brilhantes apesar de haver uma posição repetida');
ok(scene.getVisible(1,1).alpha===1,
 'uma vitória sem nova cascata não esconde a peça');
const beforeCascadeBurst=burstSpriteTweens;
const next=collapseBoard(after,removed,()=>.3);
await scene.burst(removed,{destroy:true});
ok(burstSpriteTweens-beforeCascadeBurst===removed.length,
 'todas as figuras removidas explode uma só vez');
await scene.cascade(next,removed,{turbo:true});
for(let c=0;c<5;c++)for(let r=0;r<3;r++){
 ok(scene.columns[c].visible[r].symbol===SYMBOLS[next[c][r]],'cascata '+c+','+r);
}
scene.recover(exact);
ok(!scene.busy,'recuperação do bloqueio');
for(let c=0;c<5;c++)for(let r=0;r<3;r++)
 ok(scene.columns[c].visible[r].sprite.y===(r+.5)*180,'grelha restaurada');
const store=new Map();
globalThis.localStorage={
 getItem:k=>store.get(k)||null,setItem:(k,v)=>store.set(k,v),removeItem:k=>store.delete(k)
};
const DB=new Function(parseModule(dbsource)+'\nreturn DemoDatabase;')();
const db=new DB();
await db.connect();
await db.save({balance:12,reserve:40,spins:3},next,{type:'spin',paid:.5});
ok((await db.load()).snapshot.balance===12,'saldo persistido');
ok((await db.history()).length===1,'histórico persistido');
const backup=await db.exportData();
ok(backup.state.snapshot.reserve===40,'exportação de fundo');
await db.clear();
ok((await db.load())===null,'limpeza de demo');
ok(main.includes('finally{')&&main.includes('running=false;'),
 'giro desbloqueia após exceção');
ok(reels.includes('this.tweens.add('),'Phaser usa tweens nativos');
ok(reels.includes('Phaser.Scale.FIT'),'canvas responsivo em dispositivos móveis');
ok(html.includes('id="dropAmount"'),'valor dourado de cada queda presente no HTML');
ok(css.includes('.drop-amount.active')&&css.includes('@keyframes dropValuePop'),
 'valor da queda aumenta e desaparece suavemente');
ok(main.includes('showDropAmount(lineTotal);'),
 'cada queda premiada apresenta o seu valor');
ok(main.includes('destroy:!!next,')&&!main.includes('destroy:!!next||multiple'),
 'sem novas quedas os prémios não desaparecem');
ok(main.includes('const fac=outcome.free?1:'),
 'Free Spins preserva o valor base até ao fim da série');
ok(main.includes('outcome.free&&outcome.credited>0'),
 'Free Spins anuncia prémio final creditado');
ok(reels.includes('Math.floor(48/targets.length)'),
 'limitador de partículas protege fluidez em mobile');
ok(reels.includes("function enteringSymbol("),'motor tem injeção antecipada da figura');
ok(!reels.slice(reels.indexOf('onComplete:()=>{',reels.indexOf(' spin(target,')),reels.indexOf(' burst(cells,')).includes('this.useSymbol(item,target'),
 'paragem não altera qualquer textura');

console.log('Phaser V1.1: '+checked+' testes passaram; giros='+spins+', free='+free+', Hold='+hold+', etapas='+stages);
