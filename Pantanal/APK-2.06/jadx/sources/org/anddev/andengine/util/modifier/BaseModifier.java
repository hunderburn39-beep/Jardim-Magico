package org.anddev.andengine.util.modifier;

import org.anddev.andengine.util.SmartList;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseModifier<T> implements IModifier<T> {
    protected boolean mFinished;
    private boolean mRemoveWhenFinished = true;
    private final SmartList<IModifier.IModifierListener<T>> mModifierListeners = new SmartList<>(2);

    @Override // org.anddev.andengine.util.modifier.IModifier, org.anddev.andengine.entity.modifier.IEntityModifier
    public abstract IModifier<T> deepCopy() throws IModifier.DeepCopyNotSupportedException;

    public BaseModifier() {
    }

    public BaseModifier(IModifier.IModifierListener<T> pModifierListener) {
        addModifierListener(pModifierListener);
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public boolean isFinished() {
        return this.mFinished;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public final boolean isRemoveWhenFinished() {
        return this.mRemoveWhenFinished;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public final void setRemoveWhenFinished(boolean pRemoveWhenFinished) {
        this.mRemoveWhenFinished = pRemoveWhenFinished;
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public void addModifierListener(IModifier.IModifierListener<T> pModifierListener) {
        if (pModifierListener != null) {
            this.mModifierListeners.add(pModifierListener);
        }
    }

    @Override // org.anddev.andengine.util.modifier.IModifier
    public boolean removeModifierListener(IModifier.IModifierListener<T> pModifierListener) {
        if (pModifierListener == null) {
            return false;
        }
        return this.mModifierListeners.remove(pModifierListener);
    }

    protected void onModifierStarted(T pItem) {
        SmartList<IModifier.IModifierListener<T>> modifierListeners = this.mModifierListeners;
        int modifierListenerCount = modifierListeners.size();
        for (int i = modifierListenerCount - 1; i >= 0; i--) {
            modifierListeners.get(i).onModifierStarted(this, pItem);
        }
    }

    protected void onModifierFinished(T pItem) {
        SmartList<IModifier.IModifierListener<T>> modifierListeners = this.mModifierListeners;
        int modifierListenerCount = modifierListeners.size();
        for (int i = modifierListenerCount - 1; i >= 0; i--) {
            modifierListeners.get(i).onModifierFinished(this, pItem);
        }
    }
}
