package org.anddev.andengine.entity.primitive;

import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseRectangle extends RectangularShape {
    public BaseRectangle(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight, new RectangleVertexBuffer(35044, true));
        updateVertexBuffer();
    }

    public BaseRectangle(float pX, float pY, float pWidth, float pHeight, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pWidth, pHeight, pRectangleVertexBuffer);
    }

    @Override // org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape
    public RectangleVertexBuffer getVertexBuffer() {
        return (RectangleVertexBuffer) this.mVertexBuffer;
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onUpdateVertexBuffer() {
        getVertexBuffer().update(this.mWidth, this.mHeight);
    }
}
