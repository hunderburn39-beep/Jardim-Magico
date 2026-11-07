/* Jardim Mágico Phaser V3.1 — fila única para transições do jogo.
 * Exclusivamente agendamento; nenhum impacto na matemática ou saldo.
 */
const KINDS=['auto','free','hold'];
export function nextTurn({mode='base',autoLeft=0,freePaused=false,
 visible=true,writerReady=false,ready=false,modalOpen=false,
 running=false,mutating=false,holdBusy=false}={}){
 if(!visible||!writerReady||!ready||modalOpen||running||mutating||holdBusy)return null;
 if(mode==='hold')return 'hold';
 if(mode==='free')return freePaused?null:'free';
 return mode==='base'&&autoLeft>0?'auto':null;
}
export function createTurnQueue({schedule=setTimeout,cancel=clearTimeout}={}){
 const pending={auto:null,free:null,hold:null};
 function assertKind(kind){if(!KINDS.includes(kind))throw Error('Tipo de turno inválido')}
 function clear(kind){
  assertKind(kind);
  if(pending[kind]!==null)cancel(pending[kind]);
  pending[kind]=null;
 }
 function arm(kind,delay,callback){
  assertKind(kind);clear(kind);
  const id=schedule(()=>{
   if(pending[kind]!==id)return;
   pending[kind]=null;
   callback();
  },Math.max(0,Number(delay)||0));
  pending[kind]=id;
  return id;
 }
 function clearAll(){for(const kind of KINDS)clear(kind)}
 return Object.freeze({arm,clear,clearAll,pending:kind=>{assertKind(kind);return pending[kind]!==null}});
}
