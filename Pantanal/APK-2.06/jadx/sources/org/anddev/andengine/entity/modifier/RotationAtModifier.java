package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class RotationAtModifier extends RotationModifier {
    private final float mRotationCenterX;
    private final float mRotationCenterY;

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY) {
        super(pDuration, pFromRotation, pToRotation, IEaseFunction.DEFAULT);
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
    }

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRotation, pToRotation, pEaseFunction);
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
    }

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, IEaseFunction.DEFAULT);
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
    }

    public RotationAtModifier(float pDuration, float pFromRotation, float pToRotation, float pRotationCenterX, float pRotationCenterY, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRotation, pToRotation, pEntityModifierListener, pEaseFunction);
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
    }

    protected RotationAtModifier(RotationAtModifier pRotationAtModifier) {
        super(pRotationAtModifier);
        this.mRotationCenterX = pRotationAtModifier.mRotationCenterX;
        this.mRotationCenterY = pRotationAtModifier.mRotationCenterY;
    }

    @Override // org.anddev.andengine.entity.modifier.RotationModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new RotationAtModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier, org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedInitialize(IEntity pEntity) {
        super.onManagedInitialize(pEntity);
        pEntity.setRotationCenter(this.mRotationCenterX, this.mRotationCenterY);
    }
}
