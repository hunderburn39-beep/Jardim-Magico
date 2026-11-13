package org.anddev.andengine.engine.handler.physics;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.handler.BaseEntityUpdateHandler;
import org.anddev.andengine.entity.IEntity;

/* JADX INFO: loaded from: classes.dex */
public class PhysicsHandler extends BaseEntityUpdateHandler {
    protected float mAccelerationX;
    protected float mAccelerationY;
    protected float mAngularVelocity;
    private boolean mEnabled;
    protected float mVelocityX;
    protected float mVelocityY;

    public PhysicsHandler(IEntity pEntity) {
        super(pEntity);
        this.mEnabled = true;
        this.mAccelerationX = BitmapDescriptorFactory.HUE_RED;
        this.mAccelerationY = BitmapDescriptorFactory.HUE_RED;
        this.mVelocityX = BitmapDescriptorFactory.HUE_RED;
        this.mVelocityY = BitmapDescriptorFactory.HUE_RED;
        this.mAngularVelocity = BitmapDescriptorFactory.HUE_RED;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean pEnabled) {
        this.mEnabled = pEnabled;
    }

    public float getVelocityX() {
        return this.mVelocityX;
    }

    public float getVelocityY() {
        return this.mVelocityY;
    }

    public void setVelocityX(float pVelocityX) {
        this.mVelocityX = pVelocityX;
    }

    public void setVelocityY(float pVelocityY) {
        this.mVelocityY = pVelocityY;
    }

    public void setVelocity(float pVelocity) {
        this.mVelocityX = pVelocity;
        this.mVelocityY = pVelocity;
    }

    public void setVelocity(float pVelocityX, float pVelocityY) {
        this.mVelocityX = pVelocityX;
        this.mVelocityY = pVelocityY;
    }

    public float getAccelerationX() {
        return this.mAccelerationX;
    }

    public float getAccelerationY() {
        return this.mAccelerationY;
    }

    public void setAccelerationX(float pAccelerationX) {
        this.mAccelerationX = pAccelerationX;
    }

    public void setAccelerationY(float pAccelerationY) {
        this.mAccelerationY = pAccelerationY;
    }

    public void setAcceleration(float pAccelerationX, float pAccelerationY) {
        this.mAccelerationX = pAccelerationX;
        this.mAccelerationY = pAccelerationY;
    }

    public void setAcceleration(float pAcceleration) {
        this.mAccelerationX = pAcceleration;
        this.mAccelerationY = pAcceleration;
    }

    public void accelerate(float pAccelerationX, float pAccelerationY) {
        this.mAccelerationX += pAccelerationX;
        this.mAccelerationY += pAccelerationY;
    }

    public float getAngularVelocity() {
        return this.mAngularVelocity;
    }

    public void setAngularVelocity(float pAngularVelocity) {
        this.mAngularVelocity = pAngularVelocity;
    }

    @Override // org.anddev.andengine.engine.handler.BaseEntityUpdateHandler
    protected void onUpdate(float pSecondsElapsed, IEntity pEntity) {
        if (this.mEnabled) {
            float accelerationX = this.mAccelerationX;
            float accelerationY = this.mAccelerationY;
            if (accelerationX != BitmapDescriptorFactory.HUE_RED || accelerationY != BitmapDescriptorFactory.HUE_RED) {
                this.mVelocityX += accelerationX * pSecondsElapsed;
                this.mVelocityY += accelerationY * pSecondsElapsed;
            }
            float angularVelocity = this.mAngularVelocity;
            if (angularVelocity != BitmapDescriptorFactory.HUE_RED) {
                pEntity.setRotation(pEntity.getRotation() + (angularVelocity * pSecondsElapsed));
            }
            float velocityX = this.mVelocityX;
            float velocityY = this.mVelocityY;
            if (velocityX != BitmapDescriptorFactory.HUE_RED || velocityY != BitmapDescriptorFactory.HUE_RED) {
                pEntity.setPosition(pEntity.getX() + (velocityX * pSecondsElapsed), pEntity.getY() + (velocityY * pSecondsElapsed));
            }
        }
    }

    @Override // org.anddev.andengine.engine.handler.BaseEntityUpdateHandler, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mAccelerationX = BitmapDescriptorFactory.HUE_RED;
        this.mAccelerationY = BitmapDescriptorFactory.HUE_RED;
        this.mVelocityX = BitmapDescriptorFactory.HUE_RED;
        this.mVelocityY = BitmapDescriptorFactory.HUE_RED;
        this.mAngularVelocity = BitmapDescriptorFactory.HUE_RED;
    }
}
