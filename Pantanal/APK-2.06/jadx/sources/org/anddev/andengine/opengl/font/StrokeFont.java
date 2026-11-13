package org.anddev.andengine.opengl.font;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.opengl.texture.ITexture;

/* JADX INFO: loaded from: classes.dex */
public class StrokeFont extends Font {
    private final boolean mStrokeOnly;
    private final Paint mStrokePaint;

    public StrokeFont(ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor) {
        this(pTexture, pTypeface, pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor, false);
    }

    public StrokeFont(ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor, boolean pStrokeOnly) {
        super(pTexture, pTypeface, pSize, pAntiAlias, pColor);
        this.mStrokePaint = new Paint();
        this.mStrokePaint.setTypeface(pTypeface);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setStrokeWidth(pStrokeWidth);
        this.mStrokePaint.setColor(pStrokeColor);
        this.mStrokePaint.setTextSize(pSize);
        this.mStrokePaint.setAntiAlias(pAntiAlias);
        this.mStrokeOnly = pStrokeOnly;
    }

    @Override // org.anddev.andengine.opengl.font.Font
    protected void drawCharacterString(String pCharacterAsString) {
        if (!this.mStrokeOnly) {
            super.drawCharacterString(pCharacterAsString);
        }
        this.mCanvas.drawText(pCharacterAsString, BitmapDescriptorFactory.HUE_RED, -this.mFontMetrics.ascent, this.mStrokePaint);
    }
}
