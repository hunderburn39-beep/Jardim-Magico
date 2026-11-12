/* Faraó Eterno — camada cinematográfica V1.2.
 * Som, imagem, suspense e apresentação dos prémios. Nunca altera o motor,
 * a sessão, o saldo, as linhas, os resultados ou a tabela de pagamentos.
 */
(() => {
  'use strict';
  const game = window.__faraoGame;
  const canvas = document.getElementById('canvas');
  if (!game || !canvas || !game.visualV11) return;
  const ctx = canvas.getContext('2d');
  const R = game.visualV11.layout;
  const clamp = (v, a, b) => Math.max(a, Math.min(b, v));
  const ease = v => 1 - Math.pow(1 - clamp(v, 0, 1), 3);
  const reduced = !!(window.matchMedia && matchMedia('(prefers-reduced-motion: reduce)').matches);
  const format = n => (Math.round((n + Number.EPSILON) * 100) / 100)
    .toLocaleString('pt-PT', {minimumFractionDigits: 2, maximumFractionDigits: 2}) + ' €';
  const events = { spins: 0, landings: 0, sanctuaries: 0, paylineChimes: 0 };
  game.cinematicV12 = { version: '1.6', events, mathUnchanged: false };

  // A fita gráfica é determinística e NÃO consome o RNG dos prémios.
  function stripFor(reel, stepCount, prior, final, round) {
    const symbols = ['10','J','Q','K','A','HORUS','MASK','ANUBIS','KING','TOMB'];
    const strip = Array.from({ length: stepCount + 10 }, (_, i) =>
      symbols[(i * 7 + reel * 3 + round * 5 + Math.floor(i / 5)) % symbols.length]);
    // Símbolos distintos em qualquer janela visível de três posições.
    // A sequência final é sempre a gerada pelo motor matemático.
    const locked = new Map();
    for (let row = 0; row < 3; row++) locked.set(3 + row, final[row]);
    // A grelha anterior pode vir de uma sessão guardada antes desta regra.
    const safePrior = prior.map((sym, row) => prior.slice(0, row).includes(sym)
      ? symbols.find(candidate => !prior.slice(0, row).includes(candidate)) : sym);
    for (let row = 0; row < 3; row++) locked.set(stepCount + 3 + row, safePrior[row]);
    for (let i = 0; i < strip.length; i++) {
      if (locked.has(i)) { strip[i] = locked.get(i); continue; }
      const forbidden = new Set();
      for (let j = Math.max(0, i - 2); j < i; j++) forbidden.add(strip[j]);
      for (let j = i + 1; j <= i + 2; j++) if (locked.has(j)) forbidden.add(locked.get(j));
      if (forbidden.has(strip[i])) {
        strip[i] = symbols.find(symbol => !forbidden.has(symbol)) || strip[i];
      }
    }
    return strip;
  }
  game.cinematicV12.stripFor = stripFor;

  // Rodar: início na grelha anterior; desaceleração individual já assegurada
  // pelo renderizador V1.1, que usa tempo e posição reais na fita.
  const previousStart = game.startSpin.bind(game);
  game.startSpin = function () {
    const prior = this.grid.map(col => col.slice());
    const old = this.spinObject;
    previousStart();
    const spin = this.spinObject;
    if (!this.spinning || !spin || spin === old) return;
    spin.wildFadeAt = null;
    spin.wildRestoreAt = null;
    spin.teaseAt = null;
    spin.teaseEndAt = null;
    spin.teasePlayed = false;
    const round = (this._cinemaRound || 0) + 1;
    this._cinemaRound = round;
    // V1.3.58: Free Spins deliberadamente mais lentas do que o jogo base.
    // Mesmo com Turbo guardado, os símbolos devem ser fáceis de seguir.
    // Apenas tempo visual: resultado já está definido antes da animação.
    spin.duration = spin.free ? (this.turbo ? 1350 : 2050)
      : (this.turbo ? 500 : 1040);
    spin.reelGap = spin.free ? (this.turbo ? 185 : 300)
      : (this.turbo ? 65 : 170);
    // As paragens são estritamente sequenciais (1→2→3→4→5).
    // Duas torres acrescentam UMA pausa antes do próximo tambor; essa pausa
    // mantém-se nos seguintes, mesmo quando aparece a terceira torre.
    // Assim, o 4.º nunca termina antes do 3.º.
    const baseStop = spin.grid.map((_, c) => spin.start + spin.duration + c * spin.reelGap);
    let seen = 0, suspenseDelay = 0, holdAdded = false, previousStop = spin.start;
    spin.stopAt = baseStop.map((stop, c) => {
      if (spin.free && seen >= 2 && !holdAdded) {
        suspenseDelay = this.turbo ? 280 : 720;
        holdAdded = true;
      } else if (!spin.free && seen >= 2 && !holdAdded) {
        suspenseDelay = this.turbo ? 420 : 1300;
        holdAdded = true;
      }
      const adjusted = Math.max(stop + suspenseDelay,
        c > 0 ? previousStop + spin.reelGap : stop);
      previousStop = adjusted;
      if (spin.grid[c].includes('TOMB')) seen++;
      return adjusted;
    });
    spin.cinematicSuspense = holdAdded;
    // Facilita os testes e evita regressões com paragens fora de ordem.
    spin.stopOrder = [0,1,2,3,4];
    spin.steps = Array.from({length: 5}, (_, c) => 27 + 4 * c);
    spin.show = spin.steps.map((steps, c) => stripFor(c, steps, prior[c], spin.grid[c], round));
    spin.cinematicPrior = prior;
    events.spins++;
    this.audio.startReelWhirr?.();
  };

  // WebAudio: som mecânico filtrado, sem ficheiros pagos nem música copiada.
  // Um único gerador de ruído curto em loop, com envelope e fade-out seguro.
  const audio = game.audio;
  audio.startReelWhirr = function () {
    if (!this.on || !this.ctx || document.hidden) return;
    this.stopReelWhirr();
    try {
      const ac = this.ctx, len = Math.floor(ac.sampleRate * .105);
      if (!this._reelNoise || this._reelNoise.sampleRate !== ac.sampleRate) {
        const buf = ac.createBuffer(1, len, ac.sampleRate), samples = buf.getChannelData(0);
        for (let i = 0; i < len; i++) samples[i] = (Math.random() * 2 - 1) * .66;
        this._reelNoise = buf;
      }
      const source = ac.createBufferSource(), filter = ac.createBiquadFilter(), gain = ac.createGain();
      source.buffer = this._reelNoise;
      source.loop = true;
      filter.type = 'bandpass';
      filter.frequency.setValueAtTime(240, ac.currentTime);
      filter.frequency.linearRampToValueAtTime(460, ac.currentTime + .65);
      filter.Q.value = .65;
      gain.gain.setValueAtTime(.0001, ac.currentTime);
      gain.gain.linearRampToValueAtTime(.017, ac.currentTime + .23);
      source.connect(filter); filter.connect(gain); gain.connect(this.out || ac.destination);
      source.onended = () => { try { source.disconnect(); filter.disconnect(); gain.disconnect(); } catch (_) {} };
      source.start();
      source.stop(ac.currentTime + 4.5); // guarda caso a janela seja suspensa
      this._reelAudio = {source, gain};
    } catch (_) { /* Falha áudio não interrompe a jogada. */ }
  };
  audio.stopReelWhirr = function () {
    const state = this._reelAudio;
    this._reelAudio = null;
    if (!state || !this.ctx) return;
    try {
      const t = this.ctx.currentTime;
      state.gain.gain.cancelScheduledValues(t);
      state.gain.gain.setTargetAtTime(.0001, t, .04);
      state.source.stop(t + .22);
      // O nó é libertado depois de um fade curto, sem clicks digitais.
    } catch (_) {}
  };
  // Impactos naturais e discretos: corpo grave com um toque abafado, sem
  // sinos metálicos ou notas agudas. Áudio apenas; não altera os giros.
  audio.tick = function (reel) {
    if (!this.on || !this.ctx || document.hidden) return;
    const ac = this.ctx, now = ac.currentTime, bus = this.out || ac.destination;
    try {
      // Corpo curto, semelhante ao fecho pesado de um tambor.
      const body = ac.createOscillator(), bodyGain = ac.createGain();
      body.type = 'sine';
      body.frequency.setValueAtTime(120 - reel * 3, now);
      body.frequency.exponentialRampToValueAtTime(67 - reel * 2, now + .125);
      bodyGain.gain.setValueAtTime(.0001, now);
      bodyGain.gain.exponentialRampToValueAtTime(.032, now + .007);
      bodyGain.gain.exponentialRampToValueAtTime(.0001, now + .145);
      body.connect(bodyGain); bodyGain.connect(bus);
      body.start(now); body.stop(now + .155);
      body.onended = () => { body.disconnect(); bodyGain.disconnect(); };

      // Pequena textura de madeira, filtrada; reutilizar buffer no Safari.
      if (!this._softImpactNoise || this._softImpactNoise.sampleRate !== ac.sampleRate) {
        const length = Math.max(1, Math.round(ac.sampleRate * .028));
        const buffer = ac.createBuffer(1, length, ac.sampleRate);
        const data = buffer.getChannelData(0);
        for (let j = 0; j < length; j++) data[j] = Math.random() * 2 - 1;
        this._softImpactNoise = buffer;
      }
      const tap = ac.createBufferSource(), lowpass = ac.createBiquadFilter(),
        highpass = ac.createBiquadFilter(), tapGain = ac.createGain();
      tap.buffer = this._softImpactNoise;
      lowpass.type = 'lowpass'; lowpass.frequency.value = 820;
      highpass.type = 'highpass'; highpass.frequency.value = 160;
      tapGain.gain.setValueAtTime(.016, now);
      tapGain.gain.exponentialRampToValueAtTime(.0001, now + .027);
      tap.connect(lowpass); lowpass.connect(highpass);
      highpass.connect(tapGain); tapGain.connect(bus);
      tap.start(now); tap.stop(now + .03);
      tap.onended = () => {
        tap.disconnect(); lowpass.disconnect(); highpass.disconnect(); tapGain.disconnect();
      };
    } catch (_) {
      this.tone(105, .10, 'sine', .018);
    }
  };

  audio.sanctuaryLanding = function (reel, count) {
    if (!this.on || !this.ctx || document.hidden) return;
    // As torres distinguem-se pela ressonância baixa, não por pancadas fortes.
    this.tone(360 + reel * 12, .18, 'sine', .017);
    if (count === 2) this.tone(490, .26, 'sine', .022, .045);
    if (count >= 3) this.tone(570, .30, 'sine', .027, .045);
  };
  audio.paylineChime = function (index) {
    if (!this.on || !this.ctx) return;
    this.tone(540 + (index % 5) * 55, .12, 'sine', .022);
  };

  // O fade começa apenas quando o segundo TOMB/WILD PAROU de verdade.
  // A grelha futura nunca é consultada para acionar o efeito antecipadamente.
  function wildFadeStrength(spin, now) {
    if (!spin || spin.wildFadeAt === null || spin.wildFadeAt === undefined) return 0;
    const entering = reduced ? 1 : ease((now - spin.wildFadeAt) / 300);
    const leaving = spin.wildRestoreAt === null || spin.wildRestoreAt === undefined
      ? 0 : reduced ? 1 : ease((now - spin.wildRestoreAt) / 260);
    return .62 * entering * (1 - leaving);
  }

  // Atravessar a animação dos símbolos sem deformar os ícones HD.
  const baseIcon = game.drawIcon.bind(game);
  game.drawIcon = function (symbol, x, y, w, h, win, t) {
    const spin = this.spinObject;
    const reel = Math.round((x - R.left) / R.step);
    const onReel = reel >= 0 && reel < 5 && y >= R.top - R.gh * 1.1 && y < R.top + R.gh * 4.2;
    const moving = onReel && this.spinning && spin && !spin.stopped[reel];
    // Os símbolos dos tambores já parados desvanecem; as torres WILD e
    // os tambores ainda em movimento mantêm-se bem visíveis.
    const fade = onReel && !moving && symbol !== 'TOMB' && spin?.stopped?.[reel]
      ? wildFadeStrength(spin, t || performance.now()) : 0;
    if (fade > 0) {
      ctx.save();
      ctx.globalAlpha *= 1 - fade;
    }
    if (moving && !reduced && symbol!=='TOMB') {
      ctx.save();
      ctx.globalAlpha *= .89;
      if ('filter' in ctx) ctx.filter = 'blur(0.7px)';
      baseIcon(symbol, x, y, w, h, win, t);
      ctx.restore();
    } else baseIcon(symbol, x, y, w, h, win, t);
    if (fade > 0) ctx.restore();
  };

  const baseReelBoard = game.reelBoard.bind(game);
  game.reelBoard = function (t) {
    baseReelBoard(t);
    // Tease apenas quando nas Free Spins já pararam 2 torres e outro tambor
    // ainda está em movimento. Não prever o terceiro Scatter.
    const active=this.spinObject;
    const showTease=active?.free && this.spinning &&
      Number.isFinite(active.teaseAt) && active.teaseAt>0 &&
      active.stopped.some(done=>!done) && active.teaseEndAt===null;
    if(showTease){
      const wave=reduced?1:.76+.24*Math.sin(t*.011);
      const y=R.top+R.gh*1.45;
      ctx.save();ctx.textAlign='center';ctx.textBaseline='middle';
      ctx.font='900 19px Georgia,serif';
      ctx.lineWidth=5;ctx.strokeStyle='#091827';
      ctx.shadowColor='#ffc45d';ctx.shadowBlur=reduced?0:14+10*wave;
      ctx.strokeText('FALTA 1 SCATTER!',280,y);
      ctx.fillStyle='#fff1ac';ctx.fillText('FALTA 1 SCATTER!',280,y);
      ctx.font='800 13px Arial,sans-serif';ctx.lineWidth=3;
      ctx.strokeText('+8 FREE SPINS',280,y+29);
      ctx.fillStyle='#a4f4ff';ctx.fillText('+8 FREE SPINS',280,y+29);
      ctx.restore();
    }
    const spin = this.spinObject;
    // Legenda sem retângulo, por baixo da moldura. O índice é o mesmo
    // usado pela linha desenhada pelo motor (alternância a cada 1600 ms).
    if (!this.spinning && this.lastResult && !(this.featureIntro && t >= this.featureIntro.start && t < this.featureIntro.end)) {
      const wins = this.lastResult.lineWins || [];
      let label = '';
      let tint = '#ffe5a3';
      const focus=this.sequenceFocusAt?.(t)||null;
      const phase=this.expansionView?.(t)?.stage;
      if(focus){
        const count=this.sequencePlayback?.stages?.filter(g=>g.kind!=='scatter').length||1;
        const order=this.sequencePlayback?.stages?.slice(0,focus.index+1)
          .filter(g=>g.kind!=='scatter').length||1;
        label=focus.kind==='scatter'?'TORRES · '+format(focus.amount):
          (!this.lastWasFree&&focus.kind==='line'
            ?'LINHA '+(focus.win?.line+1||focus.line+1)
            :'SEQUÊNCIA '+order+' / '+count+' · '+format(focus.amount));
        tint='#ffeab0';
      }else if(phase){
        label='EXPANSÃO '+phase.symbol+' · ACUMULADO '+format(this.displayWinAt?.(t)??0);
        tint='#80deff';
      } else if(this.lastWasFree && !(this.reveal && t<this.reveal.end) && wins.length){
        const w=wins[Math.floor(t/1600)%wins.length];
        label=(w.expanded?'EXPANSÃO '+w.symbol+' · LINHA ':'LINHA ')+(w.line+1)+' · '+format(w.amount);
        if(w.expanded)tint='#8affec';
      } else if(!(this.reveal && t<this.reveal.end) && this.lastResult.scatterWin>0){
        label='SCATTER · '+format(this.lastResult.scatterWin);
        tint='#a2fff0';
      }
      if (label) {
        ctx.save();
        ctx.textAlign='center'; ctx.textBaseline='middle';
        ctx.font='800 16px Georgia,serif';
        ctx.lineWidth=3.5; ctx.strokeStyle='#0b141b';
        ctx.shadowColor=tint; ctx.shadowBlur=reduced ? 0 : 8;
        ctx.strokeText(label, 280, 714);
        ctx.fillStyle=tint; ctx.fillText(label, 280, 714);
        ctx.restore();
      }
      // Depois da leitura descendente, a quantia da sequência surge
      // isolada, sem caixa, e o saldo já a recebeu nessa altura.
      if(focus && t>=focus.creditAt-140 && (this.lastWasFree || focus.kind!=='line')){
        const p=clamp((t-focus.creditAt+140)/340,0,1);
        const pop=1+.09*(1-p);
        const y=R.top+R.gh*3-42;
        ctx.save();ctx.translate(280,y);ctx.scale(pop,pop);
        ctx.textAlign='center';ctx.textBaseline='middle';
        ctx.font='900 35px Georgia,serif';ctx.lineJoin='round';
        ctx.lineWidth=6;ctx.strokeStyle='#20140c';
        ctx.shadowColor='#edb750';ctx.shadowBlur=reduced?0:19;
        ctx.strokeText(format(focus.amount),0,0);
        ctx.fillStyle='#ffe2a2';ctx.fillText(format(focus.amount),0,0);
        ctx.restore();
      }
    }
  };

  const baseFinish = game.finishSpin.bind(game);
  game.finishSpin = function () {
    if (!this.spinObject) return;
    baseFinish();
    this.audio.stopReelWhirr?.();
    this._cinemaPayAt = performance.now();
    this._cinemaPaySeen = new Set();
  };

  // O gong do Wild deve chamar a atenção enquanto ainda há hipótese de
  // suspense: se os Wilds só surgirem nos tambores 4/5, tocar apenas o
  // estalido normal de paragem desses tambores (sem gong especial).
  game.shouldSoundWildLanding = function (reel, spin = this.spinObject) {
    const columns = spin?.grid;
    if (!Array.isArray(columns) || !columns[reel]?.includes('TOMB')) return false;
    if (reel < 3) return true;
    return columns.slice(0,3).some(col => col.includes('TOMB'));
  };

  // Detetar apenas vitórias já comprovadas pelas colunas paradas.
  // Não usar o resultado futuro para disparar o alerta antecipadamente:
  // KING/HORUS/etc. pagam com 2; letras simples pagam a partir de 3.
  const E = window.TombEngine;
  function hasConfirmedSequence(spin,activeLines) {
    if(!E || !spin || !Array.isArray(spin.grid) || !Array.isArray(spin.stopped))return false;
    let visible=0;
    while(visible<5 && spin.stopped[visible])visible++;
    if(visible<2)return false;
    for(let i=0;i<Math.min(activeLines||10,E.LINES.length);i++){
      const rows=E.LINES[i];
      for(const symbol of E.ALL){
        let matched=0;
        for(let c=0;c<visible;c++){
          const at=spin.grid[c][rows[c]];
          if(at===symbol || (at==='TOMB' && symbol!=='TOMB'))matched++;
          else break;
        }
        if((E.PAYS[symbol]?.[matched]||0)>0)return true;
      }
    }
    return false;
  }
  game.cinematicV12.hasConfirmedSequence=hasConfirmedSequence;

  const baseFrame = game.frame.bind(game);
  game.frame = function (t) {
    const spin = this.spinObject;
    const before = spin ? spin.stopped.slice() : null;
    baseFrame(t);
    if (spin && before) {
      for (let c=0; c<5; c++) {
        if (before[c] || !spin.stopped[c]) continue;
        events.landings++;
        const hadTomb = spin.grid[c].includes('TOMB');
        if (hadTomb) {
          const count = spin.grid.reduce((n, col, index) =>
            n + (spin.stopped[index] ? col.filter(s => s === 'TOMB').length : 0), 0);
          if (this.shouldSoundWildLanding(c, spin)) {
            this.audio.sanctuaryLanding?.(c, count);
          }
          events.sanctuaries++;
          // Se houver mais tambores por parar, desvanecer os já resolvidos.
          if (count === 2 && spin.wildFadeAt === null &&
              this.spinning && this.spinObject === spin &&
              spin.stopped.some(stopped => !stopped)) {
            spin.wildFadeAt = t;
            if(spin.free && !spin.teasePlayed){
              spin.teaseAt=t;
              spin.teasePlayed=true;
              this.audio.tease?.();
              events.teases=(events.teases||0)+1;
            }
          }
          // A terceira torre revela o desfecho e devolve a imagem ao normal.
          if (count >= 3 && spin.wildFadeAt !== null && spin.wildRestoreAt === null) {
            spin.wildRestoreAt = t;
            spin.teaseEndAt=t;
          }
        }
      }
    }
    // O primeiro conjunto vencedor confirmado recebe UM alerta musical.
    // Os cinco pops de paragem mantêm-se e o gong do Wild é independente.
    if (spin && !spin._sequenceAlerted && this.sound &&
        hasConfirmedSequence(spin,this.ledger?.lines)) {
      spin._sequenceAlerted=true;
      this.audio.sequenceAlert?.();
      events.sequenceAlerts=(events.sequenceAlerts||0)+1;
    }
    if (!this.sound || document.hidden || this.paused) this.audio.stopReelWhirr?.();
    const wins = this.lastResult?.lineWins || [];
    const ready = !this.spinning && !(this.reveal && t < this.reveal.end) &&
      !(this.featureIntro && t < this.featureIntro.end) &&
      !(this.v04Celebration && t < this.v04Celebration.end);
    // As Free Spins já têm a escala musical sincronizada com a sequência,
    // e o jogo base já toca um som por linha. Evitar chimes duplicados.
    if (ready && !this.lastWasFree && !this.basePaylineDisplay?.length &&
        this._cinemaPayAt && wins.length && this._cinemaPaySeen &&
        t-this._cinemaPayAt < Math.min(9000, (wins.length+1)*1600)) {
      const i = Math.floor(t / 1600) % wins.length;
      if (!this._cinemaPaySeen.has(i)) {
        this._cinemaPaySeen.add(i);
        this.audio.paylineChime?.(i);
        events.paylineChimes++;
      }
    }
  };

  document.addEventListener('visibilitychange', () => {
    if (document.hidden) audio.stopReelWhirr?.();
  });
  document.documentElement.dataset.faraoCinematic = '1.2';
})();
