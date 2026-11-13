package org.anddev.andengine.opengl.texture.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.StreamUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class BitmapTexture extends Texture {
    private final BitmapTextureFormat mBitmapTextureFormat;
    private final int mHeight;
    private final int mWidth;

    protected abstract InputStream onGetInputStream() throws IOException;

    public BitmapTexture() throws IOException {
        this(BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, null);
    }

    public BitmapTexture(BitmapTextureFormat pBitmapTextureFormat) throws IOException {
        this(pBitmapTextureFormat, TextureOptions.DEFAULT, null);
    }

    public BitmapTexture(TextureOptions pTextureOptions) throws IOException {
        this(BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
    }

    public BitmapTexture(BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IOException {
        this(pBitmapTextureFormat, pTextureOptions, null);
    }

    public BitmapTexture(BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITexture.ITextureStateListener pTextureStateListener) throws IOException {
        super(pBitmapTextureFormat.getPixelFormat(), pTextureOptions, pTextureStateListener);
        this.mBitmapTextureFormat = pBitmapTextureFormat;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(onGetInputStream(), null, decodeOptions);
            StreamUtils.close(null);
            this.mWidth = decodeOptions.outWidth;
            this.mHeight = decodeOptions.outHeight;
            if (!MathUtils.isPowerOfTwo(this.mWidth) || !MathUtils.isPowerOfTwo(this.mHeight)) {
                throw new IllegalArgumentException("pWidth and pHeight must be a power of 2!");
            }
        } catch (Throwable th) {
            StreamUtils.close(null);
            throw th;
        }
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getWidth() {
        return this.mWidth;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getHeight() {
        return this.mHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.Texture
    protected void writeTextureToHardware(GL10 pGL) throws IOException {
        Bitmap.Config bitmapConfig = this.mBitmapTextureFormat.getBitmapConfig();
        boolean preMultipyAlpha = this.mTextureOptions.mPreMultipyAlpha;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inPreferredConfig = bitmapConfig;
        Bitmap bitmap = BitmapFactory.decodeStream(onGetInputStream(), null, decodeOptions);
        if (preMultipyAlpha) {
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        } else {
            GLHelper.glTexImage2D(pGL, 3553, 0, bitmap, 0, this.mPixelFormat);
        }
        bitmap.recycle();
    }

    public enum BitmapTextureFormat {
        RGBA_8888(Bitmap.Config.ARGB_8888, Texture.PixelFormat.RGBA_8888),
        RGB_565(Bitmap.Config.RGB_565, Texture.PixelFormat.RGB_565),
        RGBA_4444(Bitmap.Config.ARGB_4444, Texture.PixelFormat.RGBA_4444),
        A_8(Bitmap.Config.ALPHA_8, Texture.PixelFormat.A_8);

        private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat;
        private final Bitmap.Config mBitmapConfig;
        private final Texture.PixelFormat mPixelFormat;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static BitmapTextureFormat[] valuesCustom() {
            BitmapTextureFormat[] bitmapTextureFormatArrValuesCustom = values();
            int length = bitmapTextureFormatArrValuesCustom.length;
            BitmapTextureFormat[] bitmapTextureFormatArr = new BitmapTextureFormat[length];
            System.arraycopy(bitmapTextureFormatArrValuesCustom, 0, bitmapTextureFormatArr, 0, length);
            return bitmapTextureFormatArr;
        }

        static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat() {
            int[] iArr = $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat;
            if (iArr == null) {
                iArr = new int[Texture.PixelFormat.valuesCustom().length];
                try {
                    iArr[Texture.PixelFormat.AI_88.ordinal()] = 8;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[Texture.PixelFormat.A_8.ordinal()] = 6;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[Texture.PixelFormat.I_8.ordinal()] = 7;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[Texture.PixelFormat.RGBA_4444.ordinal()] = 2;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[Texture.PixelFormat.RGBA_5551.ordinal()] = 3;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[Texture.PixelFormat.RGBA_8888.ordinal()] = 4;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[Texture.PixelFormat.RGB_565.ordinal()] = 5;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[Texture.PixelFormat.UNDEFINED.ordinal()] = 1;
                } catch (NoSuchFieldError e8) {
                }
                $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat = iArr;
            }
            return iArr;
        }

        BitmapTextureFormat(Bitmap.Config pBitmapConfig, Texture.PixelFormat pPixelFormat) {
            this.mBitmapConfig = pBitmapConfig;
            this.mPixelFormat = pPixelFormat;
        }

        public static BitmapTextureFormat fromPixelFormat(Texture.PixelFormat pPixelFormat) {
            switch ($SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat()[pPixelFormat.ordinal()]) {
                case 2:
                    return RGBA_4444;
                case 3:
                default:
                    throw new IllegalArgumentException("Unsupported " + Texture.PixelFormat.class.getName() + ": '" + pPixelFormat + "'.");
                case 4:
                    return RGBA_8888;
                case 5:
                    return RGB_565;
                case 6:
                    return A_8;
            }
        }

        public Bitmap.Config getBitmapConfig() {
            return this.mBitmapConfig;
        }

        public Texture.PixelFormat getPixelFormat() {
            return this.mPixelFormat;
        }
    }
}
