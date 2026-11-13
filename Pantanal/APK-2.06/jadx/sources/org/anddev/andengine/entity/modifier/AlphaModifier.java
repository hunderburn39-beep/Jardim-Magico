package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class AlphaModifier extends SingleValueSpanEntityModifier {
    public AlphaModifier(float pDuration, float pFromAlpha, float pToAlpha) {
        this(pDuration, pFromAlpha, pToAlpha, null, IEaseFunction.DEFAULT);
    }

    public AlphaModifier(float pDuration, float pFromAlpha, float pToAlpha, IEaseFunction pEaseFunction) {
        this(pDuration, pFromAlpha, pToAlpha, null, pEaseFunction);
    }

    public AlphaModifier(float pDuration, float pFromAlpha, float pToAlpha, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromAlpha, pToAlpha, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public AlphaModifier(float pDuration, float pFromAlpha, float pToAlpha, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromAlpha, pToAlpha, pEntityModifierListener, pEaseFunction);
    }

    protected AlphaModifier(AlphaModifier pAlphaModifier) {
        super(pAlphaModifier);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new AlphaModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetInitialValue(IEntity pEntity, float pAlpha) {
        pEntity.setAlpha(pAlpha);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetValue(IEntity pEntity, float pPercentageDone, float pAlpha) {
        pEntity.setAlpha(pAlpha);
    }
}
