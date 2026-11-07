# Jardim Mágico — Phaser V1

**Jogar em HTML5:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1.6

Novo motor **Phaser 3.90.0**, com interface artística 2.5D. O jogo original em `/index.html` e o antigo protótipo PlayCanvas em `/jardim-3d/` permanecem intactos.

## Estrutura

- `index.html`: nova máquina em HTML5, controls, cofres e painel de bónus.
- `css/estilo.css`: layout responsivo iPhone/Android, cenário contínuo, moldura original e interface ouro/veludo.
- `js/reels.js`: Phaser, símbolos HD, cinco rolos verticais, cascatas, partículas e recuperação.
- `js/engine.js`: regras e saldos virtuais independentes das animações.
- `js/app.js`: coordenação de giros/bónus, painéis e temporizadores de recuperação.
- `js/database.js`: IndexedDB local com fallback localStorage, histórico e exportação JSON.
- `js/audio.js`: efeitos originais de áudio, controlos liga/desliga.
- `tests/smoke.mjs`: regressões de prémios e animação antes da publicação.

## Visual e animação

Reutiliza os **símbolos HD originais** da imagem de referência, o logótipo ornamentado, a moldura fotográfica de madeira, flores e trepadeiras e uma imagem de cenário única. A moldura tem **interior transparente**, em vez de capturas coladas sobre as peças. Os símbolos são processados com corte e remoção suave do fundo escuro, sem inversão UV ou rotação em 3D.

Os **cinco tambores descem de cima para baixo**, fazem paragem por coluna e ao vencer animam em conjunto todas as peças da combinação. Nas cascatas, as sobreviventes mantêm a ordem e as novas descem do topo. Múltiplas combinações são somadas; peças partilhadas só explodem uma vez. Sem limite fixo de quedas: termina quando não existem novas linhas premiadas.

## Funções de demonstração

- Apostas de 0,05 até 10 créditos fictícios e botão de recarga demo (+25).
- Dez linhas de prémios, WILD, SCATTER, pedra sem valor e moeda.
- Free Spins com retriggers e multiplicadores.
- Hold & Win 4×4 (3 tentativas), compra voluntária de bónus com créditos fictícios.
- TURBO, AUTO 10/25/50 e pressão longa de 3 s no RODAR.
- Jackpots de referência MIN 250×, MED 500×, MAX 1000×, ULTRA 5000× exibidos como valores ilustrativos.
- Geração de resultados integralmente pagáveis pelo fundo fictício **antes** de os apresentar, sem cortar valores depois de saírem.
- Guardar sessão e histórico no dispositivo, exportar JSON e reiniciar a demo.
- Watchdog para recuperar animação travada sem cobrar novamente o giro.

## Limitações

**Não é uma máquina a dinheiro real**, e o RTP, jackpots progressivos reais e regras não estão auditados. A base IndexedDB é **local ao navegador**; não existe base de dados central, autenticação, permissões de operador ou sincronização de contas. Para produção são necessários backend seguro, mecanismo de resultados certificado, auditoria e cumprimento das normas aplicáveis.

A experiência visual e os FPS devem ser avaliados no Safari físico. Os testes de código passam em CI, mas não substituem testes num iPhone real.

Cópia de segurança do jogo anterior: `backup/jardim-antes-phaser-v1`.

## Fonte do motor

Phaser 3.90.0 — <https://phaser.io/download/release/v3.90.0>

## V1.1 — Tambor contínuo sem troca de símbolo na paragem

Corrigido o defeito em que o tambor terminava a rotação e só depois substituía as imagens pelas figuras do prémio. Cada uma das três imagens finais passa a ser escolhida quando **entra pelo topo no último ciclo da rotação**. A partir daí, desce naturalmente até à posição vencedora, sem alteração de textura no evento `onComplete`. A imagem e o prémio continuam definidos antecipadamente pelo motor de créditos fictícios.

O teste `tests/smoke.mjs` simula a rolagem por frames e verifica para as **cinco colunas** que as imagens finais já são as corretas **antes da paragem**, e que a paragem não muda nenhuma figura. Mantém os testes de cascatas, cofre, Free Spins, Hold e armazenamento.

## V1.2 — Prémios e efeitos, sem alterar a queda aprovada

A animação de rotação vertical e a entrada das imagens finais pelo topo permanecem **iguais à V1.1**.

- As figuras que ganham iluminam-se **simultaneamente** com halos dourados, anéis e pequenos fragmentos coloridos, usando tweens nativos de Phaser.
- A mesma peça em duas linhas premiadas tem **um único efeito**, preservando o valor das duas combinações.
- Só as peças que vão ser substituídas pela cascata se dissipam; na queda final sem nova combinação as peças apenas brilham e continuam visíveis, sem desaparecer e regressar abruptamente.
- O valor **de cada queda** surge sozinho, em grande e dourado, sem caixa, e desaparece suavemente.
- O número de partículas é limitado de acordo com o número de peças vencedoras para proteger a fluidez no iPhone.
- A legenda respeita o multiplicador WILD quando aplicável, e as Free Spins apresentam a soma base por queda, creditando e anunciando o prémio acumulado apenas no fim.
- O teste automático verifica duplicados de peças premiadas, brilho sem desaparecimento, a apresentação do valor da queda e que os cinco tambores continuam a parar com as peças já visíveis **sem troca na paragem**.

Cópia de segurança anterior: `backup/jardim-phaser-v1-1-antes-premios-v1-2`.

## V1.3 — Acabamento visual fiel à arte original

- **Símbolos fotográficos:** os nove símbolos normais são recortados da folha original `assets/imagens/jm75-simbolos.webp`, na ordem correta de três colunas e três linhas. Preservam diretamente as cores, a iluminação e os reflexos do ficheiro original, sem a dessaturação do recorte anterior.
- **Bordas dos símbolos:** as células são convertidas uma única vez em texturas Phaser de 512×512, com um desvanecimento radial apenas na orla; não aparecem quadrados escuros à volta dos objetos. SCATTER, pedra e moeda mantêm as suas texturas próprias.
- **Moldura:** a moldura de madeira e flores continua a utilizar `jm75-moldura.webp`, com o centro recortado para mostrar os tambores. A máscara foi ajustada à espessura das quatro bordas, de modo a reduzir as zonas pretas da captura.
- **Painéis originais:** jackpots, barra de multiplicadores, saldo, prémio, compra de bónus, TURBO e botão central RODAR passam a reutilizar as imagens gráficas da referência (em vez de apenas gradientes genéricos).
- **Proteção da animação:** as funções de rotação e cascata são rigorosamente iguais às da V1.2, que conservava a queda aprovada pelo utilizador. O prémio e a banca também não mudaram.
- **Testes:** o CI confirma a sequência 3×3 do atlas, recortes sem troca de posições, fallback quando faltar textura, estética dos painéis e os anteriores testes da animação e das regras.

É ainda uma demonstração HTML5 com créditos fictícios e IndexedDB local. Não substitui uma validação visual no Safari físico nem auditoria de RTP.

## V1.4 — Bosque mais imersivo e interface mais luxuosa

- Fundo original reforçado com iluminação suave para preservar o ambiente mágico.
- Flores, cristais e lago da fotografia `jm75-lago.webp` reaproveitados apenas nos extremos inferiores, evitando sobrepor as imagens do botão RODAR/TURBO que já estão nessa fotografia.
- Oito pequenos pontos de luz animados lentamente por CSS, com `pointer-events:none` e desativação em modo `prefers-reduced-motion`.
- Novo `assets/porquinho-dourado.svg` com gradientes originais, brilho e sombras no Cofre Mágico, substituindo o emoji do protótipo.
- Melhor relevo, reflexos, brilho e legibilidade nos jackpots, multiplicadores, moldura, saldo, aposta e botões, sem introduzir novas caixas por cima das peças.
- CSS em ficheiro independente `css/polimento-v14.css`, para facilitar alterações ou reverter rapidamente sem afetar a versão aprovada.
- **Motor e animações de giro e cascata não foram modificados**: a queda e o efeito dos prémios permanecem exatamente como na V1.3. Não houve alteração ao motor de pagamentos, ao cofre virtual ou à base IndexedDB.
- Testes automáticos confirmam os novos elementos e a ausência de bloqueio de toques; falta avaliação visual num iPhone físico.

Cópia de segurança anterior: `backup/jardim-phaser-v1-3-antes-polimento-v1-4`.

## V1.5 — Free Spins e Hold & Win com apresentação premium

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1.6

- **Free Spins:** barra discreta por cima dos rolos com giros restantes, valor acumulado e multiplicador. Animação de abertura/reativação e anúncio distinto do prémio integral no final da série.
- **Hold & Win:** novo painel dourado e violeta com grelha de 16 casas, moedas douradas com animação de queda, contador de moedas, indicadores visíveis para as três tentativas e valor total do bónus.
- As moedas que já estavam na grelha **não reaparecem nem animam de novo** a cada tentativa: só são animadas as moedas novas indicadas pelo motor.
- Os créditos fictícios e o estado final são **gravados antes da animação de prémio**, reduzindo o risco de duplicação após atualização da página.
- O botão de jogar moedas fica bloqueado durante cada animação para evitar múltiplas tentativas acidentais.
- No final do Hold é exibido o prémio integral do motor, incluindo o extra fictício da grelha completa, quando ocorrer.
- Ficheiros novos `css/bonus-v15.css` e `js/bonus-presentation.js`, com testes próprios de moedas novas, contador de tentativas e valores. Acessibilidade: efeitos reduzidos quando o dispositivo pede menos movimento.
- **Os arquivos `js/reels.js`, `js/engine.js`, `js/database.js` e `js/audio.js` permanecem intactos**, preservando integralmente a queda aprovada e a matemática da versão anterior.

A máquina continua uma demonstração de **créditos fictícios**, com histórico IndexedDB apenas local e RTP não auditado. A publicação e os testes automáticos não substituem teste visual num iPhone físico.

Cópia de segurança anterior: `backup/jardim-phaser-v1-4-antes-bonus-v1-5`.

## V1.6 — Prémios reposicionados e multiplicadores na mesma barra

- **Celebração mais acima:** o texto de vitória aparece em três linhas — `NICE`, `MEGA` ou `SENSACIONAL` (ou o nome do bónus), a palavra **TOTAL** e, por baixo, **o valor integral do prémio**.
- **Barra dos multiplicadores:** o lado esquerdo apresenta o **valor desta queda**; na conclusão passa a mostrar **TOTAL DO GIRO**. Os orbs dos multiplicadores ficam imediatamente ao lado e à direita aparece o fator efetivo **×1 a ×5**.
- Nas Free Spins os círculos obtidos vão-se acumulando visualmente (até três visíveis), mas o fator à direita reflete sempre o **multiplicador real do motor**. Um giro com várias quedas premiadas que contenham Wild incrementa o acumulado apenas uma vez, conforme a regra de Free Spins.
- O valor da queda deixou de aparecer como um número gigante no centro das peças. Agora fica na barra dourada e mantém-se até à queda seguinte ou ao fecho do giro.
- As animações `NICE/MEGA/SENSACIONAL` sobem um pouco e são apresentadas num conjunto vertical com **frase → TOTAL → valor**. No fim das Free Spins não são apresentadas duas janelas de celebração em simultâneo.
- A apresentação está num novo ficheiro `css/premios-v16.css`, mantendo intactos os painéis e moldura artísticos da V1.4.
- **Não foram alterados** `js/reels.js`, `js/engine.js`, `js/database.js` ou `js/audio.js`. A queda de peças aprovada, a banca fictícia, os bónus e o histórico permanecem intactos.
- Os testes automáticos verificam a ordem visual dos componentes, o número de linhas da celebração, a legibilidade e o histórico dos multiplicadores.

Backup da V1.5: `backup/jardim-phaser-v1-5-antes-layout-premios-v1-6`.
