/* Jardim Mágico Phaser V3.9.1 — ritmo dos tambores.
 * Visual apenas: não altera símbolos, sorteio, premiação ou RTP.
 */
export function spinMotionProfile(turbo=false,column=0){
 const col=Math.max(0,Math.min(4,Math.trunc(Number(column)||0)));
 if(turbo){
  return {steps:15+5*col,duration:640+52*col,ease:'Cubic.easeOut'};
 }
 // Uma volta física adicional nos cinco sprites circulantes.
 // Duração ligeiramente mais longa e abrandamento menos abrupto.
 return {steps:20+5*col,duration:1780+190*col,ease:'Sine.easeOut'};
}
