package com.adcolony.sdk;

import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: loaded from: classes.dex */
class i implements ac {
    i() {
        a.a("CustomMessage.controller_send", this);
    }

    @Override // com.adcolony.sdk.ac
    public void a(z zVar) {
        final String strB = s.b(zVar.b, "type");
        final String strB2 = s.b(zVar.b, TJAdUnitConstants.String.MESSAGE);
        if (a.l != null) {
            a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.i.1
                @Override // java.lang.Runnable
                public void run() {
                    u.d.b("Received custom message " + strB2 + " of type " + strB);
                    try {
                        AdColonyCustomMessageListener adColonyCustomMessageListener = a.m.u.get(strB);
                        if (adColonyCustomMessageListener != null) {
                            adColonyCustomMessageListener.onAdColonyCustomMessage(new AdColonyCustomMessage(strB, strB2));
                        }
                    } catch (RuntimeException e) {
                    }
                }
            });
        }
    }
}
