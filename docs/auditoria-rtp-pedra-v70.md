# Jardim Mágico V70 — Auditoria do RTP após a PEDRA

**Data:** 6 de novembro de 2026. **Jogo:** créditos fictícios, sem dinheiro real. **Motor analisado:** `js/jogo-2.js`, blob Git `6846e95b7abcf2556fe3ca342fee0d4e60b75ca4`.

## Condições da medição

- 100 000 apostas base por cenário, de **0,20 €** cada (20 000 € virtuais apostados em cada cenário).
- Mesmos parâmetros de pagamento e probabilidades do motor do Jardim Mágico, comparando **PEDRA 0%** e **PEDRA 6%**, nos modos Equilibrado e Prémios Frequentes.
- Simulação Monte Carlo reproduzindo o sorteio dos símbolos, 15 linhas, pares WILD adjacentes, WILD fixos, até 12 cascatas, multiplicadores das bolas, acumulação e reativações das free spins (até duas extensões), moedas, gatilho do cofre, Hold & Win 4×4, jackpots e seus limites.
- Cofre de prémios começa a zero e recebe a contribuição de cada aposta: 96% menos os 3% destinados aos jackpots, com reserva de 4% para o operador. Todos os prémios são limitados pelos fundos disponíveis.
- Não estão incluídas **compras voluntárias** de free spins por 20× nem de Hold & Win por 100×. Também não foram simuladas variações de saldo do jogador, abandono da sessão, depósitos ou levantamentos.
- Este é um **modelo estatístico independente** construído com as funções de geração, avaliação e cascata do motor, e as regras financeiras e de bónus replicadas para correr sem animações; não é uma execução certificada do navegador.

## RTP total observado nas amostras

| Modo | Sem Pedra | Com Pedra a 6% | Diferença |
|---|---:|---:|---:|
| Equilibrado | 76,44% | **57,90%** | −18,54 pontos percentuais |
| Prémios Frequentes | 87,32% | **64,68%** | −22,64 pontos percentuais |

### RTP estimado com Pedra, por origem

| Origem | Equilibrado | Prémios Frequentes |
|---|---:|---:|
| Linhas / jogo base | 37,64% | 37,29% |
| Free spins gratuitas ativadas por SCATTER | 7,66% | 14,19% |
| Hold & Win gratuito do cofre | 9,64% | 10,29% |
| Jackpots progressivos | 2,97% | 2,90% |
| **Total observado** | **57,90%** | **64,68%** |

Diferenças de 0,01 ponto percentual nas somas podem resultar de arredondamento dos componentes.

## Outras observações nas amostras com Pedra

| Indicador (por 100 mil apostas base) | Equilibrado | Frequentes |
|---|---:|---:|
| Apostas com pelo menos uma linha vencedora | 22,47% | 32,55% |
| Apostas que ativaram free spins | 1 051 | 1 226 |
| Free spins efetivamente jogadas, incluindo extensões | 21 527 | 26 239 |
| Extensões de free spins concedidas | 673 | 898 |
| Hold & Win abertos pelo cofre | 581 | 602 |
| Hold & Win abertos por 6 moedas simultâneas | 0 | 0 |
| Pedras na grelha inicial de 15 casas (média) | 0,90 | 0,90 |
| Eventos limitados pelo saldo do cofre no modelo | 0 | 0 |

Os fundos finais, sem compras voluntárias e neste modelo, mantiveram-se positivos. O teste não prova que qualquer prémio bruto possa ser pago integralmente: o motor continua a limitar pagamentos pelo cofre e pelo teto de 5000×.

## Interpretação

1. A Pedra **não paga** e **interrompe** combinações. Os seus 6% de probabilidade são retirados às figuras normais, sem alterar diretamente as probabilidades de SCATTER, WILD, MULTI ou moedas. Menos sequências vencedoras também significam menos cascatas e menos oportunidades adicionais de ativar bónus.
2. A constante `RTP_REFERENCE=96` **não constitui evidência** de que o RTP total do jogo seja 96%. Os multiplicadores do motor `RTP_CALIBRATION={base:0.34,free:0.015,hold:0.25,holdPurchase:1.25}` são parâmetros de pagamento; a frequência de símbolos e cascatas determina o retorno observado.
3. Nas amostras a 0,20 €, o retorno efetivo com a Pedra ficou aproximadamente nos intervalos **58% (Equilibrado)** e **65% (Frequentes)**; não se deve anunciar 96% como RTP medido.
4. Os resultados variam com o tamanho da amostra, aposta escolhida, sorteios raros, uso de compras e saldo do cofre. Como os pagamentos são discretizados em cêntimos e limitados pelo fundo disponível, os valores não devem ser extrapolados automaticamente para apostas de 0,05 € ou 10 €.
5. **Não foram alteradas as probabilidades nem os coeficientes de pagamento nesta auditoria.** Para procurar retorno total próximo de um alvo, é necessária uma calibração separada, com simulações adicionais e verificação de solvência para todas as apostas e modos.

**Estado:** cálculo estatístico documentado, **não certificado** por laboratório de jogos. Nenhuma promessa de RTP teórico garantido ou de pagamento de prémios potenciais acima do cofre.
