package org.anddev.andengine.opengl.texture.atlas.buildable.builder;

import java.util.ArrayList;
import org.anddev.andengine.opengl.texture.atlas.ITextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlas;
import org.anddev.andengine.opengl.texture.source.ITextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public interface ITextureBuilder<T extends ITextureAtlasSource, A extends ITextureAtlas<T>> {
    void pack(A a, ArrayList<BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback<T>> arrayList) throws TextureAtlasSourcePackingException;

    public static class TextureAtlasSourcePackingException extends Exception {
        private static final long serialVersionUID = 4700734424214372671L;

        public TextureAtlasSourcePackingException(String pMessage) {
            super(pMessage);
        }
    }
}
