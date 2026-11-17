import fs from 'node:fs';
import zlib from 'node:zlib';
import assert from 'node:assert/strict';

const file=new URL('../stage1-plane-frames.b64',import.meta.url);
const buffer=zlib.inflateSync(Buffer.from(fs.readFileSync(file,'utf8').trim(),'base64'));
const d=new DataView(buffer.buffer,buffer.byteOffset,buffer.byteLength),bytes=buffer;
assert.equal(buffer.toString('ascii',0,3),'EP1');
assert.equal(bytes[3],1);
const count=d.getUint16(4),banks=bytes[6];let offset=7+banks+2*banks*3*16;
const allowed=new Set([10,11,12,23,25,27,28]),frames=[];
function dimensions(rawX,rawY){
 const nx=((rawX>>9)&7)+1,ny=((rawY>>9)&7)+1;
 let rx=rawX&511,ry=(rawY&255)-(rawY&256);
 if(rx>=384)rx-=512;
 const zx=(rawX>>12)&15,zy=(rawY>>12)&15;
 rx+=(nx*zx+2)>>2;ry+=(ny*zy+2)>>2;
 const nativeW=nx*(32-zx)/2,nativeH=ny*(32-zy)/2;
 return {x:(ry-22+nativeH*.5)*2,y:(320-rx-nativeW*.5)*2,w:nativeH*2,h:nativeW*2,nx,ny};
}
function role(e){
 if(e.nx>=5&&e.ny<=3)return 'ground';
 if(e.nx>=5||e.ny>=5)return 'bomber';
 if(e.nx>=3||e.ny>=3)return 'ground';
 return 'fighter';
}
function related(a,b){
 if(!a||!b||role(a)!==role(b))return false;
 if(Math.abs(a.w-b.w)>85||Math.abs(a.h-b.h)>85)return false;
 if(Math.abs(a.code-b.code)>700)return false;
 if(a.palette!==b.palette&&Math.abs(a.code-b.code)>320)return false;
 if(Math.hypot(a.x-b.x,a.y-b.y)>245)return false;
 return true;
}
for(let i=0;i<count;i++){
 const frame=d.getUint16(offset),phase=bytes[offset+2],itemsCount=bytes[offset+3];offset+=4;
 const items=[];
 for(let j=0;j<itemsCount;j++){
  const id=bytes[offset],rawX=d.getUint16(offset+1),rawY=d.getUint16(offset+3),attr=d.getUint16(offset+5),code=d.getUint16(offset+7);offset+=9;
  const palette=(attr>>8)&63;
  if(!allowed.has(palette))continue;
  const el={id,frame,code,attr,palette,phase,...dimensions(rawX,rawY)};el.role=role(el);items.push(el);
 }
 frames.push({frame,items});
}
assert.equal(count,116);assert.equal(banks,7);
const first=frames[0].frame,last=frames.at(-1).frame;
const timeOf=f=>+(47*(f-first)/(last-first)).toFixed(2);
const counts={},paletteCounts={},big=[],misclassified=[],outliers=[],reuses=[],snapshots=[];
let tracked=0,relatedCount=0;
for(let i=0;i<frames.length;i++){
 const f=frames[i],previous=frames[i-1];
 const prev=new Map((previous?.items||[]).map(e=>[e.id,e]));
 const current=new Map(f.items.map(e=>[e.id,e]));
 for(const e of f.items){
  counts[e.role]=(counts[e.role]||0)+1;paletteCounts[e.palette]=(paletteCounts[e.palette]||0)+1;
  if(Math.max(e.w,e.h)>=160)big.push({t:timeOf(f.frame),id:e.id,palette:e.palette,size:[Math.round(e.w),Math.round(e.h)],position:[Math.round(e.x),Math.round(e.y)],role:e.role});
  if(e.role==='ground'&&e.nx>=3&&e.nx<=4&&e.ny>=3&&e.ny<=4)misclassified.push(e);
  const p=prev.get(e.id);if(!p)continue;tracked++;
  const dt=(f.frame-previous.frame)/60,dist=Math.hypot(e.x-p.x,e.y-p.y),matched=related(p,e);
  if(matched)relatedCount++;
  if(dist>120||!matched){outliers.push({t:timeOf(f.frame),id:e.id,dt:+dt.toFixed(2),dist:Math.round(dist),from:[Math.round(p.x),Math.round(p.y)],to:[Math.round(e.x),Math.round(e.y)],paletteFrom:p.palette,paletteTo:e.palette,codeFrom:p.code,codeTo:e.code,sizeFrom:[Math.round(p.w),Math.round(p.h)],sizeTo:[Math.round(e.w),Math.round(e.h)],related:matched})}
  if(!matched)reuses.push({t:timeOf(f.frame),id:e.id,dist:Math.round(dist),oldRole:p.role,newRole:e.role});
 }
 if([0,2,4,6,8,10,12,14,16,18,20,24,28,32,38,44,46].some(t=>Math.abs(timeOf(f.frame)-t)<.24))
  snapshots.push({t:timeOf(f.frame),total:f.items.length,air:f.items.filter(e=>e.role!=='ground').length,large:f.items.filter(e=>e.w>100||e.h>100).map(e=>({id:e.id,x:Math.round(e.x),y:Math.round(e.y),w:Math.round(e.w),h:Math.round(e.h),type:e.role}))});
}
console.log('CAPTURE',JSON.stringify({count,banks,bytes:buffer.byteLength,first,last,spanSecs:(last-first)/60,visibleSprites:frames.reduce((n,f)=>n+f.items.length,0),roleCounts:counts,paletteCounts,uniqueIds:new Set(frames.flatMap(f=>f.items.map(e=>e.id))).size,linkedPairs:tracked,relatedPairs:relatedCount,misclassified3or4:misclassified.length,largeSprites:big.length,nonmatchingSameSlot:reuses.length}));
console.log('CAPTURE_SNAPSHOTS',JSON.stringify(snapshots.slice(0,25)));
console.log('SUSPICIOUS_JUMPS',JSON.stringify(outliers.slice(0,35)));
console.log('LARGEST_SPRITES',JSON.stringify(big.sort((a,b)=>Math.max(...b.size)-Math.max(...a.size)).slice(0,25)));
console.log('MISMATCH_SAMPLE',JSON.stringify(reuses.slice(0,35)));

const chunks=['stage1-partA.js','stage1-partB.js'].map(name=>
  fs.readFileSync(new URL('../'+name,import.meta.url),'utf8'));
const bg64=chunks.flatMap(source=>[...source.matchAll(/"([A-Za-z0-9+/=]+)"/g)].map(m=>m[1])).join('');
const bg=zlib.inflateSync(Buffer.from(bg64,'base64'));
const mapView=new DataView(bg.buffer,bg.byteOffset,bg.byteLength);
const mapFrames=mapView.getUint16(0,true),cols=mapView.getUint16(2,true),rows=mapView.getUint16(4,true);
const mapSize=10+512+cols*rows*2*2;
const mapFirst=mapView.getUint16(6,true),mapLast=mapView.getUint16(6+(mapFrames-1)*mapSize,true);
console.log('BACKGROUND_CAPTURE',JSON.stringify({mapFrames,cols,rows,first:mapFirst,last:mapLast,spanSeconds:(mapLast-mapFirst)/60,
 sharedOrigin:mapFirst===first,sharedEnd:mapLast===last,rawBytes:bg.length}));
