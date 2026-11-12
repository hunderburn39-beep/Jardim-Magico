import assert from 'node:assert/strict';
import {readFileSync,existsSync,statSync} from 'node:fs';
import {dirname,resolve,join} from 'node:path';
import {fileURLToPath} from 'node:url';
import vm from 'node:vm';

const preview=resolve(dirname(fileURLToPath(import.meta.url)),'..');
const html=readFileSync(join(preview,'index.html'),'utf8');
assert.match(html,/Stake Preview V1\.6/);
assert.match(html,/id="musicBtn"/);
assert.match(html,/id="soundBtn"/);
assert.match(html,/Kenney \(CC0\)/);
assert.match(html,/Kevin MacLeod/);
const inline=html.match(/<script>([\s\S]*?)<\/script>/)?.[1];
assert.ok(inline,'Jogo contém código JavaScript');
const source=inline.split('$("introPlay").addEventListener')[0];
const art=source.match(/const TYPES=\[([\s\S]*?)\];/)?.[1];
assert.ok(art,'Lista de símbolos encontrada');
const imagePaths=[...art.matchAll(/image:"(\.\.\/assets\/imagens\/[^"]+)"/g)].map(x=>x[1]);
assert.equal(imagePaths.length,12,'Todos os doze símbolos têm imagem HD, sem emoji');
for(const p of imagePaths){
  const local=resolve(preview,p);
  assert.ok(existsSync(local),'Símbolo não existe: '+p);
  assert.ok(statSync(local).size>1000,'Símbolo vazio: '+p);
}
const effects=['spin','stop','cascade','coin','wild','scatter','win','bonus','click'];
for(const name of effects){
  const path=resolve(preview,'../jardim-phaser/assets/audio',name+'.mp3');
  assert.ok(existsSync(path),'Efeito MP3 ausente: '+name);
  assert.ok(statSync(path).size>1500,'Efeito inválido: '+name);
}
assert.ok(existsSync(resolve(preview,'../jardim-phaser/assets/audio/zen-meditation.mp3')));
const elements=new Map();
function elem(id){
  if(!elements.has(id))elements.set(id,{style:{},textContent:'',setAttribute(k,v){this[k]=v},querySelectorAll(){return []}});
  return elements.get(id);
}
const files=[],played=[],paused=[];
class FakeAudio{
  constructor(url){this.src=url;this.volume=1;this.loop=false;this.paused=true;files.push(this)}
  setAttribute(){}
  play(){this.paused=false;played.push(this.src);return Promise.resolve()}
  pause(){this.paused=true;paused.push(this.src)}
}
const store=new Map();
const ctx=vm.createContext({
  Audio:FakeAudio,
  document:{hidden:false,getElementById:elem,querySelectorAll(){return []}},
  localStorage:{getItem:k=>store.get(k)??null,setItem:(k,v)=>store.set(k,v)},
  console,Math,Intl,Uint32Array,crypto:{getRandomValues:x=>{x[0]=13;return x}},setTimeout,clearTimeout
});
vm.runInContext(source,ctx,{timeout:10000});
assert.equal(played.length,0,'Sem início automático proibido antes de gesto no Safari');
vm.runInContext('restoreAudioPrefs();activateAudio()',ctx);
assert.equal(played.length,1,'Após gesto: uma música de fundo começa');
assert.ok(played[0].endsWith('/zen-meditation.mp3'));
assert.equal(files[0].loop,true,'Música de fundo em loop');
assert.equal(files[0].volume,.56,'Volume zen limitado');
assert.equal(vm.runInContext('backgroundMusic',ctx),files[0]);
vm.runInContext('activateAudio()',ctx);
assert.equal(played.length,1,'Cada spin não deve reiniciar a música');
vm.runInContext('playRecorded("spin")',ctx);
vm.runInContext('playRecorded("stop")',ctx);
vm.runInContext('playRecorded("coin")',ctx);
vm.runInContext('playRecorded("wild")',ctx);
assert.ok(played.some(x=>x.endsWith('/spin.mp3')));
assert.ok(played.some(x=>x.endsWith('/stop.mp3')));
assert.ok(played.some(x=>x.endsWith('/coin.mp3')));
assert.ok(played.some(x=>x.endsWith('/wild.mp3')));
vm.runInContext('suspendAudio()',ctx);
assert.ok(files[0].paused,'Música pausa quando a aba fica oculta');
assert.ok(paused.some(x=>x.endsWith('/spin.mp3')),'Efeito spin interrompido ao ocultar');
vm.runInContext('musicEnabled=false;sound=false;saveAudioPrefs()',ctx);
assert.match(store.get('jardim_stake_audio_v09'),/"music":false/);
assert.match(store.get('jardim_stake_audio_v09'),/"effects":false/);
vm.runInContext('musicEnabled=true;sound=true;restoreAudioPrefs()',ctx);
assert.equal(vm.runInContext('musicEnabled',ctx),false);
assert.equal(vm.runInContext('sound',ctx),false);
console.log('Stake Preview V1.6: 12 símbolos HD e 10 MP3 confirmados, música contínua após gesto, som independente e pausa ao ocultar.');
