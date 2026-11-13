/* Tabelas agregadas por PRÉMIO TOTAL do bilhete, extraídas dos versos oficiais
 * das emissões Santa Casa de 2026. Não contêm os bilhetes individuais, sequências
 * nem a posição original dos números/multiplicadores.
 * Os valores são euros inteiros e as quantidades contam bilhetes premiados.
 * Cada modalidade tem uma tiragem fechada INDEPENDENTE com contagem decrescente
 * por prémio total (incluindo bilhetes sem prémio). Uma compra retira uma unidade.
 * O motor FILTRA os prémios pelo fundo de cada modalidade, formado
 * pela percentagem configurável das novas vendas (60% por defeito)
 * e reduzido pelos prémios atribuídos. Portanto probabilidades
 * e RTP do motor não correspondem aos da Santa Casa. Os valores/quantidades
 * deste ficheiro são apenas a distribuição oficial de REFERÊNCIA.
 * A tiragem é guardada por navegador
 * em localStorage; não é sincronizada entre dispositivos, contas ou servidores.
 * Fontes oficiais: URLs verso nos objetos.
 */
window.SANTA_PLANS={
  "10": {
    "bet": 100,
    "emission": 40000000,
    "maxPrize": 10000,
    "luckyCount": 1,
    "playCount": 5,
    "multipliers": [
      2,
      5,
      10
    ],
    "game": 659,
    "prizes": [
      [
        1,
        5873000
      ],
      [
        2,
        2714000
      ],
      [
        5,
        1140000
      ],
      [
        10,
        560000
      ],
      [
        20,
        13000
      ],
      [
        40,
        1250
      ],
      [
        100,
        390
      ],
      [
        10000,
        25
      ]
    ],
    "totalPrizes": 23200000,
    "url": "https://www.jogossantacasa.pt/Content/images/uploadedImages/content/pjmc/gc/cont/58884/Verso_10X659.jpg"
  },
  "20": {
    "bet": 200,
    "emission": 30000000,
    "maxPrize": 20000,
    "luckyCount": 2,
    "playCount": 10,
    "multipliers": [
      2,
      5,
      10,
      20
    ],
    "game": 667,
    "prizes": [
      [
        2,
        4140000
      ],
      [
        4,
        2407500
      ],
      [
        5,
        970000
      ],
      [
        10,
        397000
      ],
      [
        20,
        300000
      ],
      [
        25,
        100000
      ],
      [
        40,
        23900
      ],
      [
        50,
        6000
      ],
      [
        100,
        1000
      ],
      [
        500,
        788
      ],
      [
        1000,
        100
      ],
      [
        20000,
        6
      ]
    ],
    "totalPrizes": 37200000,
    "url": "https://www.jogossantacasa.pt/Content/images/uploadedImages/content/pjmc/gc/cont/59166/verso_raspadinha_20X_667.jpg"
  },
  "50": {
    "bet": 500,
    "emission": 16004800,
    "maxPrize": 100000,
    "luckyCount": 5,
    "playCount": 15,
    "multipliers": [
      2,
      5,
      10,
      20,
      50
    ],
    "game": 669,
    "prizes": [
      [
        5,
        2286400
      ],
      [
        10,
        1204000
      ],
      [
        15,
        648000
      ],
      [
        20,
        264000
      ],
      [
        25,
        50000
      ],
      [
        30,
        54395
      ],
      [
        50,
        40000
      ],
      [
        100,
        20000
      ],
      [
        500,
        6106
      ],
      [
        1000,
        1109
      ],
      [
        5000,
        420
      ],
      [
        100000,
        12
      ]
    ],
    "totalPrizes": 52815850,
    "url": "https://www.jogossantacasa.pt/Content/images/uploadedImages/content/pjmc/gc/cont/59263/verso_raspadinha_50X_669.jpg"
  },
  "100": {
    "bet": 1000,
    "emission": 15000000,
    "maxPrize": 500000,
    "luckyCount": 5,
    "playCount": 20,
    "multipliers": [
      2,
      5,
      10,
      20,
      50,
      100
    ],
    "game": 677,
    "prizes": [
      [
        10,
        1864500
      ],
      [
        15,
        900000
      ],
      [
        20,
        540000
      ],
      [
        25,
        420000
      ],
      [
        40,
        300000
      ],
      [
        50,
        150000
      ],
      [
        100,
        75000
      ],
      [
        200,
        28650
      ],
      [
        500,
        11150
      ],
      [
        1000,
        3000
      ],
      [
        5000,
        750
      ],
      [
        10000,
        100
      ],
      [
        500000,
        5
      ]
    ],
    "totalPrizes": 102000000,
    "url": "https://www.jogossantacasa.pt/Content/images/uploadedImages/content/pjmc/gc/cont/59049/verso_raspadinha_100X_677.jpg"
  }
};
for(const p of Object.values(window.SANTA_PLANS)){
  const awards=p.prizes.reduce((t,[v,n])=>t+v*n,0);
  const count=p.prizes.reduce((t,[v,n])=>t+n,0);
  if(awards!==p.totalPrizes||count>p.emission)throw Error('Plano oficial inconsistente: '+p.game);
  p.winningTickets=count;
  p.rtp=awards/(p.emission*p.bet/100);
}
