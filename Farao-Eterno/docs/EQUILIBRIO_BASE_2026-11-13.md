# Faraó Eterno — equilíbrio do jogo base (13-11-2026)

## Objetivo

Permitir mais combinações grandes no **jogo base**, sem depender exclusivamente das Free Spins, sem forçar vitórias, sem mexer na tabela oficial deste jogo original e sem pagar prémios acima do cofre. Todos os valores são créditos fictícios.

## Alteração aplicada

Ficheiro: `js/economy.js`, função `weighted(level,free)`.

Os pesos dos símbolos no **jogo base** são ajustados assim, depois da regulação 85–95 existente:

| Símbolo | Fator adicional |
|---|---:|
| Faraó (KING) | 1,12× |
| Anúbis (ANUBIS) | 1,05× |
| Hórus (HORUS) | 0,94× |
| Máscara (MASK) | 0,94× |
| 10/J/Q/K/A | Sem alteração |
| Torre (TOMB), Wild/Scatter | Sem alteração |
| Todos os símbolos nas Free Spins | Sem alteração |

A grelha permanece aleatória, com três símbolos distintos por tambor. Os pagamentos e limites `PAYS` / `upperLimit` do motor `engine.js` permanecem idênticos. Nenhuma alteração foi introduzida nas novas ativações, símbolos expansivos, Free Spins ou reconstituição do histórico.

## Amostra comparativa reproduzível

125.000 **grelhas de jogo base** por cenário, 10 linhas, aposta total 1 crédito, nível administrativo 90, semente PRNG `20311020`; avaliador real `E.makeGrid` + `E.evaluate`. Mesma semente em ambos os cenários. O estudo exclui o cofre, as Free Spins e qualquer opção de arriscar.

| Métrica | Anterior | Nova versão |
|---|---:|---:|
| Combinações ≥ 10× a aposta total | 1.534 | 1.652 |
| Combinações ≥ 20× | 443 | 466 |
| Combinações ≥ 50× | 175 | 182 |
| Combinações ≥ 100× | 74 | 96 |
| Combinações ≥ 250× | 6 | 8 |
| Grelhas com 3+ torres | 1.899 | 1.916 |
| Taxa de giros base premiados | 43,274% | 42,933% |
| Retorno bruto amostral **só no base**, antes de limites do cofre | 90,058% | 92,560% |

**Atenção:** estes são resultados de uma amostra, não probabilidades exatas, não uma medição do RTP total e não uma promessa de ganhos. Ganhos muito grandes são raros e os resultados observados variam. O cofre rejeita grelhas que não pode pagar, o que altera a distribuição observada e o retorno efetivo. Não se deve utilizar a taxa administrativa 85–95 como se fosse uma certificação de RTP: determina a alocação de cada aposta ao fundo, não o retorno matemático real.

## Correção de integridade do cofre

Na atualização foi também corrigida a ordem em `S.begin`:

1. Gerar a grelha aleatória sujeita ao fundo existente mais a parte da aposta que o alimenta.
2. Criar a transação com `old.begin`.
3. **Assinalar imediatamente** `pending.economyVersion=1`.
4. Só então contabilizar a receita através de `incoming`.

Anteriormente a chamada `incoming` podia validar uma transação nova ainda sem versão e classificá-la como histórica, reabastecendo indevidamente o fundo em circunstâncias específicas. A correção não altera nem reavalia sessões já liquidadas. Transações antigas ainda sem versão mantêm a respetiva lógica de recuperação.

## Validação

`tests/base-balance.cjs` verifica a tabela e limite, pesos base, pesos Free Spins inalterados, integridade de grelhas e 1.200 rondas com cofre, confirmando que nunca é pago mais do que o fundo pode suportar. Este teste foi acrescentado a `npm test`.

Não houve teste manual num iPhone nem certificação independente do RTP total.
