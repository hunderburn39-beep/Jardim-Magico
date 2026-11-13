package org.anddev.andengine.entity.scene.background.modifier;

import org.anddev.andengine.entity.scene.background.IBackground;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ParallelModifier;

/* JADX INFO: loaded from: classes.dex */
public class ParallelBackgroundModifier extends ParallelModifier<IBackground> implements IBackgroundModifier {
    public ParallelBackgroundModifier(IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pBackgroundModifiers);
    }

    public ParallelBackgroundModifier(IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pBackgroundModifierListener, pBackgroundModifiers);
    }

    protected ParallelBackgroundModifier(ParallelBackgroundModifier pParallelBackgroundModifier) throws IModifier.DeepCopyNotSupportedException {
        super(pParallelBackgroundModifier);
    }

    @Override // org.anddev.andengine.util.modifier.ParallelModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IBackground> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new ParallelBackgroundModifier(this);
    }
}
