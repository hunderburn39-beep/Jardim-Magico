package org.anddev.andengine.opengl.texture;

import java.io.IOException;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public interface ITexture {
    void bind(GL10 gl10);

    int getHardwareTextureID();

    int getHeight();

    TextureOptions getTextureOptions();

    ITextureStateListener getTextureStateListener();

    int getWidth();

    boolean hasTextureStateListener();

    boolean isLoadedToHardware();

    boolean isUpdateOnHardwareNeeded();

    void loadToHardware(GL10 gl10) throws IOException;

    void reloadToHardware(GL10 gl10) throws IOException;

    void setLoadedToHardware(boolean z);

    void setUpdateOnHardwareNeeded(boolean z);

    void unloadFromHardware(GL10 gl10);

    public interface ITextureStateListener {
        void onLoadedToHardware(ITexture iTexture);

        void onUnloadedFromHardware(ITexture iTexture);

        public static class TextureStateAdapter<T extends ITextureAtlasSource> implements ITextureStateListener {
            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onLoadedToHardware(ITexture pTexture) {
            }

            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onUnloadedFromHardware(ITexture pTexture) {
            }
        }

        public static class DebugTextureStateListener<T extends ITextureAtlasSource> implements ITextureStateListener {
            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onLoadedToHardware(ITexture pTexture) {
                Debug.d("Texture loaded: " + pTexture.toString());
            }

            @Override // org.anddev.andengine.opengl.texture.ITexture.ITextureStateListener
            public void onUnloadedFromHardware(ITexture pTexture) {
                Debug.d("Texture unloaded: " + pTexture.toString());
            }
        }
    }
}
