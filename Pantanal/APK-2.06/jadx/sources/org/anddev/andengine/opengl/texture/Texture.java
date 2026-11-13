package org.anddev.andengine.opengl.texture;

import java.io.IOException;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.util.GLHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class Texture implements ITexture {
    private static final int[] HARDWARETEXTUREID_FETCHER = new int[1];
    protected boolean mLoadedToHardware;
    protected final PixelFormat mPixelFormat;
    protected final TextureOptions mTextureOptions;
    protected final ITexture.ITextureStateListener mTextureStateListener;
    protected int mHardwareTextureID = -1;
    protected boolean mUpdateOnHardwareNeeded = false;

    protected abstract void writeTextureToHardware(GL10 gl10) throws IOException;

    public Texture(PixelFormat pPixelFormat, TextureOptions pTextureOptions, ITexture.ITextureStateListener pTextureStateListener) throws IllegalArgumentException {
        this.mPixelFormat = pPixelFormat;
        this.mTextureOptions = pTextureOptions;
        this.mTextureStateListener = pTextureStateListener;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getHardwareTextureID() {
        return this.mHardwareTextureID;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public boolean isLoadedToHardware() {
        return this.mLoadedToHardware;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void setLoadedToHardware(boolean pLoadedToHardware) {
        this.mLoadedToHardware = pLoadedToHardware;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public boolean isUpdateOnHardwareNeeded() {
        return this.mUpdateOnHardwareNeeded;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void setUpdateOnHardwareNeeded(boolean pUpdateOnHardwareNeeded) {
        this.mUpdateOnHardwareNeeded = pUpdateOnHardwareNeeded;
    }

    public PixelFormat getPixelFormat() {
        return this.mPixelFormat;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public TextureOptions getTextureOptions() {
        return this.mTextureOptions;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public ITexture.ITextureStateListener getTextureStateListener() {
        return this.mTextureStateListener;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public boolean hasTextureStateListener() {
        return this.mTextureStateListener != null;
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void loadToHardware(GL10 pGL) throws IOException {
        GLHelper.enableTextures(pGL);
        generateHardwareTextureID(pGL);
        bindTextureOnHardware(pGL);
        applyTextureOptions(pGL);
        writeTextureToHardware(pGL);
        this.mUpdateOnHardwareNeeded = false;
        this.mLoadedToHardware = true;
        if (this.mTextureStateListener != null) {
            this.mTextureStateListener.onLoadedToHardware(this);
        }
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void unloadFromHardware(GL10 pGL) {
        GLHelper.enableTextures(pGL);
        deleteTextureOnHardware(pGL);
        this.mHardwareTextureID = -1;
        this.mLoadedToHardware = false;
        if (this.mTextureStateListener != null) {
            this.mTextureStateListener.onUnloadedFromHardware(this);
        }
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void reloadToHardware(GL10 pGL) throws IOException {
        unloadFromHardware(pGL);
        loadToHardware(pGL);
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public void bind(GL10 pGL) {
        GLHelper.bindTexture(pGL, this.mHardwareTextureID);
    }

    protected void applyTextureOptions(GL10 pGL) {
        this.mTextureOptions.apply(pGL);
    }

    protected void bindTextureOnHardware(GL10 pGL) {
        GLHelper.forceBindTexture(pGL, this.mHardwareTextureID);
    }

    protected void deleteTextureOnHardware(GL10 pGL) {
        GLHelper.deleteTexture(pGL, this.mHardwareTextureID);
    }

    protected void generateHardwareTextureID(GL10 pGL) {
        pGL.glGenTextures(1, HARDWARETEXTUREID_FETCHER, 0);
        this.mHardwareTextureID = HARDWARETEXTUREID_FETCHER[0];
    }

    public enum PixelFormat {
        UNDEFINED(-1, -1, -1),
        RGBA_4444(6408, 32819, 16),
        RGBA_5551(6408, 32820, 16),
        RGBA_8888(6408, 5121, 32),
        RGB_565(6407, 33635, 16),
        A_8(6406, 5121, 8),
        I_8(6409, 5121, 8),
        AI_88(6410, 5121, 16);

        private final int mBitsPerPixel;
        private final int mGLFormat;
        private final int mGLType;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static PixelFormat[] valuesCustom() {
            PixelFormat[] pixelFormatArrValuesCustom = values();
            int length = pixelFormatArrValuesCustom.length;
            PixelFormat[] pixelFormatArr = new PixelFormat[length];
            System.arraycopy(pixelFormatArrValuesCustom, 0, pixelFormatArr, 0, length);
            return pixelFormatArr;
        }

        PixelFormat(int pGLFormat, int pGLType, int pBitsPerPixel) {
            this.mGLFormat = pGLFormat;
            this.mGLType = pGLType;
            this.mBitsPerPixel = pBitsPerPixel;
        }

        public int getGLFormat() {
            return this.mGLFormat;
        }

        public int getGLType() {
            return this.mGLType;
        }

        public int getBitsPerPixel() {
            return this.mBitsPerPixel;
        }
    }
}
