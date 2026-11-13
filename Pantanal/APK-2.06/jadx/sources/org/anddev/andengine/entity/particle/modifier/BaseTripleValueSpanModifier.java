package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTripleValueSpanModifier extends BaseDoubleValueSpanModifier {
    private final float mFromValueC;
    private final float mSpanValueC;
    private final float mToValueC;

    protected abstract void onSetInitialValues(Particle particle, float f, float f2, float f3);

    protected abstract void onSetValues(Particle particle, float f, float f2, float f3);

    public BaseTripleValueSpanModifier(float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, float pFromTime, float pToTime) {
        super(pFromValueA, pToValueA, pFromValueB, pToValueB, pFromTime, pToTime);
        this.mFromValueC = pFromValueC;
        this.mToValueC = pToValueC;
        this.mSpanValueC = this.mToValueC - this.mFromValueC;
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseDoubleValueSpanModifier
    @Deprecated
    protected void onSetValues(Particle pParticle, float pValueA, float pValueB) {
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseDoubleValueSpanModifier
    public void onSetInitialValues(Particle pParticle, float pValueA, float pValueB) {
        onSetInitialValues(pParticle, pValueA, pValueB, this.mFromValueC);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseDoubleValueSpanModifier, org.anddev.andengine.entity.particle.modifier.BaseSingleValueSpanModifier
    protected void onSetValueInternal(Particle pParticle, float pPercent) {
        onSetValues(pParticle, super.calculateValue(pPercent), super.calculateValueB(pPercent), calculateValueC(pPercent));
    }

    private final float calculateValueC(float pPercent) {
        return this.mFromValueC + (this.mSpanValueC * pPercent);
    }
}
