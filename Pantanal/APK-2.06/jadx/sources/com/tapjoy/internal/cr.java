package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class cr {
    public static String a(String str) {
        return str == null ? "" : str;
    }

    public static String b(String str) {
        if (c(str)) {
            return null;
        }
        return str;
    }

    public static boolean c(String str) {
        return str == null || str.length() == 0;
    }
}
