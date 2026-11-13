package org.anddev.andengine.util;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MultiKeyHashMap<K, V> extends HashMap<MultiKey<K>, V> {
    private static final long serialVersionUID = -6262447639526561122L;

    public V get(K... kArr) {
        int hashCode = MultiKey.hash(kArr);
        for (Map.Entry<MultiKey<K>, V> entry : entrySet()) {
            MultiKey<K> entryKey = entry.getKey();
            if (entryKey.hashCode() == hashCode && isEqualKey(entryKey.getKeys(), kArr)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private boolean isEqualKey(K[] kArr, K[] kArr2) {
        if (kArr.length != kArr2.length) {
            return false;
        }
        for (int i = 0; i < kArr.length; i++) {
            K keyA = kArr[i];
            K keyB = kArr2[i];
            if (keyA == null) {
                if (keyB != null) {
                    return false;
                }
            } else if (!keyA.equals(keyB)) {
                return false;
            }
        }
        return true;
    }
}
