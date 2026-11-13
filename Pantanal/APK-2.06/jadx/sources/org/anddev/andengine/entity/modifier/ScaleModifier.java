package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class ScaleModifier extends DoubleValueSpanEntityModifier {
    public ScaleModifier(float pDuration, float pFromScale, float pToScale) {
        this(pDuration, pFromScale, pToScale, (IEntityModifier.IEntityModifierListener) null, IEaseFunction.DEFAULT);
    }

    public ScaleModifier(float pDuration, float pFromScale, float pToScale, IEaseFunction pEaseFunction) {
        this(pDuration, pFromScale, pToScale, (IEntityModifier.IEntityModifierListener) null, pEaseFunction);
    }

    public ScaleModifier(float pDuration, float pFromScale, float pToScale, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pFromScale, pToScale, pFromScale, pToScale, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public ScaleModifier(float pDuration, float pFromScale, float pToScale, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        this(pDuration, pFromScale, pToScale, pFromScale, pToScale, pEntityModifierListener, pEaseFunction);
    }

    public ScaleModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY) {
        this(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, null, IEaseFunction.DEFAULT);
    }

    public ScaleModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, null, pEaseFunction);
    }

    public ScaleModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public ScaleModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pEntityModifierListener, pEaseFunction);
    }

    protected ScaleModifier(ScaleModifier pScaleModifier) {
        super(pScaleModifier);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new ScaleModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier
    public void onSetInitialValues(IEntity pEntity, float pScaleA, float pScaleB) {
        pEntity.setScale(pScaleA, pScaleB);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDoubleValueSpanModifier
    public void onSetValues(IEntity pEntity, float pPercentageDone, float pScaleA, float pScaleB) {
        pEntity.setScale(pScaleA, pScaleB);
    }
}
