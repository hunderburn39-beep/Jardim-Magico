package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

/* JADX INFO: loaded from: classes.dex */
public class ArcBitmapTextureAtlasSourceDecoratorShape implements IBitmapTextureAtlasSourceDecoratorShape {
    private static final float STARTANGLE_DEFAULT = 0.0f;
    private static final float SWEEPANGLE_DEFAULT = 360.0f;
    private static final boolean USECENTER_DEFAULT = true;
    private static ArcBitmapTextureAtlasSourceDecoratorShape sDefaultInstance;
    private final RectF mRectF;
    private final float mStartAngle;
    private final float mSweepAngle;
    private final boolean mUseCenter;

    public ArcBitmapTextureAtlasSourceDecoratorShape() {
        this(0.0f, SWEEPANGLE_DEFAULT, true);
    }

    public ArcBitmapTextureAtlasSourceDecoratorShape(float pStartAngle, float pSweepAngle, boolean pUseCenter) {
        this.mRectF = new RectF();
        this.mStartAngle = pStartAngle;
        this.mSweepAngle = pSweepAngle;
        this.mUseCenter = pUseCenter;
    }

    @Deprecated
    public static ArcBitmapTextureAtlasSourceDecoratorShape getDefaultInstance() {
        if (sDefaultInstance == null) {
            sDefaultInstance = new ArcBitmapTextureAtlasSourceDecoratorShape();
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
        pCanvas.drawArc(this.mRectF, this.mStartAngle, this.mSweepAngle, this.mUseCenter, pPaint);
    }
}
