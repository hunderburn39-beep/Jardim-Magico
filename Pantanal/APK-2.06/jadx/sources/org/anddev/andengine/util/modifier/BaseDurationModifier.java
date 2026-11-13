package org.anddev.andengine.util.modifier;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseDurationModifier<T> extends BaseModifier<T> {
    protected final float mDuration;
    private float mSecondsElapsed;

    protected abstract void onManagedInitialize(T t);

    protected abstract void onManagedUpdate(float f, T t);

    public BaseDurationModifier(float pDuration) {
        this.mDuration = pDuration;
    }

    public BaseDurationModifier(float pDuration, IModifier.IModifierListener<T> pModifierListener) {
        super(pModifierListener);
        this.mDuration = pDuration;
    }

    protected BaseDurationModifier(BaseDurationModifier<T> pBaseModifier) {
        this(pBaseModifier.mDuration);
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public float getSecondsElapsed() {
        return this.mSecondsElapsed;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public float getDuration() {
        return this.mDuration;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public final float onUpdate(float pSecondsElapsed, T pItem) {
        float secondsElapsedUsed = BitmapDescriptorFactory.HUE_RED;
        if (!this.mFinished) {
            if (this.mSecondsElapsed == BitmapDescriptorFactory.HUE_RED) {
                onManagedInitialize(pItem);
                onModifierStarted(pItem);
            }
            if (this.mSecondsElapsed + pSecondsElapsed < this.mDuration) {
                secondsElapsedUsed = pSecondsElapsed;
            } else {
                secondsElapsedUsed = this.mDuration - this.mSecondsElapsed;
            }
            this.mSecondsElapsed += secondsElapsedUsed;
            onManagedUpdate(secondsElapsedUsed, pItem);
            if (this.mDuration != -1.0f && this.mSecondsElapsed >= this.mDuration) {
                this.mSecondsElapsed = this.mDuration;
                this.mFinished = true;
                onModifierFinished(pItem);
            }
        }
        return secondsElapsedUsed;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public void reset() {
        this.mFinished = false;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
    }
}
