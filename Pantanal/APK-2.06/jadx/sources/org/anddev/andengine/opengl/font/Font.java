package org.anddev.andengine.opengl.font;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.util.FloatMath;
import android.util.SparseArray;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.opengl.texture.ITexture;

/* JADX INFO: loaded from: classes.dex */
public class Font {
    protected static final int LETTER_EXTRA_WIDTH = 10;
    protected static final float LETTER_LEFT_OFFSET = 0.0f;
    protected static final int PADDING = 1;
    private final Paint mBackgroundPaint;
    protected final Paint.FontMetrics mFontMetrics;
    private final int mLineGap;
    private final int mLineHeight;
    private final ITexture mTexture;
    private final float mTextureHeight;
    private final float mTextureWidth;
    private int mCurrentTextureX = 0;
    private int mCurrentTextureY = 0;
    private final SparseArray<Letter> mManagedCharacterToLetterMap = new SparseArray<>();
    private final ArrayList<Letter> mLettersPendingToBeDrawnToTexture = new ArrayList<>();
    private final Size mCreateLetterTemporarySize = new Size();
    private final Rect mGetLetterBitmapTemporaryRect = new Rect();
    private final Rect mGetStringWidthTemporaryRect = new Rect();
    private final Rect mGetLetterBoundsTemporaryRect = new Rect();
    private final float[] mTemporaryTextWidthFetchers = new float[1];
    protected final Canvas mCanvas = new Canvas();
    protected final Paint mPaint = new Paint();

    public Font(ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor) {
        this.mTexture = pTexture;
        this.mTextureWidth = pTexture.getWidth();
        this.mTextureHeight = pTexture.getHeight();
        this.mPaint.setTypeface(pTypeface);
        this.mPaint.setColor(pColor);
        this.mPaint.setTextSize(pSize);
        this.mPaint.setAntiAlias(pAntiAlias);
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setColor(0);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mFontMetrics = this.mPaint.getFontMetrics();
        this.mLineHeight = ((int) FloatMath.ceil(Math.abs(this.mFontMetrics.ascent) + Math.abs(this.mFontMetrics.descent))) + 2;
        this.mLineGap = (int) FloatMath.ceil(this.mFontMetrics.leading);
    }

    public int getLineGap() {
        return this.mLineGap;
    }

    public int getLineHeight() {
        return this.mLineHeight;
    }

    public ITexture getTexture() {
        return this.mTexture;
    }

    public synchronized void reload() {
        ArrayList<Letter> lettersPendingToBeDrawnToTexture = this.mLettersPendingToBeDrawnToTexture;
        SparseArray<Letter> managedCharacterToLetterMap = this.mManagedCharacterToLetterMap;
        for (int i = managedCharacterToLetterMap.size() - 1; i >= 0; i--) {
            lettersPendingToBeDrawnToTexture.add(managedCharacterToLetterMap.valueAt(i));
        }
    }

    private int getLetterAdvance(char pCharacter) {
        this.mPaint.getTextWidths(String.valueOf(pCharacter), this.mTemporaryTextWidthFetchers);
        return (int) FloatMath.ceil(this.mTemporaryTextWidthFetchers[0]);
    }

    private Bitmap getLetterBitmap(char pCharacter) {
        Rect getLetterBitmapTemporaryRect = this.mGetLetterBitmapTemporaryRect;
        String characterAsString = String.valueOf(pCharacter);
        this.mPaint.getTextBounds(characterAsString, 0, 1, getLetterBitmapTemporaryRect);
        getLetterBitmapTemporaryRect.right += 2;
        int lineHeight = getLineHeight();
        Bitmap bitmap = Bitmap.createBitmap(getLetterBitmapTemporaryRect.width() == 0 ? 3 : getLetterBitmapTemporaryRect.width() + 10, lineHeight, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmap);
        this.mCanvas.drawRect(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight(), this.mBackgroundPaint);
        drawCharacterString(characterAsString);
        return bitmap;
    }

    protected void drawCharacterString(String pCharacterAsString) {
        this.mCanvas.drawText(pCharacterAsString, 1.0f, (-this.mFontMetrics.ascent) + 1.0f, this.mPaint);
    }

    public int getStringWidth(String pText) {
        this.mPaint.getTextBounds(pText, 0, pText.length(), this.mGetStringWidthTemporaryRect);
        return this.mGetStringWidthTemporaryRect.width();
    }

    private void getLetterBounds(char pCharacter, Size pSize) {
        this.mPaint.getTextBounds(String.valueOf(pCharacter), 0, 1, this.mGetLetterBoundsTemporaryRect);
        pSize.set(this.mGetLetterBoundsTemporaryRect.width() + 10 + 2, getLineHeight());
    }

    public void prepareLetters(char... pCharacters) {
        for (char character : pCharacters) {
            getLetter(character);
        }
    }

    public synchronized Letter getLetter(char pCharacter) {
        Letter letter;
        SparseArray<Letter> managedCharacterToLetterMap = this.mManagedCharacterToLetterMap;
        letter = managedCharacterToLetterMap.get(pCharacter);
        if (letter == null) {
            letter = createLetter(pCharacter);
            this.mLettersPendingToBeDrawnToTexture.add(letter);
            managedCharacterToLetterMap.put(pCharacter, letter);
        }
        return letter;
    }

    private Letter createLetter(char pCharacter) {
        float textureWidth = this.mTextureWidth;
        float textureHeight = this.mTextureHeight;
        Size createLetterTemporarySize = this.mCreateLetterTemporarySize;
        getLetterBounds(pCharacter, createLetterTemporarySize);
        float letterWidth = createLetterTemporarySize.getWidth();
        float letterHeight = createLetterTemporarySize.getHeight();
        if (this.mCurrentTextureX + letterWidth >= textureWidth) {
            this.mCurrentTextureX = 0;
            this.mCurrentTextureY += getLineGap() + getLineHeight();
        }
        float letterTextureX = this.mCurrentTextureX / textureWidth;
        float letterTextureY = this.mCurrentTextureY / textureHeight;
        float letterTextureWidth = letterWidth / textureWidth;
        float letterTextureHeight = letterHeight / textureHeight;
        Letter letter = new Letter(pCharacter, getLetterAdvance(pCharacter), (int) letterWidth, (int) letterHeight, letterTextureX, letterTextureY, letterTextureWidth, letterTextureHeight);
        this.mCurrentTextureX = (int) (this.mCurrentTextureX + letterWidth);
        return letter;
    }

    public synchronized void update(GL10 pGL) {
        ArrayList<Letter> lettersPendingToBeDrawnToTexture = this.mLettersPendingToBeDrawnToTexture;
        if (lettersPendingToBeDrawnToTexture.size() > 0) {
            this.mTexture.bind(pGL);
            float textureWidth = this.mTextureWidth;
            float textureHeight = this.mTextureHeight;
            for (int i = lettersPendingToBeDrawnToTexture.size() - 1; i >= 0; i--) {
                Letter letter = lettersPendingToBeDrawnToTexture.get(i);
                Bitmap bitmap = getLetterBitmap(letter.mCharacter);
                GLUtils.texSubImage2D(3553, 0, (int) (letter.mTextureX * textureWidth), (int) (letter.mTextureY * textureHeight), bitmap);
                bitmap.recycle();
            }
            lettersPendingToBeDrawnToTexture.clear();
            System.gc();
        }
    }
}
