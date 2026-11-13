package org.anddev.andengine.opengl.texture.compressed.pvr;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.ArrayUtils;
import org.anddev.andengine.util.ByteBufferOutputStream;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.StreamUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class PVRTexture extends Texture {
    public static final int FLAG_ALPHA = 32768;
    public static final int FLAG_BUMPMAP = 1024;
    public static final int FLAG_CUBEMAP = 4096;
    public static final int FLAG_FALSEMIPCOL = 8192;
    public static final int FLAG_MIPMAP = 256;
    public static final int FLAG_TILING = 2048;
    public static final int FLAG_TWIDDLE = 512;
    public static final int FLAG_VERTICALFLIP = 65536;
    public static final int FLAG_VOLUME = 16384;
    private final PVRTextureHeader mPVRTextureHeader;

    protected abstract InputStream onGetInputStream() throws IOException;

    public PVRTexture(PVRTextureFormat pPVRTextureFormat) throws IOException, IllegalArgumentException {
        this(pPVRTextureFormat, TextureOptions.DEFAULT, null);
    }

    public PVRTexture(PVRTextureFormat pPVRTextureFormat, ITexture.ITextureStateListener pTextureStateListener) throws IOException, IllegalArgumentException {
        this(pPVRTextureFormat, TextureOptions.DEFAULT, pTextureStateListener);
    }

    public PVRTexture(PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions) throws IOException, IllegalArgumentException {
        this(pPVRTextureFormat, pTextureOptions, null);
    }

    public PVRTexture(PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions, ITexture.ITextureStateListener pTextureStateListener) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat.getPixelFormat(), pTextureOptions, pTextureStateListener);
        InputStream inputStream = null;
        try {
            inputStream = getInputStream();
            this.mPVRTextureHeader = new PVRTextureHeader(StreamUtils.streamToBytes(inputStream, 52));
            StreamUtils.close(inputStream);
            if (!MathUtils.isPowerOfTwo(getWidth()) || !MathUtils.isPowerOfTwo(getHeight())) {
                throw new IllegalArgumentException("mWidth and mHeight must be a power of 2!");
            }
            if (this.mPVRTextureHeader.getPVRTextureFormat().getPixelFormat() != pPVRTextureFormat.getPixelFormat()) {
                throw new IllegalArgumentException("Other PVRTextureFormat: '" + this.mPVRTextureHeader.getPVRTextureFormat().getPixelFormat() + "' found than expected: '" + pPVRTextureFormat.getPixelFormat() + "'.");
            }
            if (this.mPVRTextureHeader.getPVRTextureFormat().isCompressed()) {
                throw new IllegalArgumentException("Invalid PVRTextureFormat: '" + this.mPVRTextureHeader.getPVRTextureFormat() + "'.");
            }
            this.mUpdateOnHardwareNeeded = true;
        } catch (Throwable th) {
            StreamUtils.close(inputStream);
            throw th;
        }
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getWidth() {
        return this.mPVRTextureHeader.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.ITexture
    public int getHeight() {
        return this.mPVRTextureHeader.getHeight();
    }

    public PVRTextureHeader getPVRTextureHeader() {
        return this.mPVRTextureHeader;
    }

    protected InputStream getInputStream() throws IOException {
        return onGetInputStream();
    }

    @Override // org.anddev.andengine.opengl.texture.Texture
    protected void generateHardwareTextureID(GL10 pGL) {
        pGL.glPixelStorei(3317, 1);
        super.generateHardwareTextureID(pGL);
    }

    @Override // org.anddev.andengine.opengl.texture.Texture
    protected void writeTextureToHardware(GL10 pGL) throws IOException {
        ByteBuffer pvrDataBuffer = getPVRDataBuffer();
        int width = getWidth();
        int height = getHeight();
        int dataLength = this.mPVRTextureHeader.getDataLength();
        int glFormat = this.mPixelFormat.getGLFormat();
        int glType = this.mPixelFormat.getGLType();
        int bytesPerPixel = this.mPVRTextureHeader.getBitsPerPixel() / 8;
        int mipmapLevel = 0;
        int currentPixelDataOffset = 0;
        while (currentPixelDataOffset < dataLength) {
            int currentPixelDataSize = width * height * bytesPerPixel;
            if (mipmapLevel > 0 && (width != height || MathUtils.nextPowerOfTwo(width) != width)) {
                Debug.w(String.format("Mipmap level '%u' is not squared. Width: '%u', height: '%u'. Texture won't render correctly.", Integer.valueOf(mipmapLevel), Integer.valueOf(width), Integer.valueOf(height)));
            }
            pvrDataBuffer.position(currentPixelDataOffset + 52);
            pvrDataBuffer.limit(currentPixelDataOffset + 52 + currentPixelDataSize);
            ByteBuffer pixelBuffer = pvrDataBuffer.slice();
            pGL.glTexImage2D(3553, mipmapLevel, glFormat, width, height, 0, glFormat, glType, pixelBuffer);
            currentPixelDataOffset += currentPixelDataSize;
            width = Math.max(width >> 1, 1);
            height = Math.max(height >> 1, 1);
            mipmapLevel++;
        }
    }

    protected ByteBuffer getPVRDataBuffer() throws IOException {
        InputStream inputStream = getInputStream();
        try {
            ByteBufferOutputStream os = new ByteBufferOutputStream(1024, AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
            StreamUtils.copy(inputStream, os);
            return os.toByteBuffer();
        } finally {
            StreamUtils.close(inputStream);
        }
    }

    public static class PVRTextureHeader {
        private static final int FORMAT_FLAG_MASK = 255;
        public static final byte[] MAGIC_IDENTIFIER = {80, 86, 82, 33};
        public static final int SIZE = 52;
        private final ByteBuffer mDataByteBuffer;
        private final PVRTextureFormat mPVRTextureFormat;

        public PVRTextureHeader(byte[] pData) {
            this.mDataByteBuffer = ByteBuffer.wrap(pData);
            this.mDataByteBuffer.rewind();
            this.mDataByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            if (!ArrayUtils.equals(pData, 44, MAGIC_IDENTIFIER, 0, MAGIC_IDENTIFIER.length)) {
                throw new IllegalArgumentException("Invalid " + getClass().getSimpleName() + "!");
            }
            this.mPVRTextureFormat = PVRTextureFormat.fromID(getFlags() & 255);
        }

        public PVRTextureFormat getPVRTextureFormat() {
            return this.mPVRTextureFormat;
        }

        public int headerLength() {
            return this.mDataByteBuffer.getInt(0);
        }

        public int getHeight() {
            return this.mDataByteBuffer.getInt(4);
        }

        public int getWidth() {
            return this.mDataByteBuffer.getInt(8);
        }

        public int getNumMipmaps() {
            return this.mDataByteBuffer.getInt(12);
        }

        public int getFlags() {
            return this.mDataByteBuffer.getInt(16);
        }

        public int getDataLength() {
            return this.mDataByteBuffer.getInt(20);
        }

        public int getBitsPerPixel() {
            return this.mDataByteBuffer.getInt(24);
        }

        public int getBitmaskRed() {
            return this.mDataByteBuffer.getInt(28);
        }

        public int getBitmaskGreen() {
            return this.mDataByteBuffer.getInt(32);
        }

        public int getBitmaskBlue() {
            return this.mDataByteBuffer.getInt(36);
        }

        public int getBitmaskAlpha() {
            return this.mDataByteBuffer.getInt(40);
        }

        public boolean hasAlpha() {
            return getBitmaskAlpha() != 0;
        }

        public int getPVRTag() {
            return this.mDataByteBuffer.getInt(44);
        }

        public int numSurfs() {
            return this.mDataByteBuffer.getInt(48);
        }
    }

    public enum PVRTextureFormat {
        RGBA_4444(16, false, Texture.PixelFormat.RGBA_4444),
        RGBA_5551(17, false, Texture.PixelFormat.RGBA_5551),
        RGBA_8888(18, false, Texture.PixelFormat.RGBA_8888),
        RGB_565(19, false, Texture.PixelFormat.RGB_565),
        I_8(22, false, Texture.PixelFormat.I_8),
        AI_88(23, false, Texture.PixelFormat.AI_88),
        A_8(27, false, Texture.PixelFormat.A_8);

        private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat;
        private final boolean mCompressed;
        private final int mID;
        private final Texture.PixelFormat mPixelFormat;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static PVRTextureFormat[] valuesCustom() {
            PVRTextureFormat[] pVRTextureFormatArrValuesCustom = values();
            int length = pVRTextureFormatArrValuesCustom.length;
            PVRTextureFormat[] pVRTextureFormatArr = new PVRTextureFormat[length];
            System.arraycopy(pVRTextureFormatArrValuesCustom, 0, pVRTextureFormatArr, 0, length);
            return pVRTextureFormatArr;
        }

        static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat() {
            int[] iArr = $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat;
            if (iArr == null) {
                iArr = new int[Texture.PixelFormat.valuesCustom().length];
                try {
                    iArr[Texture.PixelFormat.AI_88.ordinal()] = 8;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[Texture.PixelFormat.A_8.ordinal()] = 6;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[Texture.PixelFormat.I_8.ordinal()] = 7;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[Texture.PixelFormat.RGBA_4444.ordinal()] = 2;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[Texture.PixelFormat.RGBA_5551.ordinal()] = 3;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[Texture.PixelFormat.RGBA_8888.ordinal()] = 4;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[Texture.PixelFormat.RGB_565.ordinal()] = 5;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[Texture.PixelFormat.UNDEFINED.ordinal()] = 1;
                } catch (NoSuchFieldError e8) {
                }
                $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat = iArr;
            }
            return iArr;
        }

        PVRTextureFormat(int pID, boolean pCompressed, Texture.PixelFormat pPixelFormat) {
            this.mID = pID;
            this.mCompressed = pCompressed;
            this.mPixelFormat = pPixelFormat;
        }

        public static PVRTextureFormat fromID(int pID) {
            PVRTextureFormat[] pvrTextureFormats = valuesCustom();
            for (PVRTextureFormat pvrTextureFormat : pvrTextureFormats) {
                if (pvrTextureFormat.mID == pID) {
                    return pvrTextureFormat;
                }
            }
            throw new IllegalArgumentException("Unexpected " + PVRTextureFormat.class.getSimpleName() + "-ID: '" + pID + "'.");
        }

        public static PVRTextureFormat fromPixelFormat(Texture.PixelFormat pPixelFormat) throws IllegalArgumentException {
            switch ($SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat()[pPixelFormat.ordinal()]) {
                case 2:
                    return RGBA_4444;
                case 3:
                default:
                    throw new IllegalArgumentException("Unsupported " + Texture.PixelFormat.class.getName() + ": '" + pPixelFormat + "'.");
                case 4:
                    return RGBA_8888;
                case 5:
                    return RGB_565;
            }
        }

        public int getID() {
            return this.mID;
        }

        public boolean isCompressed() {
            return this.mCompressed;
        }

        public Texture.PixelFormat getPixelFormat() {
            return this.mPixelFormat;
        }
    }
}
