package org.anddev.andengine.util.cache;

import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.util.MultiKey;
import org.anddev.andengine.util.MultiKeyHashMap;

/* JADX INFO: loaded from: classes.dex */
public class RectangleVertexBufferCache {
    private final int mDrawType;
    private final MultiKeyHashMap<Integer, RectangleVertexBuffer> mRectangleVertexBufferCache;

    public RectangleVertexBufferCache() {
        this(35044);
    }

    public RectangleVertexBufferCache(int pDrawType) {
        this.mRectangleVertexBufferCache = new MultiKeyHashMap<>();
        this.mDrawType = pDrawType;
    }

    public RectangleVertexBuffer get(int pWidth, int pHeight) {
        RectangleVertexBuffer cachedRectangleVertexBuffer = this.mRectangleVertexBufferCache.get(Integer.valueOf(pWidth), Integer.valueOf(pHeight));
        return cachedRectangleVertexBuffer != null ? cachedRectangleVertexBuffer : put(pWidth, pHeight, new RectangleVertexBuffer(this.mDrawType, false));
    }

    public RectangleVertexBuffer put(int i, int i2, RectangleVertexBuffer rectangleVertexBuffer) {
        rectangleVertexBuffer.update(i, i2);
        BufferObjectManager.getActiveInstance().loadBufferObject(rectangleVertexBuffer);
        this.mRectangleVertexBufferCache.put((Integer) new MultiKey(Integer.valueOf(i), Integer.valueOf(i2)), rectangleVertexBuffer);
        return rectangleVertexBuffer;
    }
}
