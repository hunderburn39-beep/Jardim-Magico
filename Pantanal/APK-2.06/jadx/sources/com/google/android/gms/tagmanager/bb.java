package com.google.android.gms.tagmanager;

import android.util.LruCache;

/* JADX INFO: loaded from: classes.dex */
class bb<K, V> implements k<K, V> {
    private LruCache<K, V> Yu;

    bb(int i, final l.a<K, V> aVar) {
        this.Yu = new LruCache<K, V>(i) { // from class: com.google.android.gms.tagmanager.bb.1
            @Override // android.util.LruCache
            protected int sizeOf(K key, V value) {
                return aVar.sizeOf(key, value);
            }
        };
    }

    @Override // com.google.android.gms.tagmanager.k
    public void e(K k, V v) {
        this.Yu.put(k, v);
    }

    @Override // com.google.android.gms.tagmanager.k
    public V get(K key) {
        return this.Yu.get(key);
    }
}
