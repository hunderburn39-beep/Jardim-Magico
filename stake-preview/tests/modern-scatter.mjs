import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {dirname,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const dir=dirname(fileURLToPath(import.meta.url));
const audio=readFileSync(join(dir,'..','modern-audio.js'),'utf8');
const html=readFileSync(join(dir,'..','index.html'),'utf8');
assert.match(html,/<script src="modern-audio\.js"><\/script>/);
for(const selector of ['scatter-cell','scatter-tease','scatter-bonus','scatter-banner','scatter-revealed','scatterHalo','scatterTriggerBurst']) {
  assert.ok(html.includes(selector),'missing distinct Scatter visual effect: '+selector);
}
assert.match(html,/id="scatterBanner"/);
assert.match(html,/function showScatterScene\(/);
assert.match(html,/scatterCue\(visibleScatters\)/);
assert.match(html,/const revealedScatters=showScatterScene/);
assert.match(html,/prizeCue\(amount\/100\*/);
assert.match(html,/prizeCue\(payout,bet,"final"\)/);
assert.match(html,/clearScatterScene\(\)/);

const tones=[],layers=[];
const param=()=>({
  value:0,
  setValueAtTime(value){this.value=value},
  exponentialRampToValueAtTime(value){this.value=value}
});
class FakeNode{
  constructor(){this.gain=param();this.frequency=param();this.threshold=param();this.knee=param();this.ratio=param();this.attack=param();this.release=param()}
  connect(){return this}
  disconnect(){}
  start(){this.started=true}
  stop(){this.stopped=true}
}
class FakeCtx{
  state='running';currentTime=5;sampleRate=8000;destination={};
  createGain(){return new FakeNode()}
  createDynamicsCompressor(){return new FakeNode()}
  createOscillator(){const node=new FakeNode();tones.push(node);return node}
  createBuffer(channels,count){return {getChannelData(){return new Float32Array(count)}}}
  createBiquadFilter(){return new FakeNode()}
  createBufferSource(){const node=new FakeNode();layers.push(node);return node}
  resume(){this.state='running';return Promise.resolve()}
  suspend(){this.state='suspended';return Promise.resolve()}
}
const ctx=vm.createContext({AudioContext:FakeCtx,Math,Float32Array});
vm.runInContext(audio,ctx);
const fx=ctx.GardenModernAudio;
assert.ok(fx);
assert.equal(tones.length,0,'no sound before user gesture');
assert.equal(fx.play('scatterLand'),false,'sound is locked until tap');
assert.equal(fx.unlock(),true);
const motif=(name)=>{
  const begin=tones.length,noise=layers.length;
  assert.equal(fx.play(name),true,name);
  const out={tones:tones.length-begin,noise:layers.length-noise};
  assert.ok(out.tones>=2,'layered audio for '+name);
  return out;
};
const one=motif('scatterLand'),two=motif('scatterTease'),three=motif('scatterTrigger');
assert.ok(two.tones>one.tones,'second Scatter has rising suspense layers');
assert.ok(three.tones>two.tones,'third Scatter has a bigger musical hit');
const small=motif('prizeSmall'),large=motif('prizeLarge'),final=motif('prizeFinal'),coin=motif('prizeCoin');
assert.ok(large.tones>small.tones);
assert.ok(final.noise>large.noise,'final prize adds an airy shimmer');
assert.ok(coin.tones>=2);
fx.suspend();
assert.equal(fx.play('scatterTrigger'),false,'background tab must not play synthesized cues');
assert.equal(fx.unlock(),true);

const source=html.match(/<script>([\s\S]*?)<\/script>/)?.[1]?.split('$("introPlay").addEventListener')[0];
assert.ok(source);
const elements=new Map();
function el(id){
  if(!elements.has(id)){
    const classes=new Set();
    elements.set(id,{id,hidden:false,textContent:'',innerHTML:'',style:{},
      classList:{add(...names){names.forEach(n=>classes.add(n))},remove(...names){names.forEach(n=>classes.delete(n))},contains(n){return classes.has(n)},toggle(n,v){v?classes.add(n):classes.delete(n)}},
      setAttribute(){},querySelectorAll(){return []}});
  }
  return elements.get(id);
}
const doc={hidden:false,getElementById:el,querySelectorAll(){return []}};
const game=vm.createContext({document:doc,Math,Intl,console,Uint32Array,setTimeout,clearTimeout});
vm.runInContext(source,game,{timeout:10000});
const b1=[[9,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]];
const b2=[[9,0,0],[0,9,0],[0,0,0],[0,0,0],[0,0,0]];
const b3=[[9,0,0],[0,9,0],[0,0,9],[0,0,0],[0,0,0]];
game.b1=b1;game.b2=b2;game.b3=b3;
const before=vm.runInContext('balance',game);
assert.equal(vm.runInContext('showScatterScene(b1,false)',game),1);
assert.equal(el('scatterBanner').hidden,true);
assert.equal(vm.runInContext('showScatterScene(b2,false)',game),2);
assert.equal(el('scatterBanner').hidden,false);
assert.equal(el('reelFrame').classList.contains('scatter-tease'),true);
assert.match(el('scatterBanner').textContent,/FALTA 1/);
assert.equal(vm.runInContext('showScatterScene(b3,false)',game),3);
assert.equal(el('reelFrame').classList.contains('scatter-bonus'),true);
assert.match(el('scatterBanner').textContent,/FREE SPINS/);
vm.runInContext('showScatterScene(b2,true)',game);
assert.match(el('scatterBanner').textContent,/\+8 FREE SPINS/);
vm.runInContext('clearScatterScene()',game);
assert.equal(el('scatterBanner').hidden,true);
assert.equal(el('reelFrame').classList.contains('scatter-tease'),false);
vm.runInContext('draw(b3,[])',game);
assert.equal((el('reels').innerHTML.match(/scatter-cell/g)||[]).length,3);
assert.equal(vm.runInContext('balance',game),before,'audio/visual FX cannot change credits or result');
console.log('Original modern audio: 3 different Scatter cues, 3 prize tiers, coin chime; visuals and credits verified.');
