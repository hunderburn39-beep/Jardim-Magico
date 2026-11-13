package org.anddev.andengine.engine.options.resolutionpolicy;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseResolutionPolicy implements IResolutionPolicy {
    protected static void throwOnNotMeasureSpecEXACTLY(int pWidthMeasureSpec, int pHeightMeasureSpec) {
        int specWidthMode = View.MeasureSpec.getMode(pWidthMeasureSpec);
        int specHeightMode = View.MeasureSpec.getMode(pHeightMeasureSpec);
        if (specWidthMode != 1073741824 || specHeightMode != 1073741824) {
            throw new IllegalStateException("This IResolutionPolicy requires MeasureSpec.EXACTLY ! That means ");
        }
    }
}
