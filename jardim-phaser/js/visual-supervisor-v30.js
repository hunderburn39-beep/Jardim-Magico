/* Jardim Mágico Phaser V3.0 — supervisão exclusivamente visual.
 * Uma interrupção do navegador termina a espera, sem recalcular os prémios.
 */
export function createVisualSupervisor({schedule=setTimeout,clear=clearTimeout}={}){
 let current=null;
 function run(start,recover,deadline=7000){
  if(current)throw Error('Transição visual concorrente');
  let resolveResult,timeout=null,done=false;
  const result=new Promise(resolve=>{resolveResult=resolve});
  const task={cancel:()=>finish('interrupted')};
  current=task;
  function finish(reason){
   if(done)return;
   done=true;
   if(timeout!==null)clear(timeout);
   if(current===task)current=null;
   if(reason!=='complete'){
    try{recover?.()}catch(e){/* falha visual não interfere com o resultado gravado */}
   }
   resolveResult({ok:reason==='complete',reason});
  }
  timeout=schedule(()=>finish('timeout'),Math.max(1,Number(deadline)||7000));
  // Começa numa microtask para que cancel() nunca inicie uma animação oculta.
  Promise.resolve().then(()=>{
   if(!done)return start();
  }).then(()=>{if(!done)finish('complete')},()=>finish('error'));
  return result;
 }
 return Object.freeze({
  run,
  cancel(){current?.cancel()},
  active(){return current!==null}
 });
}
