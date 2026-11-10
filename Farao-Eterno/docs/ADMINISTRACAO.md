# Faraó Eterno — Administração V1.3.40

## Natureza dos créditos e limitações
Este jogo funciona **apenas com créditos de simulação**, guardados no armazenamento local do navegador em `farao-eterno-v2`. Não transfere dinheiro real, não tem servidor de contas, banco de dados partilhado, autenticação segura nem certificação de jogo. Os códigos PIN estão gravados no dispositivo e podem ser vistos ou alterados por quem tenha acesso aos ficheiros/armazenamento; **não usar para dinheiro real**.

## Acesso e códigos
Na janela Menu, abrir a aba **ADMINISTRAÇÃO** e carregar no único botão **ENTRAR NA ADMINISTRAÇÃO**. Código inicial: **12345**.

No cofre associado ao saldo do jogador, os botões **DEPÓSITO** e **LEVANTAMENTO** solicitam primeiro os respetivos códigos, ambos inicialmente **12345**. Só depois é apresentado o campo de valor, editável, em créditos, com vírgula ou ponto decimal.

No painel de administração é possível alterar o código do administrador, o código de depósito e o código de levantamento (4–12 algarismos), confirmando o código atual de administrador. O painel disponibiliza um botão **SAIR DA ADMINISTRAÇÃO**.

## Valores e cofres separados
- **Fundo de prémios:** inicia com 5.000 créditos virtuais de capital de simulação. O administrador pode adicionar ou retirar créditos, desde que não haja giros, créditos progressivos ou Free Spins em curso. Nunca é permitido retirar mais do que o saldo do fundo.
- **Cofre de lucros:** recebe a parcela das apostas ou das compras de bónus não destinada aos prémios. É repartido entre técnico e comerciante; cada um tem o seu valor acumulado disponível e respetivo registo de levantamentos virtuais. Levantar lucro nunca aumenta os créditos do jogador nem movimenta o fundo de prémios.
- **Depósitos e levantamentos:** adicionam/retiram saldo virtual do jogador e são contabilizados separadamente. Não geram automaticamente lucros nem creditam o fundo de prémios. Os totais históricos anteriores à atualização só podem ser recuperados enquanto ainda existirem no histórico local disponível (últimas 100 ações).

## Comissão do técnico e comerciante
A comissão do técnico é ajustável entre 0 e 100%, em unidades de 1 ponto. A do comerciante é calculada automaticamente como `100 - técnico`. Guardar uma nova repartição também reparte os lucros ainda disponíveis; os lucros previamente levantados não são alterados. As alterações ficam gravadas na sessão local.

## Perfil da máquina 85%–95%
A **taxa da máquina** é um perfil de distribuição de créditos entre prémios e lucros, ajustável entre 85% e 95%, de 1 em 1%.

Por cada aposta de 1 crédito, a parcela que entra no fundo de prémios é `taxa/100`, ficando o resto para o cofre de lucros. Os pesos relativos de figuras premium e de torres também sobem com o nível: **85% dá figuras de prémio alto menos frequentes; 95% mais frequentes** antes de aplicar o limite do fundo. Estes níveis **não representam RTP medido nem uma garantia de retorno de 85–95%**. A seleção condicionada ao fundo altera a distribuição real dos resultados.

## Prémios suportados pelo fundo
1. Antes de animar cada giro, o motor gera candidatos e calcula o seu prémio bruto com a tabela aprovada, as linhas e os símbolos expansivos das Free Spins.
2. Só aceita grelhas cujo valor bruto possa ser pago com o fundo de prémios disponível, incluindo a parcela desta aposta quando aplicável, e dentro do limite regulamentado de 5.000× da sessão.
3. Se não encontrar uma combinação permitida, tenta uma grelha neutra avaliada pelo mesmo motor. Se nem essa for pagável, **não inicia o giro e não altera o saldo, a aposta nem a sequência**: é necessário adicionar fundos.
4. A grelha escolhida é guardada em `pending` **antes de rodar**, sem ser substituída a meio da jogada.
5. Na liquidação, o cofre perde o montante do prémio exatamente uma vez, mesmo quando a interface apresenta as parcelas progressivamente. Um regresso ao Safari recupera a jogada pendente sem refazer o sorteio.
6. As Free Spins mantêm os prémios de torres, símbolos expansivos, retriggers e limite cumulativo aprovados. O jogo base mantém as suas regras de pagamento.
7. As apostas e compras de bónus alimentam os cofres; depósitos e levantamentos do jogador apenas movimentam o seu saldo virtual.

## Informações de implementação
- Motor original: `js/engine.js`
- Sessão recuperável: `js/session.js`
- Contabilidade e seleção financiada: `js/economy.js`
- Interface dos cofres e códigos: `js/admin-ui.js`
- Testes: `tests/admin-economy.cjs`

Um painel com utilizadores distintos e saldos reais exigiria base de dados, API protegida, autenticação no servidor, controlo de operações concorrentes, trilho de auditoria inviolável, conformidade legal e certificação de matemática/RTP. A página estática GitHub Pages **não oferece esses requisitos**.
