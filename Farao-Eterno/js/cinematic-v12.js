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
    .toLocaleString('pt-PT', {minimumFractionDigits: 2, maximumFractionDigits: 2}) + ' cr';
  const events = { spins: 0, landings: 0, sanctuaries: 0, paylineChimes: 0 };
  game.cinematicV12 = { version: '1.2', events, mathUnchanged: true };

  // A fita gráfica é determinística e NÃO consome o RNG dos prémios.
  function stripFor(reel, stepCount, prior, final, round) {
    const symbols = ['10','J','Q','K','A','HORUS','MASK','ANUBIS','KING','TOMB'];
    const strip = Array.from({ length: stepCount + 10 }, (_, i) =>
      symbols[(i * 7 + reel * 3 + round * 5 + Math.floor(i / 5)) % symbols.length]);
    for (let row = 0; row < 3; row++) {
      strip[stepCount + 3 + row] = prior[row];
      strip[3 + row] = final[row];
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
    const round = (this._cinemaRound || 0) + 1;
    this._cinemaRound = round;
    spin.duration = this.turbo ? 520 : 1040;
    const showTwo = spin.grid.slice(0, 4)
      .reduce((count, col) => count + col.filter(s => s === 'TOMB').length, 0) >= 2;
    spin.reelGap = this.turbo ? 72 : showTwo ? 210 : 170;
    spin.steps = Array.from({length: 5}, (_, c) => 27 + 4 * c);
    spin.show = spin.steps.map((steps, c) => stripFor(c, steps, prior[c], spin.grid[c], round));
    spin.cinematicPrior = prior;
    spin.cinematicSuspense = showTwo;
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
  audio.sanctuaryLanding = function (reel, count) {
    if (!this.on || !this.ctx) return;
    this.tone(680 + reel * 35, .22, 'sine', .040);
    this.tone(1020 + reel * 48, .28, 'triangle', .020, .06);
    if (count >= 2) this.shimmer?.(.72);
  };
  audio.paylineChime = function (index) {
    if (!this.on || !this.ctx) return;
    this.tone(540 + (index % 5) * 55, .12, 'sine', .022);
  };

  // Atravessar a animação dos símbolos sem deformar os ícones HD.
  const baseIcon = game.drawIcon.bind(game);
  game.drawIcon = function (symbol, x, y, w, h, win, t) {
    const spin = this.spinObject;
    const reel = Math.round((x - R.left) / R.step);
    const onReel = reel >= 0 && reel < 5 && y >= R.top - R.gh * 1.1 && y < R.top + R.gh * 4.2;
    const moving = onReel && this.spinning && spin && !spin.stopped[reel];
    const visibleTomb = symbol === 'TOMB' && onReel && !moving;
    if (visibleTomb && !reduced) {
      const wave = .55 + .45 * Math.sin((t || 0) * .007 + reel);
      const halo = ctx.createRadialGradient(x+w/2, y+h/2, 8, x+w/2, y+h/2, w * .63);
      halo.addColorStop(0, 'rgba(255,236,146,' + (.13 + .11 * wave) + ')');
      halo.addColorStop(1, 'rgba(255,190,71,0)');
      ctx.save(); ctx.fillStyle = halo; ctx.fillRect(x, y, w, h); ctx.restore();
    }
    if (moving && !reduced) {
      ctx.save();
      ctx.globalAlpha *= .89;
      if ('filter' in ctx) ctx.filter = 'blur(0.7px)';
      baseIcon(symbol, x, y, w, h, win, t);
      ctx.restore();
    } else baseIcon(symbol, x, y, w, h, win, t);
    if (visibleTomb && !reduced) {
      const wave = .5 + .5 * Math.sin((t || 0) * .008 + x * .03);
      ctx.save();
      ctx.strokeStyle = 'rgba(255,218,121,' + (.18 + .17 * wave) + ')';
      ctx.shadowColor = '#ffdc92'; ctx.shadowBlur = 10;
      ctx.lineWidth = 1.6;
      ctx.beginPath();
      ctx.ellipse(x+w/2, y+h/2, w*.42, h*.39, 0, 0, Math.PI*2);
      ctx.stroke(); ctx.restore();
    }
  };

  const baseReelBoard = game.reelBoard.bind(game);
  game.reelBoard = function (t) {
    baseReelBoard(t);
    const spin = this.spinObject;
    // Legenda sem retângulo, por baixo da moldura. O índice é o mesmo
    // usado pela linha desenhada pelo motor (alternância a cada 1600 ms).
    if (!this.spinning && this.lastResult && !(this.featureIntro && t < this.featureIntro.end) &&
        !(this.reveal && t < this.reveal.end)) {
      const wins = this.lastResult.lineWins || [];
      let label = '';
      let tint = '#ffe5a3';
      if (wins.length) {
        const w = wins[Math.floor(t/1600) % wins.length];
        label = (w.expanded ? 'EXPANSÃO · ' : 'LINHA ') + (w.line+1) + ' · ' + format(w.amount);
        if (w.expanded) tint = '#8affec';
      } else if (this.lastResult.scatterWin > 0) {
        label = 'SCATTER · ' + format(this.lastResult.scatterWin);
        tint = '#a2fff0';
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
          const count = spin.grid.slice(0,c+1).reduce((n, col) =>
            n + col.filter(s => s === 'TOMB').length, 0);
          this.audio.sanctuaryLanding?.(c, count);
          events.sanctuaries++;
        }
      }
    }
    if (!this.sound || document.hidden || this.paused) this.audio.stopReelWhirr?.();
    const wins = this.lastResult?.lineWins || [];
    const ready = !this.spinning && !(this.reveal && t < this.reveal.end) &&
      !(this.featureIntro && t < this.featureIntro.end) &&
      !(this.v04Celebration && t < this.v04Celebration.end);
    if (ready && this._cinemaPayAt && wins.length && this._cinemaPaySeen &&
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
