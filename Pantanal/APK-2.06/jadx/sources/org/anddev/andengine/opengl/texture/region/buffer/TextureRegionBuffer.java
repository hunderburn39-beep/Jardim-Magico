package org.anddev.andengine.opengl.texture.region.buffer;

import org.anddev.andengine.opengl.buffer.BufferObject;
import org.anddev.andengine.opengl.texture.region.BaseTextureRegion;
import org.anddev.andengine.opengl.util.FastFloatBuffer;

/* JADX INFO: loaded from: classes.dex */
public class TextureRegionBuffer extends BufferObject {
    private boolean mFlippedHorizontal;
    private boolean mFlippedVertical;
    protected final BaseTextureRegion mTextureRegion;

    public TextureRegionBuffer(BaseTextureRegion pBaseTextureRegion, int pDrawType, boolean pManaged) {
        super(8, pDrawType, pManaged);
        this.mTextureRegion = pBaseTextureRegion;
    }

    public BaseTextureRegion getTextureRegion() {
        return this.mTextureRegion;
    }

    public boolean isFlippedHorizontal() {
        return this.mFlippedHorizontal;
    }

    public void setFlippedHorizontal(boolean pFlippedHorizontal) {
        if (this.mFlippedHorizontal != pFlippedHorizontal) {
            this.mFlippedHorizontal = pFlippedHorizontal;
            update();
        }
    }

    public boolean isFlippedVertical() {
        return this.mFlippedVertical;
    }

    public void setFlippedVertical(boolean pFlippedVertical) {
        if (this.mFlippedVertical != pFlippedVertical) {
            this.mFlippedVertical = pFlippedVertical;
            update();
        }
    }

    public synchronized void update() {
        BaseTextureRegion textureRegion = this.mTextureRegion;
        int x1 = Float.floatToRawIntBits(textureRegion.getTextureCoordinateX1());
        int y1 = Float.floatToRawIntBits(textureRegion.getTextureCoordinateY1());
        int x2 = Float.floatToRawIntBits(textureRegion.getTextureCoordinateX2());
        int y2 = Float.floatToRawIntBits(textureRegion.getTextureCoordinateY2());
        int[] bufferData = this.mBufferData;
        if (this.mFlippedVertical) {
            if (this.mFlippedHorizontal) {
                bufferData[0] = x2;
                bufferData[1] = y2;
                bufferData[2] = x2;
                bufferData[3] = y1;
                bufferData[4] = x1;
                bufferData[5] = y2;
                bufferData[6] = x1;
                bufferData[7] = y1;
            } else {
                bufferData[0] = x1;
                bufferData[1] = y2;
                bufferData[2] = x1;
                bufferData[3] = y1;
                bufferData[4] = x2;
                bufferData[5] = y2;
                bufferData[6] = x2;
                bufferData[7] = y1;
            }
        } else if (this.mFlippedHorizontal) {
            bufferData[0] = x2;
            bufferData[1] = y1;
            bufferData[2] = x2;
            bufferData[3] = y2;
            bufferData[4] = x1;
            bufferData[5] = y1;
            bufferData[6] = x1;
            bufferData[7] = y2;
        } else {
            bufferData[0] = x1;
            bufferData[1] = y1;
            bufferData[2] = x1;
            bufferData[3] = y2;
            bufferData[4] = x2;
            bufferData[5] = y1;
            bufferData[6] = x2;
            bufferData[7] = y2;
        }
        FastFloatBuffer buffer = this.mFloatBuffer;
        buffer.position(0);
        buffer.put(bufferData);
        buffer.position(0);
        super.setHardwareBufferNeedsUpdate();
    }
}
