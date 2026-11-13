package org.anddev.andengine.opengl.view;

import android.content.Context;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public class RenderSurfaceView extends GLSurfaceView {
    private Renderer mRenderer;

    public RenderSurfaceView(Context pContext) {
        super(pContext);
    }

    public RenderSurfaceView(Context pContext, AttributeSet pAttrs) {
        super(pContext, pAttrs);
    }

    public void setRenderer(Engine pEngine) {
        setOnTouchListener(pEngine);
        this.mRenderer = new Renderer(pEngine);
        setRenderer(this.mRenderer);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int pWidthMeasureSpec, int pHeightMeasureSpec) {
        this.mRenderer.mEngine.getEngineOptions().getResolutionPolicy().onMeasure(this, pWidthMeasureSpec, pHeightMeasureSpec);
    }

    public void setMeasuredDimensionProxy(int pMeasuredWidth, int pMeasuredHeight) {
        setMeasuredDimension(pMeasuredWidth, pMeasuredHeight);
    }

    public static class Renderer implements GLSurfaceView.Renderer {
        private final Engine mEngine;

        public Renderer(Engine pEngine) {
            this.mEngine = pEngine;
        }

        @Override // org.anddev.andengine.opengl.view.GLSurfaceView.Renderer
        public void onSurfaceChanged(GL10 pGL, int pWidth, int pHeight) {
            Debug.d("onSurfaceChanged: pWidth=" + pWidth + "  pHeight=" + pHeight);
            this.mEngine.setSurfaceSize(pWidth, pHeight);
            pGL.glViewport(0, 0, pWidth, pHeight);
            pGL.glLoadIdentity();
        }

        @Override // org.anddev.andengine.opengl.view.GLSurfaceView.Renderer
        public void onSurfaceCreated(GL10 pGL, EGLConfig pConfig) {
            Debug.d("onSurfaceCreated");
            GLHelper.reset(pGL);
            GLHelper.setPerspectiveCorrectionHintFastest(pGL);
            GLHelper.setShadeModelFlat(pGL);
            GLHelper.disableLightning(pGL);
            GLHelper.disableDither(pGL);
            GLHelper.disableDepthTest(pGL);
            GLHelper.disableMultisample(pGL);
            GLHelper.enableBlend(pGL);
            GLHelper.enableTextures(pGL);
            GLHelper.enableTexCoordArray(pGL);
            GLHelper.enableVertexArray(pGL);
            GLHelper.enableCulling(pGL);
            pGL.glFrontFace(2305);
            pGL.glCullFace(1029);
            GLHelper.enableExtensions(pGL, this.mEngine.getEngineOptions().getRenderOptions());
        }

        @Override // org.anddev.andengine.opengl.view.GLSurfaceView.Renderer
        public void onDrawFrame(GL10 pGL) {
            try {
                this.mEngine.onDrawFrame(pGL);
            } catch (InterruptedException e) {
                Debug.e("GLThread interrupted!", e);
            }
        }
    }
}
