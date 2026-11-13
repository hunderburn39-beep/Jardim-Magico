package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public class MoveByModifier extends DoubleValueChangeEntityModifier {
    public MoveByModifier(float pDuration, float pX, float pY) {
        super(pDuration, pX, pY);
    }

    public MoveByModifier(float pDuration, float pX, float pY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pX, pY, pEntityModifierListener);
    }

    protected MoveByModifier(DoubleValueChangeEntityModifier pDoubleValueChangeEntityModifier) {
        super(pDoubleValueChangeEntityModifier);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new MoveByModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueChangeModifier
    public void onChangeValues(float pSecondsElapsed, IEntity pEntity, float pX, float pY) {
        pEntity.setPosition(pEntity.getX() + pX, pEntity.getY() + pY);
    }
}
