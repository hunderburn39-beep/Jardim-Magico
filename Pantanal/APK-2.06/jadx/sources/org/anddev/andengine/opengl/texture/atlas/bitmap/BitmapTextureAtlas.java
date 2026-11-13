package org.anddev.andengine.opengl.texture.atlas.bitmap;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.TextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public class BitmapTextureAtlas extends TextureAtlas<IBitmapTextureAtlasSource> {
    private final BitmapTexture.BitmapTextureFormat mBitmapTextureFormat;

    public BitmapTextureAtlas(int pWidth, int pHeight) {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat) {
        this(pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, null);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, pTextureAtlasStateListener);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) {
        this(pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, pTextureAtlasStateListener);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, null);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, TextureOptions pTextureOptions, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) throws IllegalArgumentException {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888, pTextureOptions, pTextureAtlasStateListener);
    }

    public BitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) throws IllegalArgumentException {
        super(pWidth, pHeight, pBitmapTextureFormat.getPixelFormat(), pTextureOptions, pTextureAtlasStateListener);
        this.mBitmapTextureFormat = pBitmapTextureFormat;
    }

    public BitmapTexture.BitmapTextureFormat getBitmapTextureFormat() {
        return this.mBitmapTextureFormat;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0097  */
    /* JADX WARN: Code duplicated, block: B:28:0x00d3 A[SYNTHETIC] */
    @Override // org.anddev.andengine.opengl.texture.Texture
    protected void writeTextureToHardware(GL10 pGL) {
        Bitmap.Config bitmapConfig = this.mBitmapTextureFormat.getBitmapConfig();
        int glFormat = this.mPixelFormat.getGLFormat();
        int glType = this.mPixelFormat.getGLType();
        boolean preMultipyAlpha = this.mTextureOptions.mPreMultipyAlpha;
        ArrayList<T> arrayList = this.mTextureAtlasSources;
        int textureSourceCount = arrayList.size();
        for (int j = 0; j < textureSourceCount; j++) {
            IBitmapTextureAtlasSource bitmapTextureAtlasSource = (IBitmapTextureAtlasSource) arrayList.get(j);
            if (bitmapTextureAtlasSource != null) {
                Bitmap bitmap = bitmapTextureAtlasSource.onLoadBitmap(bitmapConfig);
                if (bitmap == null) {
                    throw new IllegalArgumentException(String.valueOf(bitmapTextureAtlasSource.getClass().getSimpleName()) + ": " + bitmapTextureAtlasSource.toString() + " returned a null Bitmap.");
                }
                if (preMultipyAlpha) {
                    try {
                        GLUtils.texSubImage2D(3553, 0, bitmapTextureAtlasSource.getTexturePositionX(), bitmapTextureAtlasSource.getTexturePositionY(), bitmap, glFormat, glType);
                    } catch (IllegalArgumentException iae) {
                        Debug.e("Error loading: " + bitmapTextureAtlasSource.toString(), iae);
                        if (getTextureStateListener() != null) {
                            getTextureStateListener().onTextureAtlasSourceLoadExeption(this, bitmapTextureAtlasSource, iae);
                        } else {
                            throw iae;
                        }
                    }
                } else {
                    GLHelper.glTexSubImage2D(pGL, 3553, 0, bitmapTextureAtlasSource.getTexturePositionX(), bitmapTextureAtlasSource.getTexturePositionY(), bitmap, this.mPixelFormat);
                }
                bitmap.recycle();
                Debug.e("Error loading: " + bitmapTextureAtlasSource.toString(), iae);
                if (getTextureStateListener() != null) {
                    getTextureStateListener().onTextureAtlasSourceLoadExeption(this, bitmapTextureAtlasSource, iae);
                } else {
                    throw iae;
                }
            }
        }
    }

    @Override // org.anddev.andengine.opengl.texture.Texture
    protected void bindTextureOnHardware(GL10 pGL) {
        super.bindTextureOnHardware(pGL);
        Texture.PixelFormat pixelFormat = this.mBitmapTextureFormat.getPixelFormat();
        int glFormat = pixelFormat.getGLFormat();
        int glType = pixelFormat.getGLType();
        pGL.glTexImage2D(3553, 0, glFormat, this.mWidth, this.mHeight, 0, glFormat, glType, null);
    }
}
