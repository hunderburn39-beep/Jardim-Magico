# Strikers 1945 — HTML5 (V1)

Recriação independente de um arcade de aviões, inspirada na jogabilidade de Strikers 1945. **Não é a ROM original:** os gráficos e sons são gerados em HTML5 Canvas e WebAudio, sem emulador.

## Jogar no iPhone

Abre `https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/` no Safari. Escolhe um dos quatro aviões e toca em **INICIAR MISSÃO**. Arrasta o dedo para mover; o tiro é automático. O botão **BOMBAS** elimina projéteis e causa danos. **Ⅱ** pausa o jogo. Para criar um atalho, usa Partilhar > Adicionar ao ecrã principal.

## Personalização

O jogo reside em `index.html`; as características dos aviões estão em `CRAFT`, a paleta dos cenários em `THEMES`, os desenhos em `drawPlane`, `drawEnemy`, `drawBoss` e `makeBg`, os sons em `beep` e `musicTick`, e as vagas de inimigos em `wave`, `enemyFire` e `summonBoss`.

Inclui seis fases, chefes, armas melhoráveis e suporte PWA com cache offline. Testado em Chromium com ecrã tátil simulado; a compatibilidade com o Safari físico continua por confirmar.
