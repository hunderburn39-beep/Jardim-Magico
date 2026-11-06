# Jardim Mágico V71 — RTP, compras voluntárias e gestão do fundo

6 de novembro de 2026. Jogo demonstrativo de créditos fictícios. **Alvo** de longo prazo: RTP aproximado de 96% quando a margem é 4%, com o conjunto de jogo base, Free Spins, Hold & Win e jackpots. **Não é RTP certificado nem garantido.**

## Coeficientes efetivamente publicados

| Regra | Equilibrado | Prémios mais frequentes |
|---|---:|---:|
| Base | 0,570 | 0,550 |
| Free Spins gratuitas e compradas | 0,068 | 0,059 |
| Hold & Win oferecido | 0,250 | 0,250 |
| Hold & Win comprado | 1,330 | 1,330 |

Preços: compra das **15 Free Spins por 20× a aposta**, com até duas extensões, ou **Hold & Win por 100× a aposta**. A Pedra tem 6% por casa, valor zero e interrompe sequências. Permanecem 15 linhas, WILD de 1× a 5×, até 12 cascatas, teto de 5000× aposta por prémio e quatro jackpots progressivos alimentados com 3% das apostas.

## Compras: testes Monte Carlo ao motor, aposta de 0,20 €

Foi reproduzida a geração do tabuleiro, a avaliação das linhas, as cascatas com WILD fixos, bolas multiplicadoras, redrop, extensões de free spins e a grelha Hold & Win. Amostras com sementes pseudoaleatórias fixas; os resultados são uma **estimativa de prémio potencial**, não RTP real com fundos limitados.

| Compra voluntária | Amostras | Preço | Prémio médio potencial | Retorno potencial |
|---|---:|---:|---:|---:|
| Free Spins · Equilibrado | 18 000 | 4,00 € | 3,75 € | **93,63%** |
| Free Spins · Frequentes | 18 000 | 4,00 € | 3,75 € | **93,79%** |
| Hold & Win · dinheiro de moedas e multiplicadores | 45 000 | 20,00 € | 18,56 € | **92,79%** |

**Importante:** na simulação Hold, o retorno indicado inclui somente moedas e multiplicadores, não os jackpots. O Hold pode atribuir jackpots, mas **só até ao montante efetivamente guardado no respetivo fundo progressivo**. Os jackpots recebem até 3% do preço da compra, de forma separada. Em compras exclusivamente de Free Spins, os prémios de jackpots não são garantidos nem se deve somar automaticamente 3% ao retorno das compras.

Estas amostras não medem ainda um RTP global certificado da máquina com todos os perfis de aposta, compras, saldos iniciais, variação da reserva e eventos raros. O cofre limita os prémios efetivamente pagos e o retorno observado pode ser menor. Os coeficientes definidos não reagem ao histórico, à identidade do jogador ou aos saldos do operador. Margens diferentes de 4% afastam-se da calibração de referência.

## Gestão do capital do operador

O painel privado permite colocar ou recuperar créditos virtuais do **cofre de prémios**. Não são depósitos dos jogadores nem transferências monetárias reais. A recuperação pede o PIN de depósito e confirmação, é bloqueada durante rodadas e bónus e preserva pelo menos 250× a aposta selecionada. Não movimenta a reserva protegida, o saldo dos jogadores ou os quatro fundos de jackpots.

O painel e o relatório mostram o cofre atual, capital colocado, capital recuperado, montante ainda por recuperar, eventual excesso recuperado e disponibilidade de retirada. O histórico inclui data, movimento, saldo anterior e posterior, guardados no navegador.

**Referência para liquidez recreativa:** 1000× a aposta escolhida (200 € para 0,20 €, 10 000 € para 10 €). **Cobertura do limite de um prémio individual de 5000×:** 1000 € para 0,20 € e 50 000 € para 10 €. Estes valores não são garantias de RTP nem de prémios múltiplos. Uma aposta maior sem reforçar o cofre diminui a cobertura relativa.

## Limitações

O jogo usa JavaScript local, localStorage e palavras-passe visíveis no código. Não oferece segurança transacional, fiscalização ou auditoria independente. As probabilidades e o RTP não podem ser tratados como certificados para dinheiro real. A capacidade de pagamento está condicionada ao cofre e aos fundos individuais dos jackpots.
