package org.anddev.andengine.entity.util;

import org.anddev.andengine.engine.handler.IUpdateHandler;

/* JADX INFO: loaded from: classes.dex */
public class FrameCounter implements IUpdateHandler {
    private int mFrames;

    public int getFrames() {
        return this.mFrames;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        this.mFrames++;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mFrames = 0;
    }
}
