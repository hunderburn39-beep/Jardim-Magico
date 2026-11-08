/* Jardim Mágico Phaser V4.1 — apresentação das cascatas.
 * Exclusivamente visual: resultados, RNG e prémios são externos ao Phaser.
 * Perfis puros usados também nos testes sem navegador.
 */
export function burstMotionProfile(winners=1,low=false,destroy=true,duration=690){
 const count=Math.max(1,Math.min(15,Math.trunc(Number(winners)||1)));
 const time=Math.max(160,Number(duration)||690);
 const charge=destroy?(low?45:Math.min(115,Math.round(time*.14))):0;
 return {
  duration:time,
  charge,
  fadeMs:destroy?Math.round(time*.70):Math.round(time*.46),
  particles:low?1:Math.max(2,Math.min(3,Math.floor(42/count))),
  spread:low?44:Math.min(84,55+count*1.5),
  lift:destroy?22:0,
  highlight:!low
 };
}
export function cascadeMotionProfile(turbo=false,column=0,newPiece=false,low=false){
 const col=Math.max(0,Math.min(4,Math.trunc(Number(column)||0)));
 return {
  // Só as peças novas têm ligeira elasticidade ao pousar.
  duration:turbo?(newPiece?300:270):(newPiece?585:545),
  delay:(turbo?8:23)*col,
  ease:newPiece&&!low&&!turbo?'Back.easeOut':
    newPiece?'Cubic.easeOut':'Cubic.easeInOut'
 };
}
