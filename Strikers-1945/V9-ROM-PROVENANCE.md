# Strikers 1945 V9 — verified asset provenance

Source: the user's private ten-chip World ZIP. All ten expected CRC32 values verified. No ROM bytes or artwork are added to this public repository.

## Offline full extraction (verified 2026-11-17)
- 65,536 physical sprite tiles from u20/u22/u21/u23, 16,384 terrain tiles from u34. All five graphic chips decoded into 4-bit pixel indices and repacked byte-for-byte.
- 131,072 little-endian logical LUT entries from u1, referring to 61,996 distinct physical tiles.
- 105 valid initial-bank 8-bit PCM waveform headers and samples from u61. WAV audition at provisional 22,050 Hz; original music sequencing and pitch not established.
- Program reconstructed by 32-bit word-swapped interleave: 524,288 bytes, SHA256 7f944fd9b26e96b7c646bbe2576088a4ce305f5fd3c7c9707c0b16e2db4387f7. SP ffff8000, PC 00000400. Sound CPU program 131,072 bytes.
- Protection PIC 4-u59.bin not present in the supplied set.

## Visually verified LUT strips (indices, not hardware palette)
1439–1446 GAME OVER large; 1447–1451 GAME OVER small; 1452–1455 START; 1458–1462 INSERT COINS!; 1463–1466 ADD COIN; 1467–1470 FREE PLAY; 1471–1475 CONTINUE?; 1476–1477 1P/2P; 1478–1480 CREDIT; 1485–1487 ©1995; 1488–1494 PSIKYO; 1495–1504 digits 0–9. Portrait orientation 90° counterclockwise. LUT 0 is a blank tile.

Aircraft abbreviations identified visually: 348 P-38, 349 P-51, 350 SPIT FIRE, 352–353 Bf-109, 354 ZERO, 355–356 SHINDEN. These are text labels only, not the player aircraft silhouettes.

## Status / release gate
V9 is a development branch, not a released complete game. Captures of boot/title palette and main logo assembly, original player aircraft sprite assemblies, full stage maps and patterns beyond the first captured airbase segment, boss sequences, original audio commands, co-op play, ending, and native visual/runtime verification are pending. Do not substitute approximations and label them original. The current V8.8 remains on main.
