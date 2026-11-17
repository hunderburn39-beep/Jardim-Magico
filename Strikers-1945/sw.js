const CACHE='strikers-1945-native-fullrom-v7.3';
const ASSETS=['./','./index.html','./icon.svg','./manifest.webmanifest','./original-rom.js','./stage1-partA.js','./stage1-partB.js','./stage1-background.js','./stage1-flight-replay.js','./stage1-plane-frames.b64','./inspector.html','./arcade.html','./remake.html','./audio-rom.html','./sprites-rom.html'];
const ROM_ROUTE=new URL('./roms/s1945.zip',self.registration.scope).pathname;
const DB='strikers1945-user-arcade-v1',STORE='files',KEY='s1945.zip';

async function romBytes(){
 return new Promise((resolve,reject)=>{
  const r=indexedDB.open(DB,1);
  r.onupgradeneeded=()=>{if(!r.result.objectStoreNames.contains(STORE))r.result.createObjectStore(STORE)};
  r.onerror=()=>reject(r.error);
  r.onsuccess=()=>{
   const db=r.result,t=db.transaction(STORE,'readonly'),request=t.objectStore(STORE).get(KEY);
   request.onsuccess=()=>{db.close();resolve(request.result)};
   request.onerror=()=>{db.close();reject(request.error)};
  };
 });
}
self.addEventListener('install',e=>{
 e.waitUntil(caches.open(CACHE).then(c=>c.addAll(ASSETS)).then(()=>self.skipWaiting()));
});
self.addEventListener('activate',e=>{
 e.waitUntil(caches.keys().then(keys=>Promise.all(keys.filter(k=>(k.startsWith('strikers-1945-html5-')||k.startsWith('strikers-1945-original-arcade-')||k.startsWith('strikers-1945-native-fullrom-'))&&k!==CACHE).map(k=>caches.delete(k)))).then(()=>self.clients.claim()));
});
self.addEventListener('fetch',e=>{
 const req=e.request,url=new URL(req.url);
 if(url.origin!==self.location.origin||req.method!=='GET')return;
 if(url.pathname===ROM_ROUTE){
  e.respondWith((async()=>{
   const bytes=await romBytes();
   if(!bytes||bytes.byteLength<3000000)return new Response('A ROM ainda não foi escolhida.',{status:404,headers:{'content-type':'text/plain'}});
   return new Response(bytes,{status:200,headers:{
    'content-type':'application/zip','content-disposition':'inline; filename="s1945.zip"',
    'cache-control':'no-store','accept-ranges':'none'
   }});
  })().catch(()=>new Response('Erro ao abrir a ROM privada.',{status:500})));
  return;
 }
 e.respondWith(fetch(req).then(res=>{
  if(res.ok){const clone=res.clone();e.waitUntil(caches.open(CACHE).then(c=>c.put(req,clone)).catch(()=>{}))}
  return res;
 }).catch(()=>caches.match(req).then(r=>r||new Response('Offline',{status:503}))));
});
