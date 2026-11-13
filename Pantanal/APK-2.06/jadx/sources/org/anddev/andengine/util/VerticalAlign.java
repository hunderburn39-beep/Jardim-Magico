package org.anddev.andengine.util;

/* JADX INFO: loaded from: classes.dex */
public enum VerticalAlign {
    TOP,
    CENTER,
    BOTTOM;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static VerticalAlign[] valuesCustom() {
        VerticalAlign[] verticalAlignArrValuesCustom = values();
        int length = verticalAlignArrValuesCustom.length;
        VerticalAlign[] verticalAlignArr = new VerticalAlign[length];
        System.arraycopy(verticalAlignArrValuesCustom, 0, verticalAlignArr, 0, length);
        return verticalAlignArr;
    }
}
