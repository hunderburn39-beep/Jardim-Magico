package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class RotationModifier extends SingleValueSpanEntityModifier {
    public RotationModifier(float pDuration, float pFromRotation, float pToRotation) {
        this(pDuration, pFromRotation, pToRotation, null, IEaseFunction.DEFAULT);
    }

    public RotationModifier(float pDuration, float pFromRotation, float pToRotation, IEaseFunction pEaseFunction) {
        this(pDuration, pFromRotation, pToRotation, null, pEaseFunction);
    }

    public RotationModifier(float pDuration, float pFromRotation, float pToRotation, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public RotationModifier(float pDuration, float pFromRotation, float pToRotation, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, pEaseFunction);
    }

    protected RotationModifier(RotationModifier pRotationModifier) {
        super(pRotationModifier);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new RotationModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetInitialValue(IEntity pEntity, float pRotation) {
        pEntity.setRotation(pRotation);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier
    public void onSetValue(IEntity pEntity, float pPercentageDone, float pRotation) {
        pEntity.setRotation(pRotation);
    }
}
