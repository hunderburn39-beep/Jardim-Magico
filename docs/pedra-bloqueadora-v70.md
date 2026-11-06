# Jardim Mágico V70 — Pedra bloqueadora

A PEDRA é um símbolo neutro que ocupa uma casa normal nos 5 cilindros × 3 posições.

- Não tem valor (0 €), nem entra na tabela PAY ou nos jackpots.
- Não substitui nem é substituída pelo WILD. Não ativa SCATTER, MULTI, moedas, free spins ou Hold & Win.
- Numa das 15 linhas, uma PEDRA interrompe a contagem consecutiva da esquerda para a direita. Os símbolos seguintes não continuam a combinação anterior.
- Um par de WILDs continua válido apenas quando são duas casas adjacentes (nunca atravessa uma pedra).
- Não fica presa: desce normalmente durante as cascatas, ao contrário dos WILDs fixos. Não é eliminada por combinação porque não ganha.
- Probabilidade fixa por casa: **6%** em jogo base e free spins. A pedra usa a probabilidade retirada das figuras normais, sem alterar a probabilidade de WILD, SCATTER, bolas MULTI ou moedas.
- Igual nos modos Equilibrado e Prémios Mais Frequentes; independente do saldo, cofre e histórico.
- Ícone SVG em `assets/imagens/pedra-bloqueadora-v70.svg`, visível em Canvas 2D, PixiJS e na tabela de pagamentos.

Casos de referência: CORAÇÃO–CORAÇÃO–PEDRA–CORAÇÃO–CORAÇÃO não forma trio; CORAÇÃO–CORAÇÃO–CORAÇÃO–PEDRA paga só o trio; WILD–PEDRA–WILD não forma par. O limite de 5000× e o pagamento limitado pelo cofre mantêm-se.

**Nota:** adicionar uma peça bloqueadora reduz a frequência de combinações normais. A calibração anterior de retorno não deve ser assumida como validação estatística da V70; exige nova simulação.
