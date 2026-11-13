"""Safari/WebKit: jogadas reais de teste no Jardim Mágico publicado.
Usa exclusivamente créditos fictícios e um perfil isolado do navegador.
Não altera código, probabilidades ou contas do site.
"""
import json
from playwright.sync_api import sync_playwright

ROOT="https://hunderburn39-beep.github.io/Jardim-Magico/stake-preview/?v=safari-playback-ci-20261113"
def equal_amount(actual,expected,label):
    assert abs(float(actual)-float(expected))<0.011,(label,actual,expected)

def inspect_round(page,mode,previous_balance,previous_count):
    info=page.evaluate("""()=>({
      balance,serial,busy,
      history:historyRecords.map(x=>({id:x.id,mode:x.mode,bet:x.bet,cost:x.cost,payout:x.payout,book:x.book})),
      lastWin,pending:pendingRound
    })""")
    assert not info["busy"],(mode,"still processing")
    assert info["pending"] is None,(mode,"pending transaction not settled")
    assert len(info["history"])==previous_count+1,(mode,"history not committed",len(info["history"]))
    rec=info["history"][0]
    assert rec["mode"]==mode,(mode,"wrong recorded mode",rec["mode"])
    expected_payout=round(rec["book"]["payout"]*rec["bet"]/100+1e-8,2)
    equal_amount(rec["payout"],expected_payout,mode+" payout vs book")
    equal_amount(info["balance"],round(previous_balance-rec["cost"]+rec["payout"],2),mode+" balance")
    equal_amount(info["lastWin"],rec["payout"],mode+" latest win")
    assert rec["id"]==previous_count+1,(mode,"round ID out of order",rec["id"])
    print("ROUND_SETTLED_OK",mode,json.dumps({"id":rec["id"],"bet":rec["bet"],"cost":rec["cost"],"payout":rec["payout"],"balance":info["balance"],"events":len(rec["book"]["events"])},ensure_ascii=False),flush=True)
    return info

with sync_playwright() as p:
    browser=p.webkit.launch(headless=True)
    context=browser.new_context(viewport={"width":390,"height":844},device_scale_factor=2,
                                is_mobile=True,has_touch=True,reduced_motion="reduce")
    page=context.new_page()
    errors=[]
    page.on("pageerror",lambda exc:errors.append(str(exc)))
    response=page.goto(ROOT,wait_until="networkidle",timeout=45000)
    assert response and response.status==200,"Public Stake Preview not reachable"
    assert "assets/figuras-v199/joaninha-limpa.svg" in page.content(),"Public assets not updated"
    page.locator("#introPlay").click()
    page.locator("#turbo").click()
    assert page.locator("#turboLabel").inner_text()=="LIGADO"

    # Collect visual events as genuine UI animations unfold.
    page.evaluate("""()=>{
      window.__qaSeen={line:false,cascade:false,drop:false,freePanel:false,freeResult:false,holdPanel:false,holdResult:false,roundFinished:false};
      const seen=window.__qaSeen;
      const watch=()=>{
         seen.line ||= document.querySelector('#paylineSvg polyline')!==null && !document.querySelector('#linePrize').hidden;
         seen.cascade ||= !document.querySelector('#cascadeMeter').hidden;
         seen.drop ||= Number(document.querySelector('#dropNum').textContent)>=2;
         seen.freePanel ||= !document.querySelector('#freePanel').hidden;
         seen.freeResult ||= document.querySelector('#freeResultOverlay').classList.contains('show');
         seen.holdPanel ||= document.querySelector('#holdOverlay').classList.contains('show');
         seen.holdResult ||= !document.querySelector('#holdSummary').hidden;
         seen.roundFinished ||= document.querySelector('#reelFrame').classList.contains('round-finished');
      };
      new MutationObserver(watch).observe(document.body,{subtree:true,childList:true,attributes:true,characterData:true});
      watch();

      // Select a real, engine-generated example with a paying cascade for
      // deterministic COVERAGE of its visual sequence. This temporary browser-
      // only selection never touches production code or changes payout rules.
      const original=generate;
      let chosen=null,attempts=0;
      for(let i=0;i<600;i++){
         const book=original('base');attempts++;
         const reels=book.events.filter(e=>e.kind==='reels');
         if(book.events.length===reels.length && reels.length>=2 &&
            reels.some(e=>e.lines.length>0)){
           chosen=book;break;
         }
      }
      if(!chosen)throw Error('Could not find real cascading base round');
      window.__qaChoice=chosen;
      window.__qaChoiceAttempts=attempts;
      generate=function(mode){
         let book;
         if(mode==='base'&&window.__qaChoice){
            book=window.__qaChoice;window.__qaChoice=null;
         }else book=original(mode);
         window.__qaLastGenerated={mode,book};
         return book;
      };
    }""")
    initial=page.evaluate("balance")
    page.locator("#spin").click()
    page.wait_for_function("!busy && historyRecords.length===1",timeout=90000)
    base=inspect_round(page,"base",initial,0)
    seen=page.evaluate("window.__qaSeen")
    assert seen["line"] and seen["cascade"] and seen["drop"] and seen["roundFinished"],("Base cascade visuals missing",seen)
    print("BASE_VISUAL_OK",json.dumps({"attempts":page.evaluate("window.__qaChoiceAttempts"),"observed":seen},ensure_ascii=False),flush=True)

    # Validate free spins purchased through the real visible button.
    before=base["balance"]
    page.locator("#buyFree").click()
    page.wait_for_function("document.querySelector('#freeResultOverlay').classList.contains('show')",timeout=150000)
    free_end=page.evaluate("""()=>{
       const e=window.__qaLastGenerated.book.events.filter(e=>e.kind==='freeEnd');
       return {event:e[0],count:e.length,raw:document.querySelector('#freeResultRaw').textContent,
         multi:document.querySelector('#freeResultMulti').textContent,
         played:document.querySelector('#freeResultPlayed').textContent,
         total:document.querySelector('#freeResultTotal').textContent};
    }""")
    assert free_end["count"]==1 and free_end["event"]["played"]>=15,free_end
    assert free_end["multi"]=="×"+str(free_end["event"]["multi"]),free_end
    assert int(free_end["played"])==free_end["event"]["played"],free_end
    assert free_end["total"]!="0,00" or free_end["event"]["amount"]==0,free_end
    page.locator("#closeFree").click()
    page.wait_for_function("!busy && historyRecords.length===2",timeout=30000)
    free=inspect_round(page,"free",before,1)
    seen=page.evaluate("window.__qaSeen")
    assert seen["freePanel"] and seen["freeResult"],("Free visual sequence was not shown",seen)
    print("FREE_FEATURE_OK",json.dumps({"played":free_end["played"],"raw":free_end["raw"],"multi":free_end["multi"],"total":free_end["total"]},ensure_ascii=False),flush=True)

    # Hold & Win: full 4x4 / 3 attempts in modal, user confirms settlement.
    before=free["balance"]
    page.locator("#buyHold").click()
    page.wait_for_function("document.querySelector('#holdOverlay').classList.contains('show') && !document.querySelector('#closeHold').hidden",timeout=80000)
    hold_end=page.evaluate("""()=>{
      const e=window.__qaLastGenerated.book.events.filter(e=>e.kind==='holdEnd');
      return {count:e.length,event:e[0],cells:document.querySelectorAll('#holdGrid .coin-slot').length,
        summary:document.querySelector('#holdSummaryValue').textContent,
        lives:document.querySelector('#holdLives').getAttribute('aria-label')};
    }""")
    assert hold_end["count"]==1 and hold_end["cells"]==16,hold_end
    assert hold_end["event"]["grid"] and len(hold_end["event"]["grid"])==16,hold_end
    assert hold_end["summary"] and "0" in hold_end["summary"],hold_end
    page.locator("#closeHold").click()
    page.wait_for_function("!busy && historyRecords.length===3",timeout=30000)
    hold=inspect_round(page,"hold",before,2)
    seen=page.evaluate("window.__qaSeen")
    assert seen["holdPanel"] and seen["holdResult"],("Hold results not visible",seen)
    print("HOLD_FEATURE_OK",json.dumps({"filled":hold_end["event"]["filled"],"amount":hold_end["event"]["amount"],"cells":hold_end["cells"],"summary":hold_end["summary"]},ensure_ascii=False),flush=True)

    # Paytable, history search, symbol inspector and replay without a second charge.
    page.locator("#paytableBtn").click()
    assert page.locator("#paylineSheet .payline-card").count()==10,"Ten paylines not displayed"
    page.locator("#closePaytable").click()
    page.locator("#historyBtn").click()
    page.locator("#historySearch").fill("1")
    assert page.locator("#historyList [data-detail]").count()==1,"Search by round number failed"
    page.locator("#historyList [data-detail]").click()
    assert page.locator("#detailBoard .detail-cell").count()==15,"History symbol inspector not 5x3"
    page.locator("#closeDetail").click()
    before_replay=page.evaluate("({balance,serial,count:historyRecords.length})")
    page.locator("#historyBtn").click()
    page.locator("#historySearch").fill("1")
    page.locator("#historyList [data-replay]").click()
    page.wait_for_function("!busy && document.querySelector('#reelFrame').classList.contains('round-finished')",timeout=90000)
    after_replay=page.evaluate("({balance,serial,count:historyRecords.length})")
    assert before_replay==after_replay,("Replay changed test credits or records",before_replay,after_replay)
    print("HISTORY_REPLAY_OK",json.dumps(after_replay),flush=True)

    page.reload(wait_until="networkidle")
    persisted=page.evaluate("({balance,serial,count:historyRecords.length,pending:pendingRound})")
    assert persisted["pending"] is None and persisted["serial"]==3 and persisted["count"]==3,persisted
    equal_amount(persisted["balance"],hold["balance"],"Safari reload balance")
    assert not errors,("Browser JavaScript exceptions",errors)
    print("SAFARI_GAMEPLAY_PASS",json.dumps({"balance":persisted["balance"],"rounds":persisted["count"],"base":base["history"][0]["payout"],"free":free["history"][0]["payout"],"hold":hold["history"][0]["payout"],"seen":seen},ensure_ascii=False),flush=True)
    browser.close()
