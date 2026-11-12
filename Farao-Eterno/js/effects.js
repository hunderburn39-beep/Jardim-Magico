/* Faraó Eterno V0.4 – melhorias audiovisuais sem alterar a matemática do jogo.
   Cliente offline com créditos fictícios. */
(() => {
  'use strict';
  const game = window.__faraoGame;
  const canvas = document.getElementById('canvas');
  if (!game || !canvas) return;
  const ctx = canvas.getContext('2d');
  const TAU = Math.PI * 2;
  const clamp = (v, a, b) => Math.min(b, Math.max(a, v));
  const ease = p => 1 - Math.pow(1 - clamp(p, 0, 1), 3);
  const reduceMotion = window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  game.v04Celebration = null;
  game.v04Scatter = null;
  game.v04Events = { celebrations: 0, suspense: 0, landings: 0 };

  // Sons criados no momento por WebAudio, sem descarregamentos externos.
  const audio = game.audio;
  const oldSpin = audio.spin.bind(audio);
  const oldTick = audio.tick.bind(audio);
  const oldExtra = audio.extra.bind(audio);
  audio.shimmer = function (intensity = 1) {
    if (!this.on || !this.ctx) return;
    [523.25, 659.25, 783.99, 1046.5].forEach((f, i) => {
      this.tone(f, .16 + i * .035, 'sine', .020 * intensity, i * .083);
    });
  };
  audio.whisper = function (freq = 800, duration = .12, volume = .013) {
    if (!this.on || !this.ctx) return;
    // O ruído filtrado dá textura sem ficheiros MP3 nem loops permanentes.
    try {
      const sr = this.ctx.sampleRate;
      const length = Math.max(64, Math.floor(sr * duration));
      const buffer = this.ctx.createBuffer(1, length, sr);
      const data = buffer.getChannelData(0);
      for (let i = 0; i < length; i++) data[i] = (Math.random() * 2 - 1) * (1 - i / length);
      const source = this.ctx.createBufferSource();
      const filter = this.ctx.createBiquadFilter();
      const gain = this.ctx.createGain();
      source.buffer = buffer;
      filter.type = 'bandpass';
      filter.frequency.value = freq;
      filter.Q.value = 0.7;
      gain.gain.value = volume;
      source.connect(filter); filter.connect(gain); gain.connect(this.out||this.ctx.destination);
      source.start(); source.stop(this.ctx.currentTime + duration + .005);
    } catch (_) { /* Em browsers antigos ficam os tons originais. */ }
  };
  audio.spin = function () { oldSpin(); this.whisper(370, .21, .020); };
  audio.tick = function (reel) {
    oldTick(reel);
    this.whisper(510 + reel * 90, .075, .024);
    if (reel === 4) this.tone(230, .14, 'sine', .037);
  };
  audio.extra = function () { oldExtra(); this.shimmer(1.6); };
  audio.suspense = function () {
    [390, 460, 565].forEach((f, i) => this.tone(f, .23, 'triangle', .045, i * .16));
  };
  audio.fanfare = function (tier) {
    const notes = tier === 3 ? [392, 523, 659, 784, 1047, 1319]
      : tier === 2 ? [392, 494, 587, 784, 988]
      : [440, 554, 659, 880];
    notes.forEach((f, i) => this.tone(f, .32, 'triangle', .055, i * .12));
    this.shimmer(tier === 3 ? 1.6 : 1);
  };

  const startSpin = game.startSpin.bind(game);
  game.startSpin = function () {
    const wasSpinning = this.spinning;
    startSpin();
    if (!wasSpinning && this.spinning) {
      this.v04Celebration = null;
      this.v04Scatter = null;
    }
  };
  const finishSpin = game.finishSpin.bind(game);
  game.finishSpin = function () {
    if (!this.spinObject) return;
    finishSpin();
    // As Free Spins só celebram o total ao abrir o resumo final.
    if(this.lastWasFree||this.lastResult?.trigger)return;
    const staged=this.sequencePlayback?.stages||[];
    const paidSequences=staged.filter(g=>g.kind!=='scatter');
    const informational=paidSequences.length>=2;
    // A celebração mostra a SOMA das sequências já pagas; não credita nada.
    // SCATTER é contabilizado à parte, não entra neste subtítulo.
    const prize=informational
      ?paidSequences.reduce((n,g)=>n+g.amount,0):this.lastWin;
    const factor = this.bet > 0 ? prize / this.bet : 0;
    if (!Number.isFinite(factor) || factor < 10) return;
    const tier = factor >= 100 ? 3 : factor >= 30 ? 2 : 1;
    const duration = reduceMotion ? 1250 : this.turbo ? 1900 : tier === 3 ? 5150 : tier === 2 ? 4550 : 3900;
    const start=Math.max(performance.now(),this.reveal?.end||0,this.featureIntro?.end||0);
    const messages = { 1: 'GRANDE PRÉMIO', 2: 'PRÉMIO ÉPICO', 3: 'TESOURO LENDÁRIO' };
    this.v04Celebration = {
      start, end: start + duration, prize, tier, label: messages[tier],
      informational,fanPlayed:false,
      coins: Array.from({ length: reduceMotion ? 0 : tier * 13 + 17 }, (_, i) => ({
        x: 50 + Math.random() * 460,
        y: 650 + Math.random() * 120,
        vx: (Math.random() - .5) * 110,
        vy: -(130 + Math.random() * 260),
        angle: Math.random() * TAU,
        phase: i * .13,
        r: 3 + Math.random() * 5
      }))
    };
    this.messageUntil = 0; // evita dois avisos sobrepostos ao prémio especial
    this.nextActionAt = Math.max(this.nextActionAt, this.v04Celebration.end + 140);
    this.v04Events.celebrations++;
    // O som é lançado pelo drawFx no primeiro frame em que a chuva final é visível.
    // Evita tocar antecipadamente, enquanto as linhas ainda estão a ser reveladas.
  };

  const frame = game.frame.bind(game);
  game.frame = function (t) {
    // Suspense quando se veem dois Scatter nos rolos já parados.
    const spin = this.spinObject;
    if (spin && !spin.v04SuspensePlayed) {
      const left = spin.stopped.filter(Boolean).length;
      const count = spin.stopped.reduce((n, stopped, reel) =>
        n + (stopped ? spin.grid[reel].filter(s => s === 'TOMB').length : 0), 0);
      if (left >= 2 && left < 5 && count === 2) {
        spin.v04SuspensePlayed = true;
        this.v04Scatter = { start: t, end: t + (this.turbo ? 350 : 740) };
        this.v04Events.suspense++;
        audio.suspense();
      }
    }
    frame(t);
  };

  const drawBoard = game.reelBoard.bind(game);
  game.reelBoard = function (t) {
    drawBoard(t);
    // Brilho de aterragem: muito curto, desenhado só no rolo que parou.
    for (let c = 0; c < 5; c++) {
      const ms = t - this.landings[c];
      if (!this.landings[c] || ms < 0 || ms > 290) continue;
      const alpha = (1 - ms / 290) * .27;
      ctx.save();
      ctx.beginPath(); ctx.rect(33 + c * 101, 258, 99, 396); ctx.clip();
      const p = ms / 290;
      const x = 33 + c * 101;
      const shine = ctx.createLinearGradient(x, 0, x + 99, 0);
      shine.addColorStop(0, 'rgba(255,225,145,0)');
      shine.addColorStop(.52, `rgba(255,234,174,${alpha})`);
      shine.addColorStop(1, 'rgba(255,225,145,0)');
      ctx.fillStyle = shine;
      ctx.fillRect(x, 258 + p * 25, 99, 396 - p * 25);
      ctx.restore();
    }
  };

  const drawFx = game.drawFx.bind(game);
  game.drawFx = function (t) {
    drawFx(t);
    const warning = this.v04Scatter;
    if (this.spinning && warning && t < warning.end) {
      ctx.save();
      const alpha = .14 + .16 * Math.abs(Math.sin((t - warning.start) * .017));
      ctx.globalAlpha = alpha;
      ctx.strokeStyle = '#a4fff2'; ctx.lineWidth = 5;
      ctx.shadowColor = '#76fff0'; ctx.shadowBlur = 25;
      ctx.strokeRect(27, 253, 506, 403);
      ctx.restore();
    }
    const celebration = this.v04Celebration;
    if (!celebration || t < celebration.start || t > celebration.end) return;
    if(!celebration.fanPlayed&&!this.paused){
      celebration.fanPlayed=true;audio.fanfare(celebration.tier);
    }
    const p = clamp((t - celebration.start) / (celebration.end - celebration.start), 0, 1);
    const alpha = Math.min(1, p * 8, (1 - p) * 8);
    const amount = celebration.prize * ease(clamp(p * 1.28, 0, 1));
    ctx.save(); ctx.globalAlpha = alpha;
    // A moldura escurece o jogo sem tapar completamente os símbolos premiados.
    const shade = ctx.createRadialGradient(280, 435, 18, 280, 435, 310);
    shade.addColorStop(0, 'rgba(22,15,29,.93)');
    shade.addColorStop(.64, 'rgba(17,13,24,.81)');
    shade.addColorStop(1, 'rgba(10,7,18,.23)');
    ctx.fillStyle = shade; ctx.fillRect(29, 264, 502, 390);
    const beam = ctx.createRadialGradient(280, 443, 12, 280, 443, 240);
    beam.addColorStop(0, celebration.tier === 3 ? 'rgba(255,248,182,.37)' : 'rgba(255,222,135,.24)');
    beam.addColorStop(1, 'rgba(255,211,100,0)');
    ctx.fillStyle = beam; ctx.fillRect(42, 265, 477, 382);
    const sweep = Math.sin(t * .005) * .5 + .5;
    ctx.textAlign = 'center'; ctx.textBaseline = 'middle';
    ctx.shadowColor = '#d99039'; ctx.shadowBlur = 12 + sweep * 15;
    ctx.font = `900 ${celebration.tier === 3 ? 38 : 34}px Georgia`;
    ctx.strokeStyle = '#382013'; ctx.lineWidth = 5;
    ctx.strokeText(celebration.label, 280, 363);
    ctx.fillStyle = '#ffeab3'; ctx.fillText(celebration.label, 280, 363);
    ctx.shadowBlur = 12;
    ctx.font = '900 58px Georgia';
    const shown = (Math.round(amount * 100) / 100).toLocaleString('pt-PT', {minimumFractionDigits:2, maximumFractionDigits:2}) + ' cr';
    ctx.strokeStyle = '#29151d'; ctx.lineWidth = 6;
    ctx.strokeText(shown, 280, 456);
    ctx.fillStyle = '#fff0a8'; ctx.fillText(shown, 280, 456);
    ctx.shadowBlur = 0;
    ctx.font = '800 14px Arial'; ctx.fillStyle = '#89e8d8';
    ctx.fillText(celebration.informational
      ?'TOTAL DAS SEQUÊNCIAS · JÁ CREDITADO'
      :'TESOUROS DO TEMPLO', 280, 514);
    ctx.restore();
    if (!reduceMotion) {
      ctx.save(); ctx.beginPath(); ctx.rect(28, 253, 504, 405); ctx.clip();
      const secs = (t - celebration.start) / 1000;
      for (const coin of celebration.coins) {
        const x = coin.x + coin.vx * secs;
        const y = coin.y + coin.vy * secs + 115 * secs * secs;
        if (y < 260 || y > 659) continue;
        // Partilhar as mesmas moedas cunhadas e brilhantes usadas nas linhas premiadas.
        // Se a camada opcional ainda não estiver carregada, conservar a arte anterior.
        const drawGold=this.coinCelebrations?.drawGoldenCoin;
        if(typeof drawGold==='function'){
          drawGold(x,y,(coin.r+1.6)*2.1,coin.angle+secs*5,
            alpha*clamp(1-secs/3.5,0,1),true);
        }else{
          ctx.save();ctx.translate(x,y);ctx.rotate(coin.angle+secs*2.8);
          ctx.globalAlpha=alpha*clamp(1-secs/3.5,0,1);
          ctx.fillStyle='#bb7731';ctx.strokeStyle='#ffe9ac';ctx.lineWidth=1.5;
          ctx.beginPath();ctx.ellipse(0,0,coin.r,coin.r*.73,0,0,TAU);ctx.fill();ctx.stroke();
          ctx.restore();
        }
      }
      ctx.restore();
    }
  };

  // A fonte do código base não muda; a etiqueta comunica a versão exibida.

})();
/* Faraó Eterno V0.5 — iPhone, fluidez e feedback de prémios.
   Efeitos e otimizações exclusivamente visuais. Motor de prémios intacto. */
(() => {
 'use strict';
 const game = window.__faraoGame;
 const canvas = document.getElementById('canvas');
 if (!game || !canvas) return;
 const ctx = canvas.getContext('2d');
 const reduceMotion = !!(window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches);
 const onPhone = Math.min(window.innerWidth, window.innerHeight) <= 520;
 const clamp = (v,a,b) => Math.max(a,Math.min(b,v));
 game.v05Events = { gentleStops:0, winHighlights:0, visibleRenders:0, skippedRenders:0 };
 game._v05QuickStop = false;

 // A second tap stops the actual reels in order; no replay of a reel that stopped.
 const oldStart = game.startSpin.bind(game);
 game.startSpin = function () {
   if (this.spinning && this.spinObject && !this.paused) {
     if (this._v05QuickStop) return;
     this._v05QuickStop = true;
     const spin = this.spinObject, now = performance.now();
     const stopped = spin.stopped.filter(Boolean).length;
     // Avoid touching the generated grid or the settled win.
     // Reels not yet landed have 65 ms separation for a readable landing.
     spin.start = now - spin.duration - Math.max(0, stopped-1) * 65;
     spin.reelGap = 65;
     this.v04Scatter = null;
     this.v05Events.gentleStops++;
     return;
   }
   oldStart();
   if (this.spinning) this._v05QuickStop = false;
 };

 // Light shimmer only on symbols that really won, without moving their hitboxes.
 const oldIcon = game.drawIcon.bind(game);
 game.drawIcon = function(symbol,x,y,w,h,isWinner,t) {
   if (!isWinner || this.spinning || reduceMotion) return oldIcon(symbol,x,y,w,h,isWinner,t);
   const wave = .5+.5*Math.sin(t*.006+x*.017+y*.006);
   const zoom = 1 + wave*.028;
   ctx.save();
   ctx.translate(x+w/2,y+h/2);ctx.scale(zoom,zoom);ctx.translate(-x-w/2,-y-h/2);
   oldIcon(symbol,x,y,w,h,isWinner,t);
   ctx.restore();
   if(wave>.94 && this.v05Events) this.v05Events.winHighlights++;
 };

 // Correct Safari viewport changes after showing/hiding the browser bar.
 if(window.visualViewport){
   let lastH=window.visualViewport.height;
   window.visualViewport.addEventListener('resize',()=>{
     const h=window.visualViewport.height;
     if(Math.abs(h-lastH)>24){lastH=h;game.resize();}
   },{passive:true});
 }
 document.addEventListener('visibilitychange',()=>{
   // Never start autoplay in a background tab. WebAudio is resumed by next tap.
   if(document.hidden && game.audio.ctx && game.audio.ctx.state==='running'){
     game.audio.ctx.suspend().catch(()=>{});
   }
 });

 // Mobile: draw faster during spinning and celebrations; slow idle frames down.
 // Reels, timers and the RNG still execute on every requestAnimationFrame.
 const oldRender = game.render.bind(game);
 game.render = function(t) {
   if(document.hidden){this.v05Events.skippedRenders++;return;}
   const active = this.spinning ||
     (this.v04Celebration && t<=this.v04Celebration.end) ||
     (this.reveal && t<=this.reveal.end) ||
     (this.featureIntro && t<=this.featureIntro.end) ||
     (this.winCount && t<this.winCount.start+this.winCount.duration) ||
     (this.v04Scatter && t<this.v04Scatter.end) ||
     t<this.messageUntil;
   const minStep=active?1000/62:1000/31;
   if(this._v05LastRender && t-this._v05LastRender<minStep){this.v05Events.skippedRenders++;return;}
   this._v05LastRender=t;
   this.v05Events.visibleRenders++;
   oldRender(t);
 };

 window.addEventListener('resize',()=>{game._v05LastRender=0;},{passive:true});
 document.documentElement.dataset.faraoVersion='1.0';
})();
