package org.anddev.andengine.opengl.texture.atlas;

import java.util.ArrayList;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class TextureAtlas<T extends ITextureAtlasSource> extends Texture implements ITextureAtlas<T> {
    protected final int mHeight;
    protected final ArrayList<T> mTextureAtlasSources;
    protected final int mWidth;

    public TextureAtlas(int pWidth, int pHeight, Texture.PixelFormat pPixelFormat, TextureOptions pTextureOptions, ITextureAtlas.ITextureAtlasStateListener<T> pTextureAtlasStateListener) {
        super(pPixelFormat, pTextureOptions, pTextureAtlasStateListener);
        this.mTextureAtlasSources = new ArrayList<>();
        if (!MathUtils.isPowerOfTwo(pWidth) || !MathUtils.isPowerOfTwo(pHeight)) {
            throw new IllegalArgumentException("pWidth and pHeight must be a power of 2!");
        }
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getWidth() {
        return this.mWidth;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getHeight() {
        return this.mHeight;
    }

    @Override // org.anddev.andengine.opengl.texture.Texture, org.anddev.andengine.opengl.texture.ITexture
    public ITextureAtlas.ITextureAtlasStateListener<T> getTextureStateListener() {
        return (ITextureAtlas.ITextureAtlasStateListener) super.getTextureStateListener();
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas
    public void addTextureAtlasSource(T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY) throws IllegalArgumentException {
        checkTextureAtlasSourcePosition(pTextureAtlasSource, pTexturePositionX, pTexturePositionY);
        pTextureAtlasSource.setTexturePositionX(pTexturePositionX);
        pTextureAtlasSource.setTexturePositionY(pTexturePositionY);
        this.mTextureAtlasSources.add(pTextureAtlasSource);
        this.mUpdateOnHardwareNeeded = true;
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas
    public void removeTextureAtlasSource(T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY) {
        ArrayList<T> textureSources = this.mTextureAtlasSources;
        for (int i = textureSources.size() - 1; i >= 0; i--) {
            T textureSource = textureSources.get(i);
            if (textureSource == pTextureAtlasSource && textureSource.getTexturePositionX() == pTexturePositionX && textureSource.getTexturePositionY() == pTexturePositionY) {
                textureSources.remove(i);
                this.mUpdateOnHardwareNeeded = true;
                return;
            }
        }
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas
    public void clearTextureAtlasSources() {
        this.mTextureAtlasSources.clear();
        this.mUpdateOnHardwareNeeded = true;
    }

    private void checkTextureAtlasSourcePosition(T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY) throws IllegalArgumentException {
        if (pTexturePositionX < 0) {
            throw new IllegalArgumentException("Illegal negative pTexturePositionX supplied: '" + pTexturePositionX + "'");
        }
        if (pTexturePositionY < 0) {
            throw new IllegalArgumentException("Illegal negative pTexturePositionY supplied: '" + pTexturePositionY + "'");
        }
        if (pTextureAtlasSource.getWidth() + pTexturePositionX > getWidth() || pTextureAtlasSource.getHeight() + pTexturePositionY > getHeight()) {
            throw new IllegalArgumentException("Supplied pTextureAtlasSource must not exceed bounds of Texture.");
        }
    }
}
