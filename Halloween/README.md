# Halloween III — Clássica (V0.13, reconstrução independente)

Jogo browser em **Phaser 3** (formato 9:16), inspirado na interface de caça-níqueis Halloween III dos anos 2000.

**Importante:** esta é uma reconstrução experimental, com ilustrações próprias. Não é o executável original nem um port 1:1, e os pagamentos, probabilidades, RTP e bónus **não são certificados nem representam a matemática original**. Só utiliza **créditos virtuais**; não tem dinheiro real, depósitos nem levantamentos.

## Referências de visual e jogabilidade

- Fotografia da máquina Halloween III num artigo do Ministério Público do Rio Grande do Sul, 05-04-2013: https://www.mprs.mp.br/noticias/31486/
- Descrição de versões e mecânicas (20/25/30 linhas, travar tambores, bónus): https://www.casinobillionaire.com/portuguese/jogo-caca-niquel-halloween/
- Manual histórico de configuração das máquinas: https://pt.scribd.com/document/696584543/Manual-Completo-Halloween

## Modo de recursos visuais originais do APK (uso pessoal)

O código Phaser suporta agora um **modo opcional de recursos originais** sem incluir ficheiros comerciais no repositório público. No pacote pessoal criado a partir do XAPK enviado pelo utilizador:

- O `index.html` ativa `window.HALLOWEEN_ORIGINAL_ASSETS = true` antes de carregar `js/game.js`.
- O motor usa `assets-original/sprites/*.webp` para as dez peças da máquina e `*-win.webp` nas combinações premiadas (atlas `01_slots.webp`).
- O cenário 9:16 aproveita o logótipo e a textura de `01_telab2.webp`; a roleta usa `01_roleta.webp` e os efeitos áudio foram extraídos de `assets/sons/halloween/`.
- **Sem o pacote** ou com o sinalizador desativado, o jogo continua a funcionar com SVG e sons sintetizados próprios, sem pedidos a ficheiros que não existem.
- O pacote foi concebido para **uso pessoal**. A disponibilização pública de gráficos e áudio comerciais exige autorização do respetivo titular dos direitos. As fórmulas do jogo permanecem experimentais, mesmo com a arte original.

## Melhorias visuais V0.13

- Dez símbolos SVG com faces em relevo, duas camadas de espessura 3D, reflexos, sombras e placas metálicas; rasterização em 312 × 348 para maior nitidez em ecrãs Retina.
- Logótipo «Halloween III» com extrusão dourada e iluminação superior.
- Botões JOGAR, aposta, AUTO, SOM e ajuda com aro metálico, volume, reflexo e resposta visual ao toque.
- Moldura dos cinco tambores com friso em bronze e superfícies interiores com profundidade.
- Versão identificada no ecrã; ficheiros de arte com cache renovada no GitHub Pages.
- Matemática, tabela de apostas, 30 linhas, recolha de abóboras e regras de bónus da V0.12 mantidas sem alteração.

## Correção da frequência dos bónus V0.12

Os mini-bónus de caldeirões, morangos e dados eram demasiado raros. Esta versão regista os giros pagos desde o último mini-bónus e aumenta progressivamente a probabilidade de **três caldeirões na vertical**, sem passar de 49 giros pagos sem mini-bónus. Os desenhos, a lógica dos 25+5 percursos e os cinco escalões de aposta são preservados.

Esta regra de entretenimento **não existe como regra confirmada do APK**. Consulta a [análise de bónus V0.12](docs/bonus-vertical-frequente-v011.md) para os valores, a limitação das simulações e a forma de recuperar sessões anteriores.

## Linhas originais recuperadas — V0.12

Foi descodificado o formato `LNH1` do APK e foram reconstruídos os **30 desenhos originais**. A ordem real é **1–20 horizontais, 21–25 verticais, 26–30 horizontais**, diferente da ordem que usávamos anteriormente. A numeração do motor e dos marcadores foi corrigida; a geometria discreta dos símbolos e a matemática do jogo continuam interpretações do protótipo, ainda não equivalentes ao algoritmo Android.

[Consultar a descodificação técnica e a origem da numeração](docs/linhas-originais-lnh1.md).

## Implementado nesta V0.12

- Ecrã retro com **5 tambores × 3 linhas** de fundo claro, nome Halloween III, marcadores de linhas coloridos, indicadores de crédito/prémio/aposta e quatro acumulados virtuais.
- Ilustrações **SVG originais** para os **10 tipos encontrados no XAPK**: abóbora Halloween, bruxa verde, WITCH, vampiro, fantasma, bruxa na lua, monstro, diabinho, MAGIC e caldeirão. Não são extraídas de ficheiros comerciais.
- **Cinco escalões fixos de aposta total e linhas ativas** (valores em cêntimos virtuais):
  - 0,01 € → 1 linha
  - 0,05 € → 5 linhas
  - 0,10 € → 15 linhas
  - 0,15 € → 25 linhas
  - 0,20 € → 30 linhas

  Os botões `−` e `+` mudam o escalão completo; **a aposta total não é multiplicada pelo número de linhas**. A versão inicia no escalão mínimo de 0,01 € e 1 linha.
- Giros com tiras de 5 sprites por tambor, máscaras de recorte e desaceleração progressiva, paragem por ordem, linhas vencedoras **desenhadas sobre os símbolos e exibidas uma de cada vez**, sons arcade sintetizados em WebAudio (com melodia própria de celebração) e botão automático.
- **Bónus dos 12 morangos**, em grelha 4×3, com seleção por toque e prémio guardado antes da animação. A documentação do APK confirma 12 elementos gráficos, mas os valores e a forma de o ativar são experimentais.
- **Novo bónus dos dados vermelhos**, com cinco escolhas, inspirado nas cinco animações referenciadas pelo bytecode `InitDados` e no atlas `01_dado.webp`. Os cinco dados são desenhados em Phaser, sem copiar os sprites do APK. Os valores de 1 a 6 e o prémio aposta × face são regras fictícias do protótipo, não a fórmula `NumDado`/ `SetDadoPremio`.
- **Travar até quatro tambores após uma jogada sem prémio**, tocando nos próprios tambores. Todos os giros seguintes debitam normalmente a aposta.
- **25 linhas da esquerda para a direita** e **cinco linhas verticais** (linhas 21–25, identificadas no APK), uma por tambor, para prémios de três símbolos iguais ou combinações com MAGIC. Na numeração original, as cinco verticais entram no escalão de 25 linhas (0,15 €); as horizontais 26–30 são ativadas apenas com 30 linhas (0,20 €). Os percursos horizontais continuam a ser aproximações próprias do projeto, não os dados de `LNH1` descodificados.
- **Bónus sobretudo verticais:** três caldeirões no mesmo tambor, um sobre o outro, abrem um mini-jogo de caldeirões, morangos ou dados; cinco ou mais caldeirões dispersos são uma alternativa. Três ou quatro caldeirões dispersos mantêm os Free Spins mas não abrem automaticamente um mini-jogo. Os três caldeirões empilhados são destacados antes da janela de bónus, mesmo se as linhas verticais pagas não estiverem ativas. Esta é uma regra experimental para refletir a referência do utilizador; as probabilidades exatas do APK continuam por verificar.
- No bónus de roleta mantém-se a regra provisória de cinco abóboras numa linha horizontal completa.
- Uma abóbora é recolhida em rondas pagas com **quatro ou mais abóboras visíveis** (máximo de uma por ronda). Ao reunir cinco, atribui **cinco Free Spins**. Durante estas não se recolhem novas abóboras.
- A **roleta da sorte, com 30 números**, só abre quando aparecem **cinco abóboras exatamente alinhadas numa linha horizontal ativa**. Isto substitui o gatilho antigo, que abria demasiadas roletas. Os prémios são demonstrativos: multiplicadores da aposta ou um de quatro acumulados virtuais.
- Os mini-jogos guardam os resultados antes das animações; a sessão recupera mesmo se o navegador for fechado. Todas estas regras continuam a ser **reconstruções experimentais, não as fórmulas originais do APK**.
- Guardar saldo, linhas, aposta, acumulados, giros, grelha final e bónus de caldeirão, morangos, dados ou roleta pendente no armazenamento local, quando disponível. O resultado do giro é guardado antes de começar a animação: ao reabrir o jogo, a combinação e os créditos são recuperados. Aposta e linhas são bloqueadas durante Free Spins, e a seleção de aposta/linhas limpa travas de rolos.

## Valores e recuperação de sessões

A lógica guarda o saldo, os prémios e os quatro acumulados como **cêntimos fictícios inteiros** e apresenta-os em euros apenas para facilitar a leitura. O saldo de 1.000 unidades de uma sessão anterior é, portanto, apresentado como 10,00 € em créditos virtuais. A seleção antiga de 20/25/30 linhas é convertida para um dos novos pares válidos, sem eliminar o histórico nem bónus pendentes.

Os prémios das linhas são arredondados aos cêntimos, e as frações menores de um cêntimo destinadas aos quatro acumulados são guardadas entre giros, para não se perderem nas apostas pequenas. A matemática continua a ser original de demonstração, não copiada do APK.

## Tabela de pagamentos de referência

- Botão `?` junto ao título abre uma tabela com valores visualmente observados em `01_tabela1.webp` e `01_tabela2.webp`: Halloween, MAGIC, WITCH e caldeirões.
- A tabela é **documental**: a V0.8 não utiliza esses valores como coeficientes do motor e não afirma que a conversão para créditos esteja recuperada.
- Os prémios efetivamente atribuídos ainda derivam da tabela matemática demonstrativa de `js/engine.js`, que **não está validada como RTP ou matemática da aplicação Android**.
- `GameHalloween30L.NumRoleta` lê campos associados a prémio, crédito e dificuldade e chama `calcbonusRoleta`; isso não revela os pesos das casas nem autoriza uma replicação fiel de probabilidades.

## Referência técnica do XAPK

- [Auditoria estática detalhada](docs/analise-xapk-original-6-68.md): imagens, áudio, binário LNH1 e classe Java/Dalvik `GameHalloween30L` (231 métodos).
- [Transcrição parcial da tabela de prémios](docs/valores-visiveis-tabelas-original.json): valores visíveis do APK, **não usados ainda como matemática de jogo**.
- [Auditoria dos créditos virtuais V0.8](docs/auditoria-creditos-v06.md): correção da roleta excessivamente frequente e simulações determinísticas. Não é certificado de RTP.
- O modo inicial é agora de **30 linhas**. A reprodução 9:16 mantém símbolos próprios e não reutiliza os recursos comerciais do APK no repositório público.

- [Mapa dos bónus da versão Android](docs/bonus-android-v07.md): evidências Dalvik para morangos, caldeirões, roleta e dados.
- [Análise dos dados V0.8](docs/dados-android-v08.md): atlas original, cinco animações e distinção entre evidências e hipóteses.

## Estrutura

- `index.html` — viewport e carregamento do jogo.
- `js/engine.js` — cálculos e simulação.
- `js/art.js` — biblioteca SVG original.
- `js/game.js` — cena Phaser, som e interface.
- `tests/engine.test.cjs` — testes do motor e regressão económica.
- `tests/ui-smoke.test.cjs` — testes de cena Phaser sem navegador, incluindo seleção e recuperação dos morangos e dados.
- `tests/browser.cjs` — Chromium/WebKit, incluindo tabela e recuperação de sessão.
- `.github/workflows/testar-halloween.yml` — workflow de testes.
- `.github/workflows/publicar-3-jogos.yml` — publicação comum dos 4 jogos, num único GitHub Pages.

O Phaser é importado localmente de `../jardim-phaser/vendor/phaser-3.90.0.min.js`; não é necessário pagar serviços externos.

## Validação

- `node --test Halloween/tests/engine.test.cjs` — testes de regras, prémios, salvaguardas, 20.000 giros simulados e regressão económica.
- `node --test Halloween/tests/ui-smoke.test.cjs` — verifica carregamento da cena, tabela, giros e restauração de sessão.
- `node Halloween/tests/browser.cjs` — testes de renderização e interação em Chromium e Safari/WebKit no servidor de testes (requer Playwright instalado).
- O workflow independente cria primeiro o ZIP V0.8 após os testes locais e só depois tenta os testes pesados em Chromium/WebKit. Assim, uma falha na instalação dos browsers não impede a criação do ZIP.

## Próximos passos

Substituir as restantes aproximações pelas regras que conseguirmos verificar da versão original; melhorar a roleta, os acumulados e os efeitos sonoros. O GitHub Pages precisa de publicação bem-sucedida antes de se poder confirmar o URL público. Os resultados dos testes de Chromium/WebKit são verificados no workflow GitHub Actions; não presumir que passaram antes de ver os respetivos registos. **Análise do XAPK fornecido:** [`docs/analise-xapk-original-6-68.md`](docs/analise-xapk-original-6-68.md). O atlas original comprova os 10 tipos de figuras e a roleta com 30 números; a classe Android `GameHalloween30L` inclui 231 métodos com código, porém ainda não se recuperou a fórmula original de prémios. O visual original serve apenas de referência, sem reutilização de recursos protegidos.
