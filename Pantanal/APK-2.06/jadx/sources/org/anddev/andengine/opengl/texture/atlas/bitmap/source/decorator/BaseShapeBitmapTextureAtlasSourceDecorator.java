package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.Canvas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseShapeBitmapTextureAtlasSourceDecorator extends BaseBitmapTextureAtlasSourceDecorator {
    protected final IBitmapTextureAtlasSourceDecoratorShape mBitmapTextureAtlasSourceDecoratorShape;

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public abstract BaseShapeBitmapTextureAtlasSourceDecorator deepCopy();

    public BaseShapeBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource, pTextureAtlasSourceDecoratorOptions);
        this.mBitmapTextureAtlasSourceDecoratorShape = pBitmapTextureAtlasSourceDecoratorShape;
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator
    protected void onDecorateBitmap(Canvas pCanvas) {
        this.mBitmapTextureAtlasSourceDecoratorShape.onDecorateBitmap(pCanvas, this.mPaint, this.mTextureAtlasSourceDecoratorOptions == null ? BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions.DEFAULT : this.mTextureAtlasSourceDecoratorOptions);
    }
}
