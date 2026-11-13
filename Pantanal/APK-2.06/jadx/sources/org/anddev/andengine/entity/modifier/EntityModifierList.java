package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.ModifierList;

/* JADX INFO: loaded from: classes.dex */
public class EntityModifierList extends ModifierList<IEntity> {
    private static final long serialVersionUID = 161652765736600082L;

    public EntityModifierList(IEntity pTarget) {
        super(pTarget);
    }

    public EntityModifierList(IEntity pTarget, int pCapacity) {
        super(pTarget, pCapacity);
    }
}
