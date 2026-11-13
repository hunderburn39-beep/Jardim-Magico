package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public interface IBitmapTextureAtlasSource extends ITextureAtlasSource {
    IBitmapTextureAtlasSource deepCopy();

    Bitmap onLoadBitmap(Bitmap.Config config);
}
