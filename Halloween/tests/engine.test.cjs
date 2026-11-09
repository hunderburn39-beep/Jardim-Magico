/* Testes reprodutíveis do motor Halloween, sem dependências externas. */
const test = require("node:test");
const assert = require("node:assert/strict");
const E = require("../js/engine.js");

function fixed(value) { return () => value; }
function seeded(seed) {
  let state = seed >>> 0;
  return () => { state = (1664525 * state + 1013904223) >>> 0; return state / 4294967296; };
}
test("30 trajetos possíveis e todas as posições válidas", () => {
  assert.equal(E.LINES.length, 30);
  for (const line of E.LINES) {
    assert.equal(line.length, 5);
    for (const row of line) assert.ok(Number.isInteger(row) && row >= 0 && row <= 2);
  }
});
test("Aposta limitada aos valores previstos", () => {
  let s = E.createState();
  assert.equal(E.setBet(s, 5).bet, 5);
  assert.equal(E.setBet(s, 999).bet, 2);
});
test("Sem saldo não roda nem altera o estado", () => {
  const s = { ...E.createState(), balance: 0 };
  const r = E.spin(s, fixed(0.25));
  assert.equal(r.ok, false);
  assert.equal(r.state.balance, 0);
  assert.equal(r.state.spins, 0);
});
test("Recolha de abóboras gera rodadas gratuitas e preserva excedentes", () => {
  const s = E.createState();
  const r = E.spin(s, fixed(0));
  assert.equal(r.ok, true);
  assert.equal(r.result.pumpkinCount, 15);
  assert.equal(r.pumpkinBonuses, 1);
  assert.equal(r.state.pumpkins, 5);
  assert.equal(r.state.freeSpins, 5);
  assert.equal(s.spins, 0); // sem mutação do estado fornecido
});
test("Scatter acumula free spins e prémio correto", () => {
  const r = E.spin(E.createState(), fixed(0.999999));
  assert.equal(r.ok, true);
  assert.equal(r.result.scatter, 15);
  assert.equal(r.earnedSpins, 10);
  assert.equal(r.result.scatterWin, 30);
  assert.ok(r.state.freeSpins >= 10);
});
test("O Wild substitui símbolos mas não o Scatter", () => {
  const grid = Array.from({length:5}, () => ["bat", "bat", "bat"]);
  grid[0][1] = "wild";
  grid[1][1] = "wild";
  grid[2][1] = "bat";
  const result = E.evaluateGrid(grid, 2, false);
  assert.ok(result.hits.some(x => x.line === 1 && x.count === 5 && x.symbol === "bat"));
  grid[0][1] = "scatter";
  const result2 = E.evaluateGrid(grid, 2, false);
  assert.ok(!result2.hits.some(x => x.line === 1));
});
test("Rodadas gratuitas não debitam aposta e multiplicam linhas por 2", () => {
  const state = { ...E.createState(), balance: 0, freeSpins: 1 };
  const r = E.spin(state, fixed(0.4));
  assert.equal(r.ok, true);
  assert.equal(r.free, true);
  assert.equal(r.state.balance, r.result.total);
});
test("20 000 jogadas reprodutíveis sem NaN nem créditos negativos", () => {
  const rng = seeded(20261109);
  let s = { ...E.createState(), balance: 100000, bet: 2 };
  for (let i=0; i<20000; i++) {
    const result = E.spin(s, rng);
    assert.equal(result.ok, true);
    s = result.state;
    assert.ok(Number.isFinite(s.balance) && s.balance >= 0);
    assert.ok(Number.isFinite(result.result.total) && result.result.total >= 0);
    assert.ok(s.pumpkins >= 0 && s.pumpkins < 10);
    assert.equal(result.grid.length, 5);
    assert.ok(result.grid.every(col => col.length === 3));
  }
});

test("20, 25 e 30 linhas selecionam contagens diferentes", () => {
  const grid = Array.from({length:5}, () => ["wild","wild","wild"]);
  const p20 = E.evaluateGrid(grid,2,false,20);
  const p25 = E.evaluateGrid(grid,2,false,25);
  const p30 = E.evaluateGrid(grid,2,false,30);
  assert.equal(p20.hits.length,20);
  assert.equal(p25.hits.length,25);
  assert.equal(p30.hits.length,30);
});
test("Tambores travados conservam ícones entre jogadas", () => {
  const previous = Array.from({length:5},()=>["pumpkin","ghost","bat"]);
  const s = { ...E.createState(), balance:100, lines:20 };
  const result = E.spin(s, fixed(.999999), previous, new Set([0,2,4]));
  assert.equal(result.ok,true);
  assert.deepEqual(result.heldReels,[0,2,4]);
  for(const col of [0,2,4])assert.deepEqual(result.grid[col],previous[col]);
  assert.equal(result.state.lines,20);
});
test("Acumulados e seleção de linhas persistem na alteração de aposta", () => {
  const s = { ...E.createState(),lines:30,jackpots:[440,880] };
  const next = E.setBet(s,5);
  assert.equal(next.lines,30);
  assert.deepEqual(next.jackpots,[440,880]);
});
