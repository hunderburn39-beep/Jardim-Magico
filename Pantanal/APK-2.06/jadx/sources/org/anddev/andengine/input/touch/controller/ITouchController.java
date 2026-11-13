package org.anddev.andengine.input.touch.controller;

import android.view.MotionEvent;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.TouchOptions;
import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public interface ITouchController extends IUpdateHandler {

    public interface ITouchEventCallback {
        boolean onTouchEvent(TouchEvent touchEvent);
    }

    void applyTouchOptions(TouchOptions touchOptions);

    boolean onHandleMotionEvent(MotionEvent motionEvent);

    void setTouchEventCallback(ITouchEventCallback iTouchEventCallback);
}
