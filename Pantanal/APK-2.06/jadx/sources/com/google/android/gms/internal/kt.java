package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class kt {
    protected volatile int adY = -1;

    public static final <T extends kt> T a(T t, byte[] bArr) throws ks {
        return (T) b(t, bArr, 0, bArr.length);
    }

    public static final void a(kt ktVar, byte[] bArr, int i, int i2) {
        try {
            ko koVarB = ko.b(bArr, i, i2);
            ktVar.a(koVarB);
            koVarB.mw();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends kt> T b(T t, byte[] bArr, int i, int i2) throws ks {
        try {
            kn knVarA = kn.a(bArr, i, i2);
            t.b(knVarA);
            knVarA.cP(0);
            return t;
        } catch (ks e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final byte[] d(kt ktVar) {
        byte[] bArr = new byte[ktVar.c()];
        a(ktVar, bArr, 0, bArr.length);
        return bArr;
    }

    public void a(ko koVar) throws IOException {
    }

    public abstract kt b(kn knVar) throws IOException;

    public int c() {
        int iMx = mx();
        this.adY = iMx;
        return iMx;
    }

    public int mF() {
        if (this.adY < 0) {
            c();
        }
        return this.adY;
    }

    protected int mx() {
        return 0;
    }

    public String toString() {
        return ku.e(this);
    }
}
