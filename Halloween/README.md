# Halloween III — Clássica (V0.5, reconstrução independente)

Jogo browser em **Phaser 3** (formato 9:16), inspirado na interface de caça-níqueis Halloween III dos anos 2000.

**Importante:** esta é uma reconstrução experimental, com ilustrações próprias. Não é o executável original nem um port 1:1, e os pagamentos, probabilidades, RTP e bónus **não são certificados nem representam a matemática original**. Só utiliza **créditos virtuais**; não tem dinheiro real, depósitos nem levantamentos.

## Referências de visual e jogabilidade

- Fotografia da máquina Halloween III num artigo do Ministério Público do Rio Grande do Sul, 05-04-2013: https://www.mprs.mp.br/noticias/31486/
- Descrição de versões e mecânicas (20/25/30 linhas, travar tambores, bónus): https://www.casinobillionaire.com/portuguese/jogo-caca-niquel-halloween/
- Manual histórico de configuração das máquinas: https://pt.scribd.com/document/696584543/Manual-Completo-Halloween

## Implementado nesta V0.5

- Ecrã retro com **5 tambores × 3 linhas** de fundo claro, nome Halloween III, marcadores de linhas coloridos, indicadores de crédito/prémio/aposta e dois acumulados virtuais.
- Ilustrações **SVG originais** para os **10 tipos encontrados no XAPK**: abóbora Halloween, bruxa verde, WITCH, vampiro, fantasma, bruxa na lua, monstro, diabinho, MAGIC e caldeirão. Não são extraídas de ficheiros comerciais.
- Possibilidade de escolher **20, 25 ou 30 linhas** de pagamento e apostas de 1, 2, 5 ou 10 créditos.
- Giros com tiras de 5 sprites por tambor, máscaras de recorte e desaceleração progressiva, paragem por ordem, prémios realçados, sons arcade sintetizados em WebAudio (com melodia própria de celebração) e botão automático.
- **Travar até quatro tambores após uma jogada sem prémio**, tocando nos próprios tambores. Todos os giros seguintes debitam normalmente a aposta.
- Motor experimental com Wild (Magic) corrigido, inclusive para grupos Wild terminados por Scatter. Três ou mais caldeirões ativam Free Spins e uma janela para escolher um de três potes. A escolha é guardada antes de ser mostrada, para recuperar o bónus ao reabrir o navegador. Cinco abóboras recolhidas em giros pagos (no máximo uma por giro, quando há três ou mais na grelha) dão cinco Free Spins **e uma roleta virtual de **30 posições numeradas**, inspirada no atlas original**, com prémios experimentais de aposta × multiplicador ou um dos **quatro acumulados virtuais**. A roleta fica guardada durante interrupções e atribui o prémio apenas uma vez. As rodadas gratuitas não recolhem novas abóboras. Estas são regras recriadas, ainda diferentes das mecânicas e tabelas históricas documentadas.
- Guardar saldo, linhas, aposta, acumulados, giros, grelha final e bónus de caldeirão ou roleta pendente no armazenamento local, quando disponível. O resultado do giro é guardado antes de começar a animação: ao reabrir o jogo, a combinação e os créditos são recuperados. Aposta e linhas são bloqueadas durante Free Spins, e a seleção de aposta/linhas limpa travas de rolos.

## Referência técnica do XAPK

- [Auditoria estática detalhada](docs/analise-xapk-original-6-68.md): imagens, áudio, binário LNH1 e classe Java/Dalvik `GameHalloween30L` (231 métodos).
- [Transcrição parcial da tabela de prémios](docs/valores-visiveis-tabelas-original.json): valores visíveis do APK, **não usados ainda como matemática de jogo**.
- O modo inicial é agora de **30 linhas**. A reprodução 9:16 mantém símbolos próprios e não reutiliza os recursos comerciais do APK no repositório público.

## Estrutura

- `index.html` — viewport e carregamento do jogo.
- `js/engine.js` — cálculos e simulação.
- `js/art.js` — biblioteca SVG original.
- `js/game.js` — cena Phaser, som e interface.
- `tests/engine.test.cjs` — testes do motor.
- `.github/workflows/testar-halloween.yml` — workflow de testes.
- `.github/workflows/publicar-3-jogos.yml` — publicação comum dos 4 jogos, num único GitHub Pages.

O Phaser é importado localmente de `../jardim-phaser/vendor/phaser-3.90.0.min.js`; não é necessário pagar serviços externos.

## Validação

- `node --test Halloween/tests/engine.test.cjs` — testes de regras, prémios, salvaguardas e 20.000 giros simulados.
- `node Halloween/tests/browser.cjs` — testes de renderização e interação em Chromium e Safari/WebKit no servidor de testes (requer Playwright instalado).
- O workflow independente guarda imagens dos ecrãs e pacotes ZIP para testes.

## Próximos passos

Substituir as restantes aproximações pelas regras que conseguirmos verificar da versão original; melhorar a roleta, os acumulados e os efeitos sonoros. O GitHub Pages precisa de publicação bem-sucedida antes de se poder confirmar o URL público. Os resultados dos testes de Chromium/WebKit são verificados no workflow GitHub Actions; não presumir que passaram antes de ver os respetivos registos. **Análise do XAPK fornecido:** [`docs/analise-xapk-original-6-68.md`](docs/analise-xapk-original-6-68.md). O atlas original comprova os 10 tipos de figuras e a roleta com 30 números; a classe Android `GameHalloween30L` inclui 231 métodos com código, porém ainda não se recuperou a fórmula original de prémios. O visual original serve apenas de referência, sem reutilização de recursos protegidos.
