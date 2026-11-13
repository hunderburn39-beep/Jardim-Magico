package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class QuadraticBezierMoveModifier extends DurationEntityModifier {
    private final IEaseFunction mEaseFunction;
    private final float mX1;
    private final float mX2;
    private final float mX3;
    private final float mY1;
    private final float mY2;
    private final float mY3;

    public QuadraticBezierMoveModifier(float pDuration, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, IEaseFunction pEaseFunction) {
        super(pDuration);
        this.mX1 = pX1;
        this.mY1 = pY1;
        this.mX2 = pX2;
        this.mY2 = pY2;
        this.mX3 = pX3;
        this.mY3 = pY3;
        this.mEaseFunction = pEaseFunction;
    }

    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new QuadraticBezierMoveModifier(this.mDuration, this.mX1, this.mY1, this.mX2, this.mY2, this.mX3, this.mY3, this.mEaseFunction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedUpdate(float pSecondsElapsed, IEntity pEntity) {
        float percentageDone = this.mEaseFunction.getPercentage(getSecondsElapsed(), this.mDuration);
        float u = 1.0f - percentageDone;
        float tt = percentageDone * percentageDone;
        float uu = u * u;
        float ut2 = 2.0f * u * percentageDone;
        float x = (this.mX1 * uu) + (this.mX2 * ut2) + (this.mX3 * tt);
        float y = (this.mY1 * uu) + (this.mY2 * ut2) + (this.mY3 * tt);
        pEntity.setPosition(x, y);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedInitialize(IEntity pEntity) {
    }
}
