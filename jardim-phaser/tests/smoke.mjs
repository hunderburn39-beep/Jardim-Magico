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
const historyCss=await read('../css/history-v19.css');
const profileCss=await read('../css/perfis-v20.css');
const pinSource=await read('../js/admin-pin-v21.js');
const gatewaySource=await read('../js/storage-gateway-v21.js');
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
ok(bonus.freeDisplay({freeSpins:0,freeTotal:0,freeMultiplier:9}).multiplier===5,
 'Multiplicador visual nunca excede o teto do motor');

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
ok(multiSection.indexOf('id="dropAmount"')<multiSection.indexOf('id="orbs"')&&
   multiSection.indexOf('id="orbs"')<multiSection.indexOf('id="multiTotal"'),
 'barra: valor da queda à esquerda, orbs ao centro e total à direita');
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
ok(prizeCss.includes('.multi-stack .orb-group')&&
  prizeCss.includes('.drop-line-value')&&
  prizeCss.includes('.celebration-value'),
 'barra e celebração têm tipografia premium responsiva');
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
ok(main.includes('if(!freeMultiAdded)')&&
   main.includes('freeOrbHistory.push(outcome.wildMultiplier)'),
 'nas Free Spins cada giro só pode acrescentar uma bola de multi');
ok(main.includes('orbs(outcome.free?freeOrbHistory:earnedMultis,'),
 'orbs refletem os multiplicadores realmente obtidos');
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
ok(reels.includes('Math.floor(48/targets.length)'),
 'limitador de partículas protege fluidez em mobile');
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
   main.includes('if(bank.mode!==\'hold\'||holdBusy)return;'),
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
 '\nreturn {slowFrameRatio,createPerformanceGuard};')();
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
ok(html.includes('mobile-v17.css?v=1.7')&&html.includes('app.js?v=2.2'),
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
 html.includes('app.js?v=2.2'),'botão e versão Phaser V2.2');
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
ok(main.includes("if(document.hidden){\n   adminLock.lock();")&&
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
ok(main.includes('pendingVisual={id:')&&
 main.includes('demoDB.checkpoint(snapshot(),board)'),
 'resultado fica preservado e marca visual é retirada após animação');
ok(main.includes('if(!writerReady||running)return;')&&
 main.includes("PHASER · SÓ LEITURA"),
 'sem IndexedDB não permite alterar saldo com multi-abas');
ok(gatewaySource.includes("'acquireLease'")&&
 gatewaySource.includes("'renewLease'")&&
 gatewaySource.includes("'releaseLease'"),
 'porta V2.1 encaminha operações novas de sessão exclusiva');
console.log('Phaser V2.2: '+checked+' testes passaram; giros='+spins+', free='+free+', Hold='+hold+', etapas='+stages);
