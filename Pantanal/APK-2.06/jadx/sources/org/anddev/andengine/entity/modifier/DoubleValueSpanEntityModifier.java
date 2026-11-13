package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class DoubleValueSpanEntityModifier extends BaseDoubleValueSpanModifier<IEntity> implements IEntityModifier {
    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB);
    }

    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pEaseFunction);
    }

    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pEntityModifierListener);
    }

    public DoubleValueSpanEntityModifier(float pDuration, float pFromValueA, float pToValueA, float pFromValueB, float pToValueB, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromValueA, pToValueA, pFromValueB, pToValueB, pEntityModifierListener, pEaseFunction);
    }

    protected DoubleValueSpanEntityModifier(DoubleValueSpanEntityModifier pDoubleValueSpanEntityModifier) {
        super(pDoubleValueSpanEntityModifier);
    }
}
