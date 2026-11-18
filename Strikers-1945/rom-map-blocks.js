/* World ROM: two directories of stored tile-map blocks.
 * 68 blocks, 37108 tile words, validated contiguous bounds.
 * The directories are NOT hardware layer IDs. In captured stage 1,
 * both screen layers use blocks 17 and 18 from directory 0.
 * Column-pair ordering was validated against the first two complete
 * recorded frames (1176 equal words, including palette bits).
 * No ROM pixels or map payloads are embedded.
 */
(()=>{'use strict';
const dirs=Object.freeze([
 Object.freeze({pointer:0x59ba0,count:33,start:0x59cb8,end:0x640e8,flags:0}),
 Object.freeze({pointer:0x59c28,count:35,start:0x640e8,end:0x6c0c0,flags:0x400})
]);
const captureBlocks=Object.freeze([{group:0,id:17},{group:0,id:18}].map(Object.freeze));
let catalog=null,words=new Map();
function long(r,a){return r.programWord(a)*65536+r.programWord(a+2)}
function all(){
 const r=window.StrikersROM;
 if(!r?.isVerified?.())return null;
 if(catalog)return catalog;
 let total=0;
 const groups=dirs.map((d,group)=>{
  let expected=d.start;const entries=[];
  for(let id=0;id<d.count;id++){
   const address=long(r,d.pointer+4*id);
   const width=r.programWord(address),columns=r.programWord(address+2),flags=long(r,address+4);
   if(address!==expected||!Number.isInteger(width)||!Number.isInteger(columns)||width<1||columns<1||width>128||columns>256||flags!==d.flags)throw Error('Original map header '+group+':'+id);
   const wordCount=width*columns,end=address+8+wordCount*2;
   if(end>d.end)throw Error('Original map outside block');
   entries.push(Object.freeze({group,id,address,width,columns,flags,wordCount,end}));
   expected=end;total+=wordCount;
  }
  if(expected!==d.end||long(r,d.pointer+d.count*4)!==0xffffffff)throw Error('Original map directory bounds '+group);
  return Object.freeze(entries);
 });
 if(total!==37108)throw Error('Original map word count');
 catalog=Object.freeze(groups);return catalog;
}
function get(group,id){return all()?.[group]?.[id]??null}
function decode(group,id){
 const map=get(group,id);if(!map)return null;
 const key=group+':'+id;if(words.has(key))return words.get(key);
 const r=window.StrikersROM,out=new Uint16Array(map.wordCount);
 for(let col=0;col<map.columns;col++){
  const pair=col&~1,stride=Math.min(2,map.columns-pair);
  for(let row=0;row<map.width;row++){
   const wi=pair*map.width+row*stride+(col-pair);
   const value=r.programWord(map.address+8+wi*2);
   if(!Number.isInteger(value))throw Error('Missing original map word');
   out[col*map.width+row]=value;
  }
 }
 const result={map,codes:out};words.set(key,result);return result;
}
function strip(group,id,firstCol,count=21){
 const d=decode(group,id);
 if(!d||!Number.isInteger(firstCol)||!Number.isInteger(count)||firstCol<0||count<1||firstCol+count>d.map.columns)return null;
 return d.codes.slice(firstCol*d.map.width,(firstCol+count)*d.map.width);
}
function matchCapture(layer,firstCol,capture){
 const block=captureBlocks[layer];
 if(!block||!capture||capture.length!==294)return null;
 const data=capturedSceneStrip(layer,firstCol);
 if(!data||data.length!==capture.length)return null;
 for(let i=0;i<data.length;i++)if(data[i]!==capture[i])return null;
 return data;
}
// Eleven descriptors in the program's own table (code reference 0x146fe).
// The first eight are candidate campaign entries; three additional entries
// are deliberately left unclassified. Runtime sequencing is not inferred.
const SCENE_TABLE=0x50d14,SCENE_COUNT=11;
const sceneAddresses=Object.freeze([0x3f9f8,0x3f5e8,0x40a70,0x405b2,0x3f12c,0x4030c,0x40044,0x3fd3e,0x3788a,0x379b6,0x37a6e]);
let scenesCache=null;
function scenes(){
 const r=window.StrikersROM,dirs=all();if(!dirs)return null;
 if(scenesCache)return scenesCache;
 const byAddress=new Map(dirs.flat().map(m=>[m.address,m]));
 function list(start,end){
  if(start>=end||(end-start)%4)throw Error('Invalid scene list bounds');
  const entries=[];
  for(let a=start;a<end;a+=4){
   const ptr=long(r,a);
   if(ptr===0){entries.push(null);continue;}
   const m=byAddress.get(ptr);if(!m)throw Error('Unrecognized map pointer '+ptr.toString(16));
   entries.push(Object.freeze({group:m.group,id:m.id,address:ptr}));
  }
  if(entries.at(-1)!==null)throw Error('Unterminated map list');
  return Object.freeze(entries);
 }
 const result=[];
 for(let id=0;id<SCENE_COUNT;id++){
  const address=long(r,SCENE_TABLE+id*4);
  if(address!==sceneAddresses[id])throw Error('Scene descriptor '+id);
  const script=long(r,address),a=long(r,address+4),b=long(r,address+8);
  if(a!==address+52||a>=b||b>=script||script-a>400)throw Error('Scene header bounds');
  const dimensions=Object.freeze([r.programWord(address+12),r.programWord(address+14)]);
  const palettePointers=Object.freeze(Array.from({length:6},(_,i)=>long(r,address+20+i*4)));
  for(let i=0;i<6;i++){
   const base=[0x596d8,0x5995c,0x58ad8][i%3],stride=i%3===2?128:32;
   const index=(palettePointers[i]-base)/stride;
   if(!Number.isInteger(index)||index<0||index>15)throw Error('Scene palette table '+id);
  }
  result.push(Object.freeze({id,address,script,dimensions,palettePointers,mapLists:Object.freeze([list(a,b),list(b,script)])}));
 }
 scenesCache=Object.freeze(result);return scenesCache;
}
// Original palette tables: two eight-bank sets per map layer.
let paletteAudit=null,paletteCache=new Map(),artCache=new Map();
function scenePalette(id,layer,variant=0){
 const scene=scenes()?.[id],r=window.StrikersROM;
 if(!scene||![0,1].includes(layer)||![0,1].includes(variant))return null;
 const key=[id,layer,variant].join(':');
 if(paletteCache.has(key))return paletteCache.get(key);
 const address=scene.palettePointers[variant*3+layer],banks=[];
 for(let b=0;b<8;b++){
  const p=long(r,address+4*b);
  if(p===0xffffffff){banks.push(null);continue}
  const n=(p-0x1b944)/32;
  if(!Number.isInteger(n)||n<0||n>=516)throw Error('Scene palette record outside ROM table');
  const colors=window.StrikersSpriteDefinitions?.palette(n);
  if(!colors)throw Error('Scene palette unavailable');
  banks.push(colors);
 }
 const value=Object.freeze(banks);paletteCache.set(key,value);return value;
}
function auditScenePalettes(){
 if(paletteAudit)return paletteAudit;
 const ss=scenes();if(!ss)return null;
 let references=0,tileWords=0,bankChecks=0,paletteSlots=0;
 for(const scene of ss)for(let layer=0;layer<2;layer++){
  const palettes=[scenePalette(scene.id,layer,0),scenePalette(scene.id,layer,1)];
  paletteSlots+=16;
  for(const ref of scene.mapLists[layer]){
   if(!ref)continue;
   const d=decode(ref.group,ref.id);references++;tileWords+=d.codes.length;
   for(const word of d.codes)for(const bank of palettes){
    if(!bank[word>>>13])throw Error('Map references an absent palette: '+scene.id+' '+ref.id);
    bankChecks++;
   }
  }
 }
 if(references!==134||tileWords!==55044||bankChecks!==110088||paletteSlots!==352)throw Error('Unexpected map palette audit totals');
 paletteAudit=Object.freeze({references,tileWords,bankChecks,paletteSlots});
 return paletteAudit;
}
// Render an individual original block in portrait orientation, using only
// that scene/layer/variant's own palette. No guessed scroll or layer position.
function renderBlock(id,layer,listIndex,variant=0){
 const ref=scenes()?.[id]?.mapLists[layer]?.[listIndex];
 if(!ref)return null;
 const k=[id,layer,ref.group,ref.id,variant].join(':');
 if(artCache.has(k)){const c=artCache.get(k);artCache.delete(k);artCache.set(k,c);return c;}
 const d=decode(ref.group,ref.id),banks=scenePalette(id,layer,variant),rom=window.StrikersROM;
 if(!d||!banks)return null;
 const width=d.map.width*16,height=d.map.columns*16;
 const c=document.createElement('canvas');c.width=width;c.height=height;
 const ctx=c.getContext('2d'),image=ctx.createImageData(width,height),pixels=image.data;
 const rgb=v=>(v<<3)|(v>>2);
 for(let col=0;col<d.map.columns;col++)for(let row=0;row<d.map.width;row++){
  const word=d.codes[col*d.map.width+row],pal=banks[word>>>13];
  if(!pal)throw Error('No tile colors');
  const tile=word&8191;
  for(let dy=0;dy<16;dy++)for(let dx=0;dx<16;dx++){
   const pen=rom.rawPixel('tile',tile,dx,dy);if(pen===15)continue;
   const v=pal[pen],x=row*16+dy,y=height-1-(col*16+dx),i=(y*width+x)*4;
   pixels[i]=rgb((v>>10)&31);pixels[i+1]=rgb((v>>5)&31);pixels[i+2]=rgb(v&31);pixels[i+3]=255;
  }
 }
 ctx.putImageData(image,0,0);
 if(artCache.size>=3)artCache.delete(artCache.keys().next().value);
 artCache.set(k,c);return c;
}
// Scene 2's actual lists: lower [17, blank0], upper [18, blank0,19,20].
// Block 0 is 2x20, all word zero; captured upper strips establish expansion
// across the 14-row viewport. Stop at the terminator, do not wrap the map.
let streamCache=new Map();
function capturedSceneStream(layer){
 if(layer!==0&&layer!==1)return null;
 if(streamCache.has(layer))return streamCache.get(layer);
 const scene=scenes()?.[2];if(!scene)return null;
 const list=scene.mapLists[layer],expected=layer===0?[17,0]:[18,0,19,20];
 if(list.length!==expected.length+1||list.at(-1)!==null)throw Error('Captured scene list length');
 const chunks=[];let columns=0,lastContentEnd=0;
 for(let i=0;i<expected.length;i++){
  const ref=list[i];if(ref?.group!==0||ref.id!==expected[i])throw Error('Captured scene list entry');
  const d=decode(ref.group,ref.id),m=d.map;
  if(ref.id===0){
   if(m.width!==2||m.columns!==20||d.codes.some(w=>w!==0))throw Error('Unexpected blank map');
   chunks.push(new Uint16Array(14*m.columns));
  }else{
   if(m.width!==14)throw Error('Captured scene row count');
   chunks.push(d.codes);lastContentEnd=columns+m.columns;
  }
  columns+=m.columns;
 }
 const codes=new Uint16Array(columns*14);let offset=0;
 for(const chunk of chunks){codes.set(chunk,offset);offset+=chunk.length;}
 const stream=Object.freeze({layer,width:14,columns,lastContentEnd,codes});
 streamCache.set(layer,stream);return stream;
}
function capturedSceneStrip(layer,firstCol,count=21){
 const s=capturedSceneStream(layer);
 if(!s||!Number.isInteger(firstCol)||!Number.isInteger(count)||firstCol<0||count<1||firstCol+count>s.columns)return null;
 return s.codes.slice(firstCol*14,(firstCol+count)*14);
}
function stats(){const a=all();return {groups:a?.length??0,blocks:a?.reduce((n,g)=>n+g.length,0)??0,words:a?.flat().reduce((n,m)=>n+m.wordCount,0)??0,cachedBlocks:words.size};}
window.addEventListener('strikers-rom-ready',()=>{catalog=null;words.clear();scenesCache=null;paletteAudit=null;paletteCache.clear();artCache.clear();streamCache.clear()});
window.StrikersOriginalMaps=Object.freeze({all,get,decode,strip,matchCapture,scenes,scenePalette,auditScenePalettes,renderBlock,capturedSceneStream,capturedSceneStrip,stats,captureBlocks});
})();