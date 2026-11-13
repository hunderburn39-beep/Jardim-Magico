package com.tapjoy.internal;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class gi {
    private static String e;
    private static boolean f;
    public static final ScheduledExecutorService a = Executors.newScheduledThreadPool(1);
    public static final CountDownLatch b = new CountDownLatch(1);
    private static final Runnable d = new Runnable() { // from class: com.tapjoy.internal.gi.1
        @Override // java.lang.Runnable
        public final void run() {
            if (y.c()) {
                gi.b.countDown();
            } else if (y.a()) {
                gi.b.countDown();
            } else {
                gi.a.schedule(this, 300L, TimeUnit.SECONDS);
            }
        }
    };
    public static final CountDownLatch c = new CountDownLatch(1);

    public static void a() {
        a.execute(d);
    }

    public static void a(String str, boolean z) {
        e = str;
        f = z;
        c.countDown();
    }

    public static String b() {
        return e;
    }

    public static boolean c() {
        return f;
    }
}
