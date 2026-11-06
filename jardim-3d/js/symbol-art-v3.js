/* Jardim Mágico V3: SCATTER, pedra e moeda em desenho vetorial 2.5D.
   Canvas transparente com brilho de pedra, sem emoji amarelo genérico. */
export function drawMagicBonus(g,id,size=256){
 const c=size/2;const S=size/256;
 g.clearRect(0,0,size,size);g.save();g.scale(S,S);
 function circle(x,y,r,fill,stroke,lineWidth=0){
  g.beginPath();g.arc(x,y,r,0,Math.PI*2);
  g.fillStyle=fill;g.fill();
  if(stroke){g.lineWidth=lineWidth;g.strokeStyle=stroke;g.stroke()}
 }
 function oval(x,y,rx,ry,rot,fill){
  g.save();g.translate(x,y);g.rotate(rot);
  g.beginPath();g.ellipse(0,0,rx,ry,0,0,Math.PI*2);g.fillStyle=fill;g.fill();g.restore();
 }
 const gold=g.createLinearGradient(30,20,225,227);
 gold.addColorStop(0,'#fffce2');gold.addColorStop(.16,'#ffe5a2');
 gold.addColorStop(.42,'#ffb754');gold.addColorStop(.73,'#9b511f');gold.addColorStop(1,'#ffda83');
 const purple=g.createRadialGradient(85,67,1,128,130,109);
 purple.addColorStop(0,'#ffb5e9');purple.addColorStop(.28,'#e164cc');
 purple.addColorStop(.62,'#9130a7');purple.addColorStop(1,'#34105e');
 const glow=g.createRadialGradient(128,128,22,128,128,126);
 glow.addColorStop(0,'rgba(255,221,161,.38)');glow.addColorStop(1,'rgba(255,73,214,0)');
 if(id===9){
  g.shadowColor='#e66aff';g.shadowBlur=16;
  for(let j=0;j<10;j++){
   const a=j*Math.PI*2/10;
   oval(128+Math.cos(a)*70,125+Math.sin(a)*70,28,45,a+Math.PI/2,purple);
  }
  g.shadowBlur=5;
  circle(128,128,89,gold,'#ffeeb8',5);
  circle(128,128,78,purple,'#6a215f',4);
  const shine=g.createRadialGradient(99,80,4,125,126,89);
  shine.addColorStop(0,'rgba(255,255,240,.85)');
  shine.addColorStop(.24,'rgba(255,198,247,.32)');
  shine.addColorStop(1,'rgba(255,255,255,0)');
  circle(128,128,75,shine);
  g.textAlign='center';g.textBaseline='middle';g.shadowBlur=8;g.shadowColor='#290748';
  g.font='900 31px Georgia,serif';g.lineWidth=3;g.strokeStyle='#541545';
  g.strokeText('SCATTER',128,132);
  g.fillStyle='#fff3b5';g.fillText('SCATTER',128,132);
  for(let j=0;j<6;j++){
   const a=j*Math.PI/3;const x=128+Math.cos(a)*104,y=128+Math.sin(a)*104;
   circle(x,y,3,j%2?'#fff7d1':'#ff9fd9');
  }
 }else if(id===10){
  g.shadowColor='#785cba';g.shadowBlur=13;
  const points=[[124,19],[210,61],[230,154],[180,228],[72,236],[25,150],[48,63]];
  g.beginPath();g.moveTo(...points[0]);
  for(const p of points.slice(1))g.lineTo(...p);
  g.closePath();
  const stone=g.createLinearGradient(30,25,220,230);
  stone.addColorStop(0,'#c2bed5');stone.addColorStop(.36,'#6f6b88');
  stone.addColorStop(.72,'#494762');stone.addColorStop(1,'#272339');
  g.fillStyle=stone;g.fill();
  g.lineWidth=6;g.strokeStyle='#e5cda3';g.stroke();
  g.shadowBlur=0;
  for(const [poly,col] of [
   [[[124,19],[48,63],[116,124]],'#d9d5e8'],
   [[[124,19],[210,61],[116,124]],'#9893b1'],
   [[[210,61],[230,154],[116,124]],'#575770'],
   [[[230,154],[180,228],[116,124]],'#33354d'],
   [[[180,228],[72,236],[116,124]],'#666580'],
   [[[72,236],[25,150],[116,124]],'#797a93'],
   [[[25,150],[48,63],[116,124]],'#a7a5bd']
  ]){
   g.beginPath();g.moveTo(...poly[0]);for(const p of poly.slice(1))g.lineTo(...p);
   g.closePath();g.fillStyle=col;g.fill();
  }
  g.globalAlpha=.28;
  oval(89,81,45,13,-.6,'#ffffff');
  g.globalAlpha=1;
 }else if(id===11){
  g.shadowColor='#ff85df';g.shadowBlur=18;
  circle(128,128,107,gold,'#ffefba',4);
  circle(128,128,94,purple,'#8c391a',4);
  circle(128,128,79,'#632078','#df78cb',2);
  g.shadowBlur=6;
  const sheen=g.createRadialGradient(95,65,5,128,125,90);
  sheen.addColorStop(0,'rgba(255,255,235,.78)');
  sheen.addColorStop(.22,'rgba(246,153,239,.27)');
  sheen.addColorStop(1,'rgba(255,255,255,0)');
  circle(128,128,79,sheen);
  g.textAlign='center';g.textBaseline='middle';
  g.shadowColor='#2c0b3c';g.shadowBlur=7;
  g.font='900 106px Georgia,serif';g.lineWidth=5;
  g.strokeStyle='#793263';g.strokeText('2×',128,136);
  g.fillStyle='#fff0b8';g.fillText('2×',128,136);
  g.shadowBlur=0;
 }
 g.restore();
}
