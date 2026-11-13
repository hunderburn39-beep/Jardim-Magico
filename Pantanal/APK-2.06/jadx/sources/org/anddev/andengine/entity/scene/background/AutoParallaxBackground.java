package org.anddev.andengine.entity.scene.background;

/* JADX INFO: loaded from: classes.dex */
public class AutoParallaxBackground extends ParallaxBackground {
    private float mParallaxChangePerSecond;

    public AutoParallaxBackground(float pRed, float pGreen, float pBlue, float pParallaxChangePerSecond) {
        super(pRed, pGreen, pBlue);
        this.mParallaxChangePerSecond = pParallaxChangePerSecond;
    }

    public void setParallaxChangePerSecond(float pParallaxChangePerSecond) {
        this.mParallaxChangePerSecond = pParallaxChangePerSecond;
    }

    @Override // org.anddev.andengine.entity.scene.background.BaseBackground, org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        this.mParallaxValue += this.mParallaxChangePerSecond * pSecondsElapsed;
    }
}
