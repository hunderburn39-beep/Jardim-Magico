/* Testes de regressão — Portões do Trovão V14.
 * Executar: node tools/testar-wild-v14.js
 * Só créditos virtuais. Não há chamadas de rede.
 */
"use strict";
const assert=require("node:assert/strict");
global.window=global.window||{};
require("../js/regras-wild-v13.js");
const R=global.window.TrovaoRules;
const PAY={
 crown:[10,25,50],hourglass:[2.5,10,25],ring:[2,5,15],
 goblet:[1.5,2,12],ruby:[1,1.5,10],amethyst:[.8,1.2,8],
 amber:[.5,1,5],emerald:[.4,.9,4],sapphire:[.25,.75,2]
};
let testCount=0;
function test(name,callback){callback();testCount++;console.log("✓ "+name)}
function board(...groups){
 const items=[];let id=1;
 for(const [kind,count,value] of groups){
  for(let i=0;i<count;i++)items.push({id:id++,kind,...(value!==undefined?{value}:{})});
 }
 assert(items.length<=30);
 const fillers=R.KINDS.filter(kind=>!groups.some(([used])=>used===kind)).slice(0,5);
 assert(fillers.length>=5,"Precisamos de cinco símbolos diferentes para completar a grelha");
 let fill=0;
 while(items.length<30)items.push({id:id++,kind:fillers[fill++%fillers.length]});
 return items;
}
function win(...groups){return R.score(board(...groups),PAY,1)}
test("WILD substitui as nove peças pagantes",()=>{
 assert.equal(R.KINDS.length,9);
 for(const kind of R.KINDS)assert.equal(R.canWildReplace(kind),true,kind);
});
test("WILD nunca substitui SCATTER, MULT ou outro WILD",()=>{
 for(const kind of ["scatter","mult","wild"])assert.equal(R.canWildReplace(kind),false,kind);
 assert.deepEqual([...R.EXCLUDED_WILD_KINDS],["scatter","mult","wild"]);
});
test("sete coroas com WILD ×5 formam oito coroas e pagam 50×",()=>{
 const r=win(["crown",7],["wild",1,5]);
 assert.equal(r.pay,50);assert.equal(r.result[0].wildCount,1);
 assert.equal(r.result[0].wildMultiplier,5);
 assert(r.matched.has("crown")&&r.matched.has("wild"));
});
test("WILD ×1 completa combinação sem aumento extra",()=>{
 assert.equal(win(["ruby",7],["wild",1,1]).pay,1);
});
test("SCATTER sozinho nunca recebe substituição WILD",()=>{
 const r=win(["scatter",7],["wild",1,5]);
 assert.equal(r.pay,0);assert(!r.matched.has("scatter"));
});
test("SCATTER não conta WILD para ativação de bónus",()=>{
 const b=board(["scatter",3],["wild",1,5],["ruby",7]);
 const scat=b.filter(x=>x.kind==="scatter").length;
 assert.equal(scat,3);assert.equal(R.scatterMultiplier(scat),0);
});
test("esfera multiplicadora nunca recebe substituição WILD",()=>{
 const r=win(["mult",7,10],["wild",1,5]);
 assert.equal(r.pay,0);assert(!r.matched.has("mult"));
});
test("dois WILD usam só o maior valor e uma única combinação",()=>{
 const r=win(["ruby",7],["wild",1,2],["wild",1,5]);
 assert.equal(r.pay,5);
 assert.equal(r.result[0].count,9);
 assert.equal(r.result[0].wildCount,2);
 assert.equal(r.result[0].wildMultiplier,5);
 assert.equal(r.result.filter(x=>x.wildCount>0).length,1);
});
test("várias combinações naturais pagam; WILD reforça apenas uma",()=>{
 const r=win(["ruby",8],["sapphire",8],["wild",1,5]);
 assert.equal(r.result.length,2);
 assert.equal(r.result.filter(x=>x.wildCount>0).length,1);
 assert.equal(r.result.find(x=>x.symbol==="ruby").amount,5);
 assert.equal(r.result.find(x=>x.symbol==="sapphire").amount,.25);
 assert.equal(r.pay,5.25);
});
test("o Wild não duplica uma combinação que já pagava",()=>{
 const r=win(["crown",8],["wild",1,5]);
 assert.equal(r.pay,50);
 assert.equal(r.result.filter(x=>x.symbol==="crown").length,1);
});
test("sem WILD continuam válidas as tabelas originais",()=>{
 assert.equal(win(["crown",8]).pay,10);
 assert.equal(win(["hourglass",12]).pay,25);
 assert.equal(win(["emerald",10]).pay,.9);
});
test("WILD limitado aos valores de 1× a 5×",()=>{
 assert.deepEqual(R.WILD_VALUES,[1,2,3,4,5]);
 assert.equal(win(["ruby",7],["wild",1,999]).pay,5);
});
console.log("\n"+testCount+" testes passaram. Regras WILD confirmadas.");
