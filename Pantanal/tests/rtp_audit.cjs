'use strict';
/* Relatorio de amostragem da porta JS para creditos ficticios. NAO certifica RTP. */
const e=require('../apk_engine.js');
const spins=Number(process.env.AUDIT_SPINS)||40000;
const betPerLine=2,lines=25,stake=e.betTotal(betPerLine,lines);
let board=null,credit=50000,spinsDone=0,totalStake=0,totalAward=0;
let bonus={strawberry:0,cauldron:0,wheel:0,dice:0};
let jackpotCount={min:0,med:0,max:0},jackpots=e.defaultJackpots(),maxWin=0,refills=0;
let baseAward=0,bonusAward=0,jackpotAward=0,forcedSpins=0,generatorCalls=0;
const rng=Math.random;
for(let k=0;k<spins;k++){
  if(credit<stake){credit+=50000;refills++;}
  credit-=stake;totalStake+=stake;
  const difficulty=e.difficulty(credit,generatorCalls,50,rng);
  generatorCalls=difficulty.nextSpins;
  const generated=e.generateRound(board,difficulty.active,rng,{
    lines,betPerLine,generatorCount:generatorCalls,canPay:k>=239,
    medEnabled:false,maxEnabled:false,numSorteio:200
  });
  board=generated.board;
  if(generated.meta.forced.length)forcedSpins++;
  const outcome=e.resultBoard(board,lines,betPerLine);
  let pay=outcome.total;
  baseAward+=pay;
  if(k>=239){
    for(const type of new Set(outcome.jackpots.map(x=>x.type))){
      const j=e.awardJackpot(jackpots,type);
      pay+=j.amount;jackpotAward+=j.amount;jackpots[type]=j.reset;jackpotCount[type]++;
    }
  }
  const beforeBonus=pay;
  const b=outcome.bonus;
  for(const key of Object.keys(b))bonus[key]+=b[key];
  if(b.strawberry>0){
    const d=e.difficulty(credit+pay,generatorCalls,50,rng);
    generatorCalls=d.nextSpins;
    const deck=e.strawberryDeck(rng,{paymentMode:d.active,triggerCount:b.strawberry,existingBonus:0});
    for(const category of deck){if(category===6)break;pay+=e.strawberryPay(category,betPerLine,b.strawberry);}
  }
  if(b.cauldron>0){
    const deck=e.cauldronDeck(rng);
    let category=deck[Math.floor(rng()*5)];
    const d=e.difficulty(credit+pay,generatorCalls,50,rng);
    generatorCalls=d.nextSpins;
    if(!d.active&&category>2)category=deck.find(v=>v<3);
    if(category===8){
      const pumpkins=e.pumpkinDeck(rng);
      pay+=e.pumpkinPay(pumpkins[Math.floor(rng()*5)],betPerLine,b.cauldron);
    }else pay+=e.cauldronPay(category,betPerLine,b.cauldron);
  }
  for(let i=0;i<b.wheel;i++){
    const d=e.difficulty(credit+pay,generatorCalls,50,rng);
    generatorCalls=d.nextSpins;
    pay*=e.wheelRoll(rng,d.active,pay).multiplier;
  }
  for(let i=0;i<b.dice;i++){
    const d=e.difficulty(credit+pay,generatorCalls,50,rng);
    generatorCalls=d.nextSpins;
    pay*=e.diceRoll(rng,d.active).multiplier;
  }
  bonusAward+=pay-beforeBonus;
  credit+=pay;totalAward+=pay;maxWin=Math.max(maxWin,pay);
  jackpots=e.advanceJackpots(jackpots,stake,pay,5,e.jackpotCaps());
  spinsDone++;
}
const out={
  samples:spinsDone,wagerPerLine:betPerLine,activeLines:lines,stakePerSpin:stake,
  theoreticalEstimatePercent:Math.round(totalAward/totalStake*10000)/100,
  baseReturnPercent:Math.round(baseAward/totalStake*10000)/100,
  bonusReturnPercent:Math.round(bonusAward/totalStake*10000)/100,
  jackpotReturnPercent:Math.round(jackpotAward/totalStake*10000)/100,
  totalStaked:totalStake,totalReturned:totalAward,maxWin,bonusTriggers:bonus,
  jackpotTriggers:jackpotCount,virtualRefills:refills,
  generatedSpecialSpins:forcedSpins,difficultyChecks:generatorCalls,
  warning:'SAMPLE ONLY — not certified. Generator/bonus state and APK economics still under review'
};
console.log(JSON.stringify(out,null,2));
if(!Number.isFinite(out.theoreticalEstimatePercent))process.exitCode=1;
