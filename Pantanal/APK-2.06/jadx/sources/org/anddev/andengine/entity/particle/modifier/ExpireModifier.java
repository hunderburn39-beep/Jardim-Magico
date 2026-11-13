package org.anddev.andengine.entity.particle.modifier;

import org.anddev.andengine.entity.particle.Particle;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class ExpireModifier implements IParticleModifier {
    private float mMaxLifeTime;
    private float mMinLifeTime;

    public ExpireModifier(float pLifeTime) {
        this(pLifeTime, pLifeTime);
    }

    public ExpireModifier(float pMinLifeTime, float pMaxLifeTime) {
        this.mMinLifeTime = pMinLifeTime;
        this.mMaxLifeTime = pMaxLifeTime;
    }

    public float getMinLifeTime() {
        return this.mMinLifeTime;
    }

    public float getMaxLifeTime() {
        return this.mMaxLifeTime;
    }

    public void setLifeTime(float pLifeTime) {
        this.mMinLifeTime = pLifeTime;
        this.mMaxLifeTime = pLifeTime;
    }

    public void setLifeTime(float pMinLifeTime, float pMaxLifeTime) {
        this.mMinLifeTime = pMinLifeTime;
        this.mMaxLifeTime = pMaxLifeTime;
    }

    @Override // org.anddev.andengine.entity.particle.initializer.IParticleInitializer
    public void onInitializeParticle(Particle pParticle) {
        pParticle.setDeathTime((MathUtils.RANDOM.nextFloat() * (this.mMaxLifeTime - this.mMinLifeTime)) + this.mMinLifeTime);
    }

    @Override // org.anddev.andengine.entity.particle.modifier.IParticleModifier
    public void onUpdateParticle(Particle pParticle) {
    }
}
