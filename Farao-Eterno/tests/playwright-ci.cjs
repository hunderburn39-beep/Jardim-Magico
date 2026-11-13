'use strict';
/* Faraó Eterno V2 — QA do jogo REAL em Chromium + Safari/WebKit.
 * As capturas vêm do Canvas e dos PNG/WebP originais, não de uma maquete.
 * Todos os créditos são fictícios, numa sessão isolada com ?qa=.
 */
const assert=require('node:assert/strict');
const fs=require('node:fs'),path=require('node:path');
const {chromium,webkit}=require('playwright');
const BASE='http://127.0.0.1:8123/index.html?qa=v2-visual-ci';
const OUTPUT=path.resolve('visual-previews');
fs.mkdirSync(OUTPUT,{recursive:true});
const browsers=[['chromium',chromium],['webkit',webkit]];
async function test(name,engine){
 const browser=await engine.launch({headless:true});
 const page=await browser.newPage({viewport:{width:390,height:844},deviceScaleFactor:2});
 const errors=[],failedResources=[];
 page.on('pageerror',e=>errors.push(e.message));
 page.on('response',r=>{if(r.status()>=400)failedResources.push(r.status()+' '+r.url());});
 try{
  await page.goto(BASE,{waitUntil:'domcontentloaded',timeout:30000});
  await page.waitForFunction(()=>{
   const g=window.__faraoGame;
   return g?.ready&&g.artLoaded===g.artTotal&&g.temple?.width>0&&g.visualV11?.layout;
  },{},{timeout:15000});
  let v=await page.evaluate(()=>{
    const g=window.__faraoGame,stage=document.getElementById('stage');
    const b=stage.getBoundingClientRect(),r=g.visualV11.layout;
    const missing=window.TombEngine.ALL.filter(k=>!g.images[k]?.complete||g.images[k]?.naturalWidth<=0);
    return {
      version:document.documentElement.dataset.faraoVersion,
      loaded:g.artLoaded,total:g.artTotal,missing,failed:g.artFailed,
      layout:r,stage:{x:b.x,y:b.y,width:b.width,height:b.height},
      hasHud:!!document.getElementById('primary-touch-controls'),
      hasPolish:g.qualityV2?.presentationOnly===true,
      hasSuper:!!window.FaraoSuperScatter,
      hasAudio:!!g.audio?.coinCascade,
      coinBudget:g.coinCelebrations?.maxParticles,
      sparkBudget:g.coinCelebrations?.maxSparkles,
      hasIntro:!!document.getElementById('farao-opening')
    };
  });
  assert.deepEqual(v.missing,[],'Símbolos HD que não carregaram');
  assert.deepEqual(v.failed,[],'Foram utilizadas figuras alternativas');
  assert.equal(v.loaded,v.total,'Carregamento incompleto');
  assert(v.hasHud&&v.hasPolish&&v.hasSuper&&v.hasAudio,'Módulos atuais não carregados');
  assert(!v.hasIntro,'QA isolada deve ignorar a capa de entrada');
  assert.equal(v.coinBudget,68,'Excesso de moedas no ecrã do iPhone');
  assert.equal(v.sparkBudget,42,'Excesso de centelhas no ecrã do iPhone');
  const r=v.layout;
  assert(r.left>=0&&r.cw>80&&r.gh>=110&&r.step>=r.cw,'Geometria inválida');
  assert(r.left+4*r.step+r.cw<=576&&r.top+3*r.gh<=1024,'Tambores fora da tela 9:16');
  assert(Math.abs(v.stage.width/v.stage.height-9/16)<0.006,'Tela não é 9:16');
  assert(v.stage.x>=-1&&v.stage.y>=-1,'Tela cortada');
  assert(v.stage.x+v.stage.width<=391&&v.stage.y+v.stage.height<=845,'Tela não cabe no iPhone');
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-inicio.png')});
  // Verificar o canvas realmente ativo, os botões físicos e a movimentação.
  let r1=await page.evaluate(()=>{
   const g=window.__faraoGame,S=window.FaraoSession,A=window.FaraoEconomy;
   g.ledger=S.fresh();S.adjustCredits(g.ledger,'deposit',1000);
   A.adjustFund(g.ledger,'add',100000);
   g.syncLedger();g.spinning=false;g.spinObject=null;g.nextActionAt=0;
   g.paused=false;g.turbo=true;g.save();
   const balance=g.ledger.balanceU,stake=S.units(g.bet);
   g.startSpin();
   return {started:g.spinning,seq:g.ledger.sequence,balance,stake};
  });
  assert(r1.started,'O giro real não começou');
  await page.waitForFunction(()=>!window.__faraoGame.spinning,null,{timeout:20000});
  const state=await page.evaluate(()=>{
    const g=window.__faraoGame,s=g.ledger;
    return {seq:s.sequence,paid:s.stats.paid,pending:!!s.pending,stakeU:s.stats.stakeU,
      resultGrid:g.grid,complete:s.payout===null,winU:s.stats.winU,feature:!!s.feature};
  });
  assert.equal(state.paid,1,'Foi iniciada mais de uma rodada');
  assert.equal(state.seq,1,'Sequência de jogadas alterada');
  assert(!state.pending,'A transação do giro ficou pendente');
  assert.equal(state.stakeU,r1.stake,'Valor da aposta não debitado');
  assert(state.resultGrid.length===5&&state.resultGrid.every(col=>col.length===3),'Grelha 5×3 inválida');
  assert(state.winU>=0,'Prémio negativo');
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-apos-giro.png')});
  // Forçar APENAS em QA a visualização da cerimónia (face já guardada na sessão).
  // Não altera o jogo real nem o RNG: valida a interface de um estado recuperado.
  const ceremony=await page.evaluate(()=>{
    const g=window.__faraoGame,S=window.FaraoSession;
    g.ledger.payout&&S.finishPayout(g.ledger);
    g.ledger.feature={left:8,played:0,totalU:0,symbols:['Q'],retriggers:0,
      superMultiplier:5,superFace:3,superRollSeen:false};
    g.syncLedger();
    g.spinning=false;g._welcomeShown=false;g._welcome=false;
    g._awaitBonusStart=false;g.pendingSummary=null;g.nextActionAt=0;
    g.closeModal();g.showFeatureWelcome();
    return {title:document.getElementById('modal-title')?.textContent,
      die:!!document.getElementById('super-die'),
      button:!!document.getElementById('super-roll')};
  });
  assert(ceremony.die&&ceremony.button,'Cerimónia do dado não apareceu');
  assert(/SUPER SCATTER/i.test(ceremony.title),'Título do Super Scatter em falta');
  assert.equal(await page.locator('#super-die-docked').textContent(),'?',
   'A face sorteada não pode aparecer antes do lançamento');
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-super-scatter.png')});
  await page.locator('#super-roll').click({timeout:5000});
  await page.waitForFunction(()=>document.getElementById('super-dice-result')?.textContent==='×5',null,{timeout:6000});
  const face=await page.locator('#super-dice-result').textContent();
  assert.equal(face,'×5','O dado mudou a face sorteada');
  assert.equal(await page.locator('#super-die-docked').textContent(),'×5',
    'A face não ficou visível no fim da animação');
  const dieVisible=await page.locator('#super-die-docked').evaluate(el=>{
    const cs=getComputedStyle(el),r=el.getBoundingClientRect();
    return cs.display!=='none'&&cs.visibility!=='hidden'&&Number(cs.opacity)>0.9&&r.width>70&&r.height>70;
  });
  assert(dieVisible,'Dado invisível no Safari depois da rotação');
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-dado.png')});
  // O contador final é só gráfico; o saldo e o total de ganhos não mudam
  // quando a janela é apresentada ou quando se salta a animação.
  const summary=await page.evaluate(()=>{
    const g=window.__faraoGame,S=window.FaraoSession;
    g.ledger.feature=null;g._welcomeShown=false;g._welcome=false;
    g.ledger.payout=null;g.spinning=false;g.paused=false;
    g._summaryRequested=true;g.nextActionAt=0;
    g.closeModal();
    g.pendingSummary={
      played:8,retriggers:0,superMultiplier:5,
      superBaseTotalU:S.units(150),totalU:S.units(750)
    };
    g.syncLedger();
    const balance=g.ledger.balanceU,won=g.ledger.stats.winU;
    g.showSummary();
    return {balance,won,transition:g.finalV23?.active,
      paymentPending:!!g.ledger.payout,
      visible:!!document.getElementById('super-final-total')};
  });
  assert(summary.transition,'Transição cinematográfica não começou');
  assert(!summary.visible,'O prémio apareceu antes de terminar a transição');
  assert(!summary.paymentPending,'Resumo começou antes de liquidar as sequências');
  await page.waitForTimeout(240); // capturar a animação já desenhada no WebKit
  const visible=await page.locator('.farao-treasure-transition').evaluate(el=>{
    const st=getComputedStyle(el),box=el.getBoundingClientRect();
    return st.visibility==='visible'&&Number(st.opacity)>.06&&box.width>100;
  });
  assert(visible,'A transição estava ativa mas não ficou visível no ecrã');
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-transicao.png')});
  await page.waitForFunction(()=>!!document.getElementById('super-final-total'),null,{timeout:7000});
  await page.waitForFunction(()=>{
    const p=document.getElementById('super-final-total');
    return p?.classList.contains('reached')&&p.textContent.includes('750,00');
  },null,{timeout:6500});
  const summaryAfter=await page.evaluate(()=>{
    const g=window.__faraoGame;
    return {balance:g.ledger.balanceU,won:g.ledger.stats.winU,
      total:document.getElementById('super-final-total')?.textContent};
  });
  assert.equal(summaryAfter.balance,summary.balance,'Contador final duplicou os créditos');
  assert.equal(summaryAfter.won,summary.won,'Contador final alterou a contabilidade');
  assert(summaryAfter.total.includes('750,00'),'Total final incorreto');
  assert.equal(await page.evaluate(()=>window.__faraoGame.finalV23.active),false,
    'Transição bloqueada depois de aparecer o resumo');
  // Centelha da linha premiada: só apresentação e sem som extra.
  const glint=await page.evaluate(()=>{
    const g=window.__faraoGame;
    const before=g.finalV23.traces,bal=g.ledger.balanceU,wins=g.ledger.stats.winU;
    const t=performance.now();
    g.lastWasFree=false;
    g.basePaylineDisplay=[{
      start:t-900,end:t+1000,amount:2,
      win:{rows:[1,1,1,1,1],count:5,expanded:false,line:0}
    }];
    g.drawFx(t);
    g.basePaylineDisplay=[];
    return {traces:g.finalV23.traces-before,balBefore:bal,balAfter:g.ledger.balanceU,
      winsBefore:wins,winsAfter:g.ledger.stats.winU};
  });
  assert(glint.traces>0,'A luz da linha vencedora não foi desenhada');
  assert.equal(glint.balBefore,glint.balAfter,'A luz da linha alterou créditos');
  assert.equal(glint.winsBefore,glint.winsAfter,'A luz da linha alterou prémios');
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-final-750.png')});
  // Verificar responsividade sem redesenhar nem trocar os símbolos.
  for(const [w,h] of [[320,568],[375,667],[430,932]]){
    await page.setViewportSize({width:w,height:h});
    await page.waitForTimeout(100);
    const rect=await page.locator('#stage').boundingBox();
    assert(rect&&Math.abs(rect.width/rect.height-9/16)<.008,'Erro 9:16 em '+w+'×'+h);
    assert(rect.x>=-1&&rect.y>=-1&&rect.x+rect.width<=w+1&&rect.y+rect.height<=h+1,
      'Moldura cortada em '+w+'×'+h);
  }
  // Safari: fechar a página DURANTE os 1,9 s de rotação do dado
  // não pode marcar o resultado como apresentado nem sortear outra face.
  const midRoll=await page.evaluate(()=>{
    const g=window.__faraoGame;
    g.closeModal();
    g.ledger.feature={
      left:8,played:0,totalU:0,symbols:['Q'],retriggers:0,
      superMultiplier:5,superFace:3,superRollSeen:false
    };
    g.syncLedger();g.paused=false;g.spinning=false;g.nextActionAt=0;
    g._welcome=false;g._welcomeShown=false;g._awaitBonusStart=true;
    g.pendingSummary=null;
    g.showFeatureWelcome();
    document.getElementById('super-roll').click();
    g.save();
    return {rolled:g.ledger.feature.superRollSeen,
      multiplier:g.ledger.feature.superMultiplier};
  });
  assert.equal(midRoll.rolled,false,'Face marcada como vista antes do fim');
  assert.equal(midRoll.multiplier,5,'Dado deixou de ter a face pré-sorteada');
  await page.reload({waitUntil:'domcontentloaded',timeout:30000});
  await page.waitForFunction(()=>window.__faraoGame?.ready,null,{timeout:20000});
  const rollRecovery=await page.evaluate(()=>{
    const g=window.__faraoGame;
    const pending=!g.ledger.feature.superRollSeen;
    g.showFeatureWelcome();
    return {pending,multiplier:g.feature?.superMultiplier,
      result:document.getElementById('super-dice-result')?.textContent,
      button:document.getElementById('super-roll')?.textContent};
  });
  assert.equal(rollRecovery.multiplier,5,'A recuperação alterou o multiplicador');
  assert(rollRecovery.pending,'A animação interrompida foi considerada vista');
  assert.equal(rollRecovery.result,'?','O dado interrompido revelou antecipadamente o resultado');
  assert(/LANÇAR DADO/.test(rollRecovery.button),'Não é possível repetir o lançamento');
  await page.locator('#super-roll').click();
  await page.waitForFunction(()=>window.__faraoGame?.ledger?.feature?.superRollSeen===true,
    null,{timeout:6500});
  await page.locator('#super-roll').click(); // COMEÇAR FREE SPINS
  const afterRoll=await page.evaluate(()=>{
    const g=window.__faraoGame;
    return {paused:g.paused,intro:g.ledger.feature.introSeen,
      multiplier:g.ledger.feature.superMultiplier};
  });
  assert.equal(afterRoll.paused,false,'Free Spins bloqueadas após lançar o dado recuperado');
  assert(afterRoll.intro,'Revelação do bónus não gravada');
  assert.equal(afterRoll.multiplier,5);
  // O Safari também pode fechar durante uma Free Spin JÁ INICIADA.
  // RETOMAR deve soltar o motor, sem disparar um giro extra nesse toque.
  const beforeReload=await page.evaluate(()=>{
    const g=window.__faraoGame;
    g.ledger.feature={
      left:5,played:3,totalU:0,symbols:['Q'],retriggers:0,
      introSeen:true,superMultiplier:5,superFace:3,superRollSeen:true
    };
    g.ledger.pending=null;g.ledger.payout=null;g.spinning=false;
    g.syncLedger();g.paused=false;g._welcome=false;
    g._awaitBonusStart=false;g._freeStarted=true;
    g.save();
    return {balance:g.ledger.balanceU,winU:g.ledger.stats.winU,
      sequence:g.ledger.sequence};
  });
  await page.reload({waitUntil:'domcontentloaded',timeout:30000});
  await page.waitForFunction(()=>window.__faraoGame?.ready,null,{timeout:20000});
  // Nos testes ?qa=, a capa é automaticamente ultrapassada e pode
  // começar a próxima Free Spin enquanto os PNGs carregam. Confirmar
  // a recuperação e depois simular a pausa real de pagehide do Safari.
  const pauseState=await page.evaluate(()=>{
    const g=window.__faraoGame;
    const saved={
      played:g.feature?.played,superMultiplier:g.feature?.superMultiplier,
      balance:g.ledger.balanceU,winU:g.ledger.stats.winU,
      sequence:g.ledger.sequence,statsFree:g.ledger.stats.free,
      pending:!!g.ledger.pending
    };
    g.paused=true;
    g._welcome=false;g._welcomeShown=false;
    g._awaitBonusStart=false;g._freeStarted=true;
    return {...saved,paused:g.paused};
  });
  assert(pauseState.paused,'Falha ao simular a pausa de segundo plano');
  assert(pauseState.played>=3,'A recuperação perdeu as Free Spins');
  assert.equal(pauseState.superMultiplier,5,'A recuperação alterou o dado do Faraó');
  assert(pauseState.balance>=beforeReload.balance,'Saldo diminuiu após recarregar Free Spins');
  assert(pauseState.winU>=beforeReload.winU,'O histórico de prémios regrediu');
  assert(pauseState.sequence>=beforeReload.sequence,
    'A sequência das jogadas recuou na recuperação');
  await page.locator('#primary-touch-controls button[aria-label="RETOMAR"]').click({timeout:5000});
  const overlapping=await page.evaluate(()=>[...document.querySelectorAll('.touch-target')]
    .filter(b=>b.getAttribute('aria-label')==='RETOMAR')
    .every(b=>b.getAttribute('aria-hidden')==='true'&&b.tabIndex===-1));
  assert(overlapping,'O botão antigo deve ficar fora dos toques e do VoiceOver');
  await page.waitForFunction(()=>!window.__faraoGame.paused,null,{timeout:3000});
  await page.screenshot({path:path.join(OUTPUT,'farao-v2-'+name+'-free-resumidas.png')});
  assert.deepEqual(failedResources,[],'Assets não encontrados');
  assert.deepEqual(errors,[],'Erros JS inesperados');
  console.log(name.toUpperCase()+' V2 PASS · arte '+v.loaded+'/'+v.total+
      ', 9:16, giro real, Super Scatter ×5, transição e 5 screenshots');
 }finally{await page.close();await browser.close();}
}
(async()=>{
 let failed=false;
 for(const [name,engine] of browsers){
  try{await test(name,engine);}
  catch(e){failed=true;console.error(name.toUpperCase()+' V2 FAIL: '+(e?.stack||e));}
 }
 if(failed)process.exitCode=1;
})().catch(e=>{console.error(e);process.exitCode=1;});
