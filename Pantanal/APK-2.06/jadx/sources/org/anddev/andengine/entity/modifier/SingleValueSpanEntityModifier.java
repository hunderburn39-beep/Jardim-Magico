package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class SingleValueSpanEntityModifier extends BaseSingleValueSpanModifier<IEntity> implements IEntityModifier {
    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue) {
        super(pDuration, pFromValue, pToValue);
    }

    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValue, pToValue, pEaseFunction);
    }

    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromValue, pToValue, pEntityModifierListener);
    }

    public SingleValueSpanEntityModifier(float pDuration, float pFromValue, float pToValue, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValue, pToValue, pEntityModifierListener, pEaseFunction);
    }

    protected SingleValueSpanEntityModifier(SingleValueSpanEntityModifier pSingleValueSpanEntityModifier) {
        super(pSingleValueSpanEntityModifier);
    }
}
