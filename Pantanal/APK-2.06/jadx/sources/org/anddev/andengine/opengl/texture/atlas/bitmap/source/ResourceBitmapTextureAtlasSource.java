package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public class ResourceBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final Context mContext;
    private final int mDrawableResourceID;
    private final int mHeight;
    private final int mWidth;

    public ResourceBitmapTextureAtlasSource(Context pContext, int pDrawableResourceID) {
        this(pContext, pDrawableResourceID, 0, 0);
    }

    public ResourceBitmapTextureAtlasSource(Context pContext, int pDrawableResourceID, int pTexturePositionX, int pTexturePositionY) {
        super(pTexturePositionX, pTexturePositionY);
        this.mContext = pContext;
        this.mDrawableResourceID = pDrawableResourceID;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(pContext.getResources(), pDrawableResourceID, decodeOptions);
        this.mWidth = decodeOptions.outWidth;
        this.mHeight = decodeOptions.outHeight;
    }

    protected ResourceBitmapTextureAtlasSource(Context pContext, int pDrawableResourceID, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        super(pTexturePositionX, pTexturePositionY);
        this.mContext = pContext;
        this.mDrawableResourceID = pDrawableResourceID;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public ResourceBitmapTextureAtlasSource deepCopy() {
        return new ResourceBitmapTextureAtlasSource(this.mContext, this.mDrawableResourceID, this.mTexturePositionX, this.mTexturePositionY, this.mWidth, this.mHeight);
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
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inPreferredConfig = pBitmapConfig;
        return BitmapFactory.decodeResource(this.mContext.getResources(), this.mDrawableResourceID, decodeOptions);
    }

    @Override // org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource
    public String toString() {
        return String.valueOf(getClass().getSimpleName()) + "(" + this.mDrawableResourceID + ")";
    }
}
