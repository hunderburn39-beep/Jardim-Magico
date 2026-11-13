package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
class bw {
    bw() {
    }

    static String a(String str, String str2) {
        return (str == null || str.isEmpty()) ? str2 : "[" + str + "] " + str2;
    }

    static String a(String str) {
        return "Disabling AdColonyPubServices due to error or exception: " + str;
    }

    static void a(String str, String str2, Exception exc, boolean z, boolean z2) {
        if (z2) {
            str2 = a(str2);
        }
        b(str, str2);
        if (exc != null && z) {
            bv.aN().a(exc, z2);
        }
    }

    static void b(String str, String str2) {
        u.g.b(a(str, str2));
    }

    static void b(String str, String str2, Exception exc, boolean z, boolean z2) {
        a(str, str2, exc, z, z2);
        exc.printStackTrace();
    }

    static void a(String str, String str2, Exception exc, boolean z) {
        u.e.b(a(str, str2));
        exc.printStackTrace();
        if (exc != null && z) {
            bv.aN().a(exc, false);
        }
    }

    static void a(String str, String str2, Exception exc) {
        u.b.b(a(str, str2));
        exc.printStackTrace();
    }

    static void c(String str, String str2) {
        u.e.b(a(str, str2));
    }

    static void a(String str, String str2, boolean z) {
        if (z) {
            u.d.b(a(str, str2));
        } else {
            u.c.b(a(str, str2));
        }
    }

    static void b(String str, String str2, boolean z) {
        if (z) {
            u.b.b(a(str, str2));
        } else {
            u.a.b(a(str, str2));
        }
    }
}
