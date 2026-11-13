package org.anddev.andengine.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class ByteBufferOutputStream extends OutputStream {
    protected int mCount;
    protected byte[] mData;
    protected final int mMaximumGrow;

    public ByteBufferOutputStream(int pInitialCapacity, int pMaximumGrow) {
        this.mMaximumGrow = pMaximumGrow;
        this.mData = new byte[pInitialCapacity];
    }

    @Override // java.io.OutputStream
    public void write(int pByte) {
        ensureCapacity(this.mCount + 1);
        this.mData[this.mCount] = (byte) pByte;
        this.mCount++;
    }

    @Override // java.io.OutputStream
    public void write(byte[] pData, int pOffset, int pLength) {
        ensureCapacity(this.mCount + pLength);
        System.arraycopy(pData, pOffset, this.mData, this.mCount, pLength);
        this.mCount += pLength;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    private void ensureCapacity(int pDesiredCapacity) {
        if (pDesiredCapacity - this.mData.length > 0) {
            grow(pDesiredCapacity);
        }
    }

    private void grow(int pDesiredCapacity) {
        int oldCapacity = this.mData.length;
        int grow = Math.min(this.mMaximumGrow, oldCapacity);
        Debug.d("Growing by: " + grow);
        int newCapacity = oldCapacity + grow;
        if (newCapacity - pDesiredCapacity < 0) {
            newCapacity = pDesiredCapacity;
        }
        if (newCapacity < 0) {
            if (pDesiredCapacity < 0) {
                throw new OutOfMemoryError();
            }
            newCapacity = Integer.MAX_VALUE;
        }
        byte[] data = new byte[newCapacity];
        System.arraycopy(this.mData, 0, data, 0, this.mCount);
        this.mData = data;
    }

    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(this.mData, 0, this.mCount).slice();
    }
}
