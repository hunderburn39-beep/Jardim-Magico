package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ParallelModifier;

/* JADX INFO: loaded from: classes.dex */
public class ParallelEntityModifier extends ParallelModifier<IEntity> implements IEntityModifier {
    public ParallelEntityModifier(IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pEntityModifiers);
    }

    public ParallelEntityModifier(IEntityModifier.IEntityModifierListener pEntityModifierListener, IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pEntityModifierListener, pEntityModifiers);
    }

    protected ParallelEntityModifier(ParallelEntityModifier pParallelShapeModifier) throws IModifier.DeepCopyNotSupportedException {
        super(pParallelShapeModifier);
    }

    @Override // org.anddev.andengine.util.modifier.ParallelModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new ParallelEntityModifier(this);
    }
}
