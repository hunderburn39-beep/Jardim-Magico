package org.anddev.andengine.opengl.font;

import android.content.Context;
import android.graphics.Typeface;
import org.anddev.andengine.opengl.texture.ITexture;

/* JADX INFO: loaded from: classes.dex */
public class FontFactory {
    private static String sAssetBasePath = "";

    public static void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    public static void reset() {
        setAssetBasePath("");
    }

    public static Font create(ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor) {
        return new Font(pTexture, pTypeface, pSize, pAntiAlias, pColor);
    }

    public static StrokeFont createStroke(ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor) {
        return new StrokeFont(pTexture, pTypeface, pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor);
    }

    public static StrokeFont createStroke(ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor, boolean pStrokeOnly) {
        return new StrokeFont(pTexture, pTypeface, pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor, pStrokeOnly);
    }

    public static Font createFromAsset(ITexture pTexture, Context pContext, String pAssetPath, float pSize, boolean pAntiAlias, int pColor) {
        return new Font(pTexture, Typeface.createFromAsset(pContext.getAssets(), String.valueOf(sAssetBasePath) + pAssetPath), pSize, pAntiAlias, pColor);
    }

    public static StrokeFont createStrokeFromAsset(ITexture pTexture, Context pContext, String pAssetPath, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor) {
        return new StrokeFont(pTexture, Typeface.createFromAsset(pContext.getAssets(), String.valueOf(sAssetBasePath) + pAssetPath), pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor);
    }

    public static StrokeFont createStrokeFromAsset(ITexture pTexture, Context pContext, String pAssetPath, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor, boolean pStrokeOnly) {
        return new StrokeFont(pTexture, Typeface.createFromAsset(pContext.getAssets(), String.valueOf(sAssetBasePath) + pAssetPath), pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor, pStrokeOnly);
    }
}
