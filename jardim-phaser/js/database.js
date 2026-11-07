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
    merchantPct:Number.isFinite(Number(event.merchantPct))?Number(event.merchantPct):50};
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
    tx.objectStore('events').put(log);
   });
  }catch(e){this.lastError=safeErr(e);return false}
 }
 async history(){
  await this.pending.catch(()=>{});
  if(!this.database)return (this.readFallback()?.events||[]).slice().reverse();
  return new Promise((resolve,reject)=>{
   const tx=this.database.transaction('events','readonly');
   const req=tx.objectStore('events').getAll();
   req.onsuccess=()=>resolve(req.result.slice(-MAX_HISTORY).reverse());
   req.onerror=()=>reject(req.error);
  });
 }
 async exportData(){
  const state=await this.load();
  const history=await this.history();
  return {format:'jardim-magico-demo-v3',database:this.mode,
    exportedAt:new Date().toISOString(),state,history};
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
