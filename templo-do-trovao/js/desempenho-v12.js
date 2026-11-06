/* PORTÕES DO TROVÃO — V12: qualidade gráfica adaptativa
 * Mede apenas alguns frames enquanto se joga e reduz os efeitos
 * secundários se o dispositivo revelar falhas persistentes de fluidez.
 * Não lê dados privados nem modifica RNG, pagamentos ou velocidade da aposta.
 */
(()=>{
 "use strict";
 const app=document.getElementById("app");
 if(!app||typeof requestAnimationFrame!=="function")return;
 const reduced=window.matchMedia?.("(prefers-reduced-motion: reduce)")?.matches;
 if(reduced){app.classList.add("perf-low");return}
 let last=0,total=0,slow=0,windows=0;
 const MAX_WINDOWS=4;
 function tick(t){
   if(document.hidden){
     last=0;
     requestAnimationFrame(tick);
     return;
   }
   if(!app.classList.contains("is-spinning")){
     last=0;
     requestAnimationFrame(tick);
     return;
   }
   if(last){
     const dt=t-last;
     if(dt>0&&dt<250){
       total++;
       if(dt>27)slow++;
     }
   }
   last=t;
   if(total>=36){
     // Ocorre apenas após mais de um intervalo irregular.
     if(slow>=9){
       app.classList.add("perf-low");
       app.dataset.performanceMode="leve";
       return;
     }
     total=0;slow=0;
     windows++;
     if(windows>=MAX_WINDOWS){
       app.dataset.performanceMode="normal";
       return;
     }
   }
   requestAnimationFrame(tick);
 }
 requestAnimationFrame(tick);
})();
