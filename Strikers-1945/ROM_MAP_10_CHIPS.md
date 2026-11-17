# Strikers 1945 World — inventário forense das 10 ROMs recuperadas
Data: 17/11/2026

**Origem:** ficheiro s1945.zip incorporado num HTML que o utilizador forneceu anteriormente; recuperado localmente para análise. **Não publicar os bytes originais no GitHub**.

## Evidência material

O ZIP recuperado tem 5 455 851 bytes. Foram extraídos e validados os CRC32 de dez chips (13 500 416 bytes descomprimidos):

| ROM | Bytes | CRC32 confirmado | Função |
| --- | ---: | --- | --- |
| 2s.u40 | 262144 | 9b10062a | Programa principal, lanes 0–1 (68EC020) |
| 3s.u41 | 262144 | f87e871a | Programa principal, lanes 2–3 |
| 3-u63.bin | 131072 | 42d40ae1 | Programa CPU de som Z80 compatível |
| u20.bin | 2097152 | 28a27fee | Sprites, banco 0 |
| u22.bin | 2097152 | ca152a32 | Sprites, banco 1 |
| u21.bin | 2097152 | c5d60ea9 | Sprites, banco 2 |
| u23.bin | 2097152 | 48710332 | Sprites, banco 3 |
| u34.bin | 2097152 | aaf83e23 | Tiles de fundo |
| u61.bin | 2097152 | a839cf47 | Banco de amostras do Yamaha OPL4 |
| u1.bin | 262144 | dee22654 | Lookup de 131072 índices de sprites |

### Montagem do programa principal

MAME usa ROM_LOAD32_WORD_SWAP sobre 2s.u40 no offset 0 e 3s.u41 no offset 2. Cada palavra de 16 bits é trocada e intercalada para produzir uma região de endereço de 1 MiB. Os primeiros bytes remontados são:

```
FFFF8000 00000400 000198E8 000198E8 ...
```

Primeiro vetor de stack: 0xFFFF8000. Reset PC: 0x00000400. O dado é uma verificação de ordenação dos chips, **não significa que o motor JavaScript executa este programa**.

Tiles: 16 384 peças de 16×16 4bpp. Sprites: 65 536 peças de 16×16 4bpp. LUT: 131 072 índices de 16 bits little-endian.

### O que ainda falta reconstruir

1. Fluxo do programa 68EC020 que seleciona formações, movimentos e disparos.
2. Estados e hitboxes de cada inimigo e chefe, além dos dados capturados de parte da primeira fase.
3. Tabelas de scroll e paletas de vídeo em RAM para todos os níveis; os dados gráficos em ROM não contêm automaticamente os mapas finais.
4. Programa de som e comandos para o OPL4, incluindo sequenciamento correto de amostras e música.
5. Comportamento da proteção: o MCU PIC16C57 4-u59.bin figura como NO_DUMP no MAME e não está no ZIP.

O browser lê todos os dez chips, verifica CRC32, monta o mapa de código e descodifica os dados gráficos num motor Canvas/JavaScript. **Isto não é um emulador**, mas a reprodução exata de toda a IA e áudio ainda não está concluída.

Referência de mapeamento de chips: https://github.com/mamedev/mame/blob/master/src/mame/psikyo/psikyo.cpp (ROM_START(s1945)).
