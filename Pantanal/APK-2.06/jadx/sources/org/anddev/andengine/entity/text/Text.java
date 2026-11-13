package org.anddev.andengine.entity.text;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.buffer.TextTextureBuffer;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.opengl.vertex.TextVertexBuffer;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class Text extends RectangularShape {
    protected final int mCharactersMaximum;
    private final Font mFont;
    private String[] mLines;
    private int mMaximumLineWidth;
    private String mText;
    private final TextTextureBuffer mTextTextureBuffer;
    protected final int mVertexCount;
    private int[] mWidths;

    public Text(float pX, float pY, Font pFont, String pText) {
        this(pX, pY, pFont, pText, HorizontalAlign.LEFT);
    }

    public Text(float pX, float pY, Font pFont, String pText, HorizontalAlign pHorizontalAlign) {
        this(pX, pY, pFont, pText, pHorizontalAlign, pText.length() - StringUtils.countOccurrences(pText, '\n'));
    }

    protected Text(float pX, float pY, Font pFont, String pText, HorizontalAlign pHorizontalAlign, int pCharactersMaximum) {
        super(pX, pY, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, new TextVertexBuffer(pCharactersMaximum, pHorizontalAlign, 35044, true));
        this.mCharactersMaximum = pCharactersMaximum;
        this.mVertexCount = this.mCharactersMaximum * 6;
        this.mTextTextureBuffer = new TextTextureBuffer(this.mVertexCount * 2, 35044, true);
        this.mFont = pFont;
        updateText(pText);
        initBlendFunction();
    }

    protected void updateText(String pText) {
        this.mText = pText;
        Font font = this.mFont;
        this.mLines = StringUtils.split(this.mText, '\n', this.mLines);
        String[] lines = this.mLines;
        int lineCount = lines.length;
        boolean widthsReusable = this.mWidths != null && this.mWidths.length == lineCount;
        if (!widthsReusable) {
            this.mWidths = new int[lineCount];
        }
        int[] widths = this.mWidths;
        int maximumLineWidth = 0;
        for (int i = lineCount - 1; i >= 0; i--) {
            widths[i] = font.getStringWidth(lines[i]);
            maximumLineWidth = Math.max(maximumLineWidth, widths[i]);
        }
        this.mMaximumLineWidth = maximumLineWidth;
        this.mWidth = this.mMaximumLineWidth;
        float width = this.mWidth;
        this.mBaseWidth = width;
        this.mHeight = (font.getLineHeight() * lineCount) + ((lineCount - 1) * font.getLineGap());
        float height = this.mHeight;
        this.mBaseHeight = height;
        this.mRotationCenterX = width * 0.5f;
        this.mRotationCenterY = height * 0.5f;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
        this.mTextTextureBuffer.update(font, lines);
        updateVertexBuffer();
    }

    public String getText() {
        return this.mText;
    }

    public int getCharactersMaximum() {
        return this.mCharactersMaximum;
    }

    @Override // org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape
    public TextVertexBuffer getVertexBuffer() {
        return (TextVertexBuffer) this.mVertexBuffer;
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onInitDraw(GL10 pGL) {
        super.onInitDraw(pGL);
        GLHelper.enableTextures(pGL);
        GLHelper.enableTexCoordArray(pGL);
    }

    @Override // org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape
    protected void drawVertices(GL10 pGL, Camera pCamera) {
        pGL.glDrawArrays(4, 0, this.mVertexCount);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void onUpdateVertexBuffer() {
        Font font = this.mFont;
        if (font != null) {
            getVertexBuffer().update(font, this.mMaximumLineWidth, this.mWidths, this.mLines);
        }
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onApplyTransformations(GL10 pGL) {
        super.onApplyTransformations(pGL);
        applyTexture(pGL);
    }

    @Override // org.anddev.andengine.entity.shape.Shape
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.mTextTextureBuffer.isManaged()) {
            this.mTextTextureBuffer.unloadFromActiveBufferObjectManager();
        }
    }

    private void initBlendFunction() {
        if (this.mFont.getTexture().getTextureOptions().mPreMultipyAlpha) {
            setBlendFunction(1, 771);
        }
    }

    private void applyTexture(GL10 pGL) {
        if (GLHelper.EXTENSIONS_VERTEXBUFFEROBJECTS) {
            GL11 gl11 = (GL11) pGL;
            this.mTextTextureBuffer.selectOnHardware(gl11);
            this.mFont.getTexture().bind(pGL);
            GLHelper.texCoordZeroPointer(gl11);
            return;
        }
        this.mFont.getTexture().bind(pGL);
        GLHelper.texCoordPointer(pGL, this.mTextTextureBuffer.getFloatBuffer());
    }
}
