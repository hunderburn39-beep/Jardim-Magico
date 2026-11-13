package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class q implements o {
    private ko kk;
    private byte[] kl;
    private final int km;

    public q(int i) {
        this.km = i;
        reset();
    }

    @Override // com.google.android.gms.internal.o
    public void b(int i, long j) throws IOException {
        this.kk.b(i, j);
    }

    @Override // com.google.android.gms.internal.o
    public void b(int i, String str) throws IOException {
        this.kk.b(i, str);
    }

    @Override // com.google.android.gms.internal.o
    public void reset() {
        this.kl = new byte[this.km];
        this.kk = ko.o(this.kl);
    }

    @Override // com.google.android.gms.internal.o
    public byte[] z() throws IOException {
        int iMv = this.kk.mv();
        if (iMv < 0) {
            throw new IOException();
        }
        if (iMv == 0) {
            return this.kl;
        }
        byte[] bArr = new byte[this.kl.length - iMv];
        System.arraycopy(this.kl, 0, bArr, 0, bArr.length);
        return bArr;
    }
}
