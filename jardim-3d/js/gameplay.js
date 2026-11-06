/* Jardim Mágico 3D V2 — Controlador da demonstração visual.
   Toda a aposta, carteira e prémio neste módulo são valores FICTÍCIOS.
   O engine demonstrativo é separado do renderizador PlayCanvas. */
import {DemoBank,SYMBOLS,LABELS,PAYLINES,money} from './demo-engine.js?v=3.1';
import {demoDB} from './database-v3.js?v=3.1';
import {audio} from './audio-v3.js?v=3.1';

const $=id=>document.getElementById(id);
const bank=new DemoBank();
const BETS=[.05,.10,.20,.40,.60,.80,1,1.5,2,3,5,7.5,10];
let autoRemaining=0;
let activeDialog=false;
let spinBusy=false;
let holdConsumed=false;
let holdTimer=null;
let nextTimer=null;
let winTimer=null;
let gameReady=false;
let lastBoard=null;
let saveFailed=false;
window.JM3DGameController=true;
function saveDemo(type,details={}){
 const eventId=(typeof crypto!=='undefined'&&crypto.randomUUID)?
  crypto.randomUUID():Date.now()+'-'+Math.random().toString(36).slice(2);
 return demoDB.save(bank.snapshot,details.board||lastBoard,{
  id:eventId,type,stake:bank.stake,wager:details.wager||0,
  cost:details.cost||0,paid:details.paid||0,
  stages:details.stages||0
 }).then(ok=>{
  if(!ok){
   saveFailed=true;
   $('dbStatus').textContent='BASE LOCAL: ERRO';
  }
  return ok;
 }).catch(err=>{
  console.error('Base de dados:',err);
  saveFailed=true;$('dbStatus').textContent='BASE LOCAL: ERRO';
  return false;
 });
}
function restoreBank(data){
 const snapshot=data?.snapshot;
 if(!snapshot||typeof snapshot!=='object')return false;
 const validNum=['balance','reserve','stake','spins','lastWin','freeSpins',
   'freeTotal','freeMultiplier','freePlayed','freeAwarded'];
 for(const key of validNum){
  const value=Number(snapshot[key]);
  if(!Number.isFinite(value)||value<0)continue;
  bank[key]=value;
 }
 if(['base','free','hold'].includes(snapshot.mode))bank.mode=snapshot.mode;
 if(bank.mode==='hold'&&Array.isArray(snapshot.hold?.grid)&&
    snapshot.hold.grid.length===16){
  bank.hold=JSON.parse(JSON.stringify(snapshot.hold));
 }else if(bank.mode==='hold'){
  bank.mode='base';bank.hold=null;
 }
 bank.freeSpins=Math.min(60,bank.freeSpins);
 bank.freeMultiplier=Math.min(5,Math.max(1,bank.freeMultiplier));
 if(typeof snapshot.status==='string')bank.status=snapshot.status.slice(0,200);
 if(Array.isArray(data.board)&&data.board.length===5)lastBoard=data.board;
 return true;
}

const button=$('spin'),banner=$('bonusBanner'),msg=$('result');
function say(text){
 msg.textContent=text;
 $('notice').textContent=text;
}
function setSpinning(yes){
 spinBusy=yes;
 button.disabled=yes;
 $('betMinus').disabled=yes||bank.mode!=='base';
 $('betPlus').disabled=yes||bank.mode!=='base';
 $('buyBonus').disabled=yes||bank.mode!=='base';
 $('autoBtn').classList.toggle('running',autoRemaining>0);
}
function render(){
 const s=bank.snapshot;
 $('balance').textContent=money(s.balance);
 $('stake').textContent=money(s.stake);
 $('lastWin').textContent=money(s.lastWin);
 $('vaultAmount').textContent=money(s.reserve);
 $('spins').textContent=String(s.spins);
 $('jpMin').textContent=money(Math.min(s.reserve,s.stake*250));
 $('jpMed').textContent=money(Math.min(s.reserve,s.stake*500));
 $('jpMax').textContent=money(Math.min(s.reserve,s.stake*1000));
 $('jpUltra').textContent=money(Math.min(s.reserve,s.stake*5000));
 const canChange=s.mode==='base'&&!spinBusy;
 $('betMinus').disabled=!canChange||BETS.indexOf(s.stake)===0;
 $('betPlus').disabled=!canChange||BETS.indexOf(s.stake)===BETS.length-1;
 $('buyBonus').disabled=!canChange;
 $('freeMeter').hidden=s.mode!=='free';
 $('freeCount').textContent=String(s.freeSpins);
 $('freeTotal').hidden=s.mode!=='free';
 if(s.mode==='free')$('freeTotal').textContent='ACUMULADO '+money(s.freeTotal)+' ×'+s.freeMultiplier;
 $('multiTotal').textContent='×'+(s.mode==='free'?s.freeMultiplier:1);
 $('autoCount').textContent=autoRemaining?String(autoRemaining):'∞';
 $('autoBtn').classList.toggle('running',autoRemaining>0);
}
function showMultiply(mult,wilds){
 const els=[...document.querySelectorAll('#multiOrbs b')];
 for(let i=0;i<els.length;i++){
  els[i].textContent=wilds&&i<Math.min(3,wilds)?'×'+mult:'×1';
 }
}
function bigWin(text){
 audio.play('win');
 const el=$('bigWin');
 clearTimeout(winTimer);
 el.textContent=text;el.classList.add('visible');
 winTimer=setTimeout(()=>el.classList.remove('visible'),1600);
}
function freeBanner(text){
 audio.play('bonus');
 banner.textContent=text;banner.hidden=false;
 setTimeout(()=>{banner.hidden=true},1150);
}
function closeModal(){
 activeDialog=false;$('dialogShade').hidden=true;
 $('dialogBody').replaceChildren();
 // Se o jogador abriu a ajuda durante as Free Spins, continuar ao fechar.
 if(bank.mode==='free'&&!spinBusy){
  clearTimeout(nextTimer);nextTimer=setTimeout(runSpin,300);
 }
}
function dialog(title,content){
 activeDialog=true;autoRemaining=0;render();
 $('dialogTitle').textContent=title;
 const b=$('dialogBody');b.replaceChildren();
 if(typeof content==='string')b.innerHTML=content;
 else b.append(content);
 $('dialogShade').hidden=false;
 $('dialogClose').focus();
}
function choice(label,detail,fn,disabled=false){
 const b=document.createElement('button');
 b.type='button';b.className='choice';b.disabled=disabled;
 const strong=document.createElement('strong');strong.textContent=label;
 const small=document.createElement('small');small.textContent=detail;
 b.append(strong,small);b.addEventListener('click',()=>{closeModal();fn()});
 return b;
}
function makeOptions(title,paragraph,buttons){
 const wrap=document.createElement('div');
 const p=document.createElement('p');p.textContent=paragraph;wrap.appendChild(p);
 for(const args of buttons)wrap.appendChild(choice(...args));
 dialog(title,wrap);
}
function bonusModal(){
 if(spinBusy||bank.mode!=='base')return;
 makeOptions('Comprar bónus DEMO',
  'Bónus voluntários com créditos fictícios. Não existem pagamentos ou levantamento.',
  [
   ['✨ 15 Free Spins','Custo fictício: '+money(bank.bonusPrice)+' créditos',()=>{
     const r=bank.buyFree();render();say(r.message);if(r.ok){saveDemo('buy-free',{cost:bank.bonusPrice});freeBanner('✨ 15 FREE SPINS ✨');nextTimer=setTimeout(runSpin,1150)}
   },bank.balance<bank.bonusPrice],
   ['🪙 Hold & Win 4×4','Custo fictício: '+money(bank.holdPrice)+' créditos',()=>{
     const r=bank.buyHold();render();say(r.message);if(r.ok){saveDemo('buy-hold',{cost:bank.holdPrice});showHold()}
   },bank.balance<bank.holdPrice]
  ]);
}
function payModal(){
 const b=document.createElement('div');
 const p=document.createElement('p');
 p.textContent='Jardim Mágico 3D — 10 linhas demonstrativas, 5 tambores, 3 linhas, prémios com 3 ou mais símbolos na linha. O WILD substitui símbolos normais, não o SCATTER nem moedas.';
 b.appendChild(p);
 const table=document.createElement('table');table.className='payList';
 for(const [icon,label,val] of [
  ['🌟','WILD','Multiplicador ilustrativo ×1–×5'],
  ['✨','SCATTER','3 em tela: 15 Free Spins; em Free Spins, 2: +8 e 3: +15'],
  ['🪙','MOEDA','6 em tela: Hold & Win 4×4'],
  ['🪨','PEDRA','Sem valor; bloqueia sequências'],
  ['🎁','FREE SPINS','Acumulação fictícia; prémio ao terminar'],
  ['🏆','ULTRA','Referência até 5000×, limitada ao cofre demo']
 ]){
  const tr=document.createElement('tr');
  for(const str of [icon+' '+label,val]){const td=document.createElement('td');td.textContent=str;tr.append(td)}
  table.append(tr);
 }
 b.append(table);
 const note=document.createElement('p');note.className='muted';
 note.textContent='AVISO: matemática e frequências desta demonstração não são o RTP nem o sistema de pagamentos do jogo original.';
 b.append(note);
 b.append(choice('✨ Experimentar Free Spins','Apenas para testar o visual 3D.',()=>{
  bank.addFree(15);render();saveDemo('test-free');freeBanner('✨ FREE SPINS DEMO ✨');nextTimer=setTimeout(runSpin,1200)
 },bank.mode!=='base'||spinBusy));
 b.append(choice('🪙 Experimentar Hold & Win','Grelha interativa 4×4 com moedas fictícias.',()=>{
  if(bank.startHold()){render();saveDemo('test-hold');showHold()}
  else{render();say('Cofre virtual insuficiente para as moedas iniciais')}
 },bank.mode!=='base'||spinBusy));
 dialog('Regras da demonstração',b);
}
function vaultModal(){
 makeOptions('Cofre Mágico',
 'Fundo virtual disponível para pagar prémios simulados: '+money(bank.reserve)+' créditos. Este valor não representa dinheiro depositado ou disponível para levantamento.',
 [
 ['🪙 Testar Hold & Win','Ativar uma demonstração sem compra real.',()=>{
  if(bank.startHold()){render();saveDemo('test-hold');showHold()}
  else{render();say('Cofre virtual insuficiente para as moedas iniciais')}
 },bank.mode!=='base'||spinBusy],
 ['＋ Adicionar créditos DEMO','Acrescenta 25 créditos fictícios ao saldo.',()=>{
  bank.creditDemo(25);render();saveDemo('credit-demo');say('Foram adicionados 25 créditos fictícios')
 }]
 ]);
}
function autoModal(){
 if(autoRemaining){autoRemaining=0;render();say('Giros automáticos parados');return}
 if(bank.mode!=='base'||spinBusy)return;
 makeOptions('Giros automáticos','Escolhe quantos giros demonstrativos queres executar.',
 [10,25,50].map(n=>[
  n+' GIROS AUTOMÁTICOS','Podes parar voltando a carregar no AUTO.',()=>{
   autoRemaining=n;render();say('Automático: '+n+' giros');runSpin();
  }
 ]));
}
function showHold(){
 if(bank.mode!=='hold')return;
 const overlay=$('holdScreen');overlay.hidden=false;
 updateHold();
}
function updateHold(){
 const h=bank.hold;if(!h)return;
 const board=$('holdBoard');board.replaceChildren();
 for(const value of h.grid){
  const d=document.createElement('div');
  d.className=value?'filled':'';
  d.textContent=value?value+'×':'✦';
  board.append(d);
 }
 $('holdTries').textContent=h.tries+' tentativas · '+money(h.total)+' créditos fictícios';
}
function holdStep(){
 const r=bank.holdTurn();
 if(!r.ok)return;
 saveDemo(r.done?'hold-paid':'hold-turn',{paid:r.prize||0});
 if(r.done){
  $('holdScreen').hidden=true;
  render();say('Hold & Win DEMO: '+money(r.prize)+' créditos');
  if(r.prize>0)bigWin('✨ '+money(r.prize)+' ✨');
  if(autoRemaining>0)nextTimer=setTimeout(runSpin,700);
 }else updateHold();
}
function winningCoordinates(lineWins){
 // A mesma peça pode pertencer a duas combinações e é animada uma só vez.
 const unique=new Map();
 for(const win of lineWins||[]){
  for(const cell of win.cells||[]){
   unique.set(cell.c+','+cell.r,{c:cell.c,r:cell.r});
  }
 }
 return [...unique.values()];
}
function showLineBreakdown(lineWins,total,capped=null,multiplier=1){
 const target=$('winBreakdown');
 if(!target)return;
 if(!lineWins?.length){target.hidden=true;target.textContent='';return}
 const individual=lineWins.map(win=>money(win.amount));
 // Muitas combinações não devem cobrir os rolos nem os botões.
 // O valor integral continua contabilizado e apresentado.
 const terms=individual.length<=6?individual.join(' + '):
   individual.slice(0,4).join(' + ')+' + … ('+(individual.length-4)+' restantes)';
 const multiplied=multiplier>1?' ×'+multiplier+' = '+money(total*multiplier):'';
 const cap=capped!==null?' · PAGO '+money(capped):'';
 target.textContent=lineWins.length+' COMBINAÇÕES · '+
   terms+' = '+money(total)+multiplied+cap;
 target.hidden=false;
}
/* V2.3 — a animação percorre todas as quedas calculadas pelo motor.
   A banca já reservou e limitou o prémio antes dos movimentos: não se
   pode obter créditos extra ao interromper ou acelerar a animação. */
/* V3: reproduzir as etapas com proteção contra callbacks perdidos.
   A banca decide resultados ANTES do visual. Se a renderização falhar,
   mostra-se a grelha já calculada e o estado nunca fica bloqueado. */
const framePause=ms=>new Promise(resolve=>setTimeout(resolve,ms));
async function playMotion(start,board,timeoutMs=4500){
 return new Promise(resolve=>{
  let complete=false;
  let timer=null;
  const settle=(reason)=>{
   if(complete)return;
   complete=true;clearTimeout(timer);
   window.removeEventListener('jm3d-motion-error',onError);
   if(reason!=='ok'){
    try{window.JM3D?.recover?.(board)}catch(err){console.error(err)}
   }
   resolve(reason);
  };
  const onError=()=>settle('render-error');
  window.addEventListener('jm3d-motion-error',onError);
  timer=setTimeout(()=>settle('timeout'),timeoutMs);
  try{
   if(start(()=>settle('ok'))!==true)settle('not-started');
  }catch(err){
   console.error('Animação recuperada:',err);
   settle('exception');
  }
 });
}
async function runSpin(){
 clearTimeout(nextTimer);
 if(spinBusy||activeDialog||bank.mode==='hold'){
  if(bank.mode==='hold')showHold();
  return;
 }
 if(!gameReady){say('A carregar base de dados local…');return}
 if(!window.JM3D){say('Motor 3D a iniciar…');return}
 if(!window.JM3D.assetsReady?.()){
  say('A carregar símbolos HD originais…');
  if(bank.mode==='free')nextTimer=setTimeout(runSpin,550);
  return;
 }
 let outcome;
 const beforeSpin=bank.snapshot;
 try{outcome=bank.spin()}
 catch(err){
  restoreBank({snapshot:beforeSpin,board:lastBoard});
  console.error('Falha de cálculo:',err);
  render();say('Erro no cálculo; saldo e fundo preservados.');
  return;
 }
 if(!outcome.ok){autoRemaining=0;render();say(outcome.message);return}
 audio.play('spin');
 const stages=outcome.stages||[{board:outcome.board,ev:outcome.ev,removed:[]}];
 const finalBoard=stages.at(-1).board;
 let recovered=false;
 setSpinning(true);render();
 lastBoard=finalBoard;
 showLineBreakdown([],0);
 $('spinState').textContent='TAMBORES A DESCER ↓';
 say(outcome.free?'FREE SPIN · '+bank.freeSpins+' restantes':
  'Tambores a rodar de cima para baixo');
 if(!outcome.free&&autoRemaining>0)autoRemaining--;
 try{
  // Persistir o prémio integral e o resultado final ANTES de o animar.
  // Assim, recarregar a página durante uma queda não perde nem duplica créditos.
  await saveDemo('spin',{board:finalBoard,paid:outcome.credited,stages:stages.length,wager:outcome.free?0:bank.stake});
  if((await playMotion(done=>window.JM3D.spin(outcome.board,done),
    outcome.board,6500))!=='ok')recovered=true;
  for(let i=0;i<stages.length;i++){
   const stage=stages[i];
   const wins=stage.ev.lineWins||[];
   const cells=winningCoordinates(wins);
   const hasDrop=i<stages.length-1;
   const factor=stage.ev.wilds?outcome.wildMultiplier:1;
   const duration=wins.length?(wins.length>=2?1080:850):220;
   if(wins.length){
    // Estouro coletivo das peças premiadas, mesmo numa única linha
    // quando o giro tem queda seguinte.
    try{
     if(hasDrop||wins.length>=2)
      window.JM3D.burstWinners?.(cells,duration);
     else window.JM3D.highlight?.(cells,duration);
    }catch(err){console.error('Efeito de vitória:',err);recovered=true}
    const value=Math.round(stage.ev.raw*factor*100)/100;
    $('spinState').textContent=wins.length+
     ' COMBINAÇ'+(wins.length===1?'ÃO':'ÕES')+
     ' PREMIADA'+(wins.length===1?'':'S')+
     ' · QUEDA '+(i+1);
    showLineBreakdown(wins,stage.ev.raw,null,factor);
    showMultiply(outcome.wildMultiplier,stage.ev.wilds);
    say(wins.length+' combinação'+(wins.length===1?'':'ões')+
     ' · queda '+(i+1)+' · '+money(value)+' créditos');
    if(wins.length>=2)bigWin('✨ +'+money(value)+' ✨');
   }else{
    $('spinState').textContent='QUEDA '+(i+1)+' · SEM NOVAS LINHAS';
    say('Sem novas combinações — giro concluído');
   }
   await framePause(duration+70);
   if(hasDrop){
    audio.play('cascade');
    const next=stages[i+1];
    $('spinState').textContent='↓ PEÇAS A CAIR ↓';
    showLineBreakdown([],0);
    if((await playMotion(done=>window.JM3D.cascade(next.board,next.removed,done),
      next.board,4000))!=='ok')recovered=true;
   }
  }
 }catch(err){console.error('Giro recuperado:',err);recovered=true}
 finally{
  // Este caminho executa sempre, mesmo quando um frame/callback falhou.
  try{window.JM3D?.recover?.(finalBoard)}catch(err){console.error(err)}
  setSpinning(false);
  render();
  const allWins=stages.flatMap(stage=>stage.ev.lineWins||[]);
  if(allWins.length){
   showLineBreakdown(allWins,outcome.rawTotal,
    !outcome.free&&outcome.credited<outcome.potential?outcome.credited:null);
   if(!outcome.free&&outcome.potential!==outcome.rawTotal){
    const summary=$('winBreakdown');
    summary.textContent+=' · COM WILD '+money(outcome.potential);
   }
   $('spinState').textContent=allWins.length+' COMBINAÇÕES SOMADAS · '+
    (stages.length-1)+' QUEDAS';
   if(!outcome.free)bigWin('✨ TOTAL '+money(outcome.credited)+' ✨');
  }else{
   showLineBreakdown([],0);
   $('spinState').textContent='TAMBORES 3D PRONTOS';
  }
  if(recovered){
   say('Movimento 3D recuperado — resultado e prémio preservados');
  }else if(outcome.triggered){
   say(outcome.triggered);freeBanner('✨ '+outcome.triggered+' ✨');
  }else if(outcome.free){
   say('Valor acumulado: '+money(bank.freeTotal)+' ×'+bank.freeMultiplier);
  }else if(allWins.length){
   say(allWins.length+' combinações · prémio total: '+
     money(outcome.credited)+' créditos');
  }else say('Sem prémio neste giro');
  if(bank.mode==='hold')nextTimer=setTimeout(showHold,950);
  else if(bank.mode==='free')nextTimer=setTimeout(runSpin,1100);
  else if(autoRemaining>0)nextTimer=setTimeout(runSpin,780);
 }
}
function changeStake(delta){
 const idx=BETS.findIndex(v=>Math.abs(v-bank.stake)<1e-5);
 const next=BETS[Math.max(0,Math.min(BETS.length-1,(idx<0?2:idx)+delta))];
 if(bank.setStake(next)){render();saveDemo('stake');say('Aposta virtual: '+money(next))}
}
function charge(){
 bank.creditDemo(25);render();saveDemo('credit-demo');say('＋25 créditos fictícios adicionados');
}
function databaseModal(){
 const wrap=document.createElement('div');
 const p=document.createElement('p');
 p.textContent='Base de dados '+(demoDB.mode==='indexedDB'?'IndexedDB':'localStorage')+
  ' no dispositivo. São créditos fictícios; não é uma base online e não sincroniza entre dispositivos.';
 wrap.appendChild(p);
 const report=document.createElement('p');
 report.textContent='A carregar histórico…';wrap.appendChild(report);
 demoDB.history().then(rows=>{
  const spins=rows.filter(x=>x.type==='spin');
  const wagered=rows.reduce((sum,x)=>sum+Number(x.wager||0)+Number(x.cost||0),0);
  const awarded=rows.reduce((sum,x)=>sum+Number(x.paid||0),0);
  const observed=wagered>0?(awarded/wagered*100).toFixed(1)+'%':'—';
  report.textContent='Giros registados: '+spins.length+
   ' · apostas e compras demo: '+money(wagered)+
   ' · prémios fictícios: '+money(awarded)+
   ' · retorno observado: '+observed+' (não auditado)'+
   ' · saldo: '+money(bank.balance)+' · fundo: '+money(bank.reserve);
 }).catch(()=>{report.textContent='Histórico indisponível'});
 wrap.append(choice('⬇ Exportar histórico e saldo','Guardar cópia JSON neste dispositivo.',async()=>{
  try{
   const data=await demoDB.exportData();
   const blob=new Blob([JSON.stringify(data,null,2)],{type:'application/json'});
   const url=URL.createObjectURL(blob);
   const a=document.createElement('a');a.href=url;
   a.download='jardim-magico-demo-'+new Date().toISOString().slice(0,10)+'.json';
   document.body.append(a);a.click();a.remove();
   setTimeout(()=>URL.revokeObjectURL(url),2000);
  }catch(err){say('Erro ao exportar os dados')}
 }));
 wrap.append(choice('＋ Acrescentar 500 ao cofre','Fundo 100% fictício desta sessão.',()=>{
  bank.reserve=Math.round((bank.reserve+500)*100)/100;
  render();saveDemo('admin-fund-plus');say('Cofre demo aumentado em 500 créditos');
 }));
 wrap.append(choice('− Retirar 100 do cofre','Apenas com jogo base parado e fundo suficiente.',()=>{
  if(spinBusy||bank.mode!=='base'){
   say('Não é possível reduzir o cofre durante um bónus ou um giro');return;
  }
  if(bank.reserve<100){say('Cofre insuficiente');return}
  bank.reserve=Math.round((bank.reserve-100)*100)/100;
  render();saveDemo('admin-fund-minus');say('Cofre demo reduzido em 100 créditos');
 },spinBusy||bank.mode!=='base'||bank.reserve<100));
 wrap.append(choice('♻ Reiniciar demonstração','Apaga o saldo e histórico locais.',async()=>{
  if(!confirm('Apagar o saldo fictício e histórico deste dispositivo?'))return;
  autoRemaining=0;clearTimeout(nextTimer);await demoDB.clear();location.reload();
 }));
 dialog('Dados e histórico local',wrap);
}
function bind(){
 $('betMinus').addEventListener('click',()=>changeStake(-1));
 $('betPlus').addEventListener('click',()=>changeStake(1));
 $('btnDemoCredits').addEventListener('click',charge);
 $('btnInfo').addEventListener('click',payModal);
 $('btnDb').addEventListener('click',databaseModal);
 $('btnSound').addEventListener('click',()=>{
  const on=audio.toggleEffects();
  $('btnSound').setAttribute('aria-pressed',String(on));
  $('btnSound').textContent=on?'🔊':'🔇';
 });
 $('btnMusic').addEventListener('click',()=>{
  const on=audio.toggleMusic();
  $('btnMusic').setAttribute('aria-pressed',String(on));
  $('btnMusic').textContent=on?'♫':'♪';
 });
 $('buyBonus').addEventListener('click',bonusModal);
 $('vaultBtn').addEventListener('click',vaultModal);
 $('autoBtn').addEventListener('click',autoModal);
 $('holdSpin').addEventListener('click',holdStep);
 $('dialogClose').addEventListener('click',closeModal);
 $('dialogShade').addEventListener('click',e=>{if(e.target===$('dialogShade'))closeModal()});
 window.addEventListener('keydown',e=>{if(e.key==='Escape'&&activeDialog)closeModal()});
 button.addEventListener('click',()=>{if(holdConsumed){holdConsumed=false;return}runSpin()});
 // Long press de 3 segundos para abrir seleção de spins, como no jogo original.
 button.addEventListener('pointerdown',e=>{
  if(e.button!==0||spinBusy)return;
  clearTimeout(holdTimer);
  holdTimer=setTimeout(()=>{holdConsumed=true;autoModal()},3000);
 });
 for(const type of ['pointerup','pointercancel','pointerleave']){
  button.addEventListener(type,()=>clearTimeout(holdTimer))
 }
 document.addEventListener('visibilitychange',()=>{
  if(document.hidden){
   autoRemaining=0;clearTimeout(nextTimer);render();
  }else if(gameReady&&!spinBusy&&bank.mode==='free'){
   // iPhone/Safari suspende temporizadores ao sair da aplicação.
   // Recomeçar as Free Spins guardadas quando a página volta a aparecer.
   nextTimer=setTimeout(runSpin,400);
  }
 });
}
render();bind();
async function launch(){
 const label=$('dbStatus');
 try{
  const mode=await demoDB.connect();
  const existing=await demoDB.load();
  if(existing)restoreBank(existing);
  label.textContent=mode==='indexedDB'?'DB LOCAL ✓':'GUARDADO LOCALMENTE';
  gameReady=true;render();
  if(lastBoard){
   if(window.JM3D)window.JM3D.showBoard?.(lastBoard);
   else window.addEventListener('jm3d-ready',
     ()=>window.JM3D?.showBoard?.(lastBoard),{once:true});
  }
  if(bank.mode==='hold')showHold();
  else if(bank.mode==='free')nextTimer=setTimeout(runSpin,1200);
  say('Jardim Mágico pronto · progresso recuperável');
 }catch(err){
  console.error('Erro ao abrir base local:',err);
  label.textContent='DB INDISPONÍVEL';
  gameReady=true;render();say('A jogar sem base de dados persistente');
 }
}
launch();
