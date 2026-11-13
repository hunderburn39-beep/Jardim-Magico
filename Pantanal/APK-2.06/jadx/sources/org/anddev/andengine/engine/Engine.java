package org.anddev.andengine.engine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.music.MusicManager;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.audio.sound.SoundManager;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.UpdateHandlerList;
import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.controller.ITouchController;
import org.anddev.andengine.input.touch.controller.SingleTouchControler;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.sensor.SensorDelay;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.AccelerometerSensorOptions;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.sensor.location.ILocationListener;
import org.anddev.andengine.sensor.location.LocationProviderStatus;
import org.anddev.andengine.sensor.location.LocationSensorOptions;
import org.anddev.andengine.sensor.orientation.IOrientationListener;
import org.anddev.andengine.sensor.orientation.OrientationData;
import org.anddev.andengine.sensor.orientation.OrientationSensorOptions;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class Engine implements SensorEventListener, View.OnTouchListener, ITouchController.ITouchEventCallback, TimeConstants, LocationListener {
    private static final SensorDelay SENSORDELAY_DEFAULT = SensorDelay.GAME;
    private static final int UPDATEHANDLERS_CAPACITY_DEFAULT = 32;
    private AccelerometerData mAccelerometerData;
    private IAccelerometerListener mAccelerometerListener;
    protected final Camera mCamera;
    private final EngineOptions mEngineOptions;
    private boolean mIsMethodTracing;
    private Location mLocation;
    private ILocationListener mLocationListener;
    private MusicManager mMusicManager;
    private OrientationData mOrientationData;
    private IOrientationListener mOrientationListener;
    protected Scene mScene;
    private SoundManager mSoundManager;
    private ITouchController mTouchController;
    private Vibrator mVibrator;
    private boolean mRunning = false;
    private long mLastTick = -1;
    private float mSecondsElapsedTotal = BitmapDescriptorFactory.HUE_RED;
    private final State mThreadLocker = new State(null);
    private final UpdateThread mUpdateThread = new UpdateThread();
    private final RunnableHandler mUpdateThreadRunnableHandler = new RunnableHandler();
    private final TextureManager mTextureManager = new TextureManager();
    private final BufferObjectManager mBufferObjectManager = new BufferObjectManager();
    private final FontManager mFontManager = new FontManager();
    private final UpdateHandlerList mUpdateHandlers = new UpdateHandlerList(32);
    protected int mSurfaceWidth = 1;
    protected int mSurfaceHeight = 1;

    public Engine(EngineOptions pEngineOptions) {
        BitmapTextureAtlasTextureRegionFactory.reset();
        SoundFactory.reset();
        MusicFactory.reset();
        FontFactory.reset();
        BufferObjectManager.setActiveInstance(this.mBufferObjectManager);
        this.mEngineOptions = pEngineOptions;
        setTouchController(new SingleTouchControler());
        this.mCamera = pEngineOptions.getCamera();
        if (this.mEngineOptions.needsSound()) {
            this.mSoundManager = new SoundManager();
        }
        if (this.mEngineOptions.needsMusic()) {
            this.mMusicManager = new MusicManager();
        }
        this.mUpdateThread.start();
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public synchronized void start() {
        if (!this.mRunning) {
            this.mLastTick = System.nanoTime();
            this.mRunning = true;
        }
    }

    public synchronized void stop() {
        if (this.mRunning) {
            this.mRunning = false;
        }
    }

    public Scene getScene() {
        return this.mScene;
    }

    public void setScene(Scene pScene) {
        this.mScene = pScene;
    }

    public EngineOptions getEngineOptions() {
        return this.mEngineOptions;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public float getSecondsElapsedTotal() {
        return this.mSecondsElapsedTotal;
    }

    public void setSurfaceSize(int pSurfaceWidth, int pSurfaceHeight) {
        this.mSurfaceWidth = pSurfaceWidth;
        this.mSurfaceHeight = pSurfaceHeight;
        onUpdateCameraSurface();
    }

    protected void onUpdateCameraSurface() {
        this.mCamera.setSurfaceSize(0, 0, this.mSurfaceWidth, this.mSurfaceHeight);
    }

    public int getSurfaceWidth() {
        return this.mSurfaceWidth;
    }

    public int getSurfaceHeight() {
        return this.mSurfaceHeight;
    }

    public ITouchController getTouchController() {
        return this.mTouchController;
    }

    public void setTouchController(ITouchController pTouchController) {
        this.mTouchController = pTouchController;
        this.mTouchController.applyTouchOptions(this.mEngineOptions.getTouchOptions());
        this.mTouchController.setTouchEventCallback(this);
    }

    public AccelerometerData getAccelerometerData() {
        return this.mAccelerometerData;
    }

    public OrientationData getOrientationData() {
        return this.mOrientationData;
    }

    public SoundManager getSoundManager() throws IllegalStateException {
        if (this.mSoundManager != null) {
            return this.mSoundManager;
        }
        throw new IllegalStateException("To enable the SoundManager, check the EngineOptions!");
    }

    public MusicManager getMusicManager() throws IllegalStateException {
        if (this.mMusicManager != null) {
            return this.mMusicManager;
        }
        throw new IllegalStateException("To enable the MusicManager, check the EngineOptions!");
    }

    public TextureManager getTextureManager() {
        return this.mTextureManager;
    }

    public FontManager getFontManager() {
        return this.mFontManager;
    }

    public void clearUpdateHandlers() {
        this.mUpdateHandlers.clear();
    }

    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    public void unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        this.mUpdateHandlers.remove(pUpdateHandler);
    }

    public boolean isMethodTracing() {
        return this.mIsMethodTracing;
    }

    public void startMethodTracing(String pTraceFileName) {
        if (!this.mIsMethodTracing) {
            this.mIsMethodTracing = true;
            Debug.startMethodTracing(pTraceFileName);
        }
    }

    public void stopMethodTracing() {
        if (this.mIsMethodTracing) {
            Debug.stopMethodTracing();
            this.mIsMethodTracing = false;
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {
        if (this.mRunning) {
            switch (pSensor.getType()) {
                case 1:
                    if (this.mAccelerometerData != null) {
                        this.mAccelerometerData.setAccuracy(pAccuracy);
                        this.mAccelerometerListener.onAccelerometerChanged(this.mAccelerometerData);
                    } else if (this.mOrientationData != null) {
                        this.mOrientationData.setAccelerometerAccuracy(pAccuracy);
                        this.mOrientationListener.onOrientationChanged(this.mOrientationData);
                    }
                    break;
                case 2:
                    this.mOrientationData.setMagneticFieldAccuracy(pAccuracy);
                    this.mOrientationListener.onOrientationChanged(this.mOrientationData);
                    break;
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent pEvent) {
        if (this.mRunning) {
            switch (pEvent.sensor.getType()) {
                case 1:
                    if (this.mAccelerometerData != null) {
                        this.mAccelerometerData.setValues(pEvent.values);
                        this.mAccelerometerListener.onAccelerometerChanged(this.mAccelerometerData);
                    } else if (this.mOrientationData != null) {
                        this.mOrientationData.setAccelerometerValues(pEvent.values);
                        this.mOrientationListener.onOrientationChanged(this.mOrientationData);
                    }
                    break;
                case 2:
                    this.mOrientationData.setMagneticFieldValues(pEvent.values);
                    this.mOrientationListener.onOrientationChanged(this.mOrientationData);
                    break;
            }
        }
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location pLocation) {
        if (this.mLocation == null) {
            this.mLocation = pLocation;
        } else if (pLocation == null) {
            this.mLocationListener.onLocationLost();
        } else {
            this.mLocation = pLocation;
            this.mLocationListener.onLocationChanged(pLocation);
        }
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String pProvider) {
        this.mLocationListener.onLocationProviderDisabled();
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String pProvider) {
        this.mLocationListener.onLocationProviderEnabled();
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String pProvider, int pStatus, Bundle pExtras) {
        switch (pStatus) {
            case 0:
                this.mLocationListener.onLocationProviderStatusChanged(LocationProviderStatus.OUT_OF_SERVICE, pExtras);
                break;
            case 1:
                this.mLocationListener.onLocationProviderStatusChanged(LocationProviderStatus.TEMPORARILY_UNAVAILABLE, pExtras);
                break;
            case 2:
                this.mLocationListener.onLocationProviderStatusChanged(LocationProviderStatus.AVAILABLE, pExtras);
                break;
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View pView, MotionEvent pSurfaceMotionEvent) {
        if (this.mRunning) {
            boolean handled = this.mTouchController.onHandleMotionEvent(pSurfaceMotionEvent);
            try {
                Thread.sleep(20L);
                return handled;
            } catch (InterruptedException e) {
                org.anddev.andengine.util.Debug.e(e);
                return handled;
            }
        }
        return false;
    }

    @Override // org.anddev.andengine.input.touch.controller.ITouchController.ITouchEventCallback
    public boolean onTouchEvent(TouchEvent pSurfaceTouchEvent) {
        Scene scene = getSceneFromSurfaceTouchEvent(pSurfaceTouchEvent);
        Camera camera = getCameraFromSurfaceTouchEvent(pSurfaceTouchEvent);
        convertSurfaceToSceneTouchEvent(camera, pSurfaceTouchEvent);
        if (onTouchHUD(camera, pSurfaceTouchEvent)) {
            return true;
        }
        return onTouchScene(scene, pSurfaceTouchEvent);
    }

    protected boolean onTouchHUD(Camera pCamera, TouchEvent pSceneTouchEvent) {
        if (pCamera.hasHUD()) {
            return pCamera.getHUD().onSceneTouchEvent(pSceneTouchEvent);
        }
        return false;
    }

    protected boolean onTouchScene(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pScene != null) {
            return pScene.onSceneTouchEvent(pSceneTouchEvent);
        }
        return false;
    }

    public void runOnUpdateThread(Runnable pRunnable) {
        this.mUpdateThreadRunnableHandler.postRunnable(pRunnable);
    }

    public void interruptUpdateThread() {
        this.mUpdateThread.interrupt();
    }

    public void onResume() {
        this.mTextureManager.reloadTextures();
        this.mFontManager.reloadFonts();
        BufferObjectManager.setActiveInstance(this.mBufferObjectManager);
        this.mBufferObjectManager.reloadBufferObjects();
    }

    public void onPause() {
    }

    protected Camera getCameraFromSurfaceTouchEvent(TouchEvent pTouchEvent) {
        return getCamera();
    }

    protected Scene getSceneFromSurfaceTouchEvent(TouchEvent pTouchEvent) {
        return this.mScene;
    }

    protected void convertSurfaceToSceneTouchEvent(Camera pCamera, TouchEvent pSurfaceTouchEvent) {
        pCamera.convertSurfaceToSceneTouchEvent(pSurfaceTouchEvent, this.mSurfaceWidth, this.mSurfaceHeight);
    }

    protected void convertSceneToSurfaceTouchEvent(Camera pCamera, TouchEvent pSurfaceTouchEvent) {
        pCamera.convertSceneToSurfaceTouchEvent(pSurfaceTouchEvent, this.mSurfaceWidth, this.mSurfaceHeight);
    }

    public void onLoadComplete(Scene pScene) {
        setScene(pScene);
    }

    void onTickUpdate() throws InterruptedException {
        if (this.mRunning) {
            long secondsElapsed = getNanosecondsElapsed();
            onUpdate(secondsElapsed);
            yieldDraw();
        } else {
            yieldDraw();
            Thread.sleep(16L);
        }
    }

    private void yieldDraw() throws InterruptedException {
        State threadLocker = this.mThreadLocker;
        threadLocker.notifyCanDraw();
        threadLocker.waitUntilCanUpdate();
    }

    protected void onUpdate(long pNanosecondsElapsed) throws InterruptedException {
        float pSecondsElapsed = pNanosecondsElapsed / 1.0E9f;
        this.mSecondsElapsedTotal += pSecondsElapsed;
        this.mLastTick += pNanosecondsElapsed;
        this.mTouchController.onUpdate(pSecondsElapsed);
        updateUpdateHandlers(pSecondsElapsed);
        onUpdateScene(pSecondsElapsed);
    }

    protected void onUpdateScene(float pSecondsElapsed) {
        if (this.mScene != null) {
            this.mScene.onUpdate(pSecondsElapsed);
        }
    }

    protected void updateUpdateHandlers(float pSecondsElapsed) {
        this.mUpdateThreadRunnableHandler.onUpdate(pSecondsElapsed);
        this.mUpdateHandlers.onUpdate(pSecondsElapsed);
        getCamera().onUpdate(pSecondsElapsed);
    }

    public void onDrawFrame(GL10 pGL) throws InterruptedException {
        State threadLocker = this.mThreadLocker;
        threadLocker.waitUntilCanDraw();
        this.mTextureManager.updateTextures(pGL);
        this.mFontManager.updateFonts(pGL);
        if (GLHelper.EXTENSIONS_VERTEXBUFFEROBJECTS) {
            this.mBufferObjectManager.updateBufferObjects((GL11) pGL);
        }
        onDrawScene(pGL);
        threadLocker.notifyCanUpdate();
    }

    protected void onDrawScene(GL10 pGL) {
        Camera camera = getCamera();
        this.mScene.onDraw(pGL, camera);
        camera.onDrawHUD(pGL);
    }

    private long getNanosecondsElapsed() {
        long now = System.nanoTime();
        return calculateNanosecondsElapsed(now, this.mLastTick);
    }

    protected long calculateNanosecondsElapsed(long pNow, long pLastTick) {
        return pNow - pLastTick;
    }

    public boolean enableVibrator(Context pContext) {
        this.mVibrator = (Vibrator) pContext.getSystemService("vibrator");
        return this.mVibrator != null;
    }

    public void vibrate(long pMilliseconds) throws IllegalStateException {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate(pMilliseconds);
            return;
        }
        throw new IllegalStateException("You need to enable the Vibrator before you can use it!");
    }

    public void vibrate(long[] pPattern, int pRepeat) throws IllegalStateException {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate(pPattern, pRepeat);
            return;
        }
        throw new IllegalStateException("You need to enable the Vibrator before you can use it!");
    }

    public void enableLocationSensor(Context pContext, ILocationListener pLocationListener, LocationSensorOptions pLocationSensorOptions) {
        this.mLocationListener = pLocationListener;
        LocationManager locationManager = (LocationManager) pContext.getSystemService("location");
        String locationProvider = locationManager.getBestProvider(pLocationSensorOptions, pLocationSensorOptions.isEnabledOnly());
        locationManager.requestLocationUpdates(locationProvider, pLocationSensorOptions.getMinimumTriggerTime(), pLocationSensorOptions.getMinimumTriggerDistance(), this);
        onLocationChanged(locationManager.getLastKnownLocation(locationProvider));
    }

    public void disableLocationSensor(Context pContext) {
        LocationManager locationManager = (LocationManager) pContext.getSystemService("location");
        locationManager.removeUpdates(this);
    }

    public boolean enableAccelerometerSensor(Context pContext, IAccelerometerListener pAccelerometerListener) {
        return enableAccelerometerSensor(pContext, pAccelerometerListener, new AccelerometerSensorOptions(SENSORDELAY_DEFAULT));
    }

    public boolean enableAccelerometerSensor(Context pContext, IAccelerometerListener pAccelerometerListener, AccelerometerSensorOptions pAccelerometerSensorOptions) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1)) {
            return false;
        }
        this.mAccelerometerListener = pAccelerometerListener;
        if (this.mAccelerometerData == null) {
            Display display = ((WindowManager) pContext.getSystemService("window")).getDefaultDisplay();
            int displayRotation = display.getOrientation();
            this.mAccelerometerData = new AccelerometerData(displayRotation);
        }
        registerSelfAsSensorListener(sensorManager, 1, pAccelerometerSensorOptions.getSensorDelay());
        return true;
    }

    public boolean disableAccelerometerSensor(Context pContext) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1)) {
            return false;
        }
        unregisterSelfAsSensorListener(sensorManager, 1);
        return true;
    }

    public boolean enableOrientationSensor(Context pContext, IOrientationListener pOrientationListener) {
        return enableOrientationSensor(pContext, pOrientationListener, new OrientationSensorOptions(SENSORDELAY_DEFAULT));
    }

    public boolean enableOrientationSensor(Context pContext, IOrientationListener pOrientationListener, OrientationSensorOptions pOrientationSensorOptions) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1) || !isSensorSupported(sensorManager, 2)) {
            return false;
        }
        this.mOrientationListener = pOrientationListener;
        if (this.mOrientationData == null) {
            Display display = ((WindowManager) pContext.getSystemService("window")).getDefaultDisplay();
            int displayRotation = display.getOrientation();
            this.mOrientationData = new OrientationData(displayRotation);
        }
        registerSelfAsSensorListener(sensorManager, 1, pOrientationSensorOptions.getSensorDelay());
        registerSelfAsSensorListener(sensorManager, 2, pOrientationSensorOptions.getSensorDelay());
        return true;
    }

    public boolean disableOrientationSensor(Context pContext) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1) || !isSensorSupported(sensorManager, 2)) {
            return false;
        }
        unregisterSelfAsSensorListener(sensorManager, 1);
        unregisterSelfAsSensorListener(sensorManager, 2);
        return true;
    }

    private boolean isSensorSupported(SensorManager pSensorManager, int pType) {
        return pSensorManager.getSensorList(pType).size() > 0;
    }

    private void registerSelfAsSensorListener(SensorManager pSensorManager, int pType, SensorDelay pSensorDelay) {
        Sensor sensor = pSensorManager.getSensorList(pType).get(0);
        pSensorManager.registerListener(this, sensor, pSensorDelay.getDelay());
    }

    private void unregisterSelfAsSensorListener(SensorManager pSensorManager, int pType) {
        Sensor sensor = pSensorManager.getSensorList(pType).get(0);
        pSensorManager.unregisterListener(this, sensor);
    }

    private class UpdateThread extends Thread {
        public UpdateThread() {
            super("UpdateThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(Engine.this.mEngineOptions.getUpdateThreadPriority());
            while (true) {
                try {
                    Engine.this.onTickUpdate();
                } catch (InterruptedException e) {
                    org.anddev.andengine.util.Debug.d("UpdateThread interrupted. Don't worry - this Exception is most likely expected!", e);
                    interrupt();
                    return;
                }
            }
        }
    }

    private static class State {
        boolean mDrawing;

        private State() {
            this.mDrawing = false;
        }

        /* synthetic */ State(State state) {
            this();
        }

        public synchronized void notifyCanDraw() {
            this.mDrawing = true;
            notifyAll();
        }

        public synchronized void notifyCanUpdate() {
            this.mDrawing = false;
            notifyAll();
        }

        public synchronized void waitUntilCanDraw() throws InterruptedException {
            while (!this.mDrawing) {
                wait();
            }
        }

        public synchronized void waitUntilCanUpdate() throws InterruptedException {
            while (this.mDrawing) {
                wait();
            }
        }
    }
}
