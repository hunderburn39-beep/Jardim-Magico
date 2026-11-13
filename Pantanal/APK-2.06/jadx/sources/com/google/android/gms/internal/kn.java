package com.google.android.gms.internal;

import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class kn {
    private int adK;
    private int adL;
    private int adM;
    private int adN;
    private int adO;
    private int adQ;
    private final byte[] buffer;
    private int adP = Integer.MAX_VALUE;
    private int adR = 64;
    private int adS = 67108864;

    private kn(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.adK = i;
        this.adL = i + i2;
        this.adN = i;
    }

    public static kn a(byte[] bArr, int i, int i2) {
        return new kn(bArr, i, i2);
    }

    private void mr() {
        this.adL += this.adM;
        int i = this.adL;
        if (i <= this.adP) {
            this.adM = 0;
        } else {
            this.adM = i - this.adP;
            this.adL -= this.adM;
        }
    }

    public static kn n(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static long x(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public void a(kt ktVar) throws IOException {
        int iMn = mn();
        if (this.adQ >= this.adR) {
            throw ks.mE();
        }
        int iCR = cR(iMn);
        this.adQ++;
        ktVar.b(this);
        cP(0);
        this.adQ--;
        cS(iCR);
    }

    public void a(kt ktVar, int i) throws IOException {
        if (this.adQ >= this.adR) {
            throw ks.mE();
        }
        this.adQ++;
        ktVar.b(this);
        cP(kw.l(i, 4));
        this.adQ--;
    }

    public void cP(int i) throws ks {
        if (this.adO != i) {
            throw ks.mC();
        }
    }

    public boolean cQ(int i) throws IOException {
        switch (kw.de(i)) {
            case 0:
                mk();
                return true;
            case 1:
                mq();
                return true;
            case 2:
                cV(mn());
                return true;
            case 3:
                mi();
                cP(kw.l(kw.df(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                mp();
                return true;
            default:
                throw ks.mD();
        }
    }

    public int cR(int i) throws ks {
        if (i < 0) {
            throw ks.mz();
        }
        int i2 = this.adN + i;
        int i3 = this.adP;
        if (i2 > i3) {
            throw ks.my();
        }
        this.adP = i2;
        mr();
        return i3;
    }

    public void cS(int i) {
        this.adP = i;
        mr();
    }

    public void cT(int i) {
        if (i > this.adN - this.adK) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.adN - this.adK));
        }
        if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        }
        this.adN = this.adK + i;
    }

    public byte[] cU(int i) throws IOException {
        if (i < 0) {
            throw ks.mz();
        }
        if (this.adN + i > this.adP) {
            cV(this.adP - this.adN);
            throw ks.my();
        }
        if (i > this.adL - this.adN) {
            throw ks.my();
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, this.adN, bArr, 0, i);
        this.adN += i;
        return bArr;
    }

    public void cV(int i) throws IOException {
        if (i < 0) {
            throw ks.mz();
        }
        if (this.adN + i > this.adP) {
            cV(this.adP - this.adN);
            throw ks.my();
        }
        if (i > this.adL - this.adN) {
            throw ks.my();
        }
        this.adN += i;
    }

    public int getPosition() {
        return this.adN - this.adK;
    }

    public byte[] h(int i, int i2) {
        if (i2 == 0) {
            return kw.aeh;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.adK + i, bArr, 0, i2);
        return bArr;
    }

    public int mh() throws IOException {
        if (mt()) {
            this.adO = 0;
            return 0;
        }
        this.adO = mn();
        if (this.adO == 0) {
            throw ks.mB();
        }
        return this.adO;
    }

    public void mi() throws IOException {
        int iMh;
        do {
            iMh = mh();
            if (iMh == 0) {
                return;
            }
        } while (cQ(iMh));
    }

    public long mj() throws IOException {
        return mo();
    }

    public int mk() throws IOException {
        return mn();
    }

    public boolean ml() throws IOException {
        return mn() != 0;
    }

    public long mm() throws IOException {
        return x(mo());
    }

    public int mn() throws IOException {
        byte bMu = mu();
        if (bMu >= 0) {
            return bMu;
        }
        int i = bMu & 127;
        byte bMu2 = mu();
        if (bMu2 >= 0) {
            return i | (bMu2 << 7);
        }
        int i2 = i | ((bMu2 & 127) << 7);
        byte bMu3 = mu();
        if (bMu3 >= 0) {
            return i2 | (bMu3 << 14);
        }
        int i3 = i2 | ((bMu3 & 127) << 14);
        byte bMu4 = mu();
        if (bMu4 >= 0) {
            return i3 | (bMu4 << 21);
        }
        int i4 = i3 | ((bMu4 & 127) << 21);
        byte bMu5 = mu();
        int i5 = i4 | (bMu5 << 28);
        if (bMu5 >= 0) {
            return i5;
        }
        for (int i6 = 0; i6 < 5; i6++) {
            if (mu() >= 0) {
                return i5;
            }
        }
        throw ks.mA();
    }

    public long mo() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bMu = mu();
            j |= ((long) (bMu & 127)) << i;
            if ((bMu & 128) == 0) {
                return j;
            }
        }
        throw ks.mA();
    }

    public int mp() throws IOException {
        return (mu() & 255) | ((mu() & 255) << 8) | ((mu() & 255) << 16) | ((mu() & 255) << 24);
    }

    public long mq() throws IOException {
        byte bMu = mu();
        return ((((long) mu()) & 255) << 8) | (((long) bMu) & 255) | ((((long) mu()) & 255) << 16) | ((((long) mu()) & 255) << 24) | ((((long) mu()) & 255) << 32) | ((((long) mu()) & 255) << 40) | ((((long) mu()) & 255) << 48) | ((((long) mu()) & 255) << 56);
    }

    public int ms() {
        if (this.adP == Integer.MAX_VALUE) {
            return -1;
        }
        return this.adP - this.adN;
    }

    public boolean mt() {
        return this.adN == this.adL;
    }

    public byte mu() throws IOException {
        if (this.adN == this.adL) {
            throw ks.my();
        }
        byte[] bArr = this.buffer;
        int i = this.adN;
        this.adN = i + 1;
        return bArr[i];
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(mp());
    }

    public String readString() throws IOException {
        int iMn = mn();
        if (iMn > this.adL - this.adN || iMn <= 0) {
            return new String(cU(iMn), AsyncHttpResponseHandler.DEFAULT_CHARSET);
        }
        String str = new String(this.buffer, this.adN, iMn, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        this.adN = iMn + this.adN;
        return str;
    }
}
