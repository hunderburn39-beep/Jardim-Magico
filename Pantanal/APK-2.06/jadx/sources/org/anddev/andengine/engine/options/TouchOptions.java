package org.anddev.andengine.engine.options;

/* JADX INFO: loaded from: classes.dex */
public class TouchOptions {
    private boolean mRunOnUpdateThread;

    public TouchOptions enableRunOnUpdateThread() {
        return setRunOnUpdateThread(true);
    }

    public TouchOptions disableRunOnUpdateThread() {
        return setRunOnUpdateThread(false);
    }

    public TouchOptions setRunOnUpdateThread(boolean pRunOnUpdateThread) {
        this.mRunOnUpdateThread = pRunOnUpdateThread;
        return this;
    }

    public boolean isRunOnUpdateThread() {
        return this.mRunOnUpdateThread;
    }
}
