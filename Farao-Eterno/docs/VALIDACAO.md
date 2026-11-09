# Validação — Faraó Eterno V1.0

Executada em 9 de novembro de 2026. Base preservada: V0.6, commit `7222903bb4e780b6e5aa0e632d43adaad5eeab5b`.

## Motor e sessão: 24 testes aprovados

`node --test Farao-Eterno/tests/engine-session.cjs`

- Tabela de pagamentos, pesos, símbolos, dez linhas e regras de início de bónus idênticos à V0.6.
- 10.000 grelhas e avaliações determinísticas comparadas com o motor original, variando apostas, linhas e símbolos especiais.
- Geometria de todas as linhas; substituição Wild; Scatter 3/4/5; expansões em tambores separados; contagem por tambor e escolha do maior prémio por linha.
- Uma ocorrência máxima de Scatter por tambor em 10.000 grelhas.
- Recuperação de giro pago pendente e de última Free Spin com zero giros restantes; liquidação idempotente.
- Créditos insuficientes; Free Spins sem débito; início com 8; novas ativações de +8; todos os nove símbolos e novas ativações posteriores.
- Tetos separados de 5.000× para base e série de bónus; precisão das unidades inteiras em todas as apostas e números de linhas.
- Arriscar usa a aposta original, perde apenas o prémio elegível, termina depois de cinco tentativas e rejeita escolhas inválidas.
- Reposição elimina prémio antigo arriscável; migração de saldo e bónus válidos da V0.6; rejeição de gravações inválidas.
- 2.000 rondas pagas com os respetivos bónus completos: invariantes de saldo verificadas em cada transação.

Verificados também os cinco ficheiros JavaScript de produção com `node --check`.

## Interface no Chromium: 16 cenários aprovados

Executada `tests/browser.html` pelo navegador remoto, usando sessão `?qa=1` separada da normal. [Registo dos resultados observados](browser-results.json).

1. Arte HD carregada e versão 1.0.
2. Ecrã 320×568: proporção 9:16, alvos ≥44 px dentro da área do jogo.
3. Ecrã 375×667: as mesmas verificações.
4. Ecrã 390×844: as mesmas verificações.
5. Ecrã 430×932: as mesmas verificações.
6. Ecrã 768×1024: as mesmas verificações.
7. Menu, regras, tabela com dez símbolos, desenhos das dez linhas e histórico.
8. Alteração de aposta e linhas pelos controlos.
9. Giro e paragem rápida, sem duplicação do prémio ao tentar liquidar novamente.
10. Três Scatter ativam oito giros e bloqueiam a aposta.
11. Expansão em dois tambores separados e ordem de varrimento.
12. Nova ativação acrescenta oito giros e outro símbolo expansivo.
13. Oito Free Spins automáticas até ao resumo final, seguido de continuar.
14. Reposição de créditos elimina elegibilidade de um prémio antigo.
15. Recarregar a página recupera exatamente o resultado pendente.
16. Evento de sessão atualizada noutra janela pausa a janela anterior.

Inspeção visual do cenário, símbolos, interface e apresentação das linhas. Os testes de cenários usam resultados determinísticos na sessão de diagnóstico; o jogo normal continua a usar sorteios aleatórios.

## Matemática: um milhão de rondas completas

Semente 20261109, dez linhas, aposta 1. Retorno observado: **96,0005%**, intervalo normal aproximado de 95%: **94,6785–97,3225%**. Inclui 9.425 bónus e 81.504 Free Spins. Sem arriscar. [Dados e comando reproduzível](simulation-1000000.json). [Explicação e limitações](MATEMATICA.md).

## Âmbito e limitações

Só a pasta `Farao-Eterno/` é alterada. A publicação usa a árvore mais recente do repositório como base; não substitui a raiz nem as pastas, dados ou fluxos de publicação do Jardim Mágico.

Os tamanhos virtuais de ecrã foram testados no Chromium, não em dispositivos físicos. Safari/iOS, Chrome/Android, consumo de bateria e áudio em todos os modelos não foram certificados. As animações usam `requestAnimationFrame`, limite de resolução Retina, menos desenho em repouso e partículas limitadas; não se promete uma taxa de fotogramas em qualquer equipamento.

Sem servidor ou conta: créditos locais, fictícios, sem valor monetário. A sincronização entre janelas é cooperativa; não impede adulteração local e não é um sistema de transações reais. A simulação não constitui cálculo exato de RTP nem certificação. Abrir diretamente por `file://` não é o modo validado; usar o endereço HTTP/HTTPS publicado.
