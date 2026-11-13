package com.chartboost.sdk.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class bs extends OutputStream {
    private static final byte[] a = new byte[0];
    private final List<byte[]> b;
    private int c;
    private int d;
    private byte[] e;
    private int f;

    public bs() {
        this(1024);
    }

    public bs(int i) {
        this.b = new ArrayList();
        if (i < 0) {
            throw new IllegalArgumentException("Negative initial size: " + i);
        }
        synchronized (this) {
            a(i);
        }
    }

    private void a(int i) {
        if (this.c < this.b.size() - 1) {
            this.d += this.e.length;
            this.c++;
            this.e = this.b.get(this.c);
            return;
        }
        if (this.e == null) {
            this.d = 0;
        } else {
            i = Math.max(this.e.length << 1, i - this.d);
            this.d += this.e.length;
        }
        this.c++;
        this.e = new byte[i];
        this.b.add(this.e);
    }

    @Override // java.io.OutputStream
    public void write(byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (len != 0) {
            synchronized (this) {
                int i = this.f + len;
                int i2 = this.f - this.d;
                int i3 = len;
                while (i3 > 0) {
                    int iMin = Math.min(i3, this.e.length - i2);
                    System.arraycopy(b, (off + len) - i3, this.e, i2, iMin);
                    i3 -= iMin;
                    if (i3 > 0) {
                        a(i);
                        i2 = 0;
                    }
                }
                this.f = i;
            }
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int b) {
        int i = this.f - this.d;
        if (i == this.e.length) {
            a(this.f + 1);
            i = 0;
        }
        this.e[i] = (byte) b;
        this.f++;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public synchronized byte[] a() {
        byte[] bArr;
        int i = 0;
        synchronized (this) {
            int i2 = this.f;
            if (i2 == 0) {
                bArr = a;
            } else {
                byte[] bArr2 = new byte[i2];
                Iterator<byte[]> it = this.b.iterator();
                do {
                    int i3 = i2;
                    int i4 = i;
                    if (!it.hasNext()) {
                        break;
                    }
                    byte[] next = it.next();
                    int iMin = Math.min(next.length, i3);
                    System.arraycopy(next, 0, bArr2, i4, iMin);
                    i = i4 + iMin;
                    i2 = i3 - iMin;
                } while (i2 != 0);
                bArr = bArr2;
            }
        }
        return bArr;
    }

    public String toString() {
        return new String(a());
    }
}
