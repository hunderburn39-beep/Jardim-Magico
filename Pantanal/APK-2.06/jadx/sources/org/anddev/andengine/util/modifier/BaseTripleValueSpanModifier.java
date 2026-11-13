package org.anddev.andengine.util.modifier;

import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTripleValueSpanModifier<T> extends BaseDoubleValueSpanModifier<T> {
    private final float mFromValueC;
    private final float mValueSpanC;

    protected abstract void onSetInitialValues(T t, float f, float f2, float f3);

    protected abstract void onSetValues(T t, float f, float f2, float f3, float f4);

    public BaseTripleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, IEaseFunction pEaseFunction) {
        this(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pFromValueC, pToValueC, null, pEaseFunction);
    }

    public BaseTripleValueSpanModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, float pFromValueC, float pToValueC, IModifier.IModifierListener<T> pModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pModifierListener, pEaseFunction);
        this.mFromValueC = pFromValueC;
        this.mValueSpanC = pToValueC - pFromValueC;
    }

    protected BaseTripleValueSpanModifier(BaseTripleValueSpanModifier<T> pBaseTripleValueSpanModifier) {
        super(pBaseTripleValueSpanModifier);
        this.mFromValueC = pBaseTripleValueSpanModifier.mFromValueC;
        this.mValueSpanC = pBaseTripleValueSpanModifier.mValueSpanC;
    }

    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier
    protected void onSetInitialValues(T pItem, float pValueA, float pValueB) {
        onSetInitialValues(pItem, pValueA, pValueB, this.mFromValueC);
    }

    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier
    protected void onSetValues(T pItem, float pPercentageDone, float pValueA, float pValueB) {
        onSetValues(pItem, pPercentageDone, pValueA, pValueB, this.mFromValueC + (this.mValueSpanC * pPercentageDone));
    }
}
