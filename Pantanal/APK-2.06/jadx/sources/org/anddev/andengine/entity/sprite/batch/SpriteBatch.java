package org.anddev.andengine.entity.sprite.batch;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.BaseSprite;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.region.BaseTextureRegion;
import org.anddev.andengine.opengl.texture.region.buffer.SpriteBatchTextureRegionBuffer;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.opengl.vertex.SpriteBatchVertexBuffer;

/* JADX INFO: loaded from: classes.dex */
public class SpriteBatch extends Entity {
    protected final int mCapacity;
    private int mDestinationBlendFunction;
    protected int mIndex;
    private int mSourceBlendFunction;
    private final SpriteBatchTextureRegionBuffer mSpriteBatchTextureRegionBuffer;
    private final SpriteBatchVertexBuffer mSpriteBatchVertexBuffer;
    protected final ITexture mTexture;
    private int mVertices;

    public SpriteBatch(ITexture pTexture, int pCapacity) {
        this(pTexture, pCapacity, new SpriteBatchVertexBuffer(pCapacity, 35044, true), new SpriteBatchTextureRegionBuffer(pCapacity, 35044, true));
    }

    public SpriteBatch(ITexture pTexture, int pCapacity, SpriteBatchVertexBuffer pSpriteBatchVertexBuffer, SpriteBatchTextureRegionBuffer pSpriteBatchTextureRegionBuffer) {
        this.mTexture = pTexture;
        this.mCapacity = pCapacity;
        this.mSpriteBatchVertexBuffer = pSpriteBatchVertexBuffer;
        this.mSpriteBatchTextureRegionBuffer = pSpriteBatchTextureRegionBuffer;
        initBlendFunction();
    }

    public void setBlendFunction(int pSourceBlendFunction, int pDestinationBlendFunction) {
        this.mSourceBlendFunction = pSourceBlendFunction;
        this.mDestinationBlendFunction = pDestinationBlendFunction;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int pIndex) {
        assertCapacity(pIndex);
        this.mIndex = pIndex;
        int vertexIndex = pIndex * 2 * 6;
        this.mSpriteBatchVertexBuffer.setIndex(vertexIndex);
        this.mSpriteBatchTextureRegionBuffer.setIndex(vertexIndex);
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void doDraw(GL10 pGL, Camera pCamera) {
        onInitDraw(pGL);
        begin(pGL);
        onApplyVertices(pGL);
        onApplyTextureRegion(pGL);
        drawVertices(pGL, pCamera);
        end(pGL);
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        initBlendFunction();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.mSpriteBatchVertexBuffer.isManaged()) {
            this.mSpriteBatchVertexBuffer.unloadFromActiveBufferObjectManager();
        }
        if (this.mSpriteBatchTextureRegionBuffer.isManaged()) {
            this.mSpriteBatchTextureRegionBuffer.unloadFromActiveBufferObjectManager();
        }
    }

    protected void begin(GL10 pGL) {
    }

    protected void end(GL10 pGL) {
    }

    public void draw(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight) {
        assertCapacity();
        assertTexture(pTextureRegion);
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void drawWithoutChecks(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight) {
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void draw(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation) {
        assertCapacity();
        assertTexture(pTextureRegion);
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight, pRotation);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void drawWithoutChecks(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation) {
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight, pRotation);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void draw(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY) {
        assertCapacity();
        assertTexture(pTextureRegion);
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight, pScaleX, pScaleY);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void drawWithoutChecks(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pScaleX, float pScaleY) {
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight, pScaleX, pScaleY);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void draw(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY) {
        assertCapacity();
        assertTexture(pTextureRegion);
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight, pRotation, pScaleX, pScaleY);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void drawWithoutChecks(BaseTextureRegion pTextureRegion, float pX, float pY, float pWidth, float pHeight, float pRotation, float pScaleX, float pScaleY) {
        this.mSpriteBatchVertexBuffer.add(pX, pY, pWidth, pHeight, pRotation, pScaleX, pScaleY);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void draw(BaseTextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4) {
        assertCapacity();
        assertTexture(pTextureRegion);
        this.mSpriteBatchVertexBuffer.addInner(pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void drawWithoutChecks(BaseTextureRegion pTextureRegion, float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4) {
        this.mSpriteBatchVertexBuffer.addInner(pX1, pY1, pX2, pY2, pX3, pY3, pX4, pY4);
        this.mSpriteBatchTextureRegionBuffer.add(pTextureRegion);
        this.mIndex++;
    }

    public void draw(BaseSprite pBaseSprite) {
        if (pBaseSprite.isVisible()) {
            assertCapacity();
            BaseTextureRegion textureRegion = pBaseSprite.getTextureRegion();
            assertTexture(textureRegion);
            if (pBaseSprite.getRotation() == BitmapDescriptorFactory.HUE_RED && !pBaseSprite.isScaled()) {
                this.mSpriteBatchVertexBuffer.add(pBaseSprite.getX(), pBaseSprite.getY(), pBaseSprite.getWidth(), pBaseSprite.getHeight());
            } else {
                this.mSpriteBatchVertexBuffer.add(pBaseSprite.getWidth(), pBaseSprite.getHeight(), pBaseSprite.getLocalToParentTransformation());
            }
            this.mSpriteBatchTextureRegionBuffer.add(textureRegion);
            this.mIndex++;
        }
    }

    public void drawWithoutChecks(BaseSprite pBaseSprite) {
        if (pBaseSprite.isVisible()) {
            BaseTextureRegion textureRegion = pBaseSprite.getTextureRegion();
            if (pBaseSprite.getRotation() == BitmapDescriptorFactory.HUE_RED && !pBaseSprite.isScaled()) {
                this.mSpriteBatchVertexBuffer.add(pBaseSprite.getX(), pBaseSprite.getY(), pBaseSprite.getWidth(), pBaseSprite.getHeight());
            } else {
                this.mSpriteBatchVertexBuffer.add(pBaseSprite.getWidth(), pBaseSprite.getHeight(), pBaseSprite.getLocalToParentTransformation());
            }
            this.mSpriteBatchTextureRegionBuffer.add(textureRegion);
            this.mIndex++;
        }
    }

    public void submit() {
        onSubmit();
    }

    private void onSubmit() {
        this.mVertices = this.mIndex * 6;
        this.mSpriteBatchVertexBuffer.submit();
        this.mSpriteBatchTextureRegionBuffer.submit();
        this.mIndex = 0;
        this.mSpriteBatchVertexBuffer.setIndex(0);
        this.mSpriteBatchTextureRegionBuffer.setIndex(0);
    }

    private void initBlendFunction() {
        if (this.mTexture.getTextureOptions().mPreMultipyAlpha) {
            setBlendFunction(1, 771);
        }
    }

    protected void onInitDraw(GL10 pGL) {
        GLHelper.setColor(pGL, this.mRed, this.mGreen, this.mBlue, this.mAlpha);
        GLHelper.enableVertexArray(pGL);
        GLHelper.blendFunction(pGL, this.mSourceBlendFunction, this.mDestinationBlendFunction);
        GLHelper.enableTextures(pGL);
        GLHelper.enableTexCoordArray(pGL);
    }

    protected void onApplyVertices(GL10 pGL) {
        if (GLHelper.EXTENSIONS_VERTEXBUFFEROBJECTS) {
            GL11 gl11 = (GL11) pGL;
            this.mSpriteBatchVertexBuffer.selectOnHardware(gl11);
            GLHelper.vertexZeroPointer(gl11);
            return;
        }
        GLHelper.vertexPointer(pGL, this.mSpriteBatchVertexBuffer.getFloatBuffer());
    }

    private void onApplyTextureRegion(GL10 pGL) {
        if (GLHelper.EXTENSIONS_VERTEXBUFFEROBJECTS) {
            GL11 gl11 = (GL11) pGL;
            this.mSpriteBatchTextureRegionBuffer.selectOnHardware(gl11);
            this.mTexture.bind(pGL);
            GLHelper.texCoordZeroPointer(gl11);
            return;
        }
        this.mTexture.bind(pGL);
        GLHelper.texCoordPointer(pGL, this.mSpriteBatchTextureRegionBuffer.getFloatBuffer());
    }

    private void drawVertices(GL10 pGL, Camera pCamera) {
        pGL.glDrawArrays(4, 0, this.mVertices);
    }

    private void assertCapacity(int pIndex) {
        if (pIndex >= this.mCapacity) {
            throw new IllegalStateException("This supplied pIndex: '" + pIndex + "' is exceeding the capacity: '" + this.mCapacity + "' of this SpriteBatch!");
        }
    }

    private void assertCapacity() {
        if (this.mIndex == this.mCapacity) {
            throw new IllegalStateException("This SpriteBatch has already reached its capacity (" + this.mCapacity + ") !");
        }
    }

    protected void assertTexture(BaseTextureRegion pTextureRegion) {
        if (pTextureRegion.getTexture() != this.mTexture) {
            throw new IllegalArgumentException("The supplied Texture does match the Texture of this SpriteBatch!");
        }
    }
}
