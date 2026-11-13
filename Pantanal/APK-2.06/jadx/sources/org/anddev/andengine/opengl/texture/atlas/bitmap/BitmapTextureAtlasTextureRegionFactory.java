package org.anddev.andengine.opengl.texture.atlas.bitmap;

import android.content.Context;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.ResourceBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

/* JADX INFO: loaded from: classes.dex */
public class BitmapTextureAtlasTextureRegionFactory {
    private static String sAssetBasePath = "";
    private static boolean sCreateTextureRegionBuffersManaged;

    public static void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalArgumentException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    public static void setCreateTextureRegionBuffersManaged(boolean pCreateTextureRegionBuffersManaged) {
        sCreateTextureRegionBuffersManaged = pCreateTextureRegionBuffersManaged;
    }

    public static void reset() {
        setAssetBasePath("");
        setCreateTextureRegionBuffersManaged(false);
    }

    public static TextureRegion createFromAsset(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, String pAssetPath, int pTexturePositionX, int pTexturePositionY) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new AssetBitmapTextureAtlasSource(pContext, String.valueOf(sAssetBasePath) + pAssetPath);
        return createFromSource(pBitmapTextureAtlas, bitmapTextureAtlasSource, pTexturePositionX, pTexturePositionY);
    }

    public static TiledTextureRegion createTiledFromAsset(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, String pAssetPath, int pTexturePositionX, int pTexturePositionY, int pTileColumns, int pTileRows) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new AssetBitmapTextureAtlasSource(pContext, String.valueOf(sAssetBasePath) + pAssetPath);
        return createTiledFromSource(pBitmapTextureAtlas, bitmapTextureAtlasSource, pTexturePositionX, pTexturePositionY, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromResource(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, int pDrawableResourceID, int pTexturePositionX, int pTexturePositionY) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new ResourceBitmapTextureAtlasSource(pContext, pDrawableResourceID);
        return createFromSource(pBitmapTextureAtlas, bitmapTextureAtlasSource, pTexturePositionX, pTexturePositionY);
    }

    public static TiledTextureRegion createTiledFromResource(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, int pDrawableResourceID, int pTexturePositionX, int pTexturePositionY, int pTileColumns, int pTileRows) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new ResourceBitmapTextureAtlasSource(pContext, pDrawableResourceID);
        return createTiledFromSource(pBitmapTextureAtlas, bitmapTextureAtlasSource, pTexturePositionX, pTexturePositionY, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromSource(BitmapTextureAtlas pBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, int pTexturePositionX, int pTexturePositionY) {
        return TextureRegionFactory.createFromSource(pBitmapTextureAtlas, pBitmapTextureAtlasSource, pTexturePositionX, pTexturePositionY, sCreateTextureRegionBuffersManaged);
    }

    public static TiledTextureRegion createTiledFromSource(BitmapTextureAtlas pBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, int pTexturePositionX, int pTexturePositionY, int pTileColumns, int pTileRows) {
        return TextureRegionFactory.createTiledFromSource(pBitmapTextureAtlas, pBitmapTextureAtlasSource, pTexturePositionX, pTexturePositionY, pTileColumns, pTileRows, sCreateTextureRegionBuffersManaged);
    }

    public static TextureRegion createFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, String pAssetPath) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new AssetBitmapTextureAtlasSource(pContext, String.valueOf(sAssetBasePath) + pAssetPath);
        return createFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource);
    }

    public static TiledTextureRegion createTiledFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, String pAssetPath, int pTileColumns, int pTileRows) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new AssetBitmapTextureAtlasSource(pContext, String.valueOf(sAssetBasePath) + pAssetPath);
        return createTiledFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, int pDrawableResourceID) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new ResourceBitmapTextureAtlasSource(pContext, pDrawableResourceID);
        return createFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource);
    }

    public static TiledTextureRegion createTiledFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, int pDrawableResourceID, int pTileColumns, int pTileRows) {
        IBitmapTextureAtlasSource bitmapTextureAtlasSource = new ResourceBitmapTextureAtlasSource(pContext, pDrawableResourceID);
        return createTiledFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromSource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource) {
        return BuildableTextureAtlasTextureRegionFactory.createFromSource(pBuildableBitmapTextureAtlas, pBitmapTextureAtlasSource, sCreateTextureRegionBuffersManaged);
    }

    public static TiledTextureRegion createTiledFromSource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, int pTileColumns, int pTileRows) {
        return BuildableTextureAtlasTextureRegionFactory.createTiledFromSource(pBuildableBitmapTextureAtlas, pBitmapTextureAtlasSource, pTileColumns, pTileRows, sCreateTextureRegionBuffersManaged);
    }
}
