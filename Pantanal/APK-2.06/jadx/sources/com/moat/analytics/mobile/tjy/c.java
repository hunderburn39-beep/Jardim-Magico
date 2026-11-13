package com.moat.analytics.mobile.tjy;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class c implements a {
    private final WeakReference a;
    private final WeakReference b;
    private boolean c;
    private final ap d;
    private boolean e;

    c(Activity activity, ap apVar) {
        com.moat.analytics.mobile.tjy.base.asserts.a.a(activity);
        if (apVar.b()) {
            Log.d("MoatActivityState", "Listening to Activity: " + (activity != null ? activity.getClass() + "@" + activity.hashCode() : "null"));
        }
        this.a = new WeakReference(activity.getApplication());
        this.b = new WeakReference(activity);
        this.d = apVar;
        this.c = false;
    }

    @Override // com.moat.analytics.mobile.tjy.a
    public boolean a() {
        return this.e;
    }

    @Override // com.moat.analytics.mobile.tjy.a
    public void b() {
        if (this.c) {
            return;
        }
        ((Application) this.a.get()).registerActivityLifecycleCallbacks(new e(this));
    }

    @Override // com.moat.analytics.mobile.tjy.a
    public Activity c() {
        return (Activity) this.b.get();
    }
}
