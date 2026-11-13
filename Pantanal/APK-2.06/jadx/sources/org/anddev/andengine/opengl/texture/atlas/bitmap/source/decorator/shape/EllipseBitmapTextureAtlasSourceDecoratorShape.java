package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

/* JADX INFO: loaded from: classes.dex */
public class EllipseBitmapTextureAtlasSourceDecoratorShape implements IBitmapTextureAtlasSourceDecoratorShape {
    private static EllipseBitmapTextureAtlasSourceDecoratorShape sDefaultInstance;
    private final RectF mRectF = new RectF();

    public static EllipseBitmapTextureAtlasSourceDecoratorShape getDefaultInstance() {
        if (sDefaultInstance == null) {
            sDefaultInstance = new EllipseBitmapTextureAtlasSourceDecoratorShape();
        }
        return sDefaultInstance;
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape
    public void onDecorateBitmap(Canvas pCanvas, Paint pPaint, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pDecoratorOptions) {
        float left = pDecoratorOptions.getInsetLeft();
        float top = pDecoratorOptions.getInsetTop();
        float right = pCanvas.getWidth() - pDecoratorOptions.getInsetRight();
        float bottom = pCanvas.getHeight() - pDecoratorOptions.getInsetBottom();
        this.mRectF.set(left, top, right, bottom);
        pCanvas.drawOval(this.mRectF, pPaint);
    }
}
