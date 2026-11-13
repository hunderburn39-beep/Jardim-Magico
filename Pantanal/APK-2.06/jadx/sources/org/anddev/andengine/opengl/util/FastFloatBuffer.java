package org.anddev.andengine.opengl.util;

import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes.dex */
public class FastFloatBuffer {
    private static SoftReference<int[]> sWeakIntArray = new SoftReference<>(new int[0]);
    public final ByteBuffer mByteBuffer;
    private final FloatBuffer mFloatBuffer;
    private final IntBuffer mIntBuffer;

    public FastFloatBuffer(int pCapacity) {
        this.mByteBuffer = ByteBuffer.allocateDirect(pCapacity * 4).order(ByteOrder.nativeOrder());
        this.mFloatBuffer = this.mByteBuffer.asFloatBuffer();
        this.mIntBuffer = this.mByteBuffer.asIntBuffer();
    }

    public void flip() {
        this.mByteBuffer.flip();
        this.mFloatBuffer.flip();
        this.mIntBuffer.flip();
    }

    public void put(float f) {
        ByteBuffer byteBuffer = this.mByteBuffer;
        IntBuffer intBuffer = this.mIntBuffer;
        byteBuffer.position(byteBuffer.position() + 4);
        this.mFloatBuffer.put(f);
        intBuffer.position(intBuffer.position() + 1);
    }

    public void put(float[] data) {
        int length = data.length;
        int[] ia = sWeakIntArray.get();
        if (ia == null || ia.length < length) {
            ia = new int[length];
            sWeakIntArray = new SoftReference<>(ia);
        }
        for (int i = 0; i < length; i++) {
            ia[i] = Float.floatToRawIntBits(data[i]);
        }
        ByteBuffer byteBuffer = this.mByteBuffer;
        byteBuffer.position(byteBuffer.position() + (length * 4));
        FloatBuffer floatBuffer = this.mFloatBuffer;
        floatBuffer.position(floatBuffer.position() + length);
        this.mIntBuffer.put(ia, 0, length);
    }

    public void put(int[] data) {
        ByteBuffer byteBuffer = this.mByteBuffer;
        byteBuffer.position(byteBuffer.position() + (data.length * 4));
        FloatBuffer floatBuffer = this.mFloatBuffer;
        floatBuffer.position(floatBuffer.position() + data.length);
        this.mIntBuffer.put(data, 0, data.length);
    }

    public static int[] convert(float... data) {
        int length = data.length;
        int[] id = new int[length];
        for (int i = 0; i < length; i++) {
            id[i] = Float.floatToRawIntBits(data[i]);
        }
        return id;
    }

    public void put(FastFloatBuffer b) {
        ByteBuffer byteBuffer = this.mByteBuffer;
        byteBuffer.put(b.mByteBuffer);
        this.mFloatBuffer.position(byteBuffer.position() >> 2);
        this.mIntBuffer.position(byteBuffer.position() >> 2);
    }

    public int capacity() {
        return this.mFloatBuffer.capacity();
    }

    public int position() {
        return this.mFloatBuffer.position();
    }

    public void position(int p) {
        this.mByteBuffer.position(p * 4);
        this.mFloatBuffer.position(p);
        this.mIntBuffer.position(p);
    }

    public FloatBuffer slice() {
        return this.mFloatBuffer.slice();
    }

    public int remaining() {
        return this.mFloatBuffer.remaining();
    }

    public int limit() {
        return this.mFloatBuffer.limit();
    }

    public void clear() {
        this.mByteBuffer.clear();
        this.mFloatBuffer.clear();
        this.mIntBuffer.clear();
    }
}
