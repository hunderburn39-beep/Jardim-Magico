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

/* V2.2: only one browser tab owns the IndexedDB writer lease at a time.
 * A single readwrite transaction checks or acquires the lease atomically.
 * With no IndexedDB there is no safe multi-tab writer; enter read-only.
 */
const WRITER_ID='active-writer-v22',LEASE_MS=22000;
export function leaseAvailable(record,token,now){
 return !record||record.owner===token||record.expiresAt<=now;
}
export function validRecoveryMarker(marker){
 return marker&&typeof marker==='object'&&
  typeof marker.id==='string'&&marker.id.length>=8&&marker.id.length<=128&&
  typeof marker.date==='string'&&marker.date.length<=40&&
  typeof marker.paid==='number'&&Number.isFinite(marker.paid)&&marker.paid>=0;
}

/* V1.9: validar ficheiros antes de alterar a base local existente.
 * Nunca aceitar JSON arbitrário como estado do jogo.
 */
const MAX_BACKUP_EVENTS=2500;
const finiteRange=(v,min,max)=>typeof v==='number'&&Number.isFinite(v)&&v>=min&&v<=max;
const validMoney=v=>finiteRange(v,0,1e12)&&
 Math.abs(v-Math.round(v*100)/100)<=Number.EPSILON*Math.max(1,v)*2;
const sameMoney=(a,b)=>Math.round(a*100)===Math.round(b*100);
const VALID_STAKES=[.05,.1,.2,.4,.6,.8,1,1.5,2,3,5,7.5,10];
export function validateSessionState(state){
 const s=state?.snapshot,b=state?.board;
 if(!s||typeof s!=='object')throw Error('A cópia não contém uma sessão válida.');
 if(!Array.isArray(b)||b.length!==5||b.some(col=>
   !Array.isArray(col)||col.length!==3||col.some(x=>!Number.isInteger(x)||x<0||x>11)))
  throw Error('O tabuleiro da cópia de segurança é inválido.');
 for(const key of ['balance','reserve','lastWin','freeTotal','freeAwarded']){
  if(!validMoney(s[key]))throw Error('Campo inválido: '+key);
 }
 if(!VALID_STAKES.includes(s.stake)||!Number.isInteger(s.spins)||s.spins<0||
    s.spins>1e9||!Number.isInteger(s.freeSpins)||s.freeSpins<0||s.freeSpins>60||
    !Number.isInteger(s.freeMultiplier)||s.freeMultiplier<1||s.freeMultiplier>1000||
    !['base','free','hold'].includes(s.mode))
  throw Error('Os contadores e o modo da cópia são inválidos.');
 const played=s.freePlayed??0;
 if(!Number.isInteger(played)||played<0||played>60||
    (s.mode==='free'&&(s.freeSpins<1||s.freeSpins+played>60||
     Math.round(s.freeTotal*s.freeMultiplier*100)>Math.round(s.reserve*100))))
  throw Error('A série de Free Spins não tem contadores ou cobertura válidos.');
 if(s.mode==='hold'){
  const h=s.hold;
  if(!h||!Array.isArray(h.grid)||h.grid.length!==16||
     h.grid.some(v=>!Number.isInteger(v)||![0,1,2,3,5,10,20].includes(v))||
     h.grid.filter(Boolean).length<6||h.grid.every(Boolean)||
     !Number.isInteger(h.tries)||h.tries<1||h.tries>3||
     !Number.isInteger(h.rounds)||h.rounds<0||h.rounds>1e9||
     !validMoney(h.total)||!sameMoney(h.total,h.grid.reduce((a,b)=>a+b,0)*s.stake)||
     h.total>s.reserve)
   throw Error('A grelha Hold da cópia é inválida.');
 }
 if(s.pendingVisual!==undefined&&s.pendingVisual!==null&&!validRecoveryMarker(s.pendingVisual))
  throw Error('Recibo de animação pendente inválido.');
 if(s.profile!==undefined&&!['premios','normal','entretenimento'].includes(s.profile))
  throw Error('O perfil guardado não é válido.');
 if(s.admin!==undefined){
  const p=s.admin?.ownerPct;
  if(!finiteRange(p,0,100)||
    (s.admin.merchantPct!==undefined&&(!finiteRange(s.admin.merchantPct,0,100)||
      !sameMoney(p+s.admin.merchantPct,100))))
   throw Error('As percentagens administrativas são inválidas.');
 }
 return state;
}
export function validateBackup(data){
 if(!data||typeof data!=='object'||data.format!=='jardim-magico-demo-v3')
  throw Error('Formato de cópia de segurança desconhecido.');
 const state=validateSessionState(data.state);
 if(!Array.isArray(data.history)||data.history.length>MAX_BACKUP_EVENTS)
  throw Error('O histórico da cópia excede o limite permitido.');
 const ids=new Set();
 for(const e of data.history){
  if(!e||typeof e!=='object'||typeof e.eventId!=='string'||
     e.eventId.length<1||e.eventId.length>128||ids.has(e.eventId)||
     typeof e.type!=='string'||e.type.length>80||
     typeof e.timestamp!=='string'||e.timestamp.length>48||!Number.isFinite(Date.parse(e.timestamp)))
   throw Error('O histórico contém eventos malformados ou duplicados.');
  ids.add(e.eventId);
  for(const key of ['wager','cost','paid','balance','reserve','amount']){
   if(e[key]!==undefined&&!validMoney(e[key]))
    throw Error('Valor histórico inválido em '+key);
  }
  if(e.ownerPct!==undefined&&!finiteRange(e.ownerPct,0,100))
   throw Error('Percentagem histórica inválida.');
  if(e.recoveryId!==undefined&&
    (typeof e.recoveryId!=='string'||e.recoveryId.length<8||
     e.recoveryId.length>128))
   throw Error('Identificador do recibo de recuperação inválido.');
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
 constructor(){this.database=null;this.mode='opening';this.pending=Promise.resolve();this.lastError='';this.writerToken=null;}
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
      event.profile:'normal',
    ...(['spin','hold-turn','hold-paid'].includes(event.type)&&
     typeof snapshot.pendingVisual?.id==='string'?
      {recoveryId:snapshot.pendingVisual.id}:{})};
  this.pending=this.pending.catch(()=>{}).then(()=>this.write(payload,log));
  return this.pending;
 }
 async write(payload,log){
  if(!this.database){
   this.lastError='IndexedDB necessário para impedir várias abas de gravar o mesmo saldo.';
   return false;
  }
  try{
   return await new Promise((resolve,reject)=>{
    const tx=this.database.transaction(['state','events'],'readwrite');
    let authorized=false;
    tx.oncomplete=()=>resolve(authorized);
    tx.onerror=()=>reject(tx.error);
    tx.onabort=()=>reject(tx.error||Error('Transação abortada'));
    const state=tx.objectStore('state');
    const request=state.get(WRITER_ID);
    request.onsuccess=()=>{
     const record=request.result;
     if(!this.writerToken||record?.owner!==this.writerToken||
      record.expiresAt<=Date.now()){
      this.lastError='Esta aba perdeu a autorização para escrever.';
      return;
     }
     authorized=true;
     state.put({id:STATE_ID,payload});
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
    };
   });
  }catch(e){this.lastError=safeErr(e);return false}
 }

 async acquireLease(token){
  await this.pending.catch(()=>{});
  if(!this.database||typeof token!=='string'||token.length<8)return false;
  return new Promise(resolve=>{
   const tx=this.database.transaction('state','readwrite');
   const state=tx.objectStore('state');
   const req=state.get(WRITER_ID);
   let acquired=false;
   tx.oncomplete=()=>{this.writerToken=acquired?token:null;resolve(acquired)};
   tx.onerror=()=>resolve(false);tx.onabort=()=>resolve(false);
   req.onsuccess=()=>{
    const now=Date.now();
    if(!leaseAvailable(req.result,token,now))return;
    state.put({id:WRITER_ID,owner:token,expiresAt:now+LEASE_MS});
    acquired=true;
   };
  });
 }
 async renewLease(token){
  if(!this.database||this.writerToken!==token)return false;
  return new Promise(resolve=>{
   const tx=this.database.transaction('state','readwrite');
   const state=tx.objectStore('state'),req=state.get(WRITER_ID);
   let active=false;
   tx.oncomplete=()=>{if(!active)this.writerToken=null;resolve(active)};
   tx.onerror=()=>resolve(false);tx.onabort=()=>resolve(false);
   req.onsuccess=()=>{
    if(req.result?.owner!==token||req.result.expiresAt<=Date.now())return;
    state.put({id:WRITER_ID,owner:token,expiresAt:Date.now()+LEASE_MS});
    active=true;
   };
  });
 }
 async releaseLease(token){
  await this.pending.catch(()=>{});
  if(!this.database||!token)return false;
  return new Promise(resolve=>{
   const tx=this.database.transaction('state','readwrite');
   const state=tx.objectStore('state'),req=state.get(WRITER_ID);
   let released=false;
   tx.oncomplete=()=>{if(this.writerToken===token)this.writerToken=null;resolve(released)};
   tx.onerror=()=>resolve(false);tx.onabort=()=>resolve(false);
   req.onsuccess=()=>{
    if(req.result?.owner===token){state.delete(WRITER_ID);released=true}
   };
  });
 }
 async checkpoint(snapshot,board){
  await this.pending.catch(()=>{});
  if(!this.database||!this.writerToken)return false;
  const payload={snapshot:copy(snapshot),board:copy(board),updatedAt:new Date().toISOString()};
  try{
   return await new Promise(resolve=>{
    const tx=this.database.transaction('state','readwrite');
    const state=tx.objectStore('state'),req=state.get(WRITER_ID);
    let ok=false;
    tx.oncomplete=()=>resolve(ok);
    tx.onerror=()=>resolve(false);tx.onabort=()=>resolve(false);
    req.onsuccess=()=>{
     if(req.result?.owner!==this.writerToken||req.result.expiresAt<=Date.now())return;
     state.put({id:STATE_ID,payload});ok=true;
    };
   });
  }catch{return false}
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
  await this.pending.catch(()=>{});
  let state=null,history=[];
  if(!this.database){
   state=this.readFallback();
   history=ordered(state?.events||[]).slice(0,MAX_HISTORY);
  }else{
   // A mesma transação readonly fixa o momento da leitura de saldo e eventos.
   const result=await new Promise((resolve,reject)=>{
    const tx=this.database.transaction(['state','events'],'readonly');
    const balance=tx.objectStore('state').get(STATE_ID);
    const moves=tx.objectStore('events').getAll();
    tx.oncomplete=()=>resolve({
     state:balance.result?.payload||null,
     history:ordered(moves.result||[]).slice(0,MAX_HISTORY)
    });
    tx.onerror=()=>reject(tx.error);
    tx.onabort=()=>reject(tx.error||Error('Leitura interrompida'));
   });
   state=result.state;history=result.history;
  }
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
   return {ok:false,message:'Só se pode restaurar com base IndexedDB e aba ativa.'};
  }
  try{
   await new Promise((resolve,reject)=>{
    const tx=this.database.transaction(['state','events'],'readwrite');
    tx.oncomplete=()=>resolve();
    tx.onerror=()=>reject(tx.error);
    tx.onabort=()=>reject(tx.error||Error('Transação abortada'));
    const state=tx.objectStore('state'),reader=state.get(WRITER_ID);
    reader.onsuccess=()=>{
     if(reader.result?.owner!==this.writerToken||
      reader.result.expiresAt<=Date.now())return tx.abort();
     state.put({id:STATE_ID,payload});
     const events=tx.objectStore('events');
     events.clear();
     for(const log of logs)events.put(log);
    };
   });
   return {ok:true,events:logs.length};
  }catch(e){this.lastError=safeErr(e);return {ok:false,message:this.lastError}}
 }
 async clear(){
  await this.pending.catch(()=>{});
  if(!this.database||!this.writerToken)return false;
  try{
   return await new Promise(resolve=>{
    const tx=this.database.transaction(['events','state'],'readwrite');
    const state=tx.objectStore('state'),req=state.get(WRITER_ID);
    let cleared=false;
    req.onsuccess=()=>{
     if(req.result?.owner!==this.writerToken||req.result.expiresAt<=Date.now())return tx.abort();
     tx.objectStore('events').clear();
     state.delete(STATE_ID); // preserve writer lease while the tab remains active
     cleared=true;
    };
    tx.oncomplete=()=>resolve(cleared);
    tx.onerror=()=>resolve(false);tx.onabort=()=>resolve(false);
   });
  }catch{return false}
 }
}
export const demoDB=new DemoDatabase();
