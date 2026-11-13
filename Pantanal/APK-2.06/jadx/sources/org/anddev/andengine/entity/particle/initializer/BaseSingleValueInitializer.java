package org.anddev.andengine.entity.particle.initializer;

import org.anddev.andengine.entity.particle.Particle;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseSingleValueInitializer implements IParticleInitializer {
    protected float mMaxValue;
    protected float mMinValue;

    protected abstract void onInitializeParticle(Particle particle, float f);

    public BaseSingleValueInitializer(float pMinValue, float pMaxValue) {
        this.mMinValue = pMinValue;
        this.mMaxValue = pMaxValue;
    }

    @Override // org.anddev.andengine.entity.particle.initializer.IParticleInitializer
    public final void onInitializeParticle(Particle pParticle) {
        onInitializeParticle(pParticle, getRandomValue());
    }

    private final float getRandomValue() {
        return this.mMinValue == this.mMaxValue ? this.mMaxValue : (MathUtils.RANDOM.nextFloat() * (this.mMaxValue - this.mMinValue)) + this.mMinValue;
    }
}
