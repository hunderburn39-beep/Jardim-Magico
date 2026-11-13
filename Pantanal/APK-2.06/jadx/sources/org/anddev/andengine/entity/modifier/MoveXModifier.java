package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class MoveXModifier extends SingleValueSpanEntityModifier {
    public MoveXModifier(float pDuration, float pFromX, float pToX) {
        this(pDuration, pFromX, pToX, null, IEaseFunction.DEFAULT);
    }

    public MoveXModifier(float pDuration, float pFromX, float pToX, IEaseFunction pEaseFunction) {
        this(pDuration, pFromX, pToX, null, pEaseFunction);
    }

    public MoveXModifier(float pDuration, float pFromX, float pToX, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromX, pToX, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public MoveXModifier(float pDuration, float pFromX, float pToX, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromX, pToX, pEntityModifierListener, pEaseFunction);
    }

    protected MoveXModifier(MoveXModifier pMoveXModifier) {
        super(pMoveXModifier);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new MoveXModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetInitialValue(IEntity pEntity, float pX) {
        pEntity.setPosition(pX, pEntity.getY());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetValue(IEntity pEntity, float pPercentageDone, float pX) {
        pEntity.setPosition(pX, pEntity.getY());
    }
}
