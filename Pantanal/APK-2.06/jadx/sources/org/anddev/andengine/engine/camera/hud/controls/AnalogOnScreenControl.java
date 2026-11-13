package org.anddev.andengine.engine.camera.hud.controls;

import android.util.FloatMath;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class AnalogOnScreenControl extends BaseOnScreenControl implements TimeConstants, ClickDetector.IClickDetectorListener {
    private final ClickDetector mClickDetector;

    public interface IAnalogOnScreenControlListener extends BaseOnScreenControl.IOnScreenControlListener {
        void onControlClick(AnalogOnScreenControl analogOnScreenControl);
    }

    public AnalogOnScreenControl(float pX, float pY, Camera pCamera, TextureRegion pControlBaseTextureRegion, TextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, IAnalogOnScreenControlListener pAnalogOnScreenControlListener) {
        super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pAnalogOnScreenControlListener);
        this.mClickDetector = new ClickDetector(this);
        this.mClickDetector.setEnabled(false);
    }

    public AnalogOnScreenControl(float pX, float pY, Camera pCamera, TextureRegion pControlBaseTextureRegion, TextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, long pOnControlClickMaximumMilliseconds, IAnalogOnScreenControlListener pAnalogOnScreenControlListener) {
        super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pAnalogOnScreenControlListener);
        this.mClickDetector = new ClickDetector(this);
        this.mClickDetector.setTriggerClickMaximumMilliseconds(pOnControlClickMaximumMilliseconds);
    }

    @Override // org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl
    public IAnalogOnScreenControlListener getOnScreenControlListener() {
        return (IAnalogOnScreenControlListener) super.getOnScreenControlListener();
    }

    public void setOnControlClickEnabled(boolean pOnControlClickEnabled) {
        this.mClickDetector.setEnabled(pOnControlClickEnabled);
    }

    public void setOnControlClickMaximumMilliseconds(long pOnControlClickMaximumMilliseconds) {
        this.mClickDetector.setTriggerClickMaximumMilliseconds(pOnControlClickMaximumMilliseconds);
    }

    @Override // org.anddev.andengine.input.touch.detector.ClickDetector.IClickDetectorListener
    public void onClick(ClickDetector pClickDetector, TouchEvent pTouchEvent) {
        getOnScreenControlListener().onControlClick(this);
    }

    @Override // org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl
    protected boolean onHandleControlBaseTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        this.mClickDetector.onSceneTouchEvent(null, pSceneTouchEvent);
        return super.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    @Override // org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl
    protected void onUpdateControlKnob(float pRelativeX, float pRelativeY) {
        if ((pRelativeX * pRelativeX) + (pRelativeY * pRelativeY) <= 0.25f) {
            super.onUpdateControlKnob(pRelativeX, pRelativeY);
        } else {
            float angleRad = MathUtils.atan2(pRelativeY, pRelativeX);
            super.onUpdateControlKnob(FloatMath.cos(angleRad) * 0.5f, FloatMath.sin(angleRad) * 0.5f);
        }
    }
}
