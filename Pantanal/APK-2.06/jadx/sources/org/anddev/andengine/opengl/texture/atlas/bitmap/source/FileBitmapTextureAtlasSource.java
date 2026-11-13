package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.StreamUtils;

/* JADX INFO: loaded from: classes.dex */
public class FileBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final File mFile;
    private final int mHeight;
    private final int mWidth;

    public FileBitmapTextureAtlasSource(File pFile) {
        this(pFile, 0, 0);
    }

    public FileBitmapTextureAtlasSource(File pFile, int pTexturePositionX, int pTexturePositionY) throws Throwable {
        super(pTexturePositionX, pTexturePositionY);
        this.mFile = pFile;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        InputStream in = null;
        try {
            try {
                InputStream in2 = new FileInputStream(pFile);
                try {
                    BitmapFactory.decodeStream(in2, null, decodeOptions);
                    StreamUtils.close(in2);
                } catch (IOException e) {
                    e = e;
                    in = in2;
                    Debug.e("Failed loading Bitmap in FileBitmapTextureAtlasSource. File: " + pFile, e);
                    StreamUtils.close(in);
                } catch (Throwable th) {
                    th = th;
                    in = in2;
                    StreamUtils.close(in);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
            this.mWidth = decodeOptions.outWidth;
            this.mHeight = decodeOptions.outHeight;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    FileBitmapTextureAtlasSource(File pFile, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        super(pTexturePositionX, pTexturePositionY);
        this.mFile = pFile;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public FileBitmapTextureAtlasSource deepCopy() {
        return new FileBitmapTextureAtlasSource(this.mFile, this.mTexturePositionX, this.mTexturePositionY, this.mWidth, this.mHeight);
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public int getWidth() {
        return this.mWidth;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public int getHeight() {
        return this.mHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public Bitmap onLoadBitmap(Bitmap.Config pBitmapConfig) throws Throwable {
        Bitmap bitmapDecodeStream = null;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inPreferredConfig = pBitmapConfig;
        InputStream in = null;
        try {
            try {
                InputStream in2 = new FileInputStream(this.mFile);
                try {
                    bitmapDecodeStream = BitmapFactory.decodeStream(in2, null, decodeOptions);
                    StreamUtils.close(in2);
                } catch (IOException e) {
                    e = e;
                    in = in2;
                    Debug.e("Failed loading Bitmap in " + getClass().getSimpleName() + ". File: " + this.mFile, e);
                    StreamUtils.close(in);
                } catch (Throwable th) {
                    th = th;
                    in = in2;
                    StreamUtils.close(in);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
            return bitmapDecodeStream;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource
    public String toString() {
        return String.valueOf(getClass().getSimpleName()) + "(" + this.mFile + ")";
    }
}
