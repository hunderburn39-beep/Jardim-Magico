package org.anddev.andengine.entity.particle.emitter;

/* JADX INFO: loaded from: classes.dex */
public class PointParticleEmitter extends BaseParticleEmitter {
    public PointParticleEmitter(float pCenterX, float pCenterY) {
        super(pCenterX, pCenterY);
    }

    @Override // org.anddev.andengine.entity.particle.emitter.IParticleEmitter
    public void getPositionOffset(float[] pOffset) {
        pOffset[0] = this.mCenterX;
        pOffset[1] = this.mCenterY;
    }
}
