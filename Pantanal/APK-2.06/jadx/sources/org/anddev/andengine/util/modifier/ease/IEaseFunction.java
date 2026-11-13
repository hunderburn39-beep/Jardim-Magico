package org.anddev.andengine.util.modifier.ease;

/* JADX INFO: loaded from: classes.dex */
public interface IEaseFunction {
    public static final IEaseFunction DEFAULT = EaseLinear.getInstance();

    float getPercentage(float f, float f2);
}
