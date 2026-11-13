package org.anddev.andengine.entity.text;

import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class ChangeableText extends Text {
    private static final String ELLIPSIS = "...";
    private static final int ELLIPSIS_CHARACTER_COUNT = ELLIPSIS.length();
    private int mCharacterCountCurrentText;

    public ChangeableText(float pX, float pY, Font pFont, String pText) {
        this(pX, pY, pFont, pText, pText.length() - StringUtils.countOccurrences(pText, '\n'));
    }

    public ChangeableText(float pX, float pY, Font pFont, String pText, int pCharactersMaximum) {
        this(pX, pY, pFont, pText, HorizontalAlign.LEFT, pCharactersMaximum);
    }

    public ChangeableText(float pX, float pY, Font pFont, String pText, HorizontalAlign pHorizontalAlign, int pCharactersMaximum) {
        super(pX, pY, pFont, pText, pHorizontalAlign, pCharactersMaximum);
        this.mCharacterCountCurrentText = pText.length() - StringUtils.countOccurrences(pText, '\n');
    }

    public void setText(String pText) {
        setText(pText, false);
    }

    public void setText(String pText, boolean pAllowEllipsis) {
        int textCharacterCount = pText.length() - StringUtils.countOccurrences(pText, '\n');
        if (textCharacterCount > this.mCharactersMaximum) {
            if (pAllowEllipsis && this.mCharactersMaximum > ELLIPSIS_CHARACTER_COUNT) {
                updateText(pText.substring(0, this.mCharactersMaximum - ELLIPSIS_CHARACTER_COUNT).concat(ELLIPSIS));
            } else {
                updateText(pText.substring(0, this.mCharactersMaximum));
            }
            this.mCharacterCountCurrentText = this.mCharactersMaximum;
            return;
        }
        updateText(pText);
        this.mCharacterCountCurrentText = textCharacterCount;
    }

    @Override // org.anddev.andengine.entity.text.Text, org.anddev.andengine.entity.shape.RectangularShape, org.anddev.andengine.entity.shape.Shape
    protected void drawVertices(GL10 pGL, Camera pCamera) {
        pGL.glDrawArrays(4, 0, this.mCharacterCountCurrentText * 6);
    }
}
