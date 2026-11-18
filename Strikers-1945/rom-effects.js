/* Original graphical effect sequences from World ROM program records.
 * All frame codes, dimensions and flag words are read directly from the
 * verified ROM. Common palette-bank assignments match all 16 ROM tables;
 * animation duration remains provisional.
 */
(()=>{'use strict';
const groups=Object.freeze({
 large:{address:0x249c8,count:16,base:2952,bank:2},
 medium:{address:0x24bd8,count:12,base:5147,bank:2},
 burst:{address:0x24c38,count:14,base:5267,bank:3},
 small:{address:0x24d38,count:12,base:5255,bank:2}
});
let records=new Map(),images=new Map(),prepared=false;
function read(name){
 if(records.has(name))return records.get(name);
 const r=window.StrikersROM,d=window.StrikersSpriteDefinitions,g=groups[name];
 if(!g||!r?.isVerified?.()||!d)return null;
 const a=[];
 for(let i=0;i<g.count;i++){
  const at=g.address+i*8,attr=r.programWord(at),code=r.programWord(at+2),rx=r.programWord(at+4),ry=r.programWord(at+6),def=d.get(code);
  if(!def||attr!==g.bank*256||(i===0&&code!==g.base)||
    (i>0&&code!==a[i-1].code+a[i-1].nx*a[i-1].ny))throw Error('Invalid effect '+name+' '+i);
  a.push(Object.freeze({address:at,attr,code,rx,ry,nx:def.nx,ny:def.ny}));
 }
 const frozen=Object.freeze(a);records.set(name,frozen);return frozen;
}
function frame(name,progress){
 const a=read(name);
 if(!a)return null;
 return a[Math.max(0,Math.min(a.length-1,Math.floor((Number(progress)||0)*a.length)))];
}
function effectImage(name,f){
 if(!f)return null;
 const key=name+':'+f.code;if(images.has(key))return images.get(key);
 const d=window.StrikersSpriteDefinitions,c=document.createElement('canvas');
 const pal=d.commonSpritePalette(groups[name].bank);
 if(!pal)return null;
 if(!d.draw(c,f.code,pal))return null;
 images.set(key,c);return c;
}
function sprite(name,progress){return effectImage(name,frame(name,progress));}
function draw(ctx,name,progress,x,y,scale=2){
 const c=sprite(name,progress);if(!c)return false;
 const w=c.width*scale,h=c.height*scale;
 ctx.save();ctx.imageSmoothingEnabled=false;
 ctx.drawImage(c,Math.round(x-w/2),Math.round(y-h/2),w,h);ctx.restore();return true;
}
// Visual item identification from native sprite assemblies: lettered
// capsules and gold-bar rotation. The common palette tables resolve the bank; animation timing is provisional.
const itemGroups=Object.freeze({
 power:Object.freeze({address:0x3b2c4,start:1610,step:2,count:8,nx:1,ny:2,bank:1}),
 bomb:Object.freeze({address:0x3b304,start:1626,step:2,count:8,nx:1,ny:2,bank:1}),
 gold:Object.freeze({address:0x3b344,start:1643,step:1,count:8,nx:1,ny:1,bank:1})
});
function itemRecords(type){
 const key='item-records:'+type;
 if(records.has(key))return records.get(key);
 const r=window.StrikersROM,d=window.StrikersSpriteDefinitions,g=itemGroups[type];
 if(!g||!d||!r?.isVerified?.())return null;
 const frames=[];
 for(let i=0;i<g.count;i++){
  const at=g.address+i*8,attr=r.programWord(at),code=r.programWord(at+2),rx=r.programWord(at+4),ry=r.programWord(at+6);
  const def=d.get(code);
  if(attr!==g.bank*256||code!==g.start+g.step*i||def?.nx!==g.nx||def?.ny!==g.ny)return null;
  frames.push(Object.freeze({address:at,attr,code,rx,ry}));
 }
 const result=Object.freeze(frames);records.set(key,result);return result;
}
function itemImage(type,record){
 if(!record)return null;
 const g=itemGroups[type],d=window.StrikersSpriteDefinitions,code=record.code;
 const key='item:'+type+':'+code;
 if(images.has(key))return images.get(key);
 const c=document.createElement('canvas'),pal=d.commonSpritePalette(g.bank);
 if(!pal||!d.draw(c,code,pal))return null;
 images.set(key,c);return c;
}
function itemSprite(type,time){
 const a=itemRecords(type);if(!a)return null;
 return itemImage(type,a[Math.floor(Math.max(0,Number(time)||0)*12)%a.length]);
}
// Native score numerals exist for all six values. Only display the awarded
// amount; this does not assign any new payout rule or change the score.
const medalNumbers=Object.freeze({200:1651,500:1653,1000:1655,2000:1657,4000:1659,10000:1661});
function medalImage(code){
 const d=window.StrikersSpriteDefinitions;
 if(!window.StrikersROM?.isVerified?.()||!d||d.get(code)?.nx!==1||d.get(code)?.ny!==1)return null;
 const key='medal:'+code;let c=images.get(key);
 if(!c){
  c=document.createElement('canvas');
  const pal=d.palette(1); // readable provisional palette, not assigned ROM bank
  if(!pal||!d.draw(c,code,pal))return null;
  images.set(key,c);
 }
 return c;
}
function drawMedalScore(ctx,value,time,x,y){
 const base=medalNumbers[value];if(!base)return false;
 const c=medalImage(base+Math.floor(Math.max(0,Number(time)||0)*12)%2);if(!c)return false;
 ctx.save();ctx.imageSmoothingEnabled=false;ctx.drawImage(c,Math.round(x-16),Math.round(y-16),32,32);ctx.restore();return true;
}
function drawItem(ctx,type,time,x,y,scale=2){
 const c=itemSprite(type,time);if(!c)return false;
 ctx.save();ctx.imageSmoothingEnabled=false;
 ctx.drawImage(c,Math.round(x-c.width*scale/2),Math.round(y-c.height*scale/2),c.width*scale,c.height*scale);ctx.restore();
 return true;
}
function prepare(){
 if(prepared)return true;
 if(!window.StrikersROM?.isVerified?.())return false;
 for(const name of Object.keys(groups)){
  const a=read(name);if(!a||a.length!==groups[name].count)throw Error('Missing effect records: '+name);
  for(const f of a)if(!effectImage(name,f))throw Error('Missing effect image: '+name+' '+f.code);
 }
 for(const name of Object.keys(itemGroups)){
  const a=itemRecords(name);if(!a||a.length!==itemGroups[name].count)throw Error('Missing item records: '+name);
  for(const f of a)if(!itemImage(name,f))throw Error('Missing item image: '+name+' '+f.code);
 }
 for(const code of Object.values(medalNumbers))for(let i=0;i<2;i++)if(!medalImage(code+i))throw Error('Missing medal image: '+(code+i));
 prepared=true;return true;
}
function stats(){return {prepared,cachedImages:images.size,itemFrames:24,medalFrames:12,groups:Object.keys(groups).length,frames:Object.values(groups).reduce((n,g)=>n+g.count,0),paletteStatus:'static common bank pointers verified; runtime modifications possible',timingStatus:'preview'};}
window.addEventListener('strikers-rom-ready',()=>{records.clear();images.clear();prepared=false});
window.StrikersOriginalEffects=Object.freeze({groups,read,frame,sprite,draw,itemGroups,itemRecords,itemSprite,drawItem,medalNumbers,drawMedalScore,prepare,stats});
})();