package org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.IBitmapTextureAtlasSourceDecoratorShape;
import org.anddev.andengine.util.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class RadialGradientFillBitmapTextureAtlasSourceDecorator extends BaseShapeBitmapTextureAtlasSourceDecorator {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$atlas$bitmap$source$decorator$RadialGradientFillBitmapTextureAtlasSourceDecorator$RadialGradientDirection;
    private static final float[] POSITIONS_DEFAULT = {BitmapDescriptorFactory.HUE_RED, 1.0f};
    protected final int[] mColors;
    protected final float[] mPositions;
    protected final RadialGradientDirection mRadialGradientDirection;

    public enum RadialGradientDirection {
        INSIDE_OUT,
        OUTSIDE_IN;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static RadialGradientDirection[] valuesCustom() {
            RadialGradientDirection[] radialGradientDirectionArrValuesCustom = values();
            int length = radialGradientDirectionArrValuesCustom.length;
            RadialGradientDirection[] radialGradientDirectionArr = new RadialGradientDirection[length];
            System.arraycopy(radialGradientDirectionArrValuesCustom, 0, radialGradientDirectionArr, 0, length);
            return radialGradientDirectionArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$opengl$texture$atlas$bitmap$source$decorator$RadialGradientFillBitmapTextureAtlasSourceDecorator$RadialGradientDirection() {
        int[] iArr = $SWITCH_TABLE$org$anddev$andengine$opengl$texture$atlas$bitmap$source$decorator$RadialGradientFillBitmapTextureAtlasSourceDecorator$RadialGradientDirection;
        if (iArr == null) {
            iArr = new int[RadialGradientDirection.valuesCustom().length];
            try {
                iArr[RadialGradientDirection.INSIDE_OUT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RadialGradientDirection.OUTSIDE_IN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$org$anddev$andengine$opengl$texture$atlas$bitmap$source$decorator$RadialGradientFillBitmapTextureAtlasSourceDecorator$RadialGradientDirection = iArr;
        }
        return iArr;
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor, RadialGradientDirection pRadialGradientDirection) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pFromColor, pToColor, pRadialGradientDirection, (BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions) null);
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int pFromColor, int pToColor, RadialGradientDirection pRadialGradientDirection, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, new int[]{pFromColor, pToColor}, POSITIONS_DEFAULT, pRadialGradientDirection, pTextureAtlasSourceDecoratorOptions);
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int[] pColors, float[] pPositions, RadialGradientDirection pRadialGradientDirection) {
        this(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pColors, pPositions, pRadialGradientDirection, (BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions) null);
    }

    public RadialGradientFillBitmapTextureAtlasSourceDecorator(IBitmapTextureAtlasSource pBitmapTextureAtlasSource, IBitmapTextureAtlasSourceDecoratorShape pBitmapTextureAtlasSourceDecoratorShape, int[] pColors, float[] pPositions, RadialGradientDirection pRadialGradientDirection, BaseBitmapTextureAtlasSourceDecorator.TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
        super(pBitmapTextureAtlasSource, pBitmapTextureAtlasSourceDecoratorShape, pTextureAtlasSourceDecoratorOptions);
        this.mColors = pColors;
        this.mPositions = pPositions;
        this.mRadialGradientDirection = pRadialGradientDirection;
        this.mPaint.setStyle(Paint.Style.FILL);
        int width = pBitmapTextureAtlasSource.getWidth();
        int height = pBitmapTextureAtlasSource.getHeight();
        float centerX = width * 0.5f;
        float centerY = height * 0.5f;
        float radius = Math.max(centerX, centerY);
        switch ($SWITCH_TABLE$org$anddev$andengine$opengl$texture$atlas$bitmap$source$decorator$RadialGradientFillBitmapTextureAtlasSourceDecorator$RadialGradientDirection()[pRadialGradientDirection.ordinal()]) {
            case 1:
                this.mPaint.setShader(new RadialGradient(centerX, centerY, radius, pColors, pPositions, Shader.TileMode.CLAMP));
                break;
            case 2:
                ArrayUtils.reverse(pColors);
                this.mPaint.setShader(new RadialGradient(centerX, centerY, radius, pColors, pPositions, Shader.TileMode.CLAMP));
                break;
        }
    }

    @Override // org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseShapeBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator, org.anddev.andengine.opengl.texture.source.ITextureAtlasSource, org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource
    public RadialGradientFillBitmapTextureAtlasSourceDecorator deepCopy() {
        return new RadialGradientFillBitmapTextureAtlasSourceDecorator(this.mBitmapTextureAtlasSource, this.mBitmapTextureAtlasSourceDecoratorShape, this.mColors, this.mPositions, this.mRadialGradientDirection, this.mTextureAtlasSourceDecoratorOptions);
    }
}
