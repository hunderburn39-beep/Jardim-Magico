/* PORTÕES DO TROVÃO V3.0 — motor original de demonstração.
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
  // Tabela da versão clássica (expressa em múltiplos da aposta).
  // Índices: 8–9, 10–11, 12–30 símbolos iguais.
  const PAY_TABLE={
    crown:[10,25,50],hourglass:[2.5,10,25],ring:[2,5,15],
    goblet:[1.5,2,12],ruby:[1,1.5,10],amethyst:[.8,1.2,8],
    amber:[.5,1,5],emerald:[.4,.9,4],sapphire:[.25,.75,2]
  };
  function scatterMultiplier(n){return n>=6?100:n===5?5:n===4?3:0}
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
    turbo:false,sound:true,grid:[],currentWin:0,cascade:0
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
    if(p<(state.bonus?.029:.021))return {id:nextId++,kind:"scatter"};
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
      const amount=currentBet()*PAY_TABLE[symbol][n>=12?2:n>=10?1:0];
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
  function sfx(kind,options={}){
    if(state.sound && window.TrovaoAudio) window.TrovaoAudio.play(kind,options);
  }
  function render(grid,options={}){
    el.board.textContent="";
    const fragment=document.createDocumentFragment();
    grid.forEach((symbol,index)=>{
      const tile=document.createElement("div");
      tile.className="cell "+(symbol.kind==="scatter"?"scatter":symbol.kind==="mult"?"multiplier":"");
      const col=index%COLS, row=Math.floor(index/COLS);
      // Todos os símbolos novos caem de cima; os sobreviventes deslocam-se
      // a partir da posição real anterior, em vez de saltarem instantaneamente.
      const was=options.fallFrom?.get(symbol.id);
      if(options.dropIds?.has(symbol.id)||options.fallFrom){
        const oldRow=was===undefined?-2-(index%4):Math.floor(was/COLS);
        const travel=Math.max(0,row-oldRow);
        if(travel>0){
          tile.classList.add("drop-in");
          tile.style.setProperty("--fall-rows",String(-travel));
          tile.style.setProperty("--fall-delay",(col*(state.turbo?.012:.038)+row*(state.turbo?.008:.014))+"s");
          tile.style.setProperty("--fall-duration",state.turbo?".20s":".46s");
        }
      }
      if(options.matchKinds?.has(symbol.kind))tile.classList.add("winner");
      if(options.scatterGlow&&symbol.kind==="scatter")tile.classList.add("scat-hit");
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
    });
    el.board.appendChild(fragment);
    if(options.fallFrom||options.dropIds){
      const landing=state.turbo?95:290;
      for(let col=0;col<COLS;col++){
        // Timbres distintos conforme as colunas encaixam nos rolos.
        window.setTimeout(()=>sfx("drop",{column:col,at:options.fallFrom?1:0}),
          landing+col*(state.turbo?13:36));
      }
    }
  }
  function explodeWinningSymbols(){
    const tiles=Array.from(el.board.querySelectorAll(".cell.winner"));
    if(!tiles.length)return;
    el.board.classList.remove("board-impact");
    void el.board.offsetWidth;
    el.board.classList.add("board-impact");
    const total=tiles.length;
    tiles.forEach((tile,i)=>{
      tile.classList.add("burst-out");
      tile.style.setProperty("--burst-delay",((i%COLS)*(state.turbo?.003:.014))+"s");
      for(let n=0;n<7;n++){
        const shard=document.createElement("span");
        shard.className="crystal-shard";
        const angle=(n*360/7+i*21)*Math.PI/180;
        const reach=48+(n%3)*24;
        shard.style.setProperty("--sx",(Math.cos(angle)*reach)+"%");
        shard.style.setProperty("--sy",(Math.sin(angle)*reach)+"%");
        shard.style.setProperty("--sd",(n%3)*.025+"s");
        shard.style.setProperty("--shard-hue",tile.dataset.kind==="ruby"?"348":tile.dataset.kind==="sapphire"?"192":tile.dataset.kind==="emerald"?"146":tile.dataset.kind==="amethyst"?"278":"44");
        tile.appendChild(shard);
      }
    });
    sfx("burst",{level:Math.min(1.35,.72+total*.035)});
  }
  function showPrizeFloat(amount){
    if(amount<=0)return;
    const item=document.createElement("div");
    item.className="prize-float";
    item.textContent="+"+money(amount);
    el.overlay.appendChild(item);
    window.setTimeout(()=>item.remove(),state.turbo?380:950);
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
  function openModal(title,html,buttons=[]){
    modalOpen=true;const m=el.modal.querySelector(".modal");if(m)m.classList.remove("pay-modal");el.modalTitle.textContent=title;el.modalBody.innerHTML=html;el.modalActions.replaceChildren();
    for(const b of buttons){
      const button=document.createElement("button");button.textContent=b.text;
      if(b.secondary)button.classList.add("secondary");
      button.onclick=()=>{closeModal();if(b.action)b.action()};
      el.modalActions.appendChild(button);
    }
    el.modal.classList.remove("hidden");
  }
  function closeModal(){modalOpen=false;el.modal.classList.add("hidden");const m=el.modal.querySelector(".modal");if(m)m.classList.remove("pay-modal")}
  function showRules(){
    // Ordem e valores da tabela enviada pelo utilizador. Uma única fonte:
    // PAY_TABLE alimenta tanto os prémios do motor como esta grelha.
    const order=["sapphire","emerald","amber","amethyst","ruby","goblet","ring","hourglass","crown"];
    const fmt=v=>String(v).replace(".",",")+"×";
    const symSvg=kind=>'<svg class="pay-symbol" viewBox="0 0 100 100" aria-hidden="true"><use href="assets/simbolos.svg#'+kind+'"></use></svg>';
    const rows=order.map(kind=>
      '<tr><td class="pay-name">'+symSvg(kind)+'<b>'+LABELS[kind]+'</b></td>'+
      PAY_TABLE[kind].map(v=>'<td class="pay-value">'+fmt(v)+'</td>').join("")+'</tr>'
    ).join("");
    const scat='<tr class="scatter-row"><td class="pay-name">'+symSvg("scatter")+
      '<b>SCATTER</b></td><td class="pay-value">3× <small>4 símbolos</small></td>'+
      '<td class="pay-value">5× <small>5 símbolos</small></td>'+
      '<td class="pay-value">100× <small>6+ símbolos</small></td></tr>';
    openModal("TABELA DE PAGAMENTOS",
      '<div class="pay-head">⚡ OS SÍMBOLOS PAGAM EM QUALQUER POSIÇÃO ⚡</div>'+
      '<div class="pay-scroll"><table class="paytable-table"><thead><tr><th>SÍMBOLO</th><th>8–9</th><th>10–11</th><th>12+</th></tr></thead><tbody>'+rows+scat+'</tbody></table></div>'+
      '<div class="pay-rules">'+
      '<p><b>Grelha 6×5:</b> 8 ou mais figuras iguais pagam sem precisar de estar na mesma linha. As peças premiadas desaparecem, as restantes descem e entram novas.</p>'+
      '<p><b>SCATTER:</b> 4, 5 ou 6+ pagam respetivamente 3×, 5× ou 100× a aposta. 4 ou mais ativam 15 rodadas grátis; durante o bónus, 3 ou mais oferecem +5.</p>'+
      '<p><b>Multiplicadores:</b> 2× a 500×, somados numa jogada vencedora; nas rodadas grátis são acumuláveis nas condições do jogo.</p>'+
      '<p><b>Comprar bónus:</b> 100× a aposta em créditos virtuais. Limite de 5 000× a aposta por ciclo.</p>'+
      '<p class="note">Pagamentos idênticos aos valores da tabela de referência que enviaste. Os gráficos e as probabilidades são próprios deste protótipo. RTP não certificado; não utiliza dinheiro real.</p></div>',
      [{text:"FECHAR TABELA",secondary:true}]);
    const m=el.modal.querySelector(".modal");if(m)m.classList.add("pay-modal");
  }
  async function showBig(amount,label){
    if(amount<currentBet()*24)return;
    el.bigTitle.textContent=label||"GRANDE PRÉMIO!";
    el.bigAmount.textContent=money(amount);el.big.classList.remove("hidden");
    sfx("bigwin");await pause(state.turbo?420:1250);el.big.classList.add("hidden");
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
    refresh();sfx("spin");
    await pause(state.turbo?235:560);
    let totalBase=0,spinMult=0,steps=0,scatterTriggered=false,retrigger=false,scatterAward=0,maxScatterPaid=0;
    const countedMultipliers=new Set();
    for(;;){
      const scatterCount=countScatter(state.grid);
      if(scatterCount>=(bonusSpin?3:4)){
        if(bonusSpin)retrigger=true;else scatterTriggered=true;
      }
      if(scatterCount>=4 && scatterCount>maxScatterPaid){
        scatterAward+=bet*(scatterMultiplier(scatterCount)-scatterMultiplier(maxScatterPaid));
        maxScatterPaid=scatterCount;
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
      setMsg("PRÉMIO! "+text+" • QUEDA "+steps+" • "+money(wins.pay));
      showPrizeFloat(wins.pay);
      if(spinMult>0&&steps===1)sfx("mult");
      refresh();
      await pause(state.turbo?105:280);
      explodeWinningSymbols();
      await pause(state.turbo?165:400);
      const oldPositions=new Map(state.grid.map((s,index)=>[s.id,index]));
      state.grid=tumble(state.grid,wins.matched);
      render(state.grid,{fallFrom:oldPositions});
      sfx("tumble");
      await pause(state.turbo?240:550);
    }
    let total=totalBase;
    if(totalBase>0||scatterAward>0){
      if(bonusSpin){
        if(totalBase>0&&spinMult>0)state.bonusMult+=spinMult;
        // O acumulado só potencia rodadas com novo multiplicador vencedor.
        total=totalBase*(totalBase>0&&spinMult>0?Math.max(1,state.bonusMult):1)+scatterAward;
        const remaining=Math.max(0,MAX_WIN_X*bet-state.bonusWon);
        total=Math.min(total,remaining);
        state.bonusWon=Math.round((state.bonusWon+total)*100)/100;
      }else{
        state.visibleMult=totalBase>0&&spinMult>0?spinMult:1;
        total=totalBase*state.visibleMult+scatterAward;
        total=Math.min(MAX_WIN_X*bet,total);
        state.currentWin=Math.round(total*100)/100;
        state.balance=Math.round((state.balance+state.currentWin)*100)/100;
      }
      setMsg(steps+" QUEDA"+(steps!==1?"S":"")+" • "+money(total)+" GANHOS");
      sfx("reward");
    }else{
      state.visibleMult=1;
      setMsg(bonusSpin?"A TROVOADA CONTINUA...":"SEM PRÉMIO • TENTA DE NOVO");
    }
    if(bonusSpin&&retrigger){
      state.freeLeft+=5;setOverlay("+5 RODADAS GRÁTIS");
      sfx("scatter");await pause(state.turbo?250:900);setOverlay("");
    }
    if(!bonusSpin&&scatterTriggered){
      render(state.grid,{scatterGlow:true});
      setOverlay("⚡ 15 RODADAS GRÁTIS ⚡");
      sfx("scatter");await pause(state.turbo?350:1150);setOverlay("");
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
    setOverlay("BÓNUS: "+money(win));sfx("reward");
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
        state.balance=Math.round((state.balance-cost)*100)/100;state.busy=true;refresh();
        setOverlay("⚡ 4 SCATTER ⚡");await pause(state.turbo?200:800);setOverlay("");
        state.busy=false;await startBonus();
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
    if(state.sound)window.TrovaoAudio?.unlock();
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
    $("btnSound").title=state.sound?"Desligar som":"Ativar som";
    window.TrovaoAudio?.setEnabled(state.sound);
    if(state.sound)sfx("reward");
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
  window.TrovaoAudio?.setEnabled(state.sound);
  $("btnSound").textContent=state.sound?"♫":"♪";
  state.grid=makeGrid();render(state.grid);refresh();
})();
