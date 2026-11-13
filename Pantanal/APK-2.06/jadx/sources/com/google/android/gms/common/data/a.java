package com.google.android.gms.common.data;

import com.google.android.gms.internal.fq;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class a<T> implements Iterator<T> {
    private int BC = -1;
    private final DataBuffer<T> mDataBuffer;

    public a(DataBuffer<T> dataBuffer) {
        this.mDataBuffer = (DataBuffer) fq.f(dataBuffer);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.BC < this.mDataBuffer.getCount() + (-1);
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Cannot advance the iterator beyond " + this.BC);
        }
        DataBuffer<T> dataBuffer = this.mDataBuffer;
        int i = this.BC + 1;
        this.BC = i;
        return dataBuffer.get(i);
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
