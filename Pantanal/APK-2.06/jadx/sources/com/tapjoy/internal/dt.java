package com.tapjoy.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
class dt extends dk {
    private int a = 0;
    protected final byte[] d;

    dt(byte[] bArr) {
        this.d = bArr;
    }

    @Override // com.tapjoy.internal.dk
    public int a() {
        return this.d.length;
    }

    @Override // com.tapjoy.internal.dk
    protected void a(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.d, i, bArr, i2, i3);
    }

    @Override // com.tapjoy.internal.dk
    final void a(OutputStream outputStream, int i, int i2) throws IOException {
        outputStream.write(this.d, b() + i, i2);
    }

    @Override // com.tapjoy.internal.dk
    public final String b(String str) {
        return new String(this.d, b(), a(), str);
    }

    @Override // com.tapjoy.internal.dk
    public final boolean f() {
        int iB = b();
        return eb.a(this.d, iB, a() + iB);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0021, code lost:
    
        if (r5[r1] > (-65)) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004c, code lost:
    
        if (r5[r1] > (-65)) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0087, code lost:
    
        if (r5[r1] > (-65)) goto L47;
     */
    @Override // com.tapjoy.internal.dk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final int a(int i, int i2, int i3) {
        int i4;
        byte b;
        int i5;
        int i6;
        int iB = b() + i2;
        byte[] bArr = this.d;
        int i7 = iB + i3;
        if (i != 0) {
            if (iB < i7) {
                byte b2 = (byte) i;
                if (b2 < -32) {
                    if (b2 >= -62) {
                        i4 = iB + 1;
                    }
                    return -1;
                }
                if (b2 < -16) {
                    byte b3 = (byte) ((i >> 8) ^ (-1));
                    if (b3 == 0) {
                        int i8 = iB + 1;
                        b3 = bArr[iB];
                        if (i8 >= i7) {
                            return eb.a(b2, b3);
                        }
                        iB = i8;
                    }
                    if (b3 <= -65 && ((b2 != -32 || b3 >= -96) && (b2 != -19 || b3 < -96))) {
                        i4 = iB + 1;
                    }
                    return -1;
                }
                byte b4 = (byte) ((i >> 8) ^ (-1));
                byte b5 = 0;
                if (b4 == 0) {
                    i5 = iB + 1;
                    byte b6 = bArr[iB];
                    if (i5 >= i7) {
                        return eb.a(b2, b6);
                    }
                    b = b6;
                } else {
                    b5 = (byte) (i >> 16);
                    b = b4;
                    i5 = iB;
                }
                if (b5 == 0) {
                    i6 = i5 + 1;
                    b5 = bArr[i5];
                    if (i6 >= i7) {
                        return eb.a(b2, b, b5);
                    }
                } else {
                    i6 = i5;
                }
                if (b <= -65 && (((b2 << 28) + (b + 112)) >> 30) == 0 && b5 <= -65) {
                    i4 = i6 + 1;
                }
                return -1;
            }
            return i;
        }
        i4 = iB;
        return eb.b(bArr, i4, i7);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof dk) && a() == ((dk) other).a()) {
            if (a() == 0) {
                return true;
            }
            if (other instanceof dt) {
                return a((dt) other, 0, a());
            }
            if (other instanceof dy) {
                return other.equals(this);
            }
            String strValueOf = String.valueOf(String.valueOf(other.getClass()));
            throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 49).append("Has a new type of ByteString been created? Found ").append(strValueOf).toString());
        }
        return false;
    }

    final boolean a(dt dtVar, int i, int i2) {
        if (i2 > dtVar.a()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(a()).toString());
        }
        if (i + i2 > dtVar.a()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: ").append(i).append(", ").append(i2).append(", ").append(dtVar.a()).toString());
        }
        byte[] bArr = this.d;
        byte[] bArr2 = dtVar.d;
        int iB = b() + i2;
        int iB2 = b();
        int iB3 = dtVar.b() + i;
        while (iB2 < iB) {
            if (bArr[iB2] == bArr2[iB3]) {
                iB2++;
                iB3++;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override // com.tapjoy.internal.dk
    public int hashCode() {
        int iB = this.a;
        if (iB == 0) {
            int iA = a();
            iB = b(iA, 0, iA);
            if (iB == 0) {
                iB = 1;
            }
            this.a = iB;
        }
        return iB;
    }

    @Override // com.tapjoy.internal.dk
    protected final int j() {
        return this.a;
    }

    @Override // com.tapjoy.internal.dk
    protected final int b(int i, int i2, int i3) {
        byte[] bArr = this.d;
        int iB = b() + i2;
        for (int i4 = iB; i4 < iB + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    @Override // com.tapjoy.internal.dk, java.lang.Iterable
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public dk.a iterator() {
        return new a(this, (byte) 0);
    }

    class a implements dk.a {
        private int b;
        private final int c;

        /* synthetic */ a(dt dtVar, byte b) {
            this();
        }

        private a() {
            this.b = 0;
            this.c = dt.this.a();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.b < this.c;
        }

        @Override // com.tapjoy.internal.dk.a
        public final byte a() {
            try {
                byte[] bArr = dt.this.d;
                int i = this.b;
                this.b = i + 1;
                return bArr[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException(e.getMessage());
            }
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator
        public final /* synthetic */ Object next() {
            return Byte.valueOf(a());
        }
    }

    @Override // com.tapjoy.internal.dk
    protected final int h() {
        return 0;
    }

    @Override // com.tapjoy.internal.dk
    protected final boolean i() {
        return true;
    }

    protected int b() {
        return 0;
    }
}
