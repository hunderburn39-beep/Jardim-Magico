package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.SequenceModifier;

/* JADX INFO: loaded from: classes.dex */
public class SequenceEntityModifier extends SequenceModifier<IEntity> implements IEntityModifier {

    public interface ISubSequenceShapeModifierListener extends SequenceModifier.ISubSequenceModifierListener<IEntity> {
    }

    public SequenceEntityModifier(IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pEntityModifiers);
    }

    public SequenceEntityModifier(ISubSequenceShapeModifierListener pSubSequenceShapeModifierListener, IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pSubSequenceShapeModifierListener, pEntityModifiers);
    }

    public SequenceEntityModifier(IEntityModifier.IEntityModifierListener pEntityModifierListener, IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pEntityModifierListener, pEntityModifiers);
    }

    public SequenceEntityModifier(ISubSequenceShapeModifierListener pSubSequenceShapeModifierListener, IEntityModifier.IEntityModifierListener pEntityModifierListener, IEntityModifier... pEntityModifiers) throws IllegalArgumentException {
        super(pSubSequenceShapeModifierListener, pEntityModifierListener, pEntityModifiers);
    }

    protected SequenceEntityModifier(SequenceEntityModifier pSequenceShapeModifier) throws IModifier.DeepCopyNotSupportedException {
        super(pSequenceShapeModifier);
    }

    @Override // org.anddev.andengine.util.modifier.SequenceModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new SequenceEntityModifier(this);
    }
}
