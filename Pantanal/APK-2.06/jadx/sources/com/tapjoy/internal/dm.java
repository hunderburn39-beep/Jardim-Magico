package com.tapjoy.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class dm {
    final int a;
    int b;
    final OutputStream c;
    private final byte[] d;
    private int e;

    dm(byte[] bArr, int i) {
        this.e = 0;
        this.c = null;
        this.d = bArr;
        this.b = 0;
        this.a = i + 0;
    }

    private dm(OutputStream outputStream, byte[] bArr) {
        this.e = 0;
        this.c = outputStream;
        this.d = bArr;
        this.b = 0;
        this.a = bArr.length;
    }

    public static dm a(OutputStream outputStream, int i) {
        return new dm(outputStream, new byte[i]);
    }

    public final void a(int i, double d) throws IOException {
        c(i, 1);
        b(Double.doubleToRawLongBits(d));
    }

    public final void a(int i, long j) throws IOException {
        c(i, 0);
        a(j);
    }

    public final void a(int i, int i2) throws IOException {
        c(i, 0);
        f(i2);
    }

    public final void a(int i, boolean z) throws IOException {
        c(i, 0);
        c(z ? 1 : 0);
    }

    public final void a(int i, du duVar) throws IOException {
        c(i, 2);
        e(duVar.b());
        duVar.a(this);
    }

    public final void a(int i, dk dkVar) throws IOException {
        c(i, 2);
        a(dkVar);
    }

    public final void a(int i) throws IOException {
        c(1, 0);
        f(i);
    }

    private void f(int i) throws IOException {
        if (i >= 0) {
            e(i);
        } else {
            a(i);
        }
    }

    public final void a(dk dkVar) throws IOException {
        e(dkVar.a());
        c(dkVar);
    }

    public static int b(int i, long j) {
        int i2;
        int iD = d(i);
        if (((-128) & j) == 0) {
            i2 = 1;
        } else if (((-16384) & j) == 0) {
            i2 = 2;
        } else if (((-2097152) & j) == 0) {
            i2 = 3;
        } else if (((-268435456) & j) == 0) {
            i2 = 4;
        } else if (((-34359738368L) & j) == 0) {
            i2 = 5;
        } else if (((-4398046511104L) & j) == 0) {
            i2 = 6;
        } else if (((-562949953421312L) & j) == 0) {
            i2 = 7;
        } else if (((-72057594037927936L) & j) == 0) {
            i2 = 8;
        } else {
            i2 = (Long.MIN_VALUE & j) == 0 ? 9 : 10;
        }
        return i2 + iD;
    }

    public static int b(int i, int i2) {
        return d(i) + g(i2);
    }

    public static int b(int i, du duVar) {
        int iD = d(i);
        int iB = duVar.b();
        return iD + iB + h(iB);
    }

    public static int b(int i, dk dkVar) {
        return d(i) + b(dkVar);
    }

    public static int b(int i) {
        return d(1) + g(i);
    }

    private static int g(int i) {
        if (i >= 0) {
            return h(i);
        }
        return 10;
    }

    public static int b(dk dkVar) {
        return h(dkVar.a()) + dkVar.a();
    }

    private void b() throws IOException {
        if (this.c == null) {
            throw new a();
        }
        this.c.write(this.d, 0, this.b);
        this.b = 0;
    }

    public final void a() throws IOException {
        if (this.c != null) {
            b();
        }
    }

    public static class a extends IOException {
        a() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    public final void c(int i) throws IOException {
        byte b = (byte) i;
        if (this.b == this.a) {
            b();
        }
        byte[] bArr = this.d;
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = b;
        this.e++;
    }

    public final void c(dk dkVar) throws IOException {
        int iA = dkVar.a();
        if (this.a - this.b >= iA) {
            dkVar.b(this.d, 0, this.b, iA);
            this.b += iA;
        } else {
            int i = this.a - this.b;
            dkVar.b(this.d, 0, this.b, i);
            int i2 = i + 0;
            iA -= i;
            this.b = this.a;
            this.e = i + this.e;
            b();
            if (iA <= this.a) {
                dkVar.b(this.d, i2, 0, iA);
                this.b = iA;
            } else {
                OutputStream outputStream = this.c;
                if (i2 < 0) {
                    throw new IndexOutOfBoundsException(new StringBuilder(30).append("Source offset < 0: ").append(i2).toString());
                }
                if (iA < 0) {
                    throw new IndexOutOfBoundsException(new StringBuilder(23).append("Length < 0: ").append(iA).toString());
                }
                if (i2 + iA > dkVar.a()) {
                    throw new IndexOutOfBoundsException(new StringBuilder(39).append("Source end offset exceeded: ").append(iA + i2).toString());
                }
                if (iA > 0) {
                    dkVar.a(outputStream, i2, iA);
                }
            }
        }
        this.e = iA + this.e;
    }

    private void c(int i, int i2) throws IOException {
        e(ec.a(i, i2));
    }

    public static int d(int i) {
        return h(ec.a(i, 0));
    }

    public final void e(int i) throws IOException {
        while ((i & (-128)) != 0) {
            c((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
        }
        c(i);
    }

    private static int h(int i) {
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

    public final void a(long j) throws IOException {
        while (((-128) & j) != 0) {
            c((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            j >>>= 7;
        }
        c((int) j);
    }

    public final void b(long j) throws IOException {
        c(((int) j) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 8)) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 16)) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 24)) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 32)) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 40)) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 48)) & MotionEventCompat.ACTION_MASK);
        c(((int) (j >> 56)) & MotionEventCompat.ACTION_MASK);
    }
}
