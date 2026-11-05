# Portões do Trovão — V4.0

Jogo independente de demonstração com créditos virtuais, inspirado no formato de slots de queda do Olimpo. **Não afiliado, licenciado ou certificado pela Pragmatic Play.** Sem depósitos, levantamentos ou prémios em dinheiro real.

## Jogar no navegador

https://hunderburn39-beep.github.io/Jardim-Magico/templo-do-trovao/

Funciona em desktop e telemóvel, na horizontal (16:9) ou vertical. O botão ⛶ tenta ativar ecrã completo onde o navegador permite.

## Estrutura de edição

- `index.html`: elementos e controlos da interface.
- `css/estilo.css`: layout base e responsividade.
- `css/referencia-v2.css`: molduras douradas, botões e tabela ilustrada.
- `css/efeitos-v3.css`: luz nas pedras, explosões, estilhaços, iluminação de símbolos vencedores e animações de queda.
- `css/fundo-v4.css`: grelha com fundo transparente, nuvens dinâmicas, neblina, feixes de luz, moldura, animação do logótipo e relâmpagos.
- `js/jogo.js`: geração dos símbolos, probabilidades, pagamentos, motor de sequências, efeitos e auto-spins.
- `js/audio.js`: motor Web Audio, sons sintetizados de rolagem, queda de peças, explosões, multiplicadores, SCATTER e grandes prémios; não requer ficheiros externos.
- `js/fundo-v4.js`: efeitos atmosféricos, partículas luminosas, intervalos de relâmpagos e reação visual aos prémios. Respeita preferências de menos movimento.
- `assets/simbolos.svg`: sprite de todos os símbolos, SCATTER e multiplicador.
- `assets/logotipo-v4.svg`: logótipo do jogo em SVG dourado.
- `assets/nuvens-v4.svg` e `assets/relampagos-v4.svg`: camadas ilustradas para animação do cenário.
- `assets/olimpo.svg`: cenário com templo, nuvens e raios.
- `assets/deus-trovao.svg`: personagem mitológica desenhada em vetor original.
- `assets/favicon.svg`: ícone do separador.

O jogo **não partilha** código nem valores com a slot Jardim Mágico. A publicação do repositório copia este diretório para `public/templo-do-trovao/`.

## Mecânicas da versão 4

1. Tabuleiro de 6 colunas e 5 linhas. 8 ou mais símbolos iguais pagam em qualquer posição do tabuleiro.
2. Símbolos premiados iluminam-se e explodem em estilhaços brilhantes; as peças sobreviventes descem da posição anterior e as novas entram por cima (tumble). O processo repete-se enquanto houver vitórias.
3. Tabela clássica 8–9, 10–11 e 12–30 símbolos; e prémio SCATTER com 4, 5 ou 6+.
4. Quatro ou mais SCATTER na mesma grelha, incluindo após quedas, ativam 15 rodadas grátis.
5. Três ou mais SCATTER durante rodadas grátis dão cinco rodadas adicionais.
6. Esferas multiplicadoras até 500×. Várias esferas são somadas na rodada vencedora. Nas rodadas grátis, multiplicadores de rodadas premiadas acumulam-se durante o bónus.
7. Compra de bónus com créditos **virtuais**, por 100× a aposta.
8. Auto-spins: premir GIRAR durante três segundos, escolher 10/25/50/100; tocar novamente em GIRAR para parar. Turbo, som opcional e reposição de créditos virtuais.
9. Limite de 5 000× a aposta por ciclo.
10. V4 fixa um limite técnico de segurança de 36 cascatas numa jogada, distinto da versão comercial, para evitar uma execução interminável.

## Alterar os gráficos e sons

- A moldura continua dourada; o interior da grelha é quase transparente e deixa ver o templo animado, com uma leve sombra para destacar os símbolos.
- Os raios aparecem em intervalos variáveis e reagem às sequências premiadas e à ativação do bónus.
- O logótipo está em `assets/logotipo-v4.svg`, editável como vetor, sem utilizar os logótipos comerciais das referências.
- As animações do fundo param com a preferência de acessibilidade para reduzir movimento.


- Desenho e cores individuais das pedras: `assets/simbolos.svg` (5 gemas facetadas e os outros 6 símbolos).
- Brilho, flash e explosão: `css/efeitos-v3.css` (regras `.cell`, `.burst-out`, `.crystal-shard`).
- Queda por gravidade: `render()` em `js/jogo.js`, classe `.drop-in` em `css/efeitos-v3.css`.
- Volume e timbres: `js/audio.js`; eventos `spin`, `drop`, `tumble`, `burst`, `reward`, `mult`, `scatter`, `bigwin`.
- O áudio é opcional, está ativado de início e só toca depois da primeira interação permitida pelo navegador.

## Alterar probabilidades e pagamentos

As constantes `WEIGHTS`, `MULT_WEIGHTS`, `BETS`, `PAY_TABLE` e os limiares de `newSymbol()` estão no topo de `js/jogo.js`. As regras de pagamento ficam em `getWins()`, `scatterMultiplier()` e `playSpin()`.

**Atenção:** a tabela é baseada na versão clássica, mas as probabilidades são próprias e não calibradas. O RTP real desta demonstração **não foi certificado** e não se deve apresentar como 96,5% ou qualquer outro valor comercial. Para futura utilização regulamentada seriam necessários RNG verificável, matemática estatística auditada, relatórios, controlos e licenciamento adequados.

## Referência das regras

- [Página oficial Gates of Olympus](https://www.pragmaticplay.com/en/games/gates-of-olympus/)
- [Página oficial Gates of Olympus 1000](https://www.pragmaticplay.com/pt/jogos/gates-of-olympus-1000/)

Todos os gráficos desta pasta são novos e não reutilizam imagens nem logótipos comerciais apresentados como referência.
