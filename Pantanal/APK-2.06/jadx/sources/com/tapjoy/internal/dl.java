package com.tapjoy.internal;

import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class dl {
    private final byte[] a;
    private final boolean b;
    private int c;
    private int d;
    private int e;
    private final InputStream f;
    private int g;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private a n;

    interface a {
    }

    public static dl a(byte[] bArr, int i) {
        dl dlVar = new dl(bArr, i);
        try {
            dlVar.b(i);
            return dlVar;
        } catch (dq e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final void a(int i) throws dq {
        if (this.g != i) {
            throw dq.e();
        }
    }

    public final boolean a(int i, dm dmVar) throws IOException {
        int iA;
        switch (ec.a(i)) {
            case 0:
                long jG = g();
                dmVar.e(i);
                dmVar.a(jG);
                return true;
            case 1:
                long jH = h();
                dmVar.e(i);
                dmVar.b(jH);
                return true;
            case 2:
                dk dkVarE = e();
                dmVar.e(i);
                dmVar.a(dkVarE);
                return true;
            case 3:
                dmVar.e(i);
                do {
                    iA = a();
                    if (iA != 0) {
                    }
                    int iA2 = ec.a(ec.b(i), 4);
                    a(iA2);
                    dmVar.e(iA2);
                    return true;
                } while (a(iA, dmVar));
                int iA3 = ec.a(ec.b(i), 4);
                a(iA3);
                dmVar.e(iA3);
                return true;
            case 4:
                return false;
            case 5:
                int i2 = this.e;
                if (this.c - i2 < 4) {
                    c(4);
                    i2 = this.e;
                }
                byte[] bArr = this.a;
                this.e = i2 + 4;
                int i3 = ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
                dmVar.e(i);
                dmVar.c(i3 & MotionEventCompat.ACTION_MASK);
                dmVar.c((i3 >> 8) & MotionEventCompat.ACTION_MASK);
                dmVar.c((i3 >> 16) & MotionEventCompat.ACTION_MASK);
                dmVar.c((i3 >> 24) & MotionEventCompat.ACTION_MASK);
                return true;
            default:
                throw dq.f();
        }
    }

    public final long b() {
        return g();
    }

    public final int c() {
        return f();
    }

    public final boolean d() {
        return g() != 0;
    }

    public final du a(dw dwVar, dn dnVar) throws dq {
        int iF = f();
        if (this.k >= this.l) {
            throw dq.g();
        }
        int iB = b(iF);
        this.k++;
        du duVar = (du) dwVar.a(this, dnVar);
        a(0);
        this.k--;
        this.j = iB;
        j();
        return duVar;
    }

    public final dk e() {
        int iF = f();
        if (iF <= this.c - this.e && iF > 0) {
            dk djVar = (this.b && this.h) ? new dj(this.a, this.e, iF) : dk.a(this.a, this.e, iF);
            this.e = iF + this.e;
            return djVar;
        }
        if (iF == 0) {
            return dk.b;
        }
        return new dt(e(iF));
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0087 A[PHI: r2
      0x0087: PHI (r2v9 int) = (r2v8 int), (r2v11 int), (r2v13 int) binds: [B:21:0x0061, B:25:0x006d, B:29:0x0079] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007f, code lost:
    
        if (r3[r2] < 0) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int f() {
        int i;
        int i2 = this.e;
        if (this.c != i2) {
            byte[] bArr = this.a;
            int i3 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.e = i3;
                return b;
            }
            if (this.c - i3 >= 9) {
                int i4 = i3 + 1;
                int i5 = b ^ (bArr[i3] << 7);
                if (i5 < 0) {
                    i = (int) (((long) i5) ^ (-128));
                } else {
                    int i6 = i4 + 1;
                    int i7 = i5 ^ (bArr[i4] << 14);
                    if (i7 >= 0) {
                        i = (int) (((long) i7) ^ 16256);
                        i4 = i6;
                    } else {
                        i4 = i6 + 1;
                        int i8 = i7 ^ (bArr[i6] << 21);
                        if (i8 < 0) {
                            i = (int) (((long) i8) ^ (-2080896));
                        } else {
                            int i9 = i4 + 1;
                            byte b2 = bArr[i4];
                            i = (int) (((long) (i8 ^ (b2 << 28))) ^ 266354560);
                            if (b2 < 0) {
                                i4 = i9 + 1;
                                if (bArr[i9] < 0) {
                                    i9 = i4 + 1;
                                    if (bArr[i4] < 0) {
                                        i4 = i9 + 1;
                                        if (bArr[i9] < 0) {
                                            i9 = i4 + 1;
                                            if (bArr[i4] < 0) {
                                                i4 = i9 + 1;
                                            } else {
                                                i4 = i9;
                                            }
                                        }
                                    } else {
                                        i4 = i9;
                                    }
                                }
                            } else {
                                i4 = i9;
                            }
                        }
                    }
                }
                this.e = i4;
                return i;
            }
        }
        return (int) i();
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b9, code lost:
    
        if (r4[r3] < 0) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long g() {
        long j;
        int i = this.e;
        if (this.c != i) {
            byte[] bArr = this.a;
            int i2 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.e = i2;
                return b;
            }
            if (this.c - i2 >= 9) {
                int i3 = i2 + 1;
                long j2 = b ^ (bArr[i2] << 7);
                if (j2 < 0) {
                    j = j2 ^ (-128);
                } else {
                    int i4 = i3 + 1;
                    long j3 = j2 ^ ((long) (bArr[i3] << 14));
                    if (j3 >= 0) {
                        j = j3 ^ 16256;
                        i3 = i4;
                    } else {
                        i3 = i4 + 1;
                        long j4 = j3 ^ ((long) (bArr[i4] << 21));
                        if (j4 < 0) {
                            j = j4 ^ (-2080896);
                        } else {
                            int i5 = i3 + 1;
                            long j5 = j4 ^ (((long) bArr[i3]) << 28);
                            if (j5 >= 0) {
                                j = j5 ^ 266354560;
                                i3 = i5;
                            } else {
                                i3 = i5 + 1;
                                long j6 = j5 ^ (((long) bArr[i5]) << 35);
                                if (j6 < 0) {
                                    j = j6 ^ (-34093383808L);
                                } else {
                                    int i6 = i3 + 1;
                                    long j7 = j6 ^ (((long) bArr[i3]) << 42);
                                    if (j7 >= 0) {
                                        j = j7 ^ 4363953127296L;
                                        i3 = i6;
                                    } else {
                                        i3 = i6 + 1;
                                        long j8 = j7 ^ (((long) bArr[i6]) << 49);
                                        if (j8 < 0) {
                                            j = j8 ^ (-558586000294016L);
                                        } else {
                                            int i7 = i3 + 1;
                                            j = (j8 ^ (((long) bArr[i3]) << 56)) ^ 71499008037633920L;
                                            i3 = j < 0 ? i7 + 1 : i7;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.e = i3;
                return j;
            }
        }
        return i();
    }

    private long i() throws dq {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            if (this.e == this.c) {
                c(1);
            }
            byte[] bArr = this.a;
            int i2 = this.e;
            this.e = i2 + 1;
            byte b = bArr[i2];
            j |= ((long) (b & 127)) << i;
            if ((b & 128) == 0) {
                return j;
            }
        }
        throw dq.c();
    }

    public final long h() throws dq {
        int i = this.e;
        if (this.c - i < 8) {
            c(8);
            i = this.e;
        }
        byte[] bArr = this.a;
        this.e = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    private dl(byte[] bArr, int i) {
        this.h = false;
        this.j = Integer.MAX_VALUE;
        this.l = 64;
        this.m = 67108864;
        this.n = null;
        this.a = bArr;
        this.c = i + 0;
        this.e = 0;
        this.i = 0;
        this.f = null;
        this.b = false;
    }

    dl(InputStream inputStream) {
        this.h = false;
        this.j = Integer.MAX_VALUE;
        this.l = 64;
        this.m = 67108864;
        this.n = null;
        this.a = new byte[4096];
        this.c = 0;
        this.e = 0;
        this.i = 0;
        this.f = inputStream;
        this.b = false;
    }

    private int b(int i) throws dq {
        if (i < 0) {
            throw dq.b();
        }
        int i2 = this.i + this.e + i;
        int i3 = this.j;
        if (i2 > i3) {
            throw dq.a();
        }
        this.j = i2;
        j();
        return i3;
    }

    private void j() {
        this.c += this.d;
        int i = this.i + this.c;
        if (i > this.j) {
            this.d = i - this.j;
            this.c -= this.d;
        } else {
            this.d = 0;
        }
    }

    private void c(int i) throws dq {
        if (!d(i)) {
            throw dq.a();
        }
    }

    private boolean d(int i) throws IOException {
        while (this.e + i > this.c) {
            if (this.i + this.e + i > this.j || this.f == null) {
                return false;
            }
            int i2 = this.e;
            if (i2 > 0) {
                if (this.c > i2) {
                    System.arraycopy(this.a, i2, this.a, 0, this.c - i2);
                }
                this.i += i2;
                this.c -= i2;
                this.e = 0;
            }
            int i3 = this.f.read(this.a, this.c, this.a.length - this.c);
            if (i3 == 0 || i3 < -1 || i3 > this.a.length) {
                throw new IllegalStateException(new StringBuilder(102).append("InputStream#read(byte[]) returned invalid result: ").append(i3).append("\nThe InputStream implementation is buggy.").toString());
            }
            if (i3 <= 0) {
                return false;
            }
            this.c = i3 + this.c;
            if ((this.i + i) - this.m > 0) {
                throw dq.h();
            }
            j();
            if (this.c >= i) {
                return true;
            }
        }
        throw new IllegalStateException(new StringBuilder(77).append("refillBuffer() called when ").append(i).append(" bytes were already available in buffer").toString());
    }

    private byte[] e(int i) throws dq {
        if (i <= 0) {
            if (i == 0) {
                return dp.a;
            }
            throw dq.b();
        }
        if (this.i + this.e + i > this.j) {
            f((this.j - this.i) - this.e);
            throw dq.a();
        }
        if (i < 4096) {
            byte[] bArr = new byte[i];
            int i2 = this.c - this.e;
            System.arraycopy(this.a, this.e, bArr, 0, i2);
            this.e = this.c;
            int i3 = i - i2;
            if (this.c - this.e < i3) {
                c(i3);
            }
            System.arraycopy(this.a, 0, bArr, i2, i - i2);
            this.e = i - i2;
            return bArr;
        }
        int i4 = this.e;
        int i5 = this.c;
        this.i += this.c;
        this.e = 0;
        this.c = 0;
        ArrayList arrayList = new ArrayList();
        int i6 = i - (i5 - i4);
        while (i6 > 0) {
            byte[] bArr2 = new byte[Math.min(i6, 4096)];
            int i7 = 0;
            while (i7 < bArr2.length) {
                int i8 = this.f == null ? -1 : this.f.read(bArr2, i7, bArr2.length - i7);
                if (i8 == -1) {
                    throw dq.a();
                }
                this.i += i8;
                i7 += i8;
            }
            int length = i6 - bArr2.length;
            arrayList.add(bArr2);
            i6 = length;
        }
        byte[] bArr3 = new byte[i];
        int length2 = i5 - i4;
        System.arraycopy(this.a, i4, bArr3, 0, length2);
        Iterator it = arrayList.iterator();
        while (true) {
            int i9 = length2;
            if (!it.hasNext()) {
                return bArr3;
            }
            byte[] bArr4 = (byte[]) it.next();
            System.arraycopy(bArr4, 0, bArr3, i9, bArr4.length);
            length2 = bArr4.length + i9;
        }
    }

    private void f(int i) throws dq {
        if (i <= this.c - this.e && i >= 0) {
            this.e += i;
            return;
        }
        if (i < 0) {
            throw dq.b();
        }
        if (this.i + this.e + i > this.j) {
            f((this.j - this.i) - this.e);
            throw dq.a();
        }
        int i2 = this.c - this.e;
        this.e = this.c;
        c(1);
        while (i - i2 > this.c) {
            i2 += this.c;
            this.e = this.c;
            c(1);
        }
        this.e = i - i2;
    }

    public final int a() throws dq {
        if (this.e == this.c && !d(1)) {
            this.g = 0;
            return 0;
        }
        this.g = f();
        if (ec.b(this.g) == 0) {
            throw dq.d();
        }
        return this.g;
    }
}
