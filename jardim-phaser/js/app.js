/* Jardim Mágico Phaser V1 — orquestração, UI e créditos DEMO.
   Sem pagamentos reais, sem RTP auditado e sem sincronização entre devices.
   Cada resultado e prémio são decididos ANTES da animação vertical Phaser.
 */
import {DemoBank,newBoard,winningCells,money} from './engine.js?v=1';
import {demoDB} from './database.js?v=1';
import {audio} from './audio.js?v=1';
import {createReelGame} from './reels.js?v=1.1';
const $=id=>document.getElementById(id);
const BETS=[.05,.1,.2,.4,.6,.8,1,1.5,2,3,5,7.5,10];
const bank=new DemoBank();
let scene=null,game=null;
let board=newBoard(),running=false,ready=false,turbo=false;
let autoLeft=0,autoTimer=null,freeTimer=null;
let bigTimer=null,winTimer=null;
let modalOpen=false,holdsLong=false,longTimer=null;
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
 if(['base','free','hold'].includes(snapshot.mode))bank.mode=snapshot.mode;
 if(typeof snapshot.status==='string')bank.status=snapshot.status.slice(0,180);
 if(bank.mode==='hold'&&Array.isArray(snapshot.hold?.grid)&&snapshot.hold.grid.length===16)
  bank.hold=structuredClone(snapshot.hold);
 else if(bank.mode==='hold'){bank.mode='base';bank.hold=null}
 bank.freeMultiplier=Math.max(1,Math.min(5,bank.freeMultiplier));
 bank.freeSpins=Math.max(0,Math.min(60,bank.freeSpins));
}
function snapshot(){
 return bank.snapshot;
}
function update(){
 const s=snapshot();
 $('balance').textContent=money(s.balance);
 $('reserve').textContent=money(s.reserve);
 $('stake').textContent=money(s.stake);
 $('lastWin').textContent=money(s.lastWin);
 $('jpMin').textContent=money(Math.min(s.reserve,s.stake*250));
 $('jpMed').textContent=money(Math.min(s.reserve,s.stake*500));
 $('jpMax').textContent=money(Math.min(s.reserve,s.stake*1000));
 $('jpUltra').textContent=money(Math.min(s.reserve,s.stake*5000));
 $('multiTotal').textContent='×'+(s.mode==='free'?s.freeMultiplier:1);
 $('freeMeter').hidden=s.mode!=='free';
 $('freeCount').textContent=s.freeSpins;
 $('autoRemaining').textContent=autoLeft?String(autoLeft):'∞';
 $('turboState').textContent=turbo?'ON':'OFF';
 $('turboBtn').setAttribute('aria-pressed',String(turbo));
 const current=BETS.findIndex(v=>Math.abs(v-s.stake)<1e-6);
 const blocked=running||s.mode!=='base'||!ready;
 $('betDown').disabled=blocked||current<=0;
 $('betUp').disabled=blocked||current>=BETS.length-1;
 $('bonusBtn').disabled=blocked;
 $('spinBtn').disabled=running||!ready||s.mode==='hold'||s.mode==='free';
 $('autoBtn').disabled=(!ready||s.mode==='hold'||(running&&autoLeft===0))&&autoLeft===0;
 $('autoBtn').classList.toggle('active',autoLeft>0);
}
function orbs(mult=1,count=0){
 const orb=$('orbs');orb.replaceChildren();
 for(let i=0;i<Math.min(3,Math.max(2,count));i++){
  const el=document.createElement('b');
  el.textContent=i<count?'×'+mult:'×1';
  orb.appendChild(el);
 }
}
function popup(text){
 const el=$('bigWin');el.textContent=text;
 el.classList.remove('active');void el.offsetWidth;el.classList.add('active');
 clearTimeout(bigTimer);bigTimer=setTimeout(()=>el.classList.remove('active'),1400);
}
function showWinText(wins,value,total=false){
 const notice=$('winNotice');
 if(!wins.length){notice.hidden=true;return}
 let parts=wins.map(w=>money(w.amount));
 if(parts.length>5)parts=[...parts.slice(0,3),'… ('+(wins.length-3)+' outras)'];
 notice.textContent=(total?'TOTAL · ':'')+
  wins.length+' combinaç'+(wins.length===1?'ão':'ões')+
  ' · '+parts.join(' + ')+' = '+money(value);
 notice.hidden=false;
 clearTimeout(winTimer);
 if(total)winTimer=setTimeout(()=>notice.hidden=true,2300);
}
async function persist(type,details={}){
 const ok=await demoDB.save(snapshot(),details.board||board,{
  type,id:(typeof crypto!=='undefined'&&crypto.randomUUID)?
   crypto.randomUUID():'event-'+Date.now()+'-'+Math.random().toString(36).slice(2),
  stake:bank.stake,paid:details.paid||0,wager:details.wager||0,
  cost:details.cost||0,stages:details.stages||0
 });
 if(!ok)$('dbStatus').textContent='PHASER · ERRO A GUARDAR';
 return ok;
}
function stopAuto(){
 autoLeft=0;clearTimeout(autoTimer);autoTimer=null;
 update();say('Giros automáticos parados');
}
async function visual(promiseFactory,expected,deadline=7000){
 let timeout;
 try{
  const done=await Promise.race([
   Promise.resolve().then(promiseFactory).then(()=>true),
   new Promise(resolve=>{timeout=setTimeout(()=>resolve(false),deadline)})
  ]);
  if(!done){
   console.warn('Recuperação de animação Phaser: timeout');
   scene?.recover?.(expected);
  }
  return done;
 }catch(err){
  console.error('Recuperação de animação Phaser:',err);
  try{scene?.recover?.(expected)}catch{}
  return false;
 }finally{clearTimeout(timeout)}
}
async function runSpin(){
 clearTimeout(autoTimer);clearTimeout(freeTimer);
 if(!ready||!scene||running||modalOpen||bank.mode==='hold')return;
 const before=snapshot();
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
 running=true;update();
 $('winNotice').hidden=true;
 status('TAMBORES A RODAR ↓');
 say('Os símbolos estão a descer…');
 if(!outcome.free&&autoLeft>0)autoLeft--;
 // Guardar o PRÉMIO antes de apresentar peças. A recarga não volta a pagar.
 board=finalBoard.map(col=>col.slice());
 await persist('spin',{board:finalBoard,paid:outcome.credited,
  wager:outcome.free?0:bank.stake,stages:stages.length});
 audio.play('spin');
 let repaired=false;
 try{
  if(!(await visual(()=>scene.spin(outcome.board,{turbo}),
    outcome.board,turbo?3900:7000)))repaired=true;
  for(let i=0;i<stages.length;i++){
   const stage=stages[i];
   const wins=stage.ev.lineWins||[];
   const cells=winningCells(stage.ev);
   const next=i<stages.length-1?stages[i+1]:null;
   const fac=stage.ev.wilds?outcome.wildMultiplier:1;
   const lineTotal=round(stage.ev.raw*fac);
   orbs(outcome.wildMultiplier,stage.ev.wilds);
   if(!wins.length){
    status('FIM DAS QUEDAS · SEM NOVAS COMBINAÇÕES');
    break;
   }
   status('QUEDA '+(i+1)+' · '+wins.length+' COMBINAÇÃO'+
    (wins.length>1?'ÕES':'')+' PREMIADA'+(wins.length>1?'S':''));
   showWinText(wins,lineTotal);
   say(wins.length+' combinação'+(wins.length>1?'ões':'')+
    ' · queda '+(i+1)+' · '+money(lineTotal)+' créditos');
   const multiple=wins.length>1;
   if(multiple)popup('✨ +'+money(lineTotal)+' ✨');
   audio.play('win');
   if(!(await visual(()=>scene.burst(cells,{
    destroy:!!next||multiple,
    duration:turbo?390:780
   }),stage.board,turbo?1900:3000)))repaired=true;
   if(next){
    audio.play('cascade');
    status('↓ PEÇAS A CAIR DE CIMA ↓');
    if(!(await visual(()=>scene.cascade(next.board,next.removed,{turbo}),
      next.board,turbo?2500:3800)))repaired=true;
   }
  }
 }catch(err){
  repaired=true;console.error('Falha recuperada no giro:',err);
 }finally{
  // Nunca deixar preso o botão: corrigir malhas, limpar estado e continuar.
  try{scene.recover(finalBoard)}catch(err){console.warn(err)}
  running=false;
  const all=stages.flatMap(s=>s.ev.lineWins||[]);
  $('winNotice').hidden=all.length===0;
  if(all.length)showWinText(all,outcome.rawTotal,true);
  status(all.length?all.length+' COMBINAÇÕES SOMADAS · '+
   Math.max(0,stages.length-1)+' QUEDAS':'TAMBORES PRONTOS');
  update();
  if(repaired){
   say('Movimento recuperado; resultado e saldo preservados');
  }else if(outcome.triggered){
   say(outcome.triggered);popup('✨ '+outcome.triggered+' ✨');
  }else if(outcome.free){
   say('Free Spins · acumulado '+money(bank.freeTotal)+
    ' ×'+bank.freeMultiplier);
  }else if(all.length){
   say(all.length+' combinações · prémio total '+
    money(outcome.credited)+' créditos');
   popup('✨ TOTAL '+money(outcome.credited)+' ✨');
  }else say('Sem prémio neste giro');
  // Jogadas seguintes só avançam quando as quedas atuais terminarem.
  if(bank.mode==='hold')setTimeout(showHold,650);
  else if(bank.mode==='free')freeTimer=setTimeout(runSpin,turbo?350:780);
  else if(autoLeft>0)autoTimer=setTimeout(runSpin,turbo?230:600);
 }
}
function modal(title,description,actions=[]){
 modalOpen=true;clearTimeout(autoTimer);autoLeft=0;
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
  btn.addEventListener('click',()=>{
   closeModal();
   try{action.act?.()}catch(err){console.error(err);say('Operação indisponível')}
  });
  area.append(btn);
 }
 $('overlay').hidden=false;
 update();
}
function closeModal(){
 modalOpen=false;$('overlay').hidden=true;update();
 if(bank.mode==='free'&&!running)freeTimer=setTimeout(runSpin,500);
}
function bonusModal(){
 if(running||bank.mode!=='base')return;
 modal('Comprar bónus DEMO',
  'Compras voluntárias apenas com créditos fictícios; sem dinheiro real.',[
   {name:'✨ 15 Free Spins',detail:'Custo: '+money(bank.bonusPrice),
    disabled:bank.balance<bank.bonusPrice,
    act:async()=>{
     const r=bank.buyFree();update();say(r.message);
     if(r.ok){await persist('buy-free',{cost:bank.bonusPrice});popup('15 FREE SPINS');
      freeTimer=setTimeout(runSpin,1100)}
    }},
   {name:'🪙 Hold & Win 4×4',detail:'Custo: '+money(bank.holdPrice),
    disabled:bank.balance<bank.holdPrice,
    act:async()=>{
     const r=bank.buyHold();update();say(r.message);
     if(r.ok){await persist('buy-hold',{cost:bank.holdPrice});showHold()}
    }}
  ]);
}
function showHold(){
 if(bank.mode!=='hold')return;
 $('holdOverlay').hidden=false;
 renderHold();
}
function renderHold(){
 const h=bank.hold;
 if(!h)return;
 const grid=$('holdGrid');grid.replaceChildren();
 for(const value of h.grid){
  const el=document.createElement('div');
  el.className='hold-cell'+(value?' filled':'');
  el.textContent=value?value+'×':'✦';grid.append(el);
 }
 $('holdInfo').textContent=h.tries+' tentativas · '+
  money(h.total)+' créditos fictícios';
}
async function holdTurn(){
 if(bank.mode!=='hold')return;
 const button=$('holdBtn');button.disabled=true;
 try{
  const result=bank.holdTurn();
  if(!result.ok){say(result.message);return}
  renderHold();update();
  await persist(result.done?'hold-paid':'hold-turn',{paid:result.prize||0});
  if(result.done){
   $('holdOverlay').hidden=true;
   popup('✨ '+money(result.prize)+' ✨');
   say('Hold & Win · prémio integral: '+money(result.prize));
   if(autoLeft>0)autoTimer=setTimeout(runSpin,700);
  }else audio.play('cascade');
 }finally{button.disabled=false}
}
async function historyModal(){
 let log=[];
 try{log=await demoDB.history()}catch(err){console.error(err)}
 const events=log.slice(0,50);
 const wagers=log.reduce((a,x)=>a+Number(x.wager||0)+Number(x.cost||0),0);
 const paid=log.reduce((a,x)=>a+Number(x.paid||0),0);
 const observed=wagers>0?(paid/wagers*100).toFixed(1)+'%':'—';
 const details='Base de dados no teu dispositivo ('+demoDB.mode+
  '). Eventos: '+log.length+' · Apostas/compras: '+money(wagers)+
  ' · Prémios: '+money(paid)+
  ' · Retorno observado: '+observed+' (não é RTP auditado).';
 modal('Histórico e dados locais',details,[
  {name:'⬇ Exportar histórico JSON',detail:'Inclui saldo e últimos giros desta instalação.',
   act:async()=>{
    const obj=await demoDB.exportData();
    const blob=new Blob([JSON.stringify(obj,null,2)],{type:'application/json'});
    const url=URL.createObjectURL(blob);
    const link=document.createElement('a');
    link.href=url;link.download='jardim-phaser-demo-'+new Date().toISOString().slice(0,10)+'.json';
    document.body.append(link);link.click();link.remove();
    setTimeout(()=>URL.revokeObjectURL(url),2000);
   }},
  {name:'♻ Reiniciar demonstração',detail:'Apaga saldo e histórico locais.',
   act:async()=>{
    if(!confirm('Apagar todo o histórico e saldo fictício deste dispositivo?'))return;
    await demoDB.clear();location.reload();
   }}
 ]);
 const wrapper=$('modalContent');
 const listing=document.createElement('div');
 listing.style.cssText='max-height:25vh;overflow:auto;font:11px system-ui;text-align:left';
 for(const item of events.slice(0,12)){
  const p=document.createElement('p');
  p.textContent=(item.timestamp||'').slice(0,16).replace('T',' ')+' · '+
   item.type+' · '+money(item.paid||0)+' créditos';
  listing.append(p);
 }
 wrapper.append(listing);
}
function infoModal(){
 modal('Regras do Jardim Mágico Phaser',
 'Demonstração HTML5 em Phaser, com créditos fictícios. Cinco colunas, três linhas e dez linhas de prémio.',[
  {name:'🌟 WILD ×1 a ×5',detail:'Substitui figuras normais; nunca o SCATTER.'},
  {name:'✨ SCATTER',detail:'3 na grelha: 15 Free Spins. Nas Free Spins, 2: +8 ou 3: +15.'},
  {name:'🪙 HOLD & WIN',detail:'Seis moedas ativam a grelha 4×4 com três tentativas.'},
  {name:'🪨 Pedra bloqueadora',detail:'Não atribui prémios; interrompe sequências.'},
  {name:'🎁 Experimentar Free Spins',detail:'Ativa apenas um teste visual gratuito.',
   act:async()=>{
    if(bank.mode!=='base'||running)return;
    bank.addFree(15);await persist('test-free');update();
    freeTimer=setTimeout(runSpin,450);
   }},
  {name:'🪙 Experimentar Hold & Win',detail:'Ativa grelha fictícia se o fundo permitir.',
   act:async()=>{
    if(bank.mode!=='base'||running)return;
    if(bank.startHold()){await persist('test-hold');update();showHold()}
    else say('O cofre virtual não consegue pagar seis moedas iniciais');
   }}
 ]);
}
function vaultModal(){
 modal('Cofre Mágico',
 'Fundo de demonstração disponível: '+money(bank.reserve)+
 ' créditos fictícios. O motor verifica os resultados antes de os mostrar e não corta prémios atribuídos.',[
  {name:'＋ Adicionar 500 ao fundo DEMO',detail:'Apenas para experimentar mais prémios.',
   act:async()=>{
    if(bank.mode!=='base'||running)return;
    bank.reserve=round(bank.reserve+500);
    update();await persist('fund-plus');say('Fundo virtual aumentado em 500 créditos');
   }}
 ]);
}
async function creditDemo(){
 bank.creditDemo(25);update();await persist('credit-demo');
 say('Adicionados 25 créditos fictícios');
}
function changeBet(dir){
 const pos=BETS.findIndex(x=>Math.abs(x-bank.stake)<.00001);
 const next=BETS[Math.max(0,Math.min(BETS.length-1,(pos<0?2:pos)+dir))];
 if(bank.setStake(next)){
  update();persist('stake');say('Aposta virtual '+money(bank.stake));
 }
}
function autoModal(){
 if(autoLeft>0){stopAuto();return}
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
  if(e.button!==0||running)return;
  clearTimeout(longTimer);
  longTimer=setTimeout(()=>{holdsLong=true;autoModal()},3000);
 });
 for(const event of ['pointerup','pointerleave','pointercancel'])
  $('spinBtn').addEventListener(event,()=>clearTimeout(longTimer));
 $('betDown').addEventListener('click',()=>changeBet(-1));
 $('betUp').addEventListener('click',()=>changeBet(1));
 $('bonusBtn').addEventListener('click',bonusModal);
 $('vaultBtn').addEventListener('click',vaultModal);
 $('creditBtn').addEventListener('click',creditDemo);
 $('historyBtn').addEventListener('click',historyModal);
 $('infoBtn').addEventListener('click',infoModal);
 $('autoBtn').addEventListener('click',autoModal);
 $('holdBtn').addEventListener('click',holdTurn);
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
  if(document.hidden){stopAuto();clearTimeout(freeTimer)}
  else if(ready&&!running&&bank.mode==='free')
   freeTimer=setTimeout(runSpin,650);
 });
}
async function boot(){
 bind();update();
 if(typeof Phaser==='undefined'){
  $('loadError').hidden=false;
  status('Não foi possível carregar Phaser');return;
 }
 try{
  const mode=await demoDB.connect();
  const saved=await demoDB.load();
  if(saved?.snapshot)copySnapshot(saved.snapshot);
  if(Array.isArray(saved?.board)&&saved.board.length===5)board=saved.board;
  $('dbStatus').textContent=mode==='indexedDB'?'PHASER · DB LOCAL ✓':'PHASER · ARMAZENAMENTO LOCAL';
 }catch(err){
  console.error('Base local:',err);
  $('dbStatus').textContent='PHASER · SEM BASE LOCAL';
 }
 game=createReelGame();
 if(!game){status('Phaser indisponível');return}
 const available=()=>{
  scene=window.jardimPhaserScene;
  if(!scene||!scene.ready)return;
  scene.setBoard(board);
  ready=true;update();
  status('TAMBORES VERTICAIS PRONTOS');
  say('Jardim Mágico Phaser · símbolos originais HD');
  if(bank.mode==='hold')showHold();
  else if(bank.mode==='free')freeTimer=setTimeout(runSpin,1100);
 };
 if(window.jardimPhaserScene?.ready)available();
 else window.addEventListener('jardim-phaser-ready',available,{once:true});
 setTimeout(()=>{
  if(!ready){
   $('loadError').hidden=false;
   say('O motor demorou demasiado a abrir. Verifica a ligação.');
  }
 },11000);
}
boot();
