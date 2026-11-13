# Pantanal 2.06 — auditoria preliminar da matemática

Data: 13-11-2026. Origem: APK fornecido pelo proprietário, `Pantanal+Ca%C3%A7a+Niquel_2.06_APKPure.apk`, e implementação web em `Pantanal/index.html`.

## O que foi localizado no APK original

- O código original está compilado no `classes.dex` (~5,36 MB); os recursos contêm imagens, três tabelas de prémios e áudios, mas não apresentam ficheiros de configuração de RTP ou tabelas de probabilidades em JSON/CSV.
- Pacote da aplicação: `com.r7games.slotpantanal`. Foram identificadas as classes `GeneratorNew`, `Database`, `MainActivity` e `PainelScreen`.
- `GeneratorNew` declara os métodos `Generate`, `Calcpre1`, `faixa`, `travaAcum`, `travaMin` e `downDificult`. Inclui campos como `podePagar`, `bloqueado`, `aposta`, `linhas`, `tabAcum`, `tabela` e `slotVal`.
- `Database` tem `GetAcumulado`, `GetAcumefect`, `GetBloqueado`, `GetCredito`, `GetBonusToCall`, `GetReplaySlots`, métodos de gravação associados e parâmetros `dificul` / `acupor`.
- `MainActivity` apresenta rotinas de roleta, dados, caldeirão, abóbora, morango, dobrar, acumulados e atribuição de prémios.
- As tabelas originais (`assets/05_tabela1.png`, `05_tabela2.png`, `05_tabela3x.png`) distinguem sequências da esquerda e da direita, combinações `Any`, prémios especiais, bónus e três acumulados. A terceira tabela indica aposta mínima de 2 com 25 linhas para os acumulados.
- **Limite desta auditoria:** foram identificados nomes de classes, métodos, campos e tabelas gráficas; a lógica completa de `Generate`, percentagens reais, distribuição dos símbolos e RTP original **não foram ainda recuperados nem certificados**. Não se deve inferir essas taxas apenas pelos nomes das variáveis.

## Matemática implementada atualmente no browser

Código verificado em `Pantanal/index.html`, antes de qualquer alteração na matemática:

- 5 tambores × 3 linhas; 25 linhas por defeito; 10 símbolos. Os 15 símbolos são escolhidos de forma independente com pesos `[7,11,10,10,9,9,8,9,12,10]` (soma 95).
- A aposta é debitada **uma única vez por rodada**, mas o prémio de **cada linha** é calculado multiplicando o valor da tabela pela **aposta total**; não se divide a aposta pelas linhas. Este é um fator de multiplicação excessivo na versão web.
- Só conta uma sequência de 3 a 5 símbolos idênticos a partir do tambor 1. Não foram implementados todos os padrões `Any`, prémios da direita para a esquerda ou mecânicas especiais visíveis nas tabelas originais.
- Se houver pelo menos 3 símbolos com índice 9 em **qualquer** das 15 posições, ativa a roleta, independentemente das linhas.
- A roleta escolhe cada uma das 24 casas com probabilidade 1/24, e distribui quatro multiplicadores `50, 75, 100, 150` em blocos de 6 casas cada. Assim cada multiplicador tem probabilidade de 25%. Essa distribuição é experimental e não é a distribuição original comprovada.
- Os parâmetros `difficulty`, `accumulation`, `accum1min/max`, `accum2min/max`, `accum3min/max` são guardados no painel, mas não são lidos pelo sorteio `pick()`, pagamento `calcWins()` ou ativação do bónus.

## Cálculo teórico exato da implementação web com 25 linhas

Hipótese: pesos e sorteio independente conforme o código; todos os pagamentos relativos à aposta que é debitada por rodada.

- **Prémio base esperado:** 34,7721993 vezes a aposta, somando os 25 pagamentos por linha. Fórmula por símbolo com probabilidade `p`: `p^3(1-p)PAY3 + p^4(1-p)PAY4 + p^5 PAY5`, vezes 25 linhas.
- **Probabilidade de bónus:** `P(Binomial(n=15, p=10/95)>=3) = 0,2046914173`, ou **20,4691%** (aproximadamente 1 a cada 4,89 rondas).
- **Multiplicador médio da roleta:** `(50+75+100+150)/4 = 93,75`.
- **Prémio esperado do bónus por rodada:** `0,2046914173 × 93,75 = 19,1898204` vezes a aposta.
- **Retorno total teórico:** `34,7721993 + 19,1898204 = 53,9620197` vezes a aposta, equivalente a **5396,20%** de RTP estimado para este algoritmo. **Este valor não é o RTP original do APK** e denuncia um desequilíbrio grave da reconstrução web. Não traduz um rendimento garantido numa amostra curta.

## Diferenças mais importantes e recomendações

1. Não afirmar que a matemática web replica o APK. Preservar as imagens e sons, mas separar a mecânica original da reconstrução.
2. Reconstituir e validar o gerador `GeneratorNew` antes de atribuir ao APK qualquer probabilidade, RTP ou controlo de banca.
3. Reproduzir corretamente a tabela de linhas e também prémios invertidos, `Any`, especiais, bónus e acumulados.
4. Definir a aposta por linha ou uma tabela de pagamento normalizada pela aposta total; nunca multiplicar cegamente cada linha por toda a aposta se o débito é único.
5. Verificar e testar o alinhamento do ponteiro da roleta com o setor sorteado; correção visual em `mobile.css` V2.10.
6. Fazer testes automáticos de milhões de rodadas e comparar prémios e probabilidades, antes de considerar uma matemática finalizada.
7. A edição web usa somente créditos virtuais e **não** constitui um jogo certificado para dinheiro real.
