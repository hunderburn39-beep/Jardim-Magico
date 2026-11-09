#!/usr/bin/env python3
"""Prepara arte do frontend Sugar Rush para renderização Phaser transparente.

O projeto importado tem ficheiros denominados .png cujo conteúdo real é JPEG,
por vezes com fundo preto. Este processo é apenas para a build (dist):
não modifica a matemática, o jogo original, nem concede direitos dos assets.
"""
from pathlib import Path
from PIL import Image

ART = Path(__file__).resolve().parents[1] / "public" / "images" / "candies"
FILES = [*(f"candy_{n}.png" for n in range(7)), "scatter.png", "wild.png"]

def convert(path: Path):
    with Image.open(path) as opened:
        image = opened.convert("RGBA")
        pixels = image.load()
        # O preto das exportações JPEG deve tornar-se transparente.
        # Só aplicar se a maioria dos cantos pertencer ao fundo escuro.
        w, h = image.size
        corners = [pixels[0, 0], pixels[w - 1, 0],
                   pixels[0, h - 1], pixels[w - 1, h - 1]]
        dark_corners = sum(max(r, g, b) < 45 for r, g, b, _ in corners)
        if dark_corners >= 3 and opened.format == "JPEG":
            altered = []
            for r, g, b, _ in image.getdata():
                brightness = max(r, g, b)
                if brightness <= 13:
                    alpha = 0
                elif brightness >= 65:
                    alpha = 255
                else:
                    t = (brightness - 13) / 52
                    alpha = round(255 * t * t * (3 - 2 * t))
                altered.append((r, g, b, alpha))
            image.putdata(altered)
        alpha = image.getchannel("A")
        # Cortar margem transparente para aumentar a área visível do doce.
        bounds = alpha.point(lambda a: 255 if a > 24 else 0).getbbox()
        if not bounds:
            raise ValueError(f"Imagem sem pixels visíveis: {path.name}")
        cropped = image.crop(bounds)
        cropped.thumbnail((472, 472), Image.Resampling.LANCZOS)
        canvas = Image.new("RGBA", (512, 512))
        canvas.alpha_composite(cropped, ((512 - cropped.width) // 2,
                                         (512 - cropped.height) // 2))
        canvas.save(path, format="PNG", optimize=True)
        print(f"{path.name}: {opened.format} {w}x{h} -> PNG transparente 512x512")

if __name__ == "__main__":
    for name in FILES:
        f = ART / name
        if not f.is_file():
            raise FileNotFoundError(f)
        convert(f)
