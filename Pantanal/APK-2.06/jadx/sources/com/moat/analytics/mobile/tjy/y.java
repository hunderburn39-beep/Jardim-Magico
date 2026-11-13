package com.moat.analytics.mobile.tjy;

import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class y implements ba {
    final /* synthetic */ WeakReference a;
    final /* synthetic */ ap b;
    final /* synthetic */ String c;
    final /* synthetic */ v d;

    y(v vVar, WeakReference weakReference, ap apVar, String str) {
        this.d = vVar;
        this.a = weakReference;
        this.b = apVar;
        this.c = str;
    }

    @Override // com.moat.analytics.mobile.tjy.ba
    public com.moat.analytics.mobile.tjy.base.functional.a a() {
        View view = (View) this.a.get();
        if (view == null) {
            if (this.b.b()) {
                Log.e("MoatFactory", "Target view is null. Not creating NativeDisplayTracker.");
            }
            return com.moat.analytics.mobile.tjy.base.functional.a.a();
        }
        if (this.b.b()) {
            Log.d("MoatFactory", "Creating NativeDisplayTracker for " + view.getClass().getSimpleName() + "@" + view.hashCode());
        }
        return com.moat.analytics.mobile.tjy.base.functional.a.a(new af(view, this.c, this.d.b, this.b));
    }
}
