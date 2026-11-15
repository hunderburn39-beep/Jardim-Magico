/* Faraó Eterno — servidor opcional de telemetria (Node.js 20+).
 * Armazenamento em disco persistente; apenas créditos de demonstração.
 * Configuração por ambiente: ver README. Nunca pôr tokens no GitHub.
 */
import http from 'node:http';
import {mkdir,writeFile,rename,readFile,readdir,appendFile} from 'node:fs/promises';
import path from 'node:path';
import {timingSafeEqual,randomUUID} from 'node:crypto';

const PORT=Number(process.env.PORT||8080);
const HOST=process.env.HOST||'0.0.0.0';
const ROOT=path.resolve(process.env.FARAO_DATA_DIR||'./data');
const allowedOrigins=new Set((process.env.FARAO_ALLOWED_ORIGINS||'https://hunderburn39-beep.github.io')
  .split(',').map(v=>v.trim()).filter(Boolean));
let keys;
try{keys=JSON.parse(process.env.FARAO_MACHINE_TOKENS_JSON||'{}');}
catch{throw Error('FARAO_MACHINE_TOKENS_JSON: JSON inválido');}
if(!keys||typeof keys!=='object'||Array.isArray(keys)||
  !Object.entries(keys).length||Object.entries(keys).some(([id,token])=>
   !/^[\w-]{8,64}$/.test(id)||typeof token!=='string'||token.length<16))
 throw Error('Configura FARAO_MACHINE_TOKENS_JSON com IDs e tokens únicos (>=16 caracteres)');
const adminToken=process.env.FARAO_ADMIN_TOKEN||'';
if(adminToken&&adminToken.length<16)throw Error('FARAO_ADMIN_TOKEN precisa de pelo menos 16 caracteres');

const safeId=id=>typeof id==='string'&&/^[\w-]{8,64}$/.test(id);
const int=n=>Number.isSafeInteger(n)&&n>=0;
const match=(a,b)=>{
 if(typeof a!=='string'||typeof b!=='string')return false;
 const x=Buffer.from(a),y=Buffer.from(b);
 return x.length===y.length&&timingSafeEqual(x,y);
};
const bearer=req=>(req.headers.authorization||'').replace(/^Bearer /i,'');
const authorizeMachine=req=>{
 const id=req.headers['x-machine-id'];
 if(!safeId(id)||!Object.hasOwn(keys,id)||!match(bearer(req),keys[id]))return null;
 return id;
};
const authorizeAdmin=req=>!!adminToken&&match(bearer(req),adminToken);
const json=(res,status,body)=>{
 res.writeHead(status,{'Content-Type':'application/json; charset=utf-8','Cache-Control':'no-store','X-Content-Type-Options':'nosniff'});
 res.end(JSON.stringify(body));
};
async function readBody(req){
 return await new Promise((resolve,reject)=>{
  let bytes=0,text='',failed=false;
  req.on('data',chunk=>{
   if(failed)return;
   bytes+=chunk.length;
   if(bytes>180000){failed=true;reject(Object.assign(Error('Excede 180 KB'),{status:413}));req.destroy();return;}
   text+=chunk.toString('utf8');
  });
  req.on('end',()=>{if(failed)return;try{resolve(JSON.parse(text));}catch{reject(Object.assign(Error('JSON inválido'),{status:400}));}});
  req.on('error',reject);
 });
}
const numbers=(obj,keys)=>!!obj&&typeof obj==='object'&&
 keys.every(k=>int(obj[k]));
function validSnapshot(data,id){
 if(!data||typeof data!=='object'||data.schema!==1||data.machineId!==id||
  !Number.isInteger(data.sequence)||data.sequence<0||
  !int(data.balanceU)||!int(data.periodNumber)||data.periodNumber<1||
  typeof data.recordedAt!=='string'||Number.isNaN(Date.parse(data.recordedAt))||
  !numbers(data.stats,['paid','free','stakeU','winU','features'])||
  !numbers(data.totals,['depositsU','withdrawalsU','stakesU','bonusesU','winsU',
   'techCollectedU','merchantCollectedU','prizeAddedU','prizeRemovedU'])||
  !numbers(data.funds,['prizeU','technicianDueU','merchantDueU'])||
  !Array.isArray(data.closedPeriods)||data.closedPeriods.length>24||
  !Array.isArray(data.audit)||data.audit.length>40||
  !Array.isArray(data.actions)||data.actions.length>20
 )return false;
 return true;
}
const snapshotFile=id=>path.join(ROOT,'machines',id+'.json');
async function storeSnapshot(id,body){
 const receivedAt=new Date().toISOString();
 const envelope={machineId:id,receivedAt,snapshot:body};
 const target=snapshotFile(id),temp=target+'.'+randomUUID()+'.tmp';
 await mkdir(path.dirname(target),{recursive:true});
 await mkdir(path.join(ROOT,'events'),{recursive:true});
 await writeFile(temp,JSON.stringify(envelope,null,2),{mode:0o600});
 await rename(temp,target);
 const daily=receivedAt.slice(0,10);
 await appendFile(path.join(ROOT,'events',id+'-'+daily+'.jsonl'),
  JSON.stringify({machineId:id,receivedAt,snapshot:body})+'\n',{mode:0o600});
 return receivedAt;
}
async function serve(req,res){
 try{
  const origin=req.headers.origin;
  if(origin){
   if(!allowedOrigins.has(origin)){json(res,403,{ok:false,error:'Origem não autorizada'});return;}
   res.setHeader('Access-Control-Allow-Origin',origin);
   res.setHeader('Vary','Origin');
   res.setHeader('Access-Control-Allow-Headers','Authorization, Content-Type, X-Machine-ID');
   res.setHeader('Access-Control-Allow-Methods','GET,POST,OPTIONS');
  }
  if(req.method==='OPTIONS'){res.writeHead(204);res.end();return;}
  const url=new URL(req.url||'/', 'http://localhost');
  if(url.pathname==='/api/v1/health'&&req.method==='GET'){
   const id=authorizeMachine(req);
   if(!id)return json(res,401,{ok:false,error:'Credenciais inválidas'});
   return json(res,200,{ok:true,machineId:id,serverTime:new Date().toISOString()});
  }
  if(url.pathname==='/api/v1/snapshots'&&req.method==='POST'){
   const id=authorizeMachine(req);
   if(!id)return json(res,401,{ok:false,error:'Credenciais inválidas'});
   const body=await readBody(req);
   if(!validSnapshot(body,id))return json(res,422,{ok:false,error:'Snapshot inválido'});
   const receivedAt=await storeSnapshot(id,body);
   return json(res,200,{ok:true,machineId:id,receivedAt});
  }
  if(url.pathname==='/api/v1/admin/machines'&&req.method==='GET'){
   if(!authorizeAdmin(req))return json(res,401,{ok:false,error:'Admin não autorizado'});
   await mkdir(path.join(ROOT,'machines'),{recursive:true});
   const files=(await readdir(path.join(ROOT,'machines'))).filter(f=>f.endsWith('.json'));
   const machines=[];
   for(const file of files){
    const data=JSON.parse(await readFile(path.join(ROOT,'machines',file),'utf8'));
    machines.push({machineId:data.machineId,receivedAt:data.receivedAt,
     sequence:data.snapshot?.sequence,periodNumber:data.snapshot?.periodNumber});
   }
   return json(res,200,{ok:true,machines});
  }
  const matchMachine=/^\/api\/v1\/admin\/machines\/([A-Za-z0-9_-]{8,64})$/.exec(url.pathname);
  if(matchMachine&&req.method==='GET'){
   if(!authorizeAdmin(req))return json(res,401,{ok:false,error:'Admin não autorizado'});
   try{return json(res,200,{ok:true,data:JSON.parse(await readFile(snapshotFile(matchMachine[1]),'utf8'))});}
   catch(err){if(err.code==='ENOENT')return json(res,404,{ok:false,error:'Máquina sem dados'});throw err;}
  }
  return json(res,404,{ok:false,error:'Rota desconhecida'});
 }catch(err){
  console.error('Faraó server error',err?.message||err);
  if(!res.headersSent)json(res,err.status||500,{ok:false,error:err.status?err.message:'Falha interna'});
  else if(!res.destroyed)res.end();
 }
}
http.createServer((req,res)=>{void serve(req,res);}).listen(PORT,HOST,()=>{
 console.log('Faraó servidor de telemetria iniciado em '+HOST+':'+PORT);
 console.log('Máquinas autorizadas: '+Object.keys(keys).length+' · dados em '+ROOT);
});
