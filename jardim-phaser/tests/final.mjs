import assert from 'node:assert/strict';
import {readFile} from 'node:fs/promises';
import {createHash} from 'node:crypto';

const read=p=>readFile(new URL(p,import.meta.url),'utf8');
const moduleOf=async p=>import('data:text/javascript;base64,'+Buffer.from(await read(p)).toString('base64'));
const {DemoBank,newBoard}=await moduleOf('../js/engine.js');
const {validateSessionState,validateBackup}=await moduleOf('../js/database.js');
const {readCredits,validateFundChange,makePrintableReport,summarizeLedger}=await moduleOf('../js/admin-v18.js');
const {filterHistory}=await moduleOf('../js/history-v19.js');
const release=JSON.parse(await read('../release.json'));
let checks=0,turns=0,holds=0,freeSeries=0;
const check=(value,label)=>{assert.ok(value,label);checks++};
const rejects=(fn,label)=>{assert.throws(fn,undefined,label);checks++};
let seed=0x4a4d5000;
const rng=()=>((seed=(Math.imul(seed,1664525)+1013904223)>>>0)/4294967296);

for(const [path,hash] of Object.entries(release.preserved_git_blobs)){
 const data=await readFile(new URL('../../'+path,import.meta.url));
 const actual=createHash('sha1').update('blob '+data.length+'\0').update(data).digest('hex');
 check(actual===hash,'Preservação exata da V4.3.3: '+path);
}
Math.random=rng; // Also seed the engine's default Hold opener.
const board=newBoard(rng);
const fresh=()=>({snapshot:new DemoBank().snapshot,board:structuredClone(board)});
for(const stake of [.05,.1,.2,.4,.6,.8,1,1.5,2,3,5,7.5,10]){
 for(const reserve of [0,.01,1,100,2300]){
  const bank=new DemoBank();bank.stake=stake;bank.reserve=reserve;bank.balance=10000;
  for(let n=0;n<120;n++){
   if(bank.mode==='hold'){
    const result=bank.holdTurn(rng);check(result.ok,'Hold válido');holds++;
   }else{
    const result=bank.spin(rng);check(result.ok,'Giro válido');turns++;
   }
   validateSessionState({snapshot:bank.snapshot,board});checks++;
   check(bank.balance>=0&&bank.reserve>=0,'Saldo e cofre nunca negativos');
  }
 }
}
// Hold de baixo fundo, grelha completa, pagamento e impossibilidade de pagar duas vezes.
let completedGrid=false;
for(let n=0;n<1800;n++){
 const bank=new DemoBank();bank.reserve=n%2?1.2:100000;
 check(!!bank.startHold(rng),'Seis moedas iniciais cobertas');
 let result,rounds=0;
 while(bank.mode==='hold'){
  validateSessionState({snapshot:bank.snapshot,board});checks++;
  const before=bank.hold.grid.slice();result=bank.holdTurn(rng);holds++;rounds++;
  check(rounds<=48,'Hold termina: moedas finitas e três tentativas');
  check(result.hold.grid.every((v,i)=>!before[i]||v===before[i]),'Moedas conquistadas preservadas');
 }
 const paidBalance=bank.balance;
 check(!bank.holdTurn(rng).ok&&bank.balance===paidBalance,'Hold pago uma única vez');
 check(result.prize<=100000,'Pagamento integral com cobertura');
 if(result.hold.grid.every(Boolean))completedGrid=true;
}
check(completedGrid,'Foi exercitada a grelha completa com extra 250×');
for(let n=0;n<200;n++){
 const bank=new DemoBank();bank.reserve=n%2?0:2300;bank.addFree(15);
 let count=0;
 while(bank.mode==='free'){
  const result=bank.spin(rng);turns++;count++;
  check(count<=60,'Free Spins e reativações terminam até 60 giros');
  check(result.ok&&bank.reserve>=0,'Free Spins sem cofre negativo');
  validateSessionState({snapshot:bank.snapshot,board});checks++;
 }
 check(bank.freeSpins===0,'Série gratuita concluída');freeSeries++;
}
for(const [key,value] of [['balance',NaN],['reserve',-1],['stake',.06],['balance',1.001],
 ['freePlayed',1.5],['freeMultiplier',1001],['spins',-1]]){
 const bad=fresh();bad.snapshot[key]=value;
 rejects(()=>validateSessionState(bad),'Rejeita estado inválido: '+key);
}
const badFree=fresh();Object.assign(badFree.snapshot,{mode:'free',freeSpins:15,freeTotal:100,freeMultiplier:5,reserve:1});
rejects(()=>validateSessionState(badFree),'Rejeita Free Spins sem cobertura');
badFree.snapshot.reserve=1000;badFree.snapshot.freePlayed=50;
rejects(()=>validateSessionState(badFree),'Rejeita mais de 60 Free Spins');
const hold=new DemoBank();hold.startHold(rng);
const goodHold={snapshot:hold.snapshot,board};validateSessionState(goodHold);checks++;
for(const change of [s=>s.hold.total++,s=>s.hold.grid[0]=.5,s=>s.hold.tries=0,s=>s.reserve=0]){
 const bad=structuredClone(goodHold);change(bad.snapshot);
 rejects(()=>validateSessionState(bad),'Rejeita Hold adulterado');
}
for(const raw of ['', 'NaN','Infinity','1e3','1.001','-1','0','0.001','10,001','1000000000001'])
 check(readCredits(raw)===null,'Rejeita ajuste de créditos: '+raw);
for(const [raw,value] of [['0,05',.05],['25.50',25.5],['100',100],[' 1,20 ',1.2]])
 check(readCredits(raw)===value,'Aceita créditos com ponto ou vírgula');
check(!validateFundChange('withdraw','101',100).ok,'Recusa retirar mais que o saldo');
check(!validateFundChange('deposit','1',100,'free').ok,'Recusa movimentos durante bónus');
check(!validateFundChange('deposit','1',1e12).ok,'Não excede a precisão suportada');
const events=Array.from({length:260},(_,n)=>({eventId:'row-'+n,type:n%2?'player-credit':'admin-profile',
 timestamp:'2026-11-09T00:00:00Z',amount:1,note:'<script>unsafe</script>'}));
check(filterHistory(events,{category:'fund'}).length===130,'Filtro inclui créditos do jogador');
check(filterHistory(events,{category:'admin'}).length===130,'Filtro inclui perfis');
check(filterHistory(events,{query:'adicionar créditos'}).length===130,'Pesquisa por nome em português');
const report=makePrintableReport({summary:summarizeLedger(events),history:events,
 settings:{ownerPct:50,merchantPct:50},reserve:100,dbMode:'indexedDB'});
check(report.includes('260 movimentos')&&report.includes('260 existentes')===false&&report.includes('player-credit'),
 'Relatório inclui todos os movimentos guardados');
check(!report.includes('<script>'),'Relatório sem HTML de eventos executável');
const backup={format:'jardim-magico-demo-v3',state:fresh(),history:events};
check(validateBackup(backup).history.length===260,'Backup legítimo continua compatível');
const original=JSON.stringify(backup);validateBackup(backup);
check(JSON.stringify(backup)===original,'Validação não altera o backup original');
console.log(JSON.stringify({version:'5.0.0',checks,turns,holds,freeSeries,completedGrid,seed:'0x4a4d5000'}));
