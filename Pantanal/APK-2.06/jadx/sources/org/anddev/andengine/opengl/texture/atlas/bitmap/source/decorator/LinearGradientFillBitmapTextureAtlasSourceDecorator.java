package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;

/* JADX INFO: loaded from: classes.dex */
public class LinearGradientFillBitmapTextureAtlasSourceDecorator extends BaseShapeBitmapTextureAtlasSourceDecorator {
    protected final int[] mColors;
    protected final LinearGradientDirection mLinearGradientDirection;
    protected final float[] mPositions;

    public LinearGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor, LinearGradientDirection pLinearGradientDirection) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pFromColor, pToColor, pLinearGradientDirection, (BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions) null);
    }

    public LinearGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor, LinearGradientDirection pLinearGradientDirection, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, new int[]{pFromColor, pToColor}, (float[]) null, pLinearGradientDirection, pTextureAtlasSourceDecoratorOptions);
    }

    public LinearGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int[] pColors, float[] pPositions, LinearGradientDirection pLinearGradientDirection) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColors, pPositions, pLinearGradientDirection, (BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions) null);
    }

    public LinearGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int[] pColors, float[] pPositions, LinearGradientDirection pLinearGradientDirection, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pTextureAtlasSourceDecoratorOptions);
        this.mColors = pColors;
        this.mPositions = pPositions;
        this.mLinearGradientDirection = pLinearGradientDirection;
        this.mPaint.setStyle(Paint.Style.FILL);
        int right = pBitmapTextureAtlasSource.getWidth() - 1;
        int bottom = pBitmapTextureAtlasSource.getHeight() - 1;
        float fromX = pLinearGradientDirection.getFromX(right);
        float fromY = pLinearGradientDirection.getFromY(bottom);
        float toX = pLinearGradientDirection.getToX(right);
        float toY = pLinearGradientDirection.getToY(bottom);
        this.mPaint.setShader(new LinearGradient(fromX, fromY, toX, toY, pColors, pPositions, Shader.TileMode.CLAMP));
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseShapeBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public LinearGradientFillBitmapTextureAtlasSourceDecorator deepCopy() {
        return new LinearGradientFillBitmapTextureAtlasSourceDecorator(this.mBitmapTextureAtlasSource, this.mBitmapTextureAtlasSourceDecoratorShape, this.mColors, this.mPositions, this.mLinearGradientDirection, this.mTextureAtlasSourceDecoratorOptions);
    }

    public enum LinearGradientDirection {
        LEFT_TO_RIGHT(1, 0, 0, 0),
        RIGHT_TO_LEFT(0, 0, 1, 0),
        BOTTOM_TO_TOP(0, 1, 0, 0),
        TOP_TO_BOTTOM(0, 0, 0, 1),
        TOPLEFT_TO_BOTTOMRIGHT(0, 0, 1, 1),
        BOTTOMRIGHT_TO_TOPLEFT(1, 1, 0, 0),
        TOPRIGHT_TO_BOTTOMLEFT(1, 0, 0, 1),
        BOTTOMLEFT_TO_TOPRIGHT(0, 1, 1, 0);

        private final int mFromX;
        private final int mFromY;
        private final int mToX;
        private final int mToY;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static LinearGradientDirection[] valuesCustom() {
            LinearGradientDirection[] linearGradientDirectionArrValuesCustom = values();
            int length = linearGradientDirectionArrValuesCustom.length;
            LinearGradientDirection[] linearGradientDirectionArr = new LinearGradientDirection[length];
            System.arraycopy(linearGradientDirectionArrValuesCustom, 0, linearGradientDirectionArr, 0, length);
            return linearGradientDirectionArr;
        }

        LinearGradientDirection(int pFromX, int pFromY, int pToX, int pToY) {
            this.mFromX = pFromX;
            this.mFromY = pFromY;
            this.mToX = pToX;
            this.mToY = pToY;
        }

        final int getFromX(int pRight) {
            return this.mFromX * pRight;
        }

        final int getFromY(int pBottom) {
            return this.mFromY * pBottom;
        }

        final int getToX(int pRight) {
            return this.mToX * pRight;
        }

        final int getToY(int pBottom) {
            return this.mToY * pBottom;
        }
    }
}
