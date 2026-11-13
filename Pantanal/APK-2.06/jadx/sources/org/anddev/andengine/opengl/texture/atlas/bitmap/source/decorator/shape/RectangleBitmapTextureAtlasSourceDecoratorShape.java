package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

/* JADX INFO: loaded from: classes.dex */
public class RectangleBitmapTextureAtlasSourceDecoratorShape implements IBitmapTextureAtlasSourceDecoratorShape {
    private static RectangleBitmapTextureAtlasSourceDecoratorShape sDefaultInstance;

    public static RectangleBitmapTextureAtlasSourceDecoratorShape getDefaultInstance() {
        if (sDefaultInstance == null) {
            sDefaultInstance = new RectangleBitmapTextureAtlasSourceDecoratorShape();
        }
        return sDefaultInstance;
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape
    public void onDecorateBitmap(Canvas pCanvas, Paint pPaint, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pDecoratorOptions) {
        float left = pDecoratorOptions.getInsetLeft();
        float top = pDecoratorOptions.getInsetTop();
        float right = pCanvas.getWidth() - pDecoratorOptions.getInsetRight();
        float bottom = pCanvas.getHeight() - pDecoratorOptions.getInsetBottom();
        pCanvas.drawRect(left, top, right, bottom, pPaint);
    }
}
