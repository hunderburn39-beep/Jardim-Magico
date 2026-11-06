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
