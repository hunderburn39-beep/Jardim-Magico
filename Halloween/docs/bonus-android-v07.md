# Halloween III — Bónus encontrados no APK (V0.7)

Origem: XAPK `halloween-slots-30-linhas-6-68.xapk`, classe compilada `GameHalloween30L`. Análise estática em 09/11/2026, sem executar o APK.

## Confirmado no DEX

- **Morangos:** `InitMorango` faz 12 chamadas a `CreateMora` com índices **0–11**. `EscolheMorango` consulta `morangosEscolha`, `moraPrizeValues`, `moraPrizes`, `apBet` e `bonusToCall`. Não foi descodificada a relação completa entre prémio e aposta.
- **Caldeirões:** `InitCaldeirao` carrega `cal.webp` e os ficheiros `50.webp`, `75.webp`, `100.webp`, `150.webp`, `200.webp`, `250.webp`, `500.webp` e `1000.webp`. São **imagens com números**, não prova de créditos efetivos.
- **Roleta:** `01_roleta.webp` mostra 30 números. `NumRoleta` consulta campos de crédito, dificuldade e prémio e invoca `calcbonusRoleta`, pelo que a distribuição é mais complexa do que a roleta experimental Phaser.
- **Quatro tipos de bónus:** `CallBonus` contém os nomes `morango`, `caldeirao`, `roleta` e `dados`, chamando `ShowMorango`, `ShowCaldeirao`, `ShowRoleta` e `ShowDados`. O bónus de dados ainda não está implementado na recriação.
- **Dados de linhas:** o formato `LNH1` contém 30 no cabeçalho; as pequenas imagens `01_lin1c.webp` a `01_lin30c.webp` são **marcadores numerados**, não mapas dos percursos de linhas. Estes percursos não foram descodificados.

## Implementado na V0.7

- Ecrã de **12 morangos** com desenhos próprios, escolha por toque, prémio determinado antes da animação, persistência e prevenção de pagamentos repetidos.
- Caldeirões, morangos e roleta independentes, incluindo recuperação do bónus ao reabrir o jogo.
- Roleta de 30 números, dez símbolos e quatro acumulados virtuais. Valores, gatilhos e probabilidades **continuam experimentais e não são a matemática original da aplicação**.
- Gráficos e sons comerciais extraídos do XAPK permanecem fora do repositório público.

## Simulações

Cinco lotes determinísticos de 50.000 giros, aposta de dois créditos: retorno observado de **97,29%, 92,66%, 82,77%, 91,78% e 94,32%**, média simples **91,76%**. Isto não é RTP certificado nem previsão de retorno.

O desenvolvimento deve continuar com revisão de `bonusToCall`, `moraPrizeValues`, `NumRoleta` e `LNH1`, evitando atribuir ao APK regras que não foram demonstradas.
