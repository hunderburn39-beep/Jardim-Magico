# Jardim Mágico — V5.0.0 Final

[Jogar em 9:16](https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=5.0.0)

Edição final demonstrativa, baseada na V4.3.3. Mantém os símbolos HD, a queda vertical, as cascatas e o motor de prémios aprovados. O Phaser 3.90.0 é servido pelo próprio repositório, com licença MIT e bytes conferidos com a distribuição oficial.

- Interface exclusivamente vertical, com botões maiores e texto mais legível em ecrãs de 320×568, 390×844 e 430×932.
- Administração protegida por PIN local: percentagens, modos, fundo e ajustes de créditos fictícios do jogador; movimentos registados no histórico.
- Gravações administrativas aguardam a transação e repõem os valores anteriores se falharem.
- Cópias de segurança verificam apostas, cêntimos, contadores e a cobertura dos bónus; dados locais inválidos bloqueiam novas jogadas sem apagar a sessão.
- Relatório imprimível com todos os movimentos conservados, pesquisa em português, exportação e restauro JSON.
- Painéis com foco por teclado, fundo inerte, texto acessível e preferência por movimento reduzido.
- Suspensão, rotação para paisagem e perda de WebGL interrompem novas jogadas e automáticos; resultados já gravados não são novamente cobrados nem pagos.

A matemática e as imagens aprovadas são verificadas por checksum em `release.json`. Os testes de regressão, testes adicionais e testes em Chromium/WebKit antecedem a publicação automática no GitHub Pages.

## Verificar

```sh
node jardim-phaser/tests/smoke.mjs
node jardim-phaser/tests/final.mjs
```

Os testes de navegador estão em `tests/visual.mjs` e `tests/release-browser.mjs`; o GitHub Actions prepara os navegadores e conserva capturas como artefactos da execução.

## Limites desta edição

Créditos exclusivamente fictícios. IndexedDB e PIN existem apenas no navegador atual, sem contas, pagamentos ou sincronização entre dispositivos. O histórico conserva os últimos 2500 movimentos; exporta periodicamente o JSON para conservar sessões. O relatório imprime o histórico disponível e não constitui contabilidade certificada. O PIN local não protege contra alguém com acesso aos dados ou ferramentas do navegador.

O RTP não é auditado nem garantido. MIN/MED/MAX/ULTRA são referências ilustrativas; esta edição mantém a mecânica da V4.3.3, incluindo o extra de 250× ao completar o Hold 4×4. WebKit automatizado não substitui testes físicos no Safari/iPhone nem medições de bateria, som ou FPS no aparelho.

<details>
<summary>Histórico das versões anteriores</summary>

# Jardim Mágico — Phaser V1

**Jogar em HTML5:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=5.0.0

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

## V2.6 — informações de Free Spins e Hold mais claras

- O HUD Free Spins mostra **restantes**, **realizadas** e **acumulado sem MULTI**; o MULTI mantém-se no extremo direito da barra principal, sem o apresentar duas vezes.
- O Hold 4×4 tem agora **barra visual das 16 casas** e contador de casas em falta. O texto das moedas novas mostra as tentativas restantes reais, não três fixas.
- O progresso usa os dados já calculados pelo motor; inclui etiquetas para leitores de ecrã e CSS para ecrãs baixos e movimento reduzido.
- Alteração apenas visual. Sem mudanças a prémios, RTP, cascatas, texturas ou pagamentos fictícios.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.6

## V2.7 — celebrações finais mais limpas e fluídas

- NICE, MEGA e SENSACIONAL apresentam o título, TOTAL e valor num formato maior e adaptado à largura do montante. Os bónus Free Spins e Hold & Win mantêm as próprias frases.
- Evita disparar celebrações em cada cascata e voltar a dispará-las quando o giro fecha: o destaque principal aparece ao terminar a jogada, reduzindo repaints e animações repetidas.
- Os valores individuais das combinações aparecem num pequeno resumo no rodapé dos tambores, limitado a três valores e um contador de restantes. O total exato continua na barra da queda, à esquerda. Não foi criado nenhum segundo indicador de MULTI.
- Animações CSS só com opacidade e transformações, duração mais curta no Turbo, tipografia compacta em ecrãs pequenos e adaptação a preferência por movimento reduzido. Não acrescenta imagens pesadas, filtros animados ou temporizadores por frame.
- Os cálculos, o motor de créditos, a grelha Phaser e as cascatas não foram alterados. Créditos apenas fictícios, sem RTP auditado.
- Testes automáticos verificam as versões de cache, valores de exibição, resumos, duração Turbo e ausência da informação repetida.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.7

## V2.8 — dimensionamento responsivo e Safari

- Novo CSS responsivo final `responsive-v28.css` para retrato de telemóveis, retrato de tablets e paisagem. Os estilos antigos continuam intactos, mas as correções V2.8 prevalecem quando necessário.
- **Em paisagem, o canvas dos tambores mantém sempre a proporção visual de 5:3**: deixou de ser forçado a `height:100%` com `aspect-ratio:auto`. O jogo mantém a grelha, a arte e as cascatas originais.
- Ecrãs baixos já não obrigam a altura mínima de 600px que podia cortar os controlos. Em casos extremos (retrato com menos de 500px de altura), é permitido fazer scroll para alcançar os botões em vez de os esconder.
- Tablets em retrato usam a largura do ecrã com zonas dimensionadas pela altura disponível, sem esticar o tabuleiro.
- Margens seguras de iPhone/notch/gestos nos 4 lados; fontes e botões adaptados a larguras estreitas, valores grandes dos jackpots e orientação horizontal baixa. O viewport deixa de bloquear a ampliação manual do browser.
- A barra de dois valores (queda à esquerda, MULTI à direita) e as celebrações V2.7 mantêm-se sem duplicação. A matemática dos prémios, motor Phaser, persistência e RTP não auditado não foram alterados.
- Os testes automáticos verificam os breakpoints e a proporção CSS, mas a geometria visual deve ser confirmada em Safari real e Android nos diversos tamanhos de ecrã.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.8

## V2.9 — FPS adaptativo e limpeza dos efeitos temporários

- O medidor de fluidez continua a observar frames reais durante as animações; **suspende a medição quando o navegador vai para segundo plano** e retoma sem interpretar a interrupção do Safari como FPS baixo. A medição não altera os resultados.
- Quando a renderização apresenta lentidão sustentada, a máquina ativa automaticamente `lite-effects`: as faíscas e halos cosméticos ficam mais leves, enquanto símbolos, rolos, cascatas e prémios são preservados. Volta à qualidade original após três janelas de FPS estável.
- O motor Phaser passa a registar **halos e faíscas temporários** e a destruí-los quando uma animação é interrompida e a grelha é recuperada. Assim evitam-se objetos gráficos esquecidos após timeouts.
- Em modo leve, a quantidade de faíscas é reduzida proporcionalmente às peças premiadas, mantendo sempre o brilho e a animação base de cada símbolo.
- Os efeitos decorativos do fundo deixam de usar filtros intensivos durante os giros. No diagnóstico local, o utilizador vê se estão ativos os efeitos de alta qualidade ou o modo leve, sem PIN e sem dados privados no relatório.
- Os testes incluem recuperação de FPS, suspensão/retoma, orçamento de partículas e limpeza de efeitos. **Motor matemático, fundos fictícios, RTP não auditado, jogo original e aparência normal não foram alterados.**

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=2.9

## V3.0 — estabilidade e testes de regressão ampliados

- Introduzido um supervisor de transições **apenas visuais**, que termina imediatamente a espera quando o Safari fica em segundo plano ou a página é fechada. A grelha guardada é reconstruída pelo Phaser sem recalcular a jogada ou atribuir novos prémios.
- Quando o jogo está oculto, deixam de iniciar-se novas animações de eliminação, cascatas e giros automáticos. No regresso, a posse de escrita e os bónus são retomados através dos mecanismos exclusivos já existentes (V2.2–2.4).
- Cada transição monitorizada é terminada com sucesso, erro, interrupção ou timeout; temporizadores são libertados, e resoluções tardias não voltam a executar recuperação. Os diagnósticos indicam se há transição Phaser pendente.
- Testes de regressão forçam interrupções e timeouts com promessas simuladas e executam 660 rondas determinísticas mistas (3 perfis) com giros, Free Spins, Hold & Win e verificações de pagamentos únicos e reserva fictícia não negativa.
- Mantidos os gráficos, a barra da V2.5 (queda à esquerda, MULTI à direita), o modelo matemático de demonstração, as cascatas 5×3 e os bónus. Sem dinheiro real e sem RTP certificado.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.0

## V3.1 — fila de giros, pausa dos bónus e sessão no Safari

- Uma única fila `turn-queue-v31.js` agenda o próximo giro base, Free Spin ou abertura do Hold & Win. Em cada categoria existe no máximo um temporizador, cancelado antes de uma nova marcação. O botão de rodar, o turbo e as compras continuam a usar o mesmo motor de demonstração.
- Ao colocar o Safari em segundo plano, a fila é anulada. As Free Spins passam explicitamente para **pausadas**, com botão **RETOMAR FREE SPINS** no regresso; nunca avançam de forma silenciosa.
- Um giro não começa enquanto o Hold & Win está a guardar/mostrar o resultado. A série automática depois do Hold só é marcada **após** terminar a escrita e libertar o bloqueio de bónus.
- Corrigida a corrida entre libertar a posse IndexedDB e voltar à aplicação: a nova aquisição da sessão aguarda a libertação anterior. Se o navegador se ocultar durante uma operação, a aba liberta a posse ao terminar com segurança.
- O diagnóstico local indica se existe próximo giro agendado; testes automáticos verificam o cancelamento/reativação e os modos de bónus, além das simulações anteriores.
- Mantida a grelha Phaser 5×3, a barra com valor da queda à esquerda e MULTI à direita, todas as cascatas, os gráficos e os cálculos de créditos fictícios. Não é uma máquina com dinheiro real nem RTP auditado.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.1

## V3.2 — recuperação e histórico consistentes

- As cópias de segurança JSON passam a obter **saldo e eventos na mesma transação readonly IndexedDB**. Não se misturam leituras de giros diferentes. Mantém-se o formato de cópia `jardim-magico-demo-v3`.
- Cada novo giro e tentativa Hold & Win guarda uma ligação `recoveryId` ao recibo de recuperação, na transação existente (não acrescenta qualquer crédito, pagamento nem movimento adicional).
- O painel de histórico mostra as ligações ao recibo e uma **verificação informativa e não destrutiva** da coerência entre saldo, reserva e último movimento. Cópias antigas sem `recoveryId` continuam compatíveis.
- Restauro validado com recibos opcionais; os testes cobrem leitura conjunta, ligação aos recibos, discrepâncias e duplicados.
- Motor matemático, RTP não auditado, gráficos Phaser, cascatas e barra de dois valores mantêm-se intactos.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.2

## V3.4 — símbolos botânicos e moldura ilustrada

- O jogo usa **seis novas ilustrações SVG originais**: caracol (ID interno poção), joaninha (esmeralda), flor azul (coração), flor vermelha (rosa), WILD dourado mágico e pedra rochosa cinzenta. Os restantes símbolos mantêm a arte HD antiga; **índices, pesos, linhas, valores e todas as regras matemáticas permanecem inalterados**.
- Novo aro dourado com ramos verdes e flores roxas na grelha 5×3 e novo painel decorativo de cabeçalho, ambos com centro transparente, sem roubar espaço ao Phaser e mantendo toques nos controlos.
- O Phaser apresenta **×1…×5 sobre cada WILD premiado**, com os seus valores já calculados pelo motor antes de iniciar o giro. Os números desaparecem quando começa uma cascata, são mostrados novamente para as novas peças e são libertados em caso de interrupção.
- O indicador MULTI à direita da barra ganha um brilho curto quando o acumulado sobe; o prémio mostrado por queda continua a corresponder ao multiplicador usado no cálculo. Estilos respeitam `prefers-reduced-motion` e modos de desempenho leve.
- Os assets permanecem independentes em `assets/imagens/jm34-*.svg`, o código de renderização em `jardim-phaser/js/reels.js` e o estilo final em `jardim-phaser/css/bosque-v34.css`. Preparado para ajustes posteriores sem alterar a banca de demonstração.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.4

## V3.5 — cenário premium ajustado às duas referências fornecidas

- O cenário visível passa a usar o fundo imersivo já incluído no projeto `jm80-cenario-imersivo.svg`, com a profundidade do bosque e o lago até ao fundo. Não é uma captura estática: os tambores e todos os botões continuam reais e animados.
- No retrato normal, o ecrã distribui-se entre **logótipo maior**, quatro jackpots, barra de queda/MULTI, tabuleiro 5×3 com moldura florida, linha de estado, painéis de saldo e aposta, grande botão RODAR verde e TURBO dourado. Os tamanhos são proporcionais à janela; em ecrãs muito baixos e paisagem mantém-se um layout compacto acessível.
- Foram reforçados acabamentos de madeira/dourado, púrpura profundo, letras legíveis e brilhos subtis. O botão de rodar ganhou pequenas flores ornamentais e mantém o foco/touch nativo; as bordas são decorativas e nunca capturam toques.
- Continuam os símbolos selecionados: **caracol, joaninha, pedra realista, flor vermelha, flor azul e WILD**. Sem alterar os IDs de símbolos, pesos, linhas ou resultados. Os WILD premiados mostram os seus valores individuais e o MULTI soma entre quedas, tal como V3.4.
- Preservados IndexedDB, Free Spins, Hold & Win, RTP não auditado, os créditos virtuais e todos os mecanismos de recuperação; os testes de regressão verificam os recursos e as regiões responsivas.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.5

## V3.6 — apenas na vertical (9:16) e zoom por duplo toque desativado

- A aplicação Phaser só apresenta o jogo vertical. A antiga regra de dimensionamento horizontal foi retirada do tema premium; se o telemóvel rodar, é mostrado um aviso de orientação e o toque nos controlos fica bloqueado até regressar ao retrato.
- Em Safari e outros navegadores, a viewport da **página do jogo** define `maximum-scale=1,user-scalable=no`, com `touch-action:manipulation` no painel e botões e um bloqueio `dblclick` para impedir zoom por duplo toque. O canvas Phaser conserva `touch-action:none` para captar as interações do jogo.
- Corrigida uma regra antiga que escondia a seta e o texto **RODAR** no botão verde. A interface continua com os ícones aprovados, valores e lógica de créditos demonstrativos.
- Os testes automatizados cobrem 390×844, 864×1536 e 360×640 em retrato, o duplo clique e a mensagem de rotação quando o telefone está na horizontal. Nenhuma otimização ou suporte de interface 16:9 será feito nas etapas seguintes.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.6

## V3.7 — moldura bem encaixada e símbolos em destaque (apenas 9:16)

- A moldura botânica deixa de ser esticada por toda a área disponível: fica centrada sobre o tabuleiro, conservando a proporção original 1200:760 e a grelha Phaser 5×3.
- Caracol, joaninha, flores azul e vermelha, pedra e WILD ocupam mais espaço na respetiva célula; não há alteração dos IDs, taxas, resultados nem sequências dos prémios.
- O valor individual de cada WILD é agora uma etiqueta violeta/dourada no canto superior direito da sua própria célula; as etiquetas desaparecem no início da cascata e na recuperação após interrupção.
- Melhor legibilidade dos jackpots, do valor da queda, da barra MULTI e da moldura, com luzes menos intrusivas. O jogo continua exclusivamente vertical e sem zoom por duplo toque.
- Testes de navegador em 390×844, 864×1536 e 360×640 verificam a proporção da moldura, símbolos, botões e ausência de sobreposição.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.7

## V3.8 — textura natural, cor e profundidade nas peças escolhidas

- Novos SVGs independentes `assets/imagens/jm38-*.svg` para os seis símbolos aprovados. A concha do **caracol** ganhou estrias, grão, reflexos e detalhes na pele; a **joaninha** ganhou brilho de carapaça, patas definidas e superfície pontilhada.
- As duas **flores (vermelha e azul)** ganharam nervuras finas, gotículas, pólen e estames; a **pedra** ganhou textura de granito, fissuras e desgaste; o **WILD** ganhou veludo/púrpura texturado, brilho dourado e pontos mágicos.
- O fundo dos tambores ganhou profundidade verde-escura e linhas separadoras douradas, aproximando a leitura das peças do estilo de bosque da referência. A moldura continua proporcional e os multiplicadores individuais mantêm-se no canto de cada WILD.
- Os filtros dos SVGs são processados apenas na **rasterização inicial Phaser**, não em cada quadro de animação, para preservar a fluidez no iPhone.
- A nova arte foi integrada por caminhos versionados e mantém o estilo vertical 9:16, os bónus, os giros e os valores matemáticos anteriores. Continuam a ser créditos fictícios sem RTP auditado.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.8

## V3.9 — texturas com volume pré-rasterizado (apenas 9:16)

- Caracol, joaninha, flor azul e vermelha e pedra continuam a ser os símbolos escolhidos. Foram acrescentadas **sombras em relevo e reflexos próprios** em texturas 512×512, incorporados **uma única vez** ao carregar o Phaser, sem sprites ou filtros extra nas cascatas.
- O **WILD fotográfico dourado existente na biblioteca** substitui o WILD mais plano quando disponível; a arte anterior mantém-se como fallback. A imagem do WILD **não** inclui um × fixo, pelo que os valores ×1–×5 continuam individuais e animados sobre o símbolo quando há prémio.
- Ajustes discretos de contraste para destacar a arte sobre o verde profundo, sem alterar posições nem linhas do tabuleiro. A moldura V3.7 e o bloqueio de zoom da V3.6 mantêm-se.
- Os testes verificam que a composição de luz/sombra só acontece na fase de criação das texturas, os símbolos escolhidos existem, o WILD HD está carregado e os três ecrãs verticais continuam navegáveis.
- Sem alterações ao motor de prémios, à reserva de demonstração, aos Free Spins, Hold & Win, RTP ou saldos. Créditos fictícios e RTP não auditado.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.9

## V3.9.1 — giro normal ligeiramente mais demorado

- No giro normal, cada tambor percorre **mais uma volta completa** dos cinco símbolos circulantes, com 20, 25, 30, 35 e 40 posições sucessivas em vez de 15, 20, 25, 30 e 35.
- O primeiro tambor gira aproximadamente **1,78 segundos** e o último termina em cerca de **2,54 segundos** (antes 1,47 a 2,19 segundos); o abrandamento usa `Sine.easeOut` para a rotação continuar perceptível durante mais tempo.
- O **Turbo não muda**: mantém as durações e a aceleração anteriores. Os símbolos do resultado continuam a entrar fisicamente no topo na última volta, sem serem trocados quando o tambor pára.
- Testes de regressão e de navegador verificam o tempo e a combinação final em 9:16. **Nenhum cálculo de RTP, reserva, aposta, Free Spins, Hold & Win, multiplicador ou prémios foi alterado.**

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=3.9.1

## V4.0 — arte HD para os restantes símbolos (9:16)

- O bule, o cristal, o cogumelo, o chapéu e o SCATTER passam a usar a respetiva fonte HD individual com sombras/reflexos pré-combinados. A folha 3×3 anterior continua como fallback caso algum ficheiro falhe.
- Foi criada a **moeda roxa 2× com aro dourado**, em SVG 512×512, preparada uma vez pelo Phaser sem efeitos por frame.
- Os seis símbolos aprovados mantêm-se inalterados. Os restantes ganharam ligeiramente mais dimensão e acabamentos próprios, sem tapar os prémios.
- Testes acrescentados para os doze símbolos e para a seleção das cinco fontes HD. Motor matemático, giro aprovado da V3.9.1, 9:16, multiplicadores e bónus não alterados.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.0

## V4.1 — brilho, dissipação e quedas mais fluidas

- Quando há vitória com nova cascata, cada peça premiada ganha um **brilho breve**, dissolve-se suavemente para cima e libertam-se até **3 partículas por peça** (apenas uma no modo leve). Símbolos repetidos por várias linhas só desaparecem uma vez.
- Quando é a última combinação da spin, os símbolos **apenas brilham e permanecem**; não desaparecem sem que haja novas peças para entrar.
- A gravidade visual passou a ter um ligeiro desfasamento entre colunas e uma **aterragem elástica discreta** nas peças novas. Turbo e modo leve eliminam o ressalto, mantendo transições curtas. Um pequeno brilho no fundo identifica as colunas que receberam novas peças, no máximo cinco efeitos.
- O Phaser continua a recuperar o tabuleiro após qualquer interrupção: halos e partículas transitórios são destruídos, sem repetir a aposta. Sem efeitos adicionais por frame, sem som novo e sem alterações ao RNG, prémios, multiplicadores, Free Spins, Hold & Win ou reserva da demonstração.
- Testes Node e navegador confirmam a remoção única, combinações corretas, alinhamento das peças e manutenção do jogo vertical **9:16** e do giro normal V3.9.1.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.1

## V4.2 — moedas com valores e porquinho que cresce e rebenta

- Os WILD premiados e moedas especiais do tabuleiro passam a mostrar **moedas roxas/douradas com montantes** calculados a partir de `aposta × MULTI corrente` (por exemplo, aposta 0,20 × 3 = 0,60 créditos fictícios). O desenho antigo da moeda de valores não contém ×2 nem outro valor fixo: o Phaser apresenta o valor certo dinamicamente a cada queda. Estes montantes são representação visual, não pagamentos base extra.
- No Hold & Win 4×4, cada moeda apresenta o seu **valor efetivo** (aposta × multiplicador da moeda), em vez de ×1, ×2, ×3. O total continua a ser a soma integral das moedas guardadas e só é atribuído no final; recuperação de sessão mostra os mesmos valores.
- O porquinho deixa de mostrar dinheiro e texto de cofre: só a figura dourada aparece no painel, aumenta de tamanho à medida que os giros de demonstração avançam e **rebenta visualmente a cada 20 giros**, voltando ao tamanho inicial para começar outro ciclo. Não paga prémios ao rebentar, não altera saldo/reserva nem promete valores de prémio.
- O botão do porquinho mantém acesso à administração com PIN, mas o ecrã público nunca apresenta o valor interno da reserva no porquinho. Mantidas as funções de administração e cópias de segurança.
- CSS apenas vertical 9:16, animações reduzidas para quem prefere menos movimento. Testes asseguram a fórmula, limpeza de moedas ao continuar a cascata e ausência de alteração à matemática do motor.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2

## V4.2.1 — moedas em euros, por cada multiplicador individual

- As moedas passaram a mostrar sempre a forma **0,20€**, incluindo a vírgula e o símbolo € junto ao número.
- O valor mínimo da moeda é **a aposta ativa ×1**: aposta 0,20€ → 0,20€; aposta 0,10€ → 0,10€.
- Multiplicadores individuais geram valores **×2, ×3, ×4** e superiores: por exemplo, com aposta 0,20€, as moedas ×2, ×3 e ×4 apresentam 0,40€, 0,60€ e 0,80€; com aposta 0,10€, apresentam 0,20€, 0,30€ e 0,40€.
- O WILD usa o **seu próprio fator ×1…×5**, sem aplicar o multiplicador acumulado outra vez a cada moeda. As moedas normais da grelha, que não têm fator atribuído pelo motor, mostram sempre ×1. No **Hold & Win**, os valores mostrados correspondem ao fator já atribuído a cada moeda e o cálculo do prémio é o mesmo.
- Apenas formato visual: símbolo € indica o valor nominal dos créditos de demonstração e **não representa dinheiro levantável**. Sem alterações ao motor, aos resultados, ao cofre ou ao porquinho.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.1

**Correção visual complementar V4.2.1:** a moeda original do tambor passou a carregar a arte sem o “2×” impresso. Os valores em € são recolocados quando o jogo inicia, após uma jogada, na recuperação da sessão e imediatamente ao mudar a aposta. Nenhum fator é pago duas vezes.

## V4.2.2 — moedas em € durante todo o giro

- Corrigida a origem do problema da moeda: o desenho em **euros é integrado na própria textura do símbolo antes de o tambor começar a rodar**. A moeda entra pela parte de cima e atravessa a grelha já com **0,20€** (aposta 0,20€) ou **0,10€** (aposta 0,10€), sem revelação tardia.
- Cada aposta gera uma textura de moeda transparente e cacheada em `jardim-phaser/js/coin-face-v422.js`; o próprio sprite do Phaser usa essa textura no giro e na cascata. O tamanho do número ajusta-se para apostas até 10€ e os textos mantêm vírgula e símbolo €.
- O valor **×1** está associado à moeda comum, tal como a lógica atual; multiplicadores individuais **×2, ×3, ×4, ×5** continuam nos WILDs premiados e nas moedas do Hold & Win. Não são inventados multiplicadores adicionais para as moedas normais, nem criados pagamentos extra.
- Quando a aposta muda, os símbolos de moeda já presentes na grelha mudam imediatamente de textura. Quando a jogada termina, **não aparece uma segunda moeda ou texto sobre a mesma peça**.
- Formato 9:16, visual do porquinho, giro aprovado, cálculos, saldos e bónus mantidos. Testes verificam o texto pintado na textura 512×512, a troca de aposta e o uso do símbolo na animação.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.2

## V4.2.3 — fluidez e estabilidade móvel

- Evitada a recriação da moeda em euros quando a aposta não muda. A textura anterior é libertada depois de atualizar os símbolos, reduzindo consumo de memória.
- Evitadas chamadas redundantes a setTexture e setScale. A queda vertical, símbolos HD e animações de cascata permanecem exatamente com os mesmos perfis.
- As animações decorativas do fundo pausam durante o giro nos ecrãs táteis, sem alterar as peças.
- Safari BFCache: a aplicação reinicia o controlo periódico de sessão ao regressar e liberta a exclusividade ao sair, mesmo se pagehide chegar antes de document.hidden.
- O contexto WebGL perdido suspende novos giros. Depois de restaurado, o tabuleiro é reposto a partir do estado guardado. Sem alterações aos resultados e pagamentos fictícios.
- Matemática, apostas, reserva, Free Spins, Hold & Win e históricos inalterados. CI e testes físicos do navegador continuam recomendados.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.3

## V4.2.4 — estabilidade no regresso ao jogo

- Os giros e efeitos visuais param quando o Safari envia `pagehide` ou quando o WebGL perde contexto, mesmo antes de o documento ficar oficialmente oculto.
- As cascatas não tentam continuar a criar WILDs ou partículas quando os gráficos estão suspensos; o resultado da jogada já guardado é preservado, sem novo prémio.
- O tabuleiro e moedas só são reconstruídos depois de o renderizador estar disponível. Rodar, aposta e automático ficam desativados durante a suspensão e são novamente disponibilizados após a recuperação.
- As operações em conclusão libertam a sessão de escrita na base local, e o regresso ao jogo revalida a sessão exclusiva.
- Não altera os símbolos, a queda aprovada, os tempos, os efeitos normais, o saldo, Free Spins, Hold & Win nem a matemática. Os testes automatizados não substituem um teste no iPhone real.

**Jogar:** https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.4

## V4.2.5 — prevenção de novas cascatas após falha visual

- Se a animação do giro não concluir, nenhuma nova explosão ou queda visual é iniciada. O resultado já persistido é recuperado na grelha final.
- Se a explosão ou uma cascata falhar, a sequência visual é interrompida nesse ponto, sem repetir peças nem pagamentos de demonstração.
- Quando existe uma falha visual, os giros automáticos param e, se existirem Free Spins, ficam pausadas até o jogador retomar conscientemente pelo controlo de sessão.
- A recuperação comunica o resultado preservado, em vez de continuar automaticamente a apostar num navegador possivelmente instável.
- Intactos os movimentos, tempos e estilos originais dos tambores, das cascatas, do WILD e das moedas, assim como o RTP/resultado, Hold & Win, créditos e cofre fictício.
- Testes automáticos de regressão atualizados; interrupções físicas no Safari requerem confirmação no aparelho.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.5

## V4.2.6 — regresso seguro sem saltos de grelha/saldo

- Safari e Android: o regresso de uma página suspensa revalida a base local sem aplicar snapshots de saldo ou recriar os rolos a meio da jogada.
- A lease de 22 segundos continua a renovar-se durante giros longos. Quando a jogada terminar, qualquer recarga pendente é concluída antes de agendar nova aposta automática.
- O código volta a confirmar a visibilidade do jogo depois de aguardar operações assíncronas do IndexedDB; a sessão é libertada se a página tiver sido suspensa.
- Inclui regressões de código e um teste no navegador para simular pagehide/pageshow mantendo saldo e aposta. A experiência física no Safari deve ser confirmada.
- Matemática, reserva, Free Spins, Hold & Win, símbolos, moedas em €, imagem HD e movimentos da queda mantidos.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.6

## V4.2.7 — segurança na reconciliação da sessão

- Apostas, giros automáticos, compras de bónus, recargas de créditos DEMO, tentativas Hold e retomas de Free Spins aguardam a revalidação do acesso à base local.
- Os botões bloqueiam temporariamente se o IndexedDB estiver a carregar a sessão ou a libertar uma aba, evitando alterações sobre um saldo antigo após regressar do Safari.
- Uma jogada já guardada não é interrompida, repetida nem recalculada; o bloqueio aplica-se somente a ações novas.
- Testes unitários dos seis estados de sessão, além de teste no navegador de botões bloqueados após pagehide. O teste físico no Safari continua aconselhável.
- Formato 9:16, peças HD, moedas em euros, queda V3.9.1, cascatas V4.1 e regras de prémios sem alterações.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.7

## V4.2.8 — fila de giros sincronizada com sessão local

- Os giros automáticos e as Free Spins só podem ser agendados quando o IndexedDB já confirmou a posse da sessão. A verificação repete-se quando o temporizador dispara, evitando iniciar jogadas sobre um saldo em reconciliação.
- Ao concluir uma alteração de créditos ou compra de bónus, a aplicação prioriza a verificação da sessão, antes de avançar para novas Free Spins.
- Ao concluir uma tentativa Hold & Win, a interface bloqueia novas tentativas caso a sessão ainda esteja a ser revalidada.
- Testes de regressão verificam o estado da fila em modo base e Free Spins com sessão pendente, ocultação, libertação da lease e retorno à normalidade.
- Sem alterações à queda, cascata, símbolos HD, moedas em euros, prémios, RTP ou saldo já registado. Continua recomendado confirmar suspensões reais no iPhone.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.8

## V4.2.9 — retoma sem duplicação de giros automáticos

- Depois de confirmar a posse do IndexedDB e sincronizar o saldo, a fila volta a agendar **uma única vez** o próximo giro automático base que faltava. Antes desta versão, esta rotina só retomava Free Spins.
- A mesma regra cobre Free Spins não pausadas, e não duplica um temporizador já pendente. Se o jogador parou o automático, se a Free Spin foi pausada, ou se há aviso de recuperação aberto, não inicia nova aposta.
- O bloqueio V4.2.7/V4.2.8 continua ativo: sem confirmação da sessão, nunca há novo giro.
- Testes de regressão de estados base/free, pausa, modal, perda de lease, falta de giros e fila já agendada. Perfil 9:16, gráficos HD e física vertical mantidos.
- **Nenhuma alteração** ao motor de sorteio, prémios fictícios, reserva, WILD, SCATTER, Free Spins/ Hold & Win, moedas em € nem movimento aprovado dos rolos e cascatas.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.2.9

## V4.3.0 — reconciliação de sessão sem perder pedidos concorrentes

- Se o navegador Safari/iPhone regressar enquanto uma verificação do IndexedDB já está em curso, o novo pedido de validação fica assinalado e é executado de novo quando a primeira verificação terminar. Não depende de aguardar o próximo heartbeat periódico.
- A verificação extra só arranca com a página visível, o motor disponível e nenhuma operação administrativa, Hold & Win ou giro em curso. Pedidos simultâneos não criam um ciclo de tentativas em caso de erro de base de dados.
- O painel de sessão informa «A confirmar os créditos fictícios guardados…» durante a reconciliação e impede retomar Free Spins antes da confirmação.
- A fila V4.2.9 continua a retomar apenas os giros restantes, sem duplicar resultados nem autorizar novas apostas enquanto o escritor não estiver validado.
- Testes de regressão da fila de revalidação e da interface pagehide/pageshow em três formatos; adicionalmente, permanece recomendado o teste real num iPhone.
- **Intactos** símbolos HD, grelha vertical 9:16, movimento de rolos e cascatas, ganhos, apostas, RTP, Free Spins, Hold & Win e cofre virtual.

Jogar V4.3.0: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.3.0

## V4.3.1 — restauro WebGL sem interromper animações aprovadas

- A perda de contexto suspende novas ações e assinala uma reposição gráfica pendente. Se o contexto voltar durante o giro, a grelha só é reconstruída no fim da animação.
- Nas Free Spins, uma perda gráfica pausa a série, mesmo entre giros. O regresso do renderizador não inicia outra Free Spin sem intervenção do jogador.
- A recuperação pode terminar após contextrestored, fim do giro, pageshow ou inicialização, e continua a respeitar a sessão da base local.
- Testes automáticos para estados ocupado, oculto, contexto perdido e contexto restaurado; teste visual com interrupção/restauro WebGL simulado e confirmação de saldo inalterado.
- Não altera o sorteio, RTP, pagamentos, reservas, WILD/SCATTER, Free Spins, Hold & Win, artes HD ou movimento dos tambores/cascatas.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.3.1

## V4.3.2 — proteger a recuperação visual em segundo plano

- Ao ocultar a página, suspender o navegador ou perder o WebGL, um efeito interrompido deixa de tentar reconstruir sprites numa cena que não está disponível.
- A grelha final já guardada é reconstruída quando a sessão volta ao primeiro plano e a animação termina. Não existe novo sorteio nem um segundo pagamento.
- O checkpoint da sessão e o restauro gráfico esperam pela reposição pendente antes de apresentar a grelha. Free Spins interrompidas continuam a aguardar uma retoma explícita.
- Testes unitários para recuperação visual normal, ecrã oculto, página suspensa e perda de contexto; testes visuais 9:16, BFCache e WebGL simulados.
- Não se altera a física da queda, arte HD, cascatas, RTP, apostas, prémios, WILD, SCATTER, Hold & Win ou cofre virtual.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.3.2

## V4.3.3 — recuperação gráfica periódica sem bloquear o jogo

- Corrigida a situação em que uma tentativa transitória de reconstrução de sprites WebGL podia deixar os giros indefinidamente bloqueados: a verificação periódica de sessão também tenta concluir o restauro pendente.
- Nova tentativa de reconstrução apenas com o ecrã visível, contexto gráfico disponível, e sem giro nem tentativa Hold & Win em curso. Não recarrega nem volta a premiar uma jogada.
- Quando a máquina aguarda WebGL, o painel informa «A recuperar os gráficos do Jardim Mágico…», oculta a ação de retoma e mantém os botões de aposta bloqueados.
- Testes novos para página oculta, context lost, pagehide, giro e Hold em execução, restauração concluída e aviso visível em navegador real.
- A queda, as peças HD, as cascatas, os símbolos, apostas, RTP fictício, Free Spins e Hold & Win permanecem aprovados e inalterados.

Jogar: https://hunderburn39-beep.github.io/Jardim-Magico/jardim-phaser/?v=4.3.3

</details>
