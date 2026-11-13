package org.anddev.andengine.entity.scene.background;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/* JADX INFO: loaded from: classes.dex */
public class RepeatingSpriteBackground extends SpriteBackground {
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private final float mScale;

    public RepeatingSpriteBackground(float pCameraWidth, float pCameraHeight, TextureManager pTextureManager, IBitmapTextureAtlasSource pBitmapTextureAtlasSource) throws IllegalArgumentException {
        this(pCameraWidth, pCameraHeight, pTextureManager, pBitmapTextureAtlasSource, 1.0f);
    }

    public RepeatingSpriteBackground(float pCameraWidth, float pCameraHeight, TextureManager pTextureManager, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, float pScale) throws IllegalArgumentException {
        super(null);
        this.mScale = pScale;
        this.mEntity = loadSprite(pCameraWidth, pCameraHeight, pTextureManager, pBitmapTextureAtlasSource);
    }

    public BitmapTextureAtlas getBitmapTextureAtlas() {
        return this.mBitmapTextureAtlas;
    }

    private Sprite loadSprite(float pCameraWidth, float pCameraHeight, TextureManager pTextureManager, IBitmapTextureAtlasSource pBitmapTextureAtlasSource) throws IllegalArgumentException {
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(pBitmapTextureAtlasSource.getWidth(), pBitmapTextureAtlasSource.getHeight(), BitmapTexture.BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA);
        TextureRegion textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(this.mBitmapTextureAtlas, pBitmapTextureAtlasSource, 0, 0);
        int width = Math.round(pCameraWidth / this.mScale);
        int height = Math.round(pCameraHeight / this.mScale);
        textureRegion.setWidth(width);
        textureRegion.setHeight(height);
        pTextureManager.loadTexture(this.mBitmapTextureAtlas);
        Sprite sprite = new Sprite(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, width, height, textureRegion);
        sprite.setScaleCenter(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        sprite.setScale(this.mScale);
        return sprite;
    }
}
