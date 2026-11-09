/* Halloween — motor original para jogo com créditos de entretenimento. */
(function (root) {
  "use strict";
  const ROWS = 3, REELS = 5, MAX_BALANCE = 1e12;
  const PUMPKINS_FOR_BONUS = 5;
  // Valores guardados em cêntimos fictícios, não dinheiro real.
  // Aposta total por jogada e linhas formam um escalão indivisível.
  const BET_LEVELS=Object.freeze([
    {bet:1,lines:1},{bet:5,lines:5},{bet:10,lines:15},
    {bet:15,lines:25},{bet:20,lines:30}
  ].map(x=>Object.freeze(x)));
  const tierFor=(bet,lines)=>{
    return BET_LEVELS.find(t=>t.bet===bet&&t.lines===lines)
      || BET_LEVELS.find(t=>t.lines===lines)
      || (lines===20 ? BET_LEVELS[3] : null)
      || BET_LEVELS.find(t=>t.bet===bet)
      || BET_LEVELS[0];
  };
  const POT_VALUES = Object.freeze([1,2,3,5,8]);
  // O APK contém um ecrã de 12 morangos selecionáveis. O sorteio
  // e os multiplicadores abaixo são originais do protótipo, não do APK.
  const STRAWBERRY_CHOICES=12;
  const STRAWBERRY_VALUES=Object.freeze([0,1,2,3,5,8]);
  // InitDados na app Android prepara cinco elementos animados.
  // Os valores das faces 1-6 e o prémio da aposta × face são uma
  // regra de demonstração; não são a fórmula compilada do APK.
  const DICE_CHOICES=5;
  // Roleta de entretenimento, não reproduz distribuições históricas.
  // O atlas original confirma roleta numerada de 1 a 30. Os prémios
  // abaixo continuam experimentais: ainda não foi validado o algoritmo do APK.
  const WHEEL_SECTORS = Object.freeze(Array.from({length:30},(_,i)=>{
    const jackpotMap={6:0,7:1,22:2,29:3};
    const jackpotIndex=jackpotMap[i];
    return Object.freeze(jackpotIndex!==undefined ?
      {number:i+1,label:"ACUMULADO "+(jackpotIndex+1),jackpotIndex} :
      {number:i+1,label:String(i+1),multiplier:2+(i%7)});
  }));
  const SYMBOLS = Object.freeze([
    { id: "pumpkin", weight: 19, icon: "🎃", name: "Abóbora Halloween" },
    { id: "witch_green", weight: 9, icon: "🧙", name: "Bruxa verde" },
    { id: "ghost", weight: 17, icon: "👻", name: "Fantasma" },
    { id: "bat", weight: 17, icon: "🦇", name: "Bruxa na lua" },
    { id: "cat", weight: 14, icon: "🧛", name: "Vampiro" },
    { id: "skull", weight: 12, icon: "💀", name: "Monstro" },
    { id: "witch", weight: 10, icon: "🧙", name: "Bruxa WITCH" },
    { id: "devil", weight: 8, icon: "👹", name: "Diabinho" },
    { id: "wild", weight: 7, icon: "🧿", name: "Magic" },
    { id: "scatter", weight: 4, icon: "🔮", name: "Caldeirão" }
  ]);
  // 25 trajetos da esquerda para a direita + 5 trajetos na vertical,
  // um por tambor. Estes percursos são uma reconstrução do protótipo.
  const HORIZONTAL_LINES = Object.freeze([
    [1,1,1,1,1], [0,0,0,0,0], [2,2,2,2,2], [0,1,2,1,0], [2,1,0,1,2],
    [0,0,1,0,0], [2,2,1,2,2], [1,0,0,0,1], [1,2,2,2,1], [0,1,1,1,0],
    [2,1,1,1,2], [1,0,1,0,1], [1,2,1,2,1], [0,1,0,1,0], [2,1,2,1,2],
    [1,1,0,1,1], [1,1,2,1,1], [0,2,0,2,0], [2,0,2,0,2], [0,2,2,2,0],
    [2,0,0,0,2], [1,0,2,0,1], [1,2,0,2,1], [0,0,2,0,0], [2,2,0,2,2]
  ].map(a => Object.freeze(a)));
  const VERTICAL_LINES = Object.freeze(Array.from({length:REELS},(_,col)=>
    Object.freeze(Array.from({length:ROWS},(_,row)=>Object.freeze([col,row])))));
  // As entradas 0–24 são trajetos horizontais (um índice de linha por tambor).
  // As entradas 25–29 são trajetos verticais (três coordenadas [tambor, linha]).
  const LINES = Object.freeze([...HORIZONTAL_LINES,...VERTICAL_LINES]);
  // Pagamentos experimentais para 3, 4 e 5 símbolos. Não reproduzem a matemática proprietária.
  const PAY = Object.freeze({
    pumpkin: [0,0,0,2,6,16], ghost: [0,0,0,3,8,22],
    bat: [0,0,0,3,9,26], cat: [0,0,0,4,12,35],
    skull: [0,0,0,5,15,45], witch: [0,0,0,6,18,60],
    witch_green:[0,0,0,3,10,30], devil:[0,0,0,5,15,42],
    wild: [0,0,0,8,24,80]
  });
  // Créditos guardados em cêntimos inteiros, evitando frações invisíveis de cêntimo.
  const round = n => Math.round(n + Number.EPSILON);
  const validRng = rng => {
    const n = rng();
    if (!Number.isFinite(n)) throw new Error("RNG inválido");
    return Math.min(1 - Number.EPSILON, Math.max(0, n));
  };
  function pick(rng) {
    const total = SYMBOLS.reduce((a,s) => a + s.weight, 0);
    let draw = validRng(rng) * total;
    for (const s of SYMBOLS) {
      draw -= s.weight;
      if (draw < 0) return s.id;
    }
    return SYMBOLS[SYMBOLS.length - 1].id;
  }
  function createState() {
    return { balance: 1000, bet: 1, lines: 1, jackpots: [350,750,1500,3000], jackpotRemainders:[0,0,0,0], freeSpins: 0, pumpkins: 0, spins: 0, lastWin: 0, lastGrid: null, pendingPot: null, pendingWheel: null, pendingStrawberry: null, pendingDice: null };
  }
  function sanitize(s) {
    const d = createState();
    if (!s || typeof s !== "object") return d;
    const tier=tierFor(s.bet,s.lines);
    return {
      balance: Number.isFinite(s.balance) ? Math.max(0, Math.min(MAX_BALANCE, round(s.balance))) : d.balance,
      bet:tier.bet,
      lines:tier.lines,
      jackpots: Array.isArray(s.jackpots) ?
        d.jackpots.map((seed,i)=>{
          const v=s.jackpots[i];
          return Number.isFinite(v)?Math.max(0,Math.min(MAX_BALANCE,round(v))):seed;
        }) : [...d.jackpots],
      jackpotRemainders: Array.isArray(s.jackpotRemainders) && s.jackpotRemainders.length===4 ?
        s.jackpotRemainders.map(v=>Number.isFinite(v)?Math.max(0,Math.min(.999999, v)):0)
        : [...d.jackpotRemainders],
      freeSpins: Number.isInteger(s.freeSpins) ? Math.max(0, Math.min(1000,s.freeSpins)) : 0,
      pumpkins: Number.isInteger(s.pumpkins) ? Math.max(0, Math.min(4,s.pumpkins)) : 0,
      spins: Number.isInteger(s.spins) ? Math.max(0,s.spins) : 0,
      lastWin: Number.isFinite(s.lastWin) ? Math.max(0,Math.min(MAX_BALANCE,round(s.lastWin))) : 0,
      lastGrid: Array.isArray(s.lastGrid) && s.lastGrid.length===REELS &&
        s.lastGrid.every(col=>Array.isArray(col) && col.length===ROWS &&
          col.every(symbol=>SYMBOLS.some(def=>def.id===symbol))) ?
        s.lastGrid.map(col=>[...col]) : null,
      pendingPot: s.pendingPot && Array.isArray(s.pendingPot.choices) && s.pendingPot.choices.length === 3 &&
        s.pendingPot.choices.every(n => Number.isFinite(n) && n >= 0 && n <= 100000) ?
          { choices: s.pendingPot.choices.map(round) } : null,
      pendingDice: s.pendingDice && Array.isArray(s.pendingDice.faces) &&
        s.pendingDice.faces.length===DICE_CHOICES &&
        s.pendingDice.faces.every(v=>Number.isInteger(v) && v>=1 && v<=6) ?
        {faces:[...s.pendingDice.faces]} : null,
      pendingStrawberry: s.pendingStrawberry && Array.isArray(s.pendingStrawberry.choices) &&
        s.pendingStrawberry.choices.length===STRAWBERRY_CHOICES &&
        s.pendingStrawberry.choices.every(n=>Number.isFinite(n)&&n>=0&&n<=100000) ?
        {choices:s.pendingStrawberry.choices.map(round)} : null,
      pendingWheel: s.pendingWheel && Number.isInteger(s.pendingWheel.sectorIndex) &&
        s.pendingWheel.sectorIndex>=0 && s.pendingWheel.sectorIndex<WHEEL_SECTORS.length &&
        Number.isFinite(s.pendingWheel.payout) && s.pendingWheel.payout>=0 &&
        s.pendingWheel.payout<=MAX_BALANCE ?
        {sectorIndex:s.pendingWheel.sectorIndex,payout:round(s.pendingWheel.payout)} : null
    };
  }
  function setBet(input, bet) {
    const state=sanitize(input);
    const tier=BET_LEVELS.find(t=>t.bet===bet);
    if(tier && !state.freeSpins && !state.pendingPot && !state.pendingDice &&
       !state.pendingStrawberry && !state.pendingWheel){
      state.bet=tier.bet;state.lines=tier.lines;
    }
    return state;
  }
  function setLines(input, lines) {
    const state=sanitize(input);
    const tier=BET_LEVELS.find(t=>t.lines===lines);
    if(tier && !state.freeSpins && !state.pendingPot && !state.pendingDice &&
       !state.pendingStrawberry && !state.pendingWheel){
      state.bet=tier.bet;state.lines=tier.lines;
    }
    return state;
  }
  function evaluateGrid(grid, bet, inFreeSpins, activeLines = 30) {
    if (!Array.isArray(grid) || grid.length !== REELS || grid.some(c => !Array.isArray(c) || c.length !== ROWS ||
        c.some(s => !SYMBOLS.some(def => def.id === s)))) throw new Error("Grelha inválida");
    if (!BET_LEVELS.some(t=>t.lines===activeLines))
      throw new Error("Número de linhas inválido");
    const hits = [];
    let total = 0;
    for (let lineNo = 0; lineNo < activeLines; lineNo++) {
      const vertical=lineNo>=HORIZONTAL_LINES.length;
      const route=LINES[lineNo];
      const cells=vertical
        ? route
        : route.map((row,col)=>[col,row]);
      const entries=cells.map(([col,row])=>grid[col][row]);
      // Em linhas horizontais são necessários 3+ símbolos consecutivos
      // desde o tambor esquerdo. Nas cinco verticais são os três símbolos
      // do mesmo tambor (de cima para baixo). MAGIC substitui todos os
      // símbolos comuns, mas nunca o caldeirão (Scatter).
      if(entries[0]==="scatter")continue;
      let best=null;
      for(const target of Object.keys(PAY)){
        let count=0;
        for(const value of entries){
          if(value===target || (value==="wild"&&target!=="scatter"))count++;
          else break;
        }
        if(count<3)continue;
        const amount=round(bet*PAY[target][count]/activeLines*
          (inFreeSpins?2:1));
        if(!best||amount>best.amount){
          best={line:lineNo+1,count,symbol:target,amount,
            direction:vertical?"vertical":"horizontal",
            cells:cells.slice(0,count).map(cell=>[...cell])};
        }
      }
      if (best && best.amount > 0) {
        total = round(total + best.amount);
        hits.push(best);
      }
    }
    let scatter = 0, pumpkinCount = 0;
    for (const column of grid) for (const symbol of column) {
      if (symbol === "scatter") scatter++;
      if (symbol === "pumpkin") pumpkinCount++;
    }
    const scatterWin = scatter >= 3 ? round(bet * (scatter >= 5 ? 15 : scatter === 4 ? 5 : 2)) : 0;
    return { total: round(total + scatterWin), hits, scatter, scatterWin, pumpkinCount };
  }
  function spin(input, rng = Math.random, previousGrid = null, heldColumns = null) {
    const state = sanitize(input);
    const free = state.freeSpins > 0;
    if (state.pendingPot || state.pendingWheel || state.pendingStrawberry || state.pendingDice)
      return { ok: false, reason: "bonus-pendente", state };
    if (!free && state.balance < state.bet) return { ok: false, reason: "saldo", state };
    const holds = !free && Array.isArray(previousGrid) && previousGrid.length === REELS &&
      previousGrid.every(col=>Array.isArray(col) && col.length === ROWS &&
        col.every(sym=>SYMBOLS.some(s=>s.id===sym))) &&
      (Array.isArray(heldColumns)||heldColumns instanceof Set)
        ? [...new Set(heldColumns)].filter(c=>Number.isInteger(c) && c>=0 && c<REELS).slice(0,REELS-1) : [];
    const grid = Array.from({length: REELS}, (_,col) =>
      holds.includes(col) ? [...previousGrid[col]] : Array.from({length: ROWS}, () => pick(rng)));
    const result = evaluateGrid(grid, state.bet, free, state.lines);
    if (free) state.freeSpins--;
    else {
      state.balance = round(state.balance - state.bet);
      // Conservar as frações de cêntimo dos progressivos, só tornando
      // visíveis cêntimos inteiros. Assim os progressivos também avançam
      // quando a aposta é 1 cêntimo, após vários giros.
      state.jackpots=state.jackpots.map((v,i)=>{
        const increase=state.jackpotRemainders[i]+state.bet*(.01+.007*i);
        const cents=Math.floor(increase+1e-9);
        state.jackpotRemainders[i]=Math.round((increase-cents)*1e6)/1e6;
        return Math.min(MAX_BALANCE,v+cents);
      });
    }
    const earnedSpins = (result.scatter >= 3 ? 10 : 0);
    // Mecânica demonstrativa (NÃO é a fórmula da Halloween original):
    // Recolher uma abóbora por rodada paga com quatro ou mais abóboras
    // visíveis. Nas Free Spins não se recolhem abóboras, para não criar
    // cadeias de bónus sem fim.
    const collectedPumpkin = !free && result.pumpkinCount >= 4 ? 1 : 0;
    const pumpkinTotal = state.pumpkins + collectedPumpkin;
    const pumpkinBonuses = Math.floor(pumpkinTotal / PUMPKINS_FOR_BONUS);
    state.pumpkins = pumpkinTotal % PUMPKINS_FOR_BONUS;
    result.collectedPumpkin = collectedPumpkin;
    state.freeSpins = Math.min(1000, state.freeSpins + earnedSpins + pumpkinBonuses * 5);
    state.balance = Math.min(MAX_BALANCE, round(state.balance + result.total));
    state.spins++;
    state.lastWin = result.total;
    state.lastGrid = grid.map(col=>[...col]);
    // Três caldeirões desbloqueiam uma escolha de 3 potes. As escolhas
    // são determinadas no giro e persistidas antes de abrir o painel.
    if (result.scatter >= 3) {
      // No APK existem rotinas distintas para caldeirões e morangos.
      // A seleção de qual bónus surge permanece experimental.
      const bonusPick=validRng(rng);
      if(bonusPick<.15){
        state.pendingDice={
          faces:Array.from({length:DICE_CHOICES},()=>1+Math.floor(validRng(rng)*6))
        };
      }else if(bonusPick<.40){
        state.pendingStrawberry={
          choices:Array.from({length:STRAWBERRY_CHOICES},()=>
            round(state.bet*STRAWBERRY_VALUES[Math.floor(validRng(rng)*STRAWBERRY_VALUES.length)]))
        };
      }else{
        state.pendingPot={
          choices:Array.from({length:3},()=>
            round(state.bet*POT_VALUES[Math.floor(validRng(rng)*POT_VALUES.length)]))
        };
      }
    }
    // A roleta abre só com CINCO abóboras literais alinhadas numa
    // linha de pagamento ativa. Não basta encher o contador acumulado:
    // isso dava demasiados prémios grandes na matemática anterior.
    // O gatilho da máquina comercial continua por confirmar no APK.
    // Só as linhas da esquerda para a direita podem ativar a
    // roleta por cinco abóboras; uma vertical tem apenas três peças.
    const wheelEligible=HORIZONTAL_LINES.slice(0,Math.min(state.lines,25))
      .some(route=>route.every((row,col)=>grid[col][row]==="pumpkin"));
    if (wheelEligible) {
      const sectorIndex=Math.floor(validRng(rng)*WHEEL_SECTORS.length);
      const sector=WHEEL_SECTORS[sectorIndex];
      state.pendingWheel={sectorIndex,payout:sector.jackpotIndex===undefined ?
        round(state.bet*sector.multiplier) : state.jackpots[sector.jackpotIndex]};
    }
    return { ok: true, state, grid, result, free, earnedSpins, pumpkinBonuses,
      heldReels: holds, potTriggered: Boolean(state.pendingPot),
      strawberryTriggered:Boolean(state.pendingStrawberry),
      diceTriggered:Boolean(state.pendingDice),
      wheelTriggered:Boolean(state.pendingWheel), wheelEligible };
  }
  function choosePot(input, index) {
    const state = sanitize(input);
    if (!state.pendingPot) return { ok: false, reason: "sem-bonus", state };
    if (!Number.isInteger(index) || index < 0 || index > 2)
      return { ok: false, reason: "selecao-invalida", state };
    const awarded = state.pendingPot.choices[index];
    state.balance = Math.min(MAX_BALANCE, round(state.balance + awarded));
    state.lastWin = round(state.lastWin + awarded);
    const reveal = [...state.pendingPot.choices];
    state.pendingPot = null;
    return { ok: true, state, awarded, reveal };

  }
  function chooseStrawberry(input,index){
    const state=sanitize(input);
    if(!state.pendingStrawberry)return {ok:false,reason:"sem-morangos",state};
    if(!Number.isInteger(index)||index<0||index>=STRAWBERRY_CHOICES)
      return {ok:false,reason:"selecao-invalida",state};
    const reveal=[...state.pendingStrawberry.choices],awarded=reveal[index];
    state.balance=Math.min(MAX_BALANCE,round(state.balance+awarded));
    state.lastWin=round(state.lastWin+awarded);
    state.pendingStrawberry=null;
    return {ok:true,state,awarded,reveal};
  }
  function chooseDice(input,index){
    const state=sanitize(input);
    if(!state.pendingDice)return {ok:false,reason:"sem-dados",state};
    if(!Number.isInteger(index) || index<0 || index>=DICE_CHOICES)
      return {ok:false,reason:"selecao-invalida",state};
    const reveal=[...state.pendingDice.faces];
    const face=reveal[index],awarded=round(state.bet*face);
    state.balance=Math.min(MAX_BALANCE,round(state.balance+awarded));
    state.lastWin=round(state.lastWin+awarded);
    state.pendingDice=null;
    return {ok:true,state,awarded,face,reveal};
  }
  function resolveWheel(input) {
    const state=sanitize(input);
    if(!state.pendingWheel)return {ok:false,reason:"sem-roleta",state};
    const {sectorIndex,payout}=state.pendingWheel;
    const sector=WHEEL_SECTORS[sectorIndex];
    state.balance=Math.min(MAX_BALANCE,round(state.balance+payout));
    state.lastWin=round(state.lastWin+payout);
    if(sector.jackpotIndex!==undefined)
      state.jackpots[sector.jackpotIndex]=[350,750,1500,3000][sector.jackpotIndex];
      state.jackpotRemainders[sector.jackpotIndex]=0;
    state.pendingWheel=null;
    return {ok:true,state,awarded:payout,sectorIndex,label:sector.label};
  }
  const api = Object.freeze({ SYMBOLS, LINES, HORIZONTAL_LINES, VERTICAL_LINES,
    PAY, BET_LEVELS, PUMPKINS_FOR_BONUS,
    WHEEL_SECTORS, STRAWBERRY_CHOICES, DICE_CHOICES, createState, sanitize, setBet, setLines, pick, evaluateGrid,
    spin, choosePot, chooseStrawberry, chooseDice, resolveWheel });
  if (typeof module !== "undefined" && module.exports) module.exports = api;
  root.HalloweenEngine = api;
})(typeof globalThis !== "undefined" ? globalThis : this);
