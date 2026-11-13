package org.anddev.andengine.entity.layer.tiled.tmx;

import org.anddev.andengine.opengl.texture.region.TextureRegion;

/* JADX INFO: loaded from: classes.dex */
public class TMXTile {
    int mGlobalTileID;
    TextureRegion mTextureRegion;
    private final int mTileColumn;
    private final int mTileHeight;
    private final int mTileRow;
    private final int mTileWidth;

    public TMXTile(int pGlobalTileID, int pTileColumn, int pTileRow, int pTileWidth, int pTileHeight, TextureRegion pTextureRegion) {
        this.mGlobalTileID = pGlobalTileID;
        this.mTileRow = pTileRow;
        this.mTileColumn = pTileColumn;
        this.mTileWidth = pTileWidth;
        this.mTileHeight = pTileHeight;
        this.mTextureRegion = pTextureRegion;
    }

    public int getGlobalTileID() {
        return this.mGlobalTileID;
    }

    public int getTileRow() {
        return this.mTileRow;
    }

    public int getTileColumn() {
        return this.mTileColumn;
    }

    public int getTileX() {
        return this.mTileColumn * this.mTileWidth;
    }

    public int getTileY() {
        return this.mTileRow * this.mTileHeight;
    }

    public int getTileWidth() {
        return this.mTileWidth;
    }

    public int getTileHeight() {
        return this.mTileHeight;
    }

    public TextureRegion getTextureRegion() {
        return this.mTextureRegion;
    }

    public void setGlobalTileID(TMXTiledMap pTMXTiledMap, int pGlobalTileID) {
        this.mGlobalTileID = pGlobalTileID;
        this.mTextureRegion = pTMXTiledMap.getTextureRegionFromGlobalTileID(pGlobalTileID);
    }

    public void setTextureRegion(TextureRegion pTextureRegion) {
        this.mTextureRegion = pTextureRegion;
    }

    public TMXProperties<TMXTileProperty> getTMXTileProperties(TMXTiledMap pTMXTiledMap) {
        return pTMXTiledMap.getTMXTileProperties(this.mGlobalTileID);
    }
}
