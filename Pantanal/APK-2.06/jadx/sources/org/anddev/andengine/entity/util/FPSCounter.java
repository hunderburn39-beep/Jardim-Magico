package org.anddev.andengine.entity.util;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.handler.IUpdateHandler;

/* JADX INFO: loaded from: classes.dex */
public class FPSCounter implements IUpdateHandler {
    protected int mFrames;
    protected float mSecondsElapsed;

    public float getFPS() {
        return this.mFrames / this.mSecondsElapsed;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        this.mFrames++;
        this.mSecondsElapsed += pSecondsElapsed;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mFrames = 0;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
    }
}
