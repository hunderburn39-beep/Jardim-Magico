package org.anddev.andengine.entity.particle.emitter;

import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class RectangleOutlineParticleEmitter extends BaseRectangleParticleEmitter {
    public RectangleOutlineParticleEmitter(float pCenterX, float pCenterY, float pWidth, float pHeight) {
        super(pCenterX, pCenterY, pWidth, pHeight);
    }

    @Override // org.anddev.andengine.entity.particle.emitter.IParticleEmitter
    public void getPositionOffset(float[] pOffset) {
        pOffset[0] = this.mCenterX + (MathUtils.randomSign() * this.mWidthHalf);
        pOffset[1] = this.mCenterY + (MathUtils.randomSign() * this.mHeightHalf);
    }
}
