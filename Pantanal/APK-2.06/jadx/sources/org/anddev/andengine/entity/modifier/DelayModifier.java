package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public class DelayModifier extends DurationEntityModifier {
    public DelayModifier(float pDuration, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pEntityModifierListener);
    }

    public DelayModifier(float pDuration) {
        super(pDuration);
    }

    protected DelayModifier(DelayModifier pDelayModifier) {
        super(pDelayModifier);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() {
        return new DelayModifier(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedInitialize(IEntity pEntity) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.util.modifier.BaseDurationModifier
    public void onManagedUpdate(float pSecondsElapsed, IEntity pEntity) {
    }
}
