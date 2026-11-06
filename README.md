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
