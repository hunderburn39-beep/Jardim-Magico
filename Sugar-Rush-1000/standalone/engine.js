/* Sugar Rush 1000 — motor local experimental.
   Codigo original para um prototipo com creditos ficticios.
   Nao comunica com servidores nem utiliza resultados predefinidos.
   RTP NAO certificado; a matematica deve ser auditada antes de qualquer distribuicao.
*/
(function (root, factory) {
  const api = factory();
  if (typeof module === "object" && module.exports) module.exports = api;
  root.SugarLocalEngine = api;
})(typeof globalThis !== "undefined" ? globalThis : this, function () {
  "use strict";
  const SIZE = 7;
  const SCATTER = 7;
  const WEIGHTS = [19, 17, 16, 14, 13, 12, 9];
  const PAY = [
    [0.40,0.60,0.80,1,1,2,3,5,10,20,40],
    [0.50,0.60,0.80,1,1.50,2.50,4,6,12,24,50],
    [0.60,0.80,1,1.50,2,3,5,7,16,30,60],
    [0.80,1,1.50,2,2.50,4,6,10,20,40,80],
    [1,1.50,2,2.50,3,6,9,20,40,80,120],
    [1.50,2,2.50,3,4,8,12,25,60,120,200],
    [2,3,3.50,4,5,10,15,30,70,140,300]
  ];
  const FREE_AWARDS = [0,0,0,10,12,15,20,30];
  const DEFAULT_SCATTER_RATE = 0.017;
  const MAX_CASCADES = 45;
  const MAX_SESSION_SPINS = 200;
  const MAX_MULTIPLIER = 1024;
  const MAX_WIN_MULT = 25000;

  function blank() {
    return Array.from({ length: SIZE }, () => Array(SIZE).fill(0));
  }
  function copy(a) { return a.map(row => row.slice()); }
  function rand(rng) {
    const v = Number(rng());
    if (!Number.isFinite(v) || v < 0 || v > 1) throw Error("Gerador aleatorio invalido");
    return Math.min(v, 0.999999999999);
  }
  function pick(rng, scatterRate) {
    if (rand(rng) < scatterRate) return SCATTER;
    let val = rand(rng) * 100;
    for (let i = 0; i < WEIGHTS.length; i++) {
      val -= WEIGHTS[i];
      if (val < 0) return i;
    }
    return 6;
  }
  function makeBoard(rng, rate) {
    return Array.from({ length: SIZE }, () =>
      Array.from({ length: SIZE }, () => pick(rng, rate)));
  }
  function findClusters(board) {
    if (!Array.isArray(board) || board.length !== SIZE ||
        !board.every(row => Array.isArray(row) && row.length === SIZE))
      throw Error("Grelha invalida");
    const seen = blank().map(row => row.map(() => false));
    const groups = [];
    for (let r = 0; r < SIZE; r++) for (let c = 0; c < SIZE; c++) {
      const id = board[r][c];
      if (seen[r][c] || id === SCATTER || id === -1) continue;
      const pending = [[r,c]], cells = [];
      seen[r][c] = true;
      while (pending.length) {
        const [y,x] = pending.pop();
        cells.push([y,x]);
        for (const [dy,dx] of [[1,0],[-1,0],[0,1],[0,-1]]) {
          const ny = y + dy, nx = x + dx;
          if (ny >= 0 && ny < SIZE && nx >= 0 && nx < SIZE &&
              !seen[ny][nx] && board[ny][nx] === id) {
            seen[ny][nx] = true;
            pending.push([ny,nx]);
          }
        }
      }
      if (cells.length >= 5) groups.push({ id, cells });
    }
    return groups;
  }
  function tumble(board, removed, rng, rate) {
    const next = copy(board);
    const gone = new Set(removed.map(([r,c]) => r * SIZE + c));
    for (let c = 0; c < SIZE; c++) {
      const surviving = [];
      for (let r = SIZE - 1; r >= 0; r--)
        if (!gone.has(r * SIZE + c)) surviving.push(board[r][c]);
      let toRow = SIZE - 1;
      for (const id of surviving) next[toRow--][c] = id;
      while (toRow >= 0) next[toRow--][c] = pick(rng, rate);
    }
    return next;
  }
  function countScatters(board) {
    return board.reduce((n,row) => n + row.filter(id => id === SCATTER).length, 0);
  }
  function awardFor(count) {
    return count >= 3 ? FREE_AWARDS[Math.min(count, 7)] : 0;
  }

  // Os montantes sao centimos de creditos inteiros, evitando
  // acumulacao de arredondamentos em valores de ponto flutuante.
  function calculateRound(options) {
    const o = options || {};
    const rng = o.rng || Math.random;
    const betCents = Number(o.betCents);
    if (!Number.isSafeInteger(betCents) || betCents < 1 || betCents > 100000)
      throw Error("Aposta invalida");
    const rate = o.scatterRate === undefined ? DEFAULT_SCATTER_RATE : Number(o.scatterRate);
    if (!Number.isFinite(rate) || rate < 0 || rate > 0.15)
      throw Error("Probabilidade scatter invalida");
    const spots = o.spots ? copy(o.spots) : blank();
    if (spots.length !== SIZE || !spots.every(row => row.length === SIZE))
      throw Error("Multiplicadores invalidos");
    const capCents = Number.isSafeInteger(o.capCents)
      ? Math.max(0, o.capCents) : betCents * MAX_WIN_MULT;
    let board = o.initialBoard ? copy(o.initialBoard) : makeBoard(rng, rate);
    const frames = [{ kind: "reveal", board: copy(board), spots: copy(spots) }];
    let totalWinCents = 0;
    let cascades = 0;

    while (cascades < MAX_CASCADES && totalWinCents < capCents) {
      const groups = findClusters(board);
      if (!groups.length) break;
      cascades++;
      let paidCents = 0;
      const winningCells = [];
      for (const group of groups) {
        const tableMultiplier = PAY[group.id][Math.min(10, group.cells.length - 5)];
        const stacked = group.cells.reduce((sum,[r,c]) =>
          sum + (spots[r][c] >= 2 ? spots[r][c] : 0), 0);
        const multiplier = Math.max(1, stacked);
        const valueCents = Math.round(betCents * tableMultiplier * multiplier);
        const payable = Math.max(0, Math.min(valueCents, capCents - totalWinCents - paidCents));
        paidCents += payable;
        for (const pos of group.cells) winningCells.push(pos);
      }
      totalWinCents += paidCents;
      for (const [r,c] of winningCells) {
        spots[r][c] = spots[r][c] === 0 ? 1
          : spots[r][c] === 1 ? 2 : Math.min(MAX_MULTIPLIER, spots[r][c] * 2);
      }
      frames.push({
        kind: "win", board: copy(board), spots: copy(spots),
        groups, positions: winningCells, amountCents: paidCents,
        cumulativeCents: totalWinCents
      });
      if (totalWinCents >= capCents) break;
      board = tumble(board, winningCells, rng, rate);
      frames.push({ kind: "fall", board: copy(board), spots: copy(spots) });
    }

    const scatters = countScatters(board);
    return {
      frames, board, spots, winCents: totalWinCents, cascades,
      scatters, freeAward: awardFor(scatters),
      capReached: totalWinCents >= capCents,
      truncatedByCascadeLimit: cascades >= MAX_CASCADES
    };
  }

  return Object.freeze({
    SIZE, PAY, WEIGHTS, SCATTER, DEFAULT_SCATTER_RATE,
    MAX_WIN_MULT, MAX_SESSION_SPINS, MAX_CASCADES,
    blank, findClusters, countScatters, awardFor, calculateRound
  });
});
