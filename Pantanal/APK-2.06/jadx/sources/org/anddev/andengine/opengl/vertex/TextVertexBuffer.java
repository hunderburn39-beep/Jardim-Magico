package org.anddev.andengine.opengl.vertex;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.Letter;
import org.anddev.andengine.opengl.util.FastFloatBuffer;
import org.anddev.andengine.util.HorizontalAlign;

/* JADX INFO: loaded from: classes.dex */
public class TextVertexBuffer extends VertexBuffer {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign = null;
    public static final int VERTICES_PER_CHARACTER = 6;
    private final HorizontalAlign mHorizontalAlign;

    static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign() {
        int[] iArr = $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign;
        if (iArr == null) {
            iArr = new int[HorizontalAlign.valuesCustom().length];
            try {
                iArr[HorizontalAlign.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[HorizontalAlign.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[HorizontalAlign.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign = iArr;
        }
        return iArr;
    }

    public TextVertexBuffer(int pCharacterCount, HorizontalAlign pHorizontalAlign, int pDrawType, boolean pManaged) {
        super(pCharacterCount * 12, pDrawType, pManaged);
        this.mHorizontalAlign = pHorizontalAlign;
    }

    public synchronized void update(Font font, int pMaximumLineWidth, int[] pWidths, String[] pLines) {
        int lineX;
        int[] bufferData = this.mBufferData;
        int i = 0;
        int lineHeight = font.getLineHeight();
        int lineCount = pLines.length;
        int lineIndex = 0;
        while (lineIndex < lineCount) {
            String line = pLines[lineIndex];
            switch ($SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign()[this.mHorizontalAlign.ordinal()]) {
                case 2:
                    lineX = (pMaximumLineWidth - pWidths[lineIndex]) >> 1;
                    break;
                case 3:
                    lineX = pMaximumLineWidth - pWidths[lineIndex];
                    break;
                default:
                    lineX = 0;
                    break;
            }
            int lineY = lineIndex * (font.getLineHeight() + font.getLineGap());
            int lineYBits = Float.floatToRawIntBits(lineY);
            int lineLength = line.length();
            int i2 = i;
            for (int letterIndex = 0; letterIndex < lineLength; letterIndex++) {
                Letter letter = font.getLetter(line.charAt(letterIndex));
                int lineY2 = lineY + lineHeight;
                int lineX2 = lineX + letter.mWidth;
                int lineXBits = Float.floatToRawIntBits(lineX);
                int lineX2Bits = Float.floatToRawIntBits(lineX2);
                int lineY2Bits = Float.floatToRawIntBits(lineY2);
                int i3 = i2 + 1;
                bufferData[i2] = lineXBits;
                int i4 = i3 + 1;
                bufferData[i3] = lineYBits;
                int i5 = i4 + 1;
                bufferData[i4] = lineXBits;
                int i6 = i5 + 1;
                bufferData[i5] = lineY2Bits;
                int i7 = i6 + 1;
                bufferData[i6] = lineX2Bits;
                int i8 = i7 + 1;
                bufferData[i7] = lineY2Bits;
                int i9 = i8 + 1;
                bufferData[i8] = lineX2Bits;
                int i10 = i9 + 1;
                bufferData[i9] = lineY2Bits;
                int i11 = i10 + 1;
                bufferData[i10] = lineX2Bits;
                int i12 = i11 + 1;
                bufferData[i11] = lineYBits;
                int i13 = i12 + 1;
                bufferData[i12] = lineXBits;
                i2 = i13 + 1;
                bufferData[i13] = lineYBits;
                lineX += letter.mAdvance;
            }
            lineIndex++;
            i = i2;
        }
        FastFloatBuffer vertexFloatBuffer = this.mFloatBuffer;
        vertexFloatBuffer.position(0);
        vertexFloatBuffer.put(bufferData);
        vertexFloatBuffer.position(0);
        super.setHardwareBufferNeedsUpdate();
    }
}
