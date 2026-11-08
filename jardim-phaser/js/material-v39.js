/* Jardim Mágico Phaser V3.9 — volume e brilho pré-rasterizados.
 * Cada textura é criada UMA vez no arranque, nunca por frame ou cascata.
 * A imagem é preservada: não há cor uniforme nem filtros de GPU por peça.
 */
export const MATERIALS=Object.freeze({
 potion:{shadow:20,shift:13,gloss:.16},
 green:{shadow:17,shift:12,gloss:.20},
 heart:{shadow:16,shift:10,gloss:.22},
 rose:{shadow:16,shift:10,gloss:.22},
 stone:{shadow:22,shift:15,gloss:.075},
 wild:{shadow:24,shift:11,gloss:.11}
});
export function materialSettings(symbol){
 return MATERIALS[symbol]||{shadow:12,shift:8,gloss:.08};
}
export function bakeMaterial(image,symbol,makeCanvas){
 if(!image||!(image.width>0)||!(image.height>0))throw Error('Material sem imagem');
 const create=makeCanvas||(()=>document.createElement('canvas'));
 const canvas=create();canvas.width=512;canvas.height=512;
 const g=canvas.getContext('2d');
 if(!g)throw Error('Canvas sem contexto 2D');
 const {shadow,shift,gloss}=materialSettings(symbol);
 // Pré-render com margem para que a sombra não seja cortada.
 const margin=14;
 const target=512-2*margin;
 g.save();
 g.shadowColor='rgba(8,7,19,.54)';
 g.shadowBlur=shadow;
 g.shadowOffsetX=0;g.shadowOffsetY=shift;
 g.drawImage(image,margin,margin,target,target);
 g.restore();
 // Cópia nítida acima da sombra.
 g.drawImage(image,margin,margin,target,target);
 // Reflexo de material limitado aos pixels opacos (a transparência permanece).
 g.save();
 g.globalCompositeOperation='source-atop';
 const light=g.createRadialGradient(155,113,8,236,190,258);
 light.addColorStop(0,'rgba(255,250,224,'+gloss+')');
 light.addColorStop(.48,'rgba(255,238,200,'+(gloss*.2).toFixed(3)+')');
 light.addColorStop(1,'rgba(19,24,35,.065)');
 g.fillStyle=light;g.fillRect(0,0,512,512);
 g.restore();
 return canvas;
}
