package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.BaseDurationModifier;

/* JADX INFO: loaded from: classes.dex */
public abstract class DurationEntityModifier extends BaseDurationModifier<IEntity> implements IEntityModifier {
    public DurationEntityModifier(float pDuration) {
        super(pDuration);
    }

    public DurationEntityModifier(float pDuration, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pEntityModifierListener);
    }

    protected DurationEntityModifier(DurationEntityModifier pDurationEntityModifier) {
        super(pDurationEntityModifier);
    }
}
