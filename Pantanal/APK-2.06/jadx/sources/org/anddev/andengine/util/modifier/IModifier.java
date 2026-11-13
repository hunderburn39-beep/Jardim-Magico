package org.anddev.andengine.util.modifier;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public interface IModifier<T> {
    public static final Comparator<IModifier<?>> MODIFIER_COMPARATOR_DURATION_DESCENDING = new Comparator<IModifier<?>>() { // from class: org.anddev.andengine.util.modifier.IModifier.1
        @Override // java.util.Comparator
        public int compare(IModifier<?> pModifierA, IModifier<?> pModifierB) {
            float durationA = pModifierA.getDuration();
            float durationB = pModifierB.getDuration();
            if (durationA < durationB) {
                return 1;
            }
            if (durationA > durationB) {
                return -1;
            }
            return 0;
        }
    };

    public static class DeepCopyNotSupportedException extends RuntimeException {
        private static final long serialVersionUID = -5838035434002587320L;
    }

    public interface IModifierListener<T> {
        void onModifierFinished(IModifier<T> iModifier, T t);

        void onModifierStarted(IModifier<T> iModifier, T t);
    }

    void addModifierListener(IModifierListener<T> iModifierListener);

    IModifier<T> deepCopy() throws DeepCopyNotSupportedException;

    float getDuration();

    float getSecondsElapsed();

    boolean isFinished();

    boolean isRemoveWhenFinished();

    float onUpdate(float f, T t);

    boolean removeModifierListener(IModifierListener<T> iModifierListener);

    void reset();

    void setRemoveWhenFinished(boolean z);
}
