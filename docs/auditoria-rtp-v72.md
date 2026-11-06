# Jardim Mágico V72 — Auditoria RTP e fundo

Data: 6 de novembro de 2026. Créditos fictícios, sem RTP certificado.

## Correção V72

Antes, o motor atribuía pelo menos 1 cêntimo por **cada** linha vencedora. Isso distorcia os prémios, sobretudo nas apostas de 0,05 €. Agora soma o valor bruto das linhas por queda e só depois arredonda o resultado, preservando a cascata quando a queda tem valor subcêntimo.

Calibração fixa, independente do saldo do fundo:
- Equilibrado: base 0,580; Free Spins 0,069.
- Prémios frequentes: base 0,600; Free Spins 0,065.
- Hold & Win gratuito: 0,250; comprado: 1,330.
- Compras: 15 Free Spins por 20× aposta; Hold & Win por 100× aposta.
- Pedra: 6% por posição, sem prémio, interrompe sequências; jackpot e WILD inalterados.

## Exemplos de auditoria

Usou-se o simulador publicado em `scripts/auditar-rtp-v72.cjs`, com 45.000 giros base por cenário, semente 489031, aposta 0,20 €, fundo inicial 5000×, incluindo as compras e os prémios dos jackpots:

| Cenário | Equilibrado | Frequentes |
|---|---:|---:|
| Sem compras | 93,45% | 97,84% |
| Compras moderadas (1% Free Spins e 0,2% Hold por giro) | 92,15% | 92,89% |
| Compras intensas (10% Free Spins e 1% Hold por giro) | 95,38% | 91,23% |

Em outro teste, com 165.000 giros base por modo, compras moderadas e semente 489031, observaram-se 96,16% (equilibrado) e 96,61% (frequentes). A dispersão é grande: **96% é um alvo aproximado, não uma garantia**.

Para reproduzir com Node.js: `node scripts/auditar-rtp-v72.cjs --spins 30000 --stake 20 --seed 23812`. A aposta é indicada em cêntimos.

## Gestão do fundo

O painel privado permite colocar ou recuperar capital fictício do operador e regista os movimentos, separados dos depósitos dos jogadores, da reserva da banca e dos jackpots.

- Reserva mínima de recuperação: **500× a aposta atual**.
- Referência de liquidez: **2500× a aposta atual**.
- Cobertura de um prémio máximo individual: **5000× a aposta**.

Exemplo: à aposta de 0,20 €, o mínimo é 100 €, a referência é 500 € e a cobertura de 5000× exige 1000 €. Para aposta de 10 €, os valores são 5000 €, 25.000 € e 50.000 €.

O cofre limita os pagamentos se faltar dinheiro fictício; aumentar o fundo **não altera probabilidades nem torna os prémios mais frequentes**. Este projeto não está preparado para operações com dinheiro real.
