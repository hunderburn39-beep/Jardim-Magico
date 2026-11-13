package com.tapjoy.internal;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
final class dj extends dt {
    final int a;
    private final int e;

    dj(byte[] bArr, int i, int i2) {
        super(bArr);
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(29).append("Offset too small: ").append(i).toString());
        }
        if (i2 < 0) {
            throw new IllegalArgumentException(new StringBuilder(29).append("Length too small: ").append(i).toString());
        }
        if (((long) i) + ((long) i2) > bArr.length) {
            throw new IllegalArgumentException(new StringBuilder(48).append("Offset+Length too large: ").append(i).append("+").append(i2).toString());
        }
        this.a = i;
        this.e = i2;
    }

    @Override // com.tapjoy.internal.dt, com.tapjoy.internal.dk
    public final int a() {
        return this.e;
    }

    @Override // com.tapjoy.internal.dt
    protected final int b() {
        return this.a;
    }

    @Override // com.tapjoy.internal.dt, com.tapjoy.internal.dk
    protected final void a(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.d, this.a + i, bArr, i2, i3);
    }

    @Override // com.tapjoy.internal.dt, com.tapjoy.internal.dk, java.lang.Iterable
    /* JADX INFO: renamed from: c */
    public final dk.a iterator() {
        return new a(this, (byte) 0);
    }

    class a implements dk.a {
        private int b;
        private final int c;

        /* synthetic */ a(dj djVar, byte b) {
            this();
        }

        private a() {
            this.b = dj.this.a;
            this.c = this.b + dj.this.a();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.b < this.c;
        }

        @Override // com.tapjoy.internal.dk.a
        public final byte a() {
            if (this.b >= this.c) {
                throw new NoSuchElementException();
            }
            byte[] bArr = dj.this.d;
            int i = this.b;
            this.b = i + 1;
            return bArr[i];
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
}
