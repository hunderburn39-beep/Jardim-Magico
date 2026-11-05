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
