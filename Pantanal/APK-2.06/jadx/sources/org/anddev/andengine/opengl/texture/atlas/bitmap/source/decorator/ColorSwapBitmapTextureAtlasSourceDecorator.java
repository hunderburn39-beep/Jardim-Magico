package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.AvoidXfermode;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;

/* JADX INFO: loaded from: classes.dex */
public class ColorSwapBitmapTextureAtlasSourceDecorator extends BaseShapeBitmapTextureAtlasSourceDecorator {
    private static final int TOLERANCE_DEFAULT = 0;
    protected final int mColorKeyColor;
    protected final int mColorSwapColor;
    protected final int mTolerance;

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColor, int pColorSwapColor) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor, 0, pColorSwapColor, null);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColor, int pColorSwapColor, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor, 0, pColorSwapColor, pTextureAtlasSourceDecoratorOptions);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColor, int pTolerance, int pColorSwapColor) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColorKeyColor, pTolerance, pColorSwapColor, null);
    }

    public ColorSwapBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pColorKeyColor, int pTolerance, int pColorSwapColor, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pTextureAtlasSourceDecoratorOptions);
        this.mColorKeyColor = pColorKeyColor;
        this.mTolerance = pTolerance;
        this.mColorSwapColor = pColorSwapColor;
        this.mPaint.setXfermode(new AvoidXfermode(pColorKeyColor, pTolerance, AvoidXfermode.Mode.TARGET));
        this.mPaint.setColor(pColorSwapColor);
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseShapeBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public ColorSwapBitmapTextureAtlasSourceDecorator deepCopy() {
        return new ColorSwapBitmapTextureAtlasSourceDecorator(this.mBitmapTextureAtlasSource, this.mBitmapTextureAtlasSourceDecoratorShape, this.mColorKeyColor, this.mTolerance, this.mColorSwapColor, this.mTextureAtlasSourceDecoratorOptions);
    }
}
