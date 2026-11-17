# Strikers 1945 — V7.1 | Projeto de reconstrução nativa sem emulador (17/11/2026)

**Abrir:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=7.1

O endereço principal voltou a um motor Canvas/JavaScript próprio, sem EmulatorJS, FBNeo ou CPU virtual. Agora a importação da ROM completa (10 chips) é obrigatória e o parser valida CRC32 dos bytes, reconstrói o mapa de 1 MiB do programa principal 68EC020, mantém o programa de som e o banco PCM OPL4, e descodifica tiles, sprites e LUT.

**Estado da reconstrução:** a primeira fase tem capturas parciais de registos gráficos reais; a IA original, as tabelas de trajetória das restantes fases, a lógica dos chefes, os disparos precisos e a reprodução integral do som original ainda não foram reconstruídos. A componente MCU 4-u59.bin consta como NO_DUMP no MAME, não está no ZIP. A versão jogável serve como estrutura de desenvolvimento; não é uma cópia fiel completa do arcade. O carregamento dos dez chips não executa o seu código.

**Privacidade e direitos:** a ROM é carregada localmente no Safari e não é publicada no GitHub. O projeto guarda apenas código e registos de investigação autorizados.

**Modos anteriores:** a versão com emulador está arquivada em arcade.html; o protótipo HTML5 antigo está em remake.html.

---

# Strikers 1945 — V7.0: modo original como entrada principal (17/11/2026)

**Abrir:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=7.0

O endereço principal passa a carregar o arcade original através de EmulatorJS (FBNeo) com s1945.zip escolhido pelo utilizador e mantido apenas no IndexedDB do Safari. Não publicar a ROM no repositório. Os inimigos, as trajetórias, as fases, as colisões e os sons passam a ser do próprio jogo emulado, não são gerados pelo remake JavaScript.

- Apresentação adaptada ao iPhone; os controlos táteis são fornecidos pelo emulador.
- A versão HTML5 reconstruída anterior permanece em remake.html.
- O acesso legado arcade.html permanece disponível.
- O Service Worker responde à ROM fornecida localmente por roms/s1945.zip; o cache não inclui a ROM.
- Ainda não foi validada uma sessão completa no Safari do iPhone. O emulador exige ligação à Internet no primeiro arranque e ROM compatível com FBNeo.

---

# Strikers 1945 HTML5 — V6.1: auditoria de trajetórias

**Data da revisão:** 17/11/2026. **Abrir:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=6.1

## Resultados diretamente medidos nos dados de MAME capturados

| Fonte | Contagem | Frames emulados | Duração entre extremos |
| --- | ---: | ---: | ---: |
| Registos de sprites/aeronaves | 116 capturas | 480–4160 | 61,33 s |
| Tilemaps e paletas de fundo | 122 capturas | 496–2432 | 32,27 s |

Na auditoria dos registos de aviões, foram encontrados **893 sprites enumerados nas capturas**, **674 pares consecutivos com o mesmo identificador de memória** e **263 pares não compatíveis com o mesmo objeto**. Mudanças de coordenadas de aproximadamente 896–992 píxeis surgem na reutilização de registos; não devem ser interpoladas como movimento físico. O ficheiro `tests/trajectory-audit.mjs` permite repetir a análise.

## Correções implementadas

1. **Cronologia real:** reproduzir os registos dos aviões na escala dos 60 frames/s capturados, e não acelerar uma sequência de 61,33 s para 47 s.
2. **Identidade de cada aeronave:** distinguir saída, nova entrada e reutilização do mesmo slot; um novo inimigo já não herda indevidamente a trajetória e a vida do anterior.
3. **Interpolação:** limitar curvas aos extremos efetivamente capturados, reduzindo desvios artificiais, e recusar ligações de sprites de paletas incompatíveis.
4. **Visibilidade:** retirar do desenho inimigos com centro e dimensões totalmente fora do ecrã; colisão e dano seguem a aeronave redimensionada.
5. **Ataques sintéticos:** nas fases não capturadas, usar curvas de entrada e saída sem prender as aeronaves às margens; escalonar temporizadores de tiro para evitar disparos simultâneos artificiais.
6. **Fundo da fase 1:** sincronizar o desfasamento inicial de 16 frames entre as duas fontes. A partir do último tilemap original disponível, prolongar o movimento do cenário por **reutilização da última captura**: é uma extrapolação visual explícita, não uma afirmação de reconstrução integral.
7. **Verificações:** Playwright cobre controlos no iPhone, colisões, continuidade dos voos, arma e importação privada da ROM. Os testes de integração gráfica usam um ZIP de teste sintético, pelo que não substituem a inspeção no Safari com a ROM original fornecida pelo jogador.

**Limite:** este projeto é um remake HTML5 com dados parciais da primeira fase, não um emulador do código de IA/voo e dos mapas completos de Strikers 1945. Para fidelidade total falta capturar os tilemaps posteriores e a lógica original dos inimigos.

---

# Strikers 1945 HTML5 — V6.0 (16/11/2026)

Revisão de jogabilidade e apresentação para iPhone, sobre a base V5.8.
Os pixels das aeronaves continuam a vir da ROM local fornecida pelo jogador.

## Principais melhorias

- Escala visual normalizada de bombardeiros capturados, sem distorcer a imagem original.
- Hitboxes dos aviões da ROM ajustadas à dimensão renderizada (fuselagem, não asas).
- Comando táctil relativo ao ponto onde o dedo começou: deixa de haver saltos ao tocar longe do avião.
- Seguimento de dedo interpolado e inclinação suave da aeronave.
- Paleta de fundo mais legível, cor atmosférica pré-renderizada e partículas de profundidade discretas.
- Trilhos suaves dos tiros inimigos e aviso visual quando um projétil está próximo.
- Combos por eliminações em sequência e avisos de vidas perdidas.
- Interface de vidas e bombas mais clara, com botão de bomba menos intrusivo.
- Atualização do HUD apenas quando os valores mudam, e redução adaptativa da resolução em equipamentos lentos.
- Service worker V6.0 para não misturar recursos antigos e novos.
- Testes Playwright para colisões, toque relativo, armas, importação da ROM e pausas (workflow GitHub Actions).

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/Strikers-1945/?v=6.0

**Limitações:** remake independente HTML5, não é a emulação integral de Strikers 1945. A fase 1 usa dados de fundo e voo capturados, quando a ROM está carregada localmente; as restantes fases continuam com cenários próprios. A verificação final no Safari de um iPhone real continua necessária.

---

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
