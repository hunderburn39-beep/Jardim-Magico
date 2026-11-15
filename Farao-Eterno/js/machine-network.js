/* Faraó Eterno — telemetria opcional e novas versões, só créditos virtuais.
   A máquina nunca obtém pagamentos, grelhas ou comandos de jogo do servidor.
   Servidor: API própria com HTTPS, tokens por máquina e disco persistente.
*/
(() => {
 'use strict';
 const game=window.__faraoGame;
 if(!game||!window.FaraoSession)return;
 const CONFIG_KEY='farao-machine-server-v1',ID_KEY='farao-machine-id-v1';
 const UPDATE_KEY='farao-auto-update-v1';
 const safeGet=(key)=>{try{return localStorage.getItem(key);}catch{return null;}};
 const safeSet=(key,value)=>{try{localStorage.setItem(key,value);return true;}catch{return false;}};
 const escapeHTML=value=>String(value).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 const uuid=()=>crypto.randomUUID?crypto.randomUUID():Array.from(crypto.getRandomValues(new Uint8Array(16)),v=>v.toString(16).padStart(2,'0')).join('');
 let machineId=safeGet(ID_KEY);
 if(!machineId||!/^[a-zA-Z0-9_-]{8,64}$/.test(machineId)){machineId='farao-'+uuid();safeSet(ID_KEY,machineId);}
 let config={url:'',token:''};
 try{const saved=JSON.parse(safeGet(CONFIG_KEY)||'{}');if(saved&&typeof saved==='object')config={url:String(saved.url||''),token:String(saved.token||'')};}catch{}
 let autoUpdate=safeGet(UPDATE_KEY)!=='false';
 let state='Sem servidor configurado',lastSent='',lastError='',lastCheck='';
 let uploading=false,updatePending=false,updateStamp=0,sendTimer=null;
 function endpoint(base){
  const url=new URL(base);
  if(url.username||url.password||url.search||url.hash)throw Error('Endereço de servidor inválido');
  const isLocal=['localhost','127.0.0.1','[::1]'].includes(url.hostname);
  if(url.protocol!=='https:'&&!(isLocal&&url.protocol==='http:'))
    throw Error('É necessário HTTPS (HTTP só para localhost)');
  return url.origin+url.pathname.replace(/\/$/,'');
 }
 function configured(){return !!(config.url&&config.token);}
 function headers(){return {'Authorization':'Bearer '+config.token,'X-Machine-ID':machineId};}
 async function checkConnection(){
  if(!configured()){state='Sem servidor configurado';return false;}
  state='A verificar ligação…';
  try{
   const r=await fetch(config.url+'/api/v1/health',{headers:headers(),cache:'no-store',signal:AbortSignal.timeout(8000)});
   if(!r.ok)throw Error('HTTP '+r.status+(r.status===401?' · token inválido':''));
   const payload=await r.json();
   if(payload.ok!==true)throw Error('Resposta inválida');
   state='Ligado ao servidor';lastError='';lastCheck=new Date().toLocaleString('pt-PT');return true;
  }catch(err){state='Sem ligação';lastError=String(err.message||err);return false;}
 }
 function snapshot(){
  const ledger=game.ledger||{},eco=ledger.economy||{},t=eco.totals||{};
  const list=Array.isArray(ledger.history)?ledger.history:[];
  return {
   schema:1,machineId,recordedAt:new Date().toISOString(),
   periodNumber:eco.periodNumber||1,sequence:ledger.sequence||0,
   balanceU:ledger.balanceU||0,
   stats:{...(ledger.stats||{})},
   totals:{...t},funds:{
    prizeU:eco.prizeU||0,technicianDueU:eco.techDueU||0,
    merchantDueU:eco.merchantDueU||0
   },
   settings:{profile:eco.level,technicianPercent:eco.technicianPercent,merchantPercent:eco.merchantPercent},
   closedPeriods:Array.isArray(eco.periods)?eco.periods.slice(0,24):[],
   actions:list.slice(0,20).map(h=>({
    id:h.id||null,kind:h.kind||'spin',at:h.t||null,
    stake:typeof h.bet==='number'?h.bet:0,win:typeof h.win==='number'?h.win:0,
    ...(typeof h.amount==='number'?{amount:h.amount}:{})
   })),
   audit:Array.isArray(eco.log)?eco.log.slice(0,40):[]
  };
 }
 async function sendNow(){
  if(!configured()||uploading||game.superseded)return false;
  uploading=true;
  try{
   const r=await fetch(config.url+'/api/v1/snapshots',{
    method:'POST',headers:{...headers(),'Content-Type':'application/json'},
    body:JSON.stringify(snapshot()),signal:AbortSignal.timeout(10000),cache:'no-store'
   });
   if(!r.ok)throw Error('HTTP '+r.status+(r.status===401?' · token inválido':''));
   const data=await r.json();
   if(data.ok!==true||data.machineId!==machineId)throw Error('Resposta de confirmação inválida');
   state='Ligado ao servidor';lastSent=new Date().toLocaleString('pt-PT');lastError='';
   return true;
  }catch(err){state='Sem ligação';lastError=String(err.message||err);return false;}
  finally{uploading=false;}
 }
 function scheduleUpload(){
  if(!configured()||sendTimer)return;
  sendTimer=setTimeout(()=>{sendTimer=null;void sendNow();},2400);
 }
 function configure(rawURL,rawToken){
  const url=endpoint(String(rawURL||'').trim());
  const token=String(rawToken||'').trim()||config.token;
  if(token.length<16||token.length>512)throw Error('Token do servidor: entre 16 e 512 caracteres');
  const next={url,token};
  if(!safeSet(CONFIG_KEY,JSON.stringify(next)))throw Error('O navegador não consegue guardar a ligação');
  config=next;state='Configuração guardada · por testar';lastError='';scheduleUpload();
 }
 function disconnect(){
  config={url:'',token:''};try{localStorage.removeItem(CONFIG_KEY);}catch{}
  state='Sem servidor configurado';lastError='';lastSent='';
 }
 const resources=doc=>[...doc.querySelectorAll('script[src],link[rel="stylesheet"][href]')]
    .map(node=>node.getAttribute('src')||node.getAttribute('href')||'')
    .filter(Boolean).join('|');
 const installedSignature=resources(document);
 async function checkForUpdate(){
  if(!autoUpdate)return false;
  try{
   // O GitHub Pages é o único fornecedor de código; o servidor de dados
   // não pode injetar JS nem mudar o destino da atualização.
   const home=new URL('./index.html',location.href);
   const req=new URL(home);req.searchParams.set('_check',Date.now());
   const r=await fetch(req,{cache:'no-store',signal:AbortSignal.timeout(9000)});
   if(!r.ok)throw Error('Não foi possível verificar nova versão');
   const html=await r.text();
   const newer=resources(new DOMParser().parseFromString(html,'text/html'));
   if(newer&&newer!==installedSignature){updatePending=true;updateStamp=Date.now();tryUpdate();}
   lastCheck=new Date().toLocaleString('pt-PT');
   return updatePending;
  }catch{return false;}
 }
 function idleToUpdate(){
  const modal=document.getElementById('modal');
  return !game.spinning&&!game.feature&&!game.ledger.pending&&!game.ledger.payout&&
   !game.sequencePlayback&&!game.pendingSummary&&!game.featureIntro&&
   !game.superseded&&(!modal||modal.classList.contains('hidden'));
 }
 async function tryUpdate(){
  if(!updatePending||!autoUpdate||!idleToUpdate())return;
  updatePending=false;
  try{game.save();}catch{}
  if(configured())await sendNow();
  // URL com parâmetro único impede servir o documento HTML antigo em cache.
  const url=new URL(location.href);
  url.searchParams.set('_updated',String(updateStamp||Date.now()));
  location.replace(url.href);
 }
 function adminHTML(){
  return '<h3 class="farao-admin-heading">Ligação ao servidor e atualizações</h3>'+
   '<p class="farao-admin-note">ID desta máquina: <b>'+escapeHTML(machineId)+'</b></p>'+
   '<p class="farao-admin-note" id="farao-network-status">'+escapeHTML(state)+'</p>'+
   '<label class="farao-admin-field" for="machine-server-url"><span>Servidor HTTPS (API)</span><input id="machine-server-url" type="url" placeholder="https://teu-servidor.exemplo" value="'+escapeHTML(config.url)+'" autocapitalize="off" spellcheck="false"></label>'+
   '<label class="farao-admin-field" for="machine-server-token"><span>Token de acesso da máquina</span><input id="machine-server-token" type="password" placeholder="'+(config.token?'Token guardado — deixa em branco para manter':'Token atribuído pelo servidor')+'" autocomplete="off" autocapitalize="off"></label>'+
   '<div class="farao-admin-actions">'+
   '<button type="button" class="btn farao-gold-btn" id="machine-connect">LIGAR / GUARDAR</button>'+
   '<button type="button" class="btn farao-gold-btn" id="machine-test">TESTAR LIGAÇÃO</button>'+
   '<button type="button" class="btn farao-gold-btn" id="machine-send">ENVIAR DADOS AGORA</button>'+
   '<button type="button" class="btn" id="machine-disconnect">DESLIGAR</button></div>'+
   '<label class="farao-admin-field" for="machine-auto-update"><span>Atualização automática do jogo (GitHub Pages)</span><input id="machine-auto-update" type="checkbox" '+(autoUpdate?'checked':'')+'></label>'+
   '<p class="farao-admin-note">Verificação de novas versões a cada 3 minutos; aplica quando não existir jogo, bónus ou janela aberta. Sem servidor configurado, os dados ficam apenas neste navegador.</p>'+
   '<p class="farao-admin-note" id="machine-last-check">Último envio: '+escapeHTML(lastSent||'nenhum')+' · Última verificação de versão: '+escapeHTML(lastCheck||'ainda não')+'</p>';
 }
 function bindAdmin(showError){
  const $=id=>document.getElementById(id);
  const statusEl=()=>$('farao-network-status');
  const paint=()=>{if(statusEl())statusEl().textContent=state+(lastError?' · '+lastError:'');
   const details=$('machine-last-check');if(details)details.textContent='Último envio: '+(lastSent||'nenhum')+' · Última verificação de versão: '+(lastCheck||'ainda não');
  };
  const protect=fn=>async()=>{
   if(!game._adminAuthenticated)return;
   try{await fn();paint();}catch(e){showError(e.message||String(e));}
  };
  $('machine-connect').onclick=protect(async()=>{
   configure($('machine-server-url').value,$('machine-server-token').value);
   if(await checkConnection())await sendNow();
  });
  $('machine-test').onclick=protect(async()=>{await checkConnection();});
  $('machine-send').onclick=protect(async()=>{if(!configured())throw Error('Primeiro configura o servidor');await sendNow();});
  $('machine-disconnect').onclick=protect(async()=>{
   if(!confirm('Desligar esta máquina do servidor? Os dados locais mantêm-se.'))return;
   disconnect();
  });
  $('machine-auto-update').onchange=()=>{
   autoUpdate=$('machine-auto-update').checked;
   safeSet(UPDATE_KEY,String(autoUpdate));
   if(autoUpdate)void checkForUpdate();
  };
 }
 window.FaraoMachineNetwork={machineId,configured,snapshot,configure,disconnect,checkConnection,sendNow,
  checkForUpdate,adminHTML,bindAdmin,getState:()=>({state,lastSent,lastError,lastCheck,autoUpdate})};
 const priorSave=game.save.bind(game);
 game.save=function(...args){const out=priorSave(...args);scheduleUpload();return out;};
 setInterval(()=>{if(configured()&&!document.hidden)void sendNow();},45000);
 setInterval(()=>{void checkForUpdate();},180000);
 setInterval(()=>{void tryUpdate();},10000);
 document.addEventListener('visibilitychange',()=>{
  if(!document.hidden){if(configured())void sendNow();void checkForUpdate();void tryUpdate();}
 });
 window.addEventListener('online',()=>{if(configured())void sendNow();});
 setTimeout(()=>{void checkForUpdate();if(configured())void sendNow();},8500);
})();
