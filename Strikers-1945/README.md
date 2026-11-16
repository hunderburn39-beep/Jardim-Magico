# Strikers 1945 HTML5 — V5.8

## Melhorias após gravação no iPhone (16/11/2026)

- Quatro vidas iniciais e proteção mais longa entre impactos; aviso de perda de vida no ecrã.
- HUD mostra `♥ N` num indicador mais nítido.
- Área de contacto de aviões grandes centrada na fuselagem e mais pequena, reduzindo colisões que pareciam injustas.
- Disparos inimigos aguardam 6,5 segundos na primeira fase, dando tempo de adaptação.
- Aviões grandes ficam mais transparentes ao cruzar a zona do jogador, sem alterar os pixels nem as dimensões dos sprites extraídos da ROM.
- Tiros ciano ligeiramente mais espessos para se distinguirem melhor do fundo.
- Cache offline atualizado para V5.8, reduzindo o risco de o Safari apresentar a versão anterior.

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=5.8

**Estado:** mudanças publicadas no código; ainda requer validação visual no Safari real.

---

# Strikers 1945 HTML5 — V5.7

Jogo independente de aviões com controlos táteis e quatro armamentos. **Não é o jogo comercial original emulado**.

## Alterações na V5.7 — leitura visual

- Fundo da missão ligeiramente escurecido, mantendo os pixels originais da base aérea quando a ROM local está carregada.
- Nas sequências de voo capturadas da ROM, não são acrescentados alvos de solo artificiais por cima dos alvos já capturados.
- Os aviões e instalações de solo são desenhados em camadas separadas. Os aviões grandes não mudam de tamanho, mas tornam-se suavemente transparentes apenas ao cruzar a zona do jogador.
- Tiros do jogador: raios estreitos ciano; tiros inimigos: vermelho-alaranjado de centro claro e contorno escuro, facilmente identificáveis.
- A quantidade de tiros inimigos simultâneos fica limitada para melhorar a leitura no iPhone; o limite sobe progressivamente de fase para fase.
- Redução da intensidade de rastos de mísseis, explosões, vibração de impacto e efeitos luminosos.
- Pequeno marcador ciano no avião controlado pelo jogador e dicas de controlo ocultadas depois dos primeiros sete segundos.
- O distintivo de ROM deixa de cobrir a zona de jogo (a importação continua disponível).

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=5.7

**Nota:** é um remake HTML5 com gráficos da ROM fornecida localmente pelo utilizador; não é emulação integral do arcade.

---

# Strikers 1945 HTML5 — V5.6

Jogo independente de aviões com controlos táteis e quatro armamentos. **Não é o jogo comercial original emulado**.

## Alterações na V5.6

- Os alvos terrestres (tanques, navios, torres e sprites de solo da captura) já não destroem o avião por sobreposição visual: apenas os projéteis e os aviões em voo contam como perigo.
- Aviões grandes usam uma área de contacto elíptica, focada na fuselagem, em vez de um círculo demasiado largo.
- Aviões capturados da ROM só podem colidir ou disparar quando estão suficientemente visíveis, evitando tiros e impactos durante o aparecimento.
- Deteção por segmento para projéteis inimigos rápidos, reduzindo falhas de colisão entre fotogramas.
- Após um impacto, há 3 segundos de proteção e os projéteis inimigos imediatamente à volta do jogador são neutralizados.
- O desenho deixa de consumir recursos continuamente durante o menu de pausa, o fim de partida e quando a página não está visível.
- Testes de regressão adicionados para colisões de solo, aviões em voo, aparição de inimigos e projéteis rápidos.

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=5.6

**Nota:** a V5.6 melhora o remake HTML5; não reproduz integralmente a IA, matemática de dano, mapas e padrões de voo do arcade original.

---

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
