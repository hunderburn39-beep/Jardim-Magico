package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public abstract class PictureBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    protected final int mHeight;
    protected final Picture mPicture;
    protected final int mWidth;

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public abstract PictureBitmapTextureAtlasSource deepCopy();

    public PictureBitmapTextureAtlasSource(Picture pPicture) {
        this(pPicture, 0, 0);
    }

    public PictureBitmapTextureAtlasSource(Picture pPicture, int pTexturePositionX, int pTexturePositionY) {
        this(pPicture, pTexturePositionX, pTexturePositionY, pPicture.getWidth(), pPicture.getHeight());
    }

    public PictureBitmapTextureAtlasSource(Picture pPicture, int pTexturePositionX, int pTexturePositionY, float pScale) {
        this(pPicture, pTexturePositionX, pTexturePositionY, Math.round(pPicture.getWidth() * pScale), Math.round(pPicture.getHeight() * pScale));
    }

    public PictureBitmapTextureAtlasSource(Picture pPicture, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        super(pTexturePositionX, pTexturePositionY);
        this.mPicture = pPicture;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
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
        Picture picture = this.mPicture;
        if (picture == null) {
            Debug.e("Failed loading Bitmap in PictureBitmapTextureAtlasSource.");
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, pBitmapConfig);
        Canvas canvas = new Canvas(bitmap);
        float scaleX = this.mWidth / this.mPicture.getWidth();
        float scaleY = this.mHeight / this.mPicture.getHeight();
        canvas.scale(scaleX, scaleY, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        picture.draw(canvas);
        return bitmap;
    }
}
