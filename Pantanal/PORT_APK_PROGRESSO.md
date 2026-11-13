# Pantanal — migração do APK 2.06 para web (v2.14)

Atualização: 13/11/2026. Jogo de créditos **virtuais**, não certificado para dinheiro real.

## Origem consultada

Os **254 ficheiros originais** e os **2 598 Java reconstruídos** estão disponíveis no ramo [pantanal-apk-206-import](https://github.com/hunderburn39-beep/Jardim-Magico/tree/pantanal-apk-206-import/Pantanal/APK-2.06).

Fontes consultadas: GeneratorNew.java e MainActivity.java, com Init/Generate/faixa/Calcpre1/downDificult/Verifica/NumRoleta/NumDado/ShowMorango/ShowCaldeirao/ShowAbobora/SetAcumulado/SaiAcumualdo*. De TJAdUnitConstants.java foi confirmada a constante DEFAULT_VOLUME_CHECK_INTERVAL=500.

## Implementado na v2.14

- **20 linhas horizontais e diagonais** com percursos verificados no APK, combinados por máscara binária em cinco tambores. Contabiliza conjuntos dispersos de 3, 4 ou 5 símbolos quando a tabela permite, em vez de exigir sequências apenas a partir do primeiro tambor.
- **Cinco linhas verticais**, 21–25, com três símbolos por coluna. Valores do APK (por aposta de linha): índices 0:100; 1:70 + roleta; 2:50 + dados; 3:20; 4:morangos; 5:20; 6:300; 7:250; 8:200; 9:caldeirão.
- **Apostas**: escolha original de 1/5/9/15/25 linhas, aposta por linha de 1/2/5/10/20/40, débito do produto linha × aposta.
- **Bónus dos morangos**: 12 escolhas, valores de índices 0–5 correspondentes a 2/5/10/20/50/100 × aposta por linha × número de gatilhos; índice 6 termina o bónus, somando os prémios recolhidos.
- **Caldeirão**: cinco opções, com 50/75/100/150 × aposta de linha × gatilhos e alternativa especial para escolha de **cinco abóboras**, conforme referências do APK.
- **Roleta**: 24 setores, x1–x24, com sorteio diferente consoante o estado de dificuldade. Multiplica o prémio acumulado na jogada; substitui a regra experimental 50/75/100/150 da versão antiga.
- **Dados**: escolha entre cinco dados; sorteio de multiplicador x1–x6 que aplica sobre o prémio já acumulado.
- **Três acumulados**: mínimo, médio e máximo; limites e bases guardados no browser, incremento conforme fórmula encontrada em SetAcumulado(), com resgate condicionado e reposição a bases correspondentes quando um prémio de jackpot é atribuído.
- **Gerador de símbolos**: faixas e limites principais de GeneratorNew.faixa, condicional downDificult baseada em bloqueio/saldo/jogadas; substitui pesos inventados na versão web.
- **Administração**: bloqueio e percentagem de acumulação agora usados pelo motor local; guardados no browser.
- **Interface**: linhas SVG presas aos tambores nos modos vertical e horizontal, marcadores nas figuras efetivamente combinadas.

## Auditoria e testes executados

- Testes Node.js da tabela horizontal/vertical, bónus, RNG, acumulados e dificuldade: **aprovados**.
- Teste Chromium real em iPhone 390×844 e horizontal 1000×600: **aprovado** para alinhamento das linhas, pré-visualização de 25 linhas, escolha de dados e ausência de erros JavaScript, em [GitHub Actions](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43495223505). Capturas PNG disponíveis no artefacto do teste.
- Simulação de **40 000 jogadas virtuais**: retorno observado de **85,03%**, dos quais 60,88% vieram de prémios-base e 24,15% de bónus; nenhuma atribuição de acumulados nessa amostra. Resultado aleatório, **não RTP certificado nem garantia de longo prazo**, verificável no [relatório](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/43495419881).

## Limitações importantes

- Não foi recuperado um projeto-fonte Android original: o JADX reconstruiu Java a partir do DEX. Algumas funções Java estão incompletas/alteradas pelo descompilador; o estado persistente e os valores condicionais não foram totalmente verificados.
- O gerador da web usa faixas principais, **mas ainda não reproduz todos os casos forçados do GeneratorNew.Generate**, gestão de replay, gatilhos simultâneos, políticas de trava de acumulados e respostas específicas a estados de administração.
- As proteções condicionais do caldeirão e dos morangos estão aproximadas. O modo de pagamento da roleta e dos dados depende de estado reconstruído; a distribuição pode divergir do APK no longo prazo.
- Não garante que os sprites da aplicação web sejam uma réplica visual integral do APK, apesar dos recursos originais arquivados. Testes em Chromium não são substituto de testes em Safari real.
- O prémio dos acumulados só é atribuído quando estão reunidas condições de jogada, aposta por linha e tempo de sessão. A simulação de 40 000 jogadas não acionou jackpot; essa parte precisa de mais testes de distribuição.

## Ferramentas

- Pantanal/apk_engine.js: avaliador e sorteio.
- Pantanal/bonus_ui.js: quatro bónus, incluindo cena extra das abóboras.
- Pantanal/paylines.js: traçado de linhas.
- Pantanal/tests/apk_engine.test.cjs e original_rules.test.cjs: regras.
- Pantanal/tests/browser_smoke.cjs: geometria 9:16/horizontal, dados e cenários adicionais.
- Pantanal/tests/rtp_audit.cjs: 40 mil jogadas simuladas.

Os recursos originais pertencem aos respetivos titulares. Confirmar direitos antes de redistribuir publicamente ou reutilizar comercialmente.
