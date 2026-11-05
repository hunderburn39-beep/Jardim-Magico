
/* V39: dynamically reserve space for the HOLD & WIN grid without scrolling. */
(()=>{
 const modal=document.getElementById('bonusModal');
 const dialog=modal?.querySelector('.bonusDialog');
 const grid=document.getElementById('bonusGrid');
 if(!modal||!dialog||!grid)return;
 let scheduled=false;
 const view=()=>{
  const v=window.visualViewport;
  return {width:v?.width||innerWidth,height:v?.height||innerHeight};
 };
 const measure=()=>{
  scheduled=false;
  if(!modal.classList.contains('open'))return;
  const v=view(),wide=v.width>v.height;
  document.documentElement.style.setProperty('--jm-visible-height',v.height+'px');
  const style=getComputedStyle(dialog);
  const border=(parseFloat(style.borderTopWidth)||0)+(parseFloat(style.borderBottomWidth)||0);
  const pad=(parseFloat(style.paddingTop)||0)+(parseFloat(style.paddingBottom)||0);
  const horizontal=(parseFloat(style.paddingLeft)||0)+(parseFloat(style.paddingRight)||0);
  const visible=Array.from(dialog.children).filter(e=>e!==grid && getComputedStyle(e).display!=='none');
  const maxH=v.height-8;
  let size;
  if(wide){
    const col=dialog.clientWidth-horizontal;
    const gap=parseFloat(style.columnGap)||0;
    const usableLeft=col/(1+0.7)-gap;
    size=Math.min(usableLeft,v.height-border-pad-8,500);
  }else{
    const heights=visible.reduce((n,e)=>n+e.getBoundingClientRect().height,0);
    const gap=(parseFloat(style.rowGap)||0)*visible.length;
    size=Math.min(v.width-25,dialog.clientWidth-horizontal,v.height-8-border-pad-heights-gap-6,470);
  }
  size=Math.max(58,Math.floor(size));
  dialog.style.setProperty('--jm-bonus-size',size+'px');
  dialog.dataset.bonusView=wide?'horizontal':'vertical';
 };
 const schedule=()=>{if(!scheduled){scheduled=true;requestAnimationFrame(measure);}};
 window.addEventListener('resize',schedule,{passive:true});
 window.addEventListener('orientationchange',schedule,{passive:true});
 if(window.visualViewport){visualViewport.addEventListener('resize',schedule,{passive:true});}
 const observer=new MutationObserver(schedule);
 observer.observe(modal,{attributes:true,attributeFilter:['class']});
 observer.observe(grid,{childList:true,subtree:false});
 observer.observe(document.getElementById('bonusFactors'),{childList:true,subtree:true,characterData:true});
 observer.observe(document.getElementById('bonusNote'),{childList:true,subtree:true,characterData:true});
 schedule();
})();
