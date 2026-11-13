package org.anddev.andengine.engine.handler;

import org.anddev.andengine.util.SmartList;

/* JADX INFO: loaded from: classes.dex */
public class UpdateHandlerList extends SmartList<IUpdateHandler> implements IUpdateHandler {
    private static final long serialVersionUID = -8842562717687229277L;

    public UpdateHandlerList() {
    }

    public UpdateHandlerList(int pCapacity) {
        super(pCapacity);
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        int handlerCount = size();
        for (int i = handlerCount - 1; i >= 0; i--) {
            ((IUpdateHandler) get(i)).onUpdate(pSecondsElapsed);
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        int handlerCount = size();
        for (int i = handlerCount - 1; i >= 0; i--) {
            ((IUpdateHandler) get(i)).reset();
        }
    }
}
