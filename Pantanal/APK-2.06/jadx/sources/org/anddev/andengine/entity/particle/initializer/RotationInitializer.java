package org.anddev.andengine.entity.particle.initializer;

import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public class RotationInitializer extends BaseSingleValueInitializer {
    public RotationInitializer(float pRotation) {
        this(pRotation, pRotation);
    }

    public RotationInitializer(float pMinRotation, float pMaxRotation) {
        super(pMinRotation, pMaxRotation);
    }

    public float getMinRotation() {
        return this.mMinValue;
    }

    public float getMaxRotation() {
        return this.mMaxValue;
    }

    public void setRotation(float pRotation) {
        this.mMinValue = pRotation;
        this.mMaxValue = pRotation;
    }

    public void setRotation(float pMinRotation, float pMaxRotation) {
        this.mMinValue = pMinRotation;
        this.mMaxValue = pMaxRotation;
    }

    @Override // org.anddev.andengine.entity.particle.initializer.BaseSingleValueInitializer
    public void onInitializeParticle(Particle pParticle, float pRotation) {
        pParticle.setRotation(pRotation);
    }
}
