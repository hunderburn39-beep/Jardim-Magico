package org.anddev.andengine.opengl.vertex;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.opengl.util.FastFloatBuffer;

/* JADX INFO: loaded from: classes.dex */
public class RectangleVertexBuffer extends VertexBuffer {
    private static final int FLOAT_TO_RAW_INT_BITS_ZERO = Float.floatToRawIntBits(BitmapDescriptorFactory.HUE_RED);
    public static final int VERTICES_PER_RECTANGLE = 4;

    public RectangleVertexBuffer(int pDrawType, boolean pManaged) {
        super(8, pDrawType, pManaged);
    }

    public synchronized void update(float pWidth, float pHeight) {
        int x = FLOAT_TO_RAW_INT_BITS_ZERO;
        int y = FLOAT_TO_RAW_INT_BITS_ZERO;
        int x2 = Float.floatToRawIntBits(pWidth);
        int y2 = Float.floatToRawIntBits(pHeight);
        int[] bufferData = this.mBufferData;
        bufferData[0] = x;
        bufferData[1] = y;
        bufferData[2] = x;
        bufferData[3] = y2;
        bufferData[4] = x2;
        bufferData[5] = y;
        bufferData[6] = x2;
        bufferData[7] = y2;
        FastFloatBuffer buffer = getFloatBuffer();
        buffer.position(0);
        buffer.put(bufferData);
        buffer.position(0);
        super.setHardwareBufferNeedsUpdate();
    }
}
