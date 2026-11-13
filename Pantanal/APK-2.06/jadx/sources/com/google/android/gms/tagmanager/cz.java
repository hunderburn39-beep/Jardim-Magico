package com.google.android.gms.tagmanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class cz<K, V> implements k<K, V> {
    private final Map<K, V> aap = new HashMap();
    private final int aaq;
    private final l.a<K, V> aar;
    private int aas;

    cz(int i, l.a<K, V> aVar) {
        this.aaq = i;
        this.aar = aVar;
    }

    @Override // com.google.android.gms.tagmanager.k
    public synchronized void e(K k, V v) {
        try {
            if (k == null || v == null) {
                throw new NullPointerException("key == null || value == null");
            }
            this.aas += this.aar.sizeOf(k, v);
            if (this.aas > this.aaq) {
                Iterator<Map.Entry<K, V>> it = this.aap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<K, V> next = it.next();
                    this.aas -= this.aar.sizeOf(next.getKey(), next.getValue());
                    it.remove();
                    if (this.aas <= this.aaq) {
                        break;
                    }
                }
            }
            this.aap.put(k, v);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.google.android.gms.tagmanager.k
    public synchronized V get(K key) {
        return this.aap.get(key);
    }
}
