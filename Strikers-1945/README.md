# Strikers 1945 HTML5 — V4.2

Jogo independente de aviões com controlos táteis e quatro armamentos. **Não é o jogo comercial original emulado**.

## Alterações na V4.2

- Refiz os fundos HTML5 para abandonar os recortes de ilhas e usar litoral contínuo, pistas, estradas, vegetação, instalações e relevo.
- Corrigi a descodificação dos gráficos Psikyo: blocos 16×16, 4 bits por píxel, packed MSB, inversão dos bytes de cada palavra `ROM_LOAD16_WORD_SWAP`.
- Sprites: quatro ROMs na ordem exata **u20, u22, u21, u23**; tabela `u1.bin` de 131 072 referências de 16 bits little-endian.
- Criei o inspetor `inspector.html`, que mostra os tiles e sprites efetivamente descodificados e permite combinar pequenos grupos pela LUT.
- Retirei a montagem aleatória que simulava seis cenários "originais". **Os dados de pixels por si só não definem os mapas finais**.
- O ZIP selecionado é lido apenas no próprio navegador e os dados gráficos são guardados no IndexedDB do aparelho, se permitido.

## Links

- Jogo: https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=4.2
- Inspetor: https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/inspector.html

No iPhone, seleciona `s1945.zip` no botão de importação. Em seguida usa **Ver tiles / sprites descodificados**.

## Limite técnico indispensável

Os mapas de cada fase não estão prontos dentro de `u34.bin`: para reproduzir os cenários exatos do arcade é necessário recolher ou reconstruir **os índices das células do tilemap em VRAM, os bancos e atributos, as cores xRGB555 escritas em RAM, os registos de scroll e a ordem de composição das camadas durante o jogo**. Esses dados dependem do jogo a correr. A V4.2 NÃO afirma já reproduzir os cenários originais.

Não alojar sprites ou dados ROM sem direitos de distribuição. O código usa os ficheiros fornecidos pelo próprio jogador, localmente.

## Testes

`tests/rom-import.mjs` constrói um ZIP sintético sem gráficos comerciais, valida os endereços de bytes, nibble MSB/LSB, quatro bancos sprite, LUT e cache IndexedDB, mostra os resultados visuais e verifica que os fundos HTML5 não são apresentados como originais. Testes dos controlos e armas em `tests/smoke.mjs` e `tests/weapons.mjs`.
