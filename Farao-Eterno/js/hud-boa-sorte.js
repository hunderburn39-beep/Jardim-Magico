/* Faraó Eterno — HUD 9:16: BOA SORTE com reflexo e contador lateral.
 * Exclusivamente gráfico: não toca na matemática, spins, sons ou saldo.
 */
(()=>{
'use strict';
const g=window.__faraoGame;
const canvas=document.getElementById('canvas');
if(!g||!canvas)return;
const ctx=canvas.getContext('2d');
if(!ctx)return;
const WIDTH=240,HEIGHT=50;
function rounded(x,y,w,h,r,fill,stroke,lineWidth=1){
 ctx.beginPath();
 ctx.moveTo(x+r,y);ctx.lineTo(x+w-r,y);ctx.quadraticCurveTo(x+w,y,x+w,y+r);
 ctx.lineTo(x+w,y+h-r);ctx.quadraticCurveTo(x+w,y+h,x+w-r,y+h);
 ctx.lineTo(x+r,y+h);ctx.quadraticCurveTo(x,y+h,x,y+h-r);
 ctx.lineTo(x,y+r);ctx.quadraticCurveTo(x,y,x+r,y);
 ctx.closePath();
 if(fill){ctx.fillStyle=fill;ctx.fill();}
 if(stroke){ctx.lineWidth=lineWidth;ctx.strokeStyle=stroke;ctx.stroke();}
}
function ellipse(x,y,r,color){
 ctx.beginPath();ctx.arc(x,y,r,0,Math.PI*2);ctx.fillStyle=color;ctx.fill();
}
// A máscara impede que o reflexo apareça fora das letras.
const mask=document.createElement('canvas');mask.width=WIDTH;mask.height=HEIGHT;
const maskCtx=mask.getContext('2d');
const shine=document.createElement('canvas');shine.width=WIDTH;shine.height=HEIGHT;
const shineCtx=shine.getContext('2d');
if(maskCtx){
 maskCtx.font='900 29px Georgia,serif';
 maskCtx.textAlign='center';maskCtx.textBaseline='middle';
 maskCtx.fillStyle='#fff';maskCtx.fillText('BOA SORTE',WIDTH/2,HEIGHT/2);
}
function drawWish(t){
 const px=166,py=172,pw=244,ph=56,centre=px+pw/2;
 ctx.save();
 ctx.shadowColor='#000c';ctx.shadowBlur=14;ctx.shadowOffsetY=4;
 const base=ctx.createLinearGradient(0,py,0,py+ph);
 base.addColorStop(0,'#624320');
 base.addColorStop(.17,'#211f25');
 base.addColorStop(.75,'#0b1a2a');
 base.addColorStop(1,'#4a3521');
 rounded(px,py,pw,ph,15,base,'#dbaf61',2.6);
 ctx.restore();
 ctx.save();
 rounded(px+4,py+4,pw-8,ph-8,12,null,'#f1d68a77',.9);
 // Ornamento egípcio discreto nas extremidades da inscrição.
 for(const side of [-1,1]){
  const x=centre+side*109;
  ctx.strokeStyle='#d9aa5e';ctx.lineWidth=1.4;
  ctx.beginPath();ctx.moveTo(x,py+16);ctx.lineTo(x-side*8,py+28);ctx.lineTo(x,py+40);ctx.stroke();
  ellipse(x,py+28,1.8,'#6de0c5');
 }
 ctx.font='900 29px Georgia,serif';
 ctx.textAlign='center';ctx.textBaseline='middle';
 ctx.lineJoin='round';ctx.lineWidth=3;
 ctx.strokeStyle='#563313';ctx.strokeText('BOA SORTE',centre,py+ph/2+1);
 const gold=ctx.createLinearGradient(0,py+14,0,py+43);
 gold.addColorStop(0,'#fff1c4');gold.addColorStop(.40,'#eac06d');gold.addColorStop(1,'#b8772a');
 ctx.fillStyle=gold;ctx.shadowColor='#f5c97870';ctx.shadowBlur=7;
 ctx.fillText('BOA SORTE',centre,py+ph/2);
 ctx.shadowBlur=0;
 // A cada 3,8 s, um reflexo fino varre as letras da esquerda para a direita.
 if(maskCtx&&shineCtx){
  const phase=t%3800;
  if(phase<2500){
   const x=-52+(WIDTH+104)*phase/2500;
   shineCtx.clearRect(0,0,WIDTH,HEIGHT);
   shineCtx.globalCompositeOperation='source-over';
   const beam=shineCtx.createLinearGradient(x-43,0,x+43,0);
   beam.addColorStop(0,'rgba(255,255,255,0)');
   beam.addColorStop(.31,'rgba(255,245,191,.16)');
   beam.addColorStop(.46,'rgba(255,255,236,.82)');
   beam.addColorStop(.50,'rgba(255,255,255,1)');
   beam.addColorStop(.54,'rgba(255,255,236,.82)');
   beam.addColorStop(.69,'rgba(255,245,191,.16)');
   beam.addColorStop(1,'rgba(255,255,255,0)');
   shineCtx.fillStyle=beam;shineCtx.fillRect(0,0,WIDTH,HEIGHT);
   shineCtx.globalCompositeOperation='destination-in';
   shineCtx.drawImage(mask,0,0);
   shineCtx.globalCompositeOperation='source-over';
   ctx.globalCompositeOperation='screen';
   ctx.drawImage(shine,centre-WIDTH/2,py+ph/2-HEIGHT/2);
  }
 }
 ctx.restore();
}
function drawCounter(){
 const value=Number(g.feature?.left);
 if(!Number.isFinite(value))return;
 // Lado esquerdo do medalhão central: nunca sobrepõe o botão GIRAR.
 const x=32,y=824,w=165,h=102;
 ctx.save();
 ctx.shadowColor='#000c';ctx.shadowBlur=13;ctx.shadowOffsetY=5;
 const background=ctx.createLinearGradient(x,y,x+w,y+h);
 background.addColorStop(0,'#6c4b26');
 background.addColorStop(.12,'#263343');
 background.addColorStop(.69,'#0b1a2a');
 background.addColorStop(1,'#503721');
 rounded(x,y,w,h,13,background,'#e4b965',2.4);
 ctx.restore();
 ctx.save();
 rounded(x+4,y+4,w-8,h-8,9,null,'#ffe0a070',1);
 ctx.textAlign='center';ctx.textBaseline='middle';
 ctx.font='900 13px Georgia,serif';ctx.fillStyle='#ffe5ae';
 ctx.fillText('FREE SPINS',x+w/2,y+22);
 ctx.strokeStyle='#b68d5185';ctx.lineWidth=1;
 ctx.beginPath();ctx.moveTo(x+20,y+39);ctx.lineTo(x+w-20,y+39);ctx.stroke();
 ctx.shadowColor='#70f5e8';ctx.shadowBlur=12;
 ctx.font='900 37px Georgia,serif';ctx.fillStyle='#b5fff1';
 ctx.fillText(String(Math.max(0,Math.trunc(value))),x+w/2,y+66);
 ctx.shadowBlur=0;ctx.font='800 10px Arial,sans-serif';
 ctx.fillStyle='#e0c58e';ctx.fillText('RESTANTES',x+w/2,y+91);
 ctx.restore();
}
const oldHeader=g.header.bind(g);
g.header=function(t){
 oldHeader(t);
 if(this.ready)drawWish(Number.isFinite(t)?t:performance.now());
};
const oldControls=g.controls.bind(g);
g.controls=function(...args){
 oldControls(...args);
 if(this.ready&&this.feature)drawCounter();
};
document.documentElement.dataset.faraoHud='boa-sorte-1.0';
})();