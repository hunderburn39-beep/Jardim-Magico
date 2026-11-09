/* Presentation only: a multiplier must actually be present to display the HUD. */
export function dropHudView(history=[],effective=1){
 const factor=Math.max(1,Math.trunc(Number(effective)||1));
 const awarded=Array.isArray(history)&&history.some(v=>Number.isInteger(v)&&v>=1&&v<=5);
 return {factor,visible:factor>1||awarded};
}
