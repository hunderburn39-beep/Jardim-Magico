package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseSingleValueSpanModifier implements IParticleModifier {
    private final float mDuration;
    private final float mFromTime;
    private final float mFromValue;
    private final float mSpanValue;
    private final float mToTime;
    private final float mToValue;

    protected abstract void onSetInitialValue(Particle particle, float f);

    protected abstract void onSetValue(Particle particle, float f);

    public BaseSingleValueSpanModifier(float pFromValue, float pToValue, float pFromTime, float pToTime) {
        this.mFromValue = pFromValue;
        this.mToValue = pToValue;
        this.mFromTime = pFromTime;
        this.mToTime = pToTime;
        this.mSpanValue = this.mToValue - this.mFromValue;
        this.mDuration = this.mToTime - this.mFromTime;
    }

    @Override // org.anddev.andengine.entity.particle.initializer.IParticleInitializer
    public void onInitializeParticle(Particle pParticle) {
        onSetInitialValue(pParticle, this.mFromValue);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.IParticleModifier
    public void onUpdateParticle(Particle pParticle) {
        float lifeTime = pParticle.getLifeTime();
        if (lifeTime > this.mFromTime && lifeTime < this.mToTime) {
            float percent = (lifeTime - this.mFromTime) / this.mDuration;
            onSetValueInternal(pParticle, percent);
        }
    }

    public void onUpdateParticle(Particle pParticle, float overrideToTime) {
        float lifeTime = pParticle.getLifeTime();
        if (lifeTime > this.mFromTime) {
            float percent = (lifeTime - this.mFromTime) / (overrideToTime - this.mFromTime);
            onSetValueInternal(pParticle, percent);
        }
    }

    protected void onSetValueInternal(Particle pParticle, float pPercent) {
        onSetValue(pParticle, calculateValue(pPercent));
    }

    protected final float calculateValue(float pPercent) {
        return this.mFromValue + (this.mSpanValue * pPercent);
    }
}
