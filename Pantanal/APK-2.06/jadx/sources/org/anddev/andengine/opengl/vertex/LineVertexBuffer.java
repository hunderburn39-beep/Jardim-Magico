package org.anddev.andengine.opengl.vertex;

import org.anddev.andengine.opengl.util.FastFloatBuffer;

/* JADX INFO: loaded from: classes.dex */
public class LineVertexBuffer extends VertexBuffer {
    public static final int VERTICES_PER_LINE = 2;

    public LineVertexBuffer(int pDrawType, boolean pManaged) {
        super(4, pDrawType, pManaged);
    }

    public synchronized void update(float pX1, float pY1, float pX2, float pY2) {
        int[] bufferData = this.mBufferData;
        bufferData[0] = Float.floatToRawIntBits(pX1);
        bufferData[1] = Float.floatToRawIntBits(pY1);
        bufferData[2] = Float.floatToRawIntBits(pX2);
        bufferData[3] = Float.floatToRawIntBits(pY2);
        FastFloatBuffer buffer = this.mFloatBuffer;
        buffer.position(0);
        buffer.put(bufferData);
        buffer.position(0);
        super.setHardwareBufferNeedsUpdate();
    }
}
