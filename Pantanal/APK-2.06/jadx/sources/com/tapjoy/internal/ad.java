package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class ad {
    public static Object a(bf bfVar) {
        int i = 1;
        while (true) {
            try {
                return bfVar.call();
            } catch (OutOfMemoryError e) {
                if (i >= 10) {
                    throw e;
                }
                System.gc();
                i++;
            }
        }
    }
}
