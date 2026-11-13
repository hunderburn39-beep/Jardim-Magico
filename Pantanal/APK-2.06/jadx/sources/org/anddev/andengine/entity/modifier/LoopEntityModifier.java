package org.anddev.andengine.entity.modifier;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.LoopModifier;

/* JADX INFO: loaded from: classes.dex */
public class LoopEntityModifier extends LoopModifier<IEntity> implements IEntityModifier {

    public interface ILoopEntityModifierListener extends LoopModifier.ILoopModifierListener<IEntity> {
    }

    public LoopEntityModifier(IEntityModifier pEntityModifier) {
        super(pEntityModifier);
    }

    public LoopEntityModifier(IEntityModifier pEntityModifier, int pLoopCount) {
        super(pEntityModifier, pLoopCount);
    }

    public LoopEntityModifier(IEntityModifier pEntityModifier, int pLoopCount, ILoopEntityModifierListener pLoopModifierListener) {
        super(pEntityModifier, pLoopCount, pLoopModifierListener);
    }

    public LoopEntityModifier(IEntityModifier pEntityModifier, int pLoopCount, IEntityModifier.IEntityModifierListener pEntityModifierListener) {
        super(pEntityModifier, pLoopCount, pEntityModifierListener);
    }

    public LoopEntityModifier(IEntityModifier.IEntityModifierListener pEntityModifierListener, int pLoopCount, ILoopEntityModifierListener pLoopModifierListener, IEntityModifier pEntityModifier) {
        super(pEntityModifier, pLoopCount, pLoopModifierListener, pEntityModifierListener);
    }

    protected LoopEntityModifier(LoopEntityModifier pLoopEntityModifier) throws IModifier.DeepCopyNotSupportedException {
        super((LoopModifier) pLoopEntityModifier);
    }

    @Override // org.anddev.andengine.util.modifier.LoopModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IEntity> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new LoopEntityModifier(this);
    }
}
