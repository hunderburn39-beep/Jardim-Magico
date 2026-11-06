/* Jardim Mágico 3D V2.3 — rolos verticais reais + gravidade nas cascatas.
 * Cada coluna move geometrias no eixo Y; os símbolos entram PELO TOPO.
 * A arte HD permanece na face dos objetos 3D, com relevo e friso fino.
 * O movimento não decide os pagamentos. Recebe matrizes já calculadas.
 */
export function createVerticalReels({pc,model,mat,app,symbols,textureFromSymbol,createSymbolFace}){
 const STEP=1.10, TOP=.26+STEP, MAX_Y=1.71, MIN_Y=-1.23;
 const rowY=r=>TOP-r*STEP;
 const reelTiles=[],extras=[],columns=[];
 const symbolMaterials=symbols.map(textureFromSymbol);
 const edge=mat('#784630',{metal:.35,gloss:66});
 const glaze=mat('#4c215d',{metal:.23,gloss:77,emissive:'#3a164f',emission:.12});
 const inner=mat('#21102f',{metal:.06,gloss:50});
 const gold=mat('#e2ae69',{metal:.80,gloss:94,emissive:'#6d451e',emission:.12});
 const waveMaterials=[
  mat('#ffc36f',{gloss:96,emissive:'#e39f3b',emission:.56,opacity:.24}),
  mat('#94dbfa',{gloss:96,emissive:'#398fc7',emission:.48,opacity:.25}),
  mat('#e2a8eb',{gloss:94,emissive:'#a365cf',emission:.56,opacity:.24})
 ];
 let spinning=null,dropping=null;
 function applySymbol(tile,index){
  tile.idx=index;
  const material=symbolMaterials[index]||symbolMaterials[0];
  // Para malhas 2D personalizadas, atualizar o MeshInstance diretamente;
  // o setter render.material é ignorado em certos tipos de renderização.
  const instances=tile.face.render.meshInstances;
  if(instances?.length){
   for(const instance of instances)instance.material=material;
  }else{
   tile.face.render.material=material;
  }
  tile.halo.enabled=[4,5,9,11].includes(index)&&tile.node.enabled;
  if(tile.halo.enabled)tile.halo.render.material=waveMaterials[index===4?0:index===5?1:2];
 }
 function setY(tile,y){
  tile.node.setLocalPosition(tile.x,y,0);
  // Entrada e saída recortadas pelos limites interiores da madeira.
  // Ocultamos qualquer objeto totalmente fora da janela dos tambores.
  const visible=y<MAX_Y&&y>MIN_Y;
  tile.node.enabled=visible;
  if(visible)tile.halo.enabled=[4,5,9,11].includes(tile.idx);
 }
 function makeTile(col,row,slot){
  const node=new pc.Entity('Tambor '+(col+1)+' peça '+slot);
  app.root.addChild(node);
  const x=(col-2)*1.066;
  // A fotografia original não tem quadrados dourados por cada símbolo:
  // as figuras flutuam num painel violeta, entre separadores de coluna.
  const halo=model('Luminescência do símbolo','sphere',[0,0,.405],[.83,.83,.022],waveMaterials[0],node);
  // A frente tem UV explícitos e aponta sempre para +Z, sem inverter
  // as imagens em cada face de um cubo texturizado.
  const face=createSymbolFace({pc,app,node,
   material:symbolMaterials[(col*3+Math.max(0,row))%symbols.length],
   name:'Símbolo original 3D (frente UV correta)'});
  const tile={node,face,halo,idx:0,materials:symbolMaterials,x,homeRow:row,slot,
   col,done:true};
  applySymbol(tile,(col*3+Math.max(0,row))%symbols.length);
  setY(tile,rowY(slot));
  return tile;
 }
 // Fundo comum aos cinco rolos, sem divisões em quadrados individuais.
 const reelVelvet=mat('#2b1048',{metal:.09,gloss:44,emissive:'#240839',emission:.23});
 const separator=mat('#dd9d50',{metal:.81,gloss:88,emissive:'#59331b',emission:.11});
 model('Veludo dos cinco tambores','box',[0,.26,-.14],[5.40,3.30,.11],reelVelvet);
 for(let sep=0;sep<4;sep++){
  const x=(sep-1.5)*1.066;
  model('Separador fino vertical','box',[x,.26,.45],[.026,3.21,.055],separator);
 }
 for(let col=0;col<5;col++){
  const tiles=[];
  for(let row=0;row<3;row++)tiles.push(makeTile(col,row,row));
  const extra=makeTile(col,-1,-1);
  extra.node.enabled=false;
  reelTiles[col]=tiles;
  extras[col]=extra;
  columns[col]={tiles:[...tiles,extra],col,steps:0,completed:0,elapsed:0,duration:0,board:null,finished:true};
 }
 function nextInt(tile,step,steps,board){
  const remaining=steps-step;
  const final=tile.homeRow>=0&&remaining===tile.homeRow+1;
  if(final&&board)applySymbol(tile,board[tile.col][tile.homeRow]);
  else applySymbol(tile,Math.floor(Math.random()*symbols.length));
 }
 function startSpin(board,turbo=false,onDone){
  if(spinning||dropping)return false;
  let count=0;
  columns.forEach((column,i)=>{
   column.board=board;
   column.steps=8+4*Math.floor(i/2); // múltiplos de quatro: todas as peças acabam no lugar original
   column.completed=0;column.elapsed=0;
   column.duration=(.91+i*.17)/(turbo?2.25:1);
   column.finished=false;
   for(const tile of column.tiles){
    tile.slot=tile.homeRow;
    setY(tile,rowY(tile.slot));
   }
  });
  spinning={onDone};
  return true;
 }
 function beginCascade(board,removed,onDone,duration=.66){
  if(spinning||dropping)return false;
  const deleted=new Set((removed||[]).map(({c,r})=>c+','+r));
  const pieces=[];
  for(let c=0;c<5;c++){
   const source=reelTiles[c];
   const kept=[];
   const reusable=[];
   for(let r=2;r>=0;r--){
    if(deleted.has(c+','+r))reusable.push(source[r]);
    else kept.push(source[r]);
   }
   const next=Array(3);
   let to=2;
   for(const tile of kept){
    next[to]=tile;
    pieces.push({tile,from:rowY(tile.slot),to:rowY(to),target:board[c][to]});
    to--;
   }
   // Os elementos novos aparecem inteiramente por cima e descem pela coluna.
   let ordinal=0;
   while(to>=0){
    const tile=reusable[ordinal++];
    if(!tile)throw new Error('Cascata inválida: faltam peças para preencher a coluna');
    next[to]=tile;
    const start=rowY(-1)-(ordinal-1)*.24;
    applySymbol(tile,board[c][to]);
    pieces.push({tile,from:start,to:rowY(to),target:board[c][to],newTile:true});
    to--;
   }
   reelTiles[c]=next;
   const column=columns[c];
   for(let r=0;r<3;r++){
    next[r].homeRow=r;
    next[r].slot=r;
   }
   column.tiles=[...next,extras[c]];
  }
  for(const p of pieces)setY(p.tile,p.from);
  dropping={pieces,elapsed:0,duration:Math.max(.34,duration),onDone};
  return true;
 }
 function update(dt){
  const delta=Math.min(.06,Math.max(0,dt));
  if(spinning){
   let complete=0;
   for(const column of columns){
    if(column.finished){complete++;continue}
    column.elapsed+=delta;
    const t=Math.min(1,column.elapsed/column.duration);
    // Movimento sempre crescente de cima para baixo; a última peça abranda.
    const total=column.steps;
    const v=t<.78?(total-1)*(t/.78):
      (total-1)+1-Math.pow(1-(t-.78)/.22,3);
    const completed=Math.min(total,Math.floor(v+1e-7));
    while(column.completed<completed){
     column.completed++;
     for(const tile of column.tiles){
      tile.slot++;
      if(tile.slot>2){tile.slot=-1;nextInt(tile,column.completed,total,column.board)}
     }
    }
    const frac=t>=1?0:v-completed;
    for(const tile of column.tiles)setY(tile,rowY(tile.slot)-frac*STEP);
    if(t>=1){
     column.finished=true;
     for(const tile of column.tiles)setY(tile,rowY(tile.slot));
     for(let r=0;r<3;r++)applySymbol(reelTiles[column.col][r],column.board[column.col][r]);
     extras[column.col].node.enabled=false;
     complete++;
    }
   }
   if(complete===5){const cb=spinning.onDone;spinning=null;if(cb)cb()}
  }
  if(dropping){
   const d=dropping;
   d.elapsed+=delta;
   const t=Math.min(1,d.elapsed/d.duration);
   const eased=1-Math.pow(1-t,3);
   for(const p of d.pieces){
    const y=p.from+(p.to-p.from)*eased;
    setY(p.tile,y);
   }
   if(t>=1){
    for(const p of d.pieces){applySymbol(p.tile,p.target);setY(p.tile,p.to)}
    dropping=null;
    if(d.onDone)d.onDone();
   }
  }
 }
 function showBoard(board){
  if(spinning||dropping)return false;
  for(let c=0;c<5;c++)for(let r=0;r<3;r++){
   const tile=reelTiles[c][r];
   tile.slot=r;tile.homeRow=r;
   applySymbol(tile,board[c][r]);
   setY(tile,rowY(r));
  }
  return true;
 }
 function forceReset(board){
  // Recuperação determinística: independente de RAF/callbacks perdidos.
  // Cancela a animação e coloca as 15 peças no resultado já calculado.
  spinning=null;dropping=null;
  for(let c=0;c<5;c++){
   const column=columns[c];
   column.finished=true;
   column.completed=0;column.elapsed=0;column.steps=0;
   column.tiles=[...reelTiles[c],extras[c]];
   for(let r=0;r<3;r++){
    const tile=reelTiles[c][r];
    tile.slot=r;tile.homeRow=r;
    tile.node.enabled=true;tile.face.enabled=true;
    tile.face.setLocalEulerAngles(0,0,0);
    tile.face.setLocalScale(.98,.98,1);
    applySymbol(tile,board[c][r]);
    setY(tile,rowY(r));
   }
   extras[c].node.enabled=false;
  }
  return true;
 }
 return {reelTiles,extras,symbolMaterials,startSpin,beginCascade,
  update,showBoard,forceReset,isBusy:()=>!!(spinning||dropping)};
}
