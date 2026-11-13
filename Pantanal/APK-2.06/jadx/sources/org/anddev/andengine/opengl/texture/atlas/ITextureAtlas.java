package org.anddev.andengine.opengl.texture.atlas;

import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public interface ITextureAtlas<T extends ITextureAtlasSource> extends ITexture {
    void addTextureAtlasSource(T t, int i, int i2) throws IllegalArgumentException;

    void clearTextureAtlasSources();

    @Override // org.anddev.andengine.opengl.texture.ITexture
    ITextureAtlasStateListener<T> getTextureStateListener();

    void removeTextureAtlasSource(T t, int i, int i2);

    public interface ITextureAtlasStateListener<T extends ITextureAtlasSource> extends ITexture.ITextureStateListener {
        void onTextureAtlasSourceLoadExeption(ITextureAtlas<T> iTextureAtlas, T t, Throwable th);

        public static class TextureAtlasStateAdapter<T extends ITextureAtlasSource> implements ITextureAtlasStateListener<T> {
            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onLoadedToHardware(ITexture pTexture) {
            }

            @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas.ITextureAtlasStateListener
            public void onTextureAtlasSourceLoadExeption(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, Throwable pThrowable) {
            }

            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onUnloadedFromHardware(ITexture pTexture) {
            }
        }

        public static class DebugTextureAtlasStateListener<T extends ITextureAtlasSource> implements ITextureAtlasStateListener<T> {
            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onLoadedToHardware(ITexture pTexture) {
                Debug.d("Texture loaded: " + pTexture.toString());
            }

            @Override // org.anddev.andengine.opengl.texture.atlas.ITextureAtlas.ITextureAtlasStateListener
            public void onTextureAtlasSourceLoadExeption(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, Throwable pThrowable) {
                Debug.e("Exception loading TextureAtlasSource. TextureAtlas: " + pTextureAtlas.toString() + " TextureAtlasSource: " + pTextureAtlasSource.toString(), pThrowable);
            }

            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onUnloadedFromHardware(ITexture pTexture) {
                Debug.d("Texture unloaded: " + pTexture.toString());
            }
        }
    }
}
