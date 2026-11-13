package org.anddev.andengine.input.touch.controller;

import android.view.MotionEvent;
import org.anddev.andengine.engine.options.TouchOptions;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.util.pool.RunnablePoolItem;
import org.anddev.andengine.util.pool.RunnablePoolUpdateHandler;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTouchController implements ITouchController {
    private boolean mRunOnUpdateThread;
    private ITouchController.ITouchEventCallback mTouchEventCallback;
    private final RunnablePoolUpdateHandler<TouchEventRunnablePoolItem> mTouchEventRunnablePoolUpdateHandler = new RunnablePoolUpdateHandler<TouchEventRunnablePoolItem>() { // from class: org.anddev.andengine.input.touch.controller.BaseTouchController.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.anddev.andengine.util.pool.RunnablePoolUpdateHandler, org.anddev.andengine.util.pool.PoolUpdateHandler
        public TouchEventRunnablePoolItem onAllocatePoolItem() {
            return BaseTouchController.this.new TouchEventRunnablePoolItem();
        }
    };

    @Override // org.anddev.andengine.input.touch.controller.ITouchController
    public void setTouchEventCallback(ITouchController.ITouchEventCallback pTouchEventCallback) {
        this.mTouchEventCallback = pTouchEventCallback;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        if (this.mRunOnUpdateThread) {
            this.mTouchEventRunnablePoolUpdateHandler.reset();
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        if (this.mRunOnUpdateThread) {
            this.mTouchEventRunnablePoolUpdateHandler.onUpdate(pSecondsElapsed);
        }
    }

    protected boolean fireTouchEvent(float pX, float pY, int pAction, int pPointerID, MotionEvent pMotionEvent) {
        if (this.mRunOnUpdateThread) {
            TouchEvent touchEvent = TouchEvent.obtain(pX, pY, pAction, pPointerID, MotionEvent.obtain(pMotionEvent));
            TouchEventRunnablePoolItem touchEventRunnablePoolItem = this.mTouchEventRunnablePoolUpdateHandler.obtainPoolItem();
            touchEventRunnablePoolItem.set(touchEvent);
            this.mTouchEventRunnablePoolUpdateHandler.postPoolItem(touchEventRunnablePoolItem);
            return true;
        }
        TouchEvent touchEvent2 = TouchEvent.obtain(pX, pY, pAction, pPointerID, pMotionEvent);
        boolean handled = this.mTouchEventCallback.onTouchEvent(touchEvent2);
        touchEvent2.recycle();
        return handled;
    }

    @Override // org.anddev.andengine.input.touch.controller.ITouchController
    public void applyTouchOptions(TouchOptions pTouchOptions) {
        this.mRunOnUpdateThread = pTouchOptions.isRunOnUpdateThread();
    }

    class TouchEventRunnablePoolItem extends RunnablePoolItem {
        private TouchEvent mTouchEvent;

        TouchEventRunnablePoolItem() {
        }

        public void set(TouchEvent pTouchEvent) {
            this.mTouchEvent = pTouchEvent;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseTouchController.this.mTouchEventCallback.onTouchEvent(this.mTouchEvent);
        }

        @Override // org.anddev.andengine.util.pool.PoolItem
        protected void onRecycle() {
            super.onRecycle();
            TouchEvent touchEvent = this.mTouchEvent;
            touchEvent.getMotionEvent().recycle();
            touchEvent.recycle();
        }
    }
}
