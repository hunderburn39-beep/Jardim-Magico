package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class cq {
    public static void a(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static Object a(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
}
