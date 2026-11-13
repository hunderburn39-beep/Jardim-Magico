package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class ScaleAtModifier extends ScaleModifier {
    private final float mScaleCenterX;
    private final float mScaleCenterY;

    public ScaleAtModifier(float pDuration, float pFromScale, float pToScale, float pScaleCenterX, float pScaleCenterY) {
        this(pDuration, pFromScale, pToScale, pScaleCenterX, pScaleCenterY, IEaseFunction.DEFAULT);
    }

    public ScaleAtModifier(float pDuration, float pFromScale, float pToScale, float pScaleCenterX, float pScaleCenterY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromScale, pToScale, pScaleCenterX, pScaleCenterY, (IEntityModifier.IEntityModifierListener) null, pEaseFunction);
    }

    public ScaleAtModifier(float pDuration, float pFromScale, float pToScale, float pScaleCenterX, float pScaleCenterY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pFromScale, pToScale, pScaleCenterX, pScaleCenterY, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public ScaleAtModifier(float pDuration, float pFromScale, float pToScale, float pScaleCenterX, float pScaleCenterY, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        this(pDuration, pFromScale, pToScale, pFromScale, pToScale, pScaleCenterX, pScaleCenterY, pEntityModifierListener, pEaseFunction);
    }

    public ScaleAtModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, float pScaleCenterX, float pScaleCenterY) {
        this(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pScaleCenterX, pScaleCenterY, IEaseFunction.DEFAULT);
    }

    public ScaleAtModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, float pScaleCenterX, float pScaleCenterY, IEaseFunction pEaseFunction) {
        this(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pScaleCenterX, pScaleCenterY, null, pEaseFunction);
    }

    public ScaleAtModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, float pScaleCenterX, float pScaleCenterY, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pScaleCenterX, pScaleCenterY, pEntityModifierListener, IEaseFunction.DEFAULT);
    }

    public ScaleAtModifier(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY, float pScaleCenterX, float pScaleCenterY, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pEntityModifierListener, pEaseFunction);
        this.mScaleCenterX = pScaleCenterX;
        this.mScaleCenterY = pScaleCenterY;
    }

    protected ScaleAtModifier(ScaleAtModifier pScaleAtModifier) {
        super(pScaleAtModifier);
        this.mScaleCenterX = pScaleAtModifier.mScaleCenterX;
        this.mScaleCenterY = pScaleAtModifier.mScaleCenterY;
    }

    @Override // org.anddev.andengine.entity.modifier.ScaleModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new ScaleAtModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseSingleValueSpanModifier, org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedInitialize(IEntity pEntity) {
        super.onManagedInitialize(pEntity);
        pEntity.setScaleCenter(this.mScaleCenterX, this.mScaleCenterY);
    }
}
