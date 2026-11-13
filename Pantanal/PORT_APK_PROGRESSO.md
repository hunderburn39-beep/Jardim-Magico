# Pantanal — migração do APK Android 2.06 para web (v2.17)

Atualizado em **13/11/2026**. O jogo web usa **créditos virtuais**, sem servidor de apostas reais nem RTP certificado.

## Materiais de referência recuperados

Ramo: [pantanal-apk-206-import](https://github.com/hunderburn39-beep/Jardim-Magico/tree/pantanal-apk-206-import/Pantanal/APK-2.06).

Foram importadas **254 entradas originais** do APK e reconstruídos **2598 ficheiros Java** por JADX. Estes ficheiros *não* correspondem ao projeto-fonte Android original. As regras listadas abaixo foram confrontadas com os métodos do APK, incluindo GeneratorNew.java, MainActivity.java, Database.java e PainelScreen.java.

## Regras migradas e versões

- **v2.13:** 20 linhas com percursos originais, linhas 21–25 verticais de três figuras, aposta por linha e primeira versão do bónus dos dados.
- **v2.14:** tabelas horizontais por máscara binária (combinações dispersas de três, quatro e cinco símbolos), quatro bónus, cena especial das abóboras, roleta de 24 setores, acumulados e parâmetros de admin ligados aos resultados.
- **v2.15:** implementação das travas **travaMin(7)** e **travaAcum(7/8)**, deteção antecipada de morangos e caldeirão, correção de bónus duplos e padrões especiais das primeiras 200 verificações do gerador. Foi também implementado o sorteio condicional de MED e MAX e a proteção contra cinco colunas completas do símbolo específico.
- **v2.16:** contador persistente das chamadas a **downDificult()**. O APK chama esse método na geração dos tambores *e também* no cálculo de dados, roleta, morangos e caldeirão; estas chamadas passam agora a fazer parte da evolução da dificuldade no navegador.
- **v2.17:** estados **MED/MAX autorizados** e número de sorteio especial configuráveis no admin local. O código original ativava MED/MAX através de mensagens de um servidor remoto (PainelScreen.EnviarLeitura e respetivos flags \`med\`, \`max\`, \`sorteio\`). Essa comunicação **não foi recuperada**; no navegador existe apenas autorização manual para créditos virtuais. Quando o evento ocorre, a autorização é consumida.

## Como testar o admin local

1. Entrar no **Pantanal** e abrir **Administração** (PIN local previamente definido no próprio jogo).
2. Ver os acumulados MIN, MED, MAX e os valores de base/limite, de acordo com as definições do próprio jogo.
3. Ativar **Autorizar MED (modo local)** ou **Autorizar MAX (modo local)**, ajustar **Intervalo especial do APK** se necessário e carregar em **GRAVAR**.
4. Rodar com as condições de aposta exigidas pelo gerador. Ativar a autorização **não** garante um jackpot numa jogada; o sorteio especial continua probabilístico.
5. Após um evento especial MED/MAX, a autorização correspondente passa novamente a desligada.

**Importante:** o PIN do navegador e as autorizações guardadas em armazenamento local não têm segurança adequada para um serviço real de apostas. Não representam pagamentos, depósitos ou jogos de azar homologados.

## Testes de código e navegador

- [Validação do gerador original v2.15](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43504742629): proteção dos acumulados, desenhos forçados, baralhamento e 5000 resultados de teste.
- [Compilação e integração v2.16](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43505465310): validou a nova sequência de chamadas à dificuldade, testes JavaScript e integração no HTML.
- [Integração MED/MAX v2.17](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43506217452): testou o novo admin e a sintaxe da página.
- Os testes Chromium existentes verificam 390×844 (portrait), 1000×600 (horizontal), linhas alinhadas ao \`#reels\`, 25 percursos, jogada, ecrãs dos bónus e erros de JavaScript. O ambiente de teste **não equivale** a um Safari real no iPhone.

## Auditoria matemática experimental

[100 000 jogadas virtuais na v2.16](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43505686309), com 25 linhas e 2 créditos por linha, sem desbloqueios MED/MAX:

| Métrica | Resultado observado |
| --- | ---: |
| Total apostado | 5 000 000 créditos |
| Total pago | 3 857 928 créditos |
| Retorno observado | **77,16%** |
| Pagamentos-base | 59,45% |
| Bónus | 17,71% |
| Jackpot | 0,00% |
| Jogadas com padrões forçados | 2 |
| Verificações de dificuldade | 103 063 |
| Prémio máximo nesta amostra | 6 720 créditos |

Estes valores são resultados aleatórios de uma única simulação, **não** percentagem teórica comprovada. O resultado difere do RTP do APK real, que ainda não está matematicamente certificado.

## Limitações atuais

- A comunicação do Android com o servidor remoto que autorizava MED/MAX **não foi replicada**. A substituição local é explícita e não pretende mascarar a ausência do servidor.
- A persistência de sessões e de estados de bónus entre interrupções nem sempre reproduz o comportamento do Android SQLite/SharedPreferences.
- Certas transições raras do gerador podem ter diferenças devidas à reconstrução incompleta do DEX. A cadeia \`Generate\`/\`faixa\`/\`travas\` foi parcialmente portada mas não demonstrada equivalente para todos os estados.
- A arte original encontra-se arquivada no ramo de referência; o layout HTML tem proporções próprias e as capturas automáticas não certificam uma cópia pixel-a-pixel do APK.
- As probabilidades dos jackpots precisam de ser auditadas também *com* MED/MAX autorizados, de forma separada dos testes sem autorização.
- Não existe homologação ou adequação a dinheiro real; confirmar direitos de redistribuição dos recursos originais.

## Módulos úteis

- \`Pantanal/apk_engine.js\`: gerador, padrões, máscaras, eventos especiais, acumulados e sorteios.
- \`Pantanal/bonus_ui.js\`: morangos, caldeirão, abóboras, roleta e dados.
- \`Pantanal/paylines.js\`: percursos e realce de figuras vencedoras.
- \`Pantanal/tests/generator_guards.test.cjs\`: cenários controlados de trava e desbloqueio.
- \`Pantanal/tests/browser_smoke.cjs\`: controlo visual, admin e bónus no Chromium.
- \`Pantanal/tests/rtp_audit.cjs\`: simulação com chamadas adicionais da dificuldade durante os bónus.
