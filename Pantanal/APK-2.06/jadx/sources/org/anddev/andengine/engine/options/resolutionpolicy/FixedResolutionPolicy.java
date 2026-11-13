package org.anddev.andengine.engine.options.resolutionpolicy;

import org.anddev.andengine.opengl.view.RenderSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public class FixedResolutionPolicy extends BaseResolutionPolicy {
    private final int mHeight;
    private final int mWidth;

    public FixedResolutionPolicy(int pWidth, int pHeight) {
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    @Override // org.anddev.andengine.engine.options.resolutionpolicy.IResolutionPolicy
    public void onMeasure(RenderSurfaceView pRenderSurfaceView, int pWidthMeasureSpec, int pHeightMeasureSpec) {
        pRenderSurfaceView.setMeasuredDimensionProxy(this.mWidth, this.mHeight);
    }
}
