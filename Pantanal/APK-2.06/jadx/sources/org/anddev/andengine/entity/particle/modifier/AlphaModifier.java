package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public class AlphaModifier extends BaseSingleValueSpanModifier {
    public AlphaModifier(float pFromAlpha, float pToAlpha, float pFromTime, float pToTime) {
        super(pFromAlpha, pToAlpha, pFromTime, pToTime);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseSingleValueSpanModifier
    protected void onSetInitialValue(Particle pParticle, float pAlpha) {
        pParticle.setAlpha(pAlpha);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseSingleValueSpanModifier
    protected void onSetValue(Particle pParticle, float pAlpha) {
        pParticle.setAlpha(pAlpha);
    }
}
