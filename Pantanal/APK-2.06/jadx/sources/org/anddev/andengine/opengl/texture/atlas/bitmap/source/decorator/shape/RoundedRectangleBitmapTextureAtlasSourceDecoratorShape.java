package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

/* JADX INFO: loaded from: classes.dex */
public class RoundedRectangleBitmapTextureAtlasSourceDecoratorShape implements IBitmapTextureAtlasSourceDecoratorShape {
    private static final float CORNER_RADIUS_DEFAULT = 1.0f;
    private static RoundedRectangleBitmapTextureAtlasSourceDecoratorShape sDefaultInstance;
    private final float mCornerRadiusX;
    private final float mCornerRadiusY;
    private final RectF mRectF;

    public RoundedRectangleBitmapTextureAtlasSourceDecoratorShape() {
        this(1.0f, 1.0f);
    }

    public RoundedRectangleBitmapTextureAtlasSourceDecoratorShape(float pCornerRadiusX, float pCornerRadiusY) {
        this.mRectF = new RectF();
        this.mCornerRadiusX = pCornerRadiusX;
        this.mCornerRadiusY = pCornerRadiusY;
    }

    public static RoundedRectangleBitmapTextureAtlasSourceDecoratorShape getDefaultInstance() {
        if (sDefaultInstance == null) {
            sDefaultInstance = new RoundedRectangleBitmapTextureAtlasSourceDecoratorShape();
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
        pCanvas.drawRoundRect(this.mRectF, this.mCornerRadiusX, this.mCornerRadiusY, pPaint);
    }
}
