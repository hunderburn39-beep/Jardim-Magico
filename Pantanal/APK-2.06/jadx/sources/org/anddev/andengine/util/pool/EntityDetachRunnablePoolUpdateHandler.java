package org.anddev.andengine.util.pool;

/* JADX INFO: loaded from: classes.dex */
public class EntityDetachRunnablePoolUpdateHandler extends RunnablePoolUpdateHandler<EntityDetachRunnablePoolItem> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.pool.RunnablePoolUpdateHandler, org.anddev.andengine.util.pool.PoolUpdateHandler
    public EntityDetachRunnablePoolItem onAllocatePoolItem() {
        return new EntityDetachRunnablePoolItem();
    }
}
