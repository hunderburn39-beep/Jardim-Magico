package org.anddev.andengine.entity.shape;

import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.collision.RectangularShapeCollisionChecker;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.opengl.vertex.VertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class RectangularShape extends Shape {
    protected float mBaseHeight;
    protected float mBaseWidth;
    protected float mHeight;
    protected final VertexBuffer mVertexBuffer;
    protected float mWidth;

    public RectangularShape(float pX, float pY, float pWidth, float pHeight, VertexBuffer pVertexBuffer) {
        super(pX, pY);
        this.mBaseWidth = pWidth;
        this.mBaseHeight = pHeight;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        this.mVertexBuffer = pVertexBuffer;
        this.mRotationCenterX = pWidth * 0.5f;
        this.mRotationCenterY = pHeight * 0.5f;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    public VertexBuffer getVertexBuffer() {
        return this.mVertexBuffer;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getWidth() {
        return this.mWidth;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getHeight() {
        return this.mHeight;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getBaseWidth() {
        return this.mBaseWidth;
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getBaseHeight() {
        return this.mBaseHeight;
    }

    public void setWidth(float pWidth) {
        this.mWidth = pWidth;
        updateVertexBuffer();
    }

    public void setHeight(float pHeight) {
        this.mHeight = pHeight;
        updateVertexBuffer();
    }

    public void setSize(float pWidth, float pHeight) {
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        updateVertexBuffer();
    }

    public void setBaseSize() {
        if (this.mWidth != this.mBaseWidth || this.mHeight != this.mBaseHeight) {
            this.mWidth = this.mBaseWidth;
            this.mHeight = this.mBaseHeight;
            updateVertexBuffer();
        }
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected boolean isCulled(Camera pCamera) {
        float x = this.mX;
        float y = this.mY;
        return x > pCamera.getMaxX() || y > pCamera.getMaxY() || getWidth() + x < pCamera.getMinX() || getHeight() + y < pCamera.getMinY();
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void drawVertices(GL10 pGL, Camera pCamera) {
        pGL.glDrawArrays(5, 0, 4);
    }

    @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        setBaseSize();
        float baseWidth = getBaseWidth();
        float baseHeight = getBaseHeight();
        this.mRotationCenterX = baseWidth * 0.5f;
        this.mRotationCenterY = baseHeight * 0.5f;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
    }

    @Override // org.anddev.andengine.entity.scene.Scene.ITouchArea
    public boolean contains(float pX, float pY) {
        return RectangularShapeCollisionChecker.checkContains(this, pX, pY);
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    public float[] getSceneCenterCoordinates() {
        return convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f);
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public boolean collidesWith(IShape pOtherShape) {
        if (pOtherShape instanceof RectangularShape) {
            RectangularShape pOtherRectangularShape = (RectangularShape) pOtherShape;
            return RectangularShapeCollisionChecker.checkCollision(this, pOtherRectangularShape);
        }
        if (pOtherShape instanceof Line) {
            Line line = (Line) pOtherShape;
            return RectangularShapeCollisionChecker.checkCollision(this, line);
        }
        return false;
    }
}
