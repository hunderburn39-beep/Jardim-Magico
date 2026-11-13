package org.anddev.andengine.util;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ProbabilityGenerator<T> {
    private final ArrayList<Entry<T>> mEntries = new ArrayList<>();
    private float mProbabilitySum;

    public void add(float pFactor, T... tArr) {
        this.mProbabilitySum += pFactor;
        this.mEntries.add(new Entry<>(pFactor, tArr));
    }

    public T next() {
        float random = MathUtils.random(BitmapDescriptorFactory.HUE_RED, this.mProbabilitySum);
        ArrayList<Entry<T>> factors = this.mEntries;
        for (int i = factors.size() - 1; i >= 0; i--) {
            Entry<T> entry = factors.get(i);
            random -= entry.mFactor;
            if (random <= BitmapDescriptorFactory.HUE_RED) {
                return entry.getReturnValue();
            }
        }
        Entry<T> lastEntry = factors.get(factors.size() - 1);
        return lastEntry.getReturnValue();
    }

    public void clear() {
        this.mProbabilitySum = BitmapDescriptorFactory.HUE_RED;
        this.mEntries.clear();
    }

    private static class Entry<T> {
        public final T[] mData;
        public final float mFactor;

        public Entry(float pFactor, T[] tArr) {
            this.mFactor = pFactor;
            this.mData = tArr;
        }

        public T getReturnValue() {
            return this.mData.length == 1 ? this.mData[0] : (T) ArrayUtils.random(this.mData);
        }
    }
}
