package com.google.android.gms.internal;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class fu<K, V> {
    private final LinkedHashMap<K, V> DL;
    private int DM;
    private int DN;
    private int DO;
    private int DP;
    private int DQ;
    private int DR;
    private int size;

    public fu(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.DM = i;
        this.DL = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int c(K k, V v) {
        int iSizeOf = sizeOf(k, v);
        if (iSizeOf < 0) {
            throw new IllegalStateException("Negative size: " + k + "=" + v);
        }
        return iSizeOf;
    }

    protected V create(K key) {
        return null;
    }

    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final V get(K k) {
        V vPut;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v = this.DL.get(k);
            if (v != null) {
                this.DQ++;
                return v;
            }
            this.DR++;
            V vCreate = create(k);
            if (vCreate == null) {
                return null;
            }
            synchronized (this) {
                this.DO++;
                vPut = this.DL.put(k, vCreate);
                if (vPut != null) {
                    this.DL.put(k, vPut);
                } else {
                    this.size += c(k, vCreate);
                }
            }
            if (vPut != null) {
                entryRemoved(false, k, vCreate, vPut);
                return vPut;
            }
            trimToSize(this.DM);
            return vCreate;
        }
    }

    public final V put(K key, V value) {
        V vPut;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.DN++;
            this.size += c(key, value);
            vPut = this.DL.put(key, value);
            if (vPut != null) {
                this.size -= c(key, vPut);
            }
        }
        if (vPut != null) {
            entryRemoved(false, key, vPut, value);
        }
        trimToSize(this.DM);
        return vPut;
    }

    public final synchronized int size() {
        return this.size;
    }

    protected int sizeOf(K key, V value) {
        return 1;
    }

    public final synchronized String toString() {
        String str;
        synchronized (this) {
            int i = this.DQ + this.DR;
            str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.DM), Integer.valueOf(this.DQ), Integer.valueOf(this.DR), Integer.valueOf(i != 0 ? (this.DQ * 100) / i : 0));
        }
        return str;
    }

    public void trimToSize(int maxSize) {
        K key;
        V value;
        while (true) {
            synchronized (this) {
                if (this.size < 0 || (this.DL.isEmpty() && this.size != 0)) {
                    break;
                }
                if (this.size <= maxSize || this.DL.isEmpty()) {
                    return;
                }
                Map.Entry<K, V> next = this.DL.entrySet().iterator().next();
                key = next.getKey();
                value = next.getValue();
                this.DL.remove(key);
                this.size -= c(key, value);
                this.DP++;
            }
            entryRemoved(true, key, value, null);
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }
}
