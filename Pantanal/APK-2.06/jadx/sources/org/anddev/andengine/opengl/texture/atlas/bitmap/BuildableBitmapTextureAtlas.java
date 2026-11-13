package org.anddev.andengine.opengl.texture.atlas.bitmap;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlas;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture;

/* JADX INFO: loaded from: classes.dex */
public class BuildableBitmapTextureAtlas extends BuildableTextureAtlas<IBitmapTextureAtlasSource, BitmapTextureAtlas> {
    public BuildableBitmapTextureAtlas(int pWidth, int pHeight) {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat) {
        this(pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, null);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, pTextureStateListener);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) {
        this(pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, pTextureStateListener);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, null);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, TextureOptions pTextureOptions, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) throws IllegalArgumentException {
        this(pWidth, pHeight, BitmapTexture.BitmapTextureFormat.RGBA_8888, pTextureOptions, pTextureStateListener);
    }

    public BuildableBitmapTextureAtlas(int pWidth, int pHeight, BitmapTexture.BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureAtlas.ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) throws IllegalArgumentException {
        super(new BitmapTextureAtlas(pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, pTextureStateListener));
    }
}
