package org.anddev.andengine.opengl.texture.atlas.buildable;

import java.io.IOException;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.buildable.builder.ITextureBuilder;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;
import org.anddev.andengine.util.Callback;

/* JADX INFO: loaded from: classes.dex */
public class BuildableTextureAtlas<T extends ITextureAtlasSource, A extends ITextureAtlas<T>> implements ITextureAtlas<T> {
    private final A mTextureAtlas;
    private final ArrayList<TextureAtlasSourceWithWithLocationCallback<T>> mTextureAtlasSourcesToPlace = new ArrayList<>();

    public BuildableTextureAtlas(A pTextureAtlas) {
        this.mTextureAtlas = pTextureAtlas;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getWidth() {
        return this.mTextureAtlas.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getHeight() {
        return this.mTextureAtlas.getHeight();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getHardwareTextureID() {
        return this.mTextureAtlas.getHardwareTextureID();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public boolean isLoadedToHardware() {
        return this.mTextureAtlas.isLoadedToHardware();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void setLoadedToHardware(boolean pLoadedToHardware) {
        this.mTextureAtlas.setLoadedToHardware(pLoadedToHardware);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public boolean isUpdateOnHardwareNeeded() {
        return this.mTextureAtlas.isUpdateOnHardwareNeeded();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void setUpdateOnHardwareNeeded(boolean pUpdateOnHardwareNeeded) {
        this.mTextureAtlas.setUpdateOnHardwareNeeded(pUpdateOnHardwareNeeded);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void loadToHardware(GL10 pGL) throws IOException {
        this.mTextureAtlas.loadToHardware(pGL);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void unloadFromHardware(GL10 pGL) {
        this.mTextureAtlas.unloadFromHardware(pGL);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void reloadToHardware(GL10 pGL) throws IOException {
        this.mTextureAtlas.reloadToHardware(pGL);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void bind(GL10 pGL) {
        this.mTextureAtlas.bind(pGL);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public TextureOptions getTextureOptions() {
        return this.mTextureAtlas.getTextureOptions();
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas
    @Deprecated
    public void addTextureAtlasSource(T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY) {
        this.mTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, pTexturePositionX, pTexturePositionY);
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas
    public void removeTextureAtlasSource(T pTextureAtlasSource, int pTexturePositionX, int pTexturePositionY) {
        this.mTextureAtlas.removeTextureAtlasSource(pTextureAtlasSource, pTexturePositionX, pTexturePositionY);
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas
    public void clearTextureAtlasSources() {
        this.mTextureAtlas.clearTextureAtlasSources();
        this.mTextureAtlasSourcesToPlace.clear();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public boolean hasTextureStateListener() {
        return this.mTextureAtlas.hasTextureStateListener();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public ITextureAtlas.ITextureAtlasStateListener<T> getTextureStateListener() {
        return this.mTextureAtlas.getTextureStateListener();
    }

    public void addTextureAtlasSource(T pTextureAtlasSource, Callback<T> pCallback) {
        this.mTextureAtlasSourcesToPlace.add(new TextureAtlasSourceWithWithLocationCallback<>(pTextureAtlasSource, pCallback));
    }

    public void removeTextureAtlasSource(ITextureAtlasSource pTextureAtlasSource) {
        ArrayList<TextureAtlasSourceWithWithLocationCallback<T>> textureSources = this.mTextureAtlasSourcesToPlace;
        for (int i = textureSources.size() - 1; i >= 0; i--) {
            TextureAtlasSourceWithWithLocationCallback<T> textureSource = textureSources.get(i);
            if (((TextureAtlasSourceWithWithLocationCallback) textureSource).mTextureAtlasSource == pTextureAtlasSource) {
                textureSources.remove(i);
                this.mTextureAtlas.setUpdateOnHardwareNeeded(true);
                return;
            }
        }
    }

    public void build(ITextureBuilder<T, A> pTextureAtlasSourcePackingAlgorithm) throws ITextureBuilder.TextureAtlasSourcePackingException {
        pTextureAtlasSourcePackingAlgorithm.pack(this.mTextureAtlas, this.mTextureAtlasSourcesToPlace);
        this.mTextureAtlasSourcesToPlace.clear();
        this.mTextureAtlas.setUpdateOnHardwareNeeded(true);
    }

    public static class TextureAtlasSourceWithWithLocationCallback<T extends ITextureAtlasSource> {
        private final Callback<T> mCallback;
        private final T mTextureAtlasSource;

        public TextureAtlasSourceWithWithLocationCallback(T pTextureAtlasSource, Callback<T> pCallback) {
            this.mTextureAtlasSource = pTextureAtlasSource;
            this.mCallback = pCallback;
        }

        public Callback<T> getCallback() {
            return this.mCallback;
        }

        public T getTextureAtlasSource() {
            return this.mTextureAtlasSource;
        }
    }
}
