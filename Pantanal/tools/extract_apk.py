#!/usr/bin/env python3
"""Inventario e extracao integral do APK Pantanal (stdlib; nao executa o APK).

Esta ferramenta conserva cada entrada do arquivo Android, incluindo DEX,
manifest, imagens, som, tabelas e certificados. Codigo legivel obtido com JADX
e derivado do DEX, nao e o projeto fonte original dos criadores.
"""
from __future__ import annotations
import argparse
import collections
import datetime as dt
import hashlib
import json
from pathlib import Path, PurePosixPath
import re
import sys
import zipfile

def digest(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()

def category(name: str) -> str:
    p = name.lower()
    if p.endswith((".png", ".jpg", ".jpeg", ".webp", ".gif", ".bmp")): return "imagem"
    if p.endswith((".mp3", ".ogg", ".wav", ".mid", ".aac", ".m4a")): return "audio"
    if p.endswith(".dex"): return "codigo-dex"
    if p.endswith((".so", ".arsc")): return "binario-android"
    if p.endswith(".xml"): return "xml-ou-manifest-binario"
    if p.endswith((".ttf", ".otf")): return "fonte"
    if "meta-inf/" in p: return "assinatura-e-metadados"
    return "outros"

def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("apk")
    parser.add_argument("--out", default="Pantanal/APK-2.06")
    args=parser.parse_args()
    apk=Path(args.apk)
    output=Path(args.out)
    if not apk.is_file() or not zipfile.is_zipfile(apk):
        raise SystemExit("O ficheiro nao e um APK/ZIP valido.")
    output.mkdir(parents=True,exist_ok=True)
    data=apk.read_bytes()
    apk_sha=digest(data)
    manifest=[]
    counts=collections.Counter()
    category_sizes=collections.Counter()
    total=0
    with zipfile.ZipFile(apk) as z:
        infos=[i for i in z.infolist() if not i.is_dir()]
        names={i.filename for i in infos}
        assert "AndroidManifest.xml" in names, "O ficheiro nao tem AndroidManifest.xml"
        assert "classes.dex" in names, "O ficheiro nao tem classes.dex"
        assert len(infos)<10000, "Numero de recursos inesperado"
        assert sum(i.file_size for i in infos)<250_000_000, "Tamanho de extracção excessivo"
        for info in infos:
            p=PurePosixPath(info.filename)
            if p.is_absolute() or ".." in p.parts or "\\" in info.filename:
                raise ValueError("Entrada insegura: "+info.filename)
            if (info.external_attr >> 16) & 0o170000 == 0o120000:
                raise ValueError("Link simbolico nao suportado: "+info.filename)
            buf=z.read(info.filename)
            dst=output/"ficheiros"/p
            dst.parent.mkdir(parents=True,exist_ok=True)
            dst.write_bytes(buf)
            cat=category(info.filename)
            counts[cat]+=1
            category_sizes[cat]+=len(buf)
            total+=len(buf)
            manifest.append(dict(path=info.filename,category=cat,size_bytes=len(buf),sha256=digest(buf)))
    sha_path=output/"SHA256SUMS.txt"
    sha_path.write_text("".join(f"{it['sha256']}  ficheiros/{it['path']}\n" for it in manifest),encoding="utf8")
    report={
      "app":"Pantanal Caca Niquel",
      "package_id_esperado":"com.r7games.slotpantanal",
      "versao_esperada":"2.06",
      "observacao":"Verificar assinatura e versionCode no AndroidManifest.xml (binario), se disponivel",
      "apk_sha256":apk_sha,
      "apk_size_bytes":len(data),
      "files":len(manifest),
      "uncompressed_size_bytes":total,
      "category_counts":dict(sorted(counts.items())),
      "category_uncompressed_bytes":dict(sorted(category_sizes.items())),
      "entries":manifest,
    }
    (output/"inventario.json").write_text(json.dumps(report,indent=2,ensure_ascii=False)+"\n",encoding="utf8")
    (output/"README.md").write_text(
      "# Pantanal — Inventario do APK Android 2.06\n\n"
      "Esta pasta preserva os ficheiros **tal como existem no pacote APK**. "
      "O pacote e atribuído a R7 Developers e nao corresponde ao código-fonte de desenvolvimento original.\n\n"
      f"- SHA-256 do APK obtido: \`{apk_sha}\`\n"
      f"- Numero de entradas: **{len(manifest)}**; tamanho extraído: **{total} bytes**.\n"
      "- \`ficheiros/assets/\`: ficheiros do jogo (imagens, sons e tabelas, conforme presentes no APK).\n"
      "- \`ficheiros/classes.dex\`: bytecode Android compilado.\n"
      "- \`ficheiros/AndroidManifest.xml\`: manifest Android binário.\n"
      "- \`inventario.json\` e \`SHA256SUMS.txt\`: lista completa de entradas e verificacão de integridade.\n"
      "- \`jadx/\`, quando disponível: Java reconstruído automaticamente a partir do DEX (pode ser incompleto e não é o original).\n\n"
      "**Atenção:** importar imagens e bytecode não reproduz automaticamente o motor do APK no navegador. "
      "O RTP, probabilidades, dados, jackpots e administracão dependem de validação. "
      "Para redistribuir os materiais originais, confirme os direitos dos titulares.\n",
      encoding="utf8"
    )
    print(json.dumps({k:v for k,v in report.items() if k!="entries"},indent=2,ensure_ascii=False))
    return 0
if __name__=="__main__":
    sys.exit(main())
