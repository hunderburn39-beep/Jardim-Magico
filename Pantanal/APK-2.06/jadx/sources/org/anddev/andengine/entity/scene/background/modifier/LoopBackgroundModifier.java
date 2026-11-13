package org.anddev.andengine.entity.scene.background.modifier;

import org.anddev.andengine.entity.scene.background.IBackground;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.LoopModifier;

/* JADX INFO: loaded from: classes.dex */
public class LoopBackgroundModifier extends LoopModifier<IBackground> implements IBackgroundModifier {

    public interface ILoopBackgroundModifierListener extends LoopModifier.ILoopModifierListener<IBackground> {
    }

    public LoopBackgroundModifier(IBackgroundModifier pBackgroundModifier) {
        super(pBackgroundModifier);
    }

    public LoopBackgroundModifier(IBackgroundModifier pBackgroundModifier, int pLoopCount) {
        super(pBackgroundModifier, pLoopCount);
    }

    public LoopBackgroundModifier(IBackgroundModifier pBackgroundModifier, int pLoopCount, ILoopBackgroundModifierListener pLoopModifierListener) {
        super(pBackgroundModifier, pLoopCount, pLoopModifierListener, null);
    }

    public LoopBackgroundModifier(IBackgroundModifier pBackgroundModifier, int pLoopCount, IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener) {
        super(pBackgroundModifier, pLoopCount, pBackgroundModifierListener);
    }

    public LoopBackgroundModifier(IBackgroundModifier pBackgroundModifier, int pLoopCount, ILoopBackgroundModifierListener pLoopModifierListener, IBackgroundModifier.IBackgroundModifierListener pBackgroundModifierListener) {
        super(pBackgroundModifier, pLoopCount, pLoopModifierListener, pBackgroundModifierListener);
    }

    protected LoopBackgroundModifier(LoopBackgroundModifier pLoopBackgroundModifier) throws IModifier.DeepCopyNotSupportedException {
        super((LoopModifier) pLoopBackgroundModifier);
    }

    @Override // org.anddev.andengine.util.modifier.LoopModifier, org.anddev.andengine.util.modifier.BaseModifier, org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public IModifier<IBackground> deepCopy() throws IModifier.DeepCopyNotSupportedException {
        return new LoopBackgroundModifier(this);
    }
}
