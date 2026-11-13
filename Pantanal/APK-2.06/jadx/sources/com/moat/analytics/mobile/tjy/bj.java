package com.moat.analytics.mobile.tjy;

import android.util.Log;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes.dex */
class bj implements WebAdTracker {
    private final com.moat.analytics.mobile.tjy.base.functional.a a;
    private final ap b;

    bj(WebView webView, a aVar, ap apVar) {
        this.b = apVar;
        if (apVar.b()) {
            Log.d("MoatWebAdTracker", "In initialization method.");
        }
        if (webView != null) {
            this.a = com.moat.analytics.mobile.tjy.base.functional.a.a(new bi(webView, webView, false, aVar, apVar));
            return;
        }
        if (apVar.b()) {
            Log.e("MoatWebAdTracker", "WebView is null. Will not track.");
        }
        this.a = com.moat.analytics.mobile.tjy.base.functional.a.a();
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0042 A[Catch: b -> 0x0050, TRY_ENTER, TRY_LEAVE, TryCatch #0 {b -> 0x0050, blocks: (B:4:0x0009, B:5:0x0010, B:8:0x001a, B:15:0x0042), top: B:21:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:20:0x0058  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:8:0x001a A[Catch: b -> 0x0050, TRY_LEAVE, TryCatch #0 {b -> 0x0050, blocks: (B:4:0x0009, B:5:0x0010, B:8:0x001a, B:15:0x0042), top: B:21:0x0009 }] */
    @Override // com.moat.analytics.mobile.tjy.WebAdTracker
    public boolean track() {
        boolean zC;
        boolean zB = this.b.b();
        boolean z = false;
        if (zB) {
            try {
                Log.d("MoatWebAdTracker", "In track method.");
                if (!this.a.c()) {
                    zC = ((bh) this.a.b()).c();
                } else if (zB) {
                    Log.e("MoatWebAdTracker", "Internal tracker not available. Not tracking.");
                } else {
                    zC = false;
                }
                z = zC;
            } catch (com.moat.analytics.mobile.tjy.base.exception.b e) {
                com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            }
        } else {
            if (!this.a.c()) {
                zC = ((bh) this.a.b()).c();
            } else if (zB) {
                Log.e("MoatWebAdTracker", "Internal tracker not available. Not tracking.");
            } else {
                zC = false;
            }
            z = zC;
        }
        if (zB) {
            Log.d("MoatWebAdTracker", "Attempt to start tracking ad was " + (z ? "" : "un") + "successful.");
        }
        return z;
    }
}
