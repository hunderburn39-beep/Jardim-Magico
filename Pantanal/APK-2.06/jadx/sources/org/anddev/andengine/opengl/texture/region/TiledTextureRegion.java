package org.anddev.andengine.opengl.texture.region;

import org.anddev.andengine.opengl.texture.ITexture;

/* JADX INFO: loaded from: classes.dex */
public class TiledTextureRegion extends BaseTextureRegion {
    private int mCurrentTileColumn;
    private int mCurrentTileRow;
    private final int mTileColumns;
    private final int mTileCount;
    private final int mTileRows;

    public TiledTextureRegion(ITexture pTexture, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight, int pTileColumns, int pTileRows) {
        super(pTexture, pTexturePositionX, pTexturePositionY, pWidth, pHeight);
        this.mTileColumns = pTileColumns;
        this.mTileRows = pTileRows;
        this.mTileCount = this.mTileColumns * this.mTileRows;
        this.mCurrentTileColumn = 0;
        this.mCurrentTileRow = 0;
        initTextureBuffer();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    protected void initTextureBuffer() {
        if (this.mTileRows != 0 && this.mTileColumns != 0) {
            super.initTextureBuffer();
        }
    }

    public int getTileCount() {
        return this.mTileCount;
    }

    public int getTileWidth() {
        return super.getWidth() / this.mTileColumns;
    }

    public int getTileHeight() {
        return super.getHeight() / this.mTileRows;
    }

    public int getCurrentTileColumn() {
        return this.mCurrentTileColumn;
    }

    public int getCurrentTileRow() {
        return this.mCurrentTileRow;
    }

    public int getCurrentTileIndex() {
        return (this.mCurrentTileRow * this.mTileColumns) + this.mCurrentTileColumn;
    }

    public void setCurrentTileIndex(int pTileColumn, int pTileRow) {
        if (pTileColumn != this.mCurrentTileColumn || pTileRow != this.mCurrentTileRow) {
            this.mCurrentTileColumn = pTileColumn;
            this.mCurrentTileRow = pTileRow;
            super.updateTextureRegionBuffer();
        }
    }

    public void setCurrentTileIndex(int pTileIndex) {
        if (pTileIndex < this.mTileCount) {
            int tileColumns = this.mTileColumns;
            setCurrentTileIndex(pTileIndex % tileColumns, pTileIndex / tileColumns);
        }
    }

    public int getTexturePositionOfCurrentTileX() {
        return super.getTexturePositionX() + (this.mCurrentTileColumn * getTileWidth());
    }

    public int getTexturePositionOfCurrentTileY() {
        return super.getTexturePositionY() + (this.mCurrentTileRow * getTileHeight());
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public TiledTextureRegion deepCopy() {
        TiledTextureRegion deepCopy = new TiledTextureRegion(this.mTexture, getTexturePositionX(), getTexturePositionY(), getWidth(), getHeight(), this.mTileColumns, this.mTileRows);
        deepCopy.setCurrentTileIndex(this.mCurrentTileColumn, this.mCurrentTileRow);
        return deepCopy;
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateX1() {
        return getTexturePositionOfCurrentTileX() / this.mTexture.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateY1() {
        return getTexturePositionOfCurrentTileY() / this.mTexture.getHeight();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateX2() {
        return (getTexturePositionOfCurrentTileX() + getTileWidth()) / this.mTexture.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateY2() {
        return (getTexturePositionOfCurrentTileY() + getTileHeight()) / this.mTexture.getHeight();
    }

    public void nextTile() {
        int tileIndex = (getCurrentTileIndex() + 1) % getTileCount();
        setCurrentTileIndex(tileIndex);
    }
}
