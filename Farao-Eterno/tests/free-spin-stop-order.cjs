'use strict';
const test=require('node:test'),assert=require('node:assert/strict');
const fs=require('node:fs'),path=require('node:path'),vm=require('node:vm');
const cinematic=fs.readFileSync(path.join(__dirname,'../js/cinematic-v12.js'),'utf8');
const gameSrc=fs.readFileSync(path.join(__dirname,'../js/game.js'),'utf8');
const visual=fs.readFileSync(path.join(__dirname,'../js/visual-v11.js'),'utf8');
const quiet=()=>[['10','J','Q'],['K','A','10'],['J','Q','K'],['A','10','J'],['Q','K','A']];

function startWithTowers(towers,{free=true,turbo=false}={}){
 const grid=quiet();towers.forEach((col,i)=>{grid[col][i%3]='TOMB';});
 const audio={on:false,ctx:null,spin(){},stopReelWhirr(){},tick(){}};
 const game={visualV11:{layout:{}},audio,grid:quiet(),
  turbo,sound:false,spinning:false,spinObject:null,
  startSpin(){this.spinning=true;this.spinObject={
   grid,free,start:1000,stopped:[false,false,false,false,false],
   duration:0,reelGap:0};
  },
  drawIcon(){},reelBoard(){},finishSpin(){},frame(){}
 };
 const win={__faraoGame:game,matchMedia:()=>({matches:false})};
 const doc={getElementById:()=>({getContext:()=>({})}),
  addEventListener(){},documentElement:{dataset:{}}};
 vm.runInNewContext(cinematic,{window:win,document:doc,matchMedia:win.matchMedia});
 game.startSpin();
 return game.spinObject;
}

test('paragens do 1º ao 5º tambor, mesmo quando a 3ª torre cai no 4º',()=>{
 for(const towers of [[0,1,2],[0,1,3],[0,1,4],[0,2,4],[1,2,4],
                      [0,1],[1,2],[2,3],[0],[]]){
  for(const free of [false,true]){
   for(const turbo of [false,true]){
    const spin=startWithTowers(towers,{free,turbo});
    assert.deepEqual(Array.from(spin.stopOrder),[0,1,2,3,4]);
    for(let reel=1;reel<5;reel++){
     assert.ok(spin.stopAt[reel]>spin.stopAt[reel-1],
      'Ordem violada: '+JSON.stringify({towers,free,turbo,stops:spin.stopAt}));
     assert.ok(spin.stopAt[reel]-spin.stopAt[reel-1]>=spin.reelGap);
    }
    if(free)assert.ok(spin.stopAt[4]-spin.start<=2200,
      'Free Spins lentas em excesso: '+JSON.stringify({towers,turbo,stops:spin.stopAt}));
   }
  }
 }
});

test('suspense após 2 torres atrasado apenas uma vez e nunca ultrapassado',()=>{
 for(const third of [2,3,4]){
  const spin=startWithTowers([0,1,third]);
  assert.equal(spin.cinematicSuspense,true);
  assert.equal(spin.stopAt[2]-spin.stopAt[1],spin.reelGap+720);
  assert.equal(spin.stopAt[3]-spin.stopAt[2],spin.reelGap);
  assert.equal(spin.stopAt[4]-spin.stopAt[3],spin.reelGap);
 }
});

test('motor não pára dois tambores no mesmo frame ou fora de ordem',()=>{
 assert.match(gameSrc,/if\(c>0&&!item\.stopped\[c-1\]\)break;/);
 assert.match(gameSrc,/item\.stopped\[c\]=true;this\.landings\[c\]=t;this\.audio\.tick\(c\);/);
 assert.match(gameSrc,/No máximo uma paragem por frame/);
});

test('expansões não desenham barra horizontal nem leitura vertical',()=>{
 assert.doesNotMatch(visual,/scan<\.99/);
 assert.doesNotMatch(visual,/beamY-4/);
 assert.doesNotMatch(visual,/top\+gh\*3\*progress-4/);
 assert.doesNotMatch(gameSrc,/scanY-2/);
 assert.match(visual,/const ease=expanding\?progress:0/);
});

test('Free Spins usam tempos curtos nas sequências, preservando crédito faseado',()=>{
 assert.match(gameSrc,/const perReel=item\.free\?/);
 assert.match(gameSrc,/const creditAt=cursor\+preDelay/);
 assert.match(gameSrc,/this\.audio\.paylineChime\?\.\(stage\.index\)/);
});

test('pirâmides WILD pulsam sempre acima de 5%, com brilho dourado e sobreposição',()=>{
 assert.match(visual,/const pulse=1\+\.08\*wave;/);
 assert.match(visual,/ctx\.shadowColor='rgba\(255,198,68,/);
 assert.match(visual,/ctx\.shadowBlur=12\+16\*wave;/);
 assert.match(visual,/if\(sym==='TOMB'\)return;/,
   'Não desenhar a pirâmide duas vezes, uma delas debaixo dos símbolos');
 const overlay=visual.indexOf('Sobreposição real: uma única camada por cima');
 const reels=visual.indexOf('drawReelSeparators(R.left,R.top,R.step,R.gh)');
 const paylines=visual.indexOf('this.drawActivePayline(t,R.left');
 assert.ok(overlay>reels&&overlay<paylines,
   'Desenhar as pirâmides depois dos tambores e antes das linhas');
 assert.match(visual,/ctx\.rect\(R\.left-2,R\.top-5,4\*R\.step\+R\.cw\+4,R\.gh\*3\+10\)/);
 assert.doesNotMatch(visual,/ctx\.globalCompositeOperation='source-atop'/,
   'Sem banho dourado que preencha o fundo transparente');
});
