/* Jardim Mágico V81 — painel reduzido e histórico local de créditos fictícios.
   Reutiliza os controlos e os saldos do motor; não altera símbolos nem sorteios. */
(() => {
 'use strict';
 const GAME_KEY='jm_webgl_v20_funded', AUDIT_KEY='jm_admin_history_v81';
 const $=id=>document.getElementById(id), page=$('adminPage');
 if(!page||!page.querySelector('.adminRoot'))return;
 const read=key=>{try{return JSON.parse(localStorage.getItem(key)||'null')}catch(_){return null}};
 const state=()=>read(GAME_KEY)||{};
 const safe=v=>String(v??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 const money=c=>(Number(c||0)/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' €';
 const stamp=v=>{const d=new Date(v);return Number.isNaN(d.getTime())?'Sem data':d.toLocaleString('pt-PT');};
 const names={entrada:'Saldo inicial',deposito:'Depósito',levantamento:'Levantamento',levantamento_banqueiro:'Levantamento do operador',compra_hold_win:'Compra Hold & Win',compra_freespins:'Compra Free Spins',aposta:'Aposta',prémio:'Prémio',scatter:'SCATTER',premio_freespins_final:'Prémio Free Spins',prémio_hold_win:'Prémio Hold & Win',ativacao_6_moedas:'Ativação Hold & Win',ativacao_cofre:'Ativação do cofre',reserva_operador:'Alteração de reserva',partilha_banca:'Alteração de percentagens',capital_operador_entrada:'Fundo colocado',capital_operador_recuperado:'Fundo recuperado',admin_acesso:'Entrada no administrador',admin_saida:'Saída do administrador',admin_falha:'Acesso negado',modo_jogo:'Modo de jogo',frequencia_premios_normais:'Distribuição de prémios'};
 const modeName=n=>Number(n)===70?'Entretenimento':Number(n)===50?'Normal':Number(n)===30?'Prémios':'Configuração anterior';
 const root=page.querySelector('.adminRoot'),original=root.querySelector('.adminContent');
 const ui=document.createElement('section');ui.className='jm81Panel';ui.setAttribute('aria-label','Controlo simplificado');
 ui.innerHTML=`
  <p class="jm81Intro">Valores e operações em créditos fictícios. As percentagens do proprietário e do comerciante somam sempre 100%.</p>
  <div class="jm81Accounts">
   <article class="jm81Account"><h2>DONO DA MÁQUINA</h2>
    <label class="jm81FieldLabel" for="jm81OwnerPct">Percentagem do proprietário (%)</label>
    <div class="jm81Edit"><input id="jm81OwnerPct" type="number" min="0" max="100" step="1" inputmode="numeric"><button id="jm81SaveOwner" type="button">GUARDAR</button></div>
    <strong class="jm81Cash" id="jm81OwnerValue">0,00 €</strong></article>
   <article class="jm81Account"><h2>COMERCIANTE</h2>
    <label class="jm81FieldLabel" for="jm81MerchantPct">Percentagem do comerciante (%)</label>
    <div class="jm81Edit"><input id="jm81MerchantPct" type="number" min="0" max="100" step="1" inputmode="numeric"><button id="jm81SaveMerchant" type="button">GUARDAR</button></div>
    <strong class="jm81Cash" id="jm81MerchantValue">0,00 €</strong></article>
  </div>
  <p class="jm81Status" id="jm81SplitStatus" aria-live="polite"></p>
  <div class="jm81Section"><h2>MODO DE FUNCIONAMENTO</h2>
   <div class="jm81Modes" role="radiogroup" aria-label="Modo do jogo">
    <label class="jm81Mode"><input type="radio" name="jm81Mode" value="70"><span><strong>ENTRETENIMENTO</strong><small>70% para reserva da máquina, 30% para o orçamento teórico de prémios</small></span></label>
    <label class="jm81Mode"><input type="radio" name="jm81Mode" value="50"><span><strong>NORMAL</strong><small>50% para reserva, 50% para o orçamento teórico de prémios</small></span></label>
    <label class="jm81Mode"><input type="radio" name="jm81Mode" value="30"><span><strong>PRÉMIOS</strong><small>30% para reserva, 70% para o orçamento teórico de prémios</small></span></label>
   </div>
   <div class="jm81Actions"><button id="jm81SaveMode" type="button">GUARDAR MODO</button></div>
   <p class="jm81Status" id="jm81ModeStatus" aria-live="polite"></p>
   <p class="jm81Note">Estas percentagens repartem as apostas futuras entre reserva e orçamento de prémios. Não são RTP certificado nem garantia de ganhos ou perdas reais. O cofre e os bónus podem alterar o retorno observado.</p>
  </div>
  <div class="jm81Section"><h2>HISTÓRICO E RELATÓRIOS</h2>
   <div class="jm81Actions"><button type="button" id="jm81ShowHistory">HISTÓRICO</button><button type="button" id="jm81Pdf">ENVIAR RELATÓRIO PDF</button></div>
   <p class="jm81Status" id="jm81PdfStatus" aria-live="polite"></p>
   <div class="jm81History" id="jm81History" hidden><small id="jm81Count">0 ações</small>
    <div class="jm81HistoryTableWrap"><table><thead><tr><th>Data</th><th>Operação</th><th>Detalhe</th><th class="jm81Amount">Valor</th></tr></thead><tbody id="jm81Rows"></tbody></table></div>
    <button type="button" id="jm81More" hidden>VER MAIS REGISTOS</button>
   </div>
   <p class="jm81Note">Histórico guardado neste navegador, não num servidor. Uma pessoa com acesso ao dispositivo pode alterar ou apagar estes dados. Não substitui contabilidade ou auditoria de dinheiro real.</p>
  </div>`;
 root.insertBefore(ui,original);
 const ownerInput=$('jm81OwnerPct'),merchantInput=$('jm81MerchantPct'),splitStatus=$('jm81SplitStatus');
 const radios=()=>Array.from(ui.querySelectorAll('input[name="jm81Mode"]'));
 function audit(kind,detail='',amount=0){
  try{let events=read(AUDIT_KEY);if(!Array.isArray(events))events=[];
   events.push({time:new Date().toISOString(),kind,detail,amount:Number(amount)||0});
   localStorage.setItem(AUDIT_KEY,JSON.stringify(events));
  }catch(e){console.warn('Não foi possível guardar o histórico de administração',e);}
 }
 function currentPercent(){const n=Number(state().ownerPercent);return Number.isFinite(n)&&n>=0&&n<=100?n:60;}
 function updateValues(){
  if(page.hidden)return;
  $('jm81OwnerValue').textContent=$('adminSplitOwnerValue')?.textContent||money(0);
  $('jm81MerchantValue').textContent=$('adminSplitMerchantValue')?.textContent||money(0);
  const pct=Number(state().profitPercent);
  $('jm81ModeStatus').textContent='Atual: '+modeName(pct)+' — reserva da máquina '+(Number.isFinite(pct)?pct.toLocaleString('pt-PT'):'4')+'%';
 }
 function syncFull(){
  ownerInput.value=currentPercent();merchantInput.value=100-currentPercent();
  const profit=Number(state().profitPercent);
  radios().forEach(r=>r.checked=Number(r.value)===profit);
  updateValues();
 }
 function validPct(value){const n=Number(value);return String(value).trim()!==''&&Number.isInteger(n)&&n>=0&&n<=100?n:null;}
 function saveSplit(which){
  const n=validPct(which==='owner'?ownerInput.value:merchantInput.value);
  if(n===null){splitStatus.textContent='Escolhe uma percentagem inteira de 0% a 100%.';return;}
  const pct=which==='owner'?n:100-n;
  $('adminOwnerPercentInput').value=String(pct);
  $('adminMerchantPercentInput').value=String(100-pct);
  $('adminSaveSplit').click();
  const result=currentPercent();
  splitStatus.textContent=$('adminSplitStatus').textContent||'Não foi possível guardar.';
  if(result===pct)syncFull();
 }
 ownerInput.addEventListener('input',()=>{const n=validPct(ownerInput.value);if(n!==null)merchantInput.value=100-n});
 merchantInput.addEventListener('input',()=>{const n=validPct(merchantInput.value);if(n!==null)ownerInput.value=100-n});
 $('jm81SaveOwner').addEventListener('click',()=>saveSplit('owner'));
 $('jm81SaveMerchant').addEventListener('click',()=>saveSplit('merchant'));
 $('jm81SaveMode').addEventListener('click',()=>{
  const chosen=radios().find(r=>r.checked);if(!chosen){$('jm81ModeStatus').textContent='Escolhe primeiro um dos três modos.';return;}
  const old=Number(state().profitPercent),next=Number(chosen.value);
  $('adminProfitInput').value=String(next);$('adminProfitSlider').value=String(next);
  $('adminSaveProfit').click();
  if(Number(state().profitPercent)===next){
   if(old!==next)audit('modo_jogo','Alterado de '+modeName(old)+' ('+old+'%) para '+modeName(next)+' ('+next+'%)');
   syncFull();
   $('jm81ModeStatus').textContent+=' · Guardado para as próximas apostas.';
  }else $('jm81ModeStatus').textContent=$('adminProfitStatus').textContent||'Não foi possível guardar durante uma jogada.';
 });
 function collect(){
  const d=state(),out=[];
  for(const s of [...(Array.isArray(d.history)?d.history:[]),...(d.session?[d.session]:[])]){
   if(!s||!Array.isArray(s.events))continue;
   for(const e of s.events)out.push({time:e.time,kind:e.kind||'ação',detail:e.detail||'',amount:Number(e.amount)||0,session:s.id||''});
  }
  for(const e of (Array.isArray(d.adminAudit)?d.adminAudit:[])){
   out.push({time:e.time,kind:e.kind||'admin',detail:e.detail||('Anterior: '+(e.previous??'—')+' | Atual: '+(e.newValue??'—')+(e.party?' | '+e.party:'')),amount:Number(e.amount)||0,session:'admin'});
  }
  for(const e of (Array.isArray(read(AUDIT_KEY))?read(AUDIT_KEY):[])){
   out.push({time:e.time,kind:e.kind||'admin',detail:e.detail||'',amount:Number(e.amount)||0,session:'admin'});
  }
  return out.sort((a,b)=>(Date.parse(b.time)||0)-(Date.parse(a.time)||0));
 }
 let visible=150;
 function renderHistory(){
  const records=collect(),batch=records.slice(0,visible);
  $('jm81Count').textContent=records.length+' ações registadas neste navegador';
  $('jm81Rows').innerHTML=batch.length?batch.map(e=>'<tr><td>'+safe(stamp(e.time))+'</td><td>'+safe(names[e.kind]||String(e.kind||'Ação').replace(/_/g,' '))+'</td><td>'+safe(e.detail)+'</td><td class="jm81Amount">'+(e.amount?escapeSigned(e.amount):'—')+'</td></tr>').join(''):'<tr><td colspan="4">Ainda não existem movimentos.</td></tr>';
  $('jm81More').hidden=records.length<=visible;
 }
 function escapeSigned(n){return safe((n>0?'+':'')+money(n))}
 $('jm81ShowHistory').addEventListener('click',()=>{
  const panel=$('jm81History');panel.hidden=!panel.hidden;
  $('jm81ShowHistory').textContent=panel.hidden?'HISTÓRICO':'FECHAR HISTÓRICO';
  if(!panel.hidden){visible=150;renderHistory();}
 });
 $('jm81More').addEventListener('click',()=>{visible+=150;renderHistory();});
 const generatePdf=async()=>{
  const pdfStatus=$('jm81PdfStatus');
  if(!window.jspdf?.jsPDF){
   pdfStatus.textContent='Gerador de PDF indisponível. Usa o relatório da máquina e a opção Imprimir / Guardar PDF.';
   $('adminFullReport').click();return;
  }
  try{
   const {jsPDF}=window.jspdf,doc=new jsPDF({unit:'mm',format:'a4',compress:true});
   const items=collect(),d=state(),owner=currentPercent(),profit=Number(d.profitPercent)||4;
   let y=17;const left=14,max=182;
   function line(t,size=10,bold=false){
    doc.setFont('helvetica',bold?'bold':'normal');doc.setFontSize(size);
    const rows=doc.splitTextToSize(String(t),max);
    const h=(size<=9?4.8:6);
    for(const r of rows){if(y+h>285){doc.addPage();y=16;}doc.text(r,left,y);y+=h;}
   }
   line('JARDIM MAGICO — RELATORIO DE MOVIMENTOS',15,true);
   line('Emitido: '+stamp(new Date().toISOString())+' | CREDITOS FICTICIOS',9);
   line('Proprietario: '+$('jm81OwnerValue').textContent+' | '+owner+'%',10,true);
   line('Comerciante: '+$('jm81MerchantValue').textContent+' | '+(100-owner)+'%',10,true);
   line('Modo: '+modeName(profit)+' | reserva por aposta: '+profit+'%',10,true);
   line('Registos: '+items.length,9);
   line('ATENCAO: Registos locais nao auditados; nao comprovam pagamentos reais.',9);
   y+=4;line('DATA | OPERACAO | MONTANTE | DETALHE',10,true);
   for(const e of items){
    const label=names[e.kind]||String(e.kind||'Ação').replace(/_/g,' ');
    line(stamp(e.time)+' | '+label+' | '+(e.amount?money(e.amount):'—'),9,true);
    if(e.detail)line(e.detail,8);
   }
   const fileName='Jardim_Magico_Historico_'+new Date().toISOString().slice(0,10)+'.pdf';
   const blob=doc.output('blob');
   if(typeof File!=='undefined'&&navigator.share&&navigator.canShare){
    const file=new File([blob],fileName,{type:'application/pdf'});
    if(navigator.canShare({files:[file]})){
     try{await navigator.share({files:[file],title:'Relatório Jardim Mágico'});pdfStatus.textContent='Relatório enviado ou partilhado.';return;}
     catch(e){if(e?.name==='AbortError'){pdfStatus.textContent='Partilha cancelada.';return;}}
    }
   }
   doc.save(fileName);pdfStatus.textContent='Relatório PDF criado para guardar ou enviar.';
  }catch(e){console.error('Erro ao criar PDF',e);pdfStatus.textContent='Não foi possível criar o PDF neste dispositivo.';}
 };
 $('jm81Pdf').addEventListener('click',generatePdf);
 $('adminLoginSubmit')?.addEventListener('click',()=>{
  if(!page.hidden){audit('admin_acesso','Entrada autorizada');syncFull();}
  else audit('admin_falha','Tentativa de acesso não autorizada');
 });
 $('adminExit')?.addEventListener('click',()=>audit('admin_saida','Área de administração encerrada'));
 const observer=new MutationObserver(()=>{if(!page.hidden){syncFull();if(!$('jm81History').hidden)renderHistory();}});
 observer.observe(page,{attributes:true,attributeFilter:['hidden']});
 // O motor original regista apostas, depósitos, levantamentos, compras e prémios.
 // Estes eventos complementares registam interações não financeiras.
 for(const [id,title] of [['btnMusic','Controlo de música'],['btnSound','Controlo de som'],['betPlus','Aumentar aposta'],['betMinus','Diminuir aposta'],['btnHome','Botão início']]){
  $(id)?.addEventListener('click',()=>audit('interacao_jogo',title));
 }
 setInterval(()=>{if(!page.hidden){updateValues();if(!$('jm81History').hidden)renderHistory();}},1700);
})();