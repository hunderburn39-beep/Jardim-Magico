package com.adcolony.sdk;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ca extends bh {
    private static ca n;

    static ca c() {
        if (n == null) {
            n = new ca();
        }
        return n;
    }

    private ca() {
    }

    @Override // com.adcolony.sdk.bh
    protected String a() {
        return "AdColonyPubServices";
    }

    @Override // com.adcolony.sdk.bh
    protected void a(Map<String, Object> map) {
    }

    @Override // com.adcolony.sdk.bh
    protected void b(Map<String, Object> map) {
    }

    @Override // com.adcolony.sdk.bh
    protected void b() {
    }
}
