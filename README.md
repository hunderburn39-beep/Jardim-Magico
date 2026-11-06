# Jardim Mágico — jogo web e Android

O código está organizado para permitir alterações diretamente no GitHub, sem
precisar de voltar a carregar ficheiros ZIP.

## Onde editar
- `index.html`: estrutura da página e elementos do jogo.
- `css/estilo.css` e `css/estilo-*.css`: visual, dimensões e animações.
- `js/jogo.js` e `js/jogo-*.js`: comportamento, regras e funcionalidades.
- `assets/imagens/`: imagens e grafismos.
- `assets/sons/`: áudio do jogo.
- `app/`: manifesto e código Android.
- `.github/workflows/`: geração da APK e publicação no navegador.
- `scripts/organizar_projeto.py`: ferramenta usada na separação inicial.

## Publicação automática
Quando há um novo commit no ramo `main`, os fluxos do GitHub Actions
publicam a versão web no GitHub Pages e compilam a APK de depuração.

A compilação Android copia `index.html`, `css/`, `js/` e `assets/`
para `app/src/main/assets/`. Ambos os formatos usam o mesmo jogo.

## Cópia de segurança
`JardimMagicoAndroid.zip` contém a versão antiga e foi preservado.
A partir de agora, **editar os ficheiros organizados**, não o ZIP.

## V73 — gráficos 2.5D acelerados por GPU

- `js/jogo-2.js`: um único ciclo de animação para PixiJS/WebGL e Canvas 2D; não são iniciados dois tickers em paralelo.
- As cascatas dos símbolos passam para sprites Pixi reutilizáveis, com apenas cinco máscaras (uma por rolo). Canvas 2D permanece como alternativa se o WebGL não estiver disponível.
- Texturas com relevo pré-calculado (sombra, profundidade e reflexo recortado), sem filtros 3D pesados a cada frame. Cache das moedas dinâmicas limitado para evitar crescimento indefinido de memória.
- `css/estilo-23.css`: vidro e iluminação 2.5D com fundos estáticos mais leves, incluindo ajustes para mobile e utilizadores com preferência por menos movimento.
- Alvo: animação até 60 FPS em aparelhos compatíveis; o resultado real depende do dispositivo e do browser. A medição de FPS e a inspeção visual em equipamentos físicos continuam necessárias.
- As regras, prémios, RTP, depósitos/levantamentos e cálculos da banca não foram alterados nesta versão.
- Versão anterior preservada no branch `backup/jardim-v72-antes-grafico-v73`.

## V74 — visual Jardim Encantado 3D (referência 9:16)

A versão V74 aplica ao jogo funcional a direção de arte da imagem de referência: jardim encantado, acabamentos 2.5D, roxos luminosos, molduras de madeira com folhagens, dourado, cofre dourado, botão verde em relevo e controlos em painéis com brilho. O tema é construído com os recursos já existentes (bosque SVG, moldura de troncos, logótipo, símbolos originais com texturas PixiJS) mais o novo `assets/imagens/porquinho-ouro-v74.svg` e a folha `css/estilo-24.css`. Não é uma captura de ecrã estática: todos os textos, botões, símbolos e valores permanecem dinâmicos e funcionais.

- `index.html`: liga o tema V74 e o cofre dourado mantendo os identificadores dos controlos.
- `css/estilo-24.css`: interface vertical 9:16 semelhante à arte de referência e variante horizontal 16:9.
- `js/jogo-2.js?v=73`: mantém o motor PixiJS GPU da versão anterior; pagamentos, RTP, fundos e reservas não foram alterados na migração gráfica.
- Cópia de segurança da V73: branch `backup/jardim-v73-antes-arte-v74`.
- A adaptação utiliza gráficos existentes e um novo elemento SVG; não inclui uma conversão literal da imagem ilustrativa completa em símbolos individuais. Validação visual nos dispositivos reais e testes de performance continuam recomendados.

## V75 — gráficos originais da fotografia, aplicados ao jogo funcional

A V75 utiliza **recortes da fotografia de referência fornecida pelo utilizador**, em vez de uma recriação apenas por CSS. São ficheiros WebP reais em `assets/imagens/`: `jm75-logo-recortado.webp`, `jm75-topo-cenario.webp`, `jm75-rodape-cenario.webp`, `jm75-moldura.webp`, `jm75-botao-anel.webp` e `jm75-simbolos.webp`.

O atlas fotográfico `jm75-simbolos.webp` tem 3 colunas × 3 linhas, com as seguintes figuras, por ordem: poção, cristal verde, coração, bule, WILD, cristal roxo, cogumelo, chapéu e rosa. `js/jogo-2.js` separa os nove símbolos em canvases no carregamento, atualiza a tabela de pagamentos e entrega as texturas a PixiJS/WebGL (ou Canvas 2D na alternativa). As animações e os valores continuam verdadeiramente dinâmicos; a fotografia **não** é apresentada como tabuleiro estático.

A folha `css/estilo-25.css` acrescenta as camadas decorativas sobre o layout funcional, mantendo os elementos interativos reais e os seus identificadores. A fotografia do botão tem centro transparente para deixar visíveis os textos dinâmicos «RODAR», «PARAR AUTO» e a contagem de rodadas.

Não foram modificadas as fórmulas de RTP, o cofre, os jackpots nem as probabilidades. Os gráficos importados somam aproximadamente 140 kB de WebP. Cópia de segurança antes da importação: `backup/jardim-v74-antes-foto-v75`.

## V76 — melhoria gráfica após teste real no iPhone

Ajustes visuais baseados numa captura de ecrã do utilizador:

- Cenário novo de floresta encantada e lago/cascata em `assets/imagens/jm76-jardim-profundo.svg`, criado para tapar as antigas faixas de cores sólidas que apareciam sob o botão RODAR.
- Mantidas as flores e a cascata fotográficas extraídas da referência, com transição suave para o novo fundo contínuo; os elementos decorativos não capturam toques.
- Novo botão de ação `assets/imagens/jm76-rodar-luxo.svg`, com aro dourado, folhas, flores e superfície verde nítida, mas texto e contador interativos continuam HTML.
- Removidos recortes WebP minúsculos dos painéis de jackpots, saldo, aposta e turbo; substituídos por gradientes CSS, rebordos luminosos e relevo adequados a qualquer tamanho.
- Os símbolos fotográficos mantêm-se, com nitidez melhorada uma só vez no carregamento, sombra mais limpa e dimensão ajustada para impedir cortes nas bordas dos 5 rolos.
- Botão de rodar durante as free spins mantém aparência nítida mesmo quando está desativado.
- `css/estilo-26.css` contém as alterações de visual; `js/jogo-2.js?v=76` apenas muda o carregamento e apresentação das texturas, não o motor de pagamentos.
- Cópia anterior: branch `backup/jardim-v75-antes-retoque-v76`.

Validação: sintaxe JavaScript e publicação GitHub Pages; o resultado visual final ainda requer abrir e ver no iPhone, pois não foi executado um teste visual remoto na versão publicada.

## V78 — interface aberta no iPhone
- `css/estilo-28.css`: camadas visuais livres em ecrã vertical, barra superior flutuante, logótipo separado, jackpot ligeiramente à direita, tambores quase à largura total, painéis inferiores desencontrados e RODAR/TURBO integrados no cenário.
- `assets/imagens/jm77-logo-isolado.svg`: recorte fotográfico do logótipo alterado de máscara SVG para `clipPath` para melhor compatibilidade com Safari no iPhone e eliminação do retângulo residual.
- `js/jogo-3.js`: cálculo inicial da largura móvel atualizado para usar a largura do dispositivo antes de aplicar a escala final para o `VisualViewport`; mantém o limite de altura e sem scroll.
- `index.html`: ativada V78 e a folha de estilo nova.
- Não foram alteradas regras, pagamentos, RTP, jackpots nem lógica da banca.
- Backup da V77.1: `backup/jardim-v77-1-antes-layout-v78`.
- A sintaxe foi validada; a disposição final deve ser confirmada visualmente num iPhone, por depender da altura livre no navegador.

## V79 — aproximação à arte fotográfica de referência

Revisão visual focada na diferença entre a primeira arte 9:16 e as capturas no Safari do iPhone. A V78.1 tinha um bosque vetorial bastante plano e flores grandes que não correspondiam à fotografia original.

- `assets/imagens/jm79-cenario-fotografico.svg` reúne três elementos fotográficos já presentes no projeto: bosque superior, ambiente desfocado e lago/cascata. As máscaras excluem elementos de interface que existiam nas imagens, evitando logótipos ou botões duplicados.
- `css/estilo-29.css` liga esse cenário como fundo único do ecrã, reforça o ouro/púrpura e o relevo dos jackpots, moldura, multiplicadores, botões e painéis. Os cinco rolos, símbolos e controlos continuam dinâmicos.
- `index.html` identifica a versão V79 e ativa a nova folha de estilo.
- `js/jogo-2.js` (PixiJS/WebGL), pagamentos, RTP, Jackpot e banca não foram alterados nesta atualização.
- Recuperação possível através de `backup/jardim-v78-1-antes-arte-fotografica-v79`.
- Os assets provêm de recortes da referência, mas esta ainda é uma composição interativa e não uma reprodução pixel a pixel. Convém testar o resultado num iPhone real.
