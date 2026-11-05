#!/usr/bin/env python3
"""Organiza as fontes do Jardim Mágico em HTML, CSS, JS e assets."""
from __future__ import annotations
import base64
import hashlib
import re
from pathlib import Path
from zipfile import ZipFile

ROOT = Path(__file__).resolve().parent.parent
ZIP = ROOT / "JardimMagicoAndroid.zip"
STYLES = re.compile(r"<style\b([^>]*)>(.*?)</style\s*>", re.I | re.S)
SCRIPTS = re.compile(r"<script\b([^>]*)>(.*?)</script\s*>", re.I | re.S)
DATA = re.compile(
    r"data:(image/(?:png|jpeg|jpg|webp|gif|svg\+xml|avif|bmp)|"
    r"audio/(?:mpeg|mp3|wav|ogg|mp4)|font/(?:woff2?|ttf|otf))"
    r";base64,([A-Za-z0-9+/=\r\n]+)", re.I
)
TYPES = {
    "image/png": ("imagens", "png"), "image/jpeg": ("imagens", "jpg"),
    "image/jpg": ("imagens", "jpg"), "image/webp": ("imagens", "webp"),
    "image/gif": ("imagens", "gif"), "image/svg+xml": ("imagens", "svg"),
    "image/avif": ("imagens", "avif"), "image/bmp": ("imagens", "bmp"),
    "audio/mpeg": ("sons", "mp3"), "audio/mp3": ("sons", "mp3"),
    "audio/wav": ("sons", "wav"), "audio/ogg": ("sons", "ogg"),
    "audio/mp4": ("sons", "m4a"),
    "font/woff": ("fontes", "woff"), "font/woff2": ("fontes", "woff2"),
    "font/ttf": ("fontes", "ttf"), "font/otf": ("fontes", "otf"),
}
counter = {"css": 0, "js": 0, "assets": set()}

def put(file: Path, data: str | bytes):
    file.parent.mkdir(parents=True, exist_ok=True)
    if isinstance(data, str):
        file.write_text(data, encoding="utf-8")
    else:
        file.write_bytes(data)

def externalize(text: str, in_css=False) -> str:
    def rewrite(m: re.Match) -> str:
        media = m.group(1).lower()
        try:
            raw = base64.b64decode("".join(m.group(2).split()), validate=True)
        except (ValueError, base64.binascii.Error):
            return m.group(0)
        if not raw:
            return m.group(0)
        folder, ext = TYPES[media]
        rel = Path("assets") / folder / (hashlib.sha256(raw).hexdigest()[:18] + "." + ext)
        if not (ROOT / rel).exists():
            put(ROOT / rel, raw)
        counter["assets"].add(rel.as_posix())
        return ("../" if in_css else "") + rel.as_posix()
    return DATA.sub(rewrite, text)

def main():
    if not ZIP.is_file():
        raise SystemExit(f"Não existe {ZIP.name}")
    with ZipFile(ZIP) as z:
        p = "app/src/main/assets/index.html"
        if p not in z.namelist():
            raise SystemExit(f"Não existe {p} no ZIP")
        html = (ROOT / "index.html").read_text(encoding="utf-8-sig") if (ROOT / "index.html").is_file() else z.read(p).decode("utf-8-sig")
        for f in z.infolist():
            name = f.filename.replace("\\", "/")
            if f.is_dir() or name == p or name.startswith("/") or ".." in Path(name).parts:
                continue
            if name.startswith("app/src/main/assets/"):
                dest = ROOT / name.removeprefix("app/src/main/assets/")
            elif name.startswith("app/") or name in ("build.gradle", "gradle.properties", "settings.gradle"):
                dest = ROOT / name
            else:
                continue
            if not dest.exists():
                put(dest, z.read(f))

    def style(m: re.Match) -> str:
        inner = m.group(2)
        if not inner.strip():
            return m.group(0)
        counter["css"] += 1
        num = counter["css"]
        fname = "estilo.css" if num == 1 else f"estilo-{num}.css"
        put(ROOT / "css" / fname, externalize(inner, in_css=True))
        attrs = m.group(1)
        media = re.search(r'\bmedia\s*=\s*(["\'])(.*?)\1', attrs, re.I | re.S)
        media_attr = f' media="{media.group(2)}"' if media else ""
        return f'<link rel="stylesheet" href="css/{fname}"{media_attr}>'

    def script(m: re.Match) -> str:
        attrs, inner = m.group(1), m.group(2)
        if not inner.strip() or re.search(r'\bsrc\s*=', attrs, re.I):
            return m.group(0)
        typ = re.search(r'\btype\s*=\s*(["\'])(.*?)\1', attrs, re.I | re.S)
        if typ and typ.group(2).strip().lower() not in ("text/javascript", "application/javascript", "module"):
            return m.group(0)
        counter["js"] += 1
        num = counter["js"]
        fname = "jogo.js" if num == 1 else f"jogo-{num}.js"
        put(ROOT / "js" / fname, externalize(inner))
        return f'<script{attrs} src="js/{fname}"></script>'

    html = STYLES.sub(style, html)
    html = SCRIPTS.sub(script, html)
    put(ROOT / "index.html", externalize(html))
    if counter["css"] == 0:
        put(ROOT / "css" / "README.md", "Folhas de estilo do jogo.\n")
    if counter["js"] == 0:
        put(ROOT / "js" / "README.md", "Scripts do jogo.\n")
    put(ROOT / "assets" / "README.md", "Recursos gráficos, sons e fontes do jogo.\n")
    print(f"Separação: {counter['css']} CSS, {counter['js']} JS, {len(counter['assets'])} assets")
    if "<html" not in html.lower() or not (counter["css"] or counter["js"]):
        raise SystemExit("HTML inválido ou não foram encontrados estilos/scripts inline.")

if __name__ == "__main__":
    main()
