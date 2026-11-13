package org.anddev.andengine.util.sort;

import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class Sorter<T> {
    public abstract void sort(List<T> list, int i, int i2, Comparator<T> comparator);

    public abstract void sort(T[] tArr, int i, int i2, Comparator<T> comparator);

    public final void sort(T[] tArr, Comparator<T> pComparator) {
        sort(tArr, 0, tArr.length, pComparator);
    }

    public final void sort(List<T> pList, Comparator<T> pComparator) {
        sort(pList, 0, pList.size(), pComparator);
    }
}
