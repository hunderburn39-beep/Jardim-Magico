/* Halloween — motor original para jogo com créditos de entretenimento. */
(function (root) {
  "use strict";
  const ROWS = 3, REELS = 5, MAX_BALANCE = 1e12;
  const PUMPKINS_FOR_BONUS = 5;
  const POT_VALUES = Object.freeze([1,2,3,5,8]);
  const SYMBOLS = Object.freeze([
    { id: "pumpkin", weight: 19, icon: "🎃", name: "Abóbora" },
    { id: "ghost", weight: 17, icon: "👻", name: "Fantasma" },
    { id: "bat", weight: 17, icon: "🦇", name: "Bruxa na vassoura" },
    { id: "cat", weight: 14, icon: "🐈‍⬛", name: "Drácula" },
    { id: "skull", weight: 12, icon: "💀", name: "Monstro" },
    { id: "witch", weight: 10, icon: "🧙", name: "Bruxa" },
    { id: "wild", weight: 7, icon: "🧿", name: "Magic" },
    { id: "scatter", weight: 4, icon: "🔮", name: "Caldeirão" }
  ]);
  // Trajetos originais de demonstração: 20, 25 ou 30 linhas (0 a 2).
  const LINES = Object.freeze([
    [1,1,1,1,1], [0,0,0,0,0], [2,2,2,2,2], [0,1,2,1,0], [2,1,0,1,2],
    [0,0,1,0,0], [2,2,1,2,2], [1,0,0,0,1], [1,2,2,2,1], [0,1,1,1,0],
    [2,1,1,1,2], [1,0,1,0,1], [1,2,1,2,1], [0,1,0,1,0], [2,1,2,1,2],
    [1,1,0,1,1], [1,1,2,1,1], [0,2,0,2,0], [2,0,2,0,2], [0,2,2,2,0],
    [2,0,0,0,2], [1,0,2,0,1], [1,2,0,2,1], [0,0,2,0,0], [2,2,0,2,2],
    [0,1,2,2,1], [2,1,0,0,1], [1,2,2,0,1], [1,0,0,2,1], [0,2,1,2,0]
  ].map(a => Object.freeze(a)));
  // Pagamentos experimentais para 3, 4 e 5 símbolos. Não reproduzem a matemática proprietária.
  const PAY = Object.freeze({
    pumpkin: [0,0,0,2,6,16], ghost: [0,0,0,3,8,22],
    bat: [0,0,0,3,9,26], cat: [0,0,0,4,12,35],
    skull: [0,0,0,5,15,45], witch: [0,0,0,6,18,60],
    wild: [0,0,0,8,24,80]
  });
  const round = n => Math.round((n + Number.EPSILON) * 100) / 100;
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
    return { balance: 1000, bet: 2, lines: 20, jackpots: [350,750], freeSpins: 0, pumpkins: 0, spins: 0, lastWin: 0, pendingPot: null };
  }
  function sanitize(s) {
    const d = createState();
    if (!s || typeof s !== "object") return d;
    return {
      balance: Number.isFinite(s.balance) ? Math.max(0, Math.min(MAX_BALANCE, round(s.balance))) : d.balance,
      bet: [1,2,5,10].includes(s.bet) ? s.bet : d.bet,
      lines: [20,25,30].includes(s.lines) ? s.lines : d.lines,
      jackpots: Array.isArray(s.jackpots) && s.jackpots.length === 2 ? s.jackpots.map((v,i)=>
        Number.isFinite(v)?Math.max(0,Math.min(MAX_BALANCE,round(v))):d.jackpots[i]) : [...d.jackpots],
      freeSpins: Number.isInteger(s.freeSpins) ? Math.max(0, Math.min(1000,s.freeSpins)) : 0,
      pumpkins: Number.isInteger(s.pumpkins) ? Math.max(0, Math.min(4,s.pumpkins)) : 0,
      spins: Number.isInteger(s.spins) ? Math.max(0,s.spins) : 0,
      lastWin: Number.isFinite(s.lastWin) ? Math.max(0,Math.min(MAX_BALANCE,round(s.lastWin))) : 0,
      pendingPot: s.pendingPot && Array.isArray(s.pendingPot.choices) && s.pendingPot.choices.length === 3 &&
        s.pendingPot.choices.every(n => Number.isFinite(n) && n >= 0 && n <= 100000) ?
          { choices: s.pendingPot.choices.map(round) } : null
    };
  }
  function setBet(input, bet) {
    const state = sanitize(input);
    if ([1,2,5,10].includes(bet)) state.bet = bet;
    return state;
  }
  function evaluateGrid(grid, bet, inFreeSpins, activeLines = 20) {
    if (!Array.isArray(grid) || grid.length !== REELS || grid.some(c => !Array.isArray(c) || c.length !== ROWS ||
        c.some(s => !SYMBOLS.some(def => def.id === s)))) throw new Error("Grelha inválida");
    if (![20,25,30].includes(activeLines)) throw new Error("Número de linhas inválido");
    const hits = [];
    let total = 0;
    for (let lineNo = 0; lineNo < activeLines; lineNo++) {
      const route = LINES[lineNo];
      const entries = route.map((row,col) => grid[col][row]);
      // O Wild substitui símbolos comuns, não Scatter; entre combinações
      // concorrentes na mesma linha paga-se apenas a de maior valor.
      // Isto também preserva 3 Wild consecutivos seguidos de Scatter.
      if (entries[0] === "scatter") continue;
      let best = null;
      for (const target of Object.keys(PAY)) {
        let count = 0;
        for (const value of entries) {
          if (value === target || (value === "wild" && target !== "scatter")) count++;
          else break;
        }
        if (count < 3) continue;
        const amount = round(bet * PAY[target][count] / activeLines * (inFreeSpins ? 2 : 1));
        if (!best || amount > best.amount) {
          best = { line: lineNo + 1, count, symbol: target, amount,
            cells: route.slice(0,count).map((row,col) => [col,row]) };
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
    if (state.pendingPot) return { ok: false, reason: "bonus-pendente", state };
    if (!free && state.balance < state.bet) return { ok: false, reason: "saldo", state };
    const holds = !free && Array.isArray(previousGrid) && previousGrid.length === REELS &&
      previousGrid.every(col=>Array.isArray(col) && col.length === ROWS &&
        col.every(sym=>SYMBOLS.some(s=>s.id===sym))) &&
      (Array.isArray(heldColumns)||heldColumns instanceof Set)
        ? [...heldColumns].filter(c=>Number.isInteger(c) && c>=0 && c<REELS) : [];
    const grid = Array.from({length: REELS}, (_,col) =>
      holds.includes(col) ? [...previousGrid[col]] : Array.from({length: ROWS}, () => pick(rng)));
    const result = evaluateGrid(grid, state.bet, free, state.lines);
    if (free) state.freeSpins--;
    else state.balance = round(state.balance - state.bet);
    const earnedSpins = (result.scatter >= 3 ? 10 : 0);
    // No modo base, uma rodada com pelo menos três abóboras recolhe
    // apenas uma. Nas Free Spins não se recolhem abóboras adicionais,
    // evitando cadeias de bónus excessivamente longas.
    const collectedPumpkin = !free && result.pumpkinCount >= 3 ? 1 : 0;
    const pumpkinTotal = state.pumpkins + collectedPumpkin;
    const pumpkinBonuses = Math.floor(pumpkinTotal / PUMPKINS_FOR_BONUS);
    state.pumpkins = pumpkinTotal % PUMPKINS_FOR_BONUS;
    result.collectedPumpkin = collectedPumpkin;
    state.freeSpins = Math.min(1000, state.freeSpins + earnedSpins + pumpkinBonuses * 5);
    state.balance = Math.min(MAX_BALANCE, round(state.balance + result.total));
    state.spins++;
    state.lastWin = result.total;
    // Três caldeirões desbloqueiam uma escolha de 3 potes. As escolhas
    // são determinadas no giro e persistidas antes de abrir o painel.
    if (result.scatter >= 3) {
      state.pendingPot = {
        choices: Array.from({ length: 3 }, () =>
          round(state.bet * POT_VALUES[Math.floor(validRng(rng) * POT_VALUES.length)]))
      };
    }
    return { ok: true, state, grid, result, free, earnedSpins, pumpkinBonuses,
      heldReels: holds, potTriggered: Boolean(state.pendingPot) };
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
  const api = Object.freeze({ SYMBOLS, LINES, PAY, PUMPKINS_FOR_BONUS, createState, sanitize, setBet, pick, evaluateGrid, spin, choosePot });
  if (typeof module !== "undefined" && module.exports) module.exports = api;
  root.HalloweenEngine = api;
})(typeof globalThis !== "undefined" ? globalThis : this);
