/* Jardim Mágico V60 — efeitos de vitória proporcionais à aposta.
   Apenas visual: NÃO sorteia prémios, não altera jackpot, RTP, saldos ou reservas. */
(()=>{
 'use strict';
 const TIERS=Object.freeze([
  Object.freeze({min:250,label:'SENSACIONAL',slug:'sensacional',caption:'PRÉMIO EXTRAORDINÁRIO'}),
  Object.freeze({min:100,label:'ÉPICO',slug:'epico',caption:'PRÉMIO ÉPICO'}),
  Object.freeze({min:50,label:'SUPER',slug:'super',caption:'GRANDE VITÓRIA'}),
  Object.freeze({min:25,label:'MEGA',slug:'mega',caption:'PRÉMIO MEGA'}),
  Object.freeze({min:15,label:'NICE',slug:'nice',caption:'BOA JOGADA'})
 ]);
 const currency=c=>(c/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 function tierFor(valueCents,betCents){
  if(!(Number.isFinite(valueCents)&&Number.isFinite(betCents)&&valueCents>0&&betCents>0))return null;
  const tier=TIERS.find(t=>valueCents>=t.min*betCents);
  return tier?{...tier,multiplier:valueCents/betCents}:null;
 }
 let active=null;
 function clear(){
  if(!active)return;
  const {node,timer,finish}=active;
  clearTimeout(timer);
  node.remove();
  active=null;
  finish();
 }
 function play({value,bet,turbo=false,playSound}={}){
  const tier=tierFor(value,bet);
  if(!tier)return Promise.resolve(false);
  const stage=document.getElementById('stageWrap');
  if(!stage)return Promise.resolve(false);
  clear();
  const reduced=!!window.matchMedia?.('(prefers-reduced-motion: reduce)')?.matches;
  const duration=reduced?420:(turbo?1450:2600);
  const node=document.createElement('div');
  node.className='jm60WinCelebration jm60-'+tier.slug;
  node.setAttribute('role','status');
  node.setAttribute('aria-label',tier.label+'! Prémio de '+currency(value));
  node.style.setProperty('--jm60-duration',duration+'ms');
  const label=document.createElement('div');
  label.className='jm60TierName';label.textContent=tier.label+'!';
  const amount=document.createElement('div');
  amount.className='jm60TierAmount';amount.textContent=currency(value);
  const sub=document.createElement('div');
  sub.className='jm60TierCaption';sub.textContent='×'+tier.multiplier.toLocaleString('pt-PT',{maximumFractionDigits:1})+' A APOSTA';
  node.append(label,amount,sub);
  if(!reduced)for(let i=0;i<(turbo?12:25);i++){
   const spark=document.createElement('i');spark.className='jm60WinSpark';
   const rad=(Math.PI*2*i/(turbo?12:25)),dist=36+Math.random()*140;
   spark.style.setProperty('--spark-x',Math.round(Math.cos(rad)*dist)+'px');
   spark.style.setProperty('--spark-y',Math.round(Math.sin(rad)*dist)+'px');
   spark.style.setProperty('--spark-delay',((i%5)*.048).toFixed(2)+'s');
   node.append(spark);
  }
  stage.append(node);
  try{if(typeof playSound==='function')playSound(tier);}catch(err){console.warn('Som de celebração indisponível',err)}
  return new Promise(resolve=>{
   const finish=()=>resolve(true);
   const timer=setTimeout(()=>{if(active?.node===node)clear()},duration);
   active={node,timer,finish};
  });
 }
 function applySummary(node,value,bet){
  const tier=tierFor(value,bet);
  let title=node.querySelector('.jm60SummaryTitle');
  if(!title){
   title=document.createElement('div');title.className='jm60SummaryTitle';
   const heading=node.querySelector('.jm52FreeHeading');
   if(heading)heading.insertAdjacentElement('afterend',title);else node.prepend(title);
  }
  title.textContent=tier?tier.label+'!':'';
  title.className='jm60SummaryTitle'+(tier?' jm60-'+tier.slug:'');
 }
 window.JM_WINCELEBRATION_V60={tierFor,play,clear,applySummary,levels:TIERS.map(({min,label})=>({min,label}))};
})();