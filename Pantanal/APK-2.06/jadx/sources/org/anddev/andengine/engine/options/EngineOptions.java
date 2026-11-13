package org.anddev.andengine.engine.options;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.resolutionpolicy.IResolutionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class EngineOptions {
    private final Camera mCamera;
    private final boolean mFullscreen;
    private boolean mNeedsMusic;
    private boolean mNeedsSound;
    private final IResolutionPolicy mResolutionPolicy;
    private final ScreenOrientation mScreenOrientation;
    private final TouchOptions mTouchOptions = new TouchOptions();
    private final RenderOptions mRenderOptions = new RenderOptions();
    private WakeLockOptions mWakeLockOptions = WakeLockOptions.SCREEN_BRIGHT;
    private int mUpdateThreadPriority = 0;

    public enum ScreenOrientation {
        LANDSCAPE,
        PORTRAIT;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static ScreenOrientation[] valuesCustom() {
            ScreenOrientation[] screenOrientationArrValuesCustom = values();
            int length = screenOrientationArrValuesCustom.length;
            ScreenOrientation[] screenOrientationArr = new ScreenOrientation[length];
            System.arraycopy(screenOrientationArrValuesCustom, 0, screenOrientationArr, 0, length);
            return screenOrientationArr;
        }
    }

    public EngineOptions(boolean pFullscreen, ScreenOrientation pScreenOrientation, IResolutionPolicy pResolutionPolicy, Camera pCamera) {
        this.mFullscreen = pFullscreen;
        this.mScreenOrientation = pScreenOrientation;
        this.mResolutionPolicy = pResolutionPolicy;
        this.mCamera = pCamera;
    }

    public TouchOptions getTouchOptions() {
        return this.mTouchOptions;
    }

    public RenderOptions getRenderOptions() {
        return this.mRenderOptions;
    }

    public boolean isFullscreen() {
        return this.mFullscreen;
    }

    public ScreenOrientation getScreenOrientation() {
        return this.mScreenOrientation;
    }

    public IResolutionPolicy getResolutionPolicy() {
        return this.mResolutionPolicy;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public int getUpdateThreadPriority() {
        return this.mUpdateThreadPriority;
    }

    public void setUpdateThreadPriority(int pUpdateThreadPriority) {
        this.mUpdateThreadPriority = pUpdateThreadPriority;
    }

    public boolean needsSound() {
        return this.mNeedsSound;
    }

    public EngineOptions setNeedsSound(boolean pNeedsSound) {
        this.mNeedsSound = pNeedsSound;
        return this;
    }

    public boolean needsMusic() {
        return this.mNeedsMusic;
    }

    public EngineOptions setNeedsMusic(boolean pNeedsMusic) {
        this.mNeedsMusic = pNeedsMusic;
        return this;
    }

    public WakeLockOptions getWakeLockOptions() {
        return this.mWakeLockOptions;
    }

    public EngineOptions setWakeLockOptions(WakeLockOptions pWakeLockOptions) {
        this.mWakeLockOptions = pWakeLockOptions;
        return this;
    }
}
