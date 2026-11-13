package org.anddev.andengine.opengl.vertex;

import org.anddev.andengine.opengl.util.FastFloatBuffer;
import org.anddev.andengine.util.Transformation;

/* JADX INFO: loaded from: classes.dex */
public class SpriteBatchVertexBuffer extends VertexBuffer {
    public static final int VERTICES_PER_RECTANGLE = 6;
    protected int mIndex;
    private static final float[] VERTICES_TMP = new float[8];
    private static final Transformation TRANSFORATION_TMP = new Transformation();

    public SpriteBatchVertexBuffer(int pCapacity, int pDrawType, boolean pManaged) {
        super(pCapacity * 2 * 6, pDrawType, pManaged);
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int pIndex) {
        this.mIndex = pIndex;
    }

    public void add(float pX, float pY, float pWidth, float pHeight, float pRotation) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postRotate(pRotation);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        add(pWidth, pHeight, TRANSFORATION_TMP);
    }

    public void add(float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postScale(pScaleX, pScaleY);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        add(pWidth, pHeight, TRANSFORATION_TMP);
    }

    public void add(float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY) {
        float widthHalf = pWidth * 0.5f;
        float heightHalf = pHeight * 0.5f;
        TRANSFORATION_TMP.setToIdentity();
        TRANSFORATION_TMP.postTranslate(-widthHalf, -heightHalf);
        TRANSFORATION_TMP.postScale(pScaleX, pScaleY);
        TRANSFORATION_TMP.postRotate(pRotation);
        TRANSFORATION_TMP.postTranslate(widthHalf, heightHalf);
        TRANSFORATION_TMP.postTranslate(pX, pY);
        add(pWidth, pHeight, TRANSFORATION_TMP);
    }

    public void add(float pWidth, float pHeight, Transformation pTransformation) {
        VERTICES_TMP[0] = 0.0f;
        VERTICES_TMP[1] = 0.0f;
        VERTICES_TMP[2] = 0.0f;
        VERTICES_TMP[3] = pHeight;
        VERTICES_TMP[4] = pWidth;
        VERTICES_TMP[5] = 0.0f;
        VERTICES_TMP[6] = pWidth;
        VERTICES_TMP[7] = pHeight;
        pTransformation.transform(VERTICES_TMP);
        addInner(VERTICES_TMP[0], VERTICES_TMP[1], VERTICES_TMP[2], VERTICES_TMP[3], VERTICES_TMP[4], VERTICES_TMP[5], VERTICES_TMP[6], VERTICES_TMP[7]);
    }

    public void add(float pX, float pY, float pWidth, float pHeight) {
        addInner(pX, pY, pX + pWidth, pY + pHeight);
    }

    public void addInner(float pX1, float pY1, float pX2, float pY2) {
        int x1 = Float.floatToRawIntBits(pX1);
        int y1 = Float.floatToRawIntBits(pY1);
        int x2 = Float.floatToRawIntBits(pX2);
        int y2 = Float.floatToRawIntBits(pY2);
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

    public void addInner(float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4) {
        int x1 = Float.floatToRawIntBits(pX1);
        int y1 = Float.floatToRawIntBits(pY1);
        int x2 = Float.floatToRawIntBits(pX2);
        int y2 = Float.floatToRawIntBits(pY2);
        int x3 = Float.floatToRawIntBits(pX3);
        int y3 = Float.floatToRawIntBits(pY3);
        int x4 = Float.floatToRawIntBits(pX4);
        int y4 = Float.floatToRawIntBits(pY4);
        int[] bufferData = this.mBufferData;
        int index = this.mIndex;
        int index2 = index + 1;
        bufferData[index] = x1;
        int index3 = index2 + 1;
        bufferData[index2] = y1;
        int index4 = index3 + 1;
        bufferData[index3] = x2;
        int index5 = index4 + 1;
        bufferData[index4] = y2;
        int index6 = index5 + 1;
        bufferData[index5] = x3;
        int index7 = index6 + 1;
        bufferData[index6] = y3;
        int index8 = index7 + 1;
        bufferData[index7] = x3;
        int index9 = index8 + 1;
        bufferData[index8] = y3;
        int index10 = index9 + 1;
        bufferData[index9] = x2;
        int index11 = index10 + 1;
        bufferData[index10] = y2;
        int index12 = index11 + 1;
        bufferData[index11] = x4;
        bufferData[index12] = y4;
        this.mIndex = index12 + 1;
    }

    public void submit() {
        FastFloatBuffer buffer = this.mFloatBuffer;
        buffer.position(0);
        buffer.put(this.mBufferData);
        buffer.position(0);
        super.setHardwareBufferNeedsUpdate();
    }
}
