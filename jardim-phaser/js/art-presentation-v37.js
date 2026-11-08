/* Phaser V3.7 — apresentação visual. Sem efeito na matemática. */
const BOTANICAL_SIZE=Object.freeze({
 potion:1.035,green:1.045,heart:1.035,
 rose:1.035,stone:1.055,wild:1.035
});
export function symbolDisplay(symbol,cellW=180,cellH=180){
 const scale=BOTANICAL_SIZE[symbol]||.97;
 return {width:Math.round(cellW*scale),height:Math.round(cellH*scale)};
}
export function wildBadgePlacement({c,r,value}={},cellW=180,cellH=180){
 if(!Number.isInteger(c)||c<0||c>4||!Number.isInteger(r)||r<0||r>2||
    !Number.isInteger(value)||value<1||value>5)return null;
 return {x:(c+1)*cellW-cellW*.07,
  y:r*cellH+cellH*.07,label:'×'+value,
  fontSize:Math.round(cellW*.235)};
}
