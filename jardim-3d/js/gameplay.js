/* Jardim Mágico 3D V2 — Controlador da demonstração visual.
   Toda a aposta, carteira e prémio neste módulo são valores FICTÍCIOS.
   O engine demonstrativo é separado do renderizador PlayCanvas. */
import {DemoBank,SYMBOLS,LABELS,PAYLINES,money} from './demo-engine.js?v=2.1';

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
window.JM3DGameController=true;

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
 const el=$('bigWin');
 clearTimeout(winTimer);
 el.textContent=text;el.classList.add('visible');
 winTimer=setTimeout(()=>el.classList.remove('visible'),1600);
}
function freeBanner(text){
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
     const r=bank.buyFree();render();say(r.message);if(r.ok){freeBanner('✨ 15 FREE SPINS ✨');nextTimer=setTimeout(runSpin,1150)}
   },bank.balance<bank.bonusPrice],
   ['🪙 Hold & Win 4×4','Custo fictício: '+money(bank.holdPrice)+' créditos',()=>{
     const r=bank.buyHold();render();say(r.message);if(r.ok)showHold();
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
  bank.addFree(15);render();freeBanner('✨ FREE SPINS DEMO ✨');nextTimer=setTimeout(runSpin,1200)
 },bank.mode!=='base'||spinBusy));
 b.append(choice('🪙 Experimentar Hold & Win','Grelha interativa 4×4 com moedas fictícias.',()=>{
  bank.startHold();render();showHold()
 },bank.mode!=='base'||spinBusy));
 dialog('Regras da demonstração',b);
}
function vaultModal(){
 makeOptions('Cofre Mágico',
 'Fundo virtual disponível para pagar prémios simulados: '+money(bank.reserve)+' créditos. Este valor não representa dinheiro depositado ou disponível para levantamento.',
 [
 ['🪙 Testar Hold & Win','Ativar uma demonstração sem compra real.',()=>{
  bank.startHold();render();showHold()
 },bank.mode!=='base'||spinBusy],
 ['＋ Adicionar créditos DEMO','Acrescenta 25 créditos fictícios ao saldo.',()=>{
  bank.creditDemo(25);render();say('Foram adicionados 25 créditos fictícios')
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
 const multiplied=multiplier>1?' ×'+multiplier+' = '+money(total*multiplier):'';
 const cap=capped!==null?' · PAGO '+money(capped):'';
 target.textContent=lineWins.length+' COMBINAÇÕES · '+
   individual.join(' + ')+' = '+money(total)+multiplied+cap;
 target.hidden=false;
}
function runSpin(){
 clearTimeout(nextTimer);
 if(spinBusy||activeDialog||bank.mode==='hold'){if(bank.mode==='hold')showHold();return}
 if(!window.JM3D){say('Motor 3D a iniciar…');return}
 const outcome=bank.spin();
 if(!outcome.ok){autoRemaining=0;render();say(outcome.message);return}
 setSpinning(true);
 showLineBreakdown([],0);
 $('spinState').textContent='TAMBORES A RODAR…';
 say(outcome.free?'FREE SPIN · '+bank.freeSpins+' restantes':'Tambores tridimensionais em movimento');
 if(!outcome.free&&autoRemaining>0)autoRemaining--;
 const completed=()=>{
  setSpinning(false);
  render();
  const wins=outcome.ev.lineWins||[];
  const allWinners=winningCoordinates(wins);
  const simultaneous=wins.length>=2;
  if(simultaneous&&window.JM3D?.burstWinners){
   // Explosão de TODAS as peças vencedoras em simultâneo.
   window.JM3D.burstWinners(allWinners,1100);
  }else if(wins.length===1&&window.JM3D?.highlight){
   window.JM3D.highlight(allWinners,1100);
  }
  $('spinState').textContent=simultaneous?
   wins.length+' COMBINAÇÕES PREMIADAS!':
   wins.length===1?'1 COMBINAÇÃO PREMIADA':'TAMBORES 3D PRONTOS';
  const lineFactor=outcome.ev.wilds?outcome.wildMultiplier:1;
  const effectiveTotal=Math.round(outcome.ev.raw*lineFactor*100)/100;
  showLineBreakdown(wins,outcome.ev.raw,
    !outcome.free&&outcome.credited<effectiveTotal?outcome.credited:null,
    lineFactor);
  const win=outcome.free?outcome.potential:outcome.credited;
  showMultiply(outcome.wildMultiplier,outcome.ev.wilds);
  if(outcome.triggered){
   say(outcome.triggered);
   freeBanner('✨ '+outcome.triggered+' ✨');
  }else if(outcome.free){
   say('Valor acumulado: '+money(bank.freeTotal)+' ×'+bank.freeMultiplier);
  }else if(simultaneous){
   say(wins.length+' combinações · prémio total: '+money(outcome.credited)+' créditos');
  }else say(win>0?'Prémio demo: '+money(win)+' créditos':'Sem prémio neste giro');
  if(simultaneous){
   const shown=outcome.free?outcome.ev.raw:outcome.credited;
   bigWin('✨ TOTAL '+money(shown)+' ✨');
  }else if(outcome.credited>=bank.stake*15){
   bigWin('✨ '+money(outcome.credited)+' ✨');
  }
  if(outcome.ev.scatter>=3&&outcome.triggered)bigWin('FREE SPINS!');
  // Respeitar o tempo do estouro conjunto antes de mudar os tambores.
  const minimumDelay=simultaneous?1350:0;
  if(bank.mode==='hold'){nextTimer=setTimeout(showHold,Math.max(900,minimumDelay));return}
  if(bank.mode==='free'){
   nextTimer=setTimeout(runSpin,Math.max(1100,minimumDelay));return;
  }
  if(autoRemaining>0)nextTimer=setTimeout(runSpin,Math.max(bank.stake<0.1?650:800,minimumDelay));
 };
 if(!window.JM3D.spin(outcome.board,completed)){
  setSpinning(false);say('Tambores ocupados');render();
 }
}
function changeStake(delta){
 const idx=BETS.findIndex(v=>Math.abs(v-bank.stake)<1e-5);
 const next=BETS[Math.max(0,Math.min(BETS.length-1,(idx<0?2:idx)+delta))];
 if(bank.setStake(next)){render();say('Aposta virtual: '+money(next))}
}
function charge(){
 bank.creditDemo(25);render();say('＋25 créditos fictícios adicionados');
}
function bind(){
 $('betMinus').addEventListener('click',()=>changeStake(-1));
 $('betPlus').addEventListener('click',()=>changeStake(1));
 $('btnDemoCredits').addEventListener('click',charge);
 $('btnInfo').addEventListener('click',payModal);
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
  if(document.hidden){autoRemaining=0;clearTimeout(nextTimer);render()}
 });
}
render();bind();
if(window.JM3D) say('Jardim Mágico 3D pronto — créditos virtuais');
else{
 window.addEventListener('jm3d-ready',()=>{
  say('Jardim Mágico 3D pronto — créditos virtuais');
 },{once:true});
}
