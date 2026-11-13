package com.tapjoy.internal;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public final class y {
    private static String a = "pool.ntp.org";
    private static long b = 20000;
    private static volatile boolean c = false;
    private static volatile String d;
    private static volatile long e;
    private static volatile long f;
    private static volatile long g;
    private static volatile long h;
    private static volatile long i;

    static {
        a(false, "System", System.currentTimeMillis(), SystemClock.elapsedRealtime(), Long.MAX_VALUE);
    }

    private static synchronized void a(boolean z, String str, long j, long j2, long j3) {
        c = z;
        d = str;
        e = j;
        f = j2;
        g = j3;
        h = e - f;
        i = (SystemClock.elapsedRealtime() + h) - System.currentTimeMillis();
    }

    public static boolean a() {
        String str = a;
        long j = b;
        ff ffVar = new ff();
        if (!ffVar.a(str, (int) j)) {
            return false;
        }
        a(true, "SNTP", ffVar.a, ffVar.b, ffVar.c / 2);
        return true;
    }

    public static long b() {
        return SystemClock.elapsedRealtime() + h;
    }

    public static long a(long j) {
        return h + j;
    }

    public static boolean c() {
        return c;
    }
}
