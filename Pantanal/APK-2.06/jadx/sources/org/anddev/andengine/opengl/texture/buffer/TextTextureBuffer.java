package org.anddev.andengine.opengl.texture.buffer;

import org.anddev.andengine.opengl.buffer.BufferObject;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.Letter;
import org.anddev.andengine.opengl.util.FastFloatBuffer;

/* JADX INFO: loaded from: classes.dex */
public class TextTextureBuffer extends BufferObject {
    public TextTextureBuffer(int pCapacity, int pDrawType, boolean pManaged) {
        super(pCapacity, pDrawType, pManaged);
    }

    public synchronized void update(Font pFont, String[] pLines) {
        FastFloatBuffer textureFloatBuffer = getFloatBuffer();
        textureFloatBuffer.position(0);
        for (String line : pLines) {
            int lineLength = line.length();
            for (int j = 0; j < lineLength; j++) {
                Letter letter = pFont.getLetter(line.charAt(j));
                float letterTextureX = letter.mTextureX;
                float letterTextureY = letter.mTextureY;
                float letterTextureX2 = letterTextureX + letter.mTextureWidth;
                float letterTextureY2 = letterTextureY + letter.mTextureHeight;
                textureFloatBuffer.put(letterTextureX);
                textureFloatBuffer.put(letterTextureY);
                textureFloatBuffer.put(letterTextureX);
                textureFloatBuffer.put(letterTextureY2);
                textureFloatBuffer.put(letterTextureX2);
                textureFloatBuffer.put(letterTextureY2);
                textureFloatBuffer.put(letterTextureX2);
                textureFloatBuffer.put(letterTextureY2);
                textureFloatBuffer.put(letterTextureX2);
                textureFloatBuffer.put(letterTextureY);
                textureFloatBuffer.put(letterTextureX);
                textureFloatBuffer.put(letterTextureY);
            }
        }
        textureFloatBuffer.position(0);
        setHardwareBufferNeedsUpdate();
    }
}
