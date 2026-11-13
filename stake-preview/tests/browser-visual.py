"""Verificação visual real da grelha do Jardim Mágico Stake em Chromium móvel.
Não altera saldo, matemática ou dados guardados do jogo.
"""
import base64
import json
from pathlib import Path
from playwright.sync_api import sync_playwright

BASE="http://127.0.0.1:8765/stake-preview/?v=visual-qa"
ID_ORDER=[0,3,5,6,7,1,2,8,9,10,11,4,0,1,5]
def check_device(browser, width, height, label, show_image=False):
    page=browser.new_page(viewport={"width":width,"height":height},device_scale_factor=1,is_mobile=True,has_touch=True)
    js_errors=[]
    page.on("pageerror",lambda exc: js_errors.append(str(exc)))
    response=page.goto(BASE,wait_until="networkidle",timeout=45000)
    assert response and response.status==200,(label,"HTTP status",response.status if response else None)
    page.locator("#introPlay").click()
    page.wait_for_timeout(400)
    # Deterministic fixture shown only to the QA browser. No changes to engine odds or account.
    page.evaluate("""(ids)=>{
      const board=Array.from({length:5},(_,c)=>ids.slice(c*3,c*3+3));
      draw(board);
    }""",ID_ORDER)
    page.wait_for_function("document.querySelectorAll('#reelFrame .cell img.jmSymbolArt').length===15 && Array.from(document.querySelectorAll('#reelFrame .cell img.jmSymbolArt')).every(i=>i.complete&&i.naturalWidth>0)",timeout=18000)
    result=page.evaluate("""()=>{
      const frame=document.querySelector('#reelFrame').getBoundingClientRect();
      const reel=document.querySelector('#reels').getBoundingClientRect();
      const cols=[...document.querySelectorAll('#reelFrame .column')];
      const images=[...document.querySelectorAll('#reelFrame .cell img.jmSymbolArt')];
      const checks=images.map(im=>{
        const box=im.getBoundingClientRect(),cell=im.closest('.cell').getBoundingClientRect();
        const pixelRatio=Math.min(box.width/cell.width,box.height/cell.height);
        return {id:Number(im.dataset.symbolId),within:box.left>=cell.left-1&&box.right<=cell.right+1&&box.top>=cell.top-1&&box.bottom<=cell.bottom+1,ratio:+pixelRatio.toFixed(3),loaded:im.complete&&im.naturalWidth>0,clip:getComputedStyle(im).clipPath};
      });
      const alphas={};
      [1,3,5,6,7].forEach(id=>{
        const im=images.find(x=>Number(x.dataset.symbolId)===id);
        if(!im)return;
        const c=document.createElement('canvas');c.width=c.height=128;
        const ctx=c.getContext('2d',{willReadFrequently:true});ctx.drawImage(im,0,0,128,128);
        const corner=[0,0,127,0,0,127,127,127];
        alphas[id]=[ctx.getImageData(corner[0],corner[1],1,1).data[3],ctx.getImageData(corner[2],corner[3],1,1).data[3],ctx.getImageData(corner[4],corner[5],1,1).data[3],ctx.getImageData(corner[6],corner[7],1,1).data[3]];
      });
      return {frame:{width:+frame.width.toFixed(1),height:+frame.height.toFixed(1),left:+frame.left.toFixed(1),right:+frame.right.toFixed(1)},reels:{width:+reel.width.toFixed(1),height:+reel.height.toFixed(1)},columns:cols.length,cellCount:images.length,checks,alphas,viewport:innerWidth};
    }""")
    assert result["columns"]==5 and result["cellCount"]==15,(label,result)
    assert result["frame"]["left"]>=-2 and result["frame"]["right"]<=width+2,(label,"frame outside viewport",result["frame"])
    assert all(x["within"] and x["loaded"] and x["ratio"]<=.971 for x in result["checks"]),(label,"image outside tile",result["checks"])
    assert all(max(v)<=2 for v in result["alphas"].values()),(label,"nontransparent source corners",result["alphas"])
    assert not js_errors,(label,"javascript errors",js_errors)
    print("VIEWPORT_OK",label,json.dumps({"frame":result["frame"],"reels":result["reels"],"images":len(result["checks"]),"alphaCorners":result["alphas"]},ensure_ascii=False),flush=True)
    if show_image:
        img=page.screenshot(type="jpeg",quality=75,full_page=True,animations="disabled")
        print("STAKE_VISUAL_PREVIEW_START"+base64.b64encode(img).decode()+"STAKE_VISUAL_PREVIEW_END",flush=True)
    # Check every artwork, not just the mushroom, on the real animated strip.
    motion=page.evaluate("""async()=>{
      const col=document.querySelector('.column');
      const el=document.createElement('div');el.className='reel-scroll-viewport';
      const strip=document.createElement('div');strip.className='reel-scroll-strip';
      strip.style.setProperty('--reel-cell-height','80px');
      for(let id=0;id<12;id++){
        const tile=document.createElement('div');tile.className='reel-scroll-symbol';
        tile.innerHTML=symbolArtworkMarkup(id);
        strip.append(tile);
      }
      el.append(strip);col.append(el);
      const images=[...strip.querySelectorAll('img.jmSymbolArt')];
      await Promise.all(images.map(img=>img.decode().catch(()=>{})));
      const checks=images.map(im=>{
        const r=im.getBoundingClientRect(),c=im.closest('.reel-scroll-symbol').getBoundingClientRect();
        return {id:Number(im.dataset.symbolId),
          within:r.left>=c.left-1&&r.right<=c.right+1&&r.top>=c.top-1&&r.bottom<=c.bottom+1,
          loaded:im.complete&&im.naturalWidth>0,
          ratio:+Math.min(r.width/c.width,r.height/c.height).toFixed(3)};
      });
      el.remove();return checks;
    }""")
    assert len(motion)==12,(label,"not all scrolling symbols shown",motion)
    assert all(m["within"] and m["loaded"] and m["ratio"]<=.971 for m in motion),(label,"spinning symbol clipped",motion)
    print("ROLLING_12_SYMBOLS_OK",label,json.dumps(motion,ensure_ascii=False),flush=True)
    page.close()

with sync_playwright() as p:
    chromium=p.chromium.launch(headless=True,args=["--no-sandbox"])
    check_device(chromium,390,844,"Chromium iPhone compact",show_image=True)
    check_device(chromium,428,926,"Chromium iPhone large")
    check_device(chromium,360,780,"Chromium Android compact")
    chromium.close()
    # WebKit exercises Safari's rendering engine, where background/clip bugs
    # were originally reported on the user's iPhone.
    webkit=p.webkit.launch(headless=True)
    check_device(webkit,390,844,"WebKit iPhone compact")
    check_device(webkit,428,926,"WebKit iPhone large")
    webkit.close()
print("VISUAL_BROWSER_PASS 5x3 originals, 12 scrolling symbols, transparent corners, Chromium and WebKit",flush=True)
