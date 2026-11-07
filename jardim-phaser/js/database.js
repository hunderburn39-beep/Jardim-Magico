/* Jardim Mágico V3 — Base de dados local IndexedDB.
   Persistência transacional para demonstração de CRÉDITOS FICTÍCIOS.
   Não é servidor remoto, não recebe pagamentos nem dados sensíveis.
   Escreve saldo, reserva e registo de giros na mesma transação.
 */
const DB_NAME='jardim-magico-phaser-demo';
const DB_VERSION=1;
const STATE_ID='machine';
const FALLBACK='jardim-magico-phaser-v1-fallback';
const MAX_HISTORY=2500;

/* V1.9: validar ficheiros antes de alterar a base local existente.
 * Nunca aceitar JSON arbitrário como estado do jogo.
 */
const MAX_BACKUP_EVENTS=2500;
const finiteRange=(v,min,max)=>typeof v==='number'&&Number.isFinite(v)&&v>=min&&v<=max;
export function validateBackup(data){
 if(!data||typeof data!=='object'||data.format!=='jardim-magico-demo-v3')
  throw Error('Formato de cópia de segurança desconhecido.');
 const state=data.state;
 const s=state?.snapshot,b=state?.board;
 if(!s||typeof s!=='object')throw Error('A cópia não contém uma sessão válida.');
 if(!Array.isArray(b)||b.length!==5||b.some(col=>
   !Array.isArray(col)||col.length!==3||col.some(x=>!Number.isInteger(x)||x<0||x>11)))
  throw Error('O tabuleiro da cópia de segurança é inválido.');
 for(const key of ['balance','reserve','lastWin','freeTotal','freeAwarded']){
  if(!finiteRange(s[key],0,1e12))throw Error('Campo inválido: '+key);
 }
 if(!finiteRange(s.stake,.05,10)||!Number.isInteger(s.spins)||s.spins<0||
    s.spins>1e9||!Number.isInteger(s.freeSpins)||s.freeSpins<0||s.freeSpins>60||
    !Number.isInteger(s.freeMultiplier)||s.freeMultiplier<1||s.freeMultiplier>5||
    !['base','free','hold'].includes(s.mode))
  throw Error('Os contadores e o modo da cópia são inválidos.');
 if(s.mode==='hold'){
  const h=s.hold;
  if(!h||!Array.isArray(h.grid)||h.grid.length!==16||
     h.grid.some(v=>!finiteRange(v,0,1000))||
     !Number.isInteger(h.tries)||h.tries<0||h.tries>3||
     !finiteRange(h.total,0,1e12))
   throw Error('A grelha Hold da cópia é inválida.');
 }
 if(s.profile!==undefined&&!['premios','normal','entretenimento'].includes(s.profile))
  throw Error('O perfil guardado não é válido.');
 if(s.admin!==undefined){
  const p=s.admin?.ownerPct;
  if(!finiteRange(p,0,100))throw Error('As percentagens administrativas são inválidas.');
 }
 if(!Array.isArray(data.history)||data.history.length>MAX_BACKUP_EVENTS)
  throw Error('O histórico da cópia excede o limite permitido.');
 const ids=new Set();
 for(const e of data.history){
  if(!e||typeof e!=='object'||typeof e.eventId!=='string'||
     e.eventId.length<1||e.eventId.length>128||ids.has(e.eventId)||
     typeof e.type!=='string'||e.type.length>80||
     typeof e.timestamp!=='string'||e.timestamp.length>48)
   throw Error('O histórico contém eventos malformados ou duplicados.');
  ids.add(e.eventId);
  for(const key of ['wager','cost','paid','balance','reserve','amount']){
   if(e[key]!==undefined&&!finiteRange(e[key],0,1e12))
    throw Error('Valor histórico inválido em '+key);
  }
 }
 return {state:copy(state),history:copy(data.history)};
}
function ordered(events){
 return events.slice().sort((a,b)=>{
  const t=String(b.timestamp||'').localeCompare(String(a.timestamp||''));
  return t||String(b.eventId||'').localeCompare(String(a.eventId||''));
 });
}

function copy(o){return JSON.parse(JSON.stringify(o))}
const safeErr=e=>String(e?.message||e);
export class DemoDatabase{
 constructor(){this.database=null;this.mode='opening';this.pending=Promise.resolve();this.lastError='';}
 async connect(){
  if(typeof indexedDB==='undefined'){this.mode='localStorage';return this.mode}
  try{
   const db=await new Promise((resolve,reject)=>{
    const r=indexedDB.open(DB_NAME,DB_VERSION);
    r.onupgradeneeded=()=>{
     const d=r.result;
     if(!d.objectStoreNames.contains('state'))d.createObjectStore('state',{keyPath:'id'});
     if(!d.objectStoreNames.contains('events')){
      const e=d.createObjectStore('events',{keyPath:'eventId'});
      e.createIndex('timestamp','timestamp',{unique:false});
      e.createIndex('type','type',{unique:false});
     }
    };
    r.onsuccess=()=>resolve(r.result);
    r.onerror=()=>reject(r.error);
    r.onblocked=()=>reject(Error('Base de dados aberta noutra janela'));
   });
   db.onversionchange=()=>db.close();
   this.database=db;this.mode='indexedDB';return this.mode;
  }catch(e){
   this.lastError=safeErr(e);this.mode='localStorage';return this.mode;
  }
 }
 readFallback(){
  try{return JSON.parse(localStorage.getItem(FALLBACK)||'null')}catch{return null}
 }
 async load(){
  await this.pending.catch(()=>{});
  if(!this.database)return this.readFallback();
  return new Promise((resolve,reject)=>{
   const tx=this.database.transaction('state','readonly');
   const r=tx.objectStore('state').get(STATE_ID);
   r.onsuccess=()=>resolve(r.result?.payload||null);
   r.onerror=()=>reject(r.error);
  });
 }
 save(snapshot,board,event={}){
  const payload={snapshot:copy(snapshot),board:board?copy(board):null,
    updatedAt:new Date().toISOString()};
  const log={eventId:event.id||('ev-'+Date.now()+'-'+Math.random().toString(36).slice(2)),
    timestamp:payload.updatedAt,type:event.type||'update',
    stake:Number(event.stake||0),wager:Number(event.wager||0),
    cost:Number(event.cost||0),paid:Number(event.paid||0),
    spins:Number(snapshot.spins||0),balance:Number(snapshot.balance||0),
    reserve:Number(snapshot.reserve||0),stages:Number(event.stages||0),
    amount:Number(event.amount||0),note:String(event.note||'').slice(0,160),
    ownerPct:Number.isFinite(Number(event.ownerPct))?Number(event.ownerPct):50,
    merchantPct:Number.isFinite(Number(event.merchantPct))?Number(event.merchantPct):50,
    profile:['premios','normal','entretenimento'].includes(event.profile)?
      event.profile:'normal'};
  this.pending=this.pending.catch(()=>{}).then(()=>this.write(payload,log));
  return this.pending;
 }
 async write(payload,log){
  if(!this.database){
   try{
    const old=this.readFallback()||{events:[]};
    const events=(old.events||[]).slice(-(MAX_HISTORY-1));
    if(!events.some(x=>x.eventId===log.eventId))events.push(log);
    localStorage.setItem(FALLBACK,JSON.stringify({...payload,events}));
    return true;
   }catch(e){this.lastError=safeErr(e);return false}
  }
  try{
   return await new Promise((resolve,reject)=>{
    const tx=this.database.transaction(['state','events'],'readwrite');
    tx.oncomplete=()=>resolve(true);
    tx.onerror=()=>reject(tx.error);
    tx.onabort=()=>reject(tx.error||Error('Transação abortada'));
    tx.objectStore('state').put({id:STATE_ID,payload});
    const events=tx.objectStore('events');
    events.put(log);
    const count=events.count();
    count.onsuccess=()=>{
     if(count.result<=MAX_HISTORY+50)return;
     let excess=count.result-MAX_HISTORY;
     const cursor=events.index('timestamp').openCursor();
     cursor.onsuccess=()=>{
      const item=cursor.result;
      if(item&&excess>0){item.delete();excess--;item.continue()}
     };
    };
   });
  }catch(e){this.lastError=safeErr(e);return false}
 }
 async history(){
  await this.pending.catch(()=>{});
  if(!this.database)return ordered(this.readFallback()?.events||[]).slice(0,MAX_HISTORY);
  return new Promise((resolve,reject)=>{
   const tx=this.database.transaction('events','readonly');
   const req=tx.objectStore('events').getAll();
   req.onsuccess=()=>resolve(ordered(req.result).slice(0,MAX_HISTORY));
   req.onerror=()=>reject(req.error);
  });
 }
 async exportData(){
  const state=await this.load();
  const history=await this.history();
  return {format:'jardim-magico-demo-v3',database:this.mode,
    exportedAt:new Date().toISOString(),state,history};
 }
 async restoreData(backup){
  await this.pending.catch(()=>{});
  let checked;
  try{checked=validateBackup(backup)}
  catch(e){this.lastError=safeErr(e);return {ok:false,message:this.lastError}}
  const payload={...checked.state,updatedAt:new Date().toISOString()};
  const logs=ordered(checked.history).reverse();
  if(!this.database){
   try{
    localStorage.setItem(FALLBACK,JSON.stringify({...payload,events:logs}));
    return {ok:true,events:logs.length};
   }catch(e){this.lastError=safeErr(e);return {ok:false,message:this.lastError}}
  }
  try{
   await new Promise((resolve,reject)=>{
    const tx=this.database.transaction(['state','events'],'readwrite');
    tx.oncomplete=()=>resolve();
    tx.onerror=()=>reject(tx.error);
    tx.onabort=()=>reject(tx.error||Error('Transação abortada'));
    tx.objectStore('state').put({id:STATE_ID,payload});
    const events=tx.objectStore('events');
    events.clear();
    for(const log of logs)events.put(log);
   });
   return {ok:true,events:logs.length};
  }catch(e){this.lastError=safeErr(e);return {ok:false,message:this.lastError}}
 }
 async clear(){
  await this.pending.catch(()=>{});
  if(!this.database){
   try{localStorage.removeItem(FALLBACK);return true}catch{return false}
  }
  return new Promise((resolve,reject)=>{
   const tx=this.database.transaction(['events','state'],'readwrite');
   tx.objectStore('events').clear();
   tx.objectStore('state').clear();
   tx.oncomplete=()=>resolve(true);
   tx.onerror=()=>reject(tx.error);
  });
 }
}
export const demoDB=new DemoDatabase();
