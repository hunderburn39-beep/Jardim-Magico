package org.anddev.andengine.entity.primitive;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.collision.LineCollisionChecker;
import org.anddev.andengine.collision.RectangularShapeCollisionChecker;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.opengl.vertex.LineVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public class Line extends Shape {
    private static final float LINEWIDTH_DEFAULT = 1.0f;
    private final LineVertexBuffer mLineVertexBuffer;
    private float mLineWidth;
    protected float mX2;
    protected float mY2;

    public Line(float pX1, float pY1, float pX2, float pY2) {
        this(pX1, pY1, pX2, pY2, 1.0f);
    }

    public Line(float pX1, float pY1, float pX2, float pY2, float pLineWidth) {
        super(pX1, pY1);
        this.mX2 = pX2;
        this.mY2 = pY2;
        this.mLineWidth = pLineWidth;
        this.mLineVertexBuffer = new LineVertexBuffer(35044, true);
        updateVertexBuffer();
        float width = getWidth();
        float height = getHeight();
        this.mRotationCenterX = width * 0.5f;
        this.mRotationCenterY = height * 0.5f;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public float getX() {
        return super.getX();
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public float getY() {
        return super.getY();
    }

    public float getX1() {
        return super.getX();
    }

    public float getY1() {
        return super.getY();
    }

    public float getX2() {
        return this.mX2;
    }

    public float getY2() {
        return this.mY2;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public void setLineWidth(float pLineWidth) {
        this.mLineWidth = pLineWidth;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getBaseHeight() {
        return this.mY2 - this.mY;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getBaseWidth() {
        return this.mX2 - this.mX;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getHeight() {
        return this.mY2 - this.mY;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getWidth() {
        return this.mX2 - this.mX;
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public void setPosition(float pX, float pY) {
        float dX = this.mX - pX;
        float dY = this.mY - pY;
        super.setPosition(pX, pY);
        this.mX2 += dX;
        this.mY2 += dY;
    }

    public void setPosition(float pX1, float pY1, float pX2, float pY2) {
        this.mX2 = pX2;
        this.mY2 = pY2;
        super.setPosition(pX1, pY1);
        updateVertexBuffer();
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected boolean isCulled(Camera pCamera) {
        return pCamera.isLineVisible(this);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onInitDraw(GL10 pGL) {
        super.onInitDraw(pGL);
        GLHelper.disableTextures(pGL);
        GLHelper.disableTexCoordArray(pGL);
        GLHelper.lineWidth(pGL, this.mLineWidth);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    public LineVertexBuffer getVertexBuffer() {
        return this.mLineVertexBuffer;
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onUpdateVertexBuffer() {
        this.mLineVertexBuffer.update(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.mX2 - this.mX, this.mY2 - this.mY);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void drawVertices(GL10 pGL, Camera pCamera) {
        pGL.glDrawArrays(1, 0, 2);
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    public float[] getSceneCenterCoordinates() {
        return null;
    }

    @Override // org.anddev.andengine.entity.scene.Scene.ITouchArea
    @Deprecated
    public boolean contains(float pX, float pY) {
        return false;
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public float[] convertSceneToLocalCoordinates(float pX, float pY) {
        return null;
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    @Deprecated
    public float[] convertLocalToSceneCoordinates(float pX, float pY) {
        return null;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public boolean collidesWith(IShape pOtherShape) {
        if (pOtherShape instanceof Line) {
            Line otherLine = (Line) pOtherShape;
            return LineCollisionChecker.checkLineCollision(this.mX, this.mY, this.mX2, this.mY2, otherLine.mX, otherLine.mY, otherLine.mX2, otherLine.mY2);
        }
        if (pOtherShape instanceof RectangularShape) {
            RectangularShape rectangularShape = (RectangularShape) pOtherShape;
            return RectangularShapeCollisionChecker.checkCollision(rectangularShape, this);
        }
        return false;
    }
}
