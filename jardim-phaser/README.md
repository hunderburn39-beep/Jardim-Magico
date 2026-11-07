# Jardim Mágico — Phaser V1

**Jogar em HTML5:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.5

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

## V1.1 — Tambor contínuo sem troca de símbolo na paragem

Corrigido o defeito em que o tambor terminava a rotação e só depois substituía as imagens pelas figuras do prémio. Cada uma das três imagens finais passa a ser escolhida quando **entra pelo topo no último ciclo da rotação**. A partir daí, desce naturalmente até à posição vencedora, sem alteração de textura no evento `onComplete`. A imagem e o prémio continuam definidos antecipadamente pelo motor de créditos fictícios.

O teste `tests/smoke.mjs` simula a rolagem por frames e verifica para as **cinco colunas** que as imagens finais já são as corretas **antes da paragem**, e que a paragem não muda nenhuma figura. Mantém os testes de cascatas, cofre, Free Spins, Hold e armazenamento.

## V1.2 — Prémios e efeitos, sem alterar a queda aprovada

A animação de rotação vertical e a entrada das imagens finais pelo topo permanecem **iguais à V1.1**.

- As figuras que ganham iluminam-se **simultaneamente** com halos dourados, anéis e pequenos fragmentos coloridos, usando tweens nativos de Phaser.
- A mesma peça em duas linhas premiadas tem **um único efeito**, preservando o valor das duas combinações.
- Só as peças que vão ser substituídas pela cascata se dissipam; na queda final sem nova combinação as peças apenas brilham e continuam visíveis, sem desaparecer e regressar abruptamente.
- O valor **de cada queda** surge sozinho, em grande e dourado, sem caixa, e desaparece suavemente.
- O número de partículas é limitado de acordo com o número de peças vencedoras para proteger a fluidez no iPhone.
- A legenda respeita o multiplicador WILD quando aplicável, e as Free Spins apresentam a soma base por queda, creditando e anunciando o prémio acumulado apenas no fim.
- O teste automático verifica duplicados de peças premiadas, brilho sem desaparecimento, a apresentação do valor da queda e que os cinco tambores continuam a parar com as peças já visíveis **sem troca na paragem**.

Cópia de segurança anterior: `backup/jardim-phaser-v1-1-antes-premios-v1-2`.

## V1.3 — Acabamento visual fiel à arte original

- **Símbolos fotográficos:** os nove símbolos normais são recortados da folha original `assets/imagens/jm75-simbolos.webp`, na ordem correta de três colunas e três linhas. Preservam diretamente as cores, a iluminação e os reflexos do ficheiro original, sem a dessaturação do recorte anterior.
- **Bordas dos símbolos:** as células são convertidas uma única vez em texturas Phaser de 512×512, com um desvanecimento radial apenas na orla; não aparecem quadrados escuros à volta dos objetos. SCATTER, pedra e moeda mantêm as suas texturas próprias.
- **Moldura:** a moldura de madeira e flores continua a utilizar `jm75-moldura.webp`, com o centro recortado para mostrar os tambores. A máscara foi ajustada à espessura das quatro bordas, de modo a reduzir as zonas pretas da captura.
- **Painéis originais:** jackpots, barra de multiplicadores, saldo, prémio, compra de bónus, TURBO e botão central RODAR passam a reutilizar as imagens gráficas da referência (em vez de apenas gradientes genéricos).
- **Proteção da animação:** as funções de rotação e cascata são rigorosamente iguais às da V1.2, que conservava a queda aprovada pelo utilizador. O prémio e a banca também não mudaram.
- **Testes:** o CI confirma a sequência 3×3 do atlas, recortes sem troca de posições, fallback quando faltar textura, estética dos painéis e os anteriores testes da animação e das regras.

É ainda uma demonstração HTML5 com créditos fictícios e IndexedDB local. Não substitui uma validação visual no Safari físico nem auditoria de RTP.

## V1.4 — Bosque mais imersivo e interface mais luxuosa

- Fundo original reforçado com iluminação suave para preservar o ambiente mágico.
- Flores, cristais e lago da fotografia `jm75-lago.webp` reaproveitados apenas nos extremos inferiores, evitando sobrepor as imagens do botão RODAR/TURBO que já estão nessa fotografia.
- Oito pequenos pontos de luz animados lentamente por CSS, com `pointer-events:none` e desativação em modo `prefers-reduced-motion`.
- Novo `assets/porquinho-dourado.svg` com gradientes originais, brilho e sombras no Cofre Mágico, substituindo o emoji do protótipo.
- Melhor relevo, reflexos, brilho e legibilidade nos jackpots, multiplicadores, moldura, saldo, aposta e botões, sem introduzir novas caixas por cima das peças.
- CSS em ficheiro independente `css/polimento-v14.css`, para facilitar alterações ou reverter rapidamente sem afetar a versão aprovada.
- **Motor e animações de giro e cascata não foram modificados**: a queda e o efeito dos prémios permanecem exatamente como na V1.3. Não houve alteração ao motor de pagamentos, ao cofre virtual ou à base IndexedDB.
- Testes automáticos confirmam os novos elementos e a ausência de bloqueio de toques; falta avaliação visual num iPhone físico.

Cópia de segurança anterior: `backup/jardim-phaser-v1-3-antes-polimento-v1-4`.

## V1.5 — Free Spins e Hold & Win com apresentação premium

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1.6

- **Free Spins:** barra discreta por cima dos rolos com giros restantes, valor acumulado e multiplicador. Animação de abertura/reativação e anúncio distinto do prémio integral no final da série.
- **Hold & Win:** novo painel dourado e violeta com grelha de 16 casas, moedas douradas com animação de queda, contador de moedas, indicadores visíveis para as três tentativas e valor total do bónus.
- As moedas que já estavam na grelha **não reaparecem nem animam de novo** a cada tentativa: só são animadas as moedas novas indicadas pelo motor.
- Os créditos fictícios e o estado final são **gravados antes da animação de prémio**, reduzindo o risco de duplicação após atualização da página.
- O botão de jogar moedas fica bloqueado durante cada animação para evitar múltiplas tentativas acidentais.
- No final do Hold é exibido o prémio integral do motor, incluindo o extra fictício da grelha completa, quando ocorrer.
- Ficheiros novos `css/bonus-v15.css` e `js/bonus-presentation.js`, com testes próprios de moedas novas, contador de tentativas e valores. Acessibilidade: efeitos reduzidos quando o dispositivo pede menos movimento.
- **Os arquivos `js/reels.js`, `js/engine.js`, `js/database.js` e `js/audio.js` permanecem intactos**, preservando integralmente a queda aprovada e a matemática da versão anterior.

A máquina continua uma demonstração de **créditos fictícios**, com histórico IndexedDB apenas local e RTP não auditado. A publicação e os testes automáticos não substituem teste visual num iPhone físico.

Cópia de segurança anterior: `backup/jardim-phaser-v1-4-antes-bonus-v1-5`.

## V1.6 — Prémios reposicionados e multiplicadores na mesma barra

- **Celebração mais acima:** o texto de vitória aparece em três linhas — `NICE`, `MEGA` ou `SENSACIONAL` (ou o nome do bónus), a palavra **TOTAL** e, por baixo, **o valor integral do prémio**.
- **Barra dos multiplicadores:** o lado esquerdo apresenta o **valor desta queda**; na conclusão passa a mostrar **TOTAL DO GIRO**. Os orbs dos multiplicadores ficam imediatamente ao lado e à direita aparece o fator efetivo **×1 a ×5**.
- Nas Free Spins os círculos obtidos vão-se acumulando visualmente (até três visíveis), mas o fator à direita reflete sempre o **multiplicador real do motor**. Um giro com várias quedas premiadas que contenham Wild incrementa o acumulado apenas uma vez, conforme a regra de Free Spins.
- O valor da queda deixou de aparecer como um número gigante no centro das peças. Agora fica na barra dourada e mantém-se até à queda seguinte ou ao fecho do giro.
- As animações `NICE/MEGA/SENSACIONAL` sobem um pouco e são apresentadas num conjunto vertical com **frase → TOTAL → valor**. No fim das Free Spins não são apresentadas duas janelas de celebração em simultâneo.
- A apresentação está num novo ficheiro `css/premios-v16.css`, mantendo intactos os painéis e moldura artísticos da V1.4.
- **Não foram alterados** `js/reels.js`, `js/engine.js`, `js/database.js` ou `js/audio.js`. A queda de peças aprovada, a banca fictícia, os bónus e o histórico permanecem intactos.
- Os testes automáticos verificam a ordem visual dos componentes, o número de linhas da celebração, a legibilidade e o histórico dos multiplicadores.

Backup da V1.5: `backup/jardim-phaser-v1-5-antes-layout-premios-v1-6`.

## V1.7 — fluidez móvel, Safari e ecrãs compactos

- Mantida **integralmente** a lógica do motor de resultados, cofre fictício, Free Spins, Hold & Win, atlas artístico e animação vertical/cascatas aprovada da V1.6.
- Um observador leve de frames no ecrã deteta quedas persistentes de fluidez durante o giro e reduz **apenas** os filtros/efeitos decorativos do cenário (nunca a animação das peças nem o resultado). Após três janelas de frames estáveis recupera os efeitos completos.
- As oito luzes decorativas de fundo ficam pausadas enquanto os tambores estão a girar, reduzindo trabalho de composição na GPU. O modo é sempre libertado em `finally`, mesmo após uma recuperação de animação.
- O Safari não inicia novos giros enquanto o documento está oculto; mantém a retoma das Free Spins quando regressa ao ecrã.
- Ajustes de CSS em `css/mobile-v17.css` para altura de ecrã inferior a 670px, incluindo retrato compacto e paisagem baixa, preservando o encaixe do botão RODAR e painéis.
- Os testes `tests/smoke.mjs` verificam a deteção de frames lentos, paragem segura, tamanho compacto e que os scripts novos são carregados. **É necessário confirmar os FPS e o encaixe num iPhone físico**, pois os testes automatizados não substituem essa verificação.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1.7

## V1.8 — Administração local (créditos fictícios)

- Botão ⚙ abre resumo do dono, comerciante, resultado virtual e cofre.
- Dono e comerciante têm percentagens editáveis e botões GRAVAR; a soma é sempre 100%. Quotas históricas permanecem no evento, e as antigas usam 50/50.
- Adição e retirada de fundo virtual ficam registadas; movimentação bloqueada durante bónus.
- Histórico guarda acessos à administração, alterações, movimentos, giros e compras. O resumo separa apostas e compras dos prémios; movimentos de cofre não contam como receitas.
- Relatório imprimível para guardar como PDF pelo navegador (até 250 linhas de eventos recentes; resumo de todo histórico local). Exportação JSON preservada.
- Não há autenticação segura, backend central, dinheiro real ou RTP auditado. A rotação aprovada Phaser e a matemática não foram alteradas.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1.8

## V1.9 — Histórico pesquisável e restauro local

- Histórico de até 2500 eventos apresentados **por data**, agora com pesquisa, filtro por categoria e intervalo de datas e páginas de 20 movimentos. A base IndexedDB passa a eliminar as entradas mais antigas quando excede 2550 registos, preservando o limite dos 2500 mais recentes; a cópia JSON inclui todos os registos mantidos.
- Exportação JSON inclui sessão, tabuleiro, percentagens e histórico da instalação, com espera pelas operações pendentes antes de exportar.
- **Restaurar cópia JSON** valida modo, saldo fictício, tabuleiro 5×3, Hold 4×4, quotas administrativas e eventos antes de mostrar confirmação explícita. O restauro substitui o estado e o histórico de uma só vez, com transação IndexedDB (ou escrita única em armazenamento local). Cópias inválidas não substituem dados existentes. Não é sincronização entre dispositivos: o utilizador transporta o ficheiro manualmente.
- Mantidos intactos `js/engine.js` e `js/reels.js`: regras, probabilidades, cofre e sequência visual das cascatas são os mesmos da V1.8.
- Sem dinheiro real, sem conta de operador, sem backend central ou proteção contra manipulação local. As verificações automatizadas não substituem testes num Safari físico.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=1.9

## V2.0 — Três perfis de demonstração e gravação resistente

- A administração ganha o seletor **Prémios / Normal / Entretenimento** com botão GRAVAR. O modo atual aparece sempre no rodapé e é guardado no histórico e nas cópias JSON; o normal é o perfil por defeito.
- Os perfis afetam apenas a **preferência por combinações/ativação iniciais nos giros base**; as peças são selecionadas antes da animação, e a cadeia continua integralmente validada contra o fundo fictício. O perfil **Normal** conserva exatamente a distribuição original. As Free Spins continuam com a geração original sem diferenças por perfil.
- Esta parametrização **não implementa nem garante margens de 30%, 50% ou 70%** nem quaisquer percentagens de RTP: são perfis lúdicos sem retorno auditado e sem dinheiro real.
- Ao falhar uma gravação local, o giro é cancelado **antes** de animar, revertendo o estado volátil e parando o automático; a compra de bónus, a tentativa de Hold e a recarga fictícia também voltam ao estado anterior quando a gravação falha.
- A validação de restauro aceita o novo perfil e rejeita nomes de modo inválidos. Foram adicionados testes estatísticos básicos da frequência dos eventos por perfil, além dos regressivos dos tambores.
- A animação de rotação/cascata e as texturas em `js/reels.js` permanecem intactas. A base continua local ao navegador, sem autenticação real nem sincronização central.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.0

## V2.1 — PIN local e camada de armazenamento

- Ao abrir ⚙ Administração ou Histórico pede um PIN de 6 algarismos. No primeiro acesso é criado e confirmado um PIN para esta instalação, nunca há PIN de fábrica.
- Hash PBKDF2/SHA256 com sal aleatório e 180000 iterações; o PIN não é guardado em claro nem incluído no JSON. Quatro erros seguidos bloqueiam por 45 segundos. O acesso expira em oito minutos de inatividade, permite alterar PIN e fechar manualmente e é bloqueado ao ocultar o navegador.
- O cofre deixou de acrescentar 500 créditos fictícios sem passar pela administração. Operações de fundo, percentagens, modo, relatório, histórico e restauro exigem sessão local desbloqueada.
- **Limite importante:** este PIN é só uma barreira contra acesso casual à demonstração, não autenticação segura de operador. O utilizador com acesso ao código ou à base do navegador pode contorná-lo. Sem WebCrypto ou armazenamento local o painel não abre.
- Criada `js/storage-gateway-v21.js`, porta da interface para o driver local IndexedDB/localStorage. A futura arquitetura de backend está descrita em `docs/ARQUITETURA_BACKEND_V21.md`; **não há base central nem servidor instalado**.
- Sem alterações aos tambores de Phaser, às cascatas nem à matemática dos prémios. O jogo continua a trabalhar exclusivamente com créditos fictícios.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.1

## V2.2 — Exclusividade entre abas e recuperação após interrupção

- Cada aba obtém uma **posse exclusiva de escrita (lease)**, com aquisição, renovação e verificação atómicas na base IndexedDB. Apenas a aba proprietária pode atualizar o saldo/histórico; todas as outras ficam em modo de leitura. Depois de fechar uma aba, outra pode assumir a sessão e carrega os saldos guardados, sem reutilizar um estado antigo. A lease expira após 22 segundos se a aba morrer, com verificação periódica a cada 5 segundos.
- Se o browser não suportar IndexedDB ou abrir em ambiente em que não funciona, a interface fica **só de leitura**, em vez de permitir dois escritores em localStorage. É uma salvaguarda apenas desta instalação; não existe sincronização entre diferentes dispositivos nem autoridade de servidor.
- Antes da animação, cada giro já era guardado com resultado e prémio. A V2.2 acrescenta um `pendingVisual` (recibo de animação), retirado por checkpoint silencioso no final do giro. Se o Safari fechar antes do fim, no regresso a máquina coloca o tabuleiro final já gravado, informa que a animação foi interrompida e **não volta a pagar nem a cobrar o giro**.
- As operações de restauro e reset são bloqueadas para abas que não detêm o escritor. O reset preserva o registo da lease enquanto a aba estiver ativa.
- A queda Phaser e as regras dos prémios não foram alteradas; a sessão continua com créditos fictícios. Testes automáticos cobrem as condições de concorrência do algoritmo e os checkpoints, mas um teste real com duas abas Safari deve confirmar o comportamento visual.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.2

Nota adicional V2.2: ao ocultar uma aba sem giro em curso, a posse de escrita é libertada, permitindo que outra aba a adquira. A renovação em segundo plano durante uma animação mantém-se para impedir que outra aba reescreva o mesmo resultado a meio do giro.

## V2.3 — recuperação dos bónus e diagnóstico

- Recibos visuais `pendingVisual` agora identificam quando a animação interrompida era um giro base, uma Free Spin ou uma tentativa de Hold & Win, incluindo estado, valor e, no Hold, grelha 4×4. **O pagamento continua a ser gravado ANTES dos efeitos**: recuperar só lê o valor já creditado e não paga novamente.
- Ao reabrir uma Free Spin interrompida com giros pendentes, a interface mostra o total, multiplicador e contador guardados e apresenta botão **RETOMAR FREE SPINS**, em vez de avançar silenciosamente. O Hold em curso reabre a grelha e mostra moedas e tentativas restantes; o Hold já concluído apresenta o valor recebido numa mensagem de recuperação.
- O recibo é retirado através de um checkpoint local independente da contagem de giros, depois da animação. Uma falha de checkpoint não altera valores já guardados.
- Em **Regras → Diagnóstico local**, a demo mostra o modo da base, posse de escrita, estado de bónus, falhas e recuperações técnicas. É possível exportar JSON de diagnóstico sem PIN, sem saldos ou dados pessoais; contadores e últimos 70 eventos ficam só em memória nesta sessão.
- Novos testes de regressão validam recibos e recuperação dos dois bónus, resumos e limites dos diagnósticos. O motor `engine.js`, os rolos `reels.js` e os assets continuam inalterados.
- Continua a ser uma demo de créditos fictícios, sem base remota nem RTP auditado; a recuperação e o aspeto devem ser confirmados em Safari real.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.3

**Recuperação visual do Hold concluído:** o painel apresenta também a grelha final de 16 posições com as moedas já registadas. Não inicia novamente o sorteio nem volta a pagar o valor.

## V2.4 — continuidade dos bónus e ações mais seguras no telemóvel

- Adicionado um aviso pequeno de estado de sessão, com botão para rever a gravação ou tentar recuperar acesso exclusivo se outra aba estiver ativa. O aviso respeita as margens seguras do ecrã e não altera os rolos do jogo.
- **Free Spins recuperadas** exigem carregar explicitamente em RETOMAR FREE SPINS; fechar a janela por X ou fora não arranca novas jogadas. Continua disponível um botão de retomada.
- Uma gravação de aposta, recarga ou compra de bónus bloqueia temporariamente giros e operações concorrentes até terminar. Se falhar, os dados voláteis são repostos e aparece um aviso de erro local.
- O fecho da aba já não liberta prematuramente a posse de escrita durante uma operação pendente. Toques longos cancelados no botão RODAR deixam de bloquear o toque seguinte.
- CSS adaptado ao Safari e aos controlos táteis. **O motor de prémios, rolos Phaser e gráficos originais não foram alterados.** Continua a ser uma demonstração sem dinheiro real ou RTP certificado.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.4

## V2.5 — Barra de prémios simplificada

- Retirado **todo o bloco central “MULTIS ACUMULADOS”** e as bolas de multiplicadores redundantes. A barra passa a mostrar exclusivamente **valor da queda à esquerda** e **MULTI com o fator efetivo à direita**.
- Reorganizado o CSS responsivo para duas colunas com espaço confortável em 9:16 e 16:9.
- O JavaScript atualiza só `#multiTotal`; não depende de `#orbs` nem desenha valores repetidos. As contagens internas e o cálculo do multiplicador são os mesmos da V2.4, sem mexer no motor Phaser, nas cascatas ou no cálculo dos prémios.
- Atualizados os testes automatizados para verificar que a informação duplicada não regressa.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.5
