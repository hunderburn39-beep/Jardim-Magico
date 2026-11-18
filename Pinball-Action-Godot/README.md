# Pinball Action — Godot native V0.1 (development)

An isolated native Godot 4 project, based on the V8.89 HTML branch. Open this folder's project.godot, then press F5. This first milestone is a ROM video diagnostic, not a playable port. It uses no WebView or embedded HTML.

- The supplied source snapshot, four original bitplane atlases and hardware state are extracted verbatim from the V8.89 HTML; see data/reference.json for the source blob and reference fingerprints.
- Background, foreground, hardware palette, sprite priority, scrolling, original portrait rotation and crop are implemented in GDScript.
- The GPU draws separate textures; static tile textures are updated only when the corresponding RAM or palette changes. A bounded sprite texture cache is shared across draws.
- The ROM audit compares all atlas fingerprints and the independently composed first 224x256 RGBA frame with the reference. No new face or illustration is painted over the original.

## Test locally

Godot 4.3+ (Compatibility renderer). Run F5 and examine the Output panel for ROM AUDIT. A headless regression runner is included. No Godot executable was available in the authoring environment.

## Remaining migration

1. Port the machine CPU/state/input backend and deterministic 60 Hz execution, retaining exact original rules.
2. Wire the native sound CPU, timers and original AY audio.
3. Verify all four tables and the original attract mode before HD sprite remastering.
4. Add touch controls and aspect-safe mobile layout; measure performance on iPhone/Windows.
5. Perform visual and gameplay regression testing before export or publication.

Do not replace the original web game or merge this branch until verification is complete.
