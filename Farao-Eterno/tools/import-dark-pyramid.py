#!/usr/bin/env python3
"""Importar a gravação CC0 Dark Pyramid Night Ambience Loop, de szegvari.
Executado pelo GitHub Actions; nunca toca nos outros jogos.
Fonte: https://freesound.org/people/szegvari/sounds/534876/
Pixabay: https://pixabay.com/sound-effects/musical-dark-pyramid-night-ambience-loop-27178/
"""
import html
import json
import os
import re
import subprocess
import sys
import tempfile
from pathlib import Path
from urllib.parse import urlparse
from urllib.request import Request, urlopen

ROOT = Path(__file__).resolve().parents[1]
DEST = ROOT / "assets" / "dark-pyramid-ambience.mp3"
SOURCES = [
    "https://pixabay.com/sound-effects/musical-dark-pyramid-night-ambience-loop-27178/",
    "https://freesound.org/people/szegvari/sounds/534876/",
    "https://freesound.org/embed/sound/iframe/534876/simple/small/",
    "https://freesound.org/api/sounds/534876",
]
USER_AGENT = "Mozilla/5.0 (compatible; FaraoEternoAudioImporter/1.0)"
ALLOWED = {"cdn.pixabay.com", "freesound.org", "cdn.freesound.org",
           "www.freesound.org", "pixabay.com"}

def request(url):
    print("GET", url, flush=True)
    req = Request(url, headers={"User-Agent": USER_AGENT, "Accept": "*/*"})
    with urlopen(req, timeout=30) as response:
        return response.read()

def direct_audio_urls(page):
    raw = html.unescape(page.decode("utf-8", errors="replace"))
    for _ in range(4):
        raw = raw.replace("\\/", "/").replace("\\u002F", "/")
        raw = raw.replace("\\u0026", "&").replace("\\u003D", "=")
        raw = html.unescape(raw)
    urls = re.findall(r'https?://[^\s"\'<>]+', raw)
    urls += [("https:" + u) for u in re.findall(
        r"(?<!:)//(?:cdn\\.pixabay\\.com|cdn\\.freesound\\.org|freesound\\.org)/[^\\s\\\"'<>]+", raw
    )]
    # A metadata og:audio de Freesound contém um prefixo duplicado.
    urls=[u.replace("https://freesound.orghttps://", "https://") for u in urls]
    result = []
    for candidate in urls:
        candidate = candidate.rstrip(");,}")
        parsed = urlparse(candidate)
        host = parsed.hostname or ""
        if host not in ALLOWED:
            continue
        path = parsed.path.lower()
        if not any(path.endswith(x) for x in (".mp3", ".ogg", ".wav")):
            continue
        if candidate not in result:
            result.append(candidate)
    # First choose the matching recording, then more general preview links.
    return sorted(result, key=lambda u: (
        0 if any(tag in u for tag in ("534876", "27178", "dark-pyramid")) else 1,
        0 if "hq-mp3" in u or "cdn.pixabay.com" in u else 1,
    ))

def duration(filename):
    probe = subprocess.check_output([
        "ffprobe", "-v", "error", "-show_entries", "format=duration",
        "-of", "default=nw=1:nk=1", str(filename)
    ], text=True, timeout=15).strip()
    return float(probe)

def main():
    if DEST.exists() and 18 <= duration(DEST) <= 21:
        print("Música já importada:", DEST)
        return
    options = []
    for page in SOURCES:
        try:
            page_bytes=request(page)
            if "freesound.org/people/" in page:
                sample=page_bytes.decode("utf-8",errors="replace")
                print("Freesound HTML bytes",len(page_bytes))
                for token in ("534876", "previews", "mp3", "audio", "sound-data"):
                    idx=sample.lower().find(token.lower())
                    print("DEBUG",token,idx,repr(sample[max(0,idx-100):idx+220]) if idx>=0 else "",flush=True)
            options.extend(direct_audio_urls(page_bytes))
        except Exception as exc:
            print("Página indisponível:", exc, flush=True)
    options = list(dict.fromkeys(options))
    # Tentar a versão HQ da mesma gravação antes da versão LQ.
    for u in list(options):
        if "-lq.mp3" in u:
            hq=u.replace("-lq.mp3","-hq.mp3")
            if hq not in options: options.insert(0,hq)
    options.sort(key=lambda u: (0 if "-hq." in u else 1, 0 if "534876" in u else 1))
    print("Candidatos:", len(options))
    for url in options[:40]:
        print("CANDIDATO:", url)
    for url in options[:35]:
        try:
            with tempfile.TemporaryDirectory() as directory:
                media = Path(directory) / "source.media"
                data = request(url)
                if len(data) < 15_000 or len(data) > 20_000_000:
                    print("Tamanho inesperado", len(data))
                    continue
                media.write_bytes(data)
                seconds = duration(media)
                if not 19.0 <= seconds <= 20.4:
                    print("Duração diferente:", seconds)
                    continue
                DEST.parent.mkdir(parents=True, exist_ok=True)
                subprocess.run([
                    "ffmpeg", "-hide_banner", "-loglevel", "error", "-y",
                    "-i", str(media), "-vn", "-ac", "2", "-ar", "24000",
                    "-codec:a", "libmp3lame", "-b:a", "96k", str(DEST)
                ], check=True, timeout=60)
                print("Gravação CC0 importada:", str(DEST), "segundos:", duration(DEST))
                return
        except Exception as exc:
            print("Falhou áudio:", type(exc).__name__, str(exc), flush=True)
    raise SystemExit(
        "Não encontrei um ficheiro público verificável do som 534876/27178; "
        "nenhum áudio de substituição será publicado."
    )

if __name__ == "__main__":
    main()
