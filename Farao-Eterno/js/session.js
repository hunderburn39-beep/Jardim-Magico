/* Faraó Eterno — transactional, recoverable fictional-credit session.
 * One credit = 252000 integer units: exactly represents all approved line stakes.
 * The approved paytable and weights are preserved; payline layouts are versioned.
 */
(function(root,factory){
  if(typeof module==='object'&&module.exports)module.exports=factory(require('./engine.js'));
  else root.FaraoSession=factory(root.TombEngine);
})(typeof globalThis!=='undefined'?globalThis:this,function(E){
  'use strict';
  const UNIT=252000, BETS=[.10,.20,.40,.50,1,2,5,10];
  const stake=s=>Math.round(units(BETS[s.betIndex])*s.lines*(s.betMultiplier||1)/10)/UNIT;
  const units=n=>Math.round(n*UNIT), credits=n=>n/UNIT;
  const clone=x=>JSON.parse(JSON.stringify(x));
  const integer=n=>Number.isSafeInteger(n)&&n>=0;
  const gridOK=g=>Array.isArray(g)&&g.length===5&&g.every(c=>Array.isArray(c)&&c.length===3&&c.every(s=>E.ALL.includes(s))&&c.filter(s=>s==='TOMB').length<=1);
  const featureOK=f=>f===null||(f&&integer(f.left)&&integer(f.played)&&integer(f.retriggers)&&integer(f.totalU)&&Array.isArray(f.symbols)&&f.symbols.length>0&&f.symbols.length<=9&&new Set(f.symbols).size===f.symbols.length&&f.symbols.every(s=>E.NORMAL.includes(s)));
  function fresh(){return {version:2,balanceU:0,betIndex:4,betMultiplier:1,lines:10,grid:[['10','J','Q'],['HORUS','MASK','K'],['A','TOMB','10'],['ANUBIS','K','J'],['KING','A','Q']],feature:null,pending:null,payout:null,sequence:0,lastWinU:0,lastBaseWinU:0,lastBet:1,gambles:0,lastWasFree:false,history:[],stats:{paid:0,free:0,stakeU:0,winU:0,features:0},settings:{sound:true,music:true,soundMode:'all',turbo:false},last:null};}
  function validate(s){
    if(!s||s.version!==2||!integer(s.balanceU)||!integer(s.sequence)||!Number.isInteger(s.betIndex)||s.betIndex<0||s.betIndex>=BETS.length||!(s.betMultiplier===undefined||Number.isInteger(s.betMultiplier)&&s.betMultiplier>=1&&s.betMultiplier<=5)||!Number.isFinite(stake(s))||stake(s)>10||!Number.isInteger(s.lines)||s.lines<1||s.lines>10||!gridOK(s.grid)||!featureOK(s.feature)||!integer(s.lastWinU)||!integer(s.lastBaseWinU)||!(Number.isFinite(s.lastBet)&&s.lastBet>0&&s.lastBet<=10)||!integer(s.gambles)||s.gambles>5||!Array.isArray(s.history)||s.history.length>100)throw Error('Sessão inválida');
    if(!s.stats||!['paid','free','stakeU','winU','features'].every(k=>integer(s.stats[k])))throw Error('Estatísticas inválidas');
    if(s.feature&&s.feature.totalU>units(E.upperLimit(stake(s))))throw Error('Bónus inválido');
    if(s.pending){const p=s.pending;
      if(!gridOK(p.grid)||(p.paylinesVersion!==undefined&&p.paylinesVersion!==2)||(p.expansionPayVersion!==undefined&&p.expansionPayVersion!==2)||(p.towerPayVersion!==undefined&&p.towerPayVersion!==1)||p.id!==s.sequence||Math.abs(p.bet-stake(s))>1e-8||p.lines!==s.lines||typeof p.free!=='boolean'||(p.free&&!s.feature)||(!p.free&&s.feature)||!(p.nextSymbol===null||E.NORMAL.includes(p.nextSymbol)))throw Error('Jogada pendente inválida');
      const triggered=E.countScatters(p.grid)>=3,choices=p.free?E.NORMAL.filter(x=>!s.feature.symbols.includes(x)):E.NORMAL;
      if(triggered&&choices.length&&!choices.includes(p.nextSymbol)||!triggered&&p.nextSymbol!==null)throw Error('Seleção de bónus inválida');
    }
    // Prémios de uma rodada podem aguardar revelação visual, mas nunca
    // podem ser creditados duas vezes, mesmo após fechar o Safari.
    if(s.payout!==undefined&&s.payout!==null){
      const p=s.payout;
      if(!integer(p.id)||p.id!==s.sequence||!integer(p.totalU)||
         !integer(p.next)||!Array.isArray(p.groups)||p.groups.length<1||
         p.groups.length>64||p.next>=p.groups.length||
         !p.groups.every(g=>integer(g.amountU)&&g.amountU>0&&
           ['line','expansion','scatter'].includes(g.kind))||
         p.groups.reduce((v,g)=>v+g.amountU,0)!==p.totalU||
         s.pending)throw Error('Liquidação progressiva inválida');
    }
    if(s.feature&&s.feature.left===0&&!s.pending)throw Error('Bónus terminado pendente');
    return s;
  }
  // Converter registos antigos (uma linha por Free Spin) em sessões.
  // A conversão não altera montantes, grelhas, prémios ou número de jogada.
  function compactFreeHistory(s){
    if(!Array.isArray(s.history)||!s.history.some(h=>h.kind==='free'))return s;
    const compact=[];
    for(let i=0;i<s.history.length;){
      const h=s.history[i];
      if(h.kind!=='free'){compact.push(h);i++;continue;}
      const segment=[];
      while(i<s.history.length&&s.history[i].kind==='free'){
        segment.push(s.history[i++]);
      }
      const rounds=segment.reverse(),first=rounds[0],last=rounds.at(-1);
      const total=rounds.reduce((v,r)=>v+(Number.isFinite(r.win)?r.win:0),0);
      const group={
        kind:'free-bonus',id:first.id,t:first.t,updatedAt:last.t,
        bet:0,win:Math.round(total*100)/100,played:rounds.length,
        retriggers:rounds.filter(r=>r.scatters>=3).length,
        rounds,completed:!s.feature||compact.length>0
      };
      // Se o histórico mais recente pertence a um bónus em curso,
      // conservar o identificador para anexar os próximos giros.
      if(s.feature&&compact.length===0) {
        group.completed=false;
        s.feature.historyId=group.id;
      }
      compact.push(group);
    }
    s.history=compact.slice(0,100);
    return s;
  }
  function restore(raw){const s=clone(raw);validate(s);s.betMultiplier=s.betMultiplier||1;const prior=s.settings||{},sound=prior.sound!==false,music=prior.music!==false;
    const inferred=sound&&music?'all':sound?'effects':'mute';
    const soundMode=['all','effects','mute'].includes(prior.soundMode)?prior.soundMode:inferred;
    s.settings={sound:soundMode!=='mute',music:soundMode==='all',soundMode,turbo:!!prior.turbo};compactFreeHistory(s);return validate(s);}
  function migrate(old){
    const s=fresh();if(!old||typeof old!=='object')return s;
    if(Number.isFinite(old.balance)&&old.balance>=0)s.balanceU=units(Math.min(old.balance,10000000));
    if(Number.isInteger(old.betIndex))s.betIndex=Math.max(0,Math.min(BETS.length-1,old.betIndex));
    if(Number.isInteger(old.lines))s.lines=Math.max(1,Math.min(10,old.lines));
    const f=old.feature;
    if(f&&Number.isSafeInteger(f.left)&&f.left>0&&Number.isSafeInteger(f.played)&&f.played>=0&&Number.isFinite(f.total)&&f.total>=0){
      const candidate={left:f.left,played:f.played,totalU:units(f.total),symbols:f.symbols,retriggers:f.retriggers||0};
      if(featureOK(candidate)&&candidate.totalU<units(E.upperLimit(stake(s))))s.feature=clone(candidate);
    }
    if(Array.isArray(old.history))s.history=old.history.filter(h=>h&&typeof h.t==='string'&&Number.isFinite(h.bet)&&Number.isFinite(h.win)).slice(0,100).map(h=>({...h,kind:h.bonus?'free':'spin'}));
    s.betMultiplier=1;s.settings.sound=old.sound!==false;s.settings.soundMode=s.settings.sound?'all':'mute';s.settings.music=s.settings.sound;compactFreeHistory(s);return validate(s);
  }
  // Compra voluntária: 100× para pirâmides e 200× para Super Scatter; créditos fictícios.
  // A operação é atómica e recuperável no mesmo armazenamento das jogadas.
  function buyBonus(s,rng=E.randomFloat,now=new Date().toISOString(),mode='regular'){
    validate(s);
    if(s.pending||s.payout)throw Error('Existe uma jogada por terminar');
    if(s.feature)throw Error('Já tens um bónus ativo');
    if(s.lines!==10)throw Error('Para comprar o bónus tens de apostar nas 10 linhas');
    if(mode!=='regular'&&mode!=='super')throw Error('Tipo de bónus inválido');
    const bet=stake(s),costU=units(bet)*(mode==='super'?200:100);
    if(s.balanceU<costU)throw Error('Saldo insuficiente para comprar o bónus');
    // Selecionar o símbolo antes de debitar créditos. Nunca criar um giro falso.
    const symbol=E.NORMAL[Math.min(E.NORMAL.length-1,Math.max(0,Math.floor(rng()*E.NORMAL.length)))];
    s.balanceU-=costU;
    s.feature={left:8,played:0,totalU:0,symbols:[symbol],retriggers:0,purchasedMode:mode};
    s.gambles=0;s.lastBaseWinU=0;s.lastWinU=0;s.last=null;
    s.stats.features++;
    s.stats.stakeU+=costU;
    s.history.unshift({t:now,kind:'bonus-buy',bet,cost:credits(costU),win:0,symbol,mode});
    s.history=s.history.slice(0,100);
    return {costU,bet,symbol,spins:8,mode};
  }
  function begin(s,rng=E.randomFloat){
    validate(s);if(s.pending||s.payout)throw Error('Existe uma jogada por terminar');
    const bet=stake(s),free=!!s.feature;
    if(!free&&s.balanceU<units(bet))throw Error('Saldo insuficiente');
    // Commit the entire outcome, including feature selection, before animation.
    const grid=E.makeGrid(rng,free?E.FREE_WEIGHTS:E.WEIGHTS),
      result=E.evaluate(grid,bet,s.lines,free?s.feature.symbols:[],E.LINES,
        'separate',free?'free-anywhere':'base');
    let nextSymbol=null;
    if(result.trigger){const choices=free?E.NORMAL.filter(x=>!s.feature.symbols.includes(x)):E.NORMAL;
      if(choices.length)nextSymbol=choices[Math.floor(rng()*choices.length)];}
    if(free){if(!Number.isSafeInteger(s.feature.historyId))s.feature.historyId=s.sequence+1;
      s.feature.left--;s.feature.played++;s.stats.free++;}
    else{s.balanceU-=units(bet);s.gambles=0;s.lastBaseWinU=0;s.stats.paid++;s.stats.stakeU+=units(bet);}
    s.pending={id:++s.sequence,grid,free,bet,lines:s.lines,nextSymbol,paylinesVersion:E.PAYLINES_VERSION,expansionPayVersion:2,...(free?{towerPayVersion:1}:{})};
    return {pending:s.pending,result};
  }
  // Plano de sequências calculado exclusivamente a partir do resultado
  // já sorteado. A distribuição é inteira e limitada ao prémio aprovado.
  function sequencePayouts(result,prizeU){
    if(prizeU<=0)return [];
    const groups=[],separate=result.expansionMode==='separate';
    for(const w of result.lineWins||[]){
      if(separate&&w.expanded)continue;
      if(w.expanded){
        const existing=groups.find(g=>g.kind==='expansion'&&g.symbol===w.symbol);
        if(existing){existing.raw+=w.amount;continue;}
        const ex=result.expanders?.find(e=>e.symbol===w.symbol);
        groups.push({kind:'expansion',symbol:w.symbol,reels:[...(ex?.reels||[])],raw:w.amount});
      }else{
        groups.push({kind:'line',symbol:w.symbol,line:w.line,rows:[...w.rows],
          count:w.count,reels:Array.from({length:w.count},(_,i)=>i),raw:w.amount});
      }
    }
    if(separate)for(const x of result.expansionAwards||[]){
      groups.push({kind:'expansion',symbol:x.symbol,reels:[...x.reels],raw:x.amount});
    }
    if(result.scatterWin>0)groups.push({kind:'scatter',symbol:'TOMB',reels:[],raw:result.scatterWin});
    groups.sort((a,b)=>{
      const rank=k=>k==='scatter'?0:k==='line'?1:2;
      const d=rank(a.kind)-rank(b.kind);if(d)return d;
      if(a.kind==='expansion')return E.NORMAL.indexOf(a.symbol)-E.NORMAL.indexOf(b.symbol);
      return (a.line??0)-(b.line??0);
    });
    const rawSum=groups.reduce((sum,g)=>sum+g.raw,0);
    if(!groups.length||rawSum<=0)return [];
    let credited=0;
    return groups.map((g,i)=>{
      const amountU=i===groups.length-1?prizeU-credited:
        Math.max(0,Math.min(prizeU-credited,
         Math.floor(prizeU*g.raw/rawSum)));
      credited+=amountU;
      const {raw,...rest}=g;return {...rest,amountU};
    }).filter(g=>g.amountU>0);
  }
  function creditNext(s,id,index){
    validate(s);
    const payout=s.payout;
    if(!payout||payout.id!==id||payout.next!==index)return null;
    const amountU=payout.groups[index].amountU;
    s.balanceU+=amountU;s.stats.winU+=amountU;
    payout.next++;
    if(payout.next>=payout.groups.length)s.payout=null;
    return {amountU,remaining:s.payout?s.payout.groups.length-s.payout.next:0};
  }
  function finishPayout(s){
    let added=0;
    while(s.payout){
      const {id,next}=s.payout;
      const result=creditNext(s,id,next);
      if(!result)throw Error('Crédito pendente inválido');
      added+=result.amountU;
    }
    return added;
  }
  function settle(s,now=new Date().toISOString(),options={}){
    if(!s.pending)return null;validate(s);
    const p=s.pending,result=E.evaluate(p.grid,p.bet,p.lines,p.free?s.feature.symbols:[],
      p.paylinesVersion===E.PAYLINES_VERSION?E.LINES:E.LEGACY_LINES,
      p.expansionPayVersion===2?'separate':'legacy',
      p.free&&p.towerPayVersion===1?'free-anywhere':'base');
    const limitU=units(E.upperLimit(p.bet));
    const prizeU=Math.max(0,Math.min(units(result.total),p.free?limitU-s.feature.totalU:limitU));
    const groups=p.free&&options?.defer?sequencePayouts(result,prizeU):[];
    // Cada linha vencedora nas Free Spins merece a sua apresentação,
    // mesmo que seja a única combinação da jogada. Continuar a usar
    // creditNext() exatamente uma vez por grupo, sem alterar prémios.
    const progressive=groups.some(g=>g.kind==='line')||
      groups.filter(g=>g.kind!=='scatter').length>=2;
    if(progressive)s.payout={id:p.id,totalU:prizeU,next:0,groups};
    else{s.balanceU+=prizeU;s.stats.winU+=prizeU;}
    s.lastWinU=prizeU;s.lastBet=p.bet;s.lastWasFree=p.free;
    s.lastBaseWinU=!p.free&&!result.trigger?prizeU:0;s.grid=clone(p.grid);
    let bonusDone=null;
    if(p.free){
      s.feature.totalU+=prizeU;
      if(result.trigger){s.feature.left+=8;s.feature.retriggers++;if(p.nextSymbol)s.feature.symbols.push(p.nextSymbol);}
      if(s.feature.totalU>=limitU)s.feature.left=0;
      if(s.feature.left===0){bonusDone=clone(s.feature);s.feature=null;}
    }else if(result.trigger){s.feature={left:8,played:0,totalU:0,symbols:[p.nextSymbol],retriggers:0};s.stats.features++;}
    const outcome={id:p.id,free:p.free,result,prizeU,bonusDone,nextSymbol:p.nextSymbol,bet:p.bet};
    s.last=clone(outcome);
    const record={id:p.id,t:now,kind:p.free?'free':'spin',bet:p.free?0:p.bet,
      win:credits(prizeU),scatters:result.scatterCount,bonus:p.free,
      grid:clone(p.grid),lines:p.lines,lineWins:clone(result.lineWins),
      scatterWin:result.scatterWin,paylinesVersion:p.paylinesVersion??1,
      expansionPayVersion:p.expansionPayVersion??1,towerPayVersion:p.towerPayVersion??0};
    if(p.free){
      const f=s.feature||bonusDone;
      const groupId=f.historyId||p.id;
      let group=s.history.find(h=>h.kind==='free-bonus'&&h.id===groupId);
      if(!group){
        group={kind:'free-bonus',id:groupId,t:now,updatedAt:now,bet:0,win:0,
          played:0,retriggers:0,symbols:[],rounds:[],completed:false};
        s.history.unshift(group);
      }
      group.rounds.push(record);
      group.played=f.played;
      group.win=credits(f.totalU);
      group.retriggers=f.retriggers;
      group.symbols=[...f.symbols];
      group.updatedAt=now;
      group.completed=!!bonusDone;
    }else{
      s.history.unshift(record);
    }
    s.history=s.history.slice(0,100);
    s.pending=null;return {...outcome,payout:s.payout?clone(s.payout):null};
  }
  function gamble(s,guess,rng=E.randomFloat,now=new Date().toISOString()){
    validate(s);if(!['red','black','hearts','diamonds','clubs','spades'].includes(guess))throw Error('Escolha inválida');
    if(s.pending||s.payout||s.feature||s.lastBaseWinU<=0||s.gambles>=5||s.balanceU<s.lastBaseWinU)throw Error('Prémio indisponível');
    const card=['hearts','diamonds','clubs','spades'][Math.floor(rng()*4)],red=card==='hearts'||card==='diamonds';
    const won=guess===card||(guess==='red'&&red)||(guess==='black'&&!red),multiplier=guess==='red'||guess==='black'?2:4;
    const stakeU=s.lastBaseWinU,prizeU=won?Math.min(stakeU*multiplier,units(E.upperLimit(s.lastBet))):0;
    s.balanceU+=prizeU-stakeU;s.lastBaseWinU=prizeU;s.lastWinU=prizeU;s.gambles++;
    s.history.unshift({t:now,kind:'gamble',bet:credits(stakeU),win:credits(prizeU),card});s.history=s.history.slice(0,100);
    return {card,won,prizeU};
  }
  // Multiplicadores 1×–5× da aposta, respeitando o máximo de 10 créditos.
  // Não alterar a aposta durante jogadas pendentes ou bónus.
  function setBetMultiplier(s,multiplier){
    validate(s);
    if(s.feature||s.pending||s.payout)throw Error('Termina a jogada primeiro');
    if(!Number.isInteger(multiplier)||multiplier<1||multiplier>5)
      throw Error('Multiplicador inválido');
    const proposed=Math.round(units(BETS[s.betIndex])*s.lines*multiplier/10)/UNIT;
    if(proposed>10)throw Error('Aposta máxima de 10 € por jogada');
    s.betMultiplier=multiplier;
    validate(s);
    return proposed;
  }
  // Depósitos e levantamentos são apenas movimentos de créditos locais.
  function adjustCredits(s,operation,value,now=new Date().toISOString()){
    validate(s);
    if(s.feature||s.pending||s.payout)throw Error('Termina o bónus antes de movimentar créditos');
    if(!['deposit','withdraw'].includes(operation))throw Error('Operação inválida');
    const amount=Math.round(Number(value)*100)/100;
    if(!Number.isFinite(amount)||amount<=0||amount>10000)
      throw Error('Indica um valor entre 0,01 € e 10 000 €');
    const changeU=units(amount);
    if(operation==='withdraw'&&changeU>s.balanceU)throw Error('Saldo insuficiente');
    if(operation==='deposit'&&s.balanceU+changeU>units(10000000))
      throw Error('Limite de saldo atingido');
    s.balanceU+=operation==='deposit'?changeU:-changeU;
    s.history.unshift({kind:operation,t:now,amount,bet:0,win:0});
    s.history=s.history.slice(0,100);
    return {amount,balanceU:s.balanceU};
  }
  function reset(s,now=new Date().toISOString()){
    validate(s);if(s.pending||s.payout||s.feature)throw Error('Termina o bónus primeiro');
    s.balanceU=0;s.lastWinU=0;s.lastBaseWinU=0;s.gambles=0;s.last=null;
    s.history.unshift({t:now,kind:'reset',bet:0,win:0});s.history=s.history.slice(0,100);
  }
  return {UNIT,BETS,units,credits,stake,fresh,validate,restore,migrate,buyBonus,begin,settle,creditNext,finishPayout,sequencePayouts,setBetMultiplier,adjustCredits,gamble,reset,gridOK};
});
