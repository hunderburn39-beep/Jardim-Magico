# Pantanal — mapa do APK original 2.06

Origem: pacote Android de R7 Developers, `com.r7games.slotpantanal`, versão 2.06.
Importação isolada no ramo `pantanal-apk-206-import`. **A aplicação web da branch `main` não foi substituída.**

## Material importado

- `Pantanal-Original-2.06.apk`: pacote Android original da versão descarregada (guardado explicitamente, apesar do `.gitignore` global).
- `ficheiros/`: conteúdo integral de **254 entradas do APK** (inclui 218 imagens, 10 áudios, `classes.dex`, `AndroidManifest.xml`, recursos e ficheiros de assinatura).
- `jadx/sources/`: **2 598 ficheiros Java reconstruídos** por descompilação de `classes.dex`. São uma aproximação legível, **não o projeto-fonte original**.
- `inventario.json`: caminhos, tamanhos, categorias e SHA-256 de todas as entradas.
- `SHA256SUMS.txt`: verificações individuais de integridade.
- `RESULTADO_JADX.txt`: contagem de ficheiros reconstruídos.

## Pontos confirmados diretamente no motor original

`jadx/sources/com/r7games/slotpantanal/GeneratorNew.java` inclui os métodos `Generate`, `Calcpre1`, `faixa`, `travaMin`, `travaAcum` e `downDificult`.

- As **linhas 1–20** são trajetos de cinco posições nos tambores.
- As **linhas 21–25** são **verticais de três símbolos**, uma por tambor. Constam expressamente de `Calcpre1(21,1,2,3,0,0)` até `Calcpre1(25,13,14,15,0,0)`.
- O gerador contém campos `dado`, `roleta`, `caldeirao` e `Morango`. O código das interfaces e ações fica disponível em `MainActivity.java`.
- O motor usa dados de aposta, linhas, estado de acumulação, dificuldade e bloqueio. **Ainda é necessária análise e validação funcional antes de transplantar essas regras para JavaScript.**

## Gráficos originais a consultar

Consultar em `ficheiros/assets/` as imagens `05_tabela1.png`, `05_tabela2.png`, `05_tabela3x.png` e os traçados `05_lin1.png` a `05_lin25.png` (conforme presentes no arquivo). A terceira tabela deve orientar os prémios especiais e as linhas verticais. Não assumir multiplicadores nem probabilidades sem ler o código original.

## O que falta implementar na versão web

O **bónus dos dados**, a leitura de resultados das cinco linhas verticais, os três acumulados e a matemática do motor não são ativados automaticamente pelo mero ato de importar estes ficheiros. Há que reproduzir as regras com testes de regressão, mantendo gráficos e som coerentes. O jogo original usa créditos virtuais.

**Aviso de direitos:** os recursos são atribuídos a R7 Developers. Confirmar autorização dos titulares antes de redistribuir materiais publicamente ou lançar uma aplicação baseada neles.
