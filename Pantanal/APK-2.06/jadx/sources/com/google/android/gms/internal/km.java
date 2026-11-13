package com.google.android.gms.internal;

import android.support.v4.view.MotionEventCompat;

/* JADX INFO: loaded from: classes.dex */
public class km {
    private final byte[] adH = new byte[256];
    private int adI;
    private int adJ;

    public km(byte[] bArr) {
        for (int i = 0; i < 256; i++) {
            this.adH[i] = (byte) i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            i2 = (i2 + this.adH[i3] + bArr[i3 % bArr.length]) & MotionEventCompat.ACTION_MASK;
            byte b = this.adH[i3];
            this.adH[i3] = this.adH[i2];
            this.adH[i2] = b;
        }
        this.adI = 0;
        this.adJ = 0;
    }

    public void m(byte[] bArr) {
        int i = this.adI;
        int i2 = this.adJ;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & MotionEventCompat.ACTION_MASK;
            i2 = (i2 + this.adH[i]) & MotionEventCompat.ACTION_MASK;
            byte b = this.adH[i];
            this.adH[i] = this.adH[i2];
            this.adH[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.adH[(this.adH[i] + this.adH[i2]) & MotionEventCompat.ACTION_MASK]);
        }
        this.adI = i;
        this.adJ = i2;
    }
}
