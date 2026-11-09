# Faraó Eterno — V1.0

Jogo original de demonstração, gratuito, exclusivamente com créditos fictícios. Layout vertical 9:16. HTML5 Canvas 2D e Web Audio, sem dependências externas ou compilação.

## Jogar

[Abrir no GitHub Pages](https://hunderburn39-beep.github.io/Jardim-Magico/Farao-Eterno/). `GIRAR` inicia; um segundo toque pára os tambores por ordem. Aposta nos botões − e +; som, música, turbo, linhas, regras, histórico, reposição gratuita e arriscar prémio estão no menu. Free Spins automáticas, com aposta e linhas bloqueadas. Ao regressar de outra aplicação, usar `RETOMAR`.

## Regras preservadas

Motor e pagamentos derivados da V0.6, commit `7222903bb4e780b6e5aa0e632d43adaad5eeab5b`. Cinco tambores, três posições, dez linhas selecionáveis, santuário WILD/SCATTER, 8 Free Spins, novas ativações de +8 e símbolos expansivos adicionais. A maior combinação de cada linha vence. [Matemática completa](docs/MATEMATICA.md).

## Melhorias da edição completa

- Novo templo e cinco símbolos principais originais em HD, otimizados em WebP com transparência; cartas vetoriais escaláveis.
- Movimento contínuo dos tambores: a mesma tira visual acelera, desacelera e entrega o resultado, com paragens sequenciais e assentamento suave.
- Expansões progressivas de cima para baixo, contornos turquesa nos tambores premiados, linhas douradas, suspense de Scatter e celebrações por patamar.
- Música ambiente procedural original, efeitos distintos e controlo independente de música/efeitos.
- Botão de giro maior, alvos táteis de pelo menos 44 px, botões HTML acessíveis sobre o Canvas, foco de teclado e diálogo com retenção de foco.
- Créditos calculados em unidades inteiras; resultado e seleção de bónus persistidos antes da animação. Recuperação de giros interrompidos sem nova cobrança nem prémios duplicados.
- Histórico de 100 ações; migração de saldo e bónus válidos da V0.6; proteção cooperativa contra sessões abertas noutras janelas.
- Tabela ilustrada, desenhos das dez linhas, resumo final das Free Spins e simulador reproduzível.

## Estrutura e testes

`js/engine.js`: matemática; `js/session.js`: créditos e transações; `js/game.js`: apresentação e interação; `js/effects.js`: efeitos; `js/audio.js`: música; `assets/`: arte. Tudo fica nesta pasta. Nenhuma alteração ao Jardim Mágico ou aos seus dados.

Requer Node.js 18 ou superior apenas para os testes:

```sh
cd Farao-Eterno
npm test
npm run simulate
```

Para validar a interface, servir esta pasta por HTTP e abrir `tests/browser.html`, premindo **Executar testes**. Essa página usa `?qa=1` e uma chave de gravação separada. É uma ferramenta de diagnóstico, não uma rota necessária para jogar.

## Limitações

O saldo é local ao navegador, não sincronizado com uma conta. Limpar os dados do navegador apaga a sessão. Se o armazenamento estiver indisponível, o jogo indica que não grava. Não existe backend, dinheiro real, sistema de depósitos ou levantamentos. A proteção entre janelas é cooperativa, não uma barreira de segurança para valores reais. A V0.6 não guardava resultados pendentes; uma jogada já perdida nessa versão não pode ser reconstruída.

O resultado do simulador é uma estimativa, não certificação nem garantia por sessão. As regras próprias não são uma reprodução dos rolos ou do RTP do Legacy of Dead. Som depende da permissão de reprodução após um gesto. Sem instalação de APK/PWA ou garantia de funcionamento sem rede no primeiro carregamento. A validação no navegador não substitui testes físicos em todos os modelos de iPhone e Android.

Ver [registo de validação](docs/VALIDACAO.md) e [arte](docs/ARTE.md).
