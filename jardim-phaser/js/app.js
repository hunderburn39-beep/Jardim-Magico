/* Jardim Mágico Phaser V1 — orquestração, UI e créditos DEMO.
   Sem pagamentos reais, sem RTP auditado e sem sincronização entre devices.
   Cada resultado e prémio são decididos ANTES da animação vertical Phaser.
 */
import {DemoBank,newBoard,winningCells,money,DEMO_PROFILES} from './engine.js?v=3.3';
import {demoDB as localDB,validateBackup} from './database.js?v=2.2';
import {createStorageGateway} from './storage-gateway-v21.js?v=2.2';
import {createAdminPinLock} from './admin-pin-v21.js?v=2.1';
import {audio} from './audio.js?v=1';
import {createReelGame} from './reels.js?v=4.0';
import {holdLandingIndices,holdDisplay,freeDisplay} from './bonus-presentation.js?v=1.5';
import {createPerformanceGuard} from './performance-v17.js?v=2.9';
import {normalizeAdmin,readPercent,validateFundChange,summarizeLedger,makePrintableReport} from './admin-v18.js?v=1.8';
import {filterHistory,historyPage} from './history-v19.js?v=1.9';
import {auditSessionHistory} from './history-integrity-v32.js?v=3.2';
import {spinRecoveryReceipt,holdRecoveryReceipt,recoveryMessage,createDiagnostics} from './recovery-v23.js?v=2.3';
import {sessionState,canResumeFree} from './session-v24.js?v=2.4';
import {freeProgress,holdProgress} from './bonus-progress-v26.js?v=2.6';
import {celebrationView,winBreakdown} from './win-ui-v27.js?v=2.7';
import {createVisualSupervisor} from './visual-supervisor-v30.js?v=3.0';
import {createTurnQueue,nextTurn} from './turn-queue-v31.js?v=3.1';
const demoDB=createStorageGateway(localDB);
const adminLock=createAdminPinLock();
const diagnostics=createDiagnostics();
const visualSupervisor=createVisualSupervisor();
diagnostics.record('boot');
const requireAdminUnlocked=()=>adminLock.unlocked()&&adminLock.touch();
const $=id=>document.getElementById(id);
const BETS=[.05,.1,.2,.4,.6,.8,1,1.5,2,3,5,7.5,10];
const bank=new DemoBank();
let adminSettings=normalizeAdmin();
let scene=null,game=null;
const performanceGuard=createPerformanceGuard(
 document.getElementById('machine'),window,
 low=>scene?.setLowEffects?.(low)
);
let board=newBoard(),running=false,ready=false,turbo=false;
let autoLeft=0;
const turnQueue=createTurnQueue();
let writerReleasePending=null;
let bigTimer=null,winTimer=null,dropTimer=null,bonusTimer=null;
let holdBusy=false;
let freeOrbHistory=[];
let modalOpen=false,holdsLong=false,longTimer=null;
let mutating=false,saveIssue=false,freePaused=false;
let writerReady=false,writerCheckBusy=false,writerTimer=null,pendingVisual=null,recoveredVisual=false;
const tabToken=(typeof crypto!=='undefined'&&crypto.randomUUID)?
 crypto.randomUUID():'tab-'+Date.now()+'-'+Math.random().toString(36).slice(2);
let loopToken=0;
const wait=ms=>new Promise(resolve=>setTimeout(resolve,ms));
function round(n){return Math.round((n+Number.EPSILON)*100)/100}
function say(msg){
 $('resultText').textContent=msg;
}
function status(msg){$('spinText').textContent=msg}
function copySnapshot(snapshot){
 for(const k of ['balance','reserve','stake','spins','lastWin','freeSpins',
  'freeTotal','freeMultiplier','freePlayed','freeAwarded']){
  const n=Number(snapshot[k]);
  if(Number.isFinite(n)&&n>=0)bank[k]=n;
 }
 if(['premios','normal','entretenimento'].includes(snapshot.profile))bank.profile=snapshot.profile;
 if(['base','free','hold'].includes(snapshot.mode))bank.mode=snapshot.mode;
 if(snapshot.admin)adminSettings=normalizeAdmin(snapshot.admin.ownerPct);
 pendingVisual=snapshot.pendingVisual||null;
 recoveredVisual=!!pendingVisual;
 if(typeof snapshot.status==='string')bank.status=snapshot.status.slice(0,180);
 if(bank.mode==='hold'&&Array.isArray(snapshot.hold?.grid)&&snapshot.hold.grid.length===16)
  bank.hold=structuredClone(snapshot.hold);
 else {if(bank.mode==='hold')bank.mode='base';bank.hold=null}
 bank.freeMultiplier=Math.max(1,Math.min(1000,bank.freeMultiplier));
 bank.freeSpins=Math.max(0,Math.min(60,bank.freeSpins));
}
function snapshot(){
 return {...bank.snapshot,admin:{...adminSettings},pendingVisual};
}
function update(){
 const s=snapshot();
 renderSessionBanner();
 $('profileStatus').textContent='MODO '+(DEMO_PROFILES[s.profile]||DEMO_PROFILES.normal).toLocaleUpperCase('pt-PT');
 $('balance').textContent=money(s.balance);
 $('reserve').textContent=money(s.reserve);
 $('stake').textContent=money(s.stake);
 $('lastWin').textContent=money(s.lastWin);
 $('jpMin').textContent=money(Math.min(s.reserve,s.stake*250));
 $('jpMed').textContent=money(Math.min(s.reserve,s.stake*500));
 $('jpMax').textContent=money(Math.min(s.reserve,s.stake*1000));
 $('jpUltra').textContent=money(Math.min(s.reserve,s.stake*5000));
 if(!running)$('multiTotal').textContent='×'+(s.mode==='free'?s.freeMultiplier:1);
 $('freeMeter').hidden=s.mode!=='free';
 $('freeCount').textContent=s.freeSpins;
 const free=freeProgress(s);
 $('freeHud').hidden=!free.active;
 $('hudFreeCount').textContent=free.remaining;
 $('hudFreeTotal').textContent=money(free.total);
 $('hudFreePlayed').textContent=free.played+' giros realizados · MULTI aplicado no final';
 $('autoRemaining').textContent=autoLeft?String(autoLeft):'∞';
 $('turboState').textContent=turbo?'ON':'OFF';
 $('turboBtn').setAttribute('aria-pressed',String(turbo));
 const current=BETS.findIndex(v=>Math.abs(v-s.stake)<1e-6);
 const blocked=running||mutating||s.mode!=='base'||!ready||!writerReady;
 $('betDown').disabled=blocked||current<=0;
 $('betUp').disabled=blocked||current>=BETS.length-1;
 $('bonusBtn').disabled=blocked;
 $('adminBtn').disabled=blocked;
 $('spinBtn').disabled=running||mutating||!ready||!writerReady||s.mode==='hold'||s.mode==='free';
 $('creditBtn').disabled=!writerReady||running||mutating;
 $('vaultBtn').disabled=!writerReady||mutating;
 $('autoBtn').disabled=(!ready||!writerReady||mutating||s.mode==='hold'||(running&&autoLeft===0))&&autoLeft===0;
 $('autoBtn').classList.toggle('active',autoLeft>0);
}
function renderMultiplier(_history=[],effective=1){
 // O valor usado no prémio da queda é o mesmo que fica no indicador.
 const output=$('multiTotal');
 const next=Math.max(1,Math.trunc(Number(effective)||1));
 const before=Math.max(1,Number(output.dataset.value)||1);
 output.textContent='×'+next;
 output.dataset.value=String(next);
 if(next>before){
  output.classList.remove('multi-rise');
  // Reinício único de animação quando entra um multiplicador novo.
  void output.offsetWidth;
  output.classList.add('multi-rise');
 }else if(next<=1)output.classList.remove('multi-rise');
}
function celebrationTier(value,stake){
 const ratio=stake>0?value/stake:0;
 if(ratio>=100)return 'SENSACIONAL';
 if(ratio>=20)return 'MEGA';
 return 'NICE';
}
function celebrate(title,value){
 const view=celebrationView(title,value,turbo);
 if(!view)return;
 const el=$('bigWin');
 $('bigWinTitle').textContent=view.title;
 $('bigWinValue').textContent=view.formatted;
 el.dataset.tier=view.tier;
 el.dataset.width=view.width;
 el.style.setProperty('--celebration-duration',view.duration+'ms');
 clearTimeout(bigTimer);
 el.classList.remove('active');
 // Uma única leitura do layout, apenas quando o resultado fica fechado.
 void el.offsetWidth;
 el.classList.add('active');
 bigTimer=setTimeout(()=>el.classList.remove('active'),view.duration);
}
function bonusAnnouncement(title,value,duration=1600){
 const notice=$('bonusAnnouncement');
 clearTimeout(bonusTimer);
 $('bonusAnnouncementTitle').textContent=title;
 $('bonusAnnouncementValue').textContent=value;
 notice.hidden=false;
 // Reiniciar a animação de entrada e saída a cada reativação.
 notice.style.animation='none';
 void notice.offsetWidth;
 notice.style.animation='';
 bonusTimer=setTimeout(()=>{notice.hidden=true},duration);
}
function showDropAmount(value){
 // Visível à esquerda da barra, sem blocos de multiplicador duplicados.
 const el=$('dropAmount');
 clearTimeout(dropTimer);
 el.textContent=money(value);
 el.classList.remove('pulse');void el.offsetWidth;el.classList.add('pulse');
 dropTimer=setTimeout(()=>el.classList.remove('pulse'),620);
}
function showWinText(wins,value,total=false,factor=1){
 const notice=$('winNotice');
 const summary=winBreakdown(wins,total,factor);
 clearTimeout(winTimer);
 if(!summary.hasWins){notice.hidden=true;return}
 const count=document.createElement('strong');
 count.className='win-notice-count';count.textContent=summary.label;
 const values=document.createElement('span');
 values.className='win-notice-values';
 values.textContent=summary.values.join(' + ')+
  (summary.other?' + '+summary.other+' outras':'');
 notice.replaceChildren(count,values);
 // O valor TOTAL permanece na barra da queda, sem repetir a soma aqui.
 notice.setAttribute('aria-label',summary.label+' · prémio '+money(value));
 notice.hidden=false;
 if(total)winTimer=setTimeout(()=>notice.hidden=true,turbo?1450:2150);
}
async function persist(type,details={}){
 if(!writerReady){diagnostics.record('save-failed');saveIssue=true;update();return false}
 const ok=await demoDB.save(snapshot(),details.board||board,{
  type,id:(typeof crypto!=='undefined'&&crypto.randomUUID)?
   crypto.randomUUID():'event-'+Date.now()+'-'+Math.random().toString(36).slice(2),
  stake:bank.stake,paid:details.paid||0,wager:details.wager||0,
  cost:details.cost||0,stages:details.stages||0,
  ownerPct:adminSettings.ownerPct,merchantPct:adminSettings.merchantPct,
  amount:details.amount||0,note:details.note||'',profile:bank.profile
 });
 diagnostics.record(ok?'save-ok':'save-failed');
 saveIssue=!ok;
 if(!ok)$('dbStatus').textContent='PHASER · ERRO A GUARDAR';
 update();
 return ok;
}
function renderSessionBanner(){
 const state=sessionState({ready,writerReady,mutating,saveIssue,
  paused:freePaused,mode:bank.mode});
 const panel=$('sessionBanner');
 panel.hidden=state.kind==='ok'||state.kind==='boot';
 panel.dataset.kind=state.kind;
 $('sessionMessage').textContent=state.message;
 const button=$('sessionAction');
 button.hidden=!state.action;
 button.disabled=running||mutating||holdBusy||writerCheckBusy;
 if(state.action)button.textContent=state.action;
}
function continuationState(){
 return {mode:bank.mode,autoLeft,freePaused,visible:!document.hidden,
  writerReady,ready,modalOpen,running,mutating,holdBusy};
}
function scheduleNext(delay=650){
 const kind=nextTurn(continuationState());
 if(!kind)return false;
 turnQueue.clearAll();
 turnQueue.arm(kind,delay,()=>{
  if(nextTurn(continuationState())!==kind)return;
  if(kind==='hold')showHold();
  else runSpin();
 });
 return true;
}
async function releaseIfHidden(){
 if(!document.hidden||!writerReady||running||mutating||holdBusy)return false;
 writerReady=false;diagnostics.record('writer-release');update();
 const release=demoDB.releaseLease(tabToken).catch(err=>{
  console.warn('Libertar sessão em segundo plano:',err);
 });
 writerReleasePending=release;
 try{await release}finally{
  if(writerReleasePending===release)writerReleasePending=null;
 }
 return true;
}
function resumeFree(){
 if(!canResumeFree({ready,writerReady,paused:freePaused,mode:bank.mode,
  hidden:document.hidden,running,mutating}))return false;
 freePaused=false;
 turnQueue.clear('free');
 update();
 scheduleNext(350);
 return true;
}
async function withMutation(action){
 if(mutating||running||holdBusy||!writerReady)return false;
 mutating=true;update();
 try{return await action()}
 catch(e){
  saveIssue=true;diagnostics.record('save-failed');
  console.error('Operação pendente:',e);
  say('Operação não concluída. Revê a sessão local.');return false;
 }finally{
  mutating=false;update();
  if(document.hidden)await releaseIfHidden();
  else if(bank.mode==='free'&&!freePaused&&!turnQueue.pending('free'))
   scheduleNext(1350);
  else if(!writerReady)refreshWriter();
 }
}
function retrySession(){
 if(mutating||running||holdBusy||writerCheckBusy)return;
 refreshWriter(true);
}

function stopAuto(silent=false){
 const wasActive=autoLeft>0;
 autoLeft=0;turnQueue.clear('auto');
 if(wasActive){
  update();
  if(!silent)say('Giros automáticos parados');
 }
}
async function visual(promiseFactory,expected,deadline=7000){
 if(document.hidden)return false;
 const outcome=await visualSupervisor.run(
  promiseFactory,
  ()=>scene?.recover?.(expected),
  deadline
 );
 if(!outcome.ok){
  diagnostics.record(outcome.reason==='timeout'?'visual-timeout':'spin-visual-error');
  console.warn('Transição visual recuperada:',outcome.reason);
 }
 return outcome.ok;
}
async function runSpin(){
 if(document.hidden||!writerReady||!ready||!scene||running||mutating||holdBusy||
    freePaused||modalOpen||bank.mode==='hold')return;
 turnQueue.clearAll();
 const before=snapshot();
 const previousBoard=board.map(col=>col.slice());
 let outcome;
 try{outcome=bank.spin()}
 catch(err){
  console.error('Motor de prémios:',err);
  copySnapshot(before);update();
  say('Falha a calcular o giro; saldo preservado');
  return;
 }
 if(!outcome.ok){stopAuto();say(outcome.message);return}
 const stages=outcome.stages||[{board:outcome.board,ev:outcome.ev,removed:[]}];
 const finalBoard=stages.at(-1).board;
 if(!outcome.free)freeOrbHistory=[];
 const earnedMultis=[];
 running=true;update();performanceGuard.start();
 clearTimeout(dropTimer);
 $('dropAmount').textContent=money(0);
 $('dropAmount').classList.remove('pulse');
 $('dropLabel').textContent='VALOR DA QUEDA';
 renderMultiplier(outcome.free?freeOrbHistory:earnedMultis,1);
 $('winNotice').hidden=true;
 status('TAMBORES A RODAR ↓');
 say('Os símbolos estão a descer…');
 if(!outcome.free&&autoLeft>0)autoLeft--;
 // Guardar o PRÉMIO antes de apresentar peças. A recarga não volta a pagar.
 board=finalBoard.map(col=>col.slice());
 pendingVisual=spinRecoveryReceipt(outcome,bank.snapshot,stages.length);
 let saved=false;
 try{
  saved=await persist('spin',{board:finalBoard,paid:outcome.credited,
   wager:outcome.free?0:bank.stake,stages:stages.length});
 }catch(err){console.error('Falha na gravação do giro:',err)}
 if(!saved){
  copySnapshot(before);board=previousBoard;
  running=false;performanceGuard.stop();update();stopAuto();
  say('Giro cancelado: falha a guardar a sessão local. Nenhum resultado foi apresentado.');
  return;
 }
 audio.play('spin');
 let repaired=false;
 try{
  if(!(await visual(()=>scene.spin(outcome.board,{turbo}),
    outcome.board,turbo?3900:7000)))repaired=true;
  for(let i=0;i<stages.length;i++){
   // Uma aba oculta não deve iniciar bursts/cascatas adicionais.
   if(document.hidden){repaired=true;break}
   const stage=stages[i];
   // Mostrar individualmente ×1…×5 no WILD que foi premiado.
   scene.showWildValues?.(stage.ev.wildAwards||[]);
   const wins=stage.ev.lineWins||[];
   const cells=winningCells(stage.ev);
   const next=i<stages.length-1?stages[i+1]:null;
   // Nas Free Spins o multiplicador só é aplicado no fim da série.
   const fac=outcome.free?1:stage.ev.multiplier;
   const lineTotal=round(stage.ev.raw*fac);
   const wildAwards=stage.ev.wildAwards||[];
   if(wildAwards.length){
    (outcome.free?freeOrbHistory:earnedMultis)
     .push(...wildAwards.map(w=>w.value));
   }
   // MULTI mostra a soma dos WILD desta spin, incluindo cascatas.
   // No bónus, o prémio bruto continua fixo até ao pagamento final.
   renderMultiplier(outcome.free?freeOrbHistory:earnedMultis,
    stage.ev.multiplier);
   if(!wins.length){
    status('FIM DAS QUEDAS · SEM NOVAS COMBINAÇÕES');
    break;
   }
   status('QUEDA '+(i+1)+' · '+wins.length+' COMBINAÇÃO'+
    (wins.length>1?'ÕES':'')+' PREMIADA'+(wins.length>1?'S':''));
   showWinText(wins,lineTotal,false,fac);
   $('dropLabel').textContent='QUEDA '+(i+1);
   showDropAmount(lineTotal);
   say(wins.length+' combinação'+(wins.length>1?'ões':'')+
    ' · queda '+(i+1)+' · '+money(lineTotal)+' créditos');
   // Não repetir celebrações em cada cascata: um destaque no fim do giro.
   audio.play('win');
   if(!(await visual(()=>scene.burst(cells,{
    destroy:!!next,
    duration:turbo?390:780
   }),stage.board,turbo?1900:3000)))repaired=true;
   if(document.hidden){repaired=true;break}
   if(next){
    scene.clearWildValues?.();
    audio.play('cascade');
    status('↓ PEÇAS A CAIR DE CIMA ↓');
    if(!(await visual(()=>scene.cascade(next.board,next.removed,{turbo}),
      next.board,turbo?2500:3800)))repaired=true;
    if(document.hidden){repaired=true;break}
   }
  }
 }catch(err){
  repaired=true;diagnostics.record('spin-visual-error');console.error('Falha recuperada no giro:',err);
 }finally{
  // Nunca deixar preso o botão: corrigir malhas, limpar estado e continuar.
  try{scene.recover(finalBoard)}catch(err){console.warn(err)}
  if(pendingVisual)await clearVisualReceipt();
  running=false;
  performanceGuard.stop();
  const all=stages.flatMap(stage=>(stage.ev.lineWins||[]).map(win=>({
   ...win,
   amount:round(win.amount*(outcome.free?1:stage.ev.multiplier))
  })));
  $('winNotice').hidden=all.length===0;
  // A soma do base inclui WILD. Nas Free Spins o total só é
  // multiplicado e creditado quando a série terminar.
  const visibleTotal=outcome.free?outcome.rawTotal:outcome.potential;
  if(all.length)showWinText(all,visibleTotal,true);
  $('dropLabel').textContent='TOTAL DO GIRO';
  showDropAmount(all.length?visibleTotal:0);
  renderMultiplier(outcome.free?freeOrbHistory:earnedMultis,
   outcome.free?bank.freeMultiplier:outcome.spinMultiplier);
  status(all.length?all.length+' COMBINAÇÕES SOMADAS · '+
   Math.max(0,stages.length-1)+' QUEDAS':'TAMBORES PRONTOS');
  update();
  // update() atualiza o saldo e os modos; repor aqui a informação
  // visual dos Wild que efetivamente saíram durante este giro.
  renderMultiplier(outcome.free?freeOrbHistory:earnedMultis,
   outcome.free?bank.freeMultiplier:outcome.spinMultiplier);
  if(repaired){
   say('Movimento recuperado; resultado e saldo preservados');
  }else if(outcome.free&&outcome.credited>0){
   // Nas Free Spins a soma só é paga após a última rodada.
   say('Free Spins concluídas · prémio final '+money(outcome.credited)+
    ' créditos');
   // Usar apenas a celebração em três linhas, evitando dois painéis
   // de vitória sobrepostos quando acaba o bónus.
   celebrate('FREE SPINS',outcome.credited);
  }else if(outcome.triggered){
   say(outcome.triggered+(outcome.credited>0?
    ' · prémio '+money(outcome.credited):''));
   if(bank.mode==='free'||outcome.triggered.includes('Free Spins')){
    bonusAnnouncement('✨ FREE SPINS ✨',outcome.triggered,1350);
   }else if(outcome.free&&bank.mode==='base'){
    bonusAnnouncement('✨ FREE SPINS CONCLUÍDAS ✨',
     'Total: '+money(outcome.credited)+' créditos',1450);
   }
   if(outcome.credited>0)
    celebrate(celebrationTier(outcome.credited,bank.stake),outcome.credited);
  }else if(outcome.free){
   say('Free Spins · acumulado '+money(bank.freeTotal)+
    ' ×'+bank.freeMultiplier);
  }else if(all.length){
   say(all.length+' combinações · prémio total '+
    money(outcome.credited)+' créditos');
   celebrate(celebrationTier(outcome.credited,bank.stake),outcome.credited);
  }else say('Sem prémio neste giro');
  // Jogadas seguintes só avançam quando as quedas atuais terminarem.
  if(document.hidden)await releaseIfHidden();
  else{
   if(!writerReady)refreshWriter();
   else scheduleNext(bank.mode==='hold'?650:
    bank.mode==='free'?(turbo?350:780):(turbo?230:600));
  }
 }
}
function modal(title,description,actions=[]){
 modalOpen=true;turnQueue.clearAll();autoLeft=0;
 $('modalTitle').textContent=title;
 const area=$('modalContent');area.replaceChildren();
 const p=document.createElement('p');p.textContent=description;area.append(p);
 for(const action of actions){
  const btn=document.createElement('button');
  btn.className='choice';btn.type='button';btn.disabled=!!action.disabled;
  const b=document.createElement('b');b.textContent=action.name;btn.append(b);
  if(action.detail){
   const small=document.createElement('small');small.textContent=action.detail;
   btn.append(small);
  }
  btn.addEventListener('click',async()=>{
   closeModal();
   try{await action.act?.()}catch(err){console.error(err);say('Operação indisponível')}
  });
  area.append(btn);
 }
 $('overlay').hidden=false;
 update();
}
function closeModal(){
 modalOpen=false;$('overlay').hidden=true;update();
 if(bank.mode==='free'&&!running&&!freePaused&&writerReady&&!document.hidden)
  scheduleNext(500);
}
function bonusModal(){
 if(!writerReady||running||mutating||bank.mode!=='base')return;
 modal('Comprar bónus DEMO',
  'Compras voluntárias apenas com créditos fictícios; sem dinheiro real.',[
   {name:'✨ 15 Free Spins',detail:'Custo: '+money(bank.bonusPrice),
    disabled:bank.balance<bank.bonusPrice,
    act:()=>withMutation(async()=>{
     const before=snapshot(),cost=bank.bonusPrice;
     const r=bank.buyFree();update();say(r.message);
     if(r.ok){
      if(!(await persist('buy-free',{cost}))){
       copySnapshot(before);update();say('Compra cancelada: não foi possível guardar.');return;
      }
      bonusAnnouncement('✨ FREE SPINS ✨','15 giros de demonstração',1300);
      // A continuação é armada após withMutation terminar a gravação.
     }
    })},
   {name:'🪙 Hold & Win 4×4',detail:'Custo: '+money(bank.holdPrice),
    disabled:bank.balance<bank.holdPrice,
    act:()=>withMutation(async()=>{
     const before=snapshot(),cost=bank.holdPrice;
     const r=bank.buyHold();update();say(r.message);
     if(r.ok){
      if(!(await persist('buy-hold',{cost}))){
       copySnapshot(before);update();say('Compra cancelada: não foi possível guardar.');return;
      }
      showHold();
     }
    })}
  ]);
}

function exportDiagnostics(){
 const state=diagnostics.snapshot();
 const report={...state,fxLowQuality:performanceGuard.isLite(),
  visualActive:visualSupervisor.active(),
  pendingNext:['free','auto','hold'].filter(kind=>turnQueue.pending(kind)),
  dbMode:demoDB.mode,
  writerActive:writerReady,
  gameMode:bank.mode,
  pendingRecovery:!!pendingVisual};
 const file=new Blob([JSON.stringify(report,null,2)],{type:'application/json'});
 const href=URL.createObjectURL(file);
 const a=document.createElement('a');
 a.href=href;a.download='jardim-phaser-diagnostico-'+new Date().toISOString().slice(0,10)+'.json';
 document.body.append(a);a.click();a.remove();
 setTimeout(()=>URL.revokeObjectURL(href),2500);
}
function diagnosticModal(){
 const counts=diagnostics.snapshot().counts;
 modal('Diagnóstico da demonstração',
  'Estado desta sessão. Só contém contagens técnicas locais, nunca PIN nem dados pessoais.',[
  {name:'⬇ Guardar diagnóstico JSON',detail:'Inclui falhas de gravação, recuperação e estado da aba.',act:exportDiagnostics}
 ]);
 const el=document.createElement('section');
 el.className='diag-panel';
 const rows=[
  ['Base de dados',demoDB.mode],
  ['Estado de escrita',writerReady?'Aba ativa':'Só leitura'],
  ['Modo de jogo',bank.mode],
  ['Recuperação pendente',pendingVisual?'Sim':'Não'],
  ['Efeitos automáticos',performanceGuard.isLite()?'Modo leve':'Alta qualidade'],
  ['Transição Phaser',visualSupervisor.active()?'Em execução':'Livre'],
  ['Próximo giro', ['free','auto','hold'].filter(kind=>turnQueue.pending(kind)).join(', ')||'Nenhum'],
  ['Falhas a guardar',counts['save-failed']||0],
  ['Falhas de checkpoint',counts['checkpoint-failed']||0],
  ['Interrupções visuais',counts['spin-visual-error']||0],
  ['Retomas Free Spins',counts['recovered-free']||0],
  ['Retomas Hold',counts['recovered-hold']||0]
 ];
 for(const [name,value] of rows){
  const p=document.createElement('p');const strong=document.createElement('strong');
  strong.textContent=name+': ';p.append(strong,document.createTextNode(String(value)));
  el.append(p);
 }
 $('modalContent').append(el);
}
function showRecoveryState(marker){
 if(!writerReady||!ready||document.hidden)return false;
 const summary=recoveryMessage(marker,snapshot());
 if(summary.kind==='hold-active'){
  diagnostics.record('recovered-hold');
  showHold();
  $('holdInfo').textContent='↻ Recuperado · '+summary.detail;
  say('Hold & Win retomado na grelha guardada.');
  return true;
 }
 if(summary.kind==='free-active'){
  diagnostics.record('recovered-free');
  turnQueue.clear('free');
  freePaused=true;update();
  modal(summary.title,summary.detail,[{
   name:'▶ RETOMAR FREE SPINS',detail:'Só retoma as jogadas ainda pendentes.',
   act:resumeFree
  }]);
  return true;
 }
 if(!marker)return false;
 diagnostics.record(marker.kind==='hold'?'recovered-hold':'recovered-spin');
 modal(summary.title,summary.detail,[{name:'CONTINUAR',detail:'Os créditos fictícios já foram contabilizados.'}]);
 if(summary.kind==='hold-complete'&&Array.isArray(marker.grid)&&marker.grid.length===16){
  const grid=document.createElement('div');grid.className='recovered-hold-grid';
  grid.setAttribute('aria-label','Grelha de moedas recuperada de quatro por quatro');
  for(const value of marker.grid){
   const cell=document.createElement('span');
   cell.className='recovered-hold-coin'+(Number(value)>0?' with-coin':'');
   cell.textContent=Number(value)>0?Number(value)+'×':'·';
   grid.append(cell);
  }
  const caption=document.createElement('p');caption.className='recovered-hold-caption';
  caption.textContent='Última grelha guardada · '+money(marker.total)+' créditos em moedas';
  $('modalContent').append(grid,caption);
 }
 return true;
}
async function clearVisualReceipt(){
 if(!pendingVisual||!writerReady)return false;
 const original=pendingVisual;pendingVisual=null;
 try{
  const ok=await demoDB.checkpoint(snapshot(),board);
  if(ok){diagnostics.record('checkpoint-ok');return true}
 }catch(err){console.error('Checkpoint visual:',err)}
 pendingVisual=original;
 diagnostics.record('checkpoint-failed');
 return false;
}

function showHold(){
 if(bank.mode!=='hold'||!bank.hold)return;
 $('holdOverlay').hidden=false;
 $('holdResult').hidden=true;
 $('holdBtn').hidden=false;
 $('holdBtn').disabled=holdBusy;
 renderHold(null,bank.hold);
}
function renderHold(previousGrid=null,hold=bank.hold){
 if(!hold)return;
 const display=holdDisplay(hold);
 const landing=new Set(holdLandingIndices(previousGrid,hold.grid));
 const grid=$('holdGrid');grid.replaceChildren();
 for(let i=0;i<hold.grid.length;i++){
  const value=hold.grid[i];
  const cell=document.createElement('div');
  cell.className='hold-cell'+(value?' filled':'')+
   (landing.has(i)?' new-coin':'');
  cell.textContent=value?value+'×':'✦';
  cell.setAttribute('aria-label',value?'Moeda de '+value+' vezes':'Espaço vazio');
  grid.append(cell);
 }
 $('holdCoins').textContent=display.coins+'/16';
 $('holdTotal').textContent=money(display.total);
 const meter=holdProgress(hold);
 $('holdMissing').textContent=meter.missingLabel;
 $('holdProgress').setAttribute('aria-valuenow',String(meter.filled));
 $('holdProgress').setAttribute('aria-valuetext',meter.progressText);
 $('holdProgressFill').style.width=meter.percentage+'%';
 $('holdInfo').textContent=meter.attemptsText+' · giro '+display.rounds;
 const dots=$('holdAttempts').querySelectorAll('.attempt-dot');
 for(let i=0;i<dots.length;i++){
  dots[i].classList.toggle('used',i>=display.attempts);
 }
}
async function holdTurn(){
 if(!writerReady||mutating||bank.mode!=='hold'||holdBusy)return;
 holdBusy=true;
 const button=$('holdBtn');
 button.disabled=true;
 const previous=bank.hold?.grid?.slice()||Array(16).fill(0);
 const before=snapshot();
 let saved=false;
 try{
  const result=bank.holdTurn();
  if(!result.ok){say(result.message);return}
  // Os prémios e o fundo são registados ANTES da animação visual.
  pendingVisual=holdRecoveryReceipt(result,bank.snapshot);
  saved=await persist(result.done?'hold-paid':'hold-turn',{paid:result.prize||0});
  if(!saved){
   copySnapshot(before);renderHold(null,bank.hold);update();
   say('Tentativa do Hold cancelada: falha a guardar a sessão.');return;
  }
  renderHold(previous,result.hold);
  update();
  const newly=holdLandingIndices(previous,result.hold.grid).length;
  if(newly){
   audio.play('win');
   $('holdInfo').textContent='✨ '+newly+' moeda'+(newly===1?'':'s')+
    ' nova'+(newly===1?'':'s')+' · '+holdProgress(result.hold).attemptsText;
  }else{
   audio.play('cascade');
  }
  if(result.done){
   button.hidden=true;
   if(newly)await wait(turbo?350:640);
   const text=money(result.prize)+' créditos';
   $('holdResult').textContent=result.hold.grid.every(Boolean)?
    '✨ GRELHA COMPLETA · '+text+' ✨':
    '✨ PRÉMIO TOTAL · '+text+' ✨';
   $('holdResult').hidden=false;
   audio.play('bonus');
   await wait(turbo?850:1450);
   $('holdOverlay').hidden=true;
   celebrate('HOLD & WIN',result.prize);
   say('Hold & Win · prémio integral: '+money(result.prize));
   // O próximo giro automático só começa depois de libertar holdBusy.
  }else if(newly){
   // Ver a aterragem das moedas antes da próxima tentativa.
   await wait(turbo?220:490);
  }else{
   await wait(turbo?110:230);
  }
 }catch(err){
  diagnostics.record('hold-visual-error');
  console.error('Erro de animação Hold:',err);
  if(!saved)copySnapshot(before);
  say(saved?'Resultado Hold preservado; efeito visual recuperado.':
   'Não foi possível concluir a tentativa. Saldo anterior reposto.');
  if(bank.mode!=='hold')$('holdOverlay').hidden=true;
  else renderHold(null,bank.hold);
 }finally{
  if(saved&&pendingVisual)await clearVisualReceipt();
  holdBusy=false;
  button.disabled=false;
  if(document.hidden)await releaseIfHidden();
  else if(!writerReady)refreshWriter();
  else if(saved&&bank.mode==='base'&&autoLeft>0)scheduleNext(700);
 }
}


function adminPinForm(next,changePin=false){
 const configured=adminLock.isConfigured();
 modal(changePin?'Alterar PIN local':configured?'PIN de administração DEMO':'Criar PIN de administração DEMO',
  'Proteção local da demonstração. Não é autenticação segura de operador.',[]);
 const area=$('modalContent');
 const form=document.createElement('form');
 form.className='admin-pin-form';
 function addField(caption,id){
  const label=document.createElement('label');label.textContent=caption;
  const input=document.createElement('input');
  input.type='password';input.inputMode='numeric';input.maxLength=6;
  input.minLength=6;input.pattern='[0-9]{6}';input.autocomplete='off';
  input.required=true;input.id=id;
  label.append(input);form.append(label);return input;
 }
 const current=configured?addField('PIN atual','adminPinCurrent'):null;
 const nextPin=(!configured||changePin)?addField(changePin?'Novo PIN':'Escolher PIN de 6 números','adminPinNew'):null;
 const again=nextPin?addField('Confirmar novo PIN','adminPinConfirm'):null;
 const submit=document.createElement('button');submit.type='submit';
 submit.textContent=changePin?'GRAVAR NOVO PIN':configured?'DESBLOQUEAR':'CRIAR PIN';
 const msg=document.createElement('p');msg.className='admin-pin-message';msg.setAttribute('role','status');
 const note=document.createElement('p');note.className='admin-pin-note';
 note.textContent='O PIN fica só neste navegador, não é enviado a servidores e não aparece no backup. Se o perderes, poderá ser necessário limpar os dados locais.';
 form.append(submit,msg,note);area.append(form);
 form.addEventListener('submit',async e=>{
  e.preventDefault();submit.disabled=true;msg.textContent='A validar PIN…';
  try{
   if(nextPin&&nextPin.value!==again.value){msg.textContent='A confirmação não coincide.';return}
   const result=changePin?await adminLock.change(current.value,nextPin.value):
    configured?await adminLock.verify(current.value):await adminLock.create(nextPin.value);
   if(!result.ok){msg.textContent=result.message;return}
   if(changePin)await persist('admin-pin-change');
   closeModal();
   await next();
  }catch(err){console.error('PIN DEMO:',err);msg.textContent=String(err.message||err)}
  finally{submit.disabled=false}
 });
 (current||nextPin).focus();
}
async function requireAdmin(next){
 try{
  if(requireAdminUnlocked())return await next();
  adminPinForm(next);
 }catch(err){modal('PIN local indisponível',String(err.message||err),[])}
}

async function adminModal(){
 if(!writerReady||running||bank.mode!=='base'||!ready)return;
 if(!requireAdminUnlocked())return requireAdmin(adminModal);
 modal('Administração DEMO','Controlo LOCAL de créditos fictícios; não existe autenticação segura.',[]);
 await persist('admin-access');
 if(!modalOpen||$('modalTitle').textContent!=='Administração DEMO')return;
 let history=[];
 try{history=await demoDB.history()}catch(err){console.warn(err)}
 if(!modalOpen||$('modalTitle').textContent!=='Administração DEMO')return;
 const totals=summarizeLedger(history);
 const section=document.createElement('section');
 section.className='admin-panel';
 section.innerHTML=[
  '<div class="admin-stats">',
  '<div class="admin-stat"><small>DONO · DEMO</small><strong id="adminOwner"></strong></div>',
  '<div class="admin-stat"><small>COMERCIANTE · DEMO</small><strong id="adminMerchant"></strong></div>',
  '<div class="admin-stat"><small>RESULTADO OBSERVADO</small><strong id="adminNet"></strong></div>',
  '<div class="admin-stat"><small>COFRE VIRTUAL</small><strong id="adminReserve"></strong></div></div>',
  '<p>Percentagens para movimentos futuros:</p>',
  '<div class="admin-row"><label for="ownerPct">Dono (%)</label><input id="ownerPct" type="number" inputmode="decimal" min="0" max="100" step="0.01"><button id="saveOwner">GRAVAR</button></div>',
  '<div class="admin-row"><label for="merchantPct">Comerciante (%)</label><input id="merchantPct" type="number" inputmode="decimal" min="0" max="100" step="0.01"><button id="saveMerchant">GRAVAR</button></div>',
  '<p class="admin-footnote">Somam sempre 100%. Alterações não modificam os movimentos antigos. Os resultados podem ser negativos.</p>',
  '<p><b>Perfil da máquina (apenas DEMO)</b></p>',
  '<div class="admin-profile"><label for="adminProfile">Modo</label>',
  '<select id="adminProfile"><option value="premios">Prémios</option><option value="normal">Normal</option><option value="entretenimento">Entretenimento</option></select>',
  '<button type="button" id="saveProfile">GRAVAR</button></div>',
  '<p class="admin-footnote">Prémios favorece mais combinações; Entretenimento favorece menos; Normal conserva a geração original. Não equivale a margens de 30%, 50% ou 70%, nem garante RTP.</p>',
  '<p>Adicionar / retirar créditos do cofre:</p>',
  '<div class="admin-fund"><input id="adminFundAmount" type="number" inputmode="decimal" min="0.01" step="0.01" value="100" aria-label="Valor fictício">',
  '<button class="admin-action" id="fundDeposit">＋ FUNDO</button><button class="admin-action" id="fundWithdraw">− FUNDO</button></div>',
  '<p class="admin-message" id="adminMessage" role="status"></p>',
  '<button class="choice" id="adminHistory">☷ HISTÓRICO LOCAL</button>',
  '<button class="choice" id="adminReport">⬇ IMPRIMIR / GUARDAR PDF</button>',
  '<button class="choice" id="adminPinChange">🔐 ALTERAR PIN LOCAL</button>',
  '<button class="choice" id="adminLock">🔒 BLOQUEAR PAINEL</button>',
  '<p class="admin-footnote">No diálogo de impressão escolhe Guardar como PDF. Só simulação, sem dinheiro real.</p>'
 ].join('');
 $('modalContent').append(section);
 const find=id=>section.querySelector('#'+id);
 function refresh(){
  find('adminProfile').value=bank.profile;
  find('ownerPct').value=adminSettings.ownerPct;
  find('merchantPct').value=adminSettings.merchantPct;
  find('adminOwner').textContent=money(totals.owner);
  find('adminMerchant').textContent=money(totals.merchant);
  find('adminNet').textContent=money(totals.net);
  find('adminReserve').textContent=money(bank.reserve);
 }
 let working=false;
 const notice=text=>{find('adminMessage').textContent=text};
 refresh();
 async function saveShare(role){
  if(working)return;
  if(!requireAdminUnlocked()){closeModal();return requireAdmin(adminModal)}
  const value=readPercent(find(role==='owner'?'ownerPct':'merchantPct').value);
  if(value===null){notice('Percentagem inválida: usa de 0 a 100.');return}
  working=true;
  const previous=adminSettings;
  adminSettings=normalizeAdmin(role==='owner'?value:100-value);
  const saved=await persist('admin-share',{note:role+' '+value+'%'});
  if(!saved)adminSettings=previous;
  working=false;refresh();
  notice(saved?'Percentagens guardadas para movimentos futuros.':'Falha a guardar; valores anteriores repostos.');
 }
 find('saveOwner').addEventListener('click',()=>saveShare('owner'));
 find('saveMerchant').addEventListener('click',()=>saveShare('merchant'));
 find('saveProfile').addEventListener('click',async()=>{
  if(working||running||bank.mode!=='base')return;
  if(!requireAdminUnlocked()){closeModal();return requireAdmin(adminModal)}
  const desired=find('adminProfile').value,old=bank.profile;
  if(!bank.setProfile(desired)){notice('Modo inválido.');return}
  working=true;update();
  let saved=false;
  try{saved=await persist('admin-profile',{note:DEMO_PROFILES[desired]})}
  catch(err){console.error('Perfil de demonstração:',err)}
  if(!saved)bank.profile=old;
  working=false;refresh();update();
  notice(saved?'Modo de demonstração guardado.':'Não foi possível gravar o modo anterior.');
 });
 async function changeFund(kind){
  if(working)return;
  if(!requireAdminUnlocked()){closeModal();return requireAdmin(adminModal)}
  const val=validateFundChange(kind,find('adminFundAmount').value,bank.reserve,bank.mode,running);
  if(!val.ok){notice(val.message);return}
  working=true;const before=bank.reserve;
  bank.reserve=val.target;update();
  const saved=await persist(kind==='deposit'?'fund-deposit':'fund-withdraw',{amount:val.amount});
  if(!saved)bank.reserve=before;
  working=false;refresh();update();
  notice(saved?'Movimento fictício guardado.':'Não foi possível guardar; movimento desfeito.');
 }
 find('fundDeposit').addEventListener('click',()=>changeFund('deposit'));
 find('fundWithdraw').addEventListener('click',()=>changeFund('withdraw'));
 find('adminHistory').addEventListener('click',()=>requireAdmin(historyModal));
 find('adminPinChange').addEventListener('click',()=>{
  if(!requireAdminUnlocked()){closeModal();return requireAdmin(adminModal)}
  adminPinForm(adminModal,true);
 });
 find('adminLock').addEventListener('click',()=>{
  adminLock.lock();closeModal();say('Administração bloqueada neste dispositivo.');
 });
 find('adminReport').addEventListener('click',async()=>{
  if(!requireAdminUnlocked()){closeModal();return requireAdmin(adminModal)}
  const popup=window.open('','_blank');
  if(!popup){notice('Autoriza janelas emergentes para o relatório.');return}
  try{
   const current=await demoDB.history();
   popup.document.open();
   popup.document.write(makePrintableReport({
    summary:summarizeLedger(current),history:current,settings:adminSettings,
    reserve:bank.reserve,dbMode:demoDB.mode
   }));
   popup.document.close();popup.focus();popup.print();
  }catch(err){
   popup.close();console.error(err);notice('Falha a preparar o relatório.');
  }
 });
}

async function historyModal(){
 if(!requireAdminUnlocked())return requireAdmin(historyModal);
 let records=[],consistency=null;
 try{
  const view=await demoDB.exportData();
  records=view.history;
  consistency=auditSessionHistory(view.state,records);
 }catch(err){console.error('Leitura do histórico:',err)}
 const actions=[
  {name:'⬇ Exportar cópia JSON',detail:'Estado do jogo e histórico desta instalação (até 2500 movimentos).',
   act:async()=>{
    if(!requireAdminUnlocked())return requireAdmin(historyModal);
    const backup=await demoDB.exportData();
    const file=new Blob([JSON.stringify(backup,null,2)],{type:'application/json'});
    const url=URL.createObjectURL(file);
    const link=document.createElement('a');
    link.href=url;link.download='jardim-phaser-backup-'+new Date().toISOString().slice(0,10)+'.json';
    document.body.append(link);link.click();link.remove();
    setTimeout(()=>URL.revokeObjectURL(url),3000);
   }},
  {name:'↥ Restaurar cópia JSON',detail:'Valida e substitui a sessão e o histórico local. Não mistura dados.',
   disabled:running||holdBusy||bank.mode!=='base',
   act:()=>{
    if(!requireAdminUnlocked())return requireAdmin(historyModal);
    if(running||holdBusy||bank.mode!=='base')return;
    const picker=document.createElement('input');
    picker.type='file';picker.accept='.json,application/json';picker.hidden=true;
    document.body.append(picker);
    picker.addEventListener('change',async()=>{
     try{
      const file=picker.files?.[0];
      if(!file)return;
      if(file.size>5*1024*1024)throw Error('O ficheiro excede 5 MB.');
      const backup=JSON.parse(await file.text());
      validateBackup(backup);
      if(!confirm('Substituir todo o saldo fictício e histórico local pela cópia selecionada? Esta operação não pode ser desfeita.'))return;
      const outcome=await demoDB.restoreData(backup);
      if(!outcome.ok)throw Error(outcome.message||'Erro ao restaurar.');
      location.reload();
     }catch(err){console.error('Restauro:',err);alert('Cópia não restaurada: '+String(err.message||err))}
     finally{picker.remove()}
    },{once:true});
    picker.click();
   }},
  {name:'♻ Reiniciar demonstração',detail:'Apaga esta sessão e o histórico do dispositivo.',
   act:async()=>{
    if(!requireAdminUnlocked())return requireAdmin(historyModal);
    if(!confirm('Apagar todos os créditos fictícios e o histórico desta instalação?'))return;
    const success=await demoDB.clear();
    if(success)location.reload();
    else alert('Não foi possível limpar a base de dados.');
   }}
 ];
 modal('Histórico e cópias locais',
  'Dados desta instalação ('+demoDB.mode+'). Registos: '+records.length+
  '. Este histórico não é um registo financeiro certificado.',actions);
 const host=$('modalContent');
 const controls=document.createElement('div');
 controls.className='history-tools';
 controls.innerHTML=[
  '<label class="history-search">Pesquisar movimentos<input id="historyQuery" type="search" placeholder="Giro, bónus, acesso, valor…" autocomplete="off"></label>',
  '<label>Categoria<select id="historyCategory"><option value="all">Tudo</option><option value="spins">Giros</option><option value="bonus">Bónus</option><option value="fund">Fundo</option><option value="admin">Administração</option></select></label>',
  '<label>Desde<input id="historyFrom" type="date"></label>',
  '<label>Até<input id="historyTo" type="date"></label>'
 ].join('');
 host.append(controls);
 const summary=document.createElement('p');summary.className='history-summary';
 const list=document.createElement('div');list.className='history-list';
 const nav=document.createElement('div');nav.className='history-nav';
 const prev=document.createElement('button');prev.textContent='◀ Anterior';
 const count=document.createElement('span');
 const next=document.createElement('button');next.textContent='Seguinte ▶';
 nav.append(prev,count,next);
 host.append(summary,list,nav);
 let page=0;
 function paint(){
  const found=filterHistory(records,{
   query:controls.querySelector('#historyQuery').value,
   category:controls.querySelector('#historyCategory').value,
   dateFrom:controls.querySelector('#historyFrom').value,
   dateTo:controls.querySelector('#historyTo').value
  });
  const slice=historyPage(found,page);
  page=slice.page;list.replaceChildren();
  for(const event of slice.items){
   const el=document.createElement('div');el.className='history-row';
   const left=document.createElement('div');
   const name=document.createElement('b');name.textContent=event.type||'evento';
   const date=document.createElement('small');
   date.textContent=String(event.timestamp||'').replace('T',' ').slice(0,19);
   const note=document.createElement('small');
   note.textContent=event.note||('Aposta '+money(event.wager||0)+' · compra '+
    money(event.cost||0)+' · fundo '+money(event.amount||0));
   left.append(name,date,note);
   if(event.recoveryId){
    const receipt=document.createElement('small');
    receipt.textContent='Recibo: '+String(event.recoveryId).slice(0,22);
    left.append(receipt);
   }
   const value=document.createElement('strong');
   value.textContent=money(event.paid||0);
   value.title='Prémio fictício do movimento';
   el.append(left,value);list.append(el);
  }
  if(!slice.items.length){
   const empty=document.createElement('p');empty.textContent='Sem movimentos para estes filtros.';
   list.append(empty);
  }
  summary.textContent='A mostrar '+slice.total+' de '+records.length+
   ' registos locais. Prémio à direita. · '+
   (consistency?.label||'Verificação indisponível');
  count.textContent=(slice.page+1)+' / '+slice.pages;
  prev.disabled=slice.page===0;next.disabled=slice.page>=slice.pages-1;
 }
 for(const input of controls.querySelectorAll('input,select'))
  input.addEventListener('input',()=>{page=0;paint()});
 prev.addEventListener('click',()=>{page--;paint()});
 next.addEventListener('click',()=>{page++;paint()});
 paint();
}
function infoModal(){
 modal('Regras do Jardim Mágico Phaser',
 'Demonstração HTML5 em Phaser, com créditos fictícios. Cinco colunas, três linhas e dez linhas de prémio.',[
  {name:'🌟 WILD ×1 a ×5',detail:'Substitui figuras normais; nunca o SCATTER.'},
  {name:'✨ SCATTER',detail:'3 na grelha: 15 Free Spins. Nas Free Spins, 2: +8 ou 3: +15.'},
  {name:'🪙 HOLD & WIN',detail:'Seis moedas ativam a grelha 4×4 com três tentativas.'},
  {name:'🪨 Pedra bloqueadora',detail:'Não atribui prémios; interrompe sequências.'},
  {name:'🩺 Diagnóstico local',detail:'Consulta estado da base e falhas técnicas desta sessão.',act:diagnosticModal},
  {name:'🎁 Experimentar Free Spins',detail:'Ativa apenas um teste visual gratuito.',
   act:async()=>{
    if(!writerReady||bank.mode!=='base'||running)return;
    const before=snapshot();
    bank.addFree(15);
    if(!(await persist('test-free'))){
     copySnapshot(before);update();say('Teste cancelado: não foi possível guardar.');return;
    }
    update();
    bonusAnnouncement('✨ FREE SPINS ✨','15 giros gratuitos de demonstração',1200);
    scheduleNext(1250);
   }},
  {name:'🪙 Experimentar Hold & Win',detail:'Ativa grelha fictícia se o fundo permitir.',
   act:async()=>{
    if(!writerReady||bank.mode!=='base'||running)return;
    const before=snapshot();
    if(bank.startHold()){
     if(!(await persist('test-hold'))){
      copySnapshot(before);update();say('Teste cancelado: falha a guardar.');return;
     }
     update();showHold();
    }else say('O cofre virtual não consegue pagar seis moedas iniciais');
   }}
 ]);
}
function vaultModal(){
 if(!writerReady)return;
 modal('Cofre Mágico',
 'Fundo de demonstração disponível: '+money(bank.reserve)+
 ' créditos fictícios. O motor verifica os resultados antes de os mostrar e não corta prémios atribuídos.',[
  {name:'⚙ Administrar fundo DEMO',detail:'Acesso ao painel com PIN local.',
   act:()=>requireAdmin(adminModal)}
 ]);
}
async function creditDemo(){
 return withMutation(async()=>{
  const before=snapshot();
  bank.creditDemo(25);update();
  if(!(await persist('credit-demo',{amount:25}))){
   copySnapshot(before);update();say('Recarga cancelada: falha na gravação.');return false;
  }
  say('Adicionados 25 créditos fictícios');return true;
 });
}
async function changeBet(dir){
 return withMutation(async()=>{
  if(modalOpen||bank.mode!=='base')return false;
  const before=snapshot();
  const pos=BETS.findIndex(x=>Math.abs(x-bank.stake)<.00001);
  const next=BETS[Math.max(0,Math.min(BETS.length-1,(pos<0?2:pos)+dir))];
  if(!bank.setStake(next)||before.stake===next)return false;
  update();
  if(!(await persist('stake'))){
   copySnapshot(before);update();say('Aposta anterior reposta: falha ao guardar.');return false;
  }
  say('Aposta virtual '+money(bank.stake));return true;
 });
}
function autoModal(){
 if(autoLeft>0){stopAuto();return}
 if(!writerReady||mutating)return;
 if(running||bank.mode!=='base')return;
 modal('Giros automáticos',
 'Escolhe o número de giros de demonstração. Podes parar no botão AUTO.',[
  ...[10,25,50].map(n=>({
   name:n+' giros',detail:'O automático não altera os prémios.',
   act:()=>{autoLeft=n;update();runSpin()}
  }))
 ]);
}
function bind(){
 $('spinBtn').addEventListener('click',()=>{
  if(holdsLong){holdsLong=false;return}
  runSpin();
 });
 $('spinBtn').addEventListener('pointerdown',e=>{
  if(e.button!==0||running||mutating||!writerReady)return;
  clearTimeout(longTimer);
  longTimer=setTimeout(()=>{holdsLong=true;autoModal()},3000);
 });
 $('spinBtn').addEventListener('pointerup',()=>clearTimeout(longTimer));
 for(const event of ['pointerleave','pointercancel'])
  $('spinBtn').addEventListener(event,()=>{clearTimeout(longTimer);holdsLong=false});
 $('betDown').addEventListener('click',()=>changeBet(-1));
 $('betUp').addEventListener('click',()=>changeBet(1));
 $('bonusBtn').addEventListener('click',bonusModal);
 $('vaultBtn').addEventListener('click',vaultModal);
 $('creditBtn').addEventListener('click',creditDemo);
 $('historyBtn').addEventListener('click',()=>requireAdmin(historyModal));
 $('adminBtn').addEventListener('click',()=>requireAdmin(adminModal));
 $('infoBtn').addEventListener('click',infoModal);
 $('autoBtn').addEventListener('click',autoModal);
 $('holdBtn').addEventListener('click',holdTurn);
 $('sessionAction').addEventListener('click',()=>{
  const state=sessionState({ready,writerReady,mutating,saveIssue,paused:freePaused,mode:bank.mode});
  if(state.kind==='paused')resumeFree();
  else if(state.kind==='blocked'||state.kind==='error')retrySession();
 });
 $('closeModal').addEventListener('click',closeModal);
 $('overlay').addEventListener('click',e=>{
  if(e.target===$('overlay'))closeModal();
 });
 $('turboBtn').addEventListener('click',()=>{
  turbo=!turbo;update();say('TURBO '+(turbo?'ligado':'desligado'));
 });
 $('soundBtn').addEventListener('click',()=>{
  const on=audio.toggleEffects();
  $('soundBtn').textContent=on?'◖))':'◖';
  $('soundBtn').setAttribute('aria-pressed',String(on));
 });
 $('musicBtn').addEventListener('click',()=>{
  const on=audio.toggleMusic();
  $('musicBtn').textContent=on?'♫':'♪';
  $('musicBtn').setAttribute('aria-pressed',String(on));
 });
 window.addEventListener('keydown',e=>{
  if(e.key==='Escape'&&modalOpen)closeModal();
 });
 document.addEventListener('visibilitychange',()=>{
  if(document.hidden){
   visualSupervisor.cancel();
   performanceGuard.pause();
   // Let another visible tab take over as soon as this one becomes inactive.
   // A libertação é feita uma única vez por releaseIfHidden(), com
   // uma promessa que o regresso à aplicação aguarda antes de readquirir.
   adminLock.lock();
   if(modalOpen&&['Administração DEMO','Histórico e cópias locais',
    'PIN de administração DEMO','Criar PIN de administração DEMO',
    'Alterar PIN local'].includes($('modalTitle').textContent))closeModal();
   diagnostics.record('page-background');
   clearTimeout(longTimer);holdsLong=false;
   turnQueue.clearAll();
   if(bank.mode==='free')freePaused=true;
   stopAuto(true);
   update();
   releaseIfHidden();
  }
  else{
   performanceGuard.resume();
   refreshWriter();
  }
 });
}

async function refreshWriter(forceReload=false){
 if(document.hidden||writerCheckBusy||(running&&!writerReady)||mutating||holdBusy)return;
 writerCheckBusy=true;
 try{
  if(writerReleasePending)await writerReleasePending;
  if(document.hidden)return;
  let granted=false;
  if(writerReady)granted=await demoDB.renewLease(tabToken);
  else granted=await demoDB.acquireLease(tabToken);
  if(!granted){
   diagnostics.record('writer-denied');
   if(writerReady){writerReady=false;stopAuto();adminLock.lock()}
   writerReady=false;update();
   $('dbStatus').textContent='PHASER · OUTRA ABA ATIVA / SEM INDEXEDDB';
   if(!document.hidden)say('Modo de leitura: outra aba controla esta demonstração. Fecha-a para jogar aqui.');
   return;
  }
  if(!writerReady||forceReload){
   // The previous writer may have changed the balances; never reuse stale state.
   const saved=await demoDB.load();
   if(saved?.snapshot)copySnapshot(saved.snapshot);
   if(saved?.board?.length===5)board=saved.board;
   if(scene?.ready)try{scene.recover(board)}catch(err){console.warn(err)}
   writerReady=true;saveIssue=false;diagnostics.record('writer-granted');
   update();
   $('dbStatus').textContent='PHASER · BASE LOCAL EXCLUSIVA';
   if(recoveredVisual&&pendingVisual)await clearVisualReceipt();
   if(ready){
    const displayed=showRecoveryState(recoveredVisual?saved?.snapshot?.pendingVisual:null);
    if(!displayed&&bank.mode==='free'&&!document.hidden&&!freePaused)scheduleNext(1100);
    else if(!displayed&&bank.mode==='hold')showHold();
   }else say('Sessão exclusiva obtida. Saldo sincronizado com a base local.');
  }else if(ready&&!document.hidden&&!running&&!modalOpen&&bank.mode==='free'&&
    !freePaused&&!turnQueue.pending('free'))scheduleNext(650);
 }catch(err){
  writerReady=false;saveIssue=true;diagnostics.record('storage-error');console.error('Aba ativa:',err);
  $('dbStatus').textContent='PHASER · SESSÃO BLOQUEADA';
 }finally{writerCheckBusy=false;update()}
}

async function boot(){
 bind();update();
 if(typeof Phaser==='undefined'){
  $('loadError').hidden=false;
  status('Não foi possível carregar Phaser');return;
 }
 try{
  const mode=await demoDB.connect();
  // Atomic writer lease in IndexedDB; localStorage fallback stays read-only.
  const exclusive=await demoDB.acquireLease(tabToken);
  writerReady=exclusive;
  diagnostics.record(exclusive?'writer-granted':'writer-denied');
  const saved=await demoDB.load();
  if(saved?.snapshot)copySnapshot(saved.snapshot);
  if(Array.isArray(saved?.board)&&saved.board.length===5)board=saved.board;
  $('dbStatus').textContent=writerReady?'PHASER · BASE LOCAL EXCLUSIVA':'PHASER · SÓ LEITURA';
 }catch(err){
  console.error('Base local:',err);
  $('dbStatus').textContent='PHASER · SEM BASE LOCAL';
 }
 game=createReelGame();
 if(!game){status('Phaser indisponível');return}
 const available=async()=>{
  scene=window.jardimPhaserScene;
  if(!scene||!scene.ready)return;
  scene.setBoard(board);
  ready=true;update();
  status('TAMBORES VERTICAIS PRONTOS');
  if(!writerReady){
   say('Modo de leitura: outra aba usa o jogo ou não existe IndexedDB.');
   return;
  }
  const savedMarker=pendingVisual;
  if(savedMarker)await clearVisualReceipt();
  if(!showRecoveryState(savedMarker)){
   say('Jardim Mágico Phaser · símbolos originais HD');
   if(bank.mode==='hold')showHold();
   else if(bank.mode==='free')scheduleNext(1100);
  }
 };
 if(window.jardimPhaserScene?.ready)available();
 else window.addEventListener('jardim-phaser-ready',available,{once:true});
 writerTimer=setInterval(refreshWriter,5000);
 window.addEventListener('pagehide',()=>{
  visualSupervisor.cancel();
  turnQueue.clearAll();
  clearInterval(writerTimer);
  releaseIfHidden();
 });
 setTimeout(()=>{
  if(!ready){
   $('loadError').hidden=false;
   say('O motor demorou demasiado a abrir. Verifica a ligação.');
  }
 },11000);
}
boot();
