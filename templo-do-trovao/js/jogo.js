/* PORTÕES DO TROVÃO V1.0 — motor original de demonstração.
 * Não afiliado à Pragmatic Play. Sem dinheiro real. RTP não certificado.
 * Símbolos: 8+ iguais em qualquer posição; tumble; scatter; multiplicadores.
 */
"use strict";
(() => {
  const COLS = 6, ROWS = 5, SIZE = COLS * ROWS;
  const SYMBOLS = ["crown","ring","goblet","hourglass","ruby","sapphire","emerald","amethyst","amber"];
  const LABELS = {crown:"Coroa",ring:"Anel",goblet:"Cálice",hourglass:"Ampulheta",ruby:"Rubi",sapphire:"Safira",emerald:"Esmeralda",amethyst:"Ametista",amber:"Âmbar"};
  const WEIGHTS = [5,6,6,7,8,9,9,9,9];
  const MULT_VALUES = [2,3,4,5,6,8,10,12,15,20,25,50,100,250,500];
  const MULT_WEIGHTS = [28,21,15,11,8,6,4,3,2,1.5,1,.45,.18,.045,.012];
  const BETS = [.20,.40,.60,.80,1,1.50,2,3,5,10,20];
  const MAX_WIN_X = 5000;
  const INITIAL_CREDITS = 1000;
  let nextId = 1;
  const $ = id => document.getElementById(id);
  const el = {
    board:$("board"),overlay:$("boardOverlay"),balance:$("balance"),bet:$("betValue"),
    buyCost:$("buyCost"),win:$("winText"),msg:$("gameMessage"),mult:$("totalMult"),
    multHint:$("multHint"),free:$("freeLeft"),freeHint:$("freeHint"),
    cascades:$("cascadeCount"),spin:$("btnSpin"),spinLabel:$("spinLabel"),
    spinGlyph:$("spinGlyph"),spinSub:$("spinSub"),turbo:$("btnTurbo"),
    modal:$("modalBackdrop"),modalTitle:$("modalTitle"),modalBody:$("modalBody"),
    modalActions:$("modalActions"),big:$("bigWin"),bigAmount:$("bigWinAmount"),bigTitle:$("bigWinTitle")
  };
  const state = {
    balance:INITIAL_CREDITS,betIndex:4,busy:false,bonus:false,
    freeLeft:0,bonusWon:0,bonusMult:0,autoRemaining:0,cancelAuto:false,
    turbo:false,sound:false,grid:[],currentWin:0,cascade:0
  };
  let ctx = null, holdTimer = null, longFired = false, modalOpen = false;
  const money = n => new Intl.NumberFormat("pt-PT",{style:"currency",currency:"EUR",minimumFractionDigits:2,maximumFractionDigits:2}).format(Math.max(0,n));
  const currentBet = () => BETS[state.betIndex];
  const pause = ms => new Promise(resolve=>setTimeout(resolve,ms));
  // Browser crypto para gerar escolhas de demonstração sem sequências previsíveis.
  function rand(){
    if(window.crypto && window.crypto.getRandomValues){
      const a = new Uint32Array(1);window.crypto.getRandomValues(a);return a[0]/4294967296;
    }
    return Math.random();
  }
  function weighted(values, weights){
    const total=weights.reduce((a,b)=>a+b,0);
    let p=rand()*total;
    for(let i=0;i<values.length;i++){p-=weights[i];if(p<0)return values[i];}
    return values[values.length-1];
  }
  function newSymbol(){
    const p=rand();
    // Frequências específicas desta demonstração, não da versão comercial.
    if(p<(state.bonus?.029:.032))return {id:nextId++,kind:"scatter"};
    if(p<(state.bonus?.063:.055))return {id:nextId++,kind:"mult",value:weighted(MULT_VALUES,MULT_WEIGHTS)};
    return {id:nextId++,kind:weighted(SYMBOLS,WEIGHTS)};
  }
  function makeGrid(){return Array.from({length:SIZE},newSymbol)}
  function countScatter(grid){return grid.filter(s=>s.kind==="scatter").length}
  function getWins(grid){
    const count=Object.fromEntries(SYMBOLS.map(s=>[s,0]));
    for(const s of grid){if(Object.hasOwn(count,s.kind))count[s.kind]++}
    const result=[];let pay=0;
    for(const symbol of SYMBOLS){
      const n=count[symbol];
      if(n<8)continue;
      const classPay={crown:.82,ring:.76,goblet:.67,hourglass:.59,ruby:.52,sapphire:.43,emerald:.37,amethyst:.32,amber:.29}[symbol];
      const tier=n>=15?15:n>=12?6:n>=10?2.5:1;
      const amount=currentBet()*classPay*tier;
      pay+=amount;result.push({symbol,count:n,amount});
    }
    return {result,pay,matched:new Set(result.map(r=>r.symbol))};
  }
  function tumble(grid,matched){
    const next=Array(SIZE);
    for(let col=0;col<COLS;col++){
      const survivors=[];
      for(let row=ROWS-1;row>=0;row--){
        const cell=grid[row*COLS+col];
        if(!matched.has(cell.kind))survivors.push(cell);
      }
      let row=ROWS-1;
      for(const cell of survivors){next[row*COLS+col]=cell;row--}
      while(row>=0){next[row*COLS+col]=newSymbol();row--}
    }
    return next;
  }
  function render(grid,options={}){
    el.board.textContent="";
    const fragment=document.createDocumentFragment();
    for(const symbol of grid){
      const tile=document.createElement("div");
      tile.className="cell "+(symbol.kind==="scatter"?"scatter":symbol.kind==="mult"?"multiplier":"");
      if(options.dropIds?.has(symbol.id))tile.classList.add("drop-in");
      if(options.matchKinds?.has(symbol.kind))tile.classList.add("winner");
      if(options.scatterGlow && symbol.kind==="scatter")tile.classList.add("scat-hit");
      tile.dataset.kind=symbol.kind;
      const svg=document.createElementNS("http://www.w3.org/2000/svg","svg");
      svg.setAttribute("viewBox","0 0 100 100");
      svg.setAttribute("aria-hidden","true");
      const use=document.createElementNS("http://www.w3.org/2000/svg","use");
      use.setAttribute("href","assets/simbolos.svg#"+symbol.kind);svg.appendChild(use);tile.appendChild(svg);
      if(symbol.kind==="mult"){
        const v=document.createElement("span");v.className="orb-value";
        v.textContent=symbol.value+"×";tile.appendChild(v);
      }
      tile.setAttribute("aria-label",symbol.kind==="mult"?"Multiplicador "+symbol.value+" vezes":symbol.kind==="scatter"?"Scatter":LABELS[symbol.kind]);
      fragment.appendChild(tile);
    }
    el.board.appendChild(fragment);
  }
  function setMsg(t){el.msg.textContent=t}
  function setOverlay(text){
    el.overlay.replaceChildren();
    if(!text)return;
    const p=document.createElement("div");p.className="overlay-banner";p.textContent=text;el.overlay.appendChild(p);
  }
  function refresh(){
    el.balance.textContent=money(state.balance);
    el.bet.textContent=money(currentBet());
    el.buyCost.textContent=money(currentBet()*100);
    el.win.textContent=money(state.bonus?state.bonusWon:state.currentWin);
    el.free.textContent=state.bonus?String(state.freeLeft):"—";
    el.freeHint.textContent=state.bonus?"TOTAL BÓNUS "+money(state.bonusWon):"4 SCATTER ativam 15";
    el.mult.textContent=(state.bonus?Math.max(1,state.bonusMult):Math.max(1,state.visibleMult||1))+"×";
    el.multHint.textContent=state.bonus?"Acumulado no bónus":"Até 500×";
    el.cascades.textContent=String(state.cascade);
    el.spin.classList.toggle("spinning",state.busy);
    el.spin.classList.toggle("auto-mode",state.autoRemaining>0);
    el.spinLabel.textContent=state.autoRemaining>0?"AUTO "+state.autoRemaining:state.busy?"A RODAR":"GIRAR";
    el.spinGlyph.textContent=state.autoRemaining>0?"⟳":"↻";
    el.spinSub.textContent=state.autoRemaining>0?"TOCAR PARA PARAR":state.bonus?"BÓNUS ATIVO":"MANTER 3 S = AUTO";
    el.turbo.classList.toggle("active",state.turbo);
    el.turbo.querySelector("small").textContent=state.turbo?"ON":"OFF";
    el.turbo.setAttribute("aria-pressed",String(state.turbo));
    const lock=state.busy||state.bonus||state.autoRemaining>0;
    $("btnBuy").disabled=lock;$("betDown").disabled=lock;$("betUp").disabled=lock;
  }
  function beep(freq=560,dur=.07,vol=.035){
    if(!state.sound)return;
    try{
      ctx=ctx||new (window.AudioContext||window.webkitAudioContext)();
      if(ctx.state==="suspended")ctx.resume();
      const osc=ctx.createOscillator(),gain=ctx.createGain();
      osc.type="triangle";osc.frequency.setValueAtTime(freq,ctx.currentTime);
      gain.gain.setValueAtTime(vol,ctx.currentTime);gain.gain.exponentialRampToValueAtTime(.001,ctx.currentTime+dur);
      osc.connect(gain);gain.connect(ctx.destination);osc.start();osc.stop(ctx.currentTime+dur);
    }catch(_){}
  }
  function openModal(title,html,buttons=[]){
    modalOpen=true;el.modalTitle.textContent=title;el.modalBody.innerHTML=html;el.modalActions.replaceChildren();
    for(const b of buttons){
      const button=document.createElement("button");button.textContent=b.text;
      if(b.secondary)button.classList.add("secondary");
      button.onclick=()=>{closeModal();if(b.action)b.action()};
      el.modalActions.appendChild(button);
    }
    el.modal.classList.remove("hidden");
  }
  function closeModal(){modalOpen=false;el.modal.classList.add("hidden")}
  function showRules(){
    const rows=SYMBOLS.map(s=>{const factors={crown:.82,ring:.76,goblet:.67,hourglass:.59,ruby:.52,sapphire:.43,emerald:.37,amethyst:.32,amber:.29};let a=factors[s];return "<tr><td>"+LABELS[s]+"</td><td>"+a.toFixed(2).replace(".",",")+"×</td><td>"+(a*2.5).toFixed(2).replace(".",",")+"×</td><td>"+(a*6).toFixed(2).replace(".",",")+"×</td></tr>"}).join("");
    openModal("REGRAS E PAGAMENTOS",
      "<p><b>30 posições, 6 colunas × 5 linhas.</b> 8 ou mais símbolos iguais pagam em qualquer lugar do ecrã. Os símbolos premiados desaparecem e os restantes caem, criando novas combinações.</p>"+
      "<p><b>SCATTER:</b> 4 ou mais na mesma grelha ativam 15 rodadas grátis; no bónus, 3 ou mais oferecem +5 rodadas.</p>"+
      "<p><b>MULTIPLICADORES:</b> esferas de 2× a 500× são somadas numa sequência vencedora. No bónus, quando surgem em rodada premiada, somam-se ao multiplicador acumulado.</p>"+
      "<p><b>Compra de bónus:</b> 100× a aposta. Prémio máximo por ciclo: 5 000× a aposta.</p>"+
      "<table><thead><tr><th>Símbolo</th><th>8–9</th><th>10–11</th><th>12–14</th></tr></thead><tbody>"+rows+"</tbody></table>"+
      "<p class='note'>Valores são multiplicadores da aposta. Com 15 ou mais símbolos paga-se 15× o valor da coluna 8–9. Tabela e probabilidades originais desta demonstração; não são a tabela oficial da Pragmatic Play. RTP não calculado ou certificado. Sem dinheiro real.</p>",
      [{text:"ENTENDI",secondary:true}]);
  }
  async function showBig(amount,label){
    if(amount<currentBet()*24)return;
    el.bigTitle.textContent=label||"GRANDE PRÉMIO!";
    el.bigAmount.textContent=money(amount);el.big.classList.remove("hidden");
    beep(820,.3,.07);await pause(state.turbo?420:1250);el.big.classList.add("hidden");
  }
  async function playSpin(){
    if(state.busy)return false;
    const bonusSpin=state.bonus;
    const bet=currentBet();
    if(!bonusSpin&&state.balance+1e-9<bet){
      openModal("SALDO INSUFICIENTE","<p>Reponha créditos virtuais para continuar. Esta versão não aceita depósitos.</p>",[{text:"REPOR 1 000 €",action:refill},{text:"FECHAR",secondary:true}]);
      return false;
    }
    state.busy=true;
    if(!bonusSpin){state.balance=Math.round((state.balance-bet)*100)/100;state.currentWin=0}
    else state.freeLeft--;
    state.cascade=0;state.visibleMult=1;
    state.grid=makeGrid();
    const previousIds=new Set(); // preenchimento inicial anima como queda
    state.grid.forEach(s=>previousIds.add(s.id));
    render(state.grid,{dropIds:previousIds});
    setMsg(bonusSpin?"RODADA GRÁTIS • PODER ACUMULADO":"OS DEUSES ESTÃO A GIRAR...");
    refresh();beep(370,.06);
    await pause(state.turbo?95:410);
    let totalBase=0,spinMult=0,steps=0,scatterTriggered=false,retrigger=false;
    const countedMultipliers=new Set();
    for(;;){
      if(countScatter(state.grid)>=(bonusSpin?3:4)){
        if(bonusSpin)retrigger=true;else scatterTriggered=true;
      }
      for(const s of state.grid){
        if(s.kind==="mult"&&!countedMultipliers.has(s.id)){
          countedMultipliers.add(s.id);spinMult+=s.value;
        }
      }
      const wins=getWins(state.grid);
      if(!wins.result.length||steps>=36)break; // limite de proteção contra ciclos excessivos
      steps++;state.cascade=steps;
      totalBase+=wins.pay;
      render(state.grid,{matchKinds:wins.matched});
      const text=wins.result.map(w=>LABELS[w.symbol]+" ×"+w.count).join(" + ");
      setMsg("PRÉMIO! "+text+" • QUEDA "+steps);
      beep(520+steps*42,.11,.06);
      refresh();
      await pause(state.turbo?85:520);
      const oldIds=new Set(state.grid.map(s=>s.id));
      state.grid=tumble(state.grid,wins.matched);
      const bornIds=new Set(state.grid.filter(s=>!oldIds.has(s.id)).map(s=>s.id));
      render(state.grid,{dropIds:bornIds});
      await pause(state.turbo?80:390);
    }
    let total=totalBase;
    if(totalBase>0){
      if(bonusSpin){
        if(spinMult>0)state.bonusMult+=spinMult;
        // O acumulado só potencia rodadas com novo multiplicador vencedor.
        total=totalBase*(spinMult>0?Math.max(1,state.bonusMult):1);
        state.bonusWon=Math.min(MAX_WIN_X*bet,state.bonusWon+total);
        total=Math.min(total,MAX_WIN_X*bet-(state.bonusWon-total));
      }else{
        state.visibleMult=spinMult>0?spinMult:1;
        total=totalBase*state.visibleMult;
        total=Math.min(MAX_WIN_X*bet,total);
        state.currentWin=Math.round(total*100)/100;
        state.balance=Math.round((state.balance+state.currentWin)*100)/100;
      }
      setMsg(steps+" QUEDA"+(steps!==1?"S":"")+" • "+money(total)+" GANHOS");
      beep(750,.15);
    }else{
      state.visibleMult=1;
      setMsg(bonusSpin?"A TROVOADA CONTINUA...":"SEM PRÉMIO • TENTA DE NOVO");
    }
    if(bonusSpin&&retrigger){
      state.freeLeft+=5;setOverlay("+5 RODADAS GRÁTIS");
      beep(930,.22,.08);await pause(state.turbo?250:900);setOverlay("");
    }
    if(!bonusSpin&&scatterTriggered){
      render(state.grid,{scatterGlow:true});
      setOverlay("⚡ 15 RODADAS GRÁTIS ⚡");
      beep(900,.24,.085);await pause(state.turbo?350:1150);setOverlay("");
    }
    refresh();state.busy=false;refresh();
    if(bonusSpin&&state.bonusWon>=MAX_WIN_X*bet-0.001){state.freeLeft=0}
    if(bonusSpin&&state.freeLeft<=0){
      await finishBonus();
    }else if(!bonusSpin&&scatterTriggered){
      await startBonus();
    }else if(!bonusSpin){
      await showBig(state.currentWin);
    }
    return true;
  }
  async function startBonus(){
    state.bonus=true;state.freeLeft=15;state.bonusWon=0;state.bonusMult=0;
    el.free.parentElement.classList.add("bonus-active");
    setMsg("BÓNUS ATIVO • 15 RODADAS GRÁTIS");refresh();
    await pause(state.turbo?180:400);
    // Executado automaticamente; o utilizador pode terminar a automatização.
    while(state.bonus&&state.freeLeft>0){
      const ok=await playSpin();
      if(!ok)break;
      await pause(state.turbo?120:340);
    }
  }
  async function finishBonus(){
    if(!state.bonus)return;
    state.bonus=false;state.freeLeft=0;state.bonusMult=0;
    state.balance=Math.round((state.balance+state.bonusWon)*100)/100;
    state.currentWin=Math.round(state.bonusWon*100)/100;
    const win=state.currentWin;
    state.bonusWon=0;state.visibleMult=1;
    el.free.parentElement.classList.remove("bonus-active");
    setMsg("BÓNUS TERMINADO • GANHASTE "+money(win));refresh();
    setOverlay("BÓNUS: "+money(win));beep(860,.27,.08);
    await showBig(win,"PRÉMIO DO BÓNUS");
    await pause(state.turbo?220:750);setOverlay("");
  }
  async function startNormal(){
    if(state.busy||state.bonus||modalOpen)return;
    if(state.autoRemaining>0){stopAuto();return}
    await playSpin();
  }
  function stopAuto(){
    state.autoRemaining=0;state.cancelAuto=true;setMsg("AUTOMÁTICO CANCELADO");refresh();
  }
  async function runAuto(){
    state.cancelAuto=false;
    while(state.autoRemaining>0&&!state.cancelAuto){
      if(state.busy||state.bonus){await pause(250);continue}
      state.autoRemaining--;refresh();
      const ok=await playSpin();if(!ok){stopAuto();break}
      if(state.cancelAuto)break;
      await pause(state.turbo?130:600);
    }
    state.autoRemaining=0;refresh();
  }
  function showAutoOptions(){
    if(state.busy||state.bonus||modalOpen)return;
    openModal("RODADAS AUTOMÁTICAS",
      "<p>Escolhe quantas rodadas queres executar. Podes tocar em <b>GIRAR</b> a qualquer momento para parar. Mantém o botão premido 3 segundos para abrir novamente este menu.</p>"+
      "<p class='note'>Aposta e saldo são sempre de demonstração.</p>",
      [10,25,50,100].map(n=>({text:n+" RODADAS",action:()=>{state.autoRemaining=n;refresh();void runAuto()}})).concat([{text:"CANCELAR",secondary:true}]));
  }
  function buyBonus(){
    if(state.busy||state.bonus||state.autoRemaining>0)return;
    const cost=currentBet()*100;
    openModal("COMPRAR 15 RODADAS",
      "<p>Ativar 15 rodadas grátis por <b>"+money(cost)+"</b> em créditos virtuais?</p>"+
      "<p>Durante o bónus podes ganhar +5 rodadas sempre que aparecerem 3 ou mais símbolos SCATTER na grelha.</p>",
      [{text:"COMPRAR "+money(cost),action:async()=>{
        if(state.balance<cost){openModal("SALDO INSUFICIENTE","<p>Não tens créditos virtuais suficientes para comprar o bónus.</p>",[{text:"REPOR 1 000 €",action:refill},{text:"FECHAR",secondary:true}]);return}
        state.balance=Math.round((state.balance-cost)*100)/100;refresh();
        setOverlay("⚡ 4 SCATTER ⚡");await pause(state.turbo?200:800);setOverlay("");
        await startBonus();
      }},{text:"CANCELAR",secondary:true}]);
  }
  function refill(){
    if(state.busy||state.bonus)return;
    state.balance=Math.round((state.balance+INITIAL_CREDITS)*100)/100;
    setMsg("1 000 € EM CRÉDITOS VIRTUAIS ADICIONADOS");refresh();
  }
  function changeBet(delta){
    if(state.busy||state.bonus||state.autoRemaining>0)return;
    state.betIndex=Math.max(0,Math.min(BETS.length-1,state.betIndex+delta));refresh();
  }
  // Clique curto roda; manter 3 s abre o menu de auto-spins.
  el.spin.addEventListener("pointerdown",e=>{
    if(e.button!==undefined&&e.button!==0)return;
    longFired=false;clearTimeout(holdTimer);
    if(state.busy||state.bonus||state.autoRemaining>0)return;
    holdTimer=setTimeout(()=>{longFired=true;showAutoOptions()},3000);
  });
  for(const name of ["pointerup","pointercancel","pointerleave"]){
    el.spin.addEventListener(name,()=>clearTimeout(holdTimer));
  }
  el.spin.addEventListener("click",()=>{
    clearTimeout(holdTimer);
    if(longFired){longFired=false;return}
    if(state.autoRemaining>0){stopAuto();return}
    void startNormal();
  });
  $("btnHelp").onclick=showRules;
  $("modalClose").onclick=closeModal;
  el.modal.addEventListener("click",event=>{if(event.target===el.modal)closeModal()});
  $("betDown").onclick=()=>changeBet(-1);
  $("betUp").onclick=()=>changeBet(1);
  $("btnBuy").onclick=buyBonus;
  $("btnRefill").onclick=refill;
  $("btnTurbo").onclick=()=>{state.turbo=!state.turbo;refresh()};
  $("btnSound").onclick=()=>{
    state.sound=!state.sound;
    $("btnSound").textContent=state.sound?"♫":"♪";
    $("btnSound").title=state.sound?"Desligar som":"Ativar som";beep(670);
  };
  $("btnFull").onclick=async()=>{
    try{
      if(document.fullscreenElement)await document.exitFullscreen();
      else if(document.documentElement.requestFullscreen)await document.documentElement.requestFullscreen();
    }catch(_){setMsg("ECRÃ COMPLETO NÃO DISPONÍVEL NESTE NAVEGADOR")}
  };
  document.addEventListener("keydown",e=>{
    if(e.key==="Escape"&&modalOpen){closeModal();return}
    if(modalOpen||e.repeat||e.target instanceof HTMLButtonElement)return;
    if(e.code==="Space"||e.code==="Enter"){e.preventDefault();if(state.autoRemaining>0)stopAuto();else void startNormal()}
  });
  state.grid=makeGrid();render(state.grid);refresh();
})();
