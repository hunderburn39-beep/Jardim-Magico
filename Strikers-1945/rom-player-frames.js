/* World ROM player graphics: 6 groups of 20 eight-byte records.
 * Actual 32x32 sprite assemblies (two records per animation image).
 * The links between the six groups and aircraft names are visual identifications,
 * not a verified selection-screen code trace. Palette assignment is pending.
 * No frame rotation, synthetic art or ROM bytes are stored here.
 */
(()=>{'use strict';
const groups=Object.freeze([
 {id:'p38',address:0x3b384,base:5511,reference:0x3d6e0},
 {id:'p51',address:0x3b424,base:5903,reference:0x3d8f0},
 {id:'spitfire',address:0x3b4c4,base:5943,reference:0x3dcb0},
 {id:'bf109',address:0x3b564,base:5983,reference:0x3e080},
 {id:'zero',address:0x3b604,base:6462,reference:0x3e290},
 {id:'shinden',address:0x3b6a4,base:6502,reference:0x3e650}
].map(Object.freeze));
// The game's own selection-index pointer table (referenced at 0xc04e).
const SELECTION_TABLE=0x3d6b4;
function long(r,a){return r.programWord(a)*65536+r.programWord(a+2)}
function signedWord(r,a){const n=r.programWord(a);return n&0x8000?n-65536:n}
let cached=null,canvases=new Map();
function read(){
 const r=window.StrikersROM,defs=window.StrikersSpriteDefinitions;
 if(!r?.isVerified?.()||!defs)return null;
 if(cached)return cached;
 const map=new Map();
 for(const [index,g] of groups.entries()){
  const configAddress=long(r,SELECTION_TABLE+4*index);
  if(configAddress+0x14!==g.reference)throw Error('Original selection table mismatch '+index);
  const pointer=long(r,configAddress+0x14);
  // Preserve raw values until each field's use in the CPU program is traced.
  // The four 32-bit values look like fixed-point movement constants;
  // the eight signed words are geometry candidates, not confirmed hitboxes.
  const config=Object.freeze({
   selectionIndex:index,address:configAddress,
   initialWord:long(r,configAddress),
   values32:Object.freeze(Array.from({length:4},(_,i)=>long(r,configAddress+4+4*i))),
   values16:Object.freeze(Array.from({length:8},(_,i)=>signedWord(r,configAddress+24+2*i)))
  });
  if(pointer!==g.address)throw Error('Aircraft table pointer mismatch: '+g.id);
  const records=[];
  for(let i=0;i<20;i++){
   // Real World ROM layout: flags, LUT code, horizontal word, vertical word.
   const a=g.address+8*i,attr=r.programWord(a),code=r.programWord(a+2),rx=r.programWord(a+4),ry=r.programWord(a+6);
   const desc=defs.get(code);
   if(code!==g.base+4*Math.floor(i/2)||attr!==(i%2?8:0)||!desc||desc.nx!==2||desc.ny!==2)throw Error('Aircraft record mismatch: '+g.id+' #'+i);
   records.push(Object.freeze({address:a,rx,ry,attr,code}));
  }
  map.set(g.id,Object.freeze({...g,config,records:Object.freeze(records)}));
 }
 cached=map;return map;
}
function info(id){return read()?.get(id)??null}
function frame(id,bank=0,time=0){
 const g=info(id);if(!g)return null;
 // Approximation of control-to-bank and propeller timing; images themselves
 // are the original 10 frames. Zero bank uses the middle pair.
 const position=Math.max(0,Math.min(4,2+Math.round(Math.max(-1,Math.min(1,Number(bank)||0))*2)));
 const phase=Math.floor(Math.max(0,Number(time)||0)*14)%2;
 return g.records[(position*2+phase)*2];
}
function sprite(id,bank=0,time=0,palette=null){
 const f=frame(id,bank,time);if(!f)return null;
 const key=f.code+':'+(palette?Array.from(palette).join(','):'gray');
 if(canvases.has(key))return canvases.get(key);
 const c=document.createElement('canvas');
 if(!window.StrikersSpriteDefinitions.draw(c,f.code,palette))return null;
 canvases.set(key,c);return c;
}
function stats(){const m=read();return {groups:m?.size??0,records:m?120:0,uniqueImages:m?60:0,paletteStatus:'unverified',timingStatus:'provisional',recordLayout:'attr,code,rx,ry',selectionTable:SELECTION_TABLE}}
window.addEventListener('strikers-rom-ready',()=>{cached=null;canvases.clear()});
window.StrikersOriginalPlayers=Object.freeze({groups,info,frame,sprite,stats});
})();