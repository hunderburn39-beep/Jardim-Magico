package org.anddev.andengine.opengl.texture.region;

import org.anddev.andengine.util.Library;

/* JADX INFO: loaded from: classes.dex */
public class TextureRegionLibrary extends Library<BaseTextureRegion> {
    public TextureRegionLibrary(int pInitialCapacity) {
        super(pInitialCapacity);
    }

    @Override // org.anddev.andengine.util.Library
    public BaseTextureRegion get(int pID) {
        return (TextureRegion) super.get(pID);
    }

    public TiledTextureRegion getTiled(int pID) {
        return (TiledTextureRegion) this.mItems.get(pID);
    }
}
