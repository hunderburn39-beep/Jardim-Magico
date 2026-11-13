package org.anddev.andengine.entity.particle;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public class Particle extends Sprite {
    boolean mDead;
    private float mDeathTime;
    private float mLifeTime;
    private final PhysicsHandler mPhysicsHandler;

    public Particle(float pX, float pY, TextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion);
        this.mDeathTime = -1.0f;
        this.mDead = false;
        this.mPhysicsHandler = new PhysicsHandler(this);
        this.mLifeTime = BitmapDescriptorFactory.HUE_RED;
    }

    public Particle(float pX, float pY, TextureRegion pTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pTextureRegion, pRectangleVertexBuffer);
        this.mDeathTime = -1.0f;
        this.mDead = false;
        this.mPhysicsHandler = new PhysicsHandler(this);
        this.mLifeTime = BitmapDescriptorFactory.HUE_RED;
    }

    public float getLifeTime() {
        return this.mLifeTime;
    }

    public float getDeathTime() {
        return this.mDeathTime;
    }

    public void setDeathTime(float pDeathTime) {
        this.mDeathTime = pDeathTime;
    }

    public boolean isDead() {
        return this.mDead;
    }

    public void setDead(boolean pDead) {
        this.mDead = pDead;
    }

    public PhysicsHandler getPhysicsHandler() {
        return this.mPhysicsHandler;
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
        if (!this.mDead) {
            this.mLifeTime += pSecondsElapsed;
            this.mPhysicsHandler.onUpdate(pSecondsElapsed);
            super.onManagedUpdate(pSecondsElapsed);
            float deathTime = this.mDeathTime;
            if (deathTime != -1.0f && this.mLifeTime > deathTime) {
                setDead(true);
            }
        }
    }

    @Override // org.anddev.andengine.entity.sprite.BaseSprite, org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        this.mPhysicsHandler.reset();
        this.mDead = false;
        this.mDeathTime = -1.0f;
        this.mLifeTime = BitmapDescriptorFactory.HUE_RED;
    }
}
