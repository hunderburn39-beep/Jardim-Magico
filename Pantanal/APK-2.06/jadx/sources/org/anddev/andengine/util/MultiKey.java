package org.anddev.andengine.util;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MultiKey<K> {
    private final int mCachedHashCode;
    private final K[] mKeys;

    public MultiKey(K... kArr) {
        this.mKeys = kArr;
        this.mCachedHashCode = hash(kArr);
    }

    public K[] getKeys() {
        return this.mKeys;
    }

    public boolean equals(Object pOther) {
        if (pOther == this) {
            return true;
        }
        if (pOther instanceof MultiKey) {
            MultiKey<?> otherMultiKey = (MultiKey) pOther;
            return Arrays.equals(this.mKeys, otherMultiKey.mKeys);
        }
        return false;
    }

    public static int hash(Object... pKeys) {
        int hashCode = 0;
        for (Object key : pKeys) {
            if (key != null) {
                hashCode ^= key.hashCode();
            }
        }
        return hashCode;
    }

    public int hashCode() {
        return this.mCachedHashCode;
    }

    public String toString() {
        return "MultiKey" + Arrays.asList(this.mKeys).toString();
    }

    public K getKey(int pIndex) {
        return this.mKeys[pIndex];
    }

    public int size() {
        return this.mKeys.length;
    }
}
