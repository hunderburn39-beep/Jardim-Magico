/* Interface Phaser 3 original, sem assets de terceiros nem ligacao RGS. */
(function () {
  "use strict";
  const E = window.SugarLocalEngine;
  const boardEl = document.getElementById("board");
  const els = Object.fromEntries(
    ["balance","bet","lastwin","round-win","message","mode","fs-count",
     "spin","less","more","auto","turbo","reset","info","audio","overlay",
     "close-info","close-info-bottom","paytable"].map(id => [id, document.getElementById(id)])
  );
  if (!E || typeof window.Phaser === "undefined") {
    els.message.textContent = "Falha ao carregar o motor. Atualiza a página.";
    return;
  }
  const BETS = [20,40,60,80,100,200,300,500,1000];
  const SAVE_KEY = "sugar1000-offline-v2";
  const names = ["Laranja", "Rebuçado azul", "Caramelo verde",
    "Estrela dourada", "Rosa", "Uva violeta", "Cristal turquesa"];
  const colors = [0xff8a27,0x48b5fa,0x75cd48,0xf9c838,0xff529e,0xa077f9,0x45d9cb,0xfcd05d];
  const formatter = new Intl.NumberFormat("pt-PT",{minimumFractionDigits:2,maximumFractionDigits:2});
  const money = cents => formatter.format(Math.max(0,Math.round(cents))/100);
  const pause = ms => new Promise(resolve => setTimeout(resolve,ms));

  let credits = 200000, betIndex = 0, lastWin = 0, currentWin = 0;
  let running = false, autoLeft = 0, turbo = false, sound = false, scene = null;
  let freeLeft = 0, autoRequested = false;
  try {
    const saved = JSON.parse(localStorage.getItem(SAVE_KEY) || "null");
    if (saved && Number.isSafeInteger(saved.credits) &&
        saved.credits >= 0 && saved.credits < 100000000 &&
        Number.isInteger(saved.betIndex) && saved.betIndex >= 0 && saved.betIndex < BETS.length) {
      credits = saved.credits; betIndex = saved.betIndex;
    }
  } catch (_err) { /* armazenamento pode estar desativado */ }
  function persist() {
    try { localStorage.setItem(SAVE_KEY,JSON.stringify({credits,betIndex})); } catch (_err) {}
  }
  function status(message) { els.message.textContent = message; }
  function update() {
    els.balance.textContent = money(credits);
    els.bet.textContent = money(BETS[betIndex]);
    els.lastwin.textContent = money(lastWin);
    els["round-win"].textContent = money(currentWin);
    els["fs-count"].textContent = freeLeft ? freeLeft + " GIROS GRÁTIS" : "";
    els.mode.textContent = freeLeft ? "FREE SPINS" : "JOGO BASE";
    els.spin.disabled = running || !scene || credits < BETS[betIndex];
    els.less.disabled = running || betIndex === 0;
    els.more.disabled = running || betIndex === BETS.length - 1;
    els.auto.textContent = running && autoRequested ? "PARAR APÓS GIRO" : "10 GIROS AUTO";
    els.auto.classList.toggle("active", autoRequested);
    els.turbo.classList.toggle("active",turbo);
    els.turbo.setAttribute("aria-pressed",String(turbo));
    els.audio.textContent = sound ? "🔊" : "♫";
    persist();
  }
  let audioCtx;
  function tone(freq=420,duration=.07,type="sine",vol=.025) {
    if (!sound) return;
    try {
      const AC = window.AudioContext || window.webkitAudioContext;
      if (!AC) return;
      audioCtx ||= new AC();
      if (audioCtx.state === "suspended") audioCtx.resume();
      const o = audioCtx.createOscillator();
      const gain = audioCtx.createGain();
      o.frequency.setValueAtTime(freq,audioCtx.currentTime);
      o.type = type;
      gain.gain.setValueAtTime(vol,audioCtx.currentTime);
      gain.gain.exponentialRampToValueAtTime(.0001,audioCtx.currentTime + duration);
      o.connect(gain); gain.connect(audioCtx.destination);
      o.start(); o.stop(audioCtx.currentTime + duration);
    } catch (_err) {}
  }

  function makeTexture(scene,id) {
    const g = scene.add.graphics(), c = colors[id];
    g.fillStyle(0x1d074f,.28).fillRoundedRect(13,17,72,72,24);
    g.lineStyle(3,0xffffff,.45);
    if (id === 0) {
      g.fillStyle(c).fillRoundedRect(18,17,62,66,23);
      g.fillCircle(27,23,15); g.fillCircle(71,23,15);
      g.fillStyle(0xffc077).fillRoundedRect(23,20,52,55,21);
      g.fillStyle(c).fillRoundedRect(23,29,52,47,17);
      g.fillStyle(0xffffff,.88).fillCircle(35,40,5);g.fillCircle(62,40,5);
      g.fillStyle(0x5d2356).fillCircle(36,41,2);g.fillCircle(61,41,2);g.fillCircle(49,56,4);
    } else if (id === 1) {
      g.fillStyle(0x168fd6).fillCircle(49,49,38);
      g.fillStyle(0x8fe5ff).fillCircle(47,45,31);
      g.lineStyle(8,0x2b7ae6,1);g.strokeCircle(49,49,26);
      g.lineStyle(6,0xffffff,.9);g.strokeCircle(49,49,15);
      g.fillStyle(0x2d8ffa).fillCircle(49,49,8);
    } else if (id === 2) {
      g.fillStyle(0x3aa952).fillTriangle(15,50,32,21,32,77);
      g.fillStyle(0x3aa952).fillTriangle(81,50,64,21,64,77);
      g.fillStyle(0xb8ff7d).fillRoundedRect(26,20,44,58,12);
      g.fillStyle(0x58bb4b).fillRoundedRect(31,25,34,48,10);
      g.lineStyle(5,0xe2ffae,.8);g.lineBetween(37,31,58,64);
    } else if (id === 3) {
      const points = [];
      for(let i=0;i<10;i++){
        const angle = -Math.PI/2 + i*Math.PI/5, r=i%2?19:40;
        points.push(new Phaser.Geom.Point(49+Math.cos(angle)*r,49+Math.sin(angle)*r));
      }
      g.fillStyle(0xe59d17).fillPoints(points,true);
      g.fillStyle(c).fillPoints(points,true);
      g.fillStyle(0xfff4a0).fillCircle(40,35,7);
      g.lineStyle(4,0xffffff,.65);g.strokePoints(points,true);
    } else if (id === 4) {
      g.fillStyle(0xf3428e).fillRoundedRect(15,15,68,68,28);
      g.fillStyle(0xffadd7).fillRoundedRect(20,21,58,56,24);
      g.fillStyle(c).fillRoundedRect(24,23,49,50,21);
      g.lineStyle(9,0xffe5f4,.9);
      g.lineBetween(30,31,59,68);g.lineBetween(44,26,71,54);
    } else if (id === 5) {
      g.fillStyle(0x6330d9).fillCircle(49,52,39);
      g.fillStyle(c).fillCircle(46,46,34);
      g.fillStyle(0xd8b7ff,.85).fillEllipse(36,31,25,13);
      g.lineStyle(4,0xe8ccff,.75);g.strokeCircle(48,49,31);
    } else if (id === 6) {
      g.fillStyle(0x067b9a).fillPoints([
        new Phaser.Geom.Point(48,8),new Phaser.Geom.Point(83,37),
        new Phaser.Geom.Point(72,76),new Phaser.Geom.Point(48,90),
        new Phaser.Geom.Point(23,76),new Phaser.Geom.Point(11,37)],true);
      g.fillStyle(c).fillTriangle(48,10,83,37,48,59);
      g.fillStyle(0x9dfef1).fillTriangle(15,37,48,59,48,86);
      g.fillStyle(0x25aaaf).fillTriangle(83,37,72,76,48,86);
      g.lineStyle(3,0xb5fff5,.8);
      g.lineBetween(48,10,48,87);g.lineBetween(15,37,82,37);
    } else {
      g.fillStyle(0xed4e9e).fillTriangle(15,82,33,57,42,80);
      g.fillStyle(0xed4e9e).fillTriangle(80,82,63,57,55,80);
      g.fillStyle(0xffe291).fillRoundedRect(33,18,30,58,15);
      g.fillStyle(0xffa433).fillTriangle(34,29,47,5,62,29);
      g.fillStyle(0xfefae5).fillEllipse(48,45,22,25);
      g.fillStyle(0x4b178b).fillCircle(48,45,7);
      g.fillStyle(0xffffff,.8).fillCircle(39,30,4);
    }
    g.fillStyle(0xffffff,.32).fillEllipse(31,25,21,10);
    g.generateTexture("sugar_" + id,96,96);g.destroy();
  }

  class SugarScene extends Phaser.Scene {
    constructor(){super({key:"SugarScene"});this.pieces=[];}
    create(){
      const back=this.add.graphics();
      back.fillStyle(0x3b0d82,.9).fillRoundedRect(0,0,700,700,16);
      for(let r=0;r<7;r++)for(let c=0;c<7;c++){
        const light=(r+c)%2===0;
        back.fillStyle(light?0x8040c4:0x6f32b0,.68);
        back.fillRoundedRect(c*100+3,r*100+3,94,94,11);
        back.lineStyle(1.5,0xe8a8ff,.23);
        back.strokeRoundedRect(c*100+3,r*100+3,94,94,11);
      }
      for(let i=0;i<8;i++)makeTexture(this,i);
      scene=this;
      this.show({kind:"reveal",board:Array.from({length:7},()=>
        Array.from({length:7},()=>Math.floor(Math.random()*7))),spots:E.blank()},true);
      status("Junta 5 ou mais doces da mesma cor!");
      update();
    }
    show(frame,fast=false){
      for(const obj of this.pieces) obj.destroy();
      this.pieces=[];
      for(let r=0;r<7;r++)for(let c=0;c<7;c++){
        const x=c*100+50,y=r*100+50, id=frame.board[r][c];
        const item=this.add.container(x,y);
        const img=this.add.image(0,0,"sugar_"+id).setDisplaySize(88,88);
        item.add(img);
        if(id===7){
          const txt=this.add.text(0,20,"FS",{fontFamily:"Arial",fontSize:"18px",fontStyle:"bold",
            color:"#fff",stroke:"#8d2159",strokeThickness:5}).setOrigin(.5);
          item.add(txt);
        }
        const v=frame.spots?.[r]?.[c] || 0;
        if(v>=2) {
          const tag=this.add.graphics();
          tag.fillStyle(0x3e0d75,.92).fillRoundedRect(7,19,66,30,11);
          tag.lineStyle(3,0xffe680,1).strokeRoundedRect(7,19,66,30,11);
          item.add(tag);
          item.add(this.add.text(40,34,"×"+v,{
            fontFamily:"Arial",fontSize:v>=100?"19px":"24px",fontStyle:"bold",
            color:"#fff7b2",stroke:"#7f2678",strokeThickness:3
          }).setOrigin(.5));
        }
        if(frame.kind!=="win"){
          item.setY(y-(fast?30:180+25*r));
          item.setAlpha(fast?.9:0);
          this.tweens.add({targets:item,y,alpha:1,duration:fast?110:350+32*r+22*c,
            ease:"Back.easeOut"});
        }
        if(frame.kind==="win"&&frame.positions?.some(p=>p[0]===r&&p[1]===c)){
          this.tweens.add({targets:item,scale:1.16,alpha:.68,yoyo:true,repeat:1,
            duration:fast?60:135,ease:"Sine.easeInOut"});
        }
        this.pieces.push(item);
      }
    }
  }

  const config={
    type:Phaser.AUTO,
    parent:"board",
    width:700,height:700,
    transparent:true,
    antialias:true,
    powerPreference:"high-performance",
    render:{pixelArt:false,antialias:true},
    scale:{mode:Phaser.Scale.FIT,autoCenter:Phaser.Scale.CENTER_BOTH,
      width:700,height:700},
    scene:[SugarScene],
    audio:{noAudio:true}
  };
  try { new Phaser.Game(config); } catch (err) {
    status("Não foi possível iniciar a grelha gráfica.");
    console.error(err);
  }

  function paytable() {
    E.PAY.forEach((line,i)=>{
      const el=document.createElement("div");
      el.className="paytable-row";
      const strong=document.createElement("strong");
      strong.textContent=names[i];
      const values=document.createElement("span");
      values.textContent=[line[0],line[5],line[10]].join("×  /  ")+"×";
      el.append(strong,values);els.paytable.append(el);
    });
  }
  paytable();
  function openInfo() { els.overlay.hidden=false; els["close-info"].focus(); }
  function closeInfo() { els.overlay.hidden=true; els.info.focus(); }
  els.info.addEventListener("click",openInfo);
  els["close-info"].addEventListener("click",closeInfo);
  els["close-info-bottom"].addEventListener("click",closeInfo);
  els.overlay.addEventListener("click",e=>{if(e.target===els.overlay)closeInfo();});
  document.addEventListener("keydown",e=>{
    if(e.key==="Escape"&&!els.overlay.hidden)closeInfo();
    if(e.code==="Space"&&els.overlay.hidden&&document.activeElement===document.body){
      e.preventDefault();if(!running)void runBatch();
    }
  });
  els.less.addEventListener("click",()=>{if(!running&&betIndex>0){betIndex--;tone(420);update();}});
  els.more.addEventListener("click",()=>{if(!running&&betIndex<BETS.length-1){betIndex++;tone(620);update();}});
  els.turbo.addEventListener("click",()=>{turbo=!turbo;update();});
  els.audio.addEventListener("click",()=>{sound=!sound;tone(740);update();});
  els.reset.addEventListener("click",()=>{
    if(running)return;
    credits=200000;lastWin=0;currentWin=0;autoLeft=0;freeLeft=0;
    status("Saldo reiniciado: 2 000,00 créditos.");update();
  });
  els.auto.addEventListener("click",()=>{
    if(running){autoLeft=0;autoRequested=false;status("Os giros automáticos vão parar.");update();return;}
    autoLeft=10;autoRequested=true;void runBatch();
  });
  els.spin.addEventListener("click",()=>{if(!running){autoLeft=0;autoRequested=false;void runBatch();}});

  async function animateRound(result,kind){
    for(const frame of result.frames){
      if(!scene)break;
      scene.show(frame,turbo);
      if(frame.kind==="win"){
        tone(690,.08,"triangle",.028);
        status("GRUPO PREMIADO! +" + money(frame.amountCents));
        await pause(turbo?105:420);
      }else if(frame.kind==="fall"){
        tone(340,.035,"sine",.013);
        await pause(turbo?105:335);
      }else{
        tone(500,.055,"triangle",.02);
        await pause(turbo?130:470);
      }
    }
  }

  async function playCompleteSpin(){
    const bet=BETS[betIndex];
    if(credits<bet){status("Saldo insuficiente. Usa REPOR SALDO.");return false;}
    credits-=bet;currentWin=0;freeLeft=0;
    let total=0, remainingFs=0, freePlayed=0, spots=E.blank();
    const cap=bet*E.MAX_WIN_MULT;
    update();status("Boa sorte! Doces a cair...");
    let round=E.calculateRound({betCents:bet,capCents:cap});
    await animateRound(round,"base");
    total+=round.winCents;credits+=round.winCents;
    remainingFs+=round.freeAward;
    currentWin=total;freeLeft=remainingFs;update();
    if(remainingFs) {
      status(round.scatters+" FOGUETES! GANHASTE "+remainingFs+" FREE SPINS");
      tone(920,.2,"triangle",.04);
      await pause(turbo?230:1100);
    }
    while(remainingFs>0&&freePlayed<E.MAX_SESSION_SPINS&&total<cap){
      remainingFs--;freeLeft=remainingFs;freePlayed++;update();
      status("FREE SPIN "+freePlayed);
      round=E.calculateRound({betCents:bet,capCents:cap-total,spots});
      spots=round.spots;
      await animateRound(round,"free");
      total+=round.winCents;credits+=round.winCents;remainingFs+=round.freeAward;
      currentWin=total;freeLeft=remainingFs;update();
      if(round.freeAward){
        status("RETRIGGER! +"+round.freeAward+" FREE SPINS");
        tone(850,.16,"triangle",.027);
        await pause(turbo?120:650);
      }
    }
    if(freePlayed>=E.MAX_SESSION_SPINS&&remainingFs>0)
      status("Limite de 200 Free Spins atingido.");
    else if(total>=cap)status("PRÉMIO MÁXIMO: 25 000× A APOSTA!");
    else status(total>0?"PRÉMIO TOTAL: "+money(total)+" créditos":"Sem prémio. Tenta outra vez!");
    lastWin=total;currentWin=total;freeLeft=0;persist();update();
    if(total>0)tone(720,.18,"sine",.04);
    return true;
  }
  async function runBatch(){
    if(running||!scene)return;
    running=true;els.spin.disabled=true;
    try{
      do{
        const played=await playCompleteSpin();
        if(!played)break;
        if(!autoRequested)break;
        autoLeft--;
        if(autoLeft<=0)break;
        update();
        await pause(turbo?160:470);
      }while(autoLeft>0&&credits>=BETS[betIndex]);
    }catch(err){
      console.error(err);
      status("Ocorreu um erro no giro. Recarrega a página.");
    }finally{
      running=false;autoLeft=0;autoRequested=false;update();
    }
  }
  update();
})();
