package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.particle.Particle;

/* JADX INFO: loaded from: classes.dex */
public class OnAndOffCameraExpireModifier extends OffCameraExpireModifier {
    private boolean mHasBeenOnCamera;

    public OnAndOffCameraExpireModifier(Camera pCamera) {
        super(pCamera);
        this.mHasBeenOnCamera = false;
    }

    @Override // org.anddev.andengine.entity.particle.modifier.OffCameraExpireModifier, org.anddev.andengine.entity.particle.modifier.IParticleModifier
    public void onUpdateParticle(Particle pParticle) {
        if (!this.mHasBeenOnCamera && getCamera().isRectangularShapeVisible(pParticle)) {
            this.mHasBeenOnCamera = true;
        }
        if (this.mHasBeenOnCamera) {
            super.onUpdateParticle(pParticle);
        }
    }
}
