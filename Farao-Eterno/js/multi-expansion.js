/* Faraó Eterno V1.3.28 — apresentação sequencial dos expansivos.
 * Apenas apresentação: o motor e a sessão determinam os prémios.
 */
(() => {
 'use strict';
 const game=window.__faraoGame, E=window.TombEngine;
 if(!game || !E || game._multiExpansionInstalled)return;
 game._multiExpansionInstalled=true;
 const canvas=document.getElementById('canvas'), ctx=canvas?.getContext('2d');
 const ease=p=>{const q=Math.max(0,Math.min(1,p));return 1-Math.pow(1-q,3);};
 const format=n=>(Math.max(0,n)||0).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 const names={'10':'10',J:'J',Q:'Q',K:'K',A:'ÁS',HORUS:'ESCARAVELHO',MASK:'MÁSCARA',ANUBIS:'ANÚBIS',KING:'FARAÓ'};
 const originalStart=game.startSpin.bind(game);
 game.startSpin=function(){
  const wasSpinning=this.spinning;
  originalStart();
  if(!wasSpinning && this.spinning)this._multiExpansionSteps=null;
 };
 const originalFinish=game.finishSpin.bind(game);
 game.finishSpin=function(){
  const free=!!this.spinObject?.free;
  originalFinish();
  if(!free)return;
  const result=this.lastResult;
  const awards=result?.expansionAwards;
  if(!Array.isArray(awards) || !awards.length)return;
  const sorted=[...awards].filter(x=>x&&x.amount>0&&Array.isArray(x.reels)&&x.reels.length)
   .sort((a,b)=>E.NORMAL.indexOf(a.symbol)-E.NORMAL.indexOf(b.symbol));
  if(!sorted.length)return;
  const now=performance.now(),turbo=!!this.turbo;
  const perReel=turbo?130:230,preDelay=turbo?130:280;
  const countDuration=turbo?310:620,hold=turbo?140:250;
  const paid=Math.max(0,Number(this.lastWin)||0);
  const base=Math.min(paid,Math.max(0,(Number(result.baseTotal)||0)+(Number(result.scatterWin)||0)));
  let cursor=now,allocated=base;
  const stages=sorted.map(a=>{
   const amount=Math.max(0,Math.min(Number(a.amount)||0,paid-allocated));
   allocated+=amount;
   const countStart=cursor+preDelay+a.reels.length*perReel;
   const stage={
    symbol:a.symbol,reels:[...a.reels],count:a.count,amount,
    start:cursor,preDelay,perReel,countStart,countDuration,
    end:countStart+countDuration+hold
   };
   cursor=stage.end;
   return stage;
  });
  const end=cursor;
  this._multiExpansionSteps=stages;
  // A animação nunca recalcula nem altera o prémio já liquidado.
  this.reveal={start:now,end,stages,
   reels:[...new Set(stages.flatMap(s=>s.reels))],preDelay,perReel};
  this.winCount={start:now,duration:Math.max(1,end-now),amount:paid,base,stages};
  this.nextActionAt=Math.max(this.nextActionAt,end+(turbo?300:440));
  if(this.v04Celebration&&this.v04Celebration.start<end){
   const duration=this.v04Celebration.end-this.v04Celebration.start;
   this.v04Celebration.start=end;
   this.v04Celebration.end=end+Math.max(0,duration);
  }
  if(this.featureIntro&&this.featureIntro.start<end){
   const duration=this.featureIntro.end-this.featureIntro.start;
   this.featureIntro.start=end+180;
   this.featureIntro.end=this.featureIntro.start+Math.max(0,duration);
   this.nextActionAt=Math.max(this.nextActionAt,this.featureIntro.end+250);
  }
  // Num tambor partilhado, a figura final será a última (de valor mais alto).
  this.expanded={};
  for(const stage of stages)for(const reel of stage.reels)this.expanded[reel]=stage;
 };
 // Devolve, para cada tambor, a figura da etapa já iniciada. As anteriores
 // permanecem visíveis até chegar a etapa da figura seguinte.
 game.expansionAt=function(reel,t){
  const stages=this._multiExpansionSteps;
  if(!stages?.length)return null;
  let candidate=null,progress=0;
  for(const stage of stages){
   const idx=stage.reels.indexOf(reel);
   if(idx<0)continue;
   const stamp=stage.start+stage.preDelay+idx*stage.perReel;
   if(t<stamp)continue;
   candidate=stage;
   progress=ease((t-stamp)/stage.perReel);
  }
  return {candidate,progress,expanding:!!candidate};
 };
 game.bonusAmountAt=function(t){
  const w=this.winCount;
  if(!w?.stages?.length){
   if(!w)return this.lastWin;
   return w.amount*ease((t-w.start)/Math.max(1,w.duration));
  }
  let amount=w.base;
  for(const stage of w.stages)
   amount+=stage.amount*ease((t-stage.countStart)/Math.max(1,stage.countDuration));
  return Math.min(w.amount,Math.max(0,amount));
 };
 // Legenda de cada etapa: a figura inferior é sempre mostrada primeiro.
 const originalBoard=game.reelBoard.bind(game);
 game.reelBoard=function(t){
  originalBoard(t);
  if(!ctx||this.spinning||!this._multiExpansionSteps?.length)return;
  const stage=this._multiExpansionSteps.find(s=>t>=s.start&&t<s.end);
  if(!stage)return;
  const label='EXPANSÃO '+(names[stage.symbol]||stage.symbol)+
   (t>=stage.countStart?' · +'+format(stage.amount):'');
  ctx.save();ctx.textAlign='center';ctx.textBaseline='middle';
  ctx.font='800 16px Georgia,serif';ctx.lineWidth=4;
  ctx.strokeStyle='#061a28';ctx.shadowColor='#8affec';ctx.shadowBlur=8;
  ctx.strokeText(label,280,714);ctx.fillStyle='#91ffec';ctx.fillText(label,280,714);
  ctx.restore();
 };
})();