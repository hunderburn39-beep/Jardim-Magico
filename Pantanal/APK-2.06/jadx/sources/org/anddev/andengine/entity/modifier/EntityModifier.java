package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.BaseModifier;

/* JADX INFO: loaded from: classes.dex */
public abstract class EntityModifier extends BaseModifier<IEntity> implements IEntityModifier {
    public EntityModifier() {
    }

    public EntityModifier(IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pEntityModifierListener);
    }
}
