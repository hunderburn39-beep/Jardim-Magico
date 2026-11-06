# Jardim Mágico — Phaser V1

**Jogar em HTML5:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1

Novo motor **Phaser 3.90.0**, com interface artística 2.5D. O jogo original em `/index.html` e o antigo protótipo PlayCanvas em `/jardim-3d/` permanecem intactos.

## Estrutura

- `index.html`: nova máquina em HTML5, controls, cofres e painel de bónus.
- `css/estilo.css`: layout responsivo iPhone/Android, cenário contínuo, moldura original e interface ouro/veludo.
- `js/reels.js`: Phaser, símbolos HD, cinco rolos verticais, cascatas, partículas e recuperação.
- `js/engine.js`: regras e saldos virtuais independentes das animações.
- `js/app.js`: coordenação de giros/bónus, painéis e temporizadores de recuperação.
- `js/database.js`: IndexedDB local com fallback localStorage, histórico e exportação JSON.
- `js/audio.js`: efeitos originais de áudio, controlos liga/desliga.
- `tests/smoke.mjs`: regressões de prémios e animação antes da publicação.

## Visual e animação

Reutiliza os **símbolos HD originais** da imagem de referência, o logótipo ornamentado, a moldura fotográfica de madeira, flores e trepadeiras e uma imagem de cenário única. A moldura tem **interior transparente**, em vez de capturas coladas sobre as peças. Os símbolos são processados com corte e remoção suave do fundo escuro, sem inversão UV ou rotação em 3D.

Os **cinco tambores descem de cima para baixo**, fazem paragem por coluna e ao vencer animam em conjunto todas as peças da combinação. Nas cascatas, as sobreviventes mantêm a ordem e as novas descem do topo. Múltiplas combinações são somadas; peças partilhadas só explodem uma vez. Sem limite fixo de quedas: termina quando não existem novas linhas premiadas.

## Funções de demonstração

- Apostas de 0,05 até 10 créditos fictícios e botão de recarga demo (+25).
- Dez linhas de prémios, WILD, SCATTER, pedra sem valor e moeda.
- Free Spins com retriggers e multiplicadores.
- Hold & Win 4×4 (3 tentativas), compra voluntária de bónus com créditos fictícios.
- TURBO, AUTO 10/25/50 e pressão longa de 3 s no RODAR.
- Jackpots de referência MIN 250×, MED 500×, MAX 1000×, ULTRA 5000× exibidos como valores ilustrativos.
- Geração de resultados integralmente pagáveis pelo fundo fictício **antes** de os apresentar, sem cortar valores depois de saírem.
- Guardar sessão e histórico no dispositivo, exportar JSON e reiniciar a demo.
- Watchdog para recuperar animação travada sem cobrar novamente o giro.

## Limitações

**Não é uma máquina a dinheiro real**, e o RTP, jackpots progressivos reais e regras não estão auditados. A base IndexedDB é **local ao navegador**; não existe base de dados central, autenticação, permissões de operador ou sincronização de contas. Para produção são necessários backend seguro, mecanismo de resultados certificado, auditoria e cumprimento das normas aplicáveis.

A experiência visual e os FPS devem ser avaliados no Safari físico. Os testes de código passam em CI, mas não substituem testes num iPhone real.

Cópia de segurança do jogo anterior: `backup/jardim-antes-phaser-v1`.

## Fonte do motor

Phaser 3.90.0 — <https://phaser.io/download/release/v3.90.0>
