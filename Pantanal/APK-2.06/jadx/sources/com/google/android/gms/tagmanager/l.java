package com.google.android.gms.tagmanager;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
class l<K, V> {
    final a<K, V> WH = new a<K, V>() { // from class: com.google.android.gms.tagmanager.l.1
        @Override // com.google.android.gms.tagmanager.l.a
        public int sizeOf(K key, V value) {
            return 1;
        }
    };

    public interface a<K, V> {
        int sizeOf(K k, V v);
    }

    public k<K, V> a(int i, a<K, V> aVar) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        return jZ() < 12 ? new cz(i, aVar) : new bb(i, aVar);
    }

    int jZ() {
        return Build.VERSION.SDK_INT;
    }
}
