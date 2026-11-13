package com.google.android.gms.common.data;

import java.util.ArrayList;
import java.util.Iterator;
import org.anddev.andengine.entity.modifier.EntityModifierList;

/* JADX INFO: loaded from: classes.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        EntityModifierList entityModifierList = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            entityModifierList.add(arrayList.get(i).freeze());
        }
        return entityModifierList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        EntityModifierList entityModifierList = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            entityModifierList.add(e.freeze());
        }
        return entityModifierList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        EntityModifierList entityModifierList = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            entityModifierList.add(it.next().freeze());
        }
        return entityModifierList;
    }
}
