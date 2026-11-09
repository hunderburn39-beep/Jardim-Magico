'use strict';
const frame=document.getElementById('game-frame'),list=document.getElementById('results'),summary=document.getElementById('summary');
const poll=(fn,ms=8000)=>new Promise((resolve,reject)=>{const end=performance.now()+ms;const tick=()=>{try{if(fn())return resolve();if(performance.now()>end)return reject(Error('Tempo esgotado'));setTimeout(tick,25);}catch(e){reject(e);}};tick();});
const raf=()=>new Promise(r=>requestAnimationFrame(()=>requestAnimationFrame(r)));
const assert=(condition,message='Verificação falhou')=>{if(!condition)throw Error(message);};
let rows=[];
const quiet=()=>[['10','J','Q'],['K','A','10'],['J','Q','K'],['A','10','J'],['Q','K','A']];
function get(){return frame.contentWindow.__faraoGame;}
function tap(name){const d=frame.contentDocument,b=[...d.querySelectorAll('button')].find(x=>x.getAttribute('aria-label')===name||x.textContent===name);assert(b,'Botão não encontrado: '+name);b.click();}
function reset(){const g=get(),S=frame.contentWindow.FaraoSession;g.audio.stopMusic();g.ledger=S.fresh();g.ledger.settings.music=false;g.ledger.settings.sound=false;g.syncLedger();g.closeModal();g.spinning=false;g.spinObject=null;g.auto=0;g.paused=false;g.superseded=false;g.nextActionAt=0;g.pendingSummary=null;g.featureIntro=null;g.reveal=null;g.v04Celebration=null;g.winCount=null;g.expanded={};g.winCells=[];g.lastResult=null;g._v05QuickStop=false;g.save();return g;}
function gridRng(grid,next=.95){const E=frame.contentWindow.TombEngine,entries=Object.entries(E.WEIGHTS),total=entries.reduce((n,[,v])=>n+v,0);const values=grid.flat().map(s=>{let sum=0;for(const [key,w]of entries){if(key===s)return(sum+w/2)/total;sum+=w;}});let i=0;return()=>i<values.length?values[i++]:next;}
async function spin(grid,{feature=null,next=.95}={}){const g=reset();if(feature){g.ledger.feature=feature;g.syncLedger();}g.rng=gridRng(grid,next);g.turbo=true;g.save();g.paused=true;g._v05LastRender=0;g.render(performance.now());await raf();tap('RETOMAR');await poll(()=>g.spinning);await poll(()=>[...frame.contentDocument.querySelectorAll('.touch-target')].some(b=>b.getAttribute('aria-label')==='PARAR'),2000);tap('PARAR');await poll(()=>!g.spinning);g.paused=true;return g;}
async function check(name,fn){try{await fn();rows.push({name,pass:true});}catch(e){rows.push({name,pass:false,error:e.message});}const row=rows.at(-1),li=document.createElement('li');li.className=row.pass?'pass':'fail';li.textContent=(row.pass?'PASSOU · ':'FALHOU · ')+name+(row.error?' — '+row.error:'');list.appendChild(li);}
document.getElementById('run').onclick=async()=>{
 document.getElementById('run').disabled=true;rows=[];list.innerHTML='';summary.textContent='Testes em execução…';
 await poll(()=>get()?.ready);
 await check('Arte HD carregada e versão 1.0',()=>{const g=get();assert(frame.contentDocument.documentElement.dataset.faraoVersion==='1.0');assert(Object.values(g.images).every(i=>i.complete&&i.naturalWidth>0&&!i.fallback));assert(g.temple?.naturalWidth>0);});

 await check('Cenário visível através dos cinco tambores',()=>{
  const g=reset(),d=frame.contentDocument,ctx=d.getElementById('canvas').getContext('2d');
  const pixel=(x,y)=>{const v=g.viewport;return [...ctx.getImageData(Math.floor((v.ox+x*v.scale)*v.dpr),Math.floor((v.oy+y*v.scale)*v.dpr),1,1).data];};
  const draw=g.reelBoard;let expected;
  try{g.reelBoard=()=>{};g.render(4000);expected=Array.from({length:5},(_,c)=>pixel(83+c*101,270));}
  finally{g.reelBoard=draw;}
  g.render(4000);
  for(let c=0;c<5;c++){const actual=pixel(83+c*101,270),delta=actual.slice(0,3).reduce((n,v,i)=>n+Math.abs(v-expected[c][i]),0);assert(delta<42,'Fundo opaco no tambor '+(c+1)+': '+delta);}
 });
 await check('Quatro separadores fixos e zoom duplo bloqueado',()=>{
  const g=reset();assert(typeof g.drawReelSeparators==='function');
  let count=0;const draw=g.drawReelSeparators;
  try{g.drawReelSeparators=function(l,t,i,h){count++;assert(l===33&&t===258&&i===101&&h===132);return draw.call(this,l,t,i,h);};g.render(4000);}
  finally{g.drawReelSeparators=draw;}
  assert(count===1,'Separadores não desenhados numa única camada');
  const stage=frame.contentDocument.getElementById('stage'),ev=new frame.contentWindow.MouseEvent('dblclick',{cancelable:true,bubbles:true});
  stage.dispatchEvent(ev);assert(ev.defaultPrevented,'Toque duplo não bloqueado');
 });
 await check('Símbolos HD cabem nos quadrados sem deformação',()=>{
  const g=reset(),ctx=frame.contentDocument.getElementById('canvas').getContext('2d'),draw=ctx.drawImage,positions=[];
  try{ctx.drawImage=function(...args){if(args[0]===g.images.KING||args[0]===g.images.A)positions.push(args);return draw.apply(this,args);};g.drawIcon('KING',33,258,99,132);g.drawIcon('A',33,258,99,132);}
  finally{ctx.drawImage=draw;}
  assert(positions.length===2,'Símbolos não desenhados');
  for(const [img,x,y,w,h] of positions){assert(w<=94&&h<=94,'Imagem maior que o quadrado');assert(x>=33&&x+w<=132&&y>=258&&y+h<=390,'Ícone cortado');assert(Math.abs(w/h-img.naturalWidth/img.naturalHeight)<.003,'Imagem deformada');}
 });
 for(const [w,h]of [[320,568],[375,667],[390,844],[430,932],[768,1024]])await check(`Formato 9:16 e alvos táteis ≥44 px em ${w}×${h}`,async()=>{frame.style.width=w+'px';frame.style.height=h+'px';await raf();await poll(()=>Math.abs(get().viewport.width-frame.contentDocument.getElementById('stage').clientWidth)<2);const d=frame.contentDocument,b=d.getElementById('stage').getBoundingClientRect();assert(Math.abs(b.width/b.height-9/16)<.002);const buttons=[...d.querySelectorAll('.touch-target')];assert(buttons.length>=5);for(const el of buttons){const r=el.getBoundingClientRect();assert(r.width>=43.9&&r.height>=43.9);assert(r.left>=b.left-1&&r.right<=b.right+1&&r.top>=b.top-1&&r.bottom<=b.bottom+1,'Alvo fora do ecrã');}});
 frame.style.width='390px';frame.style.height='844px';await raf();
 await check('Menu, regras, tabela, dez linhas e histórico',()=>{const g=reset();g.openInfo('pays');assert(frame.contentDocument.querySelectorAll('tbody tr').length===10);assert(frame.contentDocument.querySelectorAll('.paylines figure').length===10);g.openInfo('rules');assert(frame.contentDocument.getElementById('modal-content').textContent.includes('8 Free Spins'));g.openInfo('history');g.closeModal();});
 await check('Aposta e linhas através dos controlos',async()=>{const g=reset();await raf();tap('Aumentar aposta');assert(g.bet===2);tap('Diminuir aposta');assert(g.bet===1);g.openInfo();frame.contentDocument.getElementById('line-less').click();assert(g.lines===9);g.closeModal();});
 await check('Giro real e paragem rápida sem duplicar prémio',async()=>{const g=await spin(quiet());assert(!g.spinning&&!g.ledger.pending);assert(g.ledger.sequence===1);assert(g.ledger.stats.paid===1);assert(g.v05Events.gentleStops>0);const balance=g.balance;g.finishSpin();assert(g.balance===balance);});
 await check('3 Scatter ativam 8 Free Spins e bloqueiam a aposta',async()=>{const grid=quiet();for(let c=0;c<3;c++)grid[c][0]='TOMB';const g=await spin(grid);assert(g.feature?.left===8);const before=g.bet;g.changeBet(1);assert(g.bet===before);assert(g.feature.symbols.length===1);});
 await check('Expansão em tambores separados e varrimento sequencial',async()=>{const grid=quiet();grid[1][2]='ANUBIS';grid[4][0]='ANUBIS';const g=await spin(grid,{feature:{left:8,played:0,totalU:0,symbols:['ANUBIS'],retriggers:0}});assert(g.reveal?.reels.join(',')==='1,4');assert(g.lastWin===5);assert(g.lastResult.lineWins.every(w=>w.expanded));});
 await check('Nova ativação soma 8 e outro expansivo',async()=>{const grid=quiet();for(let c=0;c<3;c++)grid[c][0]='TOMB';const g=await spin(grid,{feature:{left:8,played:0,totalU:0,symbols:['KING'],retriggers:0},next:0});assert(g.feature.left===15);assert(g.feature.symbols.length===2);assert(g.feature.retriggers===1);});
 await check('Oito Free Spins automáticas até ao resumo final',async()=>{const g=reset();g.ledger.feature={left:8,played:0,totalU:0,symbols:['KING'],retriggers:0};g.syncLedger();g.turbo=true;g.save();const base=gridRng(quiet());const E=frame.contentWindow.TombEngine,values=quiet().flat().map(sym=>{let sum=0,total=Object.values(E.WEIGHTS).reduce((a,b)=>a+b,0);for(const[k,v]of Object.entries(E.WEIGHTS)){if(k===sym)return(sum+v/2)/total;sum+=v;}});let idx=0;g.rng=()=>values[idx++%15];await poll(()=>frame.contentDocument.getElementById('collect-bonus'),30000);assert(g.ledger.stats.free===8);assert(g.feature===null);frame.contentDocument.getElementById('collect-bonus').click();});
 await check('Repor créditos elimina prémios antigos arriscáveis',()=>{const g=reset(),S=frame.contentWindow.FaraoSession;g.ledger.lastBaseWinU=S.units(10);g.syncLedger();g.openReset();frame.contentDocument.getElementById('reset-go').click();assert(g.balance===300&&g.lastBaseWin===0);});
 await check('Recarga recupera exatamente o resultado pendente',async()=>{const g=reset(),S=frame.contentWindow.FaraoSession;g.rng=gridRng(quiet());S.begin(g.ledger,g.rng);g.save();const expected=S.credits(g.ledger.balanceU)+frame.contentWindow.TombEngine.evaluate(g.ledger.pending.grid,1,10).total;frame.contentWindow.location.reload();await poll(()=>get()&&get()!==g&&get().ready);assert(get().ledger.pending===null);assert(Math.abs(get().balance-expected)<1e-8);assert(get().ledger.history.length===1);});
 await check('Conflito de sessão pausa a janela antiga',()=>{const g=reset(),w=frame.contentWindow;w.dispatchEvent(new w.StorageEvent('storage',{key:'farao-eterno-qa-v2',newValue:'{}'}));assert(g.superseded&&g.paused);assert(!frame.contentDocument.getElementById('session-lock').classList.contains('hidden'));frame.contentDocument.getElementById('session-lock').classList.add('hidden');g.superseded=false;g.paused=false;});
 reset();await raf();
 const failed=rows.filter(r=>!r.pass).length;summary.textContent=`${rows.length-failed}/${rows.length} testes passaram${failed?' · há falhas':''}`;summary.className=failed?'fail':'pass';window.FARAO_QA_RESULTS=rows;
 document.getElementById('metrics').textContent=JSON.stringify({viewport:get().viewport,rendering:get().v05Events,tests:rows.length,failed},null,2);document.getElementById('run').disabled=false;
};
document.getElementById('expansion').onclick=async()=>{await poll(()=>get()?.ready);const grid=quiet();grid[1][2]='ANUBIS';grid[4][0]='ANUBIS';await spin(grid,{feature:{left:8,played:0,totalU:0,symbols:['ANUBIS'],retriggers:0}});};
