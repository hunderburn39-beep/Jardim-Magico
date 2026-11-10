# Matemática — Faraó Eterno V1.3.22

## Origem e limites da referência

Preserva os **multiplicadores, símbolos, pesos e regras de avaliação** da V0.6 publicada no commit `7222903bb4e780b6e5aa0e632d43adaad5eeab5b`. **As 10 linhas foram ajustadas na V1.3.22 à fotografia enviada pelo utilizador.** A cópia anterior está em `tests/engine-v06.cjs` e em `LEGACY_LINES`; a comparação automática usa as linhas antigas para verificar que os pagamentos e as restantes regras continuam iguais. As jogadas iniciadas antes da atualização são liquidadas usando as suas linhas anteriores, sem alterar créditos nem prémios históricos.

Legacy of Dead, da Play’n GO, é a referência de mecânicas: santuário que combina Wild e Scatter, Free Spins com expansivos e novas ativações. [Página oficial consultada](https://www.playngo.com/games/legacy-of-dead). A arte, os pesos e o gerador desta demonstração são independentes. Não se atribui a este jogo qualquer RTP do título de referência.

## Apostas e linhas

Apostas totais: 0,10; 0,20; 0,40; 0,50; 1; 2; 5; 10 créditos. Entre 1 e 10 linhas, ativadas por ordem. Aposta por linha = aposta total ÷ linhas ativas. A aposta e as linhas ficam bloqueadas durante os giros e toda a série de Free Spins.

Filas: 0 = cima, 1 = centro, 2 = baixo. Cada coluna da lista representa um tambor, da esquerda para a direita.

| Linha | Filas |
|---|---|
| 1 | 1, 1, 1, 1, 1 |
| 2 | 0, 0, 0, 0, 0 |
| 3 | 2, 2, 2, 2, 2 |
| 4 | 0, 1, 2, 1, 0 |
| 5 | 2, 1, 0, 1, 2 |
| 6 | 1, 0, 0, 0, 1 |
| 7 | 1, 2, 2, 2, 1 |
| 8 | 0, 0, 1, 2, 2 |
| 9 | 2, 2, 1, 0, 0 |
| 10 | 1, 0, 1, 2, 1 |

## Tabela de prémios aprovada (inalterada)

Multiplicadores da aposta **por linha**, exceto os pagamentos Scatter descritos abaixo.

| Símbolo / código | 2 | 3 | 4 | 5 | Peso |
|---|---:|---:|---:|---:|---:|
| 10 | — | 5 | 20 | 80 | 18 |
| J | — | 5 | 20 | 80 | 17 |
| Q | — | 5 | 20 | 80 | 16 |
| K | — | 5 | 30 | 120 | 15 |
| A | — | 5 | 30 | 120 | 14 |
| Hórus / HORUS | 5 | 20 | 80 | 600 | 8 |
| Máscara / MASK | 5 | 20 | 80 | 600 | 7 |
| Anúbis / ANUBIS | 5 | 30 | 300 | 1600 | 5 |
| Faraó / KING | 10 | 100 | 1000 | 5000 | 3 |
| Santuário / TOMB | 10 | 100 | 1000 | 5000 | 3,85 |

As combinações normais começam no primeiro tambor, em tambores consecutivos. O santuário substitui os nove símbolos normais. Avaliam-se todos os candidatos e paga-se apenas o maior prémio por linha. Empates mantêm o primeiro candidato na ordem do motor; o valor é o mesmo.


> **Nota V1.3.22:** a alteração das linhas muda as combinações elegíveis e pode alterar a frequência de prémios e o RTP global. Os resultados de simulações anteriores à V1.3.22, quando referidos abaixo, não constituem uma medição da nova geometria. É necessária uma simulação específica para atualizar essas estimativas.

## Geração da grelha e Scatter

Soma dos pesos = 106,85. Cada posição recebe uma escolha proporcional ao peso. Existe no máximo um santuário em cada tambor: se outro santuário for sorteado no mesmo tambor, há uma nova escolha, excluindo esse símbolo. Os tambores são independentes; as posições de um mesmo tambor não são independentes devido a esta restrição. Não são usadas tiras privadas de outro jogo. As tiras do efeito visual não determinam o resultado.

`crypto.getRandomValues` fornece o sorteio no navegador; há fallback para `Math.random` em ambientes sem Web Crypto. O simulador usa uma sequência determinística com semente publicada. Nenhum sorteio depende do saldo, dos prémios anteriores ou da animação.

3/4/5 santuários em qualquer posição pagam respetivamente 2×/20×/200× a aposta **total**, além das linhas, e iniciam 8 Free Spins. Com p = 3,85 / 106,85, a probabilidade de um tambor conter um santuário é q = 1 − (1 − p)³. A probabilidade exata de ativação é:

`10q³(1−q)² + 5q⁴(1−q) + q⁵ = 0,009631424776602065`, cerca de **0,96314%**, ou 1 em 103,83 giros. Esta probabilidade não depende da aposta nem das linhas.

## Free Spins e múltiplos expansivos

Um dos nove símbolos normais é sorteado uniformemente à entrada. Cada nova ativação de 3+ santuários acrescenta 8 giros e um dos símbolos ainda não desbloqueados, também uniformemente. Até nove símbolos diferentes; depois, novas ativações continuam a dar +8 sem adicionar símbolos. Sem limite artificial de novas ativações. Sem o teto de prémio, o número médio esperado de giros por bónus seria `8 / (1 − 8 × P(ativação)) ≈ 8,66787`.

Conta-se o número de **tambores distintos** que contêm cada expansivo. Se a respetiva entrada da tabela pagar, o símbolo expande nas três posições dos seus tambores. Pode pagar com intervalos e sem começar no tambor esquerdo. Santuários não substituem expansivos nesta contagem. Três cópias no mesmo tambor contam apenas uma vez. Cartas precisam de três tambores; os quatro símbolos premium, de dois.

**Não há soma de pagamentos normal + expansivo numa linha.** Para cada linha ativa, o motor compara a combinação normal e cada expansão qualificada e paga apenas a maior. Isto é a regra aprovada da V0.6, preservada mesmo quando difere de outras implementações comerciais. Se coexistirem expansivos no mesmo tambor, a apresentação usa o de maior prémio; os contornos e o detalhe da linha identificam o candidato pago. Não há pagamento novo por cada quadro da animação.

## Tetos e precisão

O teto preservado da V0.6 é **5.000× por jogada base**, e **5.000× para o total de uma série de Free Spins**. O prémio da jogada que ativa o bónus é pago separadamente. Assim, o limite superior conjunto de uma ronda paga que ativa um bónus é 10.000×, não 5.000×. Não foi reduzido silenciosamente para igualar uma alegação sobre o jogo de referência. Quando o total do bónus atinge o teto, o crédito final é limitado ao que falta e os giros restantes terminam.

Um crédito = 252.000 unidades inteiras. Este fator representa exatamente todas as apostas aprovadas divididas por 1–10 linhas. O saldo guarda as frações válidas; a interface apresenta duas casas decimais. A conta não acumula erros de ponto flutuante. Grelha e escolha de bónus são guardadas com a aposta descontada, antes da animação. Liquidar remove a transação pendente, de forma idempotente.

Arriscar: cor = 50% para 2×; naipe = 25% para 4×; cinco tentativas máximas. Apenas sobre o último prémio base elegível, com teto de 5.000× da aposta **original**, mesmo se a aposta seguinte for alterada. Sem teto, o retorno esperado isolado desta escolha seria 100%; o teto pode reduzi-lo. Não faz parte da simulação abaixo.

## Simulação reproduzível de 1.000.000 de rondas

Comando: `node tests/simulate.cjs 1000000 20261109 docs/simulation-1000000.json`. Dez linhas, aposta de 1 crédito, cada bónus completamente terminado antes de iniciar a ronda seguinte; inclui os tetos e as novas ativações. [Dados completos](simulation-1000000.json).

| Medida | Observado |
|---|---:|
| Retorno total da amostra | 96,0005% |
| Contributo das jogadas base | 66,0035% |
| Contributo dos bónus | 29,9970% |
| Intervalo normal aproximado de 95% | 94,6785% a 97,3225% |
| Rondas com algum prémio | 35,5252% |
| Bónus iniciados | 9.425 |
| Free Spins executadas | 81.504 |
| Novas ativações no bónus | 763 |
| Maior ronda observada | 1.610,5× |
| Bónus mais longo observado | 40 giros |

**96,0005% é o retorno desta amostra, não um RTP teórico exato, certificado ou garantido.** A distribuição tem caudas pesadas; o intervalo normal é aproximado e ganhos raros afetam fortemente o resultado. Não se calculou aqui o RTP exato para todos os números de linhas. Séries curtas podem divergir muito. Não existe ajuste dinâmico dos resultados para forçar uma percentagem por jogador.
