package org.anddev.andengine.util.pool;

import java.util.ArrayList;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.util.pool.PoolItem;

/* JADX INFO: loaded from: classes.dex */
public abstract class PoolUpdateHandler<T extends PoolItem> implements IUpdateHandler {
    private final Pool<T> mPool;
    private final ArrayList<T> mScheduledPoolItems;

    protected abstract T onAllocatePoolItem();

    protected abstract void onHandlePoolItem(T t);

    public PoolUpdateHandler() {
        this.mScheduledPoolItems = new ArrayList<>();
        this.mPool = (Pool<T>) new Pool<T>() { // from class: org.anddev.andengine.util.pool.PoolUpdateHandler.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.anddev.andengine.util.pool.GenericPool
            public T onAllocatePoolItem() {
                return (T) PoolUpdateHandler.this.onAllocatePoolItem();
            }
        };
    }

    public PoolUpdateHandler(int i) {
        this.mScheduledPoolItems = new ArrayList<>();
        this.mPool = (Pool<T>) new Pool<T>(i) { // from class: org.anddev.andengine.util.pool.PoolUpdateHandler.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.anddev.andengine.util.pool.GenericPool
            public T onAllocatePoolItem() {
                return (T) PoolUpdateHandler.this.onAllocatePoolItem();
            }
        };
    }

    public PoolUpdateHandler(int i, int i2) {
        this.mScheduledPoolItems = new ArrayList<>();
        this.mPool = (Pool<T>) new Pool<T>(i, i2) { // from class: org.anddev.andengine.util.pool.PoolUpdateHandler.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.anddev.andengine.util.pool.GenericPool
            public T onAllocatePoolItem() {
                return (T) PoolUpdateHandler.this.onAllocatePoolItem();
            }
        };
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        ArrayList<T> scheduledPoolItems = this.mScheduledPoolItems;
        synchronized (scheduledPoolItems) {
            int count = scheduledPoolItems.size();
            if (count > 0) {
                Pool<T> pool = this.mPool;
                for (int i = 0; i < count; i++) {
                    T item = scheduledPoolItems.get(i);
                    onHandlePoolItem(item);
                    pool.recyclePoolItem((PoolItem) item);
                }
                scheduledPoolItems.clear();
            }
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        ArrayList<T> scheduledPoolItems = this.mScheduledPoolItems;
        synchronized (scheduledPoolItems) {
            int count = scheduledPoolItems.size();
            Pool<T> pool = this.mPool;
            for (int i = count - 1; i >= 0; i--) {
                pool.recyclePoolItem((PoolItem) scheduledPoolItems.get(i));
            }
            scheduledPoolItems.clear();
        }
    }

    public T obtainPoolItem() {
        return this.mPool.obtainPoolItem();
    }

    public void postPoolItem(T pPoolItem) {
        synchronized (this.mScheduledPoolItems) {
            try {
                if (pPoolItem == null) {
                    throw new IllegalArgumentException("PoolItem already recycled!");
                }
                if (!this.mPool.ownsPoolItem(pPoolItem)) {
                    throw new IllegalArgumentException("PoolItem from another pool!");
                }
                this.mScheduledPoolItems.add(pPoolItem);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
