import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {fileURLToPath} from 'node:url';
import {dirname,join} from 'node:path';
import vm from 'node:vm';

const html=readFileSync(join(dirname(fileURLToPath(import.meta.url)),'..','index.html'),'utf8');
assert.match(html,/Stake Preview V1\.8\.6/);
for(const target of ['introScreen','introPlay','bonusCurtain','bonusCurtainTitle','bonusCurtainSubtitle','bonusOrbit'])
  assert.ok(html.includes('id="'+target+'"'),target+' is present');
assert.match(html,/ENTRAR NO JARDIM/);
assert.match(html,/@media\(orientation:landscape\)/);
assert.match(html,/@media\(max-height:700px\)/);
assert.match(html,/\$\("introPlay"\)\.addEventListener\("click",enterGarden\)/,'Start button invokes entry from a real user click');
const code=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(code);
const source=code.split('$("introPlay").addEventListener')[0];
const elements=new Map();
function get(id){
 if(!elements.has(id)){
   const classes=new Set();
   elements.set(id,{
     id,hidden:false,style:{},textContent:'',innerHTML:'',children:[],dataset:{},
     classList:{add(k){classes.add(k)},remove(k){classes.delete(k)},contains(k){return classes.has(k)},toggle(k,p){if(p)classes.add(k);else classes.delete(k)}},
     setAttribute(k,v){this[k]=v},querySelector(){return null},querySelectorAll(){return []},
   });
 }
 return elements.get(id);
}
const plays=[],created=[];
class AudioFake{
 constructor(src){this.src=src;this.volume=1;this.loop=false;this.paused=true;created.push(this)}
 setAttribute(){}
 play(){this.paused=false;plays.push(this.src);return Promise.resolve()}
 pause(){this.paused=true}
}
const storage=new Map();
const ctx=vm.createContext({
 Audio:AudioFake,
 document:{hidden:false,getElementById:get,querySelectorAll:()=>[]},
 localStorage:{getItem:k=>storage.get(k)??null,setItem:(k,v)=>storage.set(k,v)},
 window:{},Date,Math,Intl,Uint32Array,console,
 crypto:{getRandomValues(a){a[0]=31337;return a}},
 setTimeout(callback){callback();return 1;},clearTimeout(){},
});
vm.runInContext(source,ctx,{timeout:10000});
assert.equal(vm.runInContext('gardenEntered',ctx),false);
assert.equal(plays.length,0,'Safari autoplay is not attempted before a click');
const result=vm.runInContext('enterGarden()',ctx);
assert.equal(result,true);
assert.equal(vm.runInContext('gardenEntered',ctx),true);
assert.equal(get('introScreen').hidden,true,'Splash screen disappears after entry');
assert.equal(get('introScreen').classList.contains('closing'),true);
assert.equal(get('introScreen')['aria-hidden'],'true','Splash is hidden from assistive tech');
assert.equal(plays.filter(x=>x.endsWith('zen-meditation.mp3')).length,1,'Background music starts on user entry');
assert.equal(vm.runInContext('enterGarden()',ctx),false,'Entry cannot run twice');
assert.equal(plays.filter(x=>x.endsWith('zen-meditation.mp3')).length,1,'Music never restarts on repeated entry');

const beforeBalance=vm.runInContext('balance',ctx);
await vm.runInContext('showBonusCurtain("FREE SPINS","15 RODADAS MÁGICAS","✦")',ctx);
assert.equal(get('bonusCurtain').hidden,true,'Free transition does not remain stuck');
assert.equal(get('bonusCurtain').classList.contains('visible'),false);
assert.equal(get('bonusCurtainTitle').textContent,'FREE SPINS');
assert.equal(get('bonusCurtainSubtitle').textContent,'15 RODADAS MÁGICAS');
await vm.runInContext('showBonusCurtain("HOLD & WIN","6 MOEDAS · 3 TENTATIVAS","◉")',ctx);
assert.equal(get('bonusCurtain').hidden,true,'Hold transition does not remain stuck');
assert.equal(get('bonusOrbit').textContent,'◉');
assert.equal(vm.runInContext('balance',ctx),beforeBalance,'Transitions never touch balance');
assert.equal(vm.runInContext('pendingRound',ctx),null,'Transitions never create round records');
assert.ok(plays.some(x=>x.endsWith('bonus.mp3')),'Recorded bonus intro effect is used');
assert.equal(plays.filter(x=>x.endsWith('zen-meditation.mp3')).length,1,'Background keeps playing without restart through both bonuses');

console.log('Stake Preview V1.8.6: intro requires tap, music remains continuous, free and Hold transitions finish, no credit changes.');
