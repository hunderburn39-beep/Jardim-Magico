package org.anddev.andengine.opengl.texture.region;

import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public class TextureRegionFactory {
    public static TextureRegion extractFromTexture(ITexture pTexture, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight, boolean pTextureRegionBufferManaged) {
        TextureRegion textureRegion = new TextureRegion(pTexture, pTexturePositionX, pTexturePositionY, pWidth, pHeight);
        textureRegion.setTextureRegionBufferManaged(pTextureRegionBufferManaged);
        return textureRegion;
    }

    public static <T extends ITextureAtlasSource> TextureRegion createFromSource(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY, boolean pCreateTextureRegionBuffersManaged) {
        TextureRegion textureRegion = new TextureRegion(pTextureAtlas, pTexturePositionX, pTexturePositionY, pTextureAtlasSource.getWidth(), pTextureAtlasSource.getHeight());
        pTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, textureRegion.getTexturePositionX(), textureRegion.getTexturePositionY());
        textureRegion.setTextureRegionBufferManaged(pCreateTextureRegionBuffersManaged);
        return textureRegion;
    }

    public static <T extends ITextureAtlasSource> TiledTextureRegion createTiledFromSource(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY, int pTileColumns, int pTileRows, boolean pCreateTextureRegionBuffersManaged) {
        TiledTextureRegion tiledTextureRegion = new TiledTextureRegion(pTextureAtlas, pTexturePositionX, pTexturePositionY, pTextureAtlasSource.getWidth(), pTextureAtlasSource.getHeight(), pTileColumns, pTileRows);
        pTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, tiledTextureRegion.getTexturePositionX(), tiledTextureRegion.getTexturePositionY());
        tiledTextureRegion.setTextureRegionBufferManaged(pCreateTextureRegionBuffersManaged);
        return tiledTextureRegion;
    }
}
