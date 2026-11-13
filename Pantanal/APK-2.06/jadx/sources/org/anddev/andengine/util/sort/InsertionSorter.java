package org.anddev.andengine.util.sort;

import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class InsertionSorter<T> extends Sorter<T> {
    @Override // org.anddev.andengine.util.sort.Sorter
    public void sort(T[] tArr, int pStart, int pEnd, Comparator<T> pComparator) {
        int j;
        for (int i = pStart + 1; i < pEnd; i++) {
            T current = tArr[i];
            T prev = tArr[i - 1];
            if (pComparator.compare(current, prev) < 0) {
                int j2 = i;
                while (true) {
                    j = j2 - 1;
                    tArr[j2] = prev;
                    if (j <= pStart) {
                        break;
                    }
                    prev = tArr[j - 1];
                    if (pComparator.compare(current, prev) >= 0) {
                        break;
                    } else {
                        j2 = j;
                    }
                }
                tArr[j] = current;
            }
        }
    }

    @Override // org.anddev.andengine.util.sort.Sorter
    public void sort(List<T> list, int pStart, int pEnd, Comparator<T> comparator) {
        int j;
        for (int i = pStart + 1; i < pEnd; i++) {
            T t = list.get(i);
            T t2 = list.get(i - 1);
            if (comparator.compare(t, t2) < 0) {
                int j2 = i;
                while (true) {
                    j = j2 - 1;
                    list.set(j2, t2);
                    if (j <= pStart) {
                        break;
                    }
                    t2 = list.get(j - 1);
                    if (comparator.compare(t, t2) >= 0) {
                        break;
                    } else {
                        j2 = j;
                    }
                }
                list.set(j, t);
            }
        }
    }
}
