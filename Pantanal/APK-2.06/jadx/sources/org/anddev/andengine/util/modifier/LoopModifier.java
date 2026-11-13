package org.anddev.andengine.util.modifier;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class LoopModifier<T> extends BaseModifier<T> implements IModifier.IModifierListener<T> {
    public static final int LOOP_CONTINUOUS = -1;
    private final float mDuration;
    private boolean mFinishedCached;
    private int mLoop;
    private final int mLoopCount;
    private ILoopModifierListener<T> mLoopModifierListener;
    private final IModifier<T> mModifier;
    private boolean mModifierStartedCalled;
    private float mSecondsElapsed;

    public interface ILoopModifierListener<T> {
        void onLoopFinished(LoopModifier<T> loopModifier, int i, int i2);

        void onLoopStarted(LoopModifier<T> loopModifier, int i, int i2);
    }

    public LoopModifier(IModifier<T> pModifier) {
        this(pModifier, -1);
    }

    public LoopModifier(IModifier<T> pModifier, int pLoopCount) {
        this(pModifier, pLoopCount, null, null);
    }

    public LoopModifier(IModifier<T> pModifier, int pLoopCount, IModifier.IModifierListener<T> pModifierListener) {
        this(pModifier, pLoopCount, null, pModifierListener);
    }

    public LoopModifier(IModifier<T> pModifier, int pLoopCount, ILoopModifierListener<T> pLoopModifierListener) {
        this(pModifier, pLoopCount, pLoopModifierListener, null);
    }

    public LoopModifier(IModifier<T> pModifier, int pLoopCount, ILoopModifierListener<T> pLoopModifierListener, IModifier.IModifierListener<T> pModifierListener) {
        super(pModifierListener);
        this.mModifier = pModifier;
        this.mLoopCount = pLoopCount;
        this.mLoopModifierListener = pLoopModifierListener;
        this.mLoop = 0;
        this.mDuration = pLoopCount == -1 ? Float.POSITIVE_INFINITY : pModifier.getDuration() * pLoopCount;
        this.mModifier.addModifierListener(this);
    }

    protected LoopModifier(LoopModifier<T> pLoopModifier) throws IModifier.DeepCopyNotSupportedException {
        this(pLoopModifier.mModifier.deepCopy(), pLoopModifier.mLoopCount);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public LoopModifier<T> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new LoopModifier<>((LoopModifier) this);
    }

    public ILoopModifierListener<T> getLoopModifierListener() {
        return this.mLoopModifierListener;
    }

    public void setLoopModifierListener(ILoopModifierListener<T> pLoopModifierListener) {
        this.mLoopModifierListener = pLoopModifierListener;
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
    public float onUpdate(float pSecondsElapsed, T pItem) {
        if (this.mFinished) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        float secondsElapsedRemaining = pSecondsElapsed;
        this.mFinishedCached = false;
        while (secondsElapsedRemaining > BitmapDescriptorFactory.HUE_RED && !this.mFinishedCached) {
            secondsElapsedRemaining -= this.mModifier.onUpdate(secondsElapsedRemaining, pItem);
        }
        this.mFinishedCached = false;
        float secondsElapsedUsed = pSecondsElapsed - secondsElapsedRemaining;
        this.mSecondsElapsed += secondsElapsedUsed;
        return secondsElapsedUsed;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public void reset() {
        this.mLoop = 0;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
        this.mModifierStartedCalled = false;
        this.mModifier.reset();
    }

    @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
    public void onModifierStarted(IModifier<T> pModifier, T pItem) {
        if (!this.mModifierStartedCalled) {
            this.mModifierStartedCalled = true;
            onModifierStarted(pItem);
        }
        if (this.mLoopModifierListener != null) {
            this.mLoopModifierListener.onLoopStarted(this, this.mLoop, this.mLoopCount);
        }
    }

    @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
    public void onModifierFinished(IModifier<T> pModifier, T pItem) {
        if (this.mLoopModifierListener != null) {
            this.mLoopModifierListener.onLoopFinished(this, this.mLoop, this.mLoopCount);
        }
        if (this.mLoopCount == -1) {
            this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
            this.mModifier.reset();
            return;
        }
        this.mLoop++;
        if (this.mLoop >= this.mLoopCount) {
            this.mFinished = true;
            this.mFinishedCached = true;
            onModifierFinished(pItem);
        } else {
            this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
            this.mModifier.reset();
        }
    }
}
