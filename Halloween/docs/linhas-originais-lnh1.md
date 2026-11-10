# Halloween 30 Linhas — descodificação do formato LNH1

Data da análise: 10 de novembro de 2026. Fonte: APK original fornecido para referência pessoal pelo utilizador.

## Resultado confirmado

O jogo inclui `01_linhas.bin` (47.370 bytes) e o atlas `01_linhas.webp` (1024×512). Foi possível reconstruir **as 30 imagens individuais de linhas**, sem adivinhar os seus desenhos.

A ordem gráfica original é:

- **Linhas 1–20:** traçados da esquerda para a direita.
- **Linhas 21–25:** cinco linhas verticais, uma por tambor.
- **Linhas 26–30:** mais cinco traçados da esquerda para a direita.

A ordenação anterior no motor Phaser (verticais 26–30) era incorreta e foi corrigida.

## Formato binário recuperado

Tudo é lido em **big-endian**:
- Cabeçalho: `LNH1`, largura do atlas `uint16`, altura do atlas `uint16`, número de linhas `uint16`.
- Para cada linha: largura da imagem `uint16`, altura da imagem `uint16`, número de blocos `uint16`.
- Para cada bloco: coordenadas de destino na imagem reconstruída (`x`, `y`, cada uma `uint16`), coordenadas de origem no atlas (`atlas_x`, `atlas_y`, cada uma `uint16`), largura e altura do bloco (`uint8`, `uint8`).

A reconstrução leu **30 linhas consecutivas**, consumiu exatamente os **47.370 bytes** e verificou que as regiões de origem correspondem a blocos do atlas. Os tamanhos são variáveis; por exemplo, as três primeiras linhas são imagens horizontais muito finas e as linhas 21–25 têm cerca de 33×333 px, coerente com trajetos verticais.

Foi gerada uma prancha privada de pré-visualização e um pacote com 30 imagens transparentes, para comparação visual com a aplicação Android.

## Limite do que a arte comprova

**As imagens e a sua ordem foram recuperadas.** Os valores de linha no motor Phaser continuam uma **interpretação discreta dos percursos** (linhas com símbolos nas posições 0, 1, 2). A posição exata a que cada imagem era sobreposta na grelha original e a sua tradução para um conjunto matemático de símbolos ainda exigem verificar o código de posicionamento Android.

Assim, o resultado não é uma certificação do RTP ou da tabela de prémios comercial. O público recebe apenas a lógica e a documentação; os ficheiros gráficos comerciais foram preservados em pacote pessoal.

## Consequências no projeto

- As cinco verticais entram no escalão **0,15 € / 25 linhas**, e continuam ativas em **0,20 € / 30 linhas**.
- As horizontais 26–30 só entram em **0,20 € / 30 linhas**.
- Os três caldeirões num tambor continuam a poder ativar um mini-bónus especial independentemente das linhas pagas ativas.
