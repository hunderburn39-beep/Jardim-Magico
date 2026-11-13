# Pantanal — port do APK 2.06 (estado da v2.13)

Data: 13-11-2026. Jogo web: \`Pantanal/index.html\`.

## Trabalho validado nesta fase

Recursos originais e fontes reconstruídas no ramo [pantanal-apk-206-import](https://github.com/hunderburn39-beep/Jardim-Magico/tree/pantanal-apk-206-import/Pantanal/APK-2.06). O jogo \`main\` mantém a experiência HTML.

Fontes usadas diretamente:
- \`GeneratorNew.java\`: \`Generate()\`, \`Calcpre1(1…25)\`, \`downDificult()\`.
- \`MainActivity.java\`: \`slotLinhas[0…24]\`, \`Verifica()\`, \`ShowDados()\`, \`NumDado()\`, \`SetDado()\`, \`CallBonus()\`.

Implementado e testado:
1. Substituição das linhas **1–20** pelos percursos exatos da aplicação Android.
2. Regras de formação e apresentação das linhas **21–25**, com três símbolos em coluna, um percurso por tambor.
3. Valores originais das combinações verticais: índice 0→100×, 1→70× (+roleta), 2→50× (+dados), 3→20×, 4→bónus morango, 5→20×, 6→300×, 7→250×, 8→200×, 9→bónus caldeirão. Os valores são multiplicados pela **aposta por linha**.
4. Escolha das linhas pelo botão: 1 → 5 → 9 → 15 → 25 → 1, como na aplicação Android.
5. Aposta total = aposta por linha × número de linhas. O saldo é debitado pelo total da rodada.
6. Dados: escolha entre cinco dados, ativada pela combinação vertical de três símbolos de índice 2. O multiplicador resulta de um sorteio de 80 ou 100 posições (conforme estado), e multiplica o **prémio acumulado da rodada**, não a aposta inicial.
7. A roleta passa a ser acionada por três símbolos de índice 1 na vertical; a distribuição dos prémios da roleta ainda é a reconstrução experimental da web.

## Limitações ainda em aberto

**Não se deve afirmar que o motor web já reproduz toda a matemática do APK.**

- A tabela completa de prémios **horizontais/diagonais** (com regras *Any*, da direita para a esquerda e posições não contíguas) ainda usa a implementação antiga. É o próximo módulo a migrar.
- A condição de \`downDificult()\` que seleciona a distribuição dos dados foi **aproximada**: usa saldo inferior a 42 500 e menos de 500 rodadas, enquanto o APK também considera \`bloqueado\`, estados persistentes e outros controlos. As probabilidades exatas só serão validadas após porta integral.
- A roleta, o caldeirão, os morangos, o dobro e os três acumulados ainda não estão completos.
- O RTP da v2.13 precisa ser novamente auditado por simulação; o cálculo de 5 396% da auditoria anterior referia-se à versão web antes desta revisão e **não é aplicável à v2.13**.
- A obra e os recursos do APK podem estar protegidos por direitos de terceiros. A adaptação não certifica o jogo para dinheiro real.

## Testes de regressão

\`\`\`bash
node --check Pantanal/original_rules.js
node --check Pantanal/paylines.js
node Pantanal/tests/original_rules.test.cjs
\`\`\`

Testes: 20 percursos, cinco verticais, tabela de 10 símbolos para prémios verticais, ativação de roleta e dados apenas nas condições, débito por linha e multiplicadores dos dados.

Esta versão foi montada por workflow de GitHub Actions e validada por Node.js. A publicação no GitHub Pages é independente da validação de código.
