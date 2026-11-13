package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class DataBuffer<T> implements Iterable<T> {
    protected final DataHolder BB;

    protected DataBuffer(DataHolder dataHolder) {
        this.BB = dataHolder;
        if (this.BB != null) {
            this.BB.c(this);
        }
    }

    public void close() {
        if (this.BB != null) {
            this.BB.close();
        }
    }

    public int describeContents() {
        return 0;
    }

    public abstract T get(int i);

    public int getCount() {
        if (this.BB == null) {
            return 0;
        }
        return this.BB.getCount();
    }

    public Bundle getMetadata() {
        return this.BB.getMetadata();
    }

    public boolean isClosed() {
        if (this.BB == null) {
            return true;
        }
        return this.BB.isClosed();
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return new a(this);
    }
}
