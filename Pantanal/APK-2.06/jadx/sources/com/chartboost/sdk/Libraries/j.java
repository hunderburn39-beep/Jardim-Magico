package com.chartboost.sdk.Libraries;

import android.app.Activity;
import android.content.Context;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class j extends WeakReference<Activity> {
    private static j b;
    private final int a;

    private j(Activity activity) {
        super(activity);
        this.a = activity.hashCode();
    }

    public static j a(Activity activity) {
        if (b == null || b.a != activity.hashCode()) {
            b = new j(activity);
        }
        return b;
    }

    public boolean b(Activity activity) {
        return activity != null && activity.hashCode() == this.a;
    }

    public int a() {
        return this.a;
    }

    public int hashCode() {
        return a();
    }

    public Context b() {
        Context context = (Context) get();
        if (context == null) {
            return com.chartboost.sdk.i.m;
        }
        return context;
    }
}
