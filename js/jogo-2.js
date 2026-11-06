/* Jardim Mágico — motor PixiJS/WebGL com alternativa Canvas 2D.
 * Jogo recreativo; créditos, levantamentos e resultados do operador são virtuais.
 */
(() => {
'use strict';
const $=s=>document.getElementById(s), W=900,H=535,CW=W/5,CH=H/3;
// Apostas em CÊNTIMOS: 0,05 € a 10,00 € por spin.
const BETS=[5,10,20,40,60,80,100,200,300,500,1000];
const regular=['heart','rose','mushroom','hat','teapot','potion','gem','green'];
const allSymbols=[...regular,'wild','scatter','mul','stone'];
const IMGFILES={heart:'heart.png',rose:'rose.png',mushroom:'mushroom.png',hat:'hat.png',teapot:'teapot.png',potion:'potion.png',gem:'gem.png',green:'green.png',wild:'wild.png',scatter:'scatter.png',stone:'pedra-bloqueadora-v70.svg'};
const NAME={heart:'CORAÇÃO',rose:'ROSA',mushroom:'COGUMELO',hat:'CHAPÉU',teapot:'BULE',potion:'POÇÃO',gem:'GEMA ROXA',green:'GEMA VERDE',wild:'WILD',scatter:'SCATTER',mul:'MULTIPLICADOR',stone:'PEDRA'};
const PAY={heart:[0,0,0,6,18,50],rose:[0,0,0,6,19,55],mushroom:[0,0,0,5,16,48],hat:[0,0,0,7,21,60],teapot:[0,0,0,8,23,75],potion:[0,0,0,10,30,85],gem:[0,0,0,12,38,100],green:[0,0,0,11,35,90],wild:[0,0,10,15,45,130]};
const LINES=[
 [1,1,1,1,1],[0,0,0,0,0],[2,2,2,2,2],
 [0,1,2,1,0],[2,1,0,1,2],[0,0,1,0,0],
 [2,2,1,2,2],[1,2,2,2,1],[1,0,0,0,1],
 [0,2,2,2,0],[2,0,0,0,2],[0,1,0,1,0],
 [2,1,2,1,2],[1,1,0,1,1],[1,1,2,1,1]
];
const MAX_WIN_MULT=5000;
// V66 — WILD por posição: 1–5× por símbolo, no máximo 5× por linha;
// uma única linha pode usar cada WILD em cada queda, nunca mais de uma.
const MAX_WILD_LINE_FACTOR=5;
const MAX_FREE_RETRIGGERS=2; // Regra pública V65: duas extensões por sessão (até 45 jogadas se 3 SCATTER duas vezes).
const FREE_SCATTER_PROBABILITY=.018; // Probabilidade FIXA por posição nas free spins; independente do saldo/cofre.
const FREE_MUL_PROBABILITY=.009; // V66: bola MULTI com 0,9% por posição nas free spins.
const FREE_WILD_PROBABILITY=.034;
const FREE_COIN_PROBABILITY=.030;
// V70: símbolo sem valor, probabilidade fixa e independente dos saldos.
const STONE_PROBABILITY=.06;
const CASH_PIN='12345';
const PROTECTED_SHARE=0.25; // Parte intocável da parcela reservada em cada aposta.

const MULS=[2,5,10,15,20,25,50,100,250,500,1000];
const KEY='jm_webgl_v20_funded';
// Parametrização V34: jogo base + 3 SCATTER, free spins e Hold & Win avaliados separadamente por Monte Carlo independente.
// Nota: o RTP observado pode ser inferior ao previsto devido ao limite de pagamento do cofre. Não é RTP certificado.
const RTP_REFERENCE=96;
// Valores de escala ajustados por Monte Carlo para os pesos e as frequências V42.
// A percentagem do fundo não é RTP fixo: o retorno realizado depende dos resultados,
// do cofre, do tamanho das apostas e das contribuições progressivas.
// V65 — revisão de risco: as free spins V64 ultrapassavam o fundo esperado;
 // Hold & Win comprado custa 100× a aposta, diferente de bónus oferecidos.
 // Valores fixos e públicos; nunca variam com o lucro real da banca.
// V71: probabilidades fixas; coeficientes calibrados independentemente do fundo.
const RTP_CALIBRATION={base:0.57,free:0.068,hold:0.25,holdPurchase:1.33};
const FREQUENT_RTP_CALIBRATION={base:0.55,free:0.059};
// V66: coeficientes base/free calibrados em simulação com WILD fixo 1–5×;
// constantes ao longo da sessão, sem depender do saldo ou histórico da banca.
let autoSpinsTotal=0,rtpConfig=96,adminAudit=[],awardContext='base',adminUnlocked=false,adminTaps=0,adminTapDeadline=0,autoSpinsRemaining=0,freeStartPending=false,freeAutoRunning=false,freeAutoTimer=null,scatterNoteStep=0;
const configuredRtp=()=>RTP_REFERENCE; // Fixo; sem alteração na interface de administração.
let profitPercent=4, prizePool=0, operatorReserve=0, protectedReserve=0, ownerWithdrawn=0, protectedCarry=0, riskReserve=0, ownerCarry=0, poolCarry=0;
let fundInvested=0,fundRecovered=0;
const FUND_MIN_FACTOR=250,FUND_TARGET_FACTOR=1000;
// V59: a reserva livre é distribuída entre o proprietário e o estabelecimento.
// A reserva protegida, o cofre de prémios e os jackpots não são repartidos.
let ownerPercent=60,ownerShareReserve=0,merchantShareReserve=0,shareCarry=0;
let volatilityMode='equilibrado';
const configuredProfit=()=>Math.max(1,Math.min(70,Number(profitPercent))); // Aplica-se às próximas apostas, sem retroatividade.
const effectiveBudgetPercent=()=>Math.max(0,Math.min(configuredRtp(),100-configuredProfit()));
// Integer cents and fractional carry avoid systematically losing 1 cent on small bets.
// Invariant: each wager is split into the prize vault, operator reserve and risk buffer.
function fundWager(stake){
 stake=Math.max(0,Math.round(stake));
 const ownExact=stake*configuredProfit()/100+ownerCarry;
 let own=Math.min(stake,Math.floor(ownExact+1e-10));ownerCarry=ownExact-own;
 const poolExact=stake*effectiveBudgetPercent()/100+poolCarry;
 let toPool=Math.min(stake-own,Math.floor(poolExact+1e-10));poolCarry=poolExact-toPool;
 const buffer=stake-own-toPool;
 operatorReserve+=own;riskReserve+=buffer;prizePool+=toPool;
 const jpFunding=fundProgressive(stake,toPool);prizePool-=jpFunding;
  const protectedExact=own*PROTECTED_SHARE+protectedCarry;
  const locked=Math.min(own,Math.floor(protectedExact+1e-10));
  protectedCarry=protectedExact-locked;protectedReserve+=locked;
 // Apenas os créditos livres da reserva da banca entram na partilha.
 const distributable=own-locked;
 const exactOwner=distributable*ownerPercent/100+shareCarry;
 const ownerAdd=Math.min(distributable,Math.floor(exactOwner+1e-10));
 shareCarry=exactOwner-ownerAdd;
 ownerShareReserve+=ownerAdd;
 merchantShareReserve+=distributable-ownerAdd;
 if(session){session.poolFunded=(session.poolFunded||0)+toPool;session.jackpotFunded=(session.jackpotFunded||0)+jpFunding;session.operatorFunded=(session.operatorFunded||0)+own;session.bufferFunded=(session.bufferFunded||0)+buffer;}
 return {toPool,own,buffer};
}
/* V65: pagamentos SEMPRE inferiores aos fundos realmente disponíveis,
   sem tocar na reserva do proprietário/comerciante nem nos quatro jackpots.
   Rejeita NaN, números negativos e decimais inválidos. */
function vaultPayable(requested,bet=0){
 const available=Number.isFinite(prizePool)?Math.max(0,Math.floor(prizePool)):0;
 const value=Number(requested);
 if(!Number.isFinite(value)||value<=0)return 0;
 const cap=Number.isFinite(bet)&&bet>0?Math.min(available,Math.floor(bet*MAX_WIN_MULT)):available;
 return Math.min(cap,Math.round(value));
}
function payFromVault(requested){
 const amount=vaultPayable(requested);
 if(!amount)return 0;
 prizePool-=amount;balance+=amount;
 if(session){session.prizes+=amount;session.poolPaid=(session.poolPaid||0)+amount;}
 return amount;
}
function validateVault(){return Number.isInteger(prizePool)&&Number.isInteger(operatorReserve)&&Number.isInteger(protectedReserve)&&Number.isInteger(riskReserve)&&prizePool>=0&&operatorReserve>=protectedReserve&&protectedReserve>=0&&riskReserve>=0&&Object.values(progressivePools).every(n=>Number.isInteger(n)&&n>=0)}

const rtpScaleFor=kind=>{
 const payoutKind=kind==='hold'&&bonus?.origin==='compra'?'holdPurchase':kind;
 const calibration=(volatilityMode==='frequentes'&&(payoutKind==='base'||payoutKind==='free'))?FREQUENT_RTP_CALIBRATION:RTP_CALIBRATION;
  return (effectiveBudgetPercent()/RTP_REFERENCE)*(calibration[payoutKind]??RTP_CALIBRATION[payoutKind]??1);
};
const roundedPayout=value=>{if(!(value>0))return 0;let whole=Math.floor(value),fraction=value-whole;return whole+(Math.random()<fraction?1:0)};
const rtpLabel=value=>Number(value).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+'%';
// Valores máximos elegíveis por aposta; contadores exibem créditos progressivos, não vezes.
const JACKPOTS={ULTRA:{mult:5000,color:'#ffe18f'},MAX:{mult:1000,color:'#82caff'},MED:{mult:500,color:'#d48aff'},MIN:{mult:250,color:'#7bec88'}};
// Total 3% das apostas separado, antes de qualquer pagamento, do fundo de prémios.
// A reserva do operador é independente destes montantes.
const PROGRESSIVE_RATE={ULTRA:1.35,MAX:0.75,MED:0.50,MIN:0.40};
let progressivePools={ULTRA:0,MAX:0,MED:0,MIN:0};
let progressiveCarry={ULTRA:0,MAX:0,MED:0,MIN:0};
function fundProgressive(stake,toPool){
 let allocated=0;
 for(const type of Object.keys(JACKPOTS)){
  const exact=stake*PROGRESSIVE_RATE[type]/100+progressiveCarry[type];
  const cents=Math.max(0,Math.min(toPool-allocated,Math.floor(exact+1e-10)));
  progressiveCarry[type]=Math.max(0,exact-cents);
  progressivePools[type]+=cents;allocated+=cents;
 }
 return allocated;
}
function jackpotPayable(type,bet){
 return Math.max(0,Math.min(Math.floor(progressivePools[type]||0),bet*JACKPOTS[type].mult));
}
function payProgressive(type,bet,remainingLimit){
 const available=Math.max(0,Math.floor(Number(progressivePools[type])||0));
 const limit=Number.isFinite(remainingLimit)?Math.max(0,Math.floor(remainingLimit)):0;
 const awarded=Math.min(available,jackpotPayable(type,bet),limit);
 if(!awarded)return 0;
 progressivePools[type]-=awarded;balance+=awarded;
 if(session){session.prizes+=awarded;session.jackpotPaid=(session.jackpotPaid||0)+awarded;}
 return awarded;
}
function renderJackpotCounters(){
 for(const type of Object.keys(JACKPOTS)){
  const target=$('jp'+type);
  if(target){target.textContent=money(jackpotPayable(type,BETS[betIdx]));
   target.title=`Fundo acumulado ${money(progressivePools[type])} · teto de ${JACKPOTS[type].mult}× a aposta atual`;
  }
 }
}
let symbolSeq=0,vaultCoins=0,roundVaultNew=0;const countedCoins=new Set();
const money=c=>(c/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})+' €';
const nfmt=n=>n.toLocaleString('pt-PT');
const rand=(a,b)=>a+Math.random()*(b-a);
const sleep=ms=>new Promise(r=>setTimeout(r,ms));
const R=x=>Math.floor(Math.random()*x);
const pick=(arr)=>arr[R(arr.length)];
let betIdx=2,balance=0,lastPrize=0,mode='normal',running=false,freeSpins=0,freeBet=20,bonus=null,session=null,history=[],mute=false;
let freeBonusTotal=0,freeSummaryPending=false,freeSummaryCount=0;
let freeBaseTotal=0,freeMultiplierBalls=[],freeSettlePending=false,freeSettlementRunning=false,freeRetriggersUsed=0; // V54 — acumulação até ao fim
let vaultFictiveSum=0; // Valores exibidos nas moedas do porquinho; não entram nos prémios // V52 — fecho manual das free spins
let grid=Array.from({length:5},()=>Array.from({length:3},()=>({id:pick(regular)})));
let transitions=Array(5).fill(null),cascades=Array(5).fill(null),dropItems=[],burstParticles=[],lineGlow=[],floaters=[];
const claimedMultiplierCells=new Set(); // V55: recolhidos não permanecem visíveis nos rolos
let shelf=[],winRound=0,pixi=null,pixiSprites=[],nativeAssets={},nativeCtx=$('reelCanvas').getContext('2d'),tickerRunning=false,t0=performance.now();
let audioCtx=null, fxMaster=null, lastSchoolBellAt=-Infinity, musicEnabled=true, musicVolume=.25;
// V64: áudio original do utilizador, guardado em assets/sons para funcionar online.
let jm64BonusAudioBuffer=null,jm64BonusAudioLoading=null,jm64BonusSource=null;
// V40: os sons de SCATTER/suspense acompanham apenas os símbolos realmente parados.
let scatterSuspenseSequence=0;
// Som original enviado pelo utilizador: impacto suave de 0,36 s em cada tambor.
const REEL_LANDING_B64='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qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq//vUZAAP8AAAaQAAAAgAAA0gAAABAAABpAAAACAAADSAAAAETEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq//vUZAAP8AAAaQAAAAgAAA0gAAABAAABpAAAACAAADSAAAAETEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq//vUZAAP8AAAaQAAAAgAAA0gAAABAAABpAAAACAAADSAAAAETEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq//vUZAAP8AAAaQAAAAgAAA0gAAABAAABpAAAACAAADSAAAAETEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq//vUZAAP8AAAaQAAAAgAAA0gAAABAAABpAAAACAAADSAAAAETEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq//vUZAAP8AAAaQAAAAgAAA0gAAABAAABpAAAACAAADSAAAAETEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq';
let reelLandingBuffer=null, reelLandingDecode=null;
function preloadReelLanding(){
 if(!audioCtx || reelLandingBuffer || reelLandingDecode) return;
 try{
  const binary=atob(REEL_LANDING_B64), bytes=new Uint8Array(binary.length);
  for(let i=0;i<binary.length;i++)bytes[i]=binary.charCodeAt(i);
  reelLandingDecode=audioCtx.decodeAudioData(bytes.buffer)
   .then(buffer=>{reelLandingBuffer=buffer;})
   .catch(err=>{console.warn('Som de queda indisponível',err);});
 }catch(err){console.warn('Não foi possível carregar o som de queda',err)}
}
function soundReelLanding(){
 if(mute)return;
 if(!audioCtx || !reelLandingBuffer){audioFX('drop');return;}
 try{
  const source=audioCtx.createBufferSource(),volume=audioCtx.createGain();
  source.buffer=reelLandingBuffer;volume.gain.value=.28;
  source.connect(volume);volume.connect(audioCtx.destination);source.start();
 }catch(e){audioFX('drop')}
}


function save(){try{localStorage.setItem(KEY,JSON.stringify({balance,lastPrize,betIdx,mode,session,history,freeSpins,freeBet,freeBonusTotal,freeSummaryPending,freeSummaryCount,freeBaseTotal,freeMultiplierBalls,freeSettlePending,freeRetriggersUsed,vaultCoins,vaultFictiveSum,musicEnabled,musicVolume,rtpConfig,profitPercent,ownerPercent,ownerShareReserve,merchantShareReserve,shareCarry,volatilityMode,prizePool,operatorReserve,protectedReserve,protectedCarry,ownerWithdrawn,riskReserve,ownerCarry,poolCarry,progressivePools,progressiveCarry,adminAudit,fundInvested,fundRecovered,autoSpinsRemaining,autoSpinsTotal,bonus:bonus?{...bonus,inMotion:false}:null}));}catch(e){}}
function load(){try{const data=JSON.parse(localStorage.getItem(KEY)||'null');if(data){balance=Number(data.balance)||0;lastPrize=Number(data.lastPrize)||0;const savedBetIndex=Number(data.betIdx);betIdx=Number.isInteger(savedBetIndex)?Math.max(0,Math.min(BETS.length-1,savedBetIndex)):2;mode=data.mode==='turbo'?'turbo':'normal';session=data.session||null;history=Array.isArray(data.history)?data.history:[];freeSpins=Number(data.freeSpins)||0;freeBet=Number(data.freeBet)||20;freeBonusTotal=Math.max(0,Math.round(Number(data.freeBonusTotal)||0));freeSummaryPending=!!data.freeSummaryPending;freeSummaryCount=Math.max(0,Math.round(Number(data.freeSummaryCount)||0));freeBaseTotal=Math.max(0,Math.round(Number(data.freeBaseTotal)||0));freeMultiplierBalls=Array.isArray(data.freeMultiplierBalls)?data.freeMultiplierBalls.filter(v=>Number.isInteger(v)&&v>=2&&v<=1000):[];freeSettlePending=!!data.freeSettlePending;
 // Sem retirar spins já atribuídas em versões anteriores.
 // Quando não há contador V65, considera-se consumido o limite se o bónus antigo
 // já tiver mais de 30 ou 45 rodadas atribuídas.
 const legacyGranted=Math.max(0,freeSummaryCount+freeSpins-15);
 freeRetriggersUsed=Number.isFinite(Number(data.freeRetriggersUsed))
  ?Math.max(0,Math.min(MAX_FREE_RETRIGGERS,Math.floor(Number(data.freeRetriggersUsed))))
  :Math.min(MAX_FREE_RETRIGGERS,Math.ceil(legacyGranted/15));
 vaultCoins=Math.max(0,Number(data.vaultCoins)||0);vaultFictiveSum=Math.max(0,Math.round(Number(data.vaultFictiveSum)||0));bonus=([16,25,64].includes(data.bonus?.cells?.length))?{...data.bonus,inMotion:false}:null;autoSpinsRemaining=Math.max(0,Number(data.autoSpinsRemaining)||0);autoSpinsTotal=Math.max(autoSpinsRemaining,Number(data.autoSpinsTotal)||0);musicEnabled=data.musicEnabled!==false;musicVolume=Number.isFinite(Number(data.musicVolume))?Math.max(0,Math.min(1,Number(data.musicVolume))):.25;rtpConfig=RTP_REFERENCE;adminAudit=Array.isArray(data.adminAudit)?data.adminAudit:[];
 fundInvested=Math.max(0,Math.floor(Number(data.fundInvested)||0));
 fundRecovered=Math.max(0,Math.floor(Number(data.fundRecovered)||0));
 profitPercent=Number.isFinite(Number(data.profitPercent))?Math.max(1,Math.min(70,Number(data.profitPercent))):4;
ownerPercent=Number.isFinite(Number(data.ownerPercent))?Math.max(0,Math.min(100,Math.round(Number(data.ownerPercent)))):60;
volatilityMode=data.volatilityMode==='frequentes'?'frequentes':'equilibrado';
prizePool=Math.max(0,Math.floor(Number(data.prizePool)||0));operatorReserve=Math.max(0,Math.floor(Number(data.operatorReserve)||0));protectedReserve=Math.min(operatorReserve,Math.max(0,Math.floor(Number(data.protectedReserve??(operatorReserve*PROTECTED_SHARE)))));protectedCarry=Math.max(0,Number(data.protectedCarry)||0);ownerWithdrawn=Math.max(0,Math.floor(Number(data.ownerWithdrawn)||0));riskReserve=Math.max(0,Math.floor(Number(data.riskReserve)||0));
const unprotected=Math.max(0,operatorReserve-protectedReserve);
const savedOwner=Math.floor(Number(data.ownerShareReserve)),savedMerchant=Math.floor(Number(data.merchantShareReserve));
if(Number.isFinite(savedOwner)&&Number.isFinite(savedMerchant)&&savedOwner>=0&&savedMerchant>=0&&savedOwner+savedMerchant===unprotected){
 ownerShareReserve=savedOwner;merchantShareReserve=savedMerchant;
}else{
 // Migração transparente: reservas anteriores à V59 são divididas 60/40 (ou nova preferência).
 ownerShareReserve=Math.floor(unprotected*ownerPercent/100);
 merchantShareReserve=unprotected-ownerShareReserve;
}
shareCarry=Number.isFinite(Number(data.shareCarry))?Math.max(0,Math.min(.999999,Number(data.shareCarry))):0;
ownerCarry=Math.max(0,Number(data.ownerCarry)||0);poolCarry=Math.max(0,Number(data.poolCarry)||0);
for(const type of Object.keys(JACKPOTS)){
 progressivePools[type]=Math.max(0,Math.floor(Number(data.progressivePools?.[type])||0));
 progressiveCarry[type]=Math.max(0,Number(data.progressiveCarry?.[type])||0);
}
// Compatibilidade com Hold & Win que estava aberto numa versão antiga.
if(bonus){
 const map={MINI:'MIN',MEGA:'MED',MAJOR:'MAX',MAX:'ULTRA'};
 if(bonus.gems && ('MINI' in bonus.gems || 'MEGA' in bonus.gems || 'MAJOR' in bonus.gems)){
  const old=bonus.gems;bonus.gems={ULTRA:old.MAX||0,MAX:old.MAJOR||0,MED:old.MEGA||0,MIN:old.MINI||0};
  bonus.focus=map[bonus.focus]||'MIN';bonus.jackpot=map[bonus.jackpot]||null;
  for(const c of bonus.cells||[])if(c?.kind==='gem')c.type=map[c.type]||'MIN';
 }
}return true}}catch(e){}return false}
function log(kind,amount,detail=''){if(!session)return;session.events.push({time:new Date().toISOString(),kind,amount,detail,balance});save()}
function update(){
 $('balance').textContent=money(balance);$('lastPrize').textContent=money(lastPrize);$('betValue').textContent=money(BETS[betIdx]);renderJackpotCounters();
 const betLocked=running||!!bonus||freeSpins>0||freeSummaryPending||freeSettlePending||freeSettlementRunning;
 $('betMinus').disabled=betLocked||betIdx===0;$('betPlus').disabled=betLocked||betIdx>=BETS.length-1;
 document.querySelector('.betPanel').classList.toggle('locked',!!bonus||freeSpins>0);
 $('spin').disabled=freeSettlementRunning||(!freeSummaryPending&&(freeSettlePending||!!bonus||freeStartPending||freeAutoRunning||!session||(autoSpinsRemaining===0&&(running||(freeSpins<=0&&balance<BETS[betIdx])))));
 $('buyBonus').disabled=running||!!bonus||!session||freeSpins>0;
 $('buyBonus').textContent='✨ COMPRAR BÓNUS';
 const vaultText=vaultCoins+' MOEDAS',vaultWidth=Math.min(100,vaultCoins/40*100)+'%';
 $('vaultCount').textContent=vaultText;$('vaultFill').style.width=vaultWidth;$('vaultBtn').classList.toggle('ready',vaultCoins>=4);$('vaultBtn').classList.toggle('vaultFull',vaultCoins>=8);
 $('vaultBtn').style.setProperty('--vaultGrow',(1+Math.min(26,vaultCoins)*.022).toFixed(2));
 if($('vaultBtnMobile')){
  const pig=$('vaultBtnMobile');
  pig.title='Ganha até: '+money(vaultFictiveSum)+' (contador de moedas fictícias, não é prémio garantido)';
  let display=pig.querySelector('.jm52PigValue');
  if(!display){display=document.createElement('span');display.className='jm52PigValue';pig.append(display);}
  let caption=display.querySelector('.jm67PigCaption'),amount=display.querySelector('.jm67PigTotal');
  if(!caption||!amount){
   caption=document.createElement('small');caption.className='jm67PigCaption';caption.textContent='GANHA ATÉ:';
   amount=document.createElement('strong');amount.className='jm67PigTotal';
   display.replaceChildren(caption,amount);
  }
  amount.textContent=money(vaultFictiveSum);
  pig.setAttribute('aria-label','Porquinho, ganha até '+money(vaultFictiveSum)+' em créditos fictícios');
 }if($('vaultCountMobile'))$('vaultCountMobile').textContent=vaultText;if($('vaultFillMobile'))$('vaultFillMobile').style.width=vaultWidth;if($('vaultBtnMobile')){$('vaultBtnMobile').classList.toggle('ready',vaultCoins>=4);$('vaultBtnMobile').classList.toggle('vaultFull',vaultCoins>=8);$('vaultBtnMobile').style.setProperty('--vaultGrow',(1+Math.min(26,vaultCoins)*.022).toFixed(2));}
 refreshBuyOptions();
 $('withdrawBtn').disabled=running||!!bonus||freeSpins>0||!session||balance<=0;
 $('depositBtn').disabled=running||!!bonus||freeSpins>0;
 $('autoSpinBtn').disabled=!!bonus||!session;
 $('turboBtn').classList.toggle('active',mode==='turbo');$('turboBtn').setAttribute('aria-pressed',mode==='turbo'?'true':'false');$('turboBtn').textContent='⚡ TURBO '+(mode==='turbo'?'ON':'OFF');
 $('autoInfo').textContent=autoSpinsRemaining>0?'AUTO '+autoSpinsRemaining+' / '+autoSpinsTotal:(autoSpinsTotal>0?'VOLTAS CONCLUÍDAS':'5–100 VOLTAS');$('autoProgress').style.width=autoSpinsTotal>0?(Math.max(0,Math.min(100,(autoSpinsTotal-autoSpinsRemaining)/autoSpinsTotal*100)))+'%':'0%';
 $('freeTag').classList.toggle('show',freeSpins>0&&!freeSummaryPending);$('freeTag').textContent=freeSpins+' FREE SPINS'+(freeAutoRunning?' • AUTO':'');
 $('spin').querySelector('.cycle').textContent=freeSpins?'✦':'⟳';
 const autoActive=autoSpinsRemaining>0;$('spinCount').hidden=!autoActive;$('spinCount').textContent=autoActive?autoSpinsRemaining+' VOLTAS':'';$('spinText').textContent=freeSummaryPending?'CONTINUAR':autoActive?'PARAR AUTO':'RODAR';$('spin').classList.toggle('autoRunning',autoActive);
}
function notice(s){$('notice').textContent=s;}
function modal(id,open){$(id).classList.toggle('open',!!open)}
// V21: carregamentos só após autenticação local; validade de um único movimento.
let depositAuthorized=false;
function openDepositLogin(){
 if(running||bonus||freeSpins>0)return;
 depositAuthorized=false;$('depositPassword').value='';$('depositError').textContent='';
 $('depositLoginInfo').textContent=session?'Autoriza um carregamento adicional de créditos fictícios. O saldo atual será mantido.':'Autoriza o saldo inicial de créditos fictícios.';
 modal('depositLoginModal',true);$('depositPassword').focus();
}
function showDepositForm(){
 if(!depositAuthorized)return;
 modal('depositLoginModal',false);
 $('depositTitle').textContent=session?'✦ Adicionar créditos ✦':'✦ Bem-vindo ao Jardim Mágico ✦';
 $('depositInstructions').textContent=session?'Escolhe quantos créditos fictícios queres acrescentar à sessão atual.':'Escolhe os créditos fictícios com que queres começar esta sessão.';
 $('startBtn').textContent=session?'CONFIRMAR DEPÓSITO':'INICIAR JOGO';
 $('openingCredits').value=50;
 document.querySelectorAll('#choices button').forEach(b=>b.classList.toggle('selected',b.dataset.value==='50'));
 updateDepositPreview();modal('startModal',true);
}
function updateDepositPreview(){
 const n=Number($('openingCredits').value);$('depositConfirmPreview').textContent=Number.isFinite(n)&&n>0?(session?'Saldo atual: '+money(balance)+' · Adicionar: '+money(Math.round(n*100)):'Saldo inicial: '+money(Math.round(n*100))):'Indica um valor válido.';
}
function submitDeposit(){
 if(!depositAuthorized)return;
 if(running||bonus||freeSpins>0){notice('Não é possível depositar durante um bónus.');return;}
 const value=Number($('openingCredits').value);
 if(!Number.isFinite(value)||value<1||value>100000){alert('Indica um valor entre 1 e 100 000 créditos fictícios.');return;}
 const cents=Math.round(value*100);
 if(!Number.isSafeInteger(cents)||cents<=0){alert('Valor inválido.');return;}
 const question=session?`Confirmar depósito adicional de ${money(cents)}?\nSaldo após depósito: ${money(balance+cents)}`:`Confirmar saldo inicial de ${money(cents)}?`;
 if(!window.confirm(question))return;
 depositAuthorized=false;
 if(session){
  balance+=cents;session.deposits=(session.deposits||0)+cents;
  log('deposito',cents,'Carregamento adicional de créditos virtuais autorizado');
  modal('startModal',false);notice('Depósito autorizado: +'+money(cents));update();save();soundStart();
 }else freshSession(value);
}
function freshSession(credits){
 if(session){notice('Termina a sessão atual para começares outra.');return;}
 balance=Math.round(credits*100);lastPrize=0;freeSpins=0;freeBonusTotal=0;freeSummaryPending=false;freeSummaryCount=0;freeBaseTotal=0;freeMultiplierBalls=[];freeSettlePending=false;freeSettlementRunning=false;shelf=[];vaultCoins=0;vaultFictiveSum=0;session={id:Date.now(),start:new Date().toISOString(),initial:balance,deposits:0,rtpPercent:RTP_REFERENCE,profitPercent,ownerWithdrawn:0, poolFunded:0,poolPaid:0,operatorFunded:0,bufferFunded:0,stake:0,prizes:0,withdrawn:0,spins:0,bonuses:0,events:[]};
 log('entrada',balance,'Saldo inicial de créditos virtuais');modal('startModal',false);notice('Pronto para jogar. Boa sorte no jardim!');update();save();soundStart();
}
function doWithdraw(){if(!session||running||bonus||freeSpins>0)return;if($('withdrawPassword').value!==CASH_PIN){$('withdrawError').textContent='Palavra-passe incorreta. Usa a mesma do depósito.';$('withdrawPassword').value='';return;}const value=Number($('withdrawAmount').value);if(!Number.isFinite(value)||value<=0){alert('Indica um valor válido para levantar.');return;}const cents=Math.round(value*100);if(cents>balance){alert('O valor do levantamento não pode ser superior ao saldo disponível.');return;}balance-=cents;session.withdrawn=(session.withdrawn||0)+cents;log('levantamento',cents,'Levantamento virtual parcial');$('withdrawPassword').value='';$('withdrawError').textContent='';modal('withdrawModal',false);notice('Levantamento efetuado: -'+money(cents));if(balance<=0)notice('Saldo esgotado. Podes fazer depósito para continuar a jogar.');update();save()}
/* V59 — o banqueiro levanta exclusivamente a sua parcela disponível. */
function shareAvailable(party){
 const free=Math.max(0,ownerShareReserve+merchantShareReserve);
 const bank=bankerAvailable();
 if(free===0||bank===0)return 0;
 const first=Math.min(ownerShareReserve,Math.floor(bank*ownerShareReserve/free));
 const second=Math.min(merchantShareReserve,bank-first);
 return party==='comerciante'?second:first;
}
function doBankerWithdraw(){
 if(!adminUnlocked||!session||running||bonus||freeSpins>0||freeSettlePending||freeSummaryPending){
  $('bankerWithdrawStatus').textContent='Termina primeiro as jogadas ou os bónus.';return;
 }
 if($('bankerWithdrawPassword').value!==CASH_PIN){
  $('bankerWithdrawStatus').textContent='Palavra-passe incorreta. Usa a mesma do depósito.';$('bankerWithdrawPassword').value='';return;
 }
 const amount=Number($('bankerWithdrawAmount').value),cents=Math.round(amount*100);
 if(!Number.isFinite(amount)||!Number.isSafeInteger(cents)||cents<=0){
  $('bankerWithdrawStatus').textContent='Indica um valor válido.';return;
 }
 const party=$('adminWithdrawalRecipient').value==='comerciante'?'comerciante':'proprietario';
 const maximum=shareAvailable(party);
 if(cents>maximum){
  $('bankerWithdrawStatus').textContent='Disponível para '+(party==='comerciante'?'o comerciante':'o proprietário')+': '+money(maximum)+'. Não inclui os jackpots nem a reserva protegida.';
  return;
 }
 if(party==='comerciante'){
  merchantShareReserve-=cents;
  session.merchantWithdrawn=(session.merchantWithdrawn||0)+cents;
 }else{
  ownerShareReserve-=cents;
  session.ownerShareWithdrawn=(session.ownerShareWithdrawn||0)+cents;
 }
 operatorReserve-=cents;ownerWithdrawn+=cents;
 session.ownerWithdrawn=(session.ownerWithdrawn||0)+cents;
 const partyName=party==='comerciante'?'comerciante':'proprietário';
 log('levantamento_banqueiro',cents,'Levantamento do '+partyName+' (créditos fictícios)');
 adminAudit.push({time:new Date().toISOString(),kind:'levantamento_partilhado',party,amount:cents});
 $('bankerWithdrawPassword').value='';$('bankerWithdrawAmount').value='';
 $('bankerWithdrawStatus').textContent='Atribuídos ao '+partyName+': '+money(cents)+' de créditos fictícios. Fundos dos jackpots protegidos.';
 updateAdmin();save();
}
function maybeContinueAuto(){if(freeStartPending||freeAutoRunning||freeSpins>0||freeSettlePending||freeSettlementRunning||freeSummaryPending)return;if(autoSpinsRemaining>0&&!running&&!bonus&&session&&(freeSpins>0||balance>=BETS[betIdx])){setTimeout(()=>{if(autoSpinsRemaining>0&&!running&&!bonus&&session){autoSpinsRemaining--;update();doRound();}},mode==='turbo'?120:320);}else if(autoSpinsRemaining>0&&(!session||(!freeSpins&&balance<BETS[betIdx]))){autoSpinsRemaining=0;update();save();notice('Auto-spins terminadas.')}}
function stopAuto(){autoSpinsRemaining=0;autoSpinsTotal=0;update();save();modal('autoModal',false);}
function reportRows(){return [...history,...(session?[session]:[])];}
function totals(){const a=reportRows();return a.reduce((v,s)=>({initial:v.initial+(s.initial||0),deposits:v.deposits+(s.deposits||0),stakes:v.stakes+(s.stake||0),prizes:v.prizes+(s.prizes||0),withdrawn:v.withdrawn+(s.withdrawn||0),ownerWithdrawn:v.ownerWithdrawn+(s.ownerWithdrawn||0),ownerShareWithdrawn:v.ownerShareWithdrawn+(s.ownerShareWithdrawn||0),merchantWithdrawn:v.merchantWithdrawn+(s.merchantWithdrawn||0),balance:v.balance+(s.end?0:s.id===session?.id?balance:0),spins:v.spins+(s.spins||0)}),{initial:0,deposits:0,stakes:0,prizes:0,withdrawn:0,ownerWithdrawn:0,ownerShareWithdrawn:0,merchantWithdrawn:0,balance:0,spins:0})}
function cashMovements(){
 const rows=[];let order=0;
 const validMoney=n=>Number.isFinite(Number(n))?Math.max(0,Math.round(Number(n))):0;
 for(const s of reportRows()){
  const events=Array.isArray(s.events)?s.events:[];
  const sid=String(s.id??'—');let summedIn=0,summedOut=0;
  const old=[];
  for(const e of events){
   const kind=String(e.kind||'').toLowerCase();let inAmount=0,outAmount=0;
   if(kind==='entrada'||kind==='deposito'||kind==='depósito')inAmount=validMoney(e.amount);
   if(kind==='levantamento'||kind==='levantamento_banqueiro')outAmount=validMoney(e.amount);
   if(!inAmount&&!outAmount)continue;
   summedIn+=inAmount;summedOut+=outAmount;
   old.push({date:e.time||s.start||'',session:sid,type:inAmount?'Depósito':kind==='levantamento_banqueiro'?(String(e.detail||'').includes('comerciante')?'Levantamento comerciante':'Levantamento proprietário'):'Levantamento jogador',inAmount,outAmount,desc:String(e.detail||''),order:order++});
  }
  const expectedIn=validMoney(s.initial)+validMoney(s.deposits),expectedOut=validMoney(s.withdrawn)+validMoney(s.ownerWithdrawn);
  // Compatibilidade com versões antigas que guardavam apenas os totais da sessão.
  if(summedIn<expectedIn){old.unshift({date:s.start||'',session:sid,type:'Depósito histórico',inAmount:expectedIn-summedIn,outAmount:0,desc:'Movimento agregado de sessão anterior; hora individual indisponível',order:order++});}
  if(summedOut<expectedOut){old.push({date:s.end||s.start||'',session:sid,type:'Levantamento histórico',inAmount:0,outAmount:expectedOut-summedOut,desc:'Movimento agregado de sessão anterior; hora individual indisponível',order:order++});}
  rows.push(...old);
 }
 rows.sort((a,b)=>{const at=Date.parse(a.date),bt=Date.parse(b.date);if(Number.isFinite(at)&&Number.isFinite(bt)&&at!==bt)return at-bt;return a.order-b.order;});
 let cash=0;
 for(const r of rows){cash+=r.inAmount-r.outAmount;r.cash=cash;}
 return rows;
}
function bankerAvailable(){return Math.max(0,Math.min(operatorReserve-protectedReserve,reportFinancials().ownerAvailable));}
function reportFinancials(){
 const t=totals();const deposited=t.initial+t.deposits;const withdrawn=t.withdrawn;const inVault=deposited-withdrawn-t.ownerWithdrawn;
 const playerObligations=t.balance;const ownerAvailable=Math.max(0,Math.min(inVault-playerObligations,operatorReserve-protectedReserve));
 return {t,deposited,withdrawn,inVault,playerObligations,ownerAvailable};
}
function escapeReport(v){return String(v??'').replace(/[&<>"']/g,ch=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[ch]));}
function reportDate(raw){const d=new Date(raw);return Number.isNaN(d.getTime())?'Sem data':d.toLocaleString('pt-PT',{dateStyle:'short',timeStyle:'short'});}
function formatSignedCents(c){return (c<0?'−':'')+money(Math.abs(c));}
function buildReportHTML(){
 const {t,deposited,withdrawn,inVault,playerObligations,ownerAvailable}=reportFinancials();
 const movements=cashMovements();const generated=reportDate(new Date().toISOString());
 const rowHtml=movements.length?movements.map((r,i)=>`<tr><td class="index">${i+1}</td><td>${escapeReport(reportDate(r.date))}</td><td>${escapeReport(r.session)}</td><td class="in">${r.inAmount?money(r.inAmount):'—'}</td><td class="out">${r.outAmount?money(r.outAmount):'—'}</td><td class="cash">${formatSignedCents(r.cash)}</td></tr>`).join(''):'<tr><td colspan="6" class="empty">Sem depósitos ou levantamentos registados.</td></tr>';
 const fundRows=adminAudit.filter(e=>e.kind==='capital_operador_entrada'||e.kind==='capital_operador_recuperado')
  .map((e,i)=>'<tr><td>'+(i+1)+'</td><td>'+escapeReport(reportDate(e.time))+'</td><td>'+(e.kind==='capital_operador_entrada'?'Colocado':'Recuperado')+'</td><td>'+money(Math.max(0,Math.floor(Number(e.amount)||0)))+'</td><td>'+money(Math.max(0,Math.floor(Number(e.before)||0)))+'</td><td>'+money(Math.max(0,Math.floor(Number(e.after)||0)))+'</td></tr>')
  .join('')||'<tr><td colspan="6">Sem movimentos de capital do operador.</td></tr>';
 const explanatory='O valor disponível para o operador desconta os saldos de jogadores e respeita a parcela reservada e protegida. Os fundos progressivos e o cofre de prémios não são retirados pelo banqueiro. Este é um registo local de créditos fictícios, não lucro garantido.';
 return `<!doctype html><html lang="pt-PT"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Relatório de Caixa — Jardim Mágico — ${escapeReport(generated)}</title><link rel="stylesheet" href="css/estilo-3.css"></head><body><div class="tools"><button type="button" onclick="window.print()">🖨️ Imprimir / Guardar PDF</button><button type="button" class="alt" onclick="window.close()">Fechar</button></div><main class="sheet"><div class="header"><div><h1>JARDIM MÁGICO</h1><div class="sub">Relatório de depósitos, levantamentos e cofre</div><div class="meta">Emitido em ${escapeReport(generated)}</div></div><div class="tag">CRÉDITOS FICTÍCIOS</div></div><div class="contents"><div class="cards"><div class="metric deposits"><span class="label">Total depositado</span><b>${money(deposited)}</b></div><div class="metric withdrawals"><span class="label">Total levantado</span><b>${money(withdrawn+t.ownerWithdrawn)}</b></div><div class="metric vault"><span class="label">Dinheiro em cofre</span><b>${formatSignedCents(inVault)}</b></div></div><h2 class="section-title">Registo de movimentos</h2><div class="table-scroll"><table><thead><tr><th>N.º</th><th>Data</th><th>Sessão</th><th>Depósito</th><th>Levantamento</th><th>Dinheiro em cofre</th></tr></thead><tbody>${rowHtml}</tbody></table></div><div class="totals"><h2>Resumo final do operador</h2><div class="total-row"><span>Total de depósitos</span><b>${money(deposited)}</b></div><div class="total-row"><span>Levantamentos dos jogadores</span><b>${money(withdrawn)}</b></div><div class="total-row"><span>Levantamentos da banca (total)</span><b>${money(t.ownerWithdrawn)}</b></div><div class="total-row"><span>Do proprietário</span><b>${money(t.ownerShareWithdrawn)}</b></div><div class="total-row"><span>Do comerciante</span><b>${money(t.merchantWithdrawn)}</b></div><div class="total-row highlight"><span>Dinheiro total em cofre</span><b>${formatSignedCents(inVault)}</b></div><div class="total-row"><span>Saldos ainda disponíveis aos jogadores</span><b>${money(playerObligations)}</b></div><div class="total-row owner"><span>Fundo líquido disponível para o dono</span><b>${formatSignedCents(ownerAvailable)}</b></div><div class="total-row"><span>Fundo de prémios atual</span><b>${money(prizePool)}</b></div><div class="total-row"><span>Capital do operador colocado</span><b>${money(fundInvested)}</b></div><div class="total-row"><span>Capital do operador recuperado</span><b>${money(fundRecovered)}</b></div><div class="total-row"><span>Capital ainda por recuperar</span><b>${money(Math.max(0,fundInvested-fundRecovered))}</b></div></div><h2 class="section-title">Movimentos do fundo de prémios (separados dos jogadores)</h2><div class="table-scroll"><table><thead><tr><th>N.º</th><th>Data</th><th>Operação</th><th>Montante</th><th>Fundo antes</th><th>Fundo depois</th></tr></thead><tbody>${fundRows}</tbody></table></div><p class="footer">Cálculos: cofre = depósitos − levantamentos dos jogadores − levantamentos da Banca; fundo líquido = cofre − saldos por levantar.<br>${escapeReport(explanatory)}<br>Este relatório reúne apenas os movimentos guardados neste navegador. Não constitui prova de dinheiro físico ou registo contabilístico certificado.</p><p class="meta right">Sessões: ${reportRows().length} · Jogadas: ${t.spins}</p></div></main></body></html>`;
}
// V26: o Safari não precisa de permitir pop-ups; o relatório completo abre num quadro interno.
function openReportViewer(){
 if(!adminUnlocked)return;
 const viewer=$('reportViewer'),frame=$('reportFrame');
 frame.srcdoc=buildReportHTML().replace('</style>','.tools{display:none!important}</style>');
 viewer.hidden=false;
 document.body.style.overflow='hidden';
}
function closeReportViewer(){
 $('reportViewer').hidden=true;
 $('reportFrame').removeAttribute('srcdoc');
 document.body.style.overflow=adminUnlocked?'hidden':'';
}
function printReportViewer(){
 if(!adminUnlocked)return;
 if($('reportViewer').hidden){openReportViewer();return;}
 const frame=$('reportFrame');
 try{frame.contentWindow.focus();frame.contentWindow.print();}
 catch(e){alert('Usa o botão Imprimir / Guardar PDF dentro do relatório ou guarda o ficheiro HTML.');}
}
function report(){openReportViewer();}
function downloadHTMLReport(){
 if(!adminUnlocked)return;
 const html=buildReportHTML();
 const blob=new Blob([html],{type:'text/html;charset=utf-8'});
 const href=URL.createObjectURL(blob),a=document.createElement('a');
 a.href=href;a.download='Jardim_Magico_Relatorio_Caixa_'+new Date().toISOString().slice(0,10)+'.html';
 document.body.appendChild(a);a.click();a.remove();setTimeout(()=>URL.revokeObjectURL(href),30000);
}
function printHTMLReport(){printReportViewer();}

function musicElement(){return $('bgMusic');}
function musicUI(){
 const a=musicElement();
 if(a){a.loop=true;a.volume=Math.max(0,Math.min(1,musicVolume));}
 $('btnMusic').textContent=musicEnabled?'♫':'♫̸';
 $('btnMusic').classList.toggle('off',!musicEnabled);
 $('btnMusic').setAttribute('aria-label',musicEnabled?'Desligar música de fundo':'Ligar música de fundo');
 $('btnSound').textContent=mute?'🔇':'🔊';
 $('btnSound').classList.toggle('off',mute);
 $('btnSound').setAttribute('aria-label',mute?'Ligar efeitos sonoros':'Desligar efeitos sonoros');
}
function startMusic(){
 const audio=musicElement();
 if(!audio)return;
 audio.volume=musicVolume;
 if(!musicEnabled || !session){if(!musicEnabled)audio.pause();return;}
 if(audio.paused){
  try{
   const request=audio.play();
   if(request?.catch)request.catch(()=>{ /* Safari: reativar após um toque do utilizador */ });
  }catch(e){}
 }
}
function soundStart(){
 // É invocado durante a ação física de iniciar sessão, comprar bónus ou rodar.
 // O Safari bloqueia áudio antes de uma interação do utilizador.
 startMusic();
 if(mute)return;
 try{audioCtx=audioCtx||new(window.AudioContext||window.webkitAudioContext)();audioCtx.resume();fxOutput();preloadReelLanding();preloadBonusJingle();}catch(e){}
}
/* V64: jingle escolhido pelo utilizador. Preload em Web Audio para tocar
   durante animações assíncronas no Safari; HTMLAudio como alternativa. */
function preloadBonusJingle(){
 if(jm64BonusAudioBuffer||jm64BonusAudioLoading||!audioCtx||!window.fetch)return;
 jm64BonusAudioLoading=fetch('assets/sons/bonus-floraphonic-v64.mp3?v=64.1')
  .then(response=>{if(!response.ok)throw new Error('Áudio indisponível: '+response.status);return response.arrayBuffer();})
  .then(data=>audioCtx.decodeAudioData(data))
  .then(buffer=>{jm64BonusAudioBuffer=buffer;return buffer;})
  .catch(err=>{console.warn('Jingle de bónus:',err);jm64BonusAudioLoading=null;return null;});
}
function stopBonusJingle(){
 if(jm64BonusSource){try{jm64BonusSource.stop();}catch(e){}jm64BonusSource=null;}
 const el=$('jm64BonusAudio');
 if(el&&!el.paused)el.pause();
}
function playBonusJingle(kind='bonus'){
 if(mute)return false;
 stopBonusJingle();
 if(audioCtx&&jm64BonusAudioBuffer){
  try{
   const src=audioCtx.createBufferSource(),gain=audioCtx.createGain();
   src.buffer=jm64BonusAudioBuffer;gain.gain.value=.66;
   src.connect(gain);gain.connect(fxOutput()||audioCtx.destination);
   src.onended=()=>{if(jm64BonusSource===src)jm64BonusSource=null;};
   jm64BonusSource=src;src.start();return true;
  }catch(e){console.warn('WebAudio jingle:',e);}
 }
 const el=$('jm64BonusAudio');
 if(el){
  try{
   el.currentTime=0;el.volume=.82;
   const p=el.play();
   if(p&&typeof p.catch==='function')p.catch(err=>{console.warn('Reprodução do jingle:',kind,err);audioFX('bonus')});
   return true;
  }catch(e){console.warn('Reprodução direta:',e);}
 }
 audioFX('bonus');return false;
}
// V36: compressor/limitador exclusivo para FX. Os volumes independem da música.
function fxOutput(){
 if(!audioCtx)return null;
 if(!fxMaster){
  fxMaster=audioCtx.createDynamicsCompressor();
  fxMaster.threshold.value=-13;fxMaster.knee.value=12;
  fxMaster.ratio.value=7;fxMaster.attack.value=.004;fxMaster.release.value=.16;
  fxMaster.connect(audioCtx.destination);
 }
 return fxMaster;
}
function tone(freq,d=.2,type='sine',vol=.02,startDelay=0,endFreq=freq){
 if(!audioCtx||mute)return;
 try{
  const t=audioCtx.currentTime+startDelay,o=audioCtx.createOscillator(),g=audioCtx.createGain();
  o.type=type;o.frequency.setValueAtTime(freq,t);
  if(endFreq!==freq)o.frequency.exponentialRampToValueAtTime(Math.max(45,endFreq),t+d);
  g.gain.setValueAtTime(.0001,t);g.gain.exponentialRampToValueAtTime(Math.min(.35,vol),t+Math.min(.012,d/4));
  g.gain.exponentialRampToValueAtTime(.0001,t+d);
  o.connect(g);g.connect(fxOutput());o.start(t);o.stop(t+d+.02);
 }catch(e){}
}
function noiseBurst(duration=.15,volume=.03,cutoff=500){
 if(!audioCtx||mute)return;
 try{
  const sr=audioCtx.sampleRate,len=Math.max(1,Math.floor(sr*duration)),buff=audioCtx.createBuffer(1,len,sr),ch=buff.getChannelData(0);
  for(let i=0;i<len;i++)ch[i]=(Math.random()*2-1)*(1-i/len);
  const src=audioCtx.createBufferSource(),filter=audioCtx.createBiquadFilter(),gain=audioCtx.createGain();
  filter.type='bandpass';filter.frequency.value=cutoff;filter.Q.value=.7;
  src.buffer=buff;gain.gain.value=Math.min(.2,volume);
  src.connect(filter);filter.connect(gain);gain.connect(fxOutput());src.start();
 }catch(e){}
}
// Campainha de escola antiga: golpes metálicos rápidos com harmónicos não inteiros.
// Só tocar na entrada de uma funcionalidade ou na entrega de um prémio de bónus.
function schoolBell(){
 if(!audioCtx||mute)return;
 const now=performance.now();if(now-lastSchoolBellAt<2100)return;
 lastSchoolBellAt=now;
 try{
  const base=audioCtx.currentTime+.02;
  const hits=[0,.21,.43,.66,.90,1.14,1.38];
  const harmonics=[[1,.095],[1.51,.055],[2.14,.034],[2.91,.019],[3.86,.009]];
  for(let i=0;i<hits.length;i++){
   const t=base+hits[i],decay=i===hits.length-1?1.02:.36;
   for(const [ratio,vol] of harmonics){
    const osc=audioCtx.createOscillator(),gain=audioCtx.createGain();
    osc.type='sine';osc.frequency.setValueAtTime((i%2?821:854)*ratio,t);
    osc.frequency.exponentialRampToValueAtTime((i%2?817:849)*ratio,t+decay);
    gain.gain.setValueAtTime(.0001,t);
    gain.gain.exponentialRampToValueAtTime(vol,t+.006);
    gain.gain.exponentialRampToValueAtTime(.0001,t+decay);
    osc.connect(gain);gain.connect(fxOutput());osc.start(t);osc.stop(t+decay+.03);
   }
  }
 }catch(e){}
}
function audioFX(name,value=2){
 if(!audioCtx||mute)return;
 if(name==='tap'){
  // Um clique mecânico breve, idêntico nos botões + e −.
  tone(1130,.045,'triangle',.065,0,450);tone(290,.039,'sine',.046,0,175);noiseBurst(.019,.025,2700);
 }
 if(name==='lock'){tone(840,.045,'triangle',.046,0,250);noiseBurst(.038,.025,950);}
 if(name==='drop'){tone(170,.11,'triangle',.03,0,76);noiseBurst(.055,.008,500);}
 if(name==='ballDrop'){
   // Dois impactos graves PUM-PUM na chegada do multiplicador.
   const w=Math.min(1.2,1+Math.log2(Math.max(2,Number(value)||2))*.024);
   tone(125/w,.19,'sine',.108,0,49);tone(78/w,.24,'triangle',.075,.13,42);
   tone(615*w,.13,'sine',.017,.16,930*w);noiseBurst(.065,.022,155);
  }
  if(name==='coin'){[900,1240,1650].forEach((n,i)=>tone(n,.19,'sine',.024,i*.065));noiseBurst(.035,.009,1800);}
 if(name==='burst'){noiseBurst(.26,.065,150);tone(130,.32,'sawtooth',.037,0,45);tone(680,.19,'sine',.02,.06,1210);}
 if(name==='win'){[392,493.9,587.3,784,987.8].forEach((n,i)=>tone(n,.25,'triangle',.020,i*.065));}
 if(name==='big'){[196,262,330,392,523,659,784,988].forEach((n,i)=>tone(n,.58,'triangle',.027,i*.12));noiseBurst(.32,.065,300);}
 if(name==='scatter'){[740,1046,1318,1568,2093].forEach((n,i)=>tone(n,.50,'sine',.055,i*.09,n*1.13));noiseBurst(.12,.016,1200);}
 if(name==='wild'){
  tone(420,.42,'sawtooth',.068,0,970);
  [880,1174,1568].forEach((n,i)=>tone(n,.33,'triangle',.045,.08+i*.082,n*1.07));
  noiseBurst(.075,.023,3200);
 }
 if(name==='multiplier'){
  // As bolas de multiplicação têm tilintar próprio, em duas oitavas.
  const shift=Math.min(1.48,1+Math.log2(Math.max(2,Number(value)||2))*.065);
  [650,920,1300,1810].forEach((f,i)=>tone(f*shift,.36,'sine',.055,i*.056,f*shift*1.09));
  tone(290*shift,.28,'triangle',.048,0,920*shift);noiseBurst(.085,.025,2200);
 }
 if(name==='gem'){[440,659,880,1174,1760].forEach((n,i)=>tone(n,.40,'sine',.024,i*.075));}
 if(name==='vault'){tone(95,.56,'sawtooth',.08,0,35);noiseBurst(.43,.11,105);[523,659,784,1047].forEach((f,i)=>tone(f,.4,'triangle',.022,.14+i*.11));}
 if(name==='bonus'){[261,330,392,494,659,784].forEach((n,i)=>tone(n,.41,'triangle',.026,i*.14));}
 if(name==='schoolBell')schoolBell();
}
function playScatterNote(){
 // V40: nota SCATTER bem alta, com fundamental e harmónicos, comprimidos pelo FX master.
 // As três notas Dó, Ré, Mi acompanham o SCATTER que chega aos cilindros.
 if(!audioCtx||mute)return;
 const notes=[523.25,587.33,659.25],note=notes[Math.min(scatterNoteStep,2)];
 scatterNoteStep++;
 tone(note,.68,'sine',.205,0,note);
 tone(note*2,.51,'triangle',.108,.013,note*2);
 tone(note*.5,.43,'triangle',.077,.009,note*.5);
 tone(note*3,.25,'sine',.037,.04,note*3);
 noiseBurst(.068,.025,2600);
}
function scatterSuspenseBeat(step){
 if(!audioCtx||mute)return;
 // Pulsações aceleram ligeiramente em altura. Nunca alteram a saída sorteada.
 const base=215+Math.min(16,step)*17;
 tone(base,.19,'triangle',.078,0,base*.92);
 tone(base*1.5,.12,'sine',.053,.075,base*1.55);
 tone(90,.115,'sine',.046,0,74);
}
function scatterSuspenseResolve(won){
 if(!audioCtx||mute)return;
 if(won){
  [784,988,1175].forEach((f,i)=>tone(f,.37,'triangle',.075,i*.082));
 }else{
  [392,330,261].forEach((f,i)=>tone(f,.24,'sine',.032,i*.105));
 }
}
function symbolLandingSounds(column){
 for(const s of grid[column]){
  if(s.id==='scatter')playScatterNote();
  if(s.id==='wild')audioFX('wild');
  if(s.id==='mul')audioFX('ballDrop',s.value);
  if(s.id==='coin')audioFX('coin');
 }
}

function chooseSpinMul(){let r=Math.random();if(r<.82)return 2;if(r<.965)return 3;if(r<.992)return 5;if(r<.997)return 10;if(r<.9998)return 20;return awardContext==='free'?20:1000;}
// Uma bola de free spins vale até 20×; vários MULTI somam-se e podem atingir 80× ou mais.
// O raro 1000× fora das free spins mantém-se e fica limitado pelo cofre / 5000×.
function chooseHoldFactor(){let r=Math.random();if(r<.83)return 2;if(r<.965)return 3;if(r<.99)return 5;if(r<.995)return 10;if(r<.998)return 20;if(r<.9995)return 50;if(r<.9999)return 100;return 1000;}
/* V59 — dois estilos de prémios NORMAIS, escolhidos antes da jogada.
   O modo frequente concentra algumas figuras, originando mais trios,
   mas reduz o prémio médio por linha (~0,78) para não consumir o cofre.
   Não altera os pesos de WILD, SCATTER, moedas, bolas ou os jackpots. */
const FREQUENT_SYMBOL_WEIGHTS=[3.5,2.7,1.7,1.1,1,1,1,1];
const FREQUENT_LINE_PAYOUT=0.78;
function chooseRegularSymbol(){
 if(volatilityMode!=='frequentes')return pick(regular);
 const total=FREQUENT_SYMBOL_WEIGHTS.reduce((a,b)=>a+b,0);
 let r=Math.random()*total;
 for(let i=0;i<regular.length;i++){
  r-=FREQUENT_SYMBOL_WEIGHTS[i];
  if(r<0)return regular[i];
 }
 return regular[regular.length-1];
}
const volatilityPayScale=()=>volatilityMode==='frequentes'?FREQUENT_LINE_PAYOUT:1;
function chooseWildFactor(){
 // Pesos fixos, independentes de saldo, cofre e histórico. Valor único por símbolo.
 const p=Math.random();
 if(p<.50)return 1;
 if(p<.80)return 2;
 if(p<.92)return 3;
 if(p<.98)return 4;
 return 5;
}
function sym(){const p=Math.random();if(freeSpins>0 || awardContext==='free'){
 if(p<FREE_MUL_PROBABILITY)return {id:'mul',value:chooseSpinMul()};
 if(p<FREE_MUL_PROBABILITY+FREE_SCATTER_PROBABILITY)return {id:'scatter'};
 if(p<FREE_MUL_PROBABILITY+FREE_SCATTER_PROBABILITY+FREE_WILD_PROBABILITY)return {id:'wild',factor:chooseWildFactor()};
 if(p<FREE_MUL_PROBABILITY+FREE_SCATTER_PROBABILITY+FREE_WILD_PROBABILITY+FREE_COIN_PROBABILITY)return {id:'coin',uid:++symbolSeq,value:pick([10,20,50,100,200,500,1000,2000,5000])};
}else{
 if(p<.0045)return {id:'mul',value:chooseSpinMul()};
 if(p<.0335)return {id:'scatter'};
 if(p<.060)return {id:'wild',factor:chooseWildFactor()};
 if(p<.0855)return {id:'coin',uid:++symbolSeq,value:pick([10,20,50,100,200,500,1000,2000,5000])};
}
 // A pedra retira probabilidade apenas às figuras normais; símbolos especiais mantêm-se.
 const free=freeSpins>0 || awardContext==='free';
 const specialCap=free
  ? FREE_MUL_PROBABILITY+FREE_SCATTER_PROBABILITY+FREE_WILD_PROBABILITY+FREE_COIN_PROBABILITY
  : .0855;
 if(p<specialCap+STONE_PROBABILITY)return {id:'stone'};
 return {id:chooseRegularSymbol()};
}

function flyCoinToVault(x,y,delay=0,faceValue=0){
 return new Promise(resolve=>{
  const stage=$('stageWrap').getBoundingClientRect(),dst=$('vaultFace').getBoundingClientRect(),layer=$('vaultFlight');
  const sx=stage.left+(x+.5)*stage.width/5,sy=stage.top+(y+.5)*stage.height/3;
  const ex=dst.left+dst.width/2,ey=dst.top+dst.height/2;
  const node=document.createElement('div');node.className='flyVaultCoin';node.textContent=faceValue?'+'+(faceValue/100).toLocaleString('pt-PT',{maximumFractionDigits:2})+'€':'✦';node.style.left=sx+'px';node.style.top=sy+'px';
  node.style.setProperty('--dx',(ex-sx)+'px');node.style.setProperty('--dy',(ey-sy)+'px');
  node.style.setProperty('--flyTime',mode==='turbo'?'380ms':'760ms');layer.append(node);
  setTimeout(()=>{node.classList.add('flying');audioFX('coin')},25+delay);
  const duration=(mode==='turbo'?380:760)+delay+80;
  setTimeout(()=>{node.remove();vaultCoins++;vaultFictiveSum+=faceValue;roundVaultNew++;update();
   const v=$('vaultFace');v.animate([{filter:'brightness(2.5)',transform:'scale(1.45)'},{filter:'brightness(1)',transform:'scale(1)'}],{duration:330,easing:'ease-out'});resolve();},duration);
 });
}
/* V56: moedas também estouram, voam para o porquinho e abrem uma casa
   para a cascata. Os valores impressos continuam apenas decorativos. */
function coinPop(x,y,value){
 const stage=$('stageWrap');if(!stage)return;
 const pop=document.createElement('span');pop.className='jm56CoinPop';
 pop.textContent='€';pop.style.left=((x+.5)*100/5)+'%';pop.style.top=((y+.5)*100/3)+'%';
 stage.append(pop);particlesAt((x+.5)*CW,(y+.5)*CH,'big');
 setTimeout(()=>pop.remove(),mode==='turbo'?320:540);
}
async function collectVaultCoins(){
 const fresh=[];
 for(let x=0;x<5;x++)for(let y=0;y<3;y++){
  const piece=grid[x][y];
  if(piece?.id!=='coin'||countedCoins.has(piece.uid))continue;
  countedCoins.add(piece.uid);
  const key=x+':'+y;
  claimedMultiplierCells.add(key); // fica invisível durante o voo
  fresh.push({x,y,key,value:Math.max(0,Math.round(Number(piece.value)||0))});
 }
 if(!fresh.length)return new Set();
 for(const coin of fresh)coinPop(coin.x,coin.y,coin.value);
 await Promise.all(fresh.map((coin,i)=>flyCoinToVault(coin.x,coin.y,i*(mode==='turbo'?10:30),coin.value)));
 notice('🪙 Moedas recolhidas pelo porquinho! Entram peças novas.');
 return new Set(fresh.map(coin=>coin.key));
}
function maybeTriggerVault(){
 if(!roundVaultNew||vaultCoins<8)return false;
 // Mais oportunidades de abrir, mas evita iniciar bónus sem fundos mínimos de prémios.
 return prizePool>=Math.max(20,BETS[betIdx]*5)&&Math.random()<.018;
}
async function vaultExplosion(){
 const v=$('vaultBtnMobile'),rect=v.getBoundingClientRect();const ox=rect.left+rect.width/2,oy=rect.top+rect.height/2;
 const layer=$('vaultFlight');v.classList.add('vaultBurst');audioFX('vault');notice('💥 O cofre ficou tão cheio que EXPLODIU!');
 const ripple=document.createElement('div');ripple.className='vaultRipple';ripple.style.left=ox+'px';ripple.style.top=oy+'px';layer.appendChild(ripple);
 const bounds={w:innerWidth,h:innerHeight};
 const count=mode==='turbo'?48:90;for(let i=0;i<count;i++){
  const coin=document.createElement('div');coin.className='flyVaultCoin blast';coin.textContent=i%5===0?'◆':'✦';coin.style.left=ox+'px';coin.style.top=oy+'px';
  const ang=2*Math.PI*i/count+rand(-.18,.18),length=rand(bounds.w*.18,bounds.w*.95);
  coin.style.setProperty('--floatX',(Math.cos(ang)*length)+'px');coin.style.setProperty('--floatY',(Math.sin(ang)*length*.95-rand(0,bounds.h*.3))+'px');
  coin.style.setProperty('--twist',rand(-1300,1300)+'deg');coin.style.setProperty('--endScale',rand(.28,1.45).toFixed(2));coin.style.setProperty('--life',rand(1000,1600).toFixed(0)+'ms');
  layer.appendChild(coin);setTimeout(()=>coin.remove(),1750);
 }
 await sleep(mode==='turbo'?950:1350);v.classList.remove('vaultBurst');ripple.remove();vaultCoins=0;vaultFictiveSum=0;roundVaultNew=0;update();
}
async function showFeatureIntro(kind,count=15,retrigger=false){
 const box=$('featureIntro');
 $('featureTitle').textContent=kind==='free'?(retrigger?'+':'')+count+' FREE SPINS!':'HOLD & WIN!';
 $('featureSub').textContent=kind==='free'?(retrigger?'✦ MAIS JOGADAS NO JARDIM ✦':'✦ O JARDIM DESPERTOU ✦'):'💥 O TESOURO FOI LIBERTADO!';
 box.classList.remove('active');void box.offsetWidth;box.classList.add('active');
 playBonusJingle(kind==='free'?(retrigger?'scatter_extra':'freespins'):'hold');
 await sleep(mode==='turbo'?870:1480);box.classList.remove('active');
}

/* V52 — resumo de free spins transparente, fechado apenas pelo GIRO principal. */
/* V54 — o valor das free spins só é atribuído no final, depois de todas as
   bolas multiplicadoras viajarem da faixa superior até ao cálculo central. */
function freeSummaryNode(){
 let node=$('jm52FreeSummary');
 if(!node){
  node=document.createElement('div');node.id='jm52FreeSummary';
  node.setAttribute('aria-live','polite');node.setAttribute('role','status');
  node.innerHTML='<div class="jm52FreeHeading">FREE SPINS CONCLUÍDAS</div><div class="jm54FreeFactor"></div><div class="jm54FreeEquation"></div><div class="jm52FreeValue"></div><div class="jm52FreeCount"></div><div class="jm54FreeLimit"></div><div class="jm52FreeHint">CARREGA EM GIRO PARA CONTINUAR</div>';
  $('stageWrap').append(node);
 }
 return node;
}
function fillFreeSummary(node){
 const factor=freeMultiplierBalls.length?freeMultiplierBalls.reduce((sum,x)=>sum+x,0):1;
 node.querySelector('.jm52FreeHeading').textContent='FREE SPINS CONCLUÍDAS';
 node.querySelector('.jm54FreeFactor').textContent='MULTIPLICADORES ×'+factor;
 const raw=freeBaseTotal*factor,eligible=Math.min(raw,freeBet*MAX_WIN_MULT);
 node.querySelector('.jm54FreeEquation').textContent='POTENCIAL '+money(freeBaseTotal)+' × '+factor+' = '+money(raw);
 node.querySelector('.jm54FreeLimit').textContent=raw>eligible?'TETO 5000×: '+money(eligible):freeBonusTotal<eligible?'PAGO ATÉ AO SALDO DO COFRE':'';
 node.querySelector('.jm52FreeValue').textContent=money(freeBonusTotal);
 node.querySelector('.jm52FreeCount').textContent=freeSummaryCount+' JOGADAS GRÁTIS';
 node.querySelector('.jm52FreeHint').textContent='CARREGA EM GIRO PARA CONTINUAR';
 node.classList.remove('jm54Calculating');node.classList.add('show');
 window.JM_WINCELEBRATION_V60?.applySummary(node,freeBonusTotal,freeBet);
}
async function settleFreeSpins(){
 if(freeSettlementRunning||!freeSettlePending||freeSummaryPending||freeSpins>0||bonus||running)return;
 freeSettlementRunning=true;freeAutoRunning=false;
 autoSpinsRemaining=0;autoSpinsTotal=0;
 const node=freeSummaryNode(),factor=freeMultiplierBalls.length?freeMultiplierBalls.reduce((sum,x)=>sum+x,0):1;
 node.classList.add('show','jm54Calculating');
 node.querySelector('.jm52FreeHeading').textContent='A SOMAR OS MULTIPLICADORES';
 node.querySelector('.jm54FreeFactor').textContent='✦ ✦ ✦';
 node.querySelector('.jm54FreeEquation').textContent=money(freeBaseTotal)+' × ?';
 node.querySelector('.jm52FreeValue').textContent='…';
 node.querySelector('.jm52FreeCount').textContent=freeSummaryCount+' JOGADAS GRÁTIS';
 node.querySelector('.jm54FreeLimit').textContent='';
 node.querySelector('.jm52FreeHint').textContent='A REVELAR O PRÉMIO FINAL';
 update();save();
 const ghosts=[];
 try{
  const visualPills=Array.from($('orbs').querySelectorAll('.orbPill'));
  const target=node.querySelector('.jm54FreeFactor');
  await sleep(mode==='turbo'?180:390);
  const to=target.getBoundingClientRect();
  for(let i=0;i<visualPills.length;i++){
   const el=visualPills[i],rect=el.getBoundingClientRect();
   if(!rect.width||!rect.height)continue;
   const ghost=document.createElement('span');
   ghost.className='jm54MultiplierFlight';ghost.textContent=el.textContent;
   ghost.style.left=(rect.left+rect.width/2-17)+'px';
   ghost.style.top=(rect.top+rect.height/2-17)+'px';
   document.body.append(ghost);ghosts.push(ghost);
   const dx=to.left+to.width/2-(rect.left+rect.width/2);
   const dy=to.top+to.height/2-(rect.top+rect.height/2);
   const duration=mode==='turbo'?440:800,delay=i*(mode==='turbo'?24:49);
   ghost.style.transition='transform '+duration+'ms cubic-bezier(.13,.71,.18,1),opacity '+duration+'ms ease';
   setTimeout(()=>{ghost.style.transform='translate('+dx+'px,'+dy+'px) scale(.45) rotate(260deg)';ghost.style.opacity='0';audioFX('coin');},30+delay);
  }
  await sleep(mode==='turbo'?650:1250);
  node.querySelector('.jm54FreeFactor').textContent='×'+factor;
  audioFX('win');
  node.querySelector('.jm54FreeEquation').textContent=money(freeBaseTotal)+' × '+factor+' =';
  await sleep(mode==='turbo'?330:750);
 }catch(err){console.warn('Animação dos multiplicadores:',err);}
 finally{ghosts.forEach(ghost=>ghost.remove());}
 // Não há espera entre o débito do cofre, o registo e o save: uma atualização
 // da página nunca duplica o pagamento da mesma sequência de free spins.
 const requested=Math.min(freeBaseTotal*factor,freeBet*MAX_WIN_MULT);
 const paid=payFromVault(requested);
 freeBonusTotal+=paid;lastPrize=freeBonusTotal;
 freeSettlePending=false;freeSummaryPending=true;
 freeSettlementRunning=false;
 if(paid>0)log('premio_freespins_final',paid,'Free spins: base '+money(freeBaseTotal)+' ×'+factor+'; pedido '+money(requested)+'; pago '+money(paid));
 if(paid<requested)log('limite_cofre_freespins',0,'Limite de cofre aplicado ao total: '+money(requested)+' → '+money(paid));
 fillFreeSummary(node);
 updateFreeExposure();
 node.querySelector('.jm54FreeLimit').textContent=paid<requested?'VALOR LIMITADO PELO COFRE: '+money(paid):(freeBaseTotal*factor>requested?'TETO 5000×: '+money(requested):'');
 node.querySelector('.jm54FreeEquation').textContent='POTENCIAL '+money(freeBaseTotal)+' × '+factor+' = '+money(freeBaseTotal*factor);
 audioFX(paid?'big':'win');
 notice('✦ FREE SPINS: '+money(freeBonusTotal)+' · CARREGA EM GIRO PARA CONTINUAR');
 update();save();
}
function showFreeSummary(resume=false){
 if(freeSpins>0||bonus||running)return;
 if(freeSettlePending&&!freeSummaryPending){void settleFreeSpins();return;}
 freeAutoRunning=false;freeSummaryPending=true;autoSpinsRemaining=0;autoSpinsTotal=0;
 const node=freeSummaryNode();fillFreeSummary(node);
 if(!resume){audioFX('win');notice('✦ Free spins terminadas! Carrega em GIRO para continuar.');}
 update();save();
}
function closeFreeSummary(){
 if(!freeSummaryPending||freeSettlementRunning)return;
 freeSummaryPending=false;freeSettlePending=false;
 freeBaseTotal=0;freeMultiplierBalls=[];freeRetriggersUsed=0;shelf=[];
 updateDropScore(0);
 $('jm52FreeSummary')?.classList.remove('show');
 updateShelf();
 notice('Pronto para uma nova jogada.');
 update();save();
}

function openFreeStart(){
 if(!session||bonus||!freeSpins||freeSummaryPending)return;
 freeAutoRunning=false;freeStartPending=true;autoSpinsRemaining=0;autoSpinsTotal=0;
 $('freeStartCount').textContent=freeSpins;modal('freeStartModal',true);update();save();
}
function scheduleFreeAuto(){
 if(freeAutoTimer!==null){clearTimeout(freeAutoTimer);freeAutoTimer=null;}
 if(!freeAutoRunning||freeStartPending||running||bonus||!session||$('bigWin').classList.contains('show'))return;
 if(freeSpins<=0){showFreeSummary();return;}
 freeAutoTimer=setTimeout(()=>{freeAutoTimer=null;if(freeAutoRunning&&!running&&!bonus&&!freeStartPending&&freeSpins>0)doRound();},mode==='turbo'?220:680);
}
function startFreeAuto(){
 if(!session||bonus||!freeSpins||running||freeSummaryPending)return;
 modal('freeStartModal',false);freeStartPending=false;freeAutoRunning=true;
 soundStart();update();save();doRound();
}
function wholeGrid(){return Array.from({length:5},()=>Array.from({length:3},sym));}
// V41: só 3+ símbolos consecutivos da esquerda pagam nas linhas normais.
// Exceção: um par WILD adjacente em qualquer posição da mesma linha paga por si.
// Uma linha recebe no máximo um prémio; o prémio normal de 3+ tem prioridade.
// V66: WILD fixo em cascatas, válido UMA vez por queda, com número 1×...5×.
// Quando várias linhas partilham a mesma casa WILD, paga apenas a melhor linha.
// Isto evita contar um símbolo 3-15 vezes no mesmo resultado (risco de banca).
function wildFactorOf(piece){
 return piece?.id==='wild'?Math.max(1,Math.min(5,Math.floor(Number(piece.factor)||1))):1;
}
function evaluate(g,betAmount=BETS[betIdx],paidWildOnly=null){
 const possible=[];
 const coef=betAmount/LINES.length*rtpScaleFor(awardContext)*volatilityPayScale();
 for(let i=0;i<LINES.length;i++){
  const line=LINES[i],cells=line.map((row,x)=>g[x][row]),ids=cells.map(s=>s.id);
  // PEDRA não ganha nem pode ser substituída pelo WILD.
  const firstNonWild=ids.find(id=>id!=='wild'&&id!=='stone');
  const target=firstNonWild&&PAY[firstNonWild]?firstNonWild:'wild';
  let count=0;
  for(let x=0;x<5;x++){
   const id=ids[x];
   if(id==='stone')break; // A pedra interrompe a linha sem pagar.
   if(id===target||(target!=='wild'&&id==='wild'))count++;
   else break;
  }
  if(count>=3){
   const points=Array.from({length:count},(_,x)=>[x,line[x]]);
   const wildKeys=points.filter(([x,y])=>g[x][y].id==='wild').map(([x,y])=>x+':'+y);
   const multiplier=Math.min(MAX_WILD_LINE_FACTOR,points.reduce((v,[x,y])=>v*wildFactorOf(g[x][y]),1));
   possible.push({line:i,points,which:target,wildMultiplier:multiplier,wildKeys,
    raw:coef*PAY[target][count]*multiplier});
   continue;
  }
  const pairStart=ids.findIndex((id,x)=>id==='wild'&&x<4&&ids[x+1]==='wild');
  if(pairStart>=0){
   const points=[[pairStart,line[pairStart]],[pairStart+1,line[pairStart+1]]];
   const wildKeys=points.map(([x,y])=>x+':'+y);
   const multiplier=Math.min(MAX_WILD_LINE_FACTOR,points.reduce((v,[x,y])=>v*wildFactorOf(g[x][y]),1));
   possible.push({line:i,points,which:'wild',wildMultiplier:multiplier,wildKeys,special:'twoWild',
    raw:coef*PAY.wild[2]*multiplier});
  }
 }
 // Primeiro as combinações mais valiosas; desempate pelo número da linha.
 possible.sort((a,b)=>b.raw-a.raw||a.line-b.line);
 const usedWilds=new Set(),chosen=[];
 for(const candidate of possible){
  if(candidate.wildKeys.some(key=>usedWilds.has(key)))continue;
  // Uma sequência de WILDs isolados não volta a pagar só por outro
  // símbolo cair noutra coluna; exige uma nova spin para novo sorteio.
  const pureWild=candidate.points.every(([x,y])=>g[x][y].id==='wild');
  const wildSignature=pureWild?candidate.points.map(([x,y])=>x+':'+y).join('|'):null;
  if(wildSignature&&paidWildOnly?.has(wildSignature))continue;
  if(wildSignature)paidWildOnly?.add(wildSignature);
  candidate.wildKeys.forEach(key=>usedWilds.add(key));
  const {raw,wildKeys,...pay}=candidate;
  pay.value=Math.max(1,roundedPayout(raw));
  chosen.push(pay);
 }
 chosen.sort((a,b)=>a.line-b.line);
 return {won:chosen,amount:chosen.reduce((sum,win)=>sum+win.value,0)};
}
function mulIndices(g){const out=[];for(let x=0;x<5;x++)for(let y=0;y<3;y++)if(g[x][y].id==='mul')out.push({x,y,v:g[x][y].value||2});return out;}
function scatterCount(g){return g.flat().filter(x=>x.id==='scatter').length;}

/* V46: assets recortados (RGBA/PNG), contorno e movimentos únicos. */
const JM_COLORS={heart:'#ff618c',rose:'#ff91bd',mushroom:'#ffb65d',hat:'#b39bff',teapot:'#ffe4a2',potion:'#cb81ff',gem:'#de91ff',green:'#81ffbd',wild:'#ffe17b',scatter:'#bce9ff',coin:'#ffdb7e',mul:'#ff8778',stone:'#929da0'};
const JM_REDUCED=!!(window.matchMedia&&window.matchMedia('(prefers-reduced-motion:reduce)').matches);
/* V52: texto SCATTER recortado; a esfera é animada à parte. */
function jmScatterTextArt(){
 const c=document.createElement('canvas');c.width=c.height=256;
 const g=c.getContext('2d');g.textAlign='center';g.textBaseline='middle';g.lineJoin='round';
 g.font='900 51px Georgia,serif';const label='SCATTER';
 const width=g.measureText(label).width;g.translate(128,174);g.scale(Math.min(1,202/Math.max(1,width)),1);
 g.shadowColor='#d964ff';g.shadowBlur=17;g.strokeStyle='#33104c';g.lineWidth=17;g.strokeText(label,0,0);
 g.shadowColor='#ffa7e9';g.shadowBlur=8;g.strokeStyle='#c642df';g.lineWidth=10;g.strokeText(label,0,0);
 g.shadowBlur=0;g.strokeStyle='#ffeeb4';g.lineWidth=6;g.strokeText(label,0,0);
 const gold=g.createLinearGradient(0,-27,0,30);gold.addColorStop(0,'#fffbdc');gold.addColorStop(.3,'#ffe69b');gold.addColorStop(.62,'#fdba48');gold.addColorStop(1,'#ae591c');g.fillStyle=gold;g.fillText(label,0,0);
 return c;
}

/* V53 — recorte sem contornos artificiais, halos ou molduras retangulares.
   As artes antigas incluem cenários roxos desenhados dentro de cada PNG;
   removemos o fundo a partir das margens, sem pintar uma borda por cima. */
function jmCutout(img,id){
 try{
  const n=256,c=document.createElement('canvas');c.width=c.height=n;
  const ctx=c.getContext('2d',{willReadFrequently:true});
  ctx.drawImage(img,0,0,n,n);
  const data=ctx.getImageData(0,0,n,n),d=data.data;
  let left=n,top=n,right=-1,bottom=-1;
  for(let y=0;y<n;y++)for(let x=0;x<n;x++){
   const k=(y*n+x)*4;if(d[k+3]<24)continue;
   if(x<left)left=x;if(x>right)right=x;
   if(y<top)top=y;if(y>bottom)bottom=y;
  }
  if(right<left)return img;
  const w=right-left+1,h=bottom-top+1;
  // O objeto fica dentro da caixa que já vem no ficheiro. As bordas
  // e as manchas púrpuras/escuras são tratadas como fundo transparente.
  const backgroundSamples=[];
  for(let iy=0;iy<5;iy++)for(let ix=0;ix<5;ix++){
   if(ix!==0&&ix!==4&&iy!==0&&iy!==4)continue;
   const x=Math.min(right,Math.max(left,left+Math.round((w-1)*ix/4)));
   const y=Math.min(bottom,Math.max(top,top+Math.round((h-1)*iy/4)));
   const k=(y*n+x)*4;
   if(d[k+3]>30&&Math.max(d[k],d[k+1],d[k+2])<172)
    backgroundSamples.push([d[k],d[k+1],d[k+2]]);
  }
  const threshold=id==='hat'?63:id==='mushroom'?73:78;
  const oldAlpha=new Uint8Array(n*n);
  const bg=new Uint8Array(n*n),stack=new Int32Array(n*n);
  let write=0,read=0;
  for(let y=top;y<=bottom;y++)for(let x=left;x<=right;x++)oldAlpha[y*n+x]=d[(y*n+x)*4+3];
  function closeToBackground(x,y){
   const k=(y*n+x)*4,r=d[k],g=d[k+1],b=d[k+2];
   const hi=Math.max(r,g,b),lo=Math.min(r,g,b);
   if(hi<43)return true;
   if(hi<76&&b>g*.75)return true;
   if(hi>166)return false;
   // As cores de fundo da maior parte das cartas são violeta-escuro.
   if(b>g*1.19&&r>g*1.18&&hi<116)return true;
   for(const v of backgroundSamples){
    const dr=r-v[0],dg=g-v[1],db=b-v[2];
    if(dr*dr+dg*dg+db*db<threshold*threshold)return true;
   }
   return hi<96&&hi-lo<45;
  }
  function add(x,y){
   if(x<left||x>right||y<top||y>bottom)return;
   const i=y*n+x;if(bg[i]||oldAlpha[i]<4||!closeToBackground(x,y))return;
   bg[i]=1;stack[write++]=i;
  }
  for(let x=left;x<=right;x++){add(x,top);add(x,bottom)}
  for(let y=top;y<=bottom;y++){add(left,y);add(right,y)}
  while(read<write){
   const p=stack[read++],x=p%n,y=(p/n)|0;
   add(x-1,y);add(x+1,y);add(x,y-1);add(x,y+1);
  }
  // Transparência por pixéis; não desenhamos strokes nem sombras externas.
  for(let y=top;y<=bottom;y++)for(let x=left;x<=right;x++){
   const i=y*n+x,k=i*4,hi=Math.max(d[k],d[k+1],d[k+2]);
   if(!oldAlpha[i])continue;
   const edge=Math.min(x-left,right-x,y-top,bottom-y);
   const edgeFade=Math.min(1,Math.max(0,(edge-1)/5));
   let alpha=oldAlpha[i]*edgeFade;
   if(bg[i])alpha=0;
   else if(hi<45)alpha*=.1;
   else if(hi<72)alpha*=.45;
   d[k+3]=Math.round(alpha);
  }
  ctx.putImageData(data,0,0);
  return c;
 }catch(err){console.warn('Recorte do símbolo indisponível',id,err);return img;}
}
function jmMotion(s,x,t){
 if(!s||JM_REDUCED)return {dy:0,dx:0,k:1,a:0,light:1};
 const v=t*.001,p=x*.43;let dy=0,dx=0,k=1,a=0,light=1;
 switch(s.id){
  case 'heart':k=1+.056*Math.sin(v*4.6)**2;break;
  case 'rose':a=.065*Math.sin(v*1.4+p);dy=3*Math.sin(v*1.3+p);break;
  case 'mushroom':dy=3*Math.sin(v*1.7+p);a=.045*Math.sin(v*1.9+p);break;
  case 'hat':a=.085*Math.sin(v*1.1+p);break;
  case 'teapot':a=.06*Math.sin(v*1.45+p);dx=2*Math.sin(v*.95+p);break;
  case 'potion':a=.05*Math.sin(v*2.2+p);k=1+.02*Math.sin(v*2.8)**2;light=1.2;break;
  case 'gem':a=.055*Math.sin(v*.95+p);light=1+.2*Math.sin(v*2.6)**2;break;
  case 'green':dx=3.4*Math.sin(v*1.15+p);dy=2.4*Math.cos(v*1.15+p);break;
  case 'wild':k=1+.045*Math.sin(v*3.3)**2;light=1+.38*Math.sin(v*4.2)**2;break;
  case 'scatter':a=0;k=1;light=1+.12*Math.sin(v*2.0)**2;break; // V48: só a esfera gira, o SCATTER é fixo
  case 'mul':k=1+.028*Math.sin(v*2.9)**2;break;
  case 'coin':a=.07*Math.sin(v*1.4+p);break;
 }
 return {dy,dx,k,a,light};
}
function jmPixiMotion(sp,s,x,t){
 if(!sp.visible||!s)return;
 const m=jmMotion(s,x,t);sp.rotation=m.a;sp.position.x+=m.dx;sp.position.y+=m.dy;
 sp.alpha=Math.min(1,.95+(m.light-1)*.10);
 const z=symbolZoom(s);sp.scale.set(CW*.985*z*m.k/Math.max(1,sp.texture.width),CH*.992*z*m.k/Math.max(1,sp.texture.height));
}

function imageLoad(){return Promise.all(Object.entries(IMGFILES).map(([id,path])=>new Promise(resolve=>{const img=new Image();img.onload=()=>{nativeAssets[id]=id==='scatter'?jmScatterTextArt():jmCutout(img,id);resolve()};img.onerror=()=>resolve();img.src=window.JM_INLINE_ASSETS[id]})));}
function artBg(ctx,t){ctx.save();ctx.clearRect(0,0,W,H);
 const wash=ctx.createLinearGradient(0,0,0,H);wash.addColorStop(0,'rgba(34,16,57,.12)');wash.addColorStop(.5,'rgba(50,20,79,.08)');wash.addColorStop(1,'rgba(16,13,36,.14)');ctx.fillStyle=wash;ctx.fillRect(0,0,W,H);
 for(let i=0;i<7;i++){
  const px=(Math.sin(t*.00035+i*1.3)*.5+.5)*W;
  const py=(Math.cos(t*.00028+i*.9)*.5+.5)*H;
  const r=55+(i%3)*18;
  const halo=ctx.createRadialGradient(px,py,0,px,py,r);
  halo.addColorStop(0,['rgba(255,216,146,.12)','rgba(152,220,255,.10)','rgba(206,145,255,.10)','rgba(153,255,202,.08)'][i%4]);
  halo.addColorStop(1,'rgba(255,255,255,0)');
  ctx.fillStyle=halo;ctx.beginPath();ctx.arc(px,py,r,0,Math.PI*2);ctx.fill();
 }
 for(let x=0;x<5;x++){
  const gx=x*CW;
  const g=ctx.createLinearGradient(gx,0,gx+CW,H);g.addColorStop(0,'rgba(255,255,255,.03)');g.addColorStop(.25,'rgba(182,126,255,.035)');g.addColorStop(.5,'rgba(23,7,36,.01)');g.addColorStop(.75,'rgba(126,224,255,.028)');g.addColorStop(1,'rgba(255,255,255,.03)');
  ctx.fillStyle=g;ctx.fillRect(gx+4,0,CW-8,H);
  ctx.strokeStyle='rgba(255,222,164,.18)';ctx.lineWidth=2;ctx.beginPath();ctx.moveTo(gx+1,0);ctx.lineTo(gx+1,H);ctx.stroke();
  const stripe=(t*.038+x*140)%(H+250)-125;let grad=ctx.createLinearGradient(0,stripe-90,0,stripe+90);grad.addColorStop(0,'rgba(255,255,255,0)');grad.addColorStop(.5,'rgba(255,255,255,.10)');grad.addColorStop(1,'rgba(255,255,255,0)');ctx.fillStyle=grad;ctx.fillRect(gx+8,stripe-90,CW-16,180);
 }
 ctx.strokeStyle='rgba(255,212,148,.26)';ctx.lineWidth=2;ctx.strokeRect(1,1,W-2,H-2);ctx.restore()}

const REEL_SYMBOL_ZOOM=1.27; // V52: peças normais mais pequenas, recortadas
const SPECIAL_REEL_ZOOM={wild:1.10,scatter:1.20,coin:1.02,mul:1.06,stone:1.08}; // especiais sobressaem
function symbolZoom(s){return s&&SPECIAL_REEL_ZOOM[s.id]||REEL_SYMBOL_ZOOM;}
function drawSymbol(ctx,s,x,y,w=CW,h=CH,time=0,zoom=REEL_SYMBOL_ZOOM){
 if(!s||s.id==='blank')return;
 ctx.save();const id=s.id,m=jmMotion(s,x/CW,time);
 ctx.translate(x+w/2+m.dx,y+h/2+m.dy);ctx.rotate(m.a);
 const z=zoom===REEL_SYMBOL_ZOOM?symbolZoom(s):zoom;ctx.scale(m.k*z,m.k*z);
 ctx.globalAlpha=Math.min(1,.90+(m.light-1)*.27);
 const img=nativeAssets[id];if(img){const ww=w*.985,hh=h*.992;ctx.drawImage(img,-ww/2,-hh/2,ww,hh)}else if(id==='stone'){
   // Ícone de reserva, caso o SVG ainda não esteja pronto.
   ctx.fillStyle='#87949b';ctx.strokeStyle='#424e56';ctx.lineWidth=5;
   ctx.beginPath();ctx.moveTo(-w*.34,h*.17);ctx.lineTo(-w*.27,-h*.20);
   ctx.lineTo(-w*.10,-h*.35);ctx.lineTo(w*.19,-h*.30);
   ctx.lineTo(w*.33,-h*.03);ctx.lineTo(w*.27,h*.28);
   ctx.lineTo(-w*.16,h*.31);ctx.closePath();ctx.fill();ctx.stroke();
  }else if(id==='mul'){
  const rad=Math.min(w,h)*.34;
  const grad=ctx.createRadialGradient(-rad*.34,-rad*.52,2,0,0,rad);
  grad.addColorStop(0,'#fff0dd');
  grad.addColorStop(.15,'#ffb09b');
  grad.addColorStop(.39,'#e92f36');
  grad.addColorStop(.72,'#ae0b24');
  grad.addColorStop(1,'#510719');
  ctx.fillStyle=grad;
  ctx.strokeStyle='#ffda8e';
  ctx.lineWidth=5;
  ctx.shadowColor='rgba(255,64,47,.55)';
  ctx.shadowBlur=12;
  ctx.beginPath();ctx.arc(0,0,rad,0,Math.PI*2);ctx.fill();ctx.stroke();
  ctx.shadowBlur=0;
  ctx.textAlign='center';ctx.textBaseline='middle';
  ctx.font=`800 ${rad*.78}px "Trebuchet MS",Verdana,sans-serif`;
  ctx.lineWidth=3;ctx.strokeStyle='#6c0820';ctx.strokeText(s.value+'×',0,2);
  ctx.fillStyle='#fff6dc';ctx.fillText(s.value+'×',0,2)
}else if(id==='coin'){const rad=Math.min(w,h)*.35,gd=ctx.createRadialGradient(-rad*.4,-rad*.5,1,0,0,rad);gd.addColorStop(0,'#ffffe6');gd.addColorStop(.3,'#ffe28a');gd.addColorStop(.65,'#c88625');gd.addColorStop(1,'#6d401e');ctx.fillStyle=gd;ctx.strokeStyle='#ffe8a8';ctx.lineWidth=6;ctx.beginPath();ctx.arc(0,0,rad,0,Math.PI*2);ctx.fill();ctx.stroke();ctx.textAlign='center';ctx.textBaseline='middle';ctx.font=`900 ${rad*.95}px Georgia`;ctx.fillStyle='#fff3b1';const cents=Math.max(0,Math.round(Number(s.value)||20));const label=(cents/100).toLocaleString('pt-PT',{maximumFractionDigits:2});ctx.font=`900 ${Math.max(rad*.46,rad*.85/(1+Math.max(0,label.length-2)*.12))}px Georgia`;ctx.fillText(label,0,2)}
 if(id==='wild'){ctx.fillStyle='#fff0aa';ctx.shadowColor='#ffcf76';ctx.shadowBlur=4;ctx.font=`900 ${Math.max(13,h*.095)}px Georgia`;ctx.textAlign='center';ctx.textBaseline='middle';ctx.fillText('×'+wildFactorOf(s),w*.27,-h*.32);ctx.shadowBlur=0}ctx.restore()}

const imagesCanvases={};function makeSymbolCanvas(id){if(imagesCanvases[id])return imagesCanvases[id];const can=document.createElement('canvas');can.width=180;can.height=180;drawSymbol(can.getContext('2d'),{id,value:2},0,0,180,180,0,1);return imagesCanvases[id]=can;}function makeWildCanvas(factor){
 const fixed=Math.max(1,Math.min(5,Math.floor(Number(factor)||1)));
 const key='wild'+fixed;
 if(imagesCanvases[key])return imagesCanvases[key];
 const can=document.createElement('canvas');
 can.width=can.height=180;
 drawSymbol(can.getContext('2d'),{id:'wild',factor:fixed},0,0,180,180,0,1);
 return imagesCanvases[key]=can;
}
function makeCoinCanvas(value){const key='coin'+value;if(imagesCanvases[key])return imagesCanvases[key];const can=document.createElement('canvas');can.width=can.height=180;drawSymbol(can.getContext('2d'),{id:'coin',value},0,0,180,180,0,1);return imagesCanvases[key]=can;}
function makeOrbCanvas(value){const key='mul'+value;if(imagesCanvases[key])return imagesCanvases[key];const can=document.createElement('canvas');can.width=180;can.height=180;drawSymbol(can.getContext('2d'),{id:'mul',value},0,0,180,180,0,1);return imagesCanvases[key]=can;}
let pixiReady=false;
async function initPixi(){
 if(!window.PIXI?.Application)return false;
 try{
  const app=new PIXI.Application();await app.init({width:W,height:H,backgroundAlpha:0,antialias:true,resolution:1,preference:'webgl'});
  if(!app.canvas)throw Error('no canvas');$('pixiMount').appendChild(app.canvas);pixi=app;
  for(let x=0;x<5;x++){pixiSprites[x]=[];
   const reelMask=new PIXI.Graphics().rect(x*CW,0,CW,H).fill(0xffffff);app.stage.addChild(reelMask);
   for(let y=0;y<6;y++){const sp=new PIXI.Sprite(PIXI.Texture.EMPTY);sp.mask=reelMask;sp.anchor.set(.5);sp.width=CW*.992*REEL_SYMBOL_ZOOM;sp.height=CH*.995*REEL_SYMBOL_ZOOM;sp.position.set(x*CW+CW/2,(y%3)*CH+CH/2);pixiSprites[x][y]=sp;app.stage.addChild(sp)}}
  pixiReady=true;return true;
 }catch(e){console.warn('Pixi indisponível, Canvas de reserva ativo:',e);pixiReady=false;return false;}
}
const cacheTextures=new Map();
function setPixiSprite(sp,s){if(!sp)return;if(!s){sp.visible=false;return}const key=s.id==='mul'?'mul'+(s.value||2):s.id==='coin'?'coin'+(s.value||20):s.id==='wild'?'wild'+wildFactorOf(s):s.id;let texture=cacheTextures.get(key);if(!texture){const img=s.id==='mul'?makeOrbCanvas(s.value||2):s.id==='coin'?makeCoinCanvas(s.value||20):s.id==='wild'?makeWildCanvas(wildFactorOf(s)):nativeAssets[s.id];if(!img){sp.visible=false;return}texture=PIXI.Texture.from(img);cacheTextures.set(key,texture)}sp.visible=true;sp.texture=texture;sp.width=CW*.985*symbolZoom(s);sp.height=CH*.992*symbolZoom(s);}
function animationPosition(x,y,t){
 const tr=transitions[x];
 if(tr){
  // O suspense mantém os tambores por parar a rodar, sem mostrar antecipadamente
  // os símbolos que já foram sorteados para a rodada.
  if(tr.scatterHold){
   const cycle=mode==='turbo'?145:190,elapsed=Math.max(0,t-tr.start);
   const step=Math.floor(elapsed/cycle),p=(elapsed%cycle)/cycle;
   const items=tr.holdSymbols;
   return {cur:items[(step+1)%items.length][y],y:y*CH-3*CH*(1-p),old:items[step%items.length][y],oldY:y*CH+3*CH*p};
  }
  const p=Math.max(0,Math.min(1,(t-tr.start)/tr.duration));const ease=1-Math.pow(1-p,3);
  return {cur:tr.next[y],y:y*CH-3*CH*(1-ease),old:tr.old[y],oldY:y*CH+3*CH*ease};
 }
 return {cur:grid[x][y],y:y*CH};
}
/* SCATTER: a palavra e a moldura nunca giram.
   Só a esfera azul no topo roda em 3D, com brilho de contacto (touch).
   A camada acompanha o símbolo no tambor e nas quedas, sem interferir nas jogadas. */
let jmScatterCanvas=null,jmScatterCtx=null;
const jmScatterTouches=new Map();
function jmPrepareScatterCanvas(){
 if(jmScatterCtx)return jmScatterCtx;
 const stage=$('stageWrap');
 if(!stage)return null;
 jmScatterCanvas=document.createElement('canvas');
 jmScatterCanvas.id='jmScatterOrbOverlay';
 jmScatterCanvas.width=W;jmScatterCanvas.height=H;
 jmScatterCanvas.setAttribute('aria-hidden','true');
 stage.append(jmScatterCanvas);
 jmScatterCtx=jmScatterCanvas.getContext('2d');
 // Um toque na própria figura dá duas rotações rápidas à esfera.
 // Não cancela gestos do utilizador nem altera os resultados do jogo.
 stage.addEventListener('pointerdown',ev=>{
  if(running||cascades.some(Boolean)||transitions.some(Boolean))return;
  const box=stage.getBoundingClientRect();
  if(!box.width||!box.height)return;
  const x=Math.floor((ev.clientX-box.left)*W/box.width/CW);
  const y=Math.floor((ev.clientY-box.top)*H/box.height/CH);
  if(x<0||x>=5||y<0||y>=3||grid[x]?.[y]?.id!=='scatter')return;
  jmScatterTouches.set(x+':'+y,performance.now());
 },{passive:true});
 return jmScatterCtx;
}
function jmPaintScatterMoon(c,x,y,t){
 if(JM_REDUCED)t=0;
 const row=Math.round(y/CH),key=x+':'+row;
 const since=t-(jmScatterTouches.get(key)??-1e9);
 const touch=since>=0&&since<1100;
 const u=touch?Math.min(1,since/1100):0;
 const burst=touch?(1-u)*(1-u):0;
 const extra=touch?Math.PI*4*(1-Math.pow(1-u,3)):0;
 const phase=t*.00185+x*.37+extra;
 const r=Math.min(CW,CH)*.155;
 const cx=(x+.5)*CW+(!JM_REDUCED?Math.sin(t*.0013+x)*1.6:0);
 const cy=y+CH*.31+(!JM_REDUCED?Math.sin(t*.002+x*.72)*1.8:0)-burst*2.2;
 c.save();c.translate(cx,cy);
 const glow=c.createRadialGradient(0,0,r*.38,0,0,r*(1.75+burst*.33));
 glow.addColorStop(0,'rgba(170,255,249,.29)');
 glow.addColorStop(.55,'rgba(42,215,252,.18)');
 glow.addColorStop(1,'rgba(36,203,249,0)');
 c.fillStyle=glow;c.beginPath();c.arc(0,0,r*(1.75+burst*.33),0,2*Math.PI);c.fill();
 // A superfície e os reflexos obedecem a uma fonte de luz fixa.
 c.shadowColor='#7cf8ff';c.shadowBlur=8+burst*15;
 const shell=c.createRadialGradient(-r*.34,-r*.38,r*.05,r*.07,r*.10,r*1.15);
 shell.addColorStop(0,'#f0ffff');shell.addColorStop(.18,'#9cfdf6');
 shell.addColorStop(.43,'#32cddd');shell.addColorStop(.72,'#1775b5');shell.addColorStop(1,'#102a61');
 c.fillStyle=shell;c.beginPath();c.arc(0,0,r,0,2*Math.PI);c.fill();c.shadowBlur=0;
 c.save();c.beginPath();c.arc(0,0,r*.985,0,Math.PI*2);c.clip();
 // Meridianos que se deslocam em perspectiva: rotação da bola, não da palavra.
 for(let k=0;k<4;k++){
  const a=phase+k*Math.PI/2,front=Math.cos(a);
  const xx=Math.sin(a)*r*.74;
  const rx=Math.max(r*.055,Math.abs(front)*r*.30);
  c.strokeStyle=front>0?'rgba(212,255,252,.84)':'rgba(17,87,145,.46)';
  c.lineWidth=front>0?r*.095:r*.12;
  c.beginPath();c.ellipse(xx,0,rx,r*.91,Math.sin(a)*.08,0,2*Math.PI);c.stroke();
 }
 const latitude=Math.sin(phase*.7)*r*.10;
 c.strokeStyle='rgba(202,255,250,.45)';c.lineWidth=r*.075;
 c.beginPath();c.ellipse(0,latitude,r*.94,r*.31,.08,0,2*Math.PI);c.stroke();
 const glossy=c.createRadialGradient(-r*.39,-r*.49,1,-r*.20,-r*.22,r*.83);
 glossy.addColorStop(0,'rgba(255,255,255,.71)');
 glossy.addColorStop(.37,'rgba(226,255,255,.23)');
 glossy.addColorStop(1,'rgba(255,255,255,0)');
 c.fillStyle=glossy;c.fillRect(-r,-r,r*2,r*2);
 c.restore();
 c.lineWidth=r*.095;c.strokeStyle='rgba(226,255,251,.86)';
 c.beginPath();c.arc(0,0,r,0,Math.PI*2);c.stroke();
 if(burst>0){
  c.globalAlpha=burst*.85;c.strokeStyle='#faffc8';c.lineWidth=2.5;
  c.beginPath();c.arc(0,0,r*(1.12+u*.74),0,Math.PI*2);c.stroke();
  for(let i=0;i<6;i++){
   const a=i*Math.PI/3+phase*.25,d=r*(1.35+u*.75);
   c.fillStyle=i%2?'#fffad0':'#b5fff8';
   c.beginPath();c.arc(Math.cos(a)*d,Math.sin(a)*d,Math.max(1,r*.075*(1-u)),0,Math.PI*2);c.fill();
  }
 }
 c.restore();
}
function jmRenderScatterMoons(t){
 const c=jmPrepareScatterCanvas();if(!c)return;
 c.clearRect(0,0,W,H);
 for(let x=0;x<5;x++){
  c.save();c.beginPath();c.rect(x*CW,0,CW,H);c.clip();
  if(cascades[x]){
   for(const part of cascades[x].items){
    if(part.s?.id!=='scatter'||t<part.start)continue;
    const q=Math.max(0,Math.min(1,(t-part.start)/part.duration)),ease=1-(1-q)**3;
    jmPaintScatterMoon(c,x,part.fromY+(part.toY-part.fromY)*ease,t);
   }
  }else{
   for(let y=0;y<3;y++){
    const p=animationPosition(x,y,t);
    if(p.cur?.id==='scatter')jmPaintScatterMoon(c,x,p.y,t);
    if(p.old?.id==='scatter')jmPaintScatterMoon(c,x,p.oldY,t);
   }
  }
  c.restore();
 }
}

function frame(t){
 const ctx=nativeCtx;artBg(ctx,t);
 if(pixiReady){
  for(let x=0;x<5;x++)for(let y=0;y<3;y++){
   const p=animationPosition(x,y,t),sp=pixiSprites[x][y],old=pixiSprites[x][y+3];
   if(cascades[x]||claimedMultiplierCells.has(x+':'+y)){sp.visible=false;old.visible=false;continue}
   setPixiSprite(sp,p.cur);sp.position.set(x*CW+CW/2,p.y+CH/2);jmPixiMotion(sp,p.cur,x,t);
   if(p.old){setPixiSprite(old,p.old);old.position.set(x*CW+CW/2,p.oldY+CH/2);jmPixiMotion(old,p.old,x,t);}
   else old.visible=false;
  }
 }
 for(let x=0;x<5;x++){
  ctx.save();ctx.beginPath();ctx.rect(x*CW+4,0,CW-8,H);ctx.clip();
  if(cascades[x]){
   const parts=cascades[x].items;
   for(const part of parts){let q=Math.max(0,Math.min(1,(t-part.start)/part.duration));if(t<part.start)continue;let e=1-(1-q)**3;drawSymbol(ctx,part.s,x*CW,part.fromY+(part.toY-part.fromY)*e,CW,CH,t);}
  } else if(!pixiReady){
   for(let y=0;y<3;y++){if(claimedMultiplierCells.has(x+':'+y))continue;const p=animationPosition(x,y,t);if(p.old)drawSymbol(ctx,p.old,x*CW,p.oldY,CW,CH,t);drawSymbol(ctx,p.cur,x*CW,p.y,CW,CH,t);}
  }
  ctx.restore();
 }
 if(burstParticles.length){ctx.save();burstParticles=burstParticles.filter(p=>t-p.birth<p.life);for(const p of burstParticles){const k=(t-p.birth)/p.life;ctx.globalAlpha=1-k;ctx.fillStyle=p.color;ctx.beginPath();ctx.arc(p.x+p.vx*k,p.y+p.vy*k+90*k*k,Math.max(.1,p.size*(1-k)),0,Math.PI*2);ctx.fill()}ctx.restore()}
 jmRenderScatterMoons(t);
 requestAnimationFrame(frame)
}
function particlesAt(x,y,kind='burst'){const now=performance.now();for(let i=0;i<(kind==='big'?60:22);i++){const a=Math.random()*Math.PI*2,s=rand(40,150);burstParticles.push({x,y,vx:Math.cos(a)*s,vy:Math.sin(a)*s,birth:now,life:rand(360,760),size:rand(2,7),color:pick(['#ffe697','#fff8d7','#f0a2ff','#f7b67f','#ffde55'])})}}
function setGrid(g){grid=g;save()}
function createScatterSuspense(){
 const state={active:false,count:0,completed:0,triggerColumn:-1,beat:0,timer:null};
 state.start=(x)=>{
  if(state.active)return;
  state.active=true;state.triggerColumn=x;state.beat=0;
  document.getElementById('stageWrap')?.classList.add('scatterSuspense');
  scatterSuspenseBeat(0);
  state.timer=setInterval(()=>{
   if(!state.active)return;
   scatterSuspenseBeat(++state.beat);
  },mode==='turbo'?265:345);
  notice('2 SCATTER! Falta um...');
 };
 state.stop=(won)=>{
  if(!state.active)return;
  state.active=false;
  if(state.timer!==null){clearInterval(state.timer);state.timer=null;}
  document.getElementById('stageWrap')?.classList.remove('scatterSuspense');
  scatterSuspenseResolve(won);
  if(won)notice('3 SCATTER! Bónus desbloqueado!');
  else notice('Faltou um SCATTER desta vez.');
 };
 state.onStop=(x,col)=>{
  state.completed++;
  state.count+=col.filter(s=>s.id==='scatter').length;
  if(state.active && state.count>=3)state.stop(true);
  else if(!state.active && state.count===2 && x<4)state.start(x);
  if(state.active && state.completed===5)state.stop(false);
 };
 return state;
}
async function reelDrop(x,next,duration,scatterState=null){
 const old=grid[x];
 const transition={old,next,start:performance.now(),duration};
 transitions[x]=transition;
 await sleep(duration);
 if(scatterState?.active && x>scatterState.triggerColumn){
  // Em modo de suspense, continuar a mostrar símbolos em movimento antes da revelação.
  // A grelha 'next' não é modificada; a pausa apenas prolonga a animação.
  transition.scatterHold=true;
  transition.start=performance.now();
  transition.holdSymbols=Array.from({length:6},(_,k)=>Array.from({length:3},(_,y)=>({id:regular[(x*5+y+k*2)%regular.length]})));
  const extra=(mode==='turbo'?340:750)+(x-scatterState.triggerColumn-1)*(mode==='turbo'?145:315);
  const until=performance.now()+extra;
  while(scatterState.active && performance.now()<until){await sleep(Math.min(75,Math.max(1,until-performance.now())));}
  transition.scatterHold=false;
  transition.old=transition.holdSymbols[0];
  transition.start=performance.now();
  transition.duration=mode==='turbo'?125:195;
  await sleep(transition.duration);
 }
 grid[x]=next;transitions[x]=null;
 soundReelLanding();symbolLandingSounds(x);
 if(scatterState)scatterState.onStop(x,next);
}
async function spinColumns(next){
 const duration=mode==='turbo'?185:520,stagger=mode==='turbo'?70:215;
 const suspense=createScatterSuspense(),jobs=[];
 try{
  for(let x=0;x<5;x++)jobs.push((async()=>{await sleep(stagger*x);await reelDrop(x,next[x],duration,suspense);})());
  await Promise.all(jobs);
 }finally{suspense.stop(false);}
}
// V43: iluminar somente os símbolos vencedores; sem linhas nem sublinhados.
// Mostra um único total por cascata, com desaparecimento progressivo.
/* V56: cada linha apresenta apenas o montante, sem caixa.
   O valor desvanece independentemente da cascata seguinte. */
/* V63: no tabuleiro aparece apenas o prémio desta queda; o acumulado vive
   num contador FIXO à esquerda da bola MULTI até começar a próxima spin. */
/* O prémio potencial pode ultrapassar o cofre, mas nunca é apresentado
   como garantido. A reserva da banca e os jackpots estão fora deste limite. */
function updateFreeExposure(){
 const target=$('jm65FreeExposure'),score=$('jm63DropValue');
 if(!target||!score)return;
 const freeActive=(freeSpins>0||freeAutoRunning||freeSettlePending||freeSettlementRunning||awardContext==='free')&&!freeSummaryPending;
 const base=Math.max(0,Math.floor(Number(score.dataset.cents)||0));
 if(!freeActive||base<=0){target.textContent='';return;}
 const factor=freeMultiplierBalls.length?freeMultiplierBalls.reduce((sum,v)=>sum+v,0):1;
 const possible=Math.min(base*factor,freeBet*MAX_WIN_MULT);
 const available=vaultPayable(possible,freeBet);
 target.textContent=available<possible
  ?'POTENCIAL '+money(possible)+' · COFRE PAGA ATÉ '+money(available)
  :'POTENCIAL ATÉ '+money(available)+' (COFRE)';
 target.setAttribute('aria-label',target.textContent);
}
function updateDropScore(cents){
 const node=$('jm63DropValue');
 if(!node)return;
 const value=Math.max(0,Math.round(Number(cents)||0));
 node.textContent=money(value);node.dataset.cents=String(value);
 node.setAttribute('aria-label','Valor acumulado das quedas: '+money(value));
 node.classList.remove('jm63ScorePulse');
 if(value>0){void node.offsetWidth;node.classList.add('jm63ScorePulse');}
 updateFreeExposure();
}
// V69 — o valor mostrado em cada queda da BASE não excede o montante
// ainda disponível no cofre para esta spin; o pagamento final continua
// limitado ao cofre e ao teto de 5000×. Free spins ficam independentes.
function affordableBaseDrop(requested,accumulated,stake){
 const available=Number.isFinite(prizePool)?Math.max(0,Math.floor(prizePool)):0;
 const maxBase=Math.min(available,Math.floor(Math.max(0,stake)*MAX_WIN_MULT));
 return Math.max(0,Math.min(Math.floor(Number(requested)||0),maxBase-Math.max(0,Math.floor(accumulated))));
}
function markLines(won,visibleAmount=won.reduce((sum,win)=>sum+win.value,0)){
 const lines=$('paylines'),layer=$('floaters'),stage=$('stageWrap');
 lines.replaceChildren();layer.replaceChildren();
 const seen=new Set(),glowDuration=mode==='turbo'?1200:2050;
 for(const win of won){
  for(const [x,y] of win.points){
   const key=x+':'+y;if(seen.has(key))continue;seen.add(key);
   const glow=document.createElement('span');
   glow.className='winningSymbolGlow';
   glow.style.left=((x+.5)/5*100)+'%';
   glow.style.top=((y+.5)/3*100)+'%';
   glow.style.animationDuration=glowDuration+'ms';
   layer.appendChild(glow);
   particlesAt((x+.5)*CW,(y+.5)*CH);
  }
 }
 let wins=$('jm56WinValues');
 if(!wins){wins=document.createElement('div');wins.id='jm56WinValues';stage.appendChild(wins);}
 // Cada queda mostra apenas a soma das linhas premiadas nessa queda.
 const amount=Math.max(0,Math.floor(visibleAmount));
 if(amount===0)return;
 const display=document.createElement('span');
 display.className='jm56WinAmount jm57CenterWin jm63LineAmount';
 display.textContent=money(amount);
 display.style.left='50%';
 display.style.top='50%';
 const duration=mode==='turbo'?2200:3650;
 display.style.setProperty('--win-delay','0ms');
 display.style.setProperty('--win-duration',duration+'ms');
 display.setAttribute('aria-label','Prémio das '+won.length+' linhas nesta queda: '+money(amount));
 wins.replaceChildren(display);
 setTimeout(()=>display.remove(),duration+130);
 audioFX('win');
}
function clearLines(){ $('paylines').innerHTML='';$('floaters').replaceChildren();}
/* V55 — a bola recolhida desaparece e todas as colunas afetadas
   fazem a queda ao mesmo tempo, tanto em Canvas como em Pixi. */
function multiplierPop(x,y,value){
 const stage=$('stageWrap');if(!stage)return;
 const burst=document.createElement('span');
 burst.className='jm55MultiplierPop';burst.textContent=value+'×';
 burst.style.left=((x+.5)*100/5)+'%';burst.style.top=((y+.5)*100/3)+'%';
 stage.append(burst);
 particlesAt((x+.5)*CW,(y+.5)*CH,'big');
 setTimeout(()=>burst.remove(),mode==='turbo'?300:520);
}
function nonMultiplierSymbol(){
 for(let i=0;i<12;i++){const piece=sym();if(piece.id!=='mul'&&piece.id!=='coin')return piece;}
 return {id:pick(regular)};
}
/* V66 — quedas por segmentos, com WILD ancorado no mesmo (x,y).
   Cada WILD ocupa a casa durante a spin atual. O símbolo não desce,
   não é recolhido no prémio, e é substituído numa spin NOVA.
   Os restantes símbolos descem apenas no segmento entre WILDs. */
function buildStickyColumn(col,removedRows,makeSymbol,now=0,duration=440,stagger=100){
 const next=Array(3),items=[];
 let from=0;
 for(let anchor=0;anchor<=3;anchor++){
  if(anchor<3&&col[anchor]?.id!=='wild')continue;
  const survivors=[];
  for(let y=from;y<anchor;y++){
   if(!removedRows.has(y))survivors.push({piece:col[y],fromY:y});
  }
  const count=anchor-from-survivors.length;
  for(let k=0;k<count;k++){
   const to=from+k,piece=makeSymbol();
   next[to]=piece;
   items.push({s:piece,fromY:(from-count+k)*CH,toY:to*CH,start:now+stagger*(k+1),duration});
  }
  survivors.forEach(({piece,fromY},k)=>{
   const to=from+count+k;
   next[to]=piece;
   items.push({s:piece,fromY:fromY*CH,toY:to*CH,start:now,duration});
  });
  if(anchor<3){
   next[anchor]=col[anchor];
   items.push({s:col[anchor],fromY:anchor*CH,toY:anchor*CH,start:now,duration});
  }
  from=anchor+1;
 }
 if(next.some(p=>!p))throw Error('Falha numa casa da cascata sticky');
 return {next,items};
}
async function animateCascade(remove,noMoreMultipliers=false){
 if(!remove.size)return;
 const old=grid,next=old.map(col=>[...col]),changed=[];
 const now=performance.now(),duration=mode==='turbo'?185:440,stagger=mode==='turbo'?35:100;
 for(let x=0;x<5;x++){
  const removed=[...remove].filter(k=>k.startsWith(x+':'))
   .map(k=>Number(k.split(':')[1]))
   .filter(y=>Number.isInteger(y)&&y>=0&&y<3&&old[x][y]?.id!=='wild');
  if(!removed.length)continue;
  const {next:col,items}=buildStickyColumn(old[x],new Set(removed),
   ()=>noMoreMultipliers?nonMultiplierSymbol():sym(),now,duration,stagger);
  next[x]=col;
  changed.push({x,removed,items});
  for(const y of removed)particlesAt((x+.5)*CW,(y+.5)*CH);
 }
 if(!changed.length)return;
 audioFX('burst');
 await sleep(mode==='turbo'?55:145);clearLines();
 for(const {x,items} of changed)cascades[x]={items};
 await sleep(duration+stagger*3+25);
 grid=next;
 for(const {x,removed} of changed){
  cascades[x]=null;
  for(const y of removed)claimedMultiplierCells.delete(x+':'+y);
  soundReelLanding();symbolLandingSounds(x);
 }
}
function mulOrbTop(v,x,y){
 return new Promise(resolve=>{
  const stage=$('stageWrap'),box=$('orbs'),cell=stage.getBoundingClientRect(),top=box.getBoundingClientRect();
  if(!cell.width||!cell.height){resolve();return;}
  const size=mode==='turbo'?25:32;
  const sx=cell.left+(x+.5)*cell.width/5,sy=cell.top+(y+.5)*cell.height/3;
  const dx=top.left+Math.max(8,top.width-18)-sx,dy=top.top+top.height/2-sy;
  const element=document.createElement('span');
  element.className='jm55MultiplierFlight';
  element.textContent=v+'×';element.style.left=(sx-size/2)+'px';element.style.top=(sy-size/2)+'px';
  element.style.width=size+'px';element.style.height=size+'px';
  document.body.append(element);
  const duration=mode==='turbo'?230:470;
  requestAnimationFrame(()=>requestAnimationFrame(()=>{
   element.style.transition='transform '+duration+'ms cubic-bezier(.12,.72,.19,1),opacity '+duration+'ms ease';
   element.style.transform='translate('+dx+'px,'+dy+'px) scale(.50)';
   element.style.opacity='.14';
  }));
  setTimeout(()=>{element.remove();resolve();},duration+40);
 });
}
async function collectReelMultipliers(list,free){
 if(!list.length)return new Set();
 const toRemove=new Set();
 for(const m of list){
  const key=m.x+':'+m.y;
  if(claimedMultiplierCells.has(key))continue;
  claimedMultiplierCells.add(key);toRemove.add(key);
  shelf.push(m.v);
  multiplierPop(m.x,m.y,m.v);
  audioFX('multiplier',m.v);
 }
 updateShelf();
 if(free)save();
 await Promise.all(list.filter(m=>toRemove.has(m.x+':'+m.y)).map(m=>mulOrbTop(m.v,m.x,m.y)));
 return toRemove;
}
/* V56: só UMA bola vermelha visível. O seu número é a soma atual;
   a lista de fatores continua guardada no estado para o prémio final. */
function updateShelf(){
 const box=$('orbs');
 const factor=shelf.length?shelf.reduce((sum,v)=>sum+v,0):1;
 const freeActive=freeSpins>0||freeAutoRunning||freeSettlePending||freeSummaryPending;
 const tag=document.querySelector('.shelfTag');
 if(tag)tag.textContent='MULTI';
 box.replaceChildren();
 const orb=document.createElement('span');
 orb.className='orbPill jm56RedBall';
 const digits=document.createElement('strong');digits.className='jm62MultiDigits';digits.textContent=String(factor);
 digits.style.fontSize=factor>=1000?'15px':factor>=100?'17px':factor>=10?'21px':'25px';
 const multiply=document.createElement('small');multiply.className='jm62MultiX';multiply.textContent='×';
 orb.append(digits,multiply);
 orb.title=freeActive?'Multiplicador acumulado das free spins: ×'+factor:'Multiplicador da jogada: ×'+factor;
 orb.setAttribute('aria-label',orb.title);
 box.append(orb);
 $('shelfTotal').textContent='×'+factor;
 $('shelfTotal').title=orb.title;
 updateFreeExposure(); // Bola × alterada: atualizar imediatamente o montante pagável do cofre.
}
/* V52 — são seis moedas visíveis simultaneamente nos 5 tambores (5×3).
   A chuva é apenas visual e o valor impresso em cada moeda é fictício. */
function countReelCoins(){return grid.reduce((total,col)=>total+col.filter(s=>s?.id==='coin').length,0);}
async function showSixCoinCascade(){
 const stage=$('stageWrap');if(!stage)return;
 const layer=document.createElement('div');layer.className='jm52CoinRain';
 stage.append(layer);audioFX('bonus');
 const count=mode==='turbo'?55:105;
 for(let i=0;i<count;i++){
  const coin=document.createElement('span');coin.className='jm52RainCoin';
  coin.textContent='€';
  coin.style.left=(2+Math.random()*96).toFixed(2)+'%';
  coin.style.setProperty('--fall-delay',(Math.random()*(mode==='turbo'?.35:.70)).toFixed(3)+'s');
  coin.style.setProperty('--fall-time',((mode==='turbo'?.9:1.45)+Math.random()*.38).toFixed(3)+'s');
  coin.style.setProperty('--fall-shift',((Math.random()-.5)*160).toFixed(1)+'px');
  coin.style.setProperty('--fall-rotate',((Math.random()-.5)*870).toFixed(1)+'deg');
  coin.style.setProperty('--fall-scale',(.48+Math.random()*1.12).toFixed(2));
  layer.append(coin);
 }
 notice('🪙 SEIS MOEDAS! HOLD & WIN DESBLOQUEADO!');
 for(let i=0;i<7;i++)setTimeout(()=>audioFX('coin'),i*(mode==='turbo'?74:155));
 await sleep(mode==='turbo'?1320:2450);
 layer.remove();
}

async function doRound(){
 if(running||bonus||!session||freeSettlePending||freeSummaryPending||freeSettlementRunning)return;
 const free=freeSpins>0,stake=free?0:BETS[betIdx],betAmount=free?freeBet:BETS[betIdx];if(balance<stake)return;
 running=true;scatterNoteStep=0;awardContext=free?'free':'base';roundVaultNew=0;
 $('jm56WinValues')?.replaceChildren(); // Uma nova spin não herda o valor visual da anterior.
 window.JM_WINCELEBRATION_V60?.clear();
 let lastCascadeCelebration=0; // Nível mais alto já celebrado nesta spin.
 const paidWildOnly=new Set(); // Par de WILDs / linha só de WILDs não repete em cascatas seguintes.
 if(free){freeSpins--;freeSummaryCount++;}
 else{balance-=stake;session.stake+=stake;fundWager(stake);log('aposta',-stake,'Rodada base · cofre '+money(prizePool));}
 session.spins++;update();soundStart();notice(free?'✦ Rodada grátis!':'Tambores a rodar...');shelf=free?freeMultiplierBalls:[];updateShelf();clearLines();winRound=0;updateDropScore(free?freeBaseTotal:0);
 let sixCoinTrigger=false;
 const detectSixCoins=async()=>{if(!sixCoinTrigger&&countReelCoins()>=6){sixCoinTrigger=true;await showSixCoinCascade();}};
 await spinColumns(wholeGrid());await detectSixCoins();let scatterTrigger=0,cascadeCount=0;const maxCascades=12;
 while(cascadeCount++<maxCascades){
  scatterTrigger=Math.max(scatterTrigger,scatterCount(grid));
  const ev=evaluate(grid,betAmount,paidWildOnly),m=mulIndices(grid);
  const hasCoins=grid.some(col=>col.some(piece=>piece?.id==='coin'&&!countedCoins.has(piece.uid)));
  if(!ev.won.length&&!m.length&&!hasCoins)break;
  if(ev.won.length){
   // Na base, não anunciar um valor maior do que o cofre suporta.
   // O WILD continua com as mesmas regras de contagem e não se mexe
   // no sorteio nem nas posições dos símbolos vencedores.
   const payableDrop=free?ev.amount:affordableBaseDrop(ev.amount,winRound,betAmount);
   winRound+=payableDrop;
   updateDropScore(free?freeBaseTotal+winRound:winRound);
   if(payableDrop>0){
    markLines(ev.won,payableDrop); // Centro: só a queda pagável, não o valor bruto.
    notice('Linha premiada: '+money(payableDrop)+' • cascata '+cascadeCount+(payableDrop<ev.amount?' • limitado pelo cofre':''));
    await sleep(mode==='turbo'?690:1380);
   }else if(!free){
    notice('Cofre sem fundos disponíveis para pagar esta queda.');
   }
  }
  const remove=new Set();
  for(const line of ev.won)for(const [x,y] of line.points){
   // V66: nunca remover um WILD vencedor: permanece fixo nesta spin.
   if(grid[x][y]?.id!=='wild')remove.add(x+':'+y);
  }
  const collectedMultis=await collectReelMultipliers(m,free);
  for(const key of collectedMultis)remove.add(key);
  // A soma de todas as quedas + os multiplicadores pode subir de NICE a MEGA,
  // SUPER, ÉPICO ou SENSACIONAL antes da cascata terminar.
  // Só se celebra o montante elegível neste instante (já limitado pelo cofre).
  if(!free&&winRound>0&&window.JM_WINCELEBRATION_V60){
   const currentFactor=shelf.length?shelf.reduce((sum,v)=>sum+v,0):1;
   const eligible=Math.min(prizePool,winRound*currentFactor,betAmount*MAX_WIN_MULT);
   const currentTier=window.JM_WINCELEBRATION_V60.tierFor(eligible,betAmount);
   if(currentTier&&currentTier.min>lastCascadeCelebration){
    lastCascadeCelebration=currentTier.min;
    await window.JM_WINCELEBRATION_V60.play({
     value:eligible,bet:betAmount,turbo:mode==='turbo',
     playSound:tier=>{
      audioFX(tier.min>=50?'big':'win');
      if(tier.min>=100)audioFX('schoolBell');
      if(tier.min>=25)particlesAt(W/2,H/2,'big');
     }
    });
   }
  }
  const collectedCoins=await collectVaultCoins();
  for(const key of collectedCoins)remove.add(key);
  if(!remove.size)break;
  await animateCascade(remove,cascadeCount>=maxCascades);
  scatterTrigger=Math.max(scatterTrigger,scatterCount(grid));
  await detectSixCoins();
 }
 scatterTrigger=Math.max(scatterTrigger,scatterCount(grid));
 clearLines();
 const factor=shelf.length?shelf.reduce((a,b)=>a+b,0):1;
 let requestedWin=0,winnings=0;
 if(free){
  // A base é acumulada em cêntimos: não se multiplica nem se paga em cada giro.
  freeBaseTotal=Math.min(Number.MAX_SAFE_INTEGER,freeBaseTotal+winRound);
  updateDropScore(freeBaseTotal); // Mantém o valor visível mesmo depois de acabar esta free spin.
  lastPrize=0;
  notice('FREE SPINS · Base '+money(freeBaseTotal)+' · MULTIPLICADORES ×'+factor+' · Pagamento no final');
 }else{
  requestedWin=Math.min(winRound*factor,betAmount*MAX_WIN_MULT);
  winnings=payFromVault(requestedWin);lastPrize=winnings;
  if(winnings)log('prémio',winnings,`Cascatas; linhas ${money(winRound)} ×${factor}; pedido ${money(requestedWin)}; cofre ${money(prizePool)}`);
  if(winnings<requestedWin)log('limite_cofre',0,`Prémio limitado pelo fundo: ${money(requestedWin)} → ${money(winnings)}`);
 }
 // SCATTER: na base, 3 dão 15 free spins. Durante as free spins,
 // 2 dão mais 8 e 3 ou mais dão 15. Máximo uma extensão por spin,
 // mesmo que os SCATTER surjam entre várias cascatas na mesma jogada.
 const scatterAward=free?(scatterTrigger>=3?15:scatterTrigger===2?8:0):(scatterTrigger>=3?15:0);
 // Bónus muito longos (por ex. 50+ spins) deixam de poder crescer indefinidamente.
 // Os SCATTER continuam a sair de forma aleatória, mas a regra de 2 extensões é pública.
 const extraFreeSpins=free&&freeRetriggersUsed>=MAX_FREE_RETRIGGERS?0:scatterAward;
 if(extraFreeSpins){
  if(!free){freeBonusTotal=0;freeSummaryCount=0;freeSummaryPending=false;freeBaseTotal=0;freeMultiplierBalls=[];freeSettlePending=false;freeRetriggersUsed=0;}
  freeSpins+=extraFreeSpins;
  if(free)freeRetriggersUsed++;
  freeBet=betAmount;
  notice('✦ '+scatterTrigger+' SCATTER: +'+extraFreeSpins+' FREE SPINS! · Restam '+freeSpins);
  log('scatter',0,scatterTrigger+' SCATTER: +'+extraFreeSpins+' free spins; total restante '+freeSpins);
  await showFeatureIntro('free',extraFreeSpins,free);
 }
 else if(free&&scatterAward>0)notice(scatterTrigger+' SCATTER · Limite de '+MAX_FREE_RETRIGGERS+' extensões atingido · Restam '+freeSpins+' jogadas.');
 else if(free)notice('FREE SPINS: '+money(freeBaseTotal)+' base, ×'+factor+' acumulado até ao final.');
 else if(winnings)notice(`Prémio pago: ${money(winnings)} • ${factor}×${winnings<requestedWin?' • limite do cofre':''}`);
 else notice('Sem prémio desta vez.');
 if(free&&freeSpins===0){freeSettlePending=true;save();}
 if(sixCoinTrigger){
  running=false;await launchBonus(false,'6_moedas');
 }else if(!free&&scatterTrigger>=3){
  running=false;openFreeStart();
 }else if(!free&&maybeTriggerVault()){
  await vaultExplosion();running=false;await launchBonus(false,'cofre');
 }else{
  // V60: celebração só do valor REAL pago. Não mexe no sorteio nem nos jackpots.
  if(!free&&winnings>0&&window.JM_WINCELEBRATION_V60?.tierFor(winnings,betAmount)?.min>lastCascadeCelebration){
   await window.JM_WINCELEBRATION_V60.play({
    value:winnings,bet:betAmount,turbo:mode==='turbo',
    playSound:tier=>{
     audioFX(tier.min>=50?'big':'win');
     if(tier.min>=100)audioFX('schoolBell');
     if(tier.min>=25)particlesAt(W/2,H/2,'big');
    }
   });
  }
  running=false;update();save();
 }
 if(freeAutoRunning)scheduleFreeAuto();else maybeContinueAuto();
}


// Hold & Win V51: 4×4 = 16 casas. Bónus antigos 5×5 / 8×8 guardados podem terminar sem perda de peças.
const BONUS_SIDE=4,BONUS_CELLS=BONUS_SIDE*BONUS_SIDE;
const BONUS_FACTORS=[2,3,5,10,20,50,100,1000];
function bonusFactorTotal(){return bonus?.factors.length?bonus.factors.reduce((a,b)=>a+b,0):1;}
function randomEmptyCells(){const list=[];for(let i=0;i<bonus.cells.length;i++)if(!bonus.cells[i])list.push(i);for(let i=list.length-1;i>0;i--){const j=R(i+1);[list[i],list[j]]=[list[j],list[i]];}return list;}
function addBonusCoin(i,value){if(bonus.cells[i])return false;const paid=roundedPayout(value*rtpScaleFor('hold'));bonus.cells[i]={kind:'coin',value:paid};bonus.coinsTotal+=paid;return true;}
function addBonusFactor(i,value){if(bonus.cells[i])return false;bonus.cells[i]={kind:'factor',value};bonus.factors.push(value);return true;}
function addBonusGem(i,type){if(bonus.cells[i]||bonus.gems[type]>=4)return false;bonus.cells[i]={kind:'gem',type};bonus.gems[type]++;audioFX('gem');if(bonus.gems[type]===4){bonus.jackpot=type;bonus.finished=true;bonus.endReason='jackpot';}return true;}
function diamondSvg(jp){return `<svg class="bonusCrystal" style="--crystal-main:${jp.color}" viewBox="0 0 100 105" role="img" aria-label="Diamante de cristal" xmlns="http://www.w3.org/2000/svg"><defs><linearGradient id="facet${jp.mult}" x1="0" x2="1" y1="0" y2="1"><stop offset="0" stop-color="white" stop-opacity=".96"/><stop offset=".35" stop-color="${jp.color}"/><stop offset="1" stop-color="#18112a"/></linearGradient></defs><path d="M25 10 L75 10 L97 37 L50 98 L3 37 Z" fill="url(#facet${jp.mult})" stroke="#fff4e1" stroke-width="3"/><path d="M25 10 L37 38 L3 37 Z" fill="white" opacity=".46"/><path d="M75 10 L63 38 L97 37 Z" fill="white" opacity=".22"/><path d="M37 38 L63 38 L50 98 Z" fill="white" opacity=".30"/><path d="M3 37 L37 38 L50 98 Z" fill="#130b29" opacity=".32"/><path d="M97 37 L63 38 L50 98 Z" fill="#1d1438" opacity=".45"/><path d="M25 10 L37 38 L63 38 L75 10 Z" fill="white" opacity=".36"/><path d="M50 6 L55 19 L50 15 L45 19Z" fill="white" opacity=".9"/></svg>`;}
function gemProgress(){
 $('gemCounters').innerHTML=Object.entries(JACKPOTS).map(([kind,data])=>`<div class="gemCounter ${bonus.jackpot===kind?'complete':''}" style="--jp-color:${data.color};color:${data.color}"><b>${kind}</b><span class="jpBonusValue">${money(jackpotPayable(kind,bonus.stakeBet))}</span><small>${Array.from({length:4},(_,i)=>`<span class="gemTick ${i<bonus.gems[kind]?'got':''}">◆</span>`).join('')}</small></div>`).join('');
}
function bonusCellMarkup(c){
 if(c.kind==='gem')return diamondSvg(JACKPOTS[c.type]);
 if(c.kind==='factor')return `<div class="bonusToken factor" aria-label="Multiplicador ${c.value} vezes"><strong>${c.value}×</strong><small>MULTI</small></div>`;
 return `<div class="bonusToken cash" aria-label="Moeda de ${money(c.value)}"><strong>${(c.value/100).toLocaleString('pt-PT',{minimumFractionDigits:2,maximumFractionDigits:2})}</strong><small>MOEDA</small></div>`;
}
function renderBonus(newIndices=[]){
 if(!bonus)return;
 const boardSide=Math.sqrt(bonus.cells.length);
 const boardTitle=$('bonusModal')?.querySelector('h2');
 if(boardTitle)boardTitle.textContent='🪙 HOLD & WIN '+boardSide+'×'+boardSide;
 const board=$('bonusGrid');
 board.setAttribute('aria-label','Grelha '+boardSide+' colunas por '+boardSide+' linhas');
 const boardHelp=$('bonusModal')?.querySelector('.bonusHelpLine');
 if(boardHelp)boardHelp.textContent='🪙 Moedas fixas · 💎 4 cristais iguais = jackpot · 🎡 '+bonus.cells.length+' casas completas = roleta de 32 setores (com RESPIN automático)';
 $('bonusAttempts').textContent=bonus.left;
 $('bonusSum').textContent=money(bonus.coinsTotal);
 gemProgress();
 const newer=new Set(newIndices),frag=document.createDocumentFragment();
 $('bonusGrid').style.gridTemplateColumns='repeat('+(bonus.cells.length===64?8:bonus.cells.length===25?5:4)+',minmax(0,1fr))';
 for(let i=0;i<bonus.cells.length;i++){
  const c=bonus.cells[i],el=document.createElement('div');
  el.className='bonusCell '+(c?(c.kind==='coin'?'coinCell':c.kind==='factor'?'factorCell':'gemCell'):'emptyCell')+(newer.has(i)?' newItem':'');
  el.dataset.cell=i;
  if(c){
   if(c.kind==='gem'){const jp=JACKPOTS[c.type];el.style.setProperty('--crystal-main',jp.color);el.style.setProperty('--crystal-glow',jp.color+'55');el.title=c.type+' • cristal '+bonus.gems[c.type]+'/4';}
   else el.title=c.kind==='factor'?'Multiplicador '+c.value+'×':'Moeda '+money(c.value);
   el.innerHTML=bonusCellMarkup(c);
  }else el.textContent='✧';
  frag.appendChild(el);
 }
 $('bonusGrid').replaceChildren(frag);
 $('bonusFactors').innerHTML=bonus.factors.length?bonus.factors.map(v=>`<span>${v}×</span>`).join(''):'×1';
 const holdFactor=bonusFactorTotal();
 const potential=bonus.coinsTotal*holdFactor;
 const baseMax=Math.min(potential,bonus.stakeBet*MAX_WIN_MULT);
 const basePayable=vaultPayable(baseMax,bonus.stakeBet);
 $('bonusFormula').textContent=`${money(bonus.coinsTotal)} × ${holdFactor} = ${money(potential)} (POTENCIAL)${bonus.jackpot?' + '+bonus.jackpot+' '+money(jackpotPayable(bonus.jackpot,bonus.stakeBet)):''}`;
 const payableLabel=$('jm65BonusPayable');
 if(payableLabel)payableLabel.textContent='BASE PAGÁVEL PELO COFRE: '+money(basePayable)+(basePayable<baseMax?' · LIMITE DE FUNDOS':'')+' · JACKPOTS EM FUNDOS SEPARADOS';
 $('bonusSpin').textContent=bonus.wheelPending?'ROLETA A GIRAR…':bonus.finished?'RECOLHER PRÉMIO':'RODAR BÓNUS';
 $('bonusSpin').disabled=bonus.inMotion||!!bonus.wheelPending;
}
function flyFactorToBox(index,value){
 const from=$('bonusGrid').querySelector(`[data-cell="${index}"]`), to=$('bonusFactors');
 if(!from||!to)return Promise.resolve();
 const a=from.getBoundingClientRect(),b=to.getBoundingClientRect(),node=document.createElement('div');
 node.className='factorFly';node.textContent=value+'×';node.style.left=(a.left+a.width/2-22)+'px';node.style.top=(a.top+a.height/2-22)+'px';
 document.body.appendChild(node);
 const dx=b.left+b.width/2-(a.left+a.width/2),dy=b.top+b.height/2-(a.top+a.height/2);
 requestAnimationFrame(()=>{node.style.transform=`translate(${dx}px,${dy}px) scale(.6)`;node.style.opacity='.05';});
 audioFX('multiplier',value);return new Promise(resolve=>setTimeout(()=>{node.remove();resolve()},mode==='turbo'?310:590));
}
function flyGemToCounter(index,type){
 const from=$('bonusGrid').querySelector(`[data-cell="${index}"]`),to=Array.from($('gemCounters').children).find(n=>n.textContent.includes(type));
 if(!from||!to)return Promise.resolve();
 const a=from.getBoundingClientRect(),b=to.getBoundingClientRect(),node=document.createElement('div');
 node.className='gemFly';node.style.setProperty('--gem-color',JACKPOTS[type].color);node.innerHTML=diamondSvg(JACKPOTS[type]);
 node.style.left=(a.left+a.width/2-23)+'px';node.style.top=(a.top+a.height/2-23)+'px';document.body.appendChild(node);
 const dx=b.left+b.width/2-(a.left+a.width/2),dy=b.top+b.height/2-(a.top+a.height/2);
 requestAnimationFrame(()=>{node.style.transform=`translate(${dx}px,${dy}px) scale(.40)`;node.style.opacity='.06';});
 audioFX('gem');return new Promise(resolve=>setTimeout(()=>{node.remove();resolve()},mode==='turbo'?350:630));
}
function placeRandomBonusItem(index){
 const r=Math.random();
 if(r<.820)return addBonusCoin(index,bonus.stakeBet*pick([1,1,1,2,2,3,5,5,8,10]));
 if(r<.870)return addBonusFactor(index,chooseHoldFactor());
 const kind=Math.random()<.58?bonus.focus:pick(Object.keys(JACKPOTS));
 return addBonusGem(index,kind);
}
async function launchBonus(purchase=true,origin='compra'){
 if(!session||running||bonus)return false;
 const cost=BETS[betIdx]*100;if(purchase&&balance<cost)return false;
 running=true;
 if(purchase){balance-=cost;session.stake+=cost;fundWager(cost);session.bonuses++;log('compra_hold_win',-cost,'Hold & Win 4×4 — 100× aposta');}
 else{session.bonuses++;log(origin==='6_moedas'?'ativacao_6_moedas':'ativacao_cofre',0,origin==='6_moedas'?'Hold & Win 4×4: seis moedas nos cinco tambores':'Hold & Win 4×4 ativado pelo cofre mágico');}
 bonus={left:3,coinsTotal:0,cells:Array(BONUS_CELLS).fill(null),factors:[],gems:{ULTRA:0,MAX:0,MED:0,MIN:0},jackpot:null,focus:pick(Object.keys(JACKPOTS)),finished:false,endReason:null,inMotion:false,stakeBet:BETS[betIdx],origin,rolls:0};
 const spots=randomEmptyCells();
 for(let i=0;i<7;i++)addBonusCoin(spots[i],bonus.stakeBet*pick([1,2,2,3,5,8,10]));
 addBonusGem(spots[7],bonus.focus);
 if(Math.random()<.012)addBonusFactor(spots[8],chooseHoldFactor());
 renderBonus(spots.slice(0,9));
 spots.slice(0,9).forEach((_,i)=>setTimeout(()=>audioFX('lock'),i*75));
 $('bonusNote').textContent='3 tentativas. Cada símbolo novo repõe as 3. Sem novidades, perdes uma tentativa.';
 modal('buyModal',false);soundStart();await showFeatureIntro('hold');modal('bonusModal',true);
 running=false;update();save();return true;
}
/* V44 — roleta visual independente em todas as casas livres do Hold & Win.
   O resultado é calculado pelo jogo antes desta animação; a animação nunca sorteia prémios. */
async function animateBonusRoulette(added){
 const board=$('bonusGrid');
 const squares=Array.from(board.querySelectorAll('.bonusCell.emptyCell'));
 if(!squares.length)return;
 const reduce=!!(window.matchMedia&&window.matchMedia('(prefers-reduced-motion: reduce)').matches);
 if(reduce){await sleep(100);return;}
 const quick=mode==='turbo',rounds=quick?10:16,frameDelay=quick?49:72;
 const faces=['🪙','2×','💎','✦','🪙','5×','◆','10×','🪙','✧'];
 const landed=new Set(added);
 for(let tick=0;tick<rounds;tick++){
  for(let i=0;i<squares.length;i++){
   const cell=squares[i],cellIndex=Number(cell.dataset.cell);
   const stopAt=rounds-5+(i%5);
   if(tick>=stopAt){
    if(!cell.classList.contains('jmRouletteStopped')){
     cell.classList.remove('jmRouletteRolling');
     cell.classList.add('jmRouletteStopped');
     const prize=bonus.cells[cellIndex];
     if(prize){
      cell.classList.add('jmRouletteHit');
      cell.innerHTML=bonusCellMarkup(prize);
      if(landed.has(cellIndex))audioFX(prize.kind==='factor'?'ballDrop':'lock',prize.value);
     }else cell.textContent='✧';
    }
   }else{
    cell.classList.add('jmRouletteRolling');
    cell.innerHTML='<span class="jmRouletteToken">'+faces[(tick+i*3)%faces.length]+'</span>';
   }
  }
  await sleep(frameDelay);
 }
}

/* V49: 32 casas. 16 MIN, 8 MED, 5 MAX, 1 ULTRA e 2 RESPIN.
   Setores alternados para serem legíveis na roda, sem alterar a sua probabilidade. */
const JACKPOT_WHEEL_32=Array(32).fill('MIN');
[[1,'MAX'],[3,'MED'],[5,'RESPIN'],[7,'MED'],[9,'MAX'],[11,'MED'],[13,'MAX'],[15,'MED'],[17,'ULTRA'],[19,'MED'],[21,'MAX'],[23,'MED'],[25,'RESPIN'],[27,'MED'],[29,'MAX'],[31,'MED']].forEach(([i,label])=>{JACKPOT_WHEEL_32[i]=label;});
function makeFullBoardWheelStops(){
 const steps=[];
 for(let roll=0;roll<64;roll++){
  const index=R(32);steps.push(index);
  if(JACKPOT_WHEEL_32[index]!=='RESPIN')return steps;
 }
 steps.push(0); // Proteção contra uma sequência teoricamente ilimitada de RESPIN.
 return steps;
}
function prepareFullBoardWheel(){
 if(!bonus||bonus.endReason!=='completo'||!bonus.cells.every(Boolean))return false;
 if(!Array.isArray(bonus.wheelStops)||!bonus.wheelStops.length){
  bonus.wheelStops=makeFullBoardWheelStops();
  bonus.wheelResult=JACKPOT_WHEEL_32[bonus.wheelStops[bonus.wheelStops.length-1]];
  bonus.wheelPending=true;
  save(); // Sorteio e RESPINs persistidos antes de iniciar qualquer efeito visual.
 }
 return true;
}
async function playFullBoardWheel(){
 if(!bonus||!bonus.wheelPending)return;
 const state=bonus;
 playBonusJingle('roleta');
 state.inMotion=true;renderBonus();
 modal('bonusModal',false);
 notice('TABULEIRO COMPLETO! Carrega em GIRO para parar a roleta.');
 // Montante exato que a roleta pode pagar, já descontando prémio base e cristal.
 const cap=state.stakeBet*MAX_WIN_MULT;
 const basePaid=Math.min(prizePool,Math.min(state.coinsTotal*bonusFactorTotal(),cap));
 const crystalPaid=state.jackpot?Math.min(jackpotPayable(state.jackpot,state.stakeBet),Math.max(0,cap-basePaid)):0;
 const rawWheel=jackpotPayable(state.wheelResult,state.stakeBet);
 const wheelProjected=Math.max(0,Math.min(rawWheel-(state.wheelResult===state.jackpot?crystalPaid:0),cap-basePaid-crystalPaid));
 let confirmed=false;
 try{
  if(window.JM_WHEEL_V49&&typeof window.JM_WHEEL_V49.play==='function'){
   await window.JM_WHEEL_V49.play({
    slots:JACKPOT_WHEEL_32,stops:state.wheelStops,boardCells:state.cells.length,turbo:mode==='turbo',
    amount:money(wheelProjected),
    onSpin:()=>audioFX('bonus'),
    onStop:(type)=>{audioFX(type==='RESPIN'?'lock':'big');if(type!=='RESPIN')audioFX('schoolBell');}
   });
   confirmed=true;
  }else{
   console.warn('Roleta não carregou: o resultado permanece guardado, sem pagar ou fechar.');
  }
 }catch(error){console.warn('Roleta indisponível; o sorteio não foi repetido.',error);}
 if(bonus!==state)return;
 state.inMotion=false;
 if(!confirmed){modal('bonusModal',true);renderBonus();save();return;}
 state.wheelPending=false;
 save();
 // Depois de GIRO · CONTINUAR, pagar uma vez e voltar à máquina (sem popup duplicado).
 bonusFinish();
}

async function bonusRespin(){
 if(!bonus||bonus.inMotion)return;
 if(bonus.finished){if(bonus.wheelPending)await playFullBoardWheel();else bonusFinish();return;}
 bonus.inMotion=true;renderBonus();$('bonusNote').textContent='A procurar moedas, multiplicadores e cristais…';
 bonus.rolls++;
 const empties=randomEmptyCells(),added=[],newFactors=[],newGems=[];
 // Cada tentativa pode falhar. Não existe jackpot atribuído artificialmente.
 let number=0;
 if(Math.random()>=.495){number=1+(Math.random()<.39?1:0)+(Math.random()<.17?1:0)}
 for(let i=0;i<number&&empties.length&&!bonus.finished;i++){
  const idx=empties.pop();if(!placeRandomBonusItem(idx))continue;
  added.push(idx);if(bonus.cells[idx].kind==='factor')newFactors.push([idx,bonus.cells[idx].value]);if(bonus.cells[idx].kind==='gem')newGems.push([idx,bonus.cells[idx].type]);
 }
 bonus.left=added.length?3:Math.max(0,bonus.left-1);
 if(bonus.cells.every(Boolean)){
  bonus.finished=true;bonus.endReason='completo'; // A grelha completa (16 casas para bónus novos) ativa a roleta mesmo no último respin.
 }else if(!bonus.finished&&bonus.left===0){
  bonus.finished=true;bonus.endReason='tentativas';
 }
 await animateBonusRoulette(added);
 renderBonus(added);
 if(newFactors.length||newGems.length){await Promise.all([...newFactors.map(([idx,v])=>flyFactorToBox(idx,v)),...newGems.map(([idx,type])=>flyGemToCounter(idx,type))]);}
 if(added.length){audioFX('coin');}
 if(bonus.finished&&bonus.endReason==='completo'&&bonus.cells.every(Boolean)){
  prepareFullBoardWheel();
  await playFullBoardWheel();
  if(!bonus)return; // A roleta confirmou e liquidou o bónus, sem segundo popup.
 }
 bonus.inMotion=false;renderBonus(added);
 if(bonus.finished){
  if(bonus.wheelResult){$('bonusNote').textContent='🏆 ROleta de jackpots: '+bonus.wheelResult+'! Carrega para recolher o prémio.';}
  else if(bonus.jackpot){$('bonusNote').textContent='🏆 4 CRISTAIS '+bonus.jackpot+'! Valor reservado: '+money(jackpotPayable(bonus.jackpot,bonus.stakeBet));audioFX('big');audioFX('schoolBell');}
  else if(bonus.endReason==='tentativas')$('bonusNote').textContent='Esgotaste as 3 tentativas. Recolhe as moedas e multiplicadores!';
  else $('bonusNote').textContent='Grelha completa. Recolhe os prémios!';
 }else if(added.length){
  $('bonusNote').textContent=`Caíram ${added.length} novo(s) símbolo(s). Tens novamente 3 tentativas!`;
 }else $('bonusNote').textContent=`Não caiu nada. Restam ${bonus.left} tentativa(s).`;
 save();
}
function bonusFinish(){
 if(!bonus||!bonus.finished||bonus.inMotion||bonus.wheelPending)return;
 const base=bonus.coinsTotal,factor=bonusFactorTotal(),bonusStake=bonus.stakeBet;
 const crystalJackpot=bonus.jackpot,wheelJackpot=bonus.wheelResult||null;
 const rawCash=base*factor,cap=bonus.stakeBet*MAX_WIN_MULT;
 const requestedCash=Math.min(rawCash,cap);
 const cashPaid=payFromVault(requestedCash);
 const crystalPaid=crystalJackpot?payProgressive(crystalJackpot,bonus.stakeBet,Math.max(0,cap-cashPaid)):0;
 const wheelPaid=wheelJackpot?payProgressive(wheelJackpot,bonus.stakeBet,Math.max(0,cap-cashPaid-crystalPaid)):0;
 const jackpotWin=crystalPaid+wheelPaid,final=cashPaid+jackpotWin;
 const capped=cashPaid<requestedCash?'cofre':rawCash>cap?'limite':null;
 lastPrize=final;
 log('prémio_hold_win',final,`Hold & Win: moedas ${money(base)} ×${factor}; pago cofre geral ${money(cashPaid)}; cristais ${crystalJackpot||'—'} ${money(crystalPaid)}; roleta ${wheelJackpot||'—'} ${money(wheelPaid)}; total ${money(final)} · ${bonus.endReason}`);
 modal('bonusModal',false);bonus=null;update();save();
 if(wheelJackpot){notice('🏆 JACKPOT '+wheelJackpot+': '+money(wheelPaid)+' · TOTAL '+money(final));}
 else showBig(final,base,factor,wheelJackpot||crystalJackpot,jackpotWin,capped,false,bonusStake);
 if(freeSettlePending&&!$('bigWin').classList.contains('show'))showFreeSummary();
 else if(freeAutoRunning)scheduleFreeAuto();
 else if(freeSpins>0&&!freeStartPending&&!freeSummaryPending)openFreeStart();
 else if(autoSpinsRemaining>0&&!$('bigWin').classList.contains('show'))maybeContinueAuto();
}
function refreshBuyOptions(){const costHold=BETS[betIdx]*100,costFree=BETS[betIdx]*20;$('buyHoldPrice').textContent=money(costHold);$('buyFreePrice').textContent=money(costFree);$('buyHoldChoice').disabled=balance<costHold||!session||running||!!bonus||freeSpins>0;$('buyFreeChoice').disabled=balance<costFree||!session||running||!!bonus||freeSpins>0;$('buyInfo').textContent='Saldo '+money(balance)+' • Hold & Win 100× a aposta • 15 free spins 20× a aposta';}
function openBonusPurchase(){if(!session||running||bonus)return;refreshBuyOptions();modal('buyModal',true);soundStart()}
async function buyFreeSpins(){if(!session||running||bonus||freeSpins||freeSummaryPending)return false;const bet=BETS[betIdx],cost=bet*20;if(balance<cost)return false;balance-=cost;session.stake+=cost;fundWager(cost);session.bonuses++;freeBonusTotal=0;freeSummaryCount=0;freeSummaryPending=false;freeBaseTotal=0;freeMultiplierBalls=[];freeSettlePending=false;freeRetriggersUsed=0;freeBet=bet;freeSpins+=15;updateDropScore(0);log('compra_freespins',-cost,'15 free spins a 20× aposta '+money(bet));modal('buyModal',false);notice('🌟 Compraste 15 FREE SPINS por '+money(cost));running=true;update();soundStart();await showFeatureIntro('free');running=false;openFreeStart();return true;}

function showBig(final,base,factor,jp,jackpotWin,capped=null,fromWheel=false,stake=BETS[betIdx]){
 $('bigFactor').textContent=fromWheel?'ROULETA 32 CASAS • '+jp:jp?'4 CRISTAIS • '+jp:'HOLD & WIN • 3 TENTATIVAS';
 const tier=window.JM_WINCELEBRATION_V60?.tierFor(final,stake);
 $('bigTitle').textContent=jp?'JACKPOT '+jp+'!':tier?tier.label+'!':'TOTAL HOLD & WIN';
 $('bigAmount').textContent=money(final);
 $('bigMath').textContent=`${money(base)} × ${factor}${jp?' + '+jp+' '+money(jackpotWin):''} = ${money(base*factor+jackpotWin)}${capped?' • PAGO '+money(final)+(capped==='cofre'?' (limite do cofre)':' (limite 5000×)'):''}`;
 $('bigWin').classList.add('show');audioFX('big');if(final>0)audioFX('schoolBell');particlesAt(W/2,H/2,'big');
}
function drawWinLineMaps(){
 const box=$('payLinesGrid');box.replaceChildren();
 LINES.forEach((pattern,index)=>{
  const card=document.createElement('div');card.className='payLineCard';
  const num=document.createElement('b');num.className='lineNumber';num.textContent=String(index+1);card.append(num);
  const svg=document.createElementNS('http://www.w3.org/2000/svg','svg');
  svg.setAttribute('viewBox','0 0 110 74');svg.setAttribute('role','img');svg.setAttribute('aria-label','Linha '+(index+1));
  for(let row=0;row<3;row++)for(let col=0;col<5;col++){
   const cell=document.createElementNS('http://www.w3.org/2000/svg','rect');
   cell.setAttribute('x',String(3+col*21));cell.setAttribute('y',String(4+row*21));
   cell.setAttribute('width','21');cell.setAttribute('height','21');
   cell.setAttribute('fill','none');cell.setAttribute('stroke','#e5b76b');cell.setAttribute('stroke-width','0.9');
   svg.append(cell);
  }
  const line=document.createElementNS('http://www.w3.org/2000/svg','polyline');
  line.setAttribute('points',pattern.map((row,col)=>`${13.5+col*21},${14.5+row*21}`).join(' '));
  line.setAttribute('fill','none');line.setAttribute('stroke','#ffe75a');line.setAttribute('stroke-width','5');
  line.setAttribute('stroke-linecap','round');line.setAttribute('stroke-linejoin','round');
  svg.append(line);card.append(svg);box.append(card);
 });
}
function makePaytable(){
 const el=$('payGrid');el.replaceChildren();
 for(const id of [...regular,'wild','scatter','stone']){
  const div=document.createElement('div');div.className='payItem';
  const img=document.createElement('img');img.src=window.JM_INLINE_ASSETS[id];img.alt=NAME[id];div.appendChild(img);
  const t=document.createElement('span');
  t.innerHTML=`<strong>${NAME[id]}</strong><br>${id==='wild'?`WILD 1× a 5× · permanece fixo nas cascatas desta spin · cada símbolo conta uma única vez por queda · teto conjunto 5× por linha · 2 WILD adjacentes também podem pagar.<br>`:''}${PAY[id]?
   `3× ≈ ${money(Math.round(BETS[betIdx]*PAY[id][3]/LINES.length*rtpScaleFor('base')))}<br>4× ≈ ${money(Math.round(BETS[betIdx]*PAY[id][4]/LINES.length*rtpScaleFor('base')))}<br>5× ≈ ${money(Math.round(BETS[betIdx]*PAY[id][5]/LINES.length*rtpScaleFor('base')))}`
   :id==='stone'?'SEM PRÉMIO · A pedra não tem valor, não faz combinações e interrompe linhas de figuras ou WILDs. Desce normalmente nas cascatas.':'Na base: 3 SCATTER = 15 free spins. Durante o bónus: 2 SCATTER = +8, 3 SCATTER = +15; máximo 2 extensões por bónus.'}`;
  div.append(t);el.append(div);
 }
 drawWinLineMaps();
}

// Painel reservado. A palavra-passe é apenas um bloqueio de interface num ficheiro local.

/* V71: cofre de prémios e capital do operador, sempre em créditos fictícios.
   Nunca utilizar estes movimentos para alterar probabilidades ou saldos do jogador. */
function fundThresholds(){
 const stake=BETS[betIdx],minimum=stake*FUND_MIN_FACTOR;
 return {stake,minimum,target:stake*FUND_TARGET_FACTOR,full:stake*MAX_WIN_MULT,available:Math.max(0,prizePool-minimum)};
}
function updateAdminFund(){
 const f=fundThresholds();
 const map={adminFundCurrent:prizePool,adminFundAdded:fundInvested,adminFundRecovered:fundRecovered,
  adminFundPending:Math.max(0,fundInvested-fundRecovered),
  adminFundExtra:Math.max(0,fundRecovered-fundInvested),
  adminFundAvailable:f.available,adminFundTarget:f.target,adminFundFull:f.full,adminFundStake:f.stake};
 for(const [id,value] of Object.entries(map))$(id).textContent=money(value);
 const level=prizePool<f.minimum?'critico':prizePool<f.target?'baixo':'adequado';
 const advice=prizePool<f.target?'Faltam '+money(f.target-prizePool)+' para a referência.':'O cofre atingiu a referência.';
 const elem=$('adminFundAdvice');elem.dataset.level=level;
 elem.textContent='Fundo '+level+' · cobertura atual '+Math.floor(prizePool/f.stake)+'× aposta. '+advice+
  ' Recomendação: 1000× aposta (não é garantia). Um prémio máximo 5000× exigiria '+money(f.full)+
  ' à aposta escolhida. À aposta máxima de 10,00 € seriam necessários 50 000,00 €. Os jackpots são separados.';
}
function manageAdminFund(type){
 if(!adminUnlocked)return;
 const status=$('adminFundStatus');
 if(running||bonus||freeSpins>0||freeStartPending||freeAutoRunning||freeSettlePending||freeSummaryPending||freeSettlementRunning||autoSpinsRemaining>0){
  status.textContent='Termina primeiro as jogadas e os bónus.';return;
 }
 const raw=String($('adminFundAmount').value||'').trim().replace(',','.');
 const value=Number(raw),amount=Math.round(value*100);
 if(!/^\d+(?:\.\d{1,2})?$/.test(raw)||!Number.isFinite(value)||!Number.isSafeInteger(amount)||amount<1||amount>10000000){
  status.textContent='Indica entre 0,01 € e 100 000,00 € (até duas casas decimais).';return;
 }
 if(!Number.isSafeInteger(prizePool+amount)||!Number.isSafeInteger(fundInvested+amount)||!Number.isSafeInteger(fundRecovered+amount)){
  status.textContent='O contador não suporta esse valor.';return;
 }
 const before=prizePool;
 if(type==='recover'){
  if($('adminFundPin').value!==CASH_PIN){status.textContent='Introduz o PIN do depósito para recuperar o fundo.';return;}
  const f=fundThresholds();
  if(amount>f.available){status.textContent='Máximo recuperável '+money(f.available)+'. Mantém no cofre pelo menos '+money(f.minimum)+' (250× aposta).';return;}
  if(!window.confirm('Recuperar '+money(amount)+' do fundo de prémios fictícios? O saldo passará para '+money(prizePool-amount)+'.'))return;
  prizePool-=amount;fundRecovered+=amount;
 }else if(type==='add'){
  prizePool+=amount;fundInvested+=amount;
 }else return;
 const record={time:new Date().toISOString(),kind:type==='add'?'capital_operador_entrada':'capital_operador_recuperado',amount,before,after:prizePool,stake:BETS[betIdx]};
 adminAudit.push(record);
 if(session)log(record.kind,amount,'Fundo de prémios: '+money(before)+' → '+money(prizePool)+'; movimento do operador');
 $('adminFundAmount').value='';$('adminFundPin').value='';
 status.textContent=(type==='add'?'Adicionado ':'Recuperado ')+money(amount)+'; cofre '+money(prizePool)+'.';
 update();updateAdmin();save();
}

function updateAdmin(){
 updateAdminFund();
 const t=totals(),bankTotal=t.ownerWithdrawn,rtpObserved=t.stakes>0?rtpLabel(100*t.prizes/t.stakes):'—';
 $('adminProfitSet').textContent=rtpLabel(profitPercent);
 $('adminProfitActive').textContent=session?rtpLabel(configuredProfit()):'—';
 $('adminEffectiveRtp').textContent=rtpLabel(Math.min(RTP_REFERENCE,100-profitPercent));
 $('adminCashIn').textContent=money(t.initial+t.deposits);
 $('adminCashOut').textContent=money(t.withdrawn+bankTotal);
 $('adminCashBalance').textContent=money(t.initial+t.deposits-t.withdrawn-bankTotal);
 $('adminReserveTotal').textContent=money(operatorReserve);
 $('adminReserveProtected').textContent=money(protectedReserve);
 $('adminReserveWithdrawable').textContent=money(bankerAvailable());
 const ownerAvailable=shareAvailable('proprietario'),merchantAvailable=shareAvailable('comerciante');
 $('adminSplitOwnerPct').textContent=rtpLabel(ownerPercent);
 $('adminSplitMerchantPct').textContent=rtpLabel(100-ownerPercent);
 $('adminSplitOwnerValue').textContent=money(ownerAvailable);
 $('adminSplitMerchantValue').textContent=money(merchantAvailable);
 $('adminSplitProtected').textContent=money(protectedReserve);
 $('adminSplitOwnerWithdrawn').textContent=money(t.ownerShareWithdrawn);
 $('adminSplitMerchantWithdrawn').textContent=money(t.merchantWithdrawn);
 $('adminVolatilityActive').textContent=volatilityMode==='frequentes'?'Prémios mais frequentes':'Equilibrado';

 $('adminReportSummary').innerHTML=`Proprietário: <strong>${money(ownerAvailable)}</strong> (${ownerPercent}%) · Comerciante: <strong>${money(merchantAvailable)}</strong> (${100-ownerPercent}%)<br>Apostas: <strong>${money(t.stakes)}</strong> · Prémios pagos: <strong>${money(t.prizes)}</strong> · Saldo dos jogadores: <strong>${money(t.balance)}</strong><br>Levantamentos do banqueiro: <strong>${money(bankTotal)}</strong> · Caixa livre contabilística: <strong>${money(reportFinancials().ownerAvailable)}</strong> · Rodadas: <strong>${t.spins}</strong> · RTP observado: <strong>${rtpObserved}</strong>`;
 $('adminMathInfo').textContent='V71 · RTP de referência ~96%, não certificado, para margem de 4%. Calibração Equilibrado: base 0,57 e free 0,068; Frequentes: base 0,55 e free 0,059; Hold grátis 0,25, Hold comprado 1,33. Preços: 15 Free Spins por 20× aposta e Hold por 100×. A Pedra bloqueia combinações em 6% das casas. O RTP observado é '+rtpObserved+'. O cofre de prémios e os fundos de jackpots limitam os pagamentos; não existe compensação de probabilidades pelo cofre. A margem atual é '+rtpLabel(configuredProfit())+'. O fundo do operador está separado dos depósitos de jogadores. Ver docs/auditoria-rtp-v71.md.';
}
function openAdmin(){adminUnlocked=true;modal('adminLoginModal',false);$('adminPassword').value='';$('adminPage').hidden=false;document.body.style.overflow='hidden';$('adminProfitInput').value=profitPercent.toFixed(2);$('adminProfitSlider').value=profitPercent;$('adminProfitPreview').textContent=rtpLabel(profitPercent);
 $('adminSplitSlider').value=ownerPercent;
 $('adminOwnerPercentInput').value=ownerPercent;
 $('adminMerchantPercentInput').value=100-ownerPercent;
 $('adminVolatilityMode').value=volatilityMode;
 $('adminFundAmount').value='';$('adminFundPin').value='';$('adminFundStatus').textContent='';
 previewAdminSplit(ownerPercent);
 $('bankerWithdrawAmount').value='';$('bankerWithdrawPassword').value='';$('bankerWithdrawStatus').textContent='';updateAdmin();}
function closeAdmin(){if(!$('reportViewer').hidden)closeReportViewer();$('adminPage').hidden=true;adminUnlocked=false;$('bankerWithdrawPassword').value='';document.body.style.overflow='';modal('reportModal',false);}
function saveAdminProfit(){
 if(!adminUnlocked)return;
 if(running||bonus||freeSpins>0||freeAutoRunning||freeSettlePending||freeSummaryPending||autoSpinsRemaining>0){
  $('adminProfitStatus').textContent='Altera a margem apenas entre jogadas, fora dos bónus e giros automáticos.';return;
 }
 let n=Number($('adminProfitInput').value);
 if(!Number.isFinite(n)||n<1||n>70){$('adminProfitStatus').textContent='Introduz uma percentagem entre 1,00% e 70,00%.';return;}
 n=Math.round(n*100)/100;const previous=profitPercent;profitPercent=n;
 adminAudit.push({time:new Date().toISOString(),kind:'reserva_operador',previous,newValue:n});save();updateAdmin();
 $('adminProfitStatus').textContent='Reserva configurada: '+rtpLabel(n)+'; a alteração aplica-se às apostas seguintes; não modifica valores já acumulados.';
}

/* V59 — alteração da partilha não toca nos jackpots nem em reservas protegidas.
   O saldo ainda não retirado é redistribuído; levantamentos passados permanecem. */
function previewAdminSplit(value){
 const pct=Math.max(0,Math.min(100,Math.round(Number(value)||0)));
 $('adminSplitSlider').value=pct;
 $('adminOwnerPercentInput').value=pct;
 $('adminMerchantPercentInput').value=100-pct;
 const avail=bankerAvailable();
 const owner=Math.floor(avail*pct/100),merchant=avail-owner;
 $('adminSplitPreview').textContent='Pré-visualização: tua parte '+money(owner)+' ('+pct+'%) · estabelecimento '+money(merchant)+' ('+(100-pct)+'%).';
}
function saveAdminSplit(){
 if(!adminUnlocked)return;
 if(running||bonus||freeSpins>0||freeAutoRunning||freeSettlePending||freeSummaryPending||autoSpinsRemaining>0){
  $('adminSplitStatus').textContent='Termina primeiro as jogadas e os bónus para repartir a reserva livre.';return;
 }
 const input=Number($('adminOwnerPercentInput').value);
 if(!Number.isFinite(input)||!Number.isInteger(input)||input<0||input>100){
  $('adminSplitStatus').textContent='Escolhe percentagens inteiras entre 0% e 100%.';return;
 }
 const previous=ownerPercent;ownerPercent=input;
 const distributable=Math.max(0,operatorReserve-protectedReserve);
 ownerShareReserve=Math.floor(distributable*ownerPercent/100);
 merchantShareReserve=distributable-ownerShareReserve;
 shareCarry=0;
 adminAudit.push({time:new Date().toISOString(),kind:'partilha_banca',previous,newValue:ownerPercent,merchant:100-ownerPercent});
 save();updateAdmin();previewAdminSplit(ownerPercent);
 $('adminSplitStatus').textContent='Guardado: '+ownerPercent+'% para ti e '+(100-ownerPercent)+'% para o estabelecimento. Reservas disponíveis repartidas; levantamentos anteriores e jackpots intactos.';
}
function saveAdminVolatility(){
 if(!adminUnlocked)return;
 if(running||bonus||freeSpins>0||freeAutoRunning||freeSettlePending||freeSummaryPending||autoSpinsRemaining>0){
  $('adminVolatilityStatus').textContent='Termina a sessão de free spins ou as jogadas automáticas antes de mudar a frequência.';return;
 }
 const next=$('adminVolatilityMode').value;
 if(next!=='equilibrado'&&next!=='frequentes')return;
 const previous=volatilityMode;volatilityMode=next;
 adminAudit.push({time:new Date().toISOString(),kind:'frequencia_premios_normais',previous,newValue:next});
 save();updateAdmin();
 $('adminVolatilityStatus').textContent=next==='frequentes'?'Guardado: mais combinações comuns e valores médios de linha ajustados. Jackpots sem alteração.':'Guardado: distribuição equilibrada das figuras normais. Jackpots sem alteração.';
}

function setupAdmin(){
 const logo=$('adminHotspot');logo.addEventListener('click',()=>{const now=Date.now();if(now>adminTapDeadline)adminTaps=0;adminTaps++;adminTapDeadline=now+9000;if(adminTaps>=10){adminTaps=0;adminTapDeadline=0;$('adminLoginError').textContent='';$('adminPassword').value='';modal('adminLoginModal',true);$('adminPassword').focus();}});
 $('adminLoginSubmit').onclick=()=>{if($('adminPassword').value==='1234567890')openAdmin();else{$('adminLoginError').textContent='Palavra-passe incorreta.';$('adminPassword').value='';}};
 $('adminPassword').addEventListener('keydown',e=>{if(e.key==='Enter')$('adminLoginSubmit').click()});
 $('adminLoginCancel').onclick=()=>{modal('adminLoginModal',false);$('adminPassword').value='';};
 $('adminExit').onclick=closeAdmin;
 $('adminResetOpen').onclick=()=>{if(!adminUnlocked)return;$('resetPassword').value='';$('resetAcknowledge').checked=false;$('resetError').textContent='';modal('resetModal',true)};
 $('resetCancel').onclick=()=>modal('resetModal',false);
 $('resetConfirm').onclick=()=>{if(!adminUnlocked)return;if(running||bonus||freeSpins>0||freeAutoRunning){$('resetError').textContent='Termina primeiro a jogada ou bónus.';return;}if($('resetPassword').value!=='12345'){$('resetError').textContent='Password incorreta.';return;}if(!$('resetAcknowledge').checked){$('resetError').textContent='Assinala a confirmação para continuar.';return;}localStorage.removeItem(KEY);window.location.reload();};
 $('adminProfitSlider').oninput=()=>{const n=Number($('adminProfitSlider').value);$('adminProfitInput').value=n.toFixed(2);$('adminProfitPreview').textContent=rtpLabel(n);};
 $('adminProfitInput').oninput=()=>{const n=Number($('adminProfitInput').value);if(Number.isFinite(n)&&n>=0&&n<=70){$('adminProfitSlider').value=n;$('adminProfitPreview').textContent=rtpLabel(n);}};
  $('adminSaveProfit').onclick=saveAdminProfit;
  $('adminSplitSlider').oninput=()=>previewAdminSplit($('adminSplitSlider').value);
  $('adminOwnerPercentInput').oninput=()=>previewAdminSplit($('adminOwnerPercentInput').value);
  $('adminMerchantPercentInput').oninput=()=>{
   const pct=Number($('adminMerchantPercentInput').value);
   if(Number.isFinite(pct)&&pct>=0&&pct<=100)previewAdminSplit(100-pct);
  };
  $('adminSaveSplit').onclick=saveAdminSplit;
  $('adminSaveVolatility').onclick=saveAdminVolatility;
  $('adminWithdrawalRecipient').onchange=()=>{
   const party=$('adminWithdrawalRecipient').value;
   $('bankerWithdrawStatus').textContent='Disponível para '+(party==='comerciante'?'o estabelecimento':'ti')+': '+money(shareAvailable(party));
  };
  $('bankerWithdrawButton').onclick=doBankerWithdraw;
  $('adminFundAdd').onclick=()=>manageAdminFund('add');
  $('adminFundRecover').onclick=()=>manageAdminFund('recover');
  $('bankerWithdrawPassword').addEventListener('keydown',e=>{if(e.key==='Enter')doBankerWithdraw()});
 $('adminFullReport').onclick=()=>{if(adminUnlocked)report();};
 $('reportViewSave').onclick=downloadHTMLReport;
 $('reportViewPrint').onclick=printReportViewer;
 $('reportViewClose').onclick=closeReportViewer;
 $('adminExportCsv').onclick=downloadHTMLReport;$('adminPrintReport').onclick=printHTMLReport;
}

const DISPLAY_KEY='jm_game_display_ratio_v26';
function setDisplayRatio(ratio){
 const layout=ratio==='16:9'?'wide':'portrait';
 document.querySelector('.game').dataset.layout=layout;
 window.dispatchEvent(new Event('resize'));
}
function setup(){
 document.querySelectorAll('#choices button').forEach(btn=>btn.onclick=()=>{document.querySelectorAll('#choices button').forEach(b=>b.classList.toggle('selected',b===btn));$('openingCredits').value=btn.dataset.value;updateDepositPreview()});
 $('startBtn').onclick=submitDeposit;
 $('cancelDeposit').onclick=()=>{depositAuthorized=false;modal('startModal',false)};
 $('depositBtn').onclick=openDepositLogin;
 $('depositLoginCancel').onclick=()=>{depositAuthorized=false;modal('depositLoginModal',false)};
 $('depositLoginSubmit').onclick=()=>{if($('depositPassword').value===CASH_PIN){depositAuthorized=true;$('depositPassword').value='';showDepositForm()}else{$('depositError').textContent='Palavra-passe incorreta.';$('depositPassword').value=''}};
 $('depositPassword').addEventListener('keydown',e=>{if(e.key==='Enter')$('depositLoginSubmit').click()});
 $('betMinus').onclick=()=>{if(betIdx>0&&!running&&!bonus&&freeSpins===0){soundStart();audioFX('tap');betIdx--;makePaytable();update();save()}};$('betPlus').onclick=()=>{if(betIdx<BETS.length-1&&!running&&!bonus&&freeSpins===0){soundStart();audioFX('tap');betIdx++;makePaytable();update();save()}};
 let spinLongTimer=null,spinHeld=false,spinLongUsed=false;
 const beginLongSpin=()=>{if($('spin').disabled||freeSummaryPending||window.JM_WHEEL_V49?.isPlaying?.())return;spinHeld=true;spinLongUsed=false;$('spin').classList.add('pressed');clearTimeout(spinLongTimer);if(autoSpinsRemaining>0||running||bonus||freeSpins>0)return;spinLongTimer=setTimeout(()=>{if(!spinHeld)return;spinLongUsed=true;$('spin').classList.remove('pressed');modal('autoModal',true);soundStart();},1000);};
 const cancelLongSpin=()=>{spinHeld=false;clearTimeout(spinLongTimer);$('spin').classList.remove('pressed');};
 $('spin').addEventListener('pointerdown',beginLongSpin);
 ['pointerup','pointercancel','pointerleave'].forEach(eventName=>$('spin').addEventListener(eventName,cancelLongSpin));
 $('spin').addEventListener('contextmenu',e=>e.preventDefault());
 $('spin').onclick=()=>{if(freeSummaryPending){spinLongUsed=false;closeFreeSummary();return;}if(window.JM_WHEEL_V49?.handleSpin?.()){spinLongUsed=false;return;}if(autoSpinsRemaining>0){spinLongUsed=false;stopAuto();notice('Voltas automáticas paradas.');return;}if(spinLongUsed){spinLongUsed=false;return;}if(freeSpins>0){openFreeStart();return;}if(running)return;doRound()};$('freeStartBtn').onclick=startFreeAuto;$('normalBtn').onclick=()=>{mode='normal';update();save()};$('turboBtn').onclick=()=>{mode=mode==='turbo'?'normal':'turbo';update();save()};
 $('buyBonus').onclick=openBonusPurchase;$('buyHoldChoice').onclick=()=>launchBonus(true,'compra');$('buyFreeChoice').onclick=buyFreeSpins;$('closeBuy').onclick=()=>modal('buyModal',false);
 $('bonusSpin').onclick=bonusRespin;$('vaultBtn').onclick=$('vaultBtnMobile').onclick=()=>{notice('🪙 O cofre vai crescendo e pode rebentar de surpresa, ativando Hold & Win!')};
 $('btnSound').onclick=()=>{mute=!mute;if(mute)stopBonusJingle();musicUI();if(!mute)soundStart()};
 $('btnMusic').onclick=()=>{musicEnabled=!musicEnabled;if(musicEnabled){startMusic()}else musicElement().pause();musicUI();save()};
 $('btnPay').onclick=()=>{makePaytable();modal('payModal',true)};$('closePay').onclick=()=>modal('payModal',false);
 $('closeReport').onclick=()=>modal('reportModal',false);$('exportCsv').onclick=downloadHTMLReport;$('printReport').onclick=printHTMLReport;
 $('withdrawBtn').onclick=()=>{if(!session||running||bonus||freeSpins>0)return;$('withdrawAmount').value=(balance/100).toFixed(2);$('withdrawInfo').textContent=`Saldo disponível para levantamento: ${money(balance)}.`;$('withdrawPassword').value='';$('withdrawError').textContent='';modal('withdrawModal',true);$('withdrawPassword').focus()};
 $('cancelWithdraw').onclick=()=>{$('withdrawPassword').value='';$('withdrawError').textContent='';modal('withdrawModal',false)};$('confirmWithdraw').onclick=doWithdraw;$('withdrawPassword').addEventListener('keydown',e=>{if(e.key==='Enter')doWithdraw()});
 $('autoSpinBtn').onclick=()=>{if(!session||bonus||freeSpins>0)return;document.querySelectorAll('#autoChoices button').forEach(b=>b.disabled=running);modal('autoModal',true)};document.querySelectorAll('#autoChoices button').forEach(btn=>btn.onclick=()=>{if(running)return;autoSpinsRemaining=Number(btn.dataset.auto)||0;autoSpinsTotal=autoSpinsRemaining;modal('autoModal',false);notice('Auto-spins ativadas: '+autoSpinsRemaining);update();save();if(!running&&!bonus){autoSpinsRemaining--;update();doRound();}});$('cancelAuto').onclick=()=>modal('autoModal',false);$('stopAuto').onclick=stopAuto;
 $('btnHome').onclick=()=>{if(running||bonus||freeSpins>0)return;openDepositLogin()};
 $('closeBig').onclick=()=>{$('bigWin').classList.remove('show');notice('JACKPOT recolhido. Boa continuação!');if(freeSettlePending)showFreeSummary();else if(freeAutoRunning)scheduleFreeAuto();else if(freeSpins>0&&!freeStartPending)openFreeStart();else if(autoSpinsRemaining>0)maybeContinueAuto()};
 $('openingCredits').addEventListener('input',()=>{document.querySelectorAll('#choices button').forEach(b=>b.classList.toggle('selected',Number(b.dataset.value)===Number($('openingCredits').value)));updateDepositPreview()});
}

(async()=>{setup();setupAdmin();load();updateDropScore(freeSpins>0||freeSettlePending||freeSummaryPending?freeBaseTotal:0);setDisplayRatio(window.innerWidth>window.innerHeight*1.12?'16:9':'9:16');musicUI();document.addEventListener('pointerdown',()=>{if(session&&musicEnabled&&musicElement().paused)startMusic()},{passive:true});update();makePaytable();await imageLoad();requestAnimationFrame(frame);
 // CDN PixiJS é opcional em execução: Canvas2D serve de alternativa quando offline/WebGL indisponível.
 let ticks=0;const timer=setInterval(async()=>{if(window.PIXI?.Application){clearInterval(timer);await initPixi()}else if(++ticks>=30){clearInterval(timer);console.info('Canvas 2D de reserva ativo')}},100);
 if(session){notice('Sessão retomada. Continua a jogar!');modal('startModal',false);if(freeMultiplierBalls.length)shelf=freeMultiplierBalls;updateShelf();if(freeSummaryPending)showFreeSummary(true);if(freeSettlePending&&!bonus)showFreeSummary();if(bonus){if(bonus.finished&&bonus.endReason==='completo'&&bonus.cells.every(Boolean))prepareFullBoardWheel();renderBonus();modal('bonusModal',true);$('bonusNote').textContent=bonus.wheelPending?'Roleta de jackpots por concluir. O mesmo sorteio será retomado.':bonus.finished?'Bónus concluído. Recolhe o teu prémio.':'Bónus retomado: tens '+bonus.left+' tentativas restantes.';if(bonus.wheelPending)setTimeout(()=>playFullBoardWheel(),200);}if(freeSpins>0&&!bonus)openFreeStart();else if(autoSpinsRemaining>0&&!running&&!bonus&&!freeSettlePending&&!freeSummaryPending)maybeContinueAuto();}else openDepositLogin();
})();
})();
