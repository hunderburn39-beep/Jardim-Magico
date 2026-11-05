# Jardim Mágico — Auditoria dos WILD fixos e reserva da banca (V66)

**Data:** 5 de novembro de 2026 · **Âmbito:** créditos fictícios; jogo executado no navegador.

## Correção funcional

1. Cada WILD sorteado tem fator fixo **1×, 2×, 3×, 4× ou 5×**, exibido sobre a figura no Canvas 2D e no Pixi/WebGL.
2. O WILD permanece na **mesma posição (coluna, linha) durante todas as cascatas da spin**. As outras peças descem pelos espaços disponíveis de cada segmento, sem atravessar WILDs fixos.
3. Um mesmo WILD só pode ser usado **por uma linha premiada em cada queda**. Se várias linhas partilham o mesmo WILD, tem prioridade a combinação de maior prémio bruto. O multiplicador combinado dos WILDs não ultrapassa **5× por linha**.
4. A mesma combinação formada exclusivamente por WILDs (par ou trio/mais) não paga novamente sem uma nova spin, mesmo quando outras peças caem noutro sítio.
5. Na spin seguinte é gerado um tabuleiro novo. O WILD da spin anterior não é herdado nem contabilizado noutra spin.
6. O prémio de linha no centro continua separado do acumulador da spin/free spins. As bolas MULTI são uma mecânica distinta dos WILDs.

## Probabilidades fixas e calibração

Valores independentes do saldo, do operador, da identidade do jogador e do histórico.

| Parâmetro | V66 |
|---|---:|
| Probabilidade de WILD na base, por posição | 2,65% |
| Probabilidade de WILD nas free spins, por posição | 3,4% |
| Fator do WILD | 1× a 5× |
| Pesos por fator 1× / 2× / 3× / 4× / 5× | 50% / 30% / 12% / 6% / 2% |
| Multiplicação máxima de WILD por linha | 5× |
| Coeficiente fixo de pagamentos de base | 0,34 |
| Coeficiente fixo de pagamentos de free spins | 0,015 |
| Probabilidade de bola MULTI nas free spins | 0,9% por posição |
| Maior valor de **uma** bola MULTI nas free spins | 20× |
| MULTI acumulados nas free spins | Somam-se; podem alcançar 80× e mais |
| SCATTER nas free spins | 1,8% por posição |
| Extensões por bónus | Máximo 2 (+8 ou +15 cada) |
| Cofre geral / jackpots | Fundos separados; limites de pagamento mantidos |

**Nota:** um MULTI raro de 1000× no jogo base continua sujeito ao limite do cofre; não é o jackpot MAX 1000×. A regra nova de máximo 20× aplica-se apenas a cada bola das free spins.

## Simulação dos ganhos potenciais — aposta de 0,20 €

Regimes matemáticos e símbolos retirados do motor, cascatas com WILD fixo, limite de 12 quedas por spin e 2 extensões por bónus. Os resultados não são RTP certificado e não incluem todas as interações da máquina.

| Teste | Amostra | Média observada |
|---|---:|---:|
| Base · Equilibrado | 35 000 spins | 0,0869 € por spin |
| Base · Prémios mais frequentes | 26 000 spins | 0,0882 € por spin |
| Free spins · Equilibrado (multiplicadores até 20× por bola) | 4 200 bónus | 2,1445 € de prémio potencial por bónus |
| Free spins · Prémios mais frequentes | 4 200 bónus | 3,1021 € de prémio potencial por bónus |

**Compra de free spins:** custa 20× a aposta (4,00 € a 0,20 €). Com margem da banca a 4%, cerca de **3,72 €** desse custo vão para o fundo principal de prémios e cerca de **0,12 €** para os jackpots. O prémio potencial médio de ambas as modalidades ficou abaixo do aporte principal nesta amostra, mas a variância continua a permitir sessões de prémio superior ao custo.

O Hold & Win adquirido por 100× a aposta mantém o coeficiente 1,25; o Hold & Win atribuído sem compra conserva 0,25. Não se alteraram os multiplicadores dos quatro jackpots: MIN 250×, MED 500×, MAX 1000× e ULTRA 5000×.

## Conservação de fundos

Executado teste de **70 000 apostas** de 0,05 € a 1,00 €, com margens de 4%, 10%, 20%, 30% e 70%, incluindo pagamentos extremos, pagamentos de jackpots e pedidos inválidos `NaN` / `Infinity` / negativos. Após cada aposta verificou-se:

- `cofre >= 0`, quatro fundos de jackpots `>= 0`, e `reservaOperador >= reservaProtegida`.
- `reservaOperador = reservaProtegida + parteProprietário + parteComerciante`.
- A soma de fundos, prémios pagos e eventuais buffers coincide com o total de apostas introduzidas no teste, sem criação artificial de créditos.
- Cada prémio de base, free spins ou Hold & Win é limitado ao cofre principal. Os jackpots são limitados exclusivamente aos seus fundos e ao limite por aposta.
- Num caso de **11,00 € ×80 = 880,00 € potenciais** com apenas 120,00 € de cofre principal, o máximo pagável é **120,00 €**; os 880,00 € não são apresentados como garantidos.

**Resultado: 70 000 verificações sem falhas de conservação.**

## Testes determinísticos do WILD

Passaram nove verificações: WILD ancorado no centro, animação estacionária, dois WILDs em extremos, uso único entre linhas sobrepostas, fator máximo 5× por linha, par WILD pago uma vez, não repetido em cascatas seguintes, elegível numa spin nova, e registo correto do par já pago.

## Limitação importante

O jogo é uma demonstração local com `localStorage`, créditos fictícios e gerador pseudoaleatório do navegador. **Garantimos que o código não debita mais do que os fundos disponíveis; não que qualquer prémio potencial tenha cobertura integral.** Esta auditoria não determina RTP certificado, nem cobre resistência a manipulação do navegador, persistência multi-dispositivo ou exploração com dinheiro real. Para garantir pagamentos integrais de todos os prémios seria necessário capital de cobertura pré-reservado e um sistema transacional no servidor.
