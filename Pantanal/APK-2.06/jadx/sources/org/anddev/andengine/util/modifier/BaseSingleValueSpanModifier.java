package org.anddev.andengine.util.modifier;

import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseSingleValueSpanModifier<T> extends BaseDurationModifier<T> {
    protected final IEaseFunction mEaseFunction;
    private final float mFromValue;
    private final float mValueSpan;

    protected abstract void onSetInitialValue(T t, float f);

    protected abstract void onSetValue(T t, float f, float f2);

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue) {
        this(pDuration, pFromValue, pToValue, null, IEaseFunction.DEFAULT);
    }

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValue, pToValue, null, pEaseFunction);
    }

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue, IModifier.IModifierListener<T> pModifierListener) {
        this(pDuration, pFromValue, pToValue, pModifierListener, IEaseFunction.DEFAULT);
    }

    public BaseSingleValueSpanModifier(float pDuration, float pFromValue, float pToValue, IModifier.IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pModifierListener);
        this.mFromValue = pFromValue;
        this.mValueSpan = pToValue - pFromValue;
        this.mEaseFunction = pEaseFunction;
    }

    protected BaseSingleValueSpanModifier(BaseSingleValueSpanModifier<T> pBaseSingleValueSpanModifier) {
        super(pBaseSingleValueSpanModifier);
        this.mFromValue = pBaseSingleValueSpanModifier.mFromValue;
        this.mValueSpan = pBaseSingleValueSpanModifier.mValueSpan;
        this.mEaseFunction = pBaseSingleValueSpanModifier.mEaseFunction;
    }

    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    protected void onManagedInitialize(T pItem) {
        onSetInitialValue(pItem, this.mFromValue);
    }

    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    protected void onManagedUpdate(float pSecondsElapsed, T pItem) {
        float percentageDone = this.mEaseFunction.getPercentage(getSecondsElapsed(), this.mDuration);
        onSetValue(pItem, percentageDone, this.mFromValue + (this.mValueSpan * percentageDone));
    }
}
