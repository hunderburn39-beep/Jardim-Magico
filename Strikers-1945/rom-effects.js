/* Original graphical effect sequences from World ROM program records.
 * All frame codes, dimensions and flag words are read directly from the
 * verified ROM. Duration and palette assignments are preview choices,
 * pending tracing the original animation scheduler and palette loader.
 */
(()=>{'use strict';
const groups=Object.freeze({
 large:{address:0x249c8,count:16,base:2952,bank:2,previewPalette:17},
 medium:{address:0x24bd8,count:12,base:5147,bank:2,previewPalette:17},
 burst:{address:0x24c38,count:14,base:5267,bank:3,previewPalette:17},
 small:{address:0x24d38,count:12,base:5255,bank:2,previewPalette:17}
});
let records=new Map(),images=new Map();
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
function sprite(name,progress){
 const f=frame(name,progress);if(!f)return null;
 const key=name+':'+f.code;if(images.has(key))return images.get(key);
 const d=window.StrikersSpriteDefinitions,c=document.createElement('canvas');
 const pal=d.palette(groups[name].previewPalette);
 if(!d.draw(c,f.code,pal))return null;
 images.set(key,c);return c;
}
function draw(ctx,name,progress,x,y,scale=2){
 const c=sprite(name,progress);if(!c)return false;
 const w=c.width*scale,h=c.height*scale;
 ctx.save();ctx.imageSmoothingEnabled=false;
 ctx.drawImage(c,Math.round(x-w/2),Math.round(y-h/2),w,h);ctx.restore();return true;
}
// Visual item identification from native sprite assemblies: lettered
// capsules and gold-bar rotation. Exact runtime palette assignment and
// animation timing have not yet been traced. Keep those choices separate.
const itemGroups=Object.freeze({
 power:Object.freeze({start:1626,step:2,count:8,nx:1,ny:2,previewPalette:16}),
 bomb:Object.freeze({start:1610,step:2,count:8,nx:1,ny:2,previewPalette:15}),
 gold:Object.freeze({start:1643,step:1,count:8,nx:1,ny:1,previewPalette:16})
});
function itemSprite(type,time){
 const g=itemGroups[type],d=window.StrikersSpriteDefinitions;
 if(!g||!d||!window.StrikersROM?.isVerified?.())return null;
 const code=g.start+g.step*(Math.floor(Math.max(0,Number(time)||0)*12)%g.count);
 const def=d.get(code);if(!def||def.nx!==g.nx||def.ny!==g.ny)return null;
 const key='item:'+type+':'+code;
 if(images.has(key))return images.get(key);
 const c=document.createElement('canvas'),pal=d.palette(g.previewPalette);
 if(!pal||!d.draw(c,code,pal))return null;
 images.set(key,c);return c;
}
function drawItem(ctx,type,time,x,y,scale=2){
 const c=itemSprite(type,time);if(!c)return false;
 ctx.save();ctx.imageSmoothingEnabled=false;
 ctx.drawImage(c,Math.round(x-c.width*scale/2),Math.round(y-c.height*scale/2),c.width*scale,c.height*scale);ctx.restore();
 return true;
}
function stats(){return {groups:Object.keys(groups).length,frames:Object.values(groups).reduce((n,g)=>n+g.count,0),paletteStatus:'provisional ROM palette 17',timingStatus:'preview'};}
window.addEventListener('strikers-rom-ready',()=>{records.clear();images.clear()});
window.StrikersOriginalEffects=Object.freeze({groups,read,frame,sprite,draw,itemGroups,itemSprite,drawItem,stats});
})();