# Jardim Mágico — protótipo em 3D real (PlayCanvas)

Acesso após publicação: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-3d/

Este é um **protótipo visual e interativo isolado**. **Não substitui** o jogo estável em `/index.html`.
Usa PlayCanvas Engine 2.23.3, via import map em GitHub Pages, e WebGL.

## Estrutura

- `index.html`: canvas 3D, UI, comandos, modo demonstração
- `css/3d.css`: UI adaptada a iPhone 9:16 e paisagem 16:9
- `js/main.js`: cena 3D real e giros de demonstração

## O que já funciona

- geometria tridimensional para jardim, árvores, flores, lago e cascata
- luzes direcionais, ponto de luz e materiais com especular/metálico
- moldura 3D com centro aberto, cristais, ouro e madeira
- quinze peças 3D (cinco colunas e três linhas) com símbolos
- giros visuais com rotação 3D e paragem escalonada
- alternância TURBO e movimento suave de câmara
- ecrã vertical/horizontal responsivo
- resolução máxima adaptada ao iPhone (device pixel ratio máximo 1,5)

## Limitado de propósito nesta fase

- **Não usa nem replica a matemática da banca**, RTP, linhas premiadas, freespins, hold & win, jackpots ou pagamentos.
- Não permite depósitos, levantamentos ou operações monetárias.
- Os símbolos ainda são protótipos; devem ser substituídos por arte 3D aprovada e otimizada.
- O objetivo é validar se o visual e o desempenho no iPhone justificam migrar o motor do jogo.

## Próximos passos para migrar realmente o Jardim Mágico

1. Definir modelos/texturas 3D finais de cenário, moldura e símbolos.
2. Medir FPS e memória no iPhone, otimizar tamanho de texturas e draw calls.
3. Migrar com testes automáticos as regras do jogo existente e os eventos de UI.
4. Validar cenários extremos (Wild 1×–5×, freespins, banca insuficiente, cofres e jackpot).
5. Publicar nova versão de produção apenas após testes e aprovação visual.

O jogo original permanece disponível em:
https://hunderburn39-beep.github.io/Jardim-Magico/
