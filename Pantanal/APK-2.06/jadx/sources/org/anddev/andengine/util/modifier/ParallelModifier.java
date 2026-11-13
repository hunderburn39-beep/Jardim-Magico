package org.anddev.andengine.util.modifier;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class ParallelModifier<T> extends BaseModifier<T> implements IModifier.IModifierListener<T> {
    private final float mDuration;
    private boolean mFinishedCached;
    private final IModifier<T>[] mModifiers;
    private float mSecondsElapsed;

    public ParallelModifier(IModifier<T>... iModifierArr) throws IllegalArgumentException {
        this(null, iModifierArr);
    }

    public ParallelModifier(IModifier.IModifierListener<T> pModifierListener, IModifier<T>... iModifierArr) throws IllegalArgumentException {
        super(pModifierListener);
        if (iModifierArr.length == 0) {
            throw new IllegalArgumentException("pModifiers must not be empty!");
        }
        Arrays.sort(iModifierArr, MODIFIER_COMPARATOR_DURATION_DESCENDING);
        this.mModifiers = iModifierArr;
        IModifier<T> modifierWithLongestDuration = iModifierArr[0];
        this.mDuration = modifierWithLongestDuration.getDuration();
        modifierWithLongestDuration.addModifierListener(this);
    }

    protected ParallelModifier(ParallelModifier<T> pParallelModifier) throws IModifier.DeepCopyNotSupportedException {
        IModifier<T>[] iModifierArr = pParallelModifier.mModifiers;
        this.mModifiers = new IModifier[iModifierArr.length];
        IModifier<T>[] iModifierArr2 = this.mModifiers;
        for (int i = iModifierArr2.length - 1; i >= 0; i--) {
            iModifierArr2[i] = iModifierArr[i].deepCopy();
        }
        IModifier<T> modifierWithLongestDuration = iModifierArr2[0];
        this.mDuration = modifierWithLongestDuration.getDuration();
        modifierWithLongestDuration.addModifierListener(this);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public ParallelModifier<T> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new ParallelModifier<>(this);
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
        IModifier<T>[] iModifierArr = this.mModifiers;
        this.mFinishedCached = false;
        while (secondsElapsedRemaining > BitmapDescriptorFactory.HUE_RED && !this.mFinishedCached) {
            float secondsElapsedUsed = BitmapDescriptorFactory.HUE_RED;
            for (int i = iModifierArr.length - 1; i >= 0; i--) {
                secondsElapsedUsed = Math.max(secondsElapsedUsed, iModifierArr[i].onUpdate(pSecondsElapsed, pItem));
            }
            secondsElapsedRemaining -= secondsElapsedUsed;
        }
        this.mFinishedCached = false;
        float secondsElapsedUsed2 = pSecondsElapsed - secondsElapsedRemaining;
        this.mSecondsElapsed += secondsElapsedUsed2;
        return secondsElapsedUsed2;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public void reset() {
        this.mFinished = false;
        this.mSecondsElapsed = BitmapDescriptorFactory.HUE_RED;
        IModifier<T>[] iModifierArr = this.mModifiers;
        for (int i = iModifierArr.length - 1; i >= 0; i--) {
            iModifierArr[i].reset();
        }
    }

    @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
    public void onModifierStarted(IModifier<T> pModifier, T pItem) {
        onModifierStarted(pItem);
    }

    @Override // org.anddev.andengine.util.modifier.IModifier.IModifierListener
    public void onModifierFinished(IModifier<T> pModifier, T pItem) {
        this.mFinished = true;
        this.mFinishedCached = true;
        onModifierFinished(pItem);
    }
}
