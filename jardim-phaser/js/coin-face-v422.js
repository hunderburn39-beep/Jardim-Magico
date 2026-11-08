/* Jardim Mágico V4.2.2 — valor de aposta estampado na própria moeda.
 * Esta textura acompanha o sprite em todos os frames, inclusivamente
 * na rotação e na entrada por cima do tabuleiro.
 * Apenas representação de créditos fictícios, não pagamento adicional.
 */
import {coinLabel} from './coin-values-v42.js?v=4.2.1';
export function makeCoinFace(image,bet,factor=1,makeCanvas=()=>document.createElement('canvas')){
 if(!image||!(image.width>0)||!(image.height>0))throw Error('Moeda sem imagem');
 const label=coinLabel(bet,factor);
 const canvas=makeCanvas();canvas.width=512;canvas.height=512;
 const ctx=canvas.getContext('2d');if(!ctx)throw Error('Canvas de moeda indisponível');
 ctx.drawImage(image,0,0,512,512);
 ctx.save();
 ctx.textAlign='center';ctx.textBaseline='middle';
 const size=label.length<=5?102:label.length<=7?80:label.length<=9?64:50;
 ctx.font='900 '+size+'px Georgia,serif';
 ctx.lineJoin='round';
 ctx.strokeStyle='#421535';ctx.lineWidth=Math.max(6,Math.round(size*.12));
 ctx.shadowColor='rgba(22,8,32,.65)';ctx.shadowBlur=11;ctx.shadowOffsetY=3;
 ctx.strokeText(label,256,251,352);
 ctx.shadowBlur=0;ctx.shadowOffsetY=0;
 ctx.fillStyle='#fff0bd';ctx.fillText(label,256,251,352);
 ctx.restore();
 return canvas;
}
export function coinTextureId(stake,factor=1){
 const cents=Math.round(Number(stake)*100);
 const step=Math.trunc(Number(factor));
 if(!Number.isFinite(cents)||cents<=0||!Number.isFinite(step)||step<1)
  return null;
 return 'coin-euro-'+cents+'-'+step;
}
