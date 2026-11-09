/* Faraó Eterno — transactional, recoverable fictional-credit session.
 * One credit = 252000 integer units: exactly represents all approved line stakes.
 * The V0.6 outcome evaluator, weights, lines and paytable remain unchanged.
 */
(function(root,factory){
  if(typeof module==='object'&&module.exports)module.exports=factory(require('./engine.js'));
  else root.FaraoSession=factory(root.TombEngine);
})(typeof globalThis!=='undefined'?globalThis:this,function(E){
  'use strict';
  const UNIT=252000, BETS=[.10,.20,.40,.50,1,2,5,10];
  const units=n=>Math.round(n*UNIT), credits=n=>n/UNIT;
  const clone=x=>JSON.parse(JSON.stringify(x));
  const integer=n=>Number.isSafeInteger(n)&&n>=0;
  const gridOK=g=>Array.isArray(g)&&g.length===5&&g.every(c=>Array.isArray(c)&&c.length===3&&c.every(s=>E.ALL.includes(s))&&c.filter(s=>s==='TOMB').length<=1);
  const featureOK=f=>f===null||(f&&integer(f.left)&&integer(f.played)&&integer(f.retriggers)&&integer(f.totalU)&&Array.isArray(f.symbols)&&f.symbols.length>0&&f.symbols.length<=9&&new Set(f.symbols).size===f.symbols.length&&f.symbols.every(s=>E.NORMAL.includes(s)));
  function fresh(){return {version:2,balanceU:units(300),betIndex:4,lines:10,grid:[['10','J','Q'],['HORUS','MASK','K'],['A','TOMB','10'],['ANUBIS','K','J'],['KING','A','Q']],feature:null,pending:null,sequence:0,lastWinU:0,lastBaseWinU:0,lastBet:1,gambles:0,lastWasFree:false,history:[],stats:{paid:0,free:0,stakeU:0,winU:0,features:0},settings:{sound:true,music:true,turbo:false},last:null};}
  function validate(s){
    if(!s||s.version!==2||!integer(s.balanceU)||!integer(s.sequence)||!Number.isInteger(s.betIndex)||s.betIndex<0||s.betIndex>=BETS.length||!Number.isInteger(s.lines)||s.lines<1||s.lines>10||!gridOK(s.grid)||!featureOK(s.feature)||!integer(s.lastWinU)||!integer(s.lastBaseWinU)||!BETS.includes(s.lastBet)||!integer(s.gambles)||s.gambles>5||!Array.isArray(s.history)||s.history.length>100)throw Error('Sessão inválida');
    if(!s.stats||!['paid','free','stakeU','winU','features'].every(k=>integer(s.stats[k])))throw Error('Estatísticas inválidas');
    if(s.feature&&s.feature.totalU>units(E.upperLimit(BETS[s.betIndex])))throw Error('Bónus inválido');
    if(s.pending){const p=s.pending;
      if(!gridOK(p.grid)||p.id!==s.sequence||p.bet!==BETS[s.betIndex]||p.lines!==s.lines||typeof p.free!=='boolean'||(p.free&&!s.feature)||(!p.free&&s.feature)||!(p.nextSymbol===null||E.NORMAL.includes(p.nextSymbol)))throw Error('Jogada pendente inválida');
      const triggered=E.countScatters(p.grid)>=3,choices=p.free?E.NORMAL.filter(x=>!s.feature.symbols.includes(x)):E.NORMAL;
      if(triggered&&choices.length&&!choices.includes(p.nextSymbol)||!triggered&&p.nextSymbol!==null)throw Error('Seleção de bónus inválida');
    }
    if(s.feature&&s.feature.left===0&&!s.pending)throw Error('Bónus terminado pendente');
    return s;
  }
  function restore(raw){const s=clone(raw);validate(s);s.settings={sound:s.settings?.sound!==false,music:s.settings?.music!==false,turbo:!!s.settings?.turbo};return s;}
  function migrate(old){
    const s=fresh();if(!old||typeof old!=='object')return s;
    if(Number.isFinite(old.balance)&&old.balance>=0)s.balanceU=units(Math.min(old.balance,10000000));
    if(Number.isInteger(old.betIndex))s.betIndex=Math.max(0,Math.min(BETS.length-1,old.betIndex));
    if(Number.isInteger(old.lines))s.lines=Math.max(1,Math.min(10,old.lines));
    const f=old.feature;
    if(f&&Number.isSafeInteger(f.left)&&f.left>0&&Number.isSafeInteger(f.played)&&f.played>=0&&Number.isFinite(f.total)&&f.total>=0){
      const candidate={left:f.left,played:f.played,totalU:units(f.total),symbols:f.symbols,retriggers:f.retriggers||0};
      if(featureOK(candidate)&&candidate.totalU<units(E.upperLimit(BETS[s.betIndex])))s.feature=clone(candidate);
    }
    if(Array.isArray(old.history))s.history=old.history.filter(h=>h&&typeof h.t==='string'&&Number.isFinite(h.bet)&&Number.isFinite(h.win)).slice(0,100).map(h=>({...h,kind:h.bonus?'free':'spin'}));
    s.settings.sound=old.sound!==false;return validate(s);
  }
  function begin(s,rng=E.randomFloat){
    validate(s);if(s.pending)throw Error('Existe uma jogada por terminar');
    const bet=BETS[s.betIndex],free=!!s.feature;
    if(!free&&s.balanceU<units(bet))throw Error('Créditos insuficientes');
    // Commit the entire outcome, including feature selection, before animation.
    const grid=E.makeGrid(rng),result=E.evaluate(grid,bet,s.lines,free?s.feature.symbols:[]);
    let nextSymbol=null;
    if(result.trigger){const choices=free?E.NORMAL.filter(x=>!s.feature.symbols.includes(x)):E.NORMAL;
      if(choices.length)nextSymbol=choices[Math.floor(rng()*choices.length)];}
    if(free){s.feature.left--;s.feature.played++;s.stats.free++;}
    else{s.balanceU-=units(bet);s.gambles=0;s.lastBaseWinU=0;s.stats.paid++;s.stats.stakeU+=units(bet);}
    s.pending={id:++s.sequence,grid,free,bet,lines:s.lines,nextSymbol};
    return {pending:s.pending,result};
  }
  function settle(s,now=new Date().toISOString()){
    if(!s.pending)return null;validate(s);
    const p=s.pending,result=E.evaluate(p.grid,p.bet,p.lines,p.free?s.feature.symbols:[]);
    const limitU=units(E.upperLimit(p.bet));
    const prizeU=Math.max(0,Math.min(units(result.total),p.free?limitU-s.feature.totalU:limitU));
    s.balanceU+=prizeU;s.stats.winU+=prizeU;s.lastWinU=prizeU;s.lastBet=p.bet;s.lastWasFree=p.free;
    s.lastBaseWinU=!p.free&&!result.trigger?prizeU:0;s.grid=clone(p.grid);
    let bonusDone=null;
    if(p.free){
      s.feature.totalU+=prizeU;
      if(result.trigger){s.feature.left+=8;s.feature.retriggers++;if(p.nextSymbol)s.feature.symbols.push(p.nextSymbol);}
      if(s.feature.totalU>=limitU)s.feature.left=0;
      if(s.feature.left===0){bonusDone=clone(s.feature);s.feature=null;}
    }else if(result.trigger){s.feature={left:8,played:0,totalU:0,symbols:[p.nextSymbol],retriggers:0};s.stats.features++;}
    const outcome={id:p.id,free:p.free,result,prizeU,bonusDone,nextSymbol:p.nextSymbol,bet:p.bet};
    s.last=clone(outcome);s.history.unshift({t:now,kind:p.free?'free':'spin',bet:p.free?0:p.bet,win:credits(prizeU),scatters:result.scatterCount,bonus:p.free});s.history=s.history.slice(0,100);
    s.pending=null;return outcome;
  }
  function gamble(s,guess,rng=E.randomFloat,now=new Date().toISOString()){
    validate(s);if(!['red','black','hearts','diamonds','clubs','spades'].includes(guess))throw Error('Escolha inválida');
    if(s.pending||s.feature||s.lastBaseWinU<=0||s.gambles>=5||s.balanceU<s.lastBaseWinU)throw Error('Prémio indisponível');
    const card=['hearts','diamonds','clubs','spades'][Math.floor(rng()*4)],red=card==='hearts'||card==='diamonds';
    const won=guess===card||(guess==='red'&&red)||(guess==='black'&&!red),multiplier=guess==='red'||guess==='black'?2:4;
    const stakeU=s.lastBaseWinU,prizeU=won?Math.min(stakeU*multiplier,units(E.upperLimit(s.lastBet))):0;
    s.balanceU+=prizeU-stakeU;s.lastBaseWinU=prizeU;s.lastWinU=prizeU;s.gambles++;
    s.history.unshift({t:now,kind:'gamble',bet:credits(stakeU),win:credits(prizeU),card});s.history=s.history.slice(0,100);
    return {card,won,prizeU};
  }
  function reset(s,now=new Date().toISOString()){
    validate(s);if(s.pending||s.feature)throw Error('Termina o bónus primeiro');
    s.balanceU=units(300);s.lastWinU=0;s.lastBaseWinU=0;s.gambles=0;s.last=null;
    s.history.unshift({t:now,kind:'reset',bet:0,win:0});s.history=s.history.slice(0,100);
  }
  return {UNIT,BETS,units,credits,fresh,validate,restore,migrate,begin,settle,gamble,reset,gridOK};
});
