/* PORTÕES DO TROVÃO V4 — cenário dinâmico independente do motor.
 * Camadas CSS de nuvens, templo, luz, faíscas e flashes atmosféricos.
 * Respeita preferências de redução de movimento e visibilidade do separador. */
(()=>{
 "use strict";
 const app=document.getElementById("app");
 const sparks=document.getElementById("sceneMotes");
 if(!app||!sparks)return;
 const reduced=window.matchMedia?.("(prefers-reduced-motion: reduce)");
 const mobile=window.matchMedia?.("(max-width: 850px)");
 let timer=0,clearFlash=0,raf=0,disposed=false;
 const rand=(min,max)=>min+Math.random()*(max-min);
 function populate(){
   sparks.replaceChildren();
   if(reduced?.matches)return;
   const total=mobile?.matches?13:27;
   const frag=document.createDocumentFragment();
   for(let i=0;i<total;i++){
     const mote=document.createElement("i");
     mote.className="scene-mote"+(i%6===0?" blue":"")+(i%7===0?" big":"");
     mote.style.setProperty("--mx",rand(3,98).toFixed(1)+"%");
     mote.style.setProperty("--my",rand(24,97).toFixed(1)+"%");
     mote.style.setProperty("--mxdrift",rand(-55,80).toFixed(1)+"px");
     mote.style.setProperty("--ms",rand(1.7,4.2).toFixed(1)+"px");
     mote.style.setProperty("--mt",rand(7.5,18).toFixed(2)+"s");
     mote.style.setProperty("--md",(-rand(0,20)).toFixed(2)+"s");
     frag.appendChild(mote);
   }
   sparks.appendChild(frag);
 }
 function burst(){
   if(disposed||document.hidden||reduced?.matches)return;
   app.classList.remove("storm-burst");
   // Reinicia o flash em explosões de vitória sem criar mais elementos.
   void app.offsetWidth;
   app.classList.add("storm-burst");
   clearTimeout(clearFlash);
   clearFlash=window.setTimeout(()=>app.classList.remove("storm-burst"),820);
 }
 function schedule(){
   clearTimeout(timer);
   if(disposed||reduced?.matches)return;
   timer=window.setTimeout(()=>{
     if(!document.hidden)burst();
     schedule();
   },rand(9000,16700));
 }
 function movePointer(event){
   if(mobile?.matches||reduced?.matches||document.hidden)return;
   if(raf)cancelAnimationFrame(raf);
   raf=requestAnimationFrame(()=>{
     const x=((event.clientX/window.innerWidth)-.5)*-12;
     const y=((event.clientY/window.innerHeight)-.5)*-8;
     app.style.setProperty("--sky-x",x.toFixed(2)+"px");
     app.style.setProperty("--sky-y",y.toFixed(2)+"px");
     raf=0;
   });
 }
 function visibility(){if(document.hidden){clearTimeout(timer);app.classList.remove("storm-burst")}else schedule()}
 function motionChange(){populate();schedule()}
 document.addEventListener("visibilitychange",visibility);
 document.addEventListener("pointermove",movePointer,{passive:true});
 reduced?.addEventListener?.("change",motionChange);
 mobile?.addEventListener?.("change",populate);
 window.addEventListener("pagehide",()=>{
   disposed=true;clearTimeout(timer);clearTimeout(clearFlash);
   if(raf)cancelAnimationFrame(raf);
 },{once:true});
 window.TrovaoScene=Object.freeze({
   burst,
   reactToWin(){if(Math.random()<.63)burst()}
 });
 populate();schedule();
})();