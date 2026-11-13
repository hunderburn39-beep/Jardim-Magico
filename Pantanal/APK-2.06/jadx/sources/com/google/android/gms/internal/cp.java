package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchaseListener;

/* JADX INFO: loaded from: classes.dex */
public final class cp extends co.a {
    private final InAppPurchaseListener mp;

    public cp(InAppPurchaseListener inAppPurchaseListener) {
        this.mp = inAppPurchaseListener;
    }

    @Override // com.google.android.gms.internal.co
    public void a(cn cnVar) {
        this.mp.onInAppPurchaseRequested(new cq(cnVar));
    }
}
