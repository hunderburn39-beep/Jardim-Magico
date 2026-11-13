package org.anddev.andengine.opengl.font;

/* JADX INFO: loaded from: classes.dex */
public class Letter {
    public final int mAdvance;
    public final char mCharacter;
    public final int mHeight;
    public final float mTextureHeight;
    public final float mTextureWidth;
    public final float mTextureX;
    public final float mTextureY;
    public final int mWidth;

    Letter(char pCharacter, int pAdvance, int pWidth, int pHeight, float pTextureX, float pTextureY, float pTextureWidth, float pTextureHeight) {
        this.mCharacter = pCharacter;
        this.mAdvance = pAdvance;
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        this.mTextureX = pTextureX;
        this.mTextureY = pTextureY;
        this.mTextureWidth = pTextureWidth;
        this.mTextureHeight = pTextureHeight;
    }

    public int hashCode() {
        int result = this.mCharacter + 31;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Letter other = (Letter) obj;
            return this.mCharacter == other.mCharacter;
        }
        return false;
    }
}
