/* Cronicas do Farao - independent demo math engine.
 * Inspired by familiar 5x3 Egyptian slot mechanics; no proprietary reel strips or RNG.
 * Money is expressed in credits (floating point); no real-money transactions.
 */
(function (root, factory) {
  if (typeof module === 'object' && module.exports) module.exports = factory();
  else root.TombEngine = factory();
})(typeof globalThis !== 'undefined' ? globalThis : this, function () {
  'use strict';
  const NORMAL = ['10','J','Q','K','A','HORUS','MASK','ANUBIS','KING'];
  const ALL = [...NORMAL, 'TOMB'];
  const WEIGHTS = { '10':18,'J':17,'Q':16,'K':15,'A':14,'HORUS':8,'MASK':7,'ANUBIS':5,'KING':3,'TOMB':3.85 };
  // Nas Free Spins, um peso Scatter moderadamente maior permite mais
  // retriggers reais e situações de 2 torres com suspense. Nunca altera
  // a distribuição do jogo base nem força o resultado do quinto tambor.
  const FREE_WEIGHTS = Object.freeze({...WEIGHTS,TOMB:6.5});
  const PAYS = {
    '10':{3:5,4:20,5:80}, J:{3:5,4:20,5:80}, Q:{3:5,4:20,5:80},
    K:{3:5,4:30,5:120}, A:{3:5,4:30,5:120},
    HORUS:{2:5,3:20,4:80,5:600}, MASK:{2:5,3:20,4:80,5:600},
    ANUBIS:{2:5,3:30,4:300,5:1600}, KING:{2:10,3:100,4:1000,5:5000},
    TOMB:{2:10,3:100,4:1000,5:5000}
  };
  // Geometria aprovada na tabela do utilizador (0=cima, 1=meio, 2=baixo).
  // Só as linhas mudam nesta versão: valores da tabela, pesos e RNG mantêm-se.
  const LINES = [
    [1,1,1,1,1], [0,0,0,0,0], [2,2,2,2,2],
    [0,1,2,1,0], [2,1,0,1,2],
    [1,0,0,0,1], [1,2,2,2,1],
    [0,0,1,2,2], [2,2,1,0,0], [1,0,1,2,1]
  ];
  // Geometria anterior: necessária para liquidar jogadas pendentes e
  // consultar resultados históricos sem reescrever prémios antigos.
  const LEGACY_LINES = [
    [1,1,1,1,1], [0,0,0,0,0], [2,2,2,2,2],
    [0,1,2,1,0], [2,1,0,1,2],
    [0,0,1,2,2], [2,2,1,0,0],
    [1,0,1,2,1], [1,2,1,0,1], [0,1,1,1,2]
  ];
  const PAYLINES_VERSION = 2;
  const randomFloat = () => {
    if (typeof crypto !== 'undefined' && crypto.getRandomValues) {
      const arr = new Uint32Array(1); crypto.getRandomValues(arr); return arr[0] / 4294967296;
    }
    return Math.random();
  };
  function pickWeighted(weights = WEIGHTS, rng = randomFloat) {
    const total = Object.values(weights).reduce((a,b)=>a+b,0);
    let point = rng() * total;
    for (const [symbol, weight] of Object.entries(weights)) {
      point -= weight;
      if (point < 0) return symbol;
    }
    return Object.keys(weights).at(-1);
  }
  function makeGrid(rng = randomFloat, weights = WEIGHTS) {
    // Sorteio ponderado sem reposição: três símbolos diferentes em cada tambor.
    // A tabela de pagamentos e evaluate() não mudam.
    return Array.from({length:5}, () => {
      const pool = Object.fromEntries(Object.entries(weights)
        .filter(([,w]) => Number.isFinite(w) && w > 0));
      if (Object.keys(pool).length < 3) throw new Error('São necessários três símbolos diferentes');
      const column=[];
      for (let row=0;row<3;row++) {
        const symbol=pickWeighted(pool,rng);
        column.push(symbol);
        delete pool[symbol];
      }
      return column;
    });
  }
  function countScatters(grid) {
    return grid.flat().filter(s=>s==='TOMB').length;
  }
  function lineCandidate(grid, rows, symbol) {
    let n=0;
    for(let c=0;c<5;c++) {
      const actual=grid[c][rows[c]];
      if (actual === symbol || (actual==='TOMB' && symbol!=='TOMB')) n++;
      else break;
    }
    return n;
  }
  function evaluate(grid, bet = 1, activeLines = 10, special = [], paylines = LINES, expansionMode = 'separate') {
    if(!Array.isArray(grid) || grid.length !== 5 || grid.some(col=>!Array.isArray(col)||col.length!==3||col.some(symbol=>!ALL.includes(symbol)))) throw new Error('Grelha 5x3 inválida');
    if(!Number.isFinite(bet)||bet<=0)throw new Error('Aposta inválida');
    if (!Number.isInteger(activeLines) || activeLines < 1 || activeLines > 10) throw new Error('Linhas devem ser 1–10');
    const lineStake=bet/activeLines;
    const activeSpecial = [...new Set(special.filter(s=>NORMAL.includes(s)))];
    // Each expanding candidate must occur on enough *distinct reels*.
    const expanders = activeSpecial.map(symbol => {
      const reelIndices = grid.map((col,i)=>col.includes(symbol)?i:-1).filter(x=>x>=0);
      const award=PAYS[symbol][reelIndices.length] || 0;
      return {symbol, reels:reelIndices, count:reelIndices.length, award};
    }).filter(s=>s.award>0);
    const orderedExpanders=[...expanders].sort((a,b)=>NORMAL.indexOf(a.symbol)-NORMAL.indexOf(b.symbol));
    const lineWins=[];
    let paylinesTotal=0,baseTotal=0;
    // Em Free Spins, todos os expansivos elegíveis pagam individualmente.
    // Nunca pagar duas vezes uma figura que já foi coberta pela sua expansão.
    // A opção legacy conserva exatamente a matemática de jogadas antigas.
    const separate=expansionMode==='separate' && expanders.length>1;
    const expandedSymbols=new Set(expanders.map(x=>x.symbol));
    for(let i=0;i<activeLines;i++) {
      const rows=paylines[i];
      let best={symbol:null,count:0,payCoins:0,expanded:false};
      for(const symbol of ALL) {
        if(separate && expandedSymbols.has(symbol))continue;
        const count=lineCandidate(grid,rows,symbol);
        const payCoins=PAYS[symbol][count]||0;
        if(payCoins > best.payCoins) best={symbol,count,payCoins,expanded:false};
      }
      if(!separate) {
        for(const candidate of expanders) {
          if(candidate.award > best.payCoins) best={symbol:candidate.symbol,count:candidate.count,payCoins:candidate.award,expanded:true};
        }
      }
      if(best.payCoins>0) {
        const amount=best.payCoins*lineStake;
        paylinesTotal+=amount;baseTotal+=amount;
        lineWins.push({...best,line:i,rows:[...rows],amount});
      }
      if(separate)for(const candidate of orderedExpanders){
        const amount=candidate.award*lineStake;
        paylinesTotal+=amount;
        lineWins.push({symbol:candidate.symbol,count:candidate.count,payCoins:candidate.award,
          expanded:true,line:i,rows:[...rows],amount});
      }
    }
    // Histórico, animações e contabilização usam esta lista (10 → J → Q →
    // K → A → Hórus → Máscara → Anúbis → Faraó), não o valor sorteado.
    if(separate)lineWins.sort((a,b)=>{
      if(a.expanded!==b.expanded)return a.expanded?1:-1;
      if(a.expanded){const diff=NORMAL.indexOf(a.symbol)-NORMAL.indexOf(b.symbol);
        if(diff)return diff;}
      return a.line-b.line;
    });
    const expansionAwards=separate?orderedExpanders.map(candidate=>({
      symbol:candidate.symbol,count:candidate.count,reels:[...candidate.reels],
      amount:candidate.award*bet
    })):[];
    const scatterCount=countScatters(grid);
    const scatterMult=scatterCount>=5?200:scatterCount===4?20:scatterCount===3?2:0;
    const scatterWin=scatterMult*bet;
    const total=paylinesTotal+scatterWin;
    const outcome={total, paylinesTotal, scatterWin, scatterCount, trigger:scatterCount>=3, lineWins, expanders};
    return separate?{...outcome, expansionAwards, baseTotal, expansionMode:'separate'}:outcome;
  }
  function startFeature(rng = randomFloat) {
    return {left:8, played:0, total:0, symbols:[NORMAL[Math.floor(rng()*NORMAL.length)]], retriggers:0};
  }
  function retrigger(feature, rng = randomFloat) {
    feature.left += 8;
    feature.retriggers += 1;
    const remaining=NORMAL.filter(x=>!feature.symbols.includes(x));
    if(remaining.length) feature.symbols.push(remaining[Math.floor(rng()*remaining.length)]);
    return feature;
  }
  const upperLimit = (bet) => bet*5000;
  return { NORMAL, ALL, WEIGHTS, FREE_WEIGHTS, PAYS, LINES, LEGACY_LINES, PAYLINES_VERSION, randomFloat, pickWeighted, makeGrid, countScatters, lineCandidate, evaluate, startFeature, retrigger, upperLimit };
});
