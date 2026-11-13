package org.anddev.andengine.util.pool;

import org.anddev.andengine.util.pool.PoolItem;

/* JADX INFO: loaded from: classes.dex */
public abstract class Pool<T extends PoolItem> extends GenericPool<T> {
    public Pool() {
    }

    public Pool(int pInitialSize) {
        super(pInitialSize);
    }

    public Pool(int pInitialSize, int pGrowth) {
        super(pInitialSize, pGrowth);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.anddev.andengine.util.pool.GenericPool
    public T onHandleAllocatePoolItem() {
        T t = (T) super.onHandleAllocatePoolItem();
        t.mParent = this;
        return t;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.pool.GenericPool
    public void onHandleObtainItem(T pPoolItem) {
        pPoolItem.mRecycled = false;
        pPoolItem.onObtain();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.pool.GenericPool
    public void onHandleRecycleItem(T pPoolItem) {
        pPoolItem.onRecycle();
        pPoolItem.mRecycled = true;
    }

    @Override // org.anddev.andengine.util.pool.GenericPool
    public synchronized void recyclePoolItem(T pPoolItem) {
        if (pPoolItem.mParent == null) {
            throw new IllegalArgumentException("PoolItem not assigned to a pool!");
        }
        if (!pPoolItem.isFromPool(this)) {
            throw new IllegalArgumentException("PoolItem from another pool!");
        }
        if (pPoolItem.isRecycled()) {
            throw new IllegalArgumentException("PoolItem already recycled!");
        }
        super.recyclePoolItem(pPoolItem);
    }

    public synchronized boolean ownsPoolItem(T pPoolItem) {
        return pPoolItem.mParent == this;
    }

    void recycle(PoolItem pPoolItem) {
        recyclePoolItem(pPoolItem);
    }
}
