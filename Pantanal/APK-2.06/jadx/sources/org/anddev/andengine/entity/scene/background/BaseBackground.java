package org.anddev.andengine.entity.scene.background;

import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ModifierList;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseBackground implements IBackground {
    private static final int BACKGROUNDMODIFIERS_CAPACITY_DEFAULT = 4;
    private final ModifierList<IBackground> mBackgroundModifiers = new ModifierList<>(this, 4);

    @Override // org.anddev.andengine.entity.scene.background.IBackground
    public void addBackgroundModifier(IModifier<IBackground> pBackgroundModifier) {
        this.mBackgroundModifiers.add(pBackgroundModifier);
    }

    @Override // org.anddev.andengine.entity.scene.background.IBackground
    public boolean removeBackgroundModifier(IModifier<IBackground> pBackgroundModifier) {
        return this.mBackgroundModifiers.remove(pBackgroundModifier);
    }

    @Override // org.anddev.andengine.entity.scene.background.IBackground
    public void clearBackgroundModifiers() {
        this.mBackgroundModifiers.clear();
    }

    public void onUpdate(float pSecondsElapsed) {
        this.mBackgroundModifiers.onUpdate(pSecondsElapsed);
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mBackgroundModifiers.reset();
    }
}
