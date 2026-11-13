package org.anddev.andengine.entity.scene.background;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;

/* JADX INFO: loaded from: classes.dex */
public class ColorBackground extends BaseBackground {
    private float mAlpha;
    private float mBlue;
    private boolean mColorEnabled;
    private float mGreen;
    private float mRed;

    protected ColorBackground() {
        this.mRed = BitmapDescriptorFactory.HUE_RED;
        this.mGreen = BitmapDescriptorFactory.HUE_RED;
        this.mBlue = BitmapDescriptorFactory.HUE_RED;
        this.mAlpha = 1.0f;
        this.mColorEnabled = true;
    }

    public ColorBackground(float pRed, float pGreen, float pBlue) {
        this.mRed = BitmapDescriptorFactory.HUE_RED;
        this.mGreen = BitmapDescriptorFactory.HUE_RED;
        this.mBlue = BitmapDescriptorFactory.HUE_RED;
        this.mAlpha = 1.0f;
        this.mColorEnabled = true;
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
    }

    public ColorBackground(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mRed = BitmapDescriptorFactory.HUE_RED;
        this.mGreen = BitmapDescriptorFactory.HUE_RED;
        this.mBlue = BitmapDescriptorFactory.HUE_RED;
        this.mAlpha = 1.0f;
        this.mColorEnabled = true;
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
        this.mAlpha = pAlpha;
    }

    @Override // org.anddev.andengine.entity.scene.background.IBackground
    public void setColor(float pRed, float pGreen, float pBlue) {
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
    }

    @Override // org.anddev.andengine.entity.scene.background.IBackground
    public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        setColor(pRed, pGreen, pBlue);
        this.mAlpha = pAlpha;
    }

    public void setColor(int pRed, int pGreen, int pBlue) throws IllegalArgumentException {
        setColor(pRed / 255.0f, pGreen / 255.0f, pBlue / 255.0f);
    }

    public void setColor(int pRed, int pGreen, int pBlue, int pAlpha) throws IllegalArgumentException {
        setColor(pRed / 255.0f, pGreen / 255.0f, pBlue / 255.0f, pAlpha / 255.0f);
    }

    public void setColorEnabled(boolean pColorEnabled) {
        this.mColorEnabled = pColorEnabled;
    }

    public boolean isColorEnabled() {
        return this.mColorEnabled;
    }

    @Override // org.anddev.andengine.opengl.IDrawable
    public void onDraw(GL10 pGL, Camera pCamera) {
        if (this.mColorEnabled) {
            pGL.glClearColor(this.mRed, this.mGreen, this.mBlue, this.mAlpha);
            pGL.glClear(16384);
        }
    }
}
