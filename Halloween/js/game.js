/* Halloween — apresentação original Phaser 3, independente dos outros jogos. */
(function () {
  "use strict";
  const status = document.getElementById("status");
  const E = globalThis.HalloweenEngine;
  if (!globalThis.Phaser || !E) {
    status.textContent = "Não foi possível carregar o motor do jogo. Recarrega a página.";
    return;
  }

  const W = 720, H = 1280, X = 54, Y = 386, BW = 612, BH = 492;
  const CW = BW / 5, CH = BH / 3;
  const SAVE_KEY = "halloween-noite-bruxas-v01";
  const fmt = v => Number(v).toLocaleString("pt-PT", { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  function label(scene, x, y, text, size, color, extra) {
    return scene.add.text(x,y,text,{fontFamily:"Trebuchet MS, Arial, sans-serif",
      fontStyle:"bold",fontSize:size+"px",color:color,align:"center",...extra}).setOrigin(0.5);
  }

  class Halloween extends Phaser.Scene {
    constructor() { super("Halloween"); this.state = E.createState(); this.isSpinning = false; this.muted = false; this.audioCtx = null; }
    create() {
      this.loadSave();
      this.drawBackground();
      label(this, W/2, 140, "HALLOWEEN", 81, "#ffb64b", {stroke:"#310c0e",strokeThickness:12}).setShadow(0,8,"#ec520d",15);
      label(this, W/2, 210, "NOITE DAS BRUXAS", 31, "#f9e9bd", {stroke:"#5a2437",strokeThickness:5});
      this.drawProgressBar();
      this.notice = label(this,W/2,351,"25 LINHAS DE PAGAMENTO",25,"#e9bd76",{stroke:"#130820",strokeThickness:5});
      this.drawReels();
      label(this,142,924,"SALDO",25,"#b89dbd");
      label(this,578,924,"APOSTA",25,"#b89dbd");
      this.balanceText = label(this,142,967,"",39,"#fff0c6",{stroke:"#231021",strokeThickness:3});
      this.betText = label(this,578,967,"",39,"#fff0c6",{stroke:"#231021",strokeThickness:3});
      this.winText = label(this,W/2,1018,"PRÉMIO 0,00",30,"#f8c86a");
      this.makeCircleButton(141,1123,54,"−",67,()=>this.changeBet(-1));
      this.makeCircleButton(579,1123,54,"+",67,()=>this.changeBet(1));
      this.makeCircleButton(W/2,1118,84,"⟳",94,()=>this.startSpin(),true);
      this.spinLabel = label(this,W/2,1221,"GIRAR",27,"#ffdf95");
      this.freeText = label(this,W/2,1260,"",21,"#eac2f3");
      this.soundText = label(this,629,52,"SOM ✓",23,"#ecdbaa").setInteractive({useHandCursor:true});
      this.soundText.on("pointerdown",()=>{
        this.muted=!this.muted;
        this.soundText.setText(this.muted?"SOM ✕":"SOM ✓");
      });
      this.updateHUD();
      this.input.keyboard?.on("keydown-SPACE",()=>this.startSpin());
      status.textContent = "";
    }

    drawBackground() {
      const sky = this.add.graphics();
      for(let y=0;y<H;y+=8){
        const t=y/H;
        const r=Math.round(17+31*t),g=Math.round(8+10*t),b=Math.round(38+20*t);
        sky.fillStyle(Phaser.Display.Color.GetColor(r,g,b),1).fillRect(0,y,W,9);
      }
      const moon=this.add.graphics().fillStyle(0xe4c7a6,0.14).fillCircle(520,226,133);
      moon.fillStyle(0xffdfae,0.20).fillCircle(520,226,97);
      moon.fillStyle(0xf6d5a4,0.74).fillCircle(520,226,66);
      moon.fillStyle(0xb79d8a,0.28).fillCircle(493,207,15).fillCircle(550,249,10);
      const stars=this.add.graphics();
      for(let i=0;i<65;i++){
        const x=(i*197+41)%W, y=(i*71+25)%885;
        stars.fillStyle(0xe5d9e5,i%3===0?0.7:0.24).fillCircle(x,y,i%5===0?2:1);
      }
      const castle=this.add.graphics().fillStyle(0x130c23,0.85);
      castle.fillRect(35,320,650,80);
      castle.fillRect(100,265,95,85).fillRect(252,278,155,90).fillRect(503,253,95,115);
      castle.fillTriangle(80,268,155,190,215,268).fillTriangle(480,257,552,180,618,257);
      const horizon=this.add.graphics().fillStyle(0x0a0714,0.8);
      horizon.fillEllipse(140,1270,550,440).fillEllipse(620,1270,590,370);
      const branches=this.add.graphics().lineStyle(12,0x10091b,0.97);
      branches.beginPath();branches.moveTo(0,200);branches.lineTo(66,252);branches.lineTo(130,249);branches.moveTo(65,252);branches.lineTo(94,200);branches.strokePath();
      branches.beginPath();branches.moveTo(720,260);branches.lineTo(664,305);branches.lineTo(594,290);branches.moveTo(665,305);branches.lineTo(641,242);branches.strokePath();
    }

    drawProgressBar() {
      const g=this.add.graphics();
      g.fillStyle(0x160a25,0.86).fillRoundedRect(70,260,580,57,24);
      g.lineStyle(3,0x9f672e,1).strokeRoundedRect(70,260,580,57,24);
      label(this,161,289,"🎃  ABÓBORAS",22,"#f9cc7e");
      this.progressBg=this.add.graphics();
      this.progressText=label(this,565,289,"0 / 10",25,"#ffe9b9");
    }
    drawReels() {
      const board=this.add.graphics();
      board.fillStyle(0x12081d,0.96).fillRoundedRect(X-17,Y-19,BW+34,BH+38,26);
      board.lineStyle(10,0x603334,1).strokeRoundedRect(X-17,Y-19,BW+34,BH+38,26);
      board.lineStyle(5,0xe1a14f,1).strokeRoundedRect(X-9,Y-10,BW+18,BH+20,17);
      const cells=this.add.graphics();
      this.slotTexts=Array.from({length:5},()=>[]);
      for(let col=0;col<5;col++) for(let row=0;row<3;row++){
        const x=X+col*CW,y=Y+row*CH, cx=x+CW/2, cy=y+CH/2;
        cells.fillStyle((col+row)%2===0?0x2e1836:0x24132f,0.89).fillRect(x+2,y+2,CW-4,CH-4);
        cells.lineStyle(2,0x694554,0.8).strokeRect(x+2,y+2,CW-4,CH-4);
        const s=E.SYMBOLS[(col+row*3)%E.SYMBOLS.length];
        const tile=label(this,cx,cy,s.icon,95,"#ffffff",{stroke:"#35182c",strokeThickness:1});
        this.slotTexts[col][row]=tile;
      }
      // Separadores verticais visíveis, sem caixas opacas sobre os ícones.
      const lines=this.add.graphics().lineStyle(4,0xb9813c,0.84);
      for(let col=1;col<5;col++) lines.lineBetween(X+col*CW,Y+2,X+col*CW,Y+BH-2);
      this.highlights=this.add.graphics();
    }
    makeCircleButton(x,y,r,symbol,size,callback,primary) {
      const g=this.add.graphics();
      g.fillStyle(0x0b0715,0.72).fillCircle(x,y+7,r+7);
      g.fillStyle(primary?0xe49732:0x583152,1).fillCircle(x,y,r);
      g.lineStyle(5,primary?0xffda80:0xc18e9d,1).strokeCircle(x,y,r-3);
      const t=label(this,x,y-5,symbol,size,primary?"#34121a":"#fff1d1");
      const zone=this.add.zone(x,y,2*r,2*r).setInteractive({useHandCursor:true});
      zone.on("pointerdown",()=>{ callback(); this.tweens.add({targets:t,scale:0.91,duration:90,yoyo:true}); });
      return zone;
    }
    loadSave() {
      try { this.state=E.sanitize(JSON.parse(localStorage.getItem(SAVE_KEY))); } catch { this.state=E.createState(); }
    }
    save() { try { localStorage.setItem(SAVE_KEY,JSON.stringify(this.state)); } catch { /* Safari privado */ } }
    changeBet(direction) {
      if(this.isSpinning)return;
      const bets=[1,2,5,10],idx=bets.indexOf(this.state.bet);
      this.state=E.setBet(this.state,bets[Math.max(0,Math.min(bets.length-1,idx+direction))]);
      this.updateHUD();this.save();this.tone(450,0.06);
    }
    updateHUD() {
      this.balanceText.setText(fmt(this.state.balance));
      this.betText.setText(fmt(this.state.bet));
      this.winText.setText("PRÉMIO " + fmt(this.state.lastWin));
      this.freeText.setText(this.state.freeSpins>0?"RODADAS GRÁTIS: "+this.state.freeSpins:"25 LINHAS · CRÉDITOS");
      this.progressText.setText(this.state.pumpkins+" / 10");
      this.progressBg.clear().fillStyle(0xf49d35,0.92)
        .fillRoundedRect(346,278,Math.max(4,180*this.state.pumpkins/10),24,10);
    }
    highlight(hits) {
      this.highlights.clear();
      const cells=new Set();
      for(const hit of hits) for(const cell of hit.cells) cells.add(cell[0]+","+cell[1]);
      if(!cells.size)return;
      this.highlights.lineStyle(5,0xffcf62,0.95);
      for(const key of cells) {
        const [col,row]=key.split(",").map(Number);
        this.highlights.strokeRoundedRect(X+col*CW+6,Y+row*CH+7,CW-12,CH-14,15);
      }
    }
    startSpin() {
      if(this.isSpinning)return;
      const outcome=E.spin(this.state);
      if(!outcome.ok) {
        this.notice.setText("SALDO INSUFICIENTE");
        this.tone(180,0.19);
        return;
      }
      this.isSpinning=true;
      this.notice.setText("A NOITE ESTÁ A GIRAR...");
      this.highlight([]);
      this.tone(290,0.12);
      let completed=0;
      for(let col=0;col<5;col++){
        let ticks=0, target=13+col*5;
        const event=this.time.addEvent({delay:60,repeat:target-1,callback:()=>{
          ticks++;
          for(let row=0;row<3;row++){
            const icon=E.SYMBOLS[Math.floor(Math.random()*E.SYMBOLS.length)].icon;
            this.slotTexts[col][row].setText(icon).setAlpha(0.75);
          }
          if(ticks===target){
            for(let row=0;row<3;row++){
              const icon=E.SYMBOLS.find(s=>s.id===outcome.grid[col][row]).icon;
              const tile=this.slotTexts[col][row];
              tile.setText(icon).setAlpha(1);
              this.tweens.add({targets:tile,scale:{from:1.2,to:1},duration:240,ease:"Back.Out"});
            }
            this.tone(190+col*70,0.045);
            completed++;
            if(completed===5) this.time.delayedCall(220,()=>this.finishSpin(outcome));
          }
        }});
      }
    }
    finishSpin(outcome) {
      this.state=outcome.state;
      this.save();
      this.updateHUD();
      this.highlight(outcome.result.hits);
      if(outcome.earnedSpins>0){
        this.notice.setText("🔮  +10 RODADAS GRÁTIS!");
        this.celebrate();
      } else if(outcome.pumpkinBonuses>0){
        this.notice.setText("🎃  ABÓBORAS! +"+outcome.pumpkinBonuses*5+" GIROS");
        this.celebrate();
      } else if(outcome.result.total>0){
        this.notice.setText("GANHASTE "+fmt(outcome.result.total)+" CRÉDITOS!");
        this.tone(620,0.16);
      } else {
        this.notice.setText("TENTA A TUA SORTE");
      }
      this.isSpinning=false;
    }
    celebrate() {
      this.tone(740,0.19);
      this.time.delayedCall(170,()=>this.tone(980,0.23));
      this.cameras.main.flash(220,255,173,47,false);
    }
    tone(freq,duration) {
      if(this.muted)return;
      try {
        const C=window.AudioContext||window.webkitAudioContext;
        if(!C)return;
        if(!this.audioCtx)this.audioCtx=new C();
        if(this.audioCtx.state==="suspended")this.audioCtx.resume().catch(()=>{});
        const t=this.audioCtx.currentTime;
        const osc=this.audioCtx.createOscillator(),gain=this.audioCtx.createGain();
        osc.type="sine";osc.frequency.setValueAtTime(freq,t);
        gain.gain.setValueAtTime(0.0001,t);
        gain.gain.exponentialRampToValueAtTime(0.07,t+0.02);
        gain.gain.exponentialRampToValueAtTime(0.0001,t+duration);
        osc.connect(gain);gain.connect(this.audioCtx.destination);
        osc.start(t);osc.stop(t+duration+0.02);
      } catch { /* áudio opcional */ }
    }
  }
  new Phaser.Game({
    type:Phaser.AUTO, parent:"game", width:W, height:H, backgroundColor:"#10091d",
    render:{antialias:true,pixelArt:false},
    scale:{mode:Phaser.Scale.FIT,autoCenter:Phaser.Scale.CENTER_BOTH},
    scene:[Halloween]
  });
})();
