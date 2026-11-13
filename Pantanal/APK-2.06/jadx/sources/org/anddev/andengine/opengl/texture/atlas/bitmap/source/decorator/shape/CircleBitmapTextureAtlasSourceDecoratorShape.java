package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

/* JADX INFO: loaded from: classes.dex */
public class CircleBitmapTextureAtlasSourceDecoratorShape implements IBitmapTextureAtlasSourceDecoratorShape {
    private static CircleBitmapTextureAtlasSourceDecoratorShape sDefaultInstance;

    public static CircleBitmapTextureAtlasSourceDecoratorShape getDefaultInstance() {
        if (sDefaultInstance == null) {
            sDefaultInstance = new CircleBitmapTextureAtlasSourceDecoratorShape();
        }
        return sDefaultInstance;
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape
    public void onDecorateBitmap(Canvas pCanvas, Paint pPaint, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pDecoratorOptions) {
        float width = (pCanvas.getWidth() - pDecoratorOptions.getInsetLeft()) - pDecoratorOptions.getInsetRight();
        float height = (pCanvas.getHeight() - pDecoratorOptions.getInsetTop()) - pDecoratorOptions.getInsetBottom();
        float centerX = ((pCanvas.getWidth() + pDecoratorOptions.getInsetLeft()) - pDecoratorOptions.getInsetRight()) * 0.5f;
        float centerY = ((pCanvas.getHeight() + pDecoratorOptions.getInsetTop()) - pDecoratorOptions.getInsetBottom()) * 0.5f;
        float radius = Math.min(width * 0.5f, height * 0.5f);
        pCanvas.drawCircle(centerX, centerY, radius, pPaint);
    }
}
