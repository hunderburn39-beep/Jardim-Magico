package org.anddev.andengine.opengl.texture.compressed.pvr;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.ArrayUtils;
import org.anddev.andengine.util.StreamUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class PVRCCZTexture extends PVRTexture {
    private CCZHeader mCCZHeader;

    public PVRCCZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat);
    }

    public PVRCCZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat, ITexture.ITextureStateListener pTextureStateListener) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat, pTextureStateListener);
    }

    public PVRCCZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat, pTextureOptions);
    }

    public PVRCCZTexture(PVRTexture.PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions, ITexture.ITextureStateListener pTextureStateListener) throws IOException, IllegalArgumentException {
        super(pPVRTextureFormat, pTextureOptions, pTextureStateListener);
    }

    @Override // org.anddev.andengine.opengl.texture.compressed.pvr.PVRTexture
    protected final InputStream getInputStream() throws IOException {
        InputStream inputStream = onGetInputStream();
        this.mCCZHeader = new CCZHeader(StreamUtils.streamToBytes(inputStream, 16));
        return this.mCCZHeader.getCCZCompressionFormat().wrap(inputStream);
    }

    @Override // org.anddev.andengine.opengl.texture.compressed.pvr.PVRTexture
    protected ByteBuffer getPVRDataBuffer() throws IOException {
        InputStream inputStream = getInputStream();
        try {
            byte[] data = new byte[this.mCCZHeader.getUncompressedSize()];
            StreamUtils.copy(inputStream, data);
            return ByteBuffer.wrap(data);
        } finally {
            StreamUtils.close(inputStream);
        }
    }

    public static class CCZHeader {
        public static final byte[] MAGIC_IDENTIFIER = {67, 67, 90, 33};
        public static final int SIZE = 16;
        private final CCZCompressionFormat mCCZCompressionFormat;
        private final ByteBuffer mDataByteBuffer;

        public CCZHeader(byte[] pData) {
            this.mDataByteBuffer = ByteBuffer.wrap(pData);
            this.mDataByteBuffer.rewind();
            this.mDataByteBuffer.order(ByteOrder.BIG_ENDIAN);
            if (!ArrayUtils.equals(pData, 0, MAGIC_IDENTIFIER, 0, MAGIC_IDENTIFIER.length)) {
                throw new IllegalArgumentException("Invalid " + getClass().getSimpleName() + "!");
            }
            this.mCCZCompressionFormat = CCZCompressionFormat.fromID(getCCZCompressionFormatID());
        }

        private short getCCZCompressionFormatID() {
            return this.mDataByteBuffer.getShort(4);
        }

        public CCZCompressionFormat getCCZCompressionFormat() {
            return this.mCCZCompressionFormat;
        }

        public short getVersion() {
            return this.mDataByteBuffer.getShort(6);
        }

        public int getUserdata() {
            return this.mDataByteBuffer.getInt(8);
        }

        public int getUncompressedSize() {
            return this.mDataByteBuffer.getInt(12);
        }
    }

    public enum CCZCompressionFormat {
        ZLIB(0),
        BZIP2(1),
        GZIP(2),
        NONE(3);

        private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$compressed$pvr$PVRCCZTexture$CCZCompressionFormat;
        private final short mID;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static CCZCompressionFormat[] valuesCustom() {
            CCZCompressionFormat[] cCZCompressionFormatArrValuesCustom = values();
            int length = cCZCompressionFormatArrValuesCustom.length;
            CCZCompressionFormat[] cCZCompressionFormatArr = new CCZCompressionFormat[length];
            System.arraycopy(cCZCompressionFormatArrValuesCustom, 0, cCZCompressionFormatArr, 0, length);
            return cCZCompressionFormatArr;
        }

        static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$compressed$pvr$PVRCCZTexture$CCZCompressionFormat() {
            int[] iArr = $SWITCH_TABLE$org$anddev$andengine$opengl$texture$compressed$pvr$PVRCCZTexture$CCZCompressionFormat;
            if (iArr == null) {
                iArr = new int[valuesCustom().length];
                try {
                    iArr[BZIP2.ordinal()] = 2;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[GZIP.ordinal()] = 3;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[NONE.ordinal()] = 4;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[ZLIB.ordinal()] = 1;
                } catch (NoSuchFieldError e4) {
                }
                $SWITCH_TABLE$org$anddev$andengine$opengl$texture$compressed$pvr$PVRCCZTexture$CCZCompressionFormat = iArr;
            }
            return iArr;
        }

        CCZCompressionFormat(short pID) {
            this.mID = pID;
        }

        public InputStream wrap(InputStream pInputStream) throws IOException {
            switch ($SWITCH_TABLE$org$anddev$andengine$opengl$texture$compressed$pvr$PVRCCZTexture$CCZCompressionFormat()[ordinal()]) {
                case 1:
                    return new InflaterInputStream(pInputStream, new Inflater());
                case 2:
                default:
                    throw new IllegalArgumentException("Unexpected " + CCZCompressionFormat.class.getSimpleName() + ": '" + this + "'.");
                case 3:
                    return new GZIPInputStream(pInputStream);
            }
        }

        public static CCZCompressionFormat fromID(short pID) {
            CCZCompressionFormat[] cczCompressionFormats = valuesCustom();
            for (CCZCompressionFormat cczCompressionFormat : cczCompressionFormats) {
                if (cczCompressionFormat.mID == pID) {
                    return cczCompressionFormat;
                }
            }
            throw new IllegalArgumentException("Unexpected " + CCZCompressionFormat.class.getSimpleName() + "-ID: '" + ((int) pID) + "'.");
        }
    }
}
