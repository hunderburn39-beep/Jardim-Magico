package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public class ScaleModifier extends BaseDoubleValueSpanModifier {
    public ScaleModifier(float pFromScale, float pToScale, float pFromTime, float pToTime) {
        this(pFromScale, pToScale, pFromScale, pToScale, pFromTime, pToTime);
    }

    public ScaleModifier(float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, float pFromTime, float pToTime) {
        super(pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pFromTime, pToTime);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseDoubleValueSpanModifier
    protected void onSetInitialValues(Particle pParticle, float pScaleX, float pScaleY) {
        pParticle.setScale(pScaleX, pScaleY);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.BaseDoubleValueSpanModifier
    protected void onSetValues(Particle pParticle, float pScaleX, float pScaleY) {
        pParticle.setScale(pScaleX, pScaleY);
    }
}
