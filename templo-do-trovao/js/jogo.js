/* PORTÕES DO TROVÃO V14.0 — motor original de demonstração.
 * Não afiliado à Pragmatic Play. Sem dinheiro real. RTP não certificado.
 * Símbolos: 8+ iguais em qualquer posição; tumble; scatter; multiplicadores.
 */
"use strict";
(() => {
  const COLS = 6, ROWS = 5, SIZE = COLS * ROWS;
  const SYMBOLS = ["crown","ring","goblet","hourglass","ruby","sapphire","emerald","amethyst","amber"];
  const RULES=window.TrovaoRules;
  if(!RULES)throw Error("Motor WILD indisponível");
  const LABELS = {crown:"Coroa",ring:"Anel",goblet:"Cálice",hourglass:"Ampulheta",ruby:"Rubi",sapphire:"Safira",emerald:"Esmeralda",amethyst:"Ametista",amber:"Âmbar",wild:"WILD"};
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
  // Recortes do atlas 3D (5 colunas, 3 linhas); as 11 peças têm arte própria.
  const SPRITES=Object.freeze({
    sapphire:[0,0],emerald:[1,0],amber:[2,0],amethyst:[3,0],ruby:[4,0],
    goblet:[0,1],ring:[1,1],hourglass:[2,1],crown:[3,1],scatter:[4,1],mult:[0,2]
  });
  const FALLBACK=Object.freeze({
    sapphire:"💎",emerald:"🔺",amber:"🔶",amethyst:"🔻",ruby:"❤️",wild:"🌟",
    goblet:"🏆",ring:"💍",hourglass:"⌛",crown:"👑",scatter:"⚡",mult:"✦"
  });
  function spriteStyle(kind){
    const xy=SPRITES[kind];
    if(!xy)return ["0%","0%"];
    return [(xy[0]*25)+"%",(xy[1]*50)+"%"];
  }
  function makeSprite(kind){
    const art=document.createElement("span");
    art.className="symbol-bitmap";
    if(kind==="wild")art.classList.add("wild-bitmap");
    const [ax,ay]=spriteStyle(kind);
    art.style.setProperty("--ax",ax);art.style.setProperty("--ay",ay);
    const fallback=document.createElement("span");
    fallback.className="symbol-fallback-text";
    fallback.setAttribute("aria-hidden","true");
    fallback.textContent=FALLBACK[kind]||"✦";
    art.appendChild(fallback);
    return art;
  }
  // Verificação de imagem: nenhum símbolo fica invisível em caso de falha de rede.
  function preflightSymbols(){
    if(typeof Image==="undefined"){document.body.classList.add("asset-fallback");return}
    const test=new Image();
    test.onload=()=>document.body.classList.add("assets-ready");
    test.onerror=()=>document.body.classList.add("asset-fallback");
    test.src="assets/atlas-3d-v5.webp";
    const wildTest=new Image();
    wildTest.onload=()=>document.body.classList.add("wild-asset-ready");
    wildTest.onerror=()=>document.body.classList.add("wild-asset-fallback");
    wildTest.src="../assets/imagens/05ad729435e7487db8.png";
  }
  const INITIAL_CREDITS = 1000;
  let nextId = 1;
  const $ = id => document.getElementById(id);
  const el = {
    board:$("board"),overlay:$("boardOverlay"),fx:$("winningFx"),balance:$("balance"),bet:$("betValue"),
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
  let landingTimers = [];
  // Reutilização de células evita recriar 30 imagens a cada queda.
  const tileCache = new Map();
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
  function newSymbol(){return RULES.draw(state.bonus,nextId++,rand)}
  function makeGrid(){return Array.from({length:SIZE},newSymbol)}
  function countScatter(grid){return grid.filter(s=>s.kind==="scatter").length}
  function getWins(grid){return RULES.score(grid,PAY_TABLE,currentBet())}
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
    for(const timer of landingTimers)clearTimeout(timer);
    landingTimers=[];
    const fragment=document.createDocumentFragment();
    const present=new Set();
    const landings=[];
    grid.forEach((symbol,index)=>{
      present.add(symbol.id);
      let tile=tileCache.get(symbol.id);
      if(!tile){
        tile=document.createElement("div");
        tile.appendChild(makeSprite(symbol.kind));
        if(symbol.kind==="mult"||symbol.kind==="wild"){
          const v=document.createElement("span");
          v.className=symbol.kind==="wild"?"wild-value":"orb-value";
          v.textContent=symbol.kind==="wild"?"×"+symbol.value:symbol.value+"×";
          tile.appendChild(v);
        }
        tileCache.set(symbol.id,tile);
      }
      tile.className="cell "+(symbol.kind==="scatter"?"scatter":symbol.kind==="mult"?"multiplier":symbol.kind==="wild"?"wild":"");
      const col=index%COLS,row=Math.floor(index/COLS);
      const was=options.fallFrom?.get(symbol.id);
      if(options.dropIds?.has(symbol.id)||options.fallFrom){
        const oldRow=was===undefined?-2-(index%4):Math.floor(was/COLS);
        const travel=Math.max(0,row-oldRow);
        if(travel>0){
          tile.classList.add("drop-in");
          const delay=index*(state.turbo?9:25);
          const duration=state.turbo?210:450;
          tile.style.setProperty("--fall-rows",String(-travel));
          tile.style.setProperty("--fall-delay",delay/1000+"s");
          tile.style.setProperty("--fall-duration",duration/1000+"s");
          landings.push({column:col,row,kind:symbol.kind,when:Math.round(delay+duration*.83)});
        }
      }
      if(options.matchKinds?.has(symbol.kind))tile.classList.add("winner");
      if(options.scatterGlow&&symbol.kind==="scatter")tile.classList.add("scat-hit");
      tile.dataset.kind=symbol.kind;
      tile.setAttribute("aria-label",symbol.kind==="mult"?"Multiplicador "+symbol.value+" vezes":symbol.kind==="wild"?"WILD "+symbol.value+" vezes":symbol.kind==="scatter"?"Scatter":LABELS[symbol.kind]);
      fragment.appendChild(tile);
    });
    // Move as mesmas imagens sobreviventes em vez de reconstruí-las.
    el.board.replaceChildren(fragment);
    for(const id of tileCache.keys())if(!present.has(id))tileCache.delete(id);
    if(landings.length&&state.sound){
      // Web Audio agenda as peças sem 30 setTimeout simultâneos.
      if(window.TrovaoLeather?.schedule){
        window.TrovaoLeather.schedule(landings);
      }else{
        for(const landing of landings){
          landingTimers.push(window.setTimeout(()=>{
            window.TrovaoLeather?.play({
              column:landing.column,row:landing.row,kind:landing.kind,
              strength:.59+landing.row*.047
            });
          },landing.when));
        }
      }
      // O áudio do SCATTER continua sincronizado com o pousar da peça.
      const scat=landings.filter(landing=>landing.kind==="scatter");
      if(scat.length)landingTimers.push(window.setTimeout(()=>sfx("scatter"),scat[0].when));
    }
    const lastLanding=landings.reduce((max,landing)=>Math.max(max,landing.when),0);
    return landings.length?lastLanding+95:0;
  }
  function explodeWinningSymbols(){
    const tiles=el.board.querySelectorAll(".cell.winner");
    if(!tiles.length)return 0;
    const duration=state.turbo?310:570;
    const reduced=window.matchMedia?.("(prefers-reduced-motion: reduce)")?.matches===true;
    const mobile=window.matchMedia?.("(max-width: 850px)")?.matches===true;
    // Dois ou três fragmentos por peça, não dezenas. Sem medições
    // de geometria por tile nem cópias WebP sobrepostas.
    const shards=reduced?0:mobile?2:3;
    const palette={
      sapphire:"#69dafa",emerald:"#62fdb2",amber:"#ffcb63",
      amethyst:"#dc83ff",ruby:"#ff7394",goblet:"#aaa4ff",
      ring:"#f1c16e",hourglass:"#7fffe0",crown:"#ffeb99",
      scatter:"#a5efff",mult:"#ffcf93",wild:"#f3d182"
    };
    el.fx?.replaceChildren();
    for(let i=0;i<tiles.length;i++){
      const tile=tiles[i];
      tile.classList.add("burst-out");
      tile.style.setProperty("--burst-lifetime",duration+"ms");
      tile.style.setProperty("--burst-color",palette[tile.dataset.kind]||"#ffe6af");
      for(let n=0;n<shards;n++){
        const shard=document.createElement("i");
        shard.className="v12-fragment";
        const degrees=(360/shards)*n+(i*37)%120;
        const rad=degrees*Math.PI/180;
        const distance=31+(i+n)%3*16;
        shard.style.setProperty("--fx",Math.cos(rad)*distance+"px");
        shard.style.setProperty("--fy",Math.sin(rad)*distance+"px");
        shard.style.setProperty("--rz",(n%2?1:-1)*(180+n*80)+"deg");
        tile.appendChild(shard);
      }
    }
    sfx("burst",{level:Math.min(1.24,.72+tiles.length*.025)});
    if(tiles.length>=10)window.TrovaoScene?.reactToWin();
    return reduced?70:duration;
  }
  function showPrizeFloat(amount,prizes=[]){
    if(amount<=0)return;
    const existing=el.overlay.querySelectorAll(".prize-float");
    if(existing.length>=2)existing[0].remove();
    const item=document.createElement("div");
    item.className="prize-float";
    item.appendChild(document.createTextNode("+"+money(amount)));
    if(prizes.length){
      const detail=document.createElement("small");
      detail.textContent=prizes.map(p=>LABELS[p.symbol]+" ×"+p.count+(p.wildCount?" + WILD ×"+p.wildMultiplier:"")+" • "+money(p.amount)).join("  |  ");
      item.appendChild(detail);
    }
    const lifetime=state.turbo?1250:2350;
    item.style.setProperty("--prize-duration",lifetime+"ms");
    el.overlay.appendChild(item);
    window.setTimeout(()=>item.remove(),lifetime+35);
  }
  function setMsg(t){el.msg.textContent=t}
  function setOverlay(text){
    el.overlay.replaceChildren();
    if(!text)return;
    const p=document.createElement("div");p.className="overlay-banner";p.textContent=text;el.overlay.appendChild(p);
  }
  function refresh(){
    $("app").classList.toggle("is-spinning",state.busy);
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
    el.spinSub.textContent=state.autoRemaining>0?"TOCAR PARA PARAR":state.bonus?"BÓNUS ATIVO":"MANTER 1 S = AUTO";
    el.turbo.classList.toggle("active",state.turbo);
    el.turbo.querySelector("small").textContent=state.turbo?"ON":"OFF";
    el.turbo.setAttribute("aria-pressed",String(state.turbo));
    const lock=state.busy||state.bonus||state.autoRemaining>0;
    $("btnBuy").disabled=lock;$("betDown").disabled=lock;$("betUp").disabled=lock;
  }
  function openModal(title,html,buttons=[]){
    modalOpen=true;const m=el.modal.querySelector(".modal");if(m){m.classList.remove("pay-modal");m.classList.remove("auto-modal")}el.modalTitle.textContent=title;el.modalBody.innerHTML=html;el.modalActions.replaceChildren();
    for(const b of buttons){
      const button=document.createElement("button");button.textContent=b.text;
      if(b.secondary)button.classList.add("secondary");
      button.onclick=()=>{closeModal();if(b.action)b.action()};
      el.modalActions.appendChild(button);
    }
    el.modal.classList.remove("hidden");
  }
  function closeModal(){modalOpen=false;el.modal.classList.add("hidden");const m=el.modal.querySelector(".modal");if(m){m.classList.remove("pay-modal");m.classList.remove("auto-modal")}}
  function showRules(){
    // Ordem e valores da tabela enviada pelo utilizador. Uma única fonte:
    // PAY_TABLE alimenta tanto os prémios do motor como esta grelha.
    const order=["sapphire","emerald","amber","amethyst","ruby","goblet","ring","hourglass","crown"];
    const fmt=v=>String(v).replace(".",",")+"×";
    const symSvg=kind=>{
      const [ax,ay]=spriteStyle(kind);
      return '<span class="pay-symbol symbol-bitmap'+(kind==="wild"?" wild-bitmap":"")+'" aria-hidden="true" style="--ax:'+ax+';--ay:'+ay+'"><span class="symbol-fallback-text">'+FALLBACK[kind]+'</span></span>';
    };
    const rows=order.map(kind=>
      '<tr><td class="pay-name">'+symSvg(kind)+'<b>'+LABELS[kind]+'</b></td>'+
      PAY_TABLE[kind].map(v=>'<td class="pay-value">'+fmt(v)+'</td>').join("")+'</tr>'
    ).join("");
    const scat='<tr class="scatter-row"><td class="pay-name">'+symSvg("scatter")+
      '<b>SCATTER</b></td><td class="pay-value">3× <small>4 símbolos</small></td>'+
      '<td class="pay-value">5× <small>5 símbolos</small></td>'+
      '<td class="pay-value">100× <small>6+ símbolos</small></td></tr>';
    const wildRow='<tr class="wild-payrow"><td class="pay-name">'+symSvg("wild")+'<b>WILD</b></td>'+
      '<td colspan="3">Substitui qualquer uma das 9 peças pagantes (1×–5×). Nunca substitui SCATTER, esferas MULT ou outros WILDs. Só vale para a melhor combinação da queda.</td></tr>';
    openModal("TABELA DE PAGAMENTOS",
      '<div class="pay-head">⚡ OS SÍMBOLOS PAGAM EM QUALQUER POSIÇÃO ⚡</div>'+
      '<div class="pay-scroll"><table class="paytable-table"><thead><tr><th>SÍMBOLO</th><th>8–9</th><th>10–11</th><th>12+</th></tr></thead><tbody>'+rows+scat+wildRow+'</tbody></table></div>'+
      '<div class="pay-rules">'+
      '<p><b>Grelha 6×5:</b> 8 ou mais figuras iguais pagam sem precisar de estar na mesma linha. As peças premiadas desaparecem, as restantes descem e entram novas.</p>'+
      '<p><b>SCATTER:</b> 4, 5 ou 6+ pagam respetivamente 3×, 5× ou 100× a aposta. 4 ou mais ativam 15 rodadas grátis; durante o bónus, 3 ou mais oferecem +5.</p>'+
      '<p><b>WILD (1×–5×):</b> substitui todas as 9 peças pagantes para formar 8 ou mais; NUNCA substitui SCATTER, esferas multiplicadoras nem outros WILDs. Só participa na melhor combinação da queda, usando o maior multiplicador WILD (até 5×), sem duplicar pagamentos. A imagem é a mesma do Jardim Mágico.</p>'+
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
    el.fx?.replaceChildren();
    if(!bonusSpin){state.balance=Math.round((state.balance-bet)*100)/100;state.currentWin=0}
    else state.freeLeft--;
    state.cascade=0;state.visibleMult=1;
    setMsg(bonusSpin?"RODADA GRÁTIS • PODER ACUMULADO":"OS DEUSES ESTÃO A GIRAR...");
    refresh();
    // Primeira ação audível, ainda no gesto do utilizador. O MP3 salta
    // o silêncio inicial; só depois começa a animação das peças.
    sfx("spin");
    await pause(state.turbo?65:150);
    state.grid=makeGrid();
    window.TrovaoHistory?.startSpin({bet,bonus:bonusSpin,grid:state.grid});
    const previousIds=new Set();
    state.grid.forEach(s=>previousIds.add(s.id));
    const fallFinished=render(state.grid,{dropIds:previousIds});
    await pause(fallFinished+(state.turbo?75:130));
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
      window.TrovaoHistory?.recordWin(wins);
      render(state.grid,{matchKinds:wins.matched});
      const text=wins.result.map(w=>LABELS[w.symbol]+" ×"+w.count+(w.wildCount?" com "+w.wildCount+" WILD ×"+w.wildMultiplier:"")).join(" + ");
      setMsg("PRÉMIO! "+text+" • QUEDA "+steps+" • "+money(wins.pay));
      showPrizeFloat(wins.pay,wins.result);
      if(spinMult>0&&steps===1)sfx("mult");
      refresh();
      await pause(state.turbo?105:280);
      const explosionTime=explodeWinningSymbols();
      // Esperar que os cristais se partam e se dissipem antes da queda.
      await pause(explosionTime+(state.turbo?35:40));
      const oldPositions=new Map(state.grid.map((s,index)=>[s.id,index]));
      state.grid=tumble(state.grid,wins.matched);
      const bornIds=new Set(state.grid.filter(s=>!oldPositions.has(s.id)).map(s=>s.id));
      window.TrovaoHistory?.recordFall(state.grid,bornIds);
      sfx("tumble");
      const tumbleFinished=render(state.grid,{fallFrom:oldPositions});
      await pause(tumbleFinished+(state.turbo?70:105));
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
    window.TrovaoHistory?.endSpin({
      total,scatterAward,mult:bonusSpin?(state.bonusMult||1):(state.visibleMult||1)
    });
    if(bonusSpin&&retrigger){
      state.freeLeft+=5;setOverlay("+5 RODADAS GRÁTIS");
      sfx("scatter");await pause(state.turbo?250:900);setOverlay("");
    }
    if(!bonusSpin&&scatterTriggered){
      render(state.grid,{scatterGlow:true});
      setOverlay("⚡ 15 RODADAS GRÁTIS ⚡");
      window.TrovaoScene?.burst();
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
    window.TrovaoScene?.burst();
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
      '<span class="auto-symbol">⚡ ✦ ⚡</span>'+
      '<p class="auto-instructions">Escolhe as rodadas que os deuses vão girar. '+
      'Para interromper, toca novamente em <b>GIRAR</b>.</p>'+
      '<p class="auto-sub">PRIME O BOTÃO DE GIRAR DURANTE 1 SEGUNDO PARA ABRIR ESTE QUADRO.</p>'+
      '<p class="note">Jogo demonstrativo com créditos virtuais.</p>',
      [10,25,50,100].map(n=>({text:n+" RODADAS",action:()=>{state.autoRemaining=n;refresh();void runAuto()}})).concat([{text:"FECHAR",secondary:true}]));
    el.modal.querySelector(".modal")?.classList.add("auto-modal");
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
  // Clique curto roda; manter 1 segundo abre o menu de auto-spins.
  el.spin.addEventListener("pointerdown",e=>{
    if(e.button!==undefined&&e.button!==0)return;
    longFired=false;clearTimeout(holdTimer);
    if(state.busy||state.bonus||state.autoRemaining>0)return;
    if(state.sound){window.TrovaoAudio?.unlock();window.TrovaoLeather?.unlock();}
    holdTimer=setTimeout(()=>{longFired=true;showAutoOptions()},1000);
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
    window.TrovaoLeather?.setEnabled(state.sound);
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
  preflightSymbols();
  state.grid=makeGrid();render(state.grid);refresh();
})();
