package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class MoveYModifier extends SingleValueSpanEntityModifier {
    public MoveYModifier(float pDuration, float pFromY, float pToY) {
        this(pDuration, pFromY, pToY, null, IEaseFunction.DEFAULT);
    }

    public MoveYModifier(float pDuration, float pFromY, float pToY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromY, pToY, null, pEaseFunction);
    }

    public MoveYModifier(float pDuration, float pFromY, float pToY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromY, pToY, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public MoveYModifier(float pDuration, float pFromY, float pToY, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromY, pToY, pEntityModifierListener, pEaseFunction);
    }

    protected MoveYModifier(MoveYModifier pMoveYModifier) {
        super(pMoveYModifier);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new MoveYModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetInitialValue(IEntity pEntity, float pY) {
        pEntity.setPosition(pEntity.getX(), pY);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetValue(IEntity pEntity, float pPercentageDone, float pY) {
        pEntity.setPosition(pEntity.getX(), pY);
    }
}
