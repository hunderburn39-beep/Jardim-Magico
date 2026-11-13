package org.anddev.andengine.entity.scene.popup;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;

/* JADX INFO: loaded from: classes.dex */
public class PopupScene extends CameraScene {
    public PopupScene(Camera pCamera, Scene pParentScene, float pDurationSeconds) {
        this(pCamera, pParentScene, pDurationSeconds, null);
    }

    public PopupScene(Camera pCamera, final Scene pParentScene, float pDurationSeconds, final Runnable pRunnable) {
        super(pCamera);
        setBackgroundEnabled(false);
        pParentScene.setChildScene(this, false, true, true);
        registerUpdateHandler(new TimerHandler(pDurationSeconds, new ITimerCallback() { // from class: org.anddev.andengine.entity.scene.popup.PopupScene.1
            @Override // org.anddev.andengine.engine.handler.timer.ITimerCallback
            public void onTimePassed(TimerHandler pTimerHandler) {
                PopupScene.this.unregisterUpdateHandler(pTimerHandler);
                pParentScene.clearChildScene();
                if (pRunnable != null) {
                    pRunnable.run();
                }
            }
        }));
    }
}
