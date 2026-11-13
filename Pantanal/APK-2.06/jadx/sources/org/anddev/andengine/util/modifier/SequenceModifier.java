package org.anddev.andengine.util.modifier;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.util.modifier.util.ModifierUtils;

/* JADX INFO: loaded from: classes.dex */
public class SequenceModifier<T> extends BaseModifier<T> implements IModifier.IModifierListener<T> {
    private int mCurrentSubSequenceModifierIndex;
    private final float mDuration;
    private boolean mFinishedCached;
    private float mSecondsElapsed;
    private ISubSequenceModifierListener<T> mSubSequenceModifierListener;
    private final IModifier<T>[] mSubSequenceModifiers;

    public interface ISubSequenceModifierListener<T> {
        void onSubSequenceFinished(IModifier<T> iModifier, T t, int i);

        void onSubSequenceStarted(IModifier<T> iModifier, T t, int i);
    }

    public SequenceModifier(IModifier<T>... iModifierArr) throws IllegalArgumentException {
        this(null, null, iModifierArr);
    }

    public SequenceModifier(ISubSequenceModifierListener<T> pSubSequenceModifierListener, IModifier<T>... iModifierArr) throws IllegalArgumentException {
        this(pSubSequenceModifierListener, null, iModifierArr);
    }

    public SequenceModifier(IModifier.IModifierListener<T> pModifierListener, IModifier<T>... iModifierArr) throws IllegalArgumentException {
        this(null, pModifierListener, iModifierArr);
    }

    public SequenceModifier(ISubSequenceModifierListener<T> pSubSequenceModifierListener, IModifier.IModifierListener<T> pModifierListener, IModifier<T>... iModifierArr) throws IllegalArgumentException {
        super(pModifierListener);
        if (iModifierArr.length == 0) {
            throw new IllegalArgumentException("pModifiers must not be empty!");
        }
        this.mSubSequenceModifierListener = pSubSequenceModifierListener;
        this.mSubSequenceModifiers = iModifierArr;
        this.mDuration = ModifierUtils.getSequenceDurationOfModifier(iModifierArr);
        iModifierArr[0].addModifierListener(this);
    }

    protected SequenceModifier(SequenceModifier<T> pSequenceModifier) throws IModifier.DeepCopyNotSupportedException {
        this.mDuration = pSequenceModifier.mDuration;
        IModifier<T>[] iModifierArr = pSequenceModifier.mSubSequenceModifiers;
        this.mSubSequenceModifiers = new IModifier[iModifierArr.length];
        IModifier<T>[] iModifierArr2 = this.mSubSequenceModifiers;
        for (int i = iModifierArr2.length - 1; i >= 0; i--) {
            iModifierArr2[i] = iModifierArr[i].deepCopy();
        }
        iModifierArr2[0].addModifierListener(this);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public SequenceModifier<T> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new SequenceModifier<>(this);
    }

    public ISubSequenceModifierListener<T> getSubSequenceModifierListener() {
        return this.mSubSequenceModifierListener;
    }

    public void setSubSequenceModifierListener(ISubSequenceModifierListener<T> pSubSequenceModifierListener) {
        this.mSubSequenceModifierListener = pSubSequenceModifierListener;
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
            secondsElapsedRemaining -= this.mSubSequenceModifiers[this.mCurrentSubSequenceModifierIndex].onUpdate(secondsElapsedRemaining, pItem);
        }
        this.mFinishedCached = false;
        float secondsElapsedUsed = pSecondsElapsed - secondsElapsedRemaining;
        this.mSecondsElapsed += secondsElapsedUsed;
        return secondsElapsedUsed;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public void reset() {
        if (isFinished()) {
            this.mSubSequenceModifiers[this.mSubSequenceModifiers.length - 1].removeModifierListener(this);
        } else {
            this.mSubSequenceModifiers[this.mCurrentSubSequenceModifierIndex].removeModifierListener(this);
        }
        this.mCurrentSubSequenceModifierIndex = 0;
        this.mFinished = false;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
        this.mSubSequenceModifiers[0].addModifierListener(this);
        IModifier<T>[] iModifierArr = this.mSubSequenceModifiers;
        for (int i = iModifierArr.length - 1; i >= 0; i--) {
            iModifierArr[i].reset();
        }
    }

    @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
    public void onModifierStarted(IModifier<T> pModifier, T pItem) {
        if (this.mCurrentSubSequenceModifierIndex == 0) {
            onModifierStarted(pItem);
        }
        if (this.mSubSequenceModifierListener != null) {
            this.mSubSequenceModifierListener.onSubSequenceStarted(pModifier, pItem, this.mCurrentSubSequenceModifierIndex);
        }
    }

    @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
    public void onModifierFinished(IModifier<T> pModifier, T pItem) {
        if (this.mSubSequenceModifierListener != null) {
            this.mSubSequenceModifierListener.onSubSequenceFinished(pModifier, pItem, this.mCurrentSubSequenceModifierIndex);
        }
        pModifier.removeModifierListener(this);
        this.mCurrentSubSequenceModifierIndex++;
        if (this.mCurrentSubSequenceModifierIndex < this.mSubSequenceModifiers.length) {
            IModifier<T> nextSubSequenceModifier = this.mSubSequenceModifiers[this.mCurrentSubSequenceModifierIndex];
            nextSubSequenceModifier.addModifierListener(this);
        } else {
            this.mFinished = true;
            this.mFinishedCached = true;
            onModifierFinished(pItem);
        }
    }
}
