package org.anddev.andengine.opengl.texture.region;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.region.buffer.TextureRegionBuffer;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTextureRegion {
    protected int mHeight;
    protected final ITexture mTexture;
    protected int mTexturePositionX;
    protected int mTexturePositionY;
    protected final TextureRegionBuffer mTextureRegionBuffer = new TextureRegionBuffer(this, 35044, true);
    protected int mWidth;

    protected abstract BaseTextureRegion deepCopy() throws IModifier.DeepCopyNotSupportedException;

    public abstract float getTextureCoordinateX1();

    public abstract float getTextureCoordinateX2();

    public abstract float getTextureCoordinateY1();

    public abstract float getTextureCoordinateY2();

    public BaseTextureRegion(ITexture pTexture, int pTexturePositionX, int pTexturePositionY, int pWidth, int pHeight) {
        this.mTexture = pTexture;
        this.mTexturePositionX = pTexturePositionX;
        this.mTexturePositionY = pTexturePositionY;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        initTextureBuffer();
    }

    protected void initTextureBuffer() {
        updateTextureRegionBuffer();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setWidth(int pWidth) {
        this.mWidth = pWidth;
        updateTextureRegionBuffer();
    }

    public void setHeight(int pHeight) {
        this.mHeight = pHeight;
        updateTextureRegionBuffer();
    }

    public void setTexturePosition(int pTexturePositionX, int pTexturePositionY) {
        this.mTexturePositionX = pTexturePositionX;
        this.mTexturePositionY = pTexturePositionY;
        updateTextureRegionBuffer();
    }

    public int getTexturePositionX() {
        return this.mTexturePositionX;
    }

    public int getTexturePositionY() {
        return this.mTexturePositionY;
    }

    public ITexture getTexture() {
        return this.mTexture;
    }

    public TextureRegionBuffer getTextureBuffer() {
        return this.mTextureRegionBuffer;
    }

    public boolean isFlippedHorizontal() {
        return this.mTextureRegionBuffer.isFlippedHorizontal();
    }

    public void setFlippedHorizontal(boolean pFlippedHorizontal) {
        this.mTextureRegionBuffer.setFlippedHorizontal(pFlippedHorizontal);
    }

    public boolean isFlippedVertical() {
        return this.mTextureRegionBuffer.isFlippedVertical();
    }

    public void setFlippedVertical(boolean pFlippedVertical) {
        this.mTextureRegionBuffer.setFlippedVertical(pFlippedVertical);
    }

    public boolean isTextureRegionBufferManaged() {
        return this.mTextureRegionBuffer.isManaged();
    }

    public void setTextureRegionBufferManaged(boolean pTextureRegionBufferManaged) {
        this.mTextureRegionBuffer.setManaged(pTextureRegionBufferManaged);
    }

    protected void updateTextureRegionBuffer() {
        this.mTextureRegionBuffer.update();
    }

    public void onApply(GL10 pGL) {
        this.mTexture.bind(pGL);
        if (GLHelper.EXTENSIONS_VERTEXBUFFEROBJECTS) {
            GL11 gl11 = (GL11) pGL;
            this.mTextureRegionBuffer.selectOnHardware(gl11);
            GLHelper.texCoordZeroPointer(gl11);
            return;
        }
        GLHelper.texCoordPointer(pGL, this.mTextureRegionBuffer.getFloatBuffer());
    }
}
