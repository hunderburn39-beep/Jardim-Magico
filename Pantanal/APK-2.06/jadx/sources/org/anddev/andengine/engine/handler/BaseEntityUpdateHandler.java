package org.anddev.andengine.engine.handler;

import org.anddev.andengine.entity.IEntity;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseEntityUpdateHandler implements IUpdateHandler {
    private final IEntity mEntity;

    protected abstract void onUpdate(float f, IEntity iEntity);

    public BaseEntityUpdateHandler(IEntity pEntity) {
        this.mEntity = pEntity;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public final void onUpdate(float pSecondsElapsed) {
        onUpdate(pSecondsElapsed, this.mEntity);
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
    }
}
