#!/usr/bin/env python3
from pathlib import Path

p=Path('Pantanal/index.html')
s=p.read_text()
if 'apk_engine.js?v=2.14' in s:
    print('Pantanal v2.14 ja integrado')
    raise SystemExit(0)

def once(a,b,label):
    global s
    n=s.count(a)
    assert n==1,f'{label}: {n} ocorrencias'
    s=s.replace(a,b,1)

once("const BET_LEVELS=[1,2,5,10,20,50,100,200];","const BET_LEVELS=[1,2,5,10,20,40];",'betlevels')
once("bonusCount:0,sound:true,history:[]};",
     "bonusCount:0,sound:true,history:[],blocked:50,apkAccumPercent:5,jackpots:null};",'default')
once("let board=Array.from({length:3}",
     "if(![1,5,9,15,25].includes(state.lines))state.lines=25;"
     "if(state.betIndex<0||state.betIndex>=6)state.betIndex=0;"
     "state.blocked=Number(state.blocked)||50;"
     "state.apkAccumPercent=(state.apkAccumPercent===undefined?5:Number(state.apkAccumPercent));"
     "state.jackpots=state.jackpots||{min:state.accum1min,med:state.accum2min,max:state.accum3min};"
     "\nlet board=Array.from({length:3}",'state migration')
begin=s.index('function calcWins(b){')
end=s.index('let celebrationBusy=false;',begin)
assert 60<end-begin<2000,'calcWins legacy alterado'
s=s[:begin]+"function calcWins(b){return window.PantanalApkEngine.resultBoard(b,state.lines,BET_LEVELS[state.betIndex]);}\n"+s[end:]
once("window.PantanalOriginalRules.stake(BET_LEVELS[state.betIndex],state.lines)",
     "window.PantanalApkEngine.betTotal(BET_LEVELS[state.betIndex],state.lines)",'stake')
once("const target=Array.from({length:3},()=>Array.from({length:5},()=>pick()));",
     "const difficulty=window.PantanalApkEngine.difficulty(state.credits,state.totalSpins-1,state.blocked,rnd);"
     "const target=window.PantanalApkEngine.generateBoard(board,difficulty.active,rnd);",'generator')
once("board=target;renderBoard();let {wins,total,bonus}=calcWins(board);state.lastWin=total;state.credits+=total;state.totalPaid+=total;state.maxWin=Math.max(state.maxWin,total);",
     """board=target;renderBoard();
 let {wins,total,bonus,jackpots}=calcWins(board);
 const apk=window.PantanalApkEngine,jackpotNames=[];
 state.jackpots=state.jackpots||apk.defaultJackpots();
 if(jackpots.length&&state.lines===25&&BET_LEVELS[state.betIndex]>=2&&state.totalSpins>=239){
   for(const type of new Set(jackpots.map(w=>w.type))){
     const award=apk.awardJackpot(state.jackpots,type);
     total+=award.amount;state.jackpots[type]=award.reset;
     jackpotNames.push(type.toUpperCase()+' '+money(award.amount));
   }
 }
 state.lastWin=total;state.credits+=total;state.totalPaid+=total;
 state.maxWin=Math.max(state.maxWin,total);
 const caps={min:state.accum1max,med:state.accum2max,max:state.accum3max};
 state.jackpots=apk.advanceJackpots(state.jackpots,stake,total,state.apkAccumPercent,caps);
 state.bonusCount+=Object.values(bonus).reduce((a,b)=>a+b,0);""",'payout+jp')
once("const bonusTrigger=bonus.wheel>0;const diceTrigger=bonus.dice>0;pendingDice=bonus.dice;",
     "const bonusTrigger=Object.values(bonus).some(v=>v>0);",'queue')
once("const special=wins.some(w=>(w.symbol===6&&w.count>=4)||(w.symbol===7&&w.count===5));",
     "const special=false;",'old animation unsafe')
once("if(bonusTrigger){auto=false;await wait(500);openWheel();return}\n if(diceTrigger){auto=false;await wait(500);showDiceBonus();return}",
     """if(jackpotNames.length){showStatus('ACUMULADO: '+jackpotNames.join(' • '));play('big');}
 if(bonusTrigger){
   auto=false;await wait(450);
   await window.PantanalBonusUI.launch({
     bonus,state,betPerLine:BET_LEVELS[state.betIndex],rng:rnd,
     asset,difficulty,
     hooks:{save,update,showStatus,play,money,setBusy(flag){bonusBusy=!!flag;}}
   });
   return;
 }""",'four bonus integration')
once("el('spinLabel').textContent=spinBusy?'RODANDO':'JOGAR';}",
     "el('spinLabel').textContent=spinBusy?'RODANDO':'JOGAR';updateJackpotHUD();}",'hud update')
mark='function showGame(){'
hud="""function updateJackpotHUD(){
  const game=el('game');if(!game)return;
  let root=el('pantanalJackpots');
  if(!root){
    root=document.createElement('div');root.id='pantanalJackpots';
    root.style.cssText='position:absolute;z-index:5;left:70px;width:660px;top:calc(45% + 235px);display:flex;gap:12px;justify-content:center;pointer-events:none;';
    game.appendChild(root);
  }
  const j=state.jackpots||{min:500,med:700,max:1200};
  root.innerHTML=['min','med','max'].map((k,i)=>
    '<div style="background:linear-gradient(#6d3a0b,#2b1605);border:2px solid #dcac52;border-radius:14px;padding:5px 11px;text-align:center;flex:1;color:#ffebb5;font:bold 18px Arial,sans-serif">'
    +['MIN','MED','MAX'][i]+'<div style="color:white;font-size:21px">'+money(Math.floor(j[k]*100))+'</div></div>').join('');
}
"""
once(mark,hud+mark,'hud')
once("A reconstituição das probabilidades ainda não é matemática certificada do motor original.",
     "Motor em migração do APK original; versão web com créditos virtuais e sem certificação para dinheiro real.",'help')
once("paylines.js?v=2.13","paylines.js?v=2.14",'cache')
once("</body></html>",'<script src="apk_engine.js?v=2.14"></script><script src="bonus_ui.js?v=2.14"></script></body></html>','script modules')
assert s.count("function calcWins(b){return window.PantanalApkEngine.resultBoard")==1
assert s.count("PantanalBonusUI.launch(")==1
assert s.count("apk_engine.js?v=2.14")==1
p.write_text(s)
print('Pantanal 2.14: 20+5 originais, generator faixa, quatro bonus, acumulados, teste.')
