package org.anddev.andengine.engine.handler.runnable;

import java.util.ArrayList;
import org.anddev.andengine.engine.handler.IUpdateHandler;

/* JADX INFO: loaded from: classes.dex */
public class RunnableHandler implements IUpdateHandler {
    private final ArrayList<Runnable> mRunnables = new ArrayList<>();

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public synchronized void onUpdate(float pSecondsElapsed) {
        ArrayList<Runnable> runnables = this.mRunnables;
        int runnableCount = runnables.size();
        for (int i = runnableCount - 1; i >= 0; i--) {
            runnables.get(i).run();
        }
        runnables.clear();
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mRunnables.clear();
    }

    public synchronized void postRunnable(Runnable pRunnable) {
        this.mRunnables.add(pRunnable);
    }
}
