package org.anddev.andengine.entity.scene.background.modifier;

import org.anddev.andengine.entity.scene.background.IBackground;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public interface IBackgroundModifier extends IModifier<IBackground> {

    public interface IBackgroundModifierListener extends IModifier.IModifierListener<IBackground> {
    }

    @Override // org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    IModifier<IBackground> deepCopy() throws IModifier.DeepCopyNotSupportedException;
}
