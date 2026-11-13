package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public class RotationByModifier extends SingleValueChangeEntityModifier {
    public RotationByModifier(float pDuration, float pRotation) {
        super(pDuration, pRotation);
    }

    public RotationByModifier(float pDuration, float pRotation, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pRotation, pEntityModifierListener);
    }

    protected RotationByModifier(RotationByModifier pRotationByModifier) {
        super(pRotationByModifier);
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new RotationByModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueChangeModifier
    public void onChangeValue(float pSecondsElapsed, IEntity pEntity, float pRotation) {
        pEntity.setRotation(pEntity.getRotation() + pRotation);
    }
}
