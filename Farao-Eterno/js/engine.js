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
  const PAYS = {
    '10':{3:5,4:20,5:80}, J:{3:5,4:20,5:80}, Q:{3:5,4:20,5:80},
    K:{3:5,4:30,5:120}, A:{3:5,4:30,5:120},
    HORUS:{2:5,3:20,4:80,5:600}, MASK:{2:5,3:20,4:80,5:600},
    ANUBIS:{2:5,3:30,4:300,5:1600}, KING:{2:10,3:100,4:1000,5:5000},
    TOMB:{2:10,3:100,4:1000,5:5000}
  };
  // Line 1 middle, lines 2/3 upper/lower. Rows are indexed 0,1,2.
  const LINES = [
    [1,1,1,1,1], [0,0,0,0,0], [2,2,2,2,2],
    [0,1,2,1,0], [2,1,0,1,2],
    [1,0,0,0,1], [1,2,2,2,1],
    [0,0,1,2,2], [2,2,1,0,0], [1,0,1,2,1]
  ];
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
    // Three distinct symbols per reel; retain weighted selection for each position.
    // A symbol removed from one reel remains eligible on the other four reels.
    return Array.from({length:5}, () => {
      const available = {...weights};
      return Array.from({length:3}, () => {
        const symbol = pickWeighted(available,rng);
        delete available[symbol];
        return symbol;
      });
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
  function evaluate(grid, bet = 1, activeLines = 10, special = []) {
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
    const lineWins=[];
    let paylinesTotal=0;
    for(let i=0;i<activeLines;i++) {
      const rows=LINES[i];
      let best={symbol:null,count:0,payCoins:0,expanded:false};
      for(const symbol of ALL) {
        const count=lineCandidate(grid,rows,symbol);
        const payCoins=PAYS[symbol][count]||0;
        if(payCoins > best.payCoins) best={symbol,count,payCoins,expanded:false};
      }
      for(const candidate of expanders) {
        if(candidate.award > best.payCoins) best={symbol:candidate.symbol,count:candidate.count,payCoins:candidate.award,expanded:true};
      }
      if(best.payCoins>0) {
        const amount=best.payCoins*lineStake;
        paylinesTotal+=amount;
        lineWins.push({...best, line:i, rows:[...rows], amount});
      }
    }
    const scatterCount=countScatters(grid);
    const scatterMult=scatterCount>=5?200:scatterCount===4?20:scatterCount===3?2:0;
    const scatterWin=scatterMult*bet;
    const total=paylinesTotal+scatterWin;
    return {total, paylinesTotal, scatterWin, scatterCount, trigger:scatterCount>=3, lineWins, expanders};
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
  return { NORMAL, ALL, WEIGHTS, PAYS, LINES, randomFloat, pickWeighted, makeGrid, countScatters, lineCandidate, evaluate, startFeature, retrigger, upperLimit };
});
