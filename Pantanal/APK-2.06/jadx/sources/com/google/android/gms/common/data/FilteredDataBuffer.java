package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.internal.fb;

/* JADX INFO: loaded from: classes.dex */
public abstract class FilteredDataBuffer<T> extends DataBuffer<T> {
    protected final DataBuffer<T> mDataBuffer;

    public FilteredDataBuffer(DataBuffer<T> dataBuffer) {
        super(null);
        fb.d(dataBuffer);
        fb.a(!(dataBuffer instanceof FilteredDataBuffer), "Not possible to have nested FilteredDataBuffers.");
        this.mDataBuffer = dataBuffer;
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public void close() {
        this.mDataBuffer.close();
    }

    protected abstract int computeRealPosition(int i);

    @Override // com.google.android.gms.common.data.DataBuffer
    public T get(int position) {
        return this.mDataBuffer.get(computeRealPosition(position));
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public Bundle getMetadata() {
        return this.mDataBuffer.getMetadata();
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public boolean isClosed() {
        return this.mDataBuffer.isClosed();
    }
}
