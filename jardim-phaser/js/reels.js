/* Jardim Mágico Phaser V1 — animação 2D/2.5D dos tambores e cascatas.
   O resultado é fornecido pelo motor lógico; Phaser apenas o apresenta.
   Eixo Y: baixo é positivo, nunca roda ou inverte UV das imagens. */
import {SYMBOLS,newBoard} from './engine.js?v=1';
import {burstParticleBudget} from './performance-v17.js?v=2.9';
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
/* Conjunto botânico aprovado: mantém IDs numéricos e pesos do motor,
   alterando apenas as texturas visíveis. Os símbolos restantes mantêm
   a arte anterior. */
const BOTANICAL_V34={
 potion:'jm34-caracol.svg',
 green:'jm34-joaninha.svg',
 heart:'jm34-flor-azul.svg',
 rose:'jm34-flor-vermelha.svg',
 wild:'jm34-wild.svg',
 stone:'jm34-pedra.svg'
};
const FALLBACK_EMOJI={
 potion:'🧪',green:'💚',heart:'❤️',teapot:'🫖',wild:'🌺',
 gem:'💎',mushroom:'🍄',hat:'🎩',rose:'🌹',scatter:'✨',stone:'🪨',coin:'2×'
};
const ALT_COLORS={potion:'#ef499f',green:'#48ce8b',heart:'#f66898',
 teapot:'#a792e0',wild:'#ffda84',gem:'#ce94f8',mushroom:'#f5bd8b',
 hat:'#ba7cef',rose:'#fa658b',scatter:'#82c8ec',stone:'#777487',coin:'#ffcc6b'};
/** Nine premium source glyphs arranged 3 × 3 in the reference artwork. */
export function atlasTileFor(symbol){
 const index=SYMBOLS.indexOf(symbol);
 if(index<0||index>8)return null;
 return {col:index%3,row:Math.floor(index/3)};
}
const moneyUnit=x=>Number.isFinite(x)?x:0;
function asKey(symbol){return 'art-'+symbol}
/* O resultado final é colocado quando a peça REENTRA por cima.
   Não pode haver alteração de símbolo no último frame após parar.
   Cada coluna tem cinco imagens circulantes e pára após N passos,
   sendo N divisível por cinco: as três peças visíveis já fizeram a
   última entrada durante os três passos finais do movimento. */
export function enteringSymbol(slot,wrapCount,finalWrap,targetColumn,rng=Math.random){
 if(slot>=0&&slot<3&&wrapCount===finalWrap){
  const id=targetColumn[slot];
  return typeof id==='number'?SYMBOLS[id]:id;
 }
 return SYMBOLS[Math.min(SYMBOLS.length-1,Math.floor(rng()*SYMBOLS.length))];
}
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
  this.fxObjects=new Set();
  this.wildBadges=[];
  this.liteEffects=false;
  this.failedAssets=[];
 }
 preload(){
  this.load.setPath('../assets/imagens/');
  this.load.image('symbols-atlas','jm75-simbolos.webp');
  for(const [key,file] of Object.entries(SOURCES))
   this.load.image('source-'+key,file);
  for(const [symbol,file] of Object.entries(BOTANICAL_V34))
   this.load.svg('botanical-'+symbol,file,{width:512,height:512});
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
  const atlas=this.textures.exists('symbols-atlas')?
   this.textures.get('symbols-atlas').getSourceImage():null;
  this.premiumSymbols=0;
  for(const symbol of SYMBOLS){
   const orig='source-'+symbol;
   const art=asKey(symbol);
   let canvas=null;
   // V3.4: usar o símbolo botânico isolado, SEM fundos de screenshot.
   // SVG rasterizado uma vez pelo Phaser, sem filtros pesados em cada frame.
   const painted='botanical-'+symbol;
   if(BOTANICAL_V34[symbol]&&this.textures.exists(painted)){
    try{
     const image=this.textures.get(painted).getSourceImage();
     canvas=document.createElement('canvas');
     canvas.width=canvas.height=512;
     canvas.getContext('2d').drawImage(image,0,0,512,512);
     this.premiumSymbols++;
    }catch(err){canvas=null;console.warn('Arte do bosque:',symbol,err)}
   }
   // Manter os restantes ícones HD originais sem inverter ou tingir.
   if(!canvas&&atlas&&atlasTileFor(symbol)){
    try{
     canvas=this.cutPremiumAtlas(atlas,symbol);
     this.premiumSymbols++;
    }catch(err){
     console.warn('Arte premium indisponível:',symbol,err);
    }
   }
   // Fallback para a arte anterior; nunca deixar espaço vazio.
   if(!canvas&&this.textures.exists(orig)){
    try{canvas=this.cleanOriginalArt(orig)}catch(err){
     console.warn('Recorte original indisponível:',symbol,err);
    }
   }
   if(!canvas)canvas=createFallbackCanvas(art,symbol);
   this.textures.addCanvas(art,canvas);
  }
 }
 cutPremiumAtlas(image,symbol){
  const tile=atlasTileFor(symbol);
  if(!tile)throw Error('Símbolo fora da folha artística');
  if(image.width<120||image.height<120)
   throw Error('Folha artística demasiado pequena');
  const cellW=image.width/3,cellH=image.height/3;
  const side=512;
  const canvas=document.createElement('canvas');
  canvas.width=canvas.height=side;
  const g=canvas.getContext('2d');
  // Recorte direto do original: preserva cor, luz e brilho da fotografia.
  const inset=1.2;
  g.drawImage(image,
   tile.col*cellW+inset,tile.row*cellH+inset,
   cellW-inset*2,cellH-inset*2,
   12,12,side-24,side-24);
  // Apenas suavizar a orla circular, sem remover cores da peça.
  const vignette=g.createRadialGradient(
   side/2,side/2,side*.32,side/2,side/2,side*.5);
  vignette.addColorStop(0,'rgba(255,255,255,1)');
  vignette.addColorStop(.76,'rgba(255,255,255,1)');
  vignette.addColorStop(1,'rgba(255,255,255,0)');
  g.globalCompositeOperation='destination-in';
  g.fillStyle=vignette;
  g.fillRect(0,0,side,side);
  g.globalCompositeOperation='source-over';
  return canvas;
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
  // O motor guarda IDs numéricos; Phaser usa nomes das texturas.
  // Converter aqui garante que uma vitória não cria sprites vazios.
  const name=typeof symbol==='number'?SYMBOLS[symbol]:symbol;
  const key=asKey(name);
  item.symbol=name;
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
  if(!Array.isArray(target)||target.length!==5||
    target.some(column=>!Array.isArray(column)||column.length!==3))
   return Promise.reject(Error('Resultado de cinco colunas inválido'));
  this.busy=true;
  let finished=0;
  const total=this.columns.length;
  const duration=turbo?640:1470;
  return new Promise(resolve=>{
   for(let c=0;c<5;c++){
    const items=this.columns[c].items;
    // Voltas inteiras para que cada peça acabe na mesma linha física.
    // As imagens do resultado são introduzidas na última volta e
    // viajam realmente do topo até às três posições definitivas.
    const steps=15+5*c;
    const finalWrap=steps/items.length; // 3,4,5,6,7
    const journey={distance:0};
    for(const item of items)item.wrapped=0;
    this.tweens.add({
     targets:journey,distance:steps,
     duration:duration+(turbo?52:180)*c,
     ease:'Cubic.easeOut',
     onUpdate:()=>{
      for(const item of items){
       const raw=item.slot+journey.distance+1;
       const wraps=Math.floor(raw/items.length);
       if(wraps>item.wrapped){
        // Este sprite acabou de sair totalmente por baixo do quadro.
        // Reaparece fora do ecrã em cima já com a imagem certa.
        // Nas três últimas entradas, atribuímos a combinação final.
        const symbol=enteringSymbol(item.slot,wraps,finalWrap,target[c]);
        item.wrapped=wraps;
        this.useSymbol(item,symbol);
       }
       const slot=((raw%items.length)+items.length)%items.length-1;
       item.sprite.y=(slot+.5)*CH;
      }
     },
     onComplete:()=>{
      // NÃO trocar imagens aqui. Têm de ter entrado pelo topo
      // durante o movimento, antes de o tambor abrandar e parar.
      for(const item of items)item.sprite.y=(item.slot+.5)*CH;
      // Conferência invisível: se a animação foi suspensa pelo Safari,
      // um watchdog externo pode recuperar a grelha, sem cobrar novo giro.
      const wrong=items.some(item=>item.slot>=0&&item.slot<3&&
       item.symbol!==SYMBOLS[target[c][item.slot]]);
      if(wrong)console.warn('Phaser: tambor sem conclusão visual coerente',c);
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

 clearWildValues(){
  for(const badge of this.wildBadges){
   if(badge?.active)badge.destroy();
  }
  this.wildBadges=[];
 }
 showWildValues(awards=[]){
  this.clearWildValues();
  if(!this.add?.text||!Array.isArray(awards))return;
  for(const {c,r,value} of awards){
   if(!Number.isInteger(c)||!Number.isInteger(r)||
      c<0||c>=5||r<0||r>=3||!Number.isInteger(value))continue;
   const label=this.add.text((c+.5)*CW+CW*.23,
    (r+.5)*CH+CH*.28,'×'+value,{
     fontFamily:'Georgia,serif',fontSize:'38px',fontStyle:'bold',
     color:'#fff4b6',stroke:'#5a193c',strokeThickness:9,
     shadow:{offsetX:1,offsetY:2,color:'#1d0d23',blur:5,fill:true}
    });
   label.setOrigin(.5).setDepth(23);
   if(this.reelMask)label.setMask(this.reelMask);
   this.wildBadges.push(label);
  }
 }
 setLowEffects(value){this.liteEffects=!!value}
 trackFx(shape){this.fxObjects.add(shape);return shape}
 disposeFx(shape){
  this.fxObjects.delete(shape);
  if(shape?.active)shape.destroy();
 }
 clearFx(){
  for(const shape of this.fxObjects)if(shape?.active)shape.destroy();
  this.fxObjects.clear();
 }
 burst(cells,{destroy=true,duration=690}={}){
  // Todas as peças pertencentes a linhas premiadas brilham no MESMO
  // instante. Uma figura partilhada por duas linhas só explode uma vez.
  // Quando não há nova queda, apenas pulsa: não desaparece do tabuleiro.
  if(!Array.isArray(cells)||!cells.length)return Promise.resolve();
  const seen=new Set(),targets=[];
  for(const {c,r} of cells){
   if(!Number.isInteger(c)||!Number.isInteger(r)||
     c<0||c>=5||r<0||r>=3)continue;
   const id=c+','+r;
   if(seen.has(id))continue;
   seen.add(id);
   const sprite=this.getVisible(c,r);
   if(sprite)targets.push({c,r,sprite});
  }
  if(!targets.length)return Promise.resolve();
  const particleCount=burstParticleBudget(targets.length,this.liteEffects);
  const hues=[0xffd77b,0xff98de,0xc5a1ff,0x91f1db];
  const time=Math.max(160,duration);
  return new Promise(resolve=>{
   let finished=0;
   for(const {c,r,sprite} of targets){
    const x=sprite.x,y=sprite.y;
    const baseX=sprite.scaleX,baseY=sprite.scaleY;
    const halo=this.trackFx(this.add.circle(x,y,Math.min(CW,CH)*.31,
      0xffd887,.17).setDepth(11));
    halo.setStrokeStyle?.(5,0xffefab,.95);
    halo.setMask?.(this.reelMask);
    this.tweens.add({
     targets:halo,scale:1.78,alpha:0,
     duration:time*.88,ease:'Cubic.easeOut',
     onComplete:()=>this.disposeFx(halo)
    });
    if(!this.liteEffects){
     const inner=this.trackFx(this.add.circle(x,y,Math.min(CW,CH)*.18,
       0xffefff,.32).setDepth(12));
     inner.setMask?.(this.reelMask);
     this.tweens.add({
      targets:inner,scale:2.15,alpha:0,
      duration:time*.64,ease:'Sine.easeOut',
      onComplete:()=>this.disposeFx(inner)
     });
    }
    for(let i=0;i<particleCount;i++){
     const angle=(i/particleCount)*Math.PI*2+(c*3+r)*.35;
     const distance=46+(i%3)*12+Math.random()*26;
     const spark=this.trackFx(this.add.circle(x,y,3.2+(i%2)*2.2,
       hues[(i+c+r)%hues.length],.98).setDepth(18));
     spark.setMask?.(this.reelMask);
     this.tweens.add({
      targets:spark,
      x:x+Math.cos(angle)*distance,
      y:y+Math.sin(angle)*distance+20,
      alpha:0,scale:.25,duration:time*.93,
      ease:'Cubic.easeOut',
      onComplete:()=>this.disposeFx(spark)
     });
    }
    // Dissipar apenas quando EXISTE uma nova queda: as restantes
    // peças permanecem no sítio e os tambores continuam aprovados.
    this.tweens.add({
     targets:sprite,
     scaleX:destroy?baseX*.06:baseX*1.11,
     scaleY:destroy?baseY*.06:baseY*1.11,
     alpha:destroy?0:1,
     duration:destroy?time*.77:time*.46,
     ease:destroy?'Back.easeIn':'Sine.easeInOut',
     yoyo:!destroy,
     onComplete:()=>{
      if(!destroy){
       sprite.setScale?.(baseX,baseY);
       sprite.setAlpha?.(1);
      }
      if(++finished===targets.length)resolve();
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
  this.clearWildValues();
  this.tweens.killAll();
  // Se o Safari interromper os tweens, callbacks de destruição não correm.
  // Libertar halos/sparkles temporários antes de reconstruir as peças.
  this.clearFx();
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
