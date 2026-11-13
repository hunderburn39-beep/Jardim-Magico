package com.tapjoy.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class bg extends FilterInputStream {
    public bg(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read(byte[] buffer, int offset, int count) throws IOException {
        int i = 0;
        while (i < count) {
            int i2 = super.read(buffer, offset + i, count - i);
            if (i2 == -1) {
                if (i != 0) {
                    return i;
                }
                return -1;
            }
            i += i2;
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read(byte[] buffer) throws IOException {
        int i = 0;
        while (i < buffer.length) {
            int i2 = super.read(buffer, i, buffer.length - i);
            if (i2 == -1) {
                if (i != 0) {
                    return i;
                }
                return -1;
            }
            i += i2;
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final long skip(long count) throws IOException {
        long j = 0;
        while (j < count) {
            long jSkip = super.skip(count - j);
            if (jSkip == 0) {
                if (super.read() < 0) {
                    break;
                }
                jSkip++;
            }
            j = jSkip + j;
        }
        return j;
    }
}
