package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public class EmptyBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final int mHeight;
    private final int mWidth;

    public EmptyBitmapTextureAtlasSource(int pWidth, int pHeight) {
        this(0, 0, pWidth, pHeight);
    }

    public EmptyBitmapTextureAtlasSource(int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        super(pTexturePositionX, pTexturePositionY);
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public EmptyBitmapTextureAtlasSource deepCopy() {
        return new EmptyBitmapTextureAtlasSource(this.mTexturePositionX, this.mTexturePositionY, this.mWidth, this.mHeight);
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
        return Bitmap.createBitmap(this.mWidth, this.mHeight, pBitmapConfig);
    }

    @Override // org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource
    public String toString() {
        return String.valueOf(getClass().getSimpleName()) + "(" + this.mWidth + " x " + this.mHeight + ")";
    }
}
