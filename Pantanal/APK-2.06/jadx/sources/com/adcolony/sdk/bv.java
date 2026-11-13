package com.adcolony.sdk;

import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class bv extends bg {
    private static bv ap;

    static synchronized bv aN() {
        if (ap == null) {
            ap = new bv();
        }
        return ap;
    }

    private bv() {
        f();
    }

    @Override // com.adcolony.sdk.bg
    String a() {
        return "AdColonyPubServices";
    }

    @Override // com.adcolony.sdk.bg
    ArrayList<String> b() {
        return new ArrayList<>();
    }

    @Override // com.adcolony.sdk.bg
    void a(String str) {
    }

    @Override // com.adcolony.sdk.bg
    void a(Map<String, Object> map) {
    }

    @Override // com.adcolony.sdk.bg
    void c() {
        aN().c.post(new Runnable() { // from class: com.adcolony.sdk.bv.1
            @Override // java.lang.Runnable
            public void run() {
                if (bv.this.z()) {
                    bv.this.n.a(true);
                }
            }
        });
    }

    @Override // com.adcolony.sdk.bg
    int d() {
        return aN().p.H.size();
    }

    @Override // com.adcolony.sdk.bg
    boolean e() {
        return aN().y;
    }

    @Override // com.adcolony.sdk.bg
    void b(String str) {
        if (ck.f() && !ap()) {
            try {
                aN().aB().e(str);
            } catch (Exception e) {
                a(e, "YvolverImpl.sendCommand");
            }
        }
    }
}
