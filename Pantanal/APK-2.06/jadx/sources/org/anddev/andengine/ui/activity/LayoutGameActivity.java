package org.anddev.andengine.ui.activity;

import org.anddev.andengine.opengl.view.RenderSurfaceView;

/* JADX INFO: loaded from: classes.dex */
public abstract class LayoutGameActivity extends BaseGameActivity {
    protected abstract int getLayoutID();

    protected abstract int getRenderSurfaceViewID();

    @Override // org.anddev.andengine.ui.activity.BaseGameActivity
    protected void onSetContentView() {
        super.setContentView(getLayoutID());
        this.mRenderSurfaceView = (RenderSurfaceView) findViewById(getRenderSurfaceViewID());
        this.mRenderSurfaceView.setEGLConfigChooser(false);
        this.mRenderSurfaceView.setRenderer(this.mEngine);
    }
}
