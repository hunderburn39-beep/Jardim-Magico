package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public class RotationModifier extends BaseSingleValueSpanModifier {
    public RotationModifier(float pFromRotation, float pToRotation, float pFromTime, float pToTime) {
        super(pFromRotation, pToRotation, pFromTime, pToTime);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseSingleValueSpanModifier
    protected void onSetInitialValue(Particle pParticle, float pRotation) {
        pParticle.setRotation(pRotation);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseSingleValueSpanModifier
    protected void onSetValue(Particle pParticle, float pRotation) {
        pParticle.setRotation(pRotation);
    }
}
