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
const iconNames={TOMB:'Pirâmide dourada · WILD / SCATTER',KING:'Faraó',ANUBIS:'Anúbis',HORUS:'Escaravelho alado',MASK:'Livro sagrado / Ankh',A:'A',K:'K',Q:'Q',J:'J','10':'10'};
const bets=S.BETS;
const PALETTE={gold:'#f3d389',bright:'#ffeeb1',dark:'#2a1715',teal:'#54d6ce',red:'#d85c63'};
const money = val => (Math.round((val+Number.EPSILON)*100)/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2}) + ' €';
const euro = val => (Math.round((val+Number.EPSILON)*100)/100).toLocaleString('pt-PT',{style:'currency',currency:'EUR'});
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
  this.balance=0;this.betIndex=4;this.lines=10;this.grid=E.makeGrid(rnd);
  this.spinObject=null;this.spinning=false;this.lastResult=null;this.lastWin=0;this.feature=null;this.lastBaseWin=0;this.lastWasFree=false;
  this.currentRound=0;this.winCells=[];this.expanded={};this.auto=0;this.turbo=false;this.sound=true;this.soundMode='all';
  this.reveal=null;this.featureIntro=null;this.winCount=null;
  this.started=false;this.message='PRIME GIRAR PARA ENTRAR NO TEMPLO';this.messageUntil=0;
  this.flashUntil=0;this.flashKind='';this.nextActionAt=0;this.rng=rnd;this.history=[];this.gambleTimes=0;
  this.particles=Array.from({length:42},(_,i)=>({x:rnd()*W,y:rnd()*H,s:.3+rnd()*2.5,phase:rnd()*6.283,spd:2+rnd()*10}));
  this.load();this.initInputs();this.loadArt();this.resize();window.addEventListener('resize',()=>this.resize());
  requestAnimationFrame(t=>this.frame(t));
 }
 get bet(){return S.stake(this.ledger)}
 load(){
  try{
   const raw=localStorage.getItem(STORE_KEY);
   this.ledger=raw?S.restore(JSON.parse(raw)):S.migrate(QA?null:JSON.parse(localStorage.getItem('farao-eterno-v1')||'null'));
   if(this.ledger.pending){S.settle(this.ledger);this.recovered=true;}
   if(this.ledger.payout){S.finishPayout(this.ledger);this.recovered=true;}
   // Sessões antigas podem trazer letras duplicadas já pagas. Corrigir
   // APENAS o ecrã inicial, depois de liquidar qualquer jogada pendente.
   // Saldo, prémios, histórico e resultados contabilizados ficam intactos.
   if(this.ledger.grid.some(col=>new Set(col).size!==3)){
    this.ledger.grid=this.ledger.grid.map((col,reel)=>{
     const used=new Set();
     return col.map((symbol,row)=>{
      if(!used.has(symbol)){used.add(symbol);return symbol;}
      const choices=E.NORMAL.filter(candidate=>!used.has(candidate));
      const replacement=choices[(reel+row)%choices.length];
      used.add(replacement);return replacement;
     });
    });
    // Evitar destacar linhas antigas após corrigir só o desenho guardado.
    this.ledger.last=null;
   }
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
  this.soundMode=s.settings.soundMode||'all';this.sound=this.soundMode!=='mute';this.audio.on=this.sound;this.turbo=s.settings.turbo;
  if(s.last)this.lastResult=s.last.result;
 }
 save(){
  if(this.superseded)return;
  this.ledger.settings={sound:this.sound,music:this.soundMode==='all',soundMode:this.soundMode,turbo:this.turbo};
  this.ledger.owner=this.owner;
  try{localStorage.setItem(STORE_KEY,JSON.stringify(this.ledger));this.storageOK=true;}catch(_){this.storageOK=false;}
 }

 get soundModeLabel(){return {all:'Som completo',effects:'Só tambores e símbolos',mute:'Tudo desligado'}[this.soundMode]||'Som completo';}
 cycleSoundMode(){
  const modes=['all','effects','mute'];
  this.setSoundMode(modes[(modes.indexOf(this.soundMode)+1)%modes.length]);
 }
 setSoundMode(mode){
  if(!['all','effects','mute'].includes(mode)||this.superseded)return;
  this.soundMode=mode;
  this.sound=mode!=='mute';
  this.audio.on=this.sound;
  this.ledger.settings.music=mode==='all';
  this.ledger.settings.sound=this.sound;
  this.ledger.settings.soundMode=mode;
  this.audio.setMasterMuted?.(mode==='mute');
  if(mode==='all'){
   this.audio.init();
   if(!this.paused)this.audio.startMusic?.();
  }else{
   this.audio.stopMusic?.();
   this.torchFire?.stop?.();
  }
  if(mode==='mute'){
   this.audio.stopReelWhirr?.();
   this.audio.stopExpansionScan?.();
  }
  this.save();
  this.notice('ÁUDIO · '+this.soundModeLabel.toUpperCase(),1250,'gold');
  this._primaryButtons?.sound?.setAttribute('aria-label','Áudio: '+this.soundModeLabel+'. Toque para mudar.');
 }
 announce(s){$('screen-msg').textContent=s;}
 notice(s,time=1750,kind='gold'){this.message=s;this.messageUntil=performance.now()+time;this.flashUntil=this.messageUntil;this.flashKind=kind;this.announce(s);}
 initInputs(){
  // Capture touches before the transparent button overlay: the active visual
  // extension supplies the active reel layout, keeping touch and visual geometry aligned.

  canvas.addEventListener('pointerdown',e=>{e.preventDefault();this.audio.init();let b=canvas.getBoundingClientRect();const x=(e.clientX-b.left-ox)/sc,y=(e.clientY-b.top-oy)/scY;
   if(mod.classList.contains('hidden')===false)return;
   // Direct canvas hit-testing keeps symbols tappable on iPhone Safari,
   // independently of the transparent accessibility control layer.
   const reel=this.visualV11?.layout||{left:35,top:284,cw:95,gh:129,step:101};
   if(!this.spinning&&!this.superseded&&x>=reel.left&&x<reel.left+5*reel.step&&y>=reel.top&&y<reel.top+3*reel.gh){
    const col=Math.floor((x-reel.left)/reel.step),row=Math.floor((y-reel.top)/reel.gh);
    if(col>=0&&col<5&&row>=0&&row<3){
     this.audio.click();this.showSymbolPay(this.grid[col][row]);return;
    }
   }
   for(const target of [...this.targets].reverse()){const {x:bx,y:by,w,h,action}=target;if(x>=bx&&y>=by&&x<=bx+w&&y<=by+h){this.audio.click();action();break;}}
  },{passive:false});
  document.addEventListener('keydown',ev=>{if(ev.key==='Escape'){this.closeModal();return;}if(mod.classList.contains('hidden')===false)return;
   if(ev.code==='Space'&&!ev.repeat&&!['BUTTON','INPUT','SELECT','A'].includes(ev.target.tagName)){ev.preventDefault();this.audio.init();this.advanceStage();}
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
  // Os símbolos HD aprovados devem permanecer como prioridade absoluta.
  // Safari com ligação lenta não pode trocar PNG/WebP pelos SVG antigos.
  // Apenas erros efetivos de carregamento ativam as imagens de reserva.
  this.artLoaded=0;this.artTotal=E.ALL.length;this.artFailed=[];
  this.ready=false;this.artTimedOut=false;
  const finished=new Set(),timers=new Map();
  // Limite rígido: Safari não pode prender o jogo à espera de um PNG.
  // As imagens atrasadas continuam a aparecer assim que forem carregadas.
  const openGame=setTimeout(()=>{
    if(!this.ready){this.artTimedOut=true;this.ready=true;}
  },10000);
  const done=(key,ok)=>{
   if(finished.has(key))return;
   finished.add(key);clearTimeout(timers.get(key));
   this.artLoaded=finished.size;
   if(!ok)this.artFailed.push(key);
   // Uma falha de rede nunca impede abrir a máquina.
   if(finished.size===E.ALL.length){
    clearTimeout(openGame);
    this.ready=true;
   }
  };
  const arm=(key,im)=>{
   clearTimeout(timers.get(key));
   timers.set(key,setTimeout(()=>{
    if(finished.has(key))return;
    // Não substituir a arte original por SVG só por demora de rede.
    // Só o SVG de reserva é dado como falhado depois do timeout.
    // A imagem HD pode terminar de carregar mesmo após abrir o jogo.
    if(im.fallback)done(key,false);
   },8500));
  };
  const fallback=(key,im)=>{
   if(finished.has(key))return;
   if(im.fallback){done(key,false);return;}
   im.fallback=true;
   // Os SVG de reserva existem no repositório e são leves.
   im.src='assets/'+key.toLowerCase()+'.svg?v=loading-fallback-3';
   arm(key,im);
  };
  for(const key of E.ALL){
   const im=new Image();
   im.onload=()=>{if(im.naturalWidth>0)done(key,true);else fallback(key,im);};
   im.onerror=()=>fallback(key,im);
   this.images[key]=im;
   im.src=(key!=='TOMB'&&window.FARAO_CUSTOM_SYMBOLS?.[key])||
     (key==='TOMB'?'assets/tomb.webp?v=originais-1.3.51':'assets/'+(E.NORMAL.slice(0,5).includes(key)?key+'.png?v=originais-1.3.51':key.toLowerCase()+(E.NORMAL.slice(5).includes(key)?'.webp?v=originais-1.3.51':'.svg?v=originais-1.3.51')));
   arm(key,im);
  }
  // O Ankh é uma variante VISUAL do Livro; a tabela de prémios continua igual.
  if(window.FARAO_CUSTOM_SYMBOLS?.MASK_ALT){
   const ankh=new Image();
   ankh.src=window.FARAO_CUSTOM_SYMBOLS.MASK_ALT;
   this.images.MASK_ALT=ankh;
  }
  const img=new Image();
  img.onload=()=>{
   // Restaurado o cenário original preferido, com as linhas douradas laterais
   // corrigidas e sem alterar a moldura ou o logótipo 3D sobre os tambores.
   // The thin gold line beside the title is baked into the background artwork.
   // Inpaint only the two side strips, leaving the title and the temple intact.
   const off=document.createElement('canvas');off.width=W;off.height=H;
   const oc=off.getContext('2d',{willReadFrequently:true});
   oc.drawImage(img,0,0,W,H);
   for(const [x0,x1] of [[116,224],[365,444]]){
    const top=192,bottom=205;
    const upper=oc.getImageData(x0,top-2,x1-x0,1).data;
    const lower=oc.getImageData(x0,bottom+2,x1-x0,1).data;
    const area=oc.getImageData(x0,top,x1-x0,bottom-top+1);
    for(let y=0;y<=bottom-top;y++)for(let x=0;x<x1-x0;x++){
     const p=(y*(x1-x0)+x)*4,q=x*4,f=(y+1)/(bottom-top+2);
     for(let ch=0;ch<3;ch++)area.data[p+ch]=Math.round(upper[q+ch]*(1-f)+lower[q+ch]*f);
     area.data[p+3]=255;
    }
    oc.putImageData(area,x0,top);
   }
   // Opção A: conservar exatamente a fotografia e toda a composição
   // do templo original. Apenas dar relevo, calor às tochas e profundidade.
   // O efeito é processado UMA vez no carregamento (não a cada frame).
   const polished=document.createElement('canvas');
   polished.width=W;polished.height=H;
   const pc=polished.getContext('2d');
   pc.filter='contrast(1.13) saturate(1.10) brightness(.97)';
   pc.drawImage(off,0,0,W,H);
   pc.filter='none';
   const vignette=pc.createRadialGradient(280,430,155,280,490,640);
   vignette.addColorStop(0,'rgba(10,13,22,0)');
   vignette.addColorStop(.68,'rgba(7,12,21,.045)');
   vignette.addColorStop(1,'rgba(4,8,17,.30)');
   pc.fillStyle=vignette;pc.fillRect(0,0,W,H);
   // Luz lateral muito subtil; não desenha novas colunas nem objetos.
   for(const x of [29,531]){
    const glow=pc.createRadialGradient(x,289,4,x,289,184);
    glow.addColorStop(0,'rgba(255,172,75,.115)');
    glow.addColorStop(.5,'rgba(255,147,58,.035)');
    glow.addColorStop(1,'rgba(255,147,58,0)');
    pc.fillStyle=glow;pc.fillRect(x-184,105,368,368);
   }
   this.temple=polished;
  };
  img.onerror=()=>{
   if(!img.fallback){img.fallback=true;img.src='assets/temple_back.svg';}
  };
  img.src='assets/temple.webp?v=1.3.14';
 }

  action(x,y,w,h,fn,label='Ação'){this.targets.push({x,y,w,h,action:fn,label});}
 syncTargets(){
  // Dedicated native buttons above the visual overlay, so Safari taps always work.
  if(!this._symbolOverlay){
   const layer=document.createElement('div');
   layer.id='symbol-hit-layer';
   layer.style.cssText='position:absolute;inset:0;z-index:5;pointer-events:none';
   $('stage').appendChild(layer);
   this._symbolButtons=[];
   for(let col=0;col<5;col++)for(let row=0;row<3;row++){
    const button=document.createElement('button');button.type='button';
    button.style.cssText='position:absolute;transform:translate(-50%,-50%);border:0;background:transparent;color:transparent;pointer-events:auto;touch-action:manipulation;padding:0;cursor:pointer';
    button.addEventListener('pointerdown',ev=>{
     ev.preventDefault();ev.stopPropagation();
     if(this.spinning||this.superseded||!mod.classList.contains('hidden'))return;
     this.showSymbolPay(this.grid[col][row]);
    });
    for(const eventName of ['pointerup','pointercancel','pointerleave','lostpointercapture']){
     button.addEventListener(eventName,()=>this.hideSymbolPay());
    }

    layer.appendChild(button);this._symbolButtons.push({button,col,row});
   }
   // iOS Safari may deliver release to the document rather than the button.
   // Listen in capture phase globally, including touchend, to hide instantly.
   const release=()=>this.hideSymbolPay();
   for(const eventName of ['pointerup','pointercancel','touchend','touchcancel','mouseup','blur']){
    window.addEventListener(eventName,release,{capture:true,passive:true});
   }
   document.addEventListener('visibilitychange',()=>{if(document.hidden)release();});
   this._symbolOverlay=layer;
  }
  const canInspect=!this.spinning&&!this.superseded&&mod.classList.contains('hidden');
  this._symbolOverlay.style.pointerEvents=canInspect?'auto':'none';
  const reel=this.visualV11?.layout||{left:35,top:284,cw:95,gh:129,step:101};
  for(const {button,col,row} of this._symbolButtons){
   button.style.left=(ox+(reel.left+col*reel.step+reel.cw/2)*sc)+'px';
   button.style.top=(oy+(reel.top+row*reel.gh+reel.gh/2)*scY)+'px';
   button.style.width=(reel.cw*sc)+'px';button.style.height=(reel.gh*scY)+'px';
   button.style.pointerEvents=canInspect?'auto':'none';
   button.setAttribute('aria-label','Consultar prémio de '+(iconNames[this.grid[col]?.[row]]||'símbolo'));
  }
  // The symbol overlay must never steal input from the header or spin controls.
  // Keep the reel-only hit layer beneath the native game controls.
  this._symbolOverlay.style.zIndex='2';
    // Independent, fixed-position native controls for Safari. Unlike the
  // redraw-generated targets these are never removed/recycled between frames.
  if(!this._primaryButtons){
   const layer=document.createElement('div');
   layer.id='primary-touch-controls';
   layer.style.cssText='position:absolute;inset:0;z-index:9;pointer-events:none';
   $('stage').appendChild(layer);
   this._primaryButtons={};
   for(const [key,label] of [['menu','Abrir menu'],['sound','Alternar som'],['spin','Girar']]){
    const b=document.createElement('button');
    b.type='button';b.setAttribute('aria-label',label);
    b.style.cssText='position:absolute;transform:translate(-50%,-50%);pointer-events:auto;touch-action:manipulation;border:0;background:transparent;color:transparent;font-size:1px;cursor:pointer;padding:0';
    const invoke=()=>{
     if(!mod.classList.contains('hidden')||this.superseded)return;
     this.audio.init();
     if(key==='menu')this.openInfo();
     else if(key==='sound')this.cycleSoundMode();
     else this.advanceStage();
    };
    b.addEventListener('pointerdown',e=>{e.preventDefault();e.stopPropagation();invoke();},{passive:false});
    b.addEventListener('click',e=>{if(e.detail===0)invoke();});
    layer.appendChild(b);this._primaryButtons[key]=b;
   }
   this._primaryLayer=layer;
  }
  const positions={menu:[49,66,76,76],sound:[511,66,76,76],spin:[280,878,146,146]};
  for(const [key,b] of Object.entries(this._primaryButtons)){
   const [x,y,w,h]=positions[key];
   b.style.left=(ox+x*sc)+'px';b.style.top=(oy+y*scY)+'px';
   b.style.width=(w*sc)+'px';b.style.height=(h*scY)+'px';
   b.style.pointerEvents=this.superseded||!mod.classList.contains('hidden')?'none':'auto';
   if(key==='sound')b.setAttribute('aria-label','Áudio: '+this.soundModeLabel+'. Toque para mudar.');
   if(key==='spin')b.setAttribute('aria-label','Girar ou avançar para a próxima etapa do jogo');
  }
    const root=$('touch-controls');
  while(root.children.length>this.targets.length)root.lastChild.remove();
  this.targets.forEach((target,i)=>{
   let b=root.children[i];if(!b){b=document.createElement('button');b.type='button';b.className='touch-target';root.appendChild(b);}
   b.setAttribute('aria-label',target.label);b.textContent=target.label;
   b.style.cssText=`position:absolute;pointer-events:auto;touch-action:manipulation;z-index:1;left:${ox+(target.x+target.w/2)*sc}px;top:${oy+(target.y+target.h/2)*sc}px;width:${Math.max(44,target.w*sc)}px;height:${Math.max(44,target.h*scY)}px`;
   // Bind once to the native button; read the current action after every redraw.
   b._faraoAction=target.action;
   if(!b._faraoBound){
    b._faraoBound=true;
    b.addEventListener('pointerdown',ev=>{
     if(!mod.classList.contains('hidden')||this.superseded)return;
     ev.preventDefault();ev.stopPropagation();
     this.audio.init();this.audio.click();b._faraoAction?.();
    },{passive:false});
    b.addEventListener('click',ev=>{
     if(ev.detail!==0)return; // keyboard/accessibility activation
     if(!mod.classList.contains('hidden')||this.superseded)return;
     this.audio.init();this.audio.click();b._faraoAction?.();
    });
   }
  });
 }

 // O botão GIRAR é também AVANÇAR: salta apenas a apresentação
 // da etapa atual. Nenhum resultado é alterado; créditos diferidos
 // continuam a ser liquidados exclusivamente por creditNext() no frame.
 shiftPresentation(delta){
  if(!Number.isFinite(delta)||delta<=0)return;
  const seen=new Set();
  const shift=(item,keys)=>{
   if(!item||seen.has(item))return;
   seen.add(item);
   for(const key of keys)if(Number.isFinite(item[key]))item[key]-=delta;
  };
  if(this.sequencePlayback){
   shift(this.sequencePlayback,['start','end']);
   this.sequencePlayback.stages?.forEach(st=>shift(st,['start','end','creditAt']));
  }
  if(this.reveal){
   shift(this.reveal,['start','end']);
   this.reveal.stages?.forEach(st=>shift(st,['start','end','creditAt']));
  }
  if(this.winCount){
   shift(this.winCount,['start']);
   this.winCount.stages?.forEach(st=>shift(st,['start','end']));
  }
  if(this.featureIntro)shift(this.featureIntro,['start','end']);
  this.nextActionAt=Math.max(0,this.nextActionAt-delta);
 }
 advanceStage(){
  if(this.superseded||!this.ready||!mod.classList.contains('hidden'))return;
  this.audio.init();
  const t=performance.now();
  if(this.paused){
   this.startSpin();
   return;
  }
  // 1. Durante o giro, mostrar de imediato o resultado JÁ SORTEADO.
  // O finishSpin normal (e os seus wrappers) tratam do pagamento único.
  if(this.spinning&&this.spinObject){
   const item=this.spinObject;
   item.stopped.fill(true);
   for(let i=0;i<5;i++)if(!this.landings[i])this.landings[i]=t;
   this.audio.stopReelWhirr?.();
   this.audio.tick?.(4);
   this.finishSpin();
   return;
  }
  // 2. Avançar uma linha/grupo do payout mantendo os seus créditos
  // atómicos e sequenciais. O frame vai processar os grupos ultrapassados.
  const play=this.sequencePlayback;
  if(play?.stages?.length&&t<play.end){
   // Depois de avançar para uma linha, deixar os seus símbolos aparecer:
   // cliques rápidos nunca devem saltar a combinação seguinte (ex. AAA).
   if(t<(this._lineAdvanceGuardUntil||0))return;
   const stages=play.stages;
   const current=stages.findIndex(st=>t>=st.start&&t<=st.end);
   const next=current>=0?stages[current+1]:stages.find(st=>st.start>t);
   const dest=next?next.start+Math.min(65,next.preDelay*.30):play.end+15;
   if(dest>t){
    this.shiftPresentation(dest-t);
    if(next?.kind==='line'&&next.win){
     const endOfReveal=next.start+(next.preDelay||0)+
       (next.reels?.length||next.count||3)*(next.perReel||390);
     this._lineAdvanceGuardUntil=Math.max(t+600,endOfReveal+130);
    }else this._lineAdvanceGuardUntil=0;
   }
   return;
  }
  // Se o frame ainda não creditou todos os grupos, não iniciar outro giro.
  if(this.ledger.payout)return;
  // 3. No jogo base, avançar uma linha de cada vez até à última.
  const lines=this.basePaylineDisplay;
  if(lines?.length&&!this.lastWasFree&&t<lines.at(-1).end){
   const current=lines.findIndex(st=>t>=st.start&&t<st.end);
   const next=current>=0?lines[current+1]:lines.find(st=>st.start>t);
   const dest=next?next.start+45:lines.at(-1).end+15;
   const delta=dest-t;
   if(delta>0){
    for(const st of lines){st.start-=delta;st.end-=delta;}
    this.nextActionAt=Math.max(0,this.nextActionAt-delta);
   }
   return;
  }
  // 4. Passar a página do livro sem pedir uma segunda confirmação.
  if(this.featureIntro&&t<this.featureIntro.end){
   const delta=this.featureIntro.end+15-t;
   this.shiftPresentation(delta);
   return;
  }
  // 5. Animações restantes (expansão / contagem / celebração).
  if(this.reveal&&t<this.reveal.end){
   const delta=this.reveal.end+15-t;
   this.shiftPresentation(delta);
   return;
  }
  if(this.winCount&&t<this.winCount.start+this.winCount.duration){
   const delta=this.winCount.start+this.winCount.duration+15-t;
   this.shiftPresentation(delta);
   return;
  }
  // Aguardas após uma etapa podem ser ultrapassadas sem alterar RNG.
  this.nextActionAt=Math.min(this.nextActionAt,t-1);
  if(this.pendingSummary){
   if(this.ledger.payout)return;
   this._summaryRequested=true;
   this.showSummary();
   return;
  }
  // Nas Free Spins, não converter o toque em botão TURBO.
  const prev=this._autoDispatch;
  if(this.feature)this._autoDispatch=true;
  try{this.startSpin();}finally{this._autoDispatch=prev;}
 }

 startSpin(){
  if(!mod.classList.contains('hidden')||this.superseded||!this.ready)return;
  this.audio.init();
  if(this.recovered){this.recovered=false;this.announce('Jogada recuperada. O prémio já está no saldo.');}
  if(this.paused){this.paused=false;this.audio.startMusic?.();this.notice('O TEMPLO DESPERTA',650);if(this.spinning)return;}
  if(this.spinning)return;
  if(this.ledger.payout){this.notice('A REVELAR AS SEQUÊNCIAS',650);return;}
  if(performance.now()<this.nextActionAt)return;
  const free=!!this.feature;
  let begun;
  try{begun=S.begin(this.ledger,this.rng);}catch(e){this.auto=0;this.notice('SALDO INSUFICIENTE · FAZ UM DEPÓSITO',1900,'red');return;}
  this.syncLedger();if(!free&&this.auto>0)this.auto--;
  this.reveal=null;this.featureIntro=null;this.winCount=null;this.sequencePlayback=null;this.basePaylineDisplay=[];this._lineAdvanceGuardUntil=0;this.lastWin=0;this.lastResult=null;this.expanded={};this.winCells=[];
  this.spinning=true;this.landings.fill(0);this.fxParticles=[];
  const t=performance.now(),duration=this.turbo?520:1180,reelGap=this.turbo?90:175;
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
  const settled=S.settle(this.ledger,undefined,{defer:true});if(!settled)return;
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
  this.spinObject=null;this.nextActionAt=finishedAt+(item.free?(this.turbo?550:880):(this.turbo?320:690));
  // Pagamentos de vários símbolos expansivos animados do mais baixo
  // para o mais alto, sem efetuar de novo o crédito já pago por S.settle().
  const stagedPayout=settled.payout?.groups||[];
  if(stagedPayout.length){
   let cursor=finishedAt+140;
   const stages=stagedPayout.map((group,index)=>{
    // A revelação das linhas vencedoras nas Free Spins deve permitir
    // distinguir cada figura: batida por símbolo, seguida de pausa final.
    // Estes tempos são APENAS visuais; a matemática mantém-se.
    const reels=[...group.reels];
    const isLine=group.kind==='line'&&group.count>=3;
    const freeLine=item.free&&isLine;
    const perReel=freeLine?(this.turbo?390:545)
      :item.free?(this.turbo?185:270):(this.turbo?355:700);
    const preDelay=freeLine?(this.turbo?185:260)
      :item.free?(this.turbo?110:160):(this.turbo?170:340);
    const creditAt=cursor+preDelay+Math.max(1,reels.length)*perReel;
    const end=creditAt+(freeLine?(this.turbo?800:1200)
      :item.free?(this.turbo?350:530):(this.turbo?670:1250));
    const stage={...group,index,start:cursor,preDelay,perReel,
     reels,creditAt,end,amount:S.credits(group.amountU),
     win:group.kind==='line'?result.lineWins.find(w=>!w.expanded&&
       w.line===group.line&&w.symbol===group.symbol):null};
    if(group.kind==='expansion')reels.forEach((reel,k)=>
     this.emitBurst(83+reel*101,444,13,'#83ffef',cursor+preDelay+(k+1)*perReel));
    cursor=end+(freeLine?(this.turbo?260:380)
      :item.free?(this.turbo?135:210):(this.turbo?380:840));
    return stage;
   });
   const end=stages.at(-1).end;
   this.sequencePlayback={id:settled.id,stages,start:finishedAt,end};
   this.reveal={start:finishedAt,end,stages};
   // O prémio mostrado apenas soma parcelas efetivamente creditadas.
   this.winCount={start:finishedAt,duration:end-finishedAt,amount:prize,
    stages:stages.map(g=>({amount:g.amount,start:g.creditAt,end:g.creditAt}))};
   this.nextActionAt=Math.max(this.nextActionAt,end+(item.free?(this.turbo?300:520):(this.turbo?340:750)));
  }else{
  const expansions=item.free?(result.expansionAwards||[]):[];
  if(expansions.length){
   let cursor=finishedAt+90;
   const visualStages=[],rawGroups=[{symbol:null,amount:result.baseTotal+result.scatterWin}];
   for(const candidate of expansions){
    const start=cursor,preDelay=this.turbo?180:250,perReel=this.turbo?200:310;
    const end=start+preDelay+candidate.reels.length*perReel+(this.turbo?420:650);
    const stage={symbol:candidate.symbol,reels:[...candidate.reels],start,end,preDelay,perReel,
     amount:candidate.amount};
    visualStages.push(stage);
    rawGroups.push({symbol:candidate.symbol,amount:candidate.amount,stage});
    candidate.reels.forEach((reel,k)=>this.emitBurst(83+reel*101,444,14,'#83ffef',
     start+preDelay+k*perReel));
    cursor=end+(this.turbo?180:280);
   }
   this.reveal={start:finishedAt,end:visualStages.at(-1).end,stages:visualStages};
   // Valor real, limitado a 5000× no conjunto das Free Spins. Sem arredondar
   // repetidamente: todos os passos somam exatamente o prémio liquidado.
   const rawSum=rawGroups.reduce((sum,x)=>sum+x.amount,0);
   let assigned=0;
   const counted=[];
   for(let i=0;i<rawGroups.length;i++){
    const x=rawGroups[i],last=i===rawGroups.length-1;
    const units=last?settled.prizeU-assigned:
     Math.max(0,Math.min(settled.prizeU-assigned,
      Math.floor(settled.prizeU*(rawSum?x.amount/rawSum:0))));
    assigned+=units;
    if(units<=0)continue;
    counted.push({symbol:x.symbol,amount:S.credits(units),
     start:x.stage?x.stage.start+x.stage.preDelay:finishedAt+75,
     end:x.stage?x.stage.end:Math.max(finishedAt+360,visualStages[0].start+110)});
   }
   this.winCount={start:finishedAt+75,duration:cursor-finishedAt,amount:prize,stages:counted};
   this.nextActionAt=Math.max(this.nextActionAt,cursor+135);
  }else{
   // Conservar o movimento aprovado quando só uma figura é expansiva.
   const reels=item.free?Object.keys(this.expanded).map(Number).sort((a,b)=>a-b):[];
   const animationEnd=reels.length?250+reels.length*(this.turbo?200:300)+480:0;
   for(let k=0;k<reels.length;k++)this.emitBurst(83+reels[k]*101,444,16,'#83ffef',
    finishedAt+(this.turbo?190:250)+k*(this.turbo?200:300));
   this.reveal=reels.length?{start:finishedAt,reels,preDelay:this.turbo?190:250,
    perReel:this.turbo?200:300,end:finishedAt+animationEnd}:null;
   this.winCount={start:finishedAt+(this.reveal?animationEnd-280:80),
    duration:item.free?(this.turbo?520:850):(this.turbo?300:540),amount:prize};
   this.nextActionAt=Math.max(this.nextActionAt,this.winCount.start+this.winCount.duration+(item.free?(this.turbo?330:560):250));
  }
  }
  if(item.free){
   if(result.trigger){
    // A terceira torre confirmou o retrigger real, não apenas o tease.
    // Tocar uma fanfarra curta, mostrar +8 e a nova figura, se existir.
    this.audio.retrigger?.();
    this.notice(settled.nextSymbol?'RETRIGGER +8 · NOVO EXPANSIVO':'RETRIGGER +8 FREE SPINS',this.turbo?1400:2300,'cyan');
    if(settled.nextSymbol)this.queueIntro(settled.nextSymbol,Math.max(finishedAt+500,this.reveal?.end||0));
   }else if(Object.keys(this.expanded).length)this.notice('SÍMBOLO EXPANSIVO',this.turbo?450:1050,'cyan');
   if(settled.bonusDone){this.pendingSummary=settled.bonusDone;this.nextActionAt=Math.max(this.nextActionAt,finishedAt+2300);}
  }else if(result.trigger){
   this.audio.extra();this.notice('8 FREE SPINS DESBLOQUEADAS',this.turbo?1450:2250,'cyan');
   this.queueIntro(this.feature.symbols[0],finishedAt+380);
  }else if(prize>=this.bet*20)this.notice('PRÉMIO ESPECIAL  '+money(prize),this.turbo?650:1550,'gold');
  // No jogo base a sessão credita diretamente o prémio (sem grupos
  // de payout). Criar uma sequência só VISUAL para as linhas premiadas.
  // Não modifica créditos, RNG, pagamentos, RTP ou histórico.
  if(!item.free && prize>0 && result.lineWins?.length){
   const lines=result.lineWins.filter(w=>w.amount>0&&Array.isArray(w.rows)&&w.rows.length===5);
   const rawLines=lines.reduce((sum,w)=>sum+w.amount,0);
   const total=Math.max(rawLines,Number(result.total)||0);
   const wonUnits=S.units(prize);
   const lineUnits=total>0?Math.min(wonUnits,Math.max(0,Math.round(wonUnits*rawLines/total))):0;
   let assigned=0;
   // 700 ms a desenhar até ao tambor 5 + 2 s de destaque + 450 ms de fade.
   const t0=finishedAt+(this.turbo?100:180),step=3150;
   this.basePaylineDisplay=lines.map((win,i)=>{
    const remaining=lineUnits-assigned;
    const units=i===lines.length-1?remaining:
     Math.min(remaining,Math.max(0,Math.floor(lineUnits*win.amount/rawLines)));
    assigned+=units;
    const start=t0+i*step;
    return {win,amount:S.credits(units),start,end:start+3150,_lineSoundPlayed:false};
   }).filter(entry=>entry.amount>0);
   if(this.basePaylineDisplay.length){
    const last=this.basePaylineDisplay.at(-1);
    this.nextActionAt=Math.max(this.nextActionAt,last.end+100);
   }
  }
  this.save();
 }
 queueIntro(symbol,from){
  // Resultado já fixado pelo motor antes da animação: as páginas são visuais.
  // Cada novo retrigger apresenta um novo símbolo até esgotar os nove.
  if(!E.NORMAL.includes(symbol))return;
  const duration=this.turbo?1900:2900;
  const turns=this.turbo?3:4;
  const owned=this.feature?.symbols||this.ledger.last?.bonusDone?.symbols||[symbol];
  const number=Math.max(1,owned.indexOf(symbol)+1);
  const alternatives=E.NORMAL.filter(candidate=>candidate!==symbol);
  const pages=Array.from({length:turns},(_,i)=>
   alternatives[(i*3+number+this.currentRound)%alternatives.length]);
  pages.push(symbol);
  this.featureIntro={symbol,start:from,end:from+duration,pages,turns,number};
  this.nextActionAt=Math.max(this.nextActionAt,from+duration+250);
 }
 activeBasePaylineAt(t){
  if(this.spinning||this.lastWasFree||!this.basePaylineDisplay?.length)return null;
  return this.basePaylineDisplay.find(item=>t>=item.start&&t<item.end)||null;
 }
 sequenceFocusAt(t){
  const playing=this.sequencePlayback;
  if(!playing||t<playing.start||t>playing.end)return null;
  return playing.stages.find(g=>t>=g.start&&t<=g.end)||null;
 }
 expansionView(t){
  if(!this.reveal?.stages?.length||t>=this.reveal.end)return null;
  const stage=this.reveal.stages.find(x=>t>=x.start&&t<x.end);
  if(!stage||stage.kind&&stage.kind!=='expansion')return null;
  return {stage,reveal:stage,expanded:Object.fromEntries(stage.reels.map(reel=>[reel,stage]))};
 }
 displayWinAt(t){
  const counter=this.winCount;
  if(!counter)return this.lastWin;
  if(!counter.stages)return counter.amount*(1-Math.pow(1-clamp((t-counter.start)/counter.duration,0,1),3));
  let value=0;
  for(const stage of counter.stages){
   if(t>=stage.end)value+=stage.amount;
   else if(t>stage.start){
    const p=clamp((t-stage.start)/Math.max(1,stage.end-stage.start),0,1);
    value+=stage.amount*(1-Math.pow(1-p,3));
   }
  }
  return Math.min(counter.amount,value);
 }
 computeWinners(result){
  const map=new Map();
  for(const w of result.lineWins){if(w.expanded){for(const reel of E.LINES[w.line].keys())if(result.expanders.some(p=>p.symbol===w.symbol&&p.reels.includes(reel)))for(let r=0;r<3;r++)map.set(reel+'-'+r,true);
  }else for(let reel=0;reel<w.count;reel++)map.set(reel+'-'+w.rows[reel],true);}
  if(result.scatterWin>0)for(let c=0;c<5;c++)for(let r=0;r<3;r++)if(this.grid[c][r]==='TOMB')map.set(c+'-'+r,true);
  return [...map.keys()];
 }
 autoSpin(){if(this.feature || this.auto>0)this.startSpin();}
 setAuto(){if(this.spinning){this.auto=0;this.notice('AUTOJOGO DESLIGADO',900);return;}
  this.paused=false;this.auto=this.auto?0:20;this.notice(this.auto?'20 GIROS AUTOMÁTICOS':'AUTOJOGO DESLIGADO',1100);}
 changeBet(d){if(this.spinning||this.feature||this.superseded)return;
  this.ledger.betIndex=clamp(this.betIndex+d,0,bets.length-1);
  const base=bets[this.ledger.betIndex]*this.ledger.lines/10;
  this.ledger.betMultiplier=Math.min(this.ledger.betMultiplier||1,
   Math.max(1,Math.floor(10/base)));
  this.syncLedger();this.save();
 }
 changeLines(d){if(this.spinning||this.feature||this.superseded)return;
  this.ledger.lines=clamp(this.lines+d,1,10);
  const base=bets[this.ledger.betIndex]*this.ledger.lines/10;
  this.ledger.betMultiplier=Math.min(this.ledger.betMultiplier||1,
   Math.max(1,Math.floor(10/base)));
  this.syncLedger();this.save();
 }

 openBuyBonus(){
  if(this.superseded||!this.ready||this.spinning||this.feature||this.ledger.pending||
     this.pendingSummary||performance.now()<this.nextActionAt)return;
  const priceU=S.units(this.bet)*100;
  const hasTenLines=this.lines===10;
  const enough=this.ledger.balanceU>=priceU;
  const allowed=hasTenLines&&enough;
  const reason=!hasTenLines?'Ativa as 10 linhas no menu APOSTA para comprar o bónus.':
   !enough?'Créditos insuficientes para esta compra.':'';
  this.openModal('COMPRAR BÓNUS',`<div class="bonus-summary"><p class="eyebrow">8 FREE SPINS</p><p>Compra direta de 8 Free Spins com um símbolo expansivo inicial aleatório.</p><strong>${money(S.credits(priceU))}</strong><p>Custo = <b>100 × aposta total</b> (${money(this.bet)}). <b>10 linhas ativas obrigatórias.</b> Saldo atual: ${money(this.balance)}.</p>${reason?'<p style="color:#b12323;font-weight:800">'+reason+'</p>':''}<div class="actions"><button class="btn" id="buy-bonus-confirm" ${allowed?'':'disabled'}>Confirmar compra</button><button class="btn" id="buy-bonus-cancel">Cancelar</button></div></div>`);
  $('buy-bonus-confirm').onclick=()=>this.confirmBuyBonus();
  $('buy-bonus-cancel').onclick=()=>this.closeModal();
 }
 confirmBuyBonus(){
  if(this.superseded||!this.ready||this.spinning||this.feature||this.ledger.pending||this.pendingSummary||
     mod.classList.contains('hidden'))return;
  // Duplo toque nunca pode desencadear uma segunda compra.
  const confirm=$('buy-bonus-confirm');
  if(!confirm||confirm.disabled)return;
  confirm.disabled=true;
  let purchase;
  try{purchase=S.buyBonus(this.ledger,this.rng);}
  catch(e){confirm.disabled=false;this.notice(e.message||'Compra não disponível',1700,'red');return;}
  this.auto=0;this.paused=false;this.reveal=null;this.featureIntro=null;
  this.winCount=null;this.lastResult=null;this.lastWin=0;this.lastWasFree=false;
  this.expanded={};this.winCells=[];this.pendingSummary=null;
  this.syncLedger();this.save();this.closeModal();
  const t=performance.now();
  this.audio.init();this.audio.startMusic?.();this.audio.extra();
  this.notice('BÓNUS COMPRADO · 8 FREE SPINS',1850,'cyan');
  this.queueIntro(purchase.symbol,t+320);
 }

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
 showBonusHistory(id){
  const h=this.history.find(row=>row.kind==='free-bonus'&&row.id===id);
  if(!h)return;
  const rounds=Array.isArray(h.rounds)?h.rounds:[];
  const rows=rounds.map((r,i)=>'<button type="button" class="btn farao-bonus-history-round" data-bonus-round="'+r.id+'">'+
    '<span>RODADA '+(i+1)+' · JOGADA #'+r.id+'</span>'+
    '<strong>'+money(r.win||0)+'</strong></button>').join('');
  this.openModal('FREE SPINS #'+id,'<div class="farao-ceremony">'+
    '<p class="farao-spins-label">TOTAL GANHO NO BÓNUS</p>'+
    '<strong class="farao-spins-number">'+money(h.win||0)+'</strong>'+
    '<p class="farao-bonus-rounds">'+(h.played||rounds.length)+' RODADAS · '+
     (h.retriggers||0)+' REATIVAÇÕES</p>'+
    '<p class="farao-note">Toca numa rodada para consultar os símbolos e prémios.</p>'+
    '</div><div class="farao-bonus-history-rounds">'+rows+
    '</div><button type="button" class="btn" id="back-history">VOLTAR AO HISTÓRICO</button>',true);
  $('back-history').onclick=()=>this.openInfo('history');
  modalContent.querySelectorAll('[data-bonus-round]').forEach(b=>b.onclick=()=>{
    const spinId=Number(b.dataset.bonusRound);
    if(Number.isSafeInteger(spinId))this.showHistoricSpin(spinId,null,id);
  });
 }
 showHistoricSpin(id,selectedWin=null,bonusId=null){
  const h=bonusId===null?this.history.find(x=>x.id===id&&x.grid):
    this.history.find(x=>x.kind==='free-bonus'&&x.id===bonusId)
      ?.rounds?.find(x=>x.id===id&&x.grid);
  if(!h)return;
  // Jogadas antigas conservam a geometria de linhas usada quando pagaram.
  const historicLines=h.paylinesVersion===E.PAYLINES_VERSION?E.LINES:E.LEGACY_LINES;
  const wins=Array.isArray(h.lineWins)?h.lineWins:[],valid=wins.filter(w=>Number.isInteger(w.line)&&w.line>=0&&w.line<historicLines.length);
  const selected=Number.isSafeInteger(selectedWin)&&selectedWin>=0&&selectedWin<valid.length?selectedWin:null;
  const cells=new Set();
  if(selected===null&&h.scatterWin>0){
   for(let col=0;col<5;col++)for(let row=0;row<3;row++)
    if(h.grid[col]?.[row]==='TOMB')cells.add(col+'-'+row);
  }
  for(const [index,w] of valid.entries())if(selected===null||selected===index){
   const line=historicLines[w.line];
   if(w.expanded){for(let c=0;c<5;c++)if(h.grid[c]?.includes(w.symbol))for(let row=0;row<3;row++)cells.add(c+'-'+row);}
   else for(let c=0;c<Math.min(w.count,5);c++)cells.add(c+'-'+line[c]);
  }
  const rows=Array.from({length:3},(_,row)=>'<div style="display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:3px">'+Array.from({length:5},(_,col)=>{
   const sym=h.grid[col]?.[row],img=this.images[sym],highlight=cells.has(col+'-'+row);
   return '<div style="aspect-ratio:1;border:2px solid '+(highlight?'#ffe18a':'#775d3c')+';background:#13202b"><img alt="Símbolo" src="'+(img?.src||'')+'" style="display:block;width:100%;height:100%;object-fit:contain"></div>';
  }).join('')+'</div>').join('');
  const paths=valid.filter((w,i)=>selected===null||selected===i).map((w,i)=>{
   const points=historicLines[w.line].map((row,col)=>(col*100+50)+','+(row*100+50)).join(' ');
   const color=['#ffdc75','#6af8e5','#ff8fa2','#b9a2ff'][i%4];
   return '<polyline points="'+points+'" fill="none" stroke="#0b1324" stroke-width="8" stroke-linejoin="round" stroke-linecap="round" opacity=".85"/><polyline points="'+points+'" fill="none" stroke="'+color+'" stroke-width="4" stroke-linejoin="round" stroke-linecap="round"/><circle cx="50" cy="'+(historicLines[w.line][0]*100+50)+'" r="17" fill="#14212b" stroke="'+color+'" stroke-width="3"/><text x="50" y="'+(historicLines[w.line][0]*100+56)+'" font-size="18" font-weight="bold" fill="'+color+'" text-anchor="middle">'+(w.line+1)+'</text>';
  }).join('');
  const board='<div style="position:relative;max-width:500px;margin:12px auto"><div style="display:grid;gap:3px">'+rows+'</div><svg viewBox="0 0 500 300" preserveAspectRatio="none" style="position:absolute;inset:0;width:100%;height:100%;pointer-events:none;overflow:visible">'+paths+'</svg></div>';
  const choices=valid.map((w,i)=>'<button type="button" class="btn" data-history-win="'+i+'" style="margin:3px;'+(selected===i?'border-color:#fff1a5;':'')+'">'+(w.expanded?'EXP '+w.symbol+' · ':'')+'Linha '+(w.line+1)+' · '+money(w.amount||0)+'</button>').join('');
  const towerInfo=h.scatterWin>0?
   '<p style="color:#ffe19d;font-weight:800">'+(h.towerPayVersion===1?'PIRÂMIDES EM QUALQUER POSIÇÃO':'SCATTER')+
   ': '+(h.scatters||0)+' · Parcela calculada '+money(h.scatterWin)+
   (h.towerPayVersion===1?' (sujeita ao limite do bónus)':'')+'</p>':'';
  this.openModal('JOGADA #'+id,'<p>'+new Date(h.t).toLocaleString('pt-PT')+
   ' · Aposta '+money(h.bet)+' · Prémio '+money(h.win)+'</p>'+board+towerInfo+
   '<p>'+valid.length+' linhas vencedoras. '+(valid.length?
   'Escolhe o símbolo e a linha para os destacar.':
   (h.scatterWin>0?'Prémio das torres sem precisar de linha.':'Sem linhas vencedoras nesta jogada.'))+
   '</p><div style="text-align:center">'+choices+
   '</div><button class="btn" id="back-history">Voltar ao histórico</button>');
  $('back-history').onclick=()=>bonusId===null?this.openInfo('history'):this.showBonusHistory(bonusId);
  modalContent.querySelectorAll('[data-history-win]').forEach(b=>b.onclick=()=>this.showHistoricSpin(id,Number(b.dataset.historyWin),bonusId));
 }

 showSymbolPay(symbol){
  if(this.spinning||this.superseded||!E.PAYS[symbol])return;
  this.hideSymbolPay();
  const stake=this.bet/this.lines;
  const bubble=document.createElement('div');bubble.id='symbol-pay-bubble';
  bubble.style.cssText='position:absolute;z-index:9;left:50%;top:43%;transform:translate(-50%,-50%);width:155px;padding:8px 12px;border:2px solid #e1b968;border-radius:11px;background:#14202cf5;box-shadow:0 5px 20px #000b;color:#ffe5a6;font:700 14px/1.5 Arial,sans-serif;pointer-events:none';
  bubble.innerHTML=[2,3,4,5].map(n=>'<div style="display:flex;justify-content:space-between;gap:12px"><span>'+n+'×</span><span>'+(E.PAYS[symbol][n]?money(E.PAYS[symbol][n]*stake):'—')+'</span></div>').join('')+
   (symbol==='TOMB'?'<div style="font-size:10px;line-height:1.25;margin-top:5px;color:#a7efe4">Nas Free Spins paga em qualquer posição</div>':'');
  $('stage').appendChild(bubble);
 }
 hideSymbolPay(){
  document.getElementById('symbol-pay-bubble')?.remove();
 }

 openInfo(tab='menu'){
  const tabs=`<div class="header-tabs">${[['menu','APOSTA'],['settings','CONFIGURAÇÕES'],['pays','PAGAMENTOS'],['history','HISTÓRICO'],['admin','ADMINISTRAÇÃO']].map(([key,label])=>`<button data-page="${key}" class="${tab===key?'active':''}">${label}</button>`).join('')}</div>`;
  let page='';
  if(tab==='menu'){
   page=`<div class="bet-summary"><span>Aposta total</span><strong id="bet-total">${money(this.bet)}</strong></div>
   <div class="slider-field"><div class="slider-title"><span>Valor da aposta</span><strong id="bet-display">${money(this.bet)}</strong></div><input type="range" id="bet-slider" min="0" max="${bets.length-1}" step="1" value="${this.betIndex}" ${this.spinning||this.feature?'disabled':''}></div>
   <div class="slider-field"><div class="slider-title"><span>Linhas</span><strong id="lines-display">${this.lines}</strong></div><input type="range" id="lines-slider" min="1" max="10" step="1" value="${this.lines}" ${this.spinning||this.feature?'disabled':''}></div>
   <hr class="menu-divider"><h3>Autojogo</h3>
   <div class="slider-field"><div class="slider-title"><span>Giros automáticos</span><strong id="auto-display">${this.autoChoice||20}</strong></div><input type="range" id="auto-slider" min="10" max="100" step="10" value="${this.autoChoice||20}" ${this.feature?'disabled':''}></div>
   <button class="btn auto-start" id="toggle-auto" ${this.feature?'disabled':''}>${this.auto?'PARAR AUTOJOGO':'INICIAR AUTOJOGO'}</button>
   <p class="hint">A aposta e as linhas só podem ser alteradas com os tambores parados.</p>`;
  }else if(tab==='settings'){
   page=`<h3>Configurações</h3><div class="settings-grid"><button class="btn" id="cycle-sound-mode">Som: ${this.soundModeLabel}</button><p class="hint">Toca para alternar: som completo → só tambores e símbolos → tudo desligado.</p><button class="btn" id="toggle-turbo">Velocidade: ${this.turbo?'Turbo':'Normal'}</button></div><div class="actions"><button class="btn" id="menu-reset" ${this.feature?'disabled':''}>Repor créditos</button><button class="btn" id="menu-gamble" ${this.feature||!this.lastBaseWin||this.gambleTimes>=5?'disabled':''}>Arriscar último prémio</button></div>`;
  }else if(tab==='admin'){
   page='<section class="farao-admin-entry"><p>Acesso reservado à administração da simulação.</p><button type="button" class="btn farao-admin-entry-btn" id="admin-enter">ENTRAR NA ADMINISTRAÇÃO</button></section>';
  }else if(tab==='pays'){
   // Mostrar o prémio em euros a partir da aposta atual por linha,
   // não os multiplicadores. Recalcular ao abrir o menu após alterar a aposta.
   // A tabela E.PAYS e os pagamentos do motor não são modificados.
   const stakePerLine=this.bet/this.lines;
   const figureOrder=['TOMB','KING','ANUBIS','MASK','HORUS','A','K','Q','J','10'];
   const cards=figureOrder.map(key=>{
    const image=this.images[key];
    const name=iconNames[key];
    const payments=[5,4,3,2].map(count=>{
     const multi=E.PAYS[key][count];
     const amount=multi?euro(multi*stakePerLine):'—';
     return `<div class="farao-pay-cell ${multi?'':'farao-pay-unavailable'}"><span>${count} FIG.</span><strong>${amount}</strong></div>`;
    }).join('');
    return `<article class="farao-pay-card" data-pay-figure="${key}"><div class="farao-pay-figure"><img src="${image.src}" alt="${name}" loading="lazy"><span>${name}</span></div><div class="farao-pay-values">${payments}</div></article>`;
   }).join('');
   const scatterValues=[[3,2],[4,20],[5,200]]
     .map(([count,multiplier])=>`${count} pirâmides: ${euro(this.bet*multiplier)}`).join(' · ');
   const freeTowerValues=[2,3,4,5]
     .map(count=>`${count} pirâmides: ${euro(E.PAYS.TOMB[count]*stakePerLine)}`).join(' · ');
   page=`<section class="farao-paytable" aria-label="Tabela de prémios de cada figura"><h3>TABELA DE PRÉMIOS</h3><p class="farao-pay-intro">Prémios calculados para a tua aposta atual. <b>Aposta por linha: ${euro(stakePerLine)}</b> · ${this.lines} linhas ativas. Exemplo: 5 Ases = <b>${euro(E.PAYS.A[5]*stakePerLine)}</b>.</p><div class="farao-pay-cards">${cards}</div><div class="farao-scatter-pay"><img src="${this.images.TOMB.src}" alt="Wild e Scatter"><div><strong>PIRÂMIDES · WILD / SCATTER</strong><span><b>Jogo base:</b> ${scatterValues}</span><span><b>Nas Free Spins (em qualquer posição):</b> ${freeTowerValues}</span><small>Nas Free Spins, 2 ou mais pirâmides dão o prémio pela quantidade, sem precisar de formar linha. O prémio de pirâmides é contado uma única vez por rodada. 3 ou mais pirâmides ativam ou reativam 8 Free Spins.</small></div></div><p class="farao-pay-footnote">Os prémios de linhas contam da esquerda para a direita. Só é pago o maior prémio de cada linha, incluindo expansivos. Valores de referência dos créditos do jogo.</p></section><h3>AS 10 LINHAS DE PAGAMENTO</h3><p>Da esquerda para a direita, segundo a fotografia.</p><div class="paylines">`+E.LINES.map((rows,i)=>`<figure><svg viewBox="0 0 130 76" aria-label="Linha ${i+1}"><defs><pattern id="dots${i}" width="24" height="23" patternUnits="userSpaceOnUse"><circle cx="17" cy="15" r="3" fill="#4d555d"/></pattern></defs><rect width="130" height="76" fill="url(#dots${i})"/><polyline points="${rows.map((r,c)=>`${17+c*24},${15+r*23}`).join(' ')}" fill="none" stroke="#f3ce7c" stroke-width="3"/></svg><figcaption>Linha ${i+1}</figcaption></figure>`).join('')+'</div>';
  }else if(tab==='rules'){
   page=`<h3>O santuário é WILD e SCATTER</h3><p>5 tambores × 3 posições, com 1 a 10 linhas. As combinações normais começam no tambor da esquerda, sem intervalos. O santuário substitui os restantes símbolos. Hórus, Máscara e Anúbis pagam desde 2; o Faraó e o santuário também. As cartas pagam desde 3.</p><h3>8 Free Spins e símbolos expansivos</h3><p>3 ou mais santuários em qualquer posição dão 8 giros grátis. Um símbolo normal é sorteado. Quando está em tambores suficientes para pagar, expande nas três posições desses tambores, mesmo separados. Os santuários não substituem o símbolo especial no cálculo da expansão.</p><p>Nas Free Spins, 2, 3, 4 ou 5 torres pagam pela quantidade em qualquer posição, mesmo sem linha, conforme a tabela. O prémio de torres é contabilizado uma única vez, sem acumular o Scatter do jogo base. Os Scatters aparecem mais vezes do que no jogo base. Com 2 Scatters já parados e tambores ainda a rodar, há um <b>tease</b>: os tambores abrandam e surge o aviso de suspense. O terceiro Scatter não é garantido.</p><p>Com 3 ou mais Scatters, há um <b>RETRIGGER de +8 Free Spins</b> e outro símbolo expansivo, sem repetir, até 9. Depois, os retriggers continuam a dar +8. Se duas ou mais figuras expansivas derem prémio na mesma jogada, são contabilizadas separadamente, da mais baixa à mais alta; com uma só expansão, mantém-se o maior prémio por linha.</p><h3>Limites preservados da V0.6</h3><p>Aposta de 0,10 a 10 créditos. Limite de 5.000× numa jogada base e de 5.000× no total de uma série de Free Spins. O prémio da jogada que ativa o bónus é pago à parte. O bónus termina ao atingir o seu limite. Não há limite artificial de novas ativações.</p><h3>Arriscar e sessão</h3><p>Cor: 50% para ×2; naipe: 25% para ×4. Até cinco tentativas. Arrisca apenas o último prémio base e o limite mantém a aposta da jogada original. O saldo e os resultados pendentes são guardados neste navegador. Ao regressar de outra aplicação, toca em Retomar.</p><p><a href="docs/MATEMATICA.md" target="_blank" rel="noopener">Consultar a matemática e probabilidades</a> · <a href="docs/VALIDACAO.md" target="_blank" rel="noopener">Consultar a validação</a></p><p class="hint">Matemática independente. O RTP deste jogo é medido pelo seu próprio simulador; não é o RTP do Legacy of Dead. Não existe certificação de jogo a dinheiro.</p>`;
  }else{
   // Histórico compacto: nunca incluir imagens na lista. As figuras e
   // linhas premiadas aparecem apenas quando se abre a jogada pelo seu ID.
   const esc=v=>String(v).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
   const entry=h=>{
    const hasId=Number.isSafeInteger(h.id)&&h.id>0;
    const isBonus=h.kind==='free-bonus';
    const canOpen=hasId&&(isBonus?Array.isArray(h.rounds)&&h.rounds.length>0:!!h.grid);
    const kind=isBonus?'Free Spins':h.kind==='deposit'?'Depósito':h.kind==='withdraw'?'Levantamento':h.kind==='reset'?'Reposição de saldo':h.kind==='gamble'?'Carta':h.kind==='bonus-buy'?'Compra de bónus':h.bonus?'Free Spin':'Giro';
    const number=hasId?' #'+h.id:'';
    const date=esc(new Date(h.t).toLocaleString('pt-PT'));
    const amounts=isBonus
      ?(h.played||h.rounds?.length||0)+' rodadas · Total <strong>'+money(h.win||0)+'</strong>'
      :h.kind==='bonus-buy'
      ?'Custo <strong>'+money(h.cost||0)+'</strong> · 8 Free Spins'
      :h.kind==='deposit'||h.kind==='withdraw'
      ?'Movimento <strong>'+money(h.amount||0)+'</strong>'
      :'Aposta '+money(h.bet||0)+' · Prémio <strong>'+money(h.win||0)+'</strong>';
    const details='<span class="farao-history-kind">'+esc(kind)+number+'</span>'+
      '<span class="farao-history-date">'+date+'</span>'+
      '<span class="farao-history-money">'+amounts+'</span>';
    if(isBonus&&canOpen)return '<button type="button" class="farao-history-row" data-bonus-open="'+h.id+'" data-history-id="'+h.id+'" data-round-ids="'+h.rounds.map(r=>r.id).join(',')+'" aria-label="Consultar Free Spins do bónus '+h.id+'">'+details+'<span class="farao-history-open">Ver rodadas ›</span></button>';
    if(canOpen)return '<button type="button" class="farao-history-row" data-history-open="'+h.id+'" data-history-id="'+h.id+'" aria-label="Abrir imagens da jogada '+h.id+'">'+details+'<span class="farao-history-open">Ver jogada ›</span></button>';
    return '<div class="farao-history-row farao-history-static"'+(hasId?' data-history-id="'+h.id+'"':'')+'>'+details+(hasId?'<span class="farao-history-date">Imagens indisponíveis</span>':'')+'</div>';
   };
   page=`<section class="farao-history-search"><label for="history-search-number">PROCURAR N.º DA JOGADA</label><div class="farao-history-search-controls"><input id="history-search-number" type="search" inputmode="numeric" autocomplete="off" enterkeyhint="search" placeholder="Ex.: 125" aria-describedby="history-search-count"><button type="button" class="btn" id="history-search-go">Procurar</button><button type="button" class="btn" id="history-search-clear">Limpar</button></div><p id="history-search-count" role="status" aria-live="polite"></p></section><div class="history farao-history-list" id="history-results">`+
    (this.history.length?this.history.map(entry).join(''):'<p class="farao-history-empty">Ainda não existem jogadas guardadas.</p>')+
    '</div><p class="farao-history-note">Pesquisa por número exato nas últimas '+this.history.length+' ações guardadas neste dispositivo (limite de 100).</p>';
  }
  this.openModal('FARAÓ ETERNO',tabs+page);
  modalContent.querySelectorAll('[data-page]').forEach(b=>b.onclick=()=>this.openInfo(b.dataset.page));
  if(tab==='history'){
   const search=$('history-search-number'),clear=$('history-search-clear'),go=$('history-search-go');
   const counter=$('history-search-count');
   const rows=[...modalContent.querySelectorAll('[data-history-id],.farao-history-static:not([data-history-id])')];
   const update=()=>{
    const raw=search.value.trim().replace(/^(?:jogada\s*)?#?\s*/i,'');
    const exact=raw&&/^\d+$/.test(raw)?Number(raw):null;
    let visible=0;
    for(const row of rows){
     const same=!raw||(Number.isSafeInteger(exact)&&exact>0&&
       (row.dataset.historyId===String(exact)||row.dataset.roundIds?.split(',').includes(String(exact))));
     row.hidden=!same;
     if(same)visible++;
    }
    counter.textContent=raw?
      (visible?visible+' registo(s) para a jogada #'+raw:'Jogada #'+raw+' não encontrada entre as ações guardadas.'):
      this.history.length+' ações guardadas · toca numa jogada para ver as imagens.';
   };
   search.value=this.historyLookup||'';
   search.addEventListener('input',()=>{this.historyLookup=search.value;update();});
   search.addEventListener('keydown',ev=>{if(ev.key==='Enter'){ev.preventDefault();update();search.blur();}});
   go.onclick=()=>{this.historyLookup=search.value;update();search.blur();};
   clear.onclick=()=>{search.value='';this.historyLookup='';update();search.focus();};
   modalContent.querySelectorAll('[data-history-open]').forEach(b=>b.onclick=()=>{
    const id=Number(b.dataset.historyOpen);
    if(Number.isSafeInteger(id)&&id>0)this.showHistoricSpin(id);
   });
   modalContent.querySelectorAll('[data-bonus-open]').forEach(b=>b.onclick=()=>{
    const id=Number(b.dataset.bonusOpen);
    if(Number.isSafeInteger(id)&&id>0)this.showBonusHistory(id);
   });
   update();
  }
  if(tab==='admin') $('admin-enter').onclick=()=>this.openAdminLogin?.();
  if(tab==='menu'){
   $('bet-slider').oninput=e=>{this.ledger.betIndex=Number(e.target.value);
    this.ledger.betMultiplier=Math.min(this.ledger.betMultiplier||1,
     Math.max(1,Math.floor(10/(bets[this.ledger.betIndex]*this.ledger.lines/10))));
    this.syncLedger();this.save();$('bet-display').textContent=money(this.bet);$('bet-total').textContent=money(this.bet);};
   $('lines-slider').oninput=e=>{this.ledger.lines=Number(e.target.value);
    this.ledger.betMultiplier=Math.min(this.ledger.betMultiplier||1,
     Math.max(1,Math.floor(10/(bets[this.ledger.betIndex]*this.ledger.lines/10))));
    this.syncLedger();this.save();$('lines-display').textContent=this.lines;$('bet-display').textContent=money(this.bet);$('bet-total').textContent=money(this.bet);};
   $('auto-slider').oninput=e=>{this.autoChoice=Number(e.target.value);$('auto-display').textContent=this.autoChoice;};
   $('toggle-auto').onclick=()=>{const wasAuto=this.auto>0;this.closeModal();if(wasAuto){this.auto=0;this.notice('AUTOJOGO DESLIGADO',900);}else{this.auto=this.autoChoice||20;this.paused=false;this.notice(this.auto+' GIROS AUTOMÁTICOS',1100);}};
  }else if(tab==='settings'){
   $('cycle-sound-mode').onclick=()=>{this.cycleSoundMode();this.openInfo('settings');};
   $('toggle-turbo').onclick=()=>{this.turbo=!this.turbo;this.save();this.openInfo('settings');};
   $('menu-reset').onclick=()=>this.openReset();$('menu-gamble').onclick=()=>this.openGamble();
  }
 }
 openReset(){
  if(this.feature||this.spinning)return;
  this.openModal('REPOR SALDO',`<p>Saldo atual: ${money(this.balance)}.</p><p>Repor o saldo para 0,00 €?</p><div class="actions"><button class="btn" id="reset-go">Repor saldo a zero</button><button class="btn" id="reset-cancel">Voltar</button></div>`);
  $('reset-go').onclick=()=>{S.reset(this.ledger);this.auto=0;this.syncLedger();this.winCount=null;this.save();this.closeModal();this.notice('SALDO REPOSTO A ZERO · NOVO LEGADO',1300);};
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
  // A única operação que credita cada parcela. O anúncio final não o faz.
  if(!this.spinning&&!this.paused&&!this.superseded&&!document.hidden&&
     this.sequencePlayback&&this.ledger.payout){
   for(const stage of this.sequencePlayback.stages){
    if(t<stage.creditAt||stage.paid)continue;
    const received=S.creditNext(this.ledger,this.sequencePlayback.id,stage.index);
    if(!received)continue;
    stage.paid=true;
    this.syncLedger();
    this.save();
    this.announce('Sequência '+(stage.index+1)+': '+money(stage.amount)+
      '. Saldo '+money(this.balance)+'.');
    // Nas linhas de Free Spins, a escala musical já acompanhou a
    // revelação de cada figura; não repetir um chime por cima.
    if(stage.kind!=='line')this.audio.paylineChime?.(stage.index);
   }
  }
  // Cada linha do jogo base tem agora um som próprio, sincronizado
  // com a caneta luminosa. Não credita nada: os prémios já foram pagos.
  if(!this.spinning&&!this.paused&&!this.superseded&&!document.hidden){
   const active=this.activeBasePaylineAt(t);
   if(active&&!active._lineSoundPlayed){
    active._lineSoundPlayed=true;
    this.audio.paylineChime?.(Math.max(0,this.basePaylineDisplay.indexOf(active)));
   }
  }
  if(this.spinning&&!this.paused&&!document.hidden){
   const item=this.spinObject;
   // Nunca deixar um tambor posterior parar antes do anterior, mesmo
   // com suspenses, frames atrasados ou alterações de velocidade.
   // No máximo uma paragem por frame para a sequência 1→2→3→4→5
   // ser visualmente inequívoca.
   for(let c=0;c<5;c++){
    if(item.stopped[c])continue;
    if(c>0&&!item.stopped[c-1])break;
    if(t>=(item.stopAt?.[c]??(item.start+item.duration+c*item.reelGap))){
     item.stopped[c]=true;this.landings[c]=t;this.audio.tick(c);
    }
    break;
   }
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
  if(!this.ready){
   // Só mostrar o ecrã de carregamento — nunca os nomes das peças em vez
   // da arte HD. A grelha e o botão GIRAR só aparecem com as imagens prontas.
   rect(43,337,474,269,'#061221ed','#e6ba68',18,2);
   text('A ABRIR O TEMPLO',280,398,27,'#ffe7a5','center','Georgia',900,6);
   const total=this.artTotal||E.ALL.length,loaded=this.artLoaded||0;
   text('A CARREGAR SÍMBOLOS HD',280,451,15,'#e9cc9a','center','Arial',700);
   rect(95,483,370,18,'#151c2b','#b88e4b',9,1.5);
   rect(99,487,Math.max(0,362*loaded/total),10,'#e8bf68',null,5);
   text(loaded+' / '+total,280,536,14,'#f5dfb6','center','Arial',700);
   this.syncTargets();
   return;
  }
  this.reelBoard(t);
  this.drawFx(t);
  this.statusBar(t);
  this.controls(t);
  this.overlay(t);
  this.introOverlay(t);
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
  // A inscrição 3D é colocada sobre a moldura em visual-v11.js.
  // Não desenhar o título duas vezes nem ocultar o cenário ao fundo.
  this.smallButton(49,66,68,'☰',()=>this.openInfo());
  this.smallButton(511,66,68,this.soundMode==='all'?'♫':this.soundMode==='effects'?'♪':'×',()=>this.cycleSoundMode());
  // A contagem foi transferida para a placa lateral junto ao botão GIRAR.
 }

  reelBoard(t){
  const x=20,y=251,w=520,h=410;
  // Preserve the Egyptian frame but do not obscure the temple image behind the reels.
  cx.save();cx.shadowColor='#000b';cx.shadowBlur=19;cx.shadowOffsetY=7;
  rect(x-5,y-7,w+10,h+16,null,'#dfb66a',13,4);cx.restore();
  rect(x-4,y-7,w+8,h+14,null,'#bb8648',11,2.5);
  rect(x+6,y+2,w-12,h-5,null,'#ad874d88',6,1.2);
  // Five unfilled reels with 15 fixed, square glass-cell borders.
  const cw=95,gh=132,left=35,top=258,interval=101,square=90;
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
     const rowEase=expanding?expandProgress:0;
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
      cx.restore();
     }
    }
    if(expanding){
     const vertical=grad(rx,top,rx+cw,top,[[0,'#e3fff000'],[.42,'#bfffea2a'],[.50,'#8cffe599'],[.59,'#9cffdf22'],[1,'#e3fff000']]);
     cx.save();cx.globalAlpha=.8*(1-expandProgress)+.1;rect(rx+2,top+2,cw-4,gh*3-3,vertical);cx.restore();
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
   if(this.spinning || !this.lastResult?.lineWins?.length ||
      this.featureIntro&&t>=this.featureIntro.start&&t<this.featureIntro.end)return;
   const focus=this.sequenceFocusAt(t);
   const paidLine=this.activeBasePaylineAt(t);
   const freeLineStage=this.lastWasFree&&focus?.kind==='line'&&focus.win?focus:null;
   if(this.reveal&&t<this.reveal.end&&!focus&&!paidLine)return;
   if(!this.lastWasFree&&!focus&&!paidLine)return;
   const wins=this.lastResult.lineWins;
   const chosen=paidLine?.win||(focus
     ?focus.kind==='line'?focus.win:
      focus.kind==='expansion'?wins.find(w=>w.expanded&&w.symbol===focus.symbol):null
     :wins[Math.floor(t/1600)%wins.length]);
   if(!chosen||!Array.isArray(chosen.rows)||chosen.rows.length!==5)return;
   const soften=Boolean(window.matchMedia&&matchMedia('(prefers-reduced-motion: reduce)').matches);
   const age=paidLine?Math.max(0,t-paidLine.start):0;
   const penDuration=700; // da primeira imagem até ao tambor cinco
   const progress=paidLine?clamp(age/penDuration,0,1)
     :freeLineStage?clamp((t-freeLineStage.start-freeLineStage.preDelay)/
        Math.max(1,freeLineStage.reels.length*freeLineStage.perReel),0,1):1;
   const fade=paidLine?clamp((paidLine.end-t)/450,0,1)
     :freeLineStage?clamp((freeLineStage.end-t)/450,0,1):1;
   if(fade<=0)return;
   const coords=chosen.rows.map((row,c)=>({
     x:left+c*interval+cw/2,
     y:top+row*gh+gh*.55
   }));
   // O caminho completo vai sempre até ao 5.º tambor, mesmo quando
   // apenas as primeiras 3 ou 4 figuras constituem a combinação.
   const lengths=[];let length=0;
   for(let i=1;i<coords.length;i++){
    const segment=Math.hypot(coords[i].x-coords[i-1].x,coords[i].y-coords[i-1].y);
    lengths.push(segment);length+=segment;
   }
   const covered=length*progress;
   let travelled=0,tip={...coords[0]};
   const expander=chosen.expanded?this.lastResult.expanders?.find(e=>e.symbol===chosen.symbol):null;
   const paidReels=chosen.expanded
     ?new Set(expander?.reels||[])
     :new Set(Array.from({length:Math.min(5,chosen.count)},(_,i)=>i));
   cx.save();
   cx.beginPath();cx.rect(left,top,5*interval-2,3*gh);cx.clip();
   cx.lineJoin='round';cx.lineCap='round';
   cx.beginPath();cx.moveTo(coords[0].x,coords[0].y);
   for(let i=1;i<coords.length;i++){
    const seg=lengths[i-1];
    if(covered>=travelled+seg){
     cx.lineTo(coords[i].x,coords[i].y);tip=coords[i];
    }else if(covered>travelled){
     const u=(covered-travelled)/Math.max(1,seg);
     tip={
      x:coords[i-1].x+(coords[i].x-coords[i-1].x)*u,
      y:coords[i-1].y+(coords[i].y-coords[i-1].y)*u
     };
     cx.lineTo(tip.x,tip.y);break;
    }else break;
    travelled+=seg;
   }
   // Contorno escuro + caneta azul luminosa com núcleo branco, bem visível
   // nas imagens HD sem criar uma faixa ou retângulo de fundo.
   cx.globalAlpha=.91*fade;
   cx.shadowBlur=0;cx.strokeStyle='#051e38';cx.lineWidth=11;cx.stroke();
   cx.globalAlpha=.98*fade;
   cx.shadowColor='#46cfff';cx.shadowBlur=soften?10:21;
   cx.strokeStyle='#218fff';cx.lineWidth=6.8;cx.stroke();
   cx.shadowBlur=0;cx.globalAlpha=.96*fade;
   cx.strokeStyle='#d9faff';cx.lineWidth=2.5;cx.stroke();
   // Apenas os símbolos que pagam ficam com ponto luminoso, à medida
   // que a ponta da caneta passa por cada um.
   let distance=0;
   for(let c=0;c<coords.length;c++){
    if(c>0)distance+=lengths[c-1];
    if(!paidReels.has(c)||distance>covered+1)continue;
    const p=coords[c];
    cx.globalAlpha=.95*fade;cx.shadowColor='#d4faff';cx.shadowBlur=14;
    cx.fillStyle='#efffff';cx.beginPath();cx.arc(p.x,p.y,4.7,0,Math.PI*2);cx.fill();
   }
   // Ponta móvel — estrela azul/branca como uma caneta a escrever.
   if((paidLine||freeLineStage)&&progress<1){
    cx.globalAlpha=fade;
    cx.fillStyle='#ffffff';cx.shadowColor='#9defff';cx.shadowBlur=23;
    cx.beginPath();cx.arc(tip.x,tip.y,6.7,0,Math.PI*2);cx.fill();
    cx.strokeStyle='#e8faff';cx.lineWidth=1.5;cx.shadowBlur=13;
    cx.beginPath();cx.moveTo(tip.x-11,tip.y);cx.lineTo(tip.x+11,tip.y);
    cx.moveTo(tip.x,tip.y-11);cx.lineTo(tip.x,tip.y+11);cx.stroke();
   }
   cx.restore();
   if((paidLine||freeLineStage)&&progress>=.88){
    // A quantia surge quando a caneta chega ao final do percurso.
    // Visível ~2 s, seguida de 450 ms de desaparecimento suave.
    const index=Math.min(4,Math.max(0,chosen.count-1));
    const p=coords[index],prize=money((paidLine||freeLineStage).amount);
    const appear=paidLine?clamp((age-penDuration*.88)/100,0,1)
      :clamp((progress-.88)/.12,0,1);
    const alpha=fade*appear;
    cx.save();cx.font='900 21px Georgia,serif';
    cx.textAlign='left';cx.textBaseline='middle';cx.lineJoin='round';
    const width=cx.measureText(prize).width;
    const labelX=clamp(p.x+15,left+12,left+5*interval-10-width);
    const labelY=clamp(p.y-39,top+22,top+3*gh-20);
    cx.globalAlpha=alpha;cx.lineWidth=6;
    cx.strokeStyle='#081625';cx.shadowColor='#ffdd83';cx.shadowBlur=17;
    cx.strokeText(prize,labelX,labelY);
    cx.fillStyle='#fff0ad';cx.fillText(prize,labelX,labelY);
    if(freeLineStage){
     // Confirmação visível da combinação de cada linha do bónus.
     // Ex.: "3 × A" e "3 × 10" são mostrados separadamente.
     const combination=chosen.count+' × '+chosen.symbol;
     cx.font='900 16px Georgia,serif';
     cx.lineWidth=4;cx.globalAlpha=alpha;
     cx.strokeText(combination,labelX,labelY-27);
     cx.fillText(combination,labelX,labelY-27);
    }
    cx.restore();
   }
  }
 drawIcon(sym,x,y,w,h,win=false,t=performance.now()){
  cx.save();
  // Contain all original image proportions in one centred square: no crop or stretch.
  const box=Math.min(w-6,h-6),bx=x+(w-box)/2,by=y+(h-box)/2;
  if(win&&sym!=='TOMB'){
   const glow=.6+.4*Math.sin(t*.008+x*.2);
   cx.shadowColor='#f8d875';cx.shadowBlur=16+11*glow;
   const aura=cx.createRadialGradient(x+w*.5,y+h*.5,5,x+w*.5,y+h*.5,box*.58);
   aura.addColorStop(0,`rgba(255,233,156,${.26*glow})`);
   aura.addColorStop(1,'rgba(247,194,89,0)');
   rect(bx,by,box,box,aura);
  }
  // A pirâmide usa apenas os píxeis transparentes do ficheiro original.
  // Variar Livro / Ankh em rondas alternadas sem criar símbolos novos no motor.
  const img=(sym==='MASK'&&(this.currentRound%2===1)&&
     this.images.MASK_ALT?.complete&&this.images.MASK_ALT.naturalWidth)
    ?this.images.MASK_ALT:this.images[sym];
  if(img?.complete&&img?.naturalWidth&&img.naturalHeight){
   const premium=E.NORMAL.slice(5).includes(sym)||sym==='TOMB';
   const maxSide=box*(sym==='TOMB'?.97*1.03:premium?.97:.92);
   const scale=Math.min(maxSide/img.naturalWidth,maxSide/img.naturalHeight);
   const iw=img.naturalWidth*scale,ih=img.naturalHeight*scale;
   if(sym==='TOMB'){
    // O renderizador de reserva segue a mesma pulsação de 13% e
    // iluminação dourada da pirâmide, sem placa de fundo.
    const wave=.5+.5*Math.sin(t*.0035),pulse=1+.13*wave;
    const pw=iw*pulse,ph=ih*pulse;
    cx.save();cx.filter='none';
    cx.shadowColor='rgba(255,198,68,'+(.56+.32*wave)+')';
    cx.shadowBlur=12+16*wave;cx.shadowOffsetX=0;cx.shadowOffsetY=0;
    cx.drawImage(img,x+(w-pw)/2,y+(h-ph)/2,pw,ph);
    cx.restore();
   }else cx.drawImage(img,x+(w-iw)/2,y+(h-ih)/2,iw,ih);
  }else{text(sym,x+w/2,y+h/2,26,'#fce8b4');}
  cx.restore();
  if(win&&sym!=='TOMB'){
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
  if(t>this.messageUntil || this.sequenceFocusAt?.(t) ||
     this.featureIntro&&t>=this.featureIntro.start&&t<=this.featureIntro.end)return;
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
  const clamp01=v=>Math.max(0,Math.min(1,v));
  const elapsed=t-intro.start,duration=intro.end-intro.start;
  const reduced=Boolean(window.matchMedia&&
   window.matchMedia('(prefers-reduced-motion: reduce)').matches);
  const fade=clamp01(elapsed/270)*clamp01((duration-elapsed)/360);
  const left=106,spine=280,right=454,top=355,pageH=222,pageW=166;
  const turns=intro.turns||6,lead=600,hold=1250;
  const turnLength=(duration-lead-hold)/turns;
  const progress=reduced?turns:clamp01((elapsed-lead)/(turnLength*turns))*turns;
  const index=Math.min(turns,Math.floor(progress));
  const phase=index===turns?1:progress-index;
  const pages=intro.pages||[intro.symbol];
  const current=pages[Math.min(index,pages.length-1)];
  const next=pages[Math.min(index+1,pages.length-1)];
  const previous=pages[Math.max(0,index-1)];
  const parchment=(x,y,w,h,symbol,flip=false)=>{
   cx.save();
   const shade=cx.createLinearGradient(x,y,x+w,y+h);
   shade.addColorStop(0,flip?'#e6ba74':'#f9e8b4');
   shade.addColorStop(.48,flip?'#ffe4a1':'#fff3c7');
   shade.addColorStop(1,'#bc8c4a');
   rect(x,y,w,h,shade,'#946b38',flip?1:4,1.6);
   rect(x+9,y+10,w-18,h-20,null,'#9c713b73',3,1);
   for(let j=0;j<3;j++){
    line(x+18,y+19+j*5,x+w-18,y+19+j*5,'#a67b453b',1);
    line(x+18,y+h-18-j*5,x+w-18,y+h-18-j*5,'#a67b453b',1);
   }
   const img=this.images[symbol],size=126;
   if(img&&img.complete&&img.naturalWidth){
    cx.shadowColor='#a46e27';cx.shadowBlur=11;
    cx.drawImage(img,x+(w-size)/2,y+(h-size)/2-3,size,size);
    cx.shadowBlur=0;
   }else{
    // Evitar nomes/letras como substituto da imagem durante o sorteio.
    ellipse(x+w/2,y+h/2,42,42,'#cb9a4d');
    ellipse(x+w/2,y+h/2,29,29,'#ffdf8c');
   }
   cx.restore();
  };
  const ornateBack=(x,y,w,h)=>{
   cx.save();
   const leather=cx.createLinearGradient(x,y,x+w,y+h);
   leather.addColorStop(0,'#3d1930');leather.addColorStop(.5,'#71302e');
   leather.addColorStop(1,'#251226');
   rect(x,y,w,h,leather,'#f7d18a',5,3);
   rect(x+9,y+9,w-18,h-18,null,'#e8be74',4,2);
   rect(x+17,y+17,w-34,h-34,null,'#a36e40',2,1.5);
   for(let j=0;j<4;j++){
    const xx=x+24+j*(w-48)/3;
    line(xx,y+23,xx,y+h-23,'#d4ab5655',1);
   }
   const ox=x+w/2,oy=y+h/2;
   cx.translate(ox,oy);cx.rotate(Math.PI/4);
   rect(-40,-40,80,80,'#d8af6127','#f6d585',2,3);
   rect(-30,-30,60,60,null,'#d39d49',2,1.6);
   cx.rotate(-Math.PI/4);
   // Medalhão solar egípcio em relevo, sem letras nem identificação.
   ellipse(0,0,27,27,'#af793b');
   ellipse(0,0,21,21,'#ffe3a1');
   ellipse(0,-2,13,13,'#693223');
   for(let i=0;i<12;i++){
    const a=i*Math.PI/6;
    line(Math.cos(a)*34,Math.sin(a)*34,
     Math.cos(a)*47,Math.sin(a)*47,'#ffdb8c',2.5);
   }
   cx.beginPath();cx.moveTo(-7,7);cx.quadraticCurveTo(0,-18,7,7);
   cx.quadraticCurveTo(0,12,-7,7);cx.closePath();
   cx.fillStyle='#ffe5aa';cx.fill();
   cx.restore();
  };
  cx.save();
  cx.globalAlpha=fade;
  cx.shadowColor='#000b';cx.shadowBlur=30;
  rect(41,302,478,340,'#061421f4','#e9c37c',18,3);
  cx.shadowBlur=0;
  text('SÍMBOLO EXPANSIVO',280,330,18,'#ffe4a9','center','Georgia',900,4);
  // Livro encadernado com lombada, páginas douradas e duas folhas visíveis.
  cx.save();cx.shadowColor='#000b';cx.shadowBlur=14;
  rect(93,343,374,244,'#3b2017','#c99c57',11,5);
  cx.restore();
  for(let k=0;k<4;k++)rect(102+k,350+k,356-k*2,229-k*2,null,'#d4ad6d66',5,1);
  ornateBack(left,top,pageW,pageH);
  parchment(spine+5,top,pageW,pageH,next);
  if(index<turns){
   const factor=Math.max(.018,Math.abs(Math.cos(Math.PI*phase)));
   const width=pageW*factor;
   cx.save();
   if(phase<.5){
    cx.translate(spine+4,0);
    cx.scale(factor,1);
    parchment(0,top,pageW,pageH,current,true);
   }else{
    cx.translate(spine+4-width,0);
    cx.scale(factor,1);
    ornateBack(0,top,pageW,pageH);
   }
   cx.restore();
   cx.save();cx.globalAlpha=.17*(1-factor);
   rect(spine-5,top,10,pageH,'#39200f');
   cx.restore();
  }
  const binding=cx.createLinearGradient(spine-9,0,spine+9,0);
  binding.addColorStop(0,'#68401f');binding.addColorStop(.5,'#fbe2a0');
  binding.addColorStop(1,'#693a1c');
  rect(spine-5,top-2,10,pageH+4,binding,'#754722',3,1);
  // Sem nome da figura: a arte selecionada fica a brilhar na última página.
  if(index===turns){
   const glow=clamp01((elapsed-(lead+turnLength*turns))/350);
   cx.save();cx.globalAlpha=glow*.7;
   cx.shadowColor='#ffe3a2';cx.shadowBlur=32;
   rect(spine+15,top+12,pageW-21,pageH-24,null,'#ffe6a6',7,3);
   cx.restore();
  }
  const count=Math.max(1,intro.number||1);
  text('SÍMBOLO '+count+' / 9',280,613,15,'#ffe1a0','center','Georgia',800);
  cx.restore();
 }

}
window.FaraoGame=Game;
window.__faraoGame = new Game();
document.documentElement.dataset.faraoVersion='1.0';
})();
