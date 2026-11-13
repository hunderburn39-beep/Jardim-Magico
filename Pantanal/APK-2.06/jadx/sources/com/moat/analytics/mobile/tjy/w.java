package com.moat.analytics.mobile.tjy;

import android.util.Log;
import android.webkit.WebView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class w implements ba {
    final /* synthetic */ WeakReference a;
    final /* synthetic */ ap b;
    final /* synthetic */ v c;

    w(v vVar, WeakReference weakReference, ap apVar) {
        this.c = vVar;
        this.a = weakReference;
        this.b = apVar;
    }

    @Override // com.moat.analytics.mobile.tjy.ba
    public com.moat.analytics.mobile.tjy.base.functional.a a() {
        WebView webView = (WebView) this.a.get();
        boolean zB = this.b.b();
        if (webView == null) {
            if (zB) {
                Log.e("MoatFactory", "Target ViewGroup is null. Not creating WebAdTracker.");
            }
            return com.moat.analytics.mobile.tjy.base.functional.a.a();
        }
        if (zB) {
            Log.d("MoatFactory", "Creating WebAdTracker for " + webView.getClass().getSimpleName() + "@" + webView.hashCode());
        }
        return com.moat.analytics.mobile.tjy.base.functional.a.a(new bj(webView, this.c.b, this.b));
    }
}
