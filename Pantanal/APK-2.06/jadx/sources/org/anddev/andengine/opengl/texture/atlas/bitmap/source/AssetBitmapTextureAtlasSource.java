package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.StreamUtils;

/* JADX INFO: loaded from: classes.dex */
public class AssetBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final String mAssetPath;
    private final Context mContext;
    private final int mHeight;
    private final int mWidth;

    public AssetBitmapTextureAtlasSource(Context pContext, String pAssetPath) {
        this(pContext, pAssetPath, 0, 0);
    }

    public AssetBitmapTextureAtlasSource(Context pContext, String pAssetPath, int pTexturePositionX, int pTexturePositionY) {
        super(pTexturePositionX, pTexturePositionY);
        this.mContext = pContext;
        this.mAssetPath = pAssetPath;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        InputStream in = null;
        try {
            try {
                in = pContext.getAssets().open(pAssetPath);
                BitmapFactory.decodeStream(in, null, decodeOptions);
                StreamUtils.close(in);
            } catch (IOException e) {
                Debug.e("Failed loading Bitmap in AssetBitmapTextureAtlasSource. AssetPath: " + pAssetPath, e);
                StreamUtils.close(in);
            }
            this.mWidth = decodeOptions.outWidth;
            this.mHeight = decodeOptions.outHeight;
        } catch (Throwable th) {
            StreamUtils.close(in);
            throw th;
        }
    }

    AssetBitmapTextureAtlasSource(Context pContext, String pAssetPath, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        super(pTexturePositionX, pTexturePositionY);
        this.mContext = pContext;
        this.mAssetPath = pAssetPath;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public AssetBitmapTextureAtlasSource deepCopy() {
        return new AssetBitmapTextureAtlasSource(this.mContext, this.mAssetPath, this.mTexturePositionX, this.mTexturePositionY, this.mWidth, this.mHeight);
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
    public Bitmap onLoadBitmap(Bitmap.Config pBitmapConfig) {
        Bitmap bitmapDecodeStream = null;
        InputStream in = null;
        try {
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            decodeOptions.inPreferredConfig = pBitmapConfig;
            in = this.mContext.getAssets().open(this.mAssetPath);
            bitmapDecodeStream = BitmapFactory.decodeStream(in, null, decodeOptions);
        } catch (IOException e) {
            Debug.e("Failed loading Bitmap in " + getClass().getSimpleName() + ". AssetPath: " + this.mAssetPath, e);
        } finally {
            StreamUtils.close(in);
        }
        return bitmapDecodeStream;
    }

    @Override // org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource
    public String toString() {
        return String.valueOf(getClass().getSimpleName()) + "(" + this.mAssetPath + ")";
    }
}
