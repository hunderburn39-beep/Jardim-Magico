# Portões do Trovão V13 — Simulação matemática e RTP

**Data:** 6 de novembro de 2026  
**Âmbito:** jogo com créditos virtuais; matemática própria, não certificada.  
**Teto de prémio:** **5.000× a aposta**, em cada ciclo do jogo base ou conjunto de free spins.

## Regras aplicadas nos cálculos

- Grelha de 6 colunas × 5 linhas, com prémios a partir de 8 símbolos iguais em qualquer posição.
- Quedas sucessivas enquanto houver prémios, limitadas tecnicamente a 36.
- WILD original do Jardim Mágico, com valores 1×, 2×, 3×, 4× ou 5×.
- Cada WILD substitui símbolos de **uma única combinação por queda**, sem pagamentos duplicados. Caso surjam vários WILD, aplica-se o maior valor (máximo 5×) à combinação escolhida.
- SCATTER de 4/5/6+ paga 3×/5×/100× e 4+ ativam 15 rodadas grátis. No bónus, 3+ SCATTER acrescentam 5 rodadas.
- Multiplicadores de esfera de 2× a 500× têm frequência reduzida para evitar inflacionar o retorno.
- A **compra de bónus custa 100×** a aposta, tal como antes. Prémio máximo total do bónus: 5.000×.

## Configuração de sorteio, fixa e transparente

| Símbolo especial | Jogo base (por posição) | Rodadas grátis (por posição) |
|---|---:|---:|
| SCATTER | 1,50% | 2,10% |
| Esfera multiplicadora | 0,40% | 0,60% |
| WILD 1–5× | 2,25% | 5,70% |

Os restantes resultados são escolhidos entre as 9 figuras regulares com os pesos definidos no código. A distribuição interna de valores do WILD é 80% para 1×, 15% para 2×, 4% para 3×, 0,8% para 4× e 0,2% para 5×. Estas são frequências por símbolo, não probabilidades de ganho por rodada. Todas as apostas seguem o mesmo sorteio; o saldo não intervém nas probabilidades.

## Resultado de simulações independentes

O simulador usa o **mesmo módulo de WILD** carregado no jogo. Cada execução modela 300.000 rodadas base e 5.500 bónus completos, com sementes de RNG distintas.

| Métrica | Execução A (681925) | Execução B (935721) |
|---|---:|---:|
| RTP estimado do jogo base | 80,74% | 82,59% |
| Retorno adicional esperado dos bónus ativados | 14,59 p.p. | 13,92 p.p. |
| **RTP total estimado** | **95,33%** | **96,51%** |
| Rodadas base com algum prémio | 31,33% | 31,51% |
| Ativação de bónus via SCATTER | 0,156% | 0,145% |
| Pagamento médio do bónus | 93,51× | 95,97× |
| Duração média do bónus | 19,03 rodadas | 18,97 rodadas |
| Retorno estimado da compra por 100× | 93,51% | 95,97% |

**Média das duas execuções:** RTP total ≈ **95,92%**, frequência de prémio ≈ **31,42%** e retorno médio da compra de bónus ≈ **94,74%**.

Estes valores são amostrais e podem variar: os intervalos de confiança aproximados de 95% para o RTP total foram **93,40%–97,26%** e **93,95%–99,06%**, respetivamente. Eventos raros exigem amostras maiores para estimativas precisas.

## Como reproduzir

Na pasta `templo-do-trovao/` do repositório, num computador com Node.js:

```bash
node tools/simular-rtp-v13.js 300000 5500 681925
node tools/simular-rtp-v13.js 300000 5500 935721
```

O cálculo do RTP soma os pagamentos do jogo base à contribuição esperada dos bónus acionados naturalmente, incluindo multiplicadores e SCATTER, e aplica o teto de 5.000×. A compra direta de bónus tem um RTP próprio calculado à parte.

**Não é uma certificação RNG ou auditoria regulatória.** Não utilizar os números para prometer retornos reais. O jogo continua a aceitar apenas créditos fictícios.
