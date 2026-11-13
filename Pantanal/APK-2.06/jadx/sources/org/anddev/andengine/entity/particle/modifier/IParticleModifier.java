package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;
import org.anddev.andengine.entity.particle.initializer.IParticleInitializer;

/* JADX INFO: loaded from: classes.dex */
public interface IParticleModifier extends IParticleInitializer {
    void onUpdateParticle(Particle particle);
}
