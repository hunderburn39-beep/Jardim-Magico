package org.anddev.andengine.util.pool;

import org.anddev.andengine.util.pool.RunnablePoolItem;

/* JADX INFO: loaded from: classes.dex */
public abstract class RunnablePoolUpdateHandler<T extends RunnablePoolItem> extends PoolUpdateHandler<T> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.pool.PoolUpdateHandler
    public abstract T onAllocatePoolItem();

    public RunnablePoolUpdateHandler() {
    }

    public RunnablePoolUpdateHandler(int pInitialPoolSize) {
        super(pInitialPoolSize);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.pool.PoolUpdateHandler
    public void onHandlePoolItem(T pRunnablePoolItem) {
        pRunnablePoolItem.run();
    }
}
