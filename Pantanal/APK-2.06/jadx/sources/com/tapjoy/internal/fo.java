package com.tapjoy.internal;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public abstract class fo {
    public static fo a;

    public abstract void a(Activity activity);

    public static void b(Activity activity) {
        if (a != null) {
            a.a(activity);
        }
    }
}
