package org.anddev.andengine.util;

/* JADX INFO: loaded from: classes.dex */
public enum HorizontalAlign {
    LEFT,
    CENTER,
    RIGHT;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static HorizontalAlign[] valuesCustom() {
        HorizontalAlign[] horizontalAlignArrValuesCustom = values();
        int length = horizontalAlignArrValuesCustom.length;
        HorizontalAlign[] horizontalAlignArr = new HorizontalAlign[length];
        System.arraycopy(horizontalAlignArrValuesCustom, 0, horizontalAlignArr, 0, length);
        return horizontalAlignArr;
    }
}
