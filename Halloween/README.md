# Halloween — Noite das Bruxas (V0.1)

Jogo original para navegador, em **Phaser 3**, inspirado no ambiente dos caça-níqueis Halloween clássicos. **Não é o código original** de uma máquina comercial nem reproduz matemática ou sons proprietários.

## Como jogar
- Abrir `Halloween/` a partir de um servidor HTTP na raiz do repositório (por exemplo: `python3 -m http.server 8000` e navegar para `http://localhost:8000/Halloween/`).
- Formato vertical **9:16**; interface tátil, sem zoom ao duplo toque no canvas.
- 5 tambores × 3 linhas visíveis; **25 linhas de pagamento** fixas.
- Alterar aposta com **−** e **+** (1, 2, 5 ou 10 créditos).
- Girar no botão central; o Wild substitui símbolos normais, mas não o Scatter.
- **3 ou mais Scatter** atribuem 10 rodadas gratuitas.
- **10 abóboras recolhidas** (ao longo dos giros) dão 5 rodadas gratuitas; o excedente acumula.
- Linhas durante rodadas gratuitas valem 2× a premiação da linha.
- Saldo, abóboras e rodadas gratuitas são guardados localmente no dispositivo.

Os créditos são **apenas pontos de entretenimento**, sem depósito, levantamento ou valor monetário. O motor é experimental e não tem RTP certificado. Não deve ser usado para apostas reais.

## Organização
- `index.html`: página e escala 9:16.
- `js/engine.js`: motor, símbolos, prémios, RNG e bónus.
- `js/game.js`: cena Phaser, visual, interface e animações.
- `tests/engine.test.cjs`: testes automáticos determinísticos.

A dependência Phaser é utilizada localmente do caminho `../jardim-phaser/vendor/phaser-3.90.0.min.js`; não há dependência de serviços externos ou pagos.

## GitHub Actions
`.github/workflows/testar-halloween.yml` verifica apenas alterações em `Halloween/`, testa o motor e gera um pacote de execução com a dependência Phaser.
A publicação GitHub Pages do repositório é única: o workflow partilhado de publicação junta os quatro jogos para que publicar um não apague os restantes.

## Evolução
Esta é uma V0.1 jogável e separada. A iconografia, animações e matemática são originais e simplificadas; nenhum detalhe foi confirmado como réplica exata de uma máquina Halloween física.
