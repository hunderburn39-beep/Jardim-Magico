/* Jardim Mágico 3D · protótipo PlayCanvas 2.23.3
 * Cena, moldura, luzes e objetos são geometrias 3D reais (não imagens recortadas).
 * Demonstração gráfica autónoma: sem acesso à banca nem ao RTP do jogo original.
 * Mobile: WebGL 2, DPR <=1.5, luzes estáticas sem sombras em tempo real.
 */
import * as pc from 'playcanvas';
import {createOrganicFrame} from './frame-v23.js?v=2.3';
import {createVerticalReels} from './reels-v23.js?v=2.3';

const $=id=>document.getElementById(id);
const canvas=$('application');
const output=$('result');
const status=$('spinState');
const button=$('spin');
const cameraButton=$('toggleCamera');
const turboButton=$('turbo');
const quality=$('quality');
const symbols=['🧪','💚','❤️','🫖','🌟','💎','🍄','🎩','🌹','✨','🪨','🪙'];
const labels=['Poção','Esmeralda','Coração','Bule','WILD','Cristal','Cogumelo','Chapéu','Rosa','SCATTER','Pedra','Moeda'];
const palette=[
 [.90,.26,.70],[.26,1,.53],[1,.3,.54],[.68,.37,.98],
 [1,.72,.21],[.66,.25,1],[1,.38,.48],[.66,.38,.93],
 [1,.23,.49],[.91,.65,.2],[.46,.47,.56],[1,.82,.31]
];
const state={running:false,turbo:false,orbit:false,spins:0,timer:0,cam:0,camTarget:0};
let app;
let reelTiles=[];
let symbolMaterials=[];
let reelMotion=null;
let requestedBoard=null;
let finishSpin=null;
let winPulseUntil=0;
let winPulseStart=0;
let winPulseCells=[];
let burst=null;
const burstPool=[];
const burstWaves=[];
let burstColors=[];
const fireflies=[];
const glows=[];
const lakeRipples=[];
const falls=[];
const mistPuffs=[];
const borderCrystals=[];
let sceneAge=0;

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
 // Luz fria de contorno nos cristais, com sombras desativadas no iPhone.
 const rim=new pc.Entity('Luz azul de recorte');
 rim.addComponent('light',{type:'directional',color:new pc.Color(.40,.72,1),intensity:.26,castShadows:false});
 rim.setLocalEulerAngles(-28,195,-15);
 app.root.addChild(rim);
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
 // Duas camadas líquidas contínuas; as ondas são geometrias reais.
 for(let i=0;i<7;i++){
   const xx=-3.1+i*1.04;
   const zz=-3.1+(i%3)*.32;
   const wave=model('Ondulação do lago','sphere',[xx,-6.50,zz],[.68,.026,.15],water2);
   lakeRipples.push({e:wave,x:xx,z:zz,phase:i*.83});
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
 const whiteWater=mat('#b6eefa',{metal:.04,gloss:92,emissive:'#93def8',emission:.47,opacity:.67});
 for(let i=0;i<6;i++){
   const x=2.73+i*.26;
   model('Coluna da cascata','box',[x,1.55-(i%2)*.22,-8.02+i*.03],
     [.19,2.85+(i%3)*.32,.14],waterfall);
   sphere('Pedra da cascata',2.62+i*.46,.11,-8+i*.15,.48,rocks);
   const strip=model('Fio de espuma cintilante','box',[x+.026,2.45,-7.82],
      [.028,.41,.033],whiteWater);
   falls.push({e:strip,x,phase:i*.35});
 }
 const foam=mat('#a1d9df',{metal:.03,gloss:40,emissive:'#61a5bf',emission:.24,opacity:.46});
 for(let i=0;i<5;i++){
   const e=sphere('Névoa junto à cascata',2.72+i*.23,-.3+(i%2)*.12,-7.4,.17,foam);
   mistPuffs.push({e,x:2.72+i*.23,y:-.3+(i%2)*.12,phase:i*1.41});
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
 const gilded=mat('#fff0b2',{metal:.85,gloss:98,emissive:'#724817',emission:.12});
 const carved=mat('#382419',{metal:.06,gloss:32});
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
 // V2.2: três planos de espessura real, contorno, talha e brilho.
 // Tudo se mantém FORA dos 5×3 rolos, sem moldura colada sobre símbolos.
 for(const x of [-2.97,2.97]){
   model('Talha lateral profunda','box',[x,.24,.16],[.10,4.34,.58],carved);
   model('Vinco de ouro lateral','box',[x+(x<0?.08:-.08),.25,.72],[.035,3.78,.075],gilded);
   for(let i=0;i<4;i++){
     const y=-1.42+i*1.06;
     const bead=sphere('Florão de latão',x,y,.75,.08,goldBright);
     bead.setLocalScale(.12,.18,.07);
   }
 }
 for(const y of [2.40,-1.93]){
   model('Talha transversal','box',[0,y,.20],[5.95,.15,.56],carved);
   model('Filete de ouro em alto relevo','box',[0,y+(y>0?-.06:.06),.68],[5.67,.035,.10],gilded);
 }
 for(const x of [-2.81,2.81])for(const y of [-1.81,2.28]){
   const gem=model('Ametista facetada','cone',[x,y,.87],[.17,.28,.14],gems);
   gem.setLocalEulerAngles(0,0,45);
   borderCrystals.push(gem);
 }
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
/* Ornamentos de volume real — flores, trepadeiras, lotus e lanternas.
   Todos são geometria 3D com material próprio, nunca fatias de uma imagem. */
function decorate3D(){
 const vine=mat('#327346',{metal:.04,gloss:29});
 const leaf=mat('#62a44e',{metal:.05,gloss:38});
 const gold=mat('#db9b3b',{metal:.79,gloss:90});
 const lanternGlow=mat('#ffda6b',{metal:.12,gloss:94,emissive:'#ffbc56',emission:.78});
 const petals=mat('#ff64c1',{metal:.07,gloss:75,emissive:'#a92d6e',emission:.18});
 const petalsLight=mat('#ffc0ea',{metal:.06,gloss:89,emissive:'#dc5597',emission:.22});
 const twig=mat('#7e4926',{metal:.02,gloss:35});
 for(const side of [-1,1]){
  const x=side*3.08;
  // As trepadeiras acompanham a madeira sem tapar a janela de símbolos.
  model('Trepadeira vertical','cylinder',[x,.14,.49],[.06,3.80,.07],vine);
  for(let i=0;i<6;i++){
   const y=-1.51+i*.58;
   const p=model('Folha 3D','sphere',[x-side*.12,y,.54],[.28,.11,.11],i%2?leaf:vine);
   p.setLocalEulerAngles(0,0,i%2?38:-38);
  }
  for(const y of [-1.84,2.26]){
   plant(x-side*.14,y,.72,.43,side<0?'#ff64c0':'#f49bc9');
  }
  // Novas ramificações em profundidade — volume verdadeiro no contorno.
  for(let i=0;i<3;i++){
   const y=-1.10+i*1.11;
   const branch=model('Ramo entrançado','cylinder',[x+side*.22,y,.59],[.043,.59,.043],twig);
   branch.setLocalEulerAngles(0,0,-side*38);
   const pet=model('Pétala alta','sphere',[x+side*.39,y+.19,.74],[.19,.12,.13],i%2?petals:petalsLight);
   pet.setLocalEulerAngles(0,0,side*33);
  }
  // Lanternas ornamentais suspensas ao lado do emblema.
  const lx=side*3.42;
  model('Suspensão da lanterna','cylinder',[lx,4.6,-.8],[.032,.64,.032],gold);
  model('Lanterna âmbar','sphere',[lx,4.12,-.8],[.31,.48,.24],lanternGlow);
  model('Tampa superior','cone',[lx,4.47,-.8],[.35,.19,.34],gold);
  model('Base da lanterna','cylinder',[lx,3.85,-.8],[.34,.11,.33],gold);
 }
 // Lótus 3D que remete para a flor central do logótipo fornecido.
 for(let i=0;i<8;i++){
  const a=i*Math.PI/4,x=Math.cos(a)*.38,y=5.22+Math.sin(a)*.22;
  const p=model('Pétala lótus','sphere',[x,y,-.34],[.26,.48,.15],petals);
  p.setLocalEulerAngles(0,0,(a*180/Math.PI)-90);
 }
 sphere('Lótus — centro',0,5.22,-.15,.21,lanternGlow);
 // Lótus e pedras com contacto à estrutura — sem recortes fotográficos.
 const jewel=mat('#4de0c3',{metal:.47,gloss:97,emissive:'#139f8c',emission:.48});
 for(const x of [-2.66,2.66]){
   const bead=model('Cristal da moldura','cone',[x,2.61,.35],[.19,.42,.21],jewel);
   bead.setLocalEulerAngles(0,0,x<0?19:-19);
 }
 const cherry=mat('#d24a7a',{metal:.10,gloss:87,emissive:'#81204a',emission:.13});
 for(let i=0;i<8;i++){
  const a=i*2.4;
  sphere('Botão rosa 3D',Math.cos(a)*4.7,-5.85+(i%3)*.35,-2.4-(i%4)*.34,.11+(i%2)*.05,cherry);
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
 symbolMaterials=symbols.map(textureFromSymbol);
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
/* Arte original da fotografia: aplicada a modelos 3D individuais. */
function loadOriginalSymbolArt(){
 const images={
  0:'3f3213b7316d5cf176.png',1:'e776f95aa9fd19f865.png',
  2:'66cb91b07aa8094c90.png',3:'49d6874847ff87abe4.png',
  4:'05ad729435e7487db8.png',5:'6db9f4dd9d6abeb732.png',
  6:'81cc7b8d8f2c0d0b18.png',7:'ab10713bfdfc732017.png',
  8:'ae9c51465776decce9.png'
 };
 for(const [id,file] of Object.entries(images)){
  const im=new Image();im.decoding='async';
  im.onload=()=>{
   try{
    const cut=document.createElement('canvas');cut.width=cut.height=256;
    const ctx=cut.getContext('2d',{willReadFrequently:true});
    ctx.drawImage(im,0,0,256,256);
    const a=ctx.getImageData(0,0,256,256);
    let l=256,t=256,r=0,b=0;
    for(let y=0;y<256;y++)for(let x=0;x<256;x++){
     const k=4*(y*256+x);
     if(a.data[k+3]<40)continue;
     l=Math.min(l,x);t=Math.min(t,y);r=Math.max(r,x);b=Math.max(b,y);
    }
    if(r-l<30||b-t<30)return;
    const normalized=document.createElement('canvas');
    normalized.width=normalized.height=256;
    const g=normalized.getContext('2d',{willReadFrequently:true});
    const w=r-l+1,h=b-t+1,k=Math.min(228/w,228/h);
    g.drawImage(cut,l,t,w,h,(256-w*k)/2,(256-h*k)/2,w*k,h*k);
    const pixels=g.getImageData(0,0,256,256),p=pixels.data;
    for(let j=0;j<p.length;j+=4){
     const hi=Math.max(p[j],p[j+1],p[j+2]);
     if(hi<47)p[j+3]=Math.round(p[j+3]*.1);
     else if(hi<96&&p[j+2]>p[j+1]*1.18)p[j+3]=Math.round(p[j+3]*.42);
    }
    g.putImageData(pixels,0,0);
    const tex=new pc.Texture(app.graphicsDevice,{width:256,height:256,format:pc.PIXELFORMAT_RGBA8,mipmaps:true});
    tex.setSource(normalized);
    const material=mat('#ffffff',{metal:.08,gloss:79});
    material.diffuseMap=tex;
    material.opacityMap=tex;
    material.opacityMapChannel='a';
    material.blendType=pc.BLEND_NORMAL;
    material.depthWrite=false;
    material.emissive=rgb('#65435e');
    material.emissiveMap=tex;
    material.emissiveIntensity=.2;
    material.update();
    const index=Number(id);
    symbolMaterials[index]=material;
    for(const col of reelTiles)for(const tile of col)
     if(tile.idx===index)tile.face.render.material=material;
   }catch(e){console.warn('Símbolo 3D HD:',file,e)}
  };
  im.onerror=()=>console.warn('Textura HD não carregou:',file);
  im.src='../assets/imagens/'+file;
 }
}

function setCamera(){
 const wide=innerWidth>innerHeight*1.12;
 const x=wide?2.45:0;
 const z=wide?15.6:18.1;
 const y=wide?.14:0;
 // Impacto de câmara subtil nas explosões sem movimento contínuo obrigatório.
 const shake=burst?.elapsed<burst?.duration*.45
  ?Math.sin(burst.elapsed*64)*.035*(1-burst.elapsed/(burst.duration*.45)):0;
 camera.setPosition(x+Math.sin(state.cam)*.48+shake,y+shake*.55,z);
 camera.lookAt(x,0,0);
}
let camera;
/* V2.1 — estouro simultâneo das peças de TODAS as linhas vencedoras.
   Partículas 3D previamente criadas; uma peça usada em duas linhas explode
   apenas uma vez. O cálculo monetário nunca depende desta animação. */
function prepareBurstParticles(){
 if(burstPool.length)return;
 burstColors=[
  mat('#ffe8a1',{metal:.65,gloss:85,emissive:'#ffc25d',emission:.62}),
  mat('#ff83d5',{metal:.30,gloss:88,emissive:'#ce2baf',emission:.58}),
  mat('#bf8cff',{metal:.26,gloss:81,emissive:'#8547e4',emission:.58}),
  mat('#d6fff0',{metal:.25,gloss:85,emissive:'#5cdcbf',emission:.43}),
  mat('#ffdf6c',{metal:.78,gloss:98,emissive:'#d99923',emission:.70})
 ];
 // Pool reutilizável: até 15 peças × 5 fragmentos.
 for(let i=0;i<75;i++){
  const kind=i%5===0?'cone':i%5===1?'sphere':'box';
  const entity=model('Estilhaço de vitória 3D',kind,[0,0,.92],[.01,.01,.01],burstColors[i%5]);
  entity.enabled=false;burstPool.push(entity);
 }
 const waveMat=mat('#fff2bd',{metal:.08,gloss:85,emissive:'#f7c06d',emission:.63,opacity:.21});
 for(let i=0;i<15;i++){
  const e=model('Onda de luz do prémio','sphere',[0,0,.68],[.01,.01,.015],waveMat);
  e.enabled=false;burstWaves.push(e);
 }
}
function cancelBurst(){
 if(!burst)return;
 for(const cell of burst.cells){
  cell.face.enabled=true;
  cell.face.setLocalScale(.96,.98,.14);
  cell.face.setLocalEulerAngles(0,0,0);
 }
 for(const part of burst.parts)part.entity.enabled=false;
 for(const wave of burst.waves)wave.entity.enabled=false;
 burst=null;
}
function explodeWinners(coordinates,duration=1100){
 if(state.running||!Array.isArray(coordinates))return 0;
 cancelBurst();
 winPulseUntil=0;
 for(const cell of winPulseCells)cell.face.setLocalScale(.96,.98,.14);
 winPulseCells=[];
 prepareBurstParticles();
 const seen=new Set(),cells=[],parts=[],waves=[];
 for(const {c,r} of coordinates){
  if(!Number.isInteger(c)||!Number.isInteger(r)||c<0||c>4||r<0||r>2)continue;
  const key=c+','+r;
  if(seen.has(key))continue;
  seen.add(key);
  const cell=reelTiles[c]?.[r];if(!cell)continue;
  cells.push(cell);
  const x=(c-2)*1.066,y=.26+(1-r)*1.1;
  const wave=burstWaves[c*3+r];
  wave.enabled=false;
  waves.push({entity:wave,x,y});
  for(let j=0;j<5;j++){
   const entity=burstPool[parts.length];
   entity.render.material=burstColors[cell.idx===4?4:cell.idx===5||cell.idx===1?3:cell.idx===6||cell.idx===8?1:j%5];
   const angle=(j/5)*Math.PI*2+(c+r)*.61;
   const speed=.57+.25*((c+j+r)%3);
   entity.enabled=false;
   parts.push({entity,x,y,dx:Math.cos(angle)*speed,dy:Math.sin(angle)*speed,
    dz:(j%2?1:-1)*.19,angle});
  }
 }
 if(!cells.length)return 0;
 burst={elapsed:0,duration:Math.max(.65,duration/1000),cells,parts,waves};
 return cells.length;
}
function updateBurst(dt){
 if(!burst)return;
 const effect=burst;
 effect.elapsed+=Math.min(dt,.07);
 const t=Math.min(1,effect.elapsed/effect.duration);
 // Todos os símbolos vencedores aumentam, estouram e reaparecem juntos.
 let scale;
 if(t<.25)scale=1+.18*Math.sin((t/.25)*Math.PI/2);
 else if(t<.51)scale=1.18*Math.pow(1-(t-.25)/.26,2);
 else if(t<.82)scale=.006;
 else scale=.006+(1-.006)*Math.sin(((t-.82)/.18)*Math.PI/2);
 for(const cell of effect.cells){
  cell.face.setLocalScale(.96*scale,.98*scale,.14*Math.max(scale,.006));
  cell.face.setLocalEulerAngles(0,t>.25&&t<.82?120*(t-.25):0,0);
 }
 const phase=Math.min(1,Math.max(0,(t-.25)/.62));
 // Reflexo radial por peça, ao mesmo tempo em todas as linhas.
 for(const wave of effect.waves){
  const active=t>=.21&&t<.76;
  wave.entity.enabled=active;
  if(!active)continue;
  const prog=(t-.21)/.55;
  wave.entity.setLocalPosition(wave.x,wave.y,.70);
  wave.entity.setLocalScale(.18+1.16*prog,.18+1.16*prog,.018);
 }
 for(const shard of effect.parts){
  const active=t>=.25&&t<.90;
  shard.entity.enabled=active;
  if(!active)continue;
  shard.entity.setLocalPosition(
   shard.x+shard.dx*phase,
   shard.y+shard.dy*phase-.12*phase*phase,
   .78+shard.dz*phase
  );
  const size=.14*Math.pow(1-phase,1.3)+.004;
  shard.entity.setLocalScale(size,size*(.62+.40*Math.sin(shard.angle+phase*3)**2),size*.45);
  shard.entity.setLocalEulerAngles(0,0,(shard.angle+phase*6)*180/Math.PI);
 }
 if(t>=1)cancelBurst();
}

function startSpin(matrix=null,whenDone=null){
 if(state.running||!reelMotion)return false;
 cancelBurst();
 winPulseUntil=0;
 for(const cell of winPulseCells)cell.face.setLocalScale(.947,.965,.16);
 winPulseCells=[];
 const board=matrix||Array.from({length:5},
   ()=>Array.from({length:3},()=>Math.floor(Math.random()*symbols.length)));
 state.running=true;
 button.disabled=true;
 status.textContent='Tambores a descer de cima para baixo…';
 output.textContent='Os cinco tambores estão a descer';
 const count=++state.spins;
 if($('spins'))$('spins').textContent=String(count);
 const started=reelMotion.startSpin(board,state.turbo,()=>{
   state.running=false;
   button.disabled=false;
   status.textContent='Tambores verticais parados';
   if(!whenDone)output.textContent='Giro '+state.spins+' concluído';
   if(whenDone)whenDone();
 });
 if(!started){state.running=false;button.disabled=false}
 return started;
}
function cascadeDown(board,removed,whenDone){
 if(state.running||!reelMotion)return false;
 cancelBurst();
 winPulseUntil=0;
 state.running=true;
 button.disabled=true;
 status.textContent='Queda: símbolos a descer…';
 const result=reelMotion.beginCascade(board,removed,()=>{
   state.running=false;
   button.disabled=false;
   status.textContent='Queda concluída';
   if(whenDone)whenDone();
 },state.turbo?.38:.68);
 if(!result){state.running=false;button.disabled=false}
 return result;
}
function updateSpin(dt){
 if(!reelMotion)return;
 reelMotion.update(dt);
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
 createOrganicFrame({pc,model,mat,app});
 reelMotion=createVerticalReels({pc,model,mat,app,symbols,textureFromSymbol});
 reelTiles=reelMotion.reelTiles;
 symbolMaterials=reelMotion.symbolMaterials;
 loadOriginalSymbolArt();
 setCamera();
 quality.textContent='PLAYCANVAS';
 let age=0;
 app.on('update',(dt)=>{
   age+=dt;
   updateSpin(dt);
   updateBurst(dt);
   if(winPulseUntil){
    const t=Date.now();
    const active=t<winPulseUntil;
    const v=active?1+.13*Math.sin((t-winPulseStart)*.019)**2:1;
    for(const cell of winPulseCells)cell.face.setLocalScale(.947*v,.965*v,.16);
    if(!active){winPulseUntil=0;winPulseCells=[]}
   }
   if(state.orbit){
     state.camTarget=.35*Math.sin(age*.58);
   }else{
     state.camTarget=0;
   }
   state.cam+=(state.camTarget-state.cam)*Math.min(1,dt*3.2);
   setCamera();
   // V2.2: cascata e lago com movimento físico muito leve (sem vídeo).
   // Uma única passagem por geometria simples em cada frame.
   for(const wave of lakeRipples){
     const t=age*.72+wave.phase;
     wave.e.setLocalPosition(wave.x+.10*Math.sin(t),-6.50+.014*Math.sin(t*1.7),wave.z);
     const w=.61+.12*Math.sin(t+.6)**2;
     wave.e.setLocalScale(w,.023,.12+.025*Math.sin(t));
   }
   for(const water of falls){
     const travel=(age*.78+water.phase)%2.4;
     water.e.setLocalPosition(water.x,2.55-travel,-7.81);
   }
   for(const puff of mistPuffs){
     const theta=age*.48+puff.phase;
     puff.e.setLocalPosition(puff.x+.07*Math.sin(theta),puff.y+.065*Math.cos(theta),-7.4);
   }
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
 window.JM3D={
  spin:(board,callback)=>startSpin(board,callback),
  showBoard:board=>reelMotion.showBoard(board),
  cascade:(board,removed,callback)=>cascadeDown(board,removed,callback),
  isRunning:()=>state.running,
  burstWinners:(coords,duration=1100)=>explodeWinners(coords,duration),
  isBursting:()=>!!burst,
  highlight:(coords,duration=1250)=>{
   if(state.running||!Array.isArray(coords))return;
   winPulseCells=coords.map(({c,r})=>reelTiles[c]?.[r]).filter(Boolean);
   winPulseStart=Date.now();winPulseUntil=winPulseStart+duration;
  },
  setTurbo:on=>{state.turbo=!!on;turboButton.setAttribute('aria-pressed',String(state.turbo));},
  setOrbit:on=>{state.orbit=!!on;cameraButton.setAttribute('aria-pressed',String(state.orbit));}
 };
 window.dispatchEvent(new Event('jm3d-ready'));
 button.addEventListener('click',()=>{if(!window.JM3DGameController)startSpin();});
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
