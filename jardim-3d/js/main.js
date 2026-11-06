/* Jardim Mágico 3D · protótipo PlayCanvas 2.23.3
 * Cena, moldura, luzes e objetos são geometrias 3D reais (não imagens recortadas).
 * Demonstração gráfica autónoma: sem acesso à banca nem ao RTP do jogo original.
 * Mobile: WebGL 2, DPR <=1.5, luzes estáticas sem sombras em tempo real.
 */
import * as pc from 'playcanvas';

const $=id=>document.getElementById(id);
const canvas=$('application');
const output=$('result');
const status=$('spinState');
const button=$('spin');
const cameraButton=$('toggleCamera');
const turboButton=$('turbo');
const quality=$('quality');
const symbols=['🌺','🦋','⭐','🍒','💎','🍄','🌹','💚','🎩','🌙','🪨'];
const labels=['Flor Encantada','Borboleta','Estrela','Cerejas','Cristal','Cogumelo','Rosa','Amuleto','Chapéu','Lua','Pedra'];
const palette=[
 [0.82,0.26,0.53],[0.31,0.57,0.95],[1,.78,.23],[.82,.20,.34],
 [.43,.84,.99],[.9,.48,.37],[1,.28,.55],[.45,.94,.61],
 [.66,.38,.93],[.78,.78,1],[.46,.47,.56]
];
const state={running:false,turbo:false,orbit:false,spins:0,timer:0,cam:0,camTarget:0};
let app;
let reelTiles=[];
const fireflies=[];
const glows=[];

function rgb(hex){
 return new pc.Color(parseInt(hex.slice(1,3),16)/255,parseInt(hex.slice(3,5),16)/255,parseInt(hex.slice(5,7),16)/255);
}
function mat(color, opts={}){
 const m=new pc.StandardMaterial();
 m.diffuse=typeof color==='string'?rgb(color):color;
 m.useMetalness=true;
 m.metalness=opts.metal===undefined?0.08:opts.metal;
 m.gloss=Math.min(1,Math.max(0,(opts.gloss===undefined?45:opts.gloss)/100));
 m.emissive=opts.emissive?rgb(opts.emissive):new pc.Color(0,0,0);
 m.emissiveIntensity=opts.emission||0;
 if(opts.opacity!==undefined && opts.opacity<1){
   m.opacity=opts.opacity;
   m.blendType=pc.BLEND_NORMAL;
   m.depthWrite=false;
 }
 m.cull=pc.CULLFACE_NONE;
 m.update();
 return m;
}
function model(name,type,pos,scale,material,parent){
 const e=new pc.Entity(name);
 e.addComponent('render',{type});
 e.render.material=material;
 e.setLocalPosition(pos[0],pos[1],pos[2]);
 e.setLocalScale(scale[0],scale[1],scale[2]);
 (parent||app.root).addChild(e);
 return e;
}
function sphere(name,x,y,z,size,material,parent){
 return model(name,'sphere',[x,y,z],[size,size,size],material,parent);
}
function plant(x,y,z,scale,flowerColor){
 const leaf=mat('#326948',{metal:.02,gloss:18});
 const leaf2=mat('#448855',{metal:.04,gloss:21});
 const center=mat('#efc66c',{metal:.45,gloss:70,emissive:'#754509',emission:.15});
 const petal=mat(flowerColor,{metal:.06,gloss:47,emissive:flowerColor,emission:.09});
 const stem=model('Caule','cylinder',[x,y-.28*scale,z],[.045*scale,.7*scale,.045*scale],leaf);
 stem.setLocalEulerAngles(0,0,12);
 for(let i=0;i<3;i++){
   const a=(i/3)*Math.PI*2;
   const p=model('Folha','sphere',[x+Math.cos(a)*.22*scale,y-.18*scale+Math.sin(a)*.1*scale,z],[.29*scale,.10*scale,.15*scale],i%2?leaf:leaf2);
   p.setLocalEulerAngles(0,0,a*180/Math.PI);
 }
 for(let j=0;j<5;j++){
   const a=j*Math.PI*2/5;
   model('Pétala','sphere',[x+Math.cos(a)*.19*scale,y+.22*scale+Math.sin(a)*.19*scale,z+.05*scale],[.23*scale,.14*scale,.085*scale],petal).setLocalEulerAngles(0,0,j*72);
 }
 sphere('Centro da flor',x,y+.22*scale,z+.16*scale,.09*scale,center);
}
function lighting(){
 const ambient=new pc.Color(.31,.25,.42);
 app.scene.ambientLight=ambient;
 const key=new pc.Entity('Luz dourada');
 key.addComponent('light',{type:'directional',color:new pc.Color(1,.88,.67),intensity:1.35,castShadows:false});
 key.setLocalEulerAngles(45,-30,0);
 app.root.addChild(key);
 const pink=new pc.Entity('Luz roxa');
 pink.addComponent('light',{type:'directional',color:new pc.Color(.76,.36,.94),intensity:.38,castShadows:false});
 pink.setLocalEulerAngles(-12,124,10);
 app.root.addChild(pink);
 const gemLight=new pc.Entity('Luz do lago');
 gemLight.addComponent('light',{type:'omni',color:new pc.Color(.31,.94,.96),intensity:1.1,range:10,castShadows:false});
 gemLight.setLocalPosition(0,-5,-2);
 app.root.addChild(gemLight);
}
function background(){
 const dark=mat('#291b40',{gloss:2,emissive:'#251135',emission:.15});
 model('Céu azul violeta','box',[0,0,-13],[45,32,.3],dark);
 const mist=mat('#53396c',{emissive:'#3f2a73',emission:.18});
 model('Montanha distante','sphere',[-4,-.65,-10],[8,5,1.8],mist);
 model('Montanha distante direita','sphere',[5,-1.0,-9],[8,5,2],mist);
 const ground=mat('#264b3c',{gloss:16});
 model('Solo do bosque','box',[0,-7.25,-5],[26,.9,13],ground);
 const water=mat('#17606d',{metal:.28,gloss:96,emissive:'#0a8994',emission:.55});
 const water2=mat('#338c9b',{metal:.45,gloss:92,emissive:'#077c91',emission:.35,opacity:.74});
 model('Lago oval','sphere',[0,-6.77,-3.6],[7.1,.27,2.3],water);
 for(let i=0;i<4;i++){
   const xx=-2.6+i*1.7;
   model('Reflexo no lago','sphere',[xx,-6.52,-3.1+i*.25],[.68,.026,.16],water2);
 }
 const trunk=mat('#674126',{metal:.03,gloss:20});
 const trunk2=mat('#87552f',{metal:.05,gloss:24});
 const foliage=mat('#1c5c41',{gloss:24});
 const foliage2=mat('#37794c',{gloss:24});
 for(const [x,side] of [[-4.55,-1],[4.55,1]]){
   const t=model('Tronco 3D','cylinder',[x,.45,-6],[1.04,12,1.04],side===1?trunk2:trunk);
   t.setLocalEulerAngles(0,0,side*9);
   for(let i=0;i<4;i++){
     model('Copa volumétrica','sphere',[x-side*i*.32,4.4+i*.73,-6.3-i*.2],[2.55-i*.16,1.4,.95+i*.08],i%2?foliage:foliage2);
   }
 }
 const waterfall=mat('#5ac0e1',{gloss:80,metal:.18,emissive:'#168bad',emission:.65,opacity:.79});
 const rocks=mat('#53516b',{metal:.06,gloss:16});
 for(let i=0;i<5;i++){
   model('Cascata 3D','box',[2.9+i*.22,1.8-(i%2)*.23,-8+i*.03],[.19,2.8+(i%3)*.4,.14],waterfall);
   sphere('Pedra da cascata',2.7+i*.52,.14,-8+i*.2,.54,rocks);
 }
 for(let i=0;i<12;i++){
   const side=i%2?-1:1;
   const depth=-4.8-(i%4)*.46;
   const posx=side*(3.3+(i%3)*.43);
   const posy=-5.1+Math.floor(i/3)*2.05;
   plant(posx,posy,depth,.54+(i%3)*.15,['#f63cab','#fa83c8','#a963e8','#ffc16b'][i%4]);
 }
 for(let i=0;i<26;i++){
   const x=Math.sin(i*1.77)*4.1;
   const y=-6.0+(i%13)*.83;
   const z=-3.8-(i%5)*.48;
   const material=mat(i%3?'#fff2b2':'#ff92da',{gloss:30,emissive:i%3?'#ffc263':'#ff5cba',emission:.7});
   const e=sphere('Vaga-lume',x,y,z,.04+(i%3)*.022,material);
   fireflies.push({e,y,phase:i*1.89});
 }
}
function cabinet(){
 const wood=mat('#663126',{metal:.06,gloss:41});
 const woodEdge=mat('#a36536',{metal:.20,gloss:55});
 const purple=mat('#290f49',{metal:.22,gloss:69});
 const velvet=mat('#1b0d36',{metal:.10,gloss:47,emissive:'#1e0c32',emission:.21});
 const gold=mat('#e8a93f',{metal:.88,gloss:90,emissive:'#694018',emission:.14});
 const goldBright=mat('#ffda80',{metal:.82,gloss:92,emissive:'#ad702d',emission:.2});
 const gems=mat('#8b33b1',{metal:.4,gloss:85,emissive:'#551b8c',emission:.6});
 model('Parede da máquina','box',[0,.26,-.65],[5.85,4.12,.6],wood);
 model('Veludo por detrás dos símbolos','box',[0,.34,-.28],[5.46,3.54,.16],velvet);
 // Borda geométrica real, centro totalmente aberto
 model('Moldura esquerda','box',[-2.85,.23,.3],[.26,4.22,.44],woodEdge);
 model('Moldura direita','box',[2.85,.23,.3],[.26,4.22,.44],woodEdge);
 model('Moldura topo','box',[0,2.29,.31],[5.96,.29,.46],woodEdge);
 model('Moldura base','box',[0,-1.82,.32],[5.96,.28,.47],woodEdge);
 model('Aro de ouro esquerda','box',[-2.68,.21,.61],[.07,4.08,.12],gold);
 model('Aro de ouro direita','box',[2.68,.21,.61],[.07,4.08,.12],gold);
 model('Aro de ouro topo','box',[0,2.14,.61],[5.46,.08,.14],goldBright);
 model('Aro de ouro baixo','box',[0,-1.67,.61],[5.46,.08,.14],goldBright);
 // Moldura ganha volume lateral sem roubar o espaço dos símbolos
 for(const x of [-2.85,2.85]) for(const y of [-1.82,2.27]){
   sphere('Pérola dourada da moldura',x,y,.63,.16,goldBright);
   sphere('Ametista de canto',x,y,.76,.09,gems);
 }
 model('Friso superior','box',[0,2.61,.02],[6.4,.15,.54],gold);
 model('Friso baixo','box',[0,-2.13,.02],[6.4,.16,.54],gold);
 // Emblema 3D no cimo
 const crown=model('Pedra oval do logo','sphere',[0,5.43,-1.0],[2.66,.83,.42],purple);
 model('Arco dourado do emblema','box',[0,4.62,-.4],[4.65,.11,.34],gold);
 sphere('Cristal rosa',0,5.76,-.46,.26,mat('#f183d2',{metal:.35,gloss:90,emissive:'#9e2bc6',emission:.48}));
 // Cristais tridimensionais laterais à moldura
 for(const x of [-3.12,3.12]){
   for(let i=0;i<3;i++){
     const crystal=model('Cristal do jardim','cone',[x+(x<0?-.17:.17),-1.28+i*.28,-1.3],[.24,.68,.24],i%2?gems:gold);
     crystal.setLocalEulerAngles(0,0,(x<0?-1:1)*(15+i*21));
   }
 }
}
function textureFromSymbol(symbol,index){
 const c=document.createElement('canvas');
 c.width=256;c.height=256;
 const g=c.getContext('2d');
 const grad=g.createRadialGradient(100,64,8,125,138,165);
 const p=palette[index];
 const hi='rgba('+Math.floor(p[0]*255)+','+Math.floor(p[1]*255)+','+Math.floor(p[2]*255)+',.31)';
 grad.addColorStop(0,hi);
 grad.addColorStop(.72,'rgba(49,17,67,.18)');
 grad.addColorStop(1,'rgba(21,9,34,.01)');
 g.fillStyle=grad;g.fillRect(0,0,256,256);
 // Iluminação do objeto sem fotografias sobrepostas
 g.shadowColor='rgba(255,205,117,.55)';
 g.shadowBlur=18;
 g.textAlign='center';g.textBaseline='middle';
 g.font='170px "Apple Color Emoji","Segoe UI Emoji",system-ui,sans-serif';
 g.fillText(symbol,128,136);
 g.shadowBlur=0;
 const t=new pc.Texture(app.graphicsDevice,{width:256,height:256,format:pc.PIXELFORMAT_RGBA8,mipmaps:true});
 t.setSource(c);
 const m=mat('#ffffff',{metal:.12,gloss:75});
 m.diffuseMap=t;
 m.emissiveMap=t;
 m.emissive=new pc.Color(.18,.1,.23);
 m.emissiveIntensity=.14;
 m.update();
 return m;
}
function reels(){
 const symbolMaterials=symbols.map(textureFromSymbol);
 const tileEdge=mat('#c38b4b',{metal:.74,gloss:83});
 const tileBase=mat('#38114f',{metal:.25,gloss:60});
 const backGlow=mat('#552371',{metal:.18,gloss:52,emissive:'#461369',emission:.15});
 for(let c=0;c<5;c++){
   reelTiles[c]=[];
   for(let r=0;r<3;r++){
     const x=(c-2)*1.066;
     const y=.26+(1-r)*1.1;
     model('Caixa da peça '+c+'-'+r,'box',[x,y,.20],[1.06,1.08,.11],tileEdge);
     model('Fundo da peça '+c+'-'+r,'box',[x,y,.3],[1.01,1.03,.09],backGlow);
     const face=model('Símbolo 3D '+c+'-'+r,'box',[x,y,.42],[.96,.98,.14],symbolMaterials[(r*5+c)%symbols.length]);
     reelTiles[c][r]={face,idx:(r*5+c)%symbols.length,materials:symbolMaterials,lastStep:-1,done:true};
   }
 }
}
function setCamera(){
 const wide=innerWidth>innerHeight*1.12;
 const x=wide?2.45:0;
 const z=wide?15.6:18.1;
 const y=wide?.14:0;
 camera.setPosition(x+Math.sin(state.cam)*.48,y,z);
 camera.lookAt(x,0,0);
}
let camera;
function startSpin(){
 if(state.running)return;
 state.running=true;state.timer=0;
 button.disabled=true;
 status.textContent='A rodar em 3D…';
 output.textContent='Os cinco tambores estão a rodar';
 const count=state.spins+1;
 for(let c=0;c<5;c++)for(let r=0;r<3;r++){
   const cell=reelTiles[c][r];cell.done=false;cell.lastStep=-1;
 }
 state.spins=count;
 $('spins').textContent=String(count);
}
function updateSpin(dt){
 if(!state.running)return;
 state.timer+=Math.min(dt,.07);
 const speed=state.turbo?2.45:1;
 let allDone=true;
 for(let c=0;c<5;c++){
   const delay=c*.15/speed;
   const duration=(1.1+c*.22)/speed;
   const tt=state.timer-delay;
   const done=tt>=duration;
   for(let r=0;r<3;r++){
     const cell=reelTiles[c][r];
     if(done){
       if(!cell.done){
         cell.done=true;
         cell.face.setLocalEulerAngles(0,0,0);
         cell.idx=Math.floor(Math.random()*symbols.length);
         cell.face.render.material=cell.materials[cell.idx];
       }
     }else if(tt>=0){
       allDone=false;
       const step=Math.floor(tt*13*speed);
       if(step!==cell.lastStep){
         cell.lastStep=step;
         cell.idx=Math.floor(Math.random()*symbols.length);
         cell.face.render.material=cell.materials[cell.idx];
       }
       // Virar as peças com eixo 3D, não um sprite vertical falso
       cell.face.setLocalEulerAngles((tt*840*speed+r*4)%360,Math.sin(tt*4+r)*3,0);
     }else{allDone=false;}
   }
 }
 if(allDone){
   state.running=false;button.disabled=false;
   status.textContent='Tambores tridimensionais';
   const center=reelTiles[2][1];
   output.textContent='Giro '+state.spins+' concluído · '+labels[center.idx]+' no centro';
 }
}
function build(){
 app=new pc.Application(canvas,{graphicsDeviceOptions:{antialias:true,alpha:false,powerPreference:'high-performance'}});
 app.setCanvasFillMode(pc.FILLMODE_FILL_WINDOW);
 app.setCanvasResolution(pc.RESOLUTION_AUTO);
 app.graphicsDevice.maxPixelRatio=Math.min(1.5,window.devicePixelRatio||1);
 app.start();
 camera=new pc.Entity('Câmara 3D');
 camera.addComponent('camera',{clearColor:new pc.Color(.08,.045,.15),fov:47,nearClip:.1,farClip:120});
 camera.camera.gammaCorrection=pc.GAMMA_SRGB;
 camera.camera.toneMapping=pc.TONEMAP_ACES;
 app.root.addChild(camera);
 lighting();
 background();
 cabinet();
 reels();
 setCamera();
 quality.textContent='PLAYCANVAS';
 let age=0;
 app.on('update',(dt)=>{
   age+=dt;
   updateSpin(dt);
   if(state.orbit){
     state.camTarget=.35*Math.sin(age*.58);
   }else{
     state.camTarget=0;
   }
   state.cam+=(state.camTarget-state.cam)*Math.min(1,dt*3.2);
   setCamera();
   // Poucos objetos animados e sem sombras para preservar a fluidez
   for(const f of fireflies){
     f.e.setLocalPosition(f.e.getLocalPosition().x,
       f.y+Math.sin(age*.9+f.phase)*.095,
       f.e.getLocalPosition().z);
   }
 });
 window.addEventListener('resize',setCamera,{passive:true});
 document.addEventListener('visibilitychange',()=>{
   if(document.hidden){state.orbit=false;cameraButton.setAttribute('aria-pressed','false');}
 });
 button.addEventListener('click',startSpin);
 turboButton.addEventListener('click',()=>{
   state.turbo=!state.turbo;
   turboButton.setAttribute('aria-pressed',String(state.turbo));
   output.textContent=state.turbo?'Modo TURBO 3D ativado':'Modo TURBO desativado';
 });
 cameraButton.addEventListener('click',()=>{
   state.orbit=!state.orbit;
   cameraButton.setAttribute('aria-pressed',String(state.orbit));
   output.textContent=state.orbit?'Câmara cinematográfica ativa':'Câmara fixa';
 });
}
try{
 build();
}catch(error){
 console.error('Jardim Mágico 3D não iniciou:',error);
 $('error').hidden=false;
 quality.textContent='ERRO 3D';
}
