# Auditoria de origem — Halloween 30 linhas (XAPK 6.68)

**Origem:** ficheiro `halloween-slots-30-linhas-6-68.xapk` fornecido pelo utilizador em 09/11/2026. Análise estática sem instalar nem executar a aplicação.

## Identificação

- O XAPK contém sete APKs de distribuição Android; pacote principal `com.r7games.slotsaga.apk`.
- O pacote principal contém `assets/halloween/` com **168 ficheiros** de duas famílias distintas:
  - **`01_*` (95 ficheiros)**: imagens e dados da **Halloween**, com cabeçalho próprio, sprites, tabelas, roleta, rolos e bónus.
  - **`16_*` (63 ficheiros)**: recursos de outro jogo, **Bucaneiros**. Não usar como se fossem da Halloween.
- Há também **9 ficheiros MP3** em `assets/sons/halloween/`. O `premio.mp3` tem 799 bytes e não foi reconhecido por FFprobe; não presumir que é áudio utilizável.
- A pasta `assets/halloween/` contém o binário `01_linhas.bin`, 47 370 bytes, cabeçalho **`LNH1`**. O campo `0x1E` no cabeçalho é compatível com 30 linhas, mas **a estrutura binária ainda não foi totalmente descodificada**. Não confundir este ficheiro com código-fonte original.

## Atlases e referências visuais confirmadas

| Ficheiro no APK | Dimensões | Conteúdo observado |
|---|---|---|
| `assets/halloween/01_slots.webp` | 575×1020 | Atlas de **5 colunas × 10 linhas** (células 115×102), com símbolos de giro; coluna 1 aparência normal e coluna 4 aparência destacada |
| `assets/halloween/01_ti.webp` | 575×612 | Imagens desfocadas dos símbolos durante a rotação |
| `assets/halloween/01_splash.webp` | 800×480 | Logótipo Halloween e carregamento |
| `assets/halloween/01_telab2.webp` | 800×480 | Moldura Halloween, campos bónus/crédito/prémio e indicação de multiplicadores |
| `assets/halloween/01_tela25b2.webp` | 800×480 | Interface da Roleta da Sorte |
| `assets/halloween/01_roleta.webp` | 345×345 | **Roleta circular com números de 1 a 30**, diferente da roleta de 8 setores inventada na V0.4 |
| `assets/halloween/01_mii.webp` | 750×960 | Animações de morangos, duplicação e fim de bónus |
| `assets/halloween/01_cal.webp` | 750×1400 | Atlas dos caldeirões/bónus |
| `assets/halloween/01_tabela1.webp` | 800×480 | Tabela de prémios legível para combinações especiais, Magic, caldeirões e outras peças |
| `assets/halloween/01_linhas.bin` | binário | Dados proprietários/empacotados de linhas; não é um script de JavaScript |

### Ordem dos 10 símbolos do atlas `01_slots.webp`, de cima para baixo

1. Bruxa verde com chapéu laranja
2. WITCH, bruxa de chapéu azul
3. Vampiro
4. Fantasma
5. Bruxa na lua
6. MONSTER (Frankenstein)
7. Abóbora Halloween
8. Diabinho com tridente
9. MAGIC (mágico)
10. Caldeirão

Cada linha possui cinco aparências/estados do mesmo símbolo; não são cinco símbolos distintos. Para o primeiro atlas, o recorte é `x = coluna × 115` e `y = linha × 102`. Coordenadas aqui são **metadados para referência**, não autorização de redistribuição.

## Sons em `assets/sons/halloween/`

- `acumulado.mp3` (aprox. 25 segundos)
- `bonus1.mp3` (aprox. 32 segundos)
- `bonus11.mp3` (aprox. 2,35 segundos)
- `bonus2.mp3` (aprox. 1,93 segundos)
- `fimdobonus.mp3` (aprox. 1,49 segundos)
- `premio.mp3` (799 bytes, descodificação não confirmada)
- `premio4.mp3` (aprox. 1,18 segundos)
- `premiobonus.mp3` (aprox. 1,44 segundos)
- `slots.mp3` (aprox. 2,8 segundos)

## Diferenças relevantes para o projeto Phaser

| Atual em `Halloween/` | Confirmado no XAPK |
|---|---|
| 8 símbolos originais em SVG | 10 símbolos de outro estilo em atlas de 5×10 |
| Roleta simulada com 8 setores | Roleta original com 30 casas numeradas |
| Recolha fictícia de abóboras para ativar free spins | Existe um atlas próprio para o **bónus de morangos**; apurar as regras antes de substituir |
| Interface 9:16 criada de raiz | Vários fundos de interface original 800×480, além de atlas de 575×1020 |
| Regras e tabela de pagamento estimadas | As tabelas visuais originais estão presentes em `01_tabela*.webp` e precisam de ser transcritas/validadas |
| Sons WebAudio originais | Nove ficheiros identificados no pacote, de fidelidade e duração diferentes |

## Pistas de código compilado (DEX)

A análise estática das strings de `classes.dex` encontrou nomes de métodos e estados do jogo como:

- `InitCaldeirao`, `EscolheCaldeirao`, `ExitCaldeirao`
- `InitMorango`, `EscolheMorango`, `SetMorango`, `MorangoSaida`
- `InitRoleta`, `ParaRoleta`, `SetRoletaPremio`, `calcbonusRoleta`
- Modos `Halloween 25L`, `Halloween 30L` e `Halloween Classica`

Isto demonstra que o APK inclui rotinas compiladas relacionadas com **caldeirões, morangos e roleta**, não apenas gráficos. Ainda não se identificou o código-fonte original nem se confirmou a matemática exata das rotinas. Extração de strings não é uma descompilação.

**Nota importante:** imagens e sons foram mantidos apenas no ficheiro extraído para análise privada, fora do GitHub público. Estes nomes servem para elaborar uma especificação e reconstrução original.

## Decisões técnicas

- **Não importar automaticamente os gráficos, música ou binários proprietários para este repositório público**, sem autorização/licença apropriada. O ficheiro fornecido permite análise privada, não comprova titularidade dos direitos.
- A próxima versão pode ajustar simbolologia, quantidades de linhas, animações, bónus e geometria da interface com **desenhos e áudio próprios**, guiados pela referência. Caso exista licença para reutilização dos recursos, podem ser integrados em camada opcional de assets.
- Não pressupor que o APK fornece scripts editáveis: o código Android está compilado em `classes*.dex` e poderá necessitar de análise estática adicional para interpretar regras não visíveis.
- **Não substituir pagamentos ou alterar RTP** até a tabela de prémios e as relações com aposta/linhas serem extraídas e verificadas por testes.
- Preservar os outros jogos e o workflow independente da Halloween.
