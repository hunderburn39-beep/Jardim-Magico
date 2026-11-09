# Jardim Mágico V5.0.0 — resultado e validação

Edição final demonstrativa desenvolvida a partir da V4.3.3. [Jogar](https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=5.0.0).

## Resultado

A interface vertical tem controlos maiores, números legíveis, enquadramento para ecrãs compactos e navegação por teclado nos painéis. O Phaser 3.90.0 acompanha o jogo; a sua distribuição foi comparada com o objeto oficial `eae45486b1e634facb1f65fc4ee40ad5695750fc` e inclui a licença MIT.

As alterações do fundo, saldo fictício do jogador, percentagens e modos ficam bloqueadas enquanto aguardam a transação. Uma falha repõe o valor anterior. O histórico distingue os movimentos em português e os relatórios imprimem todos os registos conservados. Importações e sessões locais são verificadas antes de permitir novas jogadas, incluindo a cobertura das Free Spins e moedas Hold.

O motor de prémios, rolos, movimento, cascatas e arte aprovados estão preservados por checksum, documentados em [`release.json`](../release.json). As regras conservadas estão em [MECANICAS_V5.md](MECANICAS_V5.md).

## Testes

A [execução de referência aprovada](https://github.com/hunderburn39-beep/Jardim-Magico/actions/runs/42545043857) verificou a aplicação final em Chromium e WebKit. A publicação em `main` repete os testes antes de disponibilizar a página.

| Área | Verificação |
| --- | --- |
| Regressão Phaser | 9.100 giros por execução; linhas, WILD, SCATTER, cascatas, banca, filas e recuperação |
| Verificações adicionais | 78.818 asserções; semente fixa; 12.089 giros, 12.282 tentativas Hold e 200 séries Free Spins |
| Fundo | Cenários de fundo zero, baixo e elevado; grelha Hold completa; pagamento único; saldos não negativos |
| Arte e queda | Doze texturas HD, moeda ligada à aposta, efeitos e tempos de rotação; nenhuma troca de peça na paragem |
| Ecrãs | 320×568, 360×640, 390×844, 430×932 e referência 864×1536; sem overflow horizontal; paisagem bloqueada |
| Navegadores | Percursos em Chromium e WebKit: aposta, recarga, giro, reload, PIN, percentagens, fundo, créditos, relatório, histórico, bónus e múltiplas abas |
| Dados | Exportação/restauro JSON e rejeição de cópias incoerentes; rollback de falha de escrita; recibos de pagamento |
| Interrupções | Sinais simulados BFCache/pagehide/pageshow e perda/restauro de WebGL, sem novo débito ou prémio duplicado |
| Compatibilidade | Testes do antigo protótipo 3D continuam a passar; os restantes jogos do repositório são preservados |

Os testes de navegador conservam capturas no artefacto `jardim-magico-v5-testes` do GitHub Actions. Os cenários de interface usam resultados fixos para conferir custos e pagamentos; os testes do motor exercitam as combinações, bónus e fundos com distribuições variáveis.

## Limitações

- Créditos exclusivamente fictícios; sem pagamentos, levantamentos reais ou contas de utilizador.
- Base e PIN locais ao navegador; sem servidor central, sincronização entre dispositivos ou autenticação segura de operador.
- Histórico limitado aos últimos 2500 movimentos. Os totais do relatório abrangem esse histórico disponível.
- RTP sem certificação ou garantia. Os jackpots do topo são ilustrativos; o Hold conserva o extra de 250× da V4.3.3.
- Os testes WebKit não representam um iPhone físico. Suspensão real do iOS, consumo de bateria, qualidade do som e FPS sustentados no aparelho continuam por confirmar.
- O relatório é HTML imprimível: a impressão física e a opção Guardar como PDF dependem do diálogo do navegador. O teste confirma o conteúdo e a invocação da impressão, sem operar a impressora.

![Jardim Mágico V5.0.0](jardim-v5-preview.jpg)
