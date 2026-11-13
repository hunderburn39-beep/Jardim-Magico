package org.anddev.andengine.engine.camera.hud.controls;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseOnScreenControl extends HUD implements Scene.IOnSceneTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerID = -1;
    private final Sprite mControlBase;
    private final Sprite mControlKnob;
    private float mControlValueX;
    private float mControlValueY;
    private final IOnScreenControlListener mOnScreenControlListener;

    public interface IOnScreenControlListener {
        void onControlChange(BaseOnScreenControl baseOnScreenControl, float f, float f2);
    }

    public BaseOnScreenControl(float pX, float pY, Camera pCamera, TextureRegion pControlBaseTextureRegion, TextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, IOnScreenControlListener pOnScreenControlListener) {
        setCamera(pCamera);
        this.mOnScreenControlListener = pOnScreenControlListener;
        this.mControlBase = new Sprite(pX, pY, pControlBaseTextureRegion) { // from class: org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.1
            @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.scene.Scene.ITouchArea
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return BaseOnScreenControl.this.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.mControlKnob = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, pControlKnobTextureRegion);
        onHandleControlKnobReleased();
        setOnSceneTouchListener(this);
        registerTouchArea(this.mControlBase);
        registerUpdateHandler(new TimerHandler(pTimeBetweenUpdates, true, new ITimerCallback() { // from class: org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.2
            @Override // org.anddev.andengine.engine.handler.timer.ITimerCallback
            public void onTimePassed(TimerHandler pTimerHandler) {
                BaseOnScreenControl.this.mOnScreenControlListener.onControlChange(BaseOnScreenControl.this, BaseOnScreenControl.this.mControlValueX, BaseOnScreenControl.this.mControlValueY);
            }
        }));
        attachChild(this.mControlBase);
        attachChild(this.mControlKnob);
        setTouchAreaBindingEnabled(true);
    }

    public Sprite getControlBase() {
        return this.mControlBase;
    }

    public Sprite getControlKnob() {
        return this.mControlKnob;
    }

    public IOnScreenControlListener getOnScreenControlListener() {
        return this.mOnScreenControlListener;
    }

    @Override // org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        int pointerID = pSceneTouchEvent.getPointerID();
        if (pointerID == this.mActivePointerID) {
            onHandleControlBaseLeft();
            switch (pSceneTouchEvent.getAction()) {
                case 1:
                case 3:
                    this.mActivePointerID = -1;
                    break;
            }
            return false;
        }
        return false;
    }

    public void refreshControlKnobPosition() {
        onUpdateControlKnob(this.mControlValueX * 0.5f, this.mControlValueY * 0.5f);
    }

    protected void onHandleControlBaseLeft() {
        onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    protected void onHandleControlKnobReleased() {
        onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    protected boolean onHandleControlBaseTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        int pointerID = pSceneTouchEvent.getPointerID();
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                if (this.mActivePointerID == -1) {
                    this.mActivePointerID = pointerID;
                    updateControlKnob(pTouchAreaLocalX, pTouchAreaLocalY);
                }
                return true;
            case 1:
            case 3:
                if (this.mActivePointerID == pointerID) {
                    this.mActivePointerID = -1;
                    onHandleControlKnobReleased();
                }
                return true;
            case 2:
            default:
                if (this.mActivePointerID == pointerID) {
                    updateControlKnob(pTouchAreaLocalX, pTouchAreaLocalY);
                }
                return true;
        }
    }

    private void updateControlKnob(float pTouchAreaLocalX, float pTouchAreaLocalY) {
        Sprite controlBase = this.mControlBase;
        float relativeX = (MathUtils.bringToBounds(BitmapDescriptorFactory.HUE_RED, controlBase.getWidth(), pTouchAreaLocalX) / controlBase.getWidth()) - 0.5f;
        float relativeY = (MathUtils.bringToBounds(BitmapDescriptorFactory.HUE_RED, controlBase.getHeight(), pTouchAreaLocalY) / controlBase.getHeight()) - 0.5f;
        onUpdateControlKnob(relativeX, relativeY);
    }

    protected void onUpdateControlKnob(float pRelativeX, float pRelativeY) {
        Sprite controlBase = this.mControlBase;
        Sprite controlKnob = this.mControlKnob;
        this.mControlValueX = 2.0f * pRelativeX;
        this.mControlValueY = 2.0f * pRelativeY;
        float[] controlBaseSceneCenterCoordinates = controlBase.getSceneCenterCoordinates();
        float x = (controlBaseSceneCenterCoordinates[0] - (controlKnob.getWidth() * 0.5f)) + (controlBase.getWidthScaled() * pRelativeX);
        float y = (controlBaseSceneCenterCoordinates[1] - (controlKnob.getHeight() * 0.5f)) + (controlBase.getHeightScaled() * pRelativeY);
        controlKnob.setPosition(x, y);
    }
}
