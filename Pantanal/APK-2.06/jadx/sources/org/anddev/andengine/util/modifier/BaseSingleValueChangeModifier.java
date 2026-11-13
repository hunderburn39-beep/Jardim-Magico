package org.anddev.andengine.util.modifier;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseSingleValueChangeModifier<T> extends BaseDurationModifier<T> {
    private final float mValueChangePerSecond;

    protected abstract void onChangeValue(float f, T t, float f2);

    public BaseSingleValueChangeModifier(float pDuration, float pValueChange) {
        this(pDuration, pValueChange, null);
    }

    public BaseSingleValueChangeModifier(float pDuration, float pValueChange, IModifier.IModifierListener<T> pModifierListener) {
        super(pDuration, pModifierListener);
        this.mValueChangePerSecond = pValueChange / pDuration;
    }

    protected BaseSingleValueChangeModifier(BaseSingleValueChangeModifier<T> pBaseSingleValueChangeModifier) {
        super(pBaseSingleValueChangeModifier);
        this.mValueChangePerSecond = pBaseSingleValueChangeModifier.mValueChangePerSecond;
    }

    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    protected void onManagedInitialize(T pItem) {
    }

    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    protected void onManagedUpdate(float pSecondsElapsed, T pItem) {
        onChangeValue(pSecondsElapsed, pItem, this.mValueChangePerSecond * pSecondsElapsed);
    }
}
