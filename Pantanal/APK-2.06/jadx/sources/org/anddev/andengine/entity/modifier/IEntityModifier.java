package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.IMatcher;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public interface IEntityModifier extends IModifier<IEntity> {

    public interface IEntityModifierListener extends IModifier.IModifierListener<IEntity> {
    }

    public interface IEntityModifierMatcher extends IMatcher<IModifier<IEntity>> {
    }

    @Override // 
    IModifier<IEntity> deepCopy() throws IModifier.DeepCopyNotSupportedException;
}
