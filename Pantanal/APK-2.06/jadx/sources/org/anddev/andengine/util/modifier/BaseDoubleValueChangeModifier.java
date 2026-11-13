package org.anddev.andengine.util.modifier;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseDoubleValueChangeModifier<T> extends BaseSingleValueChangeModifier<T> {
    private float mValueChangeBPerSecond;

    protected abstract void onChangeValues(float f, T t, float f2, float f3);

    public BaseDoubleValueChangeModifier(float pDuration, float pValueChangeA, float pValueChangeB) {
        this(pDuration, pValueChangeA, pValueChangeB, null);
    }

    public BaseDoubleValueChangeModifier(float pDuration, float pValueChangeA, float pValueChangeB, IModifier.IModifierListener<T> pModifierListener) {
        super(pDuration, pValueChangeA, pModifierListener);
        this.mValueChangeBPerSecond = pValueChangeB / pDuration;
    }

    protected BaseDoubleValueChangeModifier(BaseDoubleValueChangeModifier<T> pBaseDoubleValueChangeModifier) {
        super(pBaseDoubleValueChangeModifier);
        this.mValueChangeBPerSecond = pBaseDoubleValueChangeModifier.mValueChangeBPerSecond;
    }

    @Override // org.anddev.andengine.util.modifier.BaseSingleValueChangeModifier
    protected void onChangeValue(float pSecondsElapsed, T pItem, float pValueA) {
        onChangeValues(pSecondsElapsed, pItem, pValueA, this.mValueChangeBPerSecond * pSecondsElapsed);
    }
}
