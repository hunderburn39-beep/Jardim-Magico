package org.anddev.andengine.opengl.texture;

import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
public class TextureOptions {
    public final int mMagFilter;
    public final int mMinFilter;
    public final boolean mPreMultipyAlpha;
    public final float mWrapS;
    public final float mWrapT;
    public static final TextureOptions NEAREST = new TextureOptions(9728, 9728, 33071, 33071, false);
    public static final TextureOptions BILINEAR = new TextureOptions(9729, 9729, 33071, 33071, false);
    public static final TextureOptions REPEATING_NEAREST = new TextureOptions(9728, 9728, 10497, 10497, false);
    public static final TextureOptions REPEATING_BILINEAR = new TextureOptions(9729, 9729, 10497, 10497, false);
    public static final TextureOptions NEAREST_PREMULTIPLYALPHA = new TextureOptions(9728, 9728, 33071, 33071, true);
    public static final TextureOptions BILINEAR_PREMULTIPLYALPHA = new TextureOptions(9729, 9729, 33071, 33071, true);
    public static final TextureOptions REPEATING_NEAREST_PREMULTIPLYALPHA = new TextureOptions(9728, 9728, 10497, 10497, true);
    public static final TextureOptions REPEATING_BILINEAR_PREMULTIPLYALPHA = new TextureOptions(9729, 9729, 10497, 10497, true);
    public static final TextureOptions DEFAULT = NEAREST_PREMULTIPLYALPHA;

    public TextureOptions(int pMinFilter, int pMagFilter, int pWrapT, int pWrapS, boolean pPreMultiplyAlpha) {
        this.mMinFilter = pMinFilter;
        this.mMagFilter = pMagFilter;
        this.mWrapT = pWrapT;
        this.mWrapS = pWrapS;
        this.mPreMultipyAlpha = pPreMultiplyAlpha;
    }

    public void apply(GL10 pGL) {
        pGL.glTexParameterf(3553, 10241, this.mMinFilter);
        pGL.glTexParameterf(3553, 10240, this.mMagFilter);
        pGL.glTexParameterf(3553, 10242, this.mWrapS);
        pGL.glTexParameterf(3553, 10243, this.mWrapT);
        pGL.glTexEnvf(8960, 8704, 8448.0f);
    }
}
