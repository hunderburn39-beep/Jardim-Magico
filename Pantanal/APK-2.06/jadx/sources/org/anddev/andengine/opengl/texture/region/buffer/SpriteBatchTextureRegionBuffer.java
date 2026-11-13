package org.anddev.andengine.opengl.texture.region.buffer;

import org.anddev.andengine.opengl.buffer.BufferObject;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.region.BaseTextureRegion;
import org.anddev.andengine.opengl.util.FastFloatBuffer;

/* JADX INFO: loaded from: classes.dex */
public class SpriteBatchTextureRegionBuffer extends BufferObject {
    protected int mIndex;

    public SpriteBatchTextureRegionBuffer(int pCapacity, int pDrawType, boolean pManaged) {
        super(pCapacity * 2 * 6, pDrawType, pManaged);
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int pIndex) {
        this.mIndex = pIndex;
    }

    public void add(BaseTextureRegion pTextureRegion) {
        ITexture texture = pTextureRegion.getTexture();
        if (texture != null) {
            int x1 = Float.floatToRawIntBits(pTextureRegion.getTextureCoordinateX1());
            int y1 = Float.floatToRawIntBits(pTextureRegion.getTextureCoordinateY1());
            int x2 = Float.floatToRawIntBits(pTextureRegion.getTextureCoordinateX2());
            int y2 = Float.floatToRawIntBits(pTextureRegion.getTextureCoordinateY2());
            int[] bufferData = this.mBufferData;
            int index = this.mIndex;
            int index2 = index + 1;
            bufferData[index] = x1;
            int index3 = index2 + 1;
            bufferData[index2] = y1;
            int index4 = index3 + 1;
            bufferData[index3] = x1;
            int index5 = index4 + 1;
            bufferData[index4] = y2;
            int index6 = index5 + 1;
            bufferData[index5] = x2;
            int index7 = index6 + 1;
            bufferData[index6] = y1;
            int index8 = index7 + 1;
            bufferData[index7] = x2;
            int index9 = index8 + 1;
            bufferData[index8] = y1;
            int index10 = index9 + 1;
            bufferData[index9] = x1;
            int index11 = index10 + 1;
            bufferData[index10] = y2;
            int index12 = index11 + 1;
            bufferData[index11] = x2;
            bufferData[index12] = y2;
            this.mIndex = index12 + 1;
        }
    }

    public void submit() {
        FastFloatBuffer buffer = this.mFloatBuffer;
        buffer.position(0);
        buffer.put(this.mBufferData);
        buffer.position(0);
        super.setHardwareBufferNeedsUpdate();
    }
}
