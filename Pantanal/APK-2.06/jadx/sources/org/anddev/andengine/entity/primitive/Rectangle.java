package org.anddev.andengine.entity.primitive;

import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public class Rectangle extends BaseRectangle {
    public Rectangle(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);
    }

    public Rectangle(float pX, float pY, float pWidth, float pHeight, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pWidth, pHeight, pRectangleVertexBuffer);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onInitDraw(GL10 pGL) {
        super.onInitDraw(pGL);
        GLHelper.disableTextures(pGL);
        GLHelper.disableTexCoordArray(pGL);
    }
}
