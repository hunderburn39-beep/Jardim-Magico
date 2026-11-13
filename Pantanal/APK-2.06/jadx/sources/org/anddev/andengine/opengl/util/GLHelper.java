package org.anddev.andengine.opengl.util;

import android.graphics.Bitmap;
import android.opengl.GLException;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import com.adcolony.adcolonysdk.BuildConfig;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.engine.options.RenderOptions;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public class GLHelper {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat = null;
    public static final int BYTES_PER_FLOAT = 4;
    public static final int BYTES_PER_PIXEL_RGBA = 4;
    public static boolean EXTENSIONS_DRAWTEXTURE;
    public static boolean EXTENSIONS_TEXTURE_NON_POWER_OF_TWO;
    public static boolean EXTENSIONS_VERTEXBUFFEROBJECTS;
    private static final int[] HARDWAREBUFFERID_CONTAINER;
    private static final int[] HARDWARETEXTUREID_CONTAINER;
    private static final boolean IS_LITTLE_ENDIAN;
    private static float sAlpha;
    private static float sBlue;
    private static int sCurrentDestinationBlendMode;
    private static int sCurrentHardwareBufferID;
    private static int sCurrentHardwareTextureID;
    private static int sCurrentMatrix;
    private static int sCurrentSourceBlendMode;
    private static FastFloatBuffer sCurrentTextureFloatBuffer;
    private static FastFloatBuffer sCurrentVertexFloatBuffer;
    private static boolean sEnableBlend;
    private static boolean sEnableCulling;
    private static boolean sEnableDepthTest;
    private static boolean sEnableDither;
    private static boolean sEnableLightning;
    private static boolean sEnableMultisample;
    private static boolean sEnableScissorTest;
    private static boolean sEnableTexCoordArray;
    private static boolean sEnableTextures;
    private static boolean sEnableVertexArray;
    private static float sGreen;
    private static float sLineWidth;
    private static float sRed;

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

    static {
        IS_LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        HARDWARETEXTUREID_CONTAINER = new int[1];
        HARDWAREBUFFERID_CONTAINER = new int[1];
        sCurrentHardwareBufferID = -1;
        sCurrentHardwareTextureID = -1;
        sCurrentMatrix = -1;
        sCurrentSourceBlendMode = -1;
        sCurrentDestinationBlendMode = -1;
        sCurrentVertexFloatBuffer = null;
        sCurrentTextureFloatBuffer = null;
        sEnableDither = true;
        sEnableLightning = true;
        sEnableDepthTest = true;
        sEnableMultisample = true;
        sEnableScissorTest = false;
        sEnableBlend = false;
        sEnableCulling = false;
        sEnableTextures = false;
        sEnableTexCoordArray = false;
        sEnableVertexArray = false;
        sLineWidth = 1.0f;
        sRed = -1.0f;
        sGreen = -1.0f;
        sBlue = -1.0f;
        sAlpha = -1.0f;
        EXTENSIONS_VERTEXBUFFEROBJECTS = false;
        EXTENSIONS_DRAWTEXTURE = false;
        EXTENSIONS_TEXTURE_NON_POWER_OF_TWO = false;
    }

    public static void reset(GL10 pGL) {
        sCurrentHardwareBufferID = -1;
        sCurrentHardwareTextureID = -1;
        sCurrentMatrix = -1;
        sCurrentSourceBlendMode = -1;
        sCurrentDestinationBlendMode = -1;
        sCurrentVertexFloatBuffer = null;
        sCurrentTextureFloatBuffer = null;
        enableDither(pGL);
        enableLightning(pGL);
        enableDepthTest(pGL);
        enableMultisample(pGL);
        disableBlend(pGL);
        disableCulling(pGL);
        disableTextures(pGL);
        disableTexCoordArray(pGL);
        disableVertexArray(pGL);
        sLineWidth = 1.0f;
        sRed = -1.0f;
        sGreen = -1.0f;
        sBlue = -1.0f;
        sAlpha = -1.0f;
        EXTENSIONS_VERTEXBUFFEROBJECTS = false;
        EXTENSIONS_DRAWTEXTURE = false;
        EXTENSIONS_TEXTURE_NON_POWER_OF_TWO = false;
    }

    public static void enableExtensions(GL10 pGL, RenderOptions pRenderOptions) {
        String version = pGL.glGetString(7938);
        String renderer = pGL.glGetString(7937);
        String extensions = pGL.glGetString(7939);
        Debug.d("RENDERER: " + renderer);
        Debug.d("VERSION: " + version);
        Debug.d("EXTENSIONS: " + extensions);
        boolean isOpenGL10 = version.contains(BuildConfig.VERSION_NAME);
        boolean isOpenGL2X = version.contains("2.");
        boolean isSoftwareRenderer = renderer.contains("PixelFlinger");
        boolean isVBOCapable = extensions.contains("_vertex_buffer_object");
        boolean isDrawTextureCapable = extensions.contains("draw_texture");
        boolean isTextureNonPowerOfTwoCapable = extensions.contains("texture_npot");
        EXTENSIONS_VERTEXBUFFEROBJECTS = (pRenderOptions.isDisableExtensionVertexBufferObjects() || isSoftwareRenderer || (!isVBOCapable && isOpenGL10)) ? false : true;
        EXTENSIONS_DRAWTEXTURE = !pRenderOptions.isDisableExtensionVertexBufferObjects() && (isDrawTextureCapable || !isOpenGL10);
        EXTENSIONS_TEXTURE_NON_POWER_OF_TWO = isTextureNonPowerOfTwoCapable || isOpenGL2X;
        hackBrokenDevices();
        Debug.d("EXTENSIONS_VERXTEXBUFFEROBJECTS = " + EXTENSIONS_VERTEXBUFFEROBJECTS);
        Debug.d("EXTENSIONS_DRAWTEXTURE = " + EXTENSIONS_DRAWTEXTURE);
    }

    private static void hackBrokenDevices() {
        if (Build.PRODUCT.contains("morrison")) {
            EXTENSIONS_VERTEXBUFFEROBJECTS = false;
        }
    }

    public static void setColor(GL10 pGL, float pRed, float pGreen, float pBlue, float pAlpha) {
        if (pAlpha != sAlpha || pRed != sRed || pGreen != sGreen || pBlue != sBlue) {
            sAlpha = pAlpha;
            sRed = pRed;
            sGreen = pGreen;
            sBlue = pBlue;
            pGL.glColor4f(pRed, pGreen, pBlue, pAlpha);
        }
    }

    public static void enableVertexArray(GL10 pGL) {
        if (!sEnableVertexArray) {
            sEnableVertexArray = true;
            pGL.glEnableClientState(32884);
        }
    }

    public static void disableVertexArray(GL10 pGL) {
        if (sEnableVertexArray) {
            sEnableVertexArray = false;
            pGL.glDisableClientState(32884);
        }
    }

    public static void enableTexCoordArray(GL10 pGL) {
        if (!sEnableTexCoordArray) {
            sEnableTexCoordArray = true;
            pGL.glEnableClientState(32888);
        }
    }

    public static void disableTexCoordArray(GL10 pGL) {
        if (sEnableTexCoordArray) {
            sEnableTexCoordArray = false;
            pGL.glDisableClientState(32888);
        }
    }

    public static void enableScissorTest(GL10 pGL) {
        if (!sEnableScissorTest) {
            sEnableScissorTest = true;
            pGL.glEnable(3089);
        }
    }

    public static void disableScissorTest(GL10 pGL) {
        if (sEnableScissorTest) {
            sEnableScissorTest = false;
            pGL.glDisable(3089);
        }
    }

    public static void enableBlend(GL10 pGL) {
        if (!sEnableBlend) {
            sEnableBlend = true;
            pGL.glEnable(3042);
        }
    }

    public static void disableBlend(GL10 pGL) {
        if (sEnableBlend) {
            sEnableBlend = false;
            pGL.glDisable(3042);
        }
    }

    public static void enableCulling(GL10 pGL) {
        if (!sEnableCulling) {
            sEnableCulling = true;
            pGL.glEnable(2884);
        }
    }

    public static void disableCulling(GL10 pGL) {
        if (sEnableCulling) {
            sEnableCulling = false;
            pGL.glDisable(2884);
        }
    }

    public static void enableTextures(GL10 pGL) {
        if (!sEnableTextures) {
            sEnableTextures = true;
            pGL.glEnable(3553);
        }
    }

    public static void disableTextures(GL10 pGL) {
        if (sEnableTextures) {
            sEnableTextures = false;
            pGL.glDisable(3553);
        }
    }

    public static void enableLightning(GL10 pGL) {
        if (!sEnableLightning) {
            sEnableLightning = true;
            pGL.glEnable(2896);
        }
    }

    public static void disableLightning(GL10 pGL) {
        if (sEnableLightning) {
            sEnableLightning = false;
            pGL.glDisable(2896);
        }
    }

    public static void enableDither(GL10 pGL) {
        if (!sEnableDither) {
            sEnableDither = true;
            pGL.glEnable(3024);
        }
    }

    public static void disableDither(GL10 pGL) {
        if (sEnableDither) {
            sEnableDither = false;
            pGL.glDisable(3024);
        }
    }

    public static void enableDepthTest(GL10 pGL) {
        if (!sEnableDepthTest) {
            sEnableDepthTest = true;
            pGL.glEnable(2929);
        }
    }

    public static void disableDepthTest(GL10 pGL) {
        if (sEnableDepthTest) {
            sEnableDepthTest = false;
            pGL.glDisable(2929);
        }
    }

    public static void enableMultisample(GL10 pGL) {
        if (!sEnableMultisample) {
            sEnableMultisample = true;
            pGL.glEnable(32925);
        }
    }

    public static void disableMultisample(GL10 pGL) {
        if (sEnableMultisample) {
            sEnableMultisample = false;
            pGL.glDisable(32925);
        }
    }

    public static void bindBuffer(GL11 pGL11, int pHardwareBufferID) {
        if (sCurrentHardwareBufferID != pHardwareBufferID) {
            sCurrentHardwareBufferID = pHardwareBufferID;
            pGL11.glBindBuffer(34962, pHardwareBufferID);
        }
    }

    public static void deleteBuffer(GL11 pGL11, int pHardwareBufferID) {
        HARDWAREBUFFERID_CONTAINER[0] = pHardwareBufferID;
        pGL11.glDeleteBuffers(1, HARDWAREBUFFERID_CONTAINER, 0);
    }

    public static void bindTexture(GL10 pGL, int pHardwareTextureID) {
        if (sCurrentHardwareTextureID != pHardwareTextureID) {
            sCurrentHardwareTextureID = pHardwareTextureID;
            pGL.glBindTexture(3553, pHardwareTextureID);
        }
    }

    public static void forceBindTexture(GL10 pGL, int pHardwareTextureID) {
        sCurrentHardwareTextureID = pHardwareTextureID;
        pGL.glBindTexture(3553, pHardwareTextureID);
    }

    public static void deleteTexture(GL10 pGL, int pHardwareTextureID) {
        HARDWARETEXTUREID_CONTAINER[0] = pHardwareTextureID;
        pGL.glDeleteTextures(1, HARDWARETEXTUREID_CONTAINER, 0);
    }

    public static void texCoordPointer(GL10 pGL, FastFloatBuffer pTextureFloatBuffer) {
        if (sCurrentTextureFloatBuffer != pTextureFloatBuffer) {
            sCurrentTextureFloatBuffer = pTextureFloatBuffer;
            pGL.glTexCoordPointer(2, 5126, 0, pTextureFloatBuffer.mByteBuffer);
        }
    }

    public static void texCoordZeroPointer(GL11 pGL11) {
        pGL11.glTexCoordPointer(2, 5126, 0, 0);
    }

    public static void vertexPointer(GL10 pGL, FastFloatBuffer pVertexFloatBuffer) {
        if (sCurrentVertexFloatBuffer != pVertexFloatBuffer) {
            sCurrentVertexFloatBuffer = pVertexFloatBuffer;
            pGL.glVertexPointer(2, 5126, 0, pVertexFloatBuffer.mByteBuffer);
        }
    }

    public static void vertexZeroPointer(GL11 pGL11) {
        pGL11.glVertexPointer(2, 5126, 0, 0);
    }

    public static void blendFunction(GL10 pGL, int pSourceBlendMode, int pDestinationBlendMode) {
        if (sCurrentSourceBlendMode != pSourceBlendMode || sCurrentDestinationBlendMode != pDestinationBlendMode) {
            sCurrentSourceBlendMode = pSourceBlendMode;
            sCurrentDestinationBlendMode = pDestinationBlendMode;
            pGL.glBlendFunc(pSourceBlendMode, pDestinationBlendMode);
        }
    }

    public static void lineWidth(GL10 pGL, float pLineWidth) {
        if (sLineWidth != pLineWidth) {
            sLineWidth = pLineWidth;
            pGL.glLineWidth(pLineWidth);
        }
    }

    public static void switchToModelViewMatrix(GL10 pGL) {
        if (sCurrentMatrix != 5888) {
            sCurrentMatrix = 5888;
            pGL.glMatrixMode(5888);
        }
    }

    public static void switchToProjectionMatrix(GL10 pGL) {
        if (sCurrentMatrix != 5889) {
            sCurrentMatrix = 5889;
            pGL.glMatrixMode(5889);
        }
    }

    public static void setProjectionIdentityMatrix(GL10 pGL) {
        switchToProjectionMatrix(pGL);
        pGL.glLoadIdentity();
    }

    public static void setModelViewIdentityMatrix(GL10 pGL) {
        switchToModelViewMatrix(pGL);
        pGL.glLoadIdentity();
    }

    public static void setShadeModelFlat(GL10 pGL) {
        pGL.glShadeModel(7424);
    }

    public static void setPerspectiveCorrectionHintFastest(GL10 pGL) {
        pGL.glHint(3152, 4353);
    }

    public static void bufferData(GL11 pGL11, ByteBuffer pByteBuffer, int pUsage) {
        pGL11.glBufferData(34962, pByteBuffer.capacity(), pByteBuffer, pUsage);
    }

    public static void glTexImage2D(GL10 pGL, int pTarget, int pLevel, Bitmap pBitmap, int pBorder, Texture.PixelFormat pPixelFormat) {
        Buffer pixelBuffer = getPixels(pBitmap, pPixelFormat);
        pGL.glTexImage2D(pTarget, pLevel, pPixelFormat.getGLFormat(), pBitmap.getWidth(), pBitmap.getHeight(), pBorder, pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), pixelBuffer);
    }

    public static void glTexSubImage2D(GL10 pGL, int pTarget, int pLevel, int pXOffset, int pYOffset, Bitmap pBitmap, Texture.PixelFormat pPixelFormat) {
        Buffer pixelBuffer = getPixels(pBitmap, pPixelFormat);
        pGL.glTexSubImage2D(pTarget, pLevel, pXOffset, pYOffset, pBitmap.getWidth(), pBitmap.getHeight(), pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), pixelBuffer);
    }

    private static Buffer getPixels(Bitmap pBitmap, Texture.PixelFormat pPixelFormat) {
        int[] pixelsARGB_8888 = getPixelsARGB_8888(pBitmap);
        switch ($SWITCH_TABLE$org$anddev$andengine$opengl$texture$Texture$PixelFormat()[pPixelFormat.ordinal()]) {
            case 2:
                return ByteBuffer.wrap(convertARGB_8888toARGB_4444(pixelsARGB_8888));
            case 3:
            default:
                throw new IllegalArgumentException("Unexpected " + Texture.PixelFormat.class.getSimpleName() + ": '" + pPixelFormat + "'.");
            case 4:
                return IntBuffer.wrap(convertARGB_8888toRGBA_8888(pixelsARGB_8888));
            case 5:
                return ByteBuffer.wrap(convertARGB_8888toRGB_565(pixelsARGB_8888));
            case 6:
                return ByteBuffer.wrap(convertARGB_8888toA_8(pixelsARGB_8888));
        }
    }

    private static int[] convertARGB_8888toRGBA_8888(int[] pPixelsARGB_8888) {
        if (IS_LITTLE_ENDIAN) {
            for (int i = pPixelsARGB_8888.length - 1; i >= 0; i--) {
                int pixel = pPixelsARGB_8888[i];
                pPixelsARGB_8888[i] = ((-16711936) & pixel) | ((pixel & MotionEventCompat.ACTION_MASK) << 16) | ((16711680 & pixel) >> 16);
            }
        } else {
            for (int i2 = pPixelsARGB_8888.length - 1; i2 >= 0; i2--) {
                int pixel2 = pPixelsARGB_8888[i2];
                pPixelsARGB_8888[i2] = ((16777215 & pixel2) << 8) | (((-16777216) & pixel2) >> 24);
            }
        }
        return pPixelsARGB_8888;
    }

    private static byte[] convertARGB_8888toRGB_565(int[] pPixelsARGB_8888) {
        byte[] pixelsRGB_565 = new byte[pPixelsARGB_8888.length * 2];
        if (IS_LITTLE_ENDIAN) {
            int j = pixelsRGB_565.length - 1;
            int j2 = j;
            for (int i = pPixelsARGB_8888.length - 1; i >= 0; i--) {
                int pixel = pPixelsARGB_8888[i];
                int red = (pixel >> 16) & MotionEventCompat.ACTION_MASK;
                int green = (pixel >> 8) & MotionEventCompat.ACTION_MASK;
                int blue = pixel & MotionEventCompat.ACTION_MASK;
                int j3 = j2 - 1;
                pixelsRGB_565[j2] = (byte) ((red & 248) | (green >> 5));
                j2 = j3 - 1;
                pixelsRGB_565[j3] = (byte) (((green << 3) & 224) | (blue >> 3));
            }
        } else {
            int j4 = pixelsRGB_565.length - 1;
            int j5 = j4;
            for (int i2 = pPixelsARGB_8888.length - 1; i2 >= 0; i2--) {
                int pixel2 = pPixelsARGB_8888[i2];
                int red2 = (pixel2 >> 16) & MotionEventCompat.ACTION_MASK;
                int green2 = (pixel2 >> 8) & MotionEventCompat.ACTION_MASK;
                int blue2 = pixel2 & MotionEventCompat.ACTION_MASK;
                int j6 = j5 - 1;
                pixelsRGB_565[j5] = (byte) (((green2 << 3) & 224) | (blue2 >> 3));
                j5 = j6 - 1;
                pixelsRGB_565[j6] = (byte) ((red2 & 248) | (green2 >> 5));
            }
        }
        return pixelsRGB_565;
    }

    private static byte[] convertARGB_8888toARGB_4444(int[] pPixelsARGB_8888) {
        byte[] pixelsARGB_4444 = new byte[pPixelsARGB_8888.length * 2];
        if (IS_LITTLE_ENDIAN) {
            int j = pixelsARGB_4444.length - 1;
            int j2 = j;
            for (int i = pPixelsARGB_8888.length - 1; i >= 0; i--) {
                int pixel = pPixelsARGB_8888[i];
                int alpha = (pixel >> 28) & 15;
                int red = (pixel >> 16) & 240;
                int green = (pixel >> 8) & 240;
                int blue = pixel & 15;
                int j3 = j2 - 1;
                pixelsARGB_4444[j2] = (byte) (alpha | red);
                j2 = j3 - 1;
                pixelsARGB_4444[j3] = (byte) (green | blue);
            }
        } else {
            int j4 = pixelsARGB_4444.length - 1;
            int j5 = j4;
            for (int i2 = pPixelsARGB_8888.length - 1; i2 >= 0; i2--) {
                int pixel2 = pPixelsARGB_8888[i2];
                int alpha2 = (pixel2 >> 28) & 15;
                int red2 = (pixel2 >> 16) & 240;
                int green2 = (pixel2 >> 8) & 240;
                int blue2 = pixel2 & 15;
                int j6 = j5 - 1;
                pixelsARGB_4444[j5] = (byte) (green2 | blue2);
                j5 = j6 - 1;
                pixelsARGB_4444[j6] = (byte) (alpha2 | red2);
            }
        }
        return pixelsARGB_4444;
    }

    private static byte[] convertARGB_8888toA_8(int[] pPixelsARGB_8888) {
        byte[] pixelsA_8 = new byte[pPixelsARGB_8888.length];
        if (IS_LITTLE_ENDIAN) {
            for (int i = pPixelsARGB_8888.length - 1; i >= 0; i--) {
                pixelsA_8[i] = (byte) (pPixelsARGB_8888[i] >> 24);
            }
        } else {
            for (int i2 = pPixelsARGB_8888.length - 1; i2 >= 0; i2--) {
                pixelsA_8[i2] = (byte) (pPixelsARGB_8888[i2] & MotionEventCompat.ACTION_MASK);
            }
        }
        return pixelsA_8;
    }

    public static int[] getPixelsARGB_8888(Bitmap pBitmap) {
        int w = pBitmap.getWidth();
        int h = pBitmap.getHeight();
        int[] pixelsARGB_8888 = new int[w * h];
        pBitmap.getPixels(pixelsARGB_8888, 0, w, 0, 0, w, h);
        return pixelsARGB_8888;
    }

    public static void checkGLError(GL10 pGL) throws GLException {
        int err = pGL.glGetError();
        if (err != 0) {
            throw new GLException(err);
        }
    }
}
