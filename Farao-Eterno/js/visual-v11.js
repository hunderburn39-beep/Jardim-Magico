/* Faraó Eterno — visual 1.1: real reel-bound tiles and sculpted Egyptian controls.
   Presentation only: no RNG, payments, RTP, session or spin timing changes. */
(() => {
 'use strict';
 const game=window.__faraoGame, canvas=document.getElementById('canvas');
 if(!game || !canvas) return;
 const ctx=canvas.getContext('2d');
 // Moldura quase até às margens da tela lógica 576×1024 (9:16).
 // Cinco tambores de 105 px, com 4 px de espaço entre eles.
 const R=Object.freeze({left:18,top:284,cw:105,gh:129,step:109});
 const reducedMotion=window.matchMedia?.('(prefers-reduced-motion: reduce)').matches||false;
 const clamp=(n,a,b)=>Math.max(a,Math.min(b,n));
 const TAU=Math.PI*2;
 const format=n=>(Math.round((n+Number.EPSILON)*100)/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 const color={gold:'#f5cc77',bright:'#ffeec0',teal:'#76eddd'};
 const grad=(c,x1,y1,x2,y2,stops)=>{const g=c.createLinearGradient(x1,y1,x2,y2);for(const [p,v] of stops)g.addColorStop(p,v);return g;};
 const radial=(c,x,y,r,stops)=>{const g=c.createRadialGradient(x,y,0,x,y,r);for(const [p,v] of stops)g.addColorStop(p,v);return g;};
 function rounded(c,x,y,w,h,r,fill,stroke,lw=1){
  c.beginPath();c.roundRect(x,y,w,h,r);
  if(fill){c.fillStyle=fill;c.fill();}
  if(stroke){c.strokeStyle=stroke;c.lineWidth=lw;c.stroke();}
 }
 function line(c,x1,y1,x2,y2,col,w=1){
  c.beginPath();c.moveTo(x1,y1);c.lineTo(x2,y2);c.strokeStyle=col;c.lineWidth=w;c.stroke();
 }
 function disk(c,x,y,r,fill,stroke,lw=1){
  c.beginPath();c.arc(x,y,r,0,TAU);if(fill){c.fillStyle=fill;c.fill();}if(stroke){c.strokeStyle=stroke;c.lineWidth=lw;c.stroke();}
 }
 function txt(str,x,y,size,col,weight=800,font='Georgia'){
  ctx.save();ctx.textAlign='center';ctx.textBaseline='middle';
  ctx.font=weight+' '+size+'px '+font+',serif';ctx.fillStyle=col;
  ctx.shadowColor='#000e';ctx.shadowBlur=3;ctx.shadowOffsetY=2;
  ctx.fillText(str,x,y);ctx.restore();
 }
 function asset(w,h,paint){
  const el=document.createElement('canvas');el.width=w*2;el.height=h*2;
  const c=el.getContext('2d');c.scale(2,2);paint(c);return el;
 }
 // Transparent tiles: the entire piece, including its gold square, moves with the reel.
 // Cache the ornamental frame so iPhone/WebKit does not recreate gradients each frame.
 const tile=asset(R.cw,R.gh,c=>{
  const square=102,y=(R.gh-square)/2,x=(R.cw-square)/2;
  c.save();c.shadowColor='#000a';c.shadowBlur=4;
  rounded(c,x,y,square,square,10,grad(c,x,y,x+square,y+square,
    [[0,'#13263831'],[.5,'#081c332c'],[1,'#06152450']]),'#d99c43d0',2.3);
  c.restore();
  rounded(c,x+3,y+3,square-6,square-6,7,null,'#ffdc8e7c',1);
  rounded(c,x+6,y+6,square-12,square-12,5,null,'#5eacaa50',.9);
  // Embossed corners and tiny turquoise inlays without an opaque tile background.
  for(const xx of [x+7,x+square-7])for(const yy of [y+7,y+square-7]){
   disk(c,xx,yy,1.9,'#54c5c1', '#f8df9b',.55);
  }
  c.save();c.globalAlpha=.35;
  line(c,x+19,y+4,x+square-19,y+4,'#fff2c1',1.15);
  line(c,x+19,y+square-4,x+square-19,y+square-4,'#d69b51',1.1);
  c.restore();
 });
 // Ornate circular spin medallion, cached at double resolution.
 const medallion=asset(148,148,c=>{
  const x=74,y=74;
  c.save();c.shadowColor='#edb34b';c.shadowBlur=15;
  disk(c,x,y,64,grad(c,6,12,145,142,[[0,'#ffe9a1'],[.19,'#bd8032'],[.45,'#734018'],[.73,'#f5ba47'],[1,'#59351c']]),'#ffe1a0',3);
  c.restore();
  disk(c,x,y,59,radial(c,55,38,93,[[0,'#fff3bb'],[.3,'#e5ad4b'],[.72,'#8e4d1a'],[1,'#3c291f']]),'#74451f',2.5);
  disk(c,x,y,52,grad(c,15,10,132,138,[[0,'#f6ce6b'],[.28,'#af6e24'],[.6,'#50371d'],[.82,'#b47b32'],[1,'#f8dc89']]),'#f8df9b',2);
  disk(c,x,y,45,radial(c,55,49,72,[[0,'#e2a844'],[.5,'#8c531e'],[1,'#49301a']]),'#905820',1.3);
  for(let a=0;a<32;a++){
   const p=a*TAU/32,s=Math.sin(p),v=Math.cos(p);
   line(c,x+v*54,y+s*54,x+v*(a%4===0?59:57),y+s*(a%4===0?59:57),'#f9d187',a%4===0?1.5:.75);
  }
  for(const p of [Math.PI*.47,Math.PI*1.47]){
   disk(c,x+Math.cos(p)*61,y+Math.sin(p)*61,4.5,'#17aaad','#f8e6a4',1.5);
  }
  c.save();c.strokeStyle='#f5d08a';c.lineWidth=1;c.globalAlpha=.5;
  c.beginPath();c.arc(x,y,40,.17,Math.PI*1.05);c.stroke();c.restore();
 });
 // Names stay accessible to the established HTML touch targets.
 function drawMedallion(label,disabled,t){
  const cx=280,cy=878,r=65;
  ctx.save();
  if(disabled)ctx.globalAlpha=.57;
  const pulse=1+(!game.spinning&&!game.paused?.011*Math.sin(t*.004):0);
  ctx.translate(cx,cy);ctx.scale(pulse,pulse);ctx.translate(-cx,-cy);
  ctx.drawImage(medallion,cx-74,cy-74,148,148);
  ctx.restore();
  ctx.save();ctx.translate(cx,cy);
  ctx.shadowBlur=7;ctx.shadowColor='#fff0ac';
  ctx.lineWidth=9;ctx.strokeStyle='#ffe5a0';ctx.lineCap='round';
  if(label==='PARAR'){
   rounded(ctx,-20,-20,13,40,3,'#ffe6a1','#b87932',1);
   rounded(ctx,7,-20,13,40,3,'#ffe6a1','#b87932',1);
  }else if(label==='RETOMAR'){
   ctx.beginPath();ctx.moveTo(-13,-22);ctx.lineTo(25,0);ctx.lineTo(-13,22);ctx.closePath();
   ctx.fillStyle='#ffe6a1';ctx.fill();ctx.strokeStyle='#a86a28';ctx.lineWidth=2;ctx.stroke();
  }else{
   for(const [start,end] of [[-2.52,-.13],[.62,3.02]]){
    ctx.beginPath();ctx.arc(0,0,26,start,end);ctx.stroke();
    const px=26*Math.cos(end),py=26*Math.sin(end);
    ctx.beginPath();ctx.moveTo(px,py);
    ctx.lineTo(px-14*Math.cos(end-.58),py-14*Math.sin(end-.58));
    ctx.lineTo(px-14*Math.cos(end+.58),py-14*Math.sin(end+.58));
    ctx.closePath();ctx.fillStyle='#ffe6a1';ctx.fill();
   }
  }
  ctx.restore();
 }
 function drawScarab(c,x,y,s=1){
  c.save();c.translate(x,y);c.scale(s,s);
  // Egyptian winged scarab: jewelled body and gold feather sweeps.
  for(const side of [-1,1]){
   c.save();c.scale(side,1);c.beginPath();
   c.moveTo(0,2);c.bezierCurveTo(15,-9,29,-9,40,-3);
   c.bezierCurveTo(23,1,16,8,3,8);c.closePath();
   c.fillStyle=grad(c,0,-9,40,13,[[0,'#fff0ae'],[.4,'#9a5d23'],[1,'#e5ac4d']]);c.fill();
   for(let i=0;i<4;i++)line(c,8+i*7,0,13+i*7,5,'#295869',.95);
   c.restore();
  }
  disk(c,0,2,8,radial(c,-3,-2,13,[[0,'#b5ffff'],[.3,'#27bdbe'],[.8,'#116a71'],[1,'#083e52']]),'#ffdb85',2);
  disk(c,0,-7,3.5,'#dab75d','#ffe6a9',.8);
  c.restore();
 }
 function drawReelShell(c){
  const xx=R.left+c*R.step,bot=R.top+R.gh*3;
  // Cylinder walls: narrow fixed gold ribs; transparent central channel.
  for(const [x,sgn] of [[xx-1,1],[xx+R.cw-2,-1]]){
   const wall=grad(ctx,x-1,0,x+3,0,[[0,'#25171b'],[.24,'#ad692b'],[.44,'#ffe6a3'],[.77,'#8f592e'],[1,'#2b1e1c']]);
   rounded(ctx,x,R.top-13,3,bot-R.top+24,1.5,wall,'#d69b50a0',.8);
   line(ctx,x+sgn*2,R.top+23,x+sgn*2,bot-20,'#6be4d12f',.9);
  }
  ctx.save();ctx.shadowColor='#f4ba55';ctx.shadowBlur=5;
  rounded(ctx,xx-2,R.top-21,R.cw+4,21,8,grad(ctx,0,R.top-21,0,R.top,
   [[0,'#42271c'],[.18,'#ffe39c'],[.49,'#c78d37'],[.76,'#654126'],[1,'#301f1e']]),'#ffe9a4',1.8);
  ctx.restore();
  rounded(ctx,xx-2,bot-1,R.cw+4,23,8,grad(ctx,0,bot-1,0,bot+22,
   [[0,'#fff0b7'],[.14,'#8f5724'],[.44,'#513420'],[.68,'#d1a24a'],[1,'#30201a']]),'#eac277',1.5);
  drawScarab(ctx,xx+R.cw/2,R.top-10,.67);
  drawScarab(ctx,xx+R.cw/2,bot+10,.76);
  // Linha horizontal decorativa superior removida a pedido.
  line(ctx,xx+11,bot+16,xx+R.cw-11,bot+16,'#bb8f49',1.1);
 }
 function tileAt(sym,x,y,t,win){
  // As pirâmides são desenhadas depois dos restantes símbolos,
  // numa camada única que permite sobrepor as imagens vizinhas.
  if(sym==='TOMB')return;
  game.drawIcon(sym,x,y,R.cw,R.gh,win,t);
 }
 game.visualV11={layout:R,tile,medallion,version:'1.2-wide-reels-9x16'};
 game.drawReelSeparators=function(left,top,interval,gh){
  for(let c=1;c<5;c++){
   const x=left+c*interval-(interval-R.cw)/2;
   ctx.save();ctx.shadowColor='#edc47c';ctx.shadowBlur=5;
   line(ctx,x,top+3,x,top+gh*3-3,'#e8c581bd',1.1);
   ctx.restore();
   line(ctx,x+.6,top+6,x+.6,top+gh*3-5,'#fff2c845',.6);
  }
 };
 // Recorta apenas as margens transparentes das cartas baixas, uma vez por imagem.
 const letterBounds=new WeakMap();
 function visibleBounds(img){
  if(letterBounds.has(img))return letterBounds.get(img);
  const c=document.createElement('canvas');c.width=img.naturalWidth;c.height=img.naturalHeight;
  const cc=c.getContext('2d',{willReadFrequently:true});cc.drawImage(img,0,0);
  let box={x:0,y:0,w:c.width,h:c.height};
  try{
   const data=cc.getImageData(0,0,c.width,c.height).data;
   let minX=c.width,minY=c.height,maxX=-1,maxY=-1;
   for(let y=0;y<c.height;y+=2)for(let x=0;x<c.width;x+=2){
    if(data[(y*c.width+x)*4+3]>24){
     minX=Math.min(minX,x);minY=Math.min(minY,y);
     maxX=Math.max(maxX,x);maxY=Math.max(maxY,y);
    }
   }
   if(maxX>=minX&&maxY>=minY)box={x:minX,y:minY,w:Math.min(c.width-minX,maxX-minX+2),h:Math.min(c.height-minY,maxY-minY+2)};
  }catch(e){}
  letterBounds.set(img,box);return box;
 }
 // V1.3.52 — figuras premium com vida, preservando os WebP HD aprovados.
 // Máscara dourada pré-calculada por ficheiro: transparência original intacta
 // e nenhum filtro pesado aplicado repetidamente aos 15 símbolos.
 const livingFigures=new Set(['HORUS','MASK','ANUBIS','KING']);
 const livingGoldMasks=new WeakMap();
 function goldenSilhouette(img){
  let mask=livingGoldMasks.get(img);
  if(mask)return mask;
  const f=Math.min(1,220/Math.max(img.naturalWidth,img.naturalHeight));
  const w=Math.max(1,Math.round(img.naturalWidth*f));
  const h=Math.max(1,Math.round(img.naturalHeight*f));
  mask=document.createElement('canvas');mask.width=w;mask.height=h;
  const g=mask.getContext('2d',{alpha:true});
  g.drawImage(img,0,0,w,h);
  g.globalCompositeOperation='source-in';
  g.fillStyle='#ffe4a0';g.fillRect(0,0,w,h);
  g.globalCompositeOperation='source-over';
  livingGoldMasks.set(img,mask);
  return mask;
 }
 function drawLivingFigure(img,sym,x,y,w,h,size,t,win,paused){
  const scale=Math.min(size/img.naturalWidth,size/img.naturalHeight);
  const iw=img.naturalWidth*scale,ih=img.naturalHeight*scale;
  const px=x+(w-iw)/2,py=y+(h-ih)/2;
  // Movimento orgânico: não altera geometria nem a animação dos tambores.
  const seed=({HORUS:.4,MASK:2.1,ANUBIS:3.7,KING:5.4})[sym]+x*.017+y*.004;
  const animate=!paused&&!reducedMotion;
  const breath=animate?1.013+.018*Math.sin(t*.0022+seed):1;
  const floatY=animate?1.65*Math.sin(t*.00135+seed):0;
  const shimmer=animate?.5+.5*Math.sin(t*.0025+seed):.45;
  ctx.save();
  ctx.translate(x+w/2,y+h/2);
  ctx.scale(breath,breath);
  ctx.translate(-x-w/2,-y-h/2);
  // Aura dourada segue a silhueta da imagem, sem caixas nem fundos.
  ctx.shadowColor=win?'rgba(159,224,255,.84)':'rgba(255,187,73,.70)';
  ctx.shadowBlur=win?20:9+6*shimmer;
  ctx.drawImage(img,px,py+floatY,iw,ih);
  ctx.shadowBlur=0;
  const mask=goldenSilhouette(img);
  ctx.globalCompositeOperation='screen';
  ctx.globalAlpha=.055+.11*shimmer;
  ctx.drawImage(mask,px,py+floatY,iw,ih);
  if(animate){
   // Varre a figura com um feixe dourado, recortado à própria arte.
   const phase=(t*.00031+seed*.13)%1;
   const beamX=px+iw*(-.12+1.24*phase);
   for(const [offset,width,alpha] of [[-12,12,.12],[-3,8,.47],[8,12,.12]]){
    ctx.save();ctx.beginPath();ctx.rect(beamX+offset,py+floatY,Math.min(width,iw),ih);
    ctx.clip();
    ctx.globalAlpha=(win?1:.8)*alpha;
    ctx.drawImage(mask,px,py+floatY,iw,ih);
    ctx.restore();
   }
   // Reflexo pontual em forma de estrela: breve, com fase distinta por figura.
   const glint=Math.max(0,1-Math.abs(phase-.52)/.18);
   if(glint>.01){
    const gx=px+iw*(.20+.60*phase),gy=py+floatY+ih*.32;
    const a=glint*(win?.96:.72);
    ctx.save();ctx.globalAlpha=a;ctx.shadowColor='#ffedac';ctx.shadowBlur=8;
    ctx.strokeStyle='#fff9d2';ctx.lineWidth=1.45;ctx.beginPath();
    ctx.moveTo(gx-5,gy);ctx.lineTo(gx+5,gy);
    ctx.moveTo(gx,gy-7);ctx.lineTo(gx,gy+7);ctx.stroke();ctx.restore();
   }
  }
  ctx.restore();
 }
 // Larger icons, strictly preserving their source aspect ratio.
 game.drawIcon=function(sym,x,y,w,h,win=false,t=performance.now()){
  const img=(sym==='MASK'&&(this.currentRound%2===1)&&
    this.images.MASK_ALT?.complete&&this.images.MASK_ALT.naturalWidth)
    ?this.images.MASK_ALT:this.images[sym];
  const size=Math.min(w-4,h-4)*(sym==='TOMB'?1.0815:1);
  ctx.save();
  // As peças que efetivamente pagam brilham com uma aura azul-branca forte.
  // A aura é exclusivamente aplicada às células do conjunto premiado.
  if(win&&!reducedMotion&&sym!=='TOMB'){
   const pulse=1.055+.055*Math.sin(t*.012);
   ctx.translate(x+w/2,y+h/2);ctx.scale(pulse,pulse);ctx.translate(-x-w/2,-y-h/2);
  }
  if(win&&sym!=='TOMB'){
   const wave=reducedMotion?1:.75+.25*Math.sin(t*.014);
   const alpha=.43+.22*wave;
   ctx.save();ctx.globalCompositeOperation='screen';
   ctx.fillStyle=radial(ctx,x+w/2,y+h/2,size*.72,
    [[0,'rgba(225,250,255,'+alpha+')'],[.49,'rgba(62,170,255,'+(.29*wave)+')'],[1,'rgba(30,117,255,0)']]);
   ctx.beginPath();ctx.arc(x+w/2,y+h/2,size*.70,0,TAU);ctx.fill();
   ctx.restore();
   ctx.shadowColor='#8ddaff';ctx.shadowBlur=26+12*wave;
  }
  if(img&&img.complete&&img.naturalWidth>0&&img.naturalHeight>0){
   const lowSymbol=['10','J','Q','K','A'].includes(sym);
   if(lowSymbol){
    // Preencher quase toda a casa sem cortar letras nem deformar a imagem.
    const b=visibleBounds(img);
    const factor=Math.min((w-8)/b.w,(h-8)/b.h);
    const iw=b.w*factor,ih=b.h*factor;
    ctx.drawImage(img,b.x,b.y,b.w,b.h,x+(w-iw)/2,y+(h-ih)/2,iw,ih);
   }else if(sym==='TOMB'){
    // Pulsação permanente e suave: 100%-113% da V1.3.43.
    // A luz dourada segue o alfa da pirâmide; nunca cria fundo quadrado.
    const bounds=visibleBounds(img);
    const wave=.5+.5*Math.sin(t*.0035);
    const pulse=1+.13*wave;
    const scale=1.03*pulse*Math.min((w*1.12)/bounds.w,(h*1.42)/bounds.h);
    const iw=bounds.w*scale,ih=bounds.h*scale;
    ctx.save();
    ctx.filter='none';ctx.shadowOffsetX=0;ctx.shadowOffsetY=0;
    ctx.shadowColor='rgba(255,198,68,'+(.56+.32*wave)+')';
    ctx.shadowBlur=12+16*wave;
    ctx.drawImage(img,bounds.x,bounds.y,bounds.w,bounds.h,
      x+(w-iw)/2,y+(h-ih)/2,iw,ih);
    ctx.restore();
   }else if(sym==='SUPER'){
    // EXATAMENTE a imagem de referência do Faraó, sem máscara redesenhada.
    // Reservar espaço na base para o nome da peça, sem tapar a cara.
    const fit=Math.min((w-8)/img.naturalWidth,(h-35)/img.naturalHeight);
    const iw=img.naturalWidth*fit,ih=img.naturalHeight*fit;
    ctx.save();
    ctx.shadowColor='#f5c978';ctx.shadowBlur=reducedMotion?0:11;
    ctx.drawImage(img,x+(w-iw)/2,y+4,iw,ih);
    ctx.restore();
    const lx=x+3,ly=y+h-27,lw=w-6,lh=24;
    const banner=ctx.createLinearGradient(lx,ly,lx,ly+lh);
    banner.addColorStop(0,'#745322');
    banner.addColorStop(.25,'#111b2a');
    banner.addColorStop(1,'#09233a');
    ctx.save();
    ctx.fillStyle=banner;ctx.fillRect(lx,ly,lw,lh);
    ctx.strokeStyle='#f9d782';ctx.lineWidth=1.6;ctx.strokeRect(lx,ly,lw,lh);
    ctx.textAlign='center';ctx.textBaseline='middle';
    ctx.shadowColor='#f4d27b';ctx.shadowBlur=5;ctx.fillStyle='#fff1c3';
    ctx.font='900 11px Georgia,serif';ctx.fillText('SUPER',x+w/2,ly+7);
    ctx.font='900 9px Georgia,serif';ctx.fillText('SCATTER',x+w/2,ly+17);
    ctx.restore();
   }else if(livingFigures.has(sym)){
    drawLivingFigure(img,sym,x,y,w,h,size,t,win,this.spinning);
   }else{
    const scale=Math.min(size/img.naturalWidth,size/img.naturalHeight);
    const iw=img.naturalWidth*scale,ih=img.naturalHeight*scale;
    ctx.drawImage(img,x+(w-iw)/2,y+(h-ih)/2,iw,ih);
   }
  }else{
   // Safari ainda a receber a imagem: mostrar um marcador dourado discreto,
   // nunca os nomes internos "HORUS", "MASK", "ANUBIS" na grelha.
   ctx.save();
   ctx.shadowColor='#edba62';ctx.shadowBlur=8;
   const r=Math.min(w,h)*.32;
   ctx.fillStyle=radial(ctx,x+w/2,y+h/2,r,[[0,'#6c4b2bb8'],[1,'#2c271d35']]);
   ctx.beginPath();ctx.arc(x+w/2,y+h/2,r,0,TAU);ctx.fill();
   ctx.restore();
   const low=['10','J','Q','K','A'].includes(sym);
   txt(low?sym:'✦',x+w/2,y+h/2,low?30:36,color.bright);
  }
  if(win&&sym!=='TOMB'){
   // Reflexo azul intenso nas bordas, sem iluminar figuras fora do prémio.
   const flicker=reducedMotion?1:.76+.24*Math.sin(t*.014);
   ctx.save();ctx.lineJoin='round';
   ctx.strokeStyle='rgba(195,239,255,'+(.80+.20*flicker)+')';
   ctx.shadowColor='#40adff';ctx.shadowBlur=reducedMotion?0:22;
   rounded(ctx,x+4,y+8,w-8,h-16,9,null,ctx.strokeStyle,2.6);
   ctx.restore();
  }
  ctx.restore();
 };
 // Emblema premium 3D cacheado a 2x: ouro escovado, gravura egípcia,
 // pedra lápis-lazúli e letras esculpidas. Respeita a faixa acima das peças.
 // Renderizado UMA vez, sem gradientes nem sombras pesadas a cada frame.
 const royalLogo=asset(560,46,c=>{
  const gold=grad(c,0,0,0,46,[[0,'#fff2bd'],[.14,'#d29a45'],[.39,'#7a451d'],
   [.62,'#452a20'],[.82,'#a66a2f'],[1,'#f7ce7c']]);
  c.save();
  c.shadowColor='#000e';c.shadowBlur=13;c.shadowOffsetY=7;
  rounded(c,76,3,408,40,11,gold,'#f8dc99',2.5);
  c.restore();
  // Baixo-relevo interior e duas linhas em metal, como uma peça de joalharia.
  rounded(c,81,7,398,32,8,grad(c,0,7,0,39,
   [[0,'#1a1e2d'],[.27,'#273348'],[.49,'#111b2d'],[.76,'#111622'],[1,'#442f2a']]),
   '#c99245',1.7);
  rounded(c,86,10,388,26,6,null,'#ffdd8760',.9);
  line(c,148,10,412,10,'#fff2bc72',.9);
  line(c,148,36,412,36,'#7c552db0',.9);
  // Ornamentos alados simétricos com esmalte azul e acabamento dourado.
  for(const side of [-1,1]){
   c.save();c.translate(280+side*153,23);c.scale(side,1);
   for(let i=0;i<4;i++){
    c.beginPath();c.moveTo(17,-10+i*5);
    c.quadraticCurveTo(32+i*3,-13+i*3,43,-11+i*5);
    c.quadraticCurveTo(31,3+i*2,16,3+i*3);
    c.strokeStyle=i%2?'#b78339':'#f5cd7f';
    c.lineWidth=i===0?2:1.15;c.stroke();
   }
   c.restore();
  }
  for(const x of [101,459]){
   c.save();c.shadowColor='#49d6d0';c.shadowBlur=6;
   disk(c,x,23,10,radial(c,x-4,19,14,[[0,'#83f6e8'],[.29,'#148d9e'],
    [.72,'#123b63'],[1,'#071b30']]),'#f7d487',2);
   c.restore();
   disk(c,x-2.4,19.5,2.3,'#eaffef9c');
   for(const v of [-15,15])disk(c,x+v,23,1.4,'#fce5a2');
  }
  // Logotipo com extrusão controlada: legibilidade antes de brilho exagerado.
  c.save();c.translate(280,23);
  c.textAlign='center';c.textBaseline='middle';
  c.font='900 32px Georgia,serif';c.lineJoin='round';
  const label='FARAÓ ETERNO',width=c.measureText(label).width;
  c.scale(Math.min(1,293/Math.max(1,width)),1);
  for(let d=5;d>=1;d--){
   c.lineWidth=3.5;
   c.strokeStyle=d>3?'#161018':'#75431c';
   c.strokeText(label,d*.15,d*.62);
   c.fillStyle=d>3?'#41261c':'#a57632';
   c.fillText(label,d*.15,d*.62);
  }
  c.shadowColor='#f8ca73';c.shadowBlur=3;
  c.strokeStyle='#251b19';c.lineWidth=4.6;c.strokeText(label,0,0);
  c.strokeStyle='#ffe1a0';c.lineWidth=1.45;c.strokeText(label,0,0);
  const lettering=grad(c,0,-21,0,20,[[0,'#fffae0'],[.19,'#fbe3a0'],
   [.31,'#b87b34'],[.44,'#ffefbb'],[.62,'#d29b44'],
   [.77,'#78451f'],[.91,'#edbf64'],[1,'#fff0ba']]);
  c.fillStyle=lettering;c.fillText(label,0,0);
  c.restore();
  // Filete inferior: separa o emblema do aro superior dos tambores.
  line(c,128,42,432,42,'#efd38d99',1.1);
 });
 // V1.3.49 — passagem dourada visível, limitada às letras.
 // A luz é gerada numa camada transparente independente e recortada
 // pela silhueta do texto. 'destination-in' funciona em WebKit/Safari.
 const titleShineMask=asset(560,46,c=>{
  c.save();c.translate(280,23);
  c.textAlign='center';c.textBaseline='middle';
  c.font='900 32px Georgia,serif';
  const label='FARAÓ ETERNO',width=c.measureText(label).width;
  c.scale(Math.min(1,293/Math.max(1,width)),1);
  c.fillStyle='#fff';c.fillText(label,0,0);c.restore();
 });
 const titleShineLayer=document.createElement('canvas');
 titleShineLayer.width=1120;titleShineLayer.height=92;
 const titleShineCtx=titleShineLayer.getContext('2d',{alpha:true});
 titleShineCtx.scale(2,2);
 function drawTitle3D(t){
  ctx.save();
  ctx.globalAlpha=1;
  ctx.globalCompositeOperation='source-over';
  ctx.drawImage(royalLogo,0,238,560,46);
  ctx.restore();
  if(!Number.isFinite(t)||!titleShineCtx)return;
  // Reflexo percetível a cada 3,6 s: 2,6 s de varrimento e 1 s de pausa.
  // Mesmo com "Reduzir movimento", o efeito pedido mantém-se suave.
  const phase=t%3600;
  if(phase>=2600)return;
  const progress=phase/2600;
  const centre=96+368*progress;
  const g=titleShineCtx;
  g.clearRect(0,0,560,46);
  g.globalCompositeOperation='source-over';
  // Foco branco intenso no centro, com halo dourado em redor.
  g.fillStyle=grad(g,centre-88,0,centre+88,0,[
   [0,'rgba(255,210,110,0)'],
   [.20,'rgba(255,229,148,0.17)'],
   [.33,'rgba(255,242,192,0.48)'],
   [.43,'rgba(255,255,244,0.98)'],
   [.50,'rgba(255,255,255,1)'],
   [.57,'rgba(255,255,244,0.98)'],
   [.67,'rgba(255,239,185,0.50)'],
   [.80,'rgba(255,220,142,0.17)'],
   [1,'rgba(255,210,110,0)']
  ]);
  g.fillRect(0,0,560,46);
  g.globalCompositeOperation='destination-in';
  g.drawImage(titleShineMask,0,0,560,46);
  g.globalCompositeOperation='source-over';
  ctx.save();
  ctx.globalCompositeOperation='screen';
  ctx.globalAlpha=1;
  ctx.drawImage(titleShineLayer,0,238,560,46);
  ctx.restore();
 }
 game.reelBoard=function(t){
  // Durante a apresentação, apenas a sequência em curso pode brilhar.
  const pulseCells=new Set();
  const focus=this.sequenceFocusAt?.(t)||null;
  const wins=!this.spinning&&(!this.reveal||t>=this.reveal.end)&&(!this.featureIntro||t>=this.featureIntro.end)
   ?this.lastResult?.lineWins:null;
  const baseLine=!this.lastWasFree?this.activeBasePaylineAt?.(t):null;
  const chosen=baseLine?.win||(focus
    ?focus.kind==='line'?focus.win:
      focus.kind==='expansion'?this.lastResult?.lineWins?.find(w=>w.expanded&&w.symbol===focus.symbol):
      null
    :this.lastWasFree&&wins?.length?wins[Math.floor(t/1600)%wins.length]:null);
  if(chosen){
   if(chosen.expanded){
    const expander=this.lastResult.expanders.find(e=>e.symbol===chosen.symbol);
    for(const reel of expander?.reels||[])for(let row=0;row<3;row++)pulseCells.add(reel+'-'+row);
   }else for(let reel=0;reel<chosen.count;reel++){
     // Nas Free Spins, a peça só começa a pulsar quando a sua posição
     // é efetivamente revelada, sincronizada com o som da pedra.
     const lineStage=this.lastWasFree&&focus?.kind==='line'?focus:null;
     if(lineStage&&t<lineStage.start+lineStage.preDelay+
        (reel+1)*lineStage.perReel)continue;
     pulseCells.add(reel+'-'+chosen.rows[reel]);
   }
  }
  // Mostrar uma figura expansiva de cada vez, pelo valor crescente.
  const phase=this.expansionView?.(t);
  const phaseReveal=phase?.reveal||(this.reveal?.stages?null:this.reveal);
  let phaseExpanded=phase?.expanded||this.expanded;
  if(this.reveal?.stages?.length && !phase && !this.spinning){
   const shown=wins?.length?wins[Math.floor(t/1600)%wins.length]:null;
   const candidate=shown?.expanded?this.lastResult.expanders?.find(e=>e.symbol===shown.symbol):null;
   phaseExpanded=candidate?Object.fromEntries(candidate.reels.map(reel=>[reel,candidate])):{};
  }
  const x=R.left-2,y=R.top-2,w=4*R.step+R.cw+4,h=R.top+3*R.gh-y+10;
  ctx.save();ctx.shadowColor='#000c';ctx.shadowBlur=16;ctx.shadowOffsetY=6;
  rounded(ctx,x-5,y-7,w+10,h+10,15,null,'#deb56b',3.7);ctx.restore();
  rounded(ctx,x-1,y-5,w+2,h+5,13,null,'#745027a9',1.5);
  for(let c=0;c<5;c++){
   const rx=R.left+c*R.step,top=R.top,gh=R.gh,cw=R.cw;
   ctx.save();ctx.beginPath();ctx.rect(rx,top,cw,gh*3);ctx.clip();
   const item=this.spinObject;
   if(this.spinning&&item&&!item.stopped[c]){
    const p=clamp((t-item.start)/((item.stopAt?.[c]??(item.start+item.duration+c*item.reelGap))-item.start),0,1),a=.12,d=.32;
    let travel;
    if(p<a)travel=.5*p-.5*a/Math.PI*Math.sin(Math.PI*p/a);
    else if(p<=1-d)travel=p-a/2;
    else{const u=p-(1-d);travel=1-d-a/2+.5*u+.5*d/Math.PI*Math.sin(Math.PI*u/d);}
    const dist=item.steps[c]*travel/(1-(a+d)/2),step=Math.floor(dist),fraction=dist-step;
    // Motion blur vertical apenas nos tambores em movimento.
    // O fundo e a moldura ficam nítidos; o blur desaparece em cada paragem.
    const velocity=Math.min(1,Math.max(0,(p<.12?p/.12:p>.82?(1-p)/.18:1)));
    // Desfoque de movimento dos símbolos, sem linhas verticais artificiais.
    // Três passagens suaves criam arrastamento contínuo, sem sobrecarregar o Safari.
    const smear=velocity*28;
    for(const [offset,alpha] of [[-smear,.22],[0,.56],[smear,.22]]){
     ctx.save();
     ctx.globalAlpha=alpha;
     if('filter' in ctx)ctx.filter='blur('+(5+velocity*11).toFixed(1)+'px)';
     for(let r=-1;r<4;r++){
      const sym=item.show[c][item.steps[c]+3+r-step]||item.grid[c][clamp(r,0,2)];
      // A pirâmide também se mantém nítida durante o giro: uma única
      // passagem central sem os rastos e o filtro de movimento.
      if(sym==='TOMB'){
       if(offset!==0)continue;
       ctx.save();ctx.filter='none';ctx.globalAlpha=1;
       this.drawIcon(sym,rx,top+(r+fraction)*gh,R.cw,R.gh,false,t);
       ctx.restore();
      }else this.drawIcon(sym,rx,top+(r+fraction)*gh+offset,R.cw,R.gh,false,t);
     }
     ctx.restore();
    }
   }else{
    const grid=item&&item.stopped[c]?item.grid:this.grid;
    const ms=t-this.landings[c];
    const landing=this.landings[c]&&ms>=0&&ms<300;
    const bounce=landing?-8*Math.pow(1-ms/300,2)*Math.cos(ms*.033):0;
    const idx=phaseReveal?phaseReveal.reels.indexOf(c):-1;
    const stamp=phaseReveal?phaseReveal.start+phaseReveal.preDelay+idx*phaseReveal.perReel:0;
    const raw=phaseReveal&&idx>=0?clamp((t-stamp)/phaseReveal.perReel,0,1):1;
    const progress=raw*raw*(3-2*raw);
    const expanding=!!(phaseExpanded[c]&&(phaseReveal?idx>=0&&progress>0:this.lastWasFree));
    const ready=!!focus||!this.reveal||t>=this.reveal.end-100;
    const lineStage=this.lastWasFree&&focus?.kind==='line'&&focus.win?focus:null;
    for(let r=0;r<3;r++){
     const yy=top+r*gh,win=ready&&pulseCells.has(c+'-'+r);
     // Nas linhas das Free Spins cada peça vencedora cai na sua casa
     // quando é revelada; a pedra assenta com um ressalto suave.
     const hitAt=lineStage?.win&&c<lineStage.win.count&&r===lineStage.win.rows[c]
       ?lineStage.start+lineStage.preDelay+(c+1)*lineStage.perReel:-1;
     const elapsed=hitAt>=0?t-hitAt:-1;
     const settling=elapsed>=0&&elapsed<290;
     const drop=settling?-25*Math.pow(1-elapsed/290,2)*Math.cos(elapsed*.032):0;
     const ease=expanding?progress:0;
     if(ease<1){
      ctx.save();ctx.globalAlpha=1-ease;
      tileAt(grid[c][r],rx,yy+bounce+drop,t,win&&!expanding);ctx.restore();
     }
     if(expanding&&ease>0){
      ctx.save();ctx.globalAlpha=ease;
      const pulse=1+.035*Math.sin(Math.PI*ease);
      ctx.translate(rx+cw/2,yy+gh/2);ctx.scale(pulse,pulse);ctx.translate(-rx-cw/2,-yy-gh/2);
      tileAt(phaseExpanded[c].symbol,rx,yy,t,win);ctx.restore();
      ctx.save();ctx.shadowColor='#68ffe5';ctx.shadowBlur=10;ctx.strokeStyle='rgba(133,255,225,'+(.35+.55*ease)+')';ctx.lineWidth=2;
      rounded(ctx,rx+3,yy+10,cw-6,gh-20,8,null,ctx.strokeStyle,2);
      ctx.restore();
     }
    }
    // As pirâmides são desenhadas depois de todos os tambores.
   }
   // A very short transient glow when the real reel stops.
   const since=t-this.landings[c];
   if(this.landings[c]&&since>=0&&since<240){
    ctx.save();ctx.globalAlpha=.14*(1-since/240);
    ctx.fillStyle=grad(ctx,rx,0,rx+cw,0,[[0,'#fff1c900'],[.5,'#fff1c9aa'],[1,'#fff1c900']]);
    ctx.fillRect(rx,top,cw,gh*3);ctx.restore();
   }
   ctx.restore();
  }
  // Fixed carved metal outside the spinning tiles. No stationary square overlays.
  for(let c=0;c<5;c++)drawReelShell(c);
  this.drawReelSeparators(R.left,R.top,R.step,R.gh);
  // Sobreposição real: uma única camada por cima das 15 imagens.
  // Limitar só à moldura exterior, nunca a cada casa ou tambor.
  ctx.save();ctx.beginPath();
  ctx.rect(R.left-2,R.top-5,4*R.step+R.cw+4,R.gh*3+10);
  ctx.clip();
  for(let c=0;c<5;c++){
   const item=this.spinObject;
   if(this.spinning&&item&&!item.stopped[c])continue;
   const grid=item&&item.stopped[c]?item.grid:this.grid;
   const idx=phaseReveal?phaseReveal.reels.indexOf(c):-1;
   const stamp=phaseReveal?phaseReveal.start+phaseReveal.preDelay+idx*phaseReveal.perReel:0;
   const raw=phaseReveal&&idx>=0?clamp((t-stamp)/phaseReveal.perReel,0,1):1;
   const progress=raw*raw*(3-2*raw);
   const expanding=!!(phaseExpanded[c]&&(phaseReveal?idx>=0&&progress>0:this.lastWasFree));
   const ease=expanding?progress:0;
   const ready=!!focus||!this.reveal||t>=this.reveal.end-100;
   const since=this.landings[c]&&t-this.landings[c];
   const bounce=this.landings[c]&&since>=0&&since<300
     ?-8*Math.pow(1-since/300,2)*Math.cos(since*.033):0;
   for(let r=0;r<3;r++){
    const base=grid[c][r]==='TOMB';
    const expanded=expanding&&phaseExpanded[c].symbol==='TOMB';
    if(!base&&!expanded)continue;
    const alpha=expanded?ease:1-ease;
    if(alpha<=0)continue;
    const yy=R.top+r*R.gh;
    ctx.save();ctx.globalAlpha=alpha;
    this.drawIcon('TOMB',R.left+c*R.step,yy+(R.gh-96)/2+
     (expanded?0:bounce),R.cw,96,ready&&pulseCells.has(c+'-'+r),t);
    ctx.restore();
   }
  }
  ctx.restore();
  this.drawActivePayline(t,R.left,R.top,R.cw,R.gh,R.step);
  // Mostrar os prémios pelo brilho das peças, sem qualquer linha de varrimento.
  drawTitle3D(t); // Sempre à frente da moldura e atrás dos ícones de prémio.
  ctx.save();ctx.shadowColor='#ffdd84';ctx.shadowBlur=7;
  rounded(ctx,R.left-1,R.top+R.gh*3+8,4*R.step+R.cw+2,9,4,
   grad(ctx,0,R.top+R.gh*3+8,0,R.top+R.gh*3+17,
   [[0,'#ffefaf'],[.43,'#ae782c'],[1,'#452d22']]),'#ffe09a',1.5);
  ctx.restore();
  // Os expansivos ficam na faixa inferior, junto ao botão COMPRAR BÓNUS.
 };
 // Remove the duplicate full-width prize rectangle. Only these three meters remain.
 game.statusBar=function(t){
  // A contagem acompanha cada figura, nunca antecipa o prémio final.
  const amount=this.displayWinAt?.(t)??this.lastWin;
  const displayTotal=this.feature?Math.max(0,this.feature.total-this.lastWin)+amount:amount;
  const meters=[['SALDO',format(this.balance)],['APOSTA',format(this.bet)],['PRÉMIO',format(displayTotal)]];
  for(let i=0;i<3;i++){
   const x=28+i*170,y=R.top+R.gh*3+14,w=164,h=72;
   ctx.save();ctx.shadowColor='#000d';ctx.shadowBlur=12;ctx.shadowOffsetY=5;
   rounded(ctx,x,y,w,h,12,'#18202a','#f2c478',2.7);ctx.restore();
   rounded(ctx,x+3,y+3,w-6,h-6,9,grad(ctx,x,y,x,y+h,
    [[0,'#63503b'],[.15,'#23212c'],[.72,'#121d2d'],[1,'#332723']]),'#88633b',1);
   line(ctx,x+19,y+28,x+w-19,y+28,'#cba36277',1);
   txt(meters[i][0],x+w/2,y+18,12,'#eac88c',850,'Arial');
   const n=meters[i][1],size=n.length>13?16:n.length>10?18:23;
   txt(n,x+w/2,y+55,size,i===2?'#8dfff0':'#ffedbf');
  }
 };
 game.controls=function(){
  const disabled=this.spinning||!!this.feature||this.superseded;
  const now=performance.now();
  const presenting=!!(this.spinning||this.ledger.payout||
    (this.sequencePlayback&&now<this.sequencePlayback.end)||
    (this.basePaylineDisplay?.length&&now<this.basePaylineDisplay.at(-1).end)||
    (this.featureIntro&&now<this.featureIntro.end)||
    (this.reveal&&now<this.reveal.end)||
    (this.winCount&&now<this.winCount.start+this.winCount.duration));
  const label=this.pendingSummary?'VER TOTAL':this.paused?'RETOMAR':
    presenting?'AVANÇAR':'GIRAR';
  drawMedallion(label,!this.ready||this.superseded,now);
  if(this.ready&&!this.superseded)this.action(215,813,130,130,()=>this.advanceStage(),label);
  // Nas Free Spins automáticas, não sugerir toques que não fazem nada.
  // Quando a carta acaba de ser revelada, orientar o giro manual aprovado.
  const manualBonusReady=!!(this.feature&&this._awaitBonusStart&&
    !this._welcome&&!this.spinning&&!this.ledger.payout&&!this.paused&&
    (!this.featureIntro||now>=this.featureIntro.end)&&
    now>=this.nextActionAt);
  const hint=this.pendingSummary?'VER TOTAL DAS FREE SPINS':
    manualBonusReady?'TOCA EM GIRAR PARA CONTINUAR':
    presenting&&!this.feature?'TOCA PARA AVANÇAR A ETAPA':'';
  if(hint)txt(hint,280,963,12,'#ffe2a2',850,'Arial');
  // Botão pequeno diretamente sob o painel SALDO (fim do painel y=757).
  // Mantém distância do botão de rodar e bloqueia compras em jogadas/bónus.
  const bonusDisabled=!this.ready||this.superseded||this.spinning||
   !!this.feature||!!this.ledger?.pending||!!this.pendingSummary||
   performance.now()<this.nextActionAt;
  const bonusesY=R.top+R.gh*3+95;
  this.drawControl(28,bonusesY,164,35,'COMPRAR BÓNUS',
   ()=>this.openBuyBonus(),{size:12,disabled:bonusDisabled});
  if(this.feature?.symbols?.length){
    ctx.save();ctx.shadowColor='#0009';ctx.shadowBlur=7;
    rounded(ctx,202,bonusesY,330,35,9,
      grad(ctx,202,bonusesY,532,bonusesY+35,[[0,'#182636'],[.52,'#192f42'],[1,'#102032']]),
      '#d4aa65',1.5);
    ctx.restore();
    txt('EXPANSIVOS',248,bonusesY+18,10,'#f1d392',850,'Arial');
    const keys=this.feature.symbols;
    const size=keys.length>7?25:28,step=keys.length>7?25:31;
    for(let i=0;i<keys.length;i++)
      this.drawMiniSymbol(keys[i],294+i*step,bonusesY+(35-size)/2,size);
  }
  txt('V2.6 RC',38,985,10,'#e5c68d',850,'Arial');
  // Mostrar o mesmo ID gravado na linha correspondente do histórico.
  // Durante a rotação, usar o ID pendente, que S.settle() guarda sem alteração.
  // Nunca inventar um número pela contagem de giros, nem acrescentar zeros.
  const recorded=this.ledger?.history?.find(h=>Number.isSafeInteger(h.id));
  const spinId=this.ledger?.pending?.id ?? recorded?.id ?? this.ledger?.last?.id;
  ctx.save();
  ctx.textAlign='right';ctx.textBaseline='middle';
  ctx.font='800 11px Arial,sans-serif';
  ctx.fillStyle='#f8d78c';
  ctx.shadowColor='#000e';ctx.shadowBlur=3;ctx.shadowOffsetY=2;
  ctx.fillText('JOGADA '+(Number.isSafeInteger(spinId)&&spinId>0?'#'+spinId:'—'),526,985);
  ctx.restore();
  if(!this.storageOK)txt('SESSÃO SEM GRAVAÇÃO',280,833,11,'#ffbc83',800,'Arial');
 };
 document.documentElement.dataset.faraoVisual='1.1';
})();
