package org.anddev.andengine.opengl.texture.compressed.pvr;

import java.io.IOException;
import java.util.zip.GZIPInputStream;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.TextureOptions;

/* JADX INFO: loaded from: classes.dex */
public abstract class PVRGZTexture extends PVRTexture {
    public PVRGZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat);
    }

    public PVRGZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat, ITexture.ITextureStateListener pTextureStateListener) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat, pTextureStateListener);
    }

    public PVRGZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat, pTextureOptions);
    }

    public PVRGZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions, ITexture.ITextureStateListener pTextureStateListener) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat, pTextureOptions, pTextureStateListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.anddev.andengine.opengl.texture.compressed.pvr.PVRTexture
    public GZIPInputStream getInputStream() throws IOException {
        return new GZIPInputStream(onGetInputStream());
    }
}
