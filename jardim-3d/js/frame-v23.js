/* Jardim Mágico 3D V2.3 — moldura orgânica inspirada na fotografia.
 * Ramos torcidos em 3D, flores/folhas verdadeiras e ouro discreto.
 * Nenhuma moldura rectangular sobre os símbolos.
 */
export function createOrganicFrame({pc,model,mat,app}){
 const bark=mat('#62402b',{metal:.04,gloss:32});
 const barkLight=mat('#9c6c3f',{metal:.06,gloss:42});
 const barkShadow=mat('#3e2c22',{metal:.04,gloss:26});
 const moss=mat('#276147',{metal:.03,gloss:31});
 const leaf=mat('#5b9b51',{metal:.04,gloss:46});
 const leafLight=mat('#92b961',{metal:.06,gloss:49});
 const gilt=mat('#e7b869',{metal:.69,gloss:85,emissive:'#6d431b',emission:.10});
 const bloom=mat('#ff7bbb',{metal:.04,gloss:67,emissive:'#a73380',emission:.15});
 const pink=mat('#f5bad8',{metal:.04,gloss:68,emissive:'#ac537f',emission:.13});
 const lilac=mat('#c18ae6',{metal:.07,gloss:65,emissive:'#713893',emission:.14});
 const center=mat('#f5d78c',{metal:.38,gloss:91,emissive:'#b48d36',emission:.21});
 // Interior recuado é uma peça física de veludo (não uma fotografia).
 // Fica por trás dos rolos e permite ver as peças com bom contraste.
 model('Veludo recuado dos tambores','box',[0,.24,-.61],[5.56,3.72,.19],
   mat('#29113d',{metal:.09,gloss:48,emissive:'#250d35',emission:.12}));
 const lantern=mat('#ffe2a1',{metal:.18,gloss:93,emissive:'#ffc977',emission:.72});
 const hook=mat('#9c733d',{metal:.57,gloss:80});
 for(const side of [-1,1]){
  const x=side*3.16;
  rodLantern(x);
 }
 function rodLantern(x){
  model('Corrente fina da lanterna','cylinder',[x,2.78,.42],[.025,.48,.025],hook);
  model('Lanterna mágica','sphere',[x,2.39,.44],[.23,.34,.19],lantern);
  model('Topo metálico da lanterna','cone',[x,2.68,.44],[.25,.16,.22],hook);
  model('Base metálica da lanterna','cylinder',[x,2.12,.44],[.25,.10,.21],hook);
 }
 const woodPoints=[];
 function rod(name,a,b,radius,material){
  const dx=b[0]-a[0],dy=b[1]-a[1],dz=b[2]-a[2];
  const len=Math.hypot(dx,dy,dz);
  if(len<.01)return null;
  const ob=model(name,'cylinder',[(a[0]+b[0])/2,(a[1]+b[1])/2,(a[2]+b[2])/2],
    [radius*2,len,radius*2],material);
  // Cylinder local Y aligns with the desired path in the 3D scene.
  const q=new pc.Quat();
  q.setFromDirections(new pc.Vec3(0,1,0),
   new pc.Vec3(dx/len,dy/len,dz/len));
  ob.setLocalRotation(q);
  return ob;
 }
 function strand(name,points,r,material){
  for(let k=1;k<points.length;k++)rod(name,points[k-1],points[k],r*(1+.13*Math.sin(k*1.8)),material);
 }
 function blossom(x,y,z,scale,petalMat){
  // Five uneven petals for natural appearance, each a 3D sphere.
  for(let i=0;i<5;i++){
   const a=i*Math.PI*2/5+.14;
   const el=model('Pétala de flor','sphere',
      [x+Math.cos(a)*.15*scale,y+Math.sin(a)*.15*scale,z+.038],
      [.16*scale,.115*scale,.066*scale],petalMat);
   el.setLocalEulerAngles(0,0,a*180/Math.PI);
  }
  model('Coração da flor','sphere',[x,y,z+.10],[.09*scale,.09*scale,.07*scale],center);
  model('Folha por trás da flor','sphere',[x+.17*scale,y-.15*scale,z-.065],
   [.21*scale,.07*scale,.075*scale],leaf);
 }
 function border(side){
  const horizontal=side==='top'||side==='bottom';
  const length=horizontal?5.95:4.13,points=[];
  const count=horizontal?18:15;
  for(let i=0;i<=count;i++){
   const t=i/count;
   if(horizontal){
    const x=(t-.5)*length;
    const y=(side==='top'?2.19:-1.74)+.062*Math.sin(t*8.0+.5)+.022*Math.sin(t*24.0);
    points.push([x,y,.70+.05*Math.cos(13*t)]);
   }else{
    const y=-1.79+t*4.04;
    const x=(side==='left'?-2.88:2.88)+.052*Math.sin(t*8+1.1)+.016*Math.sin(t*25);
    points.push([x,y,.70+.047*Math.sin(t*14)]);
   }
  }
  woodPoints.push(points);
  strand('Ramo principal curvo',points,.132,bark);
  const lighter=points.map((p,i)=>[p[0]+(horizontal?0:.078*Math.sin(i*1.12)),
   p[1]+(horizontal?.065*Math.sin(i*1.28):0),
   p[2]+.10]);
  strand('Fibra de madeira entrelaçada',lighter,.058,barkLight);
  const dark=points.map((p,i)=>[p[0]+(horizontal?0:-.05),
   p[1]+(horizontal?-.09:0),p[2]-.045]);
  strand('Raiz de madeira torcida',dark,.071,barkShadow);
  // Creeper naturally follows the gnarled wood, slightly winding forward.
  const vine=points.map((p,i)=>[p[0]+(horizontal?0:.13*Math.sin(i*1.2)),
    p[1]+(horizontal?.095*Math.cos(i*1.3):0),p[2]+.12+.075*Math.sin(i*1.7)]);
  strand('Trepadeira enrolada',vine,.023,moss);
  for(let i=2;i<count-1;i+=3){
   const v=vine[i],next=vine[i+1];
   const direction=(i%2)?1:-1;
   const leafOb=model('Folha viva','sphere',[v[0]+(horizontal?.08:direction*.17),
       v[1]+(horizontal?direction*.14:.04),v[2]+.055],
      [horizontal?.16:.17,.075,.059],i%2?leafLight:leaf);
   leafOb.setLocalEulerAngles(0,0,horizontal?direction*28:direction*69);
  }
  // Fine gilt string woven into natural wood, not a thick rectangular frame.
  const gold=points.map(p=>[p[0],p[1],p[2]+.153]);
  strand('Fio fino dourado',gold,.018,gilt);
 }
 for(const side of ['top','bottom','left','right'])border(side);
 // Petals deliberately restricted to exterior contour: visible reel area clear.
 for(const [x,y,z,sc,mt] of [
  [-2.82,2.19,.95,1.25,bloom],[2.82,2.16,.94,1.15,pink],
  [-2.81,-1.70,.91,1.10,lilac],[2.83,-1.71,.96,1.16,bloom],
  [-1.50,2.28,.9,.70,pink],[1.56,2.25,.89,.70,lilac],
  [-2.95,.85,.89,.60,pink],[2.94,-.53,.93,.66,lilac],
  [-.62,-1.84,.91,.60,bloom],[.98,-1.81,.91,.62,pink]
 ])blossom(x,y,z,sc,mt);
 return {branches:4,style:'madeira entrelaçada com trepadeiras e flores 3D'};
}
