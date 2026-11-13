package org.anddev.andengine.entity.particle.emitter;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseRectangleParticleEmitter extends BaseParticleEmitter {
    protected float mHeight;
    protected float mHeightHalf;
    protected float mWidth;
    protected float mWidthHalf;

    public BaseRectangleParticleEmitter(float pCenterX, float pCenterY, float pSize) {
        this(pCenterX, pCenterY, pSize, pSize);
    }

    public BaseRectangleParticleEmitter(float pCenterX, float pCenterY, float pWidth, float pHeight) {
        super(pCenterX, pCenterY);
        setWidth(pWidth);
        setHeight(pHeight);
    }

    public float getWidth() {
        return this.mWidth;
    }

    public void setWidth(float pWidth) {
        this.mWidth = pWidth;
        this.mWidthHalf = 0.5f * pWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public void setHeight(float pHeight) {
        this.mHeight = pHeight;
        this.mHeightHalf = 0.5f * pHeight;
    }
}
