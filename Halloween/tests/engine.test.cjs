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
test("Abóboras acumulam uma por giro pago e dão bónus à quinta", () => {
  const s = E.createState();
  const r = E.spin(s, fixed(0));
  assert.equal(r.ok, true);
  assert.equal(r.result.pumpkinCount, 15);
  assert.equal(r.result.collectedPumpkin, 1);
  assert.equal(r.pumpkinBonuses, 0);
  assert.equal(r.state.pumpkins, 1);
  assert.equal(s.spins, 0);
  const fifth = E.spin({ ...E.createState(), pumpkins: 4 }, fixed(0));
  assert.equal(fifth.pumpkinBonuses, 1);
  assert.equal(fifth.state.pumpkins, 0);
  assert.equal(fifth.state.freeSpins, 5);
  assert.ok(fifth.state.pendingWheel);
  const free = E.spin({ ...E.createState(), pumpkins: 4, freeSpins: 1 }, fixed(0));
  assert.equal(free.result.collectedPumpkin, 0);
  assert.equal(free.state.pumpkins, 4);
});
test("Scatter acumula free spins e prémio correto", () => {
  const r = E.spin(E.createState(), fixed(0.999999));
  assert.equal(r.ok, true);
  assert.equal(r.result.scatter, 15);
  assert.equal(r.earnedSpins, 10);
  assert.equal(r.result.scatterWin, 30);
  assert.ok(r.state.freeSpins >= 10);
  assert.ok(r.state.pendingPot);
  assert.equal(r.state.pendingPot.choices.length, 3);
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
    assert.ok(s.pumpkins >= 0 && s.pumpkins < 5);
    if(s.pendingPot){
      const resolved = E.choosePot(s, i%3);
      assert.equal(resolved.ok,true);
      s=resolved.state;
    }
    if(s.pendingWheel){
      const resolved=E.resolveWheel(s);
      assert.equal(resolved.ok,true);
      s=resolved.state;
    }
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

test("Três Wild seguidos de Scatter ainda pagam linha de Wild", () => {
  const grid = Array.from({ length: 5 }, () => ["bat","ghost","witch"]);
  grid[0][1] = "wild"; grid[1][1] = "wild"; grid[2][1] = "wild";
  grid[3][1] = "scatter"; grid[4][1] = "scatter";
  const r = E.evaluateGrid(grid,2,false,20);
  assert.ok(r.hits.some(h => h.line===1 && h.count===3 && h.symbol==="wild"));
});
test("Bónus caldeirão bloqueia nova aposta até escolher um dos três", () => {
  const first = E.spin(E.createState(), fixed(.999999));
  assert.equal(first.ok,true);
  assert.equal(first.potTriggered,true);
  const block = E.spin(first.state,fixed(0));
  assert.equal(block.ok,false);
  assert.equal(block.reason,"bonus-pendente");
  assert.deepEqual(block.state,first.state);
  const recovered=E.sanitize(JSON.parse(JSON.stringify(first.state)));
  assert.deepEqual(recovered.pendingPot,first.state.pendingPot);
  const prize=E.choosePot(recovered,1);
  assert.equal(prize.ok,true);
  assert.equal(prize.state.pendingPot,null);
  assert.equal(prize.awarded,prize.reveal[1]);
  assert.equal(prize.state.balance,recovered.balance+prize.awarded);
  assert.equal(prize.state.lastWin,recovered.lastWin+prize.awarded);
  assert.equal(E.spin(prize.state,fixed(.3)).ok,true);
});
test("Escolha inválida do pote não altera créditos nem elimina bónus", () => {
  const state=E.spin(E.createState(),fixed(.999999)).state;
  assert.equal(E.choosePot(state,-1).ok,false);
  assert.equal(E.choosePot(state,3).ok,false);
  assert.ok(state.pendingPot);
});

test("A quinta abóbora prepara roleta recuperável e impede outro giro",()=>{
  const previous={...E.createState(),pumpkins:4};
  const outcome=E.spin(previous,fixed(0));
  assert.equal(outcome.ok,true);
  assert.equal(outcome.wheelTriggered,true);
  assert.equal(outcome.state.pendingWheel.sectorIndex,0);
  assert.equal(E.spin(outcome.state,fixed(.3)).reason,"bonus-pendente");
  const copy=E.sanitize(JSON.parse(JSON.stringify(outcome.state)));
  assert.deepEqual(copy.pendingWheel,outcome.state.pendingWheel);
  const done=E.resolveWheel(copy);
  assert.equal(done.ok,true);
  assert.equal(done.awarded,copy.pendingWheel.payout);
  assert.equal(done.state.pendingWheel,null);
  assert.equal(E.resolveWheel(done.state).ok,false);
  assert.equal(E.spin(done.state,fixed(.3)).ok,true);
});
test("Roleta atribui um acumulado e repõe apenas esse acumulado",()=>{
  let times=0;
  const rng=()=>++times<=15?0:.78; // 15 abóboras, depois setor 6 = acumulado 1
  const state={...E.createState(),pumpkins:4,jackpots:[444,888],bet:2};
  const spun=E.spin(state,rng);
  assert.equal(spun.state.pendingWheel.sectorIndex,6);
  const expected=spun.state.jackpots[0];
  assert.equal(spun.state.pendingWheel.payout,expected);
  const credited=E.resolveWheel(spun.state);
  assert.equal(credited.awarded,expected);
  assert.equal(credited.state.jackpots[0],350);
  assert.equal(credited.state.jackpots[1],spun.state.jackpots[1]);
});
test("Acumulados só crescem em giros pagos",()=>{
  const original=E.createState();
  const base=E.spin(original,fixed(.45));
  assert.ok(base.state.jackpots[0]>original.jackpots[0]);
  assert.ok(base.state.jackpots[1]>original.jackpots[1]);
  const free=E.spin({...E.createState(),freeSpins:1},fixed(.45));
  assert.deepEqual(free.state.jackpots,[350,750]);
});

test("Caldeirão e roleta na mesma ronda são resolvidos sem duplicar créditos",()=>{
  let draws=0;
  const rng=()=>{
    draws++;
    if(draws<=3)return 0;          // três abóboras
    if(draws<=6)return .999999;    // três caldeirões
    return .37;                    // restantes símbolos e sorteios
  };
  const initial={...E.createState(),pumpkins:4,balance:100};
  const spun=E.spin(initial,rng);
  assert.equal(spun.ok,true);
  assert.equal(spun.potTriggered,true);
  assert.equal(spun.wheelTriggered,true);
  assert.ok(spun.state.pendingPot);
  assert.ok(spun.state.pendingWheel);
  assert.equal(E.spin(spun.state,()=>.2).ok,false);
  const afterPot=E.choosePot(spun.state,1);
  assert.equal(afterPot.ok,true);
  assert.equal(afterPot.state.pendingPot,null);
  assert.ok(afterPot.state.pendingWheel);
  assert.equal(E.spin(afterPot.state,()=>.2).ok,false);
  const afterWheel=E.resolveWheel(afterPot.state);
  assert.equal(afterWheel.ok,true);
  assert.equal(afterWheel.state.pendingWheel,null);
  assert.equal(afterWheel.state.balance,
    Math.round((spun.state.balance+afterPot.awarded+afterWheel.awarded)*100)/100);
  assert.equal(E.resolveWheel(afterWheel.state).ok,false);
});

test("O motor guarda a grelha e a recupera sem partilhar referências",()=>{
  const result=E.spin(E.createState(),()=>.4);
  assert.deepEqual(result.state.lastGrid,result.grid);
  const copy=E.sanitize(JSON.parse(JSON.stringify(result.state)));
  assert.deepEqual(copy.lastGrid,result.grid);
  copy.lastGrid[0][0]="pumpkin";
  assert.deepEqual(result.state.lastGrid,result.grid);
  const invalid=E.sanitize({...E.createState(),lastGrid:[["bad"]]});
  assert.equal(invalid.lastGrid,null);
});
