package org.anddev.andengine.opengl.texture.atlas.bitmap;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class BitmapTextureAtlasFactory {
    public static BitmapTextureAtlas createForTextureAtlasSourceSize(BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, TextureRegion pTextureRegion) {
        return createForTextureRegionSize(pBitmapTextureFormat, pTextureRegion, TextureOptions.DEFAULT);
    }

    public static BitmapTextureAtlas createForTextureRegionSize(BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, TextureRegion pTextureRegion, TextureOptions pTextureOptions) {
        int textureRegionWidth = pTextureRegion.getWidth();
        int textureRegionHeight = pTextureRegion.getHeight();
        return new BitmapTextureAtlas(MathUtils.nextPowerOfTwo(textureRegionWidth), MathUtils.nextPowerOfTwo(textureRegionHeight), pBitmapTextureFormat, pTextureOptions);
    }

    public static BitmapTextureAtlas createForTextureAtlasSourceSize(BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, IBitmapTextureAtlasSource pBitmapTextureAtlasSource) {
        return createForTextureAtlasSourceSize(pBitmapTextureFormat, pBitmapTextureAtlasSource, TextureOptions.DEFAULT);
    }

    public static BitmapTextureAtlas createForTextureAtlasSourceSize(BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, TextureOptions pTextureOptions) {
        int textureSourceWidth = pBitmapTextureAtlasSource.getWidth();
        int textureSourceHeight = pBitmapTextureAtlasSource.getHeight();
        return new BitmapTextureAtlas(MathUtils.nextPowerOfTwo(textureSourceWidth), MathUtils.nextPowerOfTwo(textureSourceHeight), pBitmapTextureFormat, pTextureOptions);
    }
}
