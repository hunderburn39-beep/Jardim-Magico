
/* V38: fit to VisualViewport height, without page scrolling or clipping. */
(()=>{
 const game=document.querySelector('body > .game');
 if(!game)return;
 const viewport=()=>{let v=window.visualViewport;return {width:v? v.width:window.innerWidth,height:v? v.height:window.innerHeight,x:v? v.offsetLeft:0,y:v? v.offsetTop:0};};
 let queued=false,lastOrientation='';
 const fit=()=>{
  queued=false;
  const v=viewport();if(!v.width||!v.height)return;
  document.documentElement.style.setProperty('--jm-visible-height',v.height+'px');
  const orientation=v.width>v.height*1.12?'wide':'portrait';
  // O formato acompanha a rotação; depois o jogador pode alternar manualmente.
  if(orientation!==lastOrientation){
   lastOrientation=orientation;
   if(game.dataset.layout!==orientation)game.dataset.layout=orientation;
  }
  const wide=game.dataset.layout==='wide';
  // V78: não reduzir a largura antes de medir o conteúdo.
  // O CSS adapta a altura e o scale final garante que tudo cabe no VisualViewport.
  const width=wide?Math.min(v.width,1360):Math.min(v.width,505);
  game.style.width=width+'px';
  game.style.transform='none';
  game.style.left='0px';game.style.top='0px';
  const naturalHeight=Math.ceil(Math.max(game.scrollHeight,game.offsetHeight));
  // Pequena folga em baixo para a barra de gestos e para arredondamentos.
  const safeHeight=Math.max(100,v.height-3);
  const scale=Math.min(1,v.width/width,safeHeight/Math.max(1,naturalHeight));
  game.style.transform=`scale(${scale})`;
  game.style.left=(v.x+(v.width-width*scale)/2)+'px';
  game.style.top=(v.y+Math.max(0,(v.height-naturalHeight*scale)/2))+'px';
  game.dataset.fitScale=String(Math.round(scale*1000)/1000);
 };
 const schedule=()=>{if(!queued){queued=true;requestAnimationFrame(fit);}};
 window.addEventListener('resize',schedule,{passive:true});
 window.addEventListener('orientationchange',schedule,{passive:true});
 if(window.visualViewport){visualViewport.addEventListener('resize',schedule,{passive:true});visualViewport.addEventListener('scroll',schedule,{passive:true});}
 const ro=window.ResizeObserver?new ResizeObserver(schedule):null;
 if(ro)ro.observe(game);
 const mo=new MutationObserver(schedule);
 mo.observe(game,{attributes:true,attributeFilter:['data-layout']});
 schedule();
 requestAnimationFrame(()=>requestAnimationFrame(schedule));
})();
