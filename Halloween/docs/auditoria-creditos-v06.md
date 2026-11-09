# Halloween V0.6 — auditoria económica de créditos virtuais

Data: 09/11/2026 · Âmbito: `Halloween/js/engine.js`, versão demonstrativa.

## Problema identificado

A matemática anterior premiava **cinco abóboras recolhidas ao longo de várias jogadas** com uma roleta de 30 números contendo quatro acumulados virtuais de valores elevados. O gatilho acontecia muito frequentemente. Uma simulação determinística de 50.000 resultados, incluindo bónus, deu um retorno ilustrativo de cerca de **999%** relativamente às apostas debitadas. Isso era uma anomalia de desenho da demonstração, não um RTP homologado.

## Regra demonstrativa corrigida

- Uma abóbora é recolhida quando há pelo menos **quatro abóboras na grelha durante um giro pago**, no máximo uma recolha por giro.
- A cada **cinco recolhas** são concedidos **cinco giros grátis**. Giros grátis não recolhem mais abóboras.
- **A roleta abre apenas quando aparecem cinco abóboras literais alinhadas numa das linhas de pagamento ativas**, sem substituição por MAGIC. Os valores e a relação com jackpots continuam experimentais.
- O motor recusa a retenção simultânea dos cinco tambores.

Estas regras **não foram extraídas do algoritmo proprietário do APK**. Foram escolhidas para corrigir a distribuição de prémios do protótipo enquanto a investigação das rotinas `NumRoleta`, `calcbonusRoleta` e `EscolheAbobora` continua.

## Resultados da simulação

Foram realizadas cinco sequências determinísticas independentes de **50.000 giros cada**. Foram contabilizadas as apostas debitadas nos giros pagos e os créditos pagos em linhas, Free Spins, Scatter, caldeirões e roleta. Saldos e valores em créditos virtuais, com aposta de 2 créditos e 30 linhas ativas.

| Semente | Giros pagos | Free Spins | Roletas | Retorno observado |
|---|---:|---:|---:|---:|
| 20261109 | 35.302 | 14.698 | 150 | 103,31% |
| 89 | 35.570 | 14.430 | 128 | 93,68% |
| 12345 | 35.695 | 14.305 | 139 | 88,34% |
| 987654321 | 35.880 | 14.120 | 126 | 98,76% |
| 777777 | 35.552 | 14.448 | 121 | 86,52% |

**Média simples das cinco simulações: cerca de 94,12%.**

A variabilidade é elevada por causa dos acumulados de valores altos. As simulações não representam uma amostra suficiente para certificar RTP, não usam o gerador de números aleatórios do APK e **não provam que a matemática corresponde à máquina clássica**. Não usar estes dados como garantia de prémios ou pagamentos reais.

## Testes de regressão

- O conjunto de `Halloween/tests/engine.test.cjs` contém 25 testes, incluindo um cenário económico de 20.000 giros com limite de regressão superior a 200% para evitar voltar ao estado anterior.
- `Halloween/tests/ui-smoke.test.cjs` cobre carregamento da cena, painel de prémios, giro e restauração da sessão (sem um navegador real).
- `Halloween/tests/browser.cjs` destina-se a validar os mesmos recursos com Chromium e WebKit pelo GitHub Actions. O sucesso real desse workflow tem de ser confirmado nos respetivos registos.

## Próximo passo científico

Recuperar as regras efetivas da implementação Android mediante análise estática adicional das classes, dados de linhas e tabelas, e comparar os resultados com uma amostra de giros da versão original. Só então é possível substituir o motor demonstrativo por uma matemática efetivamente documentada.
