/* Halloween — apresentação estilo clássico, ícones originais, controles táteis Phaser. */
(function(){
"use strict";
const E=globalThis.HalloweenEngine, ART=globalThis.HalloweenArt;
const status=document.getElementById("status");
if(!E||!ART||!globalThis.Phaser){status.textContent="Erro ao carregar o jogo";return;}
const W=720,H=1280,X=66,Y=334,BW=588,BH=510,CW=BW/5,CH=BH/3;
const saveKey="halloween-classico-v02";
const fmt=n=>Number(n).toLocaleString("pt-PT",{minimumFractionDigits:2,maximumFractionDigits:2});
function txt(s,x,y,value,size=28,color="#fff6d9",extra={}) {
  return s.add.text(x,y,value,{fontFamily:"Georgia,serif",fontSize:size+"px",
    fontStyle:"bold",color,align:"center",stroke:"#1a1324",strokeThickness:3,...extra}).setOrigin(.5);
}
class Classica extends Phaser.Scene{
 constructor(){super("classica");this.state=E.createState();this.spinning=false;this.auto=false;this.silence=false;this.held=new Set();this.previous=null;this.canHold=false;this.audio=null;}
 preload(){
   for(const [id,svg] of Object.entries(ART))
     this.load.svg("h-"+id,"data:image/svg+xml;charset=utf-8,"+encodeURIComponent(svg),{width:156,height:174});
 }
 create(){
   this.restore();
   this.background();
   this.hud();
   this.grid();
   this.bottom();
   this.shortcuts();
   this.renderState();
   status.textContent="";
 }
 background(){
   const g=this.add.graphics();
   g.fillGradientStyle(0x0a182b,0x152d4b,0x48103f,0x681449,1).fillRect(0,0,W,H);
   const moon=this.add.graphics();
   moon.fillStyle(0xf7cb83,.13).fillCircle(575,155,125);
   moon.fillStyle(0xf0d4ad,.12).fillCircle(575,155,74);
   const stars=this.add.graphics();
   for(let i=0;i<60;i++){
     stars.fillStyle(0xfff1c2,i%3===0?.85:.25).fillCircle((i*79+37)%W,(i*137+19)%920,i%6===0?2:1);
   }
   const roof=this.add.graphics().fillStyle(0x100e29,.6);
   roof.fillTriangle(0,315,128,207,264,315).fillRect(42,279,168,80);
   roof.fillTriangle(420,315,565,215,720,315).fillRect(472,271,180,80);
   this.add.text(360,182,"Halloween",{
       fontFamily:"Georgia,serif",fontSize:"88px",fontStyle:"italic bold",color:"#ec9c44",
       stroke:"#381b29",strokeThickness:10,shadow:{color:"#ff8a2b",blur:12}
     }).setOrigin(.5);
   txt(this,360,240,"III",50,"#ffd277",{stroke:"#641b2d",strokeThickness:8});
 }
 hud(){
   const g=this.add.graphics();
   g.fillStyle(0x11122b,.94).fillRoundedRect(8,8,704,102,16);
   g.lineStyle(3,0xa47d47,1).strokeRoundedRect(8,8,704,102,16);
   for(const x of [104,355,606]){
     g.fillStyle(0x650d19,1).fillRoundedRect(x-88,37,176,57,12);
     g.lineStyle(4,0xd7a253).strokeRoundedRect(x-88,37,176,57,12);
   }
   txt(this,104,25,"CRÉDITO",22,"#ffdf84");
   txt(this,355,25,"PRÉMIO",22,"#ffdf84");
   txt(this,606,25,"APOSTA",22,"#ffdf84");
   this.credit=txt(this,104,65,"",32,"#fff8e2");
   this.prize=txt(this,355,65,"",32,"#fff8e2");
   this.stake=txt(this,606,65,"",32,"#fff8e2");
 }
 grid(){
   const frame=this.add.graphics();
   frame.fillStyle(0x071329,.95).fillRoundedRect(X-9,Y-9,BW+18,BH+18,10);
   frame.lineStyle(7,0xdb9d55,1).strokeRoundedRect(X-8,Y-8,BW+16,BH+16,9);
   frame.fillStyle(0xe9edf1,1).fillRect(X,Y,BW,BH);
   this.symbols=Array.from({length:5},()=>[]);
   for(let col=0;col<5;col++) for(let row=0;row<3;row++){
     const sx=X+CW*col,sy=Y+CH*row;
     frame.fillGradientStyle(0xf5f9fa,0xdce7ee,0xf3f6f9,0xcbd9e0,1).fillRect(sx+2,sy+2,CW-4,CH-4);
     const id=E.SYMBOLS[(col+row*3)%E.SYMBOLS.length].id;
     this.symbols[col][row]=this.add.image(sx+CW/2,sy+CH/2,"h-"+id)
       .setDisplaySize(CW-12,CH-14);
   }
   const lines=this.add.graphics().lineStyle(4,0x0e1324,1);
   for(let c=1;c<5;c++)lines.lineBetween(X+c*CW,Y,X+c*CW,Y+BH);
   for(let r=1;r<3;r++)lines.lineBetween(X,Y+r*CH,X+BW,Y+r*CH);
   this.winGlow=this.add.graphics();
   this.lineMarkers=[];
   for(let i=0;i<20;i++){
     const left=i<10,rank=left?i:i-10;
     const x=left?32:688,y=Y+19+rank*49;
     const palette=[0xf4c43a,0xdd4d5c,0x78bf55,0x5cc1de,0xd48edb];
     const circ=this.add.graphics().fillStyle(0x17182b,1).fillCircle(x,y,19)
       .lineStyle(3,palette[i%5]).strokeCircle(x,y,19);
     const t=txt(this,x,y,String(i+1),19,"#f9e1c7",{strokeThickness:0});
     this.lineMarkers.push({circ,t});
   }
   this.locks=[];
   for(let c=0;c<5;c++){
     const x=X+CW*(c+.5);
     const hit=this.add.zone(x,Y+BH/2,CW, BH).setInteractive({useHandCursor:true});
     hit.on("pointerdown",()=>this.toggleHold(c));
     const lock=txt(this,x,Y+BH-22,"",18,"#ffdf6a",{backgroundColor:"#4c0a23",padding:{x:3,y:3}});
     lock.setVisible(false);
     this.locks.push(lock);
   }
 }
 bottom(){
   txt(this,360,886,"25 LINHAS • ACUMULADOS",26,"#ffcb6b");
   const g=this.add.graphics();
   for(const [x,w] of [[172,320],[548,320]]){
     g.fillStyle(0x350e29,.96).fillRoundedRect(x-w/2,930,w,105,8);
     g.lineStyle(4,0xe6a447).strokeRoundedRect(x-w/2,930,w,105,8);
   }
   txt(this,172,953,"ACUMULADO 1",22,"#ffd177");
   txt(this,548,953,"ACUMULADO 2",22,"#ffd177");
   this.jack1=txt(this,172,996,"",39,"#fff2a7");
   this.jack2=txt(this,548,996,"",39,"#fff2a7");
   this.pumpkins=txt(this,360,1053,"🎃 🎃 🎃 🎃 🎃",25,"#ffbc65");
   this.message=txt(this,360,1089,"CARREGA EM JOGAR",26,"#ffeaca");
   this.roundButton(367,1192,70,"JOGAR",30,()=>this.spin(),true);
   this.roundButton(90,1192,48,"LINHAS",21,()=>this.changeLines());
   this.roundButton(208,1192,48,"APOSTA",19,()=>this.changeBet());
   this.roundButton(520,1192,48,"AUTO",21,()=>this.toggleAuto());
   this.roundButton(634,1192,48,"SOM",21,()=>this.toggleSound());
   this.autoText=txt(this,520,1252,"",18,"#fbdeb4");
 }
 roundButton(x,y,r,caption,size,callback,main=false){
   const g=this.add.graphics();
   g.fillStyle(0x130b1c,.84).fillCircle(x,y+5,r+4);
   g.fillStyle(main?0xb82b1d:0x8b1324,1).fillCircle(x,y,r);
   g.lineStyle(5,main?0xffc866:0xeebc94).strokeCircle(x,y,r-3);
   txt(this,x,y,caption,size,"#ffe5c7",{stroke:"#651f19",strokeThickness:3});
   this.add.zone(x,y,2*r,2*r).setInteractive({useHandCursor:true}).on("pointerdown",callback);
 }
 shortcuts(){
   this.input.keyboard?.on("keydown-SPACE",()=>this.spin());
   this.input.keyboard?.on("keydown-ARROWUP",()=>this.changeBet());
 }
 restore(){
   try {this.state=E.sanitize(JSON.parse(localStorage.getItem(saveKey)));}catch{this.state=E.createState();}
   // valores progressivos são apenas pontos e não representam qualquer obrigação de pagamento
   this.state.jackpots=this.state.jackpots||[350,750];
   this.state.lines=this.state.lines||25;
 }
 store(){
   try{localStorage.setItem(saveKey,JSON.stringify(this.state));}catch{/* Modo privado */}
 }
 renderState(){
   this.credit.setText(fmt(this.state.balance));
   this.prize.setText(fmt(this.state.lastWin));
   this.stake.setText(fmt(this.state.bet));
   this.jack1.setText(fmt(this.state.jackpots?.[0]||350));
   this.jack2.setText(fmt(this.state.jackpots?.[1]||750));
   this.pumpkins.setText("🎃 ".repeat(Math.max(1,Math.min(5,Math.ceil((this.state.pumpkins||0)/2)))).trim());
   if(this.state.freeSpins>0)this.message.setText("GIROS GRÁTIS "+this.state.freeSpins);
   this.autoText.setText(this.auto?"ATIVO":"");
 }
 changeBet(){
   if(this.spinning)return;
   const options=[1,2,5,10],i=options.indexOf(this.state.bet);
   this.state=E.setBet(this.state,options[(i+1)%options.length]);
   this.renderState();this.store();this.tone(480,.06);
 }
 changeLines(){
   if(this.spinning)return;
   const options=[20,25,30],i=options.indexOf(this.state.lines||25);
   this.state.lines=options[(i+1)%3];
   this.message.setText(this.state.lines+" LINHAS SELECIONADAS");
   this.store();this.tone(440,.06);
 }
 toggleAuto(){
   this.auto=!this.auto;
   this.autoText.setText(this.auto?"ATIVO":"");
   if(this.auto&&!this.spinning)this.spin();
 }
 toggleSound(){this.silence=!this.silence;this.message.setText(this.silence?"SOM DESLIGADO":"SOM LIGADO");}
 toggleHold(col){
   if(this.spinning||!this.canHold||!this.previous)return;
   if(this.held.has(col))this.held.delete(col);else this.held.add(col);
   this.locks[col].setVisible(this.held.has(col)).setText("TRAVADO");
   this.message.setText(this.held.size+" TAMBORES TRAVADOS");
   this.tone(560,.05);
 }
 spin(){
   if(this.spinning)return;
   const outcome=E.spin(this.state,Math.random,this.previous,this.held);
   if(!outcome.ok){
     this.message.setText("CRÉDITOS INSUFICIENTES");this.auto=false;
     this.autoText.setText("");return;
   }
   this.spinning=true;this.canHold=false;this.highlight([]);
   this.message.setText(this.held.size?"A GIRAR TAMBORES LIVRES...":"A GIRAR...");
   this.tone(320,.09);
   let count=0;
   for(let c=0;c<5;c++){
     const locked=outcome.heldReels?.includes(c);
     if(locked){count++;continue;}
     const delay=55,steps=13+5*c;
     let t=0;
     this.time.addEvent({delay,repeat:steps-1,callback:()=>{
       t++;
       for(let row=0;row<3;row++){
         const el=this.symbols[c][row];
         if(t===steps){
           el.setTexture("h-"+outcome.grid[c][row]).setAlpha(1);
           this.tweens.add({targets:el,scaleX:{from:.74,to:(CW-12)/156},scaleY:{from:.9,to:(CH-14)/174},duration:190});
         } else {
           const id=E.SYMBOLS[Math.floor(Math.random()*E.SYMBOLS.length)].id;
           el.setTexture("h-"+id).setAlpha(.72);
         }
       }
       if(t===steps){this.tone(210+c*55,.05);count++;if(count===5)this.finish(outcome);}
     }});
   }
   if(count===5)this.time.delayedCall(120,()=>this.finish(outcome));
 }
 finish(result){
   if(!this.spinning)return;
   this.spinning=false;this.state=result.state;
   this.state.lines=this.state.lines||25;
   this.state.jackpots=this.state.jackpots||[350,750];
   this.state.jackpots=this.state.jackpots.map((v,i)=>Math.round((v+this.state.bet*(i===0?.01:.017))*100)/100);
   this.previous=result.grid;
   this.held.clear();for(const label of this.locks)label.setVisible(false);
   this.canHold=result.result.total===0&&!result.free;
   this.highlight(result.result.hits);
   if(result.earnedSpins){this.message.setText("+10 GIROS GRÁTIS!");this.flash();}
   else if(result.pumpkinBonuses){this.message.setText("BÓNUS DAS ABÓBORAS!");this.flash();}
   else if(result.result.total>0){this.message.setText("PRÉMIO "+fmt(result.result.total));this.tone(740,.15);}
   else{this.message.setText("SEM PRÉMIO — TOCA NUM TAMBOR PARA TRAVAR");}
   this.renderState();this.store();
   if(this.auto)this.time.delayedCall(620,()=>{if(this.auto)this.spin();});
 }
 highlight(hits){
   this.winGlow.clear();
   const set=new Set();
   for(const hit of hits)for(const [c,r] of hit.cells)set.add(c+","+r);
   this.winGlow.lineStyle(5,0xffc332,1);
   for(const key of set){const [c,r]=key.split(",").map(Number);
     this.winGlow.strokeRoundedRect(X+c*CW+5,Y+r*CH+5,CW-10,CH-10,8);
   }
 }
 flash(){this.cameras.main.flash(230,255,212,127,false);this.tone(900,.19);}
 tone(freq,len){
   if(this.silence)return;
   try{const Audio=window.AudioContext||window.webkitAudioContext;if(!Audio)return;
     if(!this.audio)this.audio=new Audio();
     if(this.audio.state==="suspended")this.audio.resume().catch(()=>{});
     const t=this.audio.currentTime,osc=this.audio.createOscillator(),gain=this.audio.createGain();
     osc.type="square";osc.frequency.setValueAtTime(freq,t);
     gain.gain.setValueAtTime(.0001,t);gain.gain.exponentialRampToValueAtTime(.025,t+.018);
     gain.gain.exponentialRampToValueAtTime(.0001,t+len);
     osc.connect(gain);gain.connect(this.audio.destination);osc.start(t);osc.stop(t+len+.02);
   }catch{/* Áudio opcional */}
 }
}
new Phaser.Game({type:Phaser.AUTO,parent:"game",width:W,height:H,backgroundColor:"#160d28",
  scale:{mode:Phaser.Scale.FIT,autoCenter:Phaser.Scale.CENTER_BOTH},
  render:{antialias:true},scene:[Classica]});
})();
