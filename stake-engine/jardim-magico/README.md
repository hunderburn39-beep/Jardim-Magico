# Jardim Mágico — edição experimental para Stake Engine

**Estado: protótipo de matemática base, não pronto para submissão.** A edição jogável existente em `jardim-phaser/` fica intocada. Este diretório é independente e **não** recebe depósitos, apostas reais ou pagamentos.

## Objetivo e fontes

Preparar uma adaptação comercial original do Jardim Mágico (Phaser 3) às exigências do Stake Engine, mantendo os elementos de identidade que possam ser comprovadamente licenciados para utilização comercial.

- Guia oficial: https://stakeengine.org/docs
- Restrições: https://stakeengine.org/docs/approval-guidelines
- Formato matemático: https://engineio.github.io/math-sdk/rgs_docs/data_format/
- SDK oficial: https://github.com/engineio/math-sdk

## Trabalho já feito (fase A)

- O `math/prototype.py` gera rondas **independentes** com a grelha 5×3, as dez linhas de prémio, WILD e cascatas. Começa em cada aposta sem consultar banca, saldo, depósitos, modo de administração ou jogadas anteriores.
- Mantém os coeficientes nominais da edição Phaser em unidades inteiras de 1/100 aposta e emite eventos visuais por etapa.
- Gera uma biblioteca comprimida `books_base.jsonl.zst`, a tabela CSV `lookUpTable_base_0.csv` e um `index.json` compatível com a **estrutura documental** exigida pelo Engine. Cada linha do CSV corresponde ao `id` e ao `payoutMultiplier` da biblioteca.
- Inclui testes de pagamentos básicos, WILD, cascata, reprodução de rondas e consistência dos ficheiros.

## Limitações **importantes**

**Não submeter estes ficheiros.** Só o modo base está implementado. SCATTER, Free Spins e Hold & Win estão deliberadamente inativos; não são pagos nem disparados neste protótipo e a sua integração matemática é trabalho futuro. Não há garantia de RTP alvo, volatilidade ou aceitação do Engine. O `sample-report.json` contém apenas o RTP empírico da amostra criada (em percentagem), não uma certificação ou RTP ajustado. A compatibilidade da estrutura dos ficheiros não equivale à validação oficial do RGS. Falta também um frontend ligado ao fluxo `/play` e testes no ambiente da plataforma.

O Engine exige independência total das apostas e **proíbe jackpots, continuation, gamble e cashout antecipado**. Por isso, os jackpots ilustrativos, o cofre e os perfis de administração da versão de demonstração não entram nesta edição. Os bónus terão de ser resultados completos numa única aposta (sem carry entre rondas).

O cenário colorido e os símbolos podem ter de ser redesenhados numa direção artística claramente adulta. Antes de publicar, confirmar titularidade/licenças de todas as imagens, áudio e marca, e obter aprovação da equipa da plataforma.

## Executar em ambiente local de desenvolvimento

Requer Python >=3.10 e `zstd` no PATH ou o módulo `zstandard`.

```bash
cd stake-engine/jardim-magico
python3 -m unittest discover -s tests -v
python3 math/prototype.py --rounds 1000 --seed 20261111 --out build/base-prototype
```

`build/` é local, não deve ser incluído no Git. As simulações são pré-geradas para análise e validação; o servidor do operador, não o browser do jogador, selecionará os resultados ponderados depois da integração e aprovação.

## Próximas etapas antes de qualquer submissão

1. Definir matemática completa: ativação e pagamentos de SCATTER, Free Spins e Hold & Win, a terminar na mesma ronda; rever todas as regras e multiplicadores.
2. Substituir o modelo experimental por integração com o Math SDK oficial (ou validar formalmente os ficheiros alternativos), gerar biblioteca grande e otimizar um RTP por modo, com testes estatísticos e de máximo prémio.
3. Adaptar frontend Phaser a eventos vindos do RGS, sem geração de prémios no cliente; validar o modo de simulação no testnet.
4. Auditar licenças e identidade visual; documentação de jogo responsável, acessibilidade, formatos móveis e revisão de aprovação.
