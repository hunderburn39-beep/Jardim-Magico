package com.moat.analytics.mobile.tjy;

import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class x implements ba {
    final /* synthetic */ WeakReference a;
    final /* synthetic */ ap b;
    final /* synthetic */ v c;

    x(v vVar, WeakReference weakReference, ap apVar) {
        this.c = vVar;
        this.a = weakReference;
        this.b = apVar;
    }

    @Override // com.moat.analytics.mobile.tjy.ba
    public com.moat.analytics.mobile.tjy.base.functional.a a() {
        ViewGroup viewGroup = (ViewGroup) this.a.get();
        boolean zB = this.b.b();
        if (viewGroup == null) {
            if (zB) {
                Log.e("MoatFactory", "Target ViewGroup is null. Not creating WebAdTracker.");
            }
            return com.moat.analytics.mobile.tjy.base.functional.a.a();
        }
        if (zB) {
            Log.d("MoatFactory", "Creating WebAdTracker for " + viewGroup.getClass().getSimpleName() + "@" + viewGroup.hashCode());
        }
        com.moat.analytics.mobile.tjy.base.functional.a aVarA = this.c.a.a(viewGroup);
        boolean zC = aVarA.c();
        if (zB) {
            Log.e("MoatFactory", "WebView " + (zC ? "" : "not ") + "found inside of ad container.");
        }
        return com.moat.analytics.mobile.tjy.base.functional.a.a(new bj((WebView) aVarA.c(null), this.c.b, this.b));
    }
}
