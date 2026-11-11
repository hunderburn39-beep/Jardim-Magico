# Stake Engine — resultados experimentais e próximos requisitos (V0.2)

**Data:** 2026-11-11. **Ramo:** `feature/jardim-magico-stake-engine`.
**Nunca interpretar esta simulação como licença, aprovação, garantia de RTP, certificação ou configuração pronta para dinheiro real.**

## Verificações confirmadas

GitHub Actions, execução [43062665970](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43062665970):
**16 testes automáticos passaram**; três modos gerados e reponderados; artefactos `jardim-stake-math-experimental` guardados durante sete dias.

Amostra de **300 simulações por modo**, `seed=20261111`:

| Modo | Custo | RTP com pesos iguais (empírico) | RTP reponderado (da mesma amostra) | ESS (de 300 livros) |
| --- | ---: | ---: | ---: | ---: |
| base | 1× | 121.016667% | 96.000000% | 299.61 |
| free_buy | 20× | 143.561500% | 96.000000% | 285.92 |
| hold_buy | 30× | 993.588889% | 96.000000% | **16.20** |

**ESS** = tamanho efetivo da biblioteca depois dos pesos, `(soma_pesos)^2 / soma(peso^2)`. Um ESS de 16.2 no Hold é um **sinal de alerta de diversidade severamente insuficiente**: o ajustamento foi dominado por resultados pouco frequentes na amostra. Não usar o ficheiro afinado na plataforma.

Os pesos são inteiros positivos e estáticos associados a resultados pré-gerados. Nenhuma aposta anterior, valor de saldo ou jogador altera a seleção. O `math/reweight.py` não altera os eventos nem os prémios do livro; apenas escreve novas tabelas CSV e um `index.tuned.json` de exploração, que **não substitui** `index.json` nem é diretamente publicável.

## Riscos e problemas a resolver

1. **Hold & Win:** o modelo atual distribui valores de moedas e recompensa fixa de grelha completa de forma demasiado generosa. Redesenhar a frequência das novas moedas e os respetivos pesos, respeitando um limite plausível de prémio, antes de otimizar.
2. **Free Spins:** a probabilidade de 2/3 SCATTER e o acumulador WILD carecem de simulação com larga amostra e verificação de extremos.
3. **Jogo base:** validar todos os coeficientes e arredondamentos ao cêntimo, frente à matemática documentada da demonstração; a edição é um modelo separado.
4. **RNG e validação:** gerar 100 mil a 1 milhão de livros diversificados por modo, perfil de cauda, taxa de ativação, RTP matemático ponderado, variância e testes independentes, depois validar usando SDK/RGS oficial.
5. **UX / IP / regras:** não existem ainda gráficos comerciais, integração `/play` nem aprovação. Rever tema para afastar apelo a menores, comprovar licenças das imagens/sons, retirar jackpots progressivos ou publicidade aos mesmos.

## Como reproduzir no ramo

```bash
cd stake-engine/jardim-magico
python -m unittest discover -s tests -v
python math/complete.py --rounds 300 --seed 20261111 --out build/features-prototype
python math/reweight.py --source build/features-prototype --out build/tuned-prototype --target-percent 96
```

Todas as ferramentas usam créditos **puramente matemáticos/virtuais**. Nada nesta pasta recebe dinheiro, gera pagamentos reais, explora credenciais ou comunica com um casino.
