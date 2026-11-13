package org.anddev.andengine.util;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ListUtils {
    public static <T> ArrayList<? extends T> toList(T pItem) {
        ArrayList<? extends T> arrayList = new ArrayList<>();
        arrayList.add(pItem);
        return arrayList;
    }

    public static <T> ArrayList<? extends T> toList(T... tArr) {
        ArrayList<? extends T> arrayList = new ArrayList<>();
        for (T t : tArr) {
            arrayList.add(t);
        }
        return arrayList;
    }
}
