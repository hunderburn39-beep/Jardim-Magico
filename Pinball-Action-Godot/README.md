# Pinball Action — Godot native V0.3 (unverified development)

Open project.godot with Godot 4.3+ (Compatibility renderer). Press F5. The original machine program now has a GDScript CPU port, a separate native 2D hardware-video renderer and key/touch controls. This is a gameplay candidate, not a verified release. No Godot executable was available to run it in the authoring environment.

Controls: C inserts coin, Enter/1 starts P1, 2 starts P2, Space/Down launches, Left/A and Right/D operate flippers, Up/S shakes. Onscreen buttons mirror these inputs with independent finger captures for the two flippers. Hardware input remains unverified on iOS. Credit handling is delegated to the ROM, with automatic coin pulses when starting with insufficient credits.

The source original ROM snapshot, four bitplane atlases, palette, scroll and sprite order remain intact. No HTML/WebView is used. V0.3 displays the authentic original pixels through an indexed native palette shader. Palette changes no longer invalidate the tile or sprite textures; only modified tile positions are decoded. HD artwork still needs the native migration. Sound commands are counted but not played: the sound CPU and AY audio are not yet ported.

## Regressions

From this directory:
```
godot --headless --path . --script res://test_rom.gd
godot --headless --path . --script res://test_cpu.gd
```

The first checks the atlas fingerprints, reference frame, palette-only changes, single-tile decoding and unchanged layers. The second compares nine full RAM/register/credit/sound-command checkpoints across a recorded 360-frame original JavaScript replay. The reference replay was executed against the original JS; target Godot execution remains unverified. Resolve any parsing or runtime error and any RAM hash difference before considering gameplay equivalent.

## Remaining work

- Run tests on a local Godot installation and verify actual ball/flippers/score/all four boards.
- Port the original sound CPU and three AY chips.
- Refine HD resources without altering original illustration; profile on iPhone and Windows.
- Verify multitouch and export platforms.

Do not replace the published HTML or merge until the project is tested.
