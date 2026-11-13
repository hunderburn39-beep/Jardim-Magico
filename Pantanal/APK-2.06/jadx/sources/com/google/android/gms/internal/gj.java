package com.google.android.gms.internal;

import android.util.Base64;

/* JADX INFO: loaded from: classes.dex */
public final class gj {
    public static String d(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 0);
    }

    public static String e(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 10);
    }
}
