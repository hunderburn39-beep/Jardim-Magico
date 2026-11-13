package com.google.android.gms.internal;

import android.util.Base64;

/* JADX INFO: loaded from: classes.dex */
class e implements n {
    e() {
    }

    @Override // com.google.android.gms.internal.n
    public String a(byte[] bArr, boolean z) {
        return Base64.encodeToString(bArr, z ? 11 : 2);
    }

    @Override // com.google.android.gms.internal.n
    public byte[] a(String str, boolean z) throws IllegalArgumentException {
        return Base64.decode(str, z ? 11 : 2);
    }
}
