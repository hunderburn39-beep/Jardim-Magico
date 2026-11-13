package org.anddev.andengine.entity.sprite;

import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.primitive.BaseRectangle;
import org.anddev.andengine.opengl.texture.region.BaseTextureRegion;
import org.anddev.andengine.opengl.texture.region.buffer.TextureRegionBuffer;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseSprite extends BaseRectangle {
    protected final BaseTextureRegion mTextureRegion;

    public BaseSprite(float pX, float pY, float pWidth, float pHeight, BaseTextureRegion pTextureRegion) {
        super(pX, pY, pWidth, pHeight);
        this.mTextureRegion = pTextureRegion;
        initBlendFunction();
    }

    public BaseSprite(float pX, float pY, float pWidth, float pHeight, BaseTextureRegion pTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pWidth, pHeight, pRectangleVertexBuffer);
        this.mTextureRegion = pTextureRegion;
        initBlendFunction();
    }

    public BaseTextureRegion getTextureRegion() {
        return this.mTextureRegion;
    }

    public void setFlippedHorizontal(boolean pFlippedHorizontal) {
        this.mTextureRegion.setFlippedHorizontal(pFlippedHorizontal);
    }

    public void setFlippedVertical(boolean pFlippedVertical) {
        this.mTextureRegion.setFlippedVertical(pFlippedVertical);
    }

    @Override // org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        initBlendFunction();
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onInitDraw(GL10 pGL) {
        super.onInitDraw(pGL);
        GLHelper.enableTextures(pGL);
        GLHelper.enableTexCoordArray(pGL);
    }

    @Override // org.anddev.andengine.entity.shape.Shape, org.anddev.andengine.entity.Entity
    protected void doDraw(GL10 pGL, Camera pCamera) {
        this.mTextureRegion.onApply(pGL);
        super.doDraw(pGL, pCamera);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void finalize() throws Throwable {
        super.finalize();
        TextureRegionBuffer textureRegionBuffer = this.mTextureRegion.getTextureBuffer();
        if (textureRegionBuffer.isManaged()) {
            textureRegionBuffer.unloadFromActiveBufferObjectManager();
        }
    }

    private void initBlendFunction() {
        if (this.mTextureRegion.getTexture().getTextureOptions().mPreMultipyAlpha) {
            setBlendFunction(1, 771);
        }
    }
}
