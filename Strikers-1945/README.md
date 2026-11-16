# Strikers 1945 — HTML5 V4.0

Jogo HTML5 original inspirado num clássico de tiro vertical. A lógica, o controlo dos quatro aviões, as armas e os efeitos continuam independentes da ROM e do emulador.

## Jogar no iPhone

Abrir: https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=4.0

1. Para utilizar o cenário gráfico extraído da tua ROM, no menu toca em **Carregar gráficos do arcade**.
2. Seleciona o teu **s1945.zip** na aplicação Ficheiros do iPhone.
3. O navegador lê apenas **u34.bin** localmente e constrói seis fundos experimentais com os blocos gráficos originais.
4. Escolhe o avião e inicia a missão. Arrasta o dedo para pilotar, o tiro é automático, usa Bombas e pausa apenas pelo botão Ⅱ.

Sem escolher a ROM, o jogo continua a usar os cenários HTML5 anteriores. É necessário escolher o ZIP novamente após um recarregamento da página.

**Importante:** os mosaicos de 16×16 píxeis são extraídos da ROM que o utilizador escolhe, mas as paletas RGB e a montagem dos mapas são aproximações. **Não são os cenários verdadeiros reconstruídos 1:1 do original.** A reconstrução exata exige recuperar dados de paleta e de disposição dos tiles durante a execução da versão arcade.

## Privacidade e distribuição

- Os ficheiros da ROM **não são carregados para servidores**: o arquivo ZIP é lido pela API File no próprio navegador.
- O repositório contém apenas **código do leitor e regras de montagem**; não contém os gráficos originais da Psikyo nem ficheiros ROM.
- A opção usa `DecompressionStream('deflate-raw')`, disponível em versões modernas do Safari.
- O utilizador deve usar uma cópia da ROM que tenha o direito de utilizar. Não partilhar os ficheiros originais no GitHub sem autorização.

## Organização técnica

- `index.html`: gameplay, interface, aviões, projéteis, áudio e integração opcional com os gráficos da ROM.
- `original-rom.js`: leitor local do ZIP e dos tiles Psikyo 4bpp, montagem experimental dos cenários, cache em memória.
- `sw.js`: cache offline do código. A ROM não é armazenada pelo serviço.
- `tests/rom-import.mjs`: teste de importação com um **ZIP sintético**, sem recursos do jogo original.
- `tests/smoke.mjs`, `tests/weapons.mjs`: verificação dos controlos, bombas, disparos e mísseis.

Jogo independente do emulador. Controlos e testes realizados em Chromium com ecrã móvel simulado. Validar no Safari físico antes de declarar compatibilidade total.
