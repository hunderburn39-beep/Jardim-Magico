package org.anddev.andengine.opengl.texture.atlas.buildable;

import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;
import org.anddev.andengine.util.Callback;

/* JADX INFO: loaded from: classes.dex */
public class BuildableTextureAtlasTextureRegionFactory {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends ITextureAtlasSource, A extends ITextureAtlas<T>> TextureRegion createFromSource(BuildableTextureAtlas<T, A> buildableTextureAtlas, T pTextureAtlasSource, boolean pTextureRegionBufferManaged) {
        final TextureRegion textureRegion = new TextureRegion(buildableTextureAtlas, 0, 0, pTextureAtlasSource.getWidth(), pTextureAtlasSource.getHeight());
        buildableTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, new Callback<T>() { // from class: org.anddev.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlasTextureRegionFactory.1
            /* JADX WARN: Incorrect types in method signature: (TT;)V */
            @Override // org.anddev.andengine.util.Callback
            public void onCallback(ITextureAtlasSource iTextureAtlasSource) {
                textureRegion.setTexturePosition(iTextureAtlasSource.getTexturePositionX(), iTextureAtlasSource.getTexturePositionY());
            }
        });
        textureRegion.setTextureRegionBufferManaged(pTextureRegionBufferManaged);
        return textureRegion;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends ITextureAtlasSource, A extends ITextureAtlas<T>> TiledTextureRegion createTiledFromSource(BuildableTextureAtlas<T, A> buildableTextureAtlas, T pTextureAtlasSource, int pTileColumns, int pTileRows, boolean pTextureRegionBufferManaged) {
        final TiledTextureRegion tiledTextureRegion = new TiledTextureRegion(buildableTextureAtlas, 0, 0, pTextureAtlasSource.getWidth(), pTextureAtlasSource.getHeight(), pTileColumns, pTileRows);
        buildableTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, new Callback<T>() { // from class: org.anddev.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlasTextureRegionFactory.2
            /* JADX WARN: Incorrect types in method signature: (TT;)V */
            @Override // org.anddev.andengine.util.Callback
            public void onCallback(ITextureAtlasSource iTextureAtlasSource) {
                tiledTextureRegion.setTexturePosition(iTextureAtlasSource.getTexturePositionX(), iTextureAtlasSource.getTexturePositionY());
            }
        });
        tiledTextureRegion.setTextureRegionBufferManaged(pTextureRegionBufferManaged);
        return tiledTextureRegion;
    }
}
