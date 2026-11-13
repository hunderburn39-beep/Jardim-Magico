package org.anddev.andengine.engine;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.util.GLHelper;

/* JADX INFO: loaded from: classes.dex */
public class DoubleSceneSplitScreenEngine extends Engine {
    private final Camera mSecondCamera;
    private Scene mSecondScene;

    public DoubleSceneSplitScreenEngine(EngineOptions pEngineOptions, Camera pSecondCamera) {
        super(pEngineOptions);
        this.mSecondCamera = pSecondCamera;
    }

    @Override // org.anddev.andengine.engine.Engine
    @Deprecated
    public Camera getCamera() {
        return this.mCamera;
    }

    public Camera getFirstCamera() {
        return this.mCamera;
    }

    public Camera getSecondCamera() {
        return this.mSecondCamera;
    }

    @Override // org.anddev.andengine.engine.Engine
    @Deprecated
    public Scene getScene() {
        return super.getScene();
    }

    public Scene getFirstScene() {
        return super.getScene();
    }

    public Scene getSecondScene() {
        return this.mSecondScene;
    }

    @Override // org.anddev.andengine.engine.Engine
    @Deprecated
    public void setScene(Scene pScene) {
        setFirstScene(pScene);
    }

    public void setFirstScene(Scene pScene) {
        super.setScene(pScene);
    }

    public void setSecondScene(Scene pScene) {
        this.mSecondScene = pScene;
    }

    @Override // org.anddev.andengine.engine.Engine
    protected void onDrawScene(GL10 pGL) {
        Camera firstCamera = getFirstCamera();
        Camera secondCamera = getSecondCamera();
        int surfaceWidth = this.mSurfaceWidth;
        int surfaceWidthHalf = surfaceWidth >> 1;
        int surfaceHeight = this.mSurfaceHeight;
        GLHelper.enableScissorTest(pGL);
        pGL.glScissor(0, 0, surfaceWidthHalf, surfaceHeight);
        pGL.glViewport(0, 0, surfaceWidthHalf, surfaceHeight);
        this.mScene.onDraw(pGL, firstCamera);
        firstCamera.onDrawHUD(pGL);
        pGL.glScissor(surfaceWidthHalf, 0, surfaceWidthHalf, surfaceHeight);
        pGL.glViewport(surfaceWidthHalf, 0, surfaceWidthHalf, surfaceHeight);
        this.mSecondScene.onDraw(pGL, secondCamera);
        secondCamera.onDrawHUD(pGL);
        GLHelper.disableScissorTest(pGL);
    }

    @Override // org.anddev.andengine.engine.Engine
    protected Camera getCameraFromSurfaceTouchEvent(TouchEvent pTouchEvent) {
        return pTouchEvent.getX() <= ((float) (this.mSurfaceWidth >> 1)) ? getFirstCamera() : getSecondCamera();
    }

    @Override // org.anddev.andengine.engine.Engine
    protected Scene getSceneFromSurfaceTouchEvent(TouchEvent pTouchEvent) {
        return pTouchEvent.getX() <= ((float) (this.mSurfaceWidth >> 1)) ? getFirstScene() : getSecondScene();
    }

    @Override // org.anddev.andengine.engine.Engine
    protected void onUpdateScene(float pSecondsElapsed) {
        super.onUpdateScene(pSecondsElapsed);
        if (this.mSecondScene != null) {
            this.mSecondScene.onUpdate(pSecondsElapsed);
        }
    }

    @Override // org.anddev.andengine.engine.Engine
    protected void convertSurfaceToSceneTouchEvent(Camera pCamera, TouchEvent pSurfaceTouchEvent) {
        int surfaceWidthHalf = this.mSurfaceWidth >> 1;
        if (pCamera == getFirstCamera()) {
            pCamera.convertSurfaceToSceneTouchEvent(pSurfaceTouchEvent, surfaceWidthHalf, this.mSurfaceHeight);
        } else {
            pSurfaceTouchEvent.offset(-surfaceWidthHalf, BitmapDescriptorFactory.HUE_RED);
            pCamera.convertSurfaceToSceneTouchEvent(pSurfaceTouchEvent, surfaceWidthHalf, this.mSurfaceHeight);
        }
    }

    @Override // org.anddev.andengine.engine.Engine
    protected void updateUpdateHandlers(float pSecondsElapsed) {
        super.updateUpdateHandlers(pSecondsElapsed);
        getSecondCamera().onUpdate(pSecondsElapsed);
    }

    @Override // org.anddev.andengine.engine.Engine
    protected void onUpdateCameraSurface() {
        int surfaceWidth = this.mSurfaceWidth;
        int surfaceWidthHalf = surfaceWidth >> 1;
        getFirstCamera().setSurfaceSize(0, 0, surfaceWidthHalf, this.mSurfaceHeight);
        getSecondCamera().setSurfaceSize(surfaceWidthHalf, 0, surfaceWidthHalf, this.mSurfaceHeight);
    }
}
