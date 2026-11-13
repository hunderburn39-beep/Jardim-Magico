package com.tapjoy.internal;

import android.app.Activity;
import android.app.Application;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private static Application a;
    private static int b;
    private static final cc c = new cc();
    private static final Set d = Collections.synchronizedSet(new bc());
    private static final cc e = new cc();

    public static boolean b() {
        return b > 0;
    }

    public static Activity c() {
        Activity activity = (Activity) c.a();
        return activity != null ? activity : (Activity) cu.a(d.iterator());
    }

    public static void a(Activity activity) {
        c.a(activity);
    }

    public static synchronized void a(Application application) {
        if (a != application) {
            a = application;
        }
    }

    public static void b(Activity activity) {
        b++;
        c.a(activity);
        d.add(activity);
    }

    public static void c(Activity activity) {
        b--;
        c.a = null;
        d.remove(activity);
        if (b < 0) {
            b = 0;
        }
    }

    public static Activity a() {
        Activity activity = (Activity) e.a();
        if (activity == null) {
            return c();
        }
        return activity;
    }
}
