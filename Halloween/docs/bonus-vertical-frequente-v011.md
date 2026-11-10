# Halloween III V0.11 — frequência de bónus verticais (modo recreativo)

**Esta versão usa créditos virtuais e mecânicas experimentais.** Não corresponde ao cálculo de prémios nem às probabilidades certificadas do APK comercial.

## Falha corrigida

Na V0.10, o símbolo caldeirão tinha peso 4 em 117, cerca de 3,42% por posição. O gatilho principal exigia três caldeirões empilhados num tambor e o alternativo cinco dispersos. Numa simulação determinística de 50.000 giros, apenas **14 mini-bónus** surgiram (10 verticais e quatro dispersos), média de **um por 2.528 giros pagos**. Isto causava sessões inteiras sem qualquer bónus.

## Correção recreativa

O motor guarda `bonusDrySpins` no mesmo estado persistido do jogo, contando giros **pagos** sem mini-bónus:

- Antes de oito giros de espera, os resultados são os do sorteio normal.
- A partir de oito, há 3,5% de probabilidade adicional de um bónus vertical por giro pago, que passa para 7% a partir de 20 e 12% a partir de 30.
- Aos 48 giros pagos acumulados sem mini-bónus, o próximo giro pago cria uma coluna de três caldeirões (até 49 giros pagos sem mini-bónus).
- A coluna é escolhida aleatoriamente entre tambores livres, sem modificar tambores travados.
- Os resultados são definidos **antes** de atribuir créditos e guardados com a grelha. O motor usa os mesmos mecanismos de Free Spins e escolha de bónus.
- O contador regressa a zero quando um mini-bónus é ativado. Free Spins não avançam a contagem e não recebem esta combinação artificial.
- Ao recuperar uma sessão antiga, o contador é inicializado até 25 giros com base no número de jogadas registadas, sem repor saldo, aposta, bónus pendentes ou histórico.

Esta mecânica é intencionalmente **mais recreativa** e não reproduz o gerador de números, as probabilidades ou as restrições do APK original.

## Ensaios

Em cinco simulações determinísticas, uma por escalão de aposta, com 15.000 giros cada e resolução de todos os bónus pendentes, a média observada ficou perto de **um mini-bónus em cada 25 giros pagos**, com praticamente todos os mini-bónus ativados por três caldeirões na vertical. O retorno observado nos mesmos lotes variou bastante com o escalão e acumulados, **não é RTP certificado** e não garante créditos ou prémios.

Os testes de regressão incluem ativação até ao limite de espera, integridade das retenções dos tambores, proteção dos Free Spins, recuperação do contador e ausência de milhares de giros sem mini-bónus.
