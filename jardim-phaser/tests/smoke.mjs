// Jardim Mágico Phaser V1 — testes Node sem navegador (créditos fictícios).
// node jardim-phaser/tests/smoke.mjs
import {readFile} from 'node:fs/promises';
import assert from 'node:assert/strict';
import {webcrypto} from 'node:crypto';
const read=p=>readFile(new URL(p,import.meta.url),'utf8');
const source=await read('../js/engine.js');
const main=await read('../js/app.js');
const reels=await read('../js/reels.js');
const dbsource=await read('../js/database.js');
const audio=await read('../js/audio.js');
const html=await read('../index.html');
const css=await read('../css/estilo.css');
const polish=await read('../css/polimento-v14.css');
const pigArt=await read('../assets/porquinho-dourado.svg');
const bonusScript=await read('../js/bonus-presentation.js');
const bonusCss=await read('../css/bonus-v15.css');
const prizeCss=await read('../css/premios-v16.css');
const mobileCss=await read('../css/mobile-v17.css');
const perfSource=await read('../js/performance-v17.js');
const adminSource=await read('../js/admin-v18.js');
const adminCss=await read('../css/admin-v18.css');
const historySource=await read('../js/history-v19.js');
const integrity32=await read('../js/history-integrity-v32.js');
const historyCss=await read('../css/history-v19.css');
const profileCss=await read('../css/perfis-v20.css');
const pinSource=await read('../js/admin-pin-v21.js');
const gatewaySource=await read('../js/storage-gateway-v21.js');
const recoverySource=await read('../js/recovery-v23.js');
const recoveryCss=await read('../css/recovery-v23.css');
const sessionSource=await read('../js/session-v24.js');
const sessionCss=await read('../css/session-v24.css');
const barV25Css=await read('../css/barra-v25.css');
const bonusModel26=await read('../js/bonus-progress-v26.js');
const bonusStyle26=await read('../css/bonus-v26.css');
const winUI27=await read('../js/win-ui-v27.js');
const winStyle27=await read('../css/celebracoes-v27.css');
const responsive28=await read('../css/responsive-v28.css');
const perfCSS29=await read('../css/performance-v29.css');
const botanical34Css=await read('../css/bosque-v34.css');
const scene35Css=await read('../css/cenario-v35.css');
const vertical36Css=await read('../css/vertical-v36.css');
const polish37Css=await read('../css/polimento-v37.css');
const icon38Css=await read('../css/icones-v38.css');
const material39Css=await read('../css/materiais-v39.css');
const material39Code=await read('../js/material-v39.js');
const timing391Code=await read('../js/spin-motion-v391.js');
const svg38=await Promise.all(['caracol','joaninha','flor-vermelha','flor-azul','pedra','wild'].map(name=>read('../../assets/imagens/jm38-'+name+'.svg')));
const art37Code=await read('../js/art-presentation-v37.js');
const supervisorSource=await read('../js/visual-supervisor-v30.js');
const turnQueue31Source=await read('../js/turn-queue-v31.js');
const pinCss=await read('../css/admin-pin-v21.css');
let checked=0,spins=0,free=0,hold=0,stages=0;
const ok=(v,label)=>{assert.ok(v,label);checked++};
function parseModule(s){
 const c=s.replace(/^import\s+.*?;\s*$/gm,'').replace(/\bexport\s+(?=(?:const|function|class)\b)/g,'');
 new Function(c);
 return c;
}
for(const [n,s] of [['engine',source],['app',main],['reels',reels],['database',dbsource],['audio',audio],['bonus',bonusScript]]){
 parseModule(s);ok(true,'sintaxe '+n);
}
const E=new Function(parseModule(source)+'\nreturn {DemoBank,winningCells,collapseBoard};')();
const {DemoBank,winningCells,collapseBoard}=E;
const bonus=new Function(parseModule(bonusScript)+
 '\nreturn {holdLandingIndices,holdDisplay,freeDisplay};')();
const beforeCoins=[0,2,0,0,3,0,0,0,0,0,1,0,0,0,0,0];
const afterCoins=[0,2,0,1,3,0,0,0,0,0,1,0,2,0,0,0];
ok(JSON.stringify(bonus.holdLandingIndices(beforeCoins,afterCoins))===
 JSON.stringify([3,12]),'Somente as duas moedas novas recebem animação');
ok(bonus.holdLandingIndices(afterCoins,afterCoins).length===0,
 'Nenhuma moeda antiga pode aterrar novamente');
ok(bonus.holdLandingIndices(null,afterCoins).length===5,
 'As moedas de abertura podem aparecer com animação');
const hDisplay=bonus.holdDisplay({grid:afterCoins,tries:2,total:3.6,rounds:4});
ok(hDisplay.coins===5&&hDisplay.attempts===2&&hDisplay.total===3.6,
 'Moedas e total espelham o estado do Hold');
ok(bonus.holdDisplay({grid:afterCoins,tries:7,total:3.6}).attempts===3,
 'Tentativas nunca excedem três');
const fDisplay=bonus.freeDisplay({
 freeSpins:12,freeTotal:4.25,freeMultiplier:4
});
ok(fDisplay.remaining===12&&fDisplay.total===4.25&&fDisplay.multiplier===4,
 'Free Spins mostra o acumulado e multiplicador do motor');
ok(bonus.freeDisplay({freeSpins:0,freeTotal:0,freeMultiplier:1001}).multiplier===1000,
 'Multiplicador visual suporta as somas de WILD até ao limite de segurança');

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
const testFxBudget=new Function(parseModule(perfSource)+'\nreturn burstParticleBudget;')();
const artMock37=new Function(parseModule(art37Code)+
 '\nreturn {symbolDisplay,wildBadgePlacement};')();
const Spin391=new Function(parseModule(timing391Code)+'\nreturn spinMotionProfile;')();
const factory=new Function('Phaser','SYMBOLS','newBoard','burstParticleBudget',
 'symbolDisplay','wildBadgePlacement','bakeMaterial','spinMotionProfile',
 parseModule(reels)+'\nreturn ReelScene;');
const Scene=factory(globalThis.Phaser,source.match(/SYMBOLS=\[.*?\]/)?.[0],
 ()=>exact,testFxBudget,artMock37.symbolDisplay,artMock37.wildBadgePlacement,
 ()=>null,Spin391);
// More predictable: parse exports and obtain original symbol labels.
const SYMBOLS=['potion','green','heart','teapot','wild','gem','mushroom','hat','rose','scatter','stone','coin'];
const Reel=factory(globalThis.Phaser,SYMBOLS,()=>exact,testFxBudget,
 artMock37.symbolDisplay,artMock37.wildBadgePlacement,()=>null,Spin391);
// V1.3: a folha artística tem os nove ícones na ordem original, sem
// espelhamento e sem alterar as regras dos prémios ou a queda aprovada.
const scene=new Reel();
const atlasTile=new Function('SYMBOLS',parseModule(reels)+'\nreturn atlasTileFor;')(SYMBOLS);
for(let i=0;i<9;i++){
 const tile=atlasTile(SYMBOLS[i]);
 ok(tile.col===i%3&&tile.row===Math.floor(i/3),
  'símbolo HD na posição certa da folha '+SYMBOLS[i]);
}
ok(atlasTile('scatter')===null&&atlasTile('stone')===null&&atlasTile('coin')===null,
 'símbolos especiais mantêm arte própria');
const drawCalls=[];
const originalDocument=globalThis.document;
globalThis.document={
 createElement:()=>({
  width:0,height:0,getContext:()=>({
   drawImage:(...args)=>drawCalls.push(args),
   createRadialGradient:()=>({addColorStop(){}}),
   fillRect(){},globalCompositeOperation:'source-over'
  })
 })
};
try{
 const fakeAtlas={width:375,height:375};
 for(let i=0;i<9;i++){
  const symbol=SYMBOLS[i];
  const tex=scene.cutPremiumAtlas(fakeAtlas,symbol);
  ok(tex.width===512&&tex.height===512,'textura 512px '+symbol);
 }
}finally{
 if(originalDocument===undefined)delete globalThis.document;
 else globalThis.document=originalDocument;
}
ok(drawCalls.length===9,'nove recortes da mesma arte original');
for(let i=0;i<9;i++){
 const d=drawCalls[i];
 const tile=atlasTile(SYMBOLS[i]);
 ok(Math.abs(d[1]-(tile.col*125+1.2))<.001&&
    Math.abs(d[2]-(tile.row*125+1.2))<.001,
    'recorte de textura sem troca de símbolos '+SYMBOLS[i]);
}
ok(reels.includes("this.load.image('symbols-atlas','jm75-simbolos.webp')"),
 'o motor carrega a folha fotográfica premium');
ok(reels.includes("if(!canvas)canvas=createFallbackCanvas"),
 'símbolos permanecem visíveis quando a arte não carrega');
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
const strandedFx={active:true,disposed:false,destroy(){
 this.disposed=true;this.active=false
}};
scene.trackFx(strandedFx);
scene.recover(exact);
ok(strandedFx.disposed&&scene.fxObjects.size===0,
 'recuperação do Safari liberta efeitos transitórios esquecidos');
for(let c=0;c<5;c++)for(let r=0;r<3;r++)
 ok(scene.columns[c].visible[r].sprite.y===(r+.5)*180,'grelha restaurada');
const store=new Map();
globalThis.localStorage={
 getItem:k=>store.get(k)||null,setItem:(k,v)=>store.set(k,v),removeItem:k=>store.delete(k)
};
const DB=new Function(parseModule(dbsource)+'\nreturn DemoDatabase;')();
const db=new DB();
await db.connect();
const refused=await db.save({balance:12,reserve:40,spins:3},next,{type:'spin',paid:.5});
ok(refused===false,'modo sem IndexedDB não permite escrever o mesmo saldo em várias abas');
ok((await db.load())===null,'modo sem base fiável mantém sessão vazia');
ok((await db.history()).length===0,'modo só leitura não inventa histórico');
const backup=await db.exportData();
ok(backup.state===null,'exportação local sem gravação não inventa saldo');
ok((await db.clear())===false,'sem lease não é permitido apagar a sessão');
ok(main.includes('finally{')&&main.includes('running=false;'),
 'giro desbloqueia após exceção');
ok(reels.includes('this.tweens.add('),'Phaser usa tweens nativos');
ok(reels.includes('Phaser.Scale.FIT'),'canvas responsivo em dispositivos móveis');
ok(html.includes('id="dropAmount"'),'valor dourado de cada queda presente no HTML');
ok(html.includes('premios-v16.css?v=1.6'),
 'novo CSS de prémios carregado na versão 1.6');
const multiSection=html.slice(html.indexOf('<section class="multiplier"'),
 html.indexOf('<section class="frame-region"'));
ok(multiSection.indexOf('id="dropAmount"')>=0&&
 multiSection.indexOf('id="dropAmount"')<multiSection.indexOf('id="multiTotal"')&&
 !multiSection.includes('id="orbs"')&&
 !multiSection.includes('MULTIS ACUMULADOS')&&
 !multiSection.includes('class="multi-stack"'),
 'barra: queda à esquerda, multiplicador único à direita, sem informação duplicada');
ok(!html.includes('class="drop-amount"'),
 'valor deixou de estar sobreposto ao centro dos tambores');
const bigSection=html.slice(html.indexOf('id="bigWin"'),
 html.indexOf('</section>',html.indexOf('id="bigWin"')));
ok(bigSection.indexOf('id="bigWinTitle"')<
   bigSection.indexOf('class="celebration-total"')&&
   bigSection.indexOf('class="celebration-total"')<
   bigSection.indexOf('id="bigWinValue"'),
 'celebração apresenta frase, TOTAL e valor em três linhas');
ok(prizeCss.includes('.big-win{')&&prizeCss.includes('top:23%'),
 'a frase de celebração subiu em relação à posição antiga');
ok(prizeCss.includes('.drop-line-value')&&
  prizeCss.includes('.celebration-value')&&
  barV25Css.includes('grid-template-columns:minmax(0,1fr) minmax(70px,auto)')&&
  barV25Css.includes('justify-self:end'),
 'barra em duas colunas e celebração mantêm tipografia premium responsiva');
ok((prizeCss.match(/{/g)||[]).length===(prizeCss.match(/}/g)||[]).length,
 'CSS prémios V1.6 com delimitadores equilibrados');
ok(main.includes("$('dropLabel').textContent='QUEDA '+(i+1)")&&
   main.includes("$('dropLabel').textContent='TOTAL DO GIRO'"),
 'à esquerda aparece cada queda e depois o total real do giro');
ok(main.includes('function celebrationTier(')&&
   main.includes("return 'SENSACIONAL'")&&
   main.includes("return 'MEGA'")&&
   main.includes("return 'NICE'"),
 'celebração escolhe título pelo prémio sem alterar a matemática');
ok(main.includes("celebrate('HOLD & WIN',result.prize)")&&
   main.includes("celebrate('FREE SPINS',outcome.credited)"),
 'bónus mostram frase e prémio separado em três linhas');
ok(!main.includes('if(!freeMultiAdded)')&&
   main.includes('const wildAwards=stage.ev.wildAwards||[];')&&
   main.includes('stage.ev.multiplier'),
 'WILD de cada queda soma ao multi acumulado e não é ignorado');
ok(main.includes('renderMultiplier(outcome.free?freeOrbHistory:earnedMultis,')&&
 main.includes("output.textContent='×'+next;")&&
 main.includes("output.dataset.value=String(next);")&&
 !main.includes("$('orbs')"),
 'MULTI efetivo apresenta o valor acumulado só no indicador direito');
// Phaser V1.4: melhorias apenas visuais, sem interferir com jogadas.
// Testar recursos de arte e camadas sem bloqueio de toques no iPhone.
ok(html.includes('polimento-v14.css?v=1.4'),
 'novo acabamento visual importado por último');
ok(html.includes('assets/porquinho-dourado.svg'),
 'cofre com porquinho dourado ilustrado, não emoji');
ok(pigArt.startsWith('<svg')&&pigArt.includes('</svg>')&&
   pigArt.includes('id="gold"')&&pigArt.includes('id="round"'),
 'cofre SVG com gradientes dourados e contornos');
ok((html.match(/--x:/g)||[]).length===8,
 'oito pontos de luz discretos no jardim');
ok(polish.includes('pointer-events:none')&&polish.includes('.magic-dust'),
 'luzes do cenário não impedem tocar nos controlos');
ok(polish.includes('jm75-lago.webp')&&polish.includes('.garden-flora'),
 'vegetação fotográfica original só nas margens');
ok(polish.includes('prefers-reduced-motion:reduce'),
 'acessibilidade: desativar movimentos decorativos');
ok((polish.match(/{/g)||[]).length===(polish.match(/}/g)||[]).length,
 'CSS V1.4 com delimitadores equilibrados');
ok(!html.includes('<span class="piggy-art">🐷</span>'),
 'emoji antigo do cofre removido');

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
ok(reels.includes('burstParticleBudget(targets.length,this.liteEffects)')&&
 perfSource.includes('Math.floor(48/count)'),
 'limitador adaptativo de partículas protege fluidez em mobile');
ok(html.includes('id="freeHud"')&&html.includes('id="holdResult"'),
 'Free Spins e Hold têm painéis próprios');
ok(html.includes('id="holdAttempts"')&&html.includes('id="holdCoins"')&&html.includes('id="holdTotal"'),
 'Hold apresenta tentativas, moedas e acumulado');
ok(bonusCss.includes('.hold-cell.new-coin')&&bonusCss.includes('@keyframes coinLand'),
 'Moedas novas aterraram com animação, sem afetar o motor');
ok(bonusCss.includes('prefers-reduced-motion:reduce'),
 'Animações de bónus respeitam acessibilidade');
ok(main.includes("await persist(result.done?'hold-paid':'hold-turn'"),
 'Prémio do Hold é guardado antes da celebração');
ok(main.includes('renderHold(previous,result.hold);')&&
   main.includes('if(!writerReady||mutating||bank.mode!==\'hold\'||holdBusy)return;'),
 'Hold usa grelha calculada e bloqueia duplo clique durante pagamento');
ok(main.includes("bonusAnnouncement('✨ FREE SPINS CONCLUÍDAS ✨'"),
 'Free Spins anuncia prémio final integral');

ok(css.includes('jm75-rodar.webp')&&css.includes('jm75-jackpot-painel.webp'),
 'botão e jackpots usam a arte ornamental original');
ok(css.includes('jm75-hud-painel.webp')&&css.includes('jm75-multi-painel.webp'),
 'quadros e multiplicadores usam painéis pintados originais');
ok(reels.includes("function enteringSymbol("),'motor tem injeção antecipada da figura');
ok(!reels.slice(reels.indexOf('onComplete:()=>{',reels.indexOf(' spin(target,')),reels.indexOf(' burst(cells,')).includes('this.useSymbol(item,target'),
 'paragem não altera qualquer textura');

const perf=new Function(parseModule(perfSource)+
 '\nreturn {slowFrameRatio,createPerformanceGuard,burstParticleBudget};')();
ok(perf.slowFrameRatio(Array(60).fill(16.67))===0,
 'desempenho normal não provoca corte de efeitos');
ok(perf.slowFrameRatio(Array(60).fill(38))===1,
 'desempenho lento detetado sem interferir com o motor');
const classes=new Set();
const surface={classList:{
 add:c=>classes.add(c),remove:c=>classes.delete(c)
}};
let frameTick=null,cancelled=0;
const clock={
 requestAnimationFrame:fn=>{frameTick=fn;return 1},
 cancelAnimationFrame:()=>{cancelled++}
};
const guard=perf.createPerformanceGuard(surface,clock);
guard.start();
ok(classes.has('is-spinning'),'modo de giro desativa decorativos pesados');
let now=0;
for(let i=0;i<72;i++){now+=38;frameTick(now)}
ok(classes.has('lite-effects'),'modo leve ativado após frames lentos');
guard.stop();
ok(!classes.has('is-spinning')&&cancelled===1,
 'modo de giro limpa animação mesmo após erro');
ok(mobileCss.includes('max-height:570px')&&mobileCss.includes('min-height:0'),
 'ecrãs pequenos mantêm os controlos visíveis');
ok(mobileCss.includes('animation-play-state:paused')&&
 mobileCss.includes('lite-effects')&&mobileCss.includes('prefers-reduced-motion'),
 'efeitos adaptativos respeitam movimento reduzido');
ok(html.includes('mobile-v17.css?v=1.7')&&html.includes('app.js?v=4.0'),
 'nova versão carregada com cache isolada');
ok(main.includes('performanceGuard.start();')&&
 main.includes('performanceGuard.stop();')&&main.includes('document.hidden||'),
 'modo gráfico regressa ao normal e não inicia giros em segundo plano');
ok((mobileCss.match(/{/g)||[]).length===(mobileCss.match(/}/g)||[]).length,
 'CSS V1.7 com delimitadores equilibrados');
const Admin=new Function(parseModule(adminSource)+
 '\nreturn {readPercent,normalizeAdmin,validateFundChange,summarizeLedger,makePrintableReport};')();
ok(Admin.readPercent('20,5')===20.5&&Admin.readPercent(110)===null,
 'percentagem admin válida');
ok(Admin.normalizeAdmin(65).merchantPct===35,
 'percentagens somam 100');
ok(Admin.validateFundChange('deposit','100,50',25).target===125.5,
 'movimento de fundo simulado');
ok(!Admin.validateFundChange('withdraw',40,25).ok&&
 !Admin.validateFundChange('withdraw',10,25,'hold').ok,
 'não retirar fundo indisponível nem em bónus');
const stats=Admin.summarizeLedger([
 {type:'spin',wager:10,paid:2,ownerPct:60},
 {type:'buy-free',cost:4,ownerPct:25},
 {type:'hold-paid',paid:6,ownerPct:75},
 {type:'fund-deposit',amount:500},
 {type:'spin',wager:2,paid:0}
]);
ok(stats.net===8&&stats.owner===2.3&&stats.merchant===5.7,
 'resultados históricos preservam quotas por evento');
ok(stats.wagers===12&&stats.costs===4&&stats.paid===8,
 'receitas e prémios virtuais apurados');
ok(Admin.makePrintableReport({summary:stats,history:[],settings:Admin.normalizeAdmin(),
 reserve:100,dbMode:'indexedDB'}).includes('CRÉDITOS FICTÍCIOS'),
 'relatório imprimível deixa explícito ser demonstração');
ok(html.includes('admin-v18.css?v=1.8')&&html.includes('id="adminBtn"')&&
 html.includes('app.js?v=4.0'),'botão e versão Phaser V3.2');
ok(main.includes('admin-access')&&main.includes('fund-withdraw')&&
 main.includes('admin-share'),'histórico de ações admin');
ok(dbsource.includes('ownerPct:Number.isFinite')&&adminCss.includes('.admin-row'),
 'base guarda quota e painel é responsivo');
const H=new Function(parseModule(historySource)+
 '\nreturn {filterHistory,historyPage};')();
const rows=[
 {eventId:'1',type:'spin',timestamp:'2026-11-06T12:00:00Z',paid:1},
 {eventId:'2',type:'fund-deposit',timestamp:'2026-11-07T12:00:00Z',amount:20},
 {eventId:'3',type:'buy-hold',timestamp:'2026-11-07T13:00:00Z',cost:3,note:'Teste'}
];
ok(H.filterHistory(rows,{category:'fund'}).length===1,
 'histórico filtra entradas e saídas de fundo');
ok(H.filterHistory(rows,{query:'tesTe'}).length===1&&
 H.filterHistory(rows,{dateFrom:'2026-11-07'}).length===2,
 'histórico pesquisa e filtra datas');
ok(H.historyPage(rows,8,2).items.length===1&&H.historyPage(rows,8,2).page===1,
 'histórico pagina sem sair dos limites');
const Backup=new Function(parseModule(dbsource)+
 '\nreturn {validateBackup,DemoDatabase};')();
const originalBank=new DemoBank();
const originalState={snapshot:{...originalBank.snapshot,admin:{ownerPct:60,merchantPct:40}},
 board:exact};
const validBackup={format:'jardim-magico-demo-v3',state:originalState,history:rows};
ok(Backup.validateBackup(validBackup).history.length===3,
 'restauro aceita cópia de segurança válida');
ok(Backup.validateBackup(validBackup).state.snapshot.admin.ownerPct===60,
 'restauro preserva quotas anteriores');
ok((()=>{try{Backup.validateBackup({...validBackup,state:{...originalState,board:[[3]]}});return false}catch{return true}})(),
 'restauro rejeita tabuleiro inválido');
ok((()=>{try{Backup.validateBackup({...validBackup,history:[rows[0],rows[0]]});return false}catch{return true}})(),
 'restauro rejeita eventos duplicados');
const historicalDb=new Backup.DemoDatabase();
await historicalDb.connect();
// No fake IndexedDB in this Node smoke test: fallback must be read-only.
const fallbackStore=new Map();
const oldStorage=globalThis.localStorage;
globalThis.localStorage={
 getItem:k=>fallbackStore.get(k)||null,
 setItem:(k,v)=>fallbackStore.set(k,v),
 removeItem:k=>fallbackStore.delete(k)
};
try{
 const restored=await historicalDb.restoreData(validBackup);
 ok(!restored.ok,'sem IndexedDB restauro local é bloqueado para evitar conflitos');
 ok((await historicalDb.history()).length===0,'modo sem IndexedDB não grava eventos');
 const dump=await historicalDb.exportData();
 ok(dump.state===null,'exportação não fabrica sessão inexistente');
 const declined=await historicalDb.restoreData({...validBackup,state:{snapshot:{},board:[]}});
 ok(!declined.ok&&(await historicalDb.load())===null,
  'cópia inválida não destrói o estado local');
}finally{
 if(oldStorage===undefined)delete globalThis.localStorage;
 else globalThis.localStorage=oldStorage;
}
ok(historyCss.includes('.history-list')&&html.includes('history-v19.css?v=1.9'),
 'estilos da pesquisa do histórico carregados');
ok(main.includes('validateBackup(backup)')&&main.includes('historyPage(found,page)'),
 'interface valida cópias e pagina histórico');
const Modes=new Function(parseModule(source)+
 '\nreturn {DEMO_PROFILES,openingByProfile,DemoBank,evaluate};')();
let rates={};
for(const [name,seed] of [['premios',42],['normal',42],['entretenimento',42]]){
 let x=seed,hits=0;
 const rand=()=>{x=(Math.imul(x,1664525)+1013904223)>>>0;return x/4294967296};
 for(let j=0;j<2000;j++){
  const b=Modes.openingByProfile(rand,false,name,.2);
  const ev=Modes.evaluate(b,.2);
  if(ev.lines>0||ev.scatter>=3||ev.coins>=6)hits++;
 }
 rates[name]=hits/2000;
}
ok(rates.premios>rates.normal+.055&&rates.normal>rates.entretenimento+.035,
 'os três perfis alteram a frequência de eventos iniciais de forma ordenada');
const checkProfile=new Modes.DemoBank();
ok(checkProfile.profile==='normal'&&checkProfile.setProfile('premios')&&
 checkProfile.snapshot.profile==='premios'&&!checkProfile.setProfile('invalid'),
 'perfil normal por defeito e alterações validadas');
checkProfile.addFree(15);
ok(!checkProfile.setProfile('entretenimento'),
 'não é possível alterar perfil durante Free Spins');
const fixedBoard=Array.from({length:5},()=>[0,1,2]);
ok(JSON.stringify(Modes.openingByProfile(()=>.1,true,'premios',.2))===
 JSON.stringify(Modes.openingByProfile(()=>.1,true,'normal',.2)),
 'em Free Spins o modo não altera a distribuição das peças');
ok(main.includes("if(!saved){\n  copySnapshot(before);board=previousBoard;")&&
 main.includes("copySnapshot(before);renderHold(null,bank.hold)"),
 'falha na gravação cancela giro ou Hold antes de animar');
ok(main.includes("persist('admin-profile'")&&main.includes("bank.setProfile(desired)"),
 'alterar o modo só depois de escolher um perfil válido');
ok(html.includes('id="profileStatus"')&&html.includes('perfis-v20.css?v=2.0')&&
 profileCss.includes('.admin-profile'),
 'modo visível ao jogador e interface responsiva');
ok(dbsource.includes('profile:[\'premios\',\'normal\',\'entretenimento\']')&&
 dbsource.includes('O perfil guardado não é válido'),
 'histórico e cópia de segurança persistem/validam o modo');
const LocalPin=new Function(parseModule(pinSource)+
 '\nreturn {createAdminPinLock,PIN_KEY};')();
const pinStorageMap=new Map();
const pinStorage={getItem:k=>pinStorageMap.get(k)??null,
 setItem:(k,v)=>pinStorageMap.set(k,String(v))};
let clockTime=100000;
const access=LocalPin.createAdminPinLock({
 storage:pinStorage,cryptoProvider:webcrypto,now:()=>clockTime
});
ok(!access.isConfigured()&&!access.unlocked(),'PIN não existe por defeito');
ok(!(await access.create('123')).ok,'PIN exige seis números');
ok((await access.create('147258')).ok&&access.unlocked(),
 'primeiro acesso cria PIN sem código predefinido');
ok(!pinStorageMap.get(LocalPin.PIN_KEY).includes('147258'),
 'PIN não fica gravado em claro');
access.lock();
ok(!access.unlocked()&&(await access.verify('147258')).ok,
 'bloqueio manual e verificação de PIN');
clockTime+=480001;
ok(!access.unlocked(),'sessão de administração expira após oito minutos');
ok((await access.change('147258','258369')).ok,
 'alterar PIN com verificação do anterior');
access.lock();
ok(!(await access.verify('147258')).ok&&(await access.verify('258369')).ok,
 'PIN antigo rejeitado depois da alteração');
const errorsLock=LocalPin.createAdminPinLock({
 storage:{getItem:()=>null,setItem:()=>{}},
 cryptoProvider:{},now:()=>clockTime});
ok((()=>{try{errorsLock.isConfigured();return false}catch{return true}})(),
 'sem criptografia o painel falha fechado');
const Gateway=new Function(parseModule(gatewaySource)+
 '\nreturn {createStorageGateway,STORAGE_METHODS};')();
ok((()=>{try{Gateway.createStorageGateway({});return false}catch{return true}})(),
 'porta de armazenamento rejeita driver incompleto');
let calls=0;
const dummy={mode:'indexedDB'};
for(const method of Gateway.STORAGE_METHODS)
 dummy[method]=async()=>{calls++;return method};
const driver=Gateway.createStorageGateway(dummy);
ok(driver.provider==='local-demo'&&!driver.isCentralized&&
 driver.mode==='indexedDB'&&(await driver.history())==='history'&&calls===1,
 'a interface usa um driver local sem inventar servidor');
ok(main.includes('const demoDB=createStorageGateway(localDB)')&&
 main.includes('adminPinForm(')&&main.includes('requireAdminUnlocked()'),
 'PIN e gateway integrados em toda a interface');
ok(!main.includes("name:'＋ Adicionar 500 ao fundo DEMO'")&&
 main.includes("name:'⚙ Administrar fundo DEMO'"),
 'não é possível carregar o fundo diretamente pelo botão público');
ok(main.includes('if(document.hidden){')&&
 main.includes('adminLock.lock();')&&
 main.includes("id=\"adminLock\""),
 'bloqueio automático ao ocultar a app e botão manual');
ok(pinCss.includes('.admin-pin-form')&&html.includes('admin-pin-v21.css?v=2.1'),
 'interface do PIN adaptada ao telemóvel');
const CheckDB=new Function(parseModule(dbsource)+
 '\nreturn {leaseAvailable,validRecoveryMarker};')();
ok(CheckDB.leaseAvailable(null,'tab-a',1000)&&
 CheckDB.leaseAvailable({owner:'tab-a',expiresAt:2000},'tab-a',1000),
 'aba pode adquirir lease livre ou renovar o seu');
ok(!CheckDB.leaseAvailable({owner:'tab-b',expiresAt:2000},'tab-a',1000),
 'outra aba ativa impede segundo escritor');
ok(CheckDB.leaseAvailable({owner:'tab-b',expiresAt:999},'tab-a',1000),
 'lease expirada pode passar para outra aba');
ok(CheckDB.validRecoveryMarker({id:'spin-123456',date:'2026-11-07',paid:2.5})&&
 !CheckDB.validRecoveryMarker({id:'x',date:'2026',paid:-9}),
 'recibo de giro interrompido validado');
ok(dbsource.includes("state.get(WRITER_ID)")&&
 dbsource.includes("if(!this.writerToken||record?.owner!==this.writerToken"),
 'gravação verifica posse na mesma transação IndexedDB');
ok(dbsource.includes('async checkpoint(snapshot,board)')&&
 dbsource.includes('state.delete(STATE_ID)')&&
 !dbsource.includes("state.clear();"),
 'checkpoint sem novo prémio e reset mantém lease ativa');
ok(main.includes('writerReady=exclusive;')&&
 main.includes('if(!writerReady||!ready||!scene')===false&&
 main.includes('writerTimer=setInterval(refreshWriter,5000)'),
 'aquisição e renovação de writer integradas');
ok(main.includes('if(document.hidden||writerCheckBusy')&&
 main.includes('writerReleasePending=release;')&&
 main.includes('if(writerReleasePending)await writerReleasePending;')&&
 main.includes('releaseIfHidden();'),
 'a aba liberta a sessão só quando não há operações pendentes e aguarda a libertação ao regressar');
ok(main.includes('pendingVisual=spinRecoveryReceipt(')&&
 main.includes('pendingVisual=holdRecoveryReceipt(')&&
 main.includes('demoDB.checkpoint(snapshot(),board)'),
 'resultado fica preservado e marca visual é retirada após animação');
ok(main.includes('if(!writerReady||mutating||bank.mode!==\'hold\'||holdBusy)return;')&&
 main.includes("PHASER · SÓ LEITURA"),
 'sem IndexedDB não permite alterar saldo com multi-abas');
ok(gatewaySource.includes("'acquireLease'")&&
 gatewaySource.includes("'renewLease'")&&
 gatewaySource.includes("'releaseLease'"),
 'porta V2.1 encaminha operações novas de sessão exclusiva');
const Rec=new Function(parseModule(recoverySource)+
 '\nreturn {spinRecoveryReceipt,holdRecoveryReceipt,recoveryMessage,createDiagnostics};')();
const receipt=Rec.spinRecoveryReceipt({credited:12.4,free:true,
 triggered:'Fim das Free Spins'},{mode:'base',freeSpins:0,freeTotal:4,
 freeMultiplier:3},5);
ok(receipt.kind==='spin'&&receipt.wasFree&&receipt.paid===12.4&&
 receipt.stages===5&&receipt.modeAfter==='base',
 'recibo de Free Spins concluídas guarda o prémio já creditado');
ok(Rec.recoveryMessage(receipt,{mode:'base'}).kind==='free-complete',
 'sessão concluída mostra celebração recuperada sem novo pagamento');
const held={grid:Array(16).fill(0).map((_,i)=>i<8?1:0),tries:2,rounds:3,total:5};
const holdReceipt=Rec.holdRecoveryReceipt({done:true,prize:5,hold:held},{mode:'base'});
ok(holdReceipt.kind==='hold'&&holdReceipt.grid.length===16&&
 Rec.recoveryMessage(holdReceipt,{mode:'base'}).kind==='hold-complete',
 'Hold terminado guarda uma grelha visual para recuperar o resultado');
ok(Rec.recoveryMessage(null,{mode:'hold',hold:held}).kind==='hold-active'&&
 Rec.recoveryMessage(null,{mode:'free',freeSpins:9,freeTotal:5.2,
 freeMultiplier:2}).kind==='free-active',
 'bónus ativos reabrem sem repetir giros ou moedas');
const meter=Rec.createDiagnostics(10);
for(let i=0;i<22;i++)meter.record('save-failed');
meter.record('writer-granted');
ok(meter.snapshot().events.length===10&&meter.snapshot().counts['save-failed']===22,
 'diagnósticos limitam eventos mas mantêm contagens');
ok(!meter.record('pin-attempt')&&!JSON.stringify(meter.snapshot()).includes('pin-attempt'),
 'diagnóstico não aceita dados de PIN nem categorias arbitrárias');
ok(main.includes('pendingVisual=holdRecoveryReceipt(')&&
 main.includes('clearVisualReceipt()')&&main.includes('showRecoveryState(savedMarker)'),
 'Hold e Free Spins guardam recibos e retomam interface visual');
ok(main.includes("grid.className='recovered-hold-grid'")&&
 recoveryCss.includes('.recovered-hold-coin.with-coin'),
 'grelha do Hold terminado é apresentada com moedas já guardadas');
ok(main.includes("name:'🩺 Diagnóstico local'")&&
 main.includes('function diagnosticModal()')&&recoveryCss.includes('.diag-panel')&&
 html.includes('recovery-v23.css?v=2.3'),
 'painel de diagnósticos integrado e com estilos móveis');
const UI24=new Function(parseModule(sessionSource)+
 '\nreturn {sessionState,canResumeFree};')();
ok(UI24.sessionState({ready:true,writerReady:false}).kind==='blocked',
 'sem writer aviso de sessão bloqueada');
ok(UI24.sessionState({ready:true,writerReady:true,saveIssue:true}).kind==='error',
 'falha persistente apresenta estado de erro');
ok(UI24.sessionState({ready:true,writerReady:true,mutating:true}).kind==='saving',
 'feedback durante uma operação pendente');
ok(UI24.sessionState({ready:true,writerReady:true,paused:true,mode:'free'}).kind==='paused'&&
 UI24.sessionState({ready:true,writerReady:true}).kind==='ok',
 'Free Spins pausadas têm botão para retomar');
ok(UI24.canResumeFree({ready:true,writerReady:true,paused:true,mode:'free',hidden:false,running:false,mutating:false})&&
 !UI24.canResumeFree({ready:true,writerReady:true,paused:true,mode:'free',hidden:true,running:false,mutating:false}),
 'retomar apenas com aba visível');
ok(main.includes('freePaused=true;update();')&&
 main.includes('act:resumeFree')&&
 main.includes("if(bank.mode==='free'&&!running&&!freePaused"),
 'fechar modal de recuperação não inicia Free Spins');
ok(main.includes('async function withMutation(action)')&&
 main.includes("if(!(await persist('stake')))")&&
 main.includes('if(!document.hidden||!writerReady||running||mutating||holdBusy)return false;')&&
 main.includes('if(document.hidden)await releaseIfHidden();'),
 'aposta e libertação de aba respeitam gravações em curso');
ok(html.includes('id="sessionBanner"')&&html.includes('session-v24.css?v=2.4')&&
 sessionCss.includes('safe-area-inset-bottom')&&sessionCss.includes('pointer:coarse'),
 'aviso responsivo com toque e área segura no iPhone');
ok(html.includes('barra-v25.css?v=2.5')&&
 !html.includes('MULTIS ACUMULADOS')&&
 !html.includes('class="multi-stack"')&&
 !main.includes("$('orbs')")&&
 !main.includes('function orbs('),
 'nova barra elimina o bloco central sem deixar referências órfãs');
const Bonus26=new Function(parseModule(bonusModel26)+
 '\nreturn {freeProgress,holdProgress};')();
ok(Bonus26.freeProgress({mode:'free',freeSpins:11,freePlayed:4,freeTotal:12.5}).played===4&&
 Bonus26.freeProgress({mode:'free',freeSpins:11,freePlayed:4,freeTotal:12.5}).remaining===11,
 'Free Spins apresentam realizadas e restantes reais');
ok(Bonus26.freeProgress({mode:'free',freeSpins:-20,freePlayed:500}).remaining===0,
 'HUD Free Spins limita dados inválidos');
const progress26=Bonus26.holdProgress({grid:Array.from({length:16},(_,i)=>i<9?1:0),tries:2});
ok(progress26.filled===9&&progress26.left===7&&progress26.percentage===56.25&&
 progress26.attemptsText==='2 tentativas restantes',
 'Hold mostra corretamente casas preenchidas e tentativas restantes');
ok(Bonus26.holdProgress({grid:Array(16).fill(1),tries:1}).missingLabel==='GRELHA COMPLETA'&&
 Bonus26.holdProgress({grid:Array(16).fill(1),tries:1}).attemptsText==='1 tentativa restante',
 'Hold lida com grelha completa e singular das tentativas');
ok(html.includes('id="holdProgress"')&&html.includes('id="hudFreePlayed"')&&
 !html.includes('id="hudFreeMulti"')&&!html.includes('MULTIS ACUMULADOS'),
 'HUD elimina MULTI duplicado e inclui progresso do Hold');
ok(main.includes("holdProgress(result.hold).attemptsText")&&
 main.includes("setAttribute('aria-valuetext'")&&
 main.includes("freeProgress(s)")&&
 !main.includes("$('hudFreeMulti')"),
 'os indicadores são calculados a partir dos estados originais');
ok(bonusStyle26.includes('prefers-reduced-motion:reduce')&&
 bonusStyle26.includes('landscape')&&bonusStyle26.includes('hold-fill-track'),
 'progressos adaptados a telemóveis e movimento reduzido');
const Prize27=new Function(parseModule(winUI27)+
 '\nreturn {celebrationView,winBreakdown};')();
const nice27=Prize27.celebrationView('NICE',2.5,false);
const huge27=Prize27.celebrationView('SENSACIONAL',12345678.9,true);
ok(nice27.tier==='nice'&&nice27.duration===1750&&
 huge27.tier==='sensacional'&&huge27.duration===1150&&
 huge27.width==='dense',
 'celebrações adaptadas aos prémios e ao modo Turbo');
ok(Prize27.celebrationView('NICE',0)===null&&
 Prize27.celebrationView('MEGA',-1)===null&&
 Prize27.celebrationView('MEGA',Infinity)===null,
 'efeitos não aparecem para valores inválidos ou nulos');
const compact27=Prize27.winBreakdown(Array.from({length:8},(_,i)=>({amount:(i+1)/5})),false,2);
ok(compact27.count===8&&compact27.values.length===3&&compact27.other===5&&
 compact27.values[0].includes('0,40'),
 'resumo mantém valores reais sem encher o ecrã');
ok(Prize27.winBreakdown([{amount:3}],true,1).label.includes('TOTAL')&&
 !Prize27.winBreakdown([],true,1).hasWins,
 'resumo diferencia queda de total e oculta jogadas sem prémio');
ok(main.includes('function showWinText(')&&
 main.includes('winBreakdown(wins,total,factor)')&&
 main.includes('celebrationView(title,value,turbo)'),
 'apresentação de prémios usa funções visuais puras');
ok(!main.includes("if(multiple&&lineTotal>=bank.stake*8)")&&
 main.includes("celebrate('FREE SPINS',outcome.credited)")&&
 main.includes("celebrate('HOLD & WIN',result.prize)"),
 'celebrações de cascata redundantes removidas sem apagar os bónus');
ok(html.includes('celebracoes-v27.css?v=2.7')&&
 html.includes('data-tier="nice"')&&
 !html.includes('MULTIS ACUMULADOS')&&
 winStyle27.includes('prefers-reduced-motion:reduce')&&
 winStyle27.includes('orientation:landscape'),
 'visual premium preserva a barra limpa e o Safari pequeno');
ok(winStyle27.includes('.win-notice-values')&&
 winStyle27.includes('animation:celebrationV27')&&
 !winStyle27.includes('backdrop-filter'),
 'animações curtas e simples, sem filtros pesados por quadro');
ok(html.includes('responsive-v28.css?v=2.8')&&
 html.indexOf('responsive-v28.css?v=2.8')>html.indexOf('celebracoes-v27.css?v=2.7'),
 'novas regras de geometria são as últimas a carregar');
ok(html.includes('viewport-fit=cover')&&
 html.includes('maximum-scale=1, user-scalable=no')&&
 html.includes('vertical-v36.css?v=3.6'),
 'safe area iPhone e zoom por duplo toque desativado no jogo vertical');
ok(responsive28.includes('(orientation:portrait) and (max-height:710px)')&&
 responsive28.includes('min-height:0;max-height:none;height:100dvh')&&
 responsive28.includes('footer{height:auto;min-height:12px'),
 'ecrãs de retrato baixos não ficam presos a altura mínima de 600px');
ok(responsive28.includes('(orientation:portrait) and (max-height:500px)')&&
 responsive28.includes('overflow-y:auto'),
 'em ecrãs extremos controlos continuam acessíveis por scroll');
ok(responsive28.includes('(orientation:portrait) and (min-width:600px)')&&
 responsive28.includes('grid-template-rows:')&&
 responsive28.includes('width:100vw;max-width:100vw'),
 'tablets em retrato aproveitam a largura disponível');
ok(responsive28.includes('grid-template-columns:minmax(0,30%) minmax(0,35%) minmax(0,35%)')&&
 responsive28.includes('calc(80dvh - 8px)')&&
 responsive28.includes('.frame-region .reel-window{'),
 'paisagem reserva colunas próprias e limita o reel à altura');
const reelRules=responsive28.split('.frame-region .reel-window{').slice(1).map(x=>x.split('}')[0]);
ok(reelRules.length>=3&&reelRules.every(x=>x.includes('aspect-ratio:5/3'))&&
 !reelRules.some(x=>x.includes('aspect-ratio:auto')),
 'todos os ecrãs mantêm a proporção 5:3 sem deformar os símbolos');
ok(responsive28.includes('safe-area-inset-left')&&
 responsive28.includes('safe-area-inset-right')&&
 responsive28.includes('safe-area-inset-bottom'),
 'margens móveis respeitam notch e zona de gestos');
ok(!html.includes('MULTIS ACUMULADOS')&&
 !html.includes('class="multi-stack"')&&
 html.includes('id="dropAmount"')&&html.includes('id="multiTotal"'),
 'barra da queda à esquerda e MULTI à direita mantêm-se sem duplicação');
ok((responsive28.match(/{/g)||[]).length===(responsive28.match(/}/g)||[]).length,
 'CSS V2.8 tem blocos equilibrados');
const lowParticles=perf.burstParticleBudget(15,true);
ok(perf.burstParticleBudget(1,false)===6&&
 perf.burstParticleBudget(15,false)===3&&lowParticles===1,
 'modo leve reduz sprites cosméticos e conserva a versão rica');
ok(perf.burstParticleBudget(5,true)<=perf.burstParticleBudget(5,false)&&
 perf.burstParticleBudget(15,true)<=2,
 'partículas cosméticas limitadas até com quinze figuras');
const qualityChanges=[],slowClasses=new Set();
let perfNext=null,perfCancels=0,perfRequests=0;
const qualityRoot={classList:{
 add:v=>slowClasses.add(v),remove:v=>slowClasses.delete(v),
 contains:v=>slowClasses.has(v)
}};
const mockClock={
 requestAnimationFrame:fn=>{perfNext=fn;return ++perfRequests},
 cancelAnimationFrame:()=>{perfCancels++},
 matchMedia:()=>({matches:false})
};
const adapt=perf.createPerformanceGuard(qualityRoot,mockClock,low=>qualityChanges.push(low));
adapt.start();
let clockMs=0;
for(let i=0;i<72;i++){clockMs+=38;perfNext(clockMs)}
ok(adapt.isLite()&&qualityChanges.includes(true)&&
 slowClasses.has('lite-effects'),
 'fluidez baixa ativa qualidade reduzida sem tocar nos prémios');
adapt.pause();
const cancelledAtPause=perfCancels;
adapt.resume();
ok(cancelledAtPause===1&&perfRequests>72&&slowClasses.has('is-spinning'),
 'voltar do segundo plano retoma observação sem iniciar outra jogada');
for(let i=0;i<220;i++){clockMs+=16.67;perfNext(clockMs)}
ok(!adapt.isLite()&&!slowClasses.has('lite-effects')&&
 qualityChanges.includes(false),
 'após recuperação sustentada volta automaticamente a alta qualidade');
adapt.stop();
ok(!slowClasses.has('is-spinning')&&perfCancels===2,
 'limpeza da observação em pausa e no fim da jogada');
ok(reels.includes('setLowEffects(value)')&&
 reels.includes('burstParticleBudget(targets.length,this.liteEffects)')&&
 reels.includes('this.clearFx();')&&
 reels.includes('this.disposeFx(halo)')&&
 reels.includes('this.disposeFx(spark)'),
 'Phaser limpa halos e faíscas ao interromper um giro');
ok(main.includes('performanceGuard.pause()')&&
 main.includes('performanceGuard.resume()')&&
 main.includes('scene?.setLowEffects?.(low)')&&
 main.includes('performanceGuard.isLite()'),
 'Safari pausa medição de FPS e diagnóstico mostra qualidade');
ok(html.includes('performance-v29.css?v=2.9')&&
 perfCSS29.includes('.machine.is-spinning .ambient')&&
 perfCSS29.includes('.machine.lite-effects .frame-region')&&
 !html.includes('MULTIS ACUMULADOS'),
 'novos estilos leves preservam barra sem informação repetida');
const S30=new Function(parseModule(supervisorSource)+
 '\nreturn {createVisualSupervisor};')();
let finishVisual;
let visualCancelCount=0;
const clock30={callbacks:new Map(),next:0};
const V30=S30.createVisualSupervisor({
 schedule:(fn)=>{const id=++clock30.next;clock30.callbacks.set(id,fn);return id},
 clear:(id)=>clock30.callbacks.delete(id)
});
const successful30=V30.run(()=>Promise.resolve('drawn'),()=>visualCancelCount++);
ok(V30.active()&&(await successful30).ok&&!V30.active()&&
 clock30.callbacks.size===0&&visualCancelCount===0,
 'supervisor visual termina com sucesso e limpa o temporizador');
const blocked30=V30.run(()=>new Promise(resolve=>{finishVisual=resolve}),
 ()=>visualCancelCount++);
await Promise.resolve();
V30.cancel();
const aborted30=await blocked30;
ok(aborted30.reason==='interrupted'&&!V30.active()&&
 visualCancelCount===1&&clock30.callbacks.size===0,
 'Safari oculta: transição interrompida e efeito recuperado imediatamente');
finishVisual('later');
await Promise.resolve();
ok(visualCancelCount===1,
 'conclusão tardia de tween cancelado não recupera duas vezes');
const timed30=V30.run(()=>new Promise(()=>{}),()=>visualCancelCount++,50);
await Promise.resolve();
for(const cb of [...clock30.callbacks.values()])cb();
const timedResult=await timed30;
ok(timedResult.reason==='timeout'&&visualCancelCount===2&&!V30.active(),
 'timeout visual recupera o tabuleiro e liberta o botão');
const rejected30=await V30.run(()=>Promise.reject(Error('renderer')),()=>visualCancelCount++);
ok(rejected30.reason==='error'&&visualCancelCount===3&&
 clock30.callbacks.size===0,
 'erro Phaser não deixa guardião ativo');
ok(main.includes('visualSupervisor.cancel();')&&
 main.includes('if(document.hidden){repaired=true;break}')&&
 main.includes('visualSupervisor.run(')&&
 main.includes('if(document.hidden)await releaseIfHidden();')&&
 main.includes('else scheduleNext('),
 'integração interrompe cascatas ocultas e agenda apenas quando visível');
const random30=(()=>{let state=921125;
 return ()=>{state=(Math.imul(1664525,state)+1013904223)>>>0;
  return state/4294967296}
})();
let freeEnd30=0,holdEnd30=0,scenarios30=0;
for(const profile30 of ['normal','premios','entretenimento']){
 const demo30=new DemoBank();
 demo30.profile=profile30;demo30.balance=20000;demo30.reserve=2300;
 demo30.stake=.2;
 // Rodadas de stress de sessão mista, sem dependência do DOM/navegador.
 for(let i=0;i<220;i++){
  if(demo30.mode==='base'&&i%40===0){
   if(i%80===0)demo30.buyFree();
   else demo30.buyHold();
  }
  const oldMode=demo30.mode;
  if(oldMode==='hold'){
   const beforeMoney=demo30.balance,beforeFund=demo30.reserve;
   const h=demo30.holdTurn(random30);
   ok(h.ok&&demo30.reserve>=0,'Hold suportado pelo cofre no stress');
   if(h.done){
    holdEnd30++;
    ok(demo30.mode==='base'&&
     Math.abs(demo30.balance-beforeMoney-h.prize)<.001&&
     Math.abs(beforeFund-demo30.reserve-h.prize)<.001,
     'Hold paga uma vez, quando termina');
    ok(!demo30.holdTurn(random30).ok,
     'Hold concluído não pode ser pago novamente');
   }
  }else{
   const beforeMoney=demo30.balance,beforeFund=demo30.reserve;
   const spin=demo30.spin(random30);
   ok(spin.ok&&demo30.reserve>=0&&
    spin.stages.at(-1).ev.lines===0,
    'stress de giros: sequência termina e reserva não é negativa');
   if(oldMode==='free'){
    ok(spin.free&&demo30.freeMultiplier>=1&&demo30.freeMultiplier<=1000&&
     demo30.freePlayed>=1&&
     (demo30.mode==='free'?spin.credited===0:
      demo30.mode==='base'&&Math.abs(spin.credited-demo30.freeAwarded)<.001),
     'Free Spins mantêm multiplicador e pagamento apenas no fim');
    if(demo30.mode==='base')freeEnd30++;
   }else{
    ok(Math.abs(demo30.balance-beforeMoney+demo30.stake-spin.credited)<.001,
     'base: débito de uma aposta e crédito de um prémio');
   }
  }
  scenarios30++;
 }
}
ok(scenarios30===660&&freeEnd30>=1&&holdEnd30>=1,
 '660 rondas determinísticas de todos os perfis incluem Free Spins e Hold');
const Q31=new Function(parseModule(turnQueue31Source)+
 '\nreturn {nextTurn,createTurnQueue};')();
const baseTurn={ready:true,writerReady:true,visible:true,mode:'base',autoLeft:10};
ok(Q31.nextTurn(baseTurn)==='auto'&&
 Q31.nextTurn({...baseTurn,mode:'free',freePaused:false})==='free'&&
 Q31.nextTurn({...baseTurn,mode:'hold'})==='hold',
 'fila escolhe giros automáticos, Free Spins ou Hold pelo modo');
ok(Q31.nextTurn({...baseTurn,mode:'free',freePaused:true})===null&&
 Q31.nextTurn({...baseTurn,visible:false})===null&&
 Q31.nextTurn({...baseTurn,modalOpen:true})===null&&
 Q31.nextTurn({...baseTurn,holdBusy:true})===null&&
 Q31.nextTurn({...baseTurn,mutating:true})===null,
 'fila bloqueia retoma silenciosa e giros durante operações pendentes');
let nextId31=0,executed31=0;const timers31=new Map();
const queue31=Q31.createTurnQueue({
 schedule:(fn,ms)=>{
  const id=++nextId31;
  timers31.set(id,{fn:()=>{timers31.delete(id);fn()},ms});
  return id
 },
 cancel:id=>timers31.delete(id)
});
queue31.arm('free',100,()=>executed31++);
queue31.arm('free',200,()=>executed31+=10);
ok(queue31.pending('free')&&timers31.size===1&&
 [...timers31.values()][0].ms===200,
 'uma segunda marcação de Free Spins substitui a anterior');
const fired31=[...timers31.values()][0].fn;fired31();
ok(executed31===10&&!queue31.pending('free')&&
 !queue31.pending('auto'),
 'temporizador executado deixa de bloquear a próxima marcação');
queue31.arm('auto',130,()=>executed31++);
queue31.arm('hold',170,()=>executed31++);
queue31.clearAll();
ok(!queue31.pending('auto')&&!queue31.pending('hold')&&
 timers31.size===0,
 'quando Safari fica oculto não sobra nenhum giro agendado');
ok(main.includes('const turnQueue=createTurnQueue();')&&
 main.includes('turnQueue.clearAll();')&&
 main.includes('scheduleNext(350);')&&
 main.includes("if(bank.mode==='free')freePaused=true;")&&
 main.includes('if(writerReleasePending)await writerReleasePending;'),
 'fila integrada, recuperação explícita e sem corrida ao liberar a base');
ok(!main.includes('autoTimer=setTimeout(runSpin')&&
 !main.includes('freeTimer=setTimeout(runSpin')&&
 !main.includes('clearTimeout(freeTimer)')&&
 main.includes("else if(saved&&bank.mode==='base'&&autoLeft>0)scheduleNext(700)"),
 'giros automáticos e Free Spins não deixam temporizadores paralelos');
ok(main.includes('if(document.hidden)await releaseIfHidden();')&&
 main.includes('releaseIfHidden();')&&
 main.includes('turnQueue.clearAll();')&&
 main.includes('if(document.hidden)await releaseIfHidden();'),
 'sessão local protege saldo e interrompe automáticos em segundo plano');
const Audit32=new Function(parseModule(integrity32)+
 '\nreturn auditSessionHistory;')();
const receipt32={id:'spin-2026-123456',date:'2026-11-08T15:00:00Z',
 kind:'spin',paid:2};
const snap32={snapshot:{balance:120,reserve:180,pendingVisual:receipt32}};
const moves32=[
 {eventId:'a32',timestamp:'2026-11-08T14:59:00Z',
 type:'spin',balance:118,reserve:182},
 {eventId:'b32',timestamp:'2026-11-08T15:00:00Z',
 type:'spin',balance:120,reserve:180,recoveryId:receipt32.id}
];
ok(Audit32(snap32,moves32).status==='ok'&&
 Audit32(snap32,moves32).linked,
 'auditoria compara o último saldo e o recibo de recuperação');
ok(Audit32(snap32,[moves32[0]]).status==='review'&&
 Audit32(snap32,[moves32[1],moves32[1]]).status==='review',
 'auditoria assinala saldos divergentes e eventos duplicados');
ok(Audit32(snap32,[{...moves32[1],recoveryId:undefined}]).status==='legacy'&&
 Audit32(null,[]).status==='empty',
 'histórico antigo aceite sem ligação a recibos');
const D32=new Function(parseModule(dbsource)+'\nreturn DemoDatabase;')();
const local32=new D32();
local32.mode='indexedDB';
let transactionCount32=0;
local32.database={transaction:(names,mode)=>{
 transactionCount32++;
 ok(names.length===2&&names[0]==='state'&&names[1]==='events'&&
  mode==='readonly','exportação abre transação conjunta');
 const tx={oncomplete:null,onerror:null,onabort:null};
 tx.objectStore=name=>name==='state'?
  {get:()=>({result:{payload:snap32}})}:
  {getAll:()=>({result:moves32})};
 queueMicrotask(()=>tx.oncomplete?.());
 return tx;
}};
const exported32=await local32.exportData();
ok(transactionCount32===1&&exported32.state.snapshot.balance===120&&
 exported32.history.length===2&&
 exported32.history[0].eventId==='b32',
 'exportação reúne histórico e saldo de uma única fotografia da base');
ok(dbsource.includes('recoveryId:snapshot.pendingVisual.id')&&
 main.includes('auditSessionHistory(view.state,records)')&&
 main.includes("receipt.textContent='Recibo: '"),
 'movimentos gravam ligação a recibos e o histórico apresenta-a');
ok((()=>{try{Backup.validateBackup({...validBackup,history:[
 {...rows[0],recoveryId:'x'}]});return false}catch{return true}})()&&
 Backup.validateBackup({...validBackup,history:[
 {...rows[0],recoveryId:'spin-12345678'}]}).history.length===1,
 'novos recibos validados e restauro antigo continua compatível');
const W33=new Function(parseModule(source)+'\nreturn {evaluate,addWildValues,cascadeStages};')();
const pair33=Array.from({length:5},()=>[10,10,10]);
pair33[0][1]=4;pair33[1][1]=4;
const evaluation33=W33.evaluate(pair33,.2);
ok(evaluation33.wildCells.length===2&&evaluation33.wilds===2,
 'WILDs contados por posição da grelha e não por linha paga');
let cumulative33=W33.addWildValues(0,[2]);
ok(cumulative33.multiplier===2,
 'primeira queda com WILD ×2 apresenta ×2');
cumulative33=W33.addWildValues(cumulative33.total,[3]);
ok(cumulative33.multiplier===5,
 'segunda queda com WILD ×3 junta ao ×2 anterior');
cumulative33=W33.addWildValues(cumulative33.total,[1]);
ok(cumulative33.multiplier===6&&
 W33.addWildValues(0,[5,5]).multiplier===10,
 'terceira queda mantém acumulado e múltiplos WILDs somam-se');
let checked33=0;
let seed33=673933;
const random33=()=>{seed33=(Math.imul(1664525,seed33)+1013904223)>>>0;return seed33/4294967296};
for(let trial=0;trial<100;trial++){
 const chain=W33.cascadeStages(pair33,.2,random33,false,{reserve:300});
 let cum=0,pay=0;
 for(const stage of chain){
  cum+=stage.ev.wildAwards.reduce((a,w)=>a+w.value,0);
  ok(stage.ev.multiplier===Math.max(1,cum)&&
   Math.abs(stage.ev.paid-Math.round((stage.ev.raw*stage.ev.multiplier+Number.EPSILON)*100)/100)<.011,
   'cada etapa mostra o multiplicador com que foi calculada');
  pay=Math.round((pay+stage.ev.paid+Number.EPSILON)*100)/100;
  checked33++;
 }
 ok(pay<=300&&checked33>0,'cadeia de cascatas permanece coberta pelo fundo');
}
ok(dbsource.includes('s.freeMultiplier>1000')&&
 recoverySource.includes('Math.min(1000,')&&
 main.includes('outcome.spinMultiplier'),
 'recuperação, histórico e HUD aceitam novos multiplicadores');
ok(reels.includes("potion:'jm38-caracol.svg'")&&
 reels.includes("green:'jm38-joaninha.svg'")&&
 reels.includes("heart:'jm38-flor-azul.svg'")&&
 reels.includes("rose:'jm38-flor-vermelha.svg'")&&
 reels.includes("wild:'jm38-wild.svg'")&&
 reels.includes("stone:'jm38-pedra.svg'"),
 'caracol, joaninha, duas flores, WILD e pedra integrados no Phaser');
ok(reels.includes("this.load.svg('botanical-'+symbol")&&
 reels.includes("if(!canvas&&atlas&&atlasTileFor(symbol))")&&
 reels.includes("this.textures.addCanvas(art,canvas)"),
 'texturas do bosque usam SVG e as restantes mantêm fallback HD');
ok(reels.includes('showWildValues(awards=[])')&&
 reels.includes('this.clearWildValues();')&&
 main.includes('scene.showWildValues?.(stage.ev.wildAwards||[])')&&
 main.includes('scene.clearWildValues?.();'),
 'multiplicador individual fica visível sobre o WILD e limpa entre cascatas');
ok(main.includes("output.classList.add('multi-rise')")&&
 botanical34Css.includes('@keyframes jm34-rise')&&
 botanical34Css.includes('.branch-frame'),
 'MULTI ganha destaque leve sem tapar o tabuleiro');
ok(html.includes('bosque-v34.css?v=3.4')&&
 botanical34Css.includes('jm34-moldura.svg')&&
 botanical34Css.includes('jm34-topo.svg')&&
 botanical34Css.includes('pointer-events:none'),
 'nova moldura e topo integrados sem impedir os toques');
ok(html.includes('cenario-v35.css?v=3.5')&&
 html.indexOf('cenario-v35.css')>html.indexOf('bosque-v34.css'),
 'o cenário de referência é a última camada CSS');
ok(scene35Css.includes('jm80-cenario-imersivo.svg')&&
 scene35Css.includes('jm34-moldura.svg')&&
 scene35Css.includes('.machine .game-header .logo'),
 'bosque imersivo, aro botânico e logótipo grande integrados');
ok(scene35Css.includes('grid-template-rows:')&&
 scene35Css.includes('.machine .frame-region{grid-column:1;grid-row:4}')&&
 scene35Css.includes('.machine .dashboard{grid-column:1;grid-row:6}')&&
 scene35Css.includes('.machine .controls{grid-column:1;grid-row:7}'),
 'topo, jackpots, tambores, painel e botões na sequência da referência');
ok(scene35Css.includes('aspect-ratio:5/3')&&
 scene35Css.includes('width:min(100%,58dvh)')&&
 !scene35Css.includes('@media (orientation:landscape)'),
 'tambores preservam proporção 5:3 no retrato; o modo 16:9 foi retirado');
ok(scene35Css.includes('jm34-flor-vermelha.svg')&&
 scene35Css.includes('pointer-events:none')&&
 scene35Css.includes('prefers-reduced-motion'),
 'controlos ornamentados não bloqueiam os toques');
ok(scene35Css.includes('.machine .dashboard .panel')&&
 scene35Css.includes('.machine .side-btn.turbo[aria-pressed=true]'),
 'painéis e turbo conservam interação e contraste');
ok(html.includes('vertical-v36.css?v=3.6')&&
 html.includes('maximum-scale=1, user-scalable=no')&&
 html.includes("document.addEventListener('dblclick'"),
 'viewport e duplo toque impedem zoom no navegador');
ok(vertical36Css.includes('touch-action:manipulation')&&
 vertical36Css.includes('.machine .phaser-reels canvas')&&
 vertical36Css.includes('visibility:visible'),
 'toque simples continua a clicar e palavra RODAR fica visível');
ok(vertical36Css.includes('@media (orientation:landscape)')&&
 vertical36Css.includes('.machine .portrait-lock')&&
 html.includes('id="portraitLock"')&&
 !scene35Css.includes('@media (orientation:landscape)'),
 'horizontal mostra apenas aviso para voltar à vertical');
const Art37=new Function(parseModule(art37Code)+
 '\nreturn {symbolDisplay,wildBadgePlacement};')();
ok(Art37.symbolDisplay('stone').width>180&&
 Art37.symbolDisplay('green').height>180&&
 Art37.symbolDisplay('teapot').width>=180,
 'peças botânicas aprovadas mantêm escala e o bule HD fica proporcional');
const badge37=Art37.wildBadgePlacement({c:3,r:1,value:5});
ok(badge37.label==='×5'&&badge37.x<720&&badge37.x>540&&
 badge37.y>180&&badge37.y<360,
 'etiqueta do WILD fica dentro da sua célula');
ok(Art37.wildBadgePlacement({c:5,r:1,value:2})===null&&
 Art37.wildBadgePlacement({c:2,r:1,value:6})===null,
 'etiquetas não excedem grelha nem valores de WILD');
ok(reels.includes('symbolDisplay(name,CW,CH)')&&
 reels.includes('wildBadgePlacement(award,CW,CH)')&&
 reels.includes('label.setOrigin(1,0).setDepth(23)')&&
 reels.includes('this.clearWildValues();'),
 'Phaser aplica escala, posição e limpeza das etiquetas');
ok(html.includes('polimento-v37.css?v=3.7')&&
 polish37Css.includes('aspect-ratio:1200/760')&&
 polish37Css.includes('transform:translate(-50%,-50%)')&&
 polish37Css.includes('pointer-events:none'),
 'moldura proporcional não impede os toques nos tambores');
ok(!polish37Css.includes('orientation:landscape')&&
 html.includes('vertical-v36.css?v=3.6'),
 'mantida a orientação exclusiva 9:16');
ok(html.includes('icones-v38.css?v=3.8')&&
 html.indexOf('icones-v38.css')>html.indexOf('polimento-v37.css'),
 'novos materiais e separadores da grelha são a última camada visual');
ok(reels.includes('const BOTANICAL_V38={')&&
 reels.includes('Object.entries(BOTANICAL_V38)')&&
 reels.includes('if(!canvas&&BOTANICAL_V38[symbol]')&&
 reels.includes("if(symbol==='wild'&&this.textures.exists(orig))"),
 'Phaser usa texturas botânicas e WILD dourado com fallback quando necessário');
ok(svg38.length===6&&svg38.every(x=>x.includes('feTurbulence')&&
 x.includes('clipPath')&&x.includes('width="512"')&&
 x.includes('height="512"'))&&
 svg38.some(x=>x.includes('granite38'))&&
 svg38.some(x=>x.includes('dewdrop38')),
 'seis peças novas têm dimensão consistente, grão e reflexos localizados');
ok(icon38Css.includes('orientation:portrait')&&
 icon38Css.includes('repeating-linear-gradient')&&
 icon38Css.includes('.reel-velvet')&&
 !icon38Css.includes('orientation:landscape'),
 'fundo de bosque e divisão de células apenas em 9:16');
const Mat39=new Function(parseModule(material39Code)+
 '\nreturn {materialSettings,bakeMaterial};')();
ok(Mat39.materialSettings('stone').gloss<Mat39.materialSettings('heart').gloss&&
 Mat39.materialSettings('wild').shadow>Mat39.materialSettings('green').shadow,
 'granito menos brilhante e WILD com volume próprio');
let draw39=0,radial39=0,restore39=0,composition39=0;
const stops39=[];
const mockCanvas39={width:0,height:0,getContext:()=>({
 save:()=>{},restore:()=>{restore39++},
 drawImage:()=>{draw39++},
 fillRect:()=>{},createRadialGradient:()=>{
  radial39++;return {addColorStop:(a,b)=>stops39.push([a,b])};
 },
 set globalCompositeOperation(value){if(value==='source-atop')composition39++}
})};
const baked39=Mat39.bakeMaterial({width:512,height:512},'potion',()=>mockCanvas39);
ok(baked39.width===512&&baked39.height===512&&
 draw39===2&&radial39===1&&restore39===2&&composition39===1&&stops39.length===3,
 'sombra e brilho são processados uma vez, apenas nos pixels da peça');
ok(reels.includes("if(symbol==='wild'&&this.textures.exists(orig))")&&
 reels.includes('this.photoWild=true;')&&
 reels.includes('canvas=bakeMaterial(image,symbol)')&&
 reels.includes('this.premiumMaterialCount++'),
 'WILD dourado HD e cinco símbolos aprovados têm materiais próprios');
ok(html.includes('materiais-v39.css?v=3.9')&&
 material39Css.includes('orientation:portrait')&&
 !material39Css.includes('orientation:landscape'),
 'materiais 3D integram-se apenas no jogo 9:16');
const slowFirst391=Spin391(false,0),slowLast391=Spin391(false,4);
const fastFirst391=Spin391(true,0),fastLast391=Spin391(true,4);
ok(slowFirst391.steps===20&&slowLast391.steps===40&&
 slowFirst391.duration===1780&&slowLast391.duration===2540,
 'giro normal dá uma volta extra e dura ligeiramente mais');
ok(slowFirst391.ease==='Sine.easeOut'&&
 fastFirst391.ease==='Cubic.easeOut'&&
 fastFirst391.steps===15&&fastLast391.steps===35&&
 fastFirst391.duration===640&&fastLast391.duration===848,
 'o abrandamento normal é mais suave e o Turbo conserva ritmo');
ok(Array.from({length:5},(_,c)=>Spin391(false,c).steps)
 .every(steps=>steps%5===0)&&
 Array.from({length:5},(_,c)=>Spin391(true,c).steps)
 .every(steps=>steps%5===0)&&
 slowLast391.duration<7000,
 'todos os tambores completam voltas inteiras antes do watchdog');
ok(reels.includes('const motion=spinMotionProfile(turbo,c)')&&
 reels.includes('const finalWrap=steps/items.length;')&&
 reels.includes('duration,\n     ease,')&&
 !reels.includes('duration:duration+(turbo?52:180)*c'),
 'animação usa novo perfil sem trocar peças no último frame');
const Fin40=new Function(parseModule(material39Code)+'\nreturn materialSettings;')();
const Display40=new Function(parseModule(art37Code)+'\nreturn symbolDisplay;')();
ok(Fin40('gem').gloss>.2&&Fin40('scatter').shadow>20&&
 Fin40('coin').gloss>.15&&Fin40('mushroom').shift>=10,
 'materiais com brilho e profundidade nas peças restantes');
ok(Display40('gem').width>180&&Display40('coin').width>180&&
 Display40('hat').width>180&&Display40('teapot').width>=180,
 'restantes peças harmonizadas à dimensão das aprovadas');
ok(reels.includes("PREMIUM_V40=new Set(['teapot','gem','mushroom','hat','scatter'])")&&
 reels.includes('canvas=bakeMaterial(this.cleanOriginalArt(orig),symbol)')&&
 reels.includes('this.remainingHD++;'),
 'original HD para bule, gema, cogumelo, chapéu e SCATTER');
const coinSvg40=await read('../../assets/imagens/jm40-moeda-magica.svg');
ok(coinSvg40.includes('<svg')&&coinSvg40.includes('radialGradient')&&
 coinSvg40.includes('>2×</text>')&&
 reels.includes("this.load.svg('premium-coin'")&&
 reels.includes('this.coinHD=true;'),
 'moeda 2× violeta e dourada com textura Phaser');
console.log('Phaser V4.0: '+checked+' testes passaram; giros='+spins+', free='+free+', Hold='+hold+', etapas='+stages);
