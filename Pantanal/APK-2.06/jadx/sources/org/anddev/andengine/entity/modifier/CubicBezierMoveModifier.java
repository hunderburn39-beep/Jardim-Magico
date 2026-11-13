package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class CubicBezierMoveModifier extends DurationEntityModifier {
    private final IEaseFunction mEaseFunction;
    private final float mX1;
    private final float mX2;
    private final float mX3;
    private final float mX4;
    private final float mY1;
    private final float mY2;
    private final float mY3;
    private final float mY4;

    public CubicBezierMoveModifier(float pDuration, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4, IEaseFunction pEaseFunction) {
        super(pDuration);
        this.mX1 = pX1;
        this.mY1 = pY1;
        this.mX2 = pX2;
        this.mY2 = pY2;
        this.mX3 = pX3;
        this.mY3 = pY3;
        this.mX4 = pX4;
        this.mY4 = pY4;
        this.mEaseFunction = pEaseFunction;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new CubicBezierMoveModifier(this.mDuration, this.mX1, this.mY1, this.mX2, this.mY2, this.mX3, this.mY3, this.mX4, this.mY4, this.mEaseFunction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedUpdate(float pSecondsElapsed, IEntity pEntity) {
        float percentageDone = this.mEaseFunction.getPercentage(getSecondsElapsed(), this.mDuration);
        float u = 1.0f - percentageDone;
        float tt = percentageDone * percentageDone;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * percentageDone;
        float ut3 = 3.0f * uu * percentageDone;
        float utt3 = 3.0f * u * tt;
        float x = (this.mX1 * uuu) + (this.mX2 * ut3) + (this.mX3 * utt3) + (this.mX4 * ttt);
        float y = (this.mY1 * uuu) + (this.mY2 * ut3) + (this.mY3 * utt3) + (this.mY4 * ttt);
        pEntity.setPosition(x, y);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedInitialize(IEntity pEntity) {
    }
}
