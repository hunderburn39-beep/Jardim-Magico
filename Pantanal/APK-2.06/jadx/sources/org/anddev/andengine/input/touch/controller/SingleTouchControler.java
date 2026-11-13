package org.anddev.andengine.input.touch.controller;

import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public class SingleTouchControler extends BaseTouchController {
    @Override // org.anddev.andengine.input.touch.controller.ITouchController
    public boolean onHandleMotionEvent(MotionEvent pMotionEvent) {
        return fireTouchEvent(pMotionEvent.getX(), pMotionEvent.getY(), pMotionEvent.getAction(), 0, pMotionEvent);
    }
}
