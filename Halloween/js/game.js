/* Halloween — apresentação estilo clássico, ícones originais, controles táteis Phaser. */
(function(){
"use strict";
const E=globalThis.HalloweenEngine, ART=globalThis.HalloweenArt;
const status=document.getElementById("status");
if(!E||!ART||!globalThis.Phaser){status.textContent="Erro ao carregar o jogo";return;}
const W=720,H=1280,X=66,Y=334,BW=588,BH=510,CW=BW/5,CH=BH/3;
const saveKey="halloween-classico-v02"; // Guarda também sessões iniciadas na V0.2
const fmt=n=>Number(n).toLocaleString("pt-PT",{minimumFractionDigits:2,maximumFractionDigits:2});
function txt(s,x,y,value,size=28,color="#fff6d9",extra={}) {
  return s.add.text(x,y,value,{fontFamily:"Georgia,serif",fontSize:size+"px",
    fontStyle:"bold",color,align:"center",stroke:"#1a1324",strokeThickness:3,...extra}).setOrigin(.5);
}
class Classica extends Phaser.Scene{
 constructor(){super("classica");this.state=E.createState();this.spinning=false;this.auto=false;this.silence=false;this.held=new Set();this.previous=null;this.canHold=false;this.audio=null;this.potOpen=false;this.strawberryOpen=false;this.diceOpen=false;this.tableOpen=false;this.winTimer=null;this.winIndex=0;}
 preload(){
   for(const [id,svg] of Object.entries(ART)){
     // Phaser 3.90 descodifica data URI SVG com atob; a URL
     // percent-encoded anterior causava InvalidCharacterError.
     const binary=Array.from(new TextEncoder().encode(svg),b=>String.fromCharCode(b)).join("");
     this.load.svg("h-"+id,"data:image/svg+xml;base64,"+btoa(binary),{width:156,height:174});
   }
 }
 create(){
   this.restore();
   this.background();
   this.hud();
   this.grid();
   this.bottom();
   this.makePotBonus();
   this.makeStrawberryBonus();
   this.makeDiceBonus();
   this.makeWheelBonus();
   this.makePaytable();
   this.shortcuts();
   this.renderState();
   this.previous=this.state.lastGrid;
   this.canHold=Boolean(this.previous)&&this.state.lastWin===0&&
     this.state.freeSpins===0&&!this.state.pendingPot&&!this.state.pendingStrawberry&&!this.state.pendingDice&&!this.state.pendingWheel;
   if(new URLSearchParams(window.location.search).has("smoke")) globalThis.__HALLOWEEN_SCENE__=this;
   if(this.state.pendingDice)this.showDiceBonus();
   else if(this.state.pendingStrawberry)this.showStrawberryBonus();
   else if(this.state.pendingPot)this.showPotBonus();
   else if(this.state.pendingWheel)this.showWheelBonus();
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
   this.symbols=[];
   this.reelBands=[];
   for(let col=0;col<5;col++){
     const sx=X+CW*col;
     for(let row=0;row<3;row++){
       const sy=Y+CH*row;
       frame.fillGradientStyle(0xf5f9fa,0xdce7ee,0xf3f6f9,0xcbd9e0,1)
         .fillRect(sx+2,sy+2,CW-4,CH-4);
     }
     // Cada rolo tem cinco sprites: 1 fora da janela no topo, 3
     // visíveis e outro em baixo. A máscara recorta o movimento real.
     const clip=this.add.graphics().fillStyle(0xffffff,1).fillRect(sx,Y,CW,BH).setVisible(false);
     const mask=clip.createGeometryMask();
     const band=[];
     for(let index=0;index<5;index++){
       const id=(index>=1&&index<=3&&this.state.lastGrid?.[col]?.[index-1]) ||
         E.SYMBOLS[(col+index*3)%E.SYMBOLS.length].id;
       const sprite=this.add.image(sx+CW/2,Y+(index-.5)*CH,"h-"+id)
         .setDisplaySize(CW-12,CH-14).setMask(mask);
       band.push(sprite);
     }
     this.reelBands[col]=band;
     this.symbols[col]=band.slice(1,4);
   }
   const lines=this.add.graphics().lineStyle(4,0x0e1324,1);
   for(let c=1;c<5;c++)lines.lineBetween(X+c*CW,Y,X+c*CW,Y+BH);
   for(let r=1;r<3;r++)lines.lineBetween(X,Y+r*CH,X+BW,Y+r*CH);
   this.winGlow=this.add.graphics();
   this.winPath=this.add.graphics();
   this.winLabel=txt(this,360,299,"",21,"#ffe9a0");
   this.lineMarkers=[];
   for(let i=0;i<30;i++){
     const left=i<15,rank=left?i:i-15;
     const x=left?32:688,y=Y+17+rank*33;
     const palette=[0xf4c43a,0xdd4d5c,0x78bf55,0x5cc1de,0xd48edb];
     const circ=this.add.graphics().fillStyle(0x17182b,1).fillCircle(x,y,15)
       .lineStyle(3,palette[i%5]).strokeCircle(x,y,15);
     const t=txt(this,x,y,String(i+1),15,"#f9e1c7",{strokeThickness:0});
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
   this.linesText=txt(this,360,886,"",26,"#ffcb6b");
   const g=this.add.graphics();
   this.jackpotsText=[];
   for(let i=0;i<4;i++){
     const x=95+i*176,w=166;
     g.fillStyle(0x350e29,.96).fillRoundedRect(x-w/2,929,w,106,8);
     g.lineStyle(3,0xe6a447).strokeRoundedRect(x-w/2,929,w,106,8);
     txt(this,x,953,"ACUM. "+(i+1),20,"#ffd177");
     this.jackpotsText.push(txt(this,x,996,"",26,"#fff2a7"));
   }
   this.pumpkinMarkers=[];
   for(let i=0;i<5;i++){
     const pumpkin=this.add.image(228+i*66,1052,"h-pumpkin").setDisplaySize(47,56);
     this.pumpkinMarkers.push(pumpkin);
   }
   this.message=txt(this,360,1089,"CARREGA EM JOGAR",26,"#ffeaca");
   this.roundButton(367,1192,70,"JOGAR",30,()=>this.spin(),true);
   this.roundButton(90,1192,48,"LINHAS",21,()=>this.changeLines());
   this.roundButton(208,1192,48,"APOSTA",19,()=>this.changeBet());
   this.roundButton(520,1192,48,"AUTO",21,()=>this.toggleAuto());
   this.roundButton(634,1192,48,"SOM",21,()=>this.toggleSound());
   this.autoText=txt(this,520,1252,"",18,"#fbdeb4");
   const help=this.add.graphics();
   help.fillStyle(0x541b37,.96).fillCircle(671,272,28);
   help.lineStyle(3,0xfbd68b).strokeCircle(671,272,28);
   txt(this,671,272,"?",35,"#fff0cb");
   this.add.zone(671,272,65,65).setInteractive({useHandCursor:true})
     .on("pointerdown",()=>this.togglePaytable());
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
   this.state.jackpots=this.state.jackpots||[350,750,1500,3000];
   this.state.lines=this.state.lines||30;
 }
 store(){
   try{localStorage.setItem(saveKey,JSON.stringify(this.state));}catch{/* Modo privado */}
 }
 renderState(){
   this.credit.setText(fmt(this.state.balance));
   this.prize.setText(fmt(this.state.lastWin));
   this.stake.setText(fmt(this.state.bet));
   for(let i=0;i<4;i++)
     this.jackpotsText[i].setText(fmt(this.state.jackpots?.[i]??[350,750,1500,3000][i]));
   for(let i=0;i<this.pumpkinMarkers.length;i++)
      this.pumpkinMarkers[i].setAlpha(i<this.state.pumpkins?1:.3);
   this.autoText.setText(this.auto?"ATIVO":"");
   this.linesText.setText(this.state.freeSpins>0
     ? this.state.lines+" LINHAS • GIROS GRÁTIS: "+this.state.freeSpins
     : this.state.lines+" LINHAS • ACUMULADOS");
   for(let i=0;i<this.lineMarkers.length;i++){
      const active=i<this.state.lines;
      this.lineMarkers[i].circ.setVisible(active);
      this.lineMarkers[i].t.setVisible(active);
   }
 }
 changeBet(){
   if(this.spinning||this.potOpen||this.strawberryOpen||this.diceOpen||this.wheelOpen||this.tableOpen||this.state.freeSpins>0)return;
   this.clearHolds();
   const options=[1,2,5,10],i=options.indexOf(this.state.bet);
   this.state=E.setBet(this.state,options[(i+1)%options.length]);
   this.renderState();this.store();this.tone(480,.06);
 }
 changeLines(){
   if(this.spinning||this.potOpen||this.strawberryOpen||this.diceOpen||this.wheelOpen||this.tableOpen||this.state.freeSpins>0)return;
   this.clearHolds();
   const options=[20,25,30],i=options.indexOf(this.state.lines||30);
   this.state.lines=options[(i+1)%3];
   this.renderState();
   this.message.setText(this.state.lines+" LINHAS SELECIONADAS");
   this.store();this.tone(440,.06);
 }
 toggleAuto(){
   if(this.potOpen||this.strawberryOpen||this.diceOpen||this.wheelOpen||this.tableOpen)return;
   this.auto=!this.auto;
   this.autoText.setText(this.auto?"ATIVO":"");
   if(this.auto&&!this.spinning)this.spin();
 }
 toggleSound(){this.silence=!this.silence;this.message.setText(this.silence?"SOM DESLIGADO":"SOM LIGADO");}
 clearHolds(){
   this.held.clear();this.canHold=false;
   for(const label of this.locks)label.setVisible(false);
 }
 toggleHold(col){
   if(this.spinning||this.potOpen||this.strawberryOpen||this.diceOpen||this.wheelOpen||this.tableOpen||!this.canHold||!this.previous)return;
   if(this.held.has(col))this.held.delete(col);
   else if(this.held.size<4)this.held.add(col);
   else { this.message.setText("PELO MENOS UM TAMBOR TEM DE RODAR"); return; }
   this.locks[col].setVisible(this.held.has(col)).setText("TRAVADO");
   this.message.setText(this.held.size+" TAMBORES TRAVADOS");
   this.tone(560,.05);
 }
 spin(){
   if(this.spinning||this.potOpen||this.strawberryOpen||this.diceOpen||this.wheelOpen||this.tableOpen)return;
   const outcome=E.spin(this.state,Math.random,this.previous,this.held);
   if(!outcome.ok){
     if(outcome.reason==="bonus-pendente"){
       if(this.state.pendingDice)this.showDiceBonus();
       else if(this.state.pendingStrawberry)this.showStrawberryBonus();
       else if(this.state.pendingPot)this.showPotBonus();
       else if(this.state.pendingWheel)this.showWheelBonus();
       return;
     }
     this.message.setText("CRÉDITOS INSUFICIENTES");this.auto=false;
     this.autoText.setText("");return;
   }
   // Guardar o resultado antes de iniciar a animação: sair do navegador
   // durante os rolos não devolve a aposta nem perde o bónus pendente.
   this.state=outcome.state;
   this.store();
   this.spinning=true;this.canHold=false;this.highlight([]);
   this.message.setText(this.held.size?"A GIRAR TAMBORES LIVRES...":"A GIRAR...");
   this.tone(320,.09);
   let count=0;
   const active=[0,1,2,3,4].filter(col=>!outcome.heldReels?.includes(col));
   if(!active.length){
     // Não se pode travar os cinco tambores: caso um estado antigo o
     // tente, termina de forma segura sem bloquear a interface.
     this.time.delayedCall(150,()=>this.finish(outcome));
     return;
   }
   for(const col of active) this.animateReel(col,outcome.grid[col],()=>{
     count++;
     if(count===active.length)this.time.delayedCall(180,()=>this.finish(outcome));
   });

 }
 animateReel(col,finalColumn,done){
   const band=this.reelBands[col];
   const maxSteps=12+col*4;
   let step=0;
   const tick=()=>{
     step++;
     // O tempo de um movimento aumenta ao aproximar-se da paragem,
     // simulando o atrito de um tambor físico.
     const portion=step/maxSteps;
     const duration=55+Math.round(90*portion*portion*portion);
     this.tweens.add({
       targets:band,y:"+="+CH,duration,ease:"Sine.InOut",
       onComplete:()=>{
         const keys=band.map(s=>s.texture.key);
         for(let i=4;i>=1;i--)band[i].setTexture(keys[i-1]);
         const candidate=E.SYMBOLS[Math.floor(Math.random()*E.SYMBOLS.length)].id;
         band[0].setTexture("h-"+candidate);
         for(let i=0;i<5;i++)band[i].y=Y+(i-.5)*CH;
         if(step>=maxSteps){
           for(let row=0;row<3;row++)
             band[row+1].setTexture("h-"+finalColumn[row]).setAlpha(1);
           const sx=(CW-12)/156,sy=(CH-14)/174;
           this.tweens.add({targets:this.symbols[col],scaleX:{from:sx*.94,to:sx},
             scaleY:{from:sy*.94,to:sy},duration:175,ease:"Back.Out"});
           this.tone(180+col*72,.075);
           done();
         }else tick();
       }
     });
   };
   tick();
 }
 finish(result){
   if(!this.spinning)return;
   this.spinning=false;this.state=result.state;
   this.state.lines=this.state.lines||30;
   this.state.jackpots=this.state.jackpots||[350,750,1500,3000];
   this.previous=result.grid;
   this.clearHolds();
   this.canHold=result.result.total===0&&!result.free&&!result.potTriggered&&!result.strawberryTriggered&&!result.diceTriggered&&!result.wheelTriggered;
   this.highlight(result.result.hits);
   if(result.earnedSpins){this.message.setText("+10 GIROS GRÁTIS!");this.flash();}
   else if(result.pumpkinBonuses){this.message.setText("BÓNUS DAS ABÓBORAS!");this.flash();}
   else if(result.result.total>0){this.message.setText("PRÉMIO "+fmt(result.result.total));this.tone(740,.15);}
   else{this.message.setText("SEM PRÉMIO — TOCA NUM TAMBOR PARA TRAVAR");}
   this.renderState();this.store();
   if(result.diceTriggered)this.showDiceBonus();
   else if(result.strawberryTriggered)this.showStrawberryBonus();
   else if(result.potTriggered)this.showPotBonus();
   else if(result.wheelTriggered)this.showWheelBonus();
   else if(this.auto) this.time.delayedCall(620,()=>{if(this.auto)this.spin();});
 }
 makePaytable(){
   this.tableOverlay=this.add.container(0,0).setDepth(130).setVisible(false);
   const veil=this.add.rectangle(W/2,H/2,W,H,0x070c1c,.97).setInteractive();
   this.tableOverlay.add(veil);
   const panel=this.add.graphics();
   panel.fillStyle(0x171a30,.98).fillRoundedRect(28,200,664,845,24);
   panel.lineStyle(5,0xe4a84a).strokeRoundedRect(28,200,664,845,24);
   this.tableOverlay.add(panel);
   this.tableOverlay.add(txt(this,360,257,"TABELA DA HALLOWEEN III",36,"#ffe5aa"));
   this.tableOverlay.add(txt(this,360,308,"Referência visual do APK de 30 linhas",23,"#eecb9c"));
   const columns=[{title:"SÍMBOLO",x:176},{title:"3",x:360},{title:"4",x:467},{title:"5",x:584}];
   for(const col of columns)
     this.tableOverlay.add(txt(this,col.x,376,col.title,22,"#ffdc8c"));
   const entries=[
     {id:"pumpkin",label:"HALLOWEEN",numbers:["2 000","2 000","10 000"]},
     {id:"wild",label:"MAGIC",numbers:["250","500","2 000"]},
     {id:"witch",label:"WITCH",numbers:["7","100","150"]},
     {id:"scatter",label:"CALDEIRÃO",numbers:["BÓNUS","BÓNUS","BÓNUS"]}
   ];
   for(let i=0;i<entries.length;i++){
     const entry=entries[i],y=452+i*105;
     const bg=this.add.graphics();
     bg.fillStyle(i%2?0x241c38:0x36203b,.8).fillRoundedRect(50,y-37,617,79,10);
     this.tableOverlay.add(bg);
     const image=this.add.image(91,y,"h-"+entry.id).setDisplaySize(60,67);
     this.tableOverlay.add(image);
     this.tableOverlay.add(txt(this,190,y,entry.label,21,"#ffdd9a"));
     for(let j=0;j<3;j++){
       const size=entry.numbers[j]==="BÓNUS"?19:23;
       this.tableOverlay.add(txt(this,360+j*106,y,entry.numbers[j],size,"#fff2c9"));
     }
   }
   this.tableOverlay.add(txt(this,360,890,
      "Valores visíveis no APK; unidade e regras não confirmadas.",20,"#ffcf92"));
   this.tableOverlay.add(txt(this,360,926,
      "A matemática atual é experimental, não equivalente.",20,"#ffcf92"));
   const btn=this.add.graphics();
   btn.fillStyle(0xa1322a,.96).fillRoundedRect(235,967,250,62,16);
   btn.lineStyle(4,0xf1c47a).strokeRoundedRect(235,967,250,62,16);
   this.tableOverlay.add(btn);
   this.tableOverlay.add(txt(this,360,998,"FECHAR",27,"#fff6d4"));
   const tap=this.add.zone(360,998,270,75).setInteractive({useHandCursor:true});
   tap.on("pointerdown",()=>this.togglePaytable());
   this.tableOverlay.add(tap);
 }
 togglePaytable(){
   if(this.spinning||this.potOpen||this.wheelOpen)return;
   this.tableOpen=!this.tableOpen;
   if(this.tableOpen)this.auto=false;
   this.tableOverlay.setVisible(this.tableOpen);
   this.autoText.setText("");
 }
 makeDiceBonus(){
   // Atlas original: dados vermelhos e cinco animações individuais.
   // Todos os elementos aqui são desenhados no Phaser, sem assets do APK.
   this.diceOpen=false;
   this.diceOverlay=this.add.container(0,0).setDepth(125).setVisible(false);
   const dark=this.add.rectangle(W/2,H/2,W,H,0x0c0819,.97).setInteractive();
   const border=this.add.graphics();
   border.fillStyle(0x29142a,.98).fillRoundedRect(28,330,664,620,22);
   border.lineStyle(6,0xe5ad61).strokeRoundedRect(28,330,664,620,22);
   this.diceOverlay.add([dark,border,txt(this,360,391,"BÓNUS DOS DADOS",37,"#ffda9d"),
     txt(this,360,453,"ESCOLHE UM DOS CINCO",26,"#f4dfbf")]);
   this.diceHits=[];this.diceFaces=[];
   for(let i=0;i<E.DICE_CHOICES;i++){
     const x=100+i*130,y=638;
     const shadow=this.add.graphics();
     shadow.fillStyle(0x070913,.7).fillRoundedRect(x-51,y-45,108,110,14);
     shadow.fillStyle(0xa01d1b).fillRoundedRect(x-52,y-59,104,101,14);
     shadow.lineStyle(5,0xee956d).strokeRoundedRect(x-52,y-59,104,101,14);
     shadow.fillStyle(0xd52e28).fillRoundedRect(x-42,y-51,84,83,11);
     shadow.fillStyle(0xffcfb9);
     for(const [dx,dy] of [[-24,-24],[24,-24],[-24,22],[24,22]])
        shadow.fillCircle(x+dx,y+dy,5);
     this.diceOverlay.add(shadow);
     const face=txt(this,x,y-8,"?",43,"#fff2d5");
     this.diceOverlay.add(face);this.diceFaces.push(face);
     this.diceOverlay.add(txt(this,x,y+74,String(i+1),23,"#f8c888"));
     const hit=this.add.zone(x,y,116,124).setInteractive({useHandCursor:true});
     hit.on("pointerdown",()=>this.pickDice(i));
     this.diceOverlay.add(hit);this.diceHits.push(hit);
   }
   this.diceHint=txt(this,360,822,"TOCA NUM DADO",26,"#f8dcac");
   this.diceOverlay.add(this.diceHint);
 }
 showDiceBonus(){
   if(!this.state.pendingDice)return;
   this.diceOpen=true;this.auto=false;this.autoText.setText("");
   for(let i=0;i<this.diceHits.length;i++){
     this.diceHits[i].setInteractive({useHandCursor:true});
     this.diceFaces[i].setText("?");
   }
   this.diceHint.setText("QUAL SERÁ O TEU NÚMERO?");
   this.diceOverlay.setVisible(true);
   this.tone(510,.12);
 }
 pickDice(index){
   if(!this.diceOpen||!this.state.pendingDice)return;
   const result=E.chooseDice(this.state,index);
   if(!result.ok)return;
   this.state=result.state;this.store();this.renderState();
   for(let i=0;i<this.diceHits.length;i++){
     this.diceHits[i].disableInteractive();
     this.diceFaces[i].setText(String(result.reveal[i]));
   }
   this.diceHint.setText("DADO "+result.face+" · GANHASTE "+fmt(result.awarded)+" CRÉDITOS");
   this.flash();
   this.time.delayedCall(1700,()=>{
      this.diceOverlay.setVisible(false);this.diceOpen=false;
      this.message.setText("DADOS +"+fmt(result.awarded));
      if(this.state.pendingWheel)this.showWheelBonus();
      else if(this.state.pendingStrawberry)this.showStrawberryBonus();
      else if(this.state.pendingPot)this.showPotBonus();
   });
 }
 makeStrawberryBonus(){
   // Doze escolhas, como na grelha de elementos gráficos do APK.
   // Ilustrações e sorteio independentes; os valores são virtuais.
   this.strawberryOpen=false;
   this.strawberryOverlay=this.add.container(0,0).setDepth(120).setVisible(false);
   const dark=this.add.rectangle(W/2,H/2,W,H,0x100a1f,.97).setInteractive();
   const frame=this.add.graphics();
   frame.fillStyle(0x2c1630,.99).fillRoundedRect(32,265,656,740,22);
   frame.lineStyle(6,0xe8bb65).strokeRoundedRect(32,265,656,740,22);
   this.strawberryOverlay.add([dark,frame]);
   this.strawberryOverlay.add(txt(this,360,328,"BÓNUS DOS MORANGOS",37,"#ffe6ae"));
   this.strawberryOverlay.add(txt(this,360,380,"ESCOLHE UM MORANGO",26,"#ffdbad"));
   this.strawberryValues=[];
   this.strawberryHits=[];
   for(let i=0;i<E.STRAWBERRY_CHOICES;i++){
      const col=i%4,row=Math.floor(i/4);
      const x=144+col*145,y=506+row*123;
      const fruit=this.add.graphics();
      fruit.fillStyle(0xa91a34).fillEllipse(x,y,76,80);
      fruit.fillStyle(0xe82b4c).fillEllipse(x-8,y-10,57,59);
      fruit.fillStyle(0x338746);
      fruit.fillTriangle(x-28,y-33,x+30,y-33,x,y-7);
      fruit.fillStyle(0x3c8d4f).fillEllipse(x+2,y-38,48,21);
      fruit.fillStyle(0xffcc83);
      for(let k=0;k<5;k++){
         const sx=x-21+(k%3)*20,sy=y-7+Math.floor(k/3)*26;
         fruit.fillCircle(sx,sy,3);
      }
      this.strawberryOverlay.add(fruit);
      const value=txt(this,x,y+58,"?",21,"#fff3bc");
      this.strawberryOverlay.add(value);
      this.strawberryValues.push(value);
      const zone=this.add.zone(x,y,108,105).setInteractive({useHandCursor:true});
      zone.on("pointerdown",()=>this.pickStrawberry(i));
      this.strawberryOverlay.add(zone);
      this.strawberryHits.push(zone);
   }
   this.strawberryHint=txt(this,360,935,"TOCA NO MORANGO",25,"#ffdfa5");
   this.strawberryOverlay.add(this.strawberryHint);
 }
 showStrawberryBonus(){
   if(!this.state.pendingStrawberry)return;
   this.strawberryOpen=true;this.auto=false;this.autoText.setText("");
   for(let i=0;i<this.strawberryHits.length;i++){
      this.strawberryHits[i].setInteractive({useHandCursor:true});
      this.strawberryValues[i].setText("?");
   }
   this.strawberryHint.setText("ESCOLHE UM DOS 12 MORANGOS");
   this.strawberryOverlay.setVisible(true);
   this.tone(650,.13);
 }
 pickStrawberry(index){
   if(!this.strawberryOpen||!this.state.pendingStrawberry)return;
   const result=E.chooseStrawberry(this.state,index);
   if(!result.ok)return;
   this.state=result.state;this.store();this.renderState();
   for(let i=0;i<this.strawberryHits.length;i++){
      this.strawberryHits[i].disableInteractive();
      this.strawberryValues[i].setText(fmt(result.reveal[i]));
   }
   this.strawberryHint.setText("GANHASTE "+fmt(result.awarded)+" CRÉDITOS");
   this.flash();
   this.time.delayedCall(1600,()=>{
      this.strawberryOverlay.setVisible(false);
      this.strawberryOpen=false;
      this.message.setText("MORANGOS +"+fmt(result.awarded));
      if(this.state.pendingWheel)this.showWheelBonus();
      else if(this.state.pendingPot)this.showPotBonus();
   });
 }
 makePotBonus(){
   // Janela modal Phaser; mantém a escolha pendente no estado local até
   // o utilizador tocar num caldeirão, mesmo após fechar o navegador.
   this.potOverlay=this.add.container(0,0).setDepth(100).setVisible(false);
   const veil=this.add.rectangle(W/2,H/2,W,H,0x0b071c,.96).setInteractive();
   this.potOverlay.add(veil);
   const border=this.add.graphics();
   border.fillStyle(0x3b1638,.98).fillRoundedRect(53,320,614,600,28);
   border.lineStyle(8,0xd8a04a,1).strokeRoundedRect(53,320,614,600,28);
   this.potOverlay.add(border);
   const heading=txt(this,W/2,386,"BÓNUS DOS CALDEIRÕES",36,"#ffe3a2");
   const subtitle=txt(this,W/2,456,"ESCOLHE UM DOS TRÊS",27,"#f8dbcb");
   this.potOverlay.add([heading,subtitle]);
   this.potValues=[];
   this.potImages=[];
   for(let i=0;i<3;i++){
     const x=155+i*205;
     const pot=this.add.image(x,620,"h-scatter")
       .setDisplaySize(162,188).setInteractive({useHandCursor:true});
     pot.on("pointerdown",()=>this.pickPot(i));
     this.potOverlay.add(pot);
     const number=txt(this,x,751,String(i+1),29,"#f8d79b");
     this.potOverlay.add(number);
     this.potImages.push(pot);
     const value=txt(this,x,804,"?",23,"#fff6cf");
     this.potOverlay.add(value);
     this.potValues.push(value);
   }
   this.potHint=txt(this,W/2,862,"TOCA NO CALDEIRÃO",23,"#fff4d7");
   this.potOverlay.add(this.potHint);
 }
 showPotBonus(){
   if(!this.state.pendingPot)return;
   this.potOpen=true;
   this.canHold=false;
   this.auto=false;
   this.autoText.setText("");
   for(let i=0;i<3;i++){
     this.potImages[i].setInteractive({useHandCursor:true}).setAlpha(1);
     this.potValues[i].setText("?");
   }
   this.potHint.setText("ESCOLHE O TEU PRÉMIO");
   this.potOverlay.setVisible(true);
   this.tone(740,.18);
 }
 pickPot(index){
   if(!this.potOpen||!this.state.pendingPot)return;
   const outcome=E.choosePot(this.state,index);
   if(!outcome.ok)return;
   this.state=outcome.state;
   this.store();
   this.renderState();
   this.potHint.setText("GANHASTE "+fmt(outcome.awarded)+" CRÉDITOS!");
   for(let i=0;i<3;i++){
     this.potValues[i].setText(fmt(outcome.reveal[i]));
     this.potImages[i].disableInteractive().setAlpha(i===index?1:.5);
   }
   this.flash();
   this.time.delayedCall(1500,()=>{
     this.potOverlay.setVisible(false);
     this.potOpen=false;
     this.message.setText("BÓNUS +"+fmt(outcome.awarded));
     if(this.state.pendingWheel)this.showWheelBonus();
   });
 }
 makeWheelBonus(){
   this.wheelOpen=false;this.wheelBusy=false;
   this.wheelOverlay=this.add.container(0,0).setDepth(110).setVisible(false);
   const bg=this.add.rectangle(W/2,H/2,W,H,0x0e0a1c,.97).setInteractive();
   const border=this.add.graphics();
   border.fillStyle(0x28152d,.97).fillRoundedRect(26,282,668,728,20);
   border.lineStyle(7,0xf0ac4a).strokeRoundedRect(26,282,668,728,20);
   this.wheelOverlay.add([bg,border,txt(this,360,330,"ROLETA DAS ABÓBORAS",35,"#fce2a5")]);
   this.wheelDisk=this.add.container(360,642);
   const shape=this.add.graphics();
   const colors=[0xc97739,0x83233b,0xb79c40,0x355981,0x973044,0x4e804b];
   const slice=2*Math.PI/E.WHEEL_SECTORS.length;
   for(let i=0;i<E.WHEEL_SECTORS.length;i++){
     const first=-Math.PI/2-slice/2+i*slice;
     const last=first+slice;
     shape.fillStyle(colors[i%colors.length],1);
     shape.fillTriangle(0,0,Math.cos(first)*210,Math.sin(first)*210,
       Math.cos(last)*210,Math.sin(last)*210);
   }
   shape.lineStyle(9,0xe2b661).strokeCircle(0,0,209);
   shape.fillStyle(0x1d152d,1).fillCircle(0,0,34);
   shape.lineStyle(6,0xffd18d).strokeCircle(0,0,34);
   this.wheelDisk.add(shape);
   for(let i=0;i<E.WHEEL_SECTORS.length;i++){
     const angle=-Math.PI/2+i*2*Math.PI/E.WHEEL_SECTORS.length;
     const x=Math.cos(angle)*170,y=Math.sin(angle)*170;
     const label=String(E.WHEEL_SECTORS[i].number);
     this.wheelDisk.add(txt(this,x,y,label,15,"#fff6d4",
       {stroke:"#3a2431",strokeThickness:2}));
   }
   this.wheelOverlay.add(this.wheelDisk);
   const pin=this.add.graphics();
   pin.fillStyle(0xffd77c).fillTriangle(360,428,341,398,379,398);
   pin.lineStyle(3,0x552633).strokeTriangle(360,428,341,398,379,398);
   this.wheelOverlay.add(pin);
   this.wheelHint=txt(this,360,925,"PRIME O BOTÃO PARA RODAR",25,"#ffe8bc");
   this.wheelOverlay.add(this.wheelHint);
   const start=this.add.graphics();
   start.fillStyle(0x9c2a27).fillRoundedRect(221,817,278,72,18);
   start.lineStyle(4,0xffd278).strokeRoundedRect(221,817,278,72,18);
   this.wheelOverlay.add(start);
   this.wheelButton=txt(this,360,854,"RODAR ROLETA",29,"#fff1ce");
   this.wheelOverlay.add(this.wheelButton);
   const press=this.add.zone(360,852,286,80).setInteractive({useHandCursor:true});
   press.on("pointerdown",()=>this.rollWheel());
   this.wheelOverlay.add(press);
 }
 showWheelBonus(){
   if(!this.state.pendingWheel)return;
   this.wheelOpen=true;this.wheelBusy=false;
   this.auto=false;this.autoText.setText("");
   this.wheelDisk.setAngle(0);
   this.wheelHint.setText("PRIME O BOTÃO PARA RODAR");
   this.wheelButton.setText("RODAR ROLETA").setVisible(true);
   this.wheelOverlay.setVisible(true);
   this.tone(620,.18);
 }
 rollWheel(){
   if(!this.wheelOpen||this.wheelBusy||!this.state.pendingWheel)return;
   this.wheelBusy=true;this.wheelButton.setText("A RODAR...");
   this.wheelHint.setText("BOA SORTE!");
   const sector=this.state.pendingWheel.sectorIndex;
   this.tone(460,.2);
   this.tweens.add({targets:this.wheelDisk,angle:7*360-sector*(360/E.WHEEL_SECTORS.length),duration:4900,
     ease:"Cubic.Out",onComplete:()=>this.finishWheel()});
 }
 finishWheel(){
   if(!this.state.pendingWheel)return;
   const result=E.resolveWheel(this.state);
   if(!result.ok)return;
   this.state=result.state;this.store();this.renderState();
   this.wheelButton.setText("GANHASTE!");
   this.wheelHint.setText(result.label+" · "+fmt(result.awarded)+" CRÉDITOS");
   this.flash();
   this.time.delayedCall(2000,()=>{
     this.wheelOverlay.setVisible(false);
     this.wheelOpen=false;this.wheelBusy=false;
     this.message.setText("ROLETA +"+fmt(result.awarded));
     if(this.state.pendingPot)this.showPotBonus();
   });
 }
 highlight(hits){
   if(this.winTimer){
     this.winTimer.remove(false);
     this.winTimer=null;
   }
   this.winGlow.clear();this.winPath.clear();this.winLabel.setText("");
   if(!Array.isArray(hits)||hits.length===0)return;
   const sequence=hits.filter(hit=>hit?.cells?.length>=3);
   if(!sequence.length)return;
   this.winIndex=0;
   const draw=()=>{
     if(this.spinning||this.tableOpen)return;
     const hit=sequence[this.winIndex%sequence.length];
     this.winGlow.clear();this.winPath.clear();
     const palette=[0xf8bb50,0xd9566d,0x56c1d8,0x97d75b,0xc78ee8];
     const color=palette[(hit.line-1)%palette.length];
     this.winPath.lineStyle(7,color,1);
     this.winPath.beginPath();
     for(let i=0;i<hit.cells.length;i++){
       const [col,row]=hit.cells[i],x=X+(col+.5)*CW,y=Y+(row+.5)*CH;
       if(i===0)this.winPath.moveTo(x,y);else this.winPath.lineTo(x,y);
       this.winGlow.lineStyle(4,color,1).strokeRoundedRect(
         X+col*CW+6,Y+row*CH+6,CW-12,CH-12,9);
     }
     this.winPath.strokePath();
     this.winLabel.setText("LINHA "+hit.line+" · "+hit.symbol.toUpperCase()+
       " ×"+hit.count+" · "+fmt(hit.amount)+" CRÉDITOS");
   };
   draw();
   if(sequence.length>1)this.winTimer=this.time.addEvent({
     delay:1100,loop:true,callback:()=>{this.winIndex++;draw();}
   });
 }
 flash(){
   this.cameras.main.flash(230,255,212,127,false);
   // Pequena melodia original de celebração, sem amostras proprietárias.
   for(const [index,note] of [659,784,988,1318].entries())
     this.time.delayedCall(115*index,()=>this.tone(note,.17));
 }
 tone(freq,len){
   if(this.silence)return;
   try{
     const Audio=window.AudioContext||window.webkitAudioContext;
     if(!Audio)return;
     if(!this.audio)this.audio=new Audio();
     if(this.audio.state==="suspended")this.audio.resume().catch(()=>{});
     const ctx=this.audio,t=ctx.currentTime;
     const filter=ctx.createBiquadFilter();
     filter.type="lowpass";filter.frequency.value=Math.min(2800,Math.max(680,freq*3));
     const master=ctx.createGain();
     master.gain.setValueAtTime(.0001,t);
     master.gain.exponentialRampToValueAtTime(.045,t+.012);
     master.gain.exponentialRampToValueAtTime(.0001,t+len);
     filter.connect(master);master.connect(ctx.destination);
     for(const [wave,pitch,level] of [["square",freq,.64],["triangle",freq*.5,.36]]){
       const osc=ctx.createOscillator(),gain=ctx.createGain();
       osc.type=wave;gain.gain.value=level;
       osc.frequency.setValueAtTime(pitch,t);
       osc.frequency.exponentialRampToValueAtTime(Math.max(50,pitch*.87),t+len);
       osc.connect(gain);gain.connect(filter);
       osc.start(t);osc.stop(t+len+.02);
     }
   }catch{/* O áudio não bloqueia o jogo em browsers sem WebAudio. */}
 }

}
new Phaser.Game({type:Phaser.AUTO,parent:"game",width:W,height:H,backgroundColor:"#160d28",
  scale:{mode:Phaser.Scale.FIT,autoCenter:Phaser.Scale.CENTER_BOTH},
  render:{antialias:true},scene:[Classica]});
})();
