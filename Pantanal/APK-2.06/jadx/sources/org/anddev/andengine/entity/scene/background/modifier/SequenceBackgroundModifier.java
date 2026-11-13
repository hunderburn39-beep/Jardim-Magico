package org.anddev.andengine.entity.scene.background.modifier;

import org.anddev.andengine.entity.scene.background.IBackground;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.SequenceModifier;

/* JADX INFO: loaded from: classes.dex */
public class SequenceBackgroundModifier extends SequenceModifier<IBackground> implements IBackgroundModifier {

    public interface ISubSequenceBackgroundModifierListener extends SequenceModifier.ISubSequenceModifierListener<IBackground> {
    }

    public SequenceBackgroundModifier(IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pBackgroundModifiers);
    }

    public SequenceBackgroundModifier(ISubSequenceBackgroundModifierListener pSubSequenceBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pSubSequenceBackgroundModifierListener, pBackgroundModifiers);
    }

    public SequenceBackgroundModifier(IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pBackgroundModifierListener, pBackgroundModifiers);
    }

    public SequenceBackgroundModifier(ISubSequenceBackgroundModifierListener pSubSequenceBackgroundModifierListener, IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener, IBackgroundModifier... pBackgroundModifiers) throws IllegalArgumentException {
        super(pSubSequenceBackgroundModifierListener, pBackgroundModifierListener, pBackgroundModifiers);
    }

    protected SequenceBackgroundModifier(SequenceBackgroundModifier pSequenceBackgroundModifier) throws IModifier.DeepCopyNotSupportedException {
        super(pSequenceBackgroundModifier);
    }

    @Override // org.anddev.andengine.util.modifier.SequenceModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IBackground> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new SequenceBackgroundModifier(this);
    }
}
