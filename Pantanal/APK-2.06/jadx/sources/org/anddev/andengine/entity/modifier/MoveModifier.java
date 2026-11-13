package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class MoveModifier extends DoubleValueSpanEntityModifier {
    public MoveModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY) {
        this(pDuration, pFromX, pToX, pFromY, pToY, null, IEaseFunction.DEFAULT);
    }

    public MoveModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromX, pToX, pFromY, pToY, null, pEaseFunction);
    }

    public MoveModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromX, pToX, pFromY, pToY, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public MoveModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromX, pToX, pFromY, pToY, pEntityModifierListener, pEaseFunction);
    }

    protected MoveModifier(MoveModifier pMoveModifier) {
        super(pMoveModifier);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new MoveModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier
    public void onSetInitialValues(IEntity pEntity, float pX, float pY) {
        pEntity.setPosition(pX, pY);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier
    public void onSetValues(IEntity pEntity, float pPercentageDone, float pX, float pY) {
        pEntity.setPosition(pX, pY);
    }
}
