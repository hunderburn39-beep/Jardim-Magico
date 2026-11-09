/* Testes reprodutíveis do motor Halloween, sem dependências externas. */
const test = require("node:test");
const assert = require("node:assert/strict");
const E = require("../js/engine.js");

function fixed(value) { return () => value; }
function seeded(seed) {
  let state = seed >>> 0;
  return () => { state = (1664525 * state + 1013904223) >>> 0; return state / 4294967296; };
}
test("25 linhas da esquerda para a direita e cinco verticais, uma por tambor",()=>{
  assert.equal(E.HORIZONTAL_LINES.length,25);
  assert.equal(E.VERTICAL_LINES.length,5);
  assert.equal(E.LINES.length,30);
  for(const line of E.HORIZONTAL_LINES){
    assert.equal(line.length,5);
    for(const row of line)assert.ok(Number.isInteger(row)&&row>=0&&row<=2);
  }
  E.VERTICAL_LINES.forEach((line,col)=>{
    assert.deepEqual(line,[[col,0],[col,1],[col,2]]);
    assert.deepEqual(E.LINES[25+col],line);
  });
});
test("Aposta limitada aos valores previstos", () => {
  let s = E.createState();
  assert.equal(E.setBet(s, 5).bet, 5);
  assert.equal(E.setBet(s, 999).bet, 1);
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
  assert.equal(r.result.scatterWin, 15);
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
    if(s.pendingDice){
      const resolved=E.chooseDice(s,i%5);
      assert.equal(resolved.ok,true);
      s=resolved.state;
    }
    if(s.pendingStrawberry){
      const resolved=E.chooseStrawberry(s,i%12);
      assert.equal(resolved.ok,true);
      s=resolved.state;
    }
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

test("1, 5, 15, 25 e 30 linhas selecionam contagens diferentes", () => {
  const grid=Array.from({length:5},()=>["wild","wild","wild"]);
  for(const tier of E.BET_LEVELS){
    const result=E.evaluateGrid(grid,tier.bet,false,tier.lines);
    assert.equal(result.hits.length,tier.lines);
  }
});
test("Tambores travados conservam ícones entre jogadas", () => {
  const previous = Array.from({length:5},()=>["pumpkin","ghost","bat"]);
  const s = { ...E.createState(), balance:100, lines:25, bet:15 };
  const result = E.spin(s, fixed(.999999), previous, new Set([0,2,4]));
  assert.equal(result.ok,true);
  assert.deepEqual(result.heldReels,[0,2,4]);
  for(const col of [0,2,4])assert.deepEqual(result.grid[col],previous[col]);
  assert.equal(result.state.lines,25);
});
test("Acumulados e seleção de linhas persistem na alteração de aposta", () => {
  const s = { ...E.createState(),lines:30,jackpots:[440,880] };
  const next = E.setBet(s,5);
  assert.equal(next.lines,5);
  assert.equal(next.bet,5);
  assert.deepEqual(next.jackpots,[440,880,1500,3000]);
});

test("Três Wild seguidos de Scatter ainda pagam linha de Wild", () => {
  const grid = Array.from({ length: 5 }, () => ["bat","ghost","witch"]);
  grid[0][1] = "wild"; grid[1][1] = "wild"; grid[2][1] = "wild";
  grid[3][1] = "scatter"; grid[4][1] = "scatter";
  const r = E.evaluateGrid(grid,1,false,1);
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

test("Cinco abóboras alinhadas preparam roleta recuperável e impedem outro giro",()=>{
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
  const rng=()=>++times<=15?0:.2167; // 15 abóboras, depois setor 6 = acumulado 1
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
test("Acumulados guardam frações de cêntimo e só crescem em giros pagos",()=>{
  const original=E.createState();
  const first=E.spin(original,fixed(.45));
  assert.equal(first.ok,true);
  assert.ok(first.state.jackpotRemainders[0]>0);
  let paid=first.state;
  for(let i=0;i<100;i++){
    const result=E.spin(paid,fixed(.45));
    assert.equal(result.ok,true);
    paid=result.state;
  }
  assert.ok(paid.jackpots[0]>original.jackpots[0]);
  assert.ok(paid.jackpots[1]>original.jackpots[1]);
  const free=E.spin({...E.createState(),freeSpins:1},fixed(.45));
  assert.deepEqual(free.state.jackpots,[350,750,1500,3000]);
  assert.deepEqual(free.state.jackpotRemainders,[0,0,0,0]);
});

test("Caldeirão e roleta na mesma ronda são resolvidos sem duplicar créditos",()=>{
  let draws=0;
  const rng=()=>{
    draws++;
    if(draws<=15){
      const col=Math.floor((draws-1)/3),row=(draws-1)%3;
      if(row===1)return 0;         // cinco abóboras na linha central
      if(row===0&&col<3)return .999999; // três caldeirões na fila de cima
    }
    return .99;
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

test("O atlas original inspira 10 símbolos e a roleta tem 30 posições",()=>{
  assert.equal(E.SYMBOLS.length,10);
  assert.equal(new Set(E.SYMBOLS.map(s=>s.id)).size,10);
  assert.equal(E.WHEEL_SECTORS.length,30);
  assert.deepEqual(E.WHEEL_SECTORS.map(s=>s.number),Array.from({length:30},(_,i)=>i+1));
  assert.deepEqual(E.WHEEL_SECTORS.filter(s=>s.jackpotIndex!==undefined)
    .map(s=>s.jackpotIndex),[0,1,2,3]);
});
test("Versões anteriores com dois acumulados são recuperadas para quatro",()=>{
  const old={...E.createState(),jackpots:[450,900]};
  const restored=E.sanitize(old);
  assert.deepEqual(restored.jackpots,[450,900,1500,3000]);
});
test("Uma roleta com 30 posições atribui o quarto acumulado uma só vez",()=>{
  const original={...E.createState(),pumpkins:4,jackpots:[350,750,1500,4100]};
  let n=0;
  const spun=E.spin(original,()=>++n<=15?0:.999999);
  assert.equal(spun.state.pendingWheel.sectorIndex,29);
  assert.equal(spun.state.pendingWheel.payout,spun.state.jackpots[3]);
  const prize=E.resolveWheel(spun.state);
  assert.equal(prize.ok,true);
  assert.equal(prize.state.jackpots[3],3000);
  assert.equal(E.resolveWheel(prize.state).ok,false);
});

test("A roleta exige cinco abóboras na mesma linha, não apenas cinco dispersas",()=>{
  let n=0;
  const picked=E.spin(E.createState(),()=>{
    n++;
    // Cinco abóboras repartidas por duas colunas, nenhuma linha com
    // cinco abóboras consecutivas.
    return ([1,2,4,5,7].includes(n))?0:.42;
  });
  assert.equal(picked.ok,true);
  assert.equal(picked.result.pumpkinCount,5);
  assert.equal(picked.wheelEligible,false);
  assert.equal(picked.wheelTriggered,false);
  assert.equal(picked.state.pendingWheel,null);
});
test("Regressão económica: 20 mil giros não regressam ao retorno extremo inicial",()=>{
  const rng=seeded(20261109);
  let state={...E.createState(),balance:10000000,bet:2};
  let invested=0,received=0,wheels=0;
  for(let i=0;i<20000;i++){
    const step=E.spin(state,rng);
    assert.equal(step.ok,true);
    state=step.state;
    if(!step.free)invested+=state.bet;
    received+=step.result.total;
    if(state.pendingDice){
      const result=E.chooseDice(state,i%5);
      assert.equal(result.ok,true);
      received+=result.awarded;state=result.state;
    }
    if(state.pendingStrawberry){
      const result=E.chooseStrawberry(state,i%12);
      assert.equal(result.ok,true);
      received+=result.awarded;state=result.state;
    }
    if(state.pendingPot){
      const result=E.choosePot(state,i%3);
      received+=result.awarded;state=result.state;
    }
    if(state.pendingWheel){
      const result=E.resolveWheel(state);
      received+=result.awarded;state=result.state;wheels++;
    }
  }
  const sampleReturn=received/invested;
  assert.ok(sampleReturn>0 && sampleReturn<2,
    "Retorno simulado fora de limites de segurança de regressão: "+sampleReturn);
  assert.ok(wheels<150,"Roleta excessivamente frequente: "+wheels);
});

test("O motor nunca permite travar os cinco tambores",()=>{
  const old=Array.from({length:5},()=>["pumpkin","ghost","bat"]);
  const state={...E.createState(),balance:100};
  const result=E.spin(state,fixed(.5),old,new Set([0,1,2,3,4]));
  assert.equal(result.ok,true);
  assert.equal(result.heldReels.length,4);
  assert.deepEqual(result.heldReels,[0,1,2,3]);
  assert.equal(result.state.spins,1);
});

test("Três caldeirões podem abrir a escolha de 12 morangos",()=>{
  let draws=0;
  const outcome=E.spin(E.createState(),()=>++draws<=15?.999999:.25);
  assert.equal(outcome.ok,true);
  assert.equal(outcome.strawberryTriggered,true);
  assert.equal(outcome.potTriggered,false);
  assert.equal(outcome.state.pendingStrawberry.choices.length,12);
  assert.equal(E.spin(outcome.state,()=>.5).reason,"bonus-pendente");
  const saved=E.sanitize(JSON.parse(JSON.stringify(outcome.state)));
  assert.deepEqual(saved.pendingStrawberry,outcome.state.pendingStrawberry);
  assert.equal(E.chooseStrawberry(saved,-1).ok,false);
  assert.equal(E.chooseStrawberry(saved,12).ok,false);
  const prize=E.chooseStrawberry(saved,7);
  assert.equal(prize.ok,true);
  assert.equal(prize.awarded,prize.reveal[7]);
  assert.equal(prize.state.pendingStrawberry,null);
  assert.equal(E.chooseStrawberry(prize.state,7).ok,false);
  assert.equal(prize.state.balance,outcome.state.balance+prize.awarded);
});
test("Morangos e roleta pendentes resolvem-se uma vez cada",()=>{
  let n=0;
  const pick=()=>{n++;if(n<=15){const col=Math.floor((n-1)/3),row=(n-1)%3;
    if(row===1)return 0;
    if(row===0&&col<4)return .999999; // quatro caldeirões dispersos
  }
  if(n===16)return .25; // abrir 12 morangos, não os caldeirões
  return .25;};
  const state={...E.createState(),balance:10000,pumpkins:4};
  const first=E.spin(state,pick);
  assert.equal(first.ok,true);
  assert.equal(first.strawberryTriggered,true);
  assert.equal(first.wheelTriggered,true);
  const won=E.chooseStrawberry(first.state,0);
  assert.equal(won.ok,true);
  assert.ok(won.state.pendingWheel);
  assert.equal(E.spin(won.state,()=>.1).reason,"bonus-pendente");
  const wheel=E.resolveWheel(won.state);
  assert.equal(wheel.ok,true);
  assert.equal(wheel.state.pendingWheel,null);
  assert.equal(E.resolveWheel(wheel.state).ok,false);
});

test("O APK indica cinco animações de dados; criam-se cinco escolhas",()=>{
  let n=0;
  const r=E.spin(E.createState(),()=>++n<=15?.999999:.01);
  assert.equal(r.ok,true);
  assert.equal(r.diceTriggered,true);
  assert.equal(r.potTriggered,false);
  assert.equal(r.strawberryTriggered,false);
  assert.equal(r.state.pendingDice.faces.length,5);
  assert.ok(r.state.pendingDice.faces.every(face=>face>=1&&face<=6));
  assert.equal(E.spin(r.state,()=>.5).reason,"bonus-pendente");
});
test("Bónus de dados é recuperável e paga uma única vez",()=>{
  const original={...E.createState(),bet:5,lines:5,balance:120,
    pendingDice:{faces:[1,2,3,4,6]}};
  const restored=E.sanitize(JSON.parse(JSON.stringify(original)));
  assert.deepEqual(restored.pendingDice,original.pendingDice);
  assert.equal(E.chooseDice(restored,-1).ok,false);
  assert.equal(E.chooseDice(restored,5).ok,false);
  assert.equal(E.chooseDice(restored,1.5).ok,false);
  const result=E.chooseDice(restored,4);
  assert.equal(result.ok,true);
  assert.equal(result.face,6);
  assert.equal(result.awarded,30);
  assert.equal(result.state.balance,150);
  assert.equal(result.state.pendingDice,null);
  assert.equal(E.chooseDice(result.state,4).ok,false);
});
test("Dados e roleta pendentes não duplicam a recompensa",()=>{
  const input={...E.createState(),pendingDice:{faces:[2,3,4,5,6]},
    pendingWheel:{sectorIndex:0,payout:10},bet:5,lines:5};
  assert.equal(E.spin(input,()=>.2).reason,"bonus-pendente");
  const dice=E.chooseDice(input,1);
  assert.equal(dice.awarded,15);
  assert.ok(dice.state.pendingWheel);
  assert.equal(E.spin(dice.state,()=>.2).reason,"bonus-pendente");
  const wheel=E.resolveWheel(dice.state);
  assert.equal(wheel.awarded,10);
  assert.equal(wheel.state.pendingWheel,null);
  assert.equal(E.resolveWheel(wheel.state).ok,false);
  assert.equal(wheel.state.balance,input.balance+25);
});
test("Dados corrompidos não criam pagamentos ao restaurar",()=>{
  const input={...E.createState(),pendingDice:{faces:[9,-4,1,2,3]}};
  assert.equal(E.sanitize(input).pendingDice,null);
  const missing={...E.createState(),pendingDice:{faces:[1,2,3]}};
  assert.equal(E.sanitize(missing).pendingDice,null);
});

test("Escalões fixos associam aposta total em cêntimos e número de linhas",()=>{
  const expected=[[1,1],[5,5],[10,15],[15,25],[20,30]];
  assert.deepEqual(E.BET_LEVELS.map(t=>[t.bet,t.lines]),expected);
  for(const [bet,lines] of expected){
    const byBet=E.setBet(E.createState(),bet);
    assert.equal(byBet.bet,bet);
    assert.equal(byBet.lines,lines);
    const byLines=E.setLines(E.createState(),lines);
    assert.equal(byLines.bet,bet);
    assert.equal(byLines.lines,lines);
    assert.equal(E.sanitize(byBet).lines,lines);
  }
});
test("Um giro pago desconta só a aposta total e não aposta vezes linhas",()=>{
  for(const tier of E.BET_LEVELS){
    const s={...E.setBet(E.createState(),tier.bet),balance:10000};
    const r=E.spin(s,fixed(.45));
    assert.equal(r.ok,true);
    assert.equal(r.state.balance,10000-tier.bet+r.result.total);
    assert.equal(r.state.bet,tier.bet);
    assert.equal(r.state.lines,tier.lines);
  }
});
test("Apostas e linhas emparelhadas mantêm os bónus e bloqueiam alteração em Free Spins",()=>{
  for(const tier of E.BET_LEVELS){
    const s={...E.setBet(E.createState(),tier.bet),freeSpins:1};
    assert.equal(E.setBet(s,20).bet,tier.bet);
    assert.equal(E.setLines(s,30).lines,tier.lines);
    const bonus={...E.setBet(E.createState(),tier.bet),
      pendingDice:{faces:[1,2,3,4,6]}};
    assert.equal(E.setBet(bonus,20).bet,tier.bet);
  }
});
test("Sessões antigas convertem 20, 25 e 30 linhas sem pares inválidos",()=>{
  assert.deepEqual([20,25,30].map(lines=>{
    const s=E.sanitize({bet:2,lines});
    return [s.bet,s.lines];
  }),[[15,25],[15,25],[20,30]]);
});

test("Três símbolos iguais na vertical pagam só com 30 linhas ativas",()=>{
  const grid=Array.from({length:5},()=>["ghost","ghost","ghost"]);
  grid[2]=["witch","witch","witch"];
  const r25=E.evaluateGrid(grid,20,false,25);
  const r30=E.evaluateGrid(grid,20,false,30);
  assert.ok(!r25.hits.some(h=>h.direction==="vertical"));
  const hit=r30.hits.find(h=>h.line===28 && h.direction==="vertical");
  assert.ok(hit,"A linha 28 deveria pagar no terceiro tambor");
  assert.equal(hit.count,3);
  assert.equal(hit.symbol,"witch");
  assert.deepEqual(hit.cells,[[2,0],[2,1],[2,2]]);
  assert.ok(hit.amount>0);
});
test("MAGIC substitui na vertical mas não substitui caldeirões",()=>{
  const grid=Array.from({length:5},()=>["ghost","ghost","ghost"]);
  grid[4]=["wild","witch","witch"];
  const withWild=E.evaluateGrid(grid,20,false,30);
  assert.ok(withWild.hits.some(h=>h.line===30&&h.symbol==="witch"&&h.count===3));
  grid[4]=["wild","scatter","scatter"];
  const excluded=E.evaluateGrid(grid,20,false,30);
  assert.ok(!excluded.hits.some(h=>h.line===30));
});
test("Três caldeirões empilhados abrem bónus vertical e Free Spins",()=>{
  let draw=0;
  const r=E.spin(E.setBet(E.createState(),20),()=>{
    const col=Math.floor(draw/3);draw++;
    return col===3?.999999:.45;
  });
  assert.equal(r.ok,true);
  assert.equal(r.result.scatter,3);
  assert.deepEqual(r.result.verticalScatterColumns,[3]);
  assert.equal(r.verticalBonus,true);
  assert.equal(r.bonusEligible,true);
  assert.equal(r.earnedSpins,10);
  assert.equal(r.result.bonusHits.length,1);
  assert.equal(r.result.bonusHits[0].line,29);
  assert.deepEqual(r.result.bonusHits[0].cells,[[3,0],[3,1],[3,2]]);
  assert.equal([r.potTriggered,r.strawberryTriggered,r.diceTriggered].filter(Boolean).length,1);
});
test("Três caldeirões espalhados atribuem Free Spins mas não mini-jogo",()=>{
  let draw=0;
  const r=E.spin(E.setBet(E.createState(),20),()=>{
    draw++;
    return [1,4,7].includes(draw)?.999999:.45;
  });
  assert.equal(r.result.scatter,3);
  assert.deepEqual(r.result.verticalScatterColumns,[]);
  assert.equal(r.verticalBonus,false);
  assert.equal(r.bonusEligible,false);
  assert.equal(r.earnedSpins,10);
  assert.equal(r.potTriggered||r.strawberryTriggered||r.diceTriggered,false);
});
test("Quatro caldeirões espalhados podem ativar mini-jogo secundário",()=>{
  let draw=0;
  const r=E.spin(E.setBet(E.createState(),20),()=>{
    draw++;
    return [1,4,7,10].includes(draw)?.999999:.45;
  });
  assert.equal(r.result.scatter,4);
  assert.equal(r.verticalBonus,false);
  assert.equal(r.bonusEligible,true);
  assert.equal([r.potTriggered,r.strawberryTriggered,r.diceTriggered].filter(Boolean).length,1);
});
