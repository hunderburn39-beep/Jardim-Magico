package com.tapjoy.internal;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public abstract class gf {
    protected static a a;
    private static gf b;

    public abstract void a(a aVar);

    public abstract boolean b();

    public static class a {
        public final String a;
        public final String b;
        public final long c = SystemClock.elapsedRealtime();
        public final eg d = new eg(60000);

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    public static void a(gf gfVar) {
        synchronized (gf.class) {
            b = gfVar;
            a aVar = a;
            if (aVar != null) {
                a = null;
                gfVar.a(aVar);
            }
        }
    }

    public static void a(String str, String str2) {
        synchronized (gf.class) {
            a aVar = new a(str, str2);
            if (b != null) {
                a = null;
                b.a(aVar);
            } else {
                a = aVar;
            }
        }
    }

    public static boolean c() {
        if (b != null && b.b()) {
            return true;
        }
        a aVar = a;
        return (aVar == null || aVar.d.a()) ? false : true;
    }
}
