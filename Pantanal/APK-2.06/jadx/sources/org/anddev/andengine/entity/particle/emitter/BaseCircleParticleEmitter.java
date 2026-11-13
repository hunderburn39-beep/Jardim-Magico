package org.anddev.andengine.entity.particle.emitter;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseCircleParticleEmitter extends BaseParticleEmitter {
    protected float mRadiusX;
    protected float mRadiusY;

    public BaseCircleParticleEmitter(float pCenterX, float pCenterY, float pRadius) {
        this(pCenterX, pCenterY, pRadius, pRadius);
    }

    public BaseCircleParticleEmitter(float pCenterX, float pCenterY, float pRadiusX, float pRadiusY) {
        super(pCenterX, pCenterY);
        setRadiusX(pRadiusX);
        setRadiusY(pRadiusY);
    }

    public float getRadiusX() {
        return this.mRadiusX;
    }

    public void setRadiusX(float pRadiusX) {
        this.mRadiusX = pRadiusX;
    }

    public float getRadiusY() {
        return this.mRadiusY;
    }

    public void setRadiusY(float pRadiusY) {
        this.mRadiusY = pRadiusY;
    }

    public void setRadius(float pRadius) {
        this.mRadiusX = pRadius;
        this.mRadiusY = pRadius;
    }

    public void setRadius(float pRadiusX, float pRadiusY) {
        this.mRadiusX = pRadiusX;
        this.mRadiusY = pRadiusY;
    }
}
