package org.anddev.andengine.util.modifier.util;

import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public class ModifierUtils {
    public static float getSequenceDurationOfModifier(IModifier<?>[] iModifierArr) {
        float duration = Float.MIN_VALUE;
        for (int i = iModifierArr.length - 1; i >= 0; i--) {
            duration += iModifierArr[i].getDuration();
        }
        return duration;
    }
}
