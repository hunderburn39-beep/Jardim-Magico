package org.anddev.andengine.entity.particle;

import android.util.FloatMath;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.particle.emitter.IParticleEmitter;
import org.anddev.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.anddev.andengine.entity.particle.initializer.IParticleInitializer;
import org.anddev.andengine.entity.particle.modifier.IParticleModifier;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class ParticleSystem extends Entity {
    private static final int BLENDFUNCTION_DESTINATION_DEFAULT = 771;
    private static final int BLENDFUNCTION_SOURCE_DEFAULT = 1;
    private final float[] POSITION_OFFSET;
    private int mDestinationBlendFunction;
    private final IParticleEmitter mParticleEmitter;
    private int mParticleInitializerCount;
    private final ArrayList<IParticleInitializer> mParticleInitializers;
    private int mParticleModifierCount;
    private final ArrayList<IParticleModifier> mParticleModifiers;
    private final Particle[] mParticles;
    private int mParticlesAlive;
    private float mParticlesDueToSpawn;
    private final int mParticlesMaximum;
    private boolean mParticlesSpawnEnabled;
    private final float mRateMaximum;
    private final float mRateMinimum;
    private RectangleVertexBuffer mSharedParticleVertexBuffer;
    private int mSourceBlendFunction;
    private final TextureRegion mTextureRegion;

    @Deprecated
    public ParticleSystem(float pX, float pY, float pWidth, float pHeight, float pRateMinimum, float pRateMaximum, int pParticlesMaximum, TextureRegion pTextureRegion) {
        this(new RectangleParticleEmitter((pWidth * 0.5f) + pX, (0.5f * pHeight) + pY, pWidth, pHeight), pRateMinimum, pRateMaximum, pParticlesMaximum, pTextureRegion);
    }

    public ParticleSystem(IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum, TextureRegion pTextureRegion) {
        super(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        this.POSITION_OFFSET = new float[2];
        this.mSourceBlendFunction = 1;
        this.mDestinationBlendFunction = 771;
        this.mParticleInitializers = new ArrayList<>();
        this.mParticleModifiers = new ArrayList<>();
        this.mParticlesSpawnEnabled = true;
        this.mParticleEmitter = pParticleEmitter;
        this.mParticles = new Particle[pParticlesMaximum];
        this.mRateMinimum = pRateMinimum;
        this.mRateMaximum = pRateMaximum;
        this.mParticlesMaximum = pParticlesMaximum;
        this.mTextureRegion = pTextureRegion;
        registerUpdateHandler(this.mParticleEmitter);
    }

    public boolean isParticlesSpawnEnabled() {
        return this.mParticlesSpawnEnabled;
    }

    public void setParticlesSpawnEnabled(boolean pParticlesSpawnEnabled) {
        this.mParticlesSpawnEnabled = pParticlesSpawnEnabled;
    }

    public void setBlendFunction(int pSourceBlendFunction, int pDestinationBlendFunction) {
        this.mSourceBlendFunction = pSourceBlendFunction;
        this.mDestinationBlendFunction = pDestinationBlendFunction;
    }

    public IParticleEmitter getParticleEmitter() {
        return this.mParticleEmitter;
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        this.mParticlesDueToSpawn = BitmapDescriptorFactory.HUE_RED;
        this.mParticlesAlive = 0;
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedDraw(GL10 pGL, Camera pCamera) {
        Particle[] particles = this.mParticles;
        for (int i = this.mParticlesAlive - 1; i >= 0; i--) {
            particles[i].onDraw(pGL, pCamera);
        }
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (this.mParticlesSpawnEnabled) {
            spawnParticles(pSecondsElapsed);
        }
        Particle[] particles = this.mParticles;
        ArrayList<IParticleModifier> particleModifiers = this.mParticleModifiers;
        int particleModifierCountMinusOne = this.mParticleModifierCount - 1;
        for (int i = this.mParticlesAlive - 1; i >= 0; i--) {
            Particle particle = particles[i];
            for (int j = particleModifierCountMinusOne; j >= 0; j--) {
                particleModifiers.get(j).onUpdateParticle(particle);
            }
            particle.onUpdate(pSecondsElapsed);
            if (particle.mDead) {
                this.mParticlesAlive--;
                int particlesAlive = this.mParticlesAlive;
                particles[i] = particles[particlesAlive];
                particles[particlesAlive] = particle;
            }
        }
    }

    public void addParticleModifier(IParticleModifier pParticleModifier) {
        this.mParticleModifiers.add(pParticleModifier);
        this.mParticleModifierCount++;
    }

    public void removeParticleModifier(IParticleModifier pParticleModifier) {
        this.mParticleModifierCount--;
        this.mParticleModifiers.remove(pParticleModifier);
    }

    public void addParticleInitializer(IParticleInitializer pParticleInitializer) {
        this.mParticleInitializers.add(pParticleInitializer);
        this.mParticleInitializerCount++;
    }

    public void removeParticleInitializer(IParticleInitializer pParticleInitializer) {
        this.mParticleInitializerCount--;
        this.mParticleInitializers.remove(pParticleInitializer);
    }

    private void spawnParticles(float pSecondsElapsed) {
        float currentRate = determineCurrentRate();
        float newParticlesThisFrame = currentRate * pSecondsElapsed;
        this.mParticlesDueToSpawn += newParticlesThisFrame;
        int particlesToSpawnThisFrame = Math.min(this.mParticlesMaximum - this.mParticlesAlive, (int) FloatMath.floor(this.mParticlesDueToSpawn));
        this.mParticlesDueToSpawn -= particlesToSpawnThisFrame;
        for (int i = 0; i < particlesToSpawnThisFrame; i++) {
            spawnParticle();
        }
    }

    private void spawnParticle() {
        Particle[] particles = this.mParticles;
        int particlesAlive = this.mParticlesAlive;
        if (particlesAlive < this.mParticlesMaximum) {
            Particle particle = particles[particlesAlive];
            this.mParticleEmitter.getPositionOffset(this.POSITION_OFFSET);
            float x = this.POSITION_OFFSET[0];
            float y = this.POSITION_OFFSET[1];
            if (particle != null) {
                particle.reset();
                particle.setPosition(x, y);
            } else {
                if (particlesAlive == 0) {
                    particle = new Particle(x, y, this.mTextureRegion);
                    this.mSharedParticleVertexBuffer = particle.getVertexBuffer();
                } else {
                    particle = new Particle(x, y, this.mTextureRegion, this.mSharedParticleVertexBuffer);
                }
                particles[particlesAlive] = particle;
            }
            particle.setBlendFunction(this.mSourceBlendFunction, this.mDestinationBlendFunction);
            ArrayList<IParticleInitializer> particleInitializers = this.mParticleInitializers;
            for (int i = this.mParticleInitializerCount - 1; i >= 0; i--) {
                particleInitializers.get(i).onInitializeParticle(particle);
            }
            ArrayList<IParticleModifier> particleModifiers = this.mParticleModifiers;
            for (int i2 = this.mParticleModifierCount - 1; i2 >= 0; i2--) {
                particleModifiers.get(i2).onInitializeParticle(particle);
            }
            this.mParticlesAlive++;
        }
    }

    private float determineCurrentRate() {
        return this.mRateMinimum == this.mRateMaximum ? this.mRateMinimum : (MathUtils.RANDOM.nextFloat() * (this.mRateMaximum - this.mRateMinimum)) + this.mRateMinimum;
    }
}
