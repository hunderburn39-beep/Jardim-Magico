/* PIN local de demonstração V2.1. Não é autenticação de servidor. */
export const PIN_KEY='jm-phaser-demo-pin-v21';
const valid=p=>typeof p==='string'&&/^\d{6}$/.test(p);
const toHex=b=>Array.from(new Uint8Array(b),x=>x.toString(16).padStart(2,'0')).join('');
const fromHex=s=>Uint8Array.from(s.match(/../g),x=>parseInt(x,16));
export function createAdminPinLock({storage=globalThis.localStorage,cryptoProvider=globalThis.crypto,now=()=>Date.now()}={}){
 let expires=0;
 const check=()=>{if(!storage?.getItem||!storage?.setItem||!cryptoProvider?.getRandomValues||!cryptoProvider?.subtle?.deriveBits)throw Error('PIN indisponível neste navegador. Requer HTTPS e WebCrypto.')};
 const read=()=>{check();const raw=storage.getItem(PIN_KEY);if(raw===null)return null;
  let d;try{d=JSON.parse(raw)}catch{throw Error('PIN local danificado.')}
  if(d?.version!==1||!/^[0-9a-f]{32}$/.test(d.salt)||!/^[0-9a-f]{64}$/.test(d.hash)||
   !Number.isInteger(d.fails)||!Number.isFinite(d.blockUntil))throw Error('PIN local danificado.');
  return d;
 };
 const write=d=>storage.setItem(PIN_KEY,JSON.stringify(d));
 const hash=async(pin,salt)=>{const key=await cryptoProvider.subtle.importKey('raw',new TextEncoder().encode(pin),'PBKDF2',false,['deriveBits']);
  return toHex(await cryptoProvider.subtle.deriveBits({name:'PBKDF2',salt:fromHex(salt),iterations:180000,hash:'SHA-256'},key,256));
 };
 const install=async pin=>{if(!valid(pin))return {ok:false,message:'O PIN tem de ter 6 algarismos.'};
  try{check();const salt=toHex(cryptoProvider.getRandomValues(new Uint8Array(16)));
   write({version:1,salt,hash:await hash(pin,salt),fails:0,blockUntil:0});expires=now()+480000;return {ok:true};
  }catch(e){return {ok:false,message:String(e.message||e)}}
 };
 const verify=async pin=>{let d;try{d=read()}catch(e){return {ok:false,message:String(e.message||e)}}
  if(!d)return {ok:false,message:'O PIN ainda não foi criado.'};
  if(d.blockUntil>now())return {ok:false,message:'Bloqueado durante 45 segundos.'};
  if(!valid(pin))return {ok:false,message:'Introduz o PIN de 6 algarismos.'};
  try{const candidate=await hash(pin,d.salt);let mismatch=0;
   for(let i=0;i<64;i++)mismatch|=candidate.charCodeAt(i)^d.hash.charCodeAt(i);
   if(!mismatch){write({...d,fails:0,blockUntil:0});expires=now()+480000;return {ok:true}}
   const fails=d.fails+1;write({...d,fails:fails>=4?0:fails,blockUntil:fails>=4?now()+45000:0});
   expires=0;return {ok:false,message:fails>=4?'Quatro tentativas incorretas. Bloqueado por 45 segundos.':'PIN incorreto.'};
  }catch(e){expires=0;return {ok:false,message:String(e.message||e)}}
 };
 return Object.freeze({
  isConfigured:()=>read()!==null,unlocked:()=>expires>now(),
  touch:()=>{if(expires<=now())return false;expires=now()+480000;return true},
  lock:()=>{expires=0},
  create:async pin=>read()?{ok:false,message:'PIN já configurado.'}:install(pin),
  verify,
  change:async(oldPin,newPin)=>{if(!valid(newPin))return {ok:false,message:'Novo PIN inválido.'};
   const done=await verify(oldPin);return done.ok?install(newPin):done}
 });
}
