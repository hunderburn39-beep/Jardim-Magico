package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.source.BaseTextureAtlasSource;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseBitmapTextureAtlasSourceDecorator extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    protected final IBitmapTextureAtlasSource mBitmapTextureAtlasSource;
    protected Paint mPaint;
    protected TextureAtlasSourceDecoratorOptions mTextureAtlasSourceDecoratorOptions;

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public abstract BaseBitmapTextureAtlasSourceDecorator deepCopy();

    protected abstract void onDecorateBitmap(Canvas canvas);

    public BaseBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource) {
        this(pBitmapTextureAtlasSource, new TextureAtlasSourceDecoratorOptions());
    }

    public BaseBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource.getTexturePositionX(), pBitmapTextureAtlasSource.getTexturePositionY());
        this.mPaint = new Paint();
        this.mBitmapTextureAtlasSource = pBitmapTextureAtlasSource;
        this.mTextureAtlasSourceDecoratorOptions = pTextureAtlasSourceDecoratorOptions == null ? new TextureAtlasSourceDecoratorOptions() : pTextureAtlasSourceDecoratorOptions;
        this.mPaint.setAntiAlias(this.mTextureAtlasSourceDecoratorOptions.getAntiAliasing());
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public void setPaint(Paint pPaint) {
        this.mPaint = pPaint;
    }

    public TextureAtlasSourceDecoratorOptions getTextureAtlasSourceDecoratorOptions() {
        return this.mTextureAtlasSourceDecoratorOptions;
    }

    public void setTextureAtlasSourceDecoratorOptions(TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this.mTextureAtlasSourceDecoratorOptions = pTextureAtlasSourceDecoratorOptions;
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public int getWidth() {
        return this.mBitmapTextureAtlasSource.getWidth();
    }

    @Override // org.anddev.andengine.opengl.texture.source.ITextureAtlasSource
    public int getHeight() {
        return this.mBitmapTextureAtlasSource.getHeight();
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public Bitmap onLoadBitmap(Bitmap.Config pBitmapConfig) {
        Bitmap bitmap = ensureLoadedBitmapIsMutable(this.mBitmapTextureAtlasSource.onLoadBitmap(pBitmapConfig));
        Canvas canvas = new Canvas(bitmap);
        onDecorateBitmap(canvas);
        return bitmap;
    }

    private Bitmap ensureLoadedBitmapIsMutable(Bitmap pBitmap) {
        if (!pBitmap.isMutable()) {
            Bitmap mutableBitmap = pBitmap.copy(pBitmap.getConfig(), true);
            pBitmap.recycle();
            return mutableBitmap;
        }
        return pBitmap;
    }

    public static class TextureAtlasSourceDecoratorOptions {
        public static final TextureAtlasSourceDecoratorOptions DEFAULT = new TextureAtlasSourceDecoratorOptions();
        private boolean mAntiAliasing;
        private float mInsetLeft = 0.25f;
        private float mInsetRight = 0.25f;
        private float mInsetTop = 0.25f;
        private float mInsetBottom = 0.25f;

        protected TextureAtlasSourceDecoratorOptions deepCopy() {
            TextureAtlasSourceDecoratorOptions textureSourceDecoratorOptions = new TextureAtlasSourceDecoratorOptions();
            textureSourceDecoratorOptions.setInsets(this.mInsetLeft, this.mInsetTop, this.mInsetRight, this.mInsetBottom);
            textureSourceDecoratorOptions.setAntiAliasing(this.mAntiAliasing);
            return textureSourceDecoratorOptions;
        }

        public boolean getAntiAliasing() {
            return this.mAntiAliasing;
        }

        public float getInsetLeft() {
            return this.mInsetLeft;
        }

        public float getInsetRight() {
            return this.mInsetRight;
        }

        public float getInsetTop() {
            return this.mInsetTop;
        }

        public float getInsetBottom() {
            return this.mInsetBottom;
        }

        public TextureAtlasSourceDecoratorOptions setAntiAliasing(boolean pAntiAliasing) {
            this.mAntiAliasing = pAntiAliasing;
            return this;
        }

        public TextureAtlasSourceDecoratorOptions setInsetLeft(float pInsetLeft) {
            this.mInsetLeft = pInsetLeft;
            return this;
        }

        public TextureAtlasSourceDecoratorOptions setInsetRight(float pInsetRight) {
            this.mInsetRight = pInsetRight;
            return this;
        }

        public TextureAtlasSourceDecoratorOptions setInsetTop(float pInsetTop) {
            this.mInsetTop = pInsetTop;
            return this;
        }

        public TextureAtlasSourceDecoratorOptions setInsetBottom(float pInsetBottom) {
            this.mInsetBottom = pInsetBottom;
            return this;
        }

        public TextureAtlasSourceDecoratorOptions setInsets(float pInsets) {
            return setInsets(pInsets, pInsets, pInsets, pInsets);
        }

        public TextureAtlasSourceDecoratorOptions setInsets(float pInsetLeft, float pInsetTop, float pInsetRight, float pInsetBottom) {
            this.mInsetLeft = pInsetLeft;
            this.mInsetTop = pInsetTop;
            this.mInsetRight = pInsetRight;
            this.mInsetBottom = pInsetBottom;
            return this;
        }
    }
}
