package org.anddev.andengine.util.modifier;

import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseDoubleValueSpanModifier<T> extends BaseSingleValueSpanModifier<T> {
    private final float mFromValueB;
    private final float mValueSpanB;

    protected abstract void onSetInitialValues(T t, float f, float f2);

    protected abstract void onSetValues(T t, float f, float f2, float f3);

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, null, IEaseFunction.DEFAULT);
    }

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, null, pEaseFunction);
    }

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IModifier.IModifierListener<T> pModifierListener) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pModifierListener, IEaseFunction.DEFAULT);
    }

    public BaseDoubleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IModifier.IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pModifierListener, pEaseFunction);
        this.mFromValueB = pFromValueB;
        this.mValueSpanB = pToValueB - pFromValueB;
    }

    protected BaseDoubleValueSpanModifier(BaseDoubleValueSpanModifier<T> pBaseDoubleValueSpanModifier) {
        super(pBaseDoubleValueSpanModifier);
        this.mFromValueB = pBaseDoubleValueSpanModifier.mFromValueB;
        this.mValueSpanB = pBaseDoubleValueSpanModifier.mValueSpanB;
    }

    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    protected void onSetInitialValue(T pItem, float pValueA) {
        onSetInitialValues(pItem, pValueA, this.mFromValueB);
    }

    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    protected void onSetValue(T pItem, float pPercentageDone, float pValueA) {
        onSetValues(pItem, pPercentageDone, pValueA, this.mFromValueB + (this.mValueSpanB * pPercentageDone));
    }
}
