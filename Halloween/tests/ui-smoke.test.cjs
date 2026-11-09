"use strict";
/* Teste leve da cena Phaser; não usa navegador nem descarrega dependências. */
const test=require("node:test");
const assert=require("node:assert/strict");
const fs=require("node:fs");
const path=require("node:path");
const vm=require("node:vm");
const root=path.join(__dirname,"..");
function sceneFixture(){
  let configuration;
  class Scene{}
  class Game{constructor(config){configuration=config;}}
  const storage=new Map();
  const ctx={
    Phaser:{AUTO:1,Scene,Game,Scale:{FIT:1,CENTER_BOTH:1}},
    window:{location:{search:"?smoke=1"}},
    document:{getElementById:()=>({textContent:""})},
    URLSearchParams,
    TextEncoder:require("node:util").TextEncoder,
    btoa:binary=>Buffer.from(binary,"binary").toString("base64"),
    localStorage:{
      getItem:key=>storage.get(key)??null,
      setItem:(key,value)=>storage.set(key,String(value))
    }
  };
  const fakeMath=Object.create(Math);fakeMath.random=()=>.42;ctx.Math=fakeMath;
  vm.createContext(ctx);
  for(const name of ["engine","art","game"])
    vm.runInContext(fs.readFileSync(path.join(root,"js",name+".js"),"utf8"),ctx,{filename:name+".js"});
  const node=(key="h-pumpkin")=>{
    const o={texture:{key},text:"",visible:true,events:{}};
    for(const method of ["fillStyle","fillRect","fillCircle","fillEllipse","fillTriangle","lineStyle",
      "lineBetween","strokeCircle","strokeTriangle","strokeRoundedRect",
      "fillRoundedRect","fillGradientStyle","clear","setVisible","setOrigin",
      "setText","setInteractive","disableInteractive","on","setDepth",
      "add","setDisplaySize","setMask","setTexture","setAlpha","setAngle",
      "beginPath","moveTo","lineTo","strokePath"]){
        o[method]=function(...args){
          if(method==="on")o.events[args[0]]=args[1];
          if(method==="setText")o.text=args[0];
          if(method==="setTexture")o.texture.key=args[0];
          if(method==="setVisible")o.visible=args[0];
          return o;
        };
    }
    o.createGeometryMask=()=>({});
    return o;
  };
  function mount(){
    const scene=new configuration.scene[0]();
    const queue=[];
    scene.add={
      graphics:()=>node(),text:(x,y,value)=>{const item=node();item.text=String(value);return item;},zone:()=>node(),
      image:(x,y,key)=>node(key),rectangle:()=>node(),container:()=>node()
    };
    scene.load={svg:(key,uri)=>{
      assert.ok(uri.startsWith("data:image/svg+xml;base64,"),"SVG não está em base64");
      const raw=Buffer.from(uri.split(",")[1],"base64").toString("utf8");
      assert.ok(raw.startsWith("<svg xmlns="),"XML SVG inválido");
      assert.ok(key.startsWith("h-"));
    }};
    scene.input={keyboard:{on:()=>{}}};
    scene.cameras={main:{flash:()=>{}}};
    scene.tweens={add:({onComplete})=>{if(onComplete)onComplete()}};
    scene.time={
      delayedCall:(time,callback)=>queue.push(callback),
      addEvent:({callback,repeat,loop})=>{
        if(!loop)for(let i=0;i<=(repeat||0);i++)callback();
        return {remove:()=>{}};
      }
    };
    scene.preload();scene.create();
    return {scene,drain:()=>{for(let n=0;queue.length&&n<100;n++)queue.shift()();}};
  }
  return {ctx,storage,mount};
}
test("Carrega 10 símbolos, 30 trajetos, roleta com 30 números e 4 acumulados",()=>{
  const f=sceneFixture(),{scene:s}=f.mount();
  assert.equal(Object.keys(f.ctx.HalloweenArt).length,10);
  assert.equal(f.ctx.HalloweenEngine.SYMBOLS.length,10);
  assert.equal(f.ctx.HalloweenEngine.LINES.length,30);
  assert.equal(s.state.lines,1);
  assert.equal(s.state.bet,1);
  assert.equal(s.symbols.reduce((n,c)=>n+c.length,0),15);
  assert.equal(s.lineMarkers.length,30);
  assert.equal(s.jackpotsText.length,4);
  assert.equal(f.ctx.HalloweenEngine.WHEEL_SECTORS.length,30);
});
test("Abre e fecha tabela de referência sem bloquear o giro",()=>{
  const {scene:s}=sceneFixture().mount();
  s.togglePaytable();assert.equal(s.tableOpen,true);
  const old=s.state.spins;
  s.spin();assert.equal(s.state.spins,old);
  s.togglePaytable();assert.equal(s.tableOpen,false);
});
test("Mostra o percurso da linha e recupera o resultado de um giro",()=>{
  const f=sceneFixture(),a=f.mount();
  a.scene.highlight([{line:2,symbol:"wild",count:3,amount:120,
    cells:[[0,0],[1,0],[2,0]]}]);
  assert.match(a.scene.winLabel.text,/LINHA 2/);
  a.scene.spin();a.drain();
  assert.equal(a.scene.state.spins,1);
  const saved=JSON.parse(f.storage.get("halloween-classico-v02"));
  assert.equal(saved.spins,1);
  const b=f.mount(),restored=b.scene.symbols.map(c=>c.map(s=>s.texture.key.slice(2)));
  assert.deepEqual(JSON.parse(JSON.stringify(restored)),saved.lastGrid);
  assert.equal(b.scene.state.balance,saved.balance);
});

test("Doze morangos recuperam a escolha e não repetem pagamento",()=>{
  const f=sceneFixture(),a=f.mount(),s=a.scene;
  s.state.pendingStrawberry={choices:Array.from({length:12},(_,i)=>i+1)};
  s.store();
  const next=f.mount(),loaded=next.scene;
  assert.equal(loaded.strawberryOpen,true);
  assert.equal(loaded.strawberryHits.length,12);
  const balance=loaded.state.balance;
  loaded.pickStrawberry(5);
  assert.equal(loaded.state.balance,balance+6);
  assert.equal(loaded.state.pendingStrawberry,null);
  loaded.pickStrawberry(5);
  assert.equal(loaded.state.balance,balance+6);
  next.drain();
  assert.equal(loaded.strawberryOpen,false);
});

test("Cinco dados vermelhos abrem, recuperam e não pagam duas vezes",()=>{
  const f=sceneFixture(),first=f.mount();
  const old=first.scene.state.balance;
  first.scene.state.pendingDice={faces:[1,2,3,4,6]};
  first.scene.store();
  const second=f.mount(),s=second.scene;
  assert.equal(s.diceOpen,true);
  assert.equal(s.diceHits.length,5);
  assert.equal(s.state.balance,old);
  const before=s.state.spins;
  s.spin();assert.equal(s.state.spins,before);
  s.pickDice(4);
  const award=s.state.bet*6;
  assert.equal(s.state.balance,old+award);
  assert.equal(s.state.pendingDice,null);
  s.pickDice(4);
  assert.equal(s.state.balance,old+award);
  second.drain();
  assert.equal(s.diceOpen,false);
});

test("Botões − e + percorrem os cinco escalões sem separar aposta e linhas",()=>{
  const f=sceneFixture(),s=f.mount().scene;
  const tiers=[[1,1],[5,5],[10,15],[15,25],[20,30]];
  assert.deepEqual([s.state.bet,s.state.lines],tiers[0]);
  for(let i=1;i<tiers.length;i++){
    s.changeBet();
    assert.deepEqual([s.state.bet,s.state.lines],tiers[i]);
    assert.ok(s.linesText.text.includes(String(tiers[i][1])));
  }
  s.changeBet();
  assert.deepEqual([s.state.bet,s.state.lines],tiers[0]);
  s.changeLines();
  assert.deepEqual([s.state.bet,s.state.lines],tiers[4]);
  s.renderState();
  assert.ok(s.stake.text.includes("0,20"));
  assert.ok(s.stake.text.includes("€"));
});

test("Cinco linhas verticais são numeradas de 26 a 30, só ativas no escalão máximo",()=>{
  const {scene:s}=sceneFixture().mount();
  assert.deepEqual(Array.from(s.lineMarkers.slice(25),m=>String(m.t.text)).join(","),
    "26,27,28,29,30");
  assert.ok(s.lineMarkers.slice(25).every(m=>!m.t.visible));
  s.state=s.state&&{...s.state,bet:20,lines:30};
  s.renderState();
  assert.ok(s.lineMarkers.slice(25).every(m=>m.t.visible));
  s.highlight([{line:28,symbol:"witch",direction:"vertical",count:3,
    amount:6,cells:[[2,0],[2,1],[2,2]]}]);
  assert.match(s.winLabel.text,/VERTICAL 28/);
  assert.match(s.winLabel.text,/WITCH/);
  s.highlight([{line:28,symbol:"scatter",direction:"vertical",count:3,
    amount:0,bonus:true,cells:[[2,0],[2,1],[2,2]]}]);
  assert.match(s.winLabel.text,/BÓNUS VERTICAL · TAMBOR 3/);
});
