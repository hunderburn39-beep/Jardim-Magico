# Halloween III — Clássica (V0.2, reconstrução independente)

Jogo browser em **Phaser 3** (formato 9:16), inspirado na interface de caça-níqueis Halloween III dos anos 2000.

**Importante:** esta é uma reconstrução experimental, com ilustrações próprias. Não é o executável original nem um port 1:1, e os pagamentos, probabilidades, RTP e bónus **não são certificados nem representam a matemática original**. Só utiliza **créditos virtuais**; não tem dinheiro real, depósitos nem levantamentos.

## Referências de visual e jogabilidade

- Fotografia da máquina Halloween III num artigo do Ministério Público do Rio Grande do Sul, 05-04-2013: https://www.mprs.mp.br/noticias/31486/
- Descrição de versões e mecânicas (20/25/30 linhas, travar tambores, bónus): https://www.casinobillionaire.com/portuguese/jogo-caca-niquel-halloween/
- Manual histórico de configuração das máquinas: https://pt.scribd.com/document/696584543/Manual-Completo-Halloween

## Implementado nesta V0.2

- Ecrã retro com **5 tambores × 3 linhas** de fundo claro, nome Halloween III, marcadores de linhas coloridos, indicadores de crédito/prémio/aposta e dois acumulados virtuais.
- Ilustrações **SVG originais**: abóbora, fantasma, morcego, gato, monstro, bruxa, Magic e caldeirão. Não são extraídas de ficheiros comerciais.
- Possibilidade de escolher **20, 25 ou 30 linhas** de pagamento e apostas de 1, 2, 5 ou 10 créditos.
- Giros em sequência de paragens, prémios realçados, sons arcade e botão automático.
- **Travar um ou mais tambores após uma jogada sem prémio**, tocando nos próprios tambores. Todos os giros seguintes debitam normalmente a aposta.
- Motor de pagamentos e bónus demonstrativo da V0.1, com Wild (Magic), Scatter (caldeirão) e bónus de abóboras; ainda não reproduz os bónus de roleta, potes e acumulados reais do jogo clássico.
- Guardar saldo, linhas, aposta, acumulados e número de giros no armazenamento local, quando disponível.

## Estrutura

- `index.html` — viewport e carregamento do jogo.
- `js/engine.js` — cálculos e simulação.
- `js/art.js` — biblioteca SVG original.
- `js/game.js` — cena Phaser, som e interface.
- `tests/engine.test.cjs` — testes do motor.
- `.github/workflows/testar-halloween.yml` — workflow de testes.
- `.github/workflows/publicar-3-jogos.yml` — publicação comum dos 4 jogos, num único GitHub Pages.

O Phaser é importado localmente de `../jardim-phaser/vendor/phaser-3.90.0.min.js`; não é necessário pagar serviços externos.

## Próximos passos

Substituir as restantes aproximações pelas regras que conseguirmos verificar da versão original; acrescentar bónus da roleta e caldeirões, testar de forma automatizada em Safari/WebKit e Chromium, e verificar a publicação no Pages. O visual original serve apenas de referência, sem reutilização de recursos protegidos.
