/* Jardim Mágico Phaser V1 — animação 2D/2.5D dos tambores e cascatas.
   O resultado é fornecido pelo motor lógico; Phaser apenas o apresenta.
   Eixo Y: baixo é positivo, nunca roda ou inverte UV das imagens. */
import {SYMBOLS,newBoard} from './engine.js?v=1';
const DESIGN_W=900,DESIGN_H=540,CW=DESIGN_W/5,CH=DESIGN_H/3;
const SOURCES={
 potion:'3f3213b7316d5cf176.png',
 green:'e776f95aa9fd19f865.png',
 heart:'66cb91b07aa8094c90.png',
 teapot:'49d6874847ff87abe4.png',
 wild:'05ad729435e7487db8.png',
 gem:'6db9f4dd9d6abeb732.png',
 mushroom:'81cc7b8d8f2c0d0b18.png',
 hat:'ab10713bfdfc732017.png',
 rose:'ae9c51465776decce9.png',
 scatter:'d95ddf1f53c88d01c4.png'
};
const FALLBACK_EMOJI={
 potion:'🧪',green:'💚',heart:'❤️',teapot:'🫖',wild:'🌺',
 gem:'💎',mushroom:'🍄',hat:'🎩',rose:'🌹',scatter:'✨',stone:'🪨',coin:'2×'
};
const ALT_COLORS={potion:'#ef499f',green:'#48ce8b',heart:'#f66898',
 teapot:'#a792e0',wild:'#ffda84',gem:'#ce94f8',mushroom:'#f5bd8b',
 hat:'#ba7cef',rose:'#fa658b',scatter:'#82c8ec',stone:'#777487',coin:'#ffcc6b'};
const moneyUnit=x=>Number.isFinite(x)?x:0;
function asKey(symbol){return 'art-'+symbol}
function createFallbackCanvas(key,symbol){
 const canvas=document.createElement('canvas');canvas.width=canvas.height=512;
 const g=canvas.getContext('2d');
 const grad=g.createRadialGradient(230,200,20,256,256,220);
 grad.addColorStop(0,'#ffffff');grad.addColorStop(.23,ALT_COLORS[symbol]);
 grad.addColorStop(.85,'#6d2168');grad.addColorStop(1,'rgba(48,8,66,0)');
 if(symbol==='coin'){
  g.shadowColor='#edafff';g.shadowBlur=18;
  g.beginPath();g.arc(256,256,205,0,Math.PI*2);
  g.fillStyle='#f7d182';g.fill();g.lineWidth=18;g.strokeStyle='#fff4b7';g.stroke();
  g.shadowBlur=0;g.beginPath();g.arc(256,256,175,0,Math.PI*2);
  g.fillStyle=grad;g.fill();g.lineWidth=6;g.strokeStyle='#9d4580';g.stroke();
  g.font='900 178px Georgia,serif';g.textAlign='center';g.textBaseline='middle';
  g.lineWidth=10;g.strokeStyle='#63245f';g.strokeText('2×',256,274);
  g.fillStyle='#ffebb2';g.fillText('2×',256,274);
 }else if(symbol==='stone'){
  g.beginPath();g.moveTo(215,39);g.lineTo(391,92);g.lineTo(448,275);
  g.lineTo(335,449);g.lineTo(130,453);g.lineTo(62,235);
  g.lineTo(120,99);g.closePath();
  g.fillStyle=grad;g.fill();
  g.strokeStyle='#c9beea';g.lineWidth=13;g.stroke();
  g.beginPath();g.moveTo(215,39);g.lineTo(225,251);g.lineTo(62,235);
  g.moveTo(225,251);g.lineTo(448,275);g.moveTo(225,251);
  g.lineTo(335,449);g.strokeStyle='#e4d8f7';g.lineWidth=6;g.stroke();
 }else{
  const text=FALLBACK_EMOJI[symbol]||'✦';
  g.shadowColor=ALT_COLORS[symbol];g.shadowBlur=24;
  g.font='290px "Apple Color Emoji","Segoe UI Emoji",sans-serif';
  g.textAlign='center';g.textBaseline='middle';g.fillText(text,256,255);
  g.shadowBlur=0;
 }
 return canvas;
}
class ReelScene extends Phaser.Scene{
 constructor(){
  super({key:'JardimReels'});
  this.ready=false;
  this.busy=false;
  this.board=null;
  this.columns=[];
  this.items=[];
  this.spinTweens=[];
  this.particles=[];
  this.failedAssets=[];
 }
 preload(){
  this.load.setPath('../assets/imagens/');
  for(const [key,file] of Object.entries(SOURCES))
   this.load.image('source-'+key,file);
  this.load.on('loaderror',file=>{
   this.failedAssets.push(file.key);
   console.warn('Símbolo original não carregou',file.key);
  });
 }
 create(){
  this.cameras.main.setBackgroundColor('rgba(0,0,0,0)');
  // Máscara 5×3: as peças saem por baixo e reaparecem acima,
  // mas nunca ficam visíveis para lá dos limites da janela.
  const g=this.make.graphics({x:0,y:0,add:false});
  g.fillStyle(0xffffff,1);g.fillRect(0,0,DESIGN_W,DESIGN_H);
  this.reelMask=g.createGeometryMask();
  this.createTextures();
  this.addColumns();
  this.board=newBoard();
  this.setBoard(this.board);
  this.ready=true;
  window.jardimPhaserScene=this;
  window.dispatchEvent(new Event('jardim-phaser-ready'));
 }
 createTextures(){
  for(const symbol of SYMBOLS){
   const orig='source-'+symbol;
   const art=asKey(symbol);
   let canvas=null;
   if(this.textures.exists(orig)){
    try{canvas=this.cleanOriginalArt(orig)}catch(err){
      console.warn('Recorte original indisponível:',symbol,err);
    }
   }
   if(!canvas)canvas=createFallbackCanvas(art,symbol);
   this.textures.addCanvas(art,canvas);
  }
 }
 cleanOriginalArt(sourceKey){
  // Reutiliza a IMAGEM ORIGINAL sem a inverter, espelhar ou tingir.
  const image=this.textures.get(sourceKey).getSourceImage();
  const size=512;
  const input=document.createElement('canvas');
  input.width=input.height=size;
  const a=input.getContext('2d',{willReadFrequently:true});
  a.drawImage(image,0,0,size,size);
  const source=a.getImageData(0,0,size,size),p=source.data;
  let left=size,top=size,right=0,bottom=0;
  for(let y=0;y<size;y++)for(let x=0;x<size;x++){
   const k=4*(y*size+x);
   if(p[k+3]<35)continue;
   const light=Math.max(p[k],p[k+1],p[k+2]);
   // A área preta exterior e a parte escura do fundo não são símbolos.
   if(light<77)continue;
   left=Math.min(left,x);right=Math.max(right,x);
   top=Math.min(top,y);bottom=Math.max(bottom,y);
  }
  if(right-left<42||bottom-top<42)throw Error('Sem imagem legível');
  const output=document.createElement('canvas');
  output.width=output.height=size;
  const ctx=output.getContext('2d',{willReadFrequently:true});
  const w=right-left+1,h=bottom-top+1;
  const scale=Math.min(490/w,490/h);
  const newW=w*scale,newH=h*scale;
  ctx.drawImage(input,left,top,w,h,(size-newW)/2,(size-newH)/2,newW,newH);
  const content=ctx.getImageData(0,0,size,size),data=content.data;
  // Remover os retângulos violeta do screenshot sem dessaturar o símbolo.
  // Preservar luminosidade, vermelho e verde; suavizar apenas o escuro.
  for(let i=0;i<data.length;i+=4){
   if(data[i+3]===0)continue;
   const red=data[i],green=data[i+1],blue=data[i+2];
   const max=Math.max(red,green,blue),min=Math.min(red,green,blue);
   let factor=Phaser.Math.Clamp((max-32)/83,0,1);
   const darkPurple=blue>green*1.24 && red>green*1.12 && max<130;
   if(darkPurple)factor*=.20;
   const vivid=(red>green*1.55&&red>107)||
    (green>red*1.22&&green>102)||
    (blue>150&&max-min>90&&green>78);
   if(vivid)factor=Math.max(.95,factor);
   // Fade só as bordas exteriores do fragmento.
   const x=(i/4)%size,y=Math.floor((i/4)/size);
   const dx=(x-255.5)/256,dy=(y-255.5)/256;
   const radius=Math.sqrt(dx*dx+dy*dy);
   const fade=Phaser.Math.Clamp((.96-radius)/.27,0,1);
   data[i+3]=Math.round(data[i+3]*factor*fade);
  }
  ctx.putImageData(content,0,0);
  return output;
 }
 makeSprite(c,slot){
  const x=(c+.5)*CW;
  const y=(slot+.5)*CH;
  const sprite=this.add.image(x,y,asKey('potion'));
  sprite.setDisplaySize(CW*.93,CH*.94);
  sprite.setMask(this.reelMask);
  sprite.setDepth(10);
  return {sprite,slot,wrapped:0,symbol:'potion'};
 }
 addColumns(){
  // Cinco imagens por coluna: três visíveis e duas para a rolagem
  // circular. O movimento é contínuo na direção Y positiva.
  for(let c=0;c<5;c++){
   const items=[];
   for(const slot of [-1,0,1,2,3])items.push(this.makeSprite(c,slot));
   this.columns[c]={items,visible:items.filter(x=>x.slot>=0&&x.slot<=2)};
  }
 }
 useSymbol(item,symbol){
  const key=asKey(symbol);
  item.symbol=symbol;
  if(!this.textures.exists(key))return;
  item.sprite.setTexture(key);
  item.sprite.setDisplaySize(CW*.93,CH*.94);
  item.sprite.setRotation(0);item.sprite.setFlip(false,false);
  item.sprite.setAlpha(1);item.sprite.setScale(item.sprite.scaleX,item.sprite.scaleY);
 }
 getVisible(c,r){return this.columns[c].visible[r].sprite}
 setBoard(board){
  if(!Array.isArray(board)||board.length!==5)throw Error('Grelha inválida');
  this.board=board.map(c=>c.slice());
  for(let c=0;c<5;c++){
   for(const item of this.columns[c].items){
    item.sprite.setY((item.slot+.5)*CH);
    item.sprite.setAlpha(1);
    item.sprite.setAngle(0);
    if(item.slot>=0&&item.slot<3)this.useSymbol(item,board[c][item.slot]);
    else this.useSymbol(item,SYMBOLS[(c+item.slot+SYMBOLS.length)%SYMBOLS.length]);
   }
  }
  this.busy=false;
 }
 spin(target,{turbo=false}={}){
  if(this.busy)return Promise.reject(Error('Tambores ocupados'));
  if(!this.ready)return Promise.reject(Error('Phaser ainda não carregado'));
  this.busy=true;
  let finished=0;
  const total=this.columns.length;
  const duration=turbo?600:1480;
  return new Promise(resolve=>{
   for(let c=0;c<5;c++){
    const items=this.columns[c].items;
    const spins=15+5*c;
    const journey={distance:0};
    for(const item of items)item.wrapped=0;
    this.tweens.add({
     targets:journey,distance:spins,
     duration:duration+(turbo?40:185)*c,
     ease:'Cubic.easeOut',
     onUpdate:()=>{
      for(const item of items){
       const raw=item.slot+journey.distance+1;
       const wrapped=Math.floor(raw/5);
       if(wrapped!==item.wrapped){
        item.wrapped=wrapped;
        this.useSymbol(item,SYMBOLS[Math.floor(Math.random()*SYMBOLS.length)]);
       }
       const slot=((raw%5)+5)%5-1;
       item.sprite.y=(slot+.5)*CH;
      }
     },
     onComplete:()=>{
      // Cada tambor para alinhado, mostrando apenas o resultado
      // calculado pelo motor lógico. Não há troca de linhas/pagamentos.
      for(const item of items){
       item.sprite.y=(item.slot+.5)*CH;
       if(item.slot>=0&&item.slot<3)this.useSymbol(item,target[c][item.slot]);
      }
      finished++;
      if(finished===total){
       this.board=target.map(col=>col.slice());
       this.busy=false;
       resolve(target);
      }
     }
    });
   }
  });
 }
 burst(cells,{destroy=true,duration=690}={}){
  if(!Array.isArray(cells)||!cells.length)return Promise.resolve();
  const positions=new Set(),targets=[];
  for(const pos of cells){
   if(pos.c<0||pos.c>=5||pos.r<0||pos.r>=3)continue;
   const key=pos.c+','+pos.r;
   if(positions.has(key))continue;
   positions.add(key);
   targets.push({c:pos.c,r:pos.r,s:this.getVisible(pos.c,pos.r)});
  }
  return new Promise(resolve=>{
   if(!targets.length){resolve();return}
   let ended=0;
   for(const obj of targets){
    const s=obj.s,x=s.x,y=s.y;
    const wave=this.add.circle(x,y,42,0xffd275,.30).setDepth(14);
    this.tweens.add({targets:wave,scale:2.1,alpha:0,duration:duration,
      onComplete:()=>wave.destroy()});
    const palette=[0xffd277,0xff89d9,0xb284ff,0x7eead7];
    for(let i=0;i<5;i++){
     const angle=(i/5)*Math.PI*2+obj.c*.7+obj.r*.3;
     const particle=this.add.circle(x,y,4+i%2,palette[(i+obj.c)%palette.length],.9);
     particle.setDepth(18);
     const travel=45+Math.random()*55;
     this.tweens.add({targets:particle,x:x+Math.cos(angle)*travel,
       y:y+Math.sin(angle)*travel+25,alpha:0,scale:.3,
       duration:duration,ease:'Cubic.easeOut',
       onComplete:()=>particle.destroy()});
    }
    this.tweens.add({
     targets:s,
     scaleX:s.scaleX*(destroy?.05:1.13),
     scaleY:s.scaleY*(destroy?.05:1.13),
     alpha:destroy?0:1,
     duration:duration*.76,
     ease:destroy?'Back.easeIn':'Sine.easeInOut',
     yoyo:!destroy,
     onComplete:()=>{
      ended++;if(ended===targets.length)resolve();
     }
    });
   }
  });
 }
 cascade(next,removed,{turbo=false}={}){
  if(this.busy)return Promise.reject(Error('Tambores ocupados'));
  this.busy=true;
  const removedSet=new Set(removed.map(p=>p.c+','+p.r));
  const tweens=[];
  for(let c=0;c<5;c++){
   const col=this.columns[c];
   const originals=col.visible.slice();
   const kept=[],recycled=[];
   for(let r=2;r>=0;r--){
    if(removedSet.has(c+','+r))recycled.push(originals[r]);
    else kept.push(originals[r]);
   }
   const arranged=new Array(3);
   let bottom=2;
   for(const item of kept){
    arranged[bottom]=item;
    item.sprite.setAlpha(1);
    tweens.push({item,to:(bottom+.5)*CH});
    bottom--;
   }
   let order=0;
   while(bottom>=0){
    const item=recycled[order];
    if(!item)throw Error('Peça de queda não encontrada');
    arranged[bottom]=item;
    this.useSymbol(item,next[c][bottom]);
    item.sprite.y=-(order+.65)*CH;
    item.sprite.setAlpha(1);
    tweens.push({item,to:(bottom+.5)*CH});
    order++;bottom--;
   }
   for(let r=0;r<3;r++)arranged[r].slot=r;
   col.visible=arranged;
   col.items=col.items.filter(item=>item.slot===-1||item.slot===3).concat(arranged);
  }
  const duration=turbo?290:620;
  return new Promise(resolve=>{
   if(!tweens.length){
    this.setBoard(next);resolve(next);return;
   }
   let finished=0;
   for(const {item,to} of tweens){
    this.tweens.add({targets:item.sprite,y:to,duration,ease:'Cubic.easeIn',
      onComplete:()=>{
       finished++;
       if(finished===tweens.length){
        for(let c=0;c<5;c++){
         const col=this.columns[c];
         for(let r=0;r<3;r++)this.useSymbol(col.visible[r],next[c][r]);
        }
        this.board=next.map(x=>x.slice());
        this.busy=false;resolve(next);
       }
      }
    });
   }
  });
 }
 recover(board){
  this.tweens.killAll();
  this.setBoard(board);
  this.busy=false;
 }
}
export function createReelGame(){
 if(typeof Phaser==='undefined'){
  document.getElementById('loadError').hidden=false;
  return null;
 }
 return new Phaser.Game({
  type:Phaser.AUTO,parent:'phaser-reels',width:DESIGN_W,height:DESIGN_H,
  transparent:true,
  scale:{mode:Phaser.Scale.FIT,autoCenter:Phaser.Scale.CENTER_BOTH,
   width:DESIGN_W,height:DESIGN_H},
  render:{antialias:true,pixelArt:false},
  fps:{target:60,forceSetTimeOut:false},
  scene:[ReelScene]
 });
}
