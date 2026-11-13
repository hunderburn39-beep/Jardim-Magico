/* Pantanal v2.20: linhas de prémio desenhadas a partir dos resultados reais.
   Apenas apresentação visual; sem alterações ao RNG, apostas ou pagamentos. */
(function(){
  'use strict';
  const NS='http://www.w3.org/2000/svg';
  let timer=null, layer=null, serial=0;
  function make(tag,attrs){
    const n=document.createElementNS(NS,tag);
    Object.entries(attrs||{}).forEach(([k,v])=>n.setAttribute(k,String(v)));
    return n;
  }
  function clear(){
    ++serial;
    if(timer!==null){clearInterval(timer);timer=null;}
    if(layer){layer.remove();layer=null;}
  }
  function lineCoords(w){
    if(Number(w.line)>=21 && Number(w.line)<=25){
      const x=57.5+124*(Number(w.line)-21);
      return [0,1,2].map(row=>[x,51+106*row]);
    }
    if(!Array.isArray(w.path)||w.path.length<5)return [];
    return w.path.slice(0,5).map((row,col)=>[57.5+124*col,51+106*row]);
  }
  function coordsText(w){return lineCoords(w).map(([x,y])=>x+','+y).join(' ')}
  function show(wins, preview=false){
    clear();
    const reels=document.getElementById('reels');
    const game=document.getElementById('game');
    if(!reels||!game||!Array.isArray(wins))return;
    const valid=wins.filter(w=>lineCoords(w).length >= 3);
    if(!valid.length)return;
    const current=++serial;
    const svg=make('svg',{id:'pantanalPaylines',viewBox:'0 0 611 314',width:'611',height:'314','aria-hidden':'true'});
    svg.style.cssText='position:absolute;inset:0;left:0;top:0;width:100%;height:100%;z-index:9;pointer-events:none;overflow:hidden;display:block';
    const defs=make('defs');
    const glow=make('filter',{id:'pantanalWinGlow',x:'-30%',y:'-100%',width:'160%',height:'300%'});
    glow.appendChild(make('feGaussianBlur',{stdDeviation:'3'}));
    defs.appendChild(glow);svg.appendChild(defs);
    reels.appendChild(svg);layer=svg;
    const legacy=document.getElementById('lineOverlay');
    if(legacy)legacy.style.setProperty('display','none','important');
    let idx=0;
    // Desenhar imediatamente todas as linhas ativas, depois destacar cada percurso.
    if(preview){
      const others=make('g');
      const colors=['#ffe67b','#72d8ff','#ffa1d3','#b0ff9f','#c9adff'];
      valid.forEach((w,i)=>others.appendChild(make('polyline',{
        points:coordsText(w),
        fill:'none',stroke:colors[i%colors.length],'stroke-width':2,opacity:'.38',
        'stroke-linejoin':'round','stroke-linecap':'round'
      })));
      svg.appendChild(others);
    }
    function paint(){
      if(current!==serial||layer!==svg)return;
      while(svg.childNodes.length>(preview?2:1))svg.lastChild.remove();
      const w=valid[idx%valid.length];
      const points=coordsText(w);
      svg.appendChild(make('polyline',{points,fill:'none',stroke:'#ffcd36','stroke-width':'12','stroke-linejoin':'round','stroke-linecap':'round',filter:'url(#pantanalWinGlow)',opacity:'.9'}));
      svg.appendChild(make('polyline',{points,fill:'none',stroke:'#542905','stroke-width':'9','stroke-linejoin':'round','stroke-linecap':'round'}));
      svg.appendChild(make('polyline',{points,fill:'none',stroke:'#fff279','stroke-width':'5','stroke-linejoin':'round','stroke-linecap':'round'}));
      const winningCells=Array.isArray(w.cells)&&w.cells.length?
        w.cells.map(([col,row])=>[57.5+124*col,51+106*row]):
        lineCoords(w).slice(0,Math.min(w.count||0,5));
      for(const [x,y] of winningCells){
        svg.appendChild(make('circle',{cx:x,cy:y,r:12,fill:'#fff5b7',stroke:'#dd7617','stroke-width':4}));
      }
      const label=make('g');
      label.appendChild(make('rect',{x:5,y:5,width:220,height:31,rx:8,fill:'#251005',stroke:'#e2aa50','stroke-width':1.5,opacity:.94}));
      const t=make('text',{x:14,y:27,fill:'#fff1a0','font-size':20,'font-weight':'bold','font-family':'Arial,sans-serif'});
      t.textContent=preview?('LINHA '+w.line+' / '+valid.length):('LINHA '+w.line+'  +'+(Number(w.amount)||0));
      label.appendChild(t);svg.appendChild(label);
      idx++;
      if(idx>=(preview?valid.length:Math.min(valid.length,10))){
        if(timer!==null){clearInterval(timer);timer=null;}
        setTimeout(()=>{if(current===serial)clear()},1400);
      }
    }
    paint();
    if(valid.length>1)timer=setInterval(paint,preview?Math.max(160,Math.ceil(3800/valid.length)):1250);
    else setTimeout(()=>{if(current===serial)clear()},4400);
  }
  function previewLines(paths){
    const active=Array.isArray(paths)?paths:[];
    show(active.map((path,i)=>({path,line:i+1,count:0,amount:0})),true);
  }
  window.PantanalPreviewLines=previewLines;
  window.PantanalShowPaylines=show;
  window.PantanalClearPaylines=clear;
})();