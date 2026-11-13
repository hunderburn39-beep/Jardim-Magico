package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public final class ko {
    private final int adT;
    private final byte[] buffer;
    private int position;

    public static class a extends IOException {
        a(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    private ko(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.position = i;
        this.adT = i + i2;
    }

    public static int A(long j) {
        return D(j);
    }

    public static int B(long j) {
        return D(E(j));
    }

    public static int D(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static int E(boolean z) {
        return 1;
    }

    public static long E(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int b(int i, kt ktVar) {
        return cZ(i) + c(ktVar);
    }

    public static int b(int i, boolean z) {
        return cZ(i) + E(z);
    }

    public static ko b(byte[] bArr, int i, int i2) {
        return new ko(bArr, i, i2);
    }

    public static int c(int i, float f) {
        return cZ(i) + e(f);
    }

    public static int c(kt ktVar) {
        int iC = ktVar.c();
        return iC + db(iC);
    }

    public static int cX(int i) {
        if (i >= 0) {
            return db(i);
        }
        return 10;
    }

    public static int cZ(int i) {
        return db(kw.l(i, 0));
    }

    public static int cf(String str) {
        try {
            byte[] bytes = str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
            return bytes.length + db(bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int d(int i, long j) {
        return cZ(i) + A(j);
    }

    public static int db(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return ((-268435456) & i) == 0 ? 4 : 5;
    }

    public static int e(float f) {
        return 4;
    }

    public static int e(int i, long j) {
        return cZ(i) + B(j);
    }

    public static int g(int i, String str) {
        return cZ(i) + cf(str);
    }

    public static int j(int i, int i2) {
        return cZ(i) + cX(i2);
    }

    public static ko o(byte[] bArr) {
        return b(bArr, 0, bArr.length);
    }

    public void C(long j) throws IOException {
        while (((-128) & j) != 0) {
            cY((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            j >>>= 7;
        }
        cY((int) j);
    }

    public void D(boolean z) throws IOException {
        cY(z ? 1 : 0);
    }

    public void a(int i, kt ktVar) throws IOException {
        k(i, 2);
        b(ktVar);
    }

    public void a(int i, boolean z) throws IOException {
        k(i, 0);
        D(z);
    }

    public void b(byte b) throws IOException {
        if (this.position == this.adT) {
            throw new a(this.position, this.adT);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = b;
    }

    public void b(int i, float f) throws IOException {
        k(i, 5);
        d(f);
    }

    public void b(int i, long j) throws IOException {
        k(i, 0);
        y(j);
    }

    public void b(int i, String str) throws IOException {
        k(i, 2);
        ce(str);
    }

    public void b(kt ktVar) throws IOException {
        da(ktVar.mF());
        ktVar.a(this);
    }

    public void c(int i, long j) throws IOException {
        k(i, 0);
        z(j);
    }

    public void c(byte[] bArr, int i, int i2) throws IOException {
        if (this.adT - this.position < i2) {
            throw new a(this.position, this.adT);
        }
        System.arraycopy(bArr, i, this.buffer, this.position, i2);
        this.position += i2;
    }

    public void cW(int i) throws IOException {
        if (i >= 0) {
            da(i);
        } else {
            C(i);
        }
    }

    public void cY(int i) throws IOException {
        b((byte) i);
    }

    public void ce(String str) throws IOException {
        byte[] bytes = str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
        da(bytes.length);
        p(bytes);
    }

    public void d(float f) throws IOException {
        dc(Float.floatToIntBits(f));
    }

    public void da(int i) throws IOException {
        while ((i & (-128)) != 0) {
            cY((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
        }
        cY(i);
    }

    public void dc(int i) throws IOException {
        cY(i & MotionEventCompat.ACTION_MASK);
        cY((i >> 8) & MotionEventCompat.ACTION_MASK);
        cY((i >> 16) & MotionEventCompat.ACTION_MASK);
        cY((i >> 24) & MotionEventCompat.ACTION_MASK);
    }

    public void i(int i, int i2) throws IOException {
        k(i, 0);
        cW(i2);
    }

    public void k(int i, int i2) throws IOException {
        da(kw.l(i, i2));
    }

    public int mv() {
        return this.adT - this.position;
    }

    public void mw() {
        if (mv() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void p(byte[] bArr) throws IOException {
        c(bArr, 0, bArr.length);
    }

    public void y(long j) throws IOException {
        C(j);
    }

    public void z(long j) throws IOException {
        C(E(j));
    }
}
