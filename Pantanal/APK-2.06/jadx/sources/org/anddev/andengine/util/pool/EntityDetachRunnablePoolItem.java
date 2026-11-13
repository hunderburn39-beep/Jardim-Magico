package org.anddev.andengine.util.pool;

import org.anddev.andengine.entity.IEntity;

/* JADX INFO: loaded from: classes.dex */
public class EntityDetachRunnablePoolItem extends RunnablePoolItem {
    protected IEntity mEntity;

    public void setEntity(IEntity pEntity) {
        this.mEntity = pEntity;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mEntity.detachSelf();
    }
}
