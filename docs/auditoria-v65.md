# Jardim Mágico — Auditoria matemática e de fundos (V65)

> Estado: demonstração com créditos fictícios. Os resultados abaixo são simulações de regressão e testes de conservação de fundos; **não representam RTP certificado nem autorizam exploração com dinheiro real**.

## Problema observado na V64

Durante as free spins, as 2/3 figuras SCATTER davam repetidamente +8/+15 spins, permitindo sessões muito prolongadas. Um exemplo real relatado foi um valor base de **11,00 € ×80 = 880,00 €** com cerca de 50 free spins ainda por jogar. O valor multiplicado era apenas *potencial*: o código já limitava o pagamento ao cofre, mas a apresentação podia sugerir um prémio garantido e a escala dos pagamentos estava desequilibrada.

## Alterações em V65

- Probabilidade fixa por posição de SCATTER em free spins: **1,8%**, antes 3,5%; o resultado continua aleatório, sem ajustes por jogador, banca, saldo ou histórico.
- Durante free spins: 2 SCATTER = +8, 3+ SCATTER = +15; **máximo de 2 extensões por sessão nova**. O máximo atribuído é 15+15+15 = 45 rodadas. Rodadas já atribuídas em sessões antigas não são retiradas.
- Mesma sequência de free spins: soma-se o valor base das quedas, acumula-se a lista de MULTI e aplica-se o multiplicador **uma única vez no fecho**.
- Calibração estatística, independente dos saldos: base 0,61; free spins **0,035** (era 0,51); Hold & Win obtido gratuitamente 0,25; Hold & Win comprado 1,25 (custa 100× a aposta). Estes coeficientes são parâmetros do motor, não percentagens de RTP garantido.
- O cofre tem precedência: os prémios de linhas, free spins e as moedas do Hold & Win só podem retirar fundos do `prizePool` até ao montante existente. Valores potenciais e pagamento até ao cofre são mostrados separadamente.
- Jackpots MIN 250× / MED 500× / MAX 1000× / ULTRA 5000×: multiplicadores e fundos progressivos não foram alterados. O pagamento de cada jackpot é limitado ao **seu próprio fundo** e ao teto total por aposta.
- A reserva protegida da banca e a divisão proprietário/estabelecimento não entram nos prémios.

## Exemplo de 11 € ×80

Com aposta de 0,20 €, o teto geral de 5000× é 1000,00 €. O potencial de 11,00 € ×80 é 880,00 € (abaixo desse teto), mas:

| Cofre de prémios disponível | Máximo pagável no bónus |
|---:|---:|
| 30,00 € | 30,00 € |
| 120,00 € | 120,00 € |
| 880,00 € | 880,00 € |

Os jackpots utilizam fundos diferentes e só acrescem quando são efetivamente atribuídos. O número bruto calculado **não é uma promessa de pagamento superior ao cofre**.

## Como entram as apostas, com margem de banca a 4%

A contabilização usa cêntimos e remainders fracionários para não perder cêntimos nas apostas pequenas.

| Movimento | Margem banca (4%) | Fundos jackpot (3%) | Cofre principal (93%) |
|---|---:|---:|---:|
| 0,20 € de aposta normal | 0,008 € média | 0,006 € média | 0,186 € média |
| Compra free spins por 4,00 € | 0,16 € | 0,12 € | 3,72 € |
| Compra Hold & Win por 20,00 € | 0,80 € | 0,60 € | 18,60 € |

A margem da banca inclui a parte protegida e a repartição 60/40 (ou outra configurada). Os 3% dos jackpots são distribuídos por ULTRA 1,35%; MAX 0,75%; MED 0,50%; MIN 0,40%.

## Simulações de referência (aposta de 0,20 €, margem 4%, estilo Equilibrado)

**Atenção:** aproximações Monte Carlo usando geradores de símbolos, tabela de linhas, multiplicadores e regras de cascata atuais; não substituem simulação integrada certificada de todas as transições, bónus, depósitos e cofre.

| Área | Amostra | Observação |
|---|---:|---|
| Base, linhas antes de multiplicadores | 20 000 spins | ~0,115 € de prémio base médio por spin; três SCATTER em ~1,09% |
| Free spins após nova calibração | 1 500 sessões | ~20,8 free spins por bónus; **3,60 €** de prémio potencial médio; percentil 95 ~**11,18 €** |
| Hold & Win atribuído | 11 000 sessões | ~**3,53 €** de prémio base potencial médio, antes dos jackpots |
| Hold & Win comprado (custo 20 €) | 11 000 sessões | ~**17,77 €** de prémio base potencial médio, antes dos jackpots e do limite do cofre |

Os valores mudam com a margem configurada, frequência das figuras, aleatoriedade, tamanho das apostas, fundos existentes e jackpots. **Não deduzir daqui um RTP final de 96%, 93% ou outro sem simulação integral.**

## Testes de conservação de fundos

Foram testadas 60 000 apostas com apostas de 0,05 € a 1,00 € e margens sucessivas de 4%, 10%, 20%, 30% e 70%. Após cada aposta e após pagamentos de prémios base, prémios propositadamente acima do cofre e jackpots, verificou-se:

1. `prizePool >= 0` e todos os fundos de jackpot `>= 0`.
2. `operatorReserve >= protectedReserve`.
3. `operatorReserve == protectedReserve + ownerShareReserve + merchantShareReserve`.
4. Total de apostas = cofre principal + reserva da banca + buffer de risco + fundos de jackpots + prémios pagos (no teste sem levantamentos/depósitos).
5. Pedidos `NaN`, infinito ou negativos devolvem zero, sem corromper os saldos.
6. Nenhum prémio pode retirar fundos da reserva protegida ou de um jackpot diferente.

**Todos os 60 000 testes contabilísticos passaram.** Testes adicionais com 880,00 € pedidos contra cofre de 120,00 € pagaram exatamente 120,00 € e deixaram o cofre em zero, sem valores negativos.

## Limitações e tarefas futuras

O jogo continua a ser **front-end/localStorage com créditos fictícios**: os valores não são garantidos contra manipulação do navegador, múltiplos dispositivos ou falhas de persistência. Os testes matemáticos não substituem um servidor transacional, certificação RNG/RTP, auditoria independente ou licença para dinheiro real.
