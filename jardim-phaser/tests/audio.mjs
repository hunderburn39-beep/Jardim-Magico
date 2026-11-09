import assert from 'node:assert/strict';
import {readFile} from 'node:fs/promises';
import {createHash} from 'node:crypto';
const read=p=>readFile(new URL(p,import.meta.url),'utf8');
const moduleOf=async p=>import('data:text/javascript;base64,'+Buffer.from(await read(p)).toString('base64'));
const {GardenAudio,AUDIO_FILES}=await moduleOf('../js/audio.js');
const {dropHudView}=await moduleOf('../js/drop-hud.js');
let count=0;
const check=(value,message)=>{assert.ok(value,message);count++};
class Player{
 constructor(src){this.src=src;this.paused=true;this.listeners={};this.plays=0}
 addEventListener(name,fn){this.listeners[name]=fn}
 setAttribute(){}
 play(){this.paused=false;this.plays++;this.listeners.playing?.();return Promise.resolve()}
 pause(){this.paused=true;this.listeners.pause?.()}
}
class Context{
 constructor(){this.state='running';this.destination={};this.sources=[];this.currentTime=0}
 resume(){this.state='running';return Promise.resolve()}
 decodeAudioData(){return Promise.resolve({duration:.2})}
 createGain(){return {gain:{value:0},connect(){},disconnect(){}}}
 createBufferSource(){
  const node={connect(){},disconnect(){},start(){this.started=true},stop(){this.stopped=true}};
  this.sources.push(node);return node;
 }
}
const store=new Map(),storage={getItem:k=>store.get(k)||null,setItem:(k,v)=>store.set(k,v)};
const view={document:{hidden:false},matchMedia:()=>({matches:false})};
let fetches=0;
const player=new GardenAudio({view,storage,AudioType:Player,ContextType:Context,
 fetchFile:async()=>{fetches++;return {ok:true,arrayBuffer:async()=>new ArrayBuffer(4)}}});
check(player.background===null&&player.ctx===null,'Não inicia áudio antes do toque');
check(player.unlock(),'Primeiro toque desbloqueia o áudio');
await Promise.all([...player.buffers.values()]);
check(player.musicPlaying&&player.background.loop&&player.background.volume<1,'Música zen em loop e volume suave');
player.unlock();check(fetches===Object.keys(AUDIO_FILES).length,'Efeitos carregados e descodificados uma vez');
check(await player.play('win'),'Efeito reproduz uma gravação');
check(player.ctx.sources.at(-1).started&&player.active.size===1,'Efeito usa amostra descodificada');
player.toggleEffects();check(!player.effects&&player.active.size===0&&player.musicPlaying,'Desligar efeitos mantém fundo zen');
player.toggleEffects();player.toggleMusic();
check(player.effects&&!player.musicPlaying&&!player.music,'Desligar música mantém efeitos');
player.toggleMusic();check(player.musicPlaying,'Música pode ser ligada novamente');
player.pause();check(!player.musicPlaying&&player.active.size===0,'Segundo plano para todas as gravações');
player.resume();check(player.musicPlaying,'Retoma o fundo sem duplicar leitores');
view.document.hidden=true;player.pause();player.resume();
check(!player.unlock()&&!player.musicPlaying&&!await player.play('coin'),'Página oculta permanece silenciosa');
view.document.hidden=false;
const restored=new GardenAudio({view,storage,AudioType:Player,ContextType:Context});
check(restored.effects===player.effects&&restored.music===player.music,'Preferências de som conservadas');
const waits=[];
const pending=new GardenAudio({view,AudioType:Player,ContextType:Context,
 fetchFile:()=>new Promise(resolve=>waits.push(resolve))});
pending.unlock({startMusic:false});
const effect=pending.play('scatter');pending.pause();
waits.forEach(resolve=>resolve({ok:true,arrayBuffer:async()=>new ArrayBuffer(4)}));
check(!await effect&&pending.active.size===0,'Uma amostra atrasada não toca após suspensão');
for(const factor of [0,1,NaN,undefined])check(!dropHudView([],factor).visible,'Sem multiplicador: painel oculto');
check(dropHudView([],5).visible&&dropHudView([],5).factor===5,'Multiplicador real: painel visível');
check(dropHudView([1],1).visible,'WILD premiado de valor um é um multiplicador presente');
check(!dropHudView([0,9,NaN],1).visible,'Valores inválidos não mostram o painel');
const manifest=JSON.parse(await read('../assets/audio/manifest.json'));
for(const [name,metadata] of Object.entries(manifest)){
 const bytes=await readFile(new URL('../assets/audio/'+name+'.mp3',import.meta.url));
 check(bytes.length>1000&&createHash('sha256').update(bytes).digest('hex')===metadata.sha256,'Ficheiro MP3 íntegro: '+name);
}
check(manifest['zen-meditation'].seconds===120,'Fundo zen de dois minutos');
const code=await read('../js/audio.js');
check(!code.includes('createOscillator')&&!code.includes('setInterval'),'Sem bipes sintetizados nem temporizador musical');
console.log(JSON.stringify({suite:'recorded-audio-and-hud',checks:count,effects:Object.keys(AUDIO_FILES).length,zenSeconds:120}));
