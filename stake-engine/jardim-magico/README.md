# Jardim Mágico — edição experimental para Stake Engine (fase B)

**Estado: PROTÓTIPO TÉCNICO — NÃO SUBMETER / NÃO JOGAR COM DINHEIRO REAL.**
Este projeto vive isolado em `stake-engine/jardim-magico/`, num ramo de desenvolvimento; a edição jogável `jardim-phaser/` **não foi modificada**.

## Objetivo

Adaptar a lógica do Jardim Mágico para livros de resultados independentes exigidos pelo Stake Engine, conservando o que fizer sentido da identidade original, sujeito à confirmação de direitos de utilização comercial.

- Documentação oficial: https://stakeengine.org/docs
- Diretrizes de aprovação: https://stake-engine.com/docs/approval-guidelines
- Formato: https://github.com/engineio/docs/blob/main/src/routes/faq/math/what-files-are-required/%2Bpage.svx
- Math SDK: https://github.com/engineio/math-sdk

## Motor e versões

- `math/prototype.py`: prova de conceito antiga **apenas do jogo base**, preservada para comparação.
- `math/complete.py`: novo gerador experimental com **3 modos**: `base` (custo 1×), `free_buy` (20×) e `hold_buy` (30×).
- A base tem grelha 5×3, dez linhas, WILD e cascatas; 3 SCATTER em qualquer etapa ativam 15 Free Spins; se não houver SCATTER prioritário, 6 moedas ativam Hold & Win.
- As Free Spins acontecem **inteiramente no mesmo livro de resultados** e podem ser prolongadas por 2/3 SCATTER, até 60 jogos (multiplicador acumulado até ×1000). O valor bruto acumula e paga uma vez no fim da série.
- O Hold usa grelha 4×4, começa com seis moedas e reinicia as três tentativas quando recebe moedas novas. A grelha completa recebe, nesta versão experimental, uma **recompensa fixa de conclusão de 250×**. É um prémio fixo do mesmo resultado, **não** um jackpot progressivo; a interpretação para aprovação carece de confirmação.
- Cada aposta produz um resultado fechado com **um único evento `finalWin`**. O motor não guarda estado de apostas anteriores, cofres, pagamentos, perfis ou saldos de jogador.
- Cada modo cria `books_<modo>.jsonl.zst`, `lookUpTable_<modo>_0.csv` e entrada em `index.json`. A tabela CSV contém o ID, o peso inteiro positivo inicial 1 e o multiplicador inteiro idêntico ao respetivo livro. `payoutMultiplier = 100` corresponde a 1× da aposta base, independentemente do custo do modo.

**Aviso:** isto é uma **aproximação inicial das regras aprovadas**, não uma cópia exata do motor anterior. O simulador em números inteiros tem de ser revisto para respeitar o arredondamento por linha e possíveis nuances dos pagamentos. Os resultados continuam sem garantia estatística de RTP; frequências, pesos e volatilidade não foram calibrados.

## Executar e testar

Requer Python >=3.10 e o executável `zstd` (ou o módulo Python `zstandard`).

```bash
cd stake-engine/jardim-magico
python -m unittest discover -s tests -v
python math/complete.py --rounds 1000 --seed 20261111 --out build/features-prototype
```

No GitHub, o workflow `.github/workflows/stake-prototype.yml` testa as duas implementações, produz 300 simulações de cada modo e disponibiliza os ficheiros experimentais temporariamente como artefactos.

O relatório `sample-report.json` indica **RTP e taxa de acerto empíricos** da amostra, não uma previsão teórica fiável, nem um RTP aprovado para jogo comercial. Para cada modo, o cálculo percentual corrige o custo de compra, p. ex. 20× em `free_buy`.

## Limitações e bloqueadores para aprovação

1. **Matemática:** rever arredondamentos, regras exatas e hit rate, gerar grande amostra e ponderações, definir RTP teórico e intervalo de volatilidade, ensaiar prémios extremos; validar no RGS/Math SDK oficial.
2. **Produto:** desenvolver um frontend Phaser independente, que apenas reproduz eventos fornecidos pela plataforma; integração de sessão, `/play`, `/end-round`, replays, erros e acessibilidade.
3. **Direção artística adulta:** rever tema e símbolos para não atrair menores; confirmar os direitos de todos os ficheiros visuais e sons. Não usar marcas Stake/Kick nos assets.
4. **Regras:** remover jackpots de qualquer apresentação e submeter a recompensa fixa de conclusão a revisão, documentar o pagamento integral de cada bónus na mesma aposta.
5. **Segurança/compliance:** nenhuma conta, depósito, levantamento ou transação real neste protótipo; não existe certificação de RNG, RTP ou legislação aplicável.

**Apenas demonstrativo e para revisão técnica.** Não há ligação à plataforma, publicação comercial nem aprovação.
