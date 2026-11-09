# Mecânicas conservadas da V4.3.3

A V5.0.0 é uma demonstração com créditos fictícios. `engine.js`, `reels.js`, os perfis de movimento e as imagens aprovadas mantêm os mesmos bytes da base V4.3.3. `release.json` identifica os objetos Git conservados; `tests/final.mjs` confere-os antes da publicação.

## Jogo base

Grelha 5×3 e dez linhas, avaliadas da esquerda para a direita. Apostas admitidas: 0,05; 0,10; 0,20; 0,40; 0,60; 0,80; 1; 1,50; 2; 3; 5; 7,50 e 10 créditos.

Para uma linha de três, quatro ou cinco figuras, o valor bruto é `aposta × coeficiente da figura × coeficiente da sequência`. Os coeficientes de sequência são 0,07 / 0,25 / 0,80. Pela ordem de IDs do motor, os coeficientes das nove figuras normais são 3 / 4 / 8 / 5 / 10 / 7 / 4 / 4 / 7. Cada linha é arredondada a cêntimos antes da soma. Dois WILD nas duas primeiras casas de uma linha acrescentam 0,55× a aposta; podem coexistir com a combinação normal dessa linha.

WILD substitui figuras normais, sem substituir SCATTER, pedra ou moeda. Cada WILD participante recebe um valor de 1 a 5. Os valores dos WILD premiados somam-se durante as cascatas; a mesma posição partilhada por várias linhas não é contada repetidamente. O multiplicador de cada queda é o acumulado desse giro, com mínimo ×1, e multiplica o bruto dessa queda. Várias linhas são sempre somadas.

As peças premiadas saem uma única vez; sobreviventes conservam a ordem na coluna e as novas entram pelo topo. As cascatas terminam quando não existe outra linha premiada, sem contador fixo que interrompa a cadeia.

## Free Spins

Três SCATTER em qualquer etapa do giro base iniciam 15 Free Spins. Dentro da série, dois SCATTER acrescentam até oito giros, ou três acrescentam até 15, até ao limite total de 60 giros realizados e pendentes. A compra custa 20× a aposta.

O bruto das quedas acumula-se sem pagamento intermédio. O multiplicador da série começa em ×1 e soma `máximo(0, multiplicador do giro − 1)` em giros com WILD participante, até ×1000. No último giro paga-se, uma única vez, o bruto acumulado multiplicado pelo fator final. São as regras existentes na V4.3.3, sem uma nova afinação de probabilidades na V5.

## Hold & Win

Seis moedas numa etapa do jogo base ativam a grelha 4×4, desde que o mesmo giro não tenha prioridade de SCATTER. A compra custa 30× a aposta. Começa com seis moedas e três tentativas. As moedas conquistadas mantêm-se fixas; uma nova entrada repõe três tentativas.

Os valores são múltiplos da aposta. O pagamento soma todas as moedas; preencher as 16 casas acrescenta 250× a aposta. O pagamento ocorre no fim e não se repete após recarregar a página. Esta mecânica não implementa uma roleta ou sorteio independente de quatro jackpots: MIN/MED/MAX/ULTRA no ecrã continuam a ser referências ilustrativas, como na base V4.3.3.

## Fundo e dados locais

O motor verifica a cobertura do resultado e da cadeia completa antes de apresentar os símbolos. Não corta um prémio depois de o mostrar. No giro base, a aposta sai do saldo do jogador e 48% alimenta o fundo virtual; compras de bónus alimentam o fundo pelo custo total. Estes parâmetros fazem parte da matemática conservada, não de uma promessa de RTP.

As percentagens dono/comerciante repartem o resultado dos movimentos observados, permanecem em cada evento e não alteram a frequência de prémios. Os três perfis de demonstração também não equivalem a um RTP de 30%, 50% ou 70%. A seleção condicionada pelo fundo impede atribuir um RTP fixo auditado a esta demonstração.

Saldo, grelha e evento são guardados numa transação IndexedDB antes da animação. Só uma aba detém a autorização de escrita. Uma interrupção visual reconstrói o resultado guardado, sem novo sorteio ou pagamento. Os relatórios abrangem apenas os últimos 2500 movimentos que o navegador conserva, sem sincronização central.
