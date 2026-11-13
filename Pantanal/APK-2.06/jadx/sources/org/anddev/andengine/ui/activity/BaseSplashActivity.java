package org.anddev.andengine.ui.activity;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.SplashScene;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseSplashActivity extends BaseGameActivity {
    private Camera mCamera;
    private TextureRegion mLoadingScreenTextureRegion;
    private IBitmapTextureAtlasSource mSplashTextureAtlasSource;

    protected abstract Class<? extends Activity> getFollowUpActivity();

    protected abstract EngineOptions.ScreenOrientation getScreenOrientation();

    protected abstract float getSplashDuration();

    protected abstract IBitmapTextureAtlasSource onGetSplashTextureAtlasSource();

    protected float getSplashScaleFrom() {
        return 1.0f;
    }

    protected float getSplashScaleTo() {
        return 1.0f;
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public void onLoadComplete() {
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public Engine onLoadEngine() {
        this.mSplashTextureAtlasSource = onGetSplashTextureAtlasSource();
        int width = this.mSplashTextureAtlasSource.getWidth();
        int height = this.mSplashTextureAtlasSource.getHeight();
        this.mCamera = getSplashCamera(width, height);
        return new Engine(new EngineOptions(true, getScreenOrientation(), getSplashResolutionPolicy(width, height), this.mCamera));
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public void onLoadResources() {
        BitmapTextureAtlas loadingScreenBitmapTextureAtlas = BitmapTextureAtlasFactory.createForTextureAtlasSourceSize(BitmapTexture.BitmapTextureFormat.RGBA_8888, this.mSplashTextureAtlasSource, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mLoadingScreenTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(loadingScreenBitmapTextureAtlas, this.mSplashTextureAtlasSource, 0, 0);
        getEngine().getTextureManager().loadTexture(loadingScreenBitmapTextureAtlas);
    }

    @Override // org.anddev.andengine.ui.IGameInterface
    public Scene onLoadScene() {
        float splashDuration = getSplashDuration();
        SplashScene splashScene = new SplashScene(this.mCamera, this.mLoadingScreenTextureRegion, splashDuration, getSplashScaleFrom(), getSplashScaleTo());
        splashScene.registerUpdateHandler(new TimerHandler(splashDuration, new ITimerCallback() { // from class: org.anddev.andengine.ui.activity.BaseSplashActivity.1
            @Override // org.anddev.andengine.engine.handler.timer.ITimerCallback
            public void onTimePassed(TimerHandler pTimerHandler) {
                BaseSplashActivity.this.startActivity(new Intent(BaseSplashActivity.this, BaseSplashActivity.this.getFollowUpActivity()));
                BaseSplashActivity.this.finish();
            }
        }));
        return splashScene;
    }

    protected Camera getSplashCamera(int pSplashwidth, int pSplashHeight) {
        return new Camera(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, pSplashwidth, pSplashHeight);
    }

    protected IResolutionPolicy getSplashResolutionPolicy(int pSplashwidth, int pSplashHeight) {
        return new RatioResolutionPolicy(pSplashwidth, pSplashHeight);
    }
}
