/* Faraó Eterno — visual 1.1: real reel-bound tiles and sculpted Egyptian controls.
   Presentation only: no RNG, payments, RTP, session or spin timing changes. */
(() => {
 'use strict';
 const game=window.__faraoGame, canvas=document.getElementById('canvas');
 if(!game || !canvas) return;
 const ctx=canvas.getContext('2d');
 const R=Object.freeze({left:33,top:268,cw:99,gh:116,step:101});
 const clamp=(n,a,b)=>Math.max(a,Math.min(b,n));
 const TAU=Math.PI*2;
 const format=n=>(Math.round((n+Number.EPSILON)*100)/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' cr';
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
  const y=(R.gh-96)/2,x=(R.cw-96)/2;
  c.save();c.shadowColor='#000a';c.shadowBlur=4;
  rounded(c,x,y,96,96,10,grad(c,x,y,x+96,y+96,
    [[0,'#13263831'],[.5,'#081c332c'],[1,'#06152450']]),'#d99c43d0',2.3);
  c.restore();
  rounded(c,x+3,y+3,90,90,7,null,'#ffdc8e7c',1);
  rounded(c,x+6,y+6,84,84,5,null,'#5eacaa50',.9);
  // Embossed corners and tiny turquoise inlays without an opaque tile background.
  for(const xx of [x+7,x+89])for(const yy of [y+7,y+89]){
   disk(c,xx,yy,1.9,'#54c5c1', '#f8df9b',.55);
  }
  c.save();c.globalAlpha=.35;
  line(c,x+19,y+4,x+77,y+4,'#fff2c1',1.15);
  line(c,x+19,y+92,x+77,y+92,'#d69b51',1.1);
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
  for(const [x,sgn] of [[xx-2,1],[xx+R.cw-1,-1]]){
   const wall=grad(ctx,x-2,0,x+5,0,[[0,'#25171b'],[.24,'#ad692b'],[.44,'#ffe6a3'],[.77,'#8f592e'],[1,'#2b1e1c']]);
   rounded(ctx,x,R.top-10,5,bot-R.top+19,2,wall,'#d69b50a0',.8);
   line(ctx,x+sgn*3,R.top+9,x+sgn*3,bot-7,'#6be4d12f',.9);
  }
  ctx.save();ctx.shadowColor='#f4ba55';ctx.shadowBlur=5;
  rounded(ctx,xx-3,R.top-17,105,21,8,grad(ctx,0,R.top-17,0,R.top+4,
   [[0,'#42271c'],[.18,'#ffe39c'],[.49,'#c78d37'],[.76,'#654126'],[1,'#301f1e']]),'#ffe9a4',1.8);
  ctx.restore();
  rounded(ctx,xx-3,bot-3,105,23,8,grad(ctx,0,bot-3,0,bot+20,
   [[0,'#fff0b7'],[.14,'#8f5724'],[.44,'#513420'],[.68,'#d1a24a'],[1,'#30201a']]),'#eac277',1.5);
  drawScarab(ctx,xx+R.cw/2,R.top-6,.67);
  drawScarab(ctx,xx+R.cw/2,bot+8,.76);
  // Linha horizontal decorativa superior removida a pedido.
  line(ctx,xx+11,bot+14,xx+R.cw-11,bot+14,'#bb8f49',1.1);
 }
 function tileAt(sym,x,y,t,win){
  ctx.drawImage(tile,x,y,R.cw,R.gh);
  game.drawIcon(sym,x,y,R.cw,R.gh,win,t);
  // Nas cartas baixas, desenhar a moldura POR CIMA da letra ampliada,
  // para o quadrado continuar visível mesmo quando a letra ocupa toda a casa.
  if(['10','J','Q','K','A'].includes(sym)){
   const bx=x+(R.cw-96)/2,by=y+(R.gh-96)/2;
   ctx.save();ctx.shadowColor='#eab95b';ctx.shadowBlur=3;
   rounded(ctx,bx,by,96,96,10,null,'#f1bf6b',2.8);
   rounded(ctx,bx+3,by+3,90,90,7,null,'#ffe4a3aa',1.2);
   ctx.restore();
  }
 }
 game.visualV11={layout:R,tile,medallion,version:'1.1'};
 game.drawReelSeparators=function(left,top,interval,gh){
  for(let c=1;c<5;c++){
   const x=left+c*interval-1;
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
 // Larger icons, strictly preserving their source aspect ratio.
 game.drawIcon=function(sym,x,y,w,h,win=false,t=performance.now()){
  const img=this.images[sym],size=Math.min(w-1,h-8)*.985;
  ctx.save();
  if(win){
   const alpha=.20+.14*Math.sin(t*.009);
   ctx.fillStyle=radial(ctx,x+w/2,y+h/2,size*.62,[[0,'rgba(255,235,161,'+alpha+')'],[1,'rgba(255,211,101,0)']]);
   ctx.beginPath();ctx.arc(x+w/2,y+h/2,size*.59,0,TAU);ctx.fill();
   ctx.shadowColor='#f4c76b';ctx.shadowBlur=10;
  }
  if(img&&img.complete&&img.naturalWidth>0&&img.naturalHeight>0){
   const lowSymbol=['10','J','Q','K','A'].includes(sym);
   if(lowSymbol){
    // Preencher quase toda a casa sem cortar letras nem deformar a imagem.
    const b=visibleBounds(img),zoom=.88;
    const factor=Math.min(w/b.w,h/b.h)*zoom;
    const iw=b.w*factor,ih=b.h*factor;
    ctx.save();
    ctx.beginPath();ctx.rect(x,y,w,h);ctx.clip();
    ctx.drawImage(img,b.x,b.y,b.w,b.h,x+(w-iw)/2,y+(h-ih)/2-9,iw,ih);
    ctx.restore();
   }else{
    const scale=Math.min(size/img.naturalWidth,size/img.naturalHeight);
    const iw=img.naturalWidth*scale,ih=img.naturalHeight*scale;
    ctx.drawImage(img,x+(w-iw)/2,y+(h-ih)/2-9,iw,ih);
   }
  }else txt(sym,x+w/2,y+h/2,29,color.bright);
  ctx.restore();
 };
 game.reelBoard=function(t){
  const x=20,y=250,w=520,h=R.top+3*R.gh-y+23;
  ctx.save();ctx.shadowColor='#000c';ctx.shadowBlur=16;ctx.shadowOffsetY=6;
  rounded(ctx,x-5,y-7,w+10,h+10,15,null,'#deb56b',3.7);ctx.restore();
  rounded(ctx,x-1,y-5,w+2,h+5,13,null,'#745027a9',1.5);
  for(let c=0;c<5;c++){
   const rx=R.left+c*R.step,top=R.top,gh=R.gh,cw=R.cw;
   ctx.save();ctx.beginPath();ctx.rect(rx,top,cw,gh*3);ctx.clip();
   const item=this.spinObject;
   if(this.spinning&&item&&!item.stopped[c]){
    const p=clamp((t-item.start)/(item.duration+c*item.reelGap),0,1),a=.12,d=.32;
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
      this.drawIcon(sym,rx,top+(r+fraction)*gh+offset,R.cw,R.gh,false,t);
     }
     ctx.restore();
    }
   }else{
    const grid=item&&item.stopped[c]?item.grid:this.grid;
    const ms=t-this.landings[c];
    const landing=this.landings[c]&&ms>=0&&ms<300;
    const bounce=landing?-8*Math.pow(1-ms/300,2)*Math.cos(ms*.033):0;
    const idx=this.reveal?this.reveal.reels.indexOf(c):-1;
    const stamp=this.reveal?this.reveal.start+this.reveal.preDelay+idx*this.reveal.perReel:0;
    const raw=this.reveal&&idx>=0?clamp((t-stamp)/this.reveal.perReel,0,1):1;
    const progress=raw*raw*(3-2*raw);
    const expanding=!!(this.expanded[c]&&(this.reveal?idx>=0&&progress>0:this.lastWasFree));
    const ready=!this.reveal||t>=this.reveal.end-100;
    for(let r=0;r<3;r++){
     const yy=top+r*gh,win=ready&&this.winCells.includes(c+'-'+r);
     const scan=expanding?clamp(progress*3.18-r*.93,0,1):0;
     const ease=scan*scan*(3-2*scan);
     if(ease<1){
      ctx.save();ctx.globalAlpha=1-ease;
      tileAt(grid[c][r],rx,yy+bounce,t,win&&!expanding);ctx.restore();
     }
     if(expanding&&ease>0){
      ctx.save();ctx.globalAlpha=ease;
      const pulse=1+.035*Math.sin(Math.PI*ease);
      ctx.translate(rx+cw/2,yy+gh/2);ctx.scale(pulse,pulse);ctx.translate(-rx-cw/2,-yy-gh/2);
      tileAt(this.expanded[c].symbol,rx,yy,t,win);ctx.restore();
      ctx.save();ctx.shadowColor='#68ffe5';ctx.shadowBlur=10;ctx.strokeStyle='rgba(133,255,225,'+(.35+.55*ease)+')';ctx.lineWidth=2;
      rounded(ctx,rx+3,yy+10,cw-6,gh-20,8,null,ctx.strokeStyle,2);
      if(scan<.99){ctx.fillStyle='rgba(203,255,237,'+(.65-ease*.35)+')';ctx.fillRect(rx+7,yy+gh*scan-1,cw-14,3);}
      ctx.restore();
     }
    }
    if(expanding&&this.reveal&&progress<1){
     ctx.save();ctx.shadowColor='#7afbe5';ctx.shadowBlur=16;ctx.fillStyle='#8affebad';
     ctx.fillRect(rx+5,top+gh*3*progress-4,cw-10,8);ctx.restore();
    }
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
  this.drawActivePayline(t,R.left,R.top,R.cw,R.gh,R.step);
  ctx.save();ctx.shadowColor='#ffdd84';ctx.shadowBlur=7;
  rounded(ctx,23,R.top+R.gh*3+21,514,9,4,
   grad(ctx,0,R.top+R.gh*3+21,0,R.top+R.gh*3+30,
   [[0,'#ffefaf'],[.43,'#ae782c'],[1,'#452d22']]),'#ffe09a',1.5);
  ctx.restore();
  if(this.feature?.symbols?.length){
   txt('EXPANSIVOS',105,676,11,'#eed19b',800,'Arial');
   for(let i=0;i<this.feature.symbols.length;i++)this.drawMiniSymbol(this.feature.symbols[i],163+i*34,659,32);
  }
 };
 // Remove the duplicate full-width prize rectangle. Only these three meters remain.
 game.statusBar=function(t){
  const progress=this.winCount?clamp((t-this.winCount.start)/this.winCount.duration,0,1):1;
  const amount=this.winCount?this.winCount.amount*(1-Math.pow(1-progress,3)):this.lastWin;
  const meters=[['SALDO',format(this.balance)],['APOSTA',format(this.bet)],['PRÉMIO',format(this.feature?this.feature.total:amount)]];
  for(let i=0;i<3;i++){
   const x=28+i*170,y=R.top+R.gh*3+27,w=164,h=72;
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
  const label=this.paused?'RETOMAR':this.spinning?'PARAR':'GIRAR';
  drawMedallion(label,!this.ready||this.superseded,performance.now());
  if(this.ready&&!this.superseded)this.action(215,813,130,130,()=>this.startSpin(),label);
  txt('V1.2.4',38,985,10,'#e5c68d',850,'Arial');
  if(!this.storageOK)txt('SESSÃO SEM GRAVAÇÃO',280,833,11,'#ffbc83',800,'Arial');
 };
 document.documentElement.dataset.faraoVisual='1.1';
})();
