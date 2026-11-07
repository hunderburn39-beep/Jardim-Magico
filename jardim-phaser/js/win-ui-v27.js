/* Jardim Mágico Phaser V2.7 — purely visual win summaries.
 * No changes to the credit engine, prizes, or tumble animation.
 */
const currency=new Intl.NumberFormat('pt-PT',{
 minimumFractionDigits:2,maximumFractionDigits:2
});
const validAmount=x=>Number.isFinite(Number(x))?Math.max(0,Number(x)):0;
export function celebrationView(title,amount,turbo=false){
 const value=Number(amount);
 if(!Number.isFinite(value)||value<=0)return null;
 const headline=String(title||'NICE').trim().slice(0,28).toUpperCase();
 const tier=['NICE','MEGA','SENSACIONAL'].includes(headline)?
  headline.toLowerCase():'bonus';
 const formatted=currency.format(value);
 const width=formatted.length>12?'dense':formatted.length>8?'wide':'normal';
 return {title:headline,formatted,tier,width,
  duration:turbo?1150:1750};
}
export function winBreakdown(wins=[],total=false,factor=1){
 if(!Array.isArray(wins)||!wins.length){
  return {hasWins:false,count:0,label:'',values:[],other:0};
 }
 const f=Number.isFinite(Number(factor))&&Number(factor)>0?
  Number(factor):1;
 const values=wins.slice(0,3).map(win=>{
  const raw=validAmount(win?.amount)*f;
  const rounded=Math.round((raw+Number.EPSILON)*100)/100;
  return currency.format(rounded);
 });
 const count=wins.length,other=Math.max(0,count-values.length);
 return {hasWins:true,count,
  label:(total?'TOTAL · ':'')+count+' COMBINAÇ'+(count===1?'ÃO':'ÕES'),
  values,other};
}
