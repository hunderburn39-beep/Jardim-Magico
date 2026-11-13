package org.anddev.andengine.ui.activity;

import android.os.Bundle;
import android.os.PowerManager;
import android.widget.FrameLayout;
import com.google.android.gms.drive.DriveFile;
import org.anddev.andengine.audio.music.MusicManager;
import org.anddev.andengine.audio.sound.SoundManager;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.WakeLockOptions;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.sensor.accelerometer.AccelerometerSensorOptions;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.sensor.location.ILocationListener;
import org.anddev.andengine.sensor.location.LocationSensorOptions;
import org.anddev.andengine.sensor.orientation.IOrientationListener;
import org.anddev.andengine.sensor.orientation.OrientationSensorOptions;
import org.anddev.andengine.ui.IGameInterface;
import org.anddev.andengine.util.ActivityUtils;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.constants.Constants;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseGameActivity extends BaseActivity implements IGameInterface {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$engine$options$EngineOptions$ScreenOrientation;
    protected Engine mEngine;
    private boolean mGameLoaded;
    protected boolean mHasWindowFocused;
    private boolean mPaused;
    protected RenderSurfaceView mRenderSurfaceView;
    private PowerManager.WakeLock mWakeLock;

    static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$engine$options$EngineOptions$ScreenOrientation() {
        int[] iArr = $SWITCH_TABLE$org$anddev$andengine$engine$options$EngineOptions$ScreenOrientation;
        if (iArr == null) {
            iArr = new int[EngineOptions.ScreenOrientation.valuesCustom().length];
            try {
                iArr[EngineOptions.ScreenOrientation.LANDSCAPE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[EngineOptions.ScreenOrientation.PORTRAIT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$org$anddev$andengine$engine$options$EngineOptions$ScreenOrientation = iArr;
        }
        return iArr;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPaused = true;
        this.mEngine = onLoadEngine();
        applyEngineOptions(this.mEngine.getEngineOptions());
        onSetContentView();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.mPaused && this.mHasWindowFocused) {
            doResume();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean pHasWindowFocus) {
        super.onWindowFocusChanged(pHasWindowFocus);
        if (pHasWindowFocus) {
            if (this.mPaused) {
                doResume();
            }
            this.mHasWindowFocused = true;
        } else {
            if (!this.mPaused) {
                doPause();
            }
            this.mHasWindowFocused = false;
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        if (!this.mPaused) {
            doPause();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mEngine.interruptUpdateThread();
        onUnloadResources();
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public void onUnloadResources() {
        if (this.mEngine.getEngineOptions().needsMusic()) {
            getMusicManager().releaseAll();
        }
        if (this.mEngine.getEngineOptions().needsSound()) {
            getSoundManager().releaseAll();
        }
    }

    public Engine getEngine() {
        return this.mEngine;
    }

    public TextureManager getTextureManager() {
        return this.mEngine.getTextureManager();
    }

    public FontManager getFontManager() {
        return this.mEngine.getFontManager();
    }

    public SoundManager getSoundManager() {
        return this.mEngine.getSoundManager();
    }

    public MusicManager getMusicManager() {
        return this.mEngine.getMusicManager();
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public void onResumeGame() {
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public void onPauseGame() {
    }

    private void doResume() {
        if (!this.mGameLoaded) {
            onLoadResources();
            Scene scene = onLoadScene();
            this.mEngine.onLoadComplete(scene);
            onLoadComplete();
            this.mGameLoaded = true;
        }
        this.mPaused = false;
        acquireWakeLock(this.mEngine.getEngineOptions().getWakeLockOptions());
        this.mEngine.onResume();
        this.mRenderSurfaceView.onResume();
        this.mEngine.start();
        onResumeGame();
    }

    private void doPause() {
        this.mPaused = true;
        releaseWakeLock();
        this.mEngine.onPause();
        this.mEngine.stop();
        this.mRenderSurfaceView.onPause();
        onPauseGame();
    }

    public void runOnUpdateThread(Runnable pRunnable) {
        this.mEngine.runOnUpdateThread(pRunnable);
    }

    protected void onSetContentView() {
        this.mRenderSurfaceView = new RenderSurfaceView(this);
        this.mRenderSurfaceView.setEGLConfigChooser(false);
        this.mRenderSurfaceView.setRenderer(this.mEngine);
        setContentView(this.mRenderSurfaceView, createSurfaceViewLayoutParams());
    }

    private void acquireWakeLock(WakeLockOptions pWakeLockOptions) {
        if (pWakeLockOptions == WakeLockOptions.SCREEN_ON) {
            ActivityUtils.keepScreenOn(this);
            return;
        }
        PowerManager pm = (PowerManager) getSystemService("power");
        this.mWakeLock = pm.newWakeLock(pWakeLockOptions.getFlag() | DriveFile.MODE_WRITE_ONLY, Constants.DEBUGTAG);
        try {
            this.mWakeLock.acquire();
        } catch (SecurityException e) {
            Debug.e("You have to add\n\t<uses-permission android:name=\"android.permission.WAKE_LOCK\"/>\nto your AndroidManifest.xml !", e);
        }
    }

    private void releaseWakeLock() {
        if (this.mWakeLock != null && this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private void applyEngineOptions(EngineOptions pEngineOptions) {
        if (pEngineOptions.isFullscreen()) {
            ActivityUtils.requestFullscreen(this);
        }
        if (pEngineOptions.needsMusic() || pEngineOptions.needsSound()) {
            setVolumeControlStream(3);
        }
        switch ($SWITCH_TABLE$org$anddev$andengine$engine$options$EngineOptions$ScreenOrientation()[pEngineOptions.getScreenOrientation().ordinal()]) {
            case 1:
                setRequestedOrientation(0);
                break;
            case 2:
                setRequestedOrientation(1);
                break;
        }
    }

    protected FrameLayout.LayoutParams createSurfaceViewLayoutParams() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        return layoutParams;
    }

    protected void enableVibrator() {
        this.mEngine.enableVibrator(this);
    }

    protected void enableLocationSensor(ILocationListener pLocationListener, LocationSensorOptions pLocationSensorOptions) {
        this.mEngine.enableLocationSensor(this, pLocationListener, pLocationSensorOptions);
    }

    protected void disableLocationSensor() {
        this.mEngine.disableLocationSensor(this);
    }

    protected boolean enableAccelerometerSensor(IAccelerometerListener pAccelerometerListener) {
        return this.mEngine.enableAccelerometerSensor(this, pAccelerometerListener);
    }

    protected boolean enableAccelerometerSensor(IAccelerometerListener pAccelerometerListener, AccelerometerSensorOptions pAccelerometerSensorOptions) {
        return this.mEngine.enableAccelerometerSensor(this, pAccelerometerListener, pAccelerometerSensorOptions);
    }

    protected boolean disableAccelerometerSensor() {
        return this.mEngine.disableAccelerometerSensor(this);
    }

    protected boolean enableOrientationSensor(IOrientationListener pOrientationListener) {
        return this.mEngine.enableOrientationSensor(this, pOrientationListener);
    }

    protected boolean enableOrientationSensor(IOrientationListener pOrientationListener, OrientationSensorOptions pLocationSensorOptions) {
        return this.mEngine.enableOrientationSensor(this, pOrientationListener, pLocationSensorOptions);
    }

    protected boolean disableOrientationSensor() {
        return this.mEngine.disableOrientationSensor(this);
    }
}
