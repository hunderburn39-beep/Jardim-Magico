# Jardim Mágico 3D V2 — PlayCanvas

**Jogo 3D:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=2

Esta versão usa **geometria tridimensional real**, materiais, iluminação e texturas individuais para os símbolos, seguindo a referência gráfica fornecida pelo utilizador: jardim de flores, cascata, madeira dourada e veludo roxo. Não utiliza a fotografia inteira como fundo.

## Ficheiros

- `index.html`: interface de jogo e painéis, modais para informações e Hold & Win.
- `css/3d.css`: base de ecrã vertical/horizontal e canvas.
- `css/v2.css`: acabamento gráfico inspirado na referência, jackpots, HUD, logótipo e botões.
- `js/main.js`: cena PlayCanvas, flores, árvores, cascata, lótus e lanternas em 3D; 5×3 peças animadas e texturas HD.
- `js/demo-engine.js`: motor **autónomo de créditos virtuais** com regras demonstrativas e limites.
- `js/gameplay.js`: ligação entre a interface, a animação 3D e os resultados demonstrativos.

## Interações existentes nesta demonstração

- RODAR, TURBO, câmara 3D móvel, brilho dos símbolos premiados.
- Saldo e apostas fictícios ajustáveis entre 0,05 e 10,00 créditos; recarregamento demo +25.
- Dez linhas de prémios ilustrativos; WILD; pedras sem valor; SCATTER.
- 15 Free Spins, +8 com 2 Scatter ou +15 com 3 Scatter dentro do bónus (limite total de 60 giros por sequência para segurança).
- Hold & Win 4×4 com moedas e três tentativas.
- Compra voluntária de bónus **com créditos fictícios** (Free Spins 20×, Hold & Win 30×).
- AUTO 10/25/50 e seleção por pressão longa (3 segundos) no RODAR.
- Jackpots MIN 250×, MED 500×, MAX 1000× e ULTRA 5000× mostrados como limites ilustrativos em função da aposta e do cofre virtual.
- Fundo virtual que limita os pagamentos da demonstração, sem permitir valor negativo.

## Aviso importante

**Esta é uma pré-visualização gráfica e jogável.** Não opera depósitos, levantamentos nem pagamentos em dinheiro, nem representa um RTP verificado. As regras ilustrativas não substituem a matemática, a gestão da banca, o RTP ou o sistema completo do jogo Jardim Mágico original.

Antes de migrar para o motor 3D definitivo, faltam testar rigorosamente probabilidades, RTP, jackpots efetivos, validação de prémios, armazenamento, relatórios e integração administrativa. O [jogo original](../index.html) mantém-se intacto.

Backup do protótipo PlayCanvas V1: `backup/jardim-3d-v1-antes-v2`.

## V2.2 — Gráficos Premium 3D

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=2.2

Alterações exclusivas à apresentação (sem tocar em `js/demo-engine.js`):
- `js/main.js`: nova luz direcional de recorte, água com sete reflexos móveis, seis fios de espuma animada e cinco nuvens de névoa volumétrica; talha de madeira, frisos dourados, pequenos cristais facetados e ramos 3D.
- `js/visual-v22.js`: camada 3D adicional em cada uma das 15 peças, com bisel, esmalte, frisos e halo seletivo para WILD, cristais, SCATTER e moedas. Texturas HD existentes continuam no rosto das peças.
- `js/main.js`: partículas do estouro conjunto diferenciadas por símbolo, até cinco estilhaços por peça, halo radial para cada peça, pequeno impacto da câmara; todos os símbolos vencedores explodem ao mesmo tempo, sem repetir os partilhados por linhas.
- `css/v2-2.css`: materiais e reflexos coerentes em jackpots, multiplicadores, saldo, aposta, cofre, RODAR, AUTO e TURBO.
- `index.html`: ativa nova folha e atualização de cache para V2.2.
- **Desempenho**: sombras em tempo real desligadas, texturas HD de 256px e partículas mantidas num pool reutilizável. A validação visual e os FPS do Safari físico ainda exigem teste no dispositivo.
- Backup prévio: `backup/jardim-3d-v2-1-antes-v2-2-graficos`.

A V2.2 continua a ser uma **demonstração de créditos fictícios**, sem dinheiro real, nem RTP auditado ou motor financeiro de produção.

## V2.3 — moldura fiel ao jardim e tambores de cima para baixo

**Jogo:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=2.3

- `js/frame-v23.js`: remove a moldura rígida do render ativo. Ramos curvos, madeira torcida, trepadeiras enroladas, flores, pequenas lanternas e fio dourado fino; centro das cinco colunas sem obstruções. Material e geometria 3D verdadeiros, não uma imagem inteira sobreposta.
- `js/reels-v23.js`: cinco tiras verticais reais com quatro peças recicláveis por coluna (três linhas visíveis e uma peça acima). **Os símbolos passam de cima para baixo**, entram pelo topo, saem por baixo e as colunas param progressivamente com desaceleração final.
- `js/demo-engine.js`: cascatas pré-calculadas após as linhas premiadas; remove as peças premiadas, mantém a ordem vertical das restantes, cria novas figuras por cima e continua a gerar quedas até surgir uma grelha sem combinações premiadas, sem limite artificial por giro. Soma os valores de todas as combinações e de todas as quedas antes da animação, respeitando a reserva disponível.
- `js/gameplay.js`: sincroniza explosões conjuntas, quedas com gravidade e mostra o total do giro. Giros automáticos e Free Spins só avançam depois da última queda.
- `js/main.js`: integra os módulos 3D, retira do render ativo a moldura de barras da V2.2 e expõe a API de cascatas.
- `index.html`: versão e cache V2.3.

### Testes

- 10 000 giros simulados, sem reserva/saldo negativo nem Free Spins acima do limite.
- Teste com motor visual simulado: 15 posições corretas no fim da rotação vertical e no fim da queda.
- A moldura é procedural: a correspondência exata com a fotografia e o desempenho no Safari devem ser avaliados no iPhone.

**Atenção:** o jogo 3D mantém-se em modo de demonstração com créditos fictícios e matemática experimental. Não é uma slot auditada nem opera dinheiro real.

Backup da V2.2: `backup/jardim-3d-v2-2-antes-moldura-e-rolos-v2-3`.

## V2.4 — Sequências premiadas sem limite artificial

Acesso: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=2.4

- Cada queda é avaliada e, havendo novas linhas premiadas, as peças vencedoras explodem e outras descem de cima.
- **Só termina quando a última grelha não tiver nenhuma combinação premiada**, sem máximo fixo de quatro ou outro número de quedas.
- O cofre virtual condiciona **as combinações geradas antes de jogar**, nunca reduz o valor de uma combinação que tenha sido mostrada. Continua sem limitar a quantidade de quedas.
- Todos os resultados e valores são calculados antes de iniciar as animações, para não variar por FPS, TURBO ou interrupção.
- Para sequências muito compridas, o painel resume os valores individuais mas mantém a soma integral e o total real.
- As restrições das Free Spins e a matemática são específicas da simulação; o RTP não está auditado.

Teste especial: foram produzidas 13 quedas premiadas consecutivas e uma 14.ª sem prémio; a sequência terminou naturalmente, mesmo com o cofre virtual esgotado. Teste aleatório de 15 000 giros sem erros de soma.
Backup: `backup/jardim-3d-v2-3-antes-cascatas-sem-limite`.

## V2.5 — Gerador de prémios financiáveis, sem cortes

Abrir: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=2.5

- **Não se corta nenhum prémio depois de sair**: quando aparece uma combinação de valores fictícios, o valor total é pago, incluindo Wilds e todas as cascatas.
- Antes de animar os tambores, o motor calcula a **sequência inteira**, com valor de cada linha e queda, e compara a soma com a reserva virtual.
- Se o cofre não suportar uma sequência, o motor tenta outras peças e outras cascatas, mantendo coerentes as peças sobreviventes. Se as sobreviventes tornarem inevitável um prémio excessivo, escolhe um novo giro **antes de apresentar qualquer resultado**.
- Continua a haver tantas quedas quantas as combinações encontradas: **não existe limite artificial de quedas**. A última grelha é sempre uma grelha sem linhas premiadas.
- Free Spins: a seleção antecipa o valor acumulado e o multiplicador final; só mostra resultados cujo prémio integral caberá na reserva virtual.
- Hold & Win: as seis moedas iniciais e as seguintes são escolhidas apenas entre valores financiáveis. A última moeda só aparece se o cofre também suportar o prémio adicional da grelha 4×4. Termina por esgotar as três tentativas ou por completar a grelha.
- Se nem as seis moedas iniciais forem financiáveis, o Hold não pode começar. Não se atribuem moedas fictícias que excedam a reserva.
- Quando há falta de verba, a máquina pode gerar uma **jogada sem prémios** em vez de mostrar um prémio e pagá-lo parcialmente.
- O motor continua a ser apenas uma **demonstração de créditos fictícios**: a seleção condicionada pelo fundo não é uma matemática de apostas reais auditada.

Cópia de segurança anterior: `backup/jardim-3d-v2-4-antes-gerador-solvente`.

## V3 — Correção do bloqueio, gráficos mais próximos da referência e base de dados

**Abrir:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=3

### Bug da queda resolvido no controlador

- `js/gameplay.js` reproduz as etapas com `async/await` e um temporizador de segurança por giro e por queda. Não depende de um callback que possa nunca chegar.
- `js/main.js` captura erros de movimento e expõe `JM3D.recover(board)`.
- `js/reels-v23.js` implementa `forceReset(board)`: cancela movimento incompleto, repõe as 15 peças no resultado calculado e desbloqueia os botões. Inclui recuperação de cena após interrupções.
- O resultado e o prémio são calculados **antes** das animações; recuperar um erro gráfico não altera a soma nem o fundo. Uma falha de cálculo repõe o estado anterior.
- A última grelha é sempre sem novas combinações; quedas continuam enquanto aparecem prémios. O cofre condiciona os resultados antes do giro, sem truncar o prémio mostrado.
- Testes em `tests/smoke-v3.mjs`, executados pelo CI antes de publicar o GitHub Pages.

### Gráficos

- `css/v3.css`: cenário único `jm76-jardim-profundo.svg` de bosque, lago e cascata, sem recortes colados; a moldura `jm75-moldura.webp` da referência é mascarada para deixar o centro **transparente**, sem a área preta.
- PlayCanvas com canvas transparente e câmara aproximada para tornar as cinco colunas maiores.
- `js/reels-v23.js`: símbolos HD suspensos sobre um único painel de veludo violeta com separadores verticais finos, sem caixas metálicas em cada posição.
- `js/main.js`: símbolos antigos de 256px passaram a texturas de 512px, com transparência; permanece a rotação vertical 3D e o efeito de estouro conjunto.
- `js/audio-v3.js`: sons Web Audio originais para rotação, cascata, prémios, bónus e música ambiente ativada pelo utilizador.

### Base de dados e gestão da demo

- `js/database-v3.js`: **IndexedDB local do navegador**, com estado da máquina, fundo, saldo, Free Spins, Hold, último tabuleiro, histórico e registos de operações.
- O giro é guardado antes da animação para não se perder a atribuição do prémio ao atualizar a página.
- Botão ⚙: histórico de giros, apostas/compras e prémios fictícios, retorno observado **não auditado**, exportação JSON, reset e ajustes fictícios do fundo.
- Se IndexedDB estiver bloqueada, tenta `localStorage`; a UI avisa se a persistência falhar.
- Esta base **não é online**: não existe conta de jogador, autenticação, API, sincronização entre aparelhos, nem gestão monetária real. É necessário um servidor autenticado e uma base central para produção.

**Limites da versão V3:** a arte está mais próxima da fotografia graças à moldura e ao cenário originais, mas não se pode afirmar que é idêntica à imagem conceptual. O jogo é uma versão demonstrativa de créditos fictícios, ainda não um serviço de jogos a dinheiro nem uma matemática/RTP auditada.

Backup: `backup/jardim-3d-v2-5-1-antes-correcao-v3`.

### V3.1 — acabamento dos símbolos e correção de cache no Safari

- A versão oficial para testar passou a ser **https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=3.1**
- `js/symbol-art-v3.js`: SCATTER floral, pedra sem valor e moeda 2× com materiais, reflexos e transparência próprios em vez de esferas amarelas de substituição.
- `js/audio-v3.js`: controlos de música e efeitos sonoros com som original e volume suave.
- `js/gameplay.js`: relatórios da base local com totais de apostas/compras e prémios; último prémio atualizado ao iniciar a animação; bloquear retirada do fundo durante Free Spins, Hold ou giro em curso; reativar as Free Spins quando o Safari regressa de segundo plano.
- Cache dos módulos e do CSS atualizado para `?v=3.1` (evita reabrir a versão antiga no iPhone).
- O cenário principal e a moldura são artísticos 2.5D para dar fidelidade à referência; as peças, cascatas e iluminação adicional continuam com geometria 3D.
- **Não existe base de dados online sincronizada** nem validação de FPS num iPhone físico. Estes pontos permanecem fora do protótipo GitHub Pages.

## V3.2 — símbolos direitos e cores fiéis à fotografia

Link de teste: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/?v=3.2

- **Correção principal**: os símbolos deixaram de ser texturas repetidas nas seis faces de um cubo. Em `symbol-face-v32.js`, uma malha frontal quad utiliza UV [0,0]→[1,1], orientação constante para a câmara e material emissivo branco, de modo a evitar peças invertidas e sem cor.
- **Símbolos originais**: `main.js` carrega os nove PNG HD existentes, retira grande parte do fundo violeta de cada recorte e reenquadra a figura. Em vez de sombrear a arte com luzes de cena, conserva os tons rosa, verde, dourado e violeta. São texturas transparentes de 512px.
- **Carregamento**: o botão RODAR aguarda os PNG originais; não se mostram círculos vazios como resultados definitivos enquanto as imagens HD carregam.
- **Moldura**: removidos os troncos grossos do modelo procedural. O cenário usa a moldura floral `jm75-moldura.webp` extraída da arte original, com centro transparente, bem mais próxima da referência do utilizador.
- **Bosque**: retiradas árvores esféricas e barras turquesa de cascata que eram demasiado geométricas; mantém-se a imagem contínua de paisagem e efeitos ligeiros.
- **Render**: tone mapping `TONEMAP_NEUTRAL` para reduzir perda de saturação.
- **Validação de código**: testes automáticos do quad UV frontal, luz/material e 10 500 giros fictícios continuam a ser executados antes de publicar.
- Os gráficos continuam parcialmente 2.5D com rolos PlayCanvas 3D. É necessária avaliação visual no Safari do iPhone para confirmar a proximidade com a imagem conceptual.

Backup anterior: `backup/jardim-3d-v3-1-antes-simbolos-orientados-v3-2`.
