package org.anddev.andengine.opengl.texture.source;

import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTextureAtlasSource implements ITextureAtlasSource {
    protected int mTexturePositionX;
    protected int mTexturePositionY;

    public BaseTextureAtlasSource(int pTexturePositionX, int pTexturePositionY) {
        this.mTexturePositionX = pTexturePositionX;
        this.mTexturePositionY = pTexturePositionY;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public int getTexturePositionX() {
        return this.mTexturePositionX;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public int getTexturePositionY() {
        return this.mTexturePositionY;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public void setTexturePositionX(int pTexturePositionX) {
        this.mTexturePositionX = pTexturePositionX;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public void setTexturePositionY(int pTexturePositionY) {
        this.mTexturePositionY = pTexturePositionY;
    }

    public String toString() {
        return String.valueOf(getClass().getSimpleName()) + "( " + getWidth() + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + getHeight() + " @ " + this.mTexturePositionX + "/" + this.mTexturePositionY + " )";
    }
}
