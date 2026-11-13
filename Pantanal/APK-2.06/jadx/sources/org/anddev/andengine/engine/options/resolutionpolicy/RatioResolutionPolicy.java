package org.anddev.andengine.engine.options.resolutionpolicy;

import android.view.View;
import org.anddev.andengine.opengl.view.RenderSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public class RatioResolutionPolicy extends BaseResolutionPolicy {
    private final float mRatio;

    public RatioResolutionPolicy(float pRatio) {
        this.mRatio = pRatio;
    }

    public RatioResolutionPolicy(float pWidthRatio, float pHeightRatio) {
        this.mRatio = pWidthRatio / pHeightRatio;
    }

    @Override // org.anddev.andengine.engine.options.resolutionpolicy.IResolutionPolicy
    public void onMeasure(RenderSurfaceView pRenderSurfaceView, int pWidthMeasureSpec, int pHeightMeasureSpec) {
        int measuredHeight;
        int measuredWidth;
        BaseResolutionPolicy.throwOnNotMeasureSpecEXACTLY(pWidthMeasureSpec, pHeightMeasureSpec);
        int specWidth = View.MeasureSpec.getSize(pWidthMeasureSpec);
        int specHeight = View.MeasureSpec.getSize(pHeightMeasureSpec);
        float desiredRatio = this.mRatio;
        float realRatio = specWidth / specHeight;
        if (realRatio < desiredRatio) {
            measuredWidth = specWidth;
            measuredHeight = Math.round(measuredWidth / desiredRatio);
        } else {
            measuredHeight = specHeight;
            measuredWidth = Math.round(measuredHeight * desiredRatio);
        }
        pRenderSurfaceView.setMeasuredDimensionProxy(measuredWidth, measuredHeight);
    }
}
