/* Crónicas do Faraó - V0.4 original Canvas game client, no external dependencies.
   Independent demonstration, not connected with Play'n GO or real-money gambling. */
(() => {
'use strict';
const E = window.TombEngine, S=window.FaraoSession;
const QA=new URLSearchParams(location.search).has('qa');
const STORE_KEY=QA?'farao-eterno-qa-v2':'farao-eterno-v2';
if (!E) throw new Error('Motor de jogo indisponível');
const W=560,H=1000;
const $ = id => document.getElementById(id);
const canvas=$('canvas'),cx=canvas.getContext('2d',{alpha:false});
const mod=$('modal'), modalContent=$('modal-content'), title=$('modal-title');
const iconNames={TOMB:'Túmulo (WILD/SCATTER)',KING:'Rei',ANUBIS:'Anúbis',HORUS:'Hórus',MASK:'Máscara',A:'Ás',K:'Rei (K)',Q:'Dama',J:'Valete','10':'Dez'};
const bets=S.BETS;
const PALETTE={gold:'#f3d389',bright:'#ffeeb1',dark:'#2a1715',teal:'#54d6ce',red:'#d85c63'};
const money = val => (Math.round((val+Number.EPSILON)*100)/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2}) + ' cr';
const nice = num=>num.toLocaleString('pt-PT',{maximumFractionDigits:0});
const clamp=(n,a,b)=>Math.min(b,Math.max(a,n));
const rnd=()=>E.randomFloat();
const choose = arr=>arr[Math.floor(rnd()*arr.length)];
let sc=1,scY=1,ox=0,oy=0,dpr=1,lastFrame=0;
function rect(x,y,w,h,fill,stroke=null,r=0,lw=1) {
 cx.beginPath(); if(r) cx.roundRect(x,y,w,h,r); else cx.rect(x,y,w,h);
 if(fill){cx.fillStyle=fill;cx.fill();}if(stroke){cx.lineWidth=lw;cx.strokeStyle=stroke;cx.stroke();}
}
function line(x1,y1,x2,y2,color,width=1) {
 cx.beginPath();cx.moveTo(x1,y1);cx.lineTo(x2,y2);cx.lineWidth=width;cx.strokeStyle=color;cx.stroke();
}
function ellipse(x,y,rx,ry,color) {cx.beginPath();cx.ellipse(x,y,rx,ry,0,0,Math.PI*2);cx.fillStyle=color;cx.fill();}
function text(str,x,y,size,color=PALETTE.gold,align='center',family='Georgia',weight=700,shadow=0){
 cx.save();cx.textAlign=align;cx.textBaseline='middle';cx.font=`${weight} ${size}px ${family}`;
 if(shadow){cx.shadowColor='#000';cx.shadowBlur=shadow;cx.shadowOffsetY=2;}
 cx.fillStyle=color;cx.fillText(str,x,y);cx.restore();
}
function grad(x,y,x2,y2,stops){const g=cx.createLinearGradient(x,y,x2,y2);stops.forEach(([p,c])=>g.addColorStop(p,c));return g;}
function dots(x,y,r=1,color='#efc67e'){ellipse(x,y,r,r,color);}
class AudioFx {
 constructor(){this.on=true;this.ctx=null;}
 init(){if(!this.on) return;const A=window.AudioContext||window.webkitAudioContext;if(!A)return;if(!this.ctx)this.ctx=new A();if(this.ctx.state==='suspended')this.ctx.resume().catch(()=>{});}
 tone(freq=390,dur=.07,type='sine',vol=.055,offset=0){if(!this.on||!this.ctx)return;
   const t=this.ctx.currentTime+offset,o=this.ctx.createOscillator(),g=this.ctx.createGain();o.type=type;o.frequency.setValueAtTime(freq,t);o.frequency.exponentialRampToValueAtTime(Math.max(90,freq*.65),t+dur);
   g.gain.setValueAtTime(Math.max(.0001,vol),t);g.gain.exponentialRampToValueAtTime(.0001,t+dur);o.connect(g);g.connect(this.out||this.ctx.destination);o.start(t);o.stop(t+dur+.015);
 }
 click(){this.tone(670,.06,'triangle',.04);}
 spin(){this.tone(160,.18,'sawtooth',.035);this.tone(260,.15,'triangle',.027,.09);}
 tick(i){this.tone(170+i*43,.09,'triangle',.065);}
 win(bonus=false){[440,555,660,880].forEach((f,i)=>this.tone(f,.26,'sine',bonus?.09:.04,i*.09));}
 extra(){[420,530,650,870,1000].forEach((f,i)=>this.tone(f,.3,'triangle',.09,i*.11));}
 lose(){this.tone(320,.24,'sine',.04);this.tone(185,.32,'sine',.035,.14);}
}
class Game {
 constructor(){
  this.ledger=S.fresh();this.paused=false;this.superseded=false;this.storageOK=true;this.pendingSummary=null;this.owner=(crypto.randomUUID?.()||String(Math.random()));
  this.audio=new AudioFx();this.images={};this.ready=false;this.targets=[];this.temple=null;this.landings=Array(5).fill(0);this.fxParticles=[];
  this.balance=300;this.betIndex=4;this.lines=10;this.grid=E.makeGrid(rnd);
  this.spinObject=null;this.spinning=false;this.lastResult=null;this.lastWin=0;this.feature=null;this.lastBaseWin=0;this.lastWasFree=false;
  this.currentRound=0;this.winCells=[];this.expanded={};this.auto=0;this.turbo=false;this.sound=true;
  this.reveal=null;this.featureIntro=null;this.winCount=null;
  this.started=false;this.message='PRIME GIRAR PARA ENTRAR NO TEMPLO';this.messageUntil=0;
  this.flashUntil=0;this.flashKind='';this.nextActionAt=0;this.rng=rnd;this.history=[];this.gambleTimes=0;
  this.particles=Array.from({length:42},(_,i)=>({x:rnd()*W,y:rnd()*H,s:.3+rnd()*2.5,phase:rnd()*6.283,spd:2+rnd()*10}));
  this.load();this.initInputs();this.loadArt();this.resize();window.addEventListener('resize',()=>this.resize());
  requestAnimationFrame(t=>this.frame(t));
 }
 get bet(){return bets[this.betIndex]}
 load(){
  try{
   const raw=localStorage.getItem(STORE_KEY);
   this.ledger=raw?S.restore(JSON.parse(raw)):S.migrate(QA?null:JSON.parse(localStorage.getItem('farao-eterno-v1')||'null'));
   if(this.ledger.pending){S.settle(this.ledger);this.recovered=true;}
  }catch(e){this.ledger=S.fresh();this.recoveryWarning=true;}
  this.syncLedger();this.paused=!!this.feature;
  this.save();
 }
 syncLedger(){
  const s=this.ledger;
  this.balance=S.credits(s.balanceU);this.betIndex=s.betIndex;this.lines=s.lines;
  this.feature=s.feature?{...s.feature,total:S.credits(s.feature.totalU)}:null;
  this.grid=s.grid;this.lastWin=S.credits(s.lastWinU);this.lastBaseWin=S.credits(s.lastBaseWinU);
  this.lastWasFree=s.lastWasFree;this.gambleTimes=s.gambles;this.history=s.history;
  this.sound=s.settings.sound;this.audio.on=this.sound;this.turbo=s.settings.turbo;
  if(s.last)this.lastResult=s.last.result;
 }
 save(){
  if(this.superseded)return;
  this.ledger.settings={sound:this.sound,music:this.ledger.settings.music,turbo:this.turbo};
  this.ledger.owner=this.owner;
  try{localStorage.setItem(STORE_KEY,JSON.stringify(this.ledger));this.storageOK=true;}catch(_){this.storageOK=false;}
 }

 announce(s){$('screen-msg').textContent=s;}
 notice(s,time=1750,kind='gold'){this.message=s;this.messageUntil=performance.now()+time;this.flashUntil=this.messageUntil;this.flashKind=kind;this.announce(s);}
 initInputs(){
  canvas.addEventListener('pointerdown',e=>{e.preventDefault();this.audio.init();let b=canvas.getBoundingClientRect();const x=(e.clientX-b.left-ox)/sc,y=(e.clientY-b.top-oy)/scY;
   if(mod.classList.contains('hidden')===false)return;
   for(const target of [...this.targets].reverse()){const {x:bx,y:by,w,h,action}=target;if(x>=bx&&y>=by&&x<=bx+w&&y<=by+h){this.audio.click();action();break;}}
  },{passive:false});
  document.addEventListener('keydown',ev=>{if(ev.key==='Escape'){this.closeModal();return;}if(mod.classList.contains('hidden')===false)return;
   if(ev.code==='Space'&&!ev.repeat&&!['BUTTON','INPUT','SELECT','A'].includes(ev.target.tagName)){ev.preventDefault();this.audio.init();this.startSpin();}
  });
  $('close-modal').addEventListener('click',()=>this.closeModal());
  window.addEventListener('storage',ev=>{if(ev.key===STORE_KEY&&ev.newValue){this.superseded=true;this.auto=0;this.paused=true;this.audio.stopMusic?.();$('session-lock').classList.remove('hidden');}});
  $('reload-session').onclick=()=>location.reload();
  const preventStageZoom=ev=>{if(ev.cancelable)ev.preventDefault();};
  $('stage').addEventListener('dblclick',preventStageZoom,{passive:false});
  $('stage').addEventListener('gesturestart',preventStageZoom,{passive:false});
  window.addEventListener('pagehide',()=>this.save());
  document.addEventListener('visibilitychange',()=>{if(document.hidden){this.auto=0;this.paused=true;this.audio.stopMusic?.();}else if(this.paused)this.announce('Jogo em pausa. Toca em Retomar.');});
  mod.addEventListener('click',ev=>{if(ev.target===mod)this.closeModal();});
 }
 resize(){
  const box=$('stage').getBoundingClientRect(),w=box.width,h=box.height;
  dpr=clamp(devicePixelRatio||1,1,Math.min(w,h)<=520?1.75:2);
  canvas.width=Math.ceil(w*dpr);canvas.height=Math.ceil(h*dpr);
  canvas.style.width=w+'px';canvas.style.height=h+'px';
  sc=Math.min(w/576,h/1024);scY=sc;ox=(w-W*sc)/2;oy=(h-H*sc)/2;
  this.viewport={width:w,height:h,scale:sc,ox,oy,dpr};
 }

 loadArt(){
  let loaded=0;for(const key of E.ALL){const im=new Image();
   im.onload=()=>{loaded++;if(loaded===E.ALL.length)this.ready=true;};
   im.onerror=()=>{if(!im.fallback){im.fallback=true;im.src='assets/'+key.toLowerCase()+'.svg';}else{loaded++;if(loaded===E.ALL.length)this.ready=true;}};
   im.src='assets/'+key.toLowerCase()+(E.NORMAL.slice(5).includes(key)||key==='TOMB'?'.webp':'.svg');this.images[key]=im;
  }
  const img=new Image();
  img.onload=()=>{this.temple=img;};
  img.onerror=()=>{if(!img.fallback){img.fallback=true;img.src='assets/temple_back.svg';}};
  img.src='assets/temple.webp';
 }

  action(x,y,w,h,fn,label='Ação'){this.targets.push({x,y,w,h,action:fn,label});}
 syncTargets(){
  const root=$('touch-controls');
  while(root.children.length>this.targets.length)root.lastChild.remove();
  this.targets.forEach((target,i)=>{
   let b=root.children[i];if(!b){b=document.createElement('button');b.type='button';b.className='touch-target';root.appendChild(b);}
   b.setAttribute('aria-label',target.label);b.textContent=target.label;
   b.style.cssText=`left:${ox+(target.x+target.w/2)*sc}px;top:${oy+(target.y+target.h/2)*sc}px;width:${Math.max(44,target.w*sc)}px;height:${Math.max(44,target.h*scY)}px`;
   b.onclick=()=>{if(!mod.classList.contains('hidden')||this.superseded)return;this.audio.init();this.audio.click();target.action();};
  });
 }

 startSpin(){
  if(!mod.classList.contains('hidden')||this.superseded||!this.ready)return;
  this.audio.init();
  if(this.recovered){this.recovered=false;this.announce('Jogada recuperada. O prémio já está no saldo.');}
  if(this.paused){this.paused=false;this.audio.startMusic?.();this.notice('O TEMPLO DESPERTA',650);if(this.spinning)return;}
  if(this.spinning)return;
  if(performance.now()<this.nextActionAt)return;
  const free=!!this.feature;
  let begun;
  try{begun=S.begin(this.ledger,this.rng);}catch(e){this.auto=0;this.notice('CRÉDITOS INSUFICIENTES · MENU PARA REPOR',1900,'red');return;}
  this.syncLedger();if(!free&&this.auto>0)this.auto--;
  this.reveal=null;this.featureIntro=null;this.winCount=null;this.lastWin=0;this.lastResult=null;this.expanded={};this.winCells=[];
  this.spinning=true;this.landings.fill(0);this.fxParticles=[];
  const t=performance.now(),duration=this.turbo?440:940,reelGap=this.turbo?75:145;
  this.spinObject={grid:begun.pending.grid,result:begun.result,free,start:t,duration,reelGap,stopped:Array(5).fill(false),show:Array.from({length:5},()=>Array.from({length:14},()=>choose(E.ALL)))};
  this.spinObject.steps=Array.from({length:5},(_,c)=>18+c*3);
  this.spinObject.show=this.spinObject.steps.map((n,c)=>{const strip=Array.from({length:n+10},()=>choose(E.ALL));for(let r=0;r<3;r++){strip[n+3+r]=this.grid[c][r];strip[3+r]=begun.pending.grid[c][r];}return strip;});
  this.save();this.audio.spin();
 }

 finishSpin(){
  const item=this.spinObject;if(!item)return;
  this.spinning=false;this.grid=item.grid;
  const finishedAt=performance.now();
  const result=item.result;
  this.lastWasFree=item.free;
  const settled=S.settle(this.ledger);if(!settled)return;
  this.syncLedger();const prize=S.credits(settled.prizeU);this.lastResult=result;
  this.winCells=this.computeWinners(result);
  this.expanded={};
  for(const candidate of result.expanders){for(const reel of candidate.reels){
    if(!this.expanded[reel] || E.PAYS[candidate.symbol][candidate.count]>(E.PAYS[this.expanded[reel].symbol][this.expanded[reel].count]||0))this.expanded[reel]=candidate;
  }}
  // Celebration effects are visual only, and never change the settled outcome.
  for(const cell of this.winCells.slice(0,13)) {
   const [c,r]=cell.split('-').map(Number);
   this.emitBurst(82+c*101,322+r*132,3,result.trigger?'#85fff0':'#f9d27b',finishedAt);
  }
  this.announce(`${item.free?'Free Spin':'Jogada'}: prémio ${money(prize)}. Saldo ${money(this.balance)}.`);
  if(result.lineWins.length || result.scatterWin) this.audio.win(Boolean(item.free));
  this.spinObject=null;this.nextActionAt=finishedAt+(this.turbo?320:690);
  // The reels land first. During free spins, qualified symbols then expand one
  // reel at a time. Credits are awarded immediately, but the HUD counts up only
  // after the animation. All timings are visual; the result is already final.
  const expansionReels = item.free ? Object.keys(this.expanded).map(Number).sort((a,b)=>a-b) : [];
  const animationEnd = expansionReels.length ? 360+expansionReels.length*(this.turbo?140:230)+300 : 0;
  for(let k=0;k<expansionReels.length;k++)this.emitBurst(83+expansionReels[k]*101,444,16,'#83ffef',finishedAt+(this.turbo?190:360)+k*(this.turbo?140:230));
  this.reveal = expansionReels.length ? {
    start:finishedAt, reels:expansionReels, preDelay:this.turbo?190:360,
    perReel:this.turbo?140:230, end:finishedAt+animationEnd,
  } : null;
  this.winCount={start:finishedAt+(this.reveal?animationEnd-280:80),duration:this.turbo?450:820,amount:prize};
  this.nextActionAt=Math.max(this.nextActionAt,this.winCount.start+this.winCount.duration+250);
  if(item.free){
   if(result.trigger&&this.feature){
    this.audio.extra();this.notice(settled.nextSymbol?'+8 FREE SPINS · NOVO SÍMBOLO':'+8 FREE SPINS',this.turbo?1100:1850,'cyan');
    if(settled.nextSymbol)this.queueIntro(settled.nextSymbol,Math.max(finishedAt+500,this.reveal?.end||0));
   }else if(Object.keys(this.expanded).length)this.notice('SÍMBOLO EXPANSIVO',this.turbo?450:1050,'cyan');
   if(settled.bonusDone){this.pendingSummary=settled.bonusDone;this.nextActionAt=Math.max(this.nextActionAt,finishedAt+2300);}
  }else if(result.trigger){
   this.audio.extra();this.notice('8 FREE SPINS DESBLOQUEADAS',this.turbo?1450:2250,'cyan');
   this.queueIntro(this.feature.symbols[0],finishedAt+380);
  }else if(prize>=this.bet*20)this.notice('PRÉMIO ESPECIAL  '+money(prize),this.turbo?650:1550,'gold');
  this.save();
 }
 queueIntro(symbol,from){
  const duration=this.turbo?1050:1650;
  this.featureIntro={symbol,start:from,end:from+duration};
  this.nextActionAt=Math.max(this.nextActionAt,from+duration+200);
 }
 computeWinners(result){
  const map=new Map();
  for(const w of result.lineWins){if(w.expanded){for(const reel of E.LINES[w.line].keys())if(result.expanders.some(p=>p.symbol===w.symbol&&p.reels.includes(reel)))for(let r=0;r<3;r++)map.set(reel+'-'+r,true);
  }else for(let reel=0;reel<w.count;reel++)map.set(reel+'-'+w.rows[reel],true);}
  if(result.scatterCount>=3)for(let c=0;c<5;c++)for(let r=0;r<3;r++)if(this.grid[c][r]==='TOMB')map.set(c+'-'+r,true);
  return [...map.keys()];
 }
 autoSpin(){if(this.feature || this.auto>0)this.startSpin();}
 setAuto(){if(this.spinning){this.auto=0;this.notice('AUTOJOGO DESLIGADO',900);return;}
  this.paused=false;this.auto=this.auto?0:20;this.notice(this.auto?'20 GIROS AUTOMÁTICOS':'AUTOJOGO DESLIGADO',1100);}
 changeBet(d){if(this.spinning||this.feature||this.superseded)return;this.ledger.betIndex=clamp(this.betIndex+d,0,bets.length-1);this.syncLedger();this.save();}
 changeLines(d){if(this.spinning||this.feature||this.superseded)return;this.ledger.lines=clamp(this.lines+d,1,10);this.syncLedger();this.save();}
 closeModal(){
  mod.classList.add('hidden');mod.setAttribute('aria-hidden','true');$('touch-controls').inert=false;
  this.nextActionAt=Math.max(this.nextActionAt,performance.now()+350);this.focusBeforeModal?.focus();
 }
 openModal(name,html){
  if(this.spinning||this.superseded)return;
  this.auto=0;this.focusBeforeModal=document.activeElement;title.textContent=name;modalContent.innerHTML=html;
  mod.classList.remove('hidden');mod.setAttribute('aria-hidden','false');$('touch-controls').inert=true;
  $('close-modal').focus();
 }
 showHistoricSpin(id,selectedLine=null){
  const h=this.history.find(x=>x.id===id&&x.grid);if(!h)return;
  const wins=Array.isArray(h.lineWins)?h.lineWins:[],valid=wins.filter(w=>Number.isInteger(w.line)&&w.line>=0&&w.line<E.LINES.length);
  const selected=selectedLine===null?null:valid.find(w=>w.line===selectedLine)?.line??null;
  const cells=new Set();
  for(const w of valid)if(selected===null||selected===w.line){
   const line=E.LINES[w.line];
   if(w.expanded){for(let c=0;c<5;c++)if(h.grid[c]?.includes(w.symbol))for(let row=0;row<3;row++)cells.add(c+'-'+row);}
   else for(let c=0;c<Math.min(w.count,5);c++)cells.add(c+'-'+line[c]);
  }
  const rows=Array.from({length:3},(_,row)=>'<div style="display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:3px">'+Array.from({length:5},(_,col)=>{
   const sym=h.grid[col]?.[row],img=this.images[sym],highlight=cells.has(col+'-'+row);
   return '<div style="aspect-ratio:1;border:2px solid '+(highlight?'#ffe18a':'#775d3c')+';background:#13202b"><img alt="Símbolo" src="'+(img?.src||'')+'" style="display:block;width:100%;height:100%;object-fit:contain"></div>';
  }).join('')+'</div>').join('');
  const paths=valid.filter(w=>selected===null||selected===w.line).map((w,i)=>{
   const points=E.LINES[w.line].map((row,col)=>(col*100+50)+','+(row*100+50)).join(' ');
   const color=['#ffdc75','#6af8e5','#ff8fa2','#b9a2ff'][i%4];
   return '<polyline points="'+points+'" fill="none" stroke="#0b1324" stroke-width="8" stroke-linejoin="round" stroke-linecap="round" opacity=".85"/><polyline points="'+points+'" fill="none" stroke="'+color+'" stroke-width="4" stroke-linejoin="round" stroke-linecap="round"/><circle cx="50" cy="'+(E.LINES[w.line][0]*100+50)+'" r="17" fill="#14212b" stroke="'+color+'" stroke-width="3"/><text x="50" y="'+(E.LINES[w.line][0]*100+56)+'" font-size="18" font-weight="bold" fill="'+color+'" text-anchor="middle">'+(w.line+1)+'</text>';
  }).join('');
  const board='<div style="position:relative;max-width:500px;margin:12px auto"><div style="display:grid;gap:3px">'+rows+'</div><svg viewBox="0 0 500 300" preserveAspectRatio="none" style="position:absolute;inset:0;width:100%;height:100%;pointer-events:none;overflow:visible">'+paths+'</svg></div>';
  const choices=valid.map(w=>'<button type="button" class="btn" data-history-line="'+w.line+'" style="margin:3px;'+(selected===w.line?'border-color:#fff1a5;':'')+'">Linha '+(w.line+1)+' · '+money(w.amount||0)+'</button>').join('');
  this.openModal('JOGADA #'+id,'<p>'+new Date(h.t).toLocaleString('pt-PT')+' · Aposta '+money(h.bet)+' · Prémio '+money(h.win)+'</p>'+board+'<p>'+valid.length+' linhas vencedoras. '+(valid.length?'Escolhe uma linha para a destacar.':'Sem linhas vencedoras nesta jogada.')+'</p><div style="text-align:center">'+choices+'</div><button class="btn" id="back-history">Voltar ao histórico</button>');
  $('back-history').onclick=()=>this.openInfo('history');
  modalContent.querySelectorAll('[data-history-line]').forEach(b=>b.onclick=()=>this.showHistoricSpin(id,Number(b.dataset.historyLine)));
 }

 showSymbolPay(symbol){
  if(this.spinning||!E.PAYS[symbol])return;
  const image=this.images[symbol],name=iconNames[symbol]||symbol;
  const rows=[2,3,4,5].map(n=>{
   const mult=E.PAYS[symbol][n]||0;
   return '<tr><td style="padding:8px 12px">'+n+' símbolos</td><td style="padding:8px 12px;text-align:right;color:#ffe0a0">'+(mult?'×'+nice(mult)+' · '+money(mult*this.bet/this.lines):'—')+'</td></tr>';
  }).join('');
  const pic=image?.src?'<img src="'+image.src+'" alt="" style="display:block;width:100px;height:100px;object-fit:contain;margin:8px auto;filter:drop-shadow(0 0 12px #e5ac52)">':'';
  this.openModal(name.toUpperCase(),'<div style="max-width:380px;margin:auto;padding:14px;border:2px solid #c89a51;border-radius:16px;background:linear-gradient(140deg,#302419,#101b2a 70%,#3c2a19);box-shadow:inset 0 0 22px #d7a44d40,0 0 18px #c99a4540">'+pic+'<h3 style="text-align:center;color:#ffdf98">'+name+'</h3><table style="width:100%;color:#f7e9ca">'+rows+'</table><p style="font-size:12px;text-align:center">Valores por linha, para aposta de '+money(this.bet)+' e '+this.lines+' linhas ativas.</p></div>');
 }
 openInfo(tab='menu'){
  this.auto=0;
  const tabs=`<div class="header-tabs">${[['menu','Menu'],['pays','Prémios'],['rules','Regras'],['history','Histórico']].map(([key,label])=>`<button data-page="${key}" class="${tab===key?'active':''}">${label}</button>`).join('')}</div>`;
  let page='';
  if(tab==='menu'){
   page=`<p class="eyebrow">OS SEGREDOS DA ETERNIDADE</p><div class="settings-grid"><button class="btn" id="toggle-sound">Efeitos sonoros: ${this.sound?'Ligados':'Desligados'}</button><button class="btn" id="toggle-music">Música: ${this.ledger.settings.music?'Ligada':'Desligada'}</button><button class="btn" id="toggle-turbo">Velocidade: ${this.turbo?'Turbo':'Normal'}</button><button class="btn" id="toggle-auto" ${this.feature?'disabled':''}>${this.auto?'Parar automático':'20 giros automáticos'}</button></div>
   <h3>Linhas ativas: ${this.lines}</h3><div class="actions"><button class="btn" id="line-less" ${this.feature?'disabled':''}>Menos linhas</button><button class="btn" id="line-more" ${this.feature?'disabled':''}>Mais linhas</button></div>
   <div class="actions"><button class="btn" id="menu-reset" ${this.feature?'disabled':''}>Repor 300 créditos</button><button class="btn" id="menu-gamble" ${this.feature||!this.lastBaseWin||this.gambleTimes>=5?'disabled':''}>Arriscar último prémio</button></div><p class="hint">Segundo toque em GIRAR pára os tambores por ordem. O resultado já está definido. As Free Spins continuam automaticamente e a aposta fica bloqueada.</p>`;
  }else if(tab==='pays'){
   page=`<p>Multiplicadores da <b>aposta por linha</b>: ${money(this.bet/this.lines)}. Só o maior prémio de cada linha é pago, incluindo as expansões.</p><div class="scroll"><table><thead><tr><th>Símbolo</th><th>2</th><th>3</th><th>4</th><th>5</th></tr></thead><tbody>`;
   for(const key of ['KING','TOMB','ANUBIS','HORUS','MASK','A','K','Q','J','10']){page+=`<tr><td><img class="pay-symbol" src="${this.images[key].src}" alt="">${iconNames[key]}</td>${[2,3,4,5].map(n=>`<td>${E.PAYS[key][n]?'×'+nice(E.PAYS[key][n]):'—'}</td>`).join('')}</tr>`;}
   page+='</tbody></table></div><p><b>Scatter:</b> 3 santuários = ×2 da aposta total; 4 = ×20; 5 = ×200. Todos ativam 8 Free Spins.</p><h3>As dez linhas</h3><div class="paylines">'+E.LINES.map((rows,i)=>`<figure><svg viewBox="0 0 130 76" aria-label="Linha ${i+1}"><defs><pattern id="dots${i}" width="24" height="23" patternUnits="userSpaceOnUse"><circle cx="17" cy="15" r="3" fill="#4d555d"/></pattern></defs><rect width="130" height="76" fill="url(#dots${i})"/><polyline points="${rows.map((r,c)=>`${17+c*24},${15+r*23}`).join(' ')}" fill="none" stroke="#f3ce7c" stroke-width="3"/></svg><figcaption>Linha ${i+1}</figcaption></figure>`).join('')+'</div>';
  }else if(tab==='rules'){
   page=`<h3>O santuário é WILD e SCATTER</h3><p>5 tambores × 3 posições, com 1 a 10 linhas. As combinações normais começam no tambor da esquerda, sem intervalos. O santuário substitui os restantes símbolos. Hórus, Máscara e Anúbis pagam desde 2; o Faraó e o santuário também. As cartas pagam desde 3.</p><h3>8 Free Spins e símbolos expansivos</h3><p>3 ou mais santuários em qualquer posição dão 8 giros grátis. Um símbolo normal é sorteado. Quando está em tambores suficientes para pagar, expande nas três posições desses tambores, mesmo separados. Os santuários não substituem o símbolo especial no cálculo da expansão.</p><p>Em cada linha vence o maior pagamento entre a combinação normal e todos os expansivos ativos; os prémios não se somam dentro da mesma linha. Novos 3 ou mais santuários dão +8 giros e outro símbolo, sem repetir, até 9 símbolos. Depois de desbloquear os nove, as novas ativações continuam a dar +8 giros.</p><h3>Limites preservados da V0.6</h3><p>Aposta de 0,10 a 10 créditos. Limite de 5.000× numa jogada base e de 5.000× no total de uma série de Free Spins. O prémio da jogada que ativa o bónus é pago à parte. O bónus termina ao atingir o seu limite. Não há limite artificial de novas ativações.</p><h3>Arriscar e sessão</h3><p>Cor: 50% para ×2; naipe: 25% para ×4. Até cinco tentativas. Arrisca apenas o último prémio base e o limite mantém a aposta da jogada original. O saldo e os resultados pendentes são guardados neste navegador. Ao regressar de outra aplicação, toca em Retomar.</p><p><a href="docs/MATEMATICA.md" target="_blank" rel="noopener">Consultar a matemática e probabilidades</a> · <a href="docs/VALIDACAO.md" target="_blank" rel="noopener">Consultar a validação</a></p><p class="hint">Matemática independente. O RTP deste jogo é medido pelo seu próprio simulador; não é o RTP do Legacy of Dead. Não existe certificação de jogo a dinheiro.</p>`;
  }else{
   const esc=v=>String(v).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
    const preview=h=>h.grid?'<div style="display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:2px;max-width:270px;margin:9px auto;border:2px solid #bd9658;padding:4px;background:#0d1b27">'+Array.from({length:15},(_,i)=>{const c=i%5,row=Math.floor(i/5),sym=h.grid[c]?.[row],img=this.images[sym];return '<img src="'+(img?.src||'')+'" alt="" style="display:block;width:100%;aspect-ratio:1;object-fit:contain;background:#18222c" />'}).join('')+'</div>':'<small style="color:#d8ba80">Sem imagem: jogada anterior à atualização.</small>';
    page=`<p>Últimas ${this.history.length} ações. Toca na miniatura para ampliar.</p><div class="history">`+(this.history.length?this.history.map(h=>`<div style="border-bottom:1px solid #98774766;padding:10px 0"><b>${h.kind==='reset'?'Reposição de créditos':h.kind==='gamble'?'Carta':h.bonus?'★ Free Spin':'Giro'} ${Number.isInteger(h.id)?'#'+h.id:''}</b> · aposta ${money(h.bet)} · prémio <b>${money(h.win)}</b><br><small>${esc(new Date(h.t).toLocaleString('pt-PT'))}</small>${h.grid?`<button type="button" data-replay="${h.id}" style="display:block;width:100%;border:0;background:transparent;padding:0;cursor:pointer" aria-label="Ver imagem da jogada ${h.id}">${preview(h)}</button>`:h.kind==='spin'||h.kind==='free'?preview(h):''}</div>`).join(''):'<p>O teu legado começa no primeiro giro.</p>')+'</div>';
  }
  this.openModal('FARAÓ ETERNO',tabs+page);
  modalContent.querySelectorAll('[data-page]').forEach(b=>b.onclick=()=>this.openInfo(b.dataset.page));
  modalContent.querySelectorAll('[data-replay]').forEach(b=>b.onclick=()=>this.showHistoricSpin(Number(b.dataset.replay)));
  if(tab==='menu'){
   $('toggle-sound').onclick=()=>{this.sound=!this.sound;this.audio.on=this.sound;this.audio.init();this.save();this.openInfo();};
   $('toggle-music').onclick=()=>{this.ledger.settings.music=!this.ledger.settings.music;this.audio.stopMusic?.();this.save();this.openInfo();};
   $('toggle-turbo').onclick=()=>{this.turbo=!this.turbo;this.save();this.openInfo();};
   $('toggle-auto').onclick=()=>{this.closeModal();this.setAuto();};
   $('line-less').onclick=()=>{this.changeLines(-1);this.openInfo();};$('line-more').onclick=()=>{this.changeLines(1);this.openInfo();};
   $('menu-reset').onclick=()=>this.openReset();$('menu-gamble').onclick=()=>this.openGamble();
  }
 }
 openReset(){
  if(this.feature||this.spinning)return;
  this.openModal('REPOR CRÉDITOS',`<p>Saldo atual: ${money(this.balance)}.</p><p>Repor o saldo para 300 créditos?</p><div class="actions"><button class="btn" id="reset-go">Repor 300 créditos</button><button class="btn" id="reset-cancel">Voltar</button></div>`);
  $('reset-go').onclick=()=>{S.reset(this.ledger);this.auto=0;this.syncLedger();this.winCount=null;this.save();this.closeModal();this.notice('300 CRÉDITOS · UM NOVO LEGADO',1300);};
  $('reset-cancel').onclick=()=>this.openInfo();
 }
 openGamble(){
  if(this.spinning||this.feature||!this.lastBaseWin||this.gambleTimes>=5)return;
  this.openModal('ARRISCAR O PRÉMIO',`<p>Prémio disponível: <b>${money(this.lastBaseWin)}</b>. Restam ${5-this.gambleTimes} tentativas. Uma escolha errada perde este prémio.</p><div class="actions"><button class="btn red" data-guess="red">♥ Vermelho ×2</button><button class="btn" data-guess="black">♠ Preto ×2</button></div><div class="settings-grid"><button class="btn" data-guess="hearts">♥ Copas ×4</button><button class="btn" data-guess="diamonds">♦ Ouros ×4</button><button class="btn" data-guess="clubs">♣ Paus ×4</button><button class="btn" data-guess="spades">♠ Espadas ×4</button></div>`);
  modalContent.querySelectorAll('[data-guess]').forEach(b=>b.onclick=()=>this.gamble(b.dataset.guess));
 }
 gamble(guess){
  if(this.superseded)return;
  let outcome;try{outcome=S.gamble(this.ledger,guess,this.rng);}catch(e){return;}
  this.syncLedger();this.winCount=null;this.lastResult=null;this.save();this.closeModal();
  this.audio[outcome.won?'win':'lose'](true);
  this.notice('CARTA '+({hearts:'♥',diamonds:'♦',clubs:'♣',spades:'♠'}[outcome.card])+' · '+(outcome.won?money(this.lastWin):'SEM PRÉMIO'),1900,outcome.won?'gold':'red');
 }
 showSummary(){
  const b=this.pendingSummary;if(!b)return;this.pendingSummary=null;
  this.openModal('O TEU LEGADO',`<div class="bonus-summary"><p class="eyebrow">FREE SPINS CONCLUÍDAS</p><strong>${money(S.credits(b.totalU))}</strong><p>${b.played} giros · ${b.retriggers} novas ativações · ${b.symbols.length} símbolos expansivos</p><button class="btn" id="collect-bonus">Continuar</button></div>`);
  $('collect-bonus').onclick=()=>this.closeModal();
 }

 frame(t){
  const dt=Math.min(60,t-lastFrame||16);lastFrame=t;
  if(this.spinning&&!this.paused&&!document.hidden){
   const item=this.spinObject;
   for(let c=0;c<5;c++) if(!item.stopped[c] && t>=item.start+item.duration+c*item.reelGap){item.stopped[c]=true;this.landings[c]=t;this.audio.tick(c);}
   if(item.stopped.every(Boolean))this.finishSpin();
  }else if(!this.spinning&&!this.paused&&!this.superseded&&!document.hidden&&t>this.nextActionAt&&mod.classList.contains('hidden')){
   if(this.pendingSummary)this.showSummary();else if(this.feature||this.auto>0)this.autoSpin();
  }
  this.render(t,dt);
  requestAnimationFrame(time=>this.frame(time));
 }
 render(t){
  const w=canvas.width/dpr,h=canvas.height/dpr;
  cx.setTransform(dpr,0,0,dpr,0,0);cx.clearRect(0,0,w,h);
  cx.fillStyle='#09070b';cx.fillRect(0,0,w,h);
  cx.translate(ox,oy);cx.scale(sc,scY);
  this.targets=[];
  this.background(t);
  this.header(t);
  this.reelBoard(t);
  this.drawFx(t);
  this.statusBar(t);
  this.controls(t);
  this.overlay(t);
  this.introOverlay(t);
  if(!this.ready){rect(70,425,420,85,'#081829ed','#dbbd7c',10,2);text('A ABRIR O TEMPLO…',280,468,23);}
  if(this.paused){rect(100,685,360,30,'#071525d9');text('EM PAUSA · TOCA EM RETOMAR',280,700,13,'#8dffec','center','Arial');}
  this.syncTargets();
 }
 background(t){
  if(this.temple)cx.drawImage(this.temple,0,0,W,H);
  else rect(0,0,W,H,grad(0,0,0,H,[[0,'#0a1425'],[.48,'#362825'],[1,'#090e1b']]));
  // Animated warm torches; the surrounding stonework is a cached vector asset.
  for(const x of [22,538]){
   const cy=269,flutter=Math.sin(t*.012+x)*5+Math.sin(t*.026+x)*2;
   const halo=cx.createRadialGradient(x,cy,0,x,cy,115);
   halo.addColorStop(0,'#ffcc7277');halo.addColorStop(.25,'#ef8c2d24');halo.addColorStop(1,'#e66b2500');
   rect(x-115,cy-115,230,230,halo);
   cx.save();cx.shadowColor='#ff7f25';cx.shadowBlur=20;
   cx.beginPath();cx.moveTo(x-13,cy+17);cx.bezierCurveTo(x-19,cy-21,x-10,cy-38+flutter,x,cy-70+flutter);
   cx.bezierCurveTo(x+13,cy-34+flutter,x+25,cy-10,x+12,cy+17);cx.closePath();cx.fillStyle='#fa9b34';cx.fill();cx.restore();
   cx.beginPath();cx.moveTo(x-5,cy+13);cx.bezierCurveTo(x-12,cy-18,x-3,cy-31+flutter,x,cy-46+flutter);
   cx.bezierCurveTo(x+8,cy-19,x+13,cy-5,x+6,cy+13);cx.closePath();cx.fillStyle='#fff2a8';cx.fill();
   rect(x-11,cy+19,22,10,'#46322c','#dfad65',3,2);
  }
  for(const p of this.particles){
   const py=(p.y+t*.006*p.spd)%H;
   const a=.10+.22*Math.pow(Math.sin(t*.0007+p.phase),2);
   ellipse(p.x+Math.sin(t*.0004+p.phase)*5,py,p.s*.65,p.s*.65,`rgba(250,214,132,${a})`);
  }
  // A very light shimmer along the hieroglyph panels.
  const q=Math.sin(t*.00055)*.5+.5;
  rect(47,15,466,216,`rgba(245,178,97,${.018+q*.020})`);
 }

  header(t){
  rect(111,106,338,110,grad(0,106,0,216,[[0,'#06132600'],[.25,'#041121b0'],[.75,'#041121db'],[1,'#04112100']]));
  // Golden title with two embossed outlines.
  const main=grad(0,98,0,183,[[0,'#fff5ca'],[.36,'#f5c975'],[.62,'#b97832'],[.82,'#ffe3a1'],[1,'#915e28']]);
  const emboss=(s,y,font)=>{
   cx.save();cx.textAlign='center';cx.textBaseline='middle';cx.font=`900 ${font}px Georgia,serif`;
   cx.shadowColor='#000';cx.shadowBlur=8;cx.shadowOffsetY=4;
   cx.lineWidth=5;cx.strokeStyle='#1d1520';cx.strokeText(s,280,y);
   cx.shadowBlur=0;cx.shadowOffsetY=0;cx.lineWidth=1.6;cx.strokeStyle='#fff1a9';cx.strokeText(s,280,y);
   cx.fillStyle=main;cx.fillText(s,280,y);cx.restore();
  };
  emboss('FARAÓ',145,52);emboss('ETERNO',190,43);
  line(125,200,435,200,'#cda15f',1.5);
  text('OS SEGREDOS DA ETERNIDADE',280,213,10,'#e5c486','center','Georgia',800,2);
  this.smallButton(49,66,68,'☰',()=>this.openInfo());
  this.smallButton(511,66,68,this.sound?'♫':'♪',()=>{this.sound=!this.sound;this.audio.on=this.sound;this.save();});
  // Sem indicador redundante de linhas acima dos tambores.
  if(this.feature)text('FREE SPINS  '+this.feature.left+' RESTANTES',280,235,13,'#a7fff0','center','Arial',900);
 }

  reelBoard(t){
  const x=20,y=251,w=520,h=410;
  // Preserve the Egyptian frame but do not obscure the temple image behind the reels.
  cx.save();cx.shadowColor='#000b';cx.shadowBlur=19;cx.shadowOffsetY=7;
  rect(x-5,y-7,w+10,h+16,null,'#dfb66a',13,4);cx.restore();
  rect(x-4,y-7,w+8,h+14,null,'#bb8648',11,2.5);
  rect(x+6,y+2,w-12,h-5,null,'#ad874d88',6,1.2);
  // Five unfilled reels with 15 fixed, square glass-cell borders.
  const cw=99,gh=132,left=33,top=258,interval=101,square=94;
  for(let c=0;c<5;c++){
   const rx=left+c*interval;
   cx.save();cx.beginPath();cx.rect(rx,top,cw,gh*3);cx.clip();
   const item=this.spinObject;
    if(this.spinning && item && !item.stopped[c]){
     // One continuous strip, from the previous grid to the committed result.
     // Integrated cosine acceleration/braking keeps velocity continuous.
     const p=clamp((t-item.start)/(item.duration+c*item.reelGap),0,1),a=.12,d=.32;
     let travel;
     if(p<a)travel=.5*p-.5*a/Math.PI*Math.sin(Math.PI*p/a);
     else if(p<=1-d)travel=p-a/2;
     else{const u=p-(1-d);travel=1-d-a/2+.5*u+.5*d/Math.PI*Math.sin(Math.PI*u/d);}
     const dist=item.steps[c]*travel/(1-(a+d)/2),step=Math.floor(dist),fraction=dist-step;
     for(let r=-1;r<4;r++){
      const symbol=item.show[c][item.steps[c]+3+r-step]||item.grid[c][Math.max(0,Math.min(2,r))];
      this.drawIcon(symbol,rx,top+(r+fraction)*gh,cw,gh,false,t);
     }
     // Keep the temple visible even while the symbol strip is in motion.
    } else {    const reelGrid=item&&item.stopped[c]?item.grid:this.grid;
    const landMs=t-this.landings[c];
    const landing=this.landings[c]&&landMs>=0&&landMs<300;
    const bounce=landing?-10*Math.pow(1-landMs/300,2)*Math.cos(landMs*.033):0;
    const index=this.reveal?this.reveal.reels.indexOf(c):-1;
    const stamp=this.reveal?this.reveal.start+this.reveal.preDelay+index*this.reveal.perReel:0;
    const raw=this.reveal&&index>=0?clamp((t-stamp)/this.reveal.perReel,0,1):1;
    const expandProgress=raw*raw*(3-2*raw);
    const expanding=Boolean(this.expanded[c] && (this.reveal?index>=0&&expandProgress>0:this.lastWasFree));
    const readyToGlow=!this.reveal || t>=this.reveal.end-100;
    for(let r=0;r<3;r++){
     const yy=top+r*gh;
     const win=readyToGlow&&this.winCells.includes(c+'-'+r);
     // One row at a time: transformation travels from the top to the bottom.
     const rowScan=expanding?clamp(expandProgress*3.18-r*.93,0,1):0;
     const rowEase=rowScan*rowScan*(3-2*rowScan);
     if(rowEase<1){cx.save();cx.globalAlpha=1-rowEase;
      this.drawIcon(reelGrid[c][r],rx,yy+bounce,cw,gh,win&&!expanding,t);cx.restore();}
     if(expanding && rowEase>0){
      cx.save();cx.globalAlpha=rowEase;
      const pulse=1+.055*Math.sin(Math.PI*rowEase);
      this.drawIcon(this.expanded[c].symbol,rx-(pulse-1)*cw/2,yy-(pulse-1)*gh/2,cw*pulse,gh*pulse,win,t);
      cx.restore();
      cx.save();
      cx.strokeStyle=`rgba(111,255,226,${.20+.55*rowEase})`;
      cx.shadowColor='#62ffe1';cx.shadowBlur=9;
      cx.lineWidth=2;cx.strokeRect(rx+5,yy+7,cw-10,gh-14);
      if(rowScan<.99){
       const scanY=yy+gh*rowScan;
       cx.fillStyle=`rgba(207,255,237,${.85-rowEase*.38})`;
       cx.fillRect(rx+7,scanY-2,cw-14,4);
      }
      cx.restore();
     }
    }
    if(expanding){
     const vertical=grad(rx,top,rx+cw,top,[[0,'#e3fff000'],[.42,'#bfffea2a'],[.50,'#8cffe599'],[.59,'#9cffdf22'],[1,'#e3fff000']]);
     cx.save();cx.globalAlpha=.8*(1-expandProgress)+.1;rect(rx+2,top+2,cw-4,gh*3-3,vertical);cx.restore();
     if(this.reveal&&expandProgress<1){
      const beamY=top+gh*3*expandProgress;
      cx.save();cx.shadowColor='#8bffef';cx.shadowBlur=18;
      rect(rx+4,beamY-5,cw-8,11,'#90fff0b8');cx.restore();
     }
    }
   }
   cx.restore();
   // Subtle, permanently transparent glass squares on top of moving artwork.
   for(let r=0;r<3;r++){
    const yy=top+r*gh+(gh-square)/2;
    cx.save();cx.shadowColor='#f5d18a';cx.shadowBlur=2;
    rect(rx+(cw-square)/2,yy,square,square,null,'#f4d38a60',4,1.05);
    cx.restore();
   }
   line(rx+2,top+4,rx+cw-2,top+4,'#f2d69181',1.1);
   line(rx+2,top+gh*3-4,rx+cw-2,top+gh*3-4,'#f2c5775e',1.2);
  }
  this.drawReelSeparators(left,top,interval,gh);
  // Decorative royal carved corners and teal gemstones.
  for(const [xx,yy,sgn] of [[25,252,1],[535,252,-1],[25,656,1],[535,656,-1]]){
   cx.save();cx.translate(xx,yy);cx.scale(sgn,yy<400?1:-1);
   cx.beginPath();cx.moveTo(0,27);cx.lineTo(0,0);cx.lineTo(27,0);
   cx.strokeStyle='#fff0b1';cx.lineWidth=5;cx.stroke();
   cx.beginPath();cx.moveTo(6,25);cx.lineTo(6,6);cx.lineTo(25,6);cx.strokeStyle='#8c6a3a';cx.lineWidth=2;cx.stroke();cx.restore();
  }
  for(const xx of [22,538])for(const yy of [258,647]){
   cx.save();cx.shadowColor='#50ffe6';cx.shadowBlur=8;
   ellipse(xx,yy,5,8,'#2ae3d0');ellipse(xx-1,yy-2,2,4,'#ceffff');cx.restore();
  }
  // Transparent touch targets follow the 15 symbol cells.
  if(!this.spinning&&!this.superseded)for(let c=0;c<5;c++)for(let row=0;row<3;row++){
   const symbol=this.grid[c][row];
   this.action(left+c*interval,top+row*gh,cw,gh,()=>this.showSymbolPay(symbol),'Ver prémios de '+(iconNames[symbol]||symbol));
  }
  this.drawActivePayline(t,left,top,cw,gh,interval);
  rect(23,662,514,11,grad(0,661,0,673,[[0,'#f6e0a4'],[.5,'#a3773e'],[1,'#4d301f']]),'#ffe8a6',4,1.5);
  if(this.feature){
   const keys=this.feature.symbols;
   rect(58,675,444,40,'#101c2cea','#4cc8be',10,1.5);
   text('EXPANSIVOS',116,694,11,'#f5d998','center','Arial',800);
   for(let i=0;i<keys.length;i++)this.drawMiniSymbol(keys[i],178+i*33,678,31);
  }
 }

  // Exactly four fixed Egyptian-gold hairline separators, rendered over the strip.
  drawReelSeparators(left,top,interval,gh){
   for(let c=1;c<5;c++){
    const xx=left+c*interval-1;
    cx.save();cx.shadowColor='#f5d390';cx.shadowBlur=6;
    line(xx,top+5,xx,top+gh*3-5,'#ecca85ba',1.35);
    cx.restore();
    line(xx+.75,top+5,xx+.75,top+gh*3-5,'#fff1b744',.65);
   }
  }

  drawActivePayline(t,left,top,cw,gh,interval){
  if(this.spinning || !this.lastResult?.lineWins?.length || this.featureIntro&&t<this.featureIntro.end)return;
  if(this.reveal&&t<this.reveal.end)return;
  // Follow the exact same 1.6 second rotation as the information under the reels.
  const wins=this.lastResult.lineWins;
  const chosen=wins[Math.floor(t/1600)%wins.length];
  const alpha=.60+.24*Math.sin(t*.006);
  const hue=chosen.expanded?'#7affeb':'#ffe2a0';
  const soften=Boolean(window.matchMedia && matchMedia('(prefers-reduced-motion: reduce)').matches);
  cx.save();cx.beginPath();cx.rect(left,top,5*interval-2,3*gh);cx.clip();
  cx.lineJoin='round';cx.lineCap='round';
  if(chosen.expanded){
   // Expansion pays from a set of reels, even if they are not consecutive.
   const found=this.lastResult.expanders.find(e=>e.symbol===chosen.symbol);
   const reels=found?found.reels:[];
   cx.strokeStyle=hue;cx.shadowColor='#64ffe5';cx.shadowBlur=soften?0:11;
   cx.lineWidth=3;cx.globalAlpha=alpha;
   for(const c of reels){
    const xx=left+c*interval;
    cx.strokeRect(xx+5,top+5,cw-10,3*gh-10);
    // Three discreet guides show that the entire reel is active.
    for(let r=0;r<3;r++){
     const yy=top+(r+.86)*gh;
     cx.beginPath();cx.moveTo(xx+19,yy);cx.lineTo(xx+cw-19,yy);cx.stroke();
    }
   }
   // Dotted bridge denotes non-consecutive winning reels, not an extra payline.
   if(reels.length>=2){
    cx.globalAlpha=.37;cx.lineWidth=2;cx.setLineDash([5,8]);
    cx.lineDashOffset=soften?0:-t*.014;
    const yy=top+gh*3-12;
    cx.beginPath();cx.moveTo(left+reels[0]*interval+cw/2,yy);
    cx.lineTo(left+reels[reels.length-1]*interval+cw/2,yy);cx.stroke();
    cx.setLineDash([]);
   }
  } else {
   const coords=Array.from({length:chosen.count},(_,c)=>({
     x:left+c*interval+cw/2,
     y:top+chosen.rows[c]*gh+gh*.81,
     bx:left+c*interval,
     by:top+chosen.rows[c]*gh
   }));
   if(coords.length){
    cx.strokeStyle=hue;cx.lineWidth=3.3;cx.shadowColor='#ffca63';
    cx.shadowBlur=soften?0:12;cx.globalAlpha=alpha;
    cx.beginPath();coords.forEach((p,i)=>i?cx.lineTo(p.x,p.y):cx.moveTo(p.x,p.y));cx.stroke();
    coords.forEach(p=>{
     cx.globalAlpha=.25+.12*Math.sin(t*.005);
     cx.lineWidth=1.7;cx.strokeRect(p.bx+5,p.by+6,cw-10,gh-12);
     cx.globalAlpha=.92;
     cx.beginPath();cx.arc(p.x,p.y,3.5,0,Math.PI*2);
     cx.fillStyle='#fff5c9';cx.fill();
    });
   }
  }
  cx.restore();
 }
 drawIcon(sym,x,y,w,h,win=false,t=performance.now()){
  cx.save();
  // Contain all original image proportions in one centred square: no crop or stretch.
  const box=Math.min(w-6,h-6),bx=x+(w-box)/2,by=y+(h-box)/2;
  if(win){
   const glow=.6+.4*Math.sin(t*.008+x*.2);
   cx.shadowColor='#f8d875';cx.shadowBlur=16+11*glow;
   const aura=cx.createRadialGradient(x+w*.5,y+h*.5,5,x+w*.5,y+h*.5,box*.58);
   aura.addColorStop(0,`rgba(255,233,156,${.26*glow})`);
   aura.addColorStop(1,'rgba(247,194,89,0)');
   rect(bx,by,box,box,aura);
  }
  // The TOMB (tower/sanctuary) symbol has its own continuously breathing gold light.
  // Keep the effect inside the cell, with no effect on reel outcomes or payouts.
  if(sym==='TOMB'){
   const beat=.5+.5*Math.sin(t*.0037+x*.009+y*.005);
   const strength=win?.54:.25;
   cx.save();
   const radial=cx.createRadialGradient(x+w/2,y+h/2,box*.08,x+w/2,y+h/2,box*.53);
   radial.addColorStop(0,`rgba(255,242,173,${strength+.17*beat})`);
   radial.addColorStop(.52,`rgba(255,191,66,${.12+.17*beat})`);
   radial.addColorStop(1,'rgba(255,171,42,0)');
   cx.fillStyle=radial;cx.fillRect(bx,by,box,box);
   cx.restore();
  }
  const img=this.images[sym];
  if(img?.complete&&img?.naturalWidth&&img.naturalHeight){
   const premium=E.NORMAL.slice(5).includes(sym)||sym==='TOMB';
   const maxSide=box*(premium?.97:.92);
   const scale=Math.min(maxSide/img.naturalWidth,maxSide/img.naturalHeight);
   const iw=img.naturalWidth*scale,ih=img.naturalHeight*scale;
   if(sym==='TOMB'){
    const beat=.5+.5*Math.sin(t*.0037+x*.009+y*.005);
    cx.save();cx.shadowColor='#ffd36b';cx.shadowBlur=9+19*beat+(win?9:0);
    cx.drawImage(img,x+(w-iw)/2,y+(h-ih)/2,iw,ih);
    cx.restore();
    cx.save();cx.globalCompositeOperation='screen';cx.globalAlpha=.055+.18*beat+(win?.08:0);
    cx.drawImage(img,x+(w-iw)/2,y+(h-ih)/2,iw,ih);cx.restore();
   }else cx.drawImage(img,x+(w-iw)/2,y+(h-ih)/2,iw,ih);
  }else{text(sym,x+w/2,y+h/2,26,'#fce8b4');}
  cx.restore();
  if(win){
   cx.save();cx.globalAlpha=.52+.33*Math.sin(t*.009);
   cx.shadowColor='#ffe79c';cx.shadowBlur=10;
   rect(bx+2,by+2,box-4,box-4,null,'#ffdfa0',5,2);
   cx.restore();
  }
 }

  drawMiniSymbol(sym,x,y,size){
  if(this.images[sym]?.complete&&this.images[sym]?.naturalWidth)cx.drawImage(this.images[sym],x,y,size,size);
  else text(sym,x+size/2,y+size/2,10,'#ffe7a8');
 }
 emitBurst(x,y,n,color,when){
  for(let i=0;i<n;i++){
   if(this.fxParticles.length>=125)this.fxParticles.shift();
   const a=rnd()*Math.PI*2,speed=42+rnd()*152;
   this.fxParticles.push({x,y,vx:Math.cos(a)*speed,vy:Math.sin(a)*speed-34,start:when,life:390+rnd()*570,size:1.2+rnd()*2.8,color});
  }
 }
 drawFx(t){
  this.fxParticles=this.fxParticles.filter(p=>t<p.start+p.life);
  cx.save();cx.beginPath();cx.rect(26,250,508,417);cx.clip();
  for(const p of this.fxParticles){
   if(t<p.start)continue;
   const s=(t-p.start)/1000,life=1-(t-p.start)/p.life;
   cx.globalAlpha=Math.max(0,life*life);
   const x=p.x+p.vx*s,y=p.y+p.vy*s+90*s*s;
   cx.fillStyle=p.color;
   cx.fillRect(x,y,Math.max(.5,p.size*life),Math.max(1,p.size*2*life));
  }cx.restore();
 }
 statusBar(t){
  const offset=this.feature?24:0;
  const by=704+offset;
  cx.save();cx.shadowColor='#000b';cx.shadowBlur=12;cx.shadowOffsetY=4;
  rect(40,by,480,70,'#141624','#d2a563',10,2);cx.restore();
  rect(44,by+4,472,62,grad(0,by,0,by+66,[[0,'#675037'],[.12,'#29212a'],[.8,'#121927'],[1,'#755435']]),'#f1ca82',7,1.5);
  for(const x of [65,495]){ellipse(x,by+34,4.5,4.5,'#22b5ad');ellipse(x-1,by+32,1.5,1.5,'#eaffee');}
  if(this.lastWin>0||this.feature){
   text(this.feature?'BÓNUS ACUMULADO':this.lastWasFree?'PRÉMIO FREE SPIN':'PRÉMIO DA JOGADA',280,by+18,12,'#ead2a4','center','Arial',850);
   let shown=this.feature?this.feature.total:this.lastWin;
   if(this.winCount&&this.winCount.amount>0){const u=clamp((t-this.winCount.start)/this.winCount.duration,0,1);shown=(this.feature?this.feature.total-this.lastWin:0)+Math.round(this.winCount.amount*(1-Math.pow(1-u,3))*100)/100;}
   text(money(shown),280,by+46,29,'#fff1b5','center','Georgia',850,7);
  }else text(this.feature?'OS SÍMBOLOS REVELAM O TESOURO':'O PRÓXIMO SEGREDO ESPERA POR TI',280,by+35,16,'#f6d99d','center','Georgia',800,3);
  if(this.lastResult?.lineWins?.length&&!this.spinning){
   const picked=this.lastResult.lineWins[Math.floor(t/1600)%this.lastResult.lineWins.length];
   text(`${picked.expanded?'EXPANSÃO · '+(picked.line+1):'LINHA '+(picked.line+1)}  ·  ${picked.count} SÍMBOLOS  ·  ${money(picked.amount)}`,280,by+77,11,picked.expanded?'#84fff0':'#f6d9a0','center','Arial',850);
  }
  const u=this.winCount?clamp((t-this.winCount.start)/this.winCount.duration,0,1):1;
  const win=this.winCount?this.winCount.amount*(1-Math.pow(1-u,3)):this.lastWin;
  const arr=[['SALDO',money(this.balance)],['APOSTA',money(this.bet)],['PRÉMIO',money(win)]];
  for(let i=0;i<arr.length;i++){
   const bx=29+i*170,by2=814;
   cx.save();cx.shadowColor='#000a';cx.shadowBlur=8;cx.shadowOffsetY=3;
   rect(bx,by2,163,72,'#171622','#cba064',10,2);cx.restore();
   rect(bx+3,by2+3,157,66,grad(0,by2,0,by2+66,[[0,'#725538'],[.23,'#292632'],[.74,'#101824'],[1,'#2d2828']]),'#75563f',7,1);
   line(bx+22,by2+26,bx+141,by2+26,'#9d774c88',1);
   text(arr[i][0],bx+81,by2+15,11,'#d9b983','center','Arial',850);
   const sz=arr[i][1].length>12?16:arr[i][1].length>9?18:22;
   text(arr[i][1],bx+81,by2+48,sz,i===2?'#88fff0':'#ffecb2','center','Georgia',850,3);
  }
 }

  drawControl(x,y,w,h,label,action,opts={}){
  const disabled=opts.disabled,primary=opts.primary;
  cx.save();cx.shadowColor=primary?'#e2a64698':'#0009';cx.shadowBlur=primary?15:5;cx.shadowOffsetY=3;
  rect(x,y,w,h,'#171a26',disabled?'#6d5e4b':primary?'#ffe7a6':'#ca9c57',primary?15:11,primary?3.5:2);cx.restore();
  const base=primary?grad(x,y,x,y+h,[[0,'#fff2b7'],[.14,'#c99442'],[.38,'#835427'],[.72,'#54321e'],[1,'#2b2530']]):grad(x,y,x,y+h,[[0,'#7b5c3d'],[.17,'#352a2d'],[.78,'#1a1b28'],[1,'#18131f']]);
  rect(x+3,y+3,w-6,h-6,base,primary?'#f5d38b':'#61513e',primary?11:8,1);
  line(x+10,y+8,x+w-10,y+8,primary?'#fff0be8a':'#ffe1a94f',1.5);
  if(primary){
   cx.save();cx.shadowColor='#ffe093';cx.shadowBlur=9;
   ellipse(x+11,y+h/2,2.5,2.5,'#ffdd90');ellipse(x+w-11,y+h/2,2.5,2.5,'#ffdd90');cx.restore();
  }
  const main=disabled?'#817866':primary?'#fff1bf':'#f9ddb0';
  if(opts.sub){
   text(label,x+w/2,y+h*.36,opts.size||16,main,'center','Arial',900);
   text(opts.sub,x+w/2,y+h*.70,15,'#85ebdc','center','Georgia',900);
  } else text(label,x+w/2,y+h/2,opts.size||16,main,'center',primary?'Georgia':'Arial',900,primary?3:1);
  if(!disabled)this.action(x,y,w,h,action,label==='−'?'Diminuir aposta':label==='+'?'Aumentar aposta':label);
 }

  smallButton(x,y,size,label,action){
  cx.save();cx.shadowColor='#000b';cx.shadowBlur=6;
  ellipse(x,y,size*.52,size*.52,'#191827');cx.restore();
  ellipse(x,y,size*.50,size*.50,grad(x,y-size/2,x,y+size/2,[[0,'#ffe8a2'],[.35,'#af743e'],[.78,'#503624'],[1,'#8f6a3d']]));
  ellipse(x,y,size*.42,size*.42,'#162332');
  ellipse(x,y,size*.36,size*.36,'#23303b');
  text(label,x,y,25,'#ffe6aa','center','Georgia',800,2);
  this.action(x-size/2,y-size/2,size,size,action,label==='☰'?'Menu':'Som');
 }

  controls(){
  const locked=this.spinning||this.feature||this.superseded;
  this.drawControl(29,900,72,72,'−',()=>this.changeBet(-1),{size:32,disabled:locked});
  this.drawControl(109,900,72,72,'+',()=>this.changeBet(1),{size:32,disabled:locked});
  this.drawControl(198,893,164,84,this.paused?'RETOMAR':this.spinning?'PARAR':'GIRAR',()=>this.startSpin(),{primary:true,size:this.paused?20:29,disabled:!this.ready||this.superseded});
  this.drawControl(379,900,152,72,this.auto?'PARAR AUTO':'AUTO ×20',()=>this.setAuto(),{size:14,disabled:!!this.feature||this.superseded});
  text('V1.0',29,989,10,'#e1be7e','left','Arial',800);
  text('JOGADA #'+this.ledger.sequence,531,991,11,'#d7b77d','right','Arial',700);
  if(!this.storageOK)text('SESSÃO SEM GRAVAÇÃO',280,795,11,'#ffbc83','center','Arial');
 }

 overlay(t){
  if(t>this.messageUntil || this.featureIntro&&t>=this.featureIntro.start&&t<=this.featureIntro.end)return;
  const left=clamp((this.messageUntil-t)/270,0,1);
  const isExpansion=this.reveal&&t<this.reveal.end;
  const y=isExpansion?673:380,h=isExpansion?52:112;
  const color=this.flashKind==='cyan'?'#63fff0':this.flashKind==='red'?'#ff8b72':'#f8d18b';
  cx.save();cx.globalAlpha=clamp(left,.02,1);
  cx.shadowColor=color;cx.shadowBlur=15;
  rect(41,y,478,h,'#0d1826eb',color,13,2.6);cx.shadowBlur=0;
  rect(47,y+6,466,h-12,null,'#947848',9,1);
  const move=clamp((this.messageUntil-t)/((isExpansion?400:1000)),0,1);
  cx.translate(280,y+h/2);cx.scale(.97+.03*move,.97+.03*move);
  text(this.message,0,0,this.message.length>31?16:this.message.length>24?18:22,color,'center','Georgia',900,7);
  cx.restore();
 }

  introOverlay(t){
  const intro=this.featureIntro;
  if(!intro||t<intro.start||t>intro.end)return;
  const p=clamp((t-intro.start)/(intro.end-intro.start),0,1);
  const fade=clamp(p*7,0,1)*clamp((1-p)*8,0,1);
  cx.save();cx.globalAlpha=fade;
  cx.shadowColor='#000b';cx.shadowBlur=32;
  rect(40,323,480,288,'#061a2aed','#ffdb83',17,4);cx.shadowBlur=0;
  rect(48,332,464,270,'#122738ee','#48cbbd',10,2);
  const halo=cx.createRadialGradient(280,469,15,280,469,156);
  halo.addColorStop(0,'#f9d88a8b');halo.addColorStop(.48,'#53e7d344');halo.addColorStop(1,'#3cc6c200');
  ellipse(280,469,153,153,halo);
  cx.save();cx.translate(280,460);cx.rotate((t-intro.start)*.00042);
  for(let i=0;i<18;i++){cx.rotate(Math.PI/9);line(0,-120,0,-153,'#efcd84',2);}
  cx.restore();
  text('NOVO SÍMBOLO EXPANSIVO',280,367,20,'#ffe5a4','center','Georgia',900,6);
  const float=Math.sin(p*Math.PI*4)*4;
  this.drawMiniSymbol(intro.symbol,198,393+float,164);
  text(iconNames[intro.symbol]||intro.symbol,280,568,19,'#8bffea','center','Georgia',850,4);
  cx.restore();
 }

}
window.FaraoGame=Game;
window.__faraoGame = new Game();
document.documentElement.dataset.faraoVersion='1.0';
})();
