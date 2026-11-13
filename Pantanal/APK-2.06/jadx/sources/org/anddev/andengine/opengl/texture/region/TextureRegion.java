package org.anddev.andengine.opengl.texture.region;

import org.anddev.andengine.opengl.texture.ITexture;

/* JADX INFO: loaded from: classes.dex */
public class TextureRegion extends BaseTextureRegion {
    public TextureRegion(ITexture pTexture, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        super(pTexture, pTexturePositionX, pTexturePositionY, pWidth, pHeight);
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public TextureRegion deepCopy() {
        return new TextureRegion(this.mTexture, this.mTexturePositionX, this.mTexturePositionY, this.mWidth, this.mHeight);
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateX1() {
        return this.mTexturePositionX / this.mTexture.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateY1() {
        return this.mTexturePositionY / this.mTexture.getHeight();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateX2() {
        return (this.mTexturePositionX + this.mWidth) / this.mTexture.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.region.BaseTextureRegion
    public float getTextureCoordinateY2() {
        return (this.mTexturePositionY + this.mHeight) / this.mTexture.getHeight();
    }
}
