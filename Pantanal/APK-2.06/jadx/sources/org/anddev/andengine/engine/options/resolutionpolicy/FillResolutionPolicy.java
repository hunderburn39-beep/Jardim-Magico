package org.anddev.andengine.engine.options.resolutionpolicy;

import android.view.View;
import org.anddev.andengine.opengl.view.RenderSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public class FillResolutionPolicy extends BaseResolutionPolicy {
    @Override // org.anddev.andengine.engine.options.resolutionpolicy.IResolutionPolicy
    public void onMeasure(RenderSurfaceView pRenderSurfaceView, int pWidthMeasureSpec, int pHeightMeasureSpec) {
        BaseResolutionPolicy.throwOnNotMeasureSpecEXACTLY(pWidthMeasureSpec, pHeightMeasureSpec);
        int measuredWidth = View.MeasureSpec.getSize(pWidthMeasureSpec);
        int measuredHeight = View.MeasureSpec.getSize(pHeightMeasureSpec);
        pRenderSurfaceView.setMeasuredDimensionProxy(measuredWidth, measuredHeight);
    }
}
