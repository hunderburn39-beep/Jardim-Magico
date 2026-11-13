package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
public final class gn implements gl {
    private static gn Er;

    public static synchronized gl ft() {
        if (Er == null) {
            Er = new gn();
        }
        return Er;
    }

    @Override // com.google.android.gms.internal.gl
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
