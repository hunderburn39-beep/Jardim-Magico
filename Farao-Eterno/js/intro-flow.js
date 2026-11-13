/* Faraó Eterno: capa, música contínua, e Free Spins com confirmação. */
(()=>{
'use strict';
const g=window.__faraoGame,stage=document.getElementById('stage'),modal=document.getElementById('modal');
if(!g||!stage||!modal)return;
const now=()=>performance.now();
g._introCoverVisible=true;
const style=document.createElement('style');
style.textContent=[
'#farao-opening{position:absolute;inset:0;z-index:30;display:flex;flex-direction:column;align-items:center;justify-content:space-between;cursor:pointer;touch-action:manipulation;text-align:center;color:#fce9b5;background:linear-gradient(180deg,#030810bb,#08102055 38%,#04060bed),url("assets/temple.webp") center/cover no-repeat,#0b111a;box-shadow:inset 0 0 100px #000c}',
'#farao-opening:before{content:"";position:absolute;inset:2%;border:2px solid #d4a14bad;box-shadow:inset 0 0 0 3px #120e09b0,0 0 24px #e9b95c50;pointer-events:none}',
'.farao-opening-head{z-index:1;padding:clamp(48px,10vh,95px) 10px 4px;text-shadow:0 3px 12px #000}',
'.farao-opening-kicker{font:700 clamp(9px,2.5vw,13px) Georgia;letter-spacing:.22em;color:#fbe1ab}',
'.farao-opening-title{font:900 clamp(31px,9vw,55px)/1.1 Georgia;letter-spacing:.07em;margin:15px 0 9px;color:#ffdc89;text-shadow:0 3px 1px #5a3412,0 0 22px #ba762f}',
'.farao-opening-sub{font:700 clamp(10px,2.6vw,15px) Georgia;letter-spacing:.15em;color:#f8e0aa}',
'.farao-opening-king{position:relative;z-index:1;width:55%;height:40%;display:flex;align-items:center;justify-content:center;filter:drop-shadow(0 0 20px #ecab5670)}',
'.farao-opening-king img{display:block;width:100%;height:100%;object-fit:contain}',
'.farao-opening-foot{z-index:1;width:100%;padding:8px 24px clamp(48px,11vh,100px)}',
'.farao-opening-enter{display:block;max-width:290px;margin:auto;padding:14px 18px;border:2px solid #f0cb74;border-radius:50px;background:linear-gradient(160deg,#a96f24,#6c3915 53%,#b58230);color:#fff5cb;box-shadow:0 0 23px #daa34b7d;font:900 clamp(15px,4vw,20px) Georgia;letter-spacing:.12em}',
'.farao-opening-foot small{display:block;margin-top:16px;color:#fff1c5;font:500 11px system-ui;text-shadow:0 2px 5px #000}',
'#farao-opening:focus-visible{outline:4px solid #7ff4dc;outline-offset:-7px}'
].join('\n');
document.head.appendChild(style);
const splash=document.createElement('div');
splash.id='farao-opening';splash.tabIndex=0;splash.setAttribute('role','button');
splash.setAttribute('aria-label','Entrar no Faraó Eterno');
splash.innerHTML='<div class="farao-opening-head"><span class="farao-opening-kicker">UM LEGADO DO ANTIGO EGITO</span><h1 class="farao-opening-title">FARAÓ ETERNO</h1><div class="farao-opening-sub">OS SEGREDOS DA ETERNIDADE</div></div>'+
'<div class="farao-opening-king"><img src="assets/king.webp" alt="Faraó dourado" draggable="false"></div>'+
'<div class="farao-opening-foot"><span class="farao-opening-enter">TOCAR PARA ENTRAR</span><small>A música acompanha-te durante todo o jogo</small></div>';
stage.appendChild(splash);
let entered=false;
function enter(){
 if(entered)return;
 entered=true;
 g.audio.init();
 g.audio.unlockMusic?.();
 g._introCoverVisible=false;
 if(!g.superseded)g.paused=false;
 g.audio.startMusic?.();
 splash.remove();
 if(g.feature&&g._welcome&&!g._welcomeShown)g.showFeatureWelcome?.();
}
splash.addEventListener('pointerdown',e=>{e.preventDefault();e.stopPropagation();enter();},{passive:false});
splash.addEventListener('click',e=>{e.stopPropagation();enter();});
splash.addEventListener('keydown',e=>{if(e.key==='Enter'||e.key===' '){e.preventDefault();enter();}});
// Os testes automáticos com ?qa= usam sessão isolada e têm de aceder aos
// controlos sem um ecrã de apresentação a interceptar os seus cliques.
// Quem joga normalmente continua a ver a capa até tocar nela.
if(new URLSearchParams(location.search).has('qa'))enter();
/* Autoplay quando permitido; em iOS Safari o primeiro toque desbloqueia o som. */
g.audio.init();
g.audio.startMusic?.();
const oldQueue=g.queueIntro.bind(g);
g.queueIntro=function(...args){
 oldQueue(...args);
 if(this.feature&&this._freeStarted&&this.feature.played>0)this._awaitBonusStart=true;
};
const oldStart=g.startSpin.bind(g);
g.startSpin=function(...args){
 if(this._introCoverVisible)return;
 if(this.feature&&this._awaitBonusStart&&this._autoDispatch)return;
 return oldStart(...args);
};
const oldAuto=g.autoSpin.bind(g);
g.autoSpin=function(...args){
 if(this._introCoverVisible||this._awaitBonusStart)return;
 return oldAuto(...args);
};
const oldAdvance=g.advanceStage.bind(g);
g.advanceStage=function(...args){
 if(this._introCoverVisible)return;
 // Um toque durante a rotação das Free Spins não salta os cinco tambores.
 // Uma Free Spin em curso não pode ser acelerada por toques repetidos;
 // EXCEÇÃO: o Safari pode tê-la pausado a meio. RETOMAR tem de chegar
 // ao motor para continuar exatamente os mesmos 5 tambores.
 if(this.feature&&this.spinning&&!this.paused)return;
 if(this.feature&&this._awaitBonusStart&&!this._welcome){
  if(this.spinning||this.ledger.payout)return;
  if(this.featureIntro&&now()<this.featureIntro.end)return;
  if(now()<this.nextActionAt)return;
  this._autoDispatch=false;
  return this.startSpin();
 }
 return oldAdvance(...args);
};
const oldSummary=g.showSummary.bind(g);
g.showSummary=function(...args){
 if(this.pendingSummary&&!this.spinning&&!this.ledger.payout&&now()>=this.nextActionAt)this._summaryRequested=true;
 oldSummary(...args);
 if(!this._summaryAck||modal.classList.contains('hidden'))return;
 const btn=document.getElementById('collect-bonus');
 if(!btn||btn.dataset.faraoSpinContinue)return;
 btn.dataset.faraoSpinContinue='true';btn.textContent='GIRAR';
 btn.setAttribute('aria-label','Voltar ao jogo e girar');
 const acknowledge=btn.onclick;
 btn.onclick=()=>{
  acknowledge?.();
  setTimeout(()=>{
   if(this.superseded||this._introCoverVisible||this.feature||this.spinning||!modal.classList.contains('hidden'))return;
   this.nextActionAt=Math.min(this.nextActionAt,now()-1);
   this.startSpin();
  },420);
 };
};
document.documentElement.dataset.faraoIntroFlow='1.0.0';
})();