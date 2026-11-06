/* Jardim Mágico 3D V2.2 — acabamento modular de peças e metais.
 * Os cálculos e a banca ficam no demo-engine; aqui há só geometrias.
 * Evita filtros, sombras em tempo real ou texturas 4K no iPhone.
 */
export function enhanceReels({pc,model,mat,reelTiles,app}){
 const polishedGold=mat('#f5c778',{metal:.87,gloss:97,emissive:'#986123',emission:.09});
 const gildedBevel=mat('#84596c',{metal:.46,gloss:94,emissive:'#422443',emission:.1});
 const copper=mat('#693a27',{metal:.35,gloss:65});
 const halos=[
  mat('#ffc066',{gloss:92,emissive:'#f6a354',emission:.59,opacity:.27}),
  mat('#8fe9ff',{gloss:95,emissive:'#49a1e2',emission:.58,opacity:.24}),
  mat('#f4b8fc',{gloss:95,emissive:'#d77ee5',emission:.53,opacity:.27}),
  mat('#fce4a4',{gloss:95,emissive:'#c7a35e',emission:.5,opacity:.24})
 ];
 const rarity=[];
 for(let c=0;c<5;c++){
  for(let r=0;r<3;r++){
   const x=(c-2)*1.066,y=.26+(1-r)*1.1;
   const cell=reelTiles[c][r];
   // Dois níveis de profundidade na frente da base, atrás do símbolo.
   model('V2.2 chanfro da carta','box',[x,y,.317],[.999,1.022,.175],copper);
   model('V2.2 esmalte em alto relevo','box',[x,y,.378],[.975,1.002,.09],gildedBevel);
   // O ouro destaca-se FISICAMENTE na frente da placa.
   model('V2.2 friso dourado superior','box',[x,y+.507,.553],[.986,.026,.067],polishedGold);
   model('V2.2 friso dourado inferior','box',[x,y-.507,.553],[.986,.026,.067],polishedGold);
   // Duas faces em relevo de ambos os lados reforçam a sensação de volume.
   model('V2.2 aro fino esquerdo','box',[x-.492,y,.548],[.023,.975,.067],polishedGold);
   model('V2.2 aro fino direito','box',[x+.492,y,.548],[.023,.975,.067],polishedGold);
   // Halo translucido por baixo da arte HD, só nos símbolos mágicos.
   const halo=model('V2.2 halo do símbolo raro','sphere',[x,y,.415],[.76,.76,.025],halos[0]);
   halo.enabled=false;
   rarity.push({cell,halo,last:-1,phase:(c*3+r)*.41});
  }
 }
 let acc=0;
 const reduced=typeof matchMedia==='function'&&matchMedia('(prefers-reduced-motion: reduce)').matches;
 app.on('update',dt=>{
  acc+=dt;
  if(acc<.075)return;
  acc=0;
  for(const item of rarity){
   const symbol=item.cell.idx;
   const special=symbol===4||symbol===5||symbol===9||symbol===11;
   item.halo.enabled=special;
   if(!special)continue;
   if(symbol!==item.last){
    item.last=symbol;
    item.halo.render.material=halos[symbol===4?0:symbol===5?1:symbol===9?2:3];
   }
   const wave=reduced?1:(1+.045*Math.sin(performance.now()*.0023+item.phase));
   item.halo.setLocalScale(.76*wave,.76*wave,.025);
  }
 });
 return {tiles:rarity.length};
}
