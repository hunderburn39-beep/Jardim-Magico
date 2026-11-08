/* Jardim Mágico V4.2.1 — apenas valores visuais de créditos fictícios.
 * Moeda = aposta × multiplicador da própria moeda, não o acumulado.
 * Moeda do tabuleiro sem multiplicador definido = aposta × 1.
 * O valor apresentado em € é demonstrativo, sem dinheiro real.
 * Não cria pagamentos novos.
 * O porquinho é uma animação cosmética, sem tocar na reserva.
 */
export function coinValue(stake,multiplier){
 const bet=Number(stake),factor=Number(multiplier);
 if(!Number.isFinite(bet)||bet<=0||!Number.isFinite(factor)||factor<1)return 0;
 return Math.round((bet*factor+Number.EPSILON)*100)/100;
}
export function coinLabel(stake,multiplier){
 return coinValue(stake,multiplier).toFixed(2).replace('.',',')+'€';
}
export function valuedPositions(awards=[],board=[],stake=.2,multiplier=1){
 const valid=(c,r)=>Number.isInteger(c)&&c>=0&&c<5&&Number.isInteger(r)&&r>=0&&r<3;
 const out=[],seen=new Set();
 for(const {c,r,value} of (Array.isArray(awards)?awards:[])){
  if(!valid(c,r)||!Number.isInteger(value)||value<1||value>5)continue;
  const id=c+','+r;if(seen.has(id))continue;
  seen.add(id);
  // Cada moeda conserva o seu próprio ×1…×5, não repete o MULTI
  // acumulado sobre todas as posições.
  out.push({c,r,kind:'wild',factor:value,
   label:coinLabel(stake,value),amount:coinValue(stake,value)});
 }
 for(let c=0;c<5;c++)for(let r=0;r<3;r++){
  if(!Array.isArray(board?.[c])||board[c][r]!==11)continue;
  const id=c+','+r;if(seen.has(id))continue;
  seen.add(id);
  // A moeda normal não tem multiplicador atribuído pelo motor: ×1.
  // Os outros fatores pertencem aos WILDs e às moedas do Hold.
  out.push({c,r,kind:'coin',factor:1,
   label:coinLabel(stake,1),amount:coinValue(stake,1)});
 }
 return out;
}
export function pigGrowth(spins,cycle=20){
 const n=Math.max(0,Math.floor(Number(spins)||0));
 const period=Math.max(3,Math.floor(Number(cycle)||20));
 const progress=(n%period)/period;
 return {progress,scale:Math.round((.91+.43*progress)*1000)/1000,
  round:Math.floor(n/period),cycle:period};
}
