package org.anddev.andengine.util;

import android.util.SparseArray;

/* JADX INFO: loaded from: classes.dex */
public class Library<T> {
    protected final SparseArray<T> mItems;

    public Library() {
        this.mItems = new SparseArray<>();
    }

    public Library(int pInitialCapacity) {
        this.mItems = new SparseArray<>(pInitialCapacity);
    }

    public void put(int pID, T pItem) {
        T existingItem = this.mItems.get(pID);
        if (existingItem == null) {
            this.mItems.put(pID, pItem);
            return;
        }
        throw new IllegalArgumentException("ID: '" + pID + "' is already associated with item: '" + existingItem.toString() + "'.");
    }

    public void remove(int pID) {
        this.mItems.remove(pID);
    }

    public T get(int pID) {
        return this.mItems.get(pID);
    }
}
