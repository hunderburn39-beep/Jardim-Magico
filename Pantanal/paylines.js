/* Pantanal v2.11: linhas de prémio desenhadas a partir dos resultados reais.
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
    if(timer!==null){clearInterval(timer);timer=null;}
    if(layer){layer.remove();layer=null;}
  }
  function show(wins){
    clear();
    const reels=document.getElementById('reels');
    const game=document.getElementById('game');
    if(!reels||!game||!Array.isArray(wins))return;
    const valid=wins.filter(w=>Array.isArray(w.path)&&w.path.length>=5&&w.path.slice(0,5).every(v=>Number.isInteger(v)&&v>=0&&v<=2));
    if(!valid.length)return;
    const current=++serial;
    const svg=make('svg',{id:'pantanalPaylines',viewBox:'0 0 620 318',width:'620',height:'318','aria-hidden':'true'});
    svg.style.cssText='position:absolute;left:'+reels.offsetLeft+'px;top:'+reels.offsetTop+'px;width:'+reels.offsetWidth+'px;height:'+reels.offsetHeight+'px;z-index:9;pointer-events:none;overflow:visible';
    const defs=make('defs');
    const glow=make('filter',{id:'pantanalWinGlow',x:'-30%',y:'-100%',width:'160%',height:'300%'});
    glow.appendChild(make('feGaussianBlur',{stdDeviation:'3'}));
    defs.appendChild(glow);svg.appendChild(defs);
    game.appendChild(svg);layer=svg;
    const legacy=document.getElementById('lineOverlay');
    if(legacy)legacy.style.setProperty('display','none','important');
    let idx=0;
    function paint(){
      if(current!==serial||layer!==svg)return;
      while(svg.childNodes.length>1)svg.lastChild.remove();
      const w=valid[idx%valid.length];
      const points=w.path.slice(0,5).map((r,c)=>(57.5+124*c)+','+(51+106*r)).join(' ');
      svg.appendChild(make('polyline',{points,fill:'none',stroke:'#ffcd36','stroke-width':'12','stroke-linejoin':'round','stroke-linecap':'round',filter:'url(#pantanalWinGlow)',opacity:'.9'}));
      svg.appendChild(make('polyline',{points,fill:'none',stroke:'#542905','stroke-width':'9','stroke-linejoin':'round','stroke-linecap':'round'}));
      svg.appendChild(make('polyline',{points,fill:'none',stroke:'#fff279','stroke-width':'5','stroke-linejoin':'round','stroke-linecap':'round'}));
      for(let c=0;c<Math.min(w.count||0,5);c++){
        svg.appendChild(make('circle',{cx:57.5+124*c,cy:51+106*w.path[c],r:12,fill:'#fff5b7',stroke:'#dd7617','stroke-width':4}));
      }
      const label=make('g');
      label.appendChild(make('rect',{x:5,y:5,width:220,height:31,rx:8,fill:'#251005',stroke:'#e2aa50','stroke-width':1.5,opacity:.94}));
      const t=make('text',{x:14,y:27,fill:'#fff1a0','font-size':20,'font-weight':'bold','font-family':'Arial,sans-serif'});
      t.textContent='LINHA '+w.line+'  +'+(Number(w.amount)||0);
      label.appendChild(t);svg.appendChild(label);
      idx++;
      if(idx>=Math.min(valid.length,10)){
        if(timer!==null){clearInterval(timer);timer=null;}
        setTimeout(()=>{if(current===serial)clear()},1400);
      }
    }
    paint();
    if(valid.length>1)timer=setInterval(paint,1250);
    else setTimeout(()=>{if(current===serial)clear()},4400);
  }
  window.PantanalShowPaylines=show;
  window.PantanalClearPaylines=clear;
})();