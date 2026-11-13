package org.anddev.andengine.engine.camera.hud.controls;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class DigitalOnScreenControl extends BaseOnScreenControl {
    private static final float ANGLE_DELTA = 22.5f;
    private static final float EXTENT_DIAGONAL = 0.354f;
    private static final float EXTENT_SIDE = 0.5f;
    private boolean mAllowDiagonal;

    public DigitalOnScreenControl(float pX, float pY, Camera pCamera, TextureRegion pControlBaseTextureRegion, TextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, BaseOnScreenControl.IOnScreenControlListener pOnScreenControlListener) {
        this(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, false, pOnScreenControlListener);
    }

    public DigitalOnScreenControl(float pX, float pY, Camera pCamera, TextureRegion pControlBaseTextureRegion, TextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, boolean pAllowDiagonal, BaseOnScreenControl.IOnScreenControlListener pOnScreenControlListener) {
        super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pOnScreenControlListener);
        this.mAllowDiagonal = pAllowDiagonal;
    }

    public boolean isAllowDiagonal() {
        return this.mAllowDiagonal;
    }

    public void setAllowDiagonal(boolean pAllowDiagonal) {
        this.mAllowDiagonal = pAllowDiagonal;
    }

    @Override // org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl
    protected void onUpdateControlKnob(float pRelativeX, float pRelativeY) {
        if (pRelativeX == BitmapDescriptorFactory.HUE_RED && pRelativeY == BitmapDescriptorFactory.HUE_RED) {
            super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            return;
        }
        if (!this.mAllowDiagonal) {
            if (Math.abs(pRelativeX) > Math.abs(pRelativeY)) {
                if (pRelativeX > BitmapDescriptorFactory.HUE_RED) {
                    super.onUpdateControlKnob(EXTENT_SIDE, BitmapDescriptorFactory.HUE_RED);
                    return;
                } else if (pRelativeX < BitmapDescriptorFactory.HUE_RED) {
                    super.onUpdateControlKnob(-0.5f, BitmapDescriptorFactory.HUE_RED);
                    return;
                } else {
                    if (pRelativeX == BitmapDescriptorFactory.HUE_RED) {
                        super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                        return;
                    }
                    return;
                }
            }
            if (pRelativeY > BitmapDescriptorFactory.HUE_RED) {
                super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, EXTENT_SIDE);
                return;
            } else if (pRelativeY < BitmapDescriptorFactory.HUE_RED) {
                super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, -0.5f);
                return;
            } else {
                if (pRelativeY == BitmapDescriptorFactory.HUE_RED) {
                    super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    return;
                }
                return;
            }
        }
        float angle = MathUtils.radToDeg(MathUtils.atan2(pRelativeY, pRelativeX)) + 180.0f;
        if (testDiagonalAngle(BitmapDescriptorFactory.HUE_RED, angle) || testDiagonalAngle(360.0f, angle)) {
            super.onUpdateControlKnob(-0.5f, BitmapDescriptorFactory.HUE_RED);
            return;
        }
        if (testDiagonalAngle(45.0f, angle)) {
            super.onUpdateControlKnob(-0.354f, -0.354f);
            return;
        }
        if (testDiagonalAngle(90.0f, angle)) {
            super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, -0.5f);
            return;
        }
        if (testDiagonalAngle(135.0f, angle)) {
            super.onUpdateControlKnob(EXTENT_DIAGONAL, -0.354f);
            return;
        }
        if (testDiagonalAngle(180.0f, angle)) {
            super.onUpdateControlKnob(EXTENT_SIDE, BitmapDescriptorFactory.HUE_RED);
            return;
        }
        if (testDiagonalAngle(225.0f, angle)) {
            super.onUpdateControlKnob(EXTENT_DIAGONAL, EXTENT_DIAGONAL);
            return;
        }
        if (testDiagonalAngle(270.0f, angle)) {
            super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, EXTENT_SIDE);
        } else if (testDiagonalAngle(315.0f, angle)) {
            super.onUpdateControlKnob(-0.354f, EXTENT_DIAGONAL);
        } else {
            super.onUpdateControlKnob(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        }
    }

    private boolean testDiagonalAngle(float pTestAngle, float pActualAngle) {
        return pActualAngle > pTestAngle - ANGLE_DELTA && pActualAngle < pTestAngle + ANGLE_DELTA;
    }
}
