// Testes sem browser para proteger as regras e os estados dos tambores V3.
// Executar: node jardim-3d/tests/smoke-v3.mjs
import assert from 'node:assert/strict';
import {readFile} from 'node:fs/promises';
const read=async path=>readFile(new URL(path,import.meta.url),'utf8');
const importSource=async path=>import('data:text/javascript;charset=utf-8,'+
  encodeURIComponent(await read(path)));
const core=await importSource('../js/demo-engine.js');
const reel=await importSource('../js/reels-v23.js');
const db=await importSource('../js/database-v3.js');
const {DemoBank,winningCells}=core;
let passed=0;
const check=(condition,message)=>{
 assert.ok(condition,message);passed++;
};
let totalSpins=0,multi=0;
for(const stake of [.05,.2,1,5,10]){
 for(const reserve of [0,.01,.2,2,10,100,1000]){
  const bank=new DemoBank();
  bank.balance=50000;bank.stake=stake;bank.reserve=reserve;
  for(let i=0;i<300;i++){
   const out=bank.spin();
   check(out.ok,'giro calculado');
   check(out.stages.at(-1).ev.lines===0,'cascata terminou sem novas linhas');
   check(bank.reserve>=-0.00001,'cofre não negativo');
   if(!out.free)check(Math.abs(out.potential-out.credited)<.001,'prémio não truncado');
   if(bank.mode==='hold'){
    const amount=bank.reserve;
    let turns=0;
    while(bank.mode==='hold'&&turns++<50){
     const result=bank.holdTurn();
     if(result.done)check(result.prize<=amount+.001,'Hold financiado');
    }
    check(bank.mode==='base','Hold concluiu');
   }
   totalSpins++;
  }
 }
}
let seed=1234567,calls=0;
const forced=()=>{
 if(calls++<180)return 0;
 seed=(Math.imul(seed,1664525)+1013904223)>>>0;
 return seed/4294967296;
};
const forcedBank=new DemoBank();forcedBank.balance=100;forcedBank.reserve=1e6;
const forcedSpin=forcedBank.spin(forced);
check(forcedSpin.stages.length>5,'sem limite arbitrário de quatro quedas');
check(forcedSpin.stages.at(-1).ev.lines===0,'fim natural');
check(Math.abs(forcedSpin.potential-forcedSpin.credited)<.001,'pagamento integral de sequência longa');
const full=new DemoBank();full.reserve=100;full.stake=.2;
check(!!full.startHold(()=>0),'Hold iniciou');
let fullSpin;
for(let i=0;i<50&&full.mode==='hold';i++)fullSpin=full.holdTurn(()=>0);
check(fullSpin.done,'Hold terminou');
check(fullSpin.prize<=100,'Hold sem pagamento a descoberto');
const dry=new DemoBank();dry.reserve=.01;dry.stake=.2;
check(dry.startHold()===false,'não inicia seis moedas sem cobertura');
class Entity{
 constructor(name){this.name=name;this.enabled=true;this.render={material:null};this.children=[];this.position=[0,0,0]}
 setLocalPosition(...v){this.position=v}
 setLocalScale(...v){this.scale=v}
 setLocalEulerAngles(...v){this.rotation=v}
 addChild(child){this.children.push(child)}
}
const pc={Entity};
const app={root:new Entity('root')};
const model=(name,type,p,scale,material,parent)=>{
 const e=new Entity(name);e.type=type;e.render.material=material;
 e.setLocalPosition(...p);e.setLocalScale(...scale);(parent||app.root).addChild(e);return e;
};
const motion=reel.createVerticalReels({pc,model,app,
 mat:(color,opts)=>({color,opts}),symbols:Array.from({length:12},(_,i)=>i),
 textureFromSymbol:(s,i)=>({id:i})});
const board=Array.from({length:5},(_,c)=>Array.from({length:3},(_,r)=>(c+r*3)%12));
let done=0;
check(motion.startSpin(board,false,()=>done++),'giro visual iniciado');
for(let i=0;i<300;i++)motion.update(.016);
check(done===1,'callback da rotação recebido uma só vez');
for(let c=0;c<5;c++)for(let r=0;r<3;r++)
 check(motion.reelTiles[c][r].idx===board[c][r],'posição vertical correta');
const removed=[{c:0,r:0},{c:0,r:1},{c:3,r:2}];
const next=core.collapseBoard(board,removed,()=>.5);
let dropped=0;
check(motion.beginCascade(next,removed,()=>dropped++),'queda iniciada');
for(let i=0;i<120;i++)motion.update(.016);
check(dropped===1,'callback da queda recebido uma só vez');
for(let c=0;c<5;c++)for(let r=0;r<3;r++)
 check(motion.reelTiles[c][r].idx===next[c][r],'resultado visual da queda correto');
check(motion.startSpin(board,false,()=>done++),'novo giro após queda');
for(let i=0;i<20;i++)motion.update(.016);
check(motion.forceReset(next),'recuperação forçada');
check(!motion.isBusy(),'estado livre após falha de animação');
check(motion.startSpin(board,true,()=>done++),'giro após recuperação');
for(let i=0;i<300;i++)motion.update(.016);
check(!motion.isBusy(),'nenhum bloqueio na rotação seguinte');
// IndexedDB quando não está disponível: a alternativa localStorage deve
// conservar o saldo sem guardar credenciais nem permitir dinheiro real.
const stored=new Map();
globalThis.localStorage={
 getItem:key=>stored.get(key)||null,
 setItem:(key,v)=>stored.set(key,v),
 removeItem:key=>stored.delete(key)
};
const local=new db.DemoDatabase();
await local.connect();
check(local.mode==='localStorage','fallback local quando IndexedDB indisponível');
await local.save({balance:12,reserve:45,spins:2},next,{type:'spin',paid:.4,stake:.2});
check((await local.load()).snapshot.balance===12,'restauração do saldo');
check((await local.history()).length===1,'histórico de giro');
await local.clear();
check(await local.load()===null,'reinicialização dos dados locais');
console.log('Jardim Mágico 3D V3: '+passed+' verificações aprovadas; '+totalSpins+' giros simulados.');
