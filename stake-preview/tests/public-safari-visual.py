"""Confirma os símbolos reais do Stake Preview diretamente no GitHub Pages, via Safari WebKit.
A grelha fixa é criada apenas na memória do browser para inspecção visual;
não se alteram o código público, o saldo real ou a matemática de prémios.
"""
from pathlib import Path
import json,os,time
from urllib.parse import urlencode
from playwright.sync_api import sync_playwright

ROOT="https://hunderburn39-beep.github.io/Jardim-Magico/stake-preview/"
EXPECTED={
    1:"assets/figuras-v199/joaninha-limpa.svg",
    3:"assets/figuras-v199/bule-limpo.svg",
    5:"assets/figuras-v199/diamante-limpo.svg",
    6:"assets/figuras-v199/cogumelo-limpo.svg",
    7:"assets/figuras-v199/chapeu-limpo.svg",
}
ORDER=[0,3,5,6,7,1,2,8,9,10,11,4,0,1,5]
OUT=Path(os.environ.get("GITHUB_WORKSPACE","."))/"stake-safari-public.png"

with sync_playwright() as p:
    browser=p.webkit.launch(headless=True)
    for width,height,label in [(390,844,"iPhone compacto"),(428,926,"iPhone grande")]:
        page=browser.new_page(viewport={"width":width,"height":height},
                              device_scale_factor=2,is_mobile=True,has_touch=True)
        errors=[]
        page.on("pageerror",lambda err: errors.append(str(err)))
        # Pages may be redeploying. Wait for HTML that refers to the actual cleaned assets.
        ready=False
        for attempt in range(12):
            url=ROOT+"?"+urlencode({"v":"stake-v199-public-qa","attempt":attempt})
            try:
                response=page.goto(url,wait_until="domcontentloaded",timeout=20000)
                html=page.content()
                if response and response.status==200 and all(src in html for src in EXPECTED.values()):
                    ready=True
                    break
                print("PUBLIC_PAGE_PENDING",attempt+1,"status",response.status if response else None,flush=True)
            except Exception as exc:
                print("PUBLIC_PAGE_RETRY",attempt+1,str(exc)[:130],flush=True)
            time.sleep(7)
        assert ready,"Public GitHub Pages does not yet reference all five corrected SVG assets"
        page.locator("#introPlay").click()
        page.evaluate("""(ids)=>{
           const b=Array.from({length:5},(_,c)=>ids.slice(c*3,c*3+3));
           draw(b);
        }""",ORDER)
        page.wait_for_function("""()=>{
            const imgs=[...document.querySelectorAll('#reelFrame .cell img.jmSymbolArt')];
            return imgs.length===15&&imgs.every(i=>i.complete&&i.naturalWidth>0);
        }""",timeout=20000)
        result=page.evaluate("""async expected=>{
          const cells=[...document.querySelectorAll('#reelFrame .cell')];
          const checks=cells.map(el=>{
            const im=el.querySelector('img.jmSymbolArt');
            const r=im.getBoundingClientRect(),box=el.getBoundingClientRect();
            return {id:+im.dataset.symbolId,src:im.getAttribute('src'),
              loaded:im.complete&&im.naturalWidth>0,
              fits:r.left>=box.left-1&&r.top>=box.top-1&&r.right<=box.right+1&&r.bottom<=box.bottom+1,
              fitPercent:Math.round(100*Math.min(r.width/box.width,r.height/box.height))};
          });
          const alphas={};
          for(const id of [1,3,5,6,7]){
            const im=cells.map(x=>x.querySelector('img')).find(x=>+x.dataset.symbolId===id);
            const canvas=document.createElement('canvas');
            canvas.width=canvas.height=128;
            const ctx=canvas.getContext('2d',{willReadFrequently:true});
            ctx.drawImage(im,0,0,128,128);
            const corners=[[0,0],[127,0],[0,127],[127,127]];
            alphas[id]=corners.map(([x,y])=>ctx.getImageData(x,y,1,1).data[3]);
          }
          const roll=document.createElement('div');roll.className='reel-scroll-viewport';
          const strip=document.createElement('div');strip.className='reel-scroll-strip';
          strip.style.setProperty('--reel-cell-height','80px');
          for(let id=0;id<12;id++){
             const tile=document.createElement('div');tile.className='reel-scroll-symbol';
             tile.innerHTML=symbolArtworkMarkup(id);
             strip.append(tile);
          }
          roll.append(strip);document.querySelector('.column').append(roll);
          const rollImages=[...strip.querySelectorAll('img.jmSymbolArt')];
          await Promise.all(rollImages.map(img=>img.decode().catch(()=>{})));
          const moving=rollImages.map(im=>{
            const r=im.getBoundingClientRect(),cell=im.parentElement.getBoundingClientRect();
            return {id:+im.dataset.symbolId,loaded:im.complete&&im.naturalWidth>0,
              fits:r.left>=cell.left-1&&r.right<=cell.right+1&&r.top>=cell.top-1&&r.bottom<=cell.bottom+1};
          });
          roll.remove();
          const f=document.querySelector('#reelFrame').getBoundingClientRect();
          return {checks,alphas,moving,frame:{left:f.left,right:f.right},viewport:innerWidth};
        }""",EXPECTED)
        assert len(result["checks"])==15,(label,"not 5 x 3",result)
        assert all(c["fits"] and c["loaded"] and c["fitPercent"]<=97 for c in result["checks"]),(label,"cell fit failure",result)
        assert all(result["alphas"][str(i)]==[0,0,0,0] for i in EXPECTED),(label,"black background",result["alphas"])
        assert all(c["src"]==EXPECTED[c["id"]] for c in result["checks"] if c["id"] in EXPECTED),(label,"old image cached",result)
        assert len(result["moving"])==12 and all(x["fits"] and x["loaded"] for x in result["moving"]),(label,"rolling images failed",result)
        assert -1<=result["frame"]["left"] and result["frame"]["right"]<=width+1,(label,"frame outside phone",result)
        assert not errors,(label,"JS errors",errors)
        print("PUBLIC_SAFARI_PASS",label,json.dumps({"url":page.url,"symbols":15,"scrolling":12,"transparent":list(EXPECTED),"width":width},ensure_ascii=False),flush=True)
        if width==390:
            OUT.parent.mkdir(parents=True,exist_ok=True)
            page.screenshot(path=str(OUT),full_page=True,animations="disabled")
        page.close()
    browser.close()
print("PUBLISHED_VISUAL_PASS: corrected assets actually loaded from GitHub Pages on WebKit",flush=True)
