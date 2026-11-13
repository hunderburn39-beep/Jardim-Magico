package org.anddev.andengine.engine.options.resolutionpolicy;

import android.view.View;
import org.anddev.andengine.opengl.view.RenderSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public class RelativeResolutionPolicy extends BaseResolutionPolicy {
    private final float mHeightScale;
    private final float mWidthScale;

    public RelativeResolutionPolicy(float pScale) {
        this(pScale, pScale);
    }

    public RelativeResolutionPolicy(float pWidthScale, float pHeightScale) {
        this.mWidthScale = pWidthScale;
        this.mHeightScale = pHeightScale;
    }

    @Override // org.anddev.andengine.engine.options.resolutionpolicy.IResolutionPolicy
    public void onMeasure(RenderSurfaceView pRenderSurfaceView, int pWidthMeasureSpec, int pHeightMeasureSpec) {
        BaseResolutionPolicy.throwOnNotMeasureSpecEXACTLY(pWidthMeasureSpec, pHeightMeasureSpec);
        int measuredWidth = (int) (View.MeasureSpec.getSize(pWidthMeasureSpec) * this.mWidthScale);
        int measuredHeight = (int) (View.MeasureSpec.getSize(pHeightMeasureSpec) * this.mHeightScale);
        pRenderSurfaceView.setMeasuredDimensionProxy(measuredWidth, measuredHeight);
    }
}
