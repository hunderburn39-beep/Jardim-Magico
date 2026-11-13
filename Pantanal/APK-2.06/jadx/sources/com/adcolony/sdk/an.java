package com.adcolony.sdk;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class an extends InputStream {
    InputStream a;
    int b;

    an(InputStream inputStream, int i, int i2) throws IOException {
        this.a = inputStream;
        while (i > 0) {
            i -= (int) inputStream.skip(i);
        }
        this.b = i2;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        int iAvailable = this.a.available();
        return iAvailable <= this.b ? iAvailable : this.b;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.b == 0) {
            return -1;
        }
        this.b--;
        return this.a.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] buffer, int offset, int count) throws IOException {
        if (this.b == 0) {
            return -1;
        }
        if (count > this.b) {
            count = this.b;
        }
        int i = this.a.read(buffer, offset, count);
        if (i == -1) {
            return -1;
        }
        this.b -= i;
        return i;
    }

    @Override // java.io.InputStream
    public long skip(long count) throws IOException {
        int iSkip = (int) count;
        if (iSkip <= 0) {
            return 0L;
        }
        if (iSkip > this.b) {
            iSkip = this.b;
        }
        this.b -= iSkip;
        while (iSkip > 0) {
            iSkip -= (int) this.a.skip(count);
        }
        return count;
    }
}
