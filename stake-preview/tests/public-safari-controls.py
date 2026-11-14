"""Teste ponta-a-ponta em WebKit: AUTO, PARAR, PASSAR e recuperação de créditos.
Créditos fictícios, contexto privado; sem tocar no motor de pagamentos.
"""
import json
import time
from playwright.sync_api import sync_playwright

URL="https://hunderburn39-beep.github.io/Jardim-Magico/stake-preview/?v=controls-safari-qa"

def snapshot(page):
    return page.evaluate("""()=>({balance,serial,busy,autoLeft,lastWin,bet:BETS[betIndex],
      pending:pendingRound && {id:pendingRound.id,mode:pendingRound.mode,payout:pendingRound.payout,cost:pendingRound.cost},
      history:historyRecords.map(r=>({id:r.id,mode:r.mode,bet:r.bet,cost:r.cost,payout:r.payout,bookPayout:r.book.payout}))
    })""")

def check_ledger(page,tag):
    v=snapshot(page)
    assert v["pending"] is None,(tag,"pending transaction not settled",v)
    history=v["history"]
    assert all(r["id"]==v["serial"]-i for i,r in enumerate(history)),(tag,"round numbers",v)
    assert all(abs(r["payout"]-round(r["bookPayout"]*r["bet"]/100+1e-8,2))<.011 for r in history),(tag,"round payments",v)
    expected=round(1000-sum(r["cost"] for r in history)+sum(r["payout"] for r in history),2)
    assert abs(v["balance"]-expected)<.011,(tag,"credits lost or duplicated",v,expected)
    print("LEDGER_OK",tag,json.dumps({"serial":v["serial"],"rounds":len(history),"balance":v["balance"],"expected":expected}),flush=True)
    return v

with sync_playwright() as p:
    browser=p.webkit.launch(headless=True)
    context=browser.new_context(viewport={"width":390,"height":844},device_scale_factor=2,
                                is_mobile=True,has_touch=True)
    page=context.new_page()
    errors=[]
    page.on("pageerror",lambda exc: errors.append(str(exc)))
    # Wait for publication of the Hold & Win PASSAR button before browser checks.
    ready=False
    for attempt in range(35):
        response=page.goto(URL+"&update="+str(attempt),wait_until="domcontentloaded",timeout=45000)
        if response and response.status==200 and 'id="holdSkip"' in page.content() and "V1.9.9 · CASCATAS SUAVES" in page.content() and "function cascadeShiftPlan(oldRemoved)" in page.content():
            ready=True
            break
        print("SAFARI_CONTROLS_PUBLICATION_WAIT",attempt+1,flush=True)
        time.sleep(8)
    assert ready,"Published Stake game does not yet contain the Hold & Win skip button"
    page.locator("#introPlay").click()
    page.locator("#turbo").click()
    assert page.locator("#turboLabel").inner_text()=="LIGADO","Turbo toggle not shown"

    # Prepare real generated base outcomes without bonus interruption so 5 AUTO
    # rounds can finish without manual bonus dialogs. Generated via the site's
    # own unmodified maths; only the test browser chooses when to play them.
    sample=page.evaluate("""()=>{
       const original=generate,queue=[];
       let attempts=0;
       while(queue.length<10 && attempts++<1000){
          const b=original('base');
          if(b.events.length<15 && b.events.every(e=>e.kind==='reels'))queue.push(b);
       }
       if(queue.length!==10)throw Error('Insufficient naturally generated rounds');
       window.__qaOriginalGenerate=original;
       window.__qaQueue=queue;
       generate=function(mode){
         if(mode==='base' && window.__qaQueue.length)return window.__qaQueue.shift();
         return window.__qaOriginalGenerate(mode);
       };
       return {choices:queue.length,attempts};
    }""")
    print("REAL_GENERATED_OUTCOMES",json.dumps(sample),flush=True)
    assert snapshot(page)["serial"]==0
    # 1) Five rounds automatically, with exact debit/credit and button state.
    page.locator("#auto").click()
    page.wait_for_function("!busy && autoLeft===0 && historyRecords.length===5",timeout=110000)
    assert page.locator("#autoHeading").inner_text()=="↻ AUTO"
    first=check_ledger(page,"five automatic rounds")
    assert first["serial"]==5
    print("AUTO_FIVE_PASS",flush=True)

    # 2) PARAR works even while the first round of another batch spins.
    # Disable turbo to ensure there is time to stop mid-animation.
    page.locator("#turbo").click()
    assert page.locator("#turboLabel").inner_text()=="DESL."
    page.locator("#auto").click()
    page.wait_for_function("busy && autoLeft===5",timeout=15000)
    assert page.locator("#autoHeading").inner_text()=="■ PARAR"
    page.locator("#auto").click()
    assert page.evaluate("autoLeft") == 0, "Stop button did not cancel remaining rounds"
    page.wait_for_function("!busy && historyRecords.length===6",timeout=30000)
    page.wait_for_timeout(600)
    assert page.evaluate("historyRecords.length") == 6, "Ghost AUTO spin after STOP"
    check_ledger(page,"auto cancelled during first spin")
    print("AUTO_STOP_PASS",flush=True)

    # 3) PASSAR finishes a normal spinning round without mutating its outcome.
    page.locator("#spin").click()
    page.wait_for_function("busy && pendingRound!==null",timeout=12000)
    expected=page.evaluate("({id:pendingRound.id,payout:pendingRound.payout})")
    page.locator("#skipBtn").click(timeout=10000)
    page.wait_for_function("!busy && historyRecords.length===7",timeout=25000)
    check_ledger(page,"skip spinning round")
    assert page.evaluate("historyRecords[0].payout")==expected["payout"]
    assert page.evaluate("historyRecords[0].id")==expected["id"]
    print("SKIP_NORMAL_PASS",json.dumps(expected),flush=True)

    # 4) Closing Safari during an already committed animation recovers the
    # exact same pending win, exactly once, even after repeated reloads.
    page.locator("#spin").click()
    page.wait_for_function("pendingRound!==null",timeout=12000)
    pending=page.evaluate("({id:pendingRound.id,cost:pendingRound.cost,payout:pendingRound.payout,balance})")
    page.reload(wait_until="networkidle")
    page.wait_for_function("pendingRound===null && historyRecords.length===8",timeout=15000)
    recovered=check_ledger(page,"reload during spinning")
    assert recovered["history"][0]["id"]==pending["id"]
    assert recovered["history"][0]["payout"]==pending["payout"]
    page.reload(wait_until="networkidle")
    second=check_ledger(page,"reload again never double-credits")
    assert second["serial"]==recovered["serial"]==8
    assert abs(second["balance"]-recovered["balance"])<.001
    print("INTERRUPT_RECOVERY_PASS",json.dumps(pending),flush=True)

    # 5) PASSAR inside each bought bonus must dismiss its manual-continue stage
    # and credit the book once; both interactions use the public game's buttons.
    page.locator("#introPlay").click()
    for name,button in [("free","#buyFree"),("hold","#buyHold")]:
        before=snapshot(page)
        page.locator(button).click()
        page.wait_for_function("busy && pendingRound!==null",timeout=12000)
        due=page.evaluate("({mode:pendingRound.mode,payout:pendingRound.payout,id:pendingRound.id})")
        skip_selector="#holdSkip" if name=="hold" else "#skipBtn"
        page.locator(skip_selector).click(timeout=12000)
        page.wait_for_function("!busy && pendingRound===null",timeout=35000)
        after=check_ledger(page,"skip bonus "+name)
        assert after["serial"]==before["serial"]+1,(name,"not one round")
        assert after["history"][0]["mode"]==name and after["history"][0]["payout"]==due["payout"]
        print("BONUS_SKIP_PASS",name,json.dumps(due),flush=True)

    assert not errors,("Uncaught Safari JavaScript errors",errors)
    print("SAFARI_CONTROLS_PASS",json.dumps({"rounds":snapshot(page)["serial"],"balance":snapshot(page)["balance"]}),flush=True)
    browser.close()
