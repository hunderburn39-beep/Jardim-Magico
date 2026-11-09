# Halloween III — dados: evidências do APK e reconstrução V0.8

Análise estática em 09/11/2026 de `halloween-slots-30-linhas-6-68.xapk` (`com.r7games.slotsaga`), sem executar a app. Desenvolvimento em `Halloween/` do repositório.

## Confirmado no Android

- Em `assets/halloween/01_dado.webp` existe um atlas **1024×1152 RGBA**, com dados vermelhos de diferentes faces e ângulos (8 colunas por várias linhas); estes recursos comerciais não foram importados para o GitHub.
- A classe Dalvik `GameHalloween30L` contém `InitDados`, `EscolheDado`, `NumDado`, `SetDado`, `SetDadoPremio`, `ExitDados` e `ShowDados`.
- `InitDados` instancia/associa **cinco animações de dados** (`dadoAnim`), e usa o atlas `dado.webp`. `EscolheDado` desativa novas escolhas e referencia um modificador (`dadoModifier`); `NumDado` consulta o prémio e o gerador de números.
- Os tamanhos e relações de chamada de Dalvik confirmam a existência do bónus, **não revelam uma tabela certificada** para escolher dados ou calcular os pagamentos.

## Implementado na V0.8

- **Cinco dados vermelhos redesenhados como gráficos Phaser**, posicionados numa janela em formato vertical 9:16. Cada dado representa uma escolha.
- O motor `pendingDice` guarda cinco faces inteiras de 1 a 6; os resultados são determinados ao ativar o bónus e gravados antes de abrir a interface.
- `chooseDice` aceita apenas os índices 0–4, paga uma única vez, devolve a face e as restantes escolhas e elimina o estado pendente.
- Sair e voltar a abrir o navegador recupera os dados sem duplicar pagamentos; o motor recusa novos giros enquanto qualquer bónus estiver pendente.
- Nesta versão **experimental**, três ou mais caldeirões podem ativar um de três bónus escolhidos probabilisticamente: dados 15%, morangos 25%, caldeirões 60%. O prémio dos dados é `aposta × face`, com faces 1–6. **Estes pesos e valores são criações do projeto; não foram extraídos do APK.**
- A roleta de 30 números pode estar pendente ao mesmo tempo que um dos mini-jogos; só é revelada depois de a primeira escolha ser concluída.
- Todos os recursos de áudio e imagem comerciais do APK permanecem fora do repositório.

## Ensaios de regressão da economia ilustrativa

Em cinco lotes reprodutíveis de 50.000 giros, aposta virtual de 2 créditos e 30 linhas, contando linhas, free spins e todos os bónus:

| Semente | Dados | Morangos | Caldeirões | Roletas | Retorno observado |
|---|---:|---:|---:|---:|---:|
| 20261109 | 93 | 182 | 433 | 112 | 86,21% |
| 89 | 89 | 168 | 413 | 138 | 113,43% |
| 12345 | 116 | 134 | 388 | 133 | 100,98% |
| 987654321 | 92 | 148 | 405 | 137 | 91,25% |
| 777777 | 101 | 194 | 413 | 130 | 96,37% |

Média aritmética dos cinco lotes **97,65%**. Um lote passou de 100% — isto é esperado em amostras com prémios raros e elevados. Não é RTP certificado, não representa a matemática original nem garante equilíbrio de resultados futuros.

## Ainda por investigar

Interpretar o fluxo de `NumDado`, `SetDadoPremio` e `EscolheDado`, incluindo probabilidades, número de animações jogáveis e interação com acumulados; identificar regras da base de dados proprietária e diferenças entre Halloween Clássica, 25L e 30L.
