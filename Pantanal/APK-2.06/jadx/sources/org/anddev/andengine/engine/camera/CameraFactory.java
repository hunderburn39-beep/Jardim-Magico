package org.anddev.andengine.engine.camera;

import android.content.Context;
import android.util.DisplayMetrics;

/* JADX INFO: loaded from: classes.dex */
public class CameraFactory {
    public static Camera createPixelPerfectCamera(Context pContext, float pCenterX, float pCenterY) {
        DisplayMetrics displayMetrics = getDisplayMetrics(pContext);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        return new Camera(pCenterX - (width * 0.5f), pCenterY - (0.5f * height), width, height);
    }

    private static DisplayMetrics getDisplayMetrics(Context pContext) {
        return pContext.getResources().getDisplayMetrics();
    }
}
